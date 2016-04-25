/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.cloud.beans.Route;

/**
 * This class is the implementation of Cache.
 * 
 * @author rahulkhanna Date:24-Apr-2016
 */
public class CacheImpl implements Cache {

	private final Map<String, List<Route>> possibleRoutesBetweenTwoPoints;

	/**
	 * @param possibleRoutesBetweenTwoPoints
	 */
	public CacheImpl() {
		this.possibleRoutesBetweenTwoPoints = new ConcurrentHashMap<>();
	}

	@Override
	public List<Route> getRoutes(String source, String destination) {
		String key = createKeyFromSourceAndDestination(source, destination);
		return possibleRoutesBetweenTwoPoints.get(key);
	}

	/**
	 * @param source
	 * @param destination
	 * @return
	 */
	private String createKeyFromSourceAndDestination(String source, String destination) {
		return source + "_" + destination;
	}

	@Override
	public void addRoutesToCache(String source, String destination, List<Route> possibleRoutes) {
		String key = createKeyFromSourceAndDestination(source, destination);
		possibleRoutesBetweenTwoPoints.put(key, possibleRoutes);
	}

}
