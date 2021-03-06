/**************************************************************************
 * ZoneSQLiteAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data;

import com.tactfactory.tracscan.data.base.ZoneSQLiteAdapterBase;
import android.content.Context;

/**
 * Zone adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class ZoneSQLiteAdapter extends ZoneSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ZoneSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
