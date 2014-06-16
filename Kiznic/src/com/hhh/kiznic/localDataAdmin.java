package com.hhh.kiznic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;

public class localDataAdmin {

	public Context context;

	class profileInfo{
		private String name;
		private String sex;
		private String birth;
		private String image_url;
		private Bitmap profile_image;
		
		public profileInfo(String name, String sex, String birth){
			this.name = name;
			this.sex = sex;
			this.birth = birth;
			this.profile_image = null;
			this.image_url = null;
		}

		public String getname(){return name;}
		public String getsex(){return sex;}
		public String getbirth(){return birth;}
		public String getimageurl(){return image_url;}
		public Bitmap getprofile_image(){return profile_image;}
		
		public void setname(String name){
			this.name = name;
		}
		
		public void setsex(String sex){
			this.sex = sex;
		}
		
		public void setbirth(String birth){
			this.birth = birth;
		}
		
		public void setiamge_url(String url){
			this.image_url = url;
		}
		
		public void setprofile_image(Bitmap profile_image){
			this.profile_image = profile_image;
		}
	}
	
	class alarmInfo{
		private int am_hour_value;
		private int am_minute_value;
		private int pm_hour_value;
		private int pm_minute_value;

		private boolean am_alarm_onoff;
		private boolean pm_alarm_onoff;
		
		public alarmInfo(boolean am_onoff, int am_h, int am_m, boolean pm_onoff, int pm_h, int pm_m){
			am_alarm_onoff = am_onoff;
			am_hour_value = am_h;
			am_minute_value = am_m;
			
			pm_alarm_onoff = pm_onoff;
			pm_hour_value = pm_h;
			pm_minute_value = pm_m;
		}

		public boolean getam_alarm_onoff(){return am_alarm_onoff;}
		public int getam_hour_value(){return am_hour_value;}
		public int getam_minute_value(){return am_minute_value;}
		public boolean getpm_alarm_onoff(){return pm_alarm_onoff;}
		public int getpm_hour_value(){return pm_hour_value;}
		public int getpm_minute_value(){return pm_minute_value;}
		
		public void setam_alarm_onoff(boolean onoff){
			am_alarm_onoff = onoff;
		}
		public void setam_hour_value(int hour_value){
			am_hour_value = hour_value;
		}
		public void setam_minute_value(int minute_value){
			am_minute_value = minute_value;
		}
		public void setpm_alarm_onoff(boolean onoff){
			pm_alarm_onoff = onoff;
		}
		public void setpm_hour_value(int hour_value){
			pm_hour_value = hour_value;
		}
		public void setpm_minute_value(int minute_value){
			pm_minute_value = minute_value;
		}
	}
	// profile
	
	private static profileInfo profile_info[];
	private static int profile_flag;
	private static boolean tutorial_main, tutorial_safe, tutorial_mypage;
	// alarm
	
	private static alarmInfo alarm_info;
	
	public localDataAdmin(Context context) {
		
		this.context = context;
		
		profile_info = new profileInfo[3];
		for(int i=0;i<3;i++)
			profile_info[i] = new profileInfo("이름을 입력하세요", "성별을 입력하세요", "생일을 입력하세요");
		
		profile_flag = 0;
		
		alarm_info = new alarmInfo(false, 10, 00, false, 10, 00);
		
		tutorial_main = false;
		tutorial_safe = false;
		tutorial_mypage = false;
		
		try{
			getLocalData();
		}catch(Exception e){
			
		}
	}
	
	public profileInfo getprofile(int flag){return profile_info[flag];}
	public profileInfo[] getprofile(){return profile_info;}
	public int getprofileflag(){return profile_flag;}
	public void setprofileflag(int flag){profile_flag = flag;}
	public alarmInfo getalarm(){return alarm_info;}
	public boolean gettutorialmain(){return tutorial_main;}
	public boolean gettutorialsafe(){return tutorial_safe;}
	public boolean gettutorialmypage(){return tutorial_mypage;}
	public void settutorialmain(boolean flag){tutorial_main = flag;}
	public void settutorialsafe(boolean flag){tutorial_safe = flag;}
	public void settutorialmypage(boolean flag){tutorial_mypage = flag;}
	
	public void setLocalData(){
		SharedPreferences pref = context.getSharedPreferences("kiznic", 0);
		Editor editor = pref.edit();
		
		// profile
		
		for(int i=0;i<3;i++){
			editor.putString("profile"+i+"_name", profile_info[i].getname());
			editor.putString("profile"+i+"_sex", profile_info[i].getsex());
			editor.putString("profile"+i+"_birth", profile_info[i].getbirth());
			editor.putString("profile"+i+"_url", profile_info[i].getimageurl());
		}
		
		editor.putInt("profile_flag", profile_flag);
		
		// alarm

		editor.putBoolean("am_alarm_onoff", alarm_info.getam_alarm_onoff());
		editor.putInt("am_hour_value", alarm_info.getam_hour_value());
		editor.putInt("am_minute_value", alarm_info.getam_minute_value());
		editor.putBoolean("pm_alarm_onoff", alarm_info.getpm_alarm_onoff());
		editor.putInt("pm_hour_value", alarm_info.getpm_hour_value());
		editor.putInt("pm_minute_value", alarm_info.getpm_minute_value());
		
		// tutorial
		
		editor.putBoolean("tutorial_main", tutorial_main);
		editor.putBoolean("tutorial_safe", tutorial_safe);
		editor.putBoolean("tutorial_mypage", tutorial_mypage);
		
		editor.commit();
	}
	
	public void getLocalData(){
		SharedPreferences pref = context.getSharedPreferences("kiznic", 0);
		
		// profile
		
		for(int i=0;i<3;i++){
			profile_info[i].setname(pref.getString("profile"+i+"_name", "이름을 입력하세요"));
			profile_info[i].setsex(pref.getString("profile"+i+"_sex", "성별을 입력하세요"));
			profile_info[i].setbirth(pref.getString("profile"+i+"_birth", "생일을 입력하세요"));
			profile_info[i].setiamge_url(pref.getString("profile"+i+"_url", null));
		}
				
		profile_flag = pref.getInt("profile_flag", 0);
				
		// alarm

		alarm_info.setam_alarm_onoff(pref.getBoolean("am_alarm_onoff", false));
		alarm_info.setam_hour_value(pref.getInt("am_hour_value", 10));
		alarm_info.setam_minute_value(pref.getInt("am_minute_value", 0));
		alarm_info.setpm_alarm_onoff(pref.getBoolean("pm_alarm_onoff", false));
		alarm_info.setpm_hour_value(pref.getInt("pm_hour_value", 10));
		alarm_info.setpm_minute_value(pref.getInt("pm_minute_value", 0));
		
		//tutorial

		tutorial_main = pref.getBoolean("tutorial_main", false);
		tutorial_safe = pref.getBoolean("tutorial_safe", false);
		tutorial_mypage = pref.getBoolean("tutorial_mypage", false);
	}

}
