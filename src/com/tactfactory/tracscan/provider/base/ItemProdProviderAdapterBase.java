/**************************************************************************
 * ItemProdProviderAdapterBase.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.provider.TracscanProvider;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;

/**
 * ItemProdProviderAdapterBase.
 */
public abstract class ItemProdProviderAdapterBase
				extends ProviderAdapterBase<ItemProd> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ItemProdProviderAdapter";

	/** ITEMPROD_URI. */
	public	  static Uri ITEMPROD_URI;

	/** itemProd type. */
	protected static final String itemProdType =
			"itemprod";

	/** ITEMPROD_ALL. */
	protected static final int ITEMPROD_ALL =
			1242051882;
	/** ITEMPROD_ONE. */
	protected static final int ITEMPROD_ONE =
			1242051883;

	/** ITEMPROD_ORDERCUSTOMER. */
	protected static final int ITEMPROD_ORDERCUSTOMER =
			1242051884;
	/** ITEMPROD_CURRENTZONE. */
	protected static final int ITEMPROD_CURRENTZONE =
			1242051885;

	/**
	 * Static constructor.
	 */
	static {
		ITEMPROD_URI =
				TracscanProvider.generateUri(
						itemProdType);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				itemProdType,
				ITEMPROD_ALL);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				itemProdType + "/#",
				ITEMPROD_ONE);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				itemProdType + "/#/ordercustomer",
				ITEMPROD_ORDERCUSTOMER);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				itemProdType + "/#/currentzone",
				ITEMPROD_CURRENTZONE);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ItemProdProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new ItemProdSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(ITEMPROD_ALL);
		this.uriIds.add(ITEMPROD_ONE);
		this.uriIds.add(ITEMPROD_ORDERCUSTOMER);
		this.uriIds.add(ITEMPROD_CURRENTZONE);
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
			case ITEMPROD_ALL:
				result = collection + "itemprod";
				break;
			case ITEMPROD_ONE:
				result = single + "itemprod";
				break;
			case ITEMPROD_ORDERCUSTOMER:
				result = single + "itemprod";
				break;
			case ITEMPROD_CURRENTZONE:
				result = single + "itemprod";
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
			case ITEMPROD_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ItemProdSQLiteAdapter.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case ITEMPROD_ALL:
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
			case ITEMPROD_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(ItemProdSQLiteAdapter.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							ITEMPROD_URI,
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
		Cursor itemProdCursor;
		

		switch (matchedUri) {

			case ITEMPROD_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case ITEMPROD_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case ITEMPROD_ORDERCUSTOMER:
				itemProdCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (itemProdCursor.getCount() > 0) {
					itemProdCursor.moveToFirst();
					int orderCustomerId = itemProdCursor.getInt(itemProdCursor.getColumnIndex(
									ItemProdSQLiteAdapter.COL_ORDERCUSTOMER));
					
					OrderProdSQLiteAdapter orderProdAdapter = new OrderProdSQLiteAdapter(this.ctx);
					orderProdAdapter.open(this.getDb());
					result = orderProdAdapter.query(orderCustomerId);
				}
				break;

			case ITEMPROD_CURRENTZONE:
				itemProdCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (itemProdCursor.getCount() > 0) {
					itemProdCursor.moveToFirst();
					int currentZoneId = itemProdCursor.getInt(itemProdCursor.getColumnIndex(
									ItemProdSQLiteAdapter.COL_CURRENTZONE));
					
					ZoneSQLiteAdapter zoneAdapter = new ZoneSQLiteAdapter(this.ctx);
					zoneAdapter.open(this.getDb());
					result = zoneAdapter.query(currentZoneId);
				}
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
			case ITEMPROD_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						ItemProdSQLiteAdapter.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case ITEMPROD_ALL:
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
		return ITEMPROD_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = ItemProdSQLiteAdapter.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					ItemProdSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

