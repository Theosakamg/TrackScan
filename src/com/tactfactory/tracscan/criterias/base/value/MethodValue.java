/**************************************************************************
 * MethodValue.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.criterias.base.value;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.base.Joiner;

/**
 * This criteria value represents a method (ie. strtf(param1, param2))
 */
public class MethodValue extends CriteriaValue {
	/** Serial Version UID */
	static final long serialVersionUID = 2344030558210488134L;

	/** Parameters. */
	private String[] values;
	
	/** Method used. */
	private String method;

	/**
	 * Contructor.
	 * @param method The method called
	 * @param values The method arguments
	 */
	public MethodValue(String method, String... values) {
		super();
		this.values = values;
		this.method = method;
	}

	/**
	 * Set the value of this StringValue.
	 * @param values The new arguments
	 */
	public void setValues(String... values) {
		this.values = values;
	}

	/**
	 * Get this StringValue's value.
	 * @return the values
	 */
	public String[] getValues() {
		return this.values;
	}

	@Override
	public String toSQLiteString() {
		String result = this.method + "(";
		result += Joiner.on(", ").skipNulls().join(this.values);
		result += ")";
		return result;
	}

	@Override
	public String toSQLiteSelection() {
		String result = this.method + "(";
		String[] interrogationMarks = new String[this.values.length];
		Arrays.fill(interrogationMarks, "?");
		result += Joiner.on(", ").skipNulls().join(interrogationMarks);
		result += ")";
		return result;
	}

	@Override
	public void toSQLiteSelectionArgs(final ArrayList<String> array) {
		for (String value : this.values) {
			array.add(value);
		}
	}
}

