/**************************************************************************
 * LogProdProviderUtilsBase.java, tracscan Android
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

import com.tactfactory.tracscan.criterias.LogProdCriterias;
import com.tactfactory.tracscan.criterias.base.CriteriasBase;
import com.tactfactory.tracscan.criterias.base.CriteriasBase.GroupType;
import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;

import com.tactfactory.tracscan.entity.LogProd;

import com.tactfactory.tracscan.provider.LogProdProviderAdapter;
import com.tactfactory.tracscan.provider.TracscanProvider;

/**
 * LogProd Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class LogProdProviderUtilsBase
			extends ProviderUtilsBase<LogProd> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "LogProdProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public LogProdProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final LogProd item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		LogProdSQLiteAdapter adapt =
				new LogProdSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(LogProdSQLiteAdapter.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				LogProdProviderAdapter.LOGPROD_URI)
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
	 * @param item LogProd
	 * @return number of row affected
	 */
	public int delete(final LogProd item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				LogProdProviderAdapter.LOGPROD_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return LogProd
	 */
	public LogProd query(final int id) {
		LogProd result = null;
		LogProdSQLiteAdapter adapt =
					new LogProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		LogProdCriterias crits =
				new LogProdCriterias(GroupType.AND);
		crits.add(LogProdSQLiteAdapter.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			LogProdProviderAdapter.LOGPROD_URI,
			LogProdSQLiteAdapter.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<LogProd>
	 */
	public ArrayList<LogProd> queryAll() {
		ArrayList<LogProd> result =
					new ArrayList<LogProd>();
		LogProdSQLiteAdapter adapt =
					new LogProdSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				LogProdProviderAdapter.LOGPROD_URI,
				LogProdSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<LogProd>
	 */
	public ArrayList<LogProd> query(
				CriteriasBase<LogProd> criteria) {
		ArrayList<LogProd> result =
					new ArrayList<LogProd>();
		LogProdSQLiteAdapter adapt =
					new LogProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				LogProdProviderAdapter.LOGPROD_URI,
				LogProdSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item LogProd
	 
	 * @return number of rows updated
	 */
	public int update(final LogProd item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		LogProdSQLiteAdapter adapt =
				new LogProdSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				LogProdProviderAdapter.LOGPROD_URI,
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

	
}
