package com.hhh.kiznic.card;

import com.hhh.kiznic.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainRecommendCard extends Card implements View.OnClickListener{

	TextView recommend_title_text;
	Button recommend_morelist_button;

	public MainRecommendCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		init();
		setListener();
	}
	
	public void init(){
		recommend_title_text = (TextView)cardView.findViewById(R.id.recommend_title_text);
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
