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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hhh.kiznic.databasemanager.Databasehelper;

@SuppressLint("ValidFragment")
public class OneStopActivity extends Fragment implements OnClickListener{
	
	private static View view;
	private Button onestop_ciss;
	private Button onestop_sobijawon;
	private Context context;
	
	private static LinearLayout onestop_tutorial_layout;
	private static ImageView onestop_tutorial_image;
	
	public static localDataAdmin localdata;
	
	public OneStopActivity(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		view = inflater.inflate(R.layout.activity_onestop, null);
		
		init();
		clicklistener();
		
		set_image();
		tutorial_layout();
		
		return view;
	}
	
	private void set_image() {
		// TODO Auto-generated method stub
		onestop_tutorial_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.safe_tutorial_image, 600, 600));
	}

	private void tutorial_layout() {
		if(!localdata.gettutorialsafe()){
			
			onestop_tutorial_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onestop_tutorial_layout.setVisibility(LinearLayout.INVISIBLE);
					localdata.settutorialsafe(true);
					localdata.setLocalData();
				}
			});
		}
		else{
			onestop_tutorial_layout.setVisibility(LinearLayout.INVISIBLE);
		}
	}

	public void init() {
		onestop_ciss = (Button)view.findViewById(R.id.onestop_ciss);
		onestop_sobijawon = (Button)view.findViewById(R.id.onestop_sobijawon);
		
		localdata = new localDataAdmin(getActivity().getBaseContext());

		onestop_tutorial_layout = (LinearLayout)view.findViewById(R.id.onestop_tutorial_layout);
		onestop_tutorial_image = (ImageView)view.findViewById(R.id.onestop_tutorial_image);
	}
	
	public void clicklistener() {
		onestop_ciss.setOnClickListener(this);
		onestop_sobijawon.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SEND);
		switch(v.getId()){
		case R.id.onestop_ciss :
		//	Intent intent = new Intent(getActivity().getBaseContext(), SendEmailActivity.class);
		//	intent.putExtra("email_address", "aiaipming@gmail.com");
			//한국 소비자원 : safe@kca.go.kr
		//	startActivity(intent);
			
			
			intent.setType("plain/text");
			
			String[] tos = {"aiapming@gmail.com"};
			intent.putExtra(Intent.EXTRA_EMAIL, tos);
			
			intent.putExtra(Intent.EXTRA_SUBJECT, "[Kiznic] 불편사항 접수 : ");
			//intent.putExtra(Intent.EXTRA_TEXT, "김규홍은 듣고나서 밥먹으러 가자");
			
			//intent.putExtra(Intent.EXTRA_STREAM, value);
			startActivity(intent);
			break;
		case R.id.onestop_sobijawon :
			intent.setType("plain/text");
			
			String[] tos2 = {"aiapming@gmail.com"};
			intent.putExtra(Intent.EXTRA_EMAIL, tos2);
			
			intent.putExtra(Intent.EXTRA_SUBJECT, "[Kiznic] 불편사항 접수 : ");
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
