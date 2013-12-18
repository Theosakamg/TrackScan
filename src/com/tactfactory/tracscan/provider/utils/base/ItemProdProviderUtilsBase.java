/**************************************************************************
 * ItemProdProviderUtilsBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.tactfactory.tracscan.criterias.ItemProdCriterias;
import com.tactfactory.tracscan.criterias.OrderProdCriterias;
import com.tactfactory.tracscan.criterias.base.CriteriasBase;
import com.tactfactory.tracscan.criterias.base.CriteriasBase.GroupType;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;

import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.OrderProd;

import com.tactfactory.tracscan.provider.ItemProdProviderAdapter;
import com.tactfactory.tracscan.provider.OrderProdProviderAdapter;
import com.tactfactory.tracscan.provider.TracscanProvider;

/**
 * ItemProd Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ItemProdProviderUtilsBase
			extends ProviderUtilsBase<ItemProd> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "ItemProdProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public ItemProdProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final ItemProd item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ItemProdSQLiteAdapter adapt =
				new ItemProdSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(ItemProdSQLiteAdapter.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				ItemProdProviderAdapter.ITEMPROD_URI)
						.withValues(itemValues)
						.build());


		try {
			ContentProviderResult[] results = 
					prov.applyBatch(TracscanProvider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * Insert into DB.
	 * @param item ItemProd to insert
	 * @param orderProditemsInternalId orderProditemsInternal Id
	 * @return number of rows affected
	 */
	public Uri insert(final ItemProd item,
							 final int orderProditemsInternalId) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ItemProdSQLiteAdapter adapt =
				new ItemProdSQLiteAdapter(this.getContext());
		ContentValues itemValues = adapt.itemToContentValues(item,
					orderProditemsInternalId);
		itemValues.remove(ItemProdSQLiteAdapter.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				ItemProdProviderAdapter.ITEMPROD_URI)
			    	.withValues(itemValues)
			    	.build());



		try {
			ContentProviderResult[] results =
				prov.applyBatch(TracscanProvider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * Delete from DB.
	 * @param item ItemProd
	 * @return number of row affected
	 */
	public int delete(final ItemProd item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				ItemProdProviderAdapter.ITEMPROD_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return ItemProd
	 */
	public ItemProd query(final int id) {
		ItemProd result = null;
		ItemProdSQLiteAdapter adapt =
					new ItemProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		ItemProdCriterias crits =
				new ItemProdCriterias(GroupType.AND);
		crits.add(ItemProdSQLiteAdapter.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			ItemProdProviderAdapter.ITEMPROD_URI,
			ItemProdSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

				result.setOrder(
					this.getAssociateItems(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<ItemProd>
	 */
	public ArrayList<ItemProd> queryAll() {
		ArrayList<ItemProd> result =
					new ArrayList<ItemProd>();
		ItemProdSQLiteAdapter adapt =
					new ItemProdSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ItemProdProviderAdapter.ITEMPROD_URI,
				ItemProdSQLiteAdapter.ALIASED_COLS,
				null,
				null,
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<ItemProd>
	 */
	public ArrayList<ItemProd> query(
				CriteriasBase<ItemProd> criteria) {
		ArrayList<ItemProd> result =
					new ArrayList<ItemProd>();
		ItemProdSQLiteAdapter adapt =
					new ItemProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				ItemProdProviderAdapter.ITEMPROD_URI,
				ItemProdSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item ItemProd
	 * @return number of rows updated
	 */
	public int update(final ItemProd item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ItemProdSQLiteAdapter adapt =
				new ItemProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				ItemProdProviderAdapter.ITEMPROD_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		try {
			ContentProviderResult[] results = prov.applyBatch(TracscanProvider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item ItemProd
	 * @param orderProditemsInternalId orderProditemsInternal Id
	 * @return number of rows updated
	 */
	public int update(final ItemProd item,
							 final int orderProditemsInternalId) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ItemProdSQLiteAdapter adapt =
				new ItemProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item,
				orderProditemsInternalId);

		Uri uri = Uri.withAppendedPath(
				ItemProdProviderAdapter.ITEMPROD_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());



		try {
			ContentProviderResult[] results = prov.applyBatch(TracscanProvider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/** Relations operations. */
	/**
	 * Get associate Items.
	 * @param item ItemProd
	 * @return OrderProd
	 */
	public OrderProd getAssociateItems(
			final ItemProd item) {
		OrderProd result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor orderProdCursor = prov.query(
				OrderProdProviderAdapter.ORDERPROD_URI,
				OrderProdSQLiteAdapter.ALIASED_COLS,
				OrderProdSQLiteAdapter.COL_ID + "= ?",
				new String[]{String.valueOf(item.getOrder().getId())},
				null);

		if (orderProdCursor.getCount() > 0) {
			orderProdCursor.moveToFirst();
			OrderProdSQLiteAdapter orderProdAdapt =
					new OrderProdSQLiteAdapter(this.getContext());
			result = orderProdAdapt.cursorToItem(orderProdCursor);
		} else {
			result = null;
		}
		orderProdCursor.close();

		return result;
	}

}
