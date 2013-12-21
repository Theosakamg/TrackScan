/**************************************************************************
 * LogProdCriterias.java, tracscan Android
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

import com.tactfactory.tracscan.entity.LogProd;

/**
 * LogProdCriterias.
 *
 * This class can help you forge requests for your LogProd Entity.
 * For more details, see CriteriasBase.
 */
public class LogProdCriterias extends CriteriasBase<LogProd> {
	/** Serial Version UID */
	private static final long serialVersionUID = -8541907071156946645L;
	
	/** String to parcel logProdCriteria. */
	public static final String PARCELABLE =
			"logProdCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public LogProdCriterias(final GroupType type) {
		super(type);
	}
}
