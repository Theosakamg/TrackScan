/**************************************************************************
 * LogProdUtilsBase.java, tracscan Android
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
import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.test.utils.*;




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

		return logProd;
	}

	public static boolean equals(LogProd logProd1, LogProd logProd2){
		boolean ret = true;
		Assert.assertNotNull(logProd1);
		Assert.assertNotNull(logProd2);
		if (logProd1!=null && logProd2 !=null){
			Assert.assertEquals(logProd1.getId(), logProd2.getId());
		}

		return ret;
	}
}

