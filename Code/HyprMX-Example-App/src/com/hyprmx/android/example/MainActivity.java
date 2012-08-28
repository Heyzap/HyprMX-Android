package com.hyprmx.android.example;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	@SuppressWarnings("rawtypes")
	static private Class[] _launchItems = new Class[] { BannerActivity.class, ButtonActivity.class, SplashscreenActivity.class, NoUIPresentationActivity.class,
			DynamicButtonActivity.class, DynamicBannerActivity.class, AdvancedOfferListActivity.class, SettingsActivity.class };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(MainActivity.class.getSimpleName(), "Entered HyprMX Test application");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_items)));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if(position < _launchItems.length) {
			Intent i = new Intent(this, _launchItems[position]);
			startActivity(i);
		}
	}

	
}
