/**************************************************************************
 * LogProdProviderUtilsBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
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
import com.tactfactory.tracscan.criterias.ZoneCriterias;
import com.tactfactory.tracscan.criterias.UserCriterias;
import com.tactfactory.tracscan.criterias.ItemProdCriterias;
import com.tactfactory.tracscan.criterias.base.CriteriasBase;
import com.tactfactory.tracscan.criterias.base.CriteriasBase.GroupType;
import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;
import com.tactfactory.tracscan.data.UserSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;

import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ItemState;

import com.tactfactory.tracscan.provider.LogProdProviderAdapter;
import com.tactfactory.tracscan.provider.ZoneProviderAdapter;
import com.tactfactory.tracscan.provider.UserProviderAdapter;
import com.tactfactory.tracscan.provider.ItemProdProviderAdapter;
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

			if (result.getZoneLogged() != null) {
				result.setZoneLogged(
					this.getAssociateZoneLogged(result));
			}
			if (result.getUserLogged() != null) {
				result.setUserLogged(
					this.getAssociateUserLogged(result));
			}
			if (result.getItemLogged() != null) {
				result.setItemLogged(
					this.getAssociateItemLogged(result));
			}
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

	/** Relations operations. */
	/**
	 * Get associate ZoneLogged.
	 * @param item LogProd
	 * @return Zone
	 */
	public Zone getAssociateZoneLogged(
			final LogProd item) {
		Zone result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor zoneCursor = prov.query(
				ZoneProviderAdapter.ZONE_URI,
				ZoneSQLiteAdapter.ALIASED_COLS,
				ZoneSQLiteAdapter.COL_ID + "= ?",
				new String[]{String.valueOf(item.getZoneLogged().getId())},
				null);

		if (zoneCursor.getCount() > 0) {
			zoneCursor.moveToFirst();
			ZoneSQLiteAdapter zoneAdapt =
					new ZoneSQLiteAdapter(this.getContext());
			result = zoneAdapt.cursorToItem(zoneCursor);
		} else {
			result = null;
		}
		zoneCursor.close();

		return result;
	}

	/**
	 * Get associate UserLogged.
	 * @param item LogProd
	 * @return User
	 */
	public User getAssociateUserLogged(
			final LogProd item) {
		User result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userCursor = prov.query(
				UserProviderAdapter.USER_URI,
				UserSQLiteAdapter.ALIASED_COLS,
				UserSQLiteAdapter.COL_ID + "= ?",
				new String[]{String.valueOf(item.getUserLogged().getId())},
				null);

		if (userCursor.getCount() > 0) {
			userCursor.moveToFirst();
			UserSQLiteAdapter userAdapt =
					new UserSQLiteAdapter(this.getContext());
			result = userAdapt.cursorToItem(userCursor);
		} else {
			result = null;
		}
		userCursor.close();

		return result;
	}

	/**
	 * Get associate ItemLogged.
	 * @param item LogProd
	 * @return ItemProd
	 */
	public ItemProd getAssociateItemLogged(
			final LogProd item) {
		ItemProd result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor itemProdCursor = prov.query(
				ItemProdProviderAdapter.ITEMPROD_URI,
				ItemProdSQLiteAdapter.ALIASED_COLS,
				ItemProdSQLiteAdapter.COL_ID + "= ?",
				new String[]{String.valueOf(item.getItemLogged().getId())},
				null);

		if (itemProdCursor.getCount() > 0) {
			itemProdCursor.moveToFirst();
			ItemProdSQLiteAdapter itemProdAdapt =
					new ItemProdSQLiteAdapter(this.getContext());
			result = itemProdAdapt.cursorToItem(itemProdCursor);
		} else {
			result = null;
		}
		itemProdCursor.close();

		return result;
	}

}
