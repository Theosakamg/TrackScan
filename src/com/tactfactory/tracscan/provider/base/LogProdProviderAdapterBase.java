/**************************************************************************
 * LogProdProviderAdapterBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.provider.TracscanProvider;
import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;

/**
 * LogProdProviderAdapterBase.
 */
public abstract class LogProdProviderAdapterBase
				extends ProviderAdapterBase<LogProd> {

	/** TAG for debug purpose. */
	protected static final String TAG = "LogProdProviderAdapter";

	/** LOGPROD_URI. */
	public	  static Uri LOGPROD_URI;

	/** logProd type. */
	protected static final String logProdType =
			"logprod";

	/** LOGPROD_ALL. */
	protected static final int LOGPROD_ALL =
			2006257819;
	/** LOGPROD_ONE. */
	protected static final int LOGPROD_ONE =
			2006257820;


	/**
	 * Static constructor.
	 */
	static {
		LOGPROD_URI =
				TracscanProvider.generateUri(
						logProdType);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				logProdType,
				LOGPROD_ALL);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				logProdType + "/#",
				LOGPROD_ONE);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public LogProdProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new LogProdSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(LOGPROD_ALL);
		this.uriIds.add(LOGPROD_ONE);
	}

	@Override
	public String getType(final Uri uri) {
		String result;
		final String single =
				"vnc.android.cursor.item/"
					+ TracscanProvider.authority + ".";
		final String collection =
				"vnc.android.cursor.collection/"
					+ TracscanProvider.authority + ".";

		int matchedUri = TracscanProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			case LOGPROD_ALL:
				result = collection + "logprod";
				break;
			case LOGPROD_ONE:
				result = single + "logprod";
				break;
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int delete(
			final Uri uri,
			String selection,
			String[] selectionArgs) {
		int matchedUri = TracscanProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case LOGPROD_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = LogProdSQLiteAdapter.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case LOGPROD_ALL:
				result = this.adapter.delete(
							selection,
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}
	
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		int matchedUri = TracscanProviderBase
				.getUriMatcher().match(uri);
		
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case LOGPROD_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(LogProdSQLiteAdapter.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							LOGPROD_URI,
							id);
				}
				break;
			default:
				result = null;
				break;
		}
		return result;
	}

	@Override
	public Cursor query(final Uri uri,
						String[] projection,
						String selection,
						String[] selectionArgs,
						final String sortOrder) {

		int matchedUri = TracscanProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;
		

		switch (matchedUri) {

			case LOGPROD_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case LOGPROD_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int update(
			final Uri uri,
			final ContentValues values,
			String selection,
			String[] selectionArgs) {
		
		
		int matchedUri = TracscanProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			case LOGPROD_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						LogProdSQLiteAdapter.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case LOGPROD_ALL:
				result = this.adapter.update(
							values,
							selection,
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}



	/**
	 * Get the entity URI.
	 * @return The URI
	 */
	@Override
	public Uri getUri() {
		return LOGPROD_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = LogProdSQLiteAdapter.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					LogProdSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

