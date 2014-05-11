package com.hhh.kiznic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;

public class NMapViewer extends NMapActivity implements OnMapStateChangeListener{

	private static final String API_KEY="be984cc3917c237a096c3a0a911743b7";
	
	private NMapView mMapView = null;
	private NMapController mMapController = null;
	private LinearLayout MapContainer;
	
	private Activity activity;


	public NMapViewer(Activity activity){
		this.activity = activity;
		
		setmap();
	}
	
	public void setmap() {

	MapContainer = (LinearLayout)this.activity.findViewById(R.id.mapViewContainer);
	
	// create map view
	mMapView = new NMapView(activity);

	mMapController = mMapView.getMapController();
	
	// set a registered API key for Open MapViewer Library
	mMapView.setApiKey(API_KEY);

	MapContainer.addView(mMapView);
	
	// initialize map view
	mMapView.setClickable(true);
	
	mMapView.setBuiltInZoomControls(true, null);
	
	mMapView.setOnMapStateChangeListener(this);
	
	}
	
	@Override
	public void onMapInitHandler(NMapView mapview, NMapError errorInfo){
		if(errorInfo == null){
			mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091),11);
		}
		else{
			android.util.Log.e("NMAP", "onMapInitHandler:error="+errorInfo.toString());
		}
	}
	
	@Override
	public void onZoomLevelChange(NMapView mapview, int level){
		
	}
	
	@Override
	public void onMapCenterChange(NMapView mapview, NGeoPoint center){
		
	}
	
	@Override
	public void onAnimationStateChange(NMapView arg0, int animType, int animState){
		
	}

	@Override
	public void onMapCenterChangeFine(NMapView arg0){
		
	}
}
