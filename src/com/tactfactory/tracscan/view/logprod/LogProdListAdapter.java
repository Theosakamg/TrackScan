/**************************************************************************
 * LogProdListAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.logprod;

import java.util.List;

import com.tactfactory.tracscan.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;

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
	 * @param fragment fragment
	 */
	public LogProdListAdapter(Context ctx,
			LogProdListFragment fragment) {
		this(ctx);
	}
	
	public LogProdListAdapter(Context ctx) {
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
			super(context, attrs, R.layout.row_logprod);
		}

		/** Populate row with a LogProd.
		 *
		 * @param model LogProd data
		 */
		public void populate(final LogProd model) {
			View convertView = this.getInnerLayout();


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
