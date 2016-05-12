/**
 * 
 */
package edu.nyu.cloud.service.beans;

/**
 * This class represents a new request in search for ride.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class NewRideSharingRequest {

	private final String userId;
	private final String source;
	private final String destination;
	private final String date;
	private int numberOfPassengers;

	public NewRideSharingRequest() {
		this(null, null, null, null, 0);
	}

	/**
	 * Constructor
	 * 
	 * @param userId
	 * @param source
	 * @param destination
	 * @param date
	 */
	public NewRideSharingRequest(String userId, String source, String destination, String date,
			int numberOfSeatsRequired) {
		super();
		this.userId = userId;
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.numberOfPassengers = numberOfSeatsRequired;
	}

	public String getUserId() {
		return userId;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public String getDate() {
		return date;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

		
}
