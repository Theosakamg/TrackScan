/**************************************************************************
 * OrderProdEditActivity.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.orderprod;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;

import android.os.Bundle;

/** OrderProd edit Activity.
 *
 * This only contains a OrderProdEditFragment.
 *
 * @see android.app.Activity
 */
public class OrderProdEditActivity extends HarmonyDrawerActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_orderprod_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
