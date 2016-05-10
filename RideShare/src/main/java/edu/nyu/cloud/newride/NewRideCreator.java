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
	private final EmailService emailService;

	/**
	 * Constructor
	 * 
	 * @param dao
	 */
	public NewRideCreator(NewRideDao dao, IDGenerator routeIDGenerator, EmailService emailService,
			UberRideDao uberdao) {
		super();
		this.dao = dao;		
		this.routeIDGenerator = routeIDGenerator;		
		this.emailService=emailService;
		this.uberdao = uberdao;
	}

	public void createNewRideForPool(IncomingPoolRequest newPoolRequest) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		
		try {
			date = format.parse(newPoolRequest.getDate());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		NewRide ride = new NewRide(newPoolRequest.getUserId(), newPoolRequest.getSource(),
				newPoolRequest.getDestination(), date, newPoolRequest.getSelectRoute());
		long newRouteId = routeIDGenerator.getNewId();
		for (SerializableLatLng serializableLatLng : ride.getSelectedRoute().getLatlng()) {
			serializableLatLng.setRouteId(newRouteId);
			
			}
	
		System.out.println("Route id for new ride :"+newRouteId);
		ride.getSelectedRoute().setId(newRouteId);
		dao.saveNewRide(ride);
		if(newPoolRequest.getCarType().toLowerCase().equals("uber"))
		{
			ride = null;
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
		    sendConfirmationEmail(newPoolRequest.getUserId(), ride, uberRide.get(0));
		}
		if(ride != null)
		{
			sendConfirmationEmail(newPoolRequest.getUserId(), ride, null); // Send confirmation email for non uber ride.
		}
		
	}
	
	public void sendConfirmationEmail(String userId, NewRide ride, UberRide uberRide)
	{
		UserDaoImpl userDaoImpl = new UserDaoImpl(null, null, routeIDGenerator);
		UserProfile userProfileObj = userDaoImpl.getUserProfileByUserId(userId);		//Get User's email address from UserProfile table using id.
		String emailAddress = userProfileObj.getEmailAddress();
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
		emailService.sendEmail(emailAddress, emailBody.toString());
	}

}
