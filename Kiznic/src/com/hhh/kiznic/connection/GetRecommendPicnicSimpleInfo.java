package com.hhh.kiznic.connection;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hhh.kiznic.R;
import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCard;
import com.hhh.kiznic.card.MainRecommendCarditemCard;
import com.hhh.kiznic.dataclass.ConditionforSimpleInfo;
import com.hhh.kiznic.dataclass.PicnicSimpleInfo;
import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GetRecommendPicnicSimpleInfo extends AsyncTask<String, Integer, String> {

	Context mContext;
	
	String mySiDo;
	String[] addressArray;
	ListView mainListView;
	CardAdapter[] recommendCardAdapter = new CardAdapter[4];
	CardAdapter cardAdapter;
	String ages;
	String inSide;
	String latitude;
	String longitude;
	String distance;
	JSONArray sendConditionInfo;
	
	TextView categoryName[] = new TextView[4];
	
	public GetRecommendPicnicSimpleInfo(Context mContext, String ages, String inSide, String distance, CardAdapter cardAdapter, ListView mainListView) {
		this.mContext = mContext;
		this.ages = ages;
		this.inSide = inSide;
		this.distance = distance;
		this.cardAdapter = cardAdapter;
		this.mainListView = mainListView;
		//this.recommendCardAdapter = recommendCardAdapter;
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
			url = util.getWeatherURL(mContext, addressArray[0], addressArray[1]);
		} catch (IOException e) {
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
				//conditionInfo.put("play_o3value", conditionInfoforSimpleInfo.getPollutionInfo().getO3Value());
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
		
		
		String result = util.getJSONHttp(mContext, "recommend_play_list", "recommend_info", sendConditionInfo);
		
		return result;
	}
	
	protected void onPostExecute(String result) { 
		
		Util util = new Util();
		
		ArrayList<PicnicSimpleInfo> simpleInfo = new ArrayList<PicnicSimpleInfo>();
		ArrayList<PicnicSimpleInfo> simpleInfo1 = new ArrayList<PicnicSimpleInfo>();
		ArrayList<PicnicSimpleInfo> simpleInfo2 = new ArrayList<PicnicSimpleInfo>();
		ArrayList<PicnicSimpleInfo> simpleInfo3 = new ArrayList<PicnicSimpleInfo>();
		ArrayList<PicnicSimpleInfo> simpleInfo4 = new ArrayList<PicnicSimpleInfo>();
		
		Boolean isExistSimpleInfo1 = false;
		Boolean isExistSimpleInfo2 = false;
		Boolean isExistSimpleInfo3 = false;
		Boolean isExistSimpleInfo4 = false;
		
		
		try {
			simpleInfo = util.parsePicnicSimpleInfoJSON(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		for(int i=0; i<simpleInfo.size(); i++) {
			if(simpleInfo.get(i).getPlayType().equals("1")) {
				simpleInfo1.add(simpleInfo.get(i));
				
			}
			if(simpleInfo.get(i).getPlayType().equals("2")) {
				simpleInfo2.add(simpleInfo.get(i));
				
			}
			else if(simpleInfo.get(i).getPlayType().equals("3")) {
				simpleInfo3.add(simpleInfo.get(i));
				
			}
			else if(simpleInfo.get(i).getPlayType().equals("4")) {
				simpleInfo4.add(simpleInfo.get(i));
				
			}
		}
		
		//Log.d("simpleInfo1", String.valueOf(simpleInfo1.get(0).getPlayTitle()));
		//Log.d("simpleInfo2", String.valueOf(simpleInfo2.get(0).getPlayTitle()));
		//Log.d("simpleInfo3", String.valueOf(simpleInfo3.get(0).getPlayTitle()));
		//Log.d("simpleInfo4", String.valueOf(simpleInfo4.get(0).getPlayTitle()));
		
		int recommendCount = 0;
		
		if(simpleInfo1.size() != 0) {
			isExistSimpleInfo1 = true;
			recommendCount++;
		} 
		if(simpleInfo2.size() != 0) {
			isExistSimpleInfo2 = true;
			recommendCount++;
		} 
		if(simpleInfo3.size() != 0) {
			isExistSimpleInfo3 = true;
			recommendCount++;
		} 
		if(simpleInfo4.size() != 0) {
			isExistSimpleInfo4 = true;
			recommendCount++;
		}
		
		for(int i=0;i<recommendCount;i++){
			recommendCardAdapter[i] = new CardAdapter(mContext);
		}
		
		int genreCount = 1; 
		int genreListCount = 0;
			
		if(isExistSimpleInfo1) {
			cardAdapter.addItem(new MainRecommendCard(R.layout.list_item_card, "공연", mContext, genreCount));
			for(int i=0; i<simpleInfo1.size(); i++) {
				recommendCardAdapter[genreListCount].addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "공연", mContext, genreCount, simpleInfo1.get(i)));
			}
			genreCount++;
			genreListCount++;
		}
		
		if(isExistSimpleInfo2) {
			cardAdapter.addItem(new MainRecommendCard(R.layout.list_item_card, "전시/체험", mContext, genreCount));
			for(int i=0; i<simpleInfo2.size(); i++) {
				recommendCardAdapter[genreListCount].addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "전시/체험", mContext, genreCount, simpleInfo2.get(i)));
			}
			genreCount++;
			genreListCount++;
		}
		
		if(isExistSimpleInfo3) {
			cardAdapter.addItem(new MainRecommendCard(R.layout.list_item_card, "놀이", mContext, genreCount));
			for(int i=0; i<simpleInfo3.size(); i++) {
				recommendCardAdapter[genreListCount].addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "놀이", mContext, genreCount, simpleInfo3.get(i)));
			}
			genreCount++;
			genreListCount++;
		}
		
		if(isExistSimpleInfo4){
			cardAdapter.addItem(new MainRecommendCard(R.layout.list_item_card, "축제", mContext, genreCount));
			for(int i=0; i<simpleInfo4.size(); i++) {
				recommendCardAdapter[genreListCount].addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "축제", mContext, genreCount, simpleInfo4.get(i)));
			}
			genreCount++;
			genreListCount++;
		}
		
		
		mainListView.setAdapter(cardAdapter);
		
		for(int i=1;i<=genreListCount;i++){
			ListView recommendmainListView = (ListView)mainListView.getAdapter().getView(i, null, mainListView).findViewById(R.id.main_recommend_card_item_list);
			recommendmainListView.setAdapter(recommendCardAdapter[i-1]);
		}
		
		
		
	}
	
	
	
	
	
	

}
