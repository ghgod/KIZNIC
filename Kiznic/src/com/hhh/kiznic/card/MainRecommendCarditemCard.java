package com.hhh.kiznic.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhh.kiznic.R;

public class MainRecommendCarditemCard extends Card implements View.OnClickListener{

	TextView recommend_itemtitle_text;
	TextView recommend_itemage_text;
	TextView recommend_period_text;
	TextView recommend_place_text;
	TextView recommend_weathertemperature_text;
	ImageView recommend_posterimage_image;
	ImageView recommend_weatherimage_image;

	public MainRecommendCarditemCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		init();
		setListener();
	}
	
	public void init(){
		recommend_itemtitle_text = (TextView)cardView.findViewById(R.id.recommend_itemtitle_text);
		recommend_itemage_text = (TextView)cardView.findViewById(R.id.recommend_itemage_text);
		recommend_period_text = (TextView)cardView.findViewById(R.id.recommend_period_text);
		recommend_place_text = (TextView)cardView.findViewById(R.id.recommend_place_text);
		recommend_weathertemperature_text = (TextView)cardView.findViewById(R.id.recommend_weathertemperature_text);
		recommend_posterimage_image = (ImageView)cardView.findViewById(R.id.recommend_posterimage_image);
		recommend_weatherimage_image = (ImageView)cardView.findViewById(R.id.recommend_weatherimage_image);
	}
	
	@Override
	public void setListener(){
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		
		}
	}
}
