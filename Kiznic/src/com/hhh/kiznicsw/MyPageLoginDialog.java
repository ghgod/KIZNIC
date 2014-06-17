package com.hhh.kiznicsw;

import com.hhh.kiznicsw.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyPageLoginDialog extends DialogFragment{
	
	private View mypage_login_1_view;
	private View mypage_login_2_view;
	private View mypage_login_3_view;
	
	private Button dialog_login_exit_button;
	
	Dialog dialog;
	Context context;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		dialog = new Dialog(getActivity());
		
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	
		dialog.setContentView(R.layout.dialog_login_mypage);
	
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		init();
		
		setClickListener();
		
		dialog.show();
		
		return dialog;
	}

	public void init(){
		dialog_login_exit_button = (Button)dialog.findViewById(R.id.login_dialog_exit_button);
		mypage_login_1_view = (View)dialog.findViewById(R.id.mypage_login_1_view);
		mypage_login_2_view = (View)dialog.findViewById(R.id.mypage_login_2_view);
		mypage_login_3_view = (View)dialog.findViewById(R.id.mypage_login_3_view);
	}
	
	private void setClickListener(){
		dialog_login_exit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		
		mypage_login_1_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(dialog.getContext(), "KaKaoTalk 로그인", Toast.LENGTH_LONG);
	            t.show();
	            getDialog().dismiss();
			}
		});
		
		mypage_login_2_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(dialog.getContext(), "FaceBook 로그인", Toast.LENGTH_LONG);
	            t.show();
	            getDialog().dismiss();
			}
		});
		
		mypage_login_3_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(dialog.getContext(), "Naver 로그인", Toast.LENGTH_LONG);
	            t.show();
	            getDialog().dismiss();
			}
		});
	}
}
