package com.hyprmx.android.example;

import android.util.Log;
import android.widget.TextView;

import com.hyprmx.android.activities.HyprMXActivity;
import com.hyprmx.android.sdk.api.data.Offer;

public class BaseActivity extends HyprMXActivity {

	@Override
	public void onNoContentAvailable() {
		TextView view = (TextView)findViewById(R.id.result);
		if(view != null) {
			view.setText("No content available");
		}
	}

	@Override
	public void onOfferCancelled(Offer arg0) {
		TextView view = (TextView)findViewById(R.id.result);
		if(view != null) {
			view.setText("Offer cancelled");
		}
	}

	@Override
	public void onOfferCompleted(Offer arg0) {
		TextView view = (TextView)findViewById(R.id.result);
		if(view != null) {
			if (arg0 == null) {
				view.setText("Offer completed! offer var is null?");
			} else {
				Log.v("BaseActivity", "Offer Complete. Transaction ID: " + arg0.getTransactionId());
				view.setText("Offer completed! Reward Id: " + String.valueOf(arg0.getRewardId()) + " Quantity: " + String.valueOf(arg0.getRewardQuantity()));
			}
		}
	}

	@Override
	public void onUserOptedOut() {
		TextView view = (TextView)findViewById(R.id.result);
		if(view != null) {
			view.setText("User opted out");
		}
	}

}
