/**************************************************************************
 * LogProdEditFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.logprod;

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
import com.tactfactory.tracscan.entity.LogProd;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ItemState;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.widget.DateTimeWidget;

import com.tactfactory.tracscan.harmony.widget.SingleEntityWidget;
import com.tactfactory.tracscan.harmony.widget.EnumSpinner;
import com.tactfactory.tracscan.menu.SaveMenuWrapper.SaveMenuInterface;

import com.tactfactory.tracscan.provider.utils.LogProdProviderUtils;
import com.tactfactory.tracscan.provider.utils.ZoneProviderUtils;
import com.tactfactory.tracscan.provider.utils.UserProviderUtils;
import com.tactfactory.tracscan.provider.utils.ItemProdProviderUtils;

/** LogProd create fragment.
 *
 * This fragment gives you an interface to edit a LogProd.
 *
 * @see android.app.Fragment
 */
public class LogProdEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected LogProd model = new LogProd();

	/** curr.fields View. */
	/** createDate DateTime View. */
	protected DateTimeWidget createDateView;
	/** stateAction View. */
	protected EnumSpinner stateActionView;
	/** The zoneLogged chooser component. */
	protected SingleEntityWidget zoneLoggedWidget;
	/** The zoneLogged Adapter. */
	protected SingleEntityWidget.EntityAdapter<Zone>
			zoneLoggedAdapter;
	/** The userLogged chooser component. */
	protected SingleEntityWidget userLoggedWidget;
	/** The userLogged Adapter. */
	protected SingleEntityWidget.EntityAdapter<User>
			userLoggedAdapter;
	/** The itemLogged chooser component. */
	protected SingleEntityWidget itemLoggedWidget;
	/** The itemLogged Adapter. */
	protected SingleEntityWidget.EntityAdapter<ItemProd>
			itemLoggedAdapter;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.createDateView = (DateTimeWidget) view.findViewById(
				R.id.logprod_createdate);
		this.stateActionView = (EnumSpinner) view.findViewById(
				R.id.logprod_stateaction);
		this.stateActionView.setEnum(ItemState.class);
		this.zoneLoggedAdapter =
				new SingleEntityWidget.EntityAdapter<Zone>() {
			@Override
			public String entityToString(Zone item) {
				return String.valueOf(item.getId());
			}
		};
		this.zoneLoggedWidget =
			(SingleEntityWidget) view.findViewById(R.id.logprod_zonelogged_button);
		this.zoneLoggedWidget.setAdapter(this.zoneLoggedAdapter);
		this.userLoggedAdapter =
				new SingleEntityWidget.EntityAdapter<User>() {
			@Override
			public String entityToString(User item) {
				return String.valueOf(item.getId());
			}
		};
		this.userLoggedWidget =
			(SingleEntityWidget) view.findViewById(R.id.logprod_userlogged_button);
		this.userLoggedWidget.setAdapter(this.userLoggedAdapter);
		this.itemLoggedAdapter =
				new SingleEntityWidget.EntityAdapter<ItemProd>() {
			@Override
			public String entityToString(ItemProd item) {
				return String.valueOf(item.getId());
			}
		};
		this.itemLoggedWidget =
			(SingleEntityWidget) view.findViewById(R.id.logprod_itemlogged_button);
		this.itemLoggedWidget.setAdapter(this.itemLoggedAdapter);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getCreateDate() != null) {
			this.createDateView.setDateTime(this.model.getCreateDate());
		}
		if (this.model.getStateAction() != null) {
			this.stateActionView.setSelectedItem(this.model.getStateAction());
		}

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setCreateDate(this.createDateView.getDateTime());

		this.model.setStateAction((ItemState) this.stateActionView.getSelectedItem());

		this.model.setZoneLogged(this.zoneLoggedAdapter.getSelectedItem());

		this.model.setUserLogged(this.userLoggedAdapter.getSelectedItem());

		this.model.setItemLogged(this.itemLoggedAdapter.getSelectedItem());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.createDateView.getDateTime() == null) {
			error = R.string.logprod_createdate_invalid_field_error;
		}
		if (this.zoneLoggedAdapter.getSelectedItem() == null) {
			error = R.string.logprod_zonelogged_invalid_field_error;
		}
		if (this.userLoggedAdapter.getSelectedItem() == null) {
			error = R.string.logprod_userlogged_invalid_field_error;
		}
		if (this.itemLoggedAdapter.getSelectedItem() == null) {
			error = R.string.logprod_itemlogged_invalid_field_error;
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
				inflater.inflate(R.layout.fragment_logprod_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (LogProd) intent.getParcelableExtra(
				LogProd.PARCEL);

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
		private final LogProd entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final LogProdEditFragment fragment,
					final LogProd entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.logprod_progress_save_title),
					this.ctx.getString(
							R.string.logprod_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new LogProdProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("LogProdEditFragment", e.getMessage());
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
						R.string.logprod_error_create));
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
		private LogProdEditFragment fragment;
		/** zoneLogged list. */
		private ArrayList<Zone> zoneLoggedList;
		/** userLogged list. */
		private ArrayList<User> userLoggedList;
		/** itemLogged list. */
		private ArrayList<ItemProd> itemLoggedList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final LogProdEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.logprod_progress_load_relations_title),
				this.ctx.getString(
					R.string.logprod_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.zoneLoggedList = 
				new ZoneProviderUtils(this.ctx).queryAll();
			this.userLoggedList = 
				new UserProviderUtils(this.ctx).queryAll();
			this.itemLoggedList = 
				new ItemProdProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onZoneLoggedLoaded(this.zoneLoggedList);
			this.fragment.onUserLoggedLoaded(this.userLoggedList);
			this.fragment.onItemLoggedLoaded(this.itemLoggedList);

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
	 * Called when zoneLogged have been loaded.
	 * @param items The loaded items
	 */
	protected void onZoneLoggedLoaded(ArrayList<Zone> items) {
		this.zoneLoggedAdapter.loadData(items);
		
		for (Zone item : items) {
			if (item.getId() == this.model.getZoneLogged().getId()) {
				this.zoneLoggedAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when userLogged have been loaded.
	 * @param items The loaded items
	 */
	protected void onUserLoggedLoaded(ArrayList<User> items) {
		this.userLoggedAdapter.loadData(items);
		
		for (User item : items) {
			if (item.getId() == this.model.getUserLogged().getId()) {
				this.userLoggedAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when itemLogged have been loaded.
	 * @param items The loaded items
	 */
	protected void onItemLoggedLoaded(ArrayList<ItemProd> items) {
		this.itemLoggedAdapter.loadData(items);
		
		for (ItemProd item : items) {
			if (item.getId() == this.model.getItemLogged().getId()) {
				this.itemLoggedAdapter.selectItem(item);
			}
		}
	}
}
