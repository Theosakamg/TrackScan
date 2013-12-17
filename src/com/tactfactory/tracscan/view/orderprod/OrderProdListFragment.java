/**************************************************************************
 * OrderProdListFragment.java, tracscan Android
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

import com.tactfactory.tracscan.criterias.OrderProdCriterias;
import com.tactfactory.tracscan.data.OrderProdSQLiteAdapter;
import com.tactfactory.tracscan.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.tactfactory.tracscan.provider.OrderProdProviderAdapter;
import com.tactfactory.tracscan.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.entity.OrderProd;

/** OrderProd list fragment.
 *
 * This fragment gives you an interface to list all your OrderProds.
 *
 * @see android.app.Fragment
 */
public class OrderProdListFragment
		extends HarmonyListFragment<OrderProd>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected OrderProdListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_orderprod_list,
						null);

		this.initializeHackCustomList(view,
				R.id.orderprodProgressLayout,
				R.id.orderprodListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.orderprod_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new OrderProdListAdapter(this.getActivity(), this);

		// Start out with a progress indicator.
		this.setListShown(false);

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/* Do click action inside your fragment here. */
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		Loader<Cursor> result = null;
		OrderProdCriterias crit = null;
		if (bundle != null) {
			crit = (OrderProdCriterias) bundle.get(
						OrderProdCriterias.PARCELABLE);
		}

		if (crit != null) {
			result = new OrderProdListLoader(this.getActivity(),
				OrderProdProviderAdapter.ORDERPROD_URI,
				OrderProdSQLiteAdapter.ALIASED_COLS,
				crit,
				null);
		} else {
			result = new OrderProdListLoader(this.getActivity(),
				OrderProdProviderAdapter.ORDERPROD_URI,
				OrderProdSQLiteAdapter.ALIASED_COLS,
				null,
				null,
				null);
		}
		return result;
	}

	@Override
	public void onLoadFinished(
			Loader<Cursor> loader,
			Cursor data) {

		// Set the new data in the adapter.
		data.setNotificationUri(this.getActivity().getContentResolver(),
				OrderProdProviderAdapter.ORDERPROD_URI);

		ArrayList<OrderProd> users = new OrderProdSQLiteAdapter(
				this.getActivity()).cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new OrderProdListAdapter
					.OrderProdSectionIndexer(users));
		this.mAdapter.setNotifyOnChange(true);
		this.mAdapter.notifyDataSetChanged();
		this.mAdapter.setPinnedPartitionHeadersEnabled(false);
		this.mAdapter.setSectionHeaderDisplayEnabled(false);

		if (this.getListAdapter() == null) {
			this.setListAdapter(this.mAdapter);
		}

		// The list should now be shown.
		if (this.isResumed()) {
			this.setListShown(true);
		} else {
			this.setListShownNoAnimation(true);
		}

		super.onLoadFinished(loader, data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Clear the data in the adapter.
		this.mAdapter.clear();
	}

	@Override
	public void onClickAdd() {
		Intent intent = new Intent(this.getActivity(),
					OrderProdCreateActivity.class);
		this.startActivity(intent);
	}

}
