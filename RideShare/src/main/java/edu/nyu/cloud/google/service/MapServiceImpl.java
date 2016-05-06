package edu.nyu.cloud.google.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import edu.nyu.cloud.dao.db.IDGenerator;

/**
 * @author rahulkhanna Date:05-Apr-2016
 * @author bhavinmehta Date:26-Apr-2016 [Updated]
 */
public class MapServiceImpl implements MapService {

	private static final String HTTP_GET_URL = "http://maps.google.com/maps/api/geocode/json?latlng=";
	private final GeoApiContext context;
	private final CloseableHttpClient client;
	private final IDGenerator routeIdGenerator;
	private final IDGenerator latlngIdGenerator;
	
	public MapServiceImpl(IDGenerator latlngIdGenerator, IDGenerator routeIdGenerator) {
		this.context = new GeoApiContext().setApiKey("AIzaSyC_xRB8quFF-9SM2bxokO9KekSyoRsqZsE");
		client = HttpClientBuilder.create().build();
		this.routeIdGenerator =routeIdGenerator;
		this.latlngIdGenerator = latlngIdGenerator;
	}

	// fetch Route Type Object with list of waypoints or legs address, distance
	// and duration in traffic.
	@Override
	public Set<Route> fetchPossibleRoutes(String source, String destination) {
		Set<Route> listroutes = new HashSet<Route>();
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
				SerializableDistance distance = new SerializableDistance(leg[0].distance.inMeters,leg[0].distance.humanReadable);
				SerializableDuration duration = new SerializableDuration(leg[0].duration.inSeconds,leg[0].duration.humanReadable);
				Set<SerializableLatLng> latLngs = new HashSet<>(latlngofeachpath.size());
				List<String> addresses = getAddressableRoutes(uniqueWaypoints);
				Route newRoute = new Route(0l,addresses, distance, duration, latLngs);
				
				for (LatLng val : latlngofeachpath) {
					SerializableLatLng latLng = new SerializableLatLng(val.lat, val.lng,0l);
					latLng.setId(latlngIdGenerator.getNewId());
					latLngs.add(latLng);
				}
				listroutes.add(newRoute);
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

	public IDGenerator getRouteIdGenerator() {
		return routeIdGenerator;
	}

	public IDGenerator getLatlngIdGenerator() {
		return latlngIdGenerator;
	}

}