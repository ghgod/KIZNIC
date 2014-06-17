package com.hhh.kiznicsw.dataclass;

public class WeatherInfo {
	
	String searchDate;
	String dayState;
	String hour;
	String weatherDesc;
	String temperature;
	String feelTemp;
	String rainProb;
	String windSpeed;
	String humidity;
	
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
	
	public void setFeelTemp(String feelTemp) {
		this.feelTemp = feelTemp;
	}
	
	public void setRainProb(String rainProb) {
		this.rainProb = rainProb;
	}
	
	public void setWindSpeed(String windSpeed){
		this.windSpeed = windSpeed;
	}
	
	public void setHumidity(String humidity) {
		this.humidity = humidity;
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
	
	public String getFeelTemp() {
		return feelTemp;
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
	
	public String getHumidity() {
		return humidity;
	}
}
