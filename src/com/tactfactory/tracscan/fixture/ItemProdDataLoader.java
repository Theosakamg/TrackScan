/**************************************************************************
 * ItemProdDataLoader.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.fixture;

import java.util.Map;

import android.content.Context;

import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.OrderProd;

/**
 * ItemProdDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ItemProdDataLoader
						extends FixtureBase<ItemProd> {
	/** ItemProdDataLoader name. */
	private static final String FILE_NAME = "ItemProd";

	/** Constant field for id. */
	private static final String ID = "id";
	/** Constant field for name. */
	private static final String NAME = "name";
	/** Constant field for items. */
	private static final String ITEMS = "items";


	/** ItemProdDataLoader instance (Singleton). */
	private static ItemProdDataLoader instance;

	/**
	 * Get the ItemProdDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static ItemProdDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new ItemProdDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private ItemProdDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected ItemProd extractItem(final Map<?, ?> columns) {
		final ItemProd itemProd =
				new ItemProd();

		return this.extractItem(columns, itemProd);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param itemProd Entity to extract
	 * @return A ItemProd entity
	 */
	protected ItemProd extractItem(final Map<?, ?> columns,
				ItemProd itemProd) {

		if (columns.get(ID) != null) {
			itemProd.setId(
				(Integer) columns.get(ID));
		}

		if (columns.get(NAME) != null) {
			itemProd.setName(
				(String) columns.get(NAME));
		}

		if (columns.get(ITEMS) != null) {
			final OrderProd orderProd =
				OrderProdDataLoader.getInstance(
						this.ctx).items.get(
								(String) columns.get(ITEMS));
			if (orderProd != null) {
				itemProd.setOrder(orderProd);
			}
		}


		return itemProd;
	}
	/**
	 * Loads ItemProds into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final ItemProd itemProd : this.items.values()) {
			itemProd.setId(
					manager.persist(itemProd));
		}
		manager.flush();
	}

	/**
	 * Give priority for fixtures insertion in database.
	 * 0 is the first.
	 * @return The order
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * Get the fixture file name.
	 * @return A String representing the file name
	 */
	@Override
	public String getFixtureFileName() {
		return FILE_NAME;
	}
}
