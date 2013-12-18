/**************************************************************************
 * ItemProdCreateFragment.java, tracscan Android
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

/**
 * ItemProd create fragment.
 *
 * This fragment gives you an interface to create a ItemProd.
 */
public class ItemProdCreateFragment extends HarmonyFragment
			implements OnValidationListener {
	/** Model data. */
	protected ItemProd model = new ItemProd();

	/** Fields View. */
	/** name View. */
	protected EditText nameView;
	/** The items chooser component. */
	protected SingleEntityWidget itemsWidget;
	/** The items Adapter. */
	protected SingleEntityWidget.EntityAdapter<OrderProd> 
				itemsAdapter;
	/** Save button. */
	protected ValidationButtons validationButtons;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.nameView =
			(EditText) view.findViewById(R.id.itemprod_name);
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

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getName() != null) {
			this.nameView.setText(this.model.getName());
		}

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
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
		final View view = inflater.inflate(
				R.layout.fragment_itemprod_create,
				container,
				false);

		this.initializeComponent(view);
		this.loadData();
		return view;
	}

	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class CreateTask extends AsyncTask<Void, Void, Uri> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to persist. */
		private final ItemProd entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final ItemProdCreateFragment fragment,
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
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new ItemProdProviderUtils(this.ctx).insert(
						this.entity);

			return result;
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			if (result != null) {
				final HarmonyDrawerActivity activity =
										 (HarmonyDrawerActivity) this.ctx;
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(
						this.ctx.getString(
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
		private ItemProdCreateFragment fragment;
		/** items list. */
		private ArrayList<OrderProd> itemsList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final ItemProdCreateFragment fragment) {
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
			this.fragment.itemsAdapter.loadData(this.itemsList);
			this.progress.dismiss();
		}
	}

	@Override
	public void onValidationSelected() {
		if (this.validateData()) {
			this.saveData();
			new CreateTask(this, this.model).execute();
		}
	}

	@Override
	public void onCancelSelected() {
		this.getActivity().finish();
	}
}
