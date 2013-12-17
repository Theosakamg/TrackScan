/**************************************************************************
 * ZoneEditActivity.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.zone;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Zone edit Activity.
 *
 * This only contains a ZoneEditFragment.
 *
 * @see android.app.Activity
 */
public class ZoneEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_zone_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
