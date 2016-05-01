/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to represents a route between a source and destination.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class Route implements Serializable{

	private static final long serialVersionUID = 3571130958082192755L;
	private final List<String> address;
	private final SerializableDistance distance;
	private final SerializableDuration timetaken;
	private final List<SerializableLatLng> latlng;

	/**
	 * @param address
	 * @param distance
	 * @param timetaken
	 * @param latlng
	 */
	public Route(List<String> address, SerializableDistance distance, SerializableDuration timetaken,
			List<SerializableLatLng> latlng) {
		super();
		this.address = address;
		this.distance = distance;
		this.timetaken = timetaken;
		this.latlng = latlng;
	}

	/**
	 * @return the timetaken
	 */
	public SerializableDuration getTimetaken() {
		return timetaken;
	}

	/**
	 * @return the latlng
	 */
	public List<SerializableLatLng> getLatlng() {
		return latlng;
	}

	/**
	 * @return the distance
	 */
	public SerializableDistance getDistance() {
		return distance;
	}

	/**
	 * @return the address
	 */
	public List<String> getAddress() {
		return address;
	}

}
