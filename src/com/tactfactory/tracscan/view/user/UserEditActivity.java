/**************************************************************************
 * UserEditActivity.java, tracscan Android
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

/** User edit Activity.
 *
 * This only contains a UserEditFragment.
 *
 * @see android.app.Activity
 */
public class UserEditActivity extends HarmonyFragmentActivity {

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
