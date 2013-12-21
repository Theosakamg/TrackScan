/**************************************************************************
 * ZoneEditFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.zone;



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
import com.tactfactory.tracscan.entity.Zone;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;

import com.tactfactory.tracscan.menu.SaveMenuWrapper.SaveMenuInterface;

import com.tactfactory.tracscan.provider.utils.ZoneProviderUtils;

/** Zone create fragment.
 *
 * This fragment gives you an interface to edit a Zone.
 *
 * @see android.app.Fragment
 */
public class ZoneEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Zone model = new Zone();

	/** curr.fields View. */
	/** name View. */
	protected EditText nameView;
	/** quantity View. */
	protected EditText quantityView;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.nameView = (EditText) view.findViewById(
				R.id.zone_name);
		this.quantityView = (EditText) view.findViewById(
				R.id.zone_quantity);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		if (this.model.getName() != null) {
			this.nameView.setText(this.model.getName());
		}
		this.quantityView.setText(String.valueOf(this.model.getQuantity()));


	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setName(this.nameView.getEditableText().toString());

		this.model.setQuantity(Integer.parseInt(
					this.quantityView.getEditableText().toString()));

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.nameView.getText().toString().trim())) {
			error = R.string.zone_name_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.quantityView.getText().toString().trim())) {
			error = R.string.zone_quantity_invalid_field_error;
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
				inflater.inflate(R.layout.fragment_zone_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (Zone) intent.getParcelableExtra(
				Zone.PARCEL);

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
		private final Zone entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final ZoneEditFragment fragment,
					final Zone entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.zone_progress_save_title),
					this.ctx.getString(
							R.string.zone_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new ZoneProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("ZoneEditFragment", e.getMessage());
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
						R.string.zone_error_create));
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



	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

}
