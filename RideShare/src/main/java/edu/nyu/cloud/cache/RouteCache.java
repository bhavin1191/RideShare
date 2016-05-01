/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.List;

import edu.nyu.cloud.beans.Route;

/**
 * This cache is specific to Routes.
 * 
 * @author rahulkhanna
 * Date:25-Apr-2016
 */
public interface RouteCache {

	/**
	 * This function is used to get the <code>List</code> of <code>Route
	 * <code> between source and destination.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public List<Route> getRoutesForGivenSourceAndDestination(String source, String destination);

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
