/**************************************************************************
 * LogProdSQLiteAdapterBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 *
 **************************************************************************/
package com.tactfactory.tracscan.data.base;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	/** stateAction. */
	public static final String COL_STATEACTION =
			"stateAction";
	/** Alias. */
	public static final String ALIASED_COL_STATEACTION =
			TABLE_NAME + "." + COL_STATEACTION;
	/** zoneLogged. */
	public static final String COL_ZONELOGGED =
			"zoneLogged";
	/** Alias. */
	public static final String ALIASED_COL_ZONELOGGED =
			TABLE_NAME + "." + COL_ZONELOGGED;
	/** userLogged. */
	public static final String COL_USERLOGGED =
			"userLogged";
	/** Alias. */
	public static final String ALIASED_COL_USERLOGGED =
			TABLE_NAME + "." + COL_USERLOGGED;
	/** itemLogged. */
	public static final String COL_ITEMLOGGED =
			"itemLogged";
	/** Alias. */
	public static final String ALIASED_COL_ITEMLOGGED =
			TABLE_NAME + "." + COL_ITEMLOGGED;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		LogProdSQLiteAdapter.COL_ID,
		LogProdSQLiteAdapter.COL_CREATEDATE,
		LogProdSQLiteAdapter.COL_STATEACTION,
		LogProdSQLiteAdapter.COL_ZONELOGGED,
		LogProdSQLiteAdapter.COL_USERLOGGED,
		LogProdSQLiteAdapter.COL_ITEMLOGGED
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		LogProdSQLiteAdapter.ALIASED_COL_ID,
		LogProdSQLiteAdapter.ALIASED_COL_CREATEDATE,
		LogProdSQLiteAdapter.ALIASED_COL_STATEACTION,
		LogProdSQLiteAdapter.ALIASED_COL_ZONELOGGED,
		LogProdSQLiteAdapter.ALIASED_COL_USERLOGGED,
		LogProdSQLiteAdapter.ALIASED_COL_ITEMLOGGED
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
		 + COL_STATEACTION	+ " VARCHAR NOT NULL,"
		 + COL_ZONELOGGED	+ " INTEGER NOT NULL,"
		 + COL_USERLOGGED	+ " INTEGER NOT NULL,"
		 + COL_ITEMLOGGED	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + COL_ZONELOGGED + ") REFERENCES " 
			 + ZoneSQLiteAdapter.TABLE_NAME 
				+ " (" + ZoneSQLiteAdapter.COL_ID + "),"
		 + "FOREIGN KEY(" + COL_USERLOGGED + ") REFERENCES " 
			 + UserSQLiteAdapter.TABLE_NAME 
				+ " (" + UserSQLiteAdapter.COL_ID + "),"
		 + "FOREIGN KEY(" + COL_ITEMLOGGED + ") REFERENCES " 
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

		if (item.getStateAction() != null) {
			result.put(COL_STATEACTION,
				item.getStateAction().getValue());
		}

		if (item.getZoneLogged() != null) {
			result.put(COL_ZONELOGGED,
				item.getZoneLogged().getId());
		}

		if (item.getUserLogged() != null) {
			result.put(COL_USERLOGGED,
				item.getUserLogged().getId());
		}

		if (item.getItemLogged() != null) {
			result.put(COL_ITEMLOGGED,
				item.getItemLogged().getId());
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

			index = cursor.getColumnIndexOrThrow(COL_STATEACTION);
			result.setStateAction(
				ItemState.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_ZONELOGGED);
			final Zone zoneLogged = new Zone();
			zoneLogged.setId(cursor.getInt(index));
			result.setZoneLogged(zoneLogged);

			index = cursor.getColumnIndexOrThrow(COL_USERLOGGED);
			final User userLogged = new User();
			userLogged.setId(cursor.getInt(index));
			result.setUserLogged(userLogged);

			index = cursor.getColumnIndexOrThrow(COL_ITEMLOGGED);
			final ItemProd itemLogged = new ItemProd();
			itemLogged.setId(cursor.getInt(index));
			result.setItemLogged(itemLogged);


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

		if (result.getZoneLogged() != null) {
			final ZoneSQLiteAdapter zoneLoggedAdapter =
					new ZoneSQLiteAdapter(this.ctx);
			zoneLoggedAdapter.open(this.mDatabase);
			
			result.setZoneLogged(zoneLoggedAdapter.getByID(
							result.getZoneLogged().getId()));
		}
		if (result.getUserLogged() != null) {
			final UserSQLiteAdapter userLoggedAdapter =
					new UserSQLiteAdapter(this.ctx);
			userLoggedAdapter.open(this.mDatabase);
			
			result.setUserLogged(userLoggedAdapter.getByID(
							result.getUserLogged().getId()));
		}
		if (result.getItemLogged() != null) {
			final ItemProdSQLiteAdapter itemLoggedAdapter =
					new ItemProdSQLiteAdapter(this.ctx);
			itemLoggedAdapter.open(this.mDatabase);
			
			result.setItemLogged(itemLoggedAdapter.getByID(
							result.getItemLogged().getId()));
		}
		return result;
	}

	/**
	 * Find & read LogProd by zoneLogged.
	 * @param zoneloggedId zoneloggedId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogProd entities
	 */
	 public Cursor getByZoneLogged(final int zoneloggedId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogProdSQLiteAdapter.COL_ZONELOGGED + "=?";
		String idSelectionArgs = String.valueOf(zoneloggedId);
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
	 * Find & read LogProd by userLogged.
	 * @param userloggedId userloggedId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogProd entities
	 */
	 public Cursor getByUserLogged(final int userloggedId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogProdSQLiteAdapter.COL_USERLOGGED + "=?";
		String idSelectionArgs = String.valueOf(userloggedId);
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
	 * Find & read LogProd by itemLogged.
	 * @param itemloggedId itemloggedId
	 * @param orderBy Order by string (can be null)
	 * @return List of LogProd entities
	 */
	 public Cursor getByItemLogged(final int itemloggedId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = LogProdSQLiteAdapter.COL_ITEMLOGGED + "=?";
		String idSelectionArgs = String.valueOf(itemloggedId);
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
