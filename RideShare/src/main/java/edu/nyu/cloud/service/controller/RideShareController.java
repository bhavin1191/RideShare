/**
 * 
 */
package edu.nyu.cloud.service.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.nyu.cloud.beans.Route;
import edu.nyu.cloud.beans.UserProfile;
import edu.nyu.cloud.beans.factory.BeanFactory;
import edu.nyu.cloud.cache.Cache;
import edu.nyu.cloud.service.beans.IncomingPoolRequest;
import edu.nyu.cloud.service.beans.NewRideSharingRequest;
import edu.nyu.cloud.user.dao.db.UserDao;

/**
 * This class represents the controller for the app.
 * 
 * @author rahulkhanna Date:04-Apr-2016
 */

@RestController
public class RideShareController {

	private static final Logger LOG = (Logger) Logger.getLogger(RideShareController.class.getName());
	
	private final UserDao userDao;
	private final BeanFactory beanFactory;
	private final Cache applicationCache;

	public RideShareController(){
		this(null,null,null);
	}
	
	/**
	 * Constructor
	 * @param applicationCache 
	 * 
	 * @param rideCreator
	 */
	public RideShareController(BeanFactory beanFactory, UserDao userDao, Cache applicationCache) {
		super();
		this.applicationCache = applicationCache;
		this.beanFactory = beanFactory;
		this.userDao = userDao;
	}

	/**
	 * This method is called when the user has selected the favorable route for
	 * the new pool request.
	 * 
	 * @param newPoolRequest
	 */
	@RequestMapping(value = "/newpoolrequest", method = { RequestMethod.POST, RequestMethod.GET })
	public void openNewRideSharingRequest(@RequestBody IncomingPoolRequest newPoolRequest) {
		beanFactory.getRideCreator().createNewRideForPool(newPoolRequest);
	}

	/**
	 * This is called when a new user registration request comes in.
	 * 
	 * @param newUserData
	 */
	@RequestMapping(value = "/newuser", method = { RequestMethod.POST, RequestMethod.GET })
	public void registerNewUser(@RequestBody(required = false) UserProfile newUserData) {
		LOG.info("incoming new userId = " + newUserData.getFirstName());
		System.out.println("incoming new userId = " + newUserData.getFirstName());
		//userDao.persistUserProfie(newUserData);
	}

	/**
	 * This function is called when a user wanted to open a new pool request.
	 * 
	 * @param newPoolRequestToSearchRoutes
	 * @return List of <code>Routes</code> which are will be selected by the
	 *         user defining the route which will be followed by the cab driver.
	 */
	@RequestMapping(value = "/searchRoutesForNewCarPoolRequest", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<List<Route>> fetchRoutesForNewRideRegistration(
			@RequestBody IncomingPoolRequest newPoolRequestToSearchRoutes) {
		List<Route> routes = applicationCache.getRoutes(newPoolRequestToSearchRoutes.getSource(), newPoolRequestToSearchRoutes.getDestination());
		if(routes == null){
			routes = beanFactory.getMapService().fetchPossibleRoutes(newPoolRequestToSearchRoutes.getSource(),
					newPoolRequestToSearchRoutes.getDestination());
			applicationCache.addRoutesToCache(newPoolRequestToSearchRoutes.getSource(), newPoolRequestToSearchRoutes.getDestination(), routes);
		}
		return new ResponseEntity<List<Route>>(routes, HttpStatus.FOUND);
	}

	/**
	 * This function is called when a user wants to search for already opened
	 * car pool request between source and destination.
	 * 
	 * @param newRideRequest
	 */
	@RequestMapping(value = "/searchCarForPooling", method = { RequestMethod.POST, RequestMethod.GET })
	public void searchNewRideToShare(@RequestBody NewRideSharingRequest newRideRequest) {
		// TODO:
	}

}
