/**************************************************************************
 * ItemProdTestProviderBase.java, tracscan Android
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

import com.tactfactory.tracscan.provider.ItemProdProviderAdapter;

import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;

import com.tactfactory.tracscan.entity.ItemProd;

import com.tactfactory.tracscan.fixture.ItemProdDataLoader;
import com.tactfactory.tracscan.fixture.ItemProdDataLoader;

import java.util.ArrayList;
import com.tactfactory.tracscan.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** ItemProd database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ItemProdTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ItemProdTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected ItemProdSQLiteAdapter adapter;

	protected ItemProd entity;
	protected ContentResolver provider;

	protected ArrayList<ItemProd> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new ItemProdSQLiteAdapter(this.ctx);

		this.entities = new ArrayList<ItemProd>(ItemProdDataLoader.getInstance(this.ctx).getMap().values());
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}

		this.nbEntities += ItemProdDataLoader.getInstance(this.ctx).getMap().size();
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
			ItemProd itemProd = ItemProdUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(itemProd, 0);
				values.remove(ItemProdSQLiteAdapter.COL_ID);
				result = this.provider.insert(ItemProdProviderAdapter.ITEMPROD_URI, values);

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
		ItemProd result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(ItemProdProviderAdapter.ITEMPROD_URI + "/" + this.entity.getId()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ItemProdUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<ItemProd> result = null;
		try {
			Cursor c = this.provider.query(ItemProdProviderAdapter.ITEMPROD_URI, this.adapter.getCols(), null, null, null);
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
			ItemProd itemProd = ItemProdUtils.generateRandom(this.ctx);

			try {
				itemProd.setId(this.entity.getId());

				ContentValues values = this.adapter.itemToContentValues(itemProd, 0);
				result = this.provider.update(
					Uri.parse(ItemProdProviderAdapter.ITEMPROD_URI
						+ "/"
						+ itemProd.getId()),
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
			ItemProd itemProd = ItemProdUtils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(itemProd, 0);
				values.remove(ItemProdSQLiteAdapter.COL_ID);

				result = this.provider.update(ItemProdProviderAdapter.ITEMPROD_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(ItemProdProviderAdapter.ITEMPROD_URI + "/" + this.entity.getId()), null, null);

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
				result = this.provider.delete(ItemProdProviderAdapter.ITEMPROD_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
