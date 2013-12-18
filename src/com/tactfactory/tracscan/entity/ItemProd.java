package com.tactfactory.tracscan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class ItemProd  implements Serializable , Parcelable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1080925123920871057L;

	/** Key Constant for parcelable/serialization. */
	public final static String PARCEL = "ItemProd";
	
	@Id
	@Column(type = Type.INTEGER, hidden = true)
	protected int id;
	
	@Column
	protected String name;
	
	@Column(type = Type.ENUM)
	protected ItemState state;
	
	@Column(type=Type.DATETIME)
	protected DateTime updateDate;
	
	@ManyToOne
	protected OrderProd order;
	
	@OneToMany
	protected Zone currentZone;

	/**
	 * Default constructor.
	 */
	public ItemProd() {

	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		dest.writeString(this.getName());

		dest.writeParcelable(this.getOrder(), flags);
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
		this.setName(parc.readString());

		this.setOrder((OrderProd) parc.readParcelable(OrderProd.class.getClassLoader()));
	}

	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public ItemProd(Parcel parc) {
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
	public static final Parcelable.Creator<ItemProd> CREATOR
	    = new Parcelable.Creator<ItemProd>() {
		public ItemProd createFromParcel(Parcel in) {
		    return new ItemProd(in);
		}
		
		public ItemProd[] newArray(int size) {
		    return new ItemProd[size];
		}
	};

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
	 * @return the name
	 */
	public String getName() {
	     return this.name;
	}

	/**
	 * @param value the name to set
	 */
	public void setName(final String value) {
	     this.name = value;
	}

	/**
	 * @return the items
	 */
	public OrderProd getOrder() {
	     return this.order;
	}

	/**
	 * @param value the items to set
	 */
	public void setOrder(final OrderProd value) {
	     this.order = value;
	}

}
