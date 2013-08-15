package com.hyprmx.android.example;

import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.hyprmx.android.sdk.HyprMXHelper;
import com.hyprmx.android.sdk.HyprMXReward;

public class ExampleApplication extends Application {

	static final String PREFS = "prefs";

	static final String DISTRIBUTOR_ID_KEY = "distributor_id";
	static final String PROPERTY_ID_KEY = "proprety_id";
	static final String USER_ID_KEY = "user_id";
	static final String BASE_URL_KEY = "base_url";

	private static final String DEFAULT_DISTRIBUTOR_ID = "-1";
	private static final String DEFAULT_PROPERTY_ID = "1111";
	private static final String DEFAULT_USER_ID = "hyprmx_tt_android_test";

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		if(prefs.getString(DISTRIBUTOR_ID_KEY, null) == null) {
			editor.putString(DISTRIBUTOR_ID_KEY, DEFAULT_DISTRIBUTOR_ID);
			editor.putString(PROPERTY_ID_KEY, DEFAULT_PROPERTY_ID);
			editor.putString(USER_ID_KEY, DEFAULT_USER_ID);
		}
		editor.commit();

		// initialize the helper for this process.
		HyprMXHelper.getInstance(this, prefs.getString(DISTRIBUTOR_ID_KEY, null), prefs.getString(PROPERTY_ID_KEY, null), prefs.getString(USER_ID_KEY, null));
		setRewards();
		HashMap<String, String> information = new HashMap<String, String >();
// If you know the user's birthdate, save them the effort of entering it manually like this:
//		information.put("dob", "1983-07-08");
// If you don't know the user's birthdate but do know their current age, save them the effort of entering it manually like this:
//		information.put("age", "20"); // Don't send age if you're also sending date of birth
// The same pattern applies to gender:
//		information.put("gender", "m");
		HyprMXHelper.getInstance().setRequiredInformation(information);
	}
	
	public void refreshHyprMXHelper() {
		SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
		HyprMXHelper.resetInstance();
		HyprMXHelper.getInstance(this, prefs.getString(DISTRIBUTOR_ID_KEY, null), prefs.getString(PROPERTY_ID_KEY, null), prefs.getString(USER_ID_KEY, null));
		setRewards();
	}
	
	private void setRewards() {
		HyprMXReward rewards[] = new HyprMXReward[2];
		rewards[0] = new HyprMXReward(0, 0.01f, 1, "Cent", -1);
		rewards[1] = new HyprMXReward(1, 0.005f, 1000, "Half a Cent", android.R.drawable.btn_star);
		HyprMXHelper.getInstance().setRewards(rewards);
	}
}
