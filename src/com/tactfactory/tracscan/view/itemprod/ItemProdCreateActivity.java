/**************************************************************************
 * ItemProdCreateActivity.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;

import android.os.Bundle;

/** 
 * ItemProd create Activity.
 *
 * This only contains a ItemProdCreateFragment.
 *
 * @see android.app.Activity
 */
public class ItemProdCreateActivity extends HarmonyDrawerActivity {

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
