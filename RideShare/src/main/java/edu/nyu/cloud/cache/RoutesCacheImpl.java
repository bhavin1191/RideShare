/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.RedisTemplate;

import edu.nyu.cloud.beans.Route;

/**
 * This class is the implementation of RouteCache.
 * 
 * @author rahulkhanna Date:25-Apr-2016
 */
public class RoutesCacheImpl extends CacheImpl<Map<String, List<Route>>> implements RouteCache {

	/**
	 * Constructor
	 * 
	 * @param cacheTemplate
	 */
	public RoutesCacheImpl(RedisTemplate<String, Map<String, List<Route>>> cacheTemplate) {
		super(cacheTemplate);
	}

	@Override
	public List<Route> getRoutesForGivenSourceAndDestination(String source, String destination) {
		String keyForSourceAndDestination = createKey(source, destination);
		Map<String, List<Route>> cache = (Map<String, List<Route>>) getCacheByName(CacheName.ROUTE_CACHE);
		if (cache == null) {
			cache = new ConcurrentHashMap<String, List<Route>>();
			addCacheForGivenName(CacheName.ROUTE_CACHE, cache);
			return null;
		}
		return cache.get(keyForSourceAndDestination);
	}

	@Override
	public void addRoutesToCache(String source, String destination, List<Route> possibleRoutes) {
		String keyForSourceAndDestination = createKey(source, destination);
		Map<String, List<Route>> routeCache = getCacheByName(CacheName.ROUTE_CACHE);
		routeCache.put(keyForSourceAndDestination, possibleRoutes);
		save(CacheName.ROUTE_CACHE, routeCache);
	}

}
