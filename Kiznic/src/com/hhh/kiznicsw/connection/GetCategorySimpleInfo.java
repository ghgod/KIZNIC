package com.hhh.kiznicsw.connection;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hhh.kiznicsw.R;
import com.hhh.kiznicsw.card.CardAdapter;
import com.hhh.kiznicsw.card.MainRecommendCarditemCard;
import com.hhh.kiznicsw.dataclass.PicnicSimpleInfo;
import com.hhh.kiznicsw.util.LocationHelper;
import com.hhh.kiznicsw.util.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GetCategorySimpleInfo extends AsyncTask<String, Integer, String>{
	
	Context context;
	LocationHelper location;
	ProgressBar progressbar;
	ListView search_listview;
	String selectedRegion;
	int selectedGenre;
	int selectedPeriod;
	String latitude;
	String longitude;
	int pagination_no;
	String search_keyword;
	CardAdapter searchListAdapter;
	
	public GetCategorySimpleInfo(Context context, CardAdapter searchListAdapter, LocationHelper location, String selectedRegion, int selectedGenre, int selectedPeriod, ListView searchListView, ProgressBar progressbar,int pagination_no, String search_keyword ) {
		this.context = context;
		this.searchListAdapter = searchListAdapter;
		this.location = location;
		this.selectedRegion = selectedRegion;
		this.selectedGenre = selectedGenre;
		this.selectedPeriod = selectedPeriod;
		this.search_listview = searchListView;
		this.progressbar = progressbar;
		this.pagination_no = pagination_no;
		this.search_keyword = search_keyword;
		
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		//location = new LocationHelper(context);
		//location.run();
		
		
		if(selectedRegion == null) {
			String[] region = location.getAddress();
			selectedRegion = region[0];
			//selectedRegion = region[0] + " " + region[1];
		}
		
		if(search_keyword.equals("")) {
			search_keyword = null;
		}
		
		
		latitude = String.valueOf(location.getLat());
		longitude = String.valueOf(location.getLng());
		
		progressbar.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		JSONArray sendCategoryInfo = new JSONArray();
		JSONObject categoryInfo = new JSONObject();
		
		try {
			categoryInfo.put("play_address", selectedRegion);
			categoryInfo.put("date_condition", String.valueOf(selectedPeriod));
			categoryInfo.put("play_type", String.valueOf(selectedGenre));
			categoryInfo.put("play_place_lat", latitude);
			categoryInfo.put("play_place_lng", longitude);
			categoryInfo.put("pagination_no", String.valueOf(pagination_no));
			categoryInfo.put("search_keyword", search_keyword);
			
			Log.e("play_address", selectedRegion);
			Log.e("date_condition", String.valueOf(selectedPeriod));
			Log.e("play_type", String.valueOf(selectedGenre));
			Log.e("play_place_lat", latitude);
			Log.e("play_place_lng", longitude);
			Log.e("pagination_no", String.valueOf(pagination_no));
		//	Log.e("search_keyword", search_keyword);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendCategoryInfo.put(categoryInfo);
		String result = util.getJSONHttp(context, "play_search_list", "play_search_info", sendCategoryInfo);
		
		return result;
	
	}
	
	protected void onPostExecute(String result) { 
		
		Log.d("result", result);
		
		Util util = new Util();
		ArrayList<PicnicSimpleInfo> simpleInfo = new ArrayList<PicnicSimpleInfo>();
		
		try {
			simpleInfo = util.parsePicnicSimpleInfoJSON(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.e("simpleInfo.size", String.valueOf(simpleInfo.size()));
		if(simpleInfo.size()==0) {
			//Toast.makeText(context, "검색 결과가 없습니다~", Toast.LENGTH_SHORT).show();
		}
		
		//CardAdapter searchListAdapter = new CardAdapter(context);
		
		for(int i=0; i<simpleInfo.size(); i++) {
			searchListAdapter.addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "공연/전시", context, 0, simpleInfo.get(i)));

		}
		
		searchListAdapter.notifyDataSetChanged();
		
		search_listview.setAdapter(searchListAdapter);
		
		progressbar.setVisibility(View.GONE);
		
		
		SharedPreferences pref = context.getSharedPreferences("page_no", 0);
        Editor editor = pref.edit();
        editor.putString("pagination_no", String.valueOf(pagination_no + 15));
        editor.putString("play_type", String.valueOf(selectedGenre));
        editor.putString("date_condition", String.valueOf(selectedPeriod));
        editor.putString("search_keyword", search_keyword);
        editor.putString("simpleInfo.size", String.valueOf(simpleInfo.size()));
        editor.commit();
		
		
		
				
			
	}

}
