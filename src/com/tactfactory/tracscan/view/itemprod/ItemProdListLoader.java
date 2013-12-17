/**************************************************************************
 * ItemProdListLoader.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.tactfactory.tracscan.criterias.ItemProdCriterias;

/**
 * ItemProd Loader.
 */
public class ItemProdListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit ItemProdCriterias
	 */
	public ItemProdListLoader(
			final Context ctx,
			final ItemProdCriterias crit) {
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
	public ItemProdListLoader(
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
	 * @param criterias ItemProdCriterias
	 * @param sortOrder The sort order
	 */
	public ItemProdListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					ItemProdCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
