package edu.nyu.cloud.beans;

public class RideAvailableForSharing {

	private String creator;
	private int seatsAvailable;
	private String rideId;
	private String source;
	private String destination;
	private UserProfile profile;
	
	
	/**
	 * @param creator
	 * @param seatsAvailable
	 * @param rideId
	 * @param source
	 * @param destination
	 */
	public RideAvailableForSharing(String creator, int seatsAvailable, String rideId, String source,
			String destination) {
		super();
		this.creator = creator;
		this.seatsAvailable = seatsAvailable;
		this.rideId = rideId;
		this.source = source;
		this.destination = destination;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public String getRideId() {
		return rideId;
	}
	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the profile
	 */
	public UserProfile getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
		
}
