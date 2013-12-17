/**************************************************************************
 * LogProdDataLoader.java, tracscan Android
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

import com.tactfactory.tracscan.entity.LogProd;



/**
 * LogProdDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class LogProdDataLoader
						extends FixtureBase<LogProd> {
	/** LogProdDataLoader name. */
	private static final String FILE_NAME = "LogProd";

	/** Constant field for id. */
	private static final String ID = "id";


	/** LogProdDataLoader instance (Singleton). */
	private static LogProdDataLoader instance;

	/**
	 * Get the LogProdDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static LogProdDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new LogProdDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private LogProdDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected LogProd extractItem(final Map<?, ?> columns) {
		final LogProd logProd =
				new LogProd();

		return this.extractItem(columns, logProd);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param logProd Entity to extract
	 * @return A LogProd entity
	 */
	protected LogProd extractItem(final Map<?, ?> columns,
				LogProd logProd) {

		if (columns.get(ID) != null) {
			logProd.setId(
				(Integer) columns.get(ID));
		}


		return logProd;
	}
	/**
	 * Loads LogProds into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final LogProd logProd : this.items.values()) {
			logProd.setId(
					manager.persist(logProd));
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
