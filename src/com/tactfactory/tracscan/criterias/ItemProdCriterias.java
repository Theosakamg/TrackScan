/**************************************************************************
 * ItemProdCriterias.java, tracscan Android
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

import com.tactfactory.tracscan.entity.ItemProd;

/**
 * ItemProdCriterias.
 *
 * This class can help you forge requests for your ItemProd Entity.
 * For more details, see CriteriasBase.
 */
public class ItemProdCriterias extends CriteriasBase<ItemProd> {
	/** String to parcel itemProdCriteria. */
	public static final String PARCELABLE =
			"itemProdCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ItemProdCriterias(final GroupType type) {
		super(type);
	}
}
