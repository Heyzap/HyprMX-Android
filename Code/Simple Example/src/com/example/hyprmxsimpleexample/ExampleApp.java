package com.example.hyprmxsimpleexample;

import com.hyprmx.android.sdk.HyprMXHelper;

import android.app.Application;

public class ExampleApp extends Application {

	static final String distributorId = "-74";
	static final String propertyId = "1111";
	String userId = "MyUniqueUserId2";
	
	@Override
	public void onCreate() {
		super.onCreate();
		HyprMXHelper.getInstance(this, distributorId, propertyId, userId); // This only needs to happen once, but won't hurt.
	}

}
