/**************************************************************************
 * ZoneProviderAdapterBase.java, tracscan Android
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

import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.provider.TracscanProvider;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;

/**
 * ZoneProviderAdapterBase.
 */
public abstract class ZoneProviderAdapterBase
				extends ProviderAdapterBase<Zone> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ZoneProviderAdapter";

	/** ZONE_URI. */
	public	  static Uri ZONE_URI;

	/** zone type. */
	protected static final String zoneType =
			"zone";

	/** ZONE_ALL. */
	protected static final int ZONE_ALL =
			2791372;
	/** ZONE_ONE. */
	protected static final int ZONE_ONE =
			2791373;


	/**
	 * Static constructor.
	 */
	static {
		ZONE_URI =
				TracscanProvider.generateUri(
						zoneType);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				zoneType,
				ZONE_ALL);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				zoneType + "/#",
				ZONE_ONE);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ZoneProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new ZoneSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(ZONE_ALL);
		this.uriIds.add(ZONE_ONE);
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
			case ZONE_ALL:
				result = collection + "zone";
				break;
			case ZONE_ONE:
				result = single + "zone";
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
			case ZONE_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ZoneSQLiteAdapter.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case ZONE_ALL:
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
			case ZONE_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(ZoneSQLiteAdapter.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							ZONE_URI,
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

			case ZONE_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case ZONE_ONE:
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
			case ZONE_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						ZoneSQLiteAdapter.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case ZONE_ALL:
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
		return ZONE_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = ZoneSQLiteAdapter.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					ZoneSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

