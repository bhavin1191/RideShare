package edu.nyu.cloud.google.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.LogManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableInstant;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.google.maps.*;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.uber.sdk.rides.client.Response;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import edu.nyu.cloud.beans.Route;

/**
 * @author rahulkhanna Date:05-Apr-2016
 * @author bhavinmehta Date:16-Apr-2016 [Updated]
 */
public class MapServiceImpl implements MapService {

	private final GeoApiContext context;
	static ConcurrentHashMap<String, List<Route>> hashmap = new ConcurrentHashMap<String, List<Route>>();;
	//Logger sets from this GeoApiContext
	public MapServiceImpl() {
		this.context = new GeoApiContext().setApiKey("AIzaSyC1wCCjfNFeVQzMk5wKPb4KSXngr6TSVtY");
	}
	
	//fetch Route Type Object with list of waypoints or legs address, distance and duration in traffic.
	@Override
	public List<Route> fetchPossibleRoutes(String source, String destination, DateTime datetime) {
		try 
		{
			DirectionsApiRequest requestd = DirectionsApi.getDirections(context,source,destination);
			requestd.mode(TravelMode.DRIVING);
			requestd.alternatives(true).optimizeWaypoints(true);
			requestd.departureTime(datetime);
			DirectionsRoute[] getroutes = requestd.await();
			int pathcount = 0;
			List<Route> listroutes = new ArrayList<Route>();
			
			for (DirectionsRoute directions : getroutes)
			{
				List<LatLng> list = directions.overviewPolyline.decodePath();
				//System.out.println(list.toString());
				
				++ pathcount;
				if(hashmap.containsKey(source+"_"+destination))
				{
					return hashmap.get(source+"_"+destination);
					/*System.out.println("Gotcha");
					break;*/
				}
				else
				{
					Route newroute = new Route();
					Route currentRoute = displayAddressableRoutes(list,newroute);
					DirectionsLeg[] leg = directions.legs;
					currentRoute.setDistance(leg[0].distance);
					currentRoute.setTimetaken(leg[0].durationInTraffic);
					listroutes.add(currentRoute);
				}
				
			 }//for	
			
			if(!hashmap.containsKey(source+"_"+destination))
			{
				hashmap.put(source+"_"+destination,listroutes);
			}
			
			if (hashmap.isEmpty())
			{
				throw new Exception("Did not process Routes");
			}
			else
			{
				return hashmap.get(source+"_"+destination);
				/*System.out.println(hashmap.size());
				for (Entry<String, List<Route>> entry : hashmap.entrySet()) 
				{
				    String key = entry.getKey().toString();
				    List<Route> value = entry.getValue();
				    System.out.println("key, " + key + " value " + value.get(0).getDistance() );
				}*/
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Route displayAddressableRoutes(List<LatLng> list,Route newroute)
	{ 
		JSONObject location = null;
		String location_string = null;
		String previous_location = "";
		try
		{
			for(int i = 0 ; i < list.size(); i++)
			{
				String jsonresult = getLocationInfo(list.get(i).toString()); 
				JSONObject ret = new JSONObject(jsonresult);
				try 
				{
				    //Get JSON Array called "results" and then get the 0th complete object as JSON 
					if(!ret.getJSONArray("results").isNull(0))
				        location = ret.getJSONArray("results").getJSONObject(0);
					
				    // Get the value of the attribute whose name is "formatted_string"
				    location_string = location.getString("formatted_address");
				    if(previous_location.equalsIgnoreCase(location_string))
				    {
				    	previous_location = location_string;
				    }
				    else
				    {
				    	//System.out.println("Route "+pathcount+"-> Street Address:" + location_string);
				    	newroute.addRouteToList(location_string);
				    	previous_location = location_string;
				    }
				} //try
				catch (Exception e1) 
				{
				    //e1.printStackTrace();
				}
			}//for
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return newroute;
	}
	
	//Provide lat,lng format in string to fetch actual address.
	public String getLocationInfo(String latlng) throws IOException 
	{
		String result=null;
	    HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+latlng);
	    CloseableHttpClient client =  HttpClientBuilder.create().build();
	    HttpResponse response;
	    try {
	        response = client.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        InputStream stream = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null)
	        {
	            sb.append(line + "\n");
	        }
	        result = sb.toString();
	    } catch (Exception e) {
	    	    e.printStackTrace();
	        }
       client.close();
	   return result;
	}
	
	/*public static void main(String[] args) throws ParseException {
		LogManager.getLogManager().reset();
		//Set this to turn off logger for logback-classic from org.springframework
		Logger Logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.apache.http");
		Logger.setLevel(Level.OFF);
		MapService service = new MapServiceImpl();
		//Date date = new Date();
		//System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "31-08-2016 10:20:56";
		Date date1 = sdf.parse(dateInString);
		DateTime datetime = new DateTime(date1);
		System.out.println(datetime);
		service.fetchPossibleRoutes("74th St, Brooklyn, NY", "Jay St, Brooklyn, NY 11201",datetime);
		service.fetchPossibleRoutes("New York", "Jay St, Brooklyn, NY 11201",datetime);
		service.fetchPossibleRoutes("74th St, Brooklyn, NY", "Jay St, Brooklyn, NY 11201",datetime);
		System.out.println("exit");
		//System.exit(0);
	}*/

}