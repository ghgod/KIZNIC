package com.hhh.kiznic;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCard;
import com.hhh.kiznic.card.MainWeatherCard;
import com.hhh.kiznic.customdiagram.CustomCircleMeter;

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

public class MainActivity extends Activity {
	
	private ListView mainListView;
	private CardAdapter cardAdapter;
	private int cardCount = -1;
	private View profile;
	private ImageView weather_finedust, weather_nextfinedust;
	private Button title_search_button;
	private Button title_mypage_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		init();
		
		clicklistener();
		
		listsetting();
		
		profile_circleimage();
		
		//MainWeatherCard a = (MainWeatherCard)mainListView.getItemAtPosition(1);
		
		//a.getCardView().findViewById(R.id.weather_finedustimage_image);
		
		weather_finedust = (ImageView)mainListView.getAdapter().getView(0, null, mainListView).findViewById(R.id.weather_finedustimage_image);
		
		//weather_finedust = (ImageView)mainListView.getItemIdAtPosition(0).getCardView().findViewById(R.id.weather_finedustimage_image);
		//weather_nextfinedust = (ImageView)mainListView.getChildAt(0).findViewById(R.id.weather_nextfinedustimage_image);
		
		weather_finedust_set(weather_finedust,"#ACACAC","보통",120);
		//weather_finedust_set(weather_nextfinedust,"#ACACAC","보통",120);
	}
	
	private void init() {
		
		profile = (View)findViewById(R.id.profile);
		
		title_search_button = (Button)findViewById(R.id.title_search_button);
		title_mypage_button = (Button)findViewById(R.id.title_mypage_button);
		
		mainListView = (ListView)findViewById(R.id.main_list_view);
		cardAdapter = new CardAdapter(getApplicationContext());
		
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

		
		Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(b);
		
		Paint pnt = new Paint();
		
		RectF r = new RectF(50, 50, 150, 150);
	
		pnt.setStrokeWidth(10);
		pnt.setStyle(Paint.Style.STROKE);
		pnt.setAntiAlias(true);
		
		pnt.setColor(Color.parseColor(colorCode));
		canvas.drawArc(r, 0, 360, true, pnt);
		
		pnt.setColor(Color.parseColor(colorCode));
		canvas.drawArc(r, -90, gage, true, pnt);
		
		imageview.setImageBitmap(b);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
