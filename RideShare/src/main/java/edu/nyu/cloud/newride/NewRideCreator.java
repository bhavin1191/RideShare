/**
 * 
 */
package edu.nyu.cloud.newride;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.beans.SerializableLatLng;
import edu.nyu.cloud.beans.UserProfile;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.newride.dao.db.NewRideDao;
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

	/**
	 * Constructor
	 * 
	 * @param dao
	 */
	public NewRideCreator(NewRideDao dao, IDGenerator routeIDGenerator) {
		super();
		this.dao = dao;
		this.routeIDGenerator = routeIDGenerator;
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
		EmailService emailService = new EmailService();
		UserDaoImpl userDaoImpl = new UserDaoImpl(null, null, routeIDGenerator);
		UserProfile userProfileObj = userDaoImpl.getUserProfileByUserId((String)newPoolRequest.getUserId());
		//Get User's email address from UserProfile table using id.
		String emailAddress = userProfileObj.getEmailAddress();
		emailService.SendEmail(emailAddress, "Ride created Successfully");
		ride.getSelectedRoute().setId(newRouteId);
		dao.saveNewRide(ride);
		if(newPoolRequest.getCarType() == "Uber"){
			
		}
		
	}

}
