package com.hyprmx.android.example;

import android.os.Bundle;

public class ButtonActivity extends BaseActivity
{	
	private static final String TAG = ButtonActivity.class.getSimpleName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_button);
    }
}
