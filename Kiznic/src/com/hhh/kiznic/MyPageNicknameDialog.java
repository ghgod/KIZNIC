package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("ValidFragment")
public class MyPageNicknameDialog extends DialogFragment{

	Context context;
	
	EditText nickname_dialog_edittext;
	
	Dialog dialog;
	
	View dialogView;

	public MyPageNicknameDialog(Context context){
		this.context = context;
	}
	
	public interface onNicknameListener{
		void setNicknameListener(String arg);
	}
	
	//private onNicknameListener mListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		dialogView = inflater.inflate(R.layout.dialog_nickname_mypage, container, false);
		
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		nickname_dialog_edittext = (EditText)dialogView.findViewById(R.id.nickname_dialog_edittext);
		nickname_dialog_edittext.requestFocus();
		nickname_dialog_edittext.setOnEditorActionListener( new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				
				onNicknameListener activity = (onNicknameListener) getParentFragment();
	        	activity.setNicknameListener(nickname_dialog_edittext.getText().toString());
	            getDialog().dismiss();
				
				return false;
			}
		});
		
		//init();
		
		return dialogView;
	}
	

	public void init(){
		
	}
}
