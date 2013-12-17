/**************************************************************************
 * MenuWrapperBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.menu;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * MenuWrapperBase.
 * This interface is used to declare your menu groups easily.
 * Please see examples to understand how this works.
 */
public interface MenuWrapperBase {
	/**
	 * Menu initialization.
	 * @param menu The menu object
	 * @param activity The current activity.
	 * @param fragment The current fragment.
	 * @param ctx The context
	 */
	void initializeMenu(Menu menu,
			Activity activity,
			Fragment fragment,
			Context ctx);

	/**
	 * Menu update.
	 * @param menu The menu object
	 * @param activity The current activity.
	 * @param fragment The current fragment.
	 * @param ctx The context
	 */
	void updateMenu(Menu menu,
										Activity activity,
										Fragment fragment,
										Context ctx);

	/**
	 * Menu dispatch.
	 * Called when user clicked on menu.
	 * @param item The menu item clicked
	 * @param ctx The context
	 * @param fragment The fragment in which it has been clicked
	 * @return true if event has been treated
	 */
	boolean dispatch(MenuItem item,
										 Context ctx,
									     Fragment fragment);

	/**
	 * Menu onActivityResult.
	 * Used when one of your menu call another
	 * activity with startActivityOnResult().
	 * @param requestCode the requestCode
	 * @param resultCode the resultCode
	 * @param data The intent
	 * @param ctx The context
	 * @param fragment The fragment
	 */
	void onActivityResult(int requestCode,
										      int resultCode,
											  Intent data,
											  Context ctx,
											  Fragment fragment);


	/**
	 * Menu clear.
	 * @param menu The menu object
	 * @param activity The current activity.
	 * @param fragment The current fragment.
	 * @param ctx The context
	 */
	void clear(Menu menu,
			Activity activity,
			Fragment fragment,
			Context ctx);
}
