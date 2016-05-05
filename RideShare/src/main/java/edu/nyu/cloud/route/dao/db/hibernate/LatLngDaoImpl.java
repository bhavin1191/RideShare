/**
 * 
 */
package edu.nyu.cloud.route.dao.db.hibernate;

import org.hibernate.SessionFactory;

import edu.nyu.cloud.beans.SerializableLatLng;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.dao.db.hibernate.ModifiableCommonDBDao;
import edu.nyu.cloud.route.dao.db.LatLngDao;

/**
 * @author rahulkhanna Date:04-May-2016
 */
public class LatLngDaoImpl extends ModifiableCommonDBDao<SerializableLatLng, SerializableLatLng> implements LatLngDao {

	public LatLngDaoImpl(SessionFactory sessionFactory, String entityName, IDGenerator idGenerator) {
		super(sessionFactory, entityName, idGenerator);
	}

}
