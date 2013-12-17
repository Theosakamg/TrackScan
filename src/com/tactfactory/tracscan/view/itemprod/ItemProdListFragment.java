/**************************************************************************
 * ItemProdListFragment.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

import java.util.ArrayList;

import com.tactfactory.tracscan.criterias.ItemProdCriterias;
import com.tactfactory.tracscan.data.ItemProdSQLiteAdapter;
import com.tactfactory.tracscan.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.tactfactory.tracscan.provider.ItemProdProviderAdapter;
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
import com.tactfactory.tracscan.entity.ItemProd;

/** ItemProd list fragment.
 *
 * This fragment gives you an interface to list all your ItemProds.
 *
 * @see android.app.Fragment
 */
public class ItemProdListFragment
		extends HarmonyListFragment<ItemProd>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected ItemProdListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_itemprod_list,
						null);

		this.initializeHackCustomList(view,
				R.id.itemprodProgressLayout,
				R.id.itemprodListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.itemprod_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new ItemProdListAdapter(this.getActivity(), this);

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
		ItemProdCriterias crit = null;
		if (bundle != null) {
			crit = (ItemProdCriterias) bundle.get(
						ItemProdCriterias.PARCELABLE);
		}

		if (crit != null) {
			result = new ItemProdListLoader(this.getActivity(),
				ItemProdProviderAdapter.ITEMPROD_URI,
				ItemProdSQLiteAdapter.ALIASED_COLS,
				crit,
				null);
		} else {
			result = new ItemProdListLoader(this.getActivity(),
				ItemProdProviderAdapter.ITEMPROD_URI,
				ItemProdSQLiteAdapter.ALIASED_COLS,
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
				ItemProdProviderAdapter.ITEMPROD_URI);

		ArrayList<ItemProd> users = new ItemProdSQLiteAdapter(
				this.getActivity()).cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new ItemProdListAdapter
					.ItemProdSectionIndexer(users));
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
					ItemProdCreateActivity.class);
		this.startActivity(intent);
	}

}
