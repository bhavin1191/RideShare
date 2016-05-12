/**
 * 
 */
package edu.nyu.cloud.newride;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.beans.SerializableLatLng;
import edu.nyu.cloud.beans.UberRide;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.newride.dao.db.NewRideDao;
import edu.nyu.cloud.newride.dao.db.UberRideDao;
import edu.nyu.cloud.service.beans.EmailNotification;
import edu.nyu.cloud.service.beans.IncomingPoolRequest;
import edu.nyu.cloud.sqs.SQSJobSubmitter;

/**
 * This class is used to create a new ride which will be shared by other users.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class NewRideCreator {

	private final NewRideDao dao;
	private final IDGenerator routeIDGenerator;
	private final EmailNotification emailNotification;
	private final SQSJobSubmitter uberRideRequestGenerator;

	/**
	 * Constructor
	 * 
	 * @param dao
	 */
	public NewRideCreator(NewRideDao dao, IDGenerator routeIDGenerator,EmailNotification emailNotification, SQSJobSubmitter uberRideRequestGenerator ) {
		super();
		this.dao = dao;
		this.routeIDGenerator = routeIDGenerator;
		this.emailNotification = emailNotification;
		this.uberRideRequestGenerator = uberRideRequestGenerator;
	}

	public void createNewRideForPool(IncomingPoolRequest newPoolRequest) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		String emailBody = null;
		try {
			date = format.parse(newPoolRequest.getDate());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		if (newPoolRequest.getCarType().toLowerCase().equals("mycar")) {
			NewRide ride = new NewRide(newPoolRequest.getUserId(), newPoolRequest.getSource(),
					newPoolRequest.getDestination(), date, newPoolRequest.getSelectRoute(),
					newPoolRequest.getNumberOfPassengers());
			long newRouteId = routeIDGenerator.getNewId();
			for (SerializableLatLng serializableLatLng : ride.getSelectedRoute().getLatlng()) {
				serializableLatLng.setRouteId(newRouteId);

			}

			System.out.println("Route id for new ride :" + newRouteId);
			ride.getSelectedRoute().setId(newRouteId);
			dao.saveNewRide(ride);
			emailBody = constructEmailBody(ride, null);
			emailNotification.sendConfirmationEmail(newPoolRequest.getUserId(), emailBody);
		} else {
			uberRideRequestGenerator.sendJobRequest(newPoolRequest);
			emailNotification.sendConfirmationEmail(newPoolRequest.getUserId(), "Your ride has been scheduled");
		}

	}

	public String constructEmailBody(NewRide ride, UberRide uberRide) {
		StringBuilder emailBody = new StringBuilder();
		if (ride != null) {
			emailBody.append("Destination : " + ride.getDestination() + "\n");
			emailBody.append("Ride ID :" + ride.getId() + "\n");
			emailBody.append("Time of Trip :" + ride.getTimeOfTrip() + "\n");
			emailBody.append("Requested by :" + ride.getRequester() + "\n");
		} else if (uberRide != null) {
			emailBody.append("Passengers :" + uberRide.getCapacity() + "\n");
			emailBody.append("Uber Ride ID :" + uberRide.getId() + "\n");
			emailBody.append("Uber Product :" + uberRide.getProductdisplayname() + "\n");
			emailBody.append("Ride Id :" + uberRide.getRideid() + "\n");
		} else {
			emailBody.append("Ride created successfully we will provide details shortly");
		}
		return emailBody.toString();
	}

}
