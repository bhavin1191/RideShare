/**
 * 
 */
package edu.nyu.cloud.newride.dao.db.hibernate;

import java.util.List;

import org.hibernate.Query;
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
		System.out.println(ride.getSelectedRoute().getId());
		savePersistent(ride);
	}
	
	@Override
	public List<NewRide> searchRideOnSource() 
	{
   	 String selectQuery = "select * from ride where r_id IN (select src.r_id from (Select distinct ride.r_id From rideshare.ride join rideshare.latlng where ride.r_id = latlng.r_id and acos(sin(radians(:destlat))*sin(radians(latlng.lat)) + cos(radians(:destlat))*cos(radians(latlng.lat))*cos(radians(latlng.lng) - radians(:destlng))) * 6371 < 3.21869) as dest INNER JOIN (Select distinct ride.r_id From rideshare.ride join rideshare.latlng Where ride.r_id = latlng.r_id and acos(sin(radians(:lat))*sin(radians(latlng.lat)) + cos(radians(:lat))*cos(radians(latlng.lat))*cos(radians(latlng.lng) - radians(:lng))) * 6371 < 3.21869) as src where src.r_id = dest.r_id)";
   	 Query query = getSession().createSQLQuery(selectQuery);
   	 query.setParameter("lat", 41.76008);
   	 query.setParameter("lng", -75.68825000000001);
   	 query.setParameter("destlat", 40.810120000000005);
   	 query.setParameter("destlng", -74.29287000000001);
   	 List<NewRide> list = query.list();
   	 return list;
	}

}
