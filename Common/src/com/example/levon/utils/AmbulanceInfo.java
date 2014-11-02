package com.example.levon.utils;

public class AmbulanceInfo {
	private String driverName = "";
	private String licensePlate = "";
	private String organization = "";
	private String signedBy = "";
	
	public AmbulanceInfo(String driverName, String licensePlate, String organization, String signedBy) {
		this.driverName = driverName;
		this.licensePlate = licensePlate;
		this.organization = organization;
		this.signedBy = signedBy;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}
	

}
