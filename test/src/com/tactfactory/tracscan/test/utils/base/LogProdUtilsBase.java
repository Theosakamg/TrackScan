/**************************************************************************
 * LogProdUtilsBase.java, tracscan Android
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
import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.test.utils.*;



import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.fixture.ZoneDataLoader;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.fixture.UserDataLoader;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.fixture.ItemProdDataLoader;
import com.tactfactory.tracscan.entity.ItemState;
import java.util.ArrayList;

public abstract class LogProdUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static LogProd generateRandom(Context ctx){
		LogProd logProd = new LogProd();

		logProd.setId(TestUtils.generateRandomInt(0,100) + 1);
		logProd.setCreateDate(TestUtils.generateRandomDateTime());
		logProd.setState(ItemState.values()[TestUtils.generateRandomInt(0,ItemState.values().length)]);
		ArrayList<Zone> zones =
			new ArrayList<Zone>(ZoneDataLoader.getInstance(ctx).getMap().values());
		if (!zones.isEmpty()) {
			logProd.setZone(zones.get(TestUtils.generateRandomInt(0, zones.size())));
		}
		ArrayList<User> users =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!users.isEmpty()) {
			logProd.setUser(users.get(TestUtils.generateRandomInt(0, users.size())));
		}
		ArrayList<ItemProd> items =
			new ArrayList<ItemProd>(ItemProdDataLoader.getInstance(ctx).getMap().values());
		if (!items.isEmpty()) {
			logProd.setItem(items.get(TestUtils.generateRandomInt(0, items.size())));
		}

		return logProd;
	}

	public static boolean equals(LogProd logProd1, LogProd logProd2){
		boolean ret = true;
		Assert.assertNotNull(logProd1);
		Assert.assertNotNull(logProd2);
		if (logProd1!=null && logProd2 !=null){
			Assert.assertEquals(logProd1.getId(), logProd2.getId());
			Assert.assertEquals(logProd1.getCreateDate(), logProd2.getCreateDate());
			Assert.assertEquals(logProd1.getState(), logProd2.getState());
			if (logProd1.getZone() != null
					&& logProd2.getZone() != null) {
				Assert.assertEquals(logProd1.getZone().getId(),
						logProd2.getZone().getId());

			}
			if (logProd1.getUser() != null
					&& logProd2.getUser() != null) {
				Assert.assertEquals(logProd1.getUser().getId(),
						logProd2.getUser().getId());

			}
			if (logProd1.getItem() != null
					&& logProd2.getItem() != null) {
				Assert.assertEquals(logProd1.getItem().getId(),
						logProd2.getItem().getId());

			}
		}

		return ret;
	}
}

