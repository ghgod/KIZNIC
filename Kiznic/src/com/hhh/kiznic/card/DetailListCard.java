package com.hhh.kiznic.card;

import com.hhh.kiznic.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailListCard extends Card implements View.OnClickListener{
	
	private LinearLayout detailpage_infobackground_view;
	private TextView detailpage_infotitle_text;
	private TextView detailpage_info_text;
	private String contents;
	
	public DetailListCard(int layout, String cardName, Context context, int cardId, String contents) {
		super(layout, cardName, context, cardId);
		this.contents = contents;
		init();
		
		set();
	}
	
	public void init(){
		detailpage_infobackground_view = (LinearLayout)cardView.findViewById(R.id.detailpage_infobackground_view);
		detailpage_infotitle_text = (TextView)cardView.findViewById(R.id.detailpage_infotitle_text);
		detailpage_info_text = (TextView)cardView.findViewById(R.id.detailpage_info_text);
	}
	
	public void set(){
		detailpage_infotitle_text.setText(cardName);
		detailpage_info_text.setText(contents);
		
		if(cardId % 2 == 0) {
			detailpage_infobackground_view.setBackgroundColor(Color.argb(255, 249, 249, 249));
		} else {
			detailpage_infobackground_view.setBackgroundColor(Color.argb(255, 255, 255, 255));
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
