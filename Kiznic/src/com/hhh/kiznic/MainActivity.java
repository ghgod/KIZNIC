package com.hhh.kiznic;

import java.util.ArrayList;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCard;
import com.hhh.kiznic.card.MainWeatherCard;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView mainListView;
	private CardAdapter cardAdapter;
	private int cardCount = -1;
	private View profile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		init();
		
		// Title Button
		
		Button title_back_button = (Button)findViewById(R.id.title_back_button);
		title_back_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
		
		Button title_search_button = (Button)findViewById(R.id.title_search_button);
		title_search_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
				searchActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(searchActivity);
			}
		});
		Button title_mypage_button = (Button)findViewById(R.id.title_mypage_button);
		title_mypage_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent mypageActivity = new Intent(MainActivity.this, MyPageActivity.class);
				mypageActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(mypageActivity);
			}
		});
		
		// profile
		
		
		profile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mypageActivity = new Intent(MainActivity.this, MyPageActivity.class);
				startActivity(mypageActivity);
			}
		});
		
		// list
		cardAdapter.addItem(new MainWeatherCard(R.layout.list_item_weather_card, "Weather Card", getApplicationContext(), cardCount++));
	
		for(int i=0;i<4;i++){
			cardAdapter.addItem(new MainRecommendCard(R.layout.list_item_card, "Recommend Card", getApplicationContext(), cardCount++));
		}
		
		mainListView.setAdapter(cardAdapter);
	}
	
	private void init() {
		
		profile = (View)findViewById(R.id.profile);
		
		mainListView = (ListView)findViewById(R.id.main_list_view);
		cardAdapter = new CardAdapter(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
