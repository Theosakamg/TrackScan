/**************************************************************************
 * LogProdShowFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.logprod;

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
import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;
import com.tactfactory.tracscan.data.UserSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.harmony.util.DateUtils;
import com.tactfactory.tracscan.harmony.view.DeleteDialog;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.view.MultiLoader;
import com.tactfactory.tracscan.harmony.view.MultiLoader.UriLoadedCallback;
import com.tactfactory.tracscan.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.tactfactory.tracscan.provider.utils.LogProdProviderUtils;
import com.tactfactory.tracscan.provider.LogProdProviderAdapter;

/** LogProd show fragment.
 *
 * This fragment gives you an interface to show a LogProd.
 * 
 * @see android.app.Fragment
 */
public class LogProdShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected LogProd model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** createDate View. */
	protected TextView createDateView;
	/** stateAction View. */
	protected TextView stateActionView;
	/** zoneLogged View. */
	protected TextView zoneLoggedView;
	/** userLogged View. */
	protected TextView userLoggedView;
	/** itemLogged View. */
	protected TextView itemLoggedView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no LogProd. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.createDateView =
			(TextView) view.findViewById(
					R.id.logprod_createdate);
		this.stateActionView =
			(TextView) view.findViewById(
					R.id.logprod_stateaction);
		this.zoneLoggedView =
			(TextView) view.findViewById(
					R.id.logprod_zonelogged);
		this.userLoggedView =
			(TextView) view.findViewById(
					R.id.logprod_userlogged);
		this.itemLoggedView =
			(TextView) view.findViewById(
					R.id.logprod_itemlogged);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.logprod_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.logprod_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getCreateDate() != null) {
			this.createDateView.setText(
					DateUtils.formatDateTimeToString(
							this.model.getCreateDate()));
		}
		if (this.model.getStateAction() != null) {
			this.stateActionView.setText(this.model.getStateAction().toString());
		}
		if (this.model.getZoneLogged() != null) {
			this.zoneLoggedView.setText(
					String.valueOf(this.model.getZoneLogged().getId()));
		}
		if (this.model.getUserLogged() != null) {
			this.userLoggedView.setText(
					String.valueOf(this.model.getUserLogged().getId()));
		}
		if (this.model.getItemLogged() != null) {
			this.itemLoggedView.setText(
					String.valueOf(this.model.getItemLogged().getId()));
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
        				R.layout.fragment_logprod_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((LogProd) intent.getParcelableExtra(LogProd.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The LogProd to get the data from.
	 */
	public void update(LogProd item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					LogProdProviderAdapter.LOGPROD_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogProdShowFragment.this.onLogProdLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/zonelogged"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogProdShowFragment.this.onZoneLoggedLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/userlogged"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogProdShowFragment.this.onUserLoggedLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/itemlogged"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					LogProdShowFragment.this.onItemLoggedLoaded(c);
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
	public void onLogProdLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new LogProdSQLiteAdapter(getActivity()).cursorToItem(
						c,
						this.model);
			this.loadData();
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onZoneLoggedLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setZoneLogged(
							new ZoneSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setZoneLogged(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onUserLoggedLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setUserLogged(
							new UserSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setUserLogged(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onItemLoggedLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setItemLogged(
							new ItemProdSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setItemLogged(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the LogProdEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									LogProdEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("LogProd", this.model);
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
		private LogProd item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build LogProdSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final LogProd item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new LogProdProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				LogProdShowFragment.this.onPostDelete();
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

