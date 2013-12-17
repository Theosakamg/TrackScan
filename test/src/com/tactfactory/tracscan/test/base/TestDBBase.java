/**************************************************************************
 * TestDBBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.test.base;

import java.io.File;

import com.tactfactory.tracscan.provider.TracscanProvider;
import com.tactfactory.tracscan.TracscanApplication;
import com.tactfactory.tracscan.fixture.DataLoader;
import com.tactfactory.tracscan.harmony.util.DatabaseUtil;
import com.tactfactory.tracscan.data.TracscanSQLiteOpenHelper;
import com.tactfactory.tracscan.data.base.SQLiteAdapterBase;

import android.content.ContentProvider;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.util.Log;


/** Base test abstract class <br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public abstract class TestDBBase extends AndroidTestCase {
	private final static String CONTEXT_PREFIX = "test.";
	private final static String PROVIDER_AUTHORITY =
					"com.tactfactory.tracscan.provider";
	private final static Class<? extends ContentProvider> PROVIDER_CLASS =
					TracscanProvider.class;

	private static Context context = null;
	private Context baseContext;

	/**
	 * Get the original context
	 * @return unmocked Context
	 */
	protected Context getBaseContext() {
		return this.baseContext;
	}

	/**
	 * Get the mock for ContentResolver
	 * @return MockContentResolver
	 */
	protected MockContentResolver getMockContentResolver() {
		return new MockContentResolver();
	}

	/**
	 * Get the mock for Context
	 * @return MockContext
	 */
	protected Context getMockContext() {
			return this.getContext();
	}

	/**
	 * Initialize the mock Context
	 * @throws Exception
	 */
	protected void setMockContext() throws Exception {
		if (this.baseContext == null) {
			this.baseContext = this.getContext();
		}

		if (context == null) {
			ContentProvider provider = PROVIDER_CLASS.newInstance();
			MockContentResolver resolver = this.getMockContentResolver();
	
			RenamingDelegatingContext targetContextWrapper
				= new RenamingDelegatingContext(
					// The context that most methods are delegated to:
					this.getMockContext(),
					// The context that file methods are delegated to:
					this.baseContext,
					// Prefix database
					CONTEXT_PREFIX);
	
			context = new IsolatedContext(
					resolver,
					targetContextWrapper) {
						@Override
						public Object getSystemService(String name) {
								return TestDBBase.this
										.baseContext.getSystemService(name);
						}
					};
	
			provider.attachInfo(context, null);
			resolver.addProvider(PROVIDER_AUTHORITY, provider);
		}

		this.setContext(context);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		TracscanSQLiteOpenHelper.isJUnit = true;
		this.setMockContext();
		super.setUp();

		String dbPath =
				this.getContext().getDatabasePath(SQLiteAdapterBase.DB_NAME)
					.getAbsolutePath() + ".test";

		File cacheDbFile = new File(dbPath);

		if (!cacheDbFile.exists() || !DataLoader.hasFixturesBeenLoaded) {
			Log.d("TEST", "Create new Database cache");

			// Create initial database
			TracscanSQLiteOpenHelper helper =
					new TracscanSQLiteOpenHelper(
						this.getMockContext(),
						SQLiteAdapterBase.DB_NAME,
						null,
						TracscanApplication.getVersionCode(
								this.getMockContext()));

			SQLiteDatabase db = helper.getWritableDatabase();
			TracscanSQLiteOpenHelper.clearDatabase(db);

			db.beginTransaction();
			DataLoader dataLoader = new DataLoader(this.getMockContext());
			dataLoader.clean();
			dataLoader.loadData(db,
						DataLoader.MODE_APP |
						DataLoader.MODE_DEBUG |
						DataLoader.MODE_TEST);
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();

			DatabaseUtil.exportDB(this.getMockContext(),
					cacheDbFile,
					SQLiteAdapterBase.DB_NAME);
		} else {
			Log.d("TEST", "Re use old Database cache");
			DatabaseUtil.importDB(this.getMockContext(),
					cacheDbFile,
					SQLiteAdapterBase.DB_NAME,
					false);
		}
	}
}
