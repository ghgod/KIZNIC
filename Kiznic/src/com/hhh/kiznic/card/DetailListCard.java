package com.hhh.kiznic.card;

import com.hhh.kiznic.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DetailListCard extends Card implements View.OnClickListener{

	private TextView detailpage_infotitle_text;
	private TextView detailpage_info_text;
	
	public DetailListCard(int layout, String cardName, Context context, int cardId) {
		super(layout, cardName, context, cardId);
		
		init();
	}
	
	public void init(){
		detailpage_infotitle_text = (TextView)cardView.findViewById(R.id.detailpage_infotitle_text);
		detailpage_info_text = (TextView)cardView.findViewById(R.id.detailpage_info_text);
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
