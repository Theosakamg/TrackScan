/**************************************************************************
 * ZoneUtilsBase.java, tracscan Android
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
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.test.utils.*;




public abstract class ZoneUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Zone generateRandom(Context ctx){
		Zone zone = new Zone();

		zone.setId(TestUtils.generateRandomInt(0,100) + 1);
		zone.setName("name_"+TestUtils.generateRandomString(10));
		zone.setQuantity(TestUtils.generateRandomInt(0,100));

		return zone;
	}

	public static boolean equals(Zone zone1, Zone zone2){
		boolean ret = true;
		Assert.assertNotNull(zone1);
		Assert.assertNotNull(zone2);
		if (zone1!=null && zone2 !=null){
			Assert.assertEquals(zone1.getId(), zone2.getId());
			Assert.assertEquals(zone1.getName(), zone2.getName());
			Assert.assertEquals(zone1.getQuantity(), zone2.getQuantity());
		}

		return ret;
	}
}

