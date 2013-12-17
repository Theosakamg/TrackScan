/**************************************************************************
 * ItemProdUtilsBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.test.utils.base;

import android.content.Context;
import junit.framework.Assert;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.test.utils.*;



import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.fixture.OrderProdDataLoader;
import java.util.ArrayList;

public abstract class ItemProdUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static ItemProd generateRandom(Context ctx){
		ItemProd itemProd = new ItemProd();

		itemProd.setId(TestUtils.generateRandomInt(0,100) + 1);
		itemProd.setName("name_"+TestUtils.generateRandomString(10));
		ArrayList<OrderProd> itemss =
			new ArrayList<OrderProd>(OrderProdDataLoader.getInstance(ctx).getMap().values());
		if (!itemss.isEmpty()) {
			itemProd.setItems(itemss.get(TestUtils.generateRandomInt(0, itemss.size())));
		}

		return itemProd;
	}

	public static boolean equals(ItemProd itemProd1, ItemProd itemProd2){
		boolean ret = true;
		Assert.assertNotNull(itemProd1);
		Assert.assertNotNull(itemProd2);
		if (itemProd1!=null && itemProd2 !=null){
			Assert.assertEquals(itemProd1.getId(), itemProd2.getId());
			Assert.assertEquals(itemProd1.getName(), itemProd2.getName());
			if (itemProd1.getItems() != null
					&& itemProd2.getItems() != null) {
				Assert.assertEquals(itemProd1.getItems().getId(),
						itemProd2.getItems().getId());

			}
		}

		return ret;
	}
}

