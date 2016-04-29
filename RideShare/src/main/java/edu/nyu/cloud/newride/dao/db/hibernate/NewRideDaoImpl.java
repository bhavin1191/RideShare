/**
 * 
 */
package edu.nyu.cloud.newride.dao.db.hibernate;

import org.hibernate.SessionFactory;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.dao.db.hibernate.ModifiableCommonDBDao;
import edu.nyu.cloud.newride.dao.db.NewRideDao;

/**
 * @author rahulkhanna
 * Date:06-Apr-2016
 */
public class NewRideDaoImpl extends ModifiableCommonDBDao<NewRide, NewRide> implements NewRideDao {

	public NewRideDaoImpl(SessionFactory sessionFactory, String entityName, IDGenerator idGenerator) {
		super(sessionFactory, entityName, idGenerator);
	}

	@Override
	public void saveNewRide(NewRide ride) {
		ride.setId(getNextKey());
		save(ride);
	}

}
