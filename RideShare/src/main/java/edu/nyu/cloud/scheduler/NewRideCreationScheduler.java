/**
 * 
 */
package edu.nyu.cloud.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import edu.nyu.cloud.service.beans.IncomingPoolRequest;

/**
 * @author rahulkhanna Date:30-Apr-2016
 */
public abstract class NewRideCreationScheduler {

	private final Scheduler scheduler;

	/**
	 * @param scheduler
	 * @throws SchedulerException
	 */
	public NewRideCreationScheduler() throws SchedulerException {
		this.scheduler = StdSchedulerFactory.getDefaultScheduler();
	}

	public void initalize() {
		try {
			getScheduler().start();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the scheduler
	 */
	private Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * This function is used to create trigger for the job to be fired at a given time.
	 * 
	 * @param timeToFireTrigger
	 * @return
	 */
	private Trigger createNewTrigger(Date timeToFireTrigger) {
		TriggerBuilder<Trigger> trigger = TriggerBuilder.newTrigger();
		trigger.startAt(timeToFireTrigger);
		return trigger.build();
	}
	
	public void createJobForNewUberCab(IncomingPoolRequest request) throws SchedulerException, ParseException{
		JobDataMap map = new JobDataMap();
		map.put(JobParams.INCOMING_POOL_REQUEST.name(), request);
		JobDetail job = JobBuilder.newJob(NewRideCreationJob.class).usingJobData(map).build();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Trigger newTrigger = createNewTrigger(format.parse(request.getDate()));
		getScheduler().scheduleJob(job, newTrigger);
	}

}
