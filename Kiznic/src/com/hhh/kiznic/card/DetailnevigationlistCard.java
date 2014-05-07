package com.hhh.kiznic.card;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hhh.kiznic.R;

public class DetailnevigationlistCard extends Card implements View.OnClickListener{
	
	private TextView detail_nevigationinfo_text;
	
	public DetailnevigationlistCard(int layout, String cardName, Context context, int cardId) {
		super(layout, cardName, context, cardId);
		
		init();
	}
	
	public void init(){
		detail_nevigationinfo_text = (TextView)cardView.findViewById(R.id.detail_nevigationinfo_text);
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
