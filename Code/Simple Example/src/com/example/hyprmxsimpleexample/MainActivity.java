package com.example.hyprmxsimpleexample;

import com.hyprmx.android.activities.HyprMXActivity;
import com.hyprmx.android.sdk.HyprMXHelper;
import com.hyprmx.android.sdk.HyprMXPresentation;
import com.hyprmx.android.sdk.api.data.Offer;
import com.hyprmx.android.sdk.api.data.OffersAvailableResponse;
import com.hyprmx.android.sdk.utility.OfferHolder.OnOffersAvailableResponseReceivedListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends HyprMXActivity {
	
	HyprMXPresentation _presentation = null;
	Button showButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showButton = (Button) findViewById(R.id.show_button);
		showButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_presentation.show(MainActivity.this);
			}
		});
		
		HyprMXHelper.getInstance().deliverPendingRewards(this);
	}
	
	private void refresh() {
		showButton.setEnabled(false);
		_presentation = new HyprMXPresentation();
		_presentation.prepare(new OnOffersAvailableResponseReceivedListener() {
			
			@Override
			public void onOffersAvailable(OffersAvailableResponse arg0) {
				showButton.setEnabled(true);
			}
			
			@Override
			public void onNoOffersAvailable(OffersAvailableResponse arg0) {
				Toast.makeText(MainActivity.this, "No Offers Available", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onError(int arg0) {
				Toast.makeText(MainActivity.this, "Error! " + arg0, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onResume() {
		refresh();
		
		super.onResume();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Only one option, refresh
		refresh();
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onNoContentAvailable() {
		Toast.makeText(MainActivity.this, "onNoContentAvailable();", Toast.LENGTH_LONG).show();
	}



	@Override
	public void onOfferCancelled(Offer arg0) {
		Toast.makeText(MainActivity.this, "onOfferCanceled(" + arg0 + ");", Toast.LENGTH_LONG).show();
	}



	@Override
	public void onOfferCompleted(Offer arg0) {
		Toast.makeText(MainActivity.this, "onOfferCompleted(" + arg0 + ");", Toast.LENGTH_LONG).show();
	}



	@Override
	public void onUserOptedOut() {
		Toast.makeText(MainActivity.this, "onUserOptedOut();", Toast.LENGTH_LONG).show();
	}

}
