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

@Entity
public class LogProd  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"LogProd";

	/** Serial Version UID */
	private static final long serialVersionUID = 321829571509107215L;

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	protected int id;
	
	@Column(type = Type.DATETIME)
	protected DateTime createDate;
	
	@Column(type = Type.ENUM)
	protected ItemState state;
	
	@ManyToOne
	protected Zone zone;
	
	@ManyToOne
	protected User user;
	
	@ManyToOne
	protected ItemProd item;

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
		if (this.getCreateDate() != null) {
			dest.writeInt(1);
			dest.writeString(this.getCreateDate().toString());
		} else {
			dest.writeInt(0);
		}

		if (this.getState() != null) {
			dest.writeInt(1);
			dest.writeString(this.getState().getValue());
		} else {
			dest.writeInt(0);
		}

		dest.writeParcelable(this.getZone(), flags);

		dest.writeParcelable(this.getUser(), flags);

		dest.writeParcelable(this.getItem(), flags);
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
		if (parc.readInt() == 1) {
			this.setCreateDate(new DateTime(parc.readString()));
		}

		int stateBool = parc.readInt();
		if (stateBool == 1) {
			this.setState(ItemState.fromValue(parc.readString()));
		}

		this.setZone((Zone) parc.readParcelable(Zone.class.getClassLoader()));

		this.setUser((User) parc.readParcelable(User.class.getClassLoader()));

		this.setItem((ItemProd) parc.readParcelable(ItemProd.class.getClassLoader()));
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

	/**
	 * @return the createDate
	 */
	public DateTime getCreateDate() {
	     return this.createDate;
	}

	/**
	 * @param value the createDate to set
	 */
	public void setCreateDate(final DateTime value) {
	     this.createDate = value;
	}

	/**
	 * @return the state
	 */
	public ItemState getState() {
	     return this.state;
	}

	/**
	 * @param value the state to set
	 */
	public void setState(final ItemState value) {
	     this.state = value;
	}

	/**
	 * @return the zone
	 */
	public Zone getZone() {
	     return this.zone;
	}

	/**
	 * @param value the zone to set
	 */
	public void setZone(final Zone value) {
	     this.zone = value;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
	     return this.user;
	}

	/**
	 * @param value the user to set
	 */
	public void setUser(final User value) {
	     this.user = value;
	}

	/**
	 * @return the item
	 */
	public ItemProd getItem() {
	     return this.item;
	}

	/**
	 * @param value the item to set
	 */
	public void setItem(final ItemProd value) {
	     this.item = value;
	}

}
