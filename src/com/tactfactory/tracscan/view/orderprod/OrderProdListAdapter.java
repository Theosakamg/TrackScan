/**************************************************************************
 * OrderProdListAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.orderprod;

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
import com.tactfactory.tracscan.entity.OrderProd;

/**
 * List adapter for OrderProd entity.
 */
public class OrderProdListAdapter
		extends HeaderAdapter<OrderProd>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 * @param fragment fragment
	 */
	public OrderProdListAdapter(Context ctx,
			OrderProdListFragment fragment) {
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
			super(context, attrs, R.layout.row_orderprod);
		}

		/** Populate row with a OrderProd.
		 *
		 * @param model OrderProd data
		 */
		public void populate(final OrderProd model) {
			View convertView = this.getInnerLayout();
			TextView loginView =
				(TextView) convertView.findViewById(
						R.id.row_orderprod_login);
			TextView productTypeView =
				(TextView) convertView.findViewById(
						R.id.row_orderprod_producttype);
			TextView materialTypeView =
				(TextView) convertView.findViewById(
						R.id.row_orderprod_materialtype);
			TextView quantityView =
				(TextView) convertView.findViewById(
						R.id.row_orderprod_quantity);


			if (model.getLogin() != null) {
				loginView.setText(model.getLogin());
			}
			if (model.getProductType() != null) {
				productTypeView.setText(model.getProductType().name());
			}
			if (model.getMaterialType() != null) {
				materialTypeView.setText(model.getMaterialType().name());
			}
			quantityView.setText(String.valueOf(model.getQuantity()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class OrderProdSectionIndexer
					extends HeaderSectionIndexer<OrderProd>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public OrderProdSectionIndexer(List<OrderProd> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(OrderProd item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				OrderProd item,
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
	public int getPosition(OrderProd item) {
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
