/**************************************************************************
 * OrderProdDataLoader.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ProductType;
import com.tactfactory.tracscan.entity.MaterialType;



/**
 * OrderProdDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class OrderProdDataLoader
						extends FixtureBase<OrderProd> {
	/** OrderProdDataLoader name. */
	private static final String FILE_NAME = "OrderProd";

	/** Constant field for id. */
	private static final String ID = "id";
	/** Constant field for login. */
	private static final String LOGIN = "login";
	/** Constant field for productType. */
	private static final String PRODUCTTYPE = "productType";
	/** Constant field for materialType. */
	private static final String MATERIALTYPE = "materialType";
	/** Constant field for quantity. */
	private static final String QUANTITY = "quantity";
	/** Constant field for items. */
	private static final String ITEMS = "items";


	/** OrderProdDataLoader instance (Singleton). */
	private static OrderProdDataLoader instance;

	/**
	 * Get the OrderProdDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static OrderProdDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new OrderProdDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private OrderProdDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected OrderProd extractItem(final Map<?, ?> columns) {
		final OrderProd orderProd =
				new OrderProd();

		return this.extractItem(columns, orderProd);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param orderProd Entity to extract
	 * @return A OrderProd entity
	 */
	protected OrderProd extractItem(final Map<?, ?> columns,
				OrderProd orderProd) {

		if (columns.get(ID) != null) {
			orderProd.setId(
				(Integer) columns.get(ID));
		}

		if (columns.get(LOGIN) != null) {
			orderProd.setLogin(
				(String) columns.get(LOGIN));
		}

		if (columns.get(PRODUCTTYPE) != null) {
			orderProd.setProductType(ProductType.fromValue(
				(String) columns.get(PRODUCTTYPE)));
		}

		if (columns.get(MATERIALTYPE) != null) {
			orderProd.setMaterialType(MaterialType.fromValue(
				(String) columns.get(MATERIALTYPE)));
		}

		if (columns.get(QUANTITY) != null) {
			orderProd.setQuantity(
				(Integer) columns.get(QUANTITY));
		}

		if (columns.get(ITEMS) != null) {
			ArrayList<ItemProd> itemProds =
				new ArrayList<ItemProd>();
			final Map<?, ?> itemProdsMap =
				(Map<?, ?>) columns.get(ITEMS);
			for (final Object itemProdName : itemProdsMap.values()) {
				if (ItemProdDataLoader.getInstance(
					this.ctx).items.containsKey(
							(String) itemProdName)) {
				itemProds.add(
						ItemProdDataLoader.getInstance(
								this.ctx).items.get((String) itemProdName));
				}
			}
			orderProd.setItems(itemProds);
		}


		return orderProd;
	}
	/**
	 * Loads OrderProds into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final OrderProd orderProd : this.items.values()) {
			orderProd.setId(
					manager.persist(orderProd));
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
