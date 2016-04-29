/**
 * 
 */
package edu.nyu.cloud.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This class represensts a new Ride that has been create by a user for sharing.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class NewRide implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final String requester;
	private final String source;
	private final String destination;
	private final Date timeOfTrip;
	private final List<Route> selectedRoute;

	/**
	 * Constructor
	 * 
	 * @param requester
	 * @param source
	 * @param destination
	 * @param timeOfTrip
	 * @param possibleRoutes
	 */
	public NewRide(String requester, String source, String destination, Date timeOfTrip, List<Route> selectedRoute) {
		super();
		this.requester = requester;
		this.source = source;
		this.destination = destination;
		this.timeOfTrip = timeOfTrip;
		this.selectedRoute = selectedRoute;
	}

	public String getRequester() {
		return requester;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public Date getTimeOfTrip() {
		return timeOfTrip;
	}

	/**
	 * @return the selectedRoute
	 */
	public List<Route> getSelectedRoute() {
		return selectedRoute;
	}

}
