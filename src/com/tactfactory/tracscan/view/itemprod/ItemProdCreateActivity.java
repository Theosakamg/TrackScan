/**************************************************************************
 * ItemProdCreateActivity.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * ItemProd create Activity.
 *
 * This only contains a ItemProdCreateFragment.
 *
 * @see android.app.Activity
 */
public class ItemProdCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_itemprod_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
