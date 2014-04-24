package com.hhh.kiznic.card;

import com.hhh.kiznic.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	TextView weather_nextdaytemperature_text;
	TextView weather_nextdayfinedusttext_text;
	ImageButton weather_morelocationinfo_imagebutton;
	ImageView weather_weatherimage_image;
	ImageView weather_finedustimage_image;
	ImageView weather_rainfall_image;
	ImageView weather_windspeed_image;
	ImageView weather_nextdayweatherimage_image;
	ImageView weather_nextdayfinedustimage_image;
	
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
		weather_nextdaytemperature_text = (TextView)cardView.findViewById(R.id.weather_nextdaytemperature_text);
		weather_nextdayfinedusttext_text = (TextView)cardView.findViewById(R.id.weather_nextdayfinedusttext_text);
		weather_morelocationinfo_imagebutton = (ImageButton)cardView.findViewById(R.id.weather_morelocationinfo_imagebutton);
		weather_weatherimage_image = (ImageView)cardView.findViewById(R.id.weather_weatherimage_image);
		weather_finedustimage_image = (ImageView)cardView.findViewById(R.id.weather_finedustimage_image);
		weather_rainfall_image = (ImageView)cardView.findViewById(R.id.weather_rainfall_image);
		weather_windspeed_image = (ImageView)cardView.findViewById(R.id.weather_windspeed_image);
		weather_nextdayweatherimage_image = (ImageView)cardView.findViewById(R.id.weather_nextdayweatherimage_image);
		weather_nextdayfinedustimage_image = (ImageView)cardView.findViewById(R.id.weather_nextdayfinedustimage_image);
	}

	@Override
	public void setListener(){
		weather_morelocationinfo_imagebutton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.weather_nextdayweatherimage_image:
			break;
		}
	}
}
