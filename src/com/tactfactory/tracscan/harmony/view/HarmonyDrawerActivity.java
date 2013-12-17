package com.tactfactory.tracscan.harmony.view;

import com.actionbarsherlock.view.MenuItem;
import com.tactfactory.tracscan.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Drawer menu activity.
 */
public class HarmonyDrawerActivity extends HarmonyFragmentActivity {
	/** Menu Gravity. */
	private int gravity = Gravity.LEFT;
	/** Drawer layout. */
	private DrawerLayout drawer;
	/** Action Bar Drawer Toggle. */
	private ActionBarDrawerToggle drawerToggle;
	/** FrameLayout containing the content of the activity. */
	private FrameLayout contentView;
	/** Framelayout containing the left menu view. */
	private FrameLayout menuLeftView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.drawer_layout);
		
		this.contentView = 
				(FrameLayout) this.findViewById(R.id.drawer_content);
		this.menuLeftView = 
				(FrameLayout) this.findViewById(R.id.drawer_menu_left);
		
		this.drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		
		this.drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.drawable.ic_navigation_drawer,
                android.R.string.ok,
                android.R.string.cancel) {

            /** 
			 * Called when a drawer has settled in a completely closed state.
			 */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("HomeActivity");
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Open Drawer");
                invalidateOptionsMenu();
            }
        };


        this.drawer.setDrawerListener(this.drawerToggle);
        

		new DrawerManager(this,
				R.layout.sliding_menu_left);	
		//this.getSupportActionBar().setIcon(R.drawable.ic_navigation_drawer);
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        this.drawerToggle.syncState();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case android.R.id.home:
		        if (this.drawer.isDrawerOpen(this.gravity)) {
		            this.drawer.closeDrawer(this.gravity);
		        } else {
		            this.drawer.openDrawer(this.gravity);
		        }
		        return true;
		        
		}
	    return super.onOptionsItemSelected(item);
    }

	@Override
	public void setContentView(int layoutResId) {		
		LayoutInflater inflater = (LayoutInflater) 
				this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.contentView.addView(inflater.inflate(layoutResId, null));
		
	}
	/** 
	 * Set left menu layout.
	 * @param layoutRestId The left menu id.
	 */
	public void setMenuLeft(int layoutResId) {		
		LayoutInflater inflater = (LayoutInflater) 
				this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.menuLeftView.addView(inflater.inflate(layoutResId, null));
	}
	/** 
	 * Set menu gravity.
	 * @param gravitiy new gravitiy
	 */
	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	/** 
	 * Get left menu view.
	 * @param The framelayout of the left menu view
	 */
	public FrameLayout getMenuLeftView() {
		return this.menuLeftView;
	}
	
	/**
	 * Get the drawer layout.
	 * @return the drawer layout.
	 */
	public DrawerLayout getDrawerLayout() {
		return this.drawer;
	}
	
	/**
	 * Set a new drawer toggle.
	 * @param drawerToggle the new toggle
	 */
	public void setDrawerToggle(ActionBarDrawerToggle drawerToggle) {
		this.drawer.setDrawerListener(drawerToggle);
	}
	
	/**
	 * Get the gravity.
	 * @return menu gravity
	 */
	public int getGravity() {
		return this.gravity;
	}
}

