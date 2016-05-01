/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;

import com.google.maps.model.Duration;

/**
 * This class is similar to {@link Duration} but is also serializable so that it
 * can be cached.
 * 
 * @author rahulkhanna Date:29-Apr-2016
 */
public class SerializableDuration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1205821902753971231L;

	/**
	 * This is the numeric duration, in seconds. This is intended to be used
	 * only in algorithmic situations, e.g. sorting results by some user
	 * specified metric.
	 */
	private final long inSeconds;

	/**
	 * This is the human friendly duration. Use this for display purposes.
	 */
	private final String humanReadable;

	public SerializableDuration(Duration durationInTraffic) {
		this.inSeconds = durationInTraffic.inSeconds;
		this.humanReadable = durationInTraffic.humanReadable;
	}

	@Override
	public String toString() {
		return humanReadable;
	}

	/**
	 * @return the inSeconds
	 */
	public long getInSeconds() {
		return inSeconds;
	}
}
