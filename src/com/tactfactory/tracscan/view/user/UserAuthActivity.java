package com.tactfactory.tracscan.view.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.criterias.UserCriterias;
import com.tactfactory.tracscan.criterias.base.CriteriasBase.GroupType;
import com.tactfactory.tracscan.data.UserSQLiteAdapter;
import com.tactfactory.tracscan.entity.User;
import com.tactfactory.tracscan.entity.Zone;
import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.provider.UserProviderAdapter;
import com.tactfactory.tracscan.provider.utils.UserProviderUtils;
import com.tactfactory.tracscan.provider.utils.ZoneProviderUtils;

public class UserAuthActivity extends HarmonyDrawerActivity {

	ArrayList<User> users;
	ArrayList<Zone> zones;
	
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_auth);
		
		loadZone();
		loadUser();
	}

	/**
	 * Bind Loader user
	 */
	private void loadUser() {
		Spinner loginSpinner = 
				(Spinner) this.findViewById(R.id.user_login_spinner);
		
		this.users = new UserProviderUtils(this).queryAll();
		ArrayAdapter<User> loginSpinnerAdapter = new ArrayAdapter<User>(
				this,
				android.R.layout.simple_spinner_item, 
				this.users);
		
		loginSpinnerAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		loginSpinner.setAdapter(loginSpinnerAdapter);
	}
	
	/**
	 * Bind Loader zone
	 */
	private void loadZone() {
		Spinner zoneSpinner = 
				(Spinner) this.findViewById(R.id.zone_name_spinner);
		
		this.zones = new ZoneProviderUtils(this).queryAll();
		ArrayAdapter<Zone> zoneSpinnerAdapter = new ArrayAdapter<Zone>(
				this,
				android.R.layout.simple_spinner_item, 
				this.zones);
		
		zoneSpinnerAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		zoneSpinner.setAdapter(zoneSpinnerAdapter);
	}
}
