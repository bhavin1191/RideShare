package edu.nyu.cloud.newride.dao.db;

import java.util.List;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.beans.RideAvailableForSharing;
import edu.nyu.cloud.beans.SerializableLatLng;
import edu.nyu.cloud.dao.db.BaseDatabaseDao;

public interface NewRideDao extends BaseDatabaseDao<NewRide, NewRide> {

	void saveNewRide(NewRide ride);
	List<RideAvailableForSharing> searchRideOnSource(SerializableLatLng source, SerializableLatLng destination);
	
}
