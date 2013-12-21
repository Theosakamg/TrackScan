/**************************************************************************
 * CrudEditDeleteMenuWrapper.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.menu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.tactfactory.tracscan.R;

import com.tactfactory.tracscan.menu.base.MenuWrapperBase;

/**
 * Crud Menu wrapper for edit and delete actions.
 */
public class CrudEditDeleteMenuWrapper implements MenuWrapperBase {
	/** Delete menu item. */
	private MenuItem deleteItem;
	/** Edit menu item. */
	private MenuItem editItem;
	/** Menu Visibility. */
	private boolean visible = true;
	
	@Override
	public void initializeMenu(Menu menu, FragmentActivity activity,
			Fragment fragment, Context ctx) {
		if (fragment != null
				&& fragment instanceof CrudEditDeleteMenuInterface) {
			this.deleteItem 	= menu.add(
					TracscanMenu.CRUDEDITDELETE,
					0,
					Menu.NONE,
					R.string.menu_item_delete);
			this.deleteItem.setShowAsAction(
					ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
					| ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
			this.deleteItem.setVisible(false);
			
			this.editItem 	= menu.add(
					TracscanMenu.CRUDEDITDELETE,
					1,
					Menu.NONE,
					R.string.menu_item_edit);

			this.editItem.setShowAsAction(
					ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
					| ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
			this.editItem.setVisible(false);
		}
	}

	@Override
	public void updateMenu(Menu menu, FragmentActivity activity,
			Fragment fragment, Context ctx) {
		if (fragment != null 
				&& fragment instanceof CrudEditDeleteMenuInterface) {
			menu.setGroupVisible(
					TracscanMenu.CRUDEDITDELETE, this.visible);
		}
	}

	@Override
	public boolean dispatch(MenuItem item, Context ctx, Fragment fragment) {
		boolean result = false;
		if (fragment != null 
				&& fragment instanceof CrudEditDeleteMenuInterface) {
			if (item.equals(this.deleteItem)) {
				((CrudEditDeleteMenuInterface) fragment).onClickDelete();
				result = true;
			} else if (item.equals(this.editItem)) {
				((CrudEditDeleteMenuInterface) fragment).onClickEdit();
				result = true;
			}
		}
		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent data, Context ctx, Fragment fragment) {
		// We don't need this.
	}

	@Override
	public void clear(Menu menu, FragmentActivity activity,
			Fragment fragment, Context ctx) {
		
		if (fragment != null 
				&& fragment instanceof CrudEditDeleteMenuInterface) {
			menu.removeGroup(TracscanMenu.CRUDEDITDELETE);
		}
	}

	@Override
	public void hide(Menu menu, FragmentActivity activity, Fragment fragment,
			Context ctx) {
		this.visible = false;
	}

	@Override
	public void show(Menu menu, FragmentActivity activity, Fragment fragment,
			Context ctx) {
		this.visible = true;
	}

	/**
	 * Implement this interface in your activity or fragment
	 * to have edit and delete menu buttons.
	 */
	public interface CrudEditDeleteMenuInterface {
		/** On click edit. */
		void onClickEdit();
		/** On click delete. */
		void onClickDelete();
	}
}


