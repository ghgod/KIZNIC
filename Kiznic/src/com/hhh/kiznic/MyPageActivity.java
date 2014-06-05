package com.hhh.kiznic;


import java.io.File;
import java.io.IOException;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.hhh.kiznic.bluetooth.*;

@SuppressLint({ "ValidFragment", "NewApi" })
public class MyPageActivity extends Fragment implements NumberPicker.OnValueChangeListener{

	class profileInfo{
		private String name;
		private String sex;
		private String birth;
		
		public profileInfo(String name, String sex, String birth){
			this.name = name;
			this.sex = sex;
			this.birth = birth;
		}

		public String getname(){return name;}
		public String getsex(){return sex;}
		public String getbirth(){return birth;}
		
		public void setname(String name){
			this.name = name;
		}
		
		public void setsex(String sex){
			this.sex = sex;
		}
		
		public void setbirth(String birth){
			this.birth = birth;
		}
	}

	private static TextView profile_name;
	private static TextView profile_sex;
	private static TextView profile_birth;
	
	// Debugging
	private static final String TAG = "Main";
				
	// Intent request code
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;	
	private static final int REQUEST_IMAGE_ALBUM = 3;
	private static final int REQUEST_IMAGE_CROP = 4;
	
	private static BluetoothService btService = null;
	
	
	private static Context context;
	
	private static View view;
	//private static View mypage_nickname_view;
	
	// id
	
	private static ImageView mypage_idinput_image;
	
	// profile

	private static LinearLayout mypage_kidimage_view;
	private static LinearLayout mypage_kidimageset_view;
	
	private static LinearLayout mypage_profile_layout;
	
	private static ImageView mypage_profileamend_button;

	private static ImageView mypage_smallkidimage1_image;
	private static ImageView mypage_smallkidimage2_image;
	private static ImageView mypage_smallkidimage3_image;

	private static ImageView mypage_kidimage_image;
	private static ImageView mypage_kidimageset_image;
	
	// profile setting
	
	private static LinearLayout mypage_profilesetting_layout;
	
	private static ImageView mypage_profileset_button; 
	
	private static EditText mypage_profilename_edittext;
	private static EditText mypage_profilebirth_edittext;
	
	private static RadioGroup profile_kidsex_radiogroup;
	
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
	private static ImageView smartwatch_wearning_image;
	
	// alarmset
	
	private static LinearLayout mypage_alarmset_layout;

	private static NumberPicker mypage_hoursetting_np;
	private static NumberPicker mypage_minutesetting_np;
	
	private static ImageView mypage_alarmsetting_image;
	
	private static TextView mypage_amalarm_text;
	private static TextView mypage_pmalarm_text;
	
	private static boolean mypage_alarm_flag;

	private static TextView am_alarm_hour_text;
	private static TextView am_alarm_minute_text;
	private static TextView pm_alarm_hour_text;
	private static TextView pm_alarm_minute_text;
	
	// Image set
	
	private static Uri imageCaptureUri;
	
	
	//
	profileInfo[] profile_info;

	private static int profile_flag = 0;
	private static boolean profile_amend_flag = false;
	
	public MyPageActivity(Context context) {
		context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_mypage, null);
		
		init();
		
		clicklistener();
		
		setNumberpicker();
		
		set_image();
		
