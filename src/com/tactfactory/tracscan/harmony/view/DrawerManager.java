package com.tactfactory.tracscan.harmony.view;

import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.view.itemprod.ItemProdListActivity;
import com.tactfactory.tracscan.view.user.UserListActivity;
import com.tactfactory.tracscan.view.orderprod.OrderProdListActivity;
import com.tactfactory.tracscan.view.zone.ZoneListActivity;
import com.tactfactory.tracscan.view.license.LicenseActivity;
import com.tactfactory.tracscan.view.logprod.LogProdListActivity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

/**
 * Class managing menu actions for Drawer Menu.
 */
public class DrawerManager implements OnClickListener {
	/** Linked activity. */
	private HarmonyDrawerActivity drawerActivity;
	/** Drawer layout. */
	private DrawerLayout drawerLayout;
	/** Gravity. */
	private int gravity;
	
	/**
	 * Constructor.
	 *
	 * @param drawerActivity Linked activity,
	 * @param leftLayoutId Left menu layout id
	 */
	public DrawerManager(
			HarmonyDrawerActivity drawerActivity,
			int leftLayoutId) {
		
		this.drawerActivity = drawerActivity;
		this.gravity = this.drawerActivity.getGravity();
		this.drawerLayout = this.drawerActivity.getDrawerLayout();

		this.drawerActivity.setMenuLeft(leftLayoutId);
		this.initButtons();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

			case R.id.menu_itemprod_list_button:
				intent = new Intent(this.drawerActivity,
							ItemProdListActivity.class);
				break;

			case R.id.menu_user_list_button:
				intent = new Intent(this.drawerActivity,
							UserListActivity.class);
				break;

			case R.id.menu_orderprod_list_button:
				intent = new Intent(this.drawerActivity,
							OrderProdListActivity.class);
				break;

			case R.id.menu_zone_list_button:
				intent = new Intent(this.drawerActivity,
							ZoneListActivity.class);
				break;

			case R.id.menu_logprod_list_button:
				intent = new Intent(this.drawerActivity,
							LogProdListActivity.class);
				break;
				
			case R.id.menu_license_button:
				intent = new Intent(this.drawerActivity,
							LicenseActivity.class);
				break;

		}

		if (intent != null) {
			this.drawerLayout.closeDrawer(this.gravity);
			this.drawerActivity.startActivity(intent);
		}
	}
	
	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
		FrameLayout menuLeftView = this.drawerActivity.getMenuLeftView();
		menuLeftView.findViewById(
				R.id.menu_itemprod_list_button)
					.setOnClickListener(this);
		menuLeftView.findViewById(
				R.id.menu_user_list_button)
					.setOnClickListener(this);
		menuLeftView.findViewById(
				R.id.menu_orderprod_list_button)
					.setOnClickListener(this);
		menuLeftView.findViewById(
				R.id.menu_zone_list_button)
					.setOnClickListener(this);
		menuLeftView.findViewById(
				R.id.menu_logprod_list_button)
					.setOnClickListener(this);
		menuLeftView.findViewById(
				R.id.menu_license_button)
					.setOnClickListener(this);
	}
}

