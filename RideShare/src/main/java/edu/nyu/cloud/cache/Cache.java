/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.List;

import edu.nyu.cloud.beans.Route;

/**
 * This interface represents the cache of the application objects. Any
 * implementation of this class should be a singleton object and should be
 * thread safe.
 * 
 * @author rahulkhanna Date:24-Apr-2016
 */
public interface Cache {

	/**
	 * This function is used to get the <code>List</code> of <code>Route
	 * <code> between source and destination.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public List<Route> getRoutes(String source, String destination);

	/**
	 * This function is used to add newly discovered routes between a source and
	 * destination to the cache.
	 * 
	 * @param source
	 * @param destination
	 * @param possibleRoutes
	 */
	public void addRoutesToCache(String source, String destination, List<Route> possibleRoutes);

}
