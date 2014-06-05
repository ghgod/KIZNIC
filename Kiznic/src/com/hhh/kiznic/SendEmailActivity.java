package com.hhh.kiznic;

import com.hhh.kiznic.connection.SendEmailOnesStop;
import com.hhh.kiznic.dataclass.GMailSender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendEmailActivity extends Activity{
	
	private TextView sendemail_sender_textview;
	private EditText sendemail_subject_edittext;
	private EditText sendemail_contents_edittext;
	private Button sendemail_button;
	private String receiver;
	private GMailSender sender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_emailsend);
	    
	   
	    init();
	    clicklistener();
	    
	    
	}
	
	public void init() {
		sendemail_sender_textview = (TextView)findViewById(R.id.sendemail_sender_text);
		sendemail_subject_edittext = (EditText)findViewById(R.id.sendemail_subject_edittext);
		sendemail_contents_edittext = (EditText)findViewById(R.id.sendemail_contents_edittext);
		sendemail_button = (Button)findViewById(R.id.sendemail_button);
			
		sender = new GMailSender(getResources().getString(R.string.kizniconestop_mail),getResources().getString(R.string.kizniconestop_pw));
	    Intent intent = getIntent(); 
	    receiver = intent.getExtras().getString("email_address");
	    
	    sendemail_sender_textview.setText(receiver);
	}
	
	private void clicklistener() {
		
		sendemail_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sendemail_contents_edittext.getText().toString().equals("") || sendemail_subject_edittext.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "제목 혹은 내용을 입력하세요", Toast.LENGTH_SHORT).show();
				} else {
					
					try {
						new SendEmailOnesStop(sender, "[Kiznic] 불편사항 접수  : " + sendemail_subject_edittext.getText().toString(), sendemail_contents_edittext.getText().toString(), getResources().getString(R.string.kizniconestop_mail), receiver).execute();
						Toast.makeText(getApplicationContext(), "민원 접수 성공!", Toast.LENGTH_SHORT).show();
						finish();
						Log.d("[Kiznic] e-mail send success", "success");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e("[Kiznic] e-mail send fail", "fali");
					}
				}
				
			}
			
		});
		
		
	}

}	

