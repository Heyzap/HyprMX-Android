package com.hyprmx.android.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hyprmx.android.sdk.HyprMXButton;
import com.hyprmx.android.sdk.HyprMXButton.ButtonSize;
import com.hyprmx.android.sdk.HyprMXHelper;


public class DynamicButtonActivity extends BaseActivity {

	private static int buttonIds = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_button);
		final LinearLayout buttonHolder = (LinearLayout)findViewById(R.id.button_holder);
		Button button = (Button)findViewById(R.id.small);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HyprMXButton button = HyprMXHelper.getInstance().createButton(DynamicButtonActivity.this, ButtonSize.SMALL);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
				button.setId(buttonIds++);
				buttonHolder.addView(button, params);
			}

		});

		button = (Button)findViewById(R.id.large);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HyprMXButton button = HyprMXHelper.getInstance().createButton(DynamicButtonActivity.this, ButtonSize.LARGE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
				button.setId(buttonIds++);
				buttonHolder.addView(button, params);
			}

		});
		
	}

}
