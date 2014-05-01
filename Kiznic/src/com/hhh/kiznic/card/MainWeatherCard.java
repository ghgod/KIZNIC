package com.hhh.kiznic.card;

import com.hhh.kiznic.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainWeatherCard extends Card implements View.OnClickListener{

	TextView weather_location_text;
	TextView weather_simpleinfo_text;
	TextView weather_temperature_text;
	TextView weather_compareyesterday_text;
	TextView weather_windcilltemperature_text;
	TextView weather_finedusttext_text;
	TextView weather_rainfallpercent_text;
	TextView weather_windspeedpercent_text;
	TextView weather_weatherwatch_text;
	TextView weather_nextdaysimpleinfo_text;
	TextView weather_nexttemperature_text;
	TextView weather_nextfinedusttext_text;
	ImageView weather_getlocation_imagebutton;
	ImageView weather_weatherimage_image;
	ImageView weather_finedustimage_image;
	ImageView weather_rainfall_image;
	ImageView weather_windspeed_image;
	ImageView weather_nextweatherimage_image;
	ImageView weather_nextfinedustimage_image;
	public LinearLayout weather_circlemeter_layout;
	
	public MainWeatherCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		init();
		setListener();
	}

	public void init(){
		weather_location_text = (TextView)cardView.findViewById(R.id.weather_location_text);
		weather_simpleinfo_text = (TextView)cardView.findViewById(R.id.weather_simpleinfo_text);
		weather_temperature_text = (TextView)cardView.findViewById(R.id.weather_temperature_text);
		weather_compareyesterday_text = (TextView)cardView.findViewById(R.id.weather_compareyesterday_text);
		weather_windcilltemperature_text = (TextView)cardView.findViewById(R.id.weather_windcilltemperature_text);
		weather_finedusttext_text = (TextView)cardView.findViewById(R.id.weather_finedusttext_text);
		weather_rainfallpercent_text = (TextView)cardView.findViewById(R.id.weather_rainfallpercent_text);
		weather_windspeedpercent_text = (TextView)cardView.findViewById(R.id.weather_windspeedpercent_text);
		weather_weatherwatch_text = (TextView)cardView.findViewById(R.id.weather_weatherwatch_text);
		weather_nextdaysimpleinfo_text = (TextView)cardView.findViewById(R.id.weather_nextdaysimpleinfo_text);
		weather_nexttemperature_text = (TextView)cardView.findViewById(R.id.weather_nexttemperature_text);
		weather_nextfinedusttext_text = (TextView)cardView.findViewById(R.id.weather_nextfinedusttext_text);
		weather_getlocation_imagebutton = (ImageView)cardView.findViewById(R.id.weather_getlocation_imagebutton);
		weather_weatherimage_image = (ImageView)cardView.findViewById(R.id.weather_weatherimage_image);
		weather_finedustimage_image = (ImageView)cardView.findViewById(R.id.weather_finedustimage_image);
		weather_rainfall_image = (ImageView)cardView.findViewById(R.id.weather_rainfall_image);
		weather_windspeed_image = (ImageView)cardView.findViewById(R.id.weather_windspeed_image);
		weather_nextweatherimage_image = (ImageView)cardView.findViewById(R.id.weather_nextweatherimage_image);
		weather_nextfinedustimage_image = (ImageView)cardView.findViewById(R.id.weather_nextfinedustimage_image);
	}
	
	@Override
	public void setListener(){
		weather_getlocation_imagebutton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		}
	}
}
