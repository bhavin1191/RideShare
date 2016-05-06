package edu.nyu.cloud.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.RedisTemplate;

import com.google.maps.model.LatLng;

import edu.nyu.cloud.beans.NewRide;

/**
 * This class is the implementation for RideCache. It should be a singleton class.
 * 
 * @author rahulkhanna
 * Date:25-Apr-2016
 */
public class RideCacheImpl extends CacheImpl<Map<String,List<NewRide>>> implements RideCache {

	/**
	 * Constructor
	 * 
	 * @param cacheTemplate
	 */
	public RideCacheImpl(RedisTemplate<String, Map<String, List<NewRide>>> cacheTemplate) {
		super(cacheTemplate);
	}

	@Override
	public List<NewRide> getActiveRidesFromGivenSourceAndDestination(String source, String destination) {
		Map<String, List<NewRide>> activeRideCache = getCacheByName(CacheName.ACTIVE_RIDE_CACHE);
		String key = createKey(source,destination);
		if(activeRideCache != null){
			return activeRideCache.get(key);
		}else{
			activeRideCache = new ConcurrentHashMap<>();
			addCacheForGivenName(CacheName.ACTIVE_RIDE_CACHE, activeRideCache);
		}
		return null;
	}

	@Override
	public List<NewRide> getActiveRidesNearToGivenCoordinates(LatLng latLng) {
		Map<String, List<NewRide>> activeRideCache = getCacheByName(CacheName.ACTIVE_RIDE_CACHE);
		
		
		return null;
	}

	@Override
	public NewRide getRideByGivenRideId(long rideId) {
		return null;
	}
	
	@Override
	public void addNewActiceRide(NewRide ride){
		Map<String, List<NewRide>> activeRideCache = getCacheByName(CacheName.ACTIVE_RIDE_CACHE);
		String key = createKey(ride.getSource(),ride.getDestination());
		if(activeRideCache.containsKey(key)){
			List<NewRide> activeRide = activeRideCache.get(key);
			activeRide.add(ride);
		}else{
			List<NewRide> rides = new ArrayList<>();
			rides.add(ride);
			activeRideCache.put(key, rides);
			save(CacheName.ACTIVE_RIDE_CACHE, activeRideCache);
		}
	}

}
