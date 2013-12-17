/**************************************************************************
 * UserSQLiteAdapterBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data.base;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tactfactory.tracscan.data.UserSQLiteAdapter;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.AccountType;


import com.tactfactory.tracscan.TracscanApplication;


/** User adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UserAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UserSQLiteAdapterBase
						extends SQLiteAdapterBase<User> {

	/** TAG for debug purpose. */
	protected static final String TAG = "UserDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "User";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id. */
	public static final String COL_ID =
			"id";
	/** Alias. */
	public static final String ALIASED_COL_ID =
			TABLE_NAME + "." + COL_ID;
	/** type. */
	public static final String COL_TYPE =
			"type";
	/** Alias. */
	public static final String ALIASED_COL_TYPE =
			TABLE_NAME + "." + COL_TYPE;
	/** login. */
	public static final String COL_LOGIN =
			"login";
	/** Alias. */
	public static final String ALIASED_COL_LOGIN =
			TABLE_NAME + "." + COL_LOGIN;
	/** passwd. */
	public static final String COL_PASSWD =
			"passwd";
	/** Alias. */
	public static final String ALIASED_COL_PASSWD =
			TABLE_NAME + "." + COL_PASSWD;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		UserSQLiteAdapter.COL_ID,
		UserSQLiteAdapter.COL_TYPE,
		UserSQLiteAdapter.COL_LOGIN,
		UserSQLiteAdapter.COL_PASSWD
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		UserSQLiteAdapter.ALIASED_COL_ID,
		UserSQLiteAdapter.ALIASED_COL_TYPE,
		UserSQLiteAdapter.ALIASED_COL_LOGIN,
		UserSQLiteAdapter.ALIASED_COL_PASSWD
	};

	/**
	 * Get the table name used in DB for your User entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your User entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the User entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
		
		 + COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_TYPE	+ " VARCHAR NOT NULL,"
		 + COL_LOGIN	+ " VARCHAR NOT NULL,"
		 + COL_PASSWD	+ " VARCHAR NOT NULL"
		
		
		+ ", UNIQUE(" + COL_LOGIN + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert User entity to Content Values for database.
	 * @param item User entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final User item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID,
			String.valueOf(item.getId()));

		if (item.getType() != null) {
			result.put(COL_TYPE,
				item.getType().getValue());
		}

		if (item.getLogin() != null) {
			result.put(COL_LOGIN,
				item.getLogin());
		}

		if (item.getPasswd() != null) {
			result.put(COL_PASSWD,
				item.getPasswd());
		}


		return result;
	}

	/**
	 * Convert Cursor of database to User entity.
	 * @param cursor Cursor object
	 * @return User entity
	 */
	public User cursorToItem(final Cursor cursor) {
		User result = new User();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to User entity.
	 * @param cursor Cursor object
	 * @param result User entity
	 */
	public void cursorToItem(final Cursor cursor, final User result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_TYPE);
			result.setType(
				AccountType.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_LOGIN);
			result.setLogin(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_PASSWD);
			result.setPasswd(
					cursor.getString(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read User by id in database.
	 *
	 * @param id Identify of User
	 * @return User entity
	 */
	public User getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final User result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All Users entities.
	 *
	 * @return List of User entities
	 */
	public ArrayList<User> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<User> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @return Id of the User entity
	 */
	public long insert(final User item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(UserSQLiteAdapter.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					UserSQLiteAdapter.COL_ID,
					values);
		}
		item.setId((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a User entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The User entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final User item) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.update(item);
		} else {
			// Item doesn't exist => create it
			final long id = this.insert(item);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}

	/**
	 * Update a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @return count of updated entities
	 */
	public int update(final User item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 UserSQLiteAdapter.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a User entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  UserSQLiteAdapter.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param user The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final User user) {
		return this.delete(user.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the User corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  UserSQLiteAdapter.ALIASED_COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a User entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
	}

	/**
	 * Deletes the given entity.
	 * @param id The ID of the entity to delete
	 * @return the number of token deleted
	 */
	public int delete(final int id) {
		return this.delete(
				ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
	}

}
