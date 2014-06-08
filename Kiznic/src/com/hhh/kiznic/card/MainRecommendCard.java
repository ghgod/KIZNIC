package com.hhh.kiznic.card;

import com.hhh.kiznic.MainFragmentActivity;
import com.hhh.kiznic.R;
import com.hhh.kiznic.SearchActivity;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainRecommendCard extends Card implements View.OnClickListener{

	TextView recommend_title_text;
	Button recommend_morelist_button;
	MainFragmentActivity mf;

	public interface AListener{
		void setAListener(String arg);
	}
	
	public MainRecommendCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
		
		init();
		setListener();
	}
	
	
	public void init(){
		recommend_title_text = (TextView)cardView.findViewById(R.id.recommend_title_text);
		recommend_morelist_button = (Button)cardView.findViewById(R.id.recommend_morelist_button);	
		recommend_title_text.setText(cardName);
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
			//AListener activity = (AListener) getCardView();
        	//activity.setAListener("2");
			//mf.start
			
			Bundle bundle = new Bundle();
			bundle.putString("play_type", String.valueOf(cardName));
			SearchActivity frag = new SearchActivity(getCardView().getContext());
			frag.setArguments(bundle);
			frag.getS();
			mf.mViewPager.setCurrentItem(1);
			break;
		}
	}
}
