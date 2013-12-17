/**************************************************************************
 * OrderProdUtilsBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.test.utils.base;

import android.content.Context;
import junit.framework.Assert;
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.test.utils.*;



import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.fixture.ItemProdDataLoader;
import com.tactfactory.tracscan.entity.ProductType;
import com.tactfactory.tracscan.entity.MaterialType;
import java.util.ArrayList;

public abstract class OrderProdUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static OrderProd generateRandom(Context ctx){
		OrderProd orderProd = new OrderProd();

		orderProd.setId(TestUtils.generateRandomInt(0,100) + 1);
		orderProd.setLogin("login_"+TestUtils.generateRandomString(10));
		orderProd.setProductType(ProductType.values()[TestUtils.generateRandomInt(0,ProductType.values().length)]);
		orderProd.setMaterialType(MaterialType.values()[TestUtils.generateRandomInt(0,MaterialType.values().length)]);
		orderProd.setQuantity(TestUtils.generateRandomInt(0,100));
		ArrayList<ItemProd> itemss =
			new ArrayList<ItemProd>(ItemProdDataLoader.getInstance(ctx).getMap().values());
		ArrayList<ItemProd> relatedItemss = new ArrayList<ItemProd>();
		if (!itemss.isEmpty()) {
			relatedItemss.add(itemss.get(TestUtils.generateRandomInt(0, itemss.size())));
			orderProd.setItems(relatedItemss);
		}

		return orderProd;
	}

	public static boolean equals(OrderProd orderProd1, OrderProd orderProd2){
		boolean ret = true;
		Assert.assertNotNull(orderProd1);
		Assert.assertNotNull(orderProd2);
		if (orderProd1!=null && orderProd2 !=null){
			Assert.assertEquals(orderProd1.getId(), orderProd2.getId());
			Assert.assertEquals(orderProd1.getLogin(), orderProd2.getLogin());
			Assert.assertEquals(orderProd1.getProductType(), orderProd2.getProductType());
			Assert.assertEquals(orderProd1.getMaterialType(), orderProd2.getMaterialType());
			Assert.assertEquals(orderProd1.getQuantity(), orderProd2.getQuantity());
			if (orderProd1.getItems() != null
					&& orderProd2.getItems() != null) {
				Assert.assertEquals(orderProd1.getItems().size(),
					orderProd2.getItems().size());
				for (int i=0;i<orderProd1.getItems().size();i++){
					Assert.assertEquals(orderProd1.getItems().get(i).getId(),
								orderProd2.getItems().get(i).getId());
				}
			}
		}

		return ret;
	}
}

