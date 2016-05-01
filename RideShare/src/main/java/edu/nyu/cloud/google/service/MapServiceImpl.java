package edu.nyu.cloud.google.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
//import com.uber.sdk.rides.client.Response;

import edu.nyu.cloud.beans.Route;
import edu.nyu.cloud.beans.SerializableDistance;
import edu.nyu.cloud.beans.SerializableDuration;
import edu.nyu.cloud.beans.SerializableLatLng;

/**
 * @author rahulkhanna Date:05-Apr-2016
 * @author bhavinmehta Date:26-Apr-2016 [Updated]
 */
public class MapServiceImpl implements MapService {

	private static final String HTTP_GET_URL = "http://maps.google.com/maps/api/geocode/json?latlng=";
	private final GeoApiContext context;
	private final CloseableHttpClient client;
	
	public MapServiceImpl() {
		this.context = new GeoApiContext().setApiKey("AIzaSyC_xRB8quFF-9SM2bxokO9KekSyoRsqZsE");
		client = HttpClientBuilder.create().build();
	}

	// fetch Route Type Object with list of waypoints or legs address, distance
	// and duration in traffic.
	@Override
	public List<Route> fetchPossibleRoutes(String source, String destination) {
		List<Route> listroutes = new ArrayList<Route>();
		try {
			DirectionsApiRequest requestd = DirectionsApi.getDirections(context, source, destination);
			requestd.mode(TravelMode.DRIVING);
			requestd.alternatives(true).optimizeWaypoints(true);
			DirectionsRoute[] getroutes = requestd.await();
			for (DirectionsRoute directions : getroutes) {
				List<LatLng> latlngofeachpath = directions.overviewPolyline.decodePath();
				DirectionsLeg[] leg = directions.legs;

				DirectionsStep[] step = leg[0].steps;

				List<LatLng> uniqueWaypoints = findUniqueWaypoints(step);
				SerializableDistance distance = new SerializableDistance(leg[0].distance);
				SerializableDuration duration = new SerializableDuration(leg[0].duration);
				List<SerializableLatLng> latLngs = new ArrayList<>(latlngofeachpath.size());
				for (LatLng val : latlngofeachpath) {
					SerializableLatLng ladLng = new SerializableLatLng(val.lat, val.lng);
					latLngs.add(ladLng);
				}
				List<String> addresses = getAddressableRoutes(uniqueWaypoints);
				Route newroute = new Route(addresses, distance, duration, latLngs);
				listroutes.add(newroute);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listroutes;
	}

	// find unique waypoints for each route
	// @param: steps from each route found from Google Map Api
	public List<LatLng> findUniqueWaypoints(DirectionsStep[] step) throws Exception {
		List<LatLng> uniqueWaypoints = new ArrayList<LatLng>();
		LatLng current = new LatLng(0.0, 0.0);
		for (DirectionsStep waypoints : step) {
			if (!current.toString().equals(waypoints.startLocation.toString())) {
				uniqueWaypoints.add(waypoints.startLocation);
			}
			uniqueWaypoints.add(waypoints.endLocation);
			current = waypoints.endLocation;
		}
		return uniqueWaypoints;
	}

	private List<String> getAddressableRoutes(List<LatLng> latLngs) {
		JSONObject locationInJson = null;
		String location = null;
		String previousLocation = "";
		List<String> addresses = new ArrayList<>(latLngs.size());
		try {
			for (int i = 0; i < latLngs.size(); i++) {
				String result = getLocationInfo(latLngs.get(i).toString());
				JSONObject resultInJson = new JSONObject(result);
				// Get JSON Array called "results" and then get the 0th
				// complete object as JSON
				if (!resultInJson.getJSONArray("results").isNull(0))
					locationInJson = resultInJson.getJSONArray("results").getJSONObject(0);
				// Get the value of the attribute whose name is
				// "formatted_string"
				location = locationInJson.getString("formatted_address");
				if (previousLocation.equalsIgnoreCase(location)) {
					previousLocation = location;
				} else {
					// System.out.println("Route -> Street Address:" +
					// location);
					addresses.add(location);
					previousLocation = location;
				}
			}
		} catch (Exception e) {
			//throw new RuntimeException(e);
		}
		return addresses;
	}

	/**
	 * This function is used to provide lat,lng formatted in string to fetch
	 * actual address.
	 * 
	 * @param latlng
	 * @return
	 * @throws IOException
	 */
	private String getLocationInfo(String latlng) throws IOException {
		String result = null;
		HttpGet httpGet = new HttpGet(HTTP_GET_URL + latlng);
		HttpResponse response;
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			httpGet.releaseConnection();
		}
		return result;
	}

}