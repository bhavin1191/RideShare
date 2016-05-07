/**
 * 
 */
package edu.nyu.cloud.route.dao.db;

import edu.nyu.cloud.beans.Route;
import edu.nyu.cloud.dao.db.BaseDatabaseDao;

/**
 * @author rahulkhanna
 * Date:04-May-2016
 */
public interface RouteDao extends BaseDatabaseDao<Route, Route>{

	public Route getRoutesById(long id);
	
}
