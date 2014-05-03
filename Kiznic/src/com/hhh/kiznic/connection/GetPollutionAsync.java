package com.hhh.kiznic.connection;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhh.kiznic.R;
import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

public class GetPollutionAsync extends AsyncTask<String, Integer, String> {
	
	Context mContext;
	TextView weather_today_pm10value;
	ImageView weather_finedust;
	//LocationHelper location;
	String mySiDo;
	
	public GetPollutionAsync(Context mContext, TextView weather_today_pm10value, ImageView weather_finedust) {
		this.mContext = mContext;
		this.weather_finedust = weather_finedust;
		this.weather_today_pm10value = weather_today_pm10value;
		
	}
	
	
	protected void onPreExecute() {
		super.onPreExecute();
		LocationHelper location = new LocationHelper(mContext);
		location.run();
		mySiDo = location.getMySiDo();
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Util util = new Util();
		String pollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName="+mySiDo+"&pageNo=1&numOfRows=10&ServiceKey="+mContext.getResources().getString(R.string.openApiKey));
		
		return pollutionXML;
	}
	
	protected void onPostExecute(String result) {
        //tv4.setText(result);
		ArrayList<PollutionInfo> pollutionInfo = null;
		Util util = new Util();
		
		try {
			pollutionInfo = util.parseCurrentPollutionXML(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weather_today_pm10value.setText("미세먼지 농도 " + pollutionInfo.get(0).getPM10Value());
		util.weather_finedust_set(weather_finedust,(int)((1.2 * Integer.parseInt(pollutionInfo.get(0).getPM10Value()))), false, null);
	}
}