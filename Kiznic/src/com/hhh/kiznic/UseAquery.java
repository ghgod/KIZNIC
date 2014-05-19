package com.hhh.kiznic;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;

public class UseAquery {

	AQuery aq;
	
	public void UseAquery(Activity activity){
		aq = new AQuery(activity);
	}
	
	public void setImageAquery(ImageView imageview, String value){
		aq.id(imageview).image(value);
	}
	
	public void setImageAquery(ImageView imageview, String value, boolean memcache, boolean filecache){
		aq.id(imageview).image(value,memcache,filecache);
	}
	
	public void setImageAquery(ImageView imageview, String value, int setting){
		aq.id(imageview).image(value, true, true, 0, setting);
	}
	
	public void setProgressAquery(ProgressBar progress, ImageView image, String value){
		aq.id(image).progress(progress).image(value, false, false);
	}
}
