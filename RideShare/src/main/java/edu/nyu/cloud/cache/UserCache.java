/**
 * 
 */
package edu.nyu.cloud.cache;

import edu.nyu.cloud.beans.UserProfile;

/**
 * @author rahulkhanna
 * Date:12-May-2016
 */
public interface UserCache {

	UserProfile getUserByUserName(String userId);
	
	void addUserIdByUserName(UserProfile profile);
	
}
