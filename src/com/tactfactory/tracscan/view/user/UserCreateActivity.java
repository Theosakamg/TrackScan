/**************************************************************************
 * UserCreateActivity.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.user;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * User create Activity.
 *
 * This only contains a UserCreateFragment.
 *
 * @see android.app.Activity
 */
public class UserCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
