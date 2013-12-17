/**************************************************************************
 * LogProdSQLiteAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data;

import com.tactfactory.tracscan.data.base.LogProdSQLiteAdapterBase;
import android.content.Context;

/**
 * LogProd adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class LogProdSQLiteAdapter extends LogProdSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogProdSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
