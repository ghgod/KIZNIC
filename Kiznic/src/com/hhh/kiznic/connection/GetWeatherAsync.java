package com.hhh.kiznic.connection;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhh.kiznic.R;
import com.hhh.kiznic.databasemanager.Databasehelper;
import com.hhh.kiznic.dataclass.NextPollutionInfo;
import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

public class GetWeatherAsync extends AsyncTask<String, Integer, String[]> {
	
	Context mContext;
	
	TextView weather_mylocation;
	TextView weather_today_timedesc;
	TextView weather_today_temp;
	TextView weather_today_rainprob;
	TextView weather_today_windspeed;
	TextView weather_today_feeltemp;
	TextView weather_next_timedesc;
	TextView weather_next_temp;
	ImageView weather_image;
	ImageView weather_next_image;
	
	TextView weather_today_pm10value;
	ImageView weather_finedust;
	
	TextView weather_next_pm10Info;
	ImageView weather_next_dustmeter;
	
	Databasehelper dbHelper;
	String mySiDo;
	String nextPollutionState;
	
	String[] addressArray;
	LocationHelper location;
	
	int flag;
	
	public GetWeatherAsync(Context mContext, int flag, Databasehelper dbHelper, TextView weather_mylocation, TextView weather_today_timedesc,  
			TextView weather_today_temp, TextView weather_today_rainprob, TextView weather_today_windspeed,
			TextView weather_today_feeltemp, TextView weather_next_timedesc, TextView weather_next_temp,
			ImageView weather_image,ImageView weather_next_image, ImageView weather_finedust, TextView weather_today_pm10value,
			TextView weather_next_pm10Info, ImageView weather_next_dustmeter) 
	{
		this.mContext = mContext;
		this.flag = flag;
		this.dbHelper = dbHelper;
		this.weather_mylocation = weather_mylocation;
		this.weather_today_timedesc = weather_today_timedesc;
		this.weather_today_temp = weather_today_temp;
		this.weather_today_rainprob = weather_today_rainprob;
		this.weather_today_windspeed = weather_today_windspeed;
		this.weather_today_feeltemp = weather_today_feeltemp;
		this.weather_next_timedesc = weather_next_timedesc;
		this.weather_next_temp = weather_next_temp;
		this.weather_image = weather_image;
		this.weather_next_image = weather_next_image;
		
		this.weather_finedust = weather_finedust;
		this.weather_today_pm10value = weather_today_pm10value;
		
		this.weather_next_pm10Info = weather_next_pm10Info;
		this.weather_next_dustmeter = weather_next_dustmeter;
	}
	
	
	protected void onPreExecute() {
		super.onPreExecute();
		LocationHelper location = new LocationHelper(mContext);
		this.location = location;
		location.run();
		addressArray = location.getAddress();
		mySiDo = location.getMySiDo();
	}
		
