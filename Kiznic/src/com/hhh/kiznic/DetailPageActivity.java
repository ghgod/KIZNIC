package com.hhh.kiznic;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.DetailListCard;
import com.hhh.kiznic.card.DetailnevigationlistCard;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class DetailPageActivity extends NMapActivity implements OnClickListener, OnTouchListener, OnMapStateChangeListener{

	private static ListView detailInfoListView, detailNevigationInfoListView;
	private CardAdapter deatilInfoCardAdapter, detailNevigationInfoAdapter;
	
	private static ImageView detail_phone_image;
	private static ImageView detail_link_image;
	private static ImageView detail_bookmark_image;
	private static ImageView detail_sharing_image;
	
	private static Button detail_moreinfo_button;
	
	private static TextView detail_infotext_text;
	
	private int detailcardCount = -1, detailnevigationcardCount = -1;
	private boolean infotextview_flag;
	
	private LinearLayout mapViewContainer;
	private NMapController nmapController;
	private NMapView nmapView;
	private NMapViewerResourceProvider nmapViewerResourceProvider;
	private NMapOverlayManager nmapOverlayManager;
	
	private static final String APIKey="be984cc3917c237a096c3a0a911743b7";
	
	private static ScrollView detail_scrollview;
	
	static Toast t;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailpage);
	
	    init();
	    
	    setNMap();
	    
	    clicklistener();
	}

	private void setNMap() {
		mapViewContainer = (LinearLayout)findViewById(R.id.mapViewContainer);
		nmapView = new NMapView(this);
		nmapViewerResourceProvider = new NMapViewerResourceProvider(this);
		nmapOverlayManager = new NMapOverlayManager(this, nmapView, nmapViewerResourceProvider);
		
		int markerID = NMapPOIflagType.PIN;
		
		NMapPOIdata poiData = new NMapPOIdata(2, nmapViewerResourceProvider);
		poiData.beginPOIdata(2);
		poiData.addPOIitem(127.0630205, 37.5091300, "위치1", markerID, 0);
		poiData.endPOIdata();
		
		NMapPOIdataOverlay poiDataOverlay = nmapOverlayManager.createPOIdataOverlay(poiData, null);
		
		poiDataOverlay.showAllPOIdata(0);
		
		nmapController = nmapView.getMapController();
		nmapView.setApiKey(APIKey);
		mapViewContainer.addView(nmapView);
		nmapView.setClickable(true);
		//nmapView.setBuiltInZoomControls(true, null);
		nmapView.setOnMapStateChangeListener(this);
	}
	
	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(getWindow().getDecorView());
        deatilInfoCardAdapter = null;
		detailNevigationInfoAdapter = null;
		System.gc();
		
		super.onDestroy();
	}
	
	private void init() {
		detailInfoListView = (ListView)findViewById(R.id.detail_list_view);
		detailNevigationInfoListView = (ListView)findViewById(R.id.detail_list2_view);
		deatilInfoCardAdapter = new CardAdapter(getApplicationContext());
		detailNevigationInfoAdapter = new CardAdapter(getApplicationContext());

		detail_phone_image = (ImageView)findViewById(R.id.detail_phone_image);
		detail_link_image = (ImageView)findViewById(R.id.detail_link_image);
		detail_bookmark_image = (ImageView)findViewById(R.id.detail_bookmark_image);
		detail_sharing_image = (ImageView)findViewById(R.id.detail_sharing_image);
		
		detail_moreinfo_button = (Button)findViewById(R.id.detail_moreinfo_button);
		
		detail_infotext_text = (TextView)findViewById(R.id.detail_infotext_text);
		
		infotextview_flag = false;
		
		detail_scrollview = (ScrollView)findViewById(R.id.detail_scrollview);
		
		listsetting();
	}

	private void clicklistener() {
		detail_phone_image.setOnClickListener(this);
		detail_link_image.setOnClickListener(this);
		detail_bookmark_image.setOnClickListener(this);
		detail_sharing_image.setOnClickListener(this);
		detail_moreinfo_button.setOnClickListener(this);
		
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
		
		case R.id.detail_phone_image:
			t = Toast.makeText(getBaseContext(), "전화", Toast.LENGTH_LONG);
            t.show();
			break;
		case R.id.detail_link_image:
			t = Toast.makeText(getBaseContext(), "링크", Toast.LENGTH_LONG);
            t.show();
			break;
		case R.id.detail_bookmark_image:
			t = Toast.makeText(getBaseContext(), "북마크 등록", Toast.LENGTH_LONG);
			t.show();
			break;
		case R.id.detail_sharing_image:
			t = Toast.makeText(getBaseContext(), "연동", Toast.LENGTH_LONG);
			t.show();
			break;
		case R.id.detail_moreinfo_button:
			if(infotextview_flag){
				detail_infotext_text.setText("배고픈 애벌레는 먹이를 찾으러 이곳 저곳을 돌아다니기 시작한다. 이러쿵 저러쿵 왔다가갔다 으흐흐흐 모라고 써야할까 배고픈 애벌레는 이곳저곳 돌아다니다 새밥이된다.동심파괴 젠장.. 몇자까지가 적당할까, 난 여기에 조금 더 써볼까해, 음... 아무래도 5줄 정도면...");
				infotextview_flag = false;
				detail_moreinfo_button.setText("더보기");
			}
			else{
				detail_infotext_text.setText("배고픈 애벌레는 먹이를 찾으러 이곳 저곳을 돌아다니기 시작한다. 이러쿵 저러쿵 왔다가갔다 으흐흐흐 모라고 써야할까 배고픈 애벌레는 이곳저곳 돌아다니다 새밥이된다.동심파괴 젠장.. 몇자까지가 적당할까, 난 여기에 조금 더 써볼까해, 음... 아무래도 5줄 정도면 적당하겠지?\n배고픈 애벌레는 먹이를 찾으러 이곳 저곳을 돌아다니기 시작한다. 이러쿵 저러쿵 왔다가갔다 으흐흐흐 모라고 써야할까 배고픈 애벌레는 이곳저곳 돌아다니다 새밥이된다.동심파괴 젠장.. 몇자까지가 적당할까, 난 여기에 조금 더 써볼까해, 음... 아무래도 5줄 정도면 적당하겠지?");
				infotextview_flag = true;
				detail_moreinfo_button.setText("줄이기");
			}
			break;
		}
	}
	
	private void listsetting(){
		for(int i=0;i<6;i++)
			deatilInfoCardAdapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card,"Detail Info Card", getApplicationContext(), detailcardCount++));
	
		detailInfoListView.setAdapter(deatilInfoCardAdapter);
		
		LinearLayout detailInfoListLayout = (LinearLayout)findViewById(R.id.detail_listview_layout);
		LayoutParams detailInfoParams = (LayoutParams) detailInfoListLayout.getLayoutParams();
		
		int deatilInfoCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70 * (detailcardCount + 1), getResources().getDisplayMetrics());
		
		detailInfoParams.height = deatilInfoCardheight;
		
		for(int i=0;i<6;i++){
			View infoview = (View)detailInfoListView.getAdapter().getView(i, null, detailInfoListView).findViewById(R.id.detailpage_infobackground_view);
			if(i%2 == 0)
				infoview.setBackgroundColor(Color.argb(255, 249, 249, 249));
			else
				infoview.setBackgroundColor(Color.argb(255, 255, 255, 255));
		}
		//
		
		for(int i=0;i<4;i++)
			detailNevigationInfoAdapter.addItem(new DetailnevigationlistCard(R.layout.detailpage_nevigation_list_card, "Detail Nevigation Info Card", getApplicationContext(), detailnevigationcardCount++));
	
		detailNevigationInfoListView.setAdapter(detailNevigationInfoAdapter);
		
		LinearLayout detailNevigationListLayout = (LinearLayout)findViewById(R.id.detail_nevigation_layout);
		LayoutParams detailNevigationParams = (LayoutParams) detailNevigationListLayout.getLayoutParams();
		
		int deatilNevigationCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * (detailnevigationcardCount + 1), getResources().getDisplayMetrics());
		
		detailNevigationParams.height = deatilNevigationCardheight;
		
		for(int i=0;i<4;i++){
			View nevigationview = (View)detailNevigationInfoListView.getAdapter().getView(i, null, detailNevigationInfoListView).findViewById(R.id.detailpage_nevigationbackground_view);
			if(i%2 == 0)
				nevigationview.setBackgroundColor(Color.argb(255, 249, 249, 249));
			else
				nevigationview.setBackgroundColor(Color.argb(255, 255, 255, 255));
		}
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
