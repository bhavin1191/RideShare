/**
 * 
 */
package edu.nyu.cloud.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.nyu.cloud.newride.NewRideCreator;
import edu.nyu.cloud.service.beans.IncomingPoolRequest;

/**
 * @author rahulkhanna
 * Date:02-May-2016
 */
public class NewRideCreationJob implements Job{

	private final NewRideCreator rideCreator;
	
	public NewRideCreationJob(NewRideCreator rideCreator) {
		this.rideCreator = rideCreator;
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("New Ride created");
		getRideCreator().createNewRideForPool((IncomingPoolRequest)context.get(JobParams.INCOMING_POOL_REQUEST.name()));
	}

	/**
	 * @return the rideCreator
	 */
	public NewRideCreator getRideCreator() {
		return rideCreator;
	}

}
