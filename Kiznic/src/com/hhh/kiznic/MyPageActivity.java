package com.hhh.kiznic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MyPageActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_mypage);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		Button title_home_button = (Button)findViewById(R.id.title_home_button);
		title_home_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent homeActivity = new Intent(MyPageActivity.this, MainActivity.class);
				homeActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(homeActivity);
			}
		});
		Button title_search_button = (Button)findViewById(R.id.title_search_button);
		title_search_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent searchActivity = new Intent(MyPageActivity.this, SearchActivity.class);
				searchActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(searchActivity);
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
