package com.tactfactory.tracscan.view.license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tactfactory.tracscan.R;
import com.tactfactory.tracscan.harmony.view.HarmonyFragment;

/**
 * License fragment.
 */
public class LicenseFragment extends HarmonyFragment {
	/**
	 * License Map.
	 * Key = license content
	 * Value = list containing the licenses name with this content
	 */
	private HashMap<String, ViewGroup> viewGroupsMap = 
				new HashMap<String, ViewGroup>();
	/** Folder in assets for licenses. */
	private static final String LICENSES_ASSETS_FOLDER = "licenses";
	/** The view group in which to add the licenses. */
	private ViewGroup licenseGroupView;
	
	/** DP value. (ie. 20 * dp = 20dp)*/
	private float dp;
	
	/** 5dp value. */
	private int dp5;
	/** 10dp value. */
	private int dp10;
	/** 15dp value. */
	private int dp15;
	
	
	/** <FileName, Content> */
	private HashMap<String, String> licensesMap = 
			new HashMap<String, String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.dp = this.getActivity().getResources().getDisplayMetrics().density;
		this.dp5 = (int) (this.dp * 5);
		this.dp10 = (int) (this.dp * 10);
		this.dp15 = (int) (this.dp * 15);
		View view = inflater.inflate(R.layout.fragment_license, container);
		
		this.licenseGroupView = 
				(ViewGroup) view.findViewById(R.id.licensesLayout);
		
		this.addLicenses();
		
