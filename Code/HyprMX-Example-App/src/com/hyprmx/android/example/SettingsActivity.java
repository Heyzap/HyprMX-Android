package com.hyprmx.android.example;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.hyprmx.android.sdk.HyprMXHelper;

public class SettingsActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		SharedPreferences prefs = getSharedPreferences(ExampleApplication.PREFS, Context.MODE_PRIVATE);
		
		EditText editText = (EditText)findViewById(R.id.prop);
		editText.setText(prefs.getString(ExampleApplication.PROPERTY_ID_KEY, null));
		editText = (EditText)findViewById(R.id.dist);
		editText.setText(prefs.getString(ExampleApplication.DISTRIBUTOR_ID_KEY, null));
		editText = (EditText)findViewById(R.id.uid);
		editText.setText(prefs.getString(ExampleApplication.USER_ID_KEY, null));
		editText = (EditText)findViewById(R.id.persistent);
		editText.setText(HyprMXHelper.getInstance().getDeviceId());

		Button button = (Button)findViewById(R.id.reset);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HyprMXHelper.getInstance().resetDeviceID();
				EditText editText = (EditText)findViewById(R.id.persistent);
				editText.setText(HyprMXHelper.getInstance().getDeviceId());
				ExampleApplication app = (ExampleApplication)getApplication();
				app.refreshHyprMXHelper();
			}
		});
		
		button = (Button)findViewById(R.id.reset_uid);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uid = UUID.randomUUID().toString();
				
				SharedPreferences.Editor editor = getSharedPreferences(ExampleApplication.PREFS, Context.MODE_PRIVATE).edit();
				editor.putString(ExampleApplication.USER_ID_KEY, uid);
				editor.commit();
				
				ExampleApplication app = (ExampleApplication)getApplication();
				app.refreshHyprMXHelper();
				EditText editText = (EditText)findViewById(R.id.uid);
				editText.setText(HyprMXHelper.getInstance().getUserId());
			}
		});

		button = (Button)findViewById(R.id.clear);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HyprMXHelper.getInstance().resetSettings();
				ExampleApplication app = (ExampleApplication)getApplication();
				app.refreshHyprMXHelper();
			}
		});

		button = (Button)findViewById(R.id.save);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences prefs = getSharedPreferences(ExampleApplication.PREFS, Context.MODE_PRIVATE);
				EditText editText = (EditText)findViewById(R.id.uid);
				String userId = editText.getText().toString();
				// Reset settings if UserID changed
				if (userId != prefs.getString(ExampleApplication.USER_ID_KEY, null)) {
					HyprMXHelper.getInstance().resetSettings();
				}
				SharedPreferences.Editor editor = prefs.edit();
				if(userId != null && userId.length() > 0) {
					editor.putString(ExampleApplication.USER_ID_KEY, userId);
				} else {
					editor.remove(ExampleApplication.USER_ID_KEY);
				}
				editText = (EditText)findViewById(R.id.prop);
				editor.putString(ExampleApplication.PROPERTY_ID_KEY, editText.getText().toString());
				editText = (EditText)findViewById(R.id.dist);
				editor.putString(ExampleApplication.DISTRIBUTOR_ID_KEY, editText.getText().toString());
				editor.commit();

				ExampleApplication app = (ExampleApplication)getApplication();
				app.refreshHyprMXHelper();
				finish();
			}
		});
	}


}
