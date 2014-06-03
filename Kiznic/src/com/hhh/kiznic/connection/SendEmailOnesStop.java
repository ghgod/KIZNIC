package com.hhh.kiznic.connection;

import com.hhh.kiznic.dataclass.GMailSender;

import android.os.AsyncTask;
import android.util.Log;

public class SendEmailOnesStop extends AsyncTask<String, Integer, String>{
	
	private GMailSender sender;
	private String subject;
	private String body;
	private String from;
	private String to;
	
	public SendEmailOnesStop(GMailSender sender, String subject, String body, String from, String to) {
		this.sender = sender;
		this.subject = subject;
		this.body = body;
		this.from = from;
		this.to = to;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		try {
			sender.sendMail(subject, body, from, to);
			Log.d("[Kiznic] e-mail send success", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("[Kiznic] e-mail send fail", "fali");
		}
		return null;
	}
	
	

}
