/**************************************************************************
 * ValidationButtons.java, tracscan Android
 *
 * Copyright 2013
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 17, 2013
 *
 **************************************************************************/
package com.tactfactory.tracscan.harmony.widget;

import com.tactfactory.tracscan.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Simple widget for validation. (1 button ok, 1 button cancel)
 */
public class ValidationButtons extends FrameLayout {

	/** Validation listener. */
	public interface OnValidationListener {
		/**
		 * Called when user clicked the validate button.
		 */
		void onValidationSelected();

		/**
		 * Called when user clicked the cancel button.
		 */
		void onCancelSelected();
	}

	/**
	 * Listener used to notify the user click.
	 */
	private OnValidationListener listener;

	/**
	 * Constructor.
	 * @param context The context
	 * @param attrs The attribute set
	 */
	public ValidationButtons(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_validation_buttons, null);

		this.initializeComponent(view);
		this.addView(view);
	}

	/**
	 * Initialize this views components.
	 * @param view The view
	 */
	private void initializeComponent(View view) {
		Button bCancel = (Button) view
				.findViewById(R.id.validation_cancel);
		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ValidationButtons.this.listener != null) {
					ValidationButtons.this.listener.onCancelSelected();
				}
			}
		});

		Button bValidate = (Button) view
				.findViewById(R.id.validation_validate);
		bValidate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ValidationButtons.this.listener != null) {
					ValidationButtons.this.listener.onValidationSelected();
				}
			}
		});
	}

	/**
	 * Attach the given listener to the component.
	 * @param listener The listener to attach
	 */
	public void setListener(OnValidationListener listener) {
		this.listener = listener;
	}

}
