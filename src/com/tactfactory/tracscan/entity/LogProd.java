package com.tactfactory.tracscan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;

@Entity
public class LogProd  implements Serializable , Parcelable {

	/** Serial Version UID */
	private static final long serialVersionUID = 321829571509107215L;

	/** Key Constant for parcelable/serialization. */
	public final static String PARCEL = "LogProd";

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	protected int id;
	
	@Column
	protected DateTime at;

	/**
	 * Default constructor.
	 */
	public LogProd() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
	     return this.id;
	}

	/**
	 * @param value the id to set
	 */
	public void setId(final int value) {
	     this.id = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId(parc.readInt());
	}





	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public LogProd(Parcel parc) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.readFromParcel(parc);

		// You can  implement your own parcel mechanics here.

	}

	/* This method is not regenerated. You can implement your own parcel mechanics here. */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.writeToParcelRegen(dest, flags);

		// You can  implement your own parcel mechanics here.
	}

	@Override
	public int describeContents() {
		// This should return 0 
		// or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
		return 0;
	}

	/**
	 * Parcelable creator.
	 */
	public static final Parcelable.Creator<LogProd> CREATOR
	    = new Parcelable.Creator<LogProd>() {
		public LogProd createFromParcel(Parcel in) {
		    return new LogProd(in);
		}
		
		public LogProd[] newArray(int size) {
		    return new LogProd[size];
		}
	};

}
