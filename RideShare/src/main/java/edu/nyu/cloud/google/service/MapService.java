/**
 * 
 */
package edu.nyu.cloud.google.service;

import java.util.Set;

import edu.nyu.cloud.beans.Route;

/**
 * This service is used to take help from google based on different request made by the user.
 * 
 * @author rahulkhanna
 * Date:05-Apr-2016
 */
public interface MapService {
	
	public Set<Route> fetchPossibleRoutes(String source, String destination);

}
