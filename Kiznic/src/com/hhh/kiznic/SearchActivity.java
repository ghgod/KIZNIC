package com.hhh.kiznic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hhh.kiznic.SearchcategoryDialog.onSubmitListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class SearchActivity extends Fragment implements OnClickListener, onSubmitListener{

	private View search_category1_relativelayout;
	private View search_category2_relativelayout;
	private View search_category3_relativelayout;
	private View search_category4_relativelayout;
	
	private SearchcategoryDialog listdialog;
	
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
		
		listdialog = new SearchcategoryDialog();
		
		if(!title.equals("장소")){
			AdapterView.OnItemClickListener singleListListener = new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		            //String tv = (String)parent.getAdapter().getItem(position);
		        }
		    };
		    
		    // list item set
		    
		    String[] list_item = this.context.getResources().getStringArray(R.array.dialog_list_genre);
		    List<String> listItem = Arrays.asList(list_item);
			ArrayList<String> itemArrayList = new ArrayList<String> (listItem);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, itemArrayList);
		
		    listdialog.dialog_title = title;
		    listdialog.first_arrayAdapter = arrayAdapter;
		    listdialog.firstlistClickListener = singleListListener;
		
		}
		else{
			AdapterView.OnItemClickListener firstListListener = new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		            //String tv = (String)parent.getAdapter().getItem(position);
		        }
		    };
		    AdapterView.OnItemClickListener secondListListener = new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		            //String tv = (String)parent.getAdapter().getItem(position);
		        }
		    };
		    
		    String[] list_item = this.context.getResources().getStringArray(R.array.dialog_list_genre);
			List<String> listItem = Arrays.asList(list_item);
			ArrayList<String> itemArrayList = new ArrayList<String> (listItem);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, itemArrayList);
			
			String[] list_item2 = this.context.getResources().getStringArray(R.array.dialog_list_genre);
			List<String> listItem2 = Arrays.asList(list_item2);
			ArrayList<String> itemArrayList2 = new ArrayList<String> (listItem2);
			ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, itemArrayList2);
			
			listdialog.dialog_title = title;
			listdialog.first_arrayAdapter = arrayAdapter;
			listdialog.second_arrayAdapter = arrayAdapter2;
		    listdialog.firstlistClickListener = firstListListener;
		    listdialog.secondlistClickListener = secondListListener;
		}
		
		listdialog.show(getFragmentManager(), "categorylist");
	}
	
	@Override
	public void setListener(String arg){
		
	}
}
