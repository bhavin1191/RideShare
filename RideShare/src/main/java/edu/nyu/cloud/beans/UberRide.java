package edu.nyu.cloud.beans;

import java.io.Serializable;

public class UberRide implements Serializable
{
	private static final long serialVersionUID = -4826835819368846051L;
	
	private long id;
	private final String productdisplayname;
	private final String productid;
	private final String rideid;
	private final int capacity;
	private final String driverName;
	private final String drivercontact;
	private final Integer eta;
	private final String vehiclepicture;
		
	/**
	 * @param productdisplayname
	 * @param productid
	 * @param rideid
	 * @param capacity
	 * @param driverName
	 * @param drivercontact
	 * @param eta
	 * @param vehiclepicture
	 */
	public UberRide(String productdisplayname, String productid, String rideid, int capacity, String driverName,
			String drivercontact, Integer eta, String vehiclepicture) {
		this.productdisplayname = productdisplayname;
		this.productid = productid;
		this.rideid = rideid;
		this.capacity = capacity;
		this.driverName = driverName;
		this.drivercontact = drivercontact;
		this.eta = eta;
		this.vehiclepicture = vehiclepicture;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the productdisplayname
	 */
	public String getProductdisplayname() {
		return productdisplayname;
	}
	/**
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}
	/**
	 * @return the rideid
	 */
	public String getRideid() {
		return rideid;
	}
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * @return the drivercontact
	 */
	public String getDrivercontact() {
		return drivercontact;
	}
	/**
	 * @return the eta
	 */
	public Integer getEta() {
		return eta;
	}
	/**
	 * @return the vehiclepicture
	 */
	public String getVehiclepicture() {
		return vehiclepicture;
	}
	
	

}
