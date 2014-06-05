package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainWeatherCard;
import com.hhh.kiznic.connection.GetRecommendPicnicSimpleInfo;
import com.hhh.kiznic.connection.GetWeatherAsync;
import com.hhh.kiznic.databasemanager.Databasehelper;
import com.hhh.kiznic.databasemanager.KiznicSharedPreferences;
import com.hhh.kiznic.util.LocationHelper;
import com.hhh.kiznic.util.Util;

@SuppressLint({ "ValidFragment", "NewApi" })
public class MainActivity extends Fragment {
	
	private static ListView mainListView;
	private static CardAdapter cardAdapter;
	//private static CardAdapter[] recommendCardAdapter = new CardAdapter[4];
	//private static int cardCount = -1, recommendcardCount = -1;
	
	private static ImageView weather_finedust;
	
	private static TextView weather_mylocation;
	private static TextView weather_today_timedesc;
	private static TextView weather_today_temp;
	private static TextView weather_today_rainprob;
	private static TextView weather_today_windspeed;
	private static TextView weather_next_timedesc;
	private static TextView weather_next_temp;
	private static TextView weather_today_pm10value;
	private static TextView weather_today_feeltemp;
	private TextView weather_today_humidity;
	
	private static ImageView weather_refresh_button;
	private static ImageView weather_image;
	private static ImageView weather_next_image;
	private static ImageView profile_kidimage_image;
	
	private static ImageView main_seekbar_background;
	
	private static SeekBar condition_range_seekbar; 
	
	static Databasehelper dbHelper;
	
	private static Context context;
	
	private static View view;

	private int range_progress;
	
	//////////////////////////////////////////////

	public static localDataAdmin localdata;
	
	public MainActivity(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		view = inflater.inflate(R.layout.activity_main, null);
		
		//KiznicTitle a = new KiznicTitle(this);
		dbHelper = new Databasehelper(getActivity().getBaseContext());
		
		
		
		init();
		
		clicklistener();
		
		//profile_circleimage();

		set_image();
		
		if(localdata.getprofile(localdata.getprofileflag()).getimageurl() != null){
			Bitmap selectedImage = BitmapFactory.decodeFile(localdata.getprofile(localdata.getprofileflag()).getimageurl());
			if(selectedImage != null)
				profile_circleimage(selectedImage);
		}
			
		
		return view;
	}
	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(getActivity().getWindow().getDecorView());
		System.gc();
		
