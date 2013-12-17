/**************************************************************************
 * OrderProdEditFragment.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.orderprod;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
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
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.entity.ItemProd;
import com.tactfactory.tracscan.entity.ProductType;
import com.tactfactory.tracscan.entity.MaterialType;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;
import com.tactfactory.tracscan.harmony.widget.MultiEntityWidget;
import com.tactfactory.tracscan.harmony.widget.EnumSpinner;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons.OnValidationListener;
import com.tactfactory.tracscan.provider.OrderProdProviderAdapter;
import com.tactfactory.tracscan.provider.utils.OrderProdProviderUtils;
import com.tactfactory.tracscan.provider.utils.ItemProdProviderUtils;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;

/** OrderProd create fragment.
 *
 * This fragment gives you an interface to edit a OrderProd.
 *
 * @see android.app.Fragment
 */
public class OrderProdEditFragment extends HarmonyFragment
			implements OnValidationListener {
	/** Model data. */
	protected OrderProd model = new OrderProd();

	/** curr.fields View. */
	/** login View. */
	protected EditText loginView;
	/** productType View. */
	protected EnumSpinner productTypeView;
	/** materialType View. */
	protected EnumSpinner materialTypeView;
	/** quantity View. */
	protected EditText quantityView;
	/** The items chooser component. */
	protected MultiEntityWidget itemsWidget;
	/** The items Adapter. */
	protected MultiEntityWidget.EntityAdapter<ItemProd>
			itemsAdapter;
	/** Save button. */
	protected ValidationButtons validationButtons;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.loginView = (EditText) view.findViewById(
				R.id.orderprod_login);
		this.productTypeView = (EnumSpinner) view.findViewById(
				R.id.orderprod_producttype);
		this.productTypeView.setEnum(ProductType.class);
		this.materialTypeView = (EnumSpinner) view.findViewById(
				R.id.orderprod_materialtype);
		this.materialTypeView.setEnum(MaterialType.class);
		this.quantityView = (EditText) view.findViewById(
				R.id.orderprod_quantity);
		this.itemsAdapter =
				new MultiEntityWidget.EntityAdapter<ItemProd>() {
			@Override
			public String entityToString(ItemProd item) {
				return String.valueOf(item.getId());
			}
		};
		this.itemsWidget = (MultiEntityWidget) view.findViewById(
						R.id.orderprod_items_button);
		this.itemsWidget.setAdapter(this.itemsAdapter);

		this.validationButtons = (ValidationButtons) view.findViewById(
					R.id.orderprod_validation);
		this.validationButtons.setListener(this);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getLogin() != null) {
			this.loginView.setText(this.model.getLogin());
		}
		if (this.model.getProductType() != null) {
			this.productTypeView.setSelectedItem(this.model.getProductType());
		}
		if (this.model.getMaterialType() != null) {
			this.materialTypeView.setSelectedItem(this.model.getMaterialType());
		}
		this.quantityView.setText(String.valueOf(this.model.getQuantity()));

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setLogin(this.loginView.getEditableText().toString());

		this.model.setProductType((ProductType) this.productTypeView.getSelectedItem());

		this.model.setMaterialType((MaterialType) this.materialTypeView.getSelectedItem());

		this.model.setQuantity(Integer.parseInt(
					this.quantityView.getEditableText().toString()));

		this.model.setItems(this.itemsAdapter.getCheckedItems());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.loginView.getText().toString().trim())) {
			error = R.string.orderprod_login_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.quantityView.getText().toString().trim())) {
			error = R.string.orderprod_quantity_invalid_field_error;
		}
		if (this.itemsAdapter.getCheckedItems().isEmpty()) {
			error = R.string.orderprod_items_invalid_field_error;
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
				inflater.inflate(R.layout.fragment_orderprod_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (OrderProd) intent.getSerializableExtra(
				OrderProd.PARCEL);

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
		private final OrderProd entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final OrderProdEditFragment fragment,
					final OrderProd entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.orderprod_progress_save_title),
					this.ctx.getString(
							R.string.orderprod_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new OrderProdProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("OrderProdEditFragment", e.getMessage());
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
						R.string.orderprod_error_create));
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
		private OrderProdEditFragment fragment;
		/** items list. */
		private ArrayList<ItemProd> itemsList;
	/** items list. */
		private ArrayList<ItemProd> associatedItemsList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final OrderProdEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.orderprod_progress_load_relations_title),
				this.ctx.getString(
					R.string.orderprod_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.itemsList = 
				new ItemProdProviderUtils(this.ctx).queryAll();
			Uri itemsUri = OrderProdProviderAdapter.ORDERPROD_URI;
			itemsUri = Uri.withAppendedPath(itemsUri, 
									String.valueOf(this.fragment.model.getId()));
			itemsUri = Uri.withAppendedPath(itemsUri, "items");
			Cursor itemsCursor = 
					this.ctx.getContentResolver().query(
							itemsUri,
							new String[]{ItemProdSQLiteAdapter.ALIASED_COL_ID},
							null,
							null, 
							null);
			
			if (itemsCursor != null && itemsCursor.getCount() > 0) {
				this.associatedItemsList = new ArrayList<ItemProd>();
				while (itemsCursor.moveToNext()) {
					int itemsId = itemsCursor.getInt(
							itemsCursor.getColumnIndex(
									ItemProdSQLiteAdapter.COL_ID));
					for (ItemProd items : this.itemsList) {
						if (items.getId() == itemsId) {
							this.associatedItemsList.add(items);
						}
					}
				}
				itemsCursor.close();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.model.setItems(this.associatedItemsList);
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
	protected void onItemsLoaded(ArrayList<ItemProd> items) {
		this.itemsAdapter.loadData(items);
		this.itemsAdapter.setCheckedItems(this.model.getItems());
	}
}
