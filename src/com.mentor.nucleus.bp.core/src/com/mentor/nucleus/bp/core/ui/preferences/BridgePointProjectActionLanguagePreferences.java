package com.mentor.nucleus.bp.core.ui.preferences;
//====================================================================
//
//File:      BridgePointProjectActionLanguagePreferences.java
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.service.prefs.Preferences;

public class BridgePointProjectActionLanguagePreferences extends
		BridgePointProjectPreferences {

	public static final String ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE = "com.mentor.nucleus.bp.ui.project.synchMessageError"; //$NON-NLS-1$
	public static final String ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED = "com.mentor.nucleus.bp.ui.project.realizedSynchMessageError"; //$NON-NLS-1$

	private Button enableErrorForEmptySynchronousMessage;
	private Button enableErrorForEmptySynchronousMessageRealized;

	public BridgePointProjectActionLanguagePreferences(Preferences projectNode) {
		super(projectNode);
	}

	@Override
	protected Control createContents(Composite parent) {
		// create the composite to hold the widgets
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		
		enableErrorForEmptySynchronousMessage = new Button(composite,
				SWT.CHECK | SWT.LEFT);
		enableErrorForEmptySynchronousMessage
				.setText("Create an error for empty synchronous messages");
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

		enableErrorForEmptySynchronousMessageRealized = new Button(
				composite, SWT.CHECK | SWT.LEFT);
		enableErrorForEmptySynchronousMessageRealized
				.setText("Include realized elements");
		GridData childData = new GridData();
		childData.horizontalIndent = 20;
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
		syncUIWithPreferences();
		return composite;
	}

	@Override
	public void subtypePerformDefaults() {
		enableErrorForEmptySynchronousMessage.setSelection(true);
		enableErrorForEmptySynchronousMessageRealized.setSelection(false);
	}

	@Override
	protected void syncUIWithPreferences() {
		enableErrorForEmptySynchronousMessage.setSelection(getStore()
				.getBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE, true));
		enableErrorForEmptySynchronousMessageRealized.setSelection(getStore()
				.getBoolean(
						ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						false));
	}

	@Override
	protected void syncPreferencesWithUI() {
		// we need to sync preferences with the realized
		// value first, otherwise the listener will create
		// parse errors where we may not want them, and then
		// will have to immediately delete them on the event
		// sent to the listener for the realized change
		getStore().putBoolean(
				ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
				enableErrorForEmptySynchronousMessageRealized.getSelection());
		getStore().putBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
				enableErrorForEmptySynchronousMessage.getSelection());
	}

}
