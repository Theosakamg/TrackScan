/**************************************************************************
 * LogProdListAdapter.java, tracscan Android
 *
 * Copyright 2013 Mickael Gaillard / TACTfactory
 * Description : 
 * Author(s)   : Harmony
 * Licence     : all right reserved
 * Last update : Dec 21, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.logprod;

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
import com.tactfactory.tracscan.entity.LogProd;

/**
 * List adapter for LogProd entity.
 */
public class LogProdListAdapter
		extends HeaderAdapter<LogProd>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public LogProdListAdapter(Context ctx) {
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
	public LogProdListAdapter(Context context,
			int resource,
			int textViewResourceId,
			List<LogProd> objects) {
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
	public LogProdListAdapter(Context context,
			int resource,
			int textViewResourceId,
			LogProd[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 */
	public LogProdListAdapter(Context context,
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
	public LogProdListAdapter(Context context,
			int textViewResourceId,
			List<LogProd> objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public LogProdListAdapter(Context context,
			int textViewResourceId,
			LogProd[] objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 */
	public LogProdListAdapter(Context context,
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
			super(context, attrs, R.layout.row_logprod);
		}

		/** Populate row with a LogProd.
		 *
		 * @param model LogProd data
		 */
		public void populate(final LogProd model) {
			View convertView = this.getInnerLayout();
			TextView createDateView =
				(TextView) convertView.findViewById(
						R.id.row_logprod_createdate);
			TextView stateActionView =
				(TextView) convertView.findViewById(
						R.id.row_logprod_stateaction);
			TextView zoneLoggedView =
				(TextView) convertView.findViewById(
						R.id.row_logprod_zonelogged);
			TextView userLoggedView =
				(TextView) convertView.findViewById(
						R.id.row_logprod_userlogged);
			TextView itemLoggedView =
				(TextView) convertView.findViewById(
						R.id.row_logprod_itemlogged);


			if (model.getCreateDate() != null) {
				createDateView.setText(DateUtils.formatDateTimeToString(model.getCreateDate()));
			}
			if (model.getStateAction() != null) {
				stateActionView.setText(model.getStateAction().name());
			}
			zoneLoggedView.setText(
					String.valueOf(model.getZoneLogged().getId()));
			userLoggedView.setText(
					String.valueOf(model.getUserLogged().getId()));
			itemLoggedView.setText(
					String.valueOf(model.getItemLogged().getId()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class LogProdSectionIndexer
					extends HeaderSectionIndexer<LogProd>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public LogProdSectionIndexer(List<LogProd> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(LogProd item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				LogProd item,
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
	public int getPosition(LogProd item) {
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
