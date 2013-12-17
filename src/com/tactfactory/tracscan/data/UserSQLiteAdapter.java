/**************************************************************************
 * UserSQLiteAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data;

import com.tactfactory.tracscan.data.base.UserSQLiteAdapterBase;
import android.content.Context;

/**
 * User adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class UserSQLiteAdapter extends UserSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
