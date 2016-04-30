/**
 * 
 */
package edu.nyu.cloud.newride;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.newride.dao.db.NewRideDao;
import edu.nyu.cloud.service.beans.IncomingPoolRequest;

/**
 * This class is used to create a new ride which will be shared by other users.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
public class NewRideCreator {

	private final NewRideDao dao;

	/**
	 * Constructor
	 * 
	 * @param dao
	 */
	public NewRideCreator(NewRideDao dao) {
		super();
		this.dao = dao;
	}

	public void createNewRideForPool(IncomingPoolRequest newPoolRequest) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = format.parse(newPoolRequest.getDate());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		NewRide ride = new NewRide(newPoolRequest.getUserId(), newPoolRequest.getSource(),
				newPoolRequest.getDestination(), date, newPoolRequest.getSelectRoute());
		dao.saveNewRide(ride);
	}

}
