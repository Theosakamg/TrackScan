/**************************************************************************
 * ItemProdTestDBBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.ItemProd;


import java.util.ArrayList;

import com.tactfactory.tracscan.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** ItemProd database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ItemProdTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ItemProdTestDBBase extends TestDBBase {
	protected Context ctx;

	protected ItemProdSQLiteAdapter adapter;

	protected ItemProd entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ItemProdSQLiteAdapter(this.ctx);
		this.adapter.open();

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
			ItemProd itemProd = ItemProdUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(itemProd);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		ItemProd result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation

			ItemProdUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			ItemProd itemProd = ItemProdUtils.generateRandom(this.ctx);
			itemProd.setId(this.entity.getId());

			result = (int)this.adapter.update(itemProd);

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
