package com.hhh.kiznicsw.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

public class AESCBCmanage {

	public static Cipher getAESCBCEncryptor(byte[] keyBytes, byte[] IVBytes, String padding) throws Exception{
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	    IvParameterSpec ivSpec = new IvParameterSpec(IVBytes);
	    Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
	    //Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
	    return cipher;
	}

	public static Cipher getAESCBCDecryptor(byte[] keyBytes, byte[] IVBytes, String padding) throws Exception{
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	    IvParameterSpec ivSpec = new IvParameterSpec(IVBytes);
	    Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
	    //Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
	    return cipher; 
	} 
/*
	public static Cipher getAESECBEncryptor(byte[] keyBytes, String padding) throws Exception{
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    return cipher;
	}

	public static Cipher getAESECBDecryptor(byte[] keyBytes, String padding) throws Exception{
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    return cipher;
	}
*/
	public static byte[] encrypt(Cipher cipher, byte[] dataBytes) throws Exception{
		byte[] dataBytes2 = cipher.doFinal(dataBytes);
		
		//ByteArrayInputStream bIn = new ByteArrayInputStream(dataBytes2);
	    //CipherInputStream cIn = new CipherInputStream(bIn, cipher);
	    //ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	    //int ch;
	    //while ((ch = cIn.read()) >= 0) {
	     // bOut.write(ch);
	   // }
	    //cIn.close();
	    return dataBytes2;
	} 

	public static byte[] decrypt(Cipher cipher, byte[] dataBytes) throws Exception{
		byte[] dataBytes2 = Base64.decode(dataBytes, Base64.DEFAULT);
		byte[] dataByte_dofinal = cipher.doFinal(dataBytes2);
		
	//	Log.d("dofinal", new String(dataByte_dofinal));
		
		
		// ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	   // CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
	   // cOut.write(dataBytes);
	   // cOut.close();
		//Log.d("check2", new String(dataBytes2));
	    return dataByte_dofinal;    
	}
	
	public static byte[] AESCBCencrypt(byte[] keyBytes, byte[] ivBytes, String sPadding, byte[] messageBytes) throws Exception {
	    Cipher cipher = getAESCBCEncryptor(keyBytes, ivBytes, sPadding);
	    
	   
	    
	    return encrypt(cipher, messageBytes);
	}

	public static byte[] AESCBCdecrypt(byte[] keyBytes, byte[] ivBytes, String sPadding, byte[] encryptedMessageBytes) throws Exception {
	    Cipher decipher = getAESCBCDecryptor(keyBytes, ivBytes, sPadding);
	    return decrypt(decipher, encryptedMessageBytes);
	}
	
	
	
	
	
}
