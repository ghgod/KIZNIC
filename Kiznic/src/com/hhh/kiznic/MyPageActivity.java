package com.hhh.kiznic;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.hhh.kiznic.bluetooth.*;

@SuppressLint("ValidFragment")
public class MyPageActivity extends Fragment implements MyPageNicknameDialog.onNicknameListener, NumberPicker.OnValueChangeListener{

	// Debugging
	private static final String TAG = "Main";
				
	// Intent request code
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;	
	
	private static BluetoothService btService = null;
	
	
	private static Context context;
	
	private static View view;
	//private static View mypage_nickname_view;
	
	// id
	
	private static ImageView mypage_idinput_image;
	
	// profile
	
	private static LinearLayout mypage_profile_layout;
	
	private static ImageView mypage_profileamend_button;
	
	// profile setting
	
	private static LinearLayout mypage_profilesetting_layout;
	
	private static ImageView mypage_profileset_button; 
	
	// setting 
	
	private static ImageView mypage_smartpush_button;
	private static ImageView mypage_smartwatch_button;
	
	// smartalarm
	
	private static LinearLayout mypage_smartalarm_layout;
	private static LinearLayout mypage_amalarm_layout;
	private static LinearLayout mypage_pmalarm_layout;

	private static ImageView mypage_am_alarm_button;
	private static ImageView mypage_pm_alarm_button;
	
	static boolean am_alarm_flag, pm_alarm_flag;
	
	// smartwatch
	
	private static LinearLayout mypage_smartwatch_layout;
	private static Button mypage_smartwatch_onoff_button;
	
	// alarmset
	
	private static LinearLayout mypage_alarmset_layout;

	private static NumberPicker mypage_hoursetting_np;
	private static NumberPicker mypage_minutesetting_np;
	
	private static ImageView mypage_alarmsetting_image;
	
	private static TextView mypage_amalarm_text;
	private static TextView mypage_pmalarm_text;
	
	//private TextView mypage_nickname_text;
	
	public MyPageActivity(Context context) {
		context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_mypage, null);
		
		
		init();
		
		clicklistener();
		
		profile_circleimage();
		profile_small_circleimage();
		
		setNumberpicker();
		
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
		
