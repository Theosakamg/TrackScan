/**************************************************************************
 * UserShowActivity.java, tracscan Android
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
import com.tactfactory.tracscan.view.user.UserShowFragment.DeleteCallback;

import android.os.Bundle;

/** User show Activity.
 *
 * This only contains a UserShowFragment.
 *
 * @see android.app.Activity
 */
public class UserShowActivity 
		extends HarmonyFragmentActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_show);
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
