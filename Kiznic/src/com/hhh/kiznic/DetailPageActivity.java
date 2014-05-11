package com.hhh.kiznic;

import com.hhh.kiznic.card.CardAdapter;
import com.hhh.kiznic.card.DetailListCard;
import com.hhh.kiznic.card.DetailnevigationlistCard;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class DetailPageActivity extends Activity {

	private ListView detailInfoListView, detailNevigationInfoListView;
	private CardAdapter deatilInfoCardAdapter, detailNevigationInfoAdapter;
	
	private int detailcardCount = -1, detailnevigationcardCount = -1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailpage);
	
	    init();
	    
	    clicklistener();
	    
	    //NMapViewer nMap = new NMapViewer(this);
	}

	private void clicklistener() {
		
	}

	private void init() {
		detailInfoListView = (ListView)findViewById(R.id.detail_list_view);
		detailNevigationInfoListView = (ListView)findViewById(R.id.detail_list2_view);
		deatilInfoCardAdapter = new CardAdapter(getApplicationContext());
		detailNevigationInfoAdapter = new CardAdapter(getApplicationContext());
		
		listsetting();
	}

	private void listsetting(){
		
		for(int i=0;i<6;i++)
			deatilInfoCardAdapter.addItem(new DetailListCard(R.layout.detailpage_info_list_card,"Detail Info Card", getApplicationContext(), detailcardCount++));
	
		detailInfoListView.setAdapter(deatilInfoCardAdapter);
		
		LinearLayout detailInfoListLayout = (LinearLayout)findViewById(R.id.detail_listview_layout);
		LayoutParams detailInfoParams = (LayoutParams) detailInfoListLayout.getLayoutParams();
		
		int deatilInfoCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70 * (detailcardCount + 1), getResources().getDisplayMetrics());
		
		detailInfoParams.height = deatilInfoCardheight;
		
		//
		
		for(int i=0;i<4;i++)
			detailNevigationInfoAdapter.addItem(new DetailnevigationlistCard(R.layout.detailpage_nevigation_list_card, "Detail Nevigation Info Card", getApplicationContext(), detailnevigationcardCount++));
	
		detailNevigationInfoListView.setAdapter(detailNevigationInfoAdapter);
		
		LinearLayout detailNevigationListLayout = (LinearLayout)findViewById(R.id.detail_nevigation_layout);
		LayoutParams detailNevigationParams = (LayoutParams) detailNevigationListLayout.getLayoutParams();
		
		int deatilNevigationCardheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * (detailnevigationcardCount + 1), getResources().getDisplayMetrics());
		
		detailNevigationParams.height = deatilNevigationCardheight;
	}


}
