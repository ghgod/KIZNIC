package com.hhh.kiznic.card;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hhh.kiznic.DetailPageActivity;
import com.hhh.kiznic.MainActivity;
import com.hhh.kiznic.MyPageActivity;
import com.hhh.kiznic.R;
import com.hhh.kiznic.SearchActivity;
import com.hhh.kiznic.connection.GetPlaceWeatherAsync;
import com.hhh.kiznic.dataclass.PicnicSimpleInfo;
import com.hhh.kiznic.util.Util;

public class MainRecommendCarditemCard extends Card implements View.OnClickListener{

	TextView recommend_itemtitle_text;
	TextView recommend_period_text;
	TextView recommend_place_text;
	TextView recommend_weathertemperature_text;
	TextView recommend_distance_text;
	ImageView recommend_posterimage_image;
	ImageView recommend_weatherimage_image;
	View recommend_recommendview_view;
	
	String play_no;
	
	String playTitle;
	String playStartDate;
	String playEndDate;
	String playPlace;
	String playTemperature;
	String playDistance;
	String playPosterImage;
	String playWeatherImage;
	String playLatitude;
	String playLongitude;
	
	Boolean playThumbnail = false;
	
	
	PicnicSimpleInfo simpleInfo;
	AQuery aq;
	
	Context context;
	
	public MainRecommendCarditemCard(int layout, String cardName, Context context, int cardId, PicnicSimpleInfo simpleInfo){
		super(layout, cardName, context, cardId);
		
		this.context = context;
		this.simpleInfo = simpleInfo;
		init();
		try {
			setInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListener();
	}
	
	public void init(){
		recommend_itemtitle_text = (TextView)cardView.findViewById(R.id.recommend_itemtitle_text);
		recommend_period_text = (TextView)cardView.findViewById(R.id.recommend_period_text);
		recommend_place_text = (TextView)cardView.findViewById(R.id.recommend_place_text);
		recommend_distance_text = (TextView)cardView.findViewById(R.id.recommend_distance_text);
		recommend_weathertemperature_text = (TextView)cardView.findViewById(R.id.recommend_weathertemperature_text);
		recommend_posterimage_image = (ImageView)cardView.findViewById(R.id.recommend_posterimage_image);
		recommend_weatherimage_image = (ImageView)cardView.findViewById(R.id.recommend_weatherimage_image);
		recommend_recommendview_view = (View)cardView.findViewById(R.id.recommend_recommendview_view);
	}
	
	public void setInfo() throws IOException {
		
		Util util = new Util();
		aq = new AQuery(getCardView());
		
		if(simpleInfo.getPlayTitle().equals("kiznic")){
			playTitle = "제목 없음";
		} else {
			playTitle = simpleInfo.getPlayTitle();
		}
		
		if(simpleInfo.getPlayStartDate().equals("kiznic")){
			playStartDate = "미정";
		} else {
			playStartDate = simpleInfo.getPlayStartDate();
		}
		
		if(simpleInfo.getPlayEndDate().equals("kiznic")) {
			playEndDate = "미정";
		} else {
			playEndDate = simpleInfo.getPlayEndDate();
		}
		
		if(simpleInfo.getPlayPlace().equals("kiznic")) {
			playPlace = "미정";
		} else {
			playPlace = simpleInfo.getPlayPlace();
		}
		
		if(simpleInfo.getPlayDistance().equals("kiznic")) {
			playDistance = "미정";
		} else {
			playDistance = simpleInfo.getPlayDistance();
		}
		
		if(simpleInfo.getPlayLatitude().equals("kiznic")) {
			playLatitude = "37.533"; // 당산동 위치
		} else {
			playLatitude = simpleInfo.getPlayLatitude();
		}
		
		if(simpleInfo.getPlayLongitude().equals("kiznic")) {
			playLongitude = "126.9011";
		} else {
			playLongitude = simpleInfo.getPlayLongitude();		
		}
		
		if(simpleInfo.getPlayThumb().equals("kiznic")) {
			playThumbnail = true;
		}
		
		play_no = simpleInfo.getPlayNo();
				
		recommend_itemtitle_text.setText(playTitle);
		recommend_period_text.setText(playStartDate + "~" +playEndDate);
		recommend_place_text.setText(playPlace);
		recommend_distance_text.setText(playDistance);
		new GetPlaceWeatherAsync(context, Double.parseDouble(playLatitude), Double.parseDouble(playLongitude), recommend_weatherimage_image, recommend_weathertemperature_text).execute();
		if(playThumbnail) {
			recommend_posterimage_image.setImageResource(R.drawable.contents_null);
		} else {
			aq.id(R.id.recommend_posterimage_image).image(simpleInfo.getPlayThumb());

		}
	}
	
	@Override
	public void setListener(){
		
		recommend_recommendview_view.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.recommend_recommendview_view:
			Intent intent = new Intent(context, DetailPageActivity.class);
			intent.putExtra("play_no", play_no);
			context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
}
