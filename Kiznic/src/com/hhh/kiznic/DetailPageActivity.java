package com.hhh.kiznic;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.DetailListCard;
import com.hhh.kiznic.card.DetailnevigationlistCard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class DetailPageActivity extends Activity implements OnClickListener{

	private static ListView detailInfoListView, detailNevigationInfoListView;
	private CardAdapter deatilInfoCardAdapter, detailNevigationInfoAdapter;
	
	private static ImageView detail_phone_image;
	private static ImageView detail_link_image;
	private static ImageView detail_bookmark_image;
	private static ImageView detail_sharing_image;
	
	private int detailcardCount = -1, detailnevigationcardCount = -1;
	
	static Toast t;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailpage);
	
	    init();
	    
	    clicklistener();
	}

	@Override
	public void onDestroy(){
		RecycleUtils.recursiveRecycle(getWindow().getDecorView());
        deatilInfoCardAdapter = null;
		detailNevigationInfoAdapter = null;
		System.gc();
		
		super.onDestroy();
	}
	
	private void init() {
		detailInfoListView = (ListView)findViewById(R.id.detail_list_view);
		detailNevigationInfoListView = (ListView)findViewById(R.id.detail_list2_view);
		deatilInfoCardAdapter = new CardAdapter(getApplicationContext());
		detailNevigationInfoAdapter = new CardAdapter(getApplicationContext());

		detail_phone_image = (ImageView)findViewById(R.id.detail_phone_image);
		detail_link_image = (ImageView)findViewById(R.id.detail_link_image);
		detail_bookmark_image = (ImageView)findViewById(R.id.detail_bookmark_image);
		//detail_sharing_image = (ImageView)findViewById(R.id.detail_sharing_image);
		
		listsetting();
	}

	private void clicklistener() {
		detail_phone_image.setOnClickListener(this);
		detail_link_image.setOnClickListener(this);
		detail_bookmark_image.setOnClickListener(this);
		//detail_sharing_image.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){

		switch(v.getId()){
		
		case R.id.detail_phone_image:
			t = Toast.makeText(getBaseContext(), "전화", Toast.LENGTH_LONG);
            t.show();
			break;
		case R.id.detail_link_image:
			t = Toast.makeText(getBaseContext(), "링크", Toast.LENGTH_LONG);
            t.show();
			break;
		case R.id.detail_bookmark_image:
			t = Toast.makeText(getBaseContext(), "북마크 등록", Toast.LENGTH_LONG);
			t.show();
			break;
		case R.id.detail_sharing_image:
			t = Toast.makeText(getBaseContext(), "연동", Toast.LENGTH_LONG);
			t.show();
			break;
		}
	}
	
	private void listsetting(){
		for(int i=0;i<6;i++)
			deatilInfoCardAdapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card,"Detail Info Card", getApplicationContext(), detailcardCount++));
	
		detailInfoListView.setAdapter(deatilInfoCardAdapter);
		
		LinearLayout detailInfoListLayout = (LinearLayout)findViewById(R.id.detail_listview_layout);
		LayoutParams detailInfoParams = (LayoutParams) detailInfoListLayout.getLayoutParams();
		
		int deatilInfoCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70 * (detailcardCount + 1), getResources().getDisplayMetrics());
		
		detailInfoParams.height = deatilInfoCardheight;
		
		for(int i=0;i<6;i++){
			View infoview = (View)detailInfoListView.getAdapter().getView(i, null, detailInfoListView).findViewById(R.id.detailpage_infobackground_view);
			if(i%2 == 0)
				infoview.setBackgroundColor(Color.argb(255, 249, 249, 249));
			else
				infoview.setBackgroundColor(Color.argb(255, 255, 255, 255));
		}
		//
		
		for(int i=0;i<4;i++)
			detailNevigationInfoAdapter.addItem(new DetailnevigationlistCard(R.layout.detailpage_nevigation_list_card, "Detail Nevigation Info Card", getApplicationContext(), detailnevigationcardCount++));
	
		detailNevigationInfoListView.setAdapter(detailNevigationInfoAdapter);
		
		LinearLayout detailNevigationListLayout = (LinearLayout)findViewById(R.id.detail_nevigation_layout);
		LayoutParams detailNevigationParams = (LayoutParams) detailNevigationListLayout.getLayoutParams();
		
		int deatilNevigationCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * (detailnevigationcardCount + 1), getResources().getDisplayMetrics());
		
		detailNevigationParams.height = deatilNevigationCardheight;
		
		for(int i=0;i<4;i++){
			View nevigationview = (View)detailNevigationInfoListView.getAdapter().getView(i, null, detailNevigationInfoListView).findViewById(R.id.detailpage_nevigationbackground_view);
			if(i%2 == 0)
				nevigationview.setBackgroundColor(Color.argb(255, 249, 249, 249));
			else
				nevigationview.setBackgroundColor(Color.argb(255, 255, 255, 255));
		}
	}

	
}
