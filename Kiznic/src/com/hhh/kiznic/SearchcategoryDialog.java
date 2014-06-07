package com.hhh.kiznic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.CategoryListCard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class SearchcategoryDialog extends DialogFragment {

	Context context;
	
	TextView dialog_title_textview;
	ListView dialog_firstlist_listview;
	ListView dialog_secondlist_listview;
	
	String dialog_title;
	int dialog_num;
	int firstlist_position = 0;
	
	//
	
	CardAdapter first_arrayAdapter = null;
	CardAdapter second_arrayAdapter = null;
	
	ArrayList<String> location_list = new ArrayList<String>();
	
	AdapterView.OnItemClickListener firstlistClickListener = null;
	AdapterView.OnItemClickListener secondlistClickListener = null;
	
	String[] list_item = null, list_item2 = null;
	
	onSearchFlagListener activity;
	
	Dialog dialog;
	
	public interface onSearchFlagListener{
		void setSearchFlagListener(String arg, int num);
	}
	
	public SearchcategoryDialog(Context context){
		this.context = context;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		dialog = new Dialog(getActivity());
		
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	
		if(dialog_num != 0)
			dialog.setContentView(R.layout.dailog_search);
		else
			dialog.setContentView(R.layout.dailog_search2);
	
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		init();
		
		dialog_title_textview.setText(dialog_title);
		
		try {
			setList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setClickListener();
		
		dialog.show();
		
		return dialog;
	}

	public void init(){
		dialog_title_textview = (TextView)dialog.findViewById(R.id.search_dailog_title_text);
		
		first_arrayAdapter = new CardAdapter(getActivity());
		second_arrayAdapter = new CardAdapter(getActivity());
		
		 activity = (onSearchFlagListener)getParentFragment();
			
		
		if(dialog_num == 0){
			dialog_firstlist_listview = (ListView)dialog.findViewById(R.id.search_dialog_firstlist_list);
			dialog_secondlist_listview = (ListView)dialog.findViewById(R.id.search_dialog_secondlist_list);
		}
		else{
			dialog_firstlist_listview = (ListView)dialog.findViewById(R.id.search_dialog_list_list);
		}
	}
	
	private void setClickListener(){
		if(dialog_num != 0){
			dialog_firstlist_listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		           
					TextView t = (TextView) dialog_firstlist_listview.getAdapter().getView(position, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
					
					String tv = t.getText().toString();
					
					activity.setSearchFlagListener(tv, dialog_num);
					
		            if(dialog != null)
		            dialog.dismiss();
		        }
			});
		}
		else{
			dialog_firstlist_listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {

					try{
						switch(position){
						case 0:
							setlocationList("seoul");
							break;
						case 1:
							setlocationList("gwangju");
							break;
						case 2:
							setlocationList("daegu");
							break;
						case 3:
							setlocationList("daejeon");
							break;
						case 4:
							setlocationList("busan");
							break;
						case 5:
							setlocationList("ulsan");
							break;
						case 6:
							setlocationList("incheon");
							break;
						case 7:
							setlocationList("gangwondo");
							break;
						case 8:
							setlocationList("gyunggido");
							break;
						case 9:
							setlocationList("gyungsangnamdo");
							break;
						case 10:
							setlocationList("gyungsangbukdo");
							break;
						case 11:
							setlocationList("jeonranamdo");
							break;
						case 12:
							setlocationList("jeonrabukdo");
							break;
						case 13:
							setlocationList("jejudo");
							break;
						case 14:
							setlocationList("chungcheongnamdo");
							break;
						case 15:
							setlocationList("chungcheongbukdo");
							break;
						}
					}
					catch(Exception e){}
					
					firstlist_position = position;
					
					list_item2 = new String[location_list.size()];
					for(int i=0;i<location_list.size();i++)
						list_item2[i] = location_list.get(i);
					
					second_arrayAdapter = new CardAdapter(getActivity());
					
					for(int i=0;i<location_list.size();i++)
						second_arrayAdapter.addItem(new CategoryListCard(R.layout.search_categorylist_card, "category Card", getActivity().getApplicationContext(), i));
					
					dialog_secondlist_listview.setAdapter(second_arrayAdapter);
					
					TextView a;
					for(int i=0;i<location_list.size();i++){
						a = (TextView)dialog_secondlist_listview.getAdapter().getView(i, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
						a.setText(list_item2[i]);
					}
					
				}
			});
			
			dialog_secondlist_listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		           
					TextView t = (TextView) dialog_firstlist_listview.getAdapter().getView(firstlist_position, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
					TextView t2 = (TextView) dialog_secondlist_listview.getAdapter().getView(position, null, dialog_secondlist_listview).findViewById(R.id.category_item_text);
					
					
					String tv = t.getText().toString() + " " + t2.getText().toString();
					
					activity.setSearchFlagListener(tv, dialog_num);
					
		            if(dialog != null)
		            dialog.dismiss();
		        }
			});
		}
	}
	
	private void setList() throws IOException{
		
		switch(dialog_num){
		case 0:
			list_item = getResources().getStringArray(R.array.dialog_list_location);
			setlocationList("seoul");
			list_item2 = new String[location_list.size()];
			for(int i=0;i<location_list.size();i++)
				list_item2[i] = location_list.get(i);
			
			break;
		case 1:
			list_item = getResources().getStringArray(R.array.dialog_list_term);
			break;
		case 2:
			list_item = getResources().getStringArray(R.array.dialog_list_genre);
			break;
		case 3:
			list_item = getResources().getStringArray(R.array.dialog_list_array);
			break;
		}
		
		if(dialog_num != 0){
			
			if(dialog_num == 1){
				for(int i=0;i<6;i++)
					first_arrayAdapter.addItem(new CategoryListCard(R.layout.search_categorylist_card, "category Card", getActivity().getApplicationContext(), i));
			}
			else if(dialog_num == 2){
				for(int i=0;i<5;i++)
					first_arrayAdapter.addItem(new CategoryListCard(R.layout.search_categorylist_card, "category Card", getActivity().getApplicationContext(), i));
			}
			
			dialog_firstlist_listview.setAdapter(first_arrayAdapter);

			TextView a;
			LinearLayout b;
			
			if(dialog_num == 1){
				for(int i=0;i<6;i++){
					a = (TextView)dialog_firstlist_listview.getAdapter().getView(i, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
					a.setText(list_item[i]);
				}
			}
			else if(dialog_num == 2){
				for(int i=0;i<5;i++){
					a = (TextView)dialog_firstlist_listview.getAdapter().getView(i, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
					a.setText(list_item[i]);
				}
			}
		}
		else{
			for(int i=0;i<16;i++)
				first_arrayAdapter.addItem(new CategoryListCard(R.layout.search_categorylist_card, "category Card", getActivity().getApplicationContext(), i));
			
			for(int i=0;i<location_list.size();i++)
				second_arrayAdapter.addItem(new CategoryListCard(R.layout.search_categorylist_card, "category Card", getActivity().getApplicationContext(), i));
			
			
			dialog_firstlist_listview.setAdapter(first_arrayAdapter);
			dialog_secondlist_listview.setAdapter(second_arrayAdapter);
			
			TextView a;
			for(int i=0;i<16;i++){
				a = (TextView)dialog_firstlist_listview.getAdapter().getView(i, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
				a.setText(list_item[i]);
			}
			
			for(int i=0;i<location_list.size();i++){
				a = (TextView)dialog_secondlist_listview.getAdapter().getView(i, null, dialog_firstlist_listview).findViewById(R.id.category_item_text);
				a.setText(list_item2[i]);
			}
			
		}
			
	}
	
	private void setlocationList(String location) throws IOException{
		InputStream in = getActivity().getAssets().open(location + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String info = null;
		location_list.clear();
		
		while((info = reader.readLine()) != null){
			location_list.add(info);
		}
		
		reader.close();
		in.close();
	}
}