/**************************************************************************
 * OrderProdProviderAdapterBase.java, tracscan Android
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
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.provider.TracscanProvider;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;

/**
 * OrderProdProviderAdapterBase.
 */
public abstract class OrderProdProviderAdapterBase
				extends ProviderAdapterBase<OrderProd> {

	/** TAG for debug purpose. */
	protected static final String TAG = "OrderProdProviderAdapter";

	/** ORDERPROD_URI. */
	public	  static Uri ORDERPROD_URI;

	/** orderProd type. */
	protected static final String orderProdType =
			"orderprod";

	/** ORDERPROD_ALL. */
	protected static final int ORDERPROD_ALL =
			1612277669;
	/** ORDERPROD_ONE. */
	protected static final int ORDERPROD_ONE =
			1612277670;

	/** ORDERPROD_ITEMS. */
	protected static final int ORDERPROD_ITEMS =
			1612277671;

	/**
	 * Static constructor.
	 */
	static {
		ORDERPROD_URI =
				TracscanProvider.generateUri(
						orderProdType);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				orderProdType,
				ORDERPROD_ALL);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				orderProdType + "/#",
				ORDERPROD_ONE);
		TracscanProvider.getUriMatcher().addURI(
				TracscanProvider.authority,
				orderProdType + "/#/items",
				ORDERPROD_ITEMS);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public OrderProdProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new OrderProdSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(ORDERPROD_ALL);
		this.uriIds.add(ORDERPROD_ONE);
		this.uriIds.add(ORDERPROD_ITEMS);
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
			case ORDERPROD_ALL:
				result = collection + "orderprod";
				break;
			case ORDERPROD_ONE:
				result = single + "orderprod";
				break;
			case ORDERPROD_ITEMS:
				result = collection + "orderprod";
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
			case ORDERPROD_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = OrderProdSQLiteAdapter.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case ORDERPROD_ALL:
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
			case ORDERPROD_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(OrderProdSQLiteAdapter.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							ORDERPROD_URI,
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
		int id = 0;

		switch (matchedUri) {

			case ORDERPROD_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case ORDERPROD_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case ORDERPROD_ITEMS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				ItemProdSQLiteAdapter itemsAdapter = new ItemProdSQLiteAdapter(this.ctx);
				itemsAdapter.open(this.getDb());
				result = itemsAdapter.getByOrderProditemsInternal(id, ItemProdSQLiteAdapter.ALIASED_COLS, selection, selectionArgs, null);
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
			case ORDERPROD_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						OrderProdSQLiteAdapter.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case ORDERPROD_ALL:
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
		return ORDERPROD_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = OrderProdSQLiteAdapter.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					OrderProdSQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

