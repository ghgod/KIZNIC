package com.hhh.kiznic;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class MyPageActivity extends Fragment{
	
	private Context context;
	
	public MyPageActivity(Context context) {
		context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_mypage, null);
		
    	return view;
	}
}
