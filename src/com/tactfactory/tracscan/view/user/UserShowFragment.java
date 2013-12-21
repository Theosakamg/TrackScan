/**************************************************************************
 * UserShowFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.user;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.data.UserSQLiteAdapter;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.harmony.view.DeleteDialog;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.view.MultiLoader;
import com.tactfactory.tracscan.harmony.view.MultiLoader.UriLoadedCallback;
import com.tactfactory.tracscan.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.tactfactory.tracscan.provider.utils.UserProviderUtils;
import com.tactfactory.tracscan.provider.UserProviderAdapter;

/** User show fragment.
 *
 * This fragment gives you an interface to show a User.
 * 
 * @see android.app.Fragment
 */
public class UserShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected User model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** type View. */
	protected TextView typeView;
	/** login View. */
	protected TextView loginView;
	/** passwd View. */
	protected TextView passwdView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no User. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.typeView =
			(TextView) view.findViewById(
					R.id.user_type);
		this.loginView =
			(TextView) view.findViewById(
					R.id.user_login);
		this.passwdView =
			(TextView) view.findViewById(
					R.id.user_passwd);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.user_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.user_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getType() != null) {
			this.typeView.setText(this.model.getType().toString());
		}
		if (this.model.getLogin() != null) {
			this.loginView.setText(this.model.getLogin());
		}
		if (this.model.getPasswd() != null) {
			this.passwdView.setText(this.model.getPasswd());
		}
		} else {
    		this.dataLayout.setVisibility(View.GONE);
    		this.emptyText.setVisibility(View.VISIBLE);
    	}
    }

    @Override
    public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

    	// Inflate the layout for this fragment
        final View view =
        		inflater.inflate(
        				R.layout.fragment_user_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((User) intent.getParcelableExtra(User.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The User to get the data from.
	 */
	public void update(User item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					UserProviderAdapter.USER_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserShowFragment.this.onUserLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.init();
		}
    }

	/**
	 * Called when the entity has been loaded.
	 * 
	 * @param c The cursor of this entity
	 */
	public void onUserLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new UserSQLiteAdapter(getActivity()).cursorToItem(
						c,
						this.model);
			this.loadData();
		}
	}

	/**
	 * Calls the UserEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									UserEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("User", this.model);
		intent.putExtras(extras);

		this.getActivity().startActivity(intent);
	}

	/**
	 * Shows a confirmation dialog.
	 */
	@Override
	public void onClickDelete() {
		new DeleteDialog(this.getActivity(), this).show();
	}

	@Override
	public void onDeleteDialogClose(boolean ok) {
		if (ok) {
			new DeleteTask(this.getActivity(), this.model).execute();
		}
	}
	
	/** 
	 * Called when delete task is done.
	 */	
	public void onPostDelete() {
		if (this.deleteCallback != null) {
			this.deleteCallback.onItemDeleted();
		}
	}

	/**
	 * This class will remove the entity into the DB.
	 * It runs asynchronously.
	 */
	private class DeleteTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private Context ctx;
		/** Entity to delete. */
		private User item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build UserSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final User item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new UserProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				UserShowFragment.this.onPostDelete();
			}
			super.onPostExecute(result);
		}
		
		

	}
	
	/**
	 * Callback for item deletion.
	 */ 
	public interface DeleteCallback {
		/** Called when current item has been deleted. */
		void onItemDeleted();
	}
}

