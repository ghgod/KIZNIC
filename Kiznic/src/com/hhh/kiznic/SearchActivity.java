package com.hhh.kiznic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hhh.kiznic.SearchcategoryDialog.onSubmitListener;
import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.MainRecommendCarditemCard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class SearchActivity extends Fragment implements OnClickListener, onSubmitListener, OnEditorActionListener{

	private View search_category1_relativelayout;
	private View search_category2_relativelayout;
	private View search_category3_relativelayout;
	private View search_category4_relativelayout;
	
	private EditText search_search_edittext;
	private ImageView search_searchbutton_image;
	private ListView search_list_view;
	private CardAdapter searchcardAdapter;
	
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
		
		listsetting();
		
    	return view;
	}
	
	//////////////////////////////////////////////////////

	public void init(){
		
		// Button 
		
		search_category1_relativelayout = (View)view.findViewById(R.id.search_category1_relativelayout);
		search_category2_relativelayout = (View)view.findViewById(R.id.search_category2_relativelayout);
		search_category3_relativelayout = (View)view.findViewById(R.id.search_category3_relativelayout);
		search_category4_relativelayout = (View)view.findViewById(R.id.search_category4_relativelayout);
		
		// Search
		
		search_search_edittext = (EditText)view.findViewById(R.id.search_search_edittext);
		search_search_edittext.requestFocus();
		search_search_edittext.setOnEditorActionListener(this);
		
		search_searchbutton_image = (ImageView)view.findViewById(R.id.search_searchbutton_image);
		
		// List
		
		search_list_view = (ListView)view.findViewById(R.id.search_list_view);
		searchcardAdapter = new CardAdapter(getActivity().getApplicationContext());
	}
	
	@Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
        	Toast t = Toast.makeText(view.getContext(), search_search_edittext.getText().toString() + " 검색", Toast.LENGTH_LONG);
            t.show();
        	return true;
        }
        return false;
    }
	
	public void clicklistener(){	

		search_category1_relativelayout.setOnClickListener(this);
		search_category2_relativelayout.setOnClickListener(this);
		search_category3_relativelayout.setOnClickListener(this);
		search_category4_relativelayout.setOnClickListener(this);
		search_searchbutton_image.setOnClickListener(this);
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
		case R.id.search_searchbutton_image:
			Toast t = Toast.makeText(view.getContext(), search_search_edittext.getText().toString() + " 검색", Toast.LENGTH_LONG);
            t.show();
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
	
	private void listsetting(){
		/*
		for(int i=0;i<10;i++){
			searchcardAdapter.addItem(new MainRecommendCarditemCard(R.layout.list_item_card_item_card, "Search Card", getActivity().getApplicationContext(), i));
		}
		search_list_view.setAdapter(searchcardAdapter);
		*/
	}
	
	@Override
	public void setListener(String arg){
		
	}
}
