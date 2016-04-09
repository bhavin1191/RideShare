/**
 * 
 */
package edu.nyu.cloud.beans.factory;

import edu.nyu.cloud.google.service.MapService;
import edu.nyu.cloud.newride.NewRideCreator;

/**
 * This factory is used to by spring framework to instantiate prototype beans
 * for injection in singleton beans.
 * 
 * @author rahulkhanna Date:06-Apr-2016
 */
public abstract class BeanFactory {

	public abstract MapService getMapService();
	
	public abstract NewRideCreator getRideCreator();
	
}
