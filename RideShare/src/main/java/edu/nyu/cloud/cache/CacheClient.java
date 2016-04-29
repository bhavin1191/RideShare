/**
 * 
 */
package edu.nyu.cloud.cache;

/**
 * This class is responsible to connect to memcached server. This class should
 * be singleton and thread safe.
 * 
 * @author rahulkhanna Date:25-Apr-2016
 */
public class CacheClient {

	private final String cacheURL;
	private final int portNumber;

	/**
	 * @param cacheURL
	 * @param portNumber
	 */
	public CacheClient(String cacheURL, int portNumber) {
		super();
		this.cacheURL = cacheURL;
		this.portNumber = portNumber;
	}

}