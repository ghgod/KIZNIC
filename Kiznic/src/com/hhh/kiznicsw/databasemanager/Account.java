package com.hhh.kiznicsw.databasemanager;

public class Account {
	int account_no;
	String account_id;
	String account_pw;
	String account_pic;
	String account_type;
	String account_name;
	String account_birth;
	
	public Account() {
		
	}
	
	public void setNo(int account_no) {
		this.account_no = account_no;
	}
	
	public void setId(String account_id) {
		this.account_id = account_id;
	}
	
	public void setPw(String account_pw) {
		this.account_pw = account_pw;
	}
	
	public void setPic(String account_pic) {
		this.account_pic = account_pic;
	}
	
	
	public void setType(String account_type) {
		this.account_type = account_type;
	}
	
	public void setName(String account_name) {
		this.account_name = account_name;
	}
	
	public void setBirth(String account_birth) {
		this.account_birth = account_birth;
	}
	
	public int getNo() {
		return this.account_no;
	}
	
	public String getId() {
		return this.account_id;
	}
	
	public String getPw() {
		return this.account_pw;
	}
	
	public String getPic() {
		return this.account_pic;
	}
	
	public String getType() {
		return this.account_type;
	}
	
	public String getName() {
		return this.account_name;
	}
	
	public String getBirth() {
		return this.account_birth;
	}
}
