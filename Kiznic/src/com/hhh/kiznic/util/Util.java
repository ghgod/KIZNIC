package com.hhh.kiznic.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import com.hhh.kiznic.dataclass.NextPollutionInfo;
import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;



import android.util.Base64;
import android.util.Log;

public class Util {

	public static String compress(String string) throws IOException {
	    ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
	    GZIPOutputStream gos = new GZIPOutputStream(os);
	    gos.write(string.getBytes());
	    gos.close();
	    byte[] compressed = os.toByteArray();
	    os.close();
	    String compressedResult = Base64.encodeToString(compressed, Base64.DEFAULT);
	    return compressedResult;
	}

	public static String decompress(byte[] compressed) throws IOException {
	    final int BUFFER_SIZE = 32;
	    ByteArrayInputStream is = new ByteArrayInputStream(compressed);
	    GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
	    StringBuilder string = new StringBuilder();
	    byte[] data = new byte[BUFFER_SIZE];
	    int bytesRead;
	    while ((bytesRead = gis.read(data)) != -1) {
	        string.append(new String(data, 0, bytesRead));
	    }
	    gis.close();
	    is.close();
	    return string.toString();
	}
	
	public static String aesconvertedPW(String passWord, String keyString, String IVString, String sPadding) {
		byte[] demoKeyBytes = keyString.getBytes();
		byte[] demoIVBytes = IVString.getBytes();
		
		byte[] byte_account_pw = passWord.getBytes();
	    byte[] demo1EncryptedBytes = null;
		
	    try {
			demo1EncryptedBytes = AESCBCmanage.AESCBCencrypt(demoKeyBytes, demoIVBytes, sPadding, byte_account_pw);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String aes_account_pw = Base64.encodeToString(demo1EncryptedBytes, Base64.DEFAULT);
		
		return aes_account_pw;
		
	}
	
	public static JSONArray makeJSONArrayAccount(String accountID, String accountPW, int accountNo, String accountPic) {
		
		JSONObject accountInfoObject = new JSONObject();
        
    	try {
    		accountInfoObject.put("account_id",accountID);
    		accountInfoObject.put("account_pw", accountPW);
        	accountInfoObject.put("account_no", String.valueOf(accountNo));
        	accountInfoObject.put("account_pic", accountPic);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
	    	
    	JSONArray accountInfo = new JSONArray();
    	accountInfo.put(accountInfoObject);
			
		
		return accountInfo;
		
	}
	
	public String getXMLHttp(String url) {
		String received = null;
		HttpClient http = new DefaultHttpClient();
					
		try {								
			HttpParams params = http.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
			
			//Log.d("downLoadHttp_URL", url);
			
			HttpPost httpPost = new HttpPost(url);
			HttpResponse responsePost = http.execute(httpPost);
			HttpEntity resEntity = responsePost.getEntity();
				
			if(resEntity!=null){
				received = EntityUtils.toString(resEntity,"UTF-8");
				//Log.d("downLoadHTTP_result", received);
			} else {
				Log.d("downLoadHTTP", "null이야");                  
			}			
		}
		catch (Exception e) {
				Log.e("downLoadHTTP", "missed");
			e.printStackTrace();
		}
		return received;
	}
	
	public String convertFeelTemp(String temp, String windSpeed) {
		
		
		double speed = 3.6 * Double.parseDouble(windSpeed);
		double feelTemp = 13.12 + (0.6215 * Double.parseDouble(temp)) - (11.37 * Math.pow(speed, 0.16)) + (0.3965 * Math.pow(speed, 0.16) * Double.parseDouble(temp));
		DecimalFormat df = new DecimalFormat("#.#");
		String result = df.format(feelTemp);
		
		return result;
		
	}
	
	
	
	public String yesterdayDate() {
		 Calendar cal = new GregorianCalendar();
		 cal.add(Calendar.DATE, -1);
		 String month = "";
		 String day = "";
		 if((cal.get(Calendar.MONTH) + 1) < 10) {
			 month = "0"+String.valueOf((cal.get(Calendar.MONTH) + 1));
		 }
		 if(cal.get(Calendar.DAY_OF_MONTH) < 10){
			 day = "0"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		 }
		return String.valueOf(cal.get(Calendar.YEAR))+"-"+month+"-"+day;
	}

	
	public static String transformRegionName(String region1) {
		
		if(region1.equals("서울특별시")){
			return "서울";
		}
		else if(region1.equals("경기도")) {
			return "경기";
		}
		else if(region1.equals("강원도")) {
			return "강원";
		}
		else if(region1.equals("충청남도")) {
			return "충남";
		}
		else if(region1.equals("충청북도")) {
			return "충북";
		}
		else if(region1.equals("경상남도")) {
			return "경남";
		}
		else if(region1.equals("경상북도")) {
			return "경북";
		}
		else if(region1.equals("전라남도")) {
			return "전남";
		}
		else if(region1.equals("전라북도")) {
			return "전북";
		}
		else if(region1.equals("제주특별자치도")) {
			return "제주";
		}
		else if(region1.equals("부산광역시")) {
			return "부산";
		}
		else if(region1.equals("인전광역시")) {
			return "인천";
		}
		else if(region1.equals("대전광역시")) {
			return "대전";
		}
		else if(region1.equals("대구광역시")) {
			return "대구";
		}
		else if(region1.equals("울산광역시")) {
			return "울산";
		}
		else if(region1.equals("광주광역시")) {
			return "광주";
		}
		else {
			Log.d("transformRegionName", "Cannot Transform");
			return "Cannot Transform";
		}
	}
	
	public int sidoToArea(String sido) {
		if(sido.equals("서울")) {
			return 0;
		}
		else if(sido.equals("경기")) {
			return 0;
		}
		else if(sido.equals("인천")) {
			return 0;
		}
		else if(sido.equals("제주")) {
			return 1;
		}
		else if(sido.equals("경남")) {
			return 2;
		}
		else if(sido.equals("경북")) {
			return 2;
		}
		else if(sido.equals("울산")) {
			return 2;
		}
		else if(sido.equals("대구")) {
			return 2;
		}
		else if(sido.equals("부산")) {
			return 2;
		}
		else if(sido.equals("전남")) {
			return 3;
		}
		else if(sido.equals("전북")) {
			return 3;
		}
		else if(sido.equals("광주")) {
			return 3;
		}
		else if(sido.equals("강원")) {
			return 4;
		}
		else if(sido.equals("충남")) {
			return 5;
		}
		else if(sido.equals("충북")) {
			return 5;
		}
		else if(sido.equals("대전")) {
			return 5;
		}
		else {
			return 99;
		}
		
	}
	
	public static String convertDayState(String dayState, String searchDate) {
		
		String currentTime = getCurrentDate();
		
		
		if(dayState.equals("0") && currentTime.substring(8).equals(searchDate.substring(6, 8))) {
			return "오늘";
		}
		
		else if(dayState.equals("1")&& currentTime.substring(8).equals(searchDate.substring(6, 8))) {
			return "내일";
		}
		
		else if(dayState.equals("1") && !(currentTime.substring(8).equals(searchDate.substring(6, 8)))) {
			return "오늘";
		}
		else {
			return "모레";
		}
	}
	
	
	
	
	public static String transformHour(String hour) {
		int hourInt = Integer.parseInt(hour);
		
		if(hourInt <= 12) {
			return "오전 " + hour +"시";
		}
		else {
			return "오후 " + String.valueOf(hourInt - 12) + "시";
		}
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		Date currentTime = new Date ( );
		String mTime = mSimpleDateFormat.format ( currentTime );
		return mTime;
	}
	
	public String getWeatherURL(String topRegion, String middleRegion, String bottomRegion) throws JSONException {
		
		String sido = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
		
		String code1 = "";
		String code2 = "";
		
		String gridX = "";
		String gridY = "";
		
		String sido_result = getXMLHttp(sido);
		//Log.d("sido_result", sido_result);
		
		JSONArray sidoCode = new JSONArray(sido_result);
		for(int i=0; i<sidoCode.length(); i++){
			JSONObject topCode = sidoCode.getJSONObject(i);
		//	Log.d("value", topCode.getString("value"));
			if(topCode.getString("value").equals(topRegion)) {
				code1 = topCode.getString("code");
			//	Log.d("code1", code1);
			}
		}
		
		String sigungu = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl."+code1+".json.txt";
		
		String sigungu_result = getXMLHttp(sigungu);
		
		JSONArray sigunguCode = new JSONArray(sigungu_result);
		for(int i=0; i<sigunguCode.length(); i++){
			JSONObject middleCode = sigunguCode.getJSONObject(i);
			if(middleCode.getString("value").equals(middleRegion)) {
				code2 = middleCode.getString("code");
			}
		}
		
		String dong = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf."+code2+".json.txt";
		
		String dong_result = getXMLHttp(dong);
		
		JSONArray dongCode = new JSONArray(dong_result);
		for(int i=0; i<dongCode.length(); i++){
			JSONObject bottomCode = dongCode.getJSONObject(i);
			if(bottomCode.getString("value").equals(bottomRegion)) {
				gridX = bottomCode.getString("x");
				gridY = bottomCode.getString("y");
			}
		}
		return "http://www.kma.go.kr/wid/queryDFS.jsp?gridx="+gridX+"&gridy="+gridY;
	}
	
	
	public ArrayList<WeatherInfo> parseWeatherXML(String xmlText) throws IOException {
		
		boolean initem = false;
		boolean dateCheck = false;
		int flag = 10;
		ArrayList<WeatherInfo> weatherInfoList = new ArrayList<WeatherInfo>();
		
		String searchDate = "";
		String dayState = "";
		String hour = "";
		String weatherDesc = "";
		String rainProb = "";
		String windSpeed = "";
		String temp = "";
					
		try {
		
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlText));
			int eventType = parser.getEventType();
			
			String seqNo = "";
						
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
					case XmlPullParser.START_DOCUMENT :
						break;
					case XmlPullParser.START_TAG:
						String tag = parser.getName();
					    if(tag.equals("tm")) {
					    	dateCheck = true;
					    }
					    if(tag.equals("data")){
						    seqNo = parser.getAttributeValue(null, "seq");
							initem = true;
					    }
					    if(tag.equals("day") && initem) {
					    	flag = 5;
					    }
					    else if (tag.equals("hour") && initem){
							flag = 0;
						}
						else if(tag.equals("wfKor") && initem) {
							flag = 1;
						}
						else if(tag.equals("pop") && initem) {
							flag = 2;
						}
						else if(tag.equals("ws") && initem) {
							flag = 3;
						}
						else if(tag.equals("temp") && initem) {
							flag = 4;
						}
						break;
					
					case XmlPullParser.TEXT:
						if(dateCheck) {
							searchDate = parser.getText();
							dateCheck = false;
						}
						
						if(initem) {
							switch(flag) {
								case 0 :
									//weatherInfo.setTime(parser.getText());
									hour = parser.getText();
									Log.d("hour", parser.getText());
									break;
								case 1 :
									//weatherInfo.setWeatherDesc(parser.getText());
									weatherDesc = parser.getText();
									break;
								case 2 :
								//	weatherInfo.setRainProb(parser.getText());
									rainProb = parser.getText();
									break;
								case 3 :
									//weatherInfo.setWindSpeed(parser.getText());
									windSpeed = parser.getText();
									break;
								case 4 :
									//weatherInfo.setTemperature(parser.getText());
									temp = parser.getText();
									break;
								case 5 :
									dayState = parser.getText();
									break;
							}
							
						}
						break;
					
					case XmlPullParser.END_TAG:
						flag = 99;
						
						if(parser.getName().equals("data")){
							WeatherInfo weatherInfo = new WeatherInfo();
							weatherInfo.setTime(Util.transformHour(hour));
							weatherInfo.setWeatherDesc(weatherDesc);
							weatherInfo.setRainProb(rainProb);
							weatherInfo.setWindSpeed(windSpeed.substring(0, 3));
							weatherInfo.setTemperature(temp);
							weatherInfo.setDayState(Util.convertDayState(dayState, searchDate));
							weatherInfoList.add(Integer.parseInt(seqNo), weatherInfo);
						}
						break;
				}
				eventType = parser.next();
			}
					
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return weatherInfoList;
	}
	
	public ArrayList<NextPollutionInfo> parseNextPollutionXML(String xmlText) throws IOException{
		ArrayList<NextPollutionInfo> nextPollutionInfoList = new ArrayList<NextPollutionInfo>();
		
		String nextPlainInfo = "";
		String sudo_Info = "";
		String chungchung_Info = "";
		String gangwon_Info = "";
		String honam_Info = "";
		String youngnam_Info = "";
		String jeju_Info = "";
		
		Boolean find = false;
		try {
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlText));
			
			int eventType = parser.getEventType();
			
			while(eventType != XmlPullParser.END_DOCUMENT) {
				
				switch(eventType) {
					case XmlPullParser.START_DOCUMENT :
						break;
					case XmlPullParser.START_TAG:
						
						String tag = parser.getName();
						
						if(tag.equals("informGrade")) {
							find = true;
						}
						break;
					
					case XmlPullParser.TEXT:
						if(find) {
							nextPlainInfo = parser.getText();
							String[] array = nextPlainInfo.split(",");
							sudo_Info = array[0].substring(6);
							jeju_Info = array[1].substring(6);
							youngnam_Info = array[2].substring(6);
							honam_Info = array[3].substring(6);
							gangwon_Info = array[4].substring(6);
							chungchung_Info = array[5].substring(6);
							
							find = false;
						}
						break;
					
					case XmlPullParser.END_TAG:
						if(parser.getName().equals("informGrade")){
							
							NextPollutionInfo nextPollutionInfo = new NextPollutionInfo();
							
							nextPollutionInfo.setInformGrade(nextPlainInfo);
							nextPollutionInfo.setSudoInfo(sudo_Info);
							nextPollutionInfo.setJejuInfo(jeju_Info);
							nextPollutionInfo.setYoungnamInfo(youngnam_Info);
							nextPollutionInfo.setHonamInfo(honam_Info);
							nextPollutionInfo.setGangwonInfo(gangwon_Info);
							nextPollutionInfo.setChungchungInfo(chungchung_Info);
							
							nextPollutionInfoList.add(nextPollutionInfo);
						}
						
						break;
				}
				eventType = parser.next();
			}
		} 
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextPollutionInfoList;
	}
	
	
	
	public ArrayList<PollutionInfo> parseCurrentPollutionXML(String xmlText) throws IOException {
		
		
		ArrayList<PollutionInfo> pollutionInfoList = new ArrayList<PollutionInfo>();
		
		String pm10Value = "";
		String o3Value = "";
		String pm10Grade = "";
		String o3Grade = "";
		
		Boolean initem = false;
		int flag = 99;
		
		try {
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlText));
			int eventType = parser.getEventType();
			
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
					case XmlPullParser.START_DOCUMENT :
						break;
					case XmlPullParser.START_TAG:
						String tag = parser.getName();
						
						if(tag.equals("item")) {
							initem = true;
						}
						if(initem) {	
							if(tag.equals("o3Value")){
								flag = 0;
							}
							else if(tag.equals("pm10Value")){
								flag = 1;
							}
							else if(tag.equals("o3Grade")){
								flag = 2;
							}
							else if(tag.equals("pm10Grade")){
								flag = 3;
							}
						}
						break;
					
					case XmlPullParser.TEXT:
						if(initem) {
							switch(flag){
								case 0 :
									o3Value = parser.getText();
									break;
								case 1 :
									pm10Value = parser.getText();
									break;
								case 2 :
									o3Grade = parser.getText();
									break;
								case 3 :
									pm10Grade = parser.getText();
									break;
							}
						}
						break;
					
					case XmlPullParser.END_TAG:
						flag = 99;
						
						if(parser.getName().equals("item")){
							PollutionInfo pollutionInfo = new PollutionInfo();
							pollutionInfo.setO3Value(o3Value);
							pollutionInfo.setO3Grade(o3Grade);
							pollutionInfo.setPM10Value(pm10Value);
							pollutionInfo.setPM10Grade(pm10Grade);
							
							Log.d("ozValue", o3Value);
							Log.d("ozGrade", o3Grade);
							Log.d("pm10Value", pm10Value);
							Log.d("pm10Grade", pm10Grade);
							pollutionInfoList.add(pollutionInfo);
						}
						
						break;
				}
				eventType = parser.next();
			}
		} 
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return pollutionInfoList;
	}
	
	
}
