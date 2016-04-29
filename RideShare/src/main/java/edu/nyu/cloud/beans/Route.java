/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.Distance;
import com.google.maps.model.Duration;

/**
 * This class is used to represents a route between a source and destination.
 * 
 * @author rahulkhanna
 * Date:05-Apr-2016
 */
public class Route implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1188434524626095499L;
	private List<String> address;
	private Distance distance;
	private Duration timetaken;
	
	public Route()
	{
		address = new ArrayList<String>();
	}
	
	/**
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	/**
	 * @return the timetaken
	 */
	public Duration getTimetaken() {
		return timetaken;
	}

	/**
	 * @param timetaken the timetaken to set
	 */
	public void setTimetaken(Duration timetaken) {
		this.timetaken = timetaken;
	}

	public void addRouteToList(String formattedaddress)
	{
		this.address.add(formattedaddress);
		
	}
	
	public List<String> getLegs() 
	{
		return address;
	}

}