	@Override
	protected String[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		String[] result = new String[3];
		String url = null;
		try {
			url = util.getWeatherURL(mContext, addressArray[0], addressArray[1]);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String weatherXML = util.getXMLHttp(url);
		String pollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName="+mySiDo+"&pageNo=1&numOfRows=10&ServiceKey="+mContext.getResources().getString(R.string.openApiKey));
		String nextPollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?searchDate="+util.yesterdayDate()+"&ServiceKey="+mContext.getResources().getString(R.string.openApiKey));
		
		Log.d("util.yesterdayDate()", util.yesterdayDate());
		
		result[0] = weatherXML;
		result[1] = pollutionXML;
		result[2] = nextPollutionXML;
		//Log.d("Weather", xmlResult);
		return result;
	}
	
	protected void onPostExecute(String[] result) {
        //tv4.setText(result);
		ArrayList<WeatherInfo> weatherInfo = null;
		ArrayList<PollutionInfo> pollutionInfo = null;
		ArrayList<NextPollutionInfo> nextPollutionInfo = null;
		Util util = new Util();
		
		Log.d("result[2]", result[2]);
		try {
			weatherInfo = util.parseWeatherXML(result[0]);
			pollutionInfo = util.parseCurrentPollutionXML(result[1]);
			nextPollutionInfo = util.parseNextPollutionXML(result[2]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//Databasehelper dbHelper = new Databasehelper(mContext);
		//SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		//dbHelper.onCreate(mDB);
		
		switch(util.sidoToArea(mySiDo)){
		case 0 :
			nextPollutionState = nextPollutionInfo.get(0).getSudoInfo();
			break;
		case 1 :
			nextPollutionState = nextPollutionInfo.get(0).getJejuInfo();
			break;
		case 2 :
			nextPollutionState = nextPollutionInfo.get(0).getYoungnamInfo();
			break;
		case 3 :
			nextPollutionState = nextPollutionInfo.get(0).getHonamInfo();
			break;
		case 4 :
			nextPollutionState = nextPollutionInfo.get(0).getGangwonInfo();
			break;
		case 5 :
			nextPollutionState = nextPollutionInfo.get(0).getChungchungInfo();
			break;
		}
		if(flag == 0) {
			dbHelper.createWeather(weatherInfo.get(0), weatherInfo.get(1), pollutionInfo.get(0), nextPollutionState);
		} else {
			dbHelper.updateWeather(weatherInfo.get(0), weatherInfo.get(1), pollutionInfo.get(0), nextPollutionState);
		}
		
		
		WeatherInfo dbTodayWeatherInfo = dbHelper.getTodayWeatherInfo();
		WeatherInfo dbNextWeatherInfo = dbHelper.getNextWeatherInfo();
		PollutionInfo dbTodayPollutionInfo = dbHelper.getPollutionInfo();
		String dbNextPollutionInfo = dbHelper.getNextPollutionInfo();
		
		
		weather_mylocation.setText(location.getMyLocation());
		weather_today_timedesc.setText(dbTodayWeatherInfo.getDayState() + " " + dbTodayWeatherInfo.getTime() + ", " + dbTodayWeatherInfo.getWeatherDesc());
		weather_today_temp.setText(dbTodayWeatherInfo.getTemperature()+"℃");
		weather_today_rainprob.setText(" " + dbTodayWeatherInfo.getRainProb()+ " %");
		weather_today_windspeed.setText(" " + dbTodayWeatherInfo.getWindSpeed() + " m/s");
		weather_today_feeltemp.setText("체감 온도 " + dbTodayWeatherInfo.getFeelTemp()+"℃");
		weather_next_timedesc.setText(dbNextWeatherInfo.getDayState() + " " + dbNextWeatherInfo.getTime() + ", " + dbNextWeatherInfo.getWeatherDesc());
		weather_next_temp.setText(dbNextWeatherInfo.getTemperature()+"℃");
		weather_image.setImageBitmap(util.getWeatherImage(mContext, dbTodayWeatherInfo.getWeatherDesc()));
		weather_next_image.setImageBitmap(util.getWeatherImage(mContext, dbNextWeatherInfo.getWeatherDesc()));
		
		weather_today_pm10value.setText("미세먼지 농도 " + dbTodayPollutionInfo.getPM10Value());
		util.weather_finedust_set(weather_finedust,(int)((1.2 * Integer.parseInt(dbTodayPollutionInfo.getPM10Value()))), false, null);
		
		weather_next_pm10Info.setText("미세먼지  " + dbNextPollutionInfo);
		util.weather_finedust_set(weather_next_dustmeter, 0, true, dbNextPollutionInfo);
		
		/*
		weather_mylocation.setText(location.getMyLocation());
		weather_today_timedesc.setText(weatherInfo.get(0).getDayState() + " " + weatherInfo.get(0).getTime() + ", " + weatherInfo.get(0).getWeatherDesc());
		weather_today_temp.setText(weatherInfo.get(0).getTemperature()+"℃");
		weather_today_rainprob.setText(" " + weatherInfo.get(0).getRainProb() + " %");
		weather_today_windspeed.setText(" " + weatherInfo.get(0).getWindSpeed() + " m/s");
		weather_today_feeltemp.setText("체감 온도 " + weatherInfo.get(0).getFeelTemp()+"℃");
		weather_next_timedesc.setText(weatherInfo.get(1).getDayState() + " " + weatherInfo.get(1).getTime() + ", " + weatherInfo.get(1).getWeatherDesc());
		weather_next_temp.setText(weatherInfo.get(1).getTemperature()+"℃");
		weather_image.setImageBitmap(util.getWeatherImage(mContext, weatherInfo.get(0).getWeatherDesc()));
		weather_next_image.setImageBitmap(util.getWeatherImage(mContext, weatherInfo.get(1).getWeatherDesc()));
		
		weather_today_pm10value.setText("미세먼지 농도 " + pollutionInfo.get(0).getPM10Value());
		util.weather_finedust_set(weather_finedust,(int)((1.2 * Integer.parseInt(pollutionInfo.get(0).getPM10Value()))), false, null);
		
		switch(util.sidoToArea(mySiDo)){
		case 0 :
			if(nextPollutionInfo.get(0).getSudoInfo().equals("")) {
				weather_next_pm10Info.setText("미세먼지  " + "보통");
				util.weather_finedust_set(weather_next_dustmeter, 0, true, "보통");
			} else {
				weather_next_pm10Info.setText("미세먼지  " + nextPollutionInfo.get(0).getSudoInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getSudoInfo());
			}
			break;
		case 1 :
			if(nextPollutionInfo.get(0).getJejuInfo().equals("")) {
				weather_next_pm10Info.setText("미세먼지  " + "보통");
				util.weather_finedust_set(weather_next_dustmeter, 0, true, "보통");
			} else {
				weather_next_pm10Info.setText("미세먼지  " + nextPollutionInfo.get(0).getJejuInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getJejuInfo());
			}
			break;
		case 2 :
			if(nextPollutionInfo.get(0).getYoungnamInfo().equals("")) {
				weather_next_pm10Info.setText("미세먼지 " + "보통");
				util.weather_finedust_set(weather_next_dustmeter, 0, true, "보통");
			} else {
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getYoungnamInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getYoungnamInfo());
			}
			break;
		case 3 :
			if(nextPollutionInfo.get(0).getHonamInfo().equals("")) {
				weather_next_pm10Info.setText("미세먼지 " + "보통");
				util.weather_finedust_set(weather_next_dustmeter, 0, true, "보통");
			} else {
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getHonamInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getHonamInfo());
			}
			break;
		case 4 :
			if(nextPollutionInfo.get(0).getGangwonInfo().equals("")) {
				weather_next_pm10Info.setText("미세먼지 " + "보통");
				util.weather_finedust_set(weather_next_dustmeter, 0, true, "보통");
			} else {
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getGangwonInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getGangwonInfo());
			}
			break;
		case 5 :
			if(nextPollutionInfo.get(0).getChungchungInfo().equals("")) {
				weather_next_pm10Info.setText("미세먼지 " + "보통");
				util.weather_finedust_set(weather_next_dustmeter, 0, true, "보통");
			} else {
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getChungchungInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getChungchungInfo());
			}

			break;
		}
		*/
		
	}


}
