/**************************************************************************
 * ZoneSQLiteAdapterBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data.base;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;
import com.tactfactory.tracscan.entity.Zone;


import com.tactfactory.tracscan.TracscanApplication;


/** Zone adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ZoneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ZoneSQLiteAdapterBase
						extends SQLiteAdapterBase<Zone> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ZoneDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "Zone";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id. */
	public static final String COL_ID =
			"id";
	/** Alias. */
	public static final String ALIASED_COL_ID =
			TABLE_NAME + "." + COL_ID;
	/** name. */
	public static final String COL_NAME =
			"name";
	/** Alias. */
	public static final String ALIASED_COL_NAME =
			TABLE_NAME + "." + COL_NAME;
	/** quantity. */
	public static final String COL_QUANTITY =
			"quantity";
	/** Alias. */
	public static final String ALIASED_COL_QUANTITY =
			TABLE_NAME + "." + COL_QUANTITY;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		ZoneSQLiteAdapter.COL_ID,
		ZoneSQLiteAdapter.COL_NAME,
		ZoneSQLiteAdapter.COL_QUANTITY
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		ZoneSQLiteAdapter.ALIASED_COL_ID,
		ZoneSQLiteAdapter.ALIASED_COL_NAME,
		ZoneSQLiteAdapter.ALIASED_COL_QUANTITY
	};

	/**
	 * Get the table name used in DB for your Zone entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Zone entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Zone entity table.
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
		 + COL_NAME	+ " VARCHAR NOT NULL,"
		 + COL_QUANTITY	+ " INTEGER NOT NULL DEFAULT '1'"
		
		
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ZoneSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Zone entity to Content Values for database.
	 * @param item Zone entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Zone item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID,
			String.valueOf(item.getId()));

		if (item.getName() != null) {
			result.put(COL_NAME,
				item.getName());
		}

		result.put(COL_QUANTITY,
			String.valueOf(item.getQuantity()));


		return result;
	}

	/**
	 * Convert Cursor of database to Zone entity.
	 * @param cursor Cursor object
	 * @return Zone entity
	 */
	public Zone cursorToItem(final Cursor cursor) {
		Zone result = new Zone();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to Zone entity.
	 * @param cursor Cursor object
	 * @param result Zone entity
	 */
	public void cursorToItem(final Cursor cursor, final Zone result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_NAME);
			result.setName(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_QUANTITY);
			result.setQuantity(
					cursor.getInt(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read Zone by id in database.
	 *
	 * @param id Identify of Zone
	 * @return Zone entity
	 */
	public Zone getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Zone result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All Zones entities.
	 *
	 * @return List of Zone entities
	 */
	public ArrayList<Zone> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Zone> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Zone entity into database.
	 *
	 * @param item The Zone entity to persist
	 * @return Id of the Zone entity
	 */
	public long insert(final Zone item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(ZoneSQLiteAdapter.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					ZoneSQLiteAdapter.COL_ID,
					values);
		}
		item.setId((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a Zone entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Zone entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Zone item) {
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
	 * Update a Zone entity into database.
	 *
	 * @param item The Zone entity to persist
	 * @return count of updated entities
	 */
	public int update(final Zone item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 ZoneSQLiteAdapter.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Zone entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  ZoneSQLiteAdapter.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param zone The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Zone zone) {
		return this.delete(zone.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the Zone corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  ZoneSQLiteAdapter.ALIASED_COL_ID
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
	 * Query the DB to find a Zone entity.
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
