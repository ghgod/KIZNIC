package com.hhh.kiznic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;

public class SearchActivity extends Activity implements OnClickListener{

	private Button title_home_button;
	private Button title_mypage_button;
	
	private View search_category1_relativelayout;
	private View search_category2_relativelayout;
	private View search_category3_relativelayout;
	private View search_category4_relativelayout;
	
	private SearchcategoryDialog listdialog;
	
	private AdapterView.OnItemClickListener singleListListener = null;
	private AdapterView.OnItemClickListener firstListListener = null;
	private AdapterView.OnItemClickListener secondListListener = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_search);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		init();
		clicklistener();
	}

	public void init(){
		title_home_button = (Button)findViewById(R.id.title_home_button);
		title_mypage_button = (Button)findViewById(R.id.title_mypage_button);
		search_category1_relativelayout = (View)findViewById(R.id.search_category1_relativelayout);
		search_category2_relativelayout = (View)findViewById(R.id.search_category2_relativelayout);
		search_category3_relativelayout = (View)findViewById(R.id.search_category3_relativelayout);
		search_category4_relativelayout = (View)findViewById(R.id.search_category4_relativelayout);
	}
	
	public void clicklistener(){	
		title_home_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent homeActivity = new Intent(SearchActivity.this, MainActivity.class);
				homeActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(homeActivity);
			}
		});
		title_mypage_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent mypageActivity = new Intent(SearchActivity.this, MyPageActivity.class);
				mypageActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(mypageActivity);
			}
		});
		
		search_category1_relativelayout.setOnClickListener(this);
		search_category2_relativelayout.setOnClickListener(this);
		search_category3_relativelayout.setOnClickListener(this);
		search_category4_relativelayout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.search_category1_relativelayout:
			showListDialog("장르");
			break;
		case R.id.search_category2_relativelayout:
			break;
		case R.id.search_category3_relativelayout:
			break;
		case R.id.search_category4_relativelayout:
			break;
		}
	}
	
	private void showListDialog(String title){
		
		if(!title.equals("장소")){
			singleListListener = new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		            //String tv = (String)parent.getAdapter().getItem(position);
		        }
		    };
		    
		    listdialog = new SearchcategoryDialog(this, title, singleListListener);
		}
		else{
			firstListListener = new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		            //String tv = (String)parent.getAdapter().getItem(position);
		        }
		    };
		    secondListListener = new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		            //String tv = (String)parent.getAdapter().getItem(position);
		        }
		    };
		    
		    listdialog = new SearchcategoryDialog(this, title, firstListListener, secondListListener);
		}
		
		listdialog.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
