package com.hhh.kiznicsw;

import com.hhh.kiznicsw.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {
	
	LinearLayout bg_splash;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 
	    requestWindowFeature(Window.FEATURE_NO_TITLE); // 13. 화면의 타이틀바를 없애줍니다.
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	    
	    setContentView(R.layout.activity_splash);
	      
	    Handler handler = new Handler()  {
	        @Override
	        public void handleMessage(Message msg)
	        	{
	        	finish(); 	        	// 액티비티 종료
	        	Intent intent = new Intent(getApplicationContext(), MainFragmentActivity.class);
	    	    startActivity(intent);
	        	}
	        };

	    handler.sendEmptyMessageDelayed(0, 1000);
	    
	    
	}
	
	

}