		super.onDestroy();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		getWeatherFast();
	}

	
	//////////////////////////////////////////////
	

	private void set_image() {
		main_seekbar_background.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.condition_seekbar_background_image, 200, 200));
	
		profile_kidimage_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.mypage_profile, 200, 200));
	}
	
	private void init() {
		
		localdata = new localDataAdmin(getActivity().getBaseContext());
		
		//
		
		profile_kidimage_image = (ImageView)view.findViewById(R.id.profile_kidimage_image);
		
		mainListView = (ListView)view.findViewById(R.id.main_list_view);
		cardAdapter = new CardAdapter(getActivity().getApplicationContext());
	
		main_seekbar_background = (ImageView)view.findViewById(R.id.main_seekbar_background);
		
		condition_range_seekbar = (SeekBar)view.findViewById(R.id.condition_range_seekbar);
		
		listsetting();
		
		weather_mylocation = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_location_text);
		weather_today_timedesc = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_simpleinfo_text);
		weather_today_temp= (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_temperature_text);
		weather_today_rainprob = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_rainfallpercent_text);
		weather_today_windspeed = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_windspeedpercent_text);
		weather_today_feeltemp = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_windcilltemperature_text);
		weather_today_humidity = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_humidity_text);
		weather_next_timedesc = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextdaysimpleinfo_text);
		weather_next_temp = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nexttemperature_text);
		weather_today_pm10value = (TextView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_finedusttext_text);
		weather_refresh_button = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_getlocation_imagebutton);
		weather_finedust = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_finedustimage_image);
	
		weather_image = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_weatherimage_image);
		weather_next_image = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_nextweatherimage_image);
		
		//aq = new AQuery(mainListView.getAdapter().getView(0, null, mainListView));
		//aq.id(R.id.weather_weatherimage_image).image("http://bufferblog.wpengine.netdna-cdn.com/wp-content/uploads/2014/05/145.jpg");
	}
	
	private void clicklistener(){
		
		profile_kidimage_image.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				MainFragmentActivity mf = new MainFragmentActivity();
				mf.mViewPager.setCurrentItem(3);
			}
		});
		weather_refresh_button.setOnClickListener(new ImageView.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetWeatherAsync(getActivity().getBaseContext(),0, dbHelper, weather_mylocation, weather_today_timedesc, weather_today_humidity, weather_today_rainprob, weather_today_windspeed, weather_today_feeltemp, weather_next_timedesc, weather_next_temp,  weather_image, weather_next_image, weather_finedust, weather_today_pm10value).execute("");				
			}
		});

		condition_range_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), range_progress + "값 넘김", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				range_progress = progress;
			}
		});

	}
	
	private void profile_circleimage(Bitmap b){
		Bitmap bitmap = b;
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		ImageView profile_image = (ImageView)view.findViewById(R.id.profile_kidimage_image);
		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);

		
		Canvas c = new Canvas(circleBitmap);
		
		Paint paint_stroke = new Paint();
		
		paint_stroke.setARGB(255, 36, 176, 205);
		paint_stroke.setAntiAlias(true);

		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint_stroke);
		
		Paint paint = new Paint();
		paint.setShader(shader);
		paint.setAntiAlias(true);
		
		
		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2-5, paint);
		
		profile_image.setImageBitmap(circleBitmap);
	}
	
	private void listsetting(){
		// weather
		cardAdapter.addItem(new MainWeatherCard(R.layout.list_item_weather_card, "Weather Card", getActivity().getApplicationContext(), 0));
		// recommend
		new GetRecommendPicnicSimpleInfo(getActivity().getBaseContext(), "11", "1", "15", cardAdapter, mainListView ).execute("");

		mainListView.setAdapter(cardAdapter);
	}
	
	public void getWeatherFast() {
		
		KiznicSharedPreferences pref = new KiznicSharedPreferences(getActivity().getApplicationContext());
		LocationHelper location = new LocationHelper(getActivity().getApplicationContext());
		location.run();
		Util util = new Util();
		weather_mylocation.setText(location.getMyLocation());
		weather_today_timedesc.setText(pref.getValue("today_daystate") + " " + pref.getValue("today_time") + ", " + pref.getValue("today_weatherdesc"));
		weather_today_temp.setText(pref.getValue("today_temp")+"℃");
		weather_today_rainprob.setText(" " + pref.getValue("today_rainprob")+ " %");
		weather_today_windspeed.setText(" " + pref.getValue("today_windspeed") + " m/s");
		weather_today_humidity.setText(pref.getValue("today_humidity")+"%");
		
		weather_next_timedesc.setText(pref.getValue("next_daystate") + " " + pref.getValue("next_time") + ", " + pref.getValue("next_weatherdesc"));
		weather_next_temp.setText(pref.getValue("next_temp")+"℃");
		weather_image.setImageBitmap(util.getWeatherImage(getActivity().getApplicationContext(), pref.getValue("today_weatherdesc")));
		weather_next_image.setImageBitmap(util.getWeatherImage(getActivity().getApplicationContext(), pref.getValue("next_weatherdesc")));
		weather_today_pm10value.setText("미세먼지 농도 " + pref.getValue("today_pm10value"));
		if(pref.getValue("today_pm10value").equals("-") || pref.getValue("today_pm10value").equals("default")) {
			util.weather_finedust_set(weather_finedust,(int)((1.2 * 10)), false, null);
		} else {
			
			Log.e("weather", pref.getValue("today_pm10value"));
			
			util.weather_finedust_set(weather_finedust,(int)((1.2 * Integer.parseInt(pref.getValue("today_pm10value")))), false, null);

		}		
	}
	
}
