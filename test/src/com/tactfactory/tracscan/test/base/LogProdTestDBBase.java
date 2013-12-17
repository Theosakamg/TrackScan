/**************************************************************************
 * LogProdTestDBBase.java, tracscan Android
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

import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.LogProd;

import com.tactfactory.tracscan.fixture.LogProdDataLoader;

import java.util.ArrayList;

import com.tactfactory.tracscan.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** LogProd database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit LogProdTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class LogProdTestDBBase extends TestDBBase {
	protected Context ctx;

	protected LogProdSQLiteAdapter adapter;

	protected LogProd entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new LogProdSQLiteAdapter(this.ctx);
		this.adapter.open();

		ArrayList<LogProd> entities = new ArrayList<LogProd>(LogProdDataLoader.getInstance(this.ctx).getMap().values());
		if (entities.size()>0){
			this.entity = entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		this.adapter.close();

		super.tearDown();
	}

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		int result = -1;
		if (this.entity != null) {
			LogProd logProd = LogProdUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(logProd);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		LogProd result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation

			LogProdUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			LogProd logProd = LogProdUtils.generateRandom(this.ctx);
			logProd.setId(this.entity.getId());

			result = (int)this.adapter.update(logProd);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId());
			Assert.assertTrue(result >= 0);
		}
	}
}
