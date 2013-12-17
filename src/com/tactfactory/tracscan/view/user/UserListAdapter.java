/**************************************************************************
 * UserListAdapter.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.view.user;

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
import com.tactfactory.tracscan.entity.User;

/**
 * List adapter for User entity.
 */
public class UserListAdapter
		extends HeaderAdapter<User>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 * @param fragment fragment
	 */
	public UserListAdapter(Context ctx,
			UserListFragment fragment) {
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
			super(context, attrs, R.layout.row_user);
		}

		/** Populate row with a User.
		 *
		 * @param model User data
		 */
		public void populate(final User model) {
			View convertView = this.getInnerLayout();
			TextView typeView =
				(TextView) convertView.findViewById(
						R.id.row_user_type);
			TextView loginView =
				(TextView) convertView.findViewById(
						R.id.row_user_login);
			TextView passwdView =
				(TextView) convertView.findViewById(
						R.id.row_user_passwd);


			if (model.getType() != null) {
				typeView.setText(model.getType().name());
			}
			if (model.getLogin() != null) {
				loginView.setText(model.getLogin());
			}
			if (model.getPasswd() != null) {
				passwdView.setText(model.getPasswd());
			}
		}
	}

	/** Section indexer for this entity's list. */
	public static class UserSectionIndexer
					extends HeaderSectionIndexer<User>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public UserSectionIndexer(List<User> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(User item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				User item,
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
	public int getPosition(User item) {
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
