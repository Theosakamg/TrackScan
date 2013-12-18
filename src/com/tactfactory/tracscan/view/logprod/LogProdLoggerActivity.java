package com.tactfactory.tracscan.view.logprod;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.entity.OrderProd;
import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.tactfactory.tracscan.provider.utils.OrderProdProviderUtils;

public class LogProdLoggerActivity extends HarmonyDrawerActivity implements OnNavigationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_logprod_create_imie);
		
		ActionBar actionBar = this.getSupportActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayList<OrderProd> orderProds =
				new OrderProdProviderUtils(this).queryAll();

		ArrayAdapter<OrderProd> adapter = new ArrayAdapter<OrderProd>(
				this,
				android.R.layout.simple_dropdown_item_1line,
				orderProds);
		
		actionBar.setListNavigationCallbacks(adapter, this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
}
