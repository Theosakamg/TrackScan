/**************************************************************************
 * OrderProdProviderAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.tracscan.provider.base.OrderProdProviderAdapterBase;

/**
 * OrderProdProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class OrderProdProviderAdapter
					extends OrderProdProviderAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public OrderProdProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public OrderProdProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
	}
}

