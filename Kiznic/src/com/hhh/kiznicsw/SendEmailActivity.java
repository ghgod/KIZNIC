package com.hhh.kiznicsw;

import com.hhh.kiznicsw.R;
import com.hhh.kiznicsw.connection.SendEmailOnesStop;
import com.hhh.kiznicsw.dataclass.GMailSender;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
	private TextView sendemail_attach_photo_filename;
	private Button sendemail_attach_photo;
	private Button sendemail_button;
	private Button sendemail_cancel_button;
	private String receiver;
	private GMailSender sender;
	
	private String filename;
	
	private final int REQUEST_CODE_IMAGE = 1;
	
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
		sendemail_cancel_button = (Button)findViewById(R.id.sendemail_cancel_button);
		sendemail_attach_photo = (Button)findViewById(R.id.sendemail_attach_photo);
		sendemail_attach_photo_filename = (TextView)findViewById(R.id.sendemail_attach_photo_filename);
			
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
						new SendEmailOnesStop(sender, "[Kiznic] 불편사항 접수  : " + sendemail_subject_edittext.getText().toString(), sendemail_contents_edittext.getText().toString(), getResources().getString(R.string.kizniconestop_mail), receiver, filename).execute();
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
		
		sendemail_cancel_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		sendemail_attach_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, REQUEST_CODE_IMAGE);
			}
			
		});
		
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && null != data) {
			
			final Uri selectImageUri = data.getData();
			final String[] filePathColumn = {MediaStore.Images.Media.DATA};
			
			final Cursor imageCursor = this.getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
			imageCursor.moveToFirst();
			
			final int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
			final String imagePath = imageCursor.getString(columnIndex);
			imageCursor.close();
			
			filename = imagePath;
			
			sendemail_attach_photo_filename.setText(filename);
		}
	
	}
		

}	

