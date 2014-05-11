package com.hhh.kiznic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class KiznicTitle implements View.OnClickListener{

	private Activity activity;
	
	static private Button home_button;
	static private Button album_button;
	static private Button search_button;
	static private Button bookmark_button;
	static private Button mypage_button;
	
	static private int flag = 0;
	
	public KiznicTitle(Activity activity){
		this.activity = activity;
		
		init();
		
		setfocuse();
		
		setListener();
	}
	
	private void setfocuse() {
		switch(flag){
		case 0:
			home_button.setFocusable(true);
			home_button.setFocusableInTouchMode(true);
			home_button.requestFocus();
			break;
		case 1:
			album_button.setFocusable(true);
			album_button.setFocusableInTouchMode(true);
			album_button.requestFocus();
			break;
		case 2:
			search_button.setFocusable(true);
			search_button.setFocusableInTouchMode(true);
			search_button.requestFocus();
			break;
		case 3:
			bookmark_button.setFocusable(true);
			bookmark_button.setFocusableInTouchMode(true);
			bookmark_button.requestFocus();
			break;
		case 4:
			mypage_button.setFocusable(true);
			mypage_button.setFocusableInTouchMode(true);
			mypage_button.requestFocus();
			break;
		}
	}

	private void setListener() {
		home_button.setOnClickListener(this);
		album_button.setOnClickListener(this);
		search_button.setOnClickListener(this);
		bookmark_button.setOnClickListener(this);
		mypage_button.setOnClickListener(this);
	}

	private void init() {
		home_button = (Button)this.activity.findViewById(R.id.title_home_button);
		album_button = (Button)this.activity.findViewById(R.id.title_album_button);
		search_button = (Button)this.activity.findViewById(R.id.title_search_button);
		bookmark_button = (Button)this.activity.findViewById(R.id.title_bookmark_button);
		mypage_button = (Button)this.activity.findViewById(R.id.title_mypage_button);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.title_home_button:
			
			if(flag != 0){
				setfocuseflag(flag);
				flag = 0;
				Intent moveActivity = new Intent(activity, MainActivity.class);
				moveActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				activity.startActivity(moveActivity);
			}
			
			break;
		case R.id.title_album_button:
			
			if(flag != 1){
				setfocuseflag(flag);
				flag = 1;
				//Intent moveActivity = new Intent(activity, MainActivity.class);
				//moveActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				//activity.startActivity(moveActivity);
			}
			
			break;
		case R.id.title_search_button:
			
			if(flag != 2){
				setfocuseflag(flag);
				flag = 2;
				Intent moveActivity = new Intent(activity, SearchActivity.class);
				moveActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				activity.startActivity(moveActivity);
			}
			
			break;
		case R.id.title_bookmark_button:
			
			if(flag != 3){
				setfocuseflag(flag);
				flag = 3;
				//Intent moveActivity = new Intent(activity, MainActivity.class);
				//moveActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				//activity.startActivity(moveActivity);
			}
			
			break;
		case R.id.title_mypage_button:
			
			if(flag != 4){
				setfocuseflag(flag);
				flag = 4;
				Intent moveActivity = new Intent(activity, MyPageActivity.class);
				moveActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				activity.startActivity(moveActivity);
			}
			
			break;
		}
	}
	
	public void setfocuseflag(int focuse_flag){
		if(focuse_flag != 0)
			home_button.setFocusable(false);
		if(focuse_flag != 1)
			album_button.setFocusable(false);
		if(focuse_flag != 2)
			search_button.setFocusable(false);
		if(focuse_flag != 3)
			bookmark_button.setFocusable(false);
		if(focuse_flag != 4)
			mypage_button.setFocusable(false);
	}
}
