package com.tactfactory.tracscan.view.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.provider.utils.UserProviderUtils;
import com.tactfactory.tracscan.provider.utils.ZoneProviderUtils;
import com.tactfactory.tracscan.view.logprod.LogProdLoggerActivity;

public class UserAuthActivity extends HarmonyDrawerActivity 
		implements OnClickListener {

	protected ArrayList<User> users;
	protected ArrayList<Zone> zones;
	
	protected Spinner zoneSpinner;
	protected Spinner loginSpinner;
	protected EditText passView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_auth);
		
		this.loadZone();
		this.loadUser();
		
		this.passView = 
				(EditText) this.findViewById(R.id.user_password_edit);
		
		Button btn = 
				(Button) this.findViewById(R.id.user_auth_button);
		btn.setOnClickListener(this);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.passView.setText("");
	}

	/**
	 * Bind Loader user
	 */
	private void loadUser() {
		this.loginSpinner = 
				(Spinner) this.findViewById(R.id.user_login_spinner);
		
		this.users = new UserProviderUtils(this).queryAll();
		ArrayAdapter<User> loginSpinnerAdapter = new ArrayAdapter<User>(
				this,
				android.R.layout.simple_spinner_item, 
				this.users);
		
		loginSpinnerAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		this.loginSpinner.setAdapter(loginSpinnerAdapter);
	}
	
	/**
	 * Bind Loader zone
	 */
	private void loadZone() {
		this.zoneSpinner = 
				(Spinner) this.findViewById(R.id.zone_name_spinner);
		
		this.zones = new ZoneProviderUtils(this).queryAll();
		ArrayAdapter<Zone> zoneSpinnerAdapter = new ArrayAdapter<Zone>(
				this,
				android.R.layout.simple_spinner_item, 
				this.zones);
		
		zoneSpinnerAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		this.zoneSpinner.setAdapter(zoneSpinnerAdapter);
	}

	@Override
	public void onClick(View v) {
		final String passwd = this.passView.getText().toString();
		final User user = (User) this.loginSpinner.getSelectedItem();
		final Zone zone = (Zone) this.zoneSpinner.getSelectedItem();
		
		if (user.getPasswd().equals(passwd)) {
			Intent intent = new Intent(this, LogProdLoggerActivity.class);
			intent.putExtra(User.PARCEL, (Parcelable) user);
			intent.putExtra(Zone.PARCEL, (Parcelable) zone);
			this.startActivity(intent);
		} else {
			Toast.makeText(this, "Password and username incorrect.", Toast.LENGTH_SHORT).show();
		}
		
	}
}
