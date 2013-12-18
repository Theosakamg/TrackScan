/**************************************************************************
 * OrderProdShowFragment.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.orderprod;

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
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.harmony.view.DeleteDialog;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.view.MultiLoader;
import com.tactfactory.tracscan.harmony.view.MultiLoader.UriLoadedCallback;
import com.tactfactory.tracscan.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.tactfactory.tracscan.provider.utils.OrderProdProviderUtils;
import com.tactfactory.tracscan.provider.OrderProdProviderAdapter;

/** OrderProd show fragment.
 *
 * This fragment gives you an interface to show a OrderProd.
 * 
 * @see android.app.Fragment
 */
public class OrderProdShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected OrderProd model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** login View. */
	protected TextView loginView;
	/** productType View. */
	protected TextView productTypeView;
	/** materialType View. */
	protected TextView materialTypeView;
	/** quantity View. */
	protected TextView quantityView;
	/** items View. */
	protected TextView itemsView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no OrderProd. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.loginView =
			(TextView) view.findViewById(
					R.id.orderprod_login);
		this.productTypeView =
			(TextView) view.findViewById(
					R.id.orderprod_producttype);
		this.materialTypeView =
			(TextView) view.findViewById(
					R.id.orderprod_materialtype);
		this.quantityView =
			(TextView) view.findViewById(
					R.id.orderprod_quantity);
		this.itemsView =
			(TextView) view.findViewById(
					R.id.orderprod_items);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.orderprod_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.orderprod_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getCustomer() != null) {
			this.loginView.setText(this.model.getCustomer());
		}
		if (this.model.getProductType() != null) {
			this.productTypeView.setText(this.model.getProductType().toString());
		}
		if (this.model.getMaterialType() != null) {
			this.materialTypeView.setText(this.model.getMaterialType().toString());
		}
		this.quantityView.setText(String.valueOf(this.model.getQuantity()));
		if (this.model.getItems() != null) {
			String itemsValue = "";
			for (ItemProd item : this.model.getItems()) {
				itemsValue += item.getId() + ",";
			}
			this.itemsView.setText(itemsValue);
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
        				R.layout.fragment_orderprod_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((OrderProd) intent.getParcelableExtra(OrderProd.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The OrderProd to get the data from.
	 */
	public void update(OrderProd item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader<OrderProd> loader = 
					new MultiLoader<OrderProd>(this, this.model);
			String baseUri = 
					OrderProdProviderAdapter.ORDERPROD_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					OrderProdShowFragment.this.onOrderProdLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/items"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					OrderProdShowFragment.this.onItemsLoaded(c);
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
	public void onOrderProdLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			new OrderProdSQLiteAdapter(getActivity()).cursorToItem(
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
	public void onItemsLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				this.model.setItems(
						new ItemProdSQLiteAdapter(getActivity()).cursorToItems(c));
					this.loadData();
			} else {
				this.model.setItems(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the OrderProdEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									OrderProdEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("OrderProd", this.model);
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
		private OrderProd item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build OrderProdSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final OrderProd item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new OrderProdProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				OrderProdShowFragment.this.onPostDelete();
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

