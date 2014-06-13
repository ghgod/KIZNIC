package com.hhh.kiznic.card;

import com.hhh.kiznic.R;
import com.hhh.kiznic.connection.GetWeatherAsync;
import com.hhh.kiznic.databasemanager.Databasehelper;
import com.hhh.kiznic.databasemanager.KiznicSharedPreferences;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainWeatherCard extends Card implements View.OnClickListener{

	TextView weather_location_text;
	TextView weather_simpleinfo_text;
	TextView weather_temperature_text;
	TextView weather_windcilltemperature_text;
	TextView weather_finedusttext_text;
	TextView weather_rainfallpercent_text;
	TextView weather_windspeedpercent_text;
	TextView weather_humidity_text;
	TextView weather_nextdaysimpleinfo_text;
	TextView weather_nexttemperature_text;
	ImageView weather_getlocation_imagebutton;
	ImageView weather_weatherimage_image;
	ImageView weather_finedustimage_image;
	ImageView weather_rainfall_image;
	ImageView weather_windspeed_image;
	ImageView weather_nextweatherimage_image;
	public LinearLayout weather_circlemeter_layout;
	private Databasehelper dbHelper;
	
	public MainWeatherCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		init();
		
		if(Util.isOnline(context)) {
			new GetWeatherAsync(context, 0, dbHelper,  weather_location_text, weather_simpleinfo_text, weather_temperature_text, weather_windcilltemperature_text,weather_rainfallpercent_text, weather_windspeedpercent_text, weather_humidity_text, weather_nextdaysimpleinfo_text, weather_nexttemperature_text,  weather_weatherimage_image, weather_nextweatherimage_image, weather_finedustimage_image, weather_finedusttext_text ).execute("");
		} else {
			getWeatherFast();
		}

		setListener();
	}

	public void init(){
		weather_location_text = (TextView)cardView.findViewById(R.id.weather_location_text);
		weather_simpleinfo_text = (TextView)cardView.findViewById(R.id.weather_simpleinfo_text);
		weather_temperature_text = (TextView)cardView.findViewById(R.id.weather_temperature_text);
		weather_windcilltemperature_text = (TextView)cardView.findViewById(R.id.weather_windcilltemperature_text);
		weather_finedusttext_text = (TextView)cardView.findViewById(R.id.weather_finedusttext_text);
		weather_rainfallpercent_text = (TextView)cardView.findViewById(R.id.weather_rainfallpercent_text);
		weather_windspeedpercent_text = (TextView)cardView.findViewById(R.id.weather_windspeedpercent_text);
		weather_humidity_text = (TextView)cardView.findViewById(R.id.weather_humidity_text);
		weather_nextdaysimpleinfo_text = (TextView)cardView.findViewById(R.id.weather_nextdaysimpleinfo_text);
		weather_nexttemperature_text = (TextView)cardView.findViewById(R.id.weather_nexttemperature_text);
		weather_getlocation_imagebutton = (ImageView)cardView.findViewById(R.id.weather_getlocation_imagebutton);
		weather_weatherimage_image = (ImageView)cardView.findViewById(R.id.weather_weatherimage_image);
		weather_finedustimage_image = (ImageView)cardView.findViewById(R.id.weather_finedustimage_image);
		weather_rainfall_image = (ImageView)cardView.findViewById(R.id.weather_rainfall_image);
		weather_windspeed_image = (ImageView)cardView.findViewById(R.id.weather_windspeed_image);
		weather_nextweatherimage_image = (ImageView)cardView.findViewById(R.id.weather_nextweatherimage_image);
		
		Databasehelper dbHelper = new Databasehelper(context);
		this.dbHelper = dbHelper;
		
	}
	
	@Override
	public void setListener(){
		weather_getlocation_imagebutton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.weather_getlocation_imagebutton :
			if(Util.isOnline(context)) {
				new GetWeatherAsync(context, 0, dbHelper,  weather_location_text, weather_simpleinfo_text, weather_temperature_text, weather_windcilltemperature_text,weather_rainfallpercent_text, weather_windspeedpercent_text, weather_humidity_text, weather_nextdaysimpleinfo_text, weather_nexttemperature_text,  weather_weatherimage_image, weather_nextweatherimage_image, weather_finedustimage_image, weather_finedusttext_text ).execute("");
			} else {
				getWeatherFast();
			}
			
			break;
		}
	}
	
	public void getWeatherFast() {
		
		KiznicSharedPreferences pref = new KiznicSharedPreferences(context);
		Util util = new Util();
		
		weather_location_text.setText(pref.getValue("mylocation"));
		weather_simpleinfo_text.setText(pref.getValue("today_daystate") + " " + pref.getValue("today_time") + ", " + pref.getValue("today_weatherdesc"));
		weather_temperature_text.setText(pref.getValue("today_temp")+"℃");
		weather_windcilltemperature_text.setText("체감 온도 " + pref.getValue("today_feeltemp")+"℃");
		weather_rainfallpercent_text.setText(" " + pref.getValue("today_rainprob")+ " %");
		weather_windspeedpercent_text.setText(" " + pref.getValue("today_windspeed") + " m/s");
		weather_humidity_text.setText(pref.getValue("today_humidity")+"%");
		
		weather_nextdaysimpleinfo_text.setText(pref.getValue("next_daystate") + " " + pref.getValue("next_time") + ", " + pref.getValue("next_weatherdesc"));
		weather_nexttemperature_text.setText(pref.getValue("next_temp")+"℃");
		weather_weatherimage_image.setImageBitmap(util.getWeatherImage(context, pref.getValue("today_weatherdesc")));
		weather_nextweatherimage_image.setImageBitmap(util.getWeatherImage(context, pref.getValue("next_weatherdesc")));
		weather_finedusttext_text.setText("미세먼지 농도 " + pref.getValue("today_pm10value"));

		if(pref.getValue("today_pm10value").equals("-") || pref.getValue("today_pm10value").equals("default")) {
			util.weather_finedust_set(weather_finedustimage_image,(int)((1.2 * 10)), false, null);
		} else {
			util.weather_finedust_set(weather_finedustimage_image,(int)((1.2 * Integer.parseInt(pref.getValue("today_pm10value")))), false, null);
		}		
	}
}
