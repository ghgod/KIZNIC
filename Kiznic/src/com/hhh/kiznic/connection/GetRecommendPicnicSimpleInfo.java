package com.hhh.kiznic.connection;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hhh.kiznic.R;
import com.hhh.kiznic.dataclass.ConditionforSimpleInfo;
import com.hhh.kiznic.dataclass.PicnicSimpleInfo;
import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetRecommendPicnicSimpleInfo extends AsyncTask<String, Integer, String> {

	Context mContext;
	String mySiDo;
	String[] addressArray;
	
	String ages;
	String inSide;
	String latitude;
	String longitude;
	String distance;
	JSONArray sendConditionInfo;
	
	public GetRecommendPicnicSimpleInfo(Context mContext, String ages, String inSide, String distance) {
		this.mContext = mContext;
		this.ages = ages;
		this.inSide = inSide;
		this.distance = distance;
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		LocationHelper location = new LocationHelper(mContext);
		location.run();
		addressArray = location.getAddress();
		mySiDo = location.getMySiDo();
		latitude = String.valueOf(location.getLat());
		longitude = String.valueOf(location.getLng());
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		String url = null;
		try {
			url = util.getWeatherURL(addressArray[0], addressArray[1], addressArray[2]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		String weatherXML = util.getXMLHttp(url);
		String pollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName="+mySiDo+"&pageNo=1&numOfRows=10&ServiceKey="+mContext.getResources().getString(R.string.openApiKey));
	//		Log.d("Weather", xmlResult);
			
		ConditionforSimpleInfo conditionInfoforSimpleInfo = new ConditionforSimpleInfo();
		
		ArrayList<WeatherInfo> weatherInfoList = null;
		ArrayList<PollutionInfo> pollutionInfoList = null;
			
		try {
			weatherInfoList = util.parseWeatherXML(weatherXML);
			pollutionInfoList = util.parseCurrentPollutionXML(pollutionXML);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conditionInfoforSimpleInfo.setWeatherInfo(weatherInfoList.get(0));
		conditionInfoforSimpleInfo.setPollutionInfo(pollutionInfoList.get(0));
		conditionInfoforSimpleInfo.setAges(ages);
		conditionInfoforSimpleInfo.setDistance(distance);
		conditionInfoforSimpleInfo.setInside(inSide);
		conditionInfoforSimpleInfo.setLatitude(latitude);
		conditionInfoforSimpleInfo.setLongitude(longitude);
		
		
		sendConditionInfo = new JSONArray();
		JSONObject conditionInfo = new JSONObject();
		
			try {
				conditionInfo.put("play_weatherdesc", conditionInfoforSimpleInfo.getWeatherInfo().getWeatherDesc());
				conditionInfo.put("play_rainprob", conditionInfoforSimpleInfo.getWeatherInfo().getRainProb());
				conditionInfo.put("play_windspeed", conditionInfoforSimpleInfo.getWeatherInfo().getWindSpeed());
				conditionInfo.put("play_temp", conditionInfoforSimpleInfo.getWeatherInfo().getTemperature());
				conditionInfo.put("play_feeltemp", util.convertFeelTemp(conditionInfoforSimpleInfo.getWeatherInfo().getTemperature(),conditionInfoforSimpleInfo.getWeatherInfo().getWindSpeed()));
				conditionInfo.put("play_pm10value", conditionInfoforSimpleInfo.getPollutionInfo().getPM10Value());
				conditionInfo.put("play_o3value", conditionInfoforSimpleInfo.getPollutionInfo().getO3Value());
				conditionInfo.put("play_humidity", conditionInfoforSimpleInfo.getWeatherInfo().getHumidity());
				conditionInfo.put("play_ages", conditionInfoforSimpleInfo.getAges());
				conditionInfo.put("play_place_type", conditionInfoforSimpleInfo.isInside());
				conditionInfo.put("play_place_distance", conditionInfoforSimpleInfo.getDistance());
				conditionInfo.put("play_place_lat", conditionInfoforSimpleInfo.getLatitude());
				conditionInfo.put("play_place_lng", conditionInfoforSimpleInfo.getLongitude());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sendConditionInfo.put(conditionInfo);
		
		
		String result = util.getJSONHttp(mContext, "recommend_play_list", sendConditionInfo);
		
		return result;
	}
	
	protected void onPostExecute(String result) { 
		
	//	Log.d("result", result);
		Util util = new Util();
		
		ArrayList<PicnicSimpleInfo> simpleInfo = null;
		
		try {
			simpleInfo = util.parsePicnicSimpleInfoJSON(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	Log.d("picnicSimpleInfo", simpleInfo.get(0).getPlayAddress()+"I'm null");
		
			
	}
	
	
	

}