		mypage_profileamend_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_profilesetting_layout.setVisibility(LinearLayout.VISIBLE);
				mypage_profile_layout.setVisibility(LinearLayout.INVISIBLE);
			}
		});
		
		mypage_profileset_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_profilesetting_layout.setVisibility(LinearLayout.INVISIBLE);
				mypage_profile_layout.setVisibility(LinearLayout.VISIBLE);
			}
		});
		
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
		
		mypage_smartpush_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_smartalarm_layout.setVisibility(LinearLayout.VISIBLE);
				mypage_smartwatch_layout.setVisibility(LinearLayout.INVISIBLE);
			}
		});
		
		mypage_smartwatch_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_smartalarm_layout.setVisibility(LinearLayout.INVISIBLE);
				mypage_smartwatch_layout.setVisibility(LinearLayout.VISIBLE);
			}
		});
		
		mypage_amalarm_layout.setOnClickListener(new LinearLayout.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_alarmset_layout.setVisibility(LinearLayout.VISIBLE);
				
				mypage_amalarm_text.setTextColor(Color.parseColor("#FFFF00"));
				mypage_pmalarm_text.setTextColor(Color.parseColor("#FFFFFF"));
			}
		});
		
		mypage_pmalarm_layout.setOnClickListener(new LinearLayout.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_alarmset_layout.setVisibility(LinearLayout.VISIBLE);

				mypage_amalarm_text.setTextColor(Color.parseColor("#FFFFFF"));
				mypage_pmalarm_text.setTextColor(Color.parseColor("#FFFF00"));
			}
		});
		
		mypage_alarmsetting_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v){
				mypage_alarmset_layout.setVisibility(LinearLayout.INVISIBLE);
			}
		});
		
		mypage_smartwatch_onoff_button.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				
				if(adapter == null) {
					Toast.makeText(getActivity().getBaseContext(), "블루투스를 지원하지 않는 기기입니다", Toast.LENGTH_LONG).show();
				} else {
					if(adapter.getState() == BluetoothAdapter.STATE_TURNING_ON || adapter.getState() == BluetoothAdapter.STATE_ON)     
						{
						Toast.makeText(getActivity().getApplicationContext(), "Bluetooth Off", Toast.LENGTH_LONG).show();
						adapter.disable();
						}
					else
						{
						//Toast.makeText(getActivity().getBaseContext(), "블루투스를 On합니다", Toast.LENGTH_LONG).show();	
							if(btService.getDeviceState()) {
								// ��������� ���� ������ ����� ��
								btService.enableBluetooth();
							}
						}
				}		
			
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
		
		mypage_idinput_image = (ImageView)view.findViewById(R.id.mypage_idinput_image);
		
		// mypage profile
		
		mypage_profile_layout = (LinearLayout)view.findViewById(R.id.mypage_profile_layout);
		mypage_profilesetting_layout = (LinearLayout)view.findViewById(R.id.mypage_profilesetting_layout);
		
		mypage_profilesetting_layout.setVisibility(LinearLayout.INVISIBLE);

		mypage_profileamend_button = (ImageView)view.findViewById(R.id.mypage_profileamend_button);
		mypage_profileset_button = (ImageView)view.findViewById(R.id.mypage_profileset_button);
		
		// mypage smartpush, mypage smartwatch
		
		mypage_amalarm_layout = (LinearLayout)view.findViewById(R.id.mypage_amalarm_layout);
		mypage_pmalarm_layout = (LinearLayout)view.findViewById(R.id.mypage_pmalarm_layout);
		
		mypage_smartalarm_layout = (LinearLayout)view.findViewById(R.id.mypage_smartalarm_layout);
		mypage_smartwatch_layout = (LinearLayout)view.findViewById(R.id.mypage_smartwatch_layout);
		
		mypage_smartwatch_layout.setVisibility(LinearLayout.INVISIBLE);
		mypage_smartwatch_onoff_button =(Button)view.findViewById(R.id.mypage_smartwatch_onoff_button);
		
		mypage_am_alarm_button = (ImageView)view.findViewById(R.id.mypage_am_alarm_button);
		mypage_pm_alarm_button = (ImageView)view.findViewById(R.id.mypage_pm_alarm_button);
		
		am_alarm_flag = false;
		pm_alarm_flag = false;
		
		mypage_smartpush_button = (ImageView)view.findViewById(R.id.mypage_smartpush_button);
		mypage_smartwatch_button = (ImageView)view.findViewById(R.id.mypage_smartwatch_button);
		
		mypage_smartpush_button.setImageResource(R.drawable.mypage_push_image_focus);
		
		if(btService == null) {
			btService = new BluetoothService(getActivity(), mHandler);
		}
		
		// alarm set
		
		mypage_alarmset_layout = (LinearLayout)view.findViewById(R.id.mypage_alarmset_layout);
		
		mypage_alarmset_layout.setVisibility(LinearLayout.INVISIBLE);
		
		mypage_hoursetting_np = (NumberPicker)view.findViewById(R.id.mypage_hoursetting_np);
		mypage_minutesetting_np = (NumberPicker)view.findViewById(R.id.mypage_minutesetting_np);
		
		mypage_alarmsetting_image = (ImageView)view.findViewById(R.id.mypage_alarmsetting_image);

		mypage_amalarm_text = (TextView)view.findViewById(R.id.mypage_amalarm_text);
		mypage_pmalarm_text = (TextView)view.findViewById(R.id.mypage_pmalarm_text);
		
		//mypage_nickname_view = (View)view.findViewById(R.id.mypage_nickname_view);
		//mypage_nickname_text = (TextView)view.findViewById(R.id.mypage_nickname_text);

		
	}
	
	@Override
	public void setNicknameListener(String arg){
		//mypage_nickname_text.setText(arg);
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		
	}

	public void setNumberpicker(){
		View hour_upbutton = mypage_hoursetting_np.getChildAt(0);
		View hour_text = mypage_hoursetting_np.getChildAt(1);
		View hour_downbutton = mypage_hoursetting_np.getChildAt(2);
		
		View minute_upbutton = mypage_minutesetting_np.getChildAt(0);
		View minute_text = mypage_minutesetting_np.getChildAt(1);
		View minute_downView = mypage_minutesetting_np.getChildAt(2);
		
		mypage_hoursetting_np.setMaxValue(12);
		mypage_hoursetting_np.setMinValue(1);
		mypage_hoursetting_np.setWrapSelectorWheel(true);
		
		mypage_minutesetting_np.setMaxValue(60);
		mypage_minutesetting_np.setMinValue(1);
		mypage_minutesetting_np.setWrapSelectorWheel(true);
	}
	
	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
		
	};
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //Fragment fragment = getFragmentManager().findFragmentById(2);
        //fragment.onActivityResult(requestCode, resultCode, data);
		
        Log.d(TAG, "onActivityResult " + resultCode);
        
        switch (requestCode) {
        
        /** �߰��� �κ� ���� **/
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
            	btService.getDeviceInfo(data);
            }
            break;
        /** �߰��� �κ� �� **/
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
            	// Next Step
            	btService.scanDevice();
            } else {

                Log.d(TAG, "Bluetooth is not enabled");
            }
            break;
        }
	}
	
}
