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
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
//import com.uber.sdk.rides.client.Response;

import edu.nyu.cloud.beans.Route;
import edu.nyu.cloud.cache.Cache;

/**
 * @author rahulkhanna Date:05-Apr-2016
 * @author bhavinmehta Date:16-Apr-2016 [Updated]
 */
public class MapServiceImpl implements MapService {

	private static final String HTTP_GET_URL = "http://maps.google.com/maps/api/geocode/json?latlng=";
	private final GeoApiContext context;

	public MapServiceImpl(Cache applicationCache) {
		this.context = new GeoApiContext().setApiKey("AIzaSyC1wCCjfNFeVQzMk5wKPb4KSXngr6TSVtY");
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
				List<LatLng> list = directions.overviewPolyline.decodePath();
				Route newroute = new Route();
				Route currentRoute = getAddressableRoutes(list, newroute);
				DirectionsLeg[] leg = directions.legs;
				currentRoute.setDistance(leg[0].distance);
				currentRoute.setTimetaken(leg[0].durationInTraffic);
				listroutes.add(currentRoute);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listroutes;
	}

	private Route getAddressableRoutes(List<LatLng> list, Route newroute) {
		JSONObject locationInJson = null;
		String location = null;
		String previousLocation = "";
		try {
			for (int i = 0; i < list.size(); i++) {
				String result = getLocationInfo(list.get(i).toString());
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
					// System.out.println("Route "+pathcount+"-> Street
					// Address:" + location_string);
					newroute.addRouteToList(location);
					previousLocation = location;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newroute;
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
		CloseableHttpClient client = HttpClientBuilder.create().build();
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
		} finally {
			client.close();
		}
		return result;
	}
}