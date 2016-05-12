/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.maps.model.LatLng;

/**
 * This class is similar to {@link LatLng} but it is Serializable so that it can
 * be cached.
 * 
 * @author rahulkhanna Date:29-Apr-2016
 */
public class SerializableLatLng implements Serializable {

	private static final long serialVersionUID = 3933584737903911424L;
	private long id;

	/**
	 * The latitude of this location.
	 */
	private double lat;

	/**
	 * The longitude of this location.
	 */
	private double lng;
	
	@JsonIgnore
	private long routeId;

	/**
	 * Construct a location with a latitude longitude pair.
	 */
	public SerializableLatLng(double lat, double lng,long route) {
		this.lat = lat;
		this.lng = lng;
		this.routeId = route;
	}

	@Override
	public String toString() {
		// Enforce Locale to English for double to string conversion
		return String.format(Locale.ENGLISH, "%f,%f", lat, lng);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}

	public SerializableLatLng() {
		this(0D,0D,0l);
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public long getRouteId() {
		return routeId;
	}

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}
	
	
}
