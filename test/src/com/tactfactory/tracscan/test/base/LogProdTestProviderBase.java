/**************************************************************************
 * LogProdTestProviderBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.tactfactory.tracscan.provider.LogProdProviderAdapter;

import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;

import com.tactfactory.tracscan.entity.LogProd;

import com.tactfactory.tracscan.fixture.LogProdDataLoader;
import com.tactfactory.tracscan.fixture.LogProdDataLoader;

import java.util.ArrayList;
import com.tactfactory.tracscan.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** LogProd database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit LogProdTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class LogProdTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected LogProdSQLiteAdapter adapter;

	protected LogProd entity;
	protected ContentResolver provider;

	protected ArrayList<LogProd> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new LogProdSQLiteAdapter(this.ctx);

		this.entities = new ArrayList<LogProd>(LogProdDataLoader.getInstance(this.ctx).getMap().values());
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}

		this.nbEntities += LogProdDataLoader.getInstance(this.ctx).getMap().size();
		this.provider = this.getMockContext().getContentResolver();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		Uri result = null;
		if (this.entity != null) {
			LogProd logProd = LogProdUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(logProd);
				values.remove(LogProdSQLiteAdapter.COL_ID);
				result = this.provider.insert(LogProdProviderAdapter.LOGPROD_URI, values);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertNotNull(result);
			Assert.assertTrue(Integer.valueOf(result.getEncodedPath().substring(result.getEncodedPath().lastIndexOf("/")+1)) > 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		LogProd result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(LogProdProviderAdapter.LOGPROD_URI + "/" + this.entity.getId()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			LogProdUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<LogProd> result = null;
		try {
			Cursor c = this.provider.query(LogProdProviderAdapter.LOGPROD_URI, this.adapter.getCols(), null, null, null);
			result = this.adapter.cursorToItems(c);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(result);
		if (result != null) {
			Assert.assertEquals(result.size(), this.nbEntities);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			LogProd logProd = LogProdUtils.generateRandom(this.ctx);

			try {
				logProd.setId(this.entity.getId());

				ContentValues values = this.adapter.itemToContentValues(logProd);
				result = this.provider.update(
					Uri.parse(LogProdProviderAdapter.LOGPROD_URI
						+ "/"
						+ logProd.getId()),
					values,
					null,
					null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertTrue(result > 0);
		}
	}

	/** Test case UpdateAll Entity */
	@SmallTest
	public void testUpdateAll() {
		int result = -1;
		if (this.entities.size() > 0) {
			LogProd logProd = LogProdUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(logProd);
				values.remove(LogProdSQLiteAdapter.COL_ID);

				result = this.provider.update(LogProdProviderAdapter.LOGPROD_URI, values, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}

	/** Test case Delete Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			try {
				result = this.provider.delete(Uri.parse(LogProdProviderAdapter.LOGPROD_URI + "/" + this.entity.getId()), null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(result >= 0);
		}

	}

	/** Test case DeleteAll Entity */
	@SmallTest
	public void testDeleteAll() {
		int result = -1;
		if (this.entities.size() > 0) {

			try {
				result = this.provider.delete(LogProdProviderAdapter.LOGPROD_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
