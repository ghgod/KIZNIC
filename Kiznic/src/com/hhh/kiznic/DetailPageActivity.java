package com.hhh.kiznic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.hhh.kiznic.connection.GetPicnicDetailInfo;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class DetailPageActivity extends NMapActivity implements OnClickListener, OnMapStateChangeListener{

	private static ListView detailInfoListView, detailNevigationInfoListView;
	
	private TextView detail_main_title;
	private ImageView detail_main_poster;
	private static ImageView detail_phone_image;
	private static ImageView detail_link_image;
	private static ImageView detail_bookmark_image;
	private static ImageView detail_sharing_image;
	
	//private static Button detail_moreinfo_button;
	
	private static TextView detail_infotext_text;
	
	
	
	private boolean infotextview_flag;
	
	private LinearLayout detail_mainposter_layout;
	private LinearLayout mapViewContainer;
	private NMapController nmapController;
	private NMapView nmapView;
	private NMapViewerResourceProvider nmapViewerResourceProvider;
	private NMapOverlayManager nmapOverlayManager;
	private NMapPOIdata poiData;
	private NMapPOIdataOverlay poiDataOverlay;
	
	private SharedPreferences pref;	
	private static final String APIKey="be984cc3917c237a096c3a0a911743b7";
	
	private static ScrollView detail_scrollview;
	
	private static double latitude;
	private static double longitude;
	
	private GetPicnicDetailInfo info;
	
	static Toast t;
	private String play_no;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailpage);
	
	   init();
	   
	  info =  new GetPicnicDetailInfo(getApplicationContext(), Integer.parseInt(play_no), 
	    		detail_main_title, detail_main_poster, detail_infotext_text, detailInfoListView,detailNevigationInfoListView, detail_mainposter_layout, nmapView, nmapViewerResourceProvider, nmapOverlayManager, mapViewContainer);
	  
	   info.execute();
	   
	  pref = getSharedPreferences("place_geo",  0);
      //String dftValue = "1";
       
      // Log.e("latitude_보여주기", pref.getString("latitude", ""));
       //Log.e("longitude_보여주기", pref.getString("latitude", ""));
	  // setNMap(Double.parseDouble(pref.getString("latitude",  dftValue)), Double.parseDouble(pref.getString("longitude",  dftValue)), pref.getString("placeName", dftValue));
       
       
	   clicklistener();
	   
	   set_image();
	   
	}

	
	
	private void set_image() {
		detail_phone_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_phone_up, 200, 200));
		detail_link_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_link_up, 200, 200));
		//detail_bookmark_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_bookmark_up, 200, 200));
		detail_sharing_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_share_up, 200, 200));
	}



	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(getWindow().getDecorView());
		System.gc();
		
		super.onDestroy();
	}
	
	private void init() {
		
		detail_mainposter_layout = (LinearLayout)findViewById(R.id.detail_mainposter_image_layout);
		detail_main_title = (TextView)findViewById(R.id.detail_main_title);
		detail_main_poster = (ImageView)findViewById(R.id.detail_mainposter_image);
		
		detailInfoListView = (ListView)findViewById(R.id.detail_list_view);
		detailNevigationInfoListView = (ListView)findViewById(R.id.detail_list2_view);
		detail_phone_image = (ImageView)findViewById(R.id.detail_phone_image);
		detail_link_image = (ImageView)findViewById(R.id.detail_link_image);
		//detail_bookmark_image = (ImageView)findViewById(R.id.detail_bookmark_image);
		detail_sharing_image = (ImageView)findViewById(R.id.detail_sharing_image);
		
		//detail_moreinfo_button = (Button)findViewById(R.id.detail_moreinfo_button);
		
		detail_infotext_text = (TextView)findViewById(R.id.detail_infotext_text);
		
		infotextview_flag = false;
		
		detail_scrollview = (ScrollView)findViewById(R.id.detail_scrollview);
		Intent intent = getIntent();
		play_no = intent.getExtras().getString("play_no");
		
		nmapView = new NMapView(this);
		nmapViewerResourceProvider = new NMapViewerResourceProvider(this);
		nmapOverlayManager = new NMapOverlayManager(this, nmapView, nmapViewerResourceProvider);
		mapViewContainer = (LinearLayout)findViewById(R.id.mapViewContainer);

		
		
		listsetting();
	}
	
	

	
	
	public void setNMap( double latitude, double longitude, String placeName) {
		//nmapView = new NMapView(this);
		//nmapViewerResourceProvider = new NMapViewerResourceProvider(this);
		//nmapOverlayManager = new NMapOverlayManager(this, nmapView, nmapViewerResourceProvider);
		
		int markerID = NMapPOIflagType.PIN;
		
		NMapPOIdata poiData = new NMapPOIdata(2, nmapViewerResourceProvider);
		poiData.beginPOIdata(2);
		poiData.addPOIitem(longitude, latitude, placeName, markerID, 0);
		poiData.endPOIdata();
		
		NMapPOIdataOverlay poiDataOverlay = nmapOverlayManager.createPOIdataOverlay(poiData, null);
		
		poiDataOverlay.showAllPOIdata(0);
		
		nmapController = nmapView.getMapController();
		nmapView.setApiKey(APIKey);
		mapViewContainer.addView(nmapView);
		nmapView.setClickable(true);
		nmapView.setBuiltInAppControl(true);
		//nmapView.setBuiltInZoomControls(true, null);
		nmapView.setOnMapStateChangeListener(this);
	}

	private void clicklistener() {
		//detail_phone_image.setOnClickListener(this);
		detail_phone_image.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					detail_phone_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_phone_down, 200, 200));
				}
				
				if(event.getAction() == MotionEvent.ACTION_UP){
					detail_phone_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_phone_up, 200, 200));
					
					Uri number = Uri.parse("tel:" + pref.getString("contact", "tel:0000000"));
					Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
					startActivity(callIntent);
				}
				
				return true;
			}
		});
		
		detail_link_image.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					detail_link_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_link_down, 200, 200));
				}
				
				if(event.getAction() == MotionEvent.ACTION_UP){
					detail_link_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_link_up, 200, 200));
					
					Intent intent = new Intent(Intent.ACTION_VIEW);
					Log.e("title", pref.getString("title", ""));
					Uri u = Uri.parse("http://search.naver.com/search.naver?where=nexearch&query="+pref.getString("title", "")+ "&sm=top_hty");
					intent.setData(u);
					startActivity(intent);
				}
				
				return true;
			}
		});
		/*
		detail_bookmark_image.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					detail_bookmark_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_bookmark_down, 200, 200));
				}
				
				if(event.getAction() == MotionEvent.ACTION_UP){
					detail_bookmark_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_bookmark_up, 200, 200));
					
					Toast.makeText(getApplicationContext(), "준비중 입니다!", Toast.LENGTH_LONG).show();
				}
				
				return true;
			}
		});
		*/
		detail_sharing_image.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					detail_sharing_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_share_down, 200, 200));
				}
				
				if(event.getAction() == MotionEvent.ACTION_UP){
					detail_sharing_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.detail_share_up, 200, 200));
					
					LinearLayout capture = (LinearLayout)findViewById(R.id.detailpage_captureLayout);
					capture.buildDrawingCache();
					
					Bitmap captureView = capture.getDrawingCache();
					
					FileOutputStream fos;
					try {
					
						fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/capture.jpeg");
						captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
						
						Uri mSaveImageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString()+"/capture.jpeg")); //file의 경로를 uri로 변경합니다.
						
						Intent shareintent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
						shareintent.setType("image/jpg"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
						shareintent.putExtra(Intent.EXTRA_STREAM, mSaveImageUri); //사진의 Uri를 가지고 옵니다.
						startActivity(Intent.createChooser(shareintent, "공유")); //Activity를 이용하여 호출 합니다.
								
					
					} catch (FileNotFoundException e) {
					
						e.printStackTrace();
					
					}
				
					Toast.makeText(getApplicationContext(), "공유 성공", Toast.LENGTH_LONG).show();
				}
				
				return true;
			}
		});
		
		//detail_moreinfo_button.setOnClickListener(this);
		
		nmapView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					detail_scrollview.requestDisallowInterceptTouchEvent(true);
				}
				return false;
			}
		});
		
	}
	
	@Override
	public void onClick(View v){

		switch(v.getId()){
		
		/*
		case R.id.detail_moreinfo_button:
			if(infotextview_flag){
				infotextview_flag = false;
				detail_moreinfo_button.setText("더보기");
			}
			else{
				infotextview_flag = true;
				detail_moreinfo_button.setText("줄이기");
			}
			break;
			*/
		}
	}
	
	private void listsetting(){
		
			
		LinearLayout detailInfoListLayout = (LinearLayout)findViewById(R.id.detail_listview_layout);
		LayoutParams detailInfoParams = (LayoutParams) detailInfoListLayout.getLayoutParams();
		
		int deatilInfoCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70 * (3 + 1), getResources().getDisplayMetrics());
		
		detailInfoParams.height = deatilInfoCardheight;
	
		
		LinearLayout detailNevigationListLayout = (LinearLayout)findViewById(R.id.detail_nevigation_layout);
		LayoutParams detailNevigationParams = (LayoutParams) detailNevigationListLayout.getLayoutParams();
		
		int deatilNevigationCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * (0 + 1), getResources().getDisplayMetrics());
		
		detailNevigationParams.height = deatilNevigationCardheight;
		

	}

	
	@Override
	public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapCenterChangeFine(NMapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapInitHandler(NMapView mapview, NMapError errorinfo) {
		if(errorinfo == null){
			//nmapController.setMapCenter(new NGeoPoint(126.978371,37.5666091), 11);
		}
		else{
			//
		}
		
	}

	@Override
	public void onZoomLevelChange(NMapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
