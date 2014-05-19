package com.hhh.kiznic;


import com.hhh.kiznic.MyPageNicknameDialog.onNicknameListener;
import com.hhh.kiznic.SearchcategoryDialog.onSubmitListener;

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
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MyPageActivity extends Fragment implements MyPageNicknameDialog.onNicknameListener{
	
	private Context context;
	
	private View view;
	private View mypage_login_view;
	private View mypage_nickname_view;
	
	private TextView mypage_nickname_text;
	
	public MyPageActivity(Context context) {
		context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_mypage, null);
		
		init();
		
		clicklistener();
		
    	return view;
	}

	public void clicklistener() {
		mypage_login_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MyPageLoginDialog logindialog = new MyPageLoginDialog();
				
				logindialog.show(getFragmentManager(), "login");
			}
		});
		
		mypage_nickname_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MyPageNicknameDialog logindialog = new MyPageNicknameDialog(context);
				
				logindialog.show(getFragmentManager(), "nickname");
			}
		});
	}

	public void init() {
		mypage_login_view = (View)view.findViewById(R.id.mypage_login_view);
		mypage_nickname_view = (View)view.findViewById(R.id.mypage_nickname_view);
		mypage_nickname_text = (TextView)view.findViewById(R.id.mypage_nickname_text);
	}
	
	@Override
	public void setNicknameListener(String arg){
		mypage_nickname_text.setText(arg);
	}
}
