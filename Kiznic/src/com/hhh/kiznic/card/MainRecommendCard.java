package com.hhh.kiznic.card;

import com.hhh.kiznic.MainFragmentActivity;
import com.hhh.kiznic.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainRecommendCard extends Card implements View.OnClickListener{

	TextView recommend_title_text;
	Button recommend_morelist_button;
	
	MainFragmentActivity mf;
	
	Context context;

	public MainRecommendCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		this.context = context;
		
		init();
		setListener();
	}
	
	public void init(){
		recommend_title_text = (TextView)cardView.findViewById(R.id.recommend_title_text);
		recommend_morelist_button = (Button)cardView.findViewById(R.id.recommend_morelist_button);	
		
		mf = new MainFragmentActivity();
	}
	
	@Override
	public void setListener(){
		recommend_morelist_button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.recommend_morelist_button:
			mf.getViewPager().setCurrentItem(1);
			Toast t = Toast.makeText(context, "해당 카테고리 값 넘김", Toast.LENGTH_LONG);
            t.show();
			break;
		}
	}
}
