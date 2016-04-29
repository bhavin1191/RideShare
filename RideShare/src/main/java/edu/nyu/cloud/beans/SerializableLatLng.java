/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;
import java.util.Locale;

import com.google.maps.model.LatLng;

/**
 * This class is similar to {@link LatLng} but it is Serializable so that it can
 * be cached.
 * 
 * @author rahulkhanna Date:29-Apr-2016
 */
public class SerializableLatLng implements Serializable {

	private static final long serialVersionUID = 3933584737903911424L;

	/**
	 * The latitude of this location.
	 */
	public double lat;

	/**
	 * The longitude of this location.
	 */
	public double lng;

	/**
	 * Construct a location with a latitude longitude pair.
	 */
	public SerializableLatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public String toString() {
		// Enforce Locale to English for double to string conversion
		return String.format(Locale.ENGLISH, "%f,%f", lat, lng);
	}

}
