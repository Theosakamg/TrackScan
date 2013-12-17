/**************************************************************************
 * ItemProdSQLiteAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data;

import com.tactfactory.tracscan.data.base.ItemProdSQLiteAdapterBase;
import android.content.Context;

/**
 * ItemProd adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class ItemProdSQLiteAdapter extends ItemProdSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ItemProdSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
