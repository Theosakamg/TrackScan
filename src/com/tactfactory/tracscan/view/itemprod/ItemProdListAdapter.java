/**************************************************************************
 * ItemProdListAdapter.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.itemprod;

import java.util.List;

import com.tactfactory.tracscan.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.tactfactory.tracscan.harmony.util.DateUtils;
import com.tactfactory.tracscan.harmony.view.HarmonyDrawerActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.tactfactory.tracscan.entity.ItemProd;

/**
 * List adapter for ItemProd entity.
 */
public class ItemProdListAdapter
		extends HeaderAdapter<ItemProd>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ItemProdListAdapter(Context ctx) {
		super(ctx);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ItemProdListAdapter(Context context,
			int resource,
			int textViewResourceId,
			List<ItemProd> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     *
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ItemProdListAdapter(Context context,
			int resource,
			int textViewResourceId,
			ItemProd[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 */
	public ItemProdListAdapter(Context context,
			int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ItemProdListAdapter(Context context,
			int textViewResourceId,
			List<ItemProd> objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ItemProdListAdapter(Context context,
			int textViewResourceId,
			ItemProd[] objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 */
	public ItemProdListAdapter(Context context,
			int textViewResourceId) {
		super(context, textViewResourceId);
	}

	/** Holder row. */
	private static class ViewHolder extends SelectionItemView {

		/**
		 * Constructor.
		 *
		 * @param context The context
		 */
		public ViewHolder(Context context) {
			this(context, null);
		}
		
		/**
		 * Constructor.
		 *
		 * @param context The context
		 * @param attrs The attribute set
		 */
		public ViewHolder(Context context, AttributeSet attrs) {
			super(context, attrs, R.layout.row_itemprod);
		}

		/** Populate row with a ItemProd.
		 *
		 * @param model ItemProd data
		 */
		public void populate(final ItemProd model) {
			View convertView = this.getInnerLayout();
			TextView nameView =
				(TextView) convertView.findViewById(
						R.id.row_itemprod_name);
			TextView stateView =
				(TextView) convertView.findViewById(
						R.id.row_itemprod_state);
			TextView updateDateView =
				(TextView) convertView.findViewById(
						R.id.row_itemprod_updatedate);
			TextView orderCustomerView =
				(TextView) convertView.findViewById(
						R.id.row_itemprod_ordercustomer);
			TextView currentZoneView =
				(TextView) convertView.findViewById(
						R.id.row_itemprod_currentzone);


			if (model.getName() != null) {
				nameView.setText(model.getName());
			}
			if (model.getState() != null) {
				stateView.setText(model.getState().name());
			}
			if (model.getUpdateDate() != null) {
				updateDateView.setText(DateUtils.formatDateTimeToString(model.getUpdateDate()));
			}
			orderCustomerView.setText(
					String.valueOf(model.getOrderCustomer().getId()));
			currentZoneView.setText(
					String.valueOf(model.getCurrentZone().getId()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class ItemProdSectionIndexer
					extends HeaderSectionIndexer<ItemProd>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public ItemProdSectionIndexer(List<ItemProd> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(ItemProd item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				ItemProd item,
				int position) {
    	final ViewHolder view;
    	
    	if (itemView != null) {
    		view = (ViewHolder) itemView;
    	} else {
    		view = new ViewHolder(this.getContext());
		}

    	if (!((HarmonyDrawerActivity) this.getContext()).isDualMode()) {
    		view.setActivatedStateSupported(false);
		}
    	
    	view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

	@Override
	public int getPosition(ItemProd item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.getId() == this.getItem(i).getId()) {
					result = i;
				}
			}
		}
		return result;
	}
}
