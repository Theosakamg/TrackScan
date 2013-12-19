/**************************************************************************
 * LogProdSQLiteAdapterBase.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.data.base;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;
import com.tactfactory.tracscan.data.UserSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ItemState;

import com.tactfactory.tracscan.harmony.util.DateUtils;
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
	/** createDate. */
	public static final String COL_CREATEDATE =
			"createDate";
	/** Alias. */
	public static final String ALIASED_COL_CREATEDATE =
			TABLE_NAME + "." + COL_CREATEDATE;
	/** state. */
	public static final String COL_STATE =
			"state";
	/** Alias. */
	public static final String ALIASED_COL_STATE =
			TABLE_NAME + "." + COL_STATE;
	/** zone. */
	public static final String COL_ZONE =
			"zone";
	/** Alias. */
	public static final String ALIASED_COL_ZONE =
			TABLE_NAME + "." + COL_ZONE;
	/** user. */
	public static final String COL_USER =
			"user";
	/** Alias. */
	public static final String ALIASED_COL_USER =
			TABLE_NAME + "." + COL_USER;
	/** item. */
	public static final String COL_ITEM =
			"item";
	/** Alias. */
	public static final String ALIASED_COL_ITEM =
			TABLE_NAME + "." + COL_ITEM;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		LogProdSQLiteAdapter.COL_ID,
		LogProdSQLiteAdapter.COL_CREATEDATE,
		LogProdSQLiteAdapter.COL_STATE,
		LogProdSQLiteAdapter.COL_ZONE,
		LogProdSQLiteAdapter.COL_USER,
		LogProdSQLiteAdapter.COL_ITEM
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		LogProdSQLiteAdapter.ALIASED_COL_ID,
		LogProdSQLiteAdapter.ALIASED_COL_CREATEDATE,
		LogProdSQLiteAdapter.ALIASED_COL_STATE,
		LogProdSQLiteAdapter.ALIASED_COL_ZONE,
		LogProdSQLiteAdapter.ALIASED_COL_USER,
		LogProdSQLiteAdapter.ALIASED_COL_ITEM
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
		
		 + COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + COL_CREATEDATE	+ " DATETIME NOT NULL,"
		 + COL_STATE	+ " VARCHAR NOT NULL,"
		 + COL_ZONE	+ " INTEGER NOT NULL,"
		 + COL_USER	+ " INTEGER NOT NULL,"
		 + COL_ITEM	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + COL_ZONE + ") REFERENCES " 
			 + ZoneSQLiteAdapter.TABLE_NAME 
				+ " (" + ZoneSQLiteAdapter.COL_ID + "),"
		 + "FOREIGN KEY(" + COL_USER + ") REFERENCES " 
			 + UserSQLiteAdapter.TABLE_NAME 
				+ " (" + UserSQLiteAdapter.COL_ID + "),"
		 + "FOREIGN KEY(" + COL_ITEM + ") REFERENCES " 
			 + ItemProdSQLiteAdapter.TABLE_NAME 
				+ " (" + ItemProdSQLiteAdapter.COL_ID + ")"
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

		if (item.getCreateDate() != null) {
			result.put(COL_CREATEDATE,
				item.getCreateDate().toString(ISODateTimeFormat.dateTime()));
		}

		if (item.getState() != null) {
			result.put(COL_STATE,
				item.getState().getValue());
		}

		if (item.getZone() != null) {
			result.put(COL_ZONE,
				item.getZone().getId());
		}

		if (item.getUser() != null) {
			result.put(COL_USER,
				item.getUser().getId());
		}

		if (item.getItem() != null) {
			result.put(COL_ITEM,
				item.getItem().getId());
		}


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

			index = cursor.getColumnIndexOrThrow(COL_CREATEDATE);
			final DateTime dtCreateDate =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtCreateDate != null) {
					result.setCreateDate(
							dtCreateDate);
			} else {
				result.setCreateDate(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_STATE);
			result.setState(
				ItemState.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_ZONE);
			final Zone zone = new Zone();
			zone.setId(cursor.getInt(index));
			result.setZone(zone);

			index = cursor.getColumnIndexOrThrow(COL_USER);
			final User user = new User();
			user.setId(cursor.getInt(index));
			result.setUser(user);

			index = cursor.getColumnIndexOrThrow(COL_ITEM);
			final ItemProd item = new ItemProd();
			item.setId(cursor.getInt(index));
			result.setItem(item);


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

		if (result.getZone() != null) {
			final ZoneSQLiteAdapter zoneAdapter =
					new ZoneSQLiteAdapter(this.ctx);
			zoneAdapter.open(this.mDatabase);
			
			result.setZone(zoneAdapter.getByID(
							result.getZone().getId()));
		}
		if (result.getUser() != null) {
			final UserSQLiteAdapter userAdapter =
					new UserSQLiteAdapter(this.ctx);
			userAdapter.open(this.mDatabase);
			
			result.setUser(userAdapter.getByID(
							result.getUser().getId()));
		}
		if (result.getItem() != null) {
			final ItemProdSQLiteAdapter itemAdapter =
					new ItemProdSQLiteAdapter(this.ctx);
			itemAdapter.open(this.mDatabase);
			
			result.setItem(itemAdapter.getByID(
							result.getItem().getId()));
		}
		return result;
	}

	/**
	 * Find & read LogProd by zone.
	 * @param zoneId zoneId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogProd entities
	 */
	 public Cursor getByZone(final int zoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogProdSQLiteAdapter.COL_ZONE + "=?";
		String idSelectionArgs = String.valueOf(zoneId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }
	/**
	 * Find & read LogProd by user.
	 * @param userId userId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogProd entities
	 */
	 public Cursor getByUser(final int userId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogProdSQLiteAdapter.COL_USER + "=?";
		String idSelectionArgs = String.valueOf(userId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }
	/**
	 * Find & read LogProd by item.
	 * @param itemId itemId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogProd entities
	 */
	 public Cursor getByItem(final int itemId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogProdSQLiteAdapter.COL_ITEM + "=?";
		String idSelectionArgs = String.valueOf(itemId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
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
