package com.hhh.kiznicsw;

import com.hhh.kiznicsw.R;
import com.kakao.AppActionBuilder;
import com.kakao.AppActionInfoBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class EventActivity extends Fragment{
	
	private View view;
	private Context context;
	
	private static ImageView event_event1_image, event_event2_image;
	private Button event1_enter_button, event2_enter_button;
	private Button event1_detailview_button, event2_detailview_button;
	private Button event1_share_button, event2_share_button;
	public EventActivity() {
		
	}
	
	public EventActivity(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.activity_event, null);
		
		init();
		clicklistener();
		set_image();
		
		return view;
		
	}

	private void init() {
		// TODO Auto-generated method stub
		event_event1_image = (ImageView)view.findViewById(R.id.event_event1_image);
		event_event2_image = (ImageView)view.findViewById(R.id.event_event2_image);
		
		event1_detailview_button = (Button)view.findViewById(R.id.event1_detailview_button);
		event1_enter_button = (Button)view.findViewById(R.id.event1_enter_button);
		event1_share_button = (Button)view.findViewById(R.id.event1_share_button);
		
		event2_detailview_button = (Button)view.findViewById(R.id.event2_detailview_button);
		event2_enter_button = (Button)view.findViewById(R.id.event2_enter_button);
		event2_share_button = (Button)view.findViewById(R.id.event2_share_button);
		
	}
	
	private void clicklistener() {
		event1_detailview_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity().getApplicationContext(), DetailPageActivity.class);
				intent.putExtra("play_no", "13011499"); // 알라딘 디테일뷰 play_no
				getActivity().getApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}
			
		});
		
		event2_detailview_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity().getApplicationContext(), DetailPageActivity.class);
				intent.putExtra("play_no", "14005620"); // 신데렐라 디테일뷰 play_no
				getActivity().getApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}
			
		});
		
		event1_enter_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				
				String[] tos = {"kiznic@naver.com"};
				intent.putExtra(Intent.EXTRA_EMAIL, tos);
				
				intent.putExtra(Intent.EXTRA_SUBJECT, "[Kiznic] 이벤트 접수 : '알라딘'");
				intent.putExtra(Intent.EXTRA_TEXT, "꼭 되어야 하는 이유를 짧게 적어주세요~");
				
				startActivity(intent);
				
				Toast.makeText(getActivity().getApplicationContext(), "응모 성공!", Toast.LENGTH_SHORT).show();

				
			
				
				
			}
			
		});
		
		event2_enter_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("plain/text");
					
					String[] tos = {"kiznic@naver.com"};
					intent.putExtra(Intent.EXTRA_EMAIL, tos);
					
					intent.putExtra(Intent.EXTRA_SUBJECT, "[Kiznic] 이벤트 접수 : '신데렐라'");
					intent.putExtra(Intent.EXTRA_TEXT, "꼭 되어야 하는 이유를 짧게 적어주세요~");
					
					startActivity(intent);
					
					Toast.makeText(getActivity().getApplicationContext(), "응모 성공!", Toast.LENGTH_SHORT).show();

			
			}
			
		});
		
		
		event1_share_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean checkShare = kakaoShare();
			}
			
		});
		
		event2_share_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean checkShare = kakaoShare();
			}
		});
	}
	
	private void set_image() {
		event_event1_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.event1, 500, 500));
		event_event2_image.setImageBitmap(ImageDecoder.decodeSampledBitmapFromResource(getActivity().getResources(), R.drawable.event2, 500, 500));
	}
	
	private Boolean kakaoShare() {
		
		KakaoLink kakaoLink = null;
		try {
			kakaoLink = KakaoLink.getKakaoLink(getActivity().getBaseContext());
		} catch (KakaoParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
		try {
			
			kakaoTalkLinkMessageBuilder.addText("[Kiznic] 아이와 함께하는 나들이 추천");
			kakaoTalkLinkMessageBuilder.addAppButton("[Kiznic]");
			kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), (Context) getActivity());
			
			
		} catch (KakaoParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
}
