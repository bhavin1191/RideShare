/**
 * 
 */
package edu.nyu.cloud.cache;

/**
 * This interface represents the cache of the application objects. Any
 * implementation of this class should be a singleton object and should be
 * thread safe.
 * 
 * @author rahulkhanna Date:24-Apr-2016
 */
public interface Cache<V> {

	/**
	 * This function is used to get the cache for the given <code>CacheName</code>.
	 * 
	 * @param cacheName
	 * @return
	 */
	public V getCacheByName(CacheName cacheName);
	
	public void addCacheForGivenName(CacheName cachename, V o);

}
