/**************************************************************************
 * ZoneCriterias.java, tracscan Android
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
import com.tactfactory.tracscan.criterias.base.Criteria;

import com.tactfactory.tracscan.entity.Zone;

/**
 * ZoneCriterias.
 *
 * This class can help you forge requests for your Zone Entity.
 * For more details, see CriteriasBase.
 */
public class ZoneCriterias extends CriteriasBase<Zone> {
	/** String to parcel zoneCriteria. */
	public static final String PARCELABLE =
			"zoneCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ZoneCriterias(final GroupType type) {
		super(type);
	}
}
