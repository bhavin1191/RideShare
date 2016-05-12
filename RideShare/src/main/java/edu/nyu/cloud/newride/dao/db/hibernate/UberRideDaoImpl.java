/**
 * 
 */
package edu.nyu.cloud.newride.dao.db.hibernate;
import org.hibernate.SessionFactory;

import edu.nyu.cloud.beans.UberRide;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.dao.db.hibernate.ModifiableCommonDBDao;
import edu.nyu.cloud.newride.dao.db.UberRideDao;


public class UberRideDaoImpl extends ModifiableCommonDBDao<UberRide, UberRide> implements UberRideDao {

	public UberRideDaoImpl(SessionFactory sessionFactory, String entityName, IDGenerator idGenerator) {
		super(sessionFactory, entityName, idGenerator);
	}

	@Override
	public void saveUberRide(UberRide uberRide) {		
		uberRide.setId(getNextKey());
		System.out.println(uberRide.getId());
		savePersistent(uberRide);
	}

}
