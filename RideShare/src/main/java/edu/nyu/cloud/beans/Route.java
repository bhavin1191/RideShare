/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * This class is used to represents a route between a source and destination.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class Route implements Serializable {

	private static final long serialVersionUID = 3571130958082192755L;
	private Long id;
	private List<String> address;
	private SerializableDistance distance;
	private SerializableDuration timetaken;
	private Set<SerializableLatLng> latlng;

	public Route() {
		this(null, null, null, null, null);
	}

	/**
	 * @param address
	 * @param distance
	 * @param timetaken
	 * @param latlng
	 */
	public Route(Long id, List<String> address, SerializableDistance distance, SerializableDuration timetaken,
			Set<SerializableLatLng> latlng) {
		super();
		this.id = id;
		this.address = address;
		this.distance = distance;
		this.timetaken = timetaken;
		this.latlng = latlng;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public Set<SerializableLatLng> getLatlng() {
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

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public void setDistance(SerializableDistance distance) {
		this.distance = distance;
	}

	public void setTimetaken(SerializableDuration timetaken) {
		this.timetaken = timetaken;
	}

	public void setLatlng(Set<SerializableLatLng> latlng) {
		this.latlng = latlng;
	}

}
