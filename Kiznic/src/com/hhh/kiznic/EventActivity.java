package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public class EventActivity extends Fragment{
	
	private View view;
	private Context context;
	
	private static ImageView event_event1_image, event_event2_image;
	
	public EventActivity(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.activity_event, null);
		
		init();
		set_image();
		
		return view;
		
	}

	private void set_image() {
		event_event1_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.event1, 500, 500));
		event_event2_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.event2, 500, 500));
	}

	private void init() {
		// TODO Auto-generated method stub
		event_event1_image = (ImageView)view.findViewById(R.id.event_event1_image);
		event_event2_image = (ImageView)view.findViewById(R.id.event_event2_image);
	}
}
