package com.hhh.kiznicsw.connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hhh.kiznicsw.R;
import com.hhh.kiznicsw.ImageDecoder;
import com.hhh.kiznicsw.NMapPOIflagType;
import com.hhh.kiznicsw.NMapViewerResourceProvider;
import com.hhh.kiznicsw.card.CardAdapter;
import com.hhh.kiznicsw.card.DetailListCard;
import com.hhh.kiznicsw.card.DetailnevigationlistCard;
import com.hhh.kiznicsw.dataclass.PicnicDetailInfo;
import com.hhh.kiznicsw.util.Util;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class GetPicnicDetailInfo extends AsyncTask<String, Integer, String> implements OnMapStateChangeListener{

	Context context;
	public double latitude;
	public double longitude;
	TextView detail_main_title;
	ImageView detail_main_poster;
	TextView detail_info_text;
	LinearLayout detail_mainposter_layout;
	
	NMapView nmapView;
	NMapViewerResourceProvider nmapViewerResourceProvider;
	NMapOverlayManager nmapOverlayManager;
	LinearLayout mapViewContainer;
	
	
	Activity activity;
	ListView detail_list_view;
	ListView detailNevigationInfoListView;
	int play_no;
	
	String title;
	String subtitle;
	String description;
	String startDate;
	String endDate;
	String place;
	String price;
	String ages;
	String traffic;
	String lat;
	String lng;

	

	
	public GetPicnicDetailInfo(Activity activity, Context context, int play_no, TextView detail_main_title, 
			ImageView detail_main_poster, TextView detail_info_text, ListView detail_list_view
			, ListView detailNevigationInfoListView, LinearLayout detail_mainposter_layout,
			NMapView nmapView, NMapViewerResourceProvider nmapViewerResourceProvider, NMapOverlayManager nmapOverlayManager, LinearLayout mapViewContainer) {
		this.activity = activity;
		this.context = context;
		this.play_no = play_no;
		this.detail_main_title = detail_main_title;
		this.detail_main_poster = detail_main_poster;
		this.detail_info_text = detail_info_text;
		this.detail_list_view = detail_list_view;
		this.detailNevigationInfoListView = detailNevigationInfoListView;
		
		this.nmapView = nmapView;
		this.nmapViewerResourceProvider = nmapViewerResourceProvider;
		this.nmapOverlayManager = nmapOverlayManager;
		this.mapViewContainer = mapViewContainer;
		
		
		
	}
	

	
	protected void onPreExecute() {
		super.onPreExecute();
		
		
				
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		JSONArray sendforDetailInfo = new JSONArray();
		JSONObject sendInfoforDetail = new JSONObject();
		
		try {
			sendInfoforDetail.put("play_no", String.valueOf(play_no));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendforDetailInfo.put(sendInfoforDetail);
		
		String result = util.getJSONHttp(context, "recommend_play_info", "play_info", sendforDetailInfo);
		
		
		return result;
	}
	
	protected void onPostExecute(String result) { 
		
		//Log.d("detail_result", result);
		
		boolean playThumbnail = false;
		Util util = new Util();
		PicnicDetailInfo detailInfo = null;
		CardAdapter detail_info_adapter = new CardAdapter(context);
		CardAdapter detailNevigationInfoAdapter = new CardAdapter(context);
		
		
			
		try {
			detailInfo = util.parsePicnicDetailInfoJSON(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		title = detailInfo.getPlay_title();
		//subtitle = detailInfo.getPlay_subtitle();
		description = detailInfo.getPlay_description();
		startDate = detailInfo.getPlay_start_date();
		endDate = detailInfo.getPlay_end_date();
		place = detailInfo.getPlay_place();
		price = detailInfo.getPlay_price();
		ages = detailInfo.getPlay_ages();
		traffic = detailInfo.getPlay_traffic();
		
		if(detailInfo.getPlay_photo().equals("kiznic")) {
			playThumbnail = true;
		}
		
		Log.e("title", title);
		//Log.e("subtitle", subtitle);
		Log.e("description", description);
		Log.e("startDate", startDate);
		Log.e("endDate", endDate);
		Log.e("place", place);
		Log.e("price", price);
		Log.e("ages", ages);
		Log.e("traffic", traffic);
		/*
		if(detailInfo.getPlay_title().equals("null")){
			title = "kiznic";
		} else {
			title = detailInfo.getPlay_title();
		}
		
		if(detailInfo.getPlay_subtitle().equals("null")){
			subtitle = "kiznic";
		} else {
			subtitle = detailInfo.getPlay_subtitle();
		}
		
		if(detailInfo.getPlay_description().equals("null")){
			description = "kiznic";
		} else {
			description = detailInfo.getPlay_description();
		}
		
		if(detailInfo.getPlay_start_date().equals("null")){
			startDate = "kiznic";
		} else {
			startDate = detailInfo.getPlay_start_date();
		}
		
		if(detailInfo.getPlay_end_date().equals("null")) {
			endDate = "kiznic";
		} else {
			endDate = detailInfo.getPlay_end_date();
		}
		
		if(detailInfo.getPlay_place().equals("null")) {
			place = "kiznic";
		} else {
			place = detailInfo.getPlay_place();
		}
		
		if(detailInfo.getPlay_price().equals("null")) {
			price = "kiznic";
		} else {
			price = detailInfo.getPlay_price();
		}
		
		if(detailInfo.getPlay_ages().equals("null")) {
			ages = "kiznic";
		} else {
			ages = detailInfo.getPlay_ages();
		}
		
		if(detailInfo.getPlay_traffic().equals("null")) {
			traffic = "kiznic";
		} else {
			traffic = detailInfo.getPlay_traffic();
		}
		*/
		
		
		Log.e("latitude_가져오기", detailInfo.getPlay_place_latitude());
	    Log.e("longitude_가져오 기", detailInfo.getPlay_place_longitude());
		
		SharedPreferences pref = context.getSharedPreferences("place_geo", 0);
        Editor editor = pref.edit();
        
        
        editor.putString("latitude", detailInfo.getPlay_place_latitude());
        editor.putString("longitude", detailInfo.getPlay_place_longitude());
        editor.putString("placeName", detailInfo.getPlay_place());
        editor.putString("url", detailInfo.getPlay_link());
        editor.putString("title", detailInfo.getPlay_title());
        editor.putString("contact", detailInfo.getPlay_contact());
        editor.commit();
				
	
        AQuery aq = new AQuery(detail_main_poster.getRootView());
		detail_main_title.setText(title);
		
		if(playThumbnail) {
			detail_main_poster.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(context.getResources(), R.drawable.contents_null, 200, 200));
		} else {
			aq.id(R.id.detail_mainposter_image).image(detailInfo.getPlay_photo());
		}
		
		
		
		detail_info_text.setText(description);
		
		int count = 0;
		
		// 일정
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "일정", context, count++, startDate + " ~ " + endDate));
		// 장소
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "장소", context, count++, place));
		// 가격
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "가격", context, count++, price));
		// 연령
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "연령", context, count++, ages));
		//detail_info_adapter.addItem()
		
		detail_list_view.setAdapter(detail_info_adapter);
		
		int traffic_count = 0;
		
		//DetailPageActivity de = new DetailPageActivity();
		//de.setNMap(Double.parseDouble(detailInfo.getPlay_place_latitude()), Double.parseDouble(detailInfo.getPlay_place_longitude()), detailInfo.getPlay_place());
		 setNMap(nmapView, nmapViewerResourceProvider, nmapOverlayManager, mapViewContainer, Double.parseDouble(detailInfo.getPlay_place_latitude()), Double.parseDouble(detailInfo.getPlay_place_longitude()), detailInfo.getPlay_place());
		
		detailNevigationInfoAdapter.addItem(new DetailnevigationlistCard(R.layout.detailpage_nevigation_list_card, "Detail Nevigation Info Card", context, traffic_count++, traffic));
		
		detailNevigationInfoListView.setAdapter(detailNevigationInfoAdapter);
	}
	
	
	public void setNMap(NMapView nmapView, NMapViewerResourceProvider nmapViewerResourceProvider, NMapOverlayManager nmapOverlayManager, LinearLayout mapViewContainer, double latitude, double longitude, String placeName) {
		
		Log.e("setNMa 시작","start!");
		
		String APIKey="be984cc3917c237a096c3a0a911743b7";
		
		int markerID = NMapPOIflagType.PIN;
		
		NMapPOIdata poiData = new NMapPOIdata(2, nmapViewerResourceProvider);
		poiData.beginPOIdata(2);
		poiData.addPOIitem(longitude, latitude, placeName, markerID, 0);
		poiData.endPOIdata();
		
		NMapPOIdataOverlay poiDataOverlay = nmapOverlayManager.createPOIdataOverlay(poiData, null);
		
		poiDataOverlay.showAllPOIdata(0);
		
		nmapView.setApiKey(APIKey);
		mapViewContainer.addView(nmapView);
		nmapView.setClickable(true);
		nmapView.setBuiltInAppControl(true);
		//nmapView.setBuiltInZoomControls(true, null);
		nmapView.setOnMapStateChangeListener(this);
		Log.e("setNMap 끝","끝!");
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
	public void onMapInitHandler(NMapView arg0, NMapError arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onZoomLevelChange(NMapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	



}