    	return view;
	}

	private void set_image() {
		mypage_idinput_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_login, 200, 200));
		mypage_profileamend_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_profileamend_image, 200, 200));
		mypage_profileset_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_profileset_image, 200, 200));
		mypage_smartpush_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_push_image_focus, 200, 200));
		mypage_smartwatch_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_smartwatch_image_up, 200, 200));
		mypage_am_alarm_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_pushalarm_image_up, 200, 200));
		mypage_pm_alarm_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_pushalarm_image_up, 200, 200));
	
		mypage_smallkidimage1_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_smallprofile_image, 200, 200));
		mypage_smallkidimage2_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_smallprofile_image, 200, 200));
		mypage_smallkidimage3_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_smallprofile_image, 200, 200));
	
		smartwatch_wearning_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_waerning_image, 200, 200));
	
		mypage_kidimage_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_profile, 200, 200));
		mypage_kidimageset_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_profileimage_set_image, 200, 200));
	}

	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(getActivity().getWindow().getDecorView());
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
				
				mypage_kidimageset_view.setVisibility(LinearLayout.VISIBLE);
				
				profile_amend_flag = true;
			}
		});
		
		mypage_profileset_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_profilesetting_layout.setVisibility(LinearLayout.INVISIBLE);
				mypage_profile_layout.setVisibility(LinearLayout.VISIBLE);

				profile_info[profile_flag].name = mypage_profilename_edittext.getText().toString();
				profile_info[profile_flag].birth = mypage_profilebirth_edittext.getText().toString();
				
				profile_input();
				
				mypage_kidimageset_view.setVisibility(LinearLayout.INVISIBLE);
				
				mypage_profilename_edittext.setText("");
				mypage_profilebirth_edittext.setText("");
				
				profile_amend_flag = false;
			}
		});
		
		profile_kidsex_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == 0)
					profile_info[profile_flag].sex = "남아";
				else
					profile_info[profile_flag].sex = "여아";
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
				
				mypage_smartpush_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_push_image_focus, 200, 200));
				mypage_smartwatch_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_smartwatch_image_up, 200, 200));
			}
		});
		
		mypage_smartwatch_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_smartalarm_layout.setVisibility(LinearLayout.INVISIBLE);
				mypage_smartwatch_layout.setVisibility(LinearLayout.VISIBLE);
				

				mypage_smartpush_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_push_image_up, 200, 200));
				mypage_smartwatch_button.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_smartwatch_image_focus, 200, 200));
			}
		});
		
		mypage_amalarm_layout.setOnClickListener(new LinearLayout.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_alarmset_layout.setVisibility(LinearLayout.VISIBLE);
				
				mypage_amalarm_text.setTextColor(Color.parseColor("#FFFF00"));
				mypage_pmalarm_text.setTextColor(Color.parseColor("#FFFFFF"));
				
				mypage_hoursetting_np.setValue(Integer.valueOf(am_alarm_hour_text.getText().toString()));
				mypage_minutesetting_np.setValue(Integer.valueOf(am_alarm_minute_text.getText().toString()));
				
				mypage_alarm_flag = false;
			}
		});
		
		mypage_pmalarm_layout.setOnClickListener(new LinearLayout.OnClickListener(){
			@Override
			public void onClick(View v) {
				mypage_alarmset_layout.setVisibility(LinearLayout.VISIBLE);

				mypage_amalarm_text.setTextColor(Color.parseColor("#FFFFFF"));
				mypage_pmalarm_text.setTextColor(Color.parseColor("#FFFF00"));
				
				mypage_hoursetting_np.setValue(Integer.valueOf(pm_alarm_hour_text.getText().toString()));
				mypage_minutesetting_np.setValue(Integer.valueOf(pm_alarm_minute_text.getText().toString()));
				
				mypage_alarm_flag = true;
			}
		});
		
		mypage_alarmsetting_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v){
				mypage_alarmset_layout.setVisibility(LinearLayout.INVISIBLE);
				if(!mypage_alarm_flag){
					am_alarm_hour_text.setText(String.valueOf(mypage_hoursetting_np.getValue()));
					am_alarm_minute_text.setText(String.valueOf(mypage_minutesetting_np.getValue()));
				}
				else{
					pm_alarm_hour_text.setText(String.valueOf(mypage_hoursetting_np.getValue()));
					pm_alarm_minute_text.setText(String.valueOf(mypage_minutesetting_np.getValue()));
				}
			}
		});
		
		mypage_smartwatch_onoff_button.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				
				if(adapter == null) {
					Toast.makeText(getActivity().getApplicationContext(), "블루투스를 지원하지 않는 기기입니다", Toast.LENGTH_LONG).show();
				} else {
					if(adapter.getState() == BluetoothAdapter.STATE_TURNING_ON || adapter.getState() == BluetoothAdapter.STATE_ON)     
						{
						Toast.makeText(getActivity().getApplicationContext(), "블루투스를 Off니다", Toast.LENGTH_LONG).show();
						adapter.disable();
						}
					else
						{
						Toast.makeText(getActivity().getApplicationContext(), "블루투스를 On합니다", Toast.LENGTH_LONG).show();	
							if(btService.getDeviceState()) {
								// ��������� ���� ������ ����� ��
								btService.enableBluetooth();
							}
						}
				}		
			
			}
			
		});

		mypage_smallkidimage1_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!profile_amend_flag){
					profile_flag = 0;
					profile_input();
				}
			}
		});
		
		mypage_smallkidimage2_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!profile_amend_flag){
					profile_flag = 1;
					profile_input();
				}
			}
		});
		
		mypage_smallkidimage3_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!profile_amend_flag){
					profile_flag = 2;
					profile_input();
				}
			}
		});
		
		mypage_kidimageset_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				// 사진폴더이동
				Intent intent = new Intent(
                        Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");              // 모든 이미지
                intent.putExtra("crop", "true");        // Crop기능 활성화
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
                intent.putExtra("outputFormat",         // 포맷방식
                        Bitmap.CompressFormat.JPEG.toString());
                
                getActivity().startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
			}
		});
	}

	private void profile_circleimage(Bitmap b){
		Bitmap bitmap = b;
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
	
	private void profile_small_circleimage(int drawable_id, int imageview_id){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable_id);
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		ImageView profile_image = (ImageView)view.findViewById(imageview_id);
		
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
	
	private void profile_input(){
		profile_name.setText(profile_info[profile_flag].name);
		profile_sex.setText(profile_info[profile_flag].sex);
		profile_birth.setText(profile_info[profile_flag].birth);
	}
	
	public void init() {
		
		profile_info = new profileInfo[3];

		for(int i=0;i<3;i++)
			profile_info[i] = new profileInfo("이름을 입력하세요", " - ", "생일을 입력하세요");
		
		profile_name = (TextView)view.findViewById(R.id.profile_name);
		profile_sex = (TextView)view.findViewById(R.id.profile_sex);
		profile_birth = (TextView)view.findViewById(R.id.profile_birth);
		
		profile_input();
		
		mypage_idinput_image = (ImageView)view.findViewById(R.id.mypage_idinput_image);
		
		// mypage profile
		
		mypage_profile_layout = (LinearLayout)view.findViewById(R.id.mypage_profile_layout);
		mypage_profilesetting_layout = (LinearLayout)view.findViewById(R.id.mypage_profilesetting_layout);
		
		mypage_profilesetting_layout.setVisibility(LinearLayout.INVISIBLE);

		mypage_profileamend_button = (ImageView)view.findViewById(R.id.mypage_profileamend_button);
		mypage_profileset_button = (ImageView)view.findViewById(R.id.mypage_profileset_button);

		mypage_smallkidimage1_image = (ImageView)view.findViewById(R.id.mypage_smallkidimage1_image);
		mypage_smallkidimage2_image = (ImageView)view.findViewById(R.id.mypage_smallkidimage2_image);
		mypage_smallkidimage3_image = (ImageView)view.findViewById(R.id.mypage_smallkidimage3_image);
		
		mypage_profilename_edittext = (EditText)view.findViewById(R.id.mypage_profilename_edittext);
		mypage_profilebirth_edittext = (EditText)view.findViewById(R.id.mypage_profilebirth_edittext);
		
		profile_kidsex_radiogroup = (RadioGroup)view.findViewById(R.id.profile_kidsex_radiogroup);

		mypage_kidimage_view = (LinearLayout)view.findViewById(R.id.mypage_kidimage_view);
		mypage_kidimageset_view = (LinearLayout)view.findViewById(R.id.mypage_kidimageset_view);
		
		mypage_kidimageset_view.setVisibility(LinearLayout.INVISIBLE);

		mypage_kidimage_image = (ImageView)view.findViewById(R.id.mypage_kidimage_image);
		mypage_kidimageset_image = (ImageView)view.findViewById(R.id.mypage_kidimageset_image);
		
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

		am_alarm_hour_text = (TextView)view.findViewById(R.id.am_alarm_hour_text);
		am_alarm_minute_text = (TextView)view.findViewById(R.id.am_alarm_minute_text);
		pm_alarm_hour_text = (TextView)view.findViewById(R.id.pm_alarm_hour_text);
		pm_alarm_minute_text = (TextView)view.findViewById(R.id.pm_alarm_minute_text);
		

		smartwatch_wearning_image = (ImageView)view.findViewById(R.id.smartwatch_wearning_image);
	}
	
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		
	}

	public void setNumberpicker(){
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //Fragment fragment = getFragmentManager().findFragmentById(2);
        //fragment.onActivityResult(requestCode, resultCode, data);
		
        Log.d(TAG, "onActivityResult " + requestCode);
        
        switch (requestCode) {
        
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
            	btService.getDeviceInfo(data);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
            	// Next Step
            	btService.scanDevice();
            } else {

                Log.d(TAG, "Bluetooth is not enabled");
            }
            break;
            
        case REQUEST_IMAGE_ALBUM:
        	if (resultCode == Activity.RESULT_OK) {
        		String filePath = Environment.getExternalStorageDirectory()
                        + "/temp.jpg";

                System.out.println("path" + filePath); // logCat으로 경로확인.

                Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                
                profile_circleimage(selectedImage);
            }
        	      
        	break;
        }
	}
	
	private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }
	
	private File getTempFile() {
        if (isSDCARDMOUNTED()) {
            File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
                    "temp.jpg");
            try {
                f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
            } catch (IOException e) {
            }
 
            return f;
        } else
            return null;
    }
	
	private boolean isSDCARDMOUNTED() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
 
        return false;
    }
	
}
