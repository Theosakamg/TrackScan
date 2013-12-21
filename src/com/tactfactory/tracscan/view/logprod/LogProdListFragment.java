/**************************************************************************
 * LogProdListFragment.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.logprod;

import java.util.ArrayList;

import com.tactfactory.tracscan.criterias.LogProdCriterias;
import com.tactfactory.tracscan.data.LogProdSQLiteAdapter;
import com.tactfactory.tracscan.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.tactfactory.tracscan.provider.LogProdProviderAdapter;
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
import com.tactfactory.tracscan.entity.LogProd;

/** LogProd list fragment.
 *
 * This fragment gives you an interface to list all your LogProds.
 *
 * @see android.app.Fragment
 */
public class LogProdListFragment
		extends HarmonyListFragment<LogProd>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected LogProdListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_logprod_list,
						null);

		this.initializeHackCustomList(view,
				R.id.logprodProgressLayout,
				R.id.logprodListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.logprod_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new LogProdListAdapter(this.getActivity());

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
		LogProdCriterias crit = null;
		if (bundle != null) {
			crit = (LogProdCriterias) bundle.get(
						LogProdCriterias.PARCELABLE);
		}

		if (crit != null) {
			result = new LogProdListLoader(this.getActivity(),
				LogProdProviderAdapter.LOGPROD_URI,
				LogProdSQLiteAdapter.ALIASED_COLS,
				crit,
				null);
		} else {
			result = new LogProdListLoader(this.getActivity(),
				LogProdProviderAdapter.LOGPROD_URI,
				LogProdSQLiteAdapter.ALIASED_COLS,
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
				LogProdProviderAdapter.LOGPROD_URI);

		ArrayList<LogProd> users = new LogProdSQLiteAdapter(
				this.getActivity()).cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new LogProdListAdapter
					.LogProdSectionIndexer(users));
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
					LogProdCreateActivity.class);
		this.startActivity(intent);
	}

}
