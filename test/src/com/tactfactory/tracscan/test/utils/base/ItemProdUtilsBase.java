/**************************************************************************
 * ItemProdUtilsBase.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 19, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.test.utils.base;

import android.content.Context;
import junit.framework.Assert;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.test.utils.*;



import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.fixture.OrderProdDataLoader;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.fixture.ZoneDataLoader;
import com.tactfactory.tracscan.entity.ItemState;
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
		itemProd.setState(ItemState.values()[TestUtils.generateRandomInt(0,ItemState.values().length)]);
		itemProd.setUpdateDate(TestUtils.generateRandomDateTime());
		ArrayList<OrderProd> orderCustomers =
			new ArrayList<OrderProd>(OrderProdDataLoader.getInstance(ctx).getMap().values());
		if (!orderCustomers.isEmpty()) {
			itemProd.setOrderCustomer(orderCustomers.get(TestUtils.generateRandomInt(0, orderCustomers.size())));
		}
		ArrayList<Zone> currentZones =
			new ArrayList<Zone>(ZoneDataLoader.getInstance(ctx).getMap().values());
		if (!currentZones.isEmpty()) {
			itemProd.setCurrentZone(currentZones.get(TestUtils.generateRandomInt(0, currentZones.size())));
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
			Assert.assertEquals(itemProd1.getState(), itemProd2.getState());
			Assert.assertEquals(itemProd1.getUpdateDate(), itemProd2.getUpdateDate());
			if (itemProd1.getOrderCustomer() != null
					&& itemProd2.getOrderCustomer() != null) {
				Assert.assertEquals(itemProd1.getOrderCustomer().getId(),
						itemProd2.getOrderCustomer().getId());

			}
			if (itemProd1.getCurrentZone() != null
					&& itemProd2.getCurrentZone() != null) {
				Assert.assertEquals(itemProd1.getCurrentZone().getId(),
						itemProd2.getCurrentZone().getId());

			}
		}

		return ret;
	}
}

