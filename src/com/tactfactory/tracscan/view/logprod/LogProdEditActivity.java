/**************************************************************************
 * LogProdEditActivity.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
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
