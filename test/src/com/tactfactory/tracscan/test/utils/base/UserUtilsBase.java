/**************************************************************************
 * UserUtilsBase.java, tracscan Android
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
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.test.utils.*;



import com.tactfactory.tracscan.entity.AccountType;

public abstract class UserUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static User generateRandom(Context ctx){
		User user = new User();

		user.setId(TestUtils.generateRandomInt(0,100) + 1);
		user.setType(AccountType.values()[TestUtils.generateRandomInt(0,AccountType.values().length)]);
		user.setLogin("login_"+TestUtils.generateRandomString(10));
		user.setPasswd("passwd_"+TestUtils.generateRandomString(10));

		return user;
	}

	public static boolean equals(User user1, User user2){
		boolean ret = true;
		Assert.assertNotNull(user1);
		Assert.assertNotNull(user2);
		if (user1!=null && user2 !=null){
			Assert.assertEquals(user1.getId(), user2.getId());
			Assert.assertEquals(user1.getType(), user2.getType());
			Assert.assertEquals(user1.getLogin(), user2.getLogin());
			Assert.assertEquals(user1.getPasswd(), user2.getPasswd());
		}

		return ret;
	}
}

