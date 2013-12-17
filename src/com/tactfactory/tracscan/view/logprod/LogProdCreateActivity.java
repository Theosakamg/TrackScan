/**************************************************************************
 * LogProdCreateActivity.java, tracscan Android
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

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * LogProd create Activity.
 *
 * This only contains a LogProdCreateFragment.
 *
 * @see android.app.Activity
 */
public class LogProdCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_logprod_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
