package com.hhh.kiznicsw.dataclass;

import java.io.ByteArrayInputStream;  
import java.io.File;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.Date;
import java.util.Properties;  
   
import javax.activation.DataHandler;  
import javax.activation.DataSource;  
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;  
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;

public class GMailSender extends javax.mail.Authenticator {
	private String mailhost = "smtp.gmail.com";
	private String user;
	private String password;
	private Session session;

	public GMailSender(String user, String password) {
		this.user = user;
		this.password = password;

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");
		
		session = Session.getDefaultInstance(props, this);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	public synchronized void sendMail(String subject, String body,	String sender, String recipients, String filename) throws Exception {
		MimeMessage message = new MimeMessage(session);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(
				body.getBytes(), "text/html"));
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		message.setDataHandler(handler);
		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));
		
		// 메세지 몸체 생성
		  BodyPart messageBodyPart = new MimeBodyPart();

		  // 메세지 텍스트 내용 설정
		  messageBodyPart.setText(body);

		  // 다양한 종류의 데이터 추가를 위한 객체 생성
		  Multipart multipart = new MimeMultipart();

		  // 첫번째 메세지 몸체 추가
		  multipart.addBodyPart(messageBodyPart);
		  
		  if (filename != "") {
		   File file = new File(filename); 
		   if (file.exists()) {
		    messageBodyPart = new MimeBodyPart();
		    FileDataSource fds = new FileDataSource(filename);
		    messageBodyPart.setDataHandler(new DataHandler(fds));
		    messageBodyPart.setFileName(filename);
		    multipart.addBodyPart(messageBodyPart);    
		   }
		  }

		 
		  
		  // Multipart 객체를 Message 객체에 추가
		  message.setContent(multipart);
		  // 보낸 날짜 설정..ㅡ0ㅡ;; 이거빠지니깐 에러나는데..ㅠㅠ
		  message.setSentDate(new Date());
				
		
		Transport.send(message);
		Log.e("메시지", "메시지 성공");
	}

	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
}