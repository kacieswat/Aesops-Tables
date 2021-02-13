package com.techelevator.model;

public class Location {
	
	
	String cityName;	
	String stateCode;
	Integer zipCode;
	Integer cityID;

	public Location(String cityName, String stateCode, Integer zipCode, Integer cityID) {
		this.cityName = cityName;
		this.stateCode = stateCode;
		this.zipCode = zipCode;
		this.cityID=cityID;
	}
	
	

	public Integer getCityID() {
		return cityID;
	}



	public void setCityID(Integer cityID) {
		this.cityID = cityID;
	}



	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	
	

}
