/**************************************************************************
 * UserCriterias.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 16, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.criterias;

import com.tactfactory.tracscan.criterias.base.CriteriasBase;

import com.tactfactory.tracscan.entity.User;

/**
 * UserCriterias.
 *
 * This class can help you forge requests for your User Entity.
 * For more details, see CriteriasBase.
 */
public class UserCriterias extends CriteriasBase<User> {
	/** Serial Version UID */
	private static final long serialVersionUID = -1491500993763538953L;
	
	/** String to parcel userCriteria. */
	public static final String PARCELABLE =
			"userCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public UserCriterias(final GroupType type) {
		super(type);
	}
}
