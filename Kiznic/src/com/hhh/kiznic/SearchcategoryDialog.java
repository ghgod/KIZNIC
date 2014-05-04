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
	private ListView dialog_firstlist_listview;
	private ListView dialog_secondlist_listview;
	
	private String dialog_title;
	private String[] list_item;
	private String[] list_item2;
	
	private AdapterView.OnItemClickListener firstlistClickListener;
	private AdapterView.OnItemClickListener secondlistClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		
		if(secondlistClickListener == null)
			setContentView(R.layout.dailog_search);
		else
			setContentView(R.layout.dailog_search2);
		init();
		setTitle();
		if(secondlistClickListener == null)
			setList(dialog_firstlist_listview);
		else
			setList(dialog_firstlist_listview, dialog_secondlist_listview);
		
		setClickListener(firstlistClickListener , secondlistClickListener);
	}
	
	public void init(){
		dialog_title_textview = (TextView)findViewById(R.id.search_dailog_title_text);
		if(secondlistClickListener == null)
			dialog_firstlist_listview = (ListView)findViewById(R.id.search_dialog_list_list);
		else{
			dialog_firstlist_listview = (ListView)findViewById(R.id.search_dialog_firstlist_list);
			dialog_secondlist_listview = (ListView)findViewById(R.id.search_dialog_secondlist_list);
		}
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
		
		//if(dialog_title.equals("장르"))
			list_item = context.getResources().getStringArray(R.array.dialog_list_genre);
		
		List<String> listItem = Arrays.asList(list_item);
		ArrayList<String> itemArrayList = new ArrayList<String> (listItem);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemArrayList);
		dialog_firstlist_listview.setAdapter(arrayAdapter);
	
		//dialog_list_listview.getChildAt(0).setBottom(10);
	}

private void setList(ListView firstdialoglist, ListView seconddialoglist){
	
	list_item = context.getResources().getStringArray(R.array.dialog_list_genre);
	
	List<String> listItem = Arrays.asList(list_item);
	ArrayList<String> itemArrayList = new ArrayList<String> (listItem);
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemArrayList);
	dialog_firstlist_listview.setAdapter(arrayAdapter);

	//dialog_list_listview.getChildAt(0).setBottom(10);
	
	list_item2 = context.getResources().getStringArray(R.array.dialog_list_genre);
	
	List<String> listItem2 = Arrays.asList(list_item);
	ArrayList<String> itemArrayList2 = new ArrayList<String> (listItem2);
	ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemArrayList2);
	dialog_firstlist_listview.setAdapter(arrayAdapter2);
}
}