		return view;
	}
	
	/**
	 * Add the licenses from the assets into the viewgroup.
	 */
	private void addLicenses() {
		AssetManager manager = this.getActivity().getAssets();
		try {
			String[] licensesFiles = manager.list(LICENSES_ASSETS_FOLDER);
			for (String licenseFileName : licensesFiles) {
				this.licensesMap.put(licenseFileName,
						this.readLicense(licenseFileName));
				
				if (!licenseFileName.contains("_")) {
					this.createLicenseGroup(
							licenseFileName.substring(0,
									licenseFileName.lastIndexOf('.')));
				}
			}
			
			
			for (String licenseName : this.licensesMap.keySet()) {
				// Then, parse the 'normal' licenses (xxx_yyy.txt)
				if (licenseName.contains("_")) {
					this.parseLicense(licenseName,
							this.licensesMap.get(licenseName));
				}
			}
			

			for (String licenseName : this.licensesMap.keySet()) {
				// Then, parse the 'normal' licenses (xxx_yyy.txt)
				if (!licenseName.contains("_")) {
					this.parseLicense("License Details_" + licenseName,
							this.licensesMap.get(licenseName));
				}
			}
			
		} catch (IOException e) {
			Log.e("LicenseFragment", e.getMessage());
		}
	}
	
	
	/**
	 * Parse a low level license
	 * @param fileName The name of the license
	 * @return The license content
	 * @throws IOException If an error has occured
	 */
	private void parseLicense(String fileName, String licenseContent) 
			throws IOException {
		String[] split = fileName.split("_");
		String licenseName = split[0];
		String superLicenseName = 
				split[1].substring(0, split[1].lastIndexOf('.'));
		
		ViewGroup layout = this.viewGroupsMap.get(superLicenseName);
		
		this.addLicenseToViewGroup(licenseContent, licenseName, layout, false);
		
	}
	
	/**
	 * Parses a super license.
	 * @param fileName The license name
	 * @throws IOException If the license doesn't exist
	 */
	private void createLicenseGroup(String licenseName) throws IOException {
		LinearLayout layout = new LinearLayout(this.getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams subLayoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		subLayoutParams.setMargins(this.dp15, this.dp10, this.dp15, this.dp10);
		LinearLayout subLayout = new LinearLayout(this.getActivity());
		subLayout.setLayoutParams(subLayoutParams);
		subLayout.setOrientation(LinearLayout.VERTICAL);
		
		this.licenseGroupView.addView(layout);
		this.viewGroupsMap.put(licenseName, subLayout);

		TextView licenseNameTV = new TextView(this.getActivity());
		licenseNameTV.setText(licenseName);
		licenseNameTV.setTextSize(this.dp15);
		licenseNameTV.setPadding(this.dp10, this.dp5, 0, 0);
		licenseNameTV.setTypeface(Typeface.DEFAULT_BOLD);
		layout.addView(licenseNameTV);
		layout.addView(subLayout);
	}
	
	/**
	 * Add a license to the given view group.
	 * @param licenseContent The content of the license
	 * @param licenseName The license name
	 * @param group The group to add the license to
	 * @param topLevel Is this a top level license ?
	 */
	private void addLicenseToViewGroup(
			final String licenseContent,
			final String licenseName,
			final ViewGroup group,
			final boolean topLevel) {
		
		// Name View		
		TextView licenseNameTV = new TextView(this.getActivity());
		
		if (topLevel) {
			licenseNameTV.setText(licenseName);
			licenseNameTV.setTextSize(this.dp15);
			licenseNameTV.setPadding(this.dp10, this.dp5, 0, 0);
			licenseNameTV.setTypeface(Typeface.DEFAULT_BOLD);
		} else {
			
			Spannable s = new SpannableString(licenseName);
			s.setSpan(new BulletSpan(this.dp10), 0, licenseName.length(), 0);
			licenseNameTV.setText(s);
			licenseNameTV.setCompoundDrawablesRelativeWithIntrinsicBounds(
					0,
					0,
					R.drawable.expander_close_holo_light,
					0);
		}

		group.addView(licenseNameTV);

		// Content View
		if (!topLevel) {
			LayoutParams layout = new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			layout.setMargins(this.dp10, this.dp10, this.dp10, this.dp10);
			TextView licenseContentTV = new TextView(this.getActivity());
			licenseContentTV.setLayoutParams(layout);
	
			licenseNameTV.setOnClickListener(
					new CollapseClickListener(licenseContentTV));
			
			licenseContentTV.setText(licenseContent);
			licenseContentTV.setBackgroundColor(Color.WHITE);
			licenseContentTV.setPadding(
					this.dp10, this.dp5, this.dp10, this.dp5);
			group.addView(licenseContentTV);
		}
	}
	
	/**
	 * Reads a license from the assets.
	 * @param licenseName The license name
	 * @return The content of the license
	 * @throws IOException If the license isn't found
	 */
	private String readLicense(final String licenseName) throws IOException {
		InputStream stream = this.getActivity().getAssets().open(
				LICENSES_ASSETS_FOLDER + "/" + licenseName);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		String line = reader.readLine();
		StringBuilder builder = new StringBuilder();
		while (line != null) {	
			builder.append(line + "\n");
			line = reader.readLine();
		}
		
		return builder.toString();
	}
	
	/**
	 * Click Listener for expanding/collapsing an associated view.
	 */
	public class CollapseClickListener implements View.OnClickListener {
		/** Acceleration factor. */
		private static final float ACCELERATION_FACTOR = 2.0f;
		/** Is the view expanded ?*/
		private boolean opened = false;
		/** The associated view. */
		private View openingView;
		/** The animation. */
		private ExpandCollapseAnimation expandCollapseAnimation;
		
		/**
		 * Constructor.
		 * 
		 * @param openingView The associated view to collapse/expand
		 */
		public CollapseClickListener(View openingView) {
			this.openingView = openingView;
			
			this.expandCollapseAnimation = 
					new ExpandCollapseAnimation(this.openingView);
			this.expandCollapseAnimation.setFillAfter(true);
			this.expandCollapseAnimation.setDuration(0);
			this.expandCollapseAnimation.setOrientation(false);
			this.openingView.startAnimation(expandCollapseAnimation);
		}
		
		@Override
		public void onClick(View v) {		
			if (this.opened) {
				((TextView) v).setCompoundDrawablesRelativeWithIntrinsicBounds(
						0,
						0,
						R.drawable.expander_close_holo_light,
						0);
				this.expandCollapseAnimation.setInterpolator(
						new DecelerateInterpolator(ACCELERATION_FACTOR));
			} else {
				((TextView) v).setCompoundDrawablesRelativeWithIntrinsicBounds(
						0,
						0,
						R.drawable.expander_open_holo_light,
						0);
				this.expandCollapseAnimation.setInterpolator(
						new AccelerateInterpolator(ACCELERATION_FACTOR));
			}
			
			this.expandCollapseAnimation.setDuration(500);
			this.expandCollapseAnimation.setOrientation(!this.opened);
			this.openingView.startAnimation(this.expandCollapseAnimation);
			((View) this.openingView.getParent()).invalidate();
			this.opened = !this.opened;
			
		}
		
		/**
		 * Animation for expanding/collapsing a view.
		 */
		public class ExpandCollapseAnimation extends Animation {
			/** Expansion or Collasping ? */
			private boolean shouldExpand = false;
			/** Initial height of the view. */
			private Integer initialHeight = null;
			/** View. */
			private View v;
			
			/**
			 * Constructor.
			 * @param v The view to collapse/expand
			 */
			public ExpandCollapseAnimation(View v) {
				this.v = v;
			}
			
		    @Override
		    protected void applyTransformation(float interpolatedTime,
		    		Transformation t) {
		    	
		        int newHeight = initialHeight;
		        if (this.shouldExpand) {
		        	newHeight *= interpolatedTime;
		        } else {
		        	newHeight *= 1f - interpolatedTime;
		        }
		        this.v.getLayoutParams().height = newHeight;
		        this.v.requestLayout();
		    }
		 
		    @Override
		    public void initialize(int width,
		    		int height,
		    		int parentWidth,
		    		int parentHeight) {
		    	
		        super.initialize(width, height, parentWidth, parentHeight);
		        
		        if (this.initialHeight == null) {
		        	this.initialHeight = height;
		        }
		        if (height == 0) {
		        	this.shouldExpand = true;
		        }
		    }
		 
		    @Override
		    public boolean willChangeBounds() {
		        return true;
		    }
		    
		    /**
		     * Set the orientation of the animation.
		     * @param shouldExpand true if the view should expand next time.
		     * 			false otherwise
		     */
		    public void setOrientation(boolean shouldExpand) {
		    	this.shouldExpand = shouldExpand;
		    }
		}
	}
}


