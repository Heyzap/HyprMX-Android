package com.hyprmx.android.example;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hyprmx.android.sdk.HyprMXHelper;

public class SplashscreenActivity extends BaseActivity
{
	private static final String TAG = SplashscreenActivity.class.getSimpleName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_splashscreen);
        Button button = (Button)findViewById(R.id.display_splashscreen);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HyprMXHelper.getInstance().displaySplashScreen(SplashscreenActivity.this, null);
			}
		});
    }
    
}
