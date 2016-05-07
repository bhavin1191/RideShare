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

	private long id;
	
	/**
	 * This is the numeric duration, in seconds. This is intended to be used
	 * only in algorithmic situations, e.g. sorting results by some user
	 * specified metric.
	 */
	private long inSeconds;

	/**
	 * This is the human friendly duration. Use this for display purposes.
	 */
	private String humanReadable;

	public SerializableDuration(){
		this(0l,null);
	}
	
	public SerializableDuration(long inSeconds, String humanReadable) {
		this.inSeconds = inSeconds;
		this.humanReadable = humanReadable;
	}

	
	
	public String getHumanReadable() {
		return humanReadable;
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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInSeconds(long inSeconds) {
		this.inSeconds = inSeconds;
	}

	public void setHumanReadable(String humanReadable) {
		this.humanReadable = humanReadable;
	}


}
