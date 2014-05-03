package com.hhh.kiznic.connection;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhh.kiznic.dataclass.WeatherInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

public class GetWeatherAsync extends AsyncTask<String, Integer, String> {
	
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
	String[] addressArray;
	LocationHelper location;
	
	public GetWeatherAsync(Context mContext, TextView weather_mylocation, TextView weather_today_timedesc,  
			TextView weather_today_temp, TextView weather_today_rainprob, TextView weather_today_windspeed,
			TextView weather_today_feeltemp, TextView weather_next_timedesc, TextView weather_next_temp,
			ImageView weather_image,ImageView weather_next_image ) 
	{
		this.mContext = mContext;
		//this.location = location;
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
	}
	
	
	protected void onPreExecute() {
		super.onPreExecute();
		LocationHelper location = new LocationHelper(mContext);
		location.run();
		addressArray = location.getAddress();
		this.location = location;
	}
		
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		String url = null;
		try {
			url = util.getWeatherURL(addressArray[0], addressArray[1], addressArray[2]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		String xmlResult = util.getXMLHttp(url);
		//Log.d("Weather", xmlResult);
		return xmlResult;
	}
	
	protected void onPostExecute(String result) {
        //tv4.setText(result);
		ArrayList<WeatherInfo> weatherInfo = null;
		Util util = new Util();
		
		try {
			weatherInfo = util.parseWeatherXML(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weather_mylocation.setText(location.getMyLocation());
		weather_today_timedesc.setText(weatherInfo.get(0).getDayState() + " " + weatherInfo.get(0).getTime() + ", " + weatherInfo.get(0).getWeatherDesc());
		weather_today_temp.setText(weatherInfo.get(0).getTemperature()+"℃");
		weather_today_rainprob.setText(" " + weatherInfo.get(0).getRainProb() + " %");
		weather_today_windspeed.setText(" " + weatherInfo.get(0).getWindSpeed() + " m/s");
		weather_today_feeltemp.setText("체감 온도 " + util.convertFeelTemp(weatherInfo.get(0).getTemperature(), weatherInfo.get(0).getWindSpeed())+"℃");
		weather_next_timedesc.setText(weatherInfo.get(1).getDayState() + " " + weatherInfo.get(1).getTime() + ", " + weatherInfo.get(1).getWeatherDesc());
		weather_next_temp.setText(weatherInfo.get(1).getTemperature()+"℃");
		weather_image.setImageBitmap(util.getWeatherImage(mContext, weatherInfo.get(0).getWeatherDesc()));
		weather_next_image.setImageBitmap(util.getWeatherImage(mContext, weatherInfo.get(1).getWeatherDesc()));
					
	}


}
