/**************************************************************************
 * OrderProdShowActivity.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.orderprod;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.view.orderprod.OrderProdShowFragment.DeleteCallback;

import android.os.Bundle;

/** OrderProd show Activity.
 *
 * This only contains a OrderProdShowFragment.
 *
 * @see android.app.Activity
 */
public class OrderProdShowActivity 
		extends HarmonyDrawerActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_orderprod_show);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemDeleted() {
		this.finish();
	}
}
