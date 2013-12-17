/**************************************************************************
 * ZoneCreateFragment.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.zone;



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
import com.tactfactory.tracscan.entity.Zone;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;

import com.tactfactory.tracscan.harmony.widget.ValidationButtons;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons.OnValidationListener;
import com.tactfactory.tracscan.provider.utils.ZoneProviderUtils;

/**
 * Zone create fragment.
 *
 * This fragment gives you an interface to create a Zone.
 */
public class ZoneCreateFragment extends HarmonyFragment
			implements OnValidationListener {
	/** Model data. */
	protected Zone model = new Zone();

	/** Fields View. */
	/** name View. */
	protected EditText nameView;
	/** quantity View. */
	protected EditText quantityView;
	/** Save button. */
	protected ValidationButtons validationButtons;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.nameView =
			(EditText) view.findViewById(R.id.zone_name);
		this.quantityView =
			(EditText) view.findViewById(R.id.zone_quantity);

		this.validationButtons = (ValidationButtons) view.findViewById(
					R.id.zone_validation);
		this.validationButtons.setListener(this);
	}

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getName() != null) {
			this.nameView.setText(this.model.getName());
		}
		this.quantityView.setText(String.valueOf(this.model.getQuantity()));


	}

	/** Save data from fields view to model. */
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
		final View view = inflater.inflate(
				R.layout.fragment_zone_create,
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
		private final Zone entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final ZoneCreateFragment fragment,
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
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new ZoneProviderUtils(this.ctx).insert(
						this.entity);

			return result;
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			if (result != null) {
				final HarmonyFragmentActivity activity =
										 (HarmonyFragmentActivity) this.ctx;
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(
						this.ctx.getString(
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
