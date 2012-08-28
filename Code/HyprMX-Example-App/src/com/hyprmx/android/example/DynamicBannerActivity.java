package com.hyprmx.android.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hyprmx.android.sdk.HyprMXBanner;
import com.hyprmx.android.sdk.HyprMXHelper;

public class DynamicBannerActivity extends BaseActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_banner);
		final LinearLayout bannerHolder = (LinearLayout)findViewById(R.id.banner_holder);
		Button button = (Button)findViewById(R.id.banner);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HyprMXBanner banner = HyprMXHelper.getInstance().createBanner(DynamicBannerActivity.this);
				banner.setAdjustViewBounds(true);
				banner.setMaxWidth(bannerHolder.getWidth());
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
				bannerHolder.addView(banner, params);
			}

		});
	}

}
