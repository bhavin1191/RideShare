/**
 * 
 */
package edu.nyu.cloud.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.Distance;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

/**
 * This class is used to represents a route between a source and destination.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class Route {

	private List<String> address;
	private Distance distance;
	private Duration timetaken;
	private List<LatLng> latlng;

	public Route() {
		address = new ArrayList<String>();
	}

	/**
	 * @return the latlng
	 */
	public List<LatLng> getLatlng() {
		return latlng;
	}

	/**
	 * @param latlng
	 *            the latlng to set
	 */
	public void setLatlng(List<LatLng> latlng) {
		this.latlng = latlng;
	}

	/**
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
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
	 * @param timetaken
	 *            the timetaken to set
	 */
	public void setTimetaken(Duration timetaken) {
		this.timetaken = timetaken;
	}

	public void addRouteToList(String formattedaddress) {
		this.address.add(formattedaddress);

	}

	public List<String> getLegs() {
		return address;
	}

}
