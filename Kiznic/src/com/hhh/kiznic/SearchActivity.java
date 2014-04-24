package com.hhh.kiznic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SearchActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_search);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		Button title_home_button = (Button)findViewById(R.id.title_home_button);
		title_home_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent homeActivity = new Intent(SearchActivity.this, MainActivity.class);
				homeActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(homeActivity);
			}
		});
		Button title_mypage_button = (Button)findViewById(R.id.title_mypage_button);
		title_mypage_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent mypageActivity = new Intent(SearchActivity.this, MyPageActivity.class);
				mypageActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(mypageActivity);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
