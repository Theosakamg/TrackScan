/**************************************************************************
 * LogProdEditActivity.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.logprod;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;

import android.os.Bundle;

/** LogProd edit Activity.
 *
 * This only contains a LogProdEditFragment.
 *
 * @see android.app.Activity
 */
public class LogProdEditActivity extends HarmonyDrawerActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_logprod_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
