package com.hyprmx.android.example;

import com.hyprmx.android.sdk.HyprMXPresentation;
import com.hyprmx.android.sdk.api.data.OffersAvailableResponse;
import com.hyprmx.android.sdk.utility.OfferHolder;
import com.hyprmx.android.sdk.utility.OfferHolder.OnOffersAvailableResponseReceivedListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NoUIPresentationActivity extends BaseActivity {

	private static final String TAG = BannerActivity.class.getSimpleName();
	private HyprMXPresentation _presentation = null;
	private Button showButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_no_ui_presentation);

		Button prepareButton = (Button) findViewById(R.id.button_prepare);
		showButton = (Button) findViewById(R.id.button_show);
		showButton.setVisibility(View.GONE);

		prepareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showButton.setVisibility(View.GONE);

				_presentation = new HyprMXPresentation();
				_presentation
						.prepare(new OnOffersAvailableResponseReceivedListener() {

							@Override
							public void onOffersAvailable(
									OffersAvailableResponse arg0) {
								showButton.setVisibility(View.VISIBLE);
							}

							@Override
							public void onError(int arg0) {
								Toast.makeText(NoUIPresentationActivity.this,
										"Prepare failed", Toast.LENGTH_SHORT)
										.show();
							}

							@Override
							public void onNoOffersAvailable(
									OffersAvailableResponse response) {
								Toast.makeText(NoUIPresentationActivity.this,
										"No Offers", Toast.LENGTH_SHORT).show();
							}

						});

			}
		});

		showButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				_presentation.show(NoUIPresentationActivity.this);
			}
		});
	}

}
