package com.hhh.kiznic.dataclass;

public class WeatherInfo {
	
	String searchDate;
	String dayState;
	String hour;
	String weatherDesc;
	String temperature;
	String rainProb;
	String windSpeed;
	
	PollutionInfo pollutionInfo;
	
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	
	public void setDayState(String dayState){
		this.dayState = dayState;
	}
	
	public void setTime(String hour) {
		this.hour = hour;
	}
	
	public void setWeatherDesc(String weatherDesc) {
		this.weatherDesc = weatherDesc;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public void setRainProb(String rainProb) {
		this.rainProb = rainProb;
	}
	
	public void setWindSpeed(String windSpeed){
		this.windSpeed = windSpeed;
	}
	
	public void setPollutionInfo(PollutionInfo pollutionInfo) {
		this.pollutionInfo = pollutionInfo;
	}
	
	public String getSearchDate() {
		return searchDate;
	}
	
	
	public String getTime() {
		return hour;
	}
	
	public String getWeatherDesc() {
		return weatherDesc;
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	public String getRainProb() {
		return rainProb;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}
	
	public PollutionInfo getPollutionInfo() {
		return pollutionInfo;
	}
	
	public String getDayState() {
		return dayState;
	}
}
