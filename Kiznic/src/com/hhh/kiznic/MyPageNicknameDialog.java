package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("ValidFragment")
public class MyPageNicknameDialog extends DialogFragment implements OnEditorActionListener{

	Context context;
	
	EditText nickname_dialog_edittext;
	
	Dialog dialog;

	public MyPageNicknameDialog(Context context){
		this.context = context;
	}
	
	public interface onNicknameListener{
		public void setNicknameListener(String arg);
	}
	
	private onNicknameListener mListener;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		dialog = new Dialog(getActivity());
		
		mListener = (onNicknameListener) context;
		
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	
		dialog.setContentView(R.layout.dialog_nickname_mypage);
		
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		init();
		
		dialog.show();
		
		return dialog;
	}

	public void init(){
		nickname_dialog_edittext = (EditText)dialog.findViewById(R.id.nickname_dialog_edittext);
		nickname_dialog_edittext.requestFocus();
		nickname_dialog_edittext.setOnEditorActionListener(this);
	}
	
	@Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
        	mListener.setNicknameListener(nickname_dialog_edittext.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}
