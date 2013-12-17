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
import com.tactfactory.tracscan.criterias.base.Criteria;

import com.tactfactory.tracscan.entity.OrderProd;

/**
 * OrderProdCriterias.
 *
 * This class can help you forge requests for your OrderProd Entity.
 * For more details, see CriteriasBase.
 */
public class OrderProdCriterias extends CriteriasBase<OrderProd> {
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
