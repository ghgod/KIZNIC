package com.hhh.kiznic.connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hhh.kiznic.DetailPageActivity;
import com.hhh.kiznic.NMapPOIflagType;
import com.hhh.kiznic.NMapViewerResourceProvider;
import com.hhh.kiznic.R;
import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.DetailListCard;
import com.hhh.kiznic.card.DetailnevigationlistCard;
import com.hhh.kiznic.databasemanager.KiznicSharedPreferences;
import com.hhh.kiznic.dataclass.PicnicDetailInfo;
import com.hhh.kiznic.util.Util;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class GetPicnicDetailInfo extends AsyncTask<String, Integer, String>{

	Context context;
	public double latitude;
	public double longitude;
	TextView detail_main_title;
	ImageView detail_main_poster;
	TextView detail_info_text;
	LinearLayout detail_mainposter_layout;
	
	
	ListView detail_list_view;
	ListView detailNevigationInfoListView;
	int play_no;
	

	
	public GetPicnicDetailInfo(Context context, int play_no, TextView detail_main_title, 
			ImageView detail_main_poster, TextView detail_info_text, ListView detail_list_view
			, ListView detailNevigationInfoListView, LinearLayout detail_mainposter_layout) {
		
		this.context = context;
		this.play_no = play_no;
		this.detail_main_title = detail_main_title;
		this.detail_main_poster = detail_main_poster;
		this.detail_info_text = detail_info_text;
		this.detail_list_view = detail_list_view;
		this.detailNevigationInfoListView = detailNevigationInfoListView;
		
		
		
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
		
		Log.d("detail_result", result);
		
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
		detail_main_title.setText(detailInfo.getPlay_title() +":"+ detailInfo.getPlay_subtitle());
		

		aq.id(R.id.detail_mainposter_image).image(detailInfo.getPlay_photo());
		
		detail_info_text.setText(detailInfo.getPlay_description());
		
		int count = 0;
		
		// 일정
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "일정", context, count++, detailInfo.getPlay_start_date() + " ~ " + detailInfo.getPlay_end_date()));
		// 장소
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "장소", context, count++, detailInfo.getPlay_place()));
		// 가격
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "가격", context, count++, detailInfo.getPlay_price()));
		// 연령
		detail_info_adapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card, "연령", context, count++, detailInfo.getPlay_ages()));
		//detail_info_adapter.addItem()
		
		detail_list_view.setAdapter(detail_info_adapter);
		
		int traffic_count = 0;
		
		detailNevigationInfoAdapter.addItem(new DetailnevigationlistCard(R.layout.detailpage_nevigation_list_card, "Detail Nevigation Info Card", context, traffic_count++, detailInfo.getPlay_traffic()));
		
		detailNevigationInfoListView.setAdapter(detailNevigationInfoAdapter);
	}
	
	
	
	



}
