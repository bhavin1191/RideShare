/**
 * 
 */
package edu.nyu.cloud.newride.dao.db.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.beans.RideAvailableForSharing;
import edu.nyu.cloud.beans.SerializableLatLng;
import edu.nyu.cloud.cache.UserCache;
import edu.nyu.cloud.dao.db.IDGenerator;
import edu.nyu.cloud.dao.db.hibernate.ModifiableCommonDBDao;
import edu.nyu.cloud.newride.dao.db.NewRideDao;

/**
 * @author rahulkhanna Date:06-Apr-2016
 */
public class NewRideDaoImpl extends ModifiableCommonDBDao<NewRide, NewRide> implements NewRideDao {

	private final UserCache userCache;
	
	public NewRideDaoImpl(SessionFactory sessionFactory, String entityName, IDGenerator idGenerator, UserCache userCache) {
		super(sessionFactory, entityName, idGenerator);
		this.userCache = userCache;
	}

	@Override
	public void saveNewRide(NewRide ride) {
		ride.setId(getNextKey());
		System.out.println(ride.getSelectedRoute().getId());
		savePersistent(ride);
	}

	@Override
	public List<RideAvailableForSharing> searchRideOnSource(SerializableLatLng source, SerializableLatLng destination, int numberOfPassengers) {
		String selectQuery = "select r.id id, r.user_name requester, r.source source, r.destination destination, r.timeoftrip timeOfTrip, r.seatsavailable seatsAvailable, r.r_id "
				+ " from ride r"
				+ " where r.r_id IN (select src.r_id from (Select distinct ride.r_id From rideshare.ride join rideshare.latlng "
				+ " where ride.r_id = latlng.r_id and acos(sin(radians(:destlat))*sin(radians(latlng.lat)) + cos(radians(:destlat))*cos(radians(latlng.lat))*cos(radians(latlng.lng) - radians(:destlng))) * 6371 < 3.21869) as dest INNER JOIN (Select distinct ride.r_id From rideshare.ride join rideshare.latlng Where ride.r_id = latlng.r_id and acos(sin(radians(:lat))*sin(radians(latlng.lat)) + cos(radians(:lat))*cos(radians(latlng.lat))*cos(radians(latlng.lng) - radians(:lng))) * 6371 < 3.21869) as src where src.r_id = dest.r_id)"
				+ " and r.seatsAvailable > :numberOfPassengers ";
		Query query = getSession().createSQLQuery(selectQuery);
		query.setParameter("lat", source.getLat());
		query.setParameter("lng", source.getLng());
		query.setParameter("destlat", destination.getLat());
		query.setParameter("destlng", destination.getLng());
		query.setParameter("numberOfPassengers", numberOfPassengers);
		List list = query.list();
		List<RideAvailableForSharing> rides = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			if (object != null) {
				RideAvailableForSharing ride = new RideAvailableForSharing((String) object[1], (int)object[5],
						String.valueOf(((BigInteger)object[0]).intValue()), (String) object[2], (String) object[3],0);
				rides.add(ride);
				ride.setProfile(userCache.getUserByUserName(ride.getCreator()));
			}
		}
		return rides;
	}

	@Override
	public void updateRide(RideAvailableForSharing acceptedRide) {
		String sql = "update table ride r set r.seatsavailable = :availableSeats where r.id = :rideId ";
		Query query = getSession().createSQLQuery(sql);
		query.setInteger("availableSeats", acceptedRide.getSeatsAvailable()-acceptedRide.getRequiredSeats());
		query.executeUpdate();
	}

}
