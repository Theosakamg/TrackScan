package com.tactfactory.tracscan;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class TestActivity extends android.app.Activity {

	public void toto() {
		
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		 String[] projection = new String[] { ContactsContract.Contacts._ID,
		   ContactsContract.Contacts.DISPLAY_NAME,
		   ContactsContract.Contacts.HAS_PHONE_NUMBER };
		 String where = null;
		 String[] whereArgs = null;
		 String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
		   + " COLLATE LOCALIZED ASC";

		Cursor cursor = this.getContentResolver().query(uri, projection, where, whereArgs, sortOrder);
	}
}
