/**************************************************************************
 * ItemProdSQLiteAdapterBase.java, tracscan Android
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
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.entity.ItemState;

import com.tactfactory.tracscan.harmony.util.DateUtils;
import com.tactfactory.tracscan.TracscanApplication;


/** ItemProd adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ItemProdAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ItemProdSQLiteAdapterBase
						extends SQLiteAdapterBase<ItemProd> {

	/** TAG for debug purpose. */
	protected static final String TAG = "ItemProdDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "ItemProd";

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
	/** state. */
	public static final String COL_STATE =
			"state";
	/** Alias. */
	public static final String ALIASED_COL_STATE =
			TABLE_NAME + "." + COL_STATE;
	/** updateDate. */
	public static final String COL_UPDATEDATE =
			"updateDate";
	/** Alias. */
	public static final String ALIASED_COL_UPDATEDATE =
			TABLE_NAME + "." + COL_UPDATEDATE;
	/** orderCustomer. */
	public static final String COL_ORDERCUSTOMER =
			"orderCustomer";
	/** Alias. */
	public static final String ALIASED_COL_ORDERCUSTOMER =
			TABLE_NAME + "." + COL_ORDERCUSTOMER;
	/** currentZone. */
	public static final String COL_CURRENTZONE =
			"currentZone";
	/** Alias. */
	public static final String ALIASED_COL_CURRENTZONE =
			TABLE_NAME + "." + COL_CURRENTZONE;
	/** OrderProd_items_internal. */
	public static final String COL_ORDERPRODITEMSINTERNAL =
			"OrderProd_items_internal";
	/** Alias. */
	public static final String ALIASED_COL_ORDERPRODITEMSINTERNAL =
			TABLE_NAME + "." + COL_ORDERPRODITEMSINTERNAL;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		ItemProdSQLiteAdapter.COL_ID,
		ItemProdSQLiteAdapter.COL_NAME,
		ItemProdSQLiteAdapter.COL_STATE,
		ItemProdSQLiteAdapter.COL_UPDATEDATE,
		ItemProdSQLiteAdapter.COL_ORDERCUSTOMER,
		ItemProdSQLiteAdapter.COL_CURRENTZONE,
		ItemProdSQLiteAdapter.COL_ORDERPRODITEMSINTERNAL
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		ItemProdSQLiteAdapter.ALIASED_COL_ID,
		ItemProdSQLiteAdapter.ALIASED_COL_NAME,
		ItemProdSQLiteAdapter.ALIASED_COL_STATE,
		ItemProdSQLiteAdapter.ALIASED_COL_UPDATEDATE,
		ItemProdSQLiteAdapter.ALIASED_COL_ORDERCUSTOMER,
		ItemProdSQLiteAdapter.ALIASED_COL_CURRENTZONE,
		ItemProdSQLiteAdapter.ALIASED_COL_ORDERPRODITEMSINTERNAL
	};

	/**
	 * Get the table name used in DB for your ItemProd entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your ItemProd entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the ItemProd entity table.
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
		 + COL_STATE	+ " VARCHAR NOT NULL,"
		 + COL_UPDATEDATE	+ " DATETIME NOT NULL,"
		 + COL_ORDERCUSTOMER	+ " INTEGER NOT NULL,"
		 + COL_CURRENTZONE	+ " INTEGER NOT NULL,"
		 + COL_ORDERPRODITEMSINTERNAL	+ " INTEGER,"
		
		
		 + "FOREIGN KEY(" + COL_ORDERCUSTOMER + ") REFERENCES " 
			 + OrderProdSQLiteAdapter.TABLE_NAME 
				+ " (" + OrderProdSQLiteAdapter.COL_ID + "),"
		 + "FOREIGN KEY(" + COL_CURRENTZONE + ") REFERENCES " 
			 + ZoneSQLiteAdapter.TABLE_NAME 
				+ " (" + ZoneSQLiteAdapter.COL_ID + "),"
		 + "FOREIGN KEY(" + COL_ORDERPRODITEMSINTERNAL + ") REFERENCES " 
			 + OrderProdSQLiteAdapter.TABLE_NAME 
				+ " (" + OrderProdSQLiteAdapter.COL_ID + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ItemProdSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters
	/** Convert ItemProd entity to Content Values for database.
	 *
	 * @param item ItemProd entity object
	 * @param orderprodId orderprod id
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ItemProd item,
				int orderprodId) {
		final ContentValues result = this.itemToContentValues(item);
		result.put(COL_ORDERPRODITEMSINTERNAL,
				String.valueOf(orderprodId));
		return result;
	}

	/**
	 * Convert ItemProd entity to Content Values for database.
	 * @param item ItemProd entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ItemProd item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID,
			String.valueOf(item.getId()));

		if (item.getName() != null) {
			result.put(COL_NAME,
				item.getName());
		}

		if (item.getState() != null) {
			result.put(COL_STATE,
				item.getState().getValue());
		}

		if (item.getUpdateDate() != null) {
			result.put(COL_UPDATEDATE,
				item.getUpdateDate().toString(ISODateTimeFormat.dateTime()));
		}

		if (item.getOrderCustomer() != null) {
			result.put(COL_ORDERCUSTOMER,
				item.getOrderCustomer().getId());
		}

		if (item.getCurrentZone() != null) {
			result.put(COL_CURRENTZONE,
				item.getCurrentZone().getId());
		}


		return result;
	}

	/**
	 * Convert Cursor of database to ItemProd entity.
	 * @param cursor Cursor object
	 * @return ItemProd entity
	 */
	public ItemProd cursorToItem(final Cursor cursor) {
		ItemProd result = new ItemProd();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to ItemProd entity.
	 * @param cursor Cursor object
	 * @param result ItemProd entity
	 */
	public void cursorToItem(final Cursor cursor, final ItemProd result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_NAME);
			result.setName(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_STATE);
			result.setState(
				ItemState.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_UPDATEDATE);
			final DateTime dtUpdateDate =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtUpdateDate != null) {
					result.setUpdateDate(
							dtUpdateDate);
			} else {
				result.setUpdateDate(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_ORDERCUSTOMER);
			final OrderProd orderCustomer = new OrderProd();
			orderCustomer.setId(cursor.getInt(index));
			result.setOrderCustomer(orderCustomer);

			index = cursor.getColumnIndexOrThrow(COL_CURRENTZONE);
			final Zone currentZone = new Zone();
			currentZone.setId(cursor.getInt(index));
			result.setCurrentZone(currentZone);


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read ItemProd by id in database.
	 *
	 * @param id Identify of ItemProd
	 * @return ItemProd entity
	 */
	public ItemProd getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final ItemProd result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getOrderCustomer() != null) {
			final OrderProdSQLiteAdapter orderCustomerAdapter =
					new OrderProdSQLiteAdapter(this.ctx);
			orderCustomerAdapter.open(this.mDatabase);
			
			result.setOrderCustomer(orderCustomerAdapter.getByID(
							result.getOrderCustomer().getId()));
		}
		if (result.getCurrentZone() != null) {
			final ZoneSQLiteAdapter currentZoneAdapter =
					new ZoneSQLiteAdapter(this.ctx);
			currentZoneAdapter.open(this.mDatabase);
			
			result.setCurrentZone(currentZoneAdapter.getByID(
							result.getCurrentZone().getId()));
		}
		return result;
	}

	/**
	 * Find & read ItemProd by orderCustomer.
	 * @param ordercustomerId ordercustomerId
	 * @param orderBy Order by string (can be null)
	 * @return List of ItemProd entities
	 */
	 public Cursor getByOrderCustomer(final int ordercustomerId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = ItemProdSQLiteAdapter.COL_ORDERCUSTOMER + "=?";
		String idSelectionArgs = String.valueOf(ordercustomerId);
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
	 * Find & read ItemProd by currentZone.
	 * @param currentzoneId currentzoneId
	 * @param orderBy Order by string (can be null)
	 * @return List of ItemProd entities
	 */
	 public Cursor getByCurrentZone(final int currentzoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = ItemProdSQLiteAdapter.COL_CURRENTZONE + "=?";
		String idSelectionArgs = String.valueOf(currentzoneId);
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
	 * Find & read ItemProd by OrderProditemsInternal.
	 * @param orderproditemsinternalId orderproditemsinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of ItemProd entities
	 */
	 public Cursor getByOrderProditemsInternal(final int orderproditemsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = ItemProdSQLiteAdapter.COL_ORDERPRODITEMSINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(orderproditemsinternalId);
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
	 * Read All ItemProds entities.
	 *
	 * @return List of ItemProd entities
	 */
	public ArrayList<ItemProd> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<ItemProd> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a ItemProd entity into database.
	 *
	 * @param item The ItemProd entity to persist
	 * @return Id of the ItemProd entity
	 */
	public long insert(final ItemProd item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item, 0);
		values.remove(ItemProdSQLiteAdapter.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					ItemProdSQLiteAdapter.COL_ID,
					values);
		}
		item.setId((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a ItemProd entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The ItemProd entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final ItemProd item) {
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
	 * Update a ItemProd entity into database.
	 *
	 * @param item The ItemProd entity to persist
	 * @return count of updated entities
	 */
	public int update(final ItemProd item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item, 0);
		final String whereClause =
				 ItemProdSQLiteAdapter.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Update a ItemProd entity into database.
	 *
	 * @param item The ItemProd entity to persist
	 * @param orderprodId The orderprod id
	 * @return count of updated entities
	 */
	public int updateWithOrderProdItems(
					ItemProd item, int orderprodId) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		ContentValues values =
				this.itemToContentValues(item,
							orderprodId);
		String whereClause =
				 ItemProdSQLiteAdapter.COL_ID
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a ItemProd entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The ItemProd entity to persist
	 * @param orderprodId The orderprod id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithOrderProdItems(
			ItemProd item, int orderprodId) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.updateWithOrderProdItems(item,
					orderprodId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithOrderProdItems(item,
					orderprodId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a ItemProd entity into database.
	 *
	 * @param item The ItemProd entity to persist
	 * @param orderprodId The orderprod id
	 * @return Id of the ItemProd entity
	 */
	public long insertWithOrderProdItems(
			ItemProd item, int orderprodId) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		ContentValues values = this.itemToContentValues(item,
				orderprodId);
		values.remove(ItemProdSQLiteAdapter.COL_ID);
		int newid = (int) this.insert(
			null,
			values);


		return newid;
	}


	/**
	 * Delete a ItemProd entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  ItemProdSQLiteAdapter.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param itemProd The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final ItemProd itemProd) {
		return this.delete(itemProd.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the ItemProd corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  ItemProdSQLiteAdapter.ALIASED_COL_ID
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
	 * Query the DB to find a ItemProd entity.
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
