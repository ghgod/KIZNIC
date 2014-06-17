package com.hhh.kiznicsw.databasemanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;

public class KiznicSharedPreferences {

	 private final String PREF_NAME = "com.hhh.kiznic.weather";
	 
	 private static Context mContext;
	 
	    public KiznicSharedPreferences(Context c) {
	        mContext = c;
	    }
	 
	    public void put(String key, String value) {
	        android.content.SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	        Editor editor = pref.edit();
	 
	        editor.putString(key, value);
	        editor.commit();
	    }
	 
	   		 
	    public String getValue(String key) {
	        android.content.SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,  Context.MODE_PRIVATE);
	        String dftValue = "default";
	        try {
	            return pref.getString(key, dftValue);
	        } catch (Exception e) {
	            return dftValue;
	        }
	 
	    }
	 
	   
	
}
