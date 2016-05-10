package edu.nyu.cloud.newride.dao.db;

import edu.nyu.cloud.beans.UberRide;

import java.util.List;

import edu.nyu.cloud.dao.db.BaseDatabaseDao;

public interface UberRideDao extends BaseDatabaseDao<UberRide, UberRide> {

	void saveUberRide(UberRide uberRide);
//	List<UberRide> searchUberRideOnSource(String source, String destination);
	
}
