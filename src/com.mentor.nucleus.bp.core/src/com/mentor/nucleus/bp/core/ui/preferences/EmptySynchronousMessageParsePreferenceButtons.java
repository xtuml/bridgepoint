package com.mentor.nucleus.bp.core.ui.preferences;
//====================================================================
//
//File:      EmptySynchronousMessageParsePreferenceButtons.java
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class EmptySynchronousMessageParsePreferenceButtons {

	public static Button[] createPreferenceButtons(Composite parent) {
		return createPreferenceButtons(parent, false);
	}
	
	public static Button[] createPreferenceButtons(Composite parent, boolean doubleWidth) {

		final Button enableErrorForEmptySynchronousMessage = new Button(
				parent, SWT.CHECK | SWT.LEFT);
		enableErrorForEmptySynchronousMessage
				.setText("Create an error for empty synchronous messages");
		enableErrorForEmptySynchronousMessage
				.setToolTipText("This preference will enable parse errors when a return value is "
						+ "required.\nThe parse errors will be created for the following "
						+ "elements when they\nhave no action language:\n\nFunction\nClass "
						+ "Operation\nBridge Operation\nProvided Interface Operation\nRequired Interface"
						+ " Operation");
		if(doubleWidth) {
			GridData data = new GridData();
			data.horizontalSpan = 2;
			enableErrorForEmptySynchronousMessage.setLayoutData(data);
		}
		final Button enableErrorForEmptySynchronousMessageRealized = new Button(
				parent, SWT.CHECK | SWT.LEFT);
		enableErrorForEmptySynchronousMessageRealized
				.setText("Include realized elements");
		GridData childData = new GridData();
		childData.horizontalIndent = 20;
		if(doubleWidth) {
			childData.horizontalSpan = 2;
		}
		enableErrorForEmptySynchronousMessageRealized.setLayoutData(childData);
		enableErrorForEmptySynchronousMessageRealized
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// if this button is enabled and the non-realized
						// one is not, then enable that one
						if (enableErrorForEmptySynchronousMessageRealized
								.getSelection()) {
							enableErrorForEmptySynchronousMessage
									.setSelection(true);
						}
					}
				});
		enableErrorForEmptySynchronousMessage
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// always uncheck the realized button when
						// this one is unchecked
						if (!enableErrorForEmptySynchronousMessage
								.getSelection()) {
							enableErrorForEmptySynchronousMessageRealized
									.setSelection(false);
						}
					}
				});
		enableErrorForEmptySynchronousMessageRealized
				.setToolTipText("This preference will enable parse errors for realized elements");
		return new Button[] { enableErrorForEmptySynchronousMessage,
				enableErrorForEmptySynchronousMessageRealized };

	}

}
