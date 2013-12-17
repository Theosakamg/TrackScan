/**************************************************************************
 * LogProdSQLiteAdapterBase.java, tracscan Android
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

import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.LogProd;


import com.tactfactory.tracscan.TracscanApplication;


/** LogProd adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit LogProdAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class LogProdSQLiteAdapterBase
						extends SQLiteAdapterBase<LogProd> {

	/** TAG for debug purpose. */
	protected static final String TAG = "LogProdDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "LogProd";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id. */
	public static final String COL_ID =
			"id";
	/** Alias. */
	public static final String ALIASED_COL_ID =
			TABLE_NAME + "." + COL_ID;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		LogProdSQLiteAdapter.COL_ID
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		LogProdSQLiteAdapter.ALIASED_COL_ID
	};

	/**
	 * Get the table name used in DB for your LogProd entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your LogProd entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the LogProd entity table.
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
		
		 + COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT"
		
		
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogProdSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert LogProd entity to Content Values for database.
	 * @param item LogProd entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final LogProd item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID,
			String.valueOf(item.getId()));


		return result;
	}

	/**
	 * Convert Cursor of database to LogProd entity.
	 * @param cursor Cursor object
	 * @return LogProd entity
	 */
	public LogProd cursorToItem(final Cursor cursor) {
		LogProd result = new LogProd();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to LogProd entity.
	 * @param cursor Cursor object
	 * @param result LogProd entity
	 */
	public void cursorToItem(final Cursor cursor, final LogProd result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read LogProd by id in database.
	 *
	 * @param id Identify of LogProd
	 * @return LogProd entity
	 */
	public LogProd getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final LogProd result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All LogProds entities.
	 *
	 * @return List of LogProd entities
	 */
	public ArrayList<LogProd> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<LogProd> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a LogProd entity into database.
	 *
	 * @param item The LogProd entity to persist
	 * @return Id of the LogProd entity
	 */
	public long insert(final LogProd item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(LogProdSQLiteAdapter.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					LogProdSQLiteAdapter.COL_ID,
					values);
		}
		item.setId((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a LogProd entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The LogProd entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final LogProd item) {
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
	 * Update a LogProd entity into database.
	 *
	 * @param item The LogProd entity to persist
	 * @return count of updated entities
	 */
	public int update(final LogProd item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 LogProdSQLiteAdapter.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a LogProd entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  LogProdSQLiteAdapter.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param logProd The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final LogProd logProd) {
		return this.delete(logProd.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the LogProd corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  LogProdSQLiteAdapter.ALIASED_COL_ID
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
	 * Query the DB to find a LogProd entity.
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
