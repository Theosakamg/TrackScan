/**************************************************************************
 * ZoneCreateActivity.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.zone;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;

import android.os.Bundle;

/** 
 * Zone create Activity.
 *
 * This only contains a ZoneCreateFragment.
 *
 * @see android.app.Activity
 */
public class ZoneCreateActivity extends HarmonyDrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_zone_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
