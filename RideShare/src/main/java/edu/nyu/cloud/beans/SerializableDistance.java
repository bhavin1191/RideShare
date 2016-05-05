/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;

import com.google.maps.model.Distance;

/**
 * This class is similar to {@link Distance} but is also Serializable so that it can be cached.
 * 
 * @author rahulkhanna Date:29-Apr-2016
 */
public class SerializableDistance implements Serializable {

	private static final long serialVersionUID = -4826575819368846051L;

	/**
	 * This is the numeric distance, always in meters. This is intended to be
	 * used only in algorithmic situations, e.g. sorting results by some user
	 * specified metric.
	 */
	private long inMeters;

	/**
	 * This is the human friendly distance. This is rounded and in an
	 * appropriate unit for the request. The units can be overriden with a
	 * request parameter.
	 */
	private String humanReadable;

	public SerializableDistance(){
		this(0L,null);
	}
	
	public SerializableDistance(long inMeters, String humanReadable) {
		this.inMeters = inMeters;
		this.humanReadable = humanReadable;
	}

	@Override
	public String toString() {
		return humanReadable;
	}

	/**
	 * @return the inMeters
	 */
	public long getInMeters() {
		return inMeters;
	}

	public String getHumanReadable() {
		return humanReadable;
	}

	public void setHumanReadable(String humanReadable) {
		this.humanReadable = humanReadable;
	}

	public void setInMeters(long inMeters) {
		this.inMeters = inMeters;
	}
	

}
