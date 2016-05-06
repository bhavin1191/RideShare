/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.List;

import com.google.maps.model.LatLng;

import edu.nyu.cloud.beans.NewRide;

/**
 * This cache is specific to Rides.
 * 
 * @author rahulkhanna
 * Date:25-Apr-2016
 */
public interface RideCache {
	
	public List<NewRide> getActiveRidesFromGivenSourceAndDestination(String source, String destination);
	
	public List<NewRide> getActiveRidesNearToGivenCoordinates(LatLng latLng);
	
	public void addNewActiceRide(NewRide ride);
	
	public NewRide getRideByGivenRideId(long rideId);

}
