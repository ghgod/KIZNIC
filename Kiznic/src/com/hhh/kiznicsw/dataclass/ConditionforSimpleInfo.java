package com.hhh.kiznicsw.dataclass;


public class ConditionforSimpleInfo {

	WeatherInfo weatherInfo;
	PollutionInfo pollutionInfo;
	
	String ages;
	String inSide;
	String distance;
	String latitude;
	String longitude;
	
	public void setAges(String ages) {
		this.ages = ages;
	}
	
	public void setInside(String inSide){
		this.inSide = inSide;
	}
		
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setWeatherInfo(WeatherInfo weatherInfo) {
		this.weatherInfo = weatherInfo;
	}
	
	public void setPollutionInfo(PollutionInfo pollutionInfo) {
		this.pollutionInfo = pollutionInfo;
	}
	
	public String getAges() {
		return ages;
	}
	
	public String isInside() {
		return inSide;
	}
	
	public String getDistance() {
		return distance;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	
	public WeatherInfo getWeatherInfo() {
		return weatherInfo;
	}
	
	public PollutionInfo getPollutionInfo() {
		return pollutionInfo;
	}
	
	
	
	
}