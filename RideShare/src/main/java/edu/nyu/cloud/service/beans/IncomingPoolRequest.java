package edu.nyu.cloud.service.beans;

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
	private final String date;
	private final String userId;
	private final Route selectRoute;
	private final String carType;

	public IncomingPoolRequest(){
		this(0,null,null,null,null,null,null);
	}
	
	/**
	 * @param numberOfPassengers
	 * @param destination
	 * @param source
	 * @param date
	 * @param userId
	 */
	public IncomingPoolRequest(int numberOfPassengers, String destination, String source, String date, String userId,Route selectRoute,String carType) {
		super();
		this.numberOfPassengers = numberOfPassengers;
		this.destination = destination;
		this.source = source;
		this.date = date;
		this.userId = userId;
		this.selectRoute = selectRoute;
		this.carType = carType;
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

	public String getDate() {
		return date;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * @return the selectRoute
	 */
	public Route getSelectRoute() {
		return selectRoute;
	}

	/**
	 * @return the carType
	 */
	public String getCarType() {
		return carType;
	}

}
