/**************************************************************************
 * UserEditFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.user;



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
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.AccountType;

import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;

import com.tactfactory.tracscan.harmony.widget.EnumSpinner;
import com.tactfactory.tracscan.menu.SaveMenuWrapper.SaveMenuInterface;

import com.tactfactory.tracscan.provider.utils.UserProviderUtils;

/** User create fragment.
 *
 * This fragment gives you an interface to edit a User.
 *
 * @see android.app.Fragment
 */
public class UserEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected User model = new User();

	/** curr.fields View. */
	/** type View. */
	protected EnumSpinner typeView;
	/** login View. */
	protected EditText loginView;
	/** passwd View. */
	protected EditText passwdView;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.typeView = (EnumSpinner) view.findViewById(
				R.id.user_type);
		this.typeView.setEnum(AccountType.class);
		this.loginView = (EditText) view.findViewById(
				R.id.user_login);
		this.passwdView = (EditText) view.findViewById(
				R.id.user_passwd);
	}

	/** Load data from model to curr.fields view. */
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

	/** Save data from curr.fields view to model. */
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
		final View view =
				inflater.inflate(R.layout.fragment_user_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (User) intent.getParcelableExtra(
				User.PARCEL);

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
		private final User entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final UserEditFragment fragment,
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
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new UserProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("UserEditFragment", e.getMessage());
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
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

}
