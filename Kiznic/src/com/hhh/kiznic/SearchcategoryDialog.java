package com.hhh.kiznic;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
	
	ArrayAdapter<String> first_arrayAdapter = null;
	ArrayAdapter<String> second_arrayAdapter = null;
	
	AdapterView.OnItemClickListener firstlistClickListener = null;
	AdapterView.OnItemClickListener secondlistClickListener = null;
	
	Dialog dialog;
	
	interface onSubmitListener{
		void setListener(String arg);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		dialog = new Dialog(getActivity());
		
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	
		if(secondlistClickListener == null)
			dialog.setContentView(R.layout.dailog_search);
		else
			dialog.setContentView(R.layout.dailog_search2);
	
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		init();
		
		dialog_title_textview.setText(dialog_title);
		
		setList();
		
		setClickListener(firstlistClickListener , secondlistClickListener);
		
		dialog.show();
		
		return dialog;
	}

	public void init(){
		dialog_title_textview = (TextView)dialog.findViewById(R.id.search_dailog_title_text);
		
		if(second_arrayAdapter != null){
			dialog_firstlist_listview = (ListView)dialog.findViewById(R.id.search_dialog_firstlist_list);
			dialog_secondlist_listview = (ListView)dialog.findViewById(R.id.search_dialog_secondlist_list);
		}
		else{
			dialog_firstlist_listview = (ListView)dialog.findViewById(R.id.search_dialog_list_list);
		}
	}
	
	private void setClickListener(AdapterView.OnItemClickListener first , AdapterView.OnItemClickListener second){
		if(second == null){
			dialog_firstlist_listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		           
					String tv = (String)parent.getAdapter().getItem(position);
		        
		            Toast.makeText(getActivity().getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
		            
		            if(dialog != null)
		            dialog.dismiss();
		        }
			});
		}
		else{
			dialog_firstlist_listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		           
					String tv = (String)parent.getAdapter().getItem(position);
		        
		            Toast.makeText(getActivity().getBaseContext(), String.valueOf(position) + "의 값으로 바뀜", Toast.LENGTH_SHORT).show();
		        }
			});
			
			dialog_secondlist_listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
		           
					String tv = (String)parent.getAdapter().getItem(position);
		        
		            Toast.makeText(getActivity().getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
		            
		            if(dialog != null)
		            dialog.dismiss();
		        }
			});
		}
	}
	
	private void setList(){
			
		//if(dialog_title.equals("장르"))
		dialog_firstlist_listview.setAdapter(first_arrayAdapter);
		
		if(second_arrayAdapter != null)
			dialog_secondlist_listview.setAdapter(second_arrayAdapter);
	}
}
