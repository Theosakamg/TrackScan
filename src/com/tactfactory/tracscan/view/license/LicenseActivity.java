package com.tactfactory.tracscan.view.license;

import android.os.Bundle;

import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;

public class LicenseActivity extends HarmonyFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_license);

		// Google Analytics
		//GoogleAnalyticsSessionManager.getInstance(getApplication())
		//        	.incrementActivityCount();
	}
}

