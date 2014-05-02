package com.hhh.kiznic.util;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhh.kiznic.R;
import com.hhh.kiznic.dataclass.NextPollutionInfo;

public class getNextPollutionAsync extends AsyncTask<String, Integer, String> {

	Context mContext;
	TextView weather_next_pm10Info;
	ImageView weather_next_dustmeter;
	String mySiDo;
	
	public getNextPollutionAsync(Context mContext, TextView weather_next_pm10Info, ImageView weather_next_dustmeter, String mySiDo) {
		this.mContext = mContext;
		this.weather_next_pm10Info = weather_next_pm10Info;
		this.weather_next_dustmeter = weather_next_dustmeter;
		this.mySiDo = mySiDo;
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		String nextPollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?searchDate="+util.yesterdayDate()+"&ServiceKey="+mContext.getResources().getString(R.string.openApiKey));
		Log.d("2014-??", util.yesterdayDate());
		return nextPollutionXML;
	}
	
	protected void onPostExecute(String result) {
        //tv4.setText(result);
		ArrayList<NextPollutionInfo> nextPollutionInfo = null;
		Util util = new Util();
		
		try {
			nextPollutionInfo = util.parseNextPollutionXML(result);
			Log.d("pollution", result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(util.sidoToArea(mySiDo)){
			case 0 :
				weather_next_pm10Info.setText("미세먼지  " + nextPollutionInfo.get(0).getSudoInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getSudoInfo());
				break;
			case 1 :
				weather_next_pm10Info.setText("미세먼지  " + nextPollutionInfo.get(0).getJejuInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getJejuInfo());
				break;
			case 2 :
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getYoungnamInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getYoungnamInfo());
				break;
			case 3 :
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getHonamInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getHonamInfo());
				break;
			case 4 :
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getGangwonInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getGangwonInfo());
				break;
			case 5 :
				weather_next_pm10Info.setText("미세먼지 " + nextPollutionInfo.get(0).getChungchungInfo());
				util.weather_finedust_set(weather_next_dustmeter, 0, true, nextPollutionInfo.get(0).getChungchungInfo());
				break;
		}
		
	}
	
}