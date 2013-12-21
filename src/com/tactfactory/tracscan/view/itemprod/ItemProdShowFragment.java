/**************************************************************************
 * ItemProdShowFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

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
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ZoneSQLiteAdapter;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.harmony.util.DateUtils;
import com.tactfactory.tracscan.harmony.view.DeleteDialog;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.view.MultiLoader;
import com.tactfactory.tracscan.harmony.view.MultiLoader.UriLoadedCallback;
import com.tactfactory.tracscan.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.tactfactory.tracscan.provider.utils.ItemProdProviderUtils;
import com.tactfactory.tracscan.provider.ItemProdProviderAdapter;

/** ItemProd show fragment.
 *
 * This fragment gives you an interface to show a ItemProd.
 * 
 * @see android.app.Fragment
 */
public class ItemProdShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected ItemProd model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** name View. */
	protected TextView nameView;
	/** state View. */
	protected TextView stateView;
	/** updateDate View. */
	protected TextView updateDateView;
	/** orderCustomer View. */
	protected TextView orderCustomerView;
	/** currentZone View. */
	protected TextView currentZoneView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no ItemProd. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.nameView =
			(TextView) view.findViewById(
					R.id.itemprod_name);
		this.stateView =
			(TextView) view.findViewById(
					R.id.itemprod_state);
		this.updateDateView =
			(TextView) view.findViewById(
					R.id.itemprod_updatedate);
		this.orderCustomerView =
			(TextView) view.findViewById(
					R.id.itemprod_ordercustomer);
		this.currentZoneView =
			(TextView) view.findViewById(
					R.id.itemprod_currentzone);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.itemprod_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.itemprod_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getName() != null) {
			this.nameView.setText(this.model.getName());
		}
		if (this.model.getState() != null) {
			this.stateView.setText(this.model.getState().toString());
		}
		if (this.model.getUpdateDate() != null) {
			this.updateDateView.setText(
					DateUtils.formatDateTimeToString(
							this.model.getUpdateDate()));
		}
		if (this.model.getOrderCustomer() != null) {
			this.orderCustomerView.setText(
					String.valueOf(this.model.getOrderCustomer().getId()));
		}
		if (this.model.getCurrentZone() != null) {
			this.currentZoneView.setText(
					String.valueOf(this.model.getCurrentZone().getId()));
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
        				R.layout.fragment_itemprod_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((ItemProd) intent.getParcelableExtra(ItemProd.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The ItemProd to get the data from.
	 */
	public void update(ItemProd item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					ItemProdProviderAdapter.ITEMPROD_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					ItemProdShowFragment.this.onItemProdLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/ordercustomer"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					ItemProdShowFragment.this.onOrderCustomerLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/currentzone"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					ItemProdShowFragment.this.onCurrentZoneLoaded(c);
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
	public void onItemProdLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new ItemProdSQLiteAdapter(getActivity()).cursorToItem(
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
	public void onOrderCustomerLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setOrderCustomer(
							new OrderProdSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setOrderCustomer(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onCurrentZoneLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.setCurrentZone(
							new ZoneSQLiteAdapter(getActivity()).cursorToItem(c));
					this.loadData();
			}
			} else {
				this.model.setCurrentZone(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the ItemProdEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									ItemProdEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("ItemProd", this.model);
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
		private ItemProd item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build ItemProdSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final ItemProd item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new ItemProdProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				ItemProdShowFragment.this.onPostDelete();
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

