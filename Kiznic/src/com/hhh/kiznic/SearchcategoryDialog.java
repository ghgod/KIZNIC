package com.hhh.kiznic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SearchcategoryDialog extends Dialog {

	private Context context;
	
	private TextView dialog_title_textview;
	private ListView dialog_list_listview;
	
	private String dialog_title;
	private String[] list_item;
	
	private AdapterView.OnItemClickListener firstlistClickListener;
	private AdapterView.OnItemClickListener secondlistClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		
		init();
		
		if(secondlistClickListener == null)
			setContentView(R.layout.dailog_search);
		//else
			//setContentView();
		
		setTitle();
		setList(dialog_list_listview);
		
		setClickListener(firstlistClickListener , secondlistClickListener);
	}
	
	public void init(){
		dialog_title_textview = (TextView)findViewById(R.id.search_dailog_title_text);
		dialog_list_listview = (ListView)findViewById(R.id.search_dialog_list_list);
	}
	
	public SearchcategoryDialog(Context context , String title , 
			AdapterView.OnItemClickListener singleListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		this.context = context;
		dialog_title = title;
		firstlistClickListener = singleListener;
	}
	
	public SearchcategoryDialog(Context context , String title , 
			AdapterView.OnItemClickListener firstListener, AdapterView.OnItemClickListener secondListener) {
		super(context , android.R.style.Theme_Translucent_NoTitleBar);
		this.context = context;
		dialog_title = title;
		firstlistClickListener = firstListener;
		secondlistClickListener = secondListener;
		
	}
	
	private void setClickListener(AdapterView.OnItemClickListener first , AdapterView.OnItemClickListener second){
		if(second == null){
			//mLeftButton.setOnClickListener(left);
			//mRightButton.setOnClickListener(right);
		}
		else{
			
		}
	}
	
	private void setTitle(){
		dialog_title_textview.setText(dialog_title);
	}
	
	private void setList(ListView dialoglist){
		
		if(dialoglist.equals("장르"))
			list_item = context.getResources().getStringArray(R.array.dialog_list_genre);
		
		List<String> listItem = Arrays.asList(list_item);
		ArrayList<String> itemArrayList = new ArrayList<String> (listItem);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemArrayList);
		//dialog_list_listview.setAdapter(arrayAdapter)
	}
}
