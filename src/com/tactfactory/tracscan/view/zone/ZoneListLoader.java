/**************************************************************************
 * ZoneListLoader.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.zone;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.tactfactory.tracscan.criterias.ZoneCriterias;

/**
 * Zone Loader.
 */
public class ZoneListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit ZoneCriterias
	 */
	public ZoneListLoader(
			final Context ctx,
			final ZoneCriterias crit) {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param selection The selection
	 * @param selectionArgs The selection Args
	 * @param sortOrder The sort order
	 */
	public ZoneListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					String selection,
					String[] selectionArgs,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				selection,
				selectionArgs,
				sortOrder);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param criterias ZoneCriterias
	 * @param sortOrder The sort order
	 */
	public ZoneListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					ZoneCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
