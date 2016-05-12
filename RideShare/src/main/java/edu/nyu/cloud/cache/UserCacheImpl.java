/**
 * 
 */
package edu.nyu.cloud.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import edu.nyu.cloud.beans.UserProfile;

/**
 * @author rahulkhanna
 * Date:12-May-2016
 */
public class UserCacheImpl extends CacheImpl<Map<String,UserProfile>> implements UserCache {

	protected UserCacheImpl(RedisTemplate<String, Map<String, UserProfile>> cacheTemplate) {
		super(cacheTemplate);
	}

	@Override
	public UserProfile getUserByUserName(String userId) {
		Map<String, UserProfile> userCache = (Map<String, UserProfile>)getCacheByName(CacheName.USER_CACHE);
		return userCache.get(userId);
	}

	@Override
	public void addUserIdByUserName(UserProfile profile) {
		Map<String, UserProfile> userCache = (Map<String, UserProfile>)getCacheByName(CacheName.USER_CACHE);
		if(userCache == null){
			userCache = new HashMap<String, UserProfile>();
		}
		userCache.put(profile.getUserName(), profile);
		save(CacheName.USER_CACHE, userCache);
	}

}
