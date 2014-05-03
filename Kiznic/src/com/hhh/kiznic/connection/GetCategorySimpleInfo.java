package com.hhh.kiznic.connection;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hhh.kiznic.dataclass.PicnicSimpleInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetCategorySimpleInfo extends AsyncTask<String, Integer, String>{
	
	Context context;
	LocationHelper location;
	
	String selectedRegion;
	int selectedGenre;
	int selectedPeriod;
	
	public GetCategorySimpleInfo(Context context, LocationHelper location, String selectedRegion, int selectedGenre, int selectedPeriod ) {
		this.context = context;
		this.location = location;
		this.selectedRegion = selectedRegion;
		this.selectedGenre = selectedGenre;
		this.selectedPeriod = selectedPeriod;
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		if(selectedRegion == null) {
			String[] region = location.getAddress();
			selectedRegion = region[0] + region[1];
		}
				
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		JSONArray sendCategoryInfo = new JSONArray();
		JSONObject categoryInfo = new JSONObject();
		
		try {
			categoryInfo.put("play_region", selectedRegion);
			categoryInfo.put("play_type", String.valueOf(selectedGenre));
			categoryInfo.put("play_period", String.valueOf(selectedPeriod));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendCategoryInfo.put(categoryInfo);
		
		String result = util.getJSONHttp(context, "search_play_list", sendCategoryInfo);
		
		
		return result;
	
	}
	
	protected void onPostExecute(String result) { 
		
		Log.d("result", result);
		
		Util util = new Util();
		ArrayList<PicnicSimpleInfo> simpleInfo = null;
		
		try {
			simpleInfo = util.parsePicnicSimpleInfoJSON(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("picnicSimpleInfo", simpleInfo.get(0).getPlayTitle());
		
			
	}

}
