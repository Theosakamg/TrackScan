/**************************************************************************
 * ItemProdEditActivity.java, tracscan Android
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

/** ItemProd edit Activity.
 *
 * This only contains a ItemProdEditFragment.
 *
 * @see android.app.Activity
 */
public class ItemProdEditActivity extends HarmonyDrawerActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_itemprod_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
