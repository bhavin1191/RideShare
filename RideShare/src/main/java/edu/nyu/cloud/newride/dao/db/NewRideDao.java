package edu.nyu.cloud.newride.dao.db;

import edu.nyu.cloud.beans.NewRide;
import edu.nyu.cloud.dao.db.BaseDatabaseDao;

public interface NewRideDao extends BaseDatabaseDao<NewRide, NewRide> {

	void saveNewRide(NewRide ride);
	
}
