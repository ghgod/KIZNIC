package com.hhh.kiznic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hhh.kiznic.SearchcategoryDialog.onSearchFlagListener;
import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCard;
import com.hhh.kiznic.card.MainRecommendCard.AListener;
import com.hhh.kiznic.connection.GetCategorySimpleInfo;
import com.hhh.kiznic.util.LocationHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class SearchActivity extends Fragment implements OnClickListener, onSearchFlagListener{

	private static View search_category1_relativelayout;
	private static View search_category2_relativelayout;
	private static View search_category3_relativelayout;
	private static View search_category4_relativelayout;

	private static TextView search_category1_text;
	private static TextView search_category2_text;
	private static TextView search_category3_text;
	private static TextView search_category4_text;
	
	private static EditText search_search_edittext;
	private static ImageView search_searchbutton_image;
	private static ListView search_list_view;
	private static CardAdapter searchcardAdapter;
	
	private static SearchcategoryDialog listdialog;
	
	private Context context;
	
	private static View view;

	private static String play_type;
	
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
	
	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(((Activity) context).getWindow().getDecorView());
		System.gc();
		
		super.onDestroy();
	}
	
	public void getS(){
		play_type = getArguments().getString("play_type");
		LocationHelper location = new LocationHelper(context);
		location.run();
		Log.d("location", String.valueOf(location.getMyLocation()));
		
		new GetCategorySimpleInfo(context, location, "서울특별시 영등포구", Integer.parseInt(play_type), Integer.parseInt("1"), search_list_view).execute();	
	}
	
	//////////////////////////////////////////////////////
	
	public void init(){
		search_category1_relativelayout = (View)view.findViewById(R.id.search_category1_relativelayout);
		search_category2_relativelayout = (View)view.findViewById(R.id.search_category2_relativelayout);
		search_category3_relativelayout = (View)view.findViewById(R.id.search_category3_relativelayout);
		search_category4_relativelayout = (View)view.findViewById(R.id.search_category4_relativelayout);
		search_list_view = (ListView)(view.findViewById(R.id.search_list_view));

		search_category1_text = (TextView)view.findViewById(R.id.search_category1_text);
		search_category2_text = (TextView)view.findViewById(R.id.search_category2_text);
		search_category3_text = (TextView)view.findViewById(R.id.search_category3_text);
		search_category4_text = (TextView)view.findViewById(R.id.search_category4_text);
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
			showListDialog(0, search_category1_text.getText().toString());
			break;
		case R.id.search_category2_relativelayout:
			showListDialog(1, search_category2_text.getText().toString());
			break;
		case R.id.search_category3_relativelayout:
			showListDialog(2, search_category3_text.getText().toString());
			break;
		case R.id.search_category4_relativelayout:
			showListDialog(3, search_category4_text.getText().toString());
			break;
		}
	}
	
private void showListDialog(int dialog_num, String dialog_title){
		
		listdialog = new SearchcategoryDialog();
		
	    listdialog.dialog_title = dialog_title;
	    listdialog.dialog_num = dialog_num;
		
	    listdialog.show(getChildFragmentManager(), "categorylist");
	}

	@Override
	public void setSearchFlagListener(String arg, int num){
	
		switch(num){
		case 0:
			search_category1_text.setText(arg);
			break;
		case 1:
			search_category2_text.setText(arg);
			break;
		case 2:
			search_category2_text.setText(arg);
			break;
		case 3:
			search_category4_text.setText(arg);
			break;
		}
	
	}
	
	
	
}
