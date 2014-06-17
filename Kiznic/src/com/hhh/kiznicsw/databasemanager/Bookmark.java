package com.hhh.kiznicsw.databasemanager;

public class Bookmark {
	
	int bookmark_no;
	int play_no;
	String time_stamp;
	
	public Bookmark() {
		
	}
	
	public void setPlayno(int play_no) {
		this.play_no = play_no;
	}
	
	public void setTimestamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	public int getPlayno() {
		return this.play_no;
	}
	
	public String getTime_stamp() {
		return this.time_stamp;
	}
	
	
}
