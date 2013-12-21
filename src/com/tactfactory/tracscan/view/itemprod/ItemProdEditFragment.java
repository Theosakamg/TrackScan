/**************************************************************************
 * ItemProdEditFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.sqlite.SQLiteException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.entity.ItemState;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.widget.DateTimeWidget;

import com.tactfactory.tracscan.harmony.widget.SingleEntityWidget;
import com.tactfactory.tracscan.harmony.widget.EnumSpinner;
import com.tactfactory.tracscan.menu.SaveMenuWrapper.SaveMenuInterface;

import com.tactfactory.tracscan.provider.utils.ItemProdProviderUtils;
import com.tactfactory.tracscan.provider.utils.OrderProdProviderUtils;
import com.tactfactory.tracscan.provider.utils.ZoneProviderUtils;

/** ItemProd create fragment.
 *
 * This fragment gives you an interface to edit a ItemProd.
 *
 * @see android.app.Fragment
 */
public class ItemProdEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected ItemProd model = new ItemProd();

	/** curr.fields View. */
	/** name View. */
	protected EditText nameView;
	/** state View. */
	protected EnumSpinner stateView;
	/** updateDate DateTime View. */
	protected DateTimeWidget updateDateView;
	/** The orderCustomer chooser component. */
	protected SingleEntityWidget orderCustomerWidget;
	/** The orderCustomer Adapter. */
	protected SingleEntityWidget.EntityAdapter<OrderProd>
			orderCustomerAdapter;
	/** The currentZone chooser component. */
	protected SingleEntityWidget currentZoneWidget;
	/** The currentZone Adapter. */
	protected SingleEntityWidget.EntityAdapter<Zone>
			currentZoneAdapter;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.nameView = (EditText) view.findViewById(
				R.id.itemprod_name);
		this.stateView = (EnumSpinner) view.findViewById(
				R.id.itemprod_state);
		this.stateView.setEnum(ItemState.class);
		this.updateDateView = (DateTimeWidget) view.findViewById(
				R.id.itemprod_updatedate);
		this.orderCustomerAdapter =
				new SingleEntityWidget.EntityAdapter<OrderProd>() {
			@Override
			public String entityToString(OrderProd item) {
				return String.valueOf(item.getId());
			}
		};
		this.orderCustomerWidget =
			(SingleEntityWidget) view.findViewById(R.id.itemprod_ordercustomer_button);
		this.orderCustomerWidget.setAdapter(this.orderCustomerAdapter);
		this.currentZoneAdapter =
				new SingleEntityWidget.EntityAdapter<Zone>() {
			@Override
			public String entityToString(Zone item) {
				return String.valueOf(item.getId());
			}
		};
		this.currentZoneWidget =
			(SingleEntityWidget) view.findViewById(R.id.itemprod_currentzone_button);
		this.currentZoneWidget.setAdapter(this.currentZoneAdapter);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getName() != null) {
			this.nameView.setText(this.model.getName());
		}
		if (this.model.getState() != null) {
			this.stateView.setSelectedItem(this.model.getState());
		}
		if (this.model.getUpdateDate() != null) {
			this.updateDateView.setDateTime(this.model.getUpdateDate());
		}

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setName(this.nameView.getEditableText().toString());

		this.model.setState((ItemState) this.stateView.getSelectedItem());

		this.model.setUpdateDate(this.updateDateView.getDateTime());

		this.model.setOrderCustomer(this.orderCustomerAdapter.getSelectedItem());

		this.model.setCurrentZone(this.currentZoneAdapter.getSelectedItem());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.nameView.getText().toString().trim())) {
			error = R.string.itemprod_name_invalid_field_error;
		}
		if (this.updateDateView.getDateTime() == null) {
			error = R.string.itemprod_updatedate_invalid_field_error;
		}
		if (this.orderCustomerAdapter.getSelectedItem() == null) {
			error = R.string.itemprod_ordercustomer_invalid_field_error;
		}
		if (this.currentZoneAdapter.getSelectedItem() == null) {
			error = R.string.itemprod_currentzone_invalid_field_error;
		}
	
		if (error > 0) {
			Toast.makeText(this.getActivity(),
				this.getActivity().getString(error),
				Toast.LENGTH_SHORT).show();
		}
		return error == 0;
	}
	@Override
	public View onCreateView(
				LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		final View view =
				inflater.inflate(R.layout.fragment_itemprod_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (ItemProd) intent.getParcelableExtra(
				ItemProd.PARCEL);

		this.initializeComponent(view);
		this.loadData();

		return view;
	}

	/**
	 * This class will update the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class EditTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to update. */
		private final ItemProd entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final ItemProdEditFragment fragment,
					final ItemProd entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.itemprod_progress_save_title),
					this.ctx.getString(
							R.string.itemprod_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new ItemProdProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("ItemProdEditFragment", e.getMessage());
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result > 0) {
				final HarmonyDrawerActivity activity =
						(HarmonyDrawerActivity) this.ctx;
				activity.setResult(HarmonyDrawerActivity.RESULT_OK);
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(this.ctx.getString(
						R.string.itemprod_error_create));
				builder.setPositiveButton(
						this.ctx.getString(android.R.string.yes),
						new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
																int which) {

							}
						});
				builder.show();
			}

			this.progress.dismiss();
		}
	}


	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class LoadTask extends AsyncTask<Void, Void, Void> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Progress Dialog. */
		private ProgressDialog progress;
		/** Fragment. */
		private ItemProdEditFragment fragment;
		/** orderCustomer list. */
		private ArrayList<OrderProd> orderCustomerList;
		/** currentZone list. */
		private ArrayList<Zone> currentZoneList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final ItemProdEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.itemprod_progress_load_relations_title),
				this.ctx.getString(
					R.string.itemprod_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.orderCustomerList = 
				new OrderProdProviderUtils(this.ctx).queryAll();
			this.currentZoneList = 
				new ZoneProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onOrderCustomerLoaded(this.orderCustomerList);
			this.fragment.onCurrentZoneLoaded(this.currentZoneList);

			this.progress.dismiss();
		}
	}

	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

	/**
	 * Called when orderCustomer have been loaded.
	 * @param items The loaded items
	 */
	protected void onOrderCustomerLoaded(ArrayList<OrderProd> items) {
		this.orderCustomerAdapter.loadData(items);
		
		for (OrderProd item : items) {
			if (item.getId() == this.model.getOrderCustomer().getId()) {
				this.orderCustomerAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when currentZone have been loaded.
	 * @param items The loaded items
	 */
	protected void onCurrentZoneLoaded(ArrayList<Zone> items) {
		this.currentZoneAdapter.loadData(items);
		
		for (Zone item : items) {
			if (item.getId() == this.model.getCurrentZone().getId()) {
				this.currentZoneAdapter.selectItem(item);
			}
		}
	}
}
