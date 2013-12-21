/**************************************************************************
 * LogProdUtilsBase.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
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
		logProd.setStateAction(ItemState.values()[TestUtils.generateRandomInt(0,ItemState.values().length)]);
		ArrayList<Zone> zoneLoggeds =
			new ArrayList<Zone>(ZoneDataLoader.getInstance(ctx).getMap().values());
		if (!zoneLoggeds.isEmpty()) {
			logProd.setZoneLogged(zoneLoggeds.get(TestUtils.generateRandomInt(0, zoneLoggeds.size())));
		}
		ArrayList<User> userLoggeds =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!userLoggeds.isEmpty()) {
			logProd.setUserLogged(userLoggeds.get(TestUtils.generateRandomInt(0, userLoggeds.size())));
		}
		ArrayList<ItemProd> itemLoggeds =
			new ArrayList<ItemProd>(ItemProdDataLoader.getInstance(ctx).getMap().values());
		if (!itemLoggeds.isEmpty()) {
			logProd.setItemLogged(itemLoggeds.get(TestUtils.generateRandomInt(0, itemLoggeds.size())));
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
			Assert.assertEquals(logProd1.getStateAction(), logProd2.getStateAction());
			if (logProd1.getZoneLogged() != null
					&& logProd2.getZoneLogged() != null) {
				Assert.assertEquals(logProd1.getZoneLogged().getId(),
						logProd2.getZoneLogged().getId());

			}
			if (logProd1.getUserLogged() != null
					&& logProd2.getUserLogged() != null) {
				Assert.assertEquals(logProd1.getUserLogged().getId(),
						logProd2.getUserLogged().getId());

			}
			if (logProd1.getItemLogged() != null
					&& logProd2.getItemLogged() != null) {
				Assert.assertEquals(logProd1.getItemLogged().getId(),
						logProd2.getItemLogged().getId());

			}
		}

		return ret;
	}
}

