package com.hhh.kiznic;


import com.androidquery.AQuery;
import com.hhh.kiznic.MyPageNicknameDialog.onNicknameListener;
import com.hhh.kiznic.SearchcategoryDialog.onSubmitListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MyPageActivity extends Fragment implements MyPageNicknameDialog.onNicknameListener, NumberPicker.OnValueChangeListener{
	
	private static Context context;
	
	private static View view;
	private static View mypage_nickname_view;
	private static LinearLayout mypage_profile_layout;
	private static LinearLayout mypage_smart_setting_layout;
	
	private static ImageView mypage_idinput_image;
	private static ImageView mypage_profileamend_button;
	
	private static ImageView mypage_profileset_button; 

	private static ImageView mypage_am_alarm_button;
	private static ImageView mypage_pm_alarm_button;
	
	private static ImageView mypage_smartpush_button;
	private static ImageView mypage_smartwatch_button;
	
	private AQuery aq;
	
	static boolean am_alarm_flag, pm_alarm_flag;
	
	//private TextView mypage_nickname_text;
	
	public MyPageActivity(Context context) {
		context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_mypage, null);
		
		init();
		
		clicklistener();
		
		profile_circleimage();
		profile_small_circleimage();
		
    	return view;
	}

	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(((Activity) context).getWindow().getDecorView());
		System.gc();
		
		super.onDestroy();
	}
	
	public void clicklistener() {
		mypage_idinput_image.setOnClickListener(new ImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MyPageLoginDialog logindialog = new MyPageLoginDialog();
				
				logindialog.show(getFragmentManager(), "login");
			}
		});
		profile_view_setlistener();
		
		mypage_smartpush_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				
				mypage_smartpush_button.setImageResource(R.drawable.mypage_push_image_focus);
				mypage_smartwatch_button.setImageResource(R.drawable.mypage_smartwatch_image_up);
				
				mypage_smart_setting_layout = (LinearLayout)view.findViewById(R.id.mypage_smart_setting_layout);
				mypage_smart_setting_layout.removeAllViews();
				
				LayoutInflater inflater2 = getActivity().getLayoutInflater();
				mypage_smart_setting_layout.addView(inflater2.inflate(R.layout.mypage_pushalarm_setting, null));
				
				mypage_am_alarm_button = (ImageView)view.findViewById(R.id.mypage_am_alarm_button);
				mypage_pm_alarm_button = (ImageView)view.findViewById(R.id.mypage_pm_alarm_button);
				
				mypage_am_alarm_button.setOnClickListener(new ImageView.OnClickListener(){
					@Override
					public void onClick(View v) {
						if(!am_alarm_flag){
							mypage_am_alarm_button.setImageResource(R.drawable.mypage_pushalarm_image_focus);
							am_alarm_flag = true;
						}
						else{
							mypage_am_alarm_button.setImageResource(R.drawable.mypage_pushalarm_image_up);
							am_alarm_flag = false;
						}
					}
				});
				
				mypage_pm_alarm_button.setOnClickListener(new ImageView.OnClickListener(){
					@Override
					public void onClick(View v) {
						if(!pm_alarm_flag){
							mypage_pm_alarm_button.setImageResource(R.drawable.mypage_pushalarm_image_focus);
							pm_alarm_flag = true;
						}
						else{
							mypage_pm_alarm_button.setImageResource(R.drawable.mypage_pushalarm_image_up);
							pm_alarm_flag = false;
						}
					}
				});
			}
		});
		
		mypage_smartwatch_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				
				mypage_smartpush_button.setImageResource(R.drawable.mypage_push_image_up);
				mypage_smartwatch_button.setImageResource(R.drawable.mypage_smartwatch_image_focus);
				
				mypage_smart_setting_layout = (LinearLayout)view.findViewById(R.id.mypage_smart_setting_layout);
				mypage_smart_setting_layout.removeAllViews();
				
				LayoutInflater inflater2 = getActivity().getLayoutInflater();
				mypage_smart_setting_layout.addView(inflater2.inflate(R.layout.mypage_smartwatch_setting, null));
			}
		});
		
		/*
		mypage_nickname_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MyPageNicknameDialog logindialog = new MyPageNicknameDialog(context);
				
				logindialog.show(getChildFragmentManager(), "nickname");
			}
		});
		*/
	}

	private void profile_circleimage(){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kid);
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		ImageView profile_image = (ImageView)view.findViewById(R.id.mypage_kidimage_image);
		
		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
		
		Canvas c = new Canvas(circleBitmap);
		
		Paint paint_stroke = new Paint();
		
		paint_stroke.setARGB(255, 106, 193, 198);
		paint_stroke.setAntiAlias(true);

		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint_stroke);
		
		Paint paint = new Paint(); 
		paint.setShader(shader);
		paint.setAntiAlias(true);
		
		
		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2-5, paint);
		
		profile_image.setImageBitmap(circleBitmap);
	}
	
	private void profile_small_circleimage(){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kid);
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		ImageView profile_image = (ImageView)view.findViewById(R.id.mypage_smallkidimage_image);
		
		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
		
		Canvas c = new Canvas(circleBitmap);
		
		Paint paint_stroke = new Paint();
		
		paint_stroke.setARGB(255, 252, 233, 6);
		paint_stroke.setAntiAlias(true);

		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint_stroke);
		
		Paint paint = new Paint(); 
		paint.setShader(shader);
		paint.setAntiAlias(true);
		
		
		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2-10, paint);
		
		profile_image.setImageBitmap(circleBitmap);
	}
	
	public void init() {
		
		mypage_profile_layout = (LinearLayout)view.findViewById(R.id.mypage_profile_layout);
		mypage_profile_layout.removeAllViews();
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mypage_profile_layout.addView(inflater.inflate(R.layout.mypage_profile, null));
		
		mypage_idinput_image = (ImageView)view.findViewById(R.id.mypage_idinput_image);
		mypage_profileamend_button = (ImageView)view.findViewById(R.id.mypage_profileamend_button);
		//mypage_nickname_view = (View)view.findViewById(R.id.mypage_nickname_view);
		//mypage_nickname_text = (TextView)view.findViewById(R.id.mypage_nickname_text);

		mypage_am_alarm_button = (ImageView)view.findViewById(R.id.mypage_am_alarm_button);
		mypage_pm_alarm_button = (ImageView)view.findViewById(R.id.mypage_pm_alarm_button);
		
		am_alarm_flag = false;
		pm_alarm_flag = false;
		
		mypage_smartpush_button = (ImageView)view.findViewById(R.id.mypage_smartpush_button);
		mypage_smartwatch_button = (ImageView)view.findViewById(R.id.mypage_smartwatch_button);
		
		mypage_smartpush_button.setImageResource(R.drawable.mypage_push_image_focus);
		
		mypage_smart_setting_layout = (LinearLayout)view.findViewById(R.id.mypage_smart_setting_layout);
		mypage_smart_setting_layout.removeAllViews();
		
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		mypage_smart_setting_layout.addView(inflater2.inflate(R.layout.mypage_pushalarm_setting, null));

	}
	
	public void profile_view_setlistener(){
		mypage_profileamend_button.setOnClickListener(new ImageView.OnClickListener(){
			
			@Override
			public void onClick(View v){
				mypage_profile_layout.removeAllViews();
				LayoutInflater inflater = getActivity().getLayoutInflater();
				mypage_profile_layout.addView(inflater.inflate(R.layout.mypage_profile_setting, null));
				if(mypage_profileset_button == null)
					mypage_profileset_button = (ImageView)view.findViewById(R.id.mypage_profileset_button);
				profilesetting_view_setlistener();
			}
		});
	}
	public void profilesetting_view_setlistener(){
		mypage_profileset_button.setOnClickListener(new ImageView.OnClickListener(){
			
			@Override
			public void onClick(View v){
				mypage_profile_layout.removeAllViews();
				LayoutInflater inflater = getActivity().getLayoutInflater();
				mypage_profile_layout.addView(inflater.inflate(R.layout.mypage_profile, null));
				
				if(mypage_profileamend_button == null)
					mypage_profileamend_button = (ImageView)view.findViewById(R.id.mypage_profileamend_button);
				profile_view_setlistener();
			}
		});
	}
	
	@Override
	public void setNicknameListener(String arg){
		//mypage_nickname_text.setText(arg);
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		Log.i("value is",""+newVal);
	}
}
