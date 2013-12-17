/**************************************************************************
 * ZoneDataLoader.java, tracscan Android
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

import com.tactfactory.tracscan.entity.Zone;



/**
 * ZoneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ZoneDataLoader
						extends FixtureBase<Zone> {
	/** ZoneDataLoader name. */
	private static final String FILE_NAME = "Zone";

	/** Constant field for id. */
	private static final String ID = "id";
	/** Constant field for name. */
	private static final String NAME = "name";
	/** Constant field for quantity. */
	private static final String QUANTITY = "quantity";


	/** ZoneDataLoader instance (Singleton). */
	private static ZoneDataLoader instance;

	/**
	 * Get the ZoneDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static ZoneDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new ZoneDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private ZoneDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected Zone extractItem(final Map<?, ?> columns) {
		final Zone zone =
				new Zone();

		return this.extractItem(columns, zone);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param zone Entity to extract
	 * @return A Zone entity
	 */
	protected Zone extractItem(final Map<?, ?> columns,
				Zone zone) {

		if (columns.get(ID) != null) {
			zone.setId(
				(Integer) columns.get(ID));
		}

		if (columns.get(NAME) != null) {
			zone.setName(
				(String) columns.get(NAME));
		}

		if (columns.get(QUANTITY) != null) {
			zone.setQuantity(
				(Integer) columns.get(QUANTITY));
		}


		return zone;
	}
	/**
	 * Loads Zones into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final Zone zone : this.items.values()) {
			zone.setId(
					manager.persist(zone));
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
