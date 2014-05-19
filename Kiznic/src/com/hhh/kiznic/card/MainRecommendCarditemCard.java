package com.hhh.kiznic.card;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhh.kiznic.DetailPageActivity;
import com.hhh.kiznic.MainActivity;
import com.hhh.kiznic.MyPageActivity;
import com.hhh.kiznic.R;
import com.hhh.kiznic.SearchActivity;
import com.hhh.kiznic.dataclass.PicnicSimpleInfo;
import com.hhh.kiznic.util.Util;

public class MainRecommendCarditemCard extends Card implements View.OnClickListener{

	TextView recommend_itemtitle_text;
	TextView recommend_itemage_text;
	TextView recommend_period_text;
	TextView recommend_place_text;
	TextView recommend_weathertemperature_text;
	TextView recommend_distance_text;
	ImageView recommend_posterimage_image;
	ImageView recommend_weatherimage_image;
	View recommend_recommendview_view;
	
	PicnicSimpleInfo simpleInfo;
	
	Context context;

	public MainRecommendCarditemCard(int layout, String cardName, Context context, int cardId, PicnicSimpleInfo simpleInfo){
		super(layout, cardName, context, cardId);
		
		this.context = context;
		this.simpleInfo = simpleInfo;
		init();
		setListener();
	}
	
	public void init(){
		recommend_itemtitle_text = (TextView)cardView.findViewById(R.id.recommend_itemtitle_text);
		recommend_itemage_text = (TextView)cardView.findViewById(R.id.recommend_itemage_text);
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
		
		recommend_itemtitle_text.setText(simpleInfo.getPlayTitle());
		
		//recommend_itemage_text.setText(simpleInfo.get)
		
		recommend_period_text.setText(simpleInfo.getPlayStartDate() + "~" +simpleInfo.getPlayEndDate());
		recommend_place_text.setText(simpleInfo.getPlayPlace());
		recommend_distance_text.setText(simpleInfo.getPlayDistance());
		recommend_posterimage_image.setImageBitmap(util.decompBitmap(simpleInfo.getPlayThumb()));
	}
	
	@Override
	public void setListener(){
		
		recommend_recommendview_view.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.recommend_recommendview_view:
			Intent a = new Intent(context, DetailPageActivity.class);
			context.startActivity(a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
}
