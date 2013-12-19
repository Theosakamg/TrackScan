/**************************************************************************
 * OrderProdProviderUtilsBase.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
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

import com.tactfactory.tracscan.criterias.OrderProdCriterias;
import com.tactfactory.tracscan.criterias.ItemProdCriterias;
import com.tactfactory.tracscan.criterias.base.Criteria;
import com.tactfactory.tracscan.criterias.base.Criteria.Type;
import com.tactfactory.tracscan.criterias.base.value.ArrayValue;
import com.tactfactory.tracscan.criterias.base.CriteriasBase;
import com.tactfactory.tracscan.criterias.base.CriteriasBase.GroupType;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;

import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ProductType;
import com.tactfactory.tracscan.entity.MaterialType;

import com.tactfactory.tracscan.provider.OrderProdProviderAdapter;
import com.tactfactory.tracscan.provider.ItemProdProviderAdapter;
import com.tactfactory.tracscan.provider.TracscanProvider;

/**
 * OrderProd Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class OrderProdProviderUtilsBase
			extends ProviderUtilsBase<OrderProd> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "OrderProdProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public OrderProdProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final OrderProd item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		OrderProdSQLiteAdapter adapt =
				new OrderProdSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(OrderProdSQLiteAdapter.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				OrderProdProviderAdapter.ORDERPROD_URI)
						.withValues(itemValues)
						.build());

		if (item.getItems() != null && item.getItems().size() > 0) {
			String itemsSelection = ItemProdSQLiteAdapter.COL_ID + " IN (";
			String[] itemsSelectionArgs = new String[item.getItems().size()];
			for (int i = 0; i < item.getItems().size(); i++) {
				itemsSelectionArgs[i] = String.valueOf(item.getItems().get(i).getId());
				itemsSelection += "? ";
				if (i != item.getItems().size() - 1) {
					 itemsSelection += ", ";
				}
			}
			itemsSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(ItemProdProviderAdapter.ITEMPROD_URI)
					.withValueBackReference(
							ItemProdSQLiteAdapter
									.COL_ORDERPRODITEMSINTERNAL,
							0)
					.withSelection(itemsSelection, itemsSelectionArgs)
					.build());
		}

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
	 * @param item OrderProd
	 * @return number of row affected
	 */
	public int delete(final OrderProd item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				OrderProdProviderAdapter.ORDERPROD_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return OrderProd
	 */
	public OrderProd query(final int id) {
		OrderProd result = null;
		OrderProdSQLiteAdapter adapt =
					new OrderProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		OrderProdCriterias crits =
				new OrderProdCriterias(GroupType.AND);
		crits.add(OrderProdSQLiteAdapter.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			OrderProdProviderAdapter.ORDERPROD_URI,
			OrderProdSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

			result.setItems(
				this.getAssociateItems(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<OrderProd>
	 */
	public ArrayList<OrderProd> queryAll() {
		ArrayList<OrderProd> result =
					new ArrayList<OrderProd>();
		OrderProdSQLiteAdapter adapt =
					new OrderProdSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				OrderProdProviderAdapter.ORDERPROD_URI,
				OrderProdSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<OrderProd>
	 */
	public ArrayList<OrderProd> query(
				CriteriasBase<OrderProd> criteria) {
		ArrayList<OrderProd> result =
					new ArrayList<OrderProd>();
		OrderProdSQLiteAdapter adapt =
					new OrderProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				OrderProdProviderAdapter.ORDERPROD_URI,
				OrderProdSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item OrderProd
	 
	 * @return number of rows updated
	 */
	public int update(final OrderProd item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		OrderProdSQLiteAdapter adapt =
				new OrderProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				OrderProdProviderAdapter.ORDERPROD_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		if (item.getItems() != null && item.getItems().size() > 0) {
			// Set new items for OrderProd
			ItemProdCriterias itemsCrit =
						new ItemProdCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(ItemProdSQLiteAdapter.COL_ID);
			crit.addValue(values);
			itemsCrit.add(crit);


			for (int i = 0; i < item.getItems().size(); i++) {
				values.addValue(String.valueOf(
						item.getItems().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					ItemProdProviderAdapter.ITEMPROD_URI)
						.withValue(
								ItemProdSQLiteAdapter
										.COL_ORDERPRODITEMSINTERNAL,
								item.getId())
					.withSelection(
							itemsCrit.toSQLiteSelection(),
							itemsCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated items
			crit.setType(Type.NOT_IN);
			itemsCrit.add(ItemProdSQLiteAdapter.COL_ORDERPRODITEMSINTERNAL,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					ItemProdProviderAdapter.ITEMPROD_URI)
						.withValue(
								ItemProdSQLiteAdapter
										.COL_ORDERPRODITEMSINTERNAL,
								null)
					.withSelection(
							itemsCrit.toSQLiteSelection(),
							itemsCrit.toSQLiteSelectionArgs())
					.build());
		}


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
	 * @param item OrderProd
	 * @return ItemProd
	 */
	public ArrayList<ItemProd> getAssociateItems(
			final OrderProd item) {
		ArrayList<ItemProd> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor itemProdCursor = prov.query(
				ItemProdProviderAdapter.ITEMPROD_URI,
				ItemProdSQLiteAdapter.ALIASED_COLS,
				ItemProdSQLiteAdapter.COL_ORDERPRODITEMSINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		ItemProdSQLiteAdapter itemProdAdapt =
				new ItemProdSQLiteAdapter(this.getContext());
		result = itemProdAdapt.cursorToItems(
						itemProdCursor);
		itemProdCursor.close();

		return result;
	}

}
