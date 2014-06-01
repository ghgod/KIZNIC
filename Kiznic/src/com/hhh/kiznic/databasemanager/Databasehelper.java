package com.hhh.kiznic.databasemanager;

import com.hhh.kiznic.dataclass.PollutionInfo;
import com.hhh.kiznic.dataclass.WeatherInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Databasehelper extends SQLiteOpenHelper{
	
	//Logcat Tag
	private static final String LOG = "Databasehelper";
	
	// Database Vesion
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
	private static final String DATABASE_NAME = "KiznicLocalManager";
	
	// Table Names
	private static final String TABLE_WEATHER = "weather";
	private static final String TABLE_ACCOUNT = "Account";
	private static final String TABLE_BOOKMARK = "Bookmark";
	
	// Weather Table - column names
	
	//private static final String KEY_WEATHER_ID = "weather_id";
	private static final String KEY_TODAY_DAYSTATE = "weather_today_daystate";
	private static final String KEY_TODAY_TIME = "weather_today_time";
	private static final String KEY_TODAY_DESC = "weather_today_desc";
	private static final String KEY_TODAY_TEMP = "weather_today_temp";
	private static final String KEY_TODAY_FEELTEMP = "weather_today_feeltemp";
	private static final String KEY_TODAY_RAINPROB = "weather_today_rainprob";
	private static final String KEY_TODAY_WINDSPEED = "weather_today_windspeed";
	private static final String KEY_TODAY_HUMIDITY = "weather_today_humidity";
	private static final String KEY_TODAY_POLLUTIONSTATE = "weather_today_pollution_state";
	private static final String KEY_TODAY_POLLUTIONCONCENTRATION = "weather_today_pollution_concentration";
	private static final String KEY_NEXT_DAYSTATE = "weather_next_daystate";
	private static final String KEY_NEXT_TIME = "weather_next_time";
	private static final String KEY_NEXT_DESC = "weather_next_desc";
	private static final String KEY_NEXT_TEMP = "weather_next_temp";
	private static final String KEY_NEXT_POLLUTIONSTATE = "weather_next_pollution_state"; 
	
	// Account Table - column names
	private static final String KEY_NO = "account_no";
	private static final String KEY_ID = "account_id";
	private static final String KEY_PW = "account_pw";
	private static final String KEY_PIC = "account_pic";
	private static final String KEY_TYPE = "account_type";
	private static final String KEY_NAME = "account_name";
	private static final String KEY_BIRTH = "account_birth";
	
	// Bookmark Table = column names
	private static final String KEY_BOOKMARK_NO = "bookmark_no";
	private static final String KEY_PLAY_NO = "play_no";
	private static final String KEY_TIMESTAMP = "time_stamp";
	
	// Table Create Statements
	
	// Table Create Statement
	private static final String CREATE_TABLE_WEATHER = "CREATE TABLE " + TABLE_WEATHER + "(" + "_id" + " TEXT,"+ KEY_TODAY_DAYSTATE 
			+ " TEXT," + KEY_TODAY_TIME + " TEXT," + KEY_TODAY_DESC +" TEXT," + KEY_TODAY_TEMP + " TEXT," + KEY_TODAY_FEELTEMP + " TEXT," + KEY_TODAY_RAINPROB 
			+ " TEXT," + KEY_TODAY_WINDSPEED + " TEXT," + KEY_TODAY_HUMIDITY + " TEXT," + KEY_TODAY_POLLUTIONSTATE + " TEXT," + KEY_TODAY_POLLUTIONCONCENTRATION 
			+ " TEXT," + KEY_NEXT_DAYSTATE	+ " TEXT," + KEY_NEXT_TIME + " TEXT," + KEY_NEXT_DESC + " TEXT," + KEY_NEXT_TEMP + " TEXT," + KEY_NEXT_POLLUTIONSTATE + " TEXT" + ")";
	private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNT + "(" + KEY_NO + " INTEGER PRIMARY KEY," 
			+ KEY_ID + " TEXT," + KEY_PW + " TEXT," + KEY_PIC + " TEXT," + KEY_TYPE + " TEXT," + KEY_NAME + " TEXT," + KEY_BIRTH + " TEXT" + ")";
	private static final String CREATE_TABLE_BOOKMARK = "CREATE TABLE " + TABLE_BOOKMARK + "(" + KEY_BOOKMARK_NO + " TEXT," + KEY_PLAY_NO + " TEXT,"
			 + KEY_TIMESTAMP + " DATETIME" + ")";
	
	public Databasehelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_WEATHER);
		//db.execSQL(CREATE_TABLE_ACCOUNT);
		//db.execSQL(CREATE_TABLE_BOOKMARK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		// drop order tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
		// create new tables
		onCreate(db);
	}
	
	/*
	 * Insert Weather
	 */
	
	public String createWeather(WeatherInfo weatherTodayInfo, WeatherInfo weatherNextInfo, PollutionInfo pollutionInfo ) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("_id", "1");
		values.put(KEY_TODAY_DAYSTATE, weatherTodayInfo.getDayState());
		values.put(KEY_TODAY_TIME, weatherTodayInfo.getTime());
	    values.put(KEY_TODAY_DESC, weatherTodayInfo.getWeatherDesc());
	    values.put(KEY_TODAY_TEMP, weatherTodayInfo.getTemperature());
	    values.put(KEY_TODAY_FEELTEMP, weatherTodayInfo.getFeelTemp());
	    values.put(KEY_TODAY_RAINPROB, weatherTodayInfo.getRainProb());
	    values.put(KEY_TODAY_WINDSPEED, weatherTodayInfo.getWindSpeed());
	    values.put(KEY_TODAY_HUMIDITY, weatherTodayInfo.getHumidity());
	    values.put(KEY_TODAY_POLLUTIONCONCENTRATION, pollutionInfo.getPM10Value());
	    values.put(KEY_NEXT_DAYSTATE, weatherNextInfo.getDayState());
	    values.put(KEY_NEXT_TIME, weatherNextInfo.getTime());
	    values.put(KEY_NEXT_DESC, weatherNextInfo.getWeatherDesc());
	    values.put(KEY_NEXT_TEMP, weatherNextInfo.getTemperature());
		
	 // insert row
	    long account_row_ID = db.insert(TABLE_WEATHER, null, values);
	    
	    db.close();
	    
	    if(account_row_ID != 1) {
	    	return "success";
	    }
	    else {
	    	return "fail";
	    }
	    
	}
	
	
	
	/*
	 * Insert user Account
	 */
	
	
		
	public String createAccount(Account account) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NO, account.getNo());
	    values.put(KEY_ID, account.getId());
	    values.put(KEY_PW, account.getPw());
	    values.put(KEY_PIC, account.getPic());
	    values.put(KEY_TYPE, account.getType());
	    values.put(KEY_NAME, account.getName());
	    values.put(KEY_BIRTH, account.getBirth());
	    
	    // insert row
	    long account_row_ID = db.insert(TABLE_ACCOUNT, null, values);
	 
	    if(account_row_ID != 1) {
	    	return "success";
	    }
	    else {
	    	return "fail";
	    }
	}
	
	/*
	 * Update weather
	 */
	
	public void updateWeather(WeatherInfo weatherTodayInfo, WeatherInfo weatherNextInfo, PollutionInfo pollutionInfo) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		//values.put("_id", "1");
	    values.put(KEY_TODAY_DAYSTATE, weatherTodayInfo.getDayState());
		values.put(KEY_TODAY_TIME,  weatherTodayInfo.getTime());
	    values.put(KEY_TODAY_DESC, weatherTodayInfo.getWeatherDesc());
	    values.put(KEY_TODAY_TEMP, weatherTodayInfo.getTemperature());
	    values.put(KEY_TODAY_FEELTEMP, weatherTodayInfo.getFeelTemp());
	    values.put(KEY_TODAY_RAINPROB, weatherTodayInfo.getRainProb());
	    values.put(KEY_TODAY_WINDSPEED, weatherTodayInfo.getWindSpeed());
	    values.put(KEY_TODAY_HUMIDITY, weatherTodayInfo.getHumidity());
	    values.put(KEY_TODAY_POLLUTIONCONCENTRATION, pollutionInfo.getPM10Value());
	    values.put(KEY_NEXT_DAYSTATE, weatherNextInfo.getDayState());
	    values.put(KEY_NEXT_TIME, weatherNextInfo.getTime());
	    values.put(KEY_NEXT_DESC, weatherNextInfo.getWeatherDesc());
	    values.put(KEY_NEXT_TEMP, weatherNextInfo.getTemperature());
	    
	    db.update(TABLE_WEATHER, values, "_id=1", null);
		
	    db.close();
	}
	
	/*
	 * get TodayWeather
	 */
	public WeatherInfo getTodayWeatherInfo() {
		
		SQLiteDatabase db = this.getReadableDatabase();
		WeatherInfo weatherInfo = new WeatherInfo();
	    String selectQuery = "SELECT  * FROM " + TABLE_WEATHER + " WHERE " + "_id=1";
	    
	    Cursor c = db.rawQuery(selectQuery, null);
		//Log.d("cursorSuccess", "cursorOK"); 
	    
		if (c != null ) 
	    	c.moveToFirst();
	    
		//Log.d("cursorSuccess", "cursorOK"); 
	    weatherInfo.setDayState(c.getString(c.getColumnIndex(KEY_TODAY_DAYSTATE)));
	    weatherInfo.setTime(c.getString(c.getColumnIndex(KEY_TODAY_TIME)));
	    weatherInfo.setWeatherDesc(c.getString(c.getColumnIndex(KEY_TODAY_DESC)));
	    weatherInfo.setTemperature(c.getString(c.getColumnIndex(KEY_TODAY_TEMP)));
	    weatherInfo.setFeelTemp(c.getString(c.getColumnIndex(KEY_TODAY_FEELTEMP)));
	    weatherInfo.setRainProb(c.getString(c.getColumnIndex(KEY_TODAY_RAINPROB)));
	    weatherInfo.setWindSpeed(c.getString(c.getColumnIndex(KEY_TODAY_WINDSPEED)));
	    weatherInfo.setHumidity(c.getString(c.getColumnIndex(KEY_TODAY_HUMIDITY)));
	    
	    Log.d("daystate", weatherInfo.getDayState());
	    Log.d("time", weatherInfo.getTime());
	    Log.d("temp", weatherInfo.getWeatherDesc());
	    Log.d("feeltemp", weatherInfo.getFeelTemp());
	    Log.d("rainprob", weatherInfo.getRainProb());
	    Log.d("windspeed", weatherInfo.getWindSpeed());
	    Log.d("humidity", weatherInfo.getHumidity());
	    
	    c.close();	    
	    db.close();
	    return weatherInfo;
	}
	
	/*
	 * get NextWeather
	 */
	
	public WeatherInfo getNextWeatherInfo()  {
		
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    String selectQuery = "SELECT  * FROM " + TABLE_WEATHER + " WHERE "
	            + "_id=1";
	    
	    Cursor c = db.rawQuery(selectQuery, null);
		 
	    if (c != null)
	        c.moveToFirst();
	    
	    WeatherInfo weatherInfo = new WeatherInfo();
	    weatherInfo.setDayState(c.getString(c.getColumnIndex(KEY_NEXT_DAYSTATE)));
	    weatherInfo.setTime(c.getString(c.getColumnIndex(KEY_NEXT_TIME)));
	    weatherInfo.setTemperature(c.getString(c.getColumnIndex(KEY_NEXT_TEMP)));
	    weatherInfo.setWeatherDesc(c.getString(c.getColumnIndex(KEY_NEXT_DESC)));
	    
	    db.close();
	    return weatherInfo;
	}
	
	public PollutionInfo getPollutionInfo() {
		
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    String selectQuery = "SELECT  * FROM " + TABLE_WEATHER + " WHERE "
	            + "_id=1";
	    
	    Cursor c = db.rawQuery(selectQuery, null);
		 
	    if (c != null)
	        c.moveToFirst();
	    
	    PollutionInfo pollutionInfo = new PollutionInfo();
	    pollutionInfo.setPM10Value(c.getString(c.getColumnIndex(KEY_TODAY_POLLUTIONCONCENTRATION)));
	    
	    db.close();
	    return pollutionInfo;
	}
	

	
	/*
	 * get single Account
	 */
	public Account getAccount(int account_no) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT + " WHERE "
	            + KEY_NO + " = " + String.valueOf(account_no);
	 
	    //Log.e(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	        c.moveToFirst();
	 
	    Account account = new Account();
	    account.setNo(c.getInt(c.getColumnIndex(KEY_NO)));
	    account.setId(c.getString(c.getColumnIndex(KEY_ID)));
	    account.setPw(c.getString(c.getColumnIndex(KEY_PW)));
	    account.setName(c.getString(c.getColumnIndex(KEY_NAME)));
	    account.setPic(c.getString(c.getColumnIndex(KEY_PIC)));
	    account.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
	    account.setBirth(c.getString(c.getColumnIndex(KEY_BIRTH)));
	    	    	 
	    return account;
	}
	

}
