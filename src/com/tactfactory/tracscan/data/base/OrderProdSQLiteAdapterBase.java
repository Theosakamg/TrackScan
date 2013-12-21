/**************************************************************************
 * OrderProdSQLiteAdapterBase.java, tracscan Android
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
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ProductType;
import com.tactfactory.tracscan.entity.MaterialType;


import com.tactfactory.tracscan.TracscanApplication;


/** OrderProd adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit OrderProdAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class OrderProdSQLiteAdapterBase
						extends SQLiteAdapterBase<OrderProd> {

	/** TAG for debug purpose. */
	protected static final String TAG = "OrderProdDBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "OrderProd";

	/**
	 *  Columns constants fields mapping.
	 */
	/** id. */
	public static final String COL_ID =
			"id";
	/** Alias. */
	public static final String ALIASED_COL_ID =
			TABLE_NAME + "." + COL_ID;
	/** customer. */
	public static final String COL_CUSTOMER =
			"customer";
	/** Alias. */
	public static final String ALIASED_COL_CUSTOMER =
			TABLE_NAME + "." + COL_CUSTOMER;
	/** productType. */
	public static final String COL_PRODUCTTYPE =
			"productType";
	/** Alias. */
	public static final String ALIASED_COL_PRODUCTTYPE =
			TABLE_NAME + "." + COL_PRODUCTTYPE;
	/** materialType. */
	public static final String COL_MATERIALTYPE =
			"materialType";
	/** Alias. */
	public static final String ALIASED_COL_MATERIALTYPE =
			TABLE_NAME + "." + COL_MATERIALTYPE;
	/** quantity. */
	public static final String COL_QUANTITY =
			"quantity";
	/** Alias. */
	public static final String ALIASED_COL_QUANTITY =
			TABLE_NAME + "." + COL_QUANTITY;

	/** Global Fields. */
	public static final String[] COLS = new String[] {

		OrderProdSQLiteAdapter.COL_ID,
		OrderProdSQLiteAdapter.COL_CUSTOMER,
		OrderProdSQLiteAdapter.COL_PRODUCTTYPE,
		OrderProdSQLiteAdapter.COL_MATERIALTYPE,
		OrderProdSQLiteAdapter.COL_QUANTITY
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {

		OrderProdSQLiteAdapter.ALIASED_COL_ID,
		OrderProdSQLiteAdapter.ALIASED_COL_CUSTOMER,
		OrderProdSQLiteAdapter.ALIASED_COL_PRODUCTTYPE,
		OrderProdSQLiteAdapter.ALIASED_COL_MATERIALTYPE,
		OrderProdSQLiteAdapter.ALIASED_COL_QUANTITY
	};

	/**
	 * Get the table name used in DB for your OrderProd entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your OrderProd entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the OrderProd entity table.
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
		 + COL_CUSTOMER	+ " VARCHAR NOT NULL,"
		 + COL_PRODUCTTYPE	+ " VARCHAR NOT NULL,"
		 + COL_MATERIALTYPE	+ " VARCHAR NOT NULL,"
		 + COL_QUANTITY	+ " INTEGER NOT NULL"
		
		
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public OrderProdSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert OrderProd entity to Content Values for database.
	 * @param item OrderProd entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final OrderProd item) {
		final ContentValues result = new ContentValues();

		result.put(COL_ID,
			String.valueOf(item.getId()));

		if (item.getCustomer() != null) {
			result.put(COL_CUSTOMER,
				item.getCustomer());
		}

		if (item.getProductType() != null) {
			result.put(COL_PRODUCTTYPE,
				item.getProductType().getValue());
		}

		if (item.getMaterialType() != null) {
			result.put(COL_MATERIALTYPE,
				item.getMaterialType().getValue());
		}

		result.put(COL_QUANTITY,
			String.valueOf(item.getQuantity()));


		return result;
	}

	/**
	 * Convert Cursor of database to OrderProd entity.
	 * @param cursor Cursor object
	 * @return OrderProd entity
	 */
	public OrderProd cursorToItem(final Cursor cursor) {
		OrderProd result = new OrderProd();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to OrderProd entity.
	 * @param cursor Cursor object
	 * @param result OrderProd entity
	 */
	public void cursorToItem(final Cursor cursor, final OrderProd result) {
		if (cursor.getCount() != 0) {
			int index;

			index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_CUSTOMER);
			result.setCustomer(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_PRODUCTTYPE);
			result.setProductType(
				ProductType.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_MATERIALTYPE);
			result.setMaterialType(
				MaterialType.fromValue(cursor.getString(index)));

			index = cursor.getColumnIndexOrThrow(COL_QUANTITY);
			result.setQuantity(
					cursor.getInt(index));


		}
	}

	//// CRUD Entity ////
	/**
	 * Find & read OrderProd by id in database.
	 *
	 * @param id Identify of OrderProd
	 * @return OrderProd entity
	 */
	public OrderProd getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final OrderProd result = this.cursorToItem(cursor);
		cursor.close();

		final ItemProdSQLiteAdapter itemsAdapter =
				new ItemProdSQLiteAdapter(this.ctx);
		itemsAdapter.open(this.mDatabase);
		Cursor itemsCursor = itemsAdapter
					.getByOrderProditemsInternal(result.getId(), ItemProdSQLiteAdapter.ALIASED_COLS, null, null, null);
		result.setItems(itemsAdapter.cursorToItems(itemsCursor));
		return result;
	}


	/**
	 * Read All OrderProds entities.
	 *
	 * @return List of OrderProd entities
	 */
	public ArrayList<OrderProd> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<OrderProd> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a OrderProd entity into database.
	 *
	 * @param item The OrderProd entity to persist
	 * @return Id of the OrderProd entity
	 */
	public long insert(final OrderProd item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		values.remove(OrderProdSQLiteAdapter.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					OrderProdSQLiteAdapter.COL_ID,
					values);
		}
		item.setId((int) newid);
		if (item.getItems() != null) {
			ItemProdSQLiteAdapterBase itemsAdapter =
					new ItemProdSQLiteAdapter(this.ctx);
			itemsAdapter.open(this.mDatabase);
			for (ItemProd itemprod
						: item.getItems()) {
				itemsAdapter.insertOrUpdateWithOrderProdItems(
									itemprod,
									newid);
			}
		}
		return newid;
	}

	/**
	 * Either insert or update a OrderProd entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The OrderProd entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final OrderProd item) {
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
	 * Update a OrderProd entity into database.
	 *
	 * @param item The OrderProd entity to persist
	 * @return count of updated entities
	 */
	public int update(final OrderProd item) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item);
		final String whereClause =
				 OrderProdSQLiteAdapter.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a OrderProd entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  OrderProdSQLiteAdapter.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param orderProd The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final OrderProd orderProd) {
		return this.delete(orderProd.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the OrderProd corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (TracscanApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  OrderProdSQLiteAdapter.ALIASED_COL_ID
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
	 * Query the DB to find a OrderProd entity.
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
