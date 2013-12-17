/**************************************************************************
 * TracscanSQLiteOpenHelper.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data;

import com.tactfactory.tracscan.data.base.TracscanSQLiteOpenHelperBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class TracscanSQLiteOpenHelper
					extends TracscanSQLiteOpenHelperBase {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param name name
	 * @param factory factory
	 * @param version version
	 */
	public TracscanSQLiteOpenHelper(final Context ctx,
		   final String name, final CursorFactory factory, final int version) {
		super(ctx, name, factory, version);
	}

}
