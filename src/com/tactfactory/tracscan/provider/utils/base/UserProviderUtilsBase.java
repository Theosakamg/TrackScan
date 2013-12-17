/**************************************************************************
 * UserProviderUtilsBase.java, tracscan Android
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

import com.tactfactory.tracscan.criterias.UserCriterias;
import com.tactfactory.tracscan.criterias.base.CriteriasBase;
import com.tactfactory.tracscan.criterias.base.CriteriasBase.GroupType;
import com.tactfactory.tracscan.data.UserSQLiteAdapter;

import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.AccountType;

import com.tactfactory.tracscan.provider.UserProviderAdapter;
import com.tactfactory.tracscan.provider.TracscanProvider;

/**
 * User Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class UserProviderUtilsBase
			extends ProviderUtilsBase<User> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "UserProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public UserProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final User item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		UserSQLiteAdapter adapt =
				new UserSQLiteAdapter(this.getContext());


		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(UserSQLiteAdapter.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				UserProviderAdapter.USER_URI)
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
	 * @param item User
	 * @return number of row affected
	 */
	public int delete(final User item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				UserProviderAdapter.USER_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return User
	 */
	public User query(final int id) {
		User result = null;
		UserSQLiteAdapter adapt =
					new UserSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		UserCriterias crits =
				new UserCriterias(GroupType.AND);
		crits.add(UserSQLiteAdapter.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			UserProviderAdapter.USER_URI,
			UserSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<User>
	 */
	public ArrayList<User> queryAll() {
		ArrayList<User> result =
					new ArrayList<User>();
		UserSQLiteAdapter adapt =
					new UserSQLiteAdapter(this.getContext());
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				UserProviderAdapter.USER_URI,
				UserSQLiteAdapter.ALIASED_COLS,
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
	 * @return ArrayList<User>
	 */
	public ArrayList<User> query(
				CriteriasBase<User> criteria) {
		ArrayList<User> result =
					new ArrayList<User>();
		UserSQLiteAdapter adapt =
					new UserSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				UserProviderAdapter.USER_URI,
				UserSQLiteAdapter.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = adapt.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item User
	 
	 * @return number of rows updated
	 */
	public int update(final User item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		UserSQLiteAdapter adapt =
				new UserSQLiteAdapter(this.getContext());
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = adapt.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				UserProviderAdapter.USER_URI,
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
