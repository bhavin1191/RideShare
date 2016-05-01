/**
 * 
 */
package edu.nyu.cloud.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * This class is the implementation of Cache. Every other specific cache
 * implementation should be extended from this class.
 * 
 * @param V
 *            denotes the type of Cached object.
 * @author rahulkhanna Date:24-Apr-2016
 */
public abstract class CacheImpl<V> implements Cache<V> {

	private final RedisTemplate<String, V> cacheTemplate;
	
	/**
	 * @param cacheTemplate
	 */
	protected CacheImpl(RedisTemplate<String,V> cacheTemplate) {
		this.cacheTemplate = cacheTemplate;
	}

	/**
	 * This function is used to get the object from the cache.
	 * 
	 * @return the cached object corresponding to given <code>cacheName</code>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V getCacheByName(CacheName cacheName) {
		return (V) cacheTemplate.opsForHash().get(cacheName.name(), cacheName.name().hashCode());
	}

	public void addCacheForGivenName(CacheName cacheName, V o){
		cacheTemplate.opsForHash().put(cacheName.name(), cacheName.name().hashCode(), o);
	}
	
	public void save(CacheName cacheName, V o){
		addCacheForGivenName(cacheName, o);
	}
	
	/**
	 * This function is used to create key out of given values separated by "_".
	 * 
	 * @param values
	 * @return
	 */
	protected String createKey(String... values) {
		String key = "";
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length - 1; i++) {
				String val = values[i];
				key += val + "_";
			}
			key += values[values.length - 1];
		} else {
			throw new IllegalArgumentException("Key can not be created from empty values.");
		}
		return key;
	}

}
