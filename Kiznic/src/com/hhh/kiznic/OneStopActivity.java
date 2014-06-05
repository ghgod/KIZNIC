package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hhh.kiznic.databasemanager.Databasehelper;

@SuppressLint("ValidFragment")
public class OneStopActivity extends Fragment implements OnClickListener{
	
	private static View view;
	private Button onestop_ciss;
	private Context context;
	
	public OneStopActivity(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		view = inflater.inflate(R.layout.activity_onestop, null);
		
		init();
		clicklistener();
		
		return view;
	}
	
	public void init() {
		onestop_ciss = (Button)view.findViewById(R.id.onestop_ciss);
	}
	
	public void clicklistener() {
		onestop_ciss.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.onestop_ciss :
			Intent intent = new Intent(getActivity().getBaseContext(), SendEmailActivity.class);
			intent.putExtra("email_address", "aiaipming@gmail.com");
			//한국 소비자원 : safe@kca.go.kr
			startActivity(intent);
			break;
		}
	}
	
	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(getActivity().getWindow().getDecorView());
		System.gc();
		
		super.onDestroy();
	}
	
}
