/**
 * 
 */
package edu.nyu.cloud.route.dao.db.hibernate;

import org.hibernate.SessionFactory;

import edu.nyu.cloud.beans.Route;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.dao.db.hibernate.ModifiableCommonDBDao;
import edu.nyu.cloud.route.dao.db.RouteDao;

/**
 * @author rahulkhanna
 * Date:04-May-2016
 */
public class RouteDaoImpl extends ModifiableCommonDBDao<Route, Route> implements RouteDao {
	
	
	public RouteDaoImpl(SessionFactory sessionFactory, String entityName, IDGenerator idGenerator) {
		super(sessionFactory, entityName, idGenerator);
	}

	@Override
	public Route getRoutesById(long id) {
		return null;
	}

}
