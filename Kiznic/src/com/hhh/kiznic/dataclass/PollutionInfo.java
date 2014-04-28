package com.hhh.kiznic.dataclass;

public class PollutionInfo {
	String pm10Value;
	String o3Value;
	String pm10Grade;
	String o3Grade;
	
	public void setPM10Value(String pm10Value) {
		this.pm10Value = pm10Value;
	}
	
	public void setO3Value(String o3Value) {
		this.o3Value = o3Value;
	}
	
	public void setPM10Grade(String pm10Grade){
		this.pm10Grade = pm10Grade;
	}
	
	public void setO3Grade(String o3Grade) {
		this.o3Grade = o3Grade;
	}
	
	public String getPM10Value() {
		return pm10Value;
	}
	
	public String getO3Value() {
		return o3Value;
	}
	
	public String getPM10Grade() {
		return pm10Grade;
	}
	
	public String getO3Grade() {
		return o3Grade;
	}
}
