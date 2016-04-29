package edu.nyu.cloud.service.beans;

import java.util.Date;
import java.util.List;

import edu.nyu.cloud.beans.Route;

/**
 * This class represents the new car pool request which is been opened by the User to share the ride.  
 * 
 * @author rahulkhanna Date:04-Apr-2016
 */
public class IncomingPoolRequest {

	private final int numberOfPassengers;
	private final String destination;
	private final String source;
	private final Date date;
	private final String userId;
	private final List<Route> selectRoute;

	public IncomingPoolRequest(){
		this(0,null,null,null,null,null);
	}
	
	/**
	 * @param numberOfPassengers
	 * @param destination
	 * @param source
	 * @param date
	 * @param userId
	 */
	public IncomingPoolRequest(int numberOfPassengers, String destination, String source, Date date, String userId,List<Route> selectRoute) {
		super();
		this.numberOfPassengers = numberOfPassengers;
		this.destination = destination;
		this.source = source;
		this.date = date;
		this.userId = userId;
		this.selectRoute = selectRoute;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public String getDestination() {
		return destination;
	}

	public String getSource() {
		return source;
	}

	public Date getDate() {
		return date;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * @return the selectRoute
	 */
	public List<Route> getSelectRoute() {
		return selectRoute;
	}

}
