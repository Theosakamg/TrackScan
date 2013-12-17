/**************************************************************************
 * UserCreateFragment.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.user;



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
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.AccountType;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;

import com.tactfactory.tracscan.harmony.widget.EnumSpinner;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons;
import com.tactfactory.tracscan.harmony.widget.ValidationButtons.OnValidationListener;
import com.tactfactory.tracscan.provider.utils.UserProviderUtils;

/**
 * User create fragment.
 *
 * This fragment gives you an interface to create a User.
 */
public class UserCreateFragment extends HarmonyFragment
			implements OnValidationListener {
	/** Model data. */
	protected User model = new User();

	/** Fields View. */
	/** type View. */
	protected EnumSpinner typeView;
	/** login View. */
	protected EditText loginView;
	/** passwd View. */
	protected EditText passwdView;
	/** Save button. */
	protected ValidationButtons validationButtons;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.typeView =
			(EnumSpinner) view.findViewById(R.id.user_type);
		this.typeView.setEnum(AccountType.class);
		this.loginView =
			(EditText) view.findViewById(R.id.user_login);
		this.passwdView =
			(EditText) view.findViewById(R.id.user_passwd);

		this.validationButtons = (ValidationButtons) view.findViewById(
					R.id.user_validation);
		this.validationButtons.setListener(this);
	}

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getType() != null) {
			this.typeView.setSelectedItem(this.model.getType());
		}
		if (this.model.getLogin() != null) {
			this.loginView.setText(this.model.getLogin());
		}
		if (this.model.getPasswd() != null) {
			this.passwdView.setText(this.model.getPasswd());
		}


	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setType((AccountType) this.typeView.getSelectedItem());

		this.model.setLogin(this.loginView.getEditableText().toString());

		this.model.setPasswd(this.passwdView.getEditableText().toString());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.loginView.getText().toString().trim())) {
			error = R.string.user_login_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.passwdView.getText().toString().trim())) {
			error = R.string.user_passwd_invalid_field_error;
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
				R.layout.fragment_user_create,
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
		private final User entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final UserCreateFragment fragment,
				final User entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.user_progress_save_title),
					this.ctx.getString(
							R.string.user_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new UserProviderUtils(this.ctx).insert(
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
								R.string.user_error_create));
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
