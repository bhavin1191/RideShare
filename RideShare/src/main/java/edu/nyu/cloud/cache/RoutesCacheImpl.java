/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.RedisTemplate;

import edu.nyu.cloud.beans.Route;

/**
 * This class is the implementation of RouteCache.
 * 
 * @author rahulkhanna Date:25-Apr-2016
 */
public class RoutesCacheImpl extends CacheImpl<Map<String, Set<Route>>> implements RouteCache {

	/**
	 * Constructor
	 * 
	 * @param cacheTemplate
	 */
	public RoutesCacheImpl(RedisTemplate<String, Map<String, Set<Route>>> cacheTemplate) {
		super(cacheTemplate);
	}

	@Override
	public Set<Route> getRoutesForGivenSourceAndDestination(String source, String destination) {
		String keyForSourceAndDestination = createKey(source, destination);
		Map<String, Set<Route>> cache = (Map<String, Set<Route>>) getCacheByName(CacheName.ROUTE_CACHE);
		if (cache == null) {
			cache = new ConcurrentHashMap<String, Set<Route>>();
			addCacheForGivenName(CacheName.ROUTE_CACHE, cache);
			return null;
		}
		return cache.get(keyForSourceAndDestination);
	}

	@Override
	public void addRoutesToCache(String source, String destination, Set<Route> possibleRoutes) {
		String keyForSourceAndDestination = createKey(source, destination);
		Map<String, Set<Route>> routeCache = getCacheByName(CacheName.ROUTE_CACHE);
		routeCache.put(keyForSourceAndDestination, possibleRoutes);
		save(CacheName.ROUTE_CACHE, routeCache);
	}

}
