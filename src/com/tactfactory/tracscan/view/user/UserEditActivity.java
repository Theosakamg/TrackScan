/**************************************************************************
 * UserEditActivity.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.user;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;

import android.os.Bundle;

/** User edit Activity.
 *
 * This only contains a UserEditFragment.
 *
 * @see android.app.Activity
 */
public class UserEditActivity extends HarmonyDrawerActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
