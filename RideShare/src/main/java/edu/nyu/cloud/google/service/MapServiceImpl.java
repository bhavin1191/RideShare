
package edu.nyu.cloud.google.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.LogManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import edu.nyu.cloud.beans.Route;

/**
 * @author rahulkhanna Date:05-Apr-2016
 * @author bhavinmehta Date:16-Apr-2016 [Updated]
 */
public class MapServiceImpl implements MapService {

	private final GeoApiContext context;
	
	//Logger sets from this GeoApiCOntext
	public MapServiceImpl() {
		this.context = new GeoApiContext().setApiKey("AIzaSyC_xRB8quFF-9SM2bxokO9KekSyoRsqZsE");
	}
	
	@Override
	public List<Route> fetchPossibleRoutes(String source, String destination) {
		try 
		{
			DirectionsApiRequest requestd = DirectionsApi.getDirections(context, source,destination);
			requestd.mode(TravelMode.DRIVING);
			requestd.alternatives(true);
			DirectionsRoute[] getroutes = requestd.await();
			   //System.out.println(routes.length);
			int done = displayAddressableRoutes(getroutes);
			if (done == 0)
			{
				throw new Exception("Did not process Routes");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public int displayAddressableRoutes(DirectionsRoute[] routes)
	{ 
		try
		{
			int pathcount = 0;
			for (DirectionsRoute directions : routes)
			{
				List<LatLng> list = directions.overviewPolyline.decodePath();
				String previous_location = "";
				++ pathcount;
				for(int i = 0 ; i < list.size(); i++)
				{
					String jsonresult = getLocationInfo(list.get(i).toString()); 
					JSONObject ret = new JSONObject(jsonresult);
					JSONObject location = null;
					String location_string = null;
					
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
					    	System.out.println("Route "+pathcount+"-> Street Address:" + location_string);
					    	previous_location = location_string;
					    }
					} //try
					catch (Exception e1) 
					{
					    //e1.printStackTrace();
					}
				}//for
				
			}//for
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public String getLocationInfo( String latlng) throws IOException 
	{
		String result=null;
	    HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+latlng);
	    CloseableHttpClient client =  HttpClientBuilder.create().build();
	    HttpResponse response;
	    StringBuilder stringBuilder = new StringBuilder();

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
	
	/*public static void main(String[] args) {
		//LogManager.getLogManager().reset();
		//Set this to turn off logger for logback-classic from org.springframework
		Logger Logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.apache.http");
		Logger.setLevel(Level.OFF);
		MapService service = new MapServiceImpl();
		service.fetchPossibleRoutes("New York", "New Jersey");
	
	}*/

}
