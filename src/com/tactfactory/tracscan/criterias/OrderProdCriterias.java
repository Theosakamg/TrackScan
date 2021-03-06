/**************************************************************************
 * OrderProdCriterias.java, tracscan Android
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

import com.tactfactory.tracscan.entity.OrderProd;

/**
 * OrderProdCriterias.
 *
 * This class can help you forge requests for your OrderProd Entity.
 * For more details, see CriteriasBase.
 */
public class OrderProdCriterias extends CriteriasBase<OrderProd> {
	/** Serial Version UID */
	private static final long serialVersionUID = 8892952233559831820L;
	
	/** String to parcel orderProdCriteria. */
	public static final String PARCELABLE =
			"orderProdCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public OrderProdCriterias(final GroupType type) {
		super(type);
	}
}
