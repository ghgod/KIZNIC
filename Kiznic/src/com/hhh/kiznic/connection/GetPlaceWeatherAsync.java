package com.hhh.kiznic.connection;

import java.io.IOException;
import java.util.ArrayList;

import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class GetPlaceWeatherAsync extends AsyncTask<String, Integer, String>{
	
	private Context context;
	private String[] placeAddressArray;
	private double latitude;
	private double longitude;
	private ImageView recommend_weatherimage_image;
	private TextView recommend_weathertemperature_text;
	
	public GetPlaceWeatherAsync(Context context, double latitude, double longitude, ImageView recommend_weatherimage_image, TextView recommend_weathertemperature_text){
		this.context = context;
		this.latitude = latitude;
		this.longitude = longitude;
		this.recommend_weatherimage_image = recommend_weatherimage_image;
		this.recommend_weathertemperature_text = recommend_weathertemperature_text;
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		LocationHelper location = new LocationHelper(context);
		location.run();
		placeAddressArray = location.getAddressUsingLatLng(latitude, longitude);		
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		String url = null;
		try {
			url = util.getWeatherURL(context, placeAddressArray[0], placeAddressArray[1]);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String placeWeatherXML = util.getXMLHttp(url);
		
		return placeWeatherXML;
	}

	protected void onPostExecute(String result) {
		
		ArrayList<WeatherInfo> weatherInfo = null;
		Util util = new Util();
		
		try {
			weatherInfo = util.parseWeatherXML(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("weatherInfo를 안줘?", weatherInfo.get(0).getWeatherDesc());
		recommend_weatherimage_image.setImageBitmap(util.getPlaceWeatherImage(context, weatherInfo.get(0).getWeatherDesc()));
		recommend_weathertemperature_text.setText(weatherInfo.get(0).getTemperature()+"℃");
	
	}
}
