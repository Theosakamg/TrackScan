/**************************************************************************
 * HomeActivity.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;
import com.tactfactory.tracscan.view.itemprod.ItemProdCreateActivity;
import com.tactfactory.tracscan.view.itemprod.ItemProdListActivity;
import com.tactfactory.tracscan.view.user.UserCreateActivity;
import com.tactfactory.tracscan.view.user.UserListActivity;
import com.tactfactory.tracscan.view.orderprod.OrderProdCreateActivity;
import com.tactfactory.tracscan.view.orderprod.OrderProdListActivity;
import com.tactfactory.tracscan.view.zone.ZoneCreateActivity;
import com.tactfactory.tracscan.view.zone.ZoneListActivity;
import com.tactfactory.tracscan.view.logprod.LogProdCreateActivity;
import com.tactfactory.tracscan.view.logprod.LogProdListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
		implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		this.initButtons();
	}

	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
		this.findViewById(R.id.itemprod_create_button)
						.setOnClickListener(this);
		this.findViewById(R.id.itemprod_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.user_create_button)
						.setOnClickListener(this);
		this.findViewById(R.id.user_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.orderprod_create_button)
						.setOnClickListener(this);
		this.findViewById(R.id.orderprod_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.zone_create_button)
						.setOnClickListener(this);
		this.findViewById(R.id.zone_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.logprod_create_button)
						.setOnClickListener(this);
		this.findViewById(R.id.logprod_list_button)
						.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.itemprod_create_button:
				intent = new Intent(this,
						ItemProdCreateActivity.class);
				break;

			case R.id.itemprod_list_button:
				intent = new Intent(this,
						ItemProdListActivity.class);
				break;

			case R.id.user_create_button:
				intent = new Intent(this,
						UserCreateActivity.class);
				break;

			case R.id.user_list_button:
				intent = new Intent(this,
						UserListActivity.class);
				break;

			case R.id.orderprod_create_button:
				intent = new Intent(this,
						OrderProdCreateActivity.class);
				break;

			case R.id.orderprod_list_button:
				intent = new Intent(this,
						OrderProdListActivity.class);
				break;

			case R.id.zone_create_button:
				intent = new Intent(this,
						ZoneCreateActivity.class);
				break;

			case R.id.zone_list_button:
				intent = new Intent(this,
						ZoneListActivity.class);
				break;

			case R.id.logprod_create_button:
				intent = new Intent(this,
						LogProdCreateActivity.class);
				break;

			case R.id.logprod_list_button:
				intent = new Intent(this,
						LogProdListActivity.class);
				break;

			default:
				intent = null;
				break;
		}

		if (intent != null) {
			this.startActivity(intent);
		}
	}

}
