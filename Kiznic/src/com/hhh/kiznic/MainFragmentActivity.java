package com.hhh.kiznic;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainFragmentActivity extends FragmentActivity implements ActionBar.TabListener{

	SectionsPagerAdapter mSectionsPagerAdapter;

	public static ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_fragment);
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.kiznic_title_bar);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
	
		/////////////////////////////////////////////////
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		/////////////////////////////////////////////////
		
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			
		
			actionBar.addTab(actionBar.newTab().setTabListener(this));
			
		}
		for(int i=0;i<4;i++){
			actionBar.addTab(actionBar.newTab().setTabListener(this));
		}
		
		actionBar.getTabAt(0).setCustomView(R.layout.kiznic_title_home_tab);
		actionBar.getTabAt(1).setCustomView(R.layout.kiznic_title_search_tab);
		actionBar.getTabAt(2).setCustomView(R.layout.kiznic_title_mypage_tab);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//View rootView = inflater.inflate(R.layout.kiznic_title_bar,
					//container, false);
			//return rootView;
			
			return null;
		}
	}
	
	@SuppressLint("DefaultLocale")
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		Context mContext;

		public SectionsPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
			case 0:
				return new MainActivity(mContext);
			case 1:
				return new SearchActivity(mContext);
			case 2:
				return new MyPageActivity(mContext);
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}
		
		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position){
			
			switch(position){
			case 0:
				return "a";
			case 1:
				return "b";
			case 2:
				return "c";
			}
			
			return null;
		}
	}
}
