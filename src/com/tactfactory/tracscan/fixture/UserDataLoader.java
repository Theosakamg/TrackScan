/**************************************************************************
 * UserDataLoader.java, tracscan Android
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

import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.AccountType;



/**
 * UserDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class UserDataLoader
						extends FixtureBase<User> {
	/** UserDataLoader name. */
	private static final String FILE_NAME = "User";

	/** Constant field for id. */
	private static final String ID = "id";
	/** Constant field for type. */
	private static final String TYPE = "type";
	/** Constant field for login. */
	private static final String LOGIN = "login";
	/** Constant field for passwd. */
	private static final String PASSWD = "passwd";


	/** UserDataLoader instance (Singleton). */
	private static UserDataLoader instance;

	/**
	 * Get the UserDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static UserDataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new UserDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private UserDataLoader(final Context ctx) {
		super(ctx);
	}


	@Override
	protected User extractItem(final Map<?, ?> columns) {
		final User user =
				new User();

		return this.extractItem(columns, user);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param user Entity to extract
	 * @return A User entity
	 */
	protected User extractItem(final Map<?, ?> columns,
				User user) {

		if (columns.get(ID) != null) {
			user.setId(
				(Integer) columns.get(ID));
		}

		if (columns.get(TYPE) != null) {
			user.setType(AccountType.fromValue(
				(String) columns.get(TYPE)));
		}

		if (columns.get(LOGIN) != null) {
			user.setLogin(
				(String) columns.get(LOGIN));
		}

		if (columns.get(PASSWD) != null) {
			user.setPasswd(
				(String) columns.get(PASSWD));
		}


		return user;
	}
	/**
	 * Loads Users into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final User user : this.items.values()) {
			user.setId(
					manager.persist(user));
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
