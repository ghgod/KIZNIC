package com.hhh.kiznic.connection;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hhh.kiznic.R;
import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCarditemCard;
import com.hhh.kiznic.dataclass.PicnicSimpleInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class GetCategorySimpleInfo extends AsyncTask<String, Integer, String>{
	
	Context context;
	LocationHelper location;
	ListView search_listview;
	String selectedRegion;
	int selectedGenre;
	int selectedPeriod;
	
	public GetCategorySimpleInfo(Context context, LocationHelper location, String selectedRegion, int selectedGenre, int selectedPeriod, ListView searchListView ) {
		this.context = context;
		this.location = location;
		this.selectedRegion = selectedRegion;
		this.selectedGenre = selectedGenre;
		this.selectedPeriod = selectedPeriod;
		this.search_listview = searchListView;
		Log.d("getCategory 생성자", "getCategory 생성자");
		
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		if(selectedRegion == null) {
			String[] region = location.getAddress();
			selectedRegion = region[0] + " " + region[1];
		}
		
		Log.d("preexecute", "preexecute 실행");
				
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		JSONArray sendCategoryInfo = new JSONArray();
		JSONObject categoryInfo = new JSONObject();
		
		try {
			categoryInfo.put("play_address", selectedRegion);
			categoryInfo.put("play_type", String.valueOf(selectedGenre));
			categoryInfo.put("play_limit_time", String.valueOf(selectedPeriod));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendCategoryInfo.put(categoryInfo);
		Log.d("getCategory까지는 돼", "getCategory까지는 돼");
		
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
		
		CardAdapter searchListAdapter = new CardAdapter(context);
		
		for(int i=0; i<simpleInfo.size(); i++) {
			searchListAdapter.addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "공연/전시", context, 0, simpleInfo.get(i)));

		}
		
		search_listview.setAdapter(searchListAdapter);
				
			
	}

}
