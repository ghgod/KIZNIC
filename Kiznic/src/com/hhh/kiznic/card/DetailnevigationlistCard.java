package com.hhh.kiznic.card;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hhh.kiznic.R;

public class DetailnevigationlistCard extends Card implements View.OnClickListener{
	
	private LinearLayout detail_navigation_view;
	private TextView detail_nevigationinfo_text;
	private String contents;
	
	public DetailnevigationlistCard(int layout, String cardName, Context context, int cardId, String contents) {
		super(layout, cardName, context, cardId);
		this.contents = contents;
		init();
	}
	
	public void init(){
		detail_nevigationinfo_text = (TextView)cardView.findViewById(R.id.detail_nevigationinfo_text);
		detail_navigation_view = (LinearLayout)cardView.findViewById(R.id.detailpage_nevigationbackground_view);
		detail_nevigationinfo_text.setText(contents);
		
		if(cardId%2==0) {
			detail_navigation_view.setBackgroundColor(Color.argb(255, 249, 249, 249));
		}else {
			detail_navigation_view.setBackgroundColor(Color.argb(255, 255, 255, 255));
		}
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
