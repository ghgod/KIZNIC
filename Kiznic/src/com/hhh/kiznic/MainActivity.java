package com.hhh.kiznic;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCard;
import com.hhh.kiznic.card.MainWeatherCard;
import com.hhh.kiznic.dataclass.NextPollutionInfo;
import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ListView mainListView;
	private CardAdapter cardAdapter;
	private int cardCount = -1;
	private View profile;
	private ImageView weather_finedust, weather_nextfinedust;
	private Button title_search_button;
	private Button title_mypage_button;
	
	//////////////////��� ��� ������ ///////////////////
	private TextView weather_mylocation;
	private TextView weather_today_timedesc;
	private TextView weather_today_temp;
	private TextView weather_today_rainprob;
	private TextView weather_today_windspeed;
	private TextView weather_next_timedesc;
	private TextView weather_next_temp;
	private TextView weather_today_pm10value;
	private TextView weather_today_o3grade;
	private TextView weather_next_pm10Info;
	private TextView wewather_today_feeltemp;
	
	
	private ImageView weather_refresh_button;
	
	private String[] addressArray;
	private String mySiDo;
	private String dust_gage;
	
	
	
	//////////////////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		 
		LocationHelper location = new LocationHelper(getBaseContext());
		location.run();
		
		
		
		init();
		
		weather_mylocation.setText(location.getMyLocation());
		addressArray = location.getAddress();
		mySiDo = location.getMySiDo();
		
		clicklistener();
		
		profile_circleimage();
		
		weather_finedust = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_finedustimage_image);

		
		
		////////////by Hyouk Jang //////////
	
		
		
		new getWeatherAsync().execute("");
		new getPollutionAsync().execute("");
		new getNextPollutionAsync().execute("");
		//////////////////////////////////////////////////
		
		//weather_finedust_set(weather_finedust,"#ACACAC","보통",(int)(3.6 * Integer.parseInt(dust_gage)-90));
	}
	
	private void init() {
		
		profile = (View)findViewById(R.id.profile);
		
		title_search_button = (Button)findViewById(R.id.title_search_button);
		title_mypage_button = (Button)findViewById(R.id.title_mypage_button);
		
		mainListView = (ListView)findViewById(R.id.main_list_view);
		cardAdapter = new CardAdapter(getApplicationContext());
		
		listsetting();
		
		weather_mylocation = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_location_text);
		weather_today_timedesc = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_simpleinfo_text);
		weather_today_temp= (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_temperature_text);
		weather_today_rainprob = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_rainfallpercent_text);
		weather_today_windspeed = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_windspeedpercent_text);
		weather_today_pm10value = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_finedusttext_text);
		weather_today_o3grade = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_weatherwatch_text);
		weather_next_timedesc = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextdaysimpleinfo_text);
		weather_next_temp = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nexttemperature_text);
		weather_next_pm10Info  = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextfinedusttext_text);
		weather_refresh_button = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_getlocation_imagebutton);
		wewather_today_feeltemp = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_windcilltemperature_text);
		
	}
	
	private void clicklistener(){
		
		
		profile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mypageActivity = new Intent(MainActivity.this, MyPageActivity.class);
				startActivity(mypageActivity);
			}
		});
		
		title_search_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
				searchActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(searchActivity);
			}
		});
		
		title_mypage_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent mypageActivity = new Intent(MainActivity.this, MyPageActivity.class);
				mypageActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(mypageActivity);
			}
		});
		
		weather_refresh_button.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new getWeatherAsync().execute("");
				new getPollutionAsync().execute("");
				new getNextPollutionAsync().execute("");
			}
			
		});
	}
	
	private void profile_circleimage(){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kid);
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		ImageView profile_image = (ImageView)findViewById(R.id.profile_kidimage_image);
		
		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
		        paint.setShader(shader);

		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);

		profile_image.setImageBitmap(circleBitmap);
	}
	
	private void listsetting(){
		
		// weather
		
		cardAdapter.addItem(new MainWeatherCard(R.layout.list_item_weather_card, "Weather Card", getApplicationContext(), cardCount++));
		
		// recommend
		
		for(int i=0;i<4;i++){
			cardAdapter.addItem(new MainRecommendCard(R.layout.list_item_card, "Recommend Card", getApplicationContext(), cardCount++));
		}

		mainListView.setAdapter(cardAdapter);
	}
	
	public void weather_finedust_set(ImageView imageview, String colorCode, String text, int gage){

		Bitmap b = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(b);
		
		Paint pnt = new Paint();
		Paint circlepnt = new Paint();
		
		RectF r = new RectF(5, 5, 35, 35);
	
		pnt.setStrokeWidth(3);
		pnt.setStyle(Paint.Style.STROKE);
		pnt.setAntiAlias(true);
		
		pnt.setColor(Color.parseColor("#FFFFFF"));
		canvas.drawArc(r, 0, 360, true, pnt);
		
		pnt.setColor(Color.parseColor("#000000"));
		canvas.drawArc(r, -90, gage, true, pnt);
		
		circlepnt.setColor(Color.parseColor("#DEE7E7"));
		circlepnt.setAntiAlias(true);
		
		canvas.drawCircle(20, 20, 15, circlepnt);
		
		imageview.setImageBitmap(b);
	}
	///////////////////////장혁 작성//////////////////////
	
	
	class getWeatherAsync extends AsyncTask<String, Integer, String> {
		
			
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
					
			String xmlResult = util.getXMLHttp(url);
			return xmlResult;
		}
		
		protected void onPostExecute(String result) {
	        //tv4.setText(result);
			ArrayList<WeatherInfo> weatherInfo = null;
			Util util = new Util();
			
			try {
				weatherInfo = util.parseWeatherXML(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			weather_today_timedesc.setText(weatherInfo.get(0).getDayState() + " " + weatherInfo.get(0).getTime() + ", " + weatherInfo.get(0).getWeatherDesc());
			weather_today_temp.setText(weatherInfo.get(0).getTemperature()+"°");
			weather_today_rainprob.setText(" " + weatherInfo.get(0).getRainProb() + " %");
			weather_today_windspeed.setText(" " + weatherInfo.get(0).getWindSpeed() + " m/s");
			wewather_today_feeltemp.setText("체감 온도 " + util.convertFeelTemp(weatherInfo.get(0).getTemperature(), weatherInfo.get(0).getWindSpeed())+"°");
			weather_next_timedesc.setText(weatherInfo.get(1).getDayState() + " " + weatherInfo.get(1).getTime() + ", " + weatherInfo.get(1).getWeatherDesc());
			weather_next_temp.setText(weatherInfo.get(1).getTemperature()+"°");
			
		}
	}
	
	class getPollutionAsync extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Util util = new Util();
			String pollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName="+mySiDo+"&pageNo=1&numOfRows=10&ServiceKey="+getResources().getString(R.string.openApiKey));
			
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
			weather_today_o3grade.setText("오존 농도 " + pollutionInfo.get(0).getO3Value());
			dust_gage = pollutionInfo.get(0).getPM10Value();
			weather_finedust_set(weather_finedust,"#ACACAC","보통",(int)((3.6 * Integer.parseInt(dust_gage))));
		}
	}
	
	class getNextPollutionAsync extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Util util = new Util();
			String nextPollutionXML = util.getXMLHttp("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?searchDate="+util.yesterdayDate()+"&ServiceKey="+getResources().getString(R.string.openApiKey));
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
					weather_next_pm10Info.setText("미세먼지 농도 " + nextPollutionInfo.get(0).getSudoInfo());
					break;
				case 1 :
					weather_next_pm10Info.setText("미세먼지 농도  " + nextPollutionInfo.get(0).getJejuInfo());
					break;
				case 2 :
					weather_next_pm10Info.setText("미세먼지 농도  " + nextPollutionInfo.get(0).getYoungnamInfo());
					break;
				case 3 :
					weather_next_pm10Info.setText("미세먼지 농도 " + nextPollutionInfo.get(0).getHonamInfo());
					break;
				case 4 :
					weather_next_pm10Info.setText("미세먼지 농도 " + nextPollutionInfo.get(0).getGangwonInfo());
					break;
				case 5 :
					weather_next_pm10Info.setText("미세먼지 농도 " + nextPollutionInfo.get(0).getChungchungInfo());
					break;
			}
				
		}
		
	}
	
	
	
	
	///////////////////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
