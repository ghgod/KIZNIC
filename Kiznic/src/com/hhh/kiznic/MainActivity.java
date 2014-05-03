package com.hhh.kiznic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCard;
import com.hhh.kiznic.card.MainWeatherCard;
import com.hhh.kiznic.connection.GetNextPollutionAsync;
import com.hhh.kiznic.connection.GetRecommendPicnicSimpleInfo;
import com.hhh.kiznic.connection.GetPollutionAsync;
import com.hhh.kiznic.connection.GetWeatherAsync;
import com.hhh.kiznic.util.LocationHelper;

public class MainActivity extends Activity {
	
	private ListView mainListView;
	private CardAdapter cardAdapter;
	private int cardCount = -1;
	private View profile;
	private ImageView weather_finedust;
	
	private Button title_home_button;
	private Button title_search_button;
	private Button title_mypage_button;
	
	
	private TextView weather_mylocation;
	private TextView weather_today_timedesc;
	private TextView weather_today_temp;
	private TextView weather_today_rainprob;
	private TextView weather_today_windspeed;
	private TextView weather_next_timedesc;
	private TextView weather_next_temp;
	private TextView weather_today_pm10value;
	private TextView weather_next_pm10Info;
	private TextView wewather_today_feeltemp;
	
	
	private ImageView weather_refresh_button;
	private ImageView weather_next_dustmeter;
	private ImageView weather_image;
	private ImageView weather_next_image;
	
	private ToggleButton inside;
	
		
	//////////////////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		init();
		
		clicklistener();
		
		profile_circleimage();
	
		//////////////////////////////////////////	
		new GetWeatherAsync(getBaseContext(), weather_mylocation, weather_today_timedesc, weather_today_temp, weather_today_rainprob, weather_today_windspeed, wewather_today_feeltemp, weather_next_timedesc, weather_next_temp,  weather_image, weather_next_image).execute("");
		new GetPollutionAsync(getBaseContext(), weather_today_pm10value, weather_finedust).execute("");
		new GetNextPollutionAsync(getBaseContext(),weather_next_pm10Info, weather_next_dustmeter).execute("");
		//////////////////////////////////////////
	
	}
	
	private void init() {
		
		profile = (View)findViewById(R.id.profile);
		
		title_home_button = (Button)findViewById(R.id.title_home_button);
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
		weather_next_timedesc = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextdaysimpleinfo_text);
		weather_next_temp = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nexttemperature_text);
		weather_next_pm10Info  = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextfinedusttext_text);
		weather_refresh_button = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_getlocation_imagebutton);
		wewather_today_feeltemp = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_windcilltemperature_text);
		weather_finedust = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_finedustimage_image);
		weather_next_dustmeter = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextfinedustimage_image);
		
		weather_image = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_weatherimage_image);
		weather_next_image = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextweatherimage_image);
		
		inside = (ToggleButton)findViewById(R.id.condition_inout_image);
		
		title_home_button.setSelected(true);
		
	}
	
	private void clicklistener(){
		
		
		profile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mypageActivity = new Intent(MainActivity.this, MyPageActivity.class);
				startActivity(mypageActivity);
			}
		});
		
		title_search_button.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				title_search_button.setSelected(true);
				Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
				searchActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(searchActivity);
			}
		});
		
		title_mypage_button.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				title_mypage_button.setSelected(true);
				Intent mypageActivity = new Intent(MainActivity.this, MyPageActivity.class);
				mypageActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(mypageActivity);
			}
		});
		
		weather_refresh_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					
				new GetWeatherAsync(getBaseContext(), weather_mylocation, weather_today_timedesc, weather_today_temp, weather_today_rainprob, weather_today_windspeed, wewather_today_feeltemp, weather_next_timedesc, weather_next_temp,  weather_image, weather_next_image).execute("");
				new GetPollutionAsync(getBaseContext(), weather_today_pm10value, weather_finedust).execute("");
				new GetNextPollutionAsync(getBaseContext(),weather_next_pm10Info, weather_next_dustmeter).execute("");
			}
			
		});
		
		inside.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetRecommendPicnicSimpleInfo(getBaseContext(), "11", "1", "15" ).execute("");
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
	
	
	
	///////////////////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
