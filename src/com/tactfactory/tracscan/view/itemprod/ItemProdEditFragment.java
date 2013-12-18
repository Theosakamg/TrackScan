/**************************************************************************
 * ItemProdEditFragment.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
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

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;

import com.tactfactory.tracscan.harmony.widget.SingleEntityWidget;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons.OnValidationListener;

import com.tactfactory.tracscan.provider.utils.ItemProdProviderUtils;
import com.tactfactory.tracscan.provider.utils.OrderProdProviderUtils;

/** ItemProd create fragment.
 *
 * This fragment gives you an interface to edit a ItemProd.
 *
 * @see android.app.Fragment
 */
public class ItemProdEditFragment extends HarmonyFragment
			implements OnValidationListener {
	/** Model data. */
	protected ItemProd model = new ItemProd();

	/** curr.fields View. */
	/** name View. */
	protected EditText nameView;
	/** The items chooser component. */
	protected SingleEntityWidget itemsWidget;
	/** The items Adapter. */
	protected SingleEntityWidget.EntityAdapter<OrderProd>
			itemsAdapter;
	/** Save button. */
	protected ValidationButtons validationButtons;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.nameView = (EditText) view.findViewById(
				R.id.itemprod_name);
		this.itemsAdapter =
				new SingleEntityWidget.EntityAdapter<OrderProd>() {
			@Override
			public String entityToString(OrderProd item) {
				return String.valueOf(item.getId());
			}
		};
		this.itemsWidget =
			(SingleEntityWidget) view.findViewById(R.id.itemprod_items_button);
		this.itemsWidget.setAdapter(this.itemsAdapter);

		this.validationButtons = (ValidationButtons) view.findViewById(
					R.id.itemprod_validation);
		this.validationButtons.setListener(this);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getName() != null) {
			this.nameView.setText(this.model.getName());
		}

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setName(this.nameView.getEditableText().toString());

		this.model.setOrder(this.itemsAdapter.getSelectedItem());

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
		if (this.itemsAdapter.getSelectedItem() == null) {
			error = R.string.itemprod_items_invalid_field_error;
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
		this.model = (ItemProd) intent.getSerializableExtra(
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
		/** items list. */
		private ArrayList<OrderProd> itemsList;

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
			this.itemsList = 
				new OrderProdProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onItemsLoaded(this.itemsList);

			this.progress.dismiss();
		}
	}

	@Override
	public void onValidationSelected() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

	@Override
	public void onCancelSelected() {
		this.getActivity().finish();
	}

	/**
	 * Called when items have been loaded.
	 * @param items The loaded items
	 */
	protected void onItemsLoaded(ArrayList<OrderProd> items) {
		this.itemsAdapter.loadData(items);
		
		for (OrderProd item : items) {
			if (item.getId() == this.model.getOrder().getId()) {
				this.itemsAdapter.selectItem(item);
			}
		}
	}
}
