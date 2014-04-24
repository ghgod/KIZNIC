package com.hhh.kiznic.card;

import com.hhh.kiznic.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainRecommendCard extends Card implements View.OnClickListener{

	TextView recommend_title_text;
	TextView recommend_itemtitle_text, recommend_itemtitle_text2;
	TextView recommend_itemage_text, recommend_itemage_text2;
	TextView recommend_period_text, recommend_period_text2;
	TextView recommend_place_text, recommend_place_text2;
	TextView recommend_weathertemperature_text, recommend_weathertemperature_text2;
	ImageView recommend_posterimage_image, recommend_posterimage_image2;
	ImageView recommend_weatherimage_image, recommend_weatherimage_image2;
	Button recommend_morelist_button;

	public MainRecommendCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		init();
		setListener();
	}
	
	public void init(){
		recommend_title_text = (TextView)cardView.findViewById(R.id.recommend_title_text);
		recommend_itemtitle_text = (TextView)cardView.findViewById(R.id.recommend_itemtitle_text);
		recommend_itemtitle_text2 = (TextView)cardView.findViewById(R.id.recommend_itemtitle_text2);
		recommend_itemage_text = (TextView)cardView.findViewById(R.id.recommend_itemage_text);
		recommend_itemage_text2 = (TextView)cardView.findViewById(R.id.recommend_itemage_text2);
		recommend_period_text = (TextView)cardView.findViewById(R.id.recommend_period_text);
		recommend_period_text2 = (TextView)cardView.findViewById(R.id.recommend_period_text2);
		recommend_place_text = (TextView)cardView.findViewById(R.id.recommend_place_text);
		recommend_place_text2 = (TextView)cardView.findViewById(R.id.recommend_place_text2);
		recommend_weathertemperature_text = (TextView)cardView.findViewById(R.id.recommend_weathertemperature_text);
		recommend_weathertemperature_text2 = (TextView)cardView.findViewById(R.id.recommend_weathertemperature_text2);
		recommend_posterimage_image = (ImageView)cardView.findViewById(R.id.recommend_posterimage_image);
		recommend_posterimage_image2 = (ImageView)cardView.findViewById(R.id.recommend_posterimage_image2);
		recommend_weatherimage_image = (ImageView)cardView.findViewById(R.id.recommend_weatherimage_image);
		recommend_weatherimage_image2 = (ImageView)cardView.findViewById(R.id.recommend_weatherimage_image2);
		recommend_morelist_button = (Button)cardView.findViewById(R.id.recommend_morelist_button);	
	}
	
	@Override
	public void setListener(){
		recommend_morelist_button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.recommend_morelist_button:
			break;
		}
	}
}
