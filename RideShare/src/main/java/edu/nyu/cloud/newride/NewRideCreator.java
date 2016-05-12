/**
 * 
 */
package edu.nyu.cloud.newride;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.uber.sdk.rides.client.error.ApiException;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.beans.SerializableLatLng;
import edu.nyu.cloud.beans.UberRide;
import edu.nyu.cloud.beans.UserProfile;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.newride.dao.db.NewRideDao;
import edu.nyu.cloud.newride.dao.db.UberRideDao;
import edu.nyu.cloud.service.beans.IncomingPoolRequest;
import edu.nyu.cloud.service.beans.EmailNotification;
import edu.nyu.cloud.service.beans.EmailService;
import edu.nyu.cloud.user.dao.db.hibernate.*;

/**
 * This class is used to create a new ride which will be shared by other users.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class NewRideCreator {

	private final NewRideDao dao;	
	private final IDGenerator routeIDGenerator;
	private final UberRideDao uberdao;
	private final EmailNotification emailNotification;

	/**
	 * Constructor
	 * 
	 * @param dao
	 */
	public NewRideCreator(NewRideDao dao, IDGenerator routeIDGenerator,
			UberRideDao uberdao, EmailNotification emailNotification) {
		super();
		this.dao = dao;		
		this.routeIDGenerator = routeIDGenerator;		
		this.uberdao = uberdao;
		this.emailNotification = emailNotification;
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
		
		if(newPoolRequest.getCarType().toLowerCase().equals("mycar"))
		{
			NewRide ride = new NewRide(newPoolRequest.getUserId(), newPoolRequest.getSource(),
					newPoolRequest.getDestination(), date, newPoolRequest.getSelectRoute());
			long newRouteId = routeIDGenerator.getNewId();
			for (SerializableLatLng serializableLatLng : ride.getSelectedRoute().getLatlng()) {
				serializableLatLng.setRouteId(newRouteId);
				
				}
		
			System.out.println("Route id for new ride :"+newRouteId);
			ride.getSelectedRoute().setId(newRouteId);
			dao.saveNewRide(ride);
			emailBody = constructEmailBody(ride,null);
			emailNotification.sendConfirmationEmail(newPoolRequest.getUserId(), emailBody); // Send confirmation email for non uber ride.
			
		}
		else if(newPoolRequest.getCarType().toLowerCase().equals("uber"))
		{
			
			NewUberRide newUber = null;
			try {
				newUber = new NewUberRide();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<UberRide> uberRide = null;
			try {
				uberRide = newUber.confirmRide(newPoolRequest.getSource(),newPoolRequest.getDestination(),newPoolRequest.getNumberOfPassengers());
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			uberdao.saveUberRide(uberRide.get(0));
			emailBody = constructEmailBody(null,uberRide.get(0));
			emailNotification.sendConfirmationEmail(newPoolRequest.getUserId(), emailBody);
		}
		
		
	}
	
	public String constructEmailBody( NewRide ride, UberRide uberRide)
	{
		StringBuilder emailBody = new StringBuilder();
		if(ride != null)
		{
			emailBody.append("Destination : "+ ride.getDestination() + "\n");
			emailBody.append("Ride ID :" + ride.getId() + "\n");
			emailBody.append("Time of Trip :"+ ride.getTimeOfTrip() +"\n");
			emailBody.append("Requested by :" + ride.getRequester() + "\n");
		}
		else if(uberRide != null)
		{
			emailBody.append("Passengers :" + uberRide.getCapacity() + "\n");
			emailBody.append("Uber Ride ID :" +uberRide.getId() +"\n");
			emailBody.append("Uber Product :" + uberRide.getProductdisplayname() + "\n");
			emailBody.append("Ride Id :" +uberRide.getRideid()+ "\n");
		}
		else
		{
			emailBody.append("Ride created successfully we will provide details shortly");
		}
		return emailBody.toString();
	}

}
