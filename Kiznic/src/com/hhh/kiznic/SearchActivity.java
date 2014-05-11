package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class SearchActivity extends Fragment implements OnClickListener{

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
	
	private Context context;
	
	private View view;

	//////////////////////////////////////////////////////
	
	public SearchActivity(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_search, null);
		
		init();
		
		clicklistener();
		
    	return view;
	}
	
	//////////////////////////////////////////////////////

	public void init(){
		search_category1_relativelayout = (View)view.findViewById(R.id.search_category1_relativelayout);
		search_category2_relativelayout = (View)view.findViewById(R.id.search_category2_relativelayout);
		search_category3_relativelayout = (View)view.findViewById(R.id.search_category3_relativelayout);
		search_category4_relativelayout = (View)view.findViewById(R.id.search_category4_relativelayout);
	}
	
	public void clicklistener(){	
		/*
		title_home_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				title_home_button.setSelected(true);
				Intent homeActivity = new Intent(SearchActivity.this, MainActivity.class);
				homeActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(homeActivity);
			}
		});
		title_mypage_button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				title_mypage_button.setSelected(true);
				Intent mypageActivity = new Intent(SearchActivity.this, MyPageActivity.class);
				mypageActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(mypageActivity);
			}
		});
		*/
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
			showListDialog("기간");
			break;
		case R.id.search_category3_relativelayout:
			showListDialog("장소");
			break;
		case R.id.search_category4_relativelayout:
			showListDialog("정렬");
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
		    
		    listdialog = new SearchcategoryDialog(context, title, singleListListener);
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
		    
		    listdialog = new SearchcategoryDialog(context, title, firstListListener, secondListListener);
		}
		
		listdialog.show();
	}
	
}
