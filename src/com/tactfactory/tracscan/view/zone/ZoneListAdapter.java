/**************************************************************************
 * ZoneListAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.zone;

import java.util.List;

import com.tactfactory.tracscan.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.tactfactory.tracscan.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.tactfactory.tracscan.entity.Zone;

/**
 * List adapter for Zone entity.
 */
public class ZoneListAdapter
		extends HeaderAdapter<Zone>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 * @param fragment fragment
	 */
	public ZoneListAdapter(Context ctx,
			ZoneListFragment fragment) {
		super(ctx);
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
			super(context, attrs, R.layout.row_zone);
		}

		/** Populate row with a Zone.
		 *
		 * @param model Zone data
		 */
		public void populate(final Zone model) {
			View convertView = this.getInnerLayout();
			TextView nameView =
				(TextView) convertView.findViewById(
						R.id.row_zone_name);
			TextView quantityView =
				(TextView) convertView.findViewById(
						R.id.row_zone_quantity);


			if (model.getName() != null) {
				nameView.setText(model.getName());
			}
			quantityView.setText(String.valueOf(model.getQuantity()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class ZoneSectionIndexer
					extends HeaderSectionIndexer<Zone>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public ZoneSectionIndexer(List<Zone> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(Zone item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				Zone item,
				int position) {
    	final ViewHolder view;
    	
    	if (itemView != null) {
    		view = (ViewHolder) itemView;
    	} else {
    		view = new ViewHolder(this.getContext());
		}

    	if (!((HarmonyFragmentActivity) this.getContext()).isDualMode()) {
    		view.setActivatedStateSupported(false);
		}
    	
    	view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

	@Override
	public int getPosition(Zone item) {
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
