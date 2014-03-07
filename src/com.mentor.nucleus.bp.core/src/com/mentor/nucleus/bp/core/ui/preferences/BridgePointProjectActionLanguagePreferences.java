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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.CorePlugin;

public class BridgePointProjectActionLanguagePreferences extends
		BridgePointProjectPreferences {

	private static final String PREFIX = "bridgepoint_prefs_";  //$NON-NLS-1$
    public static final String ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE = PREFIX + "enable_error_for_empty_synchronous_message"; //$NON-NLS-1$
    public static final String ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED = PREFIX + "enable_error_for_empty_synchronous_message_realized"; //$NON-NLS-1$


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
		Button[] buttons = EmptySynchronousMessageParsePreferenceButtons.createPreferenceButtons(composite);
		enableErrorForEmptySynchronousMessage = buttons[0];
		enableErrorForEmptySynchronousMessageRealized = buttons[1];
		syncUIWithPreferences();
		return composite;
	}

	@Override
	public void subtypePerformDefaults() {
		// defaults need to match the workspace preferences
		boolean enabledForSyncMessages = CorePlugin.getDefault()
				.getPreferenceStore()
				.getBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE);
		boolean enabledForRealizedSyncMessages = CorePlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED);
		enableErrorForEmptySynchronousMessage.setSelection(enabledForSyncMessages);
		enableErrorForEmptySynchronousMessageRealized.setSelection(enabledForRealizedSyncMessages);
	}

	@Override
	protected void syncUIWithPreferences() {
		boolean enabledForSyncMessages = CorePlugin.getDefault()
				.getPreferenceStore()
				.getBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE);
		boolean enabledForRealizedSyncMessages = CorePlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED);
		enableErrorForEmptySynchronousMessage.setSelection(getStore()
				.getBoolean(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE, enabledForSyncMessages));
		enableErrorForEmptySynchronousMessageRealized.setSelection(getStore()
				.getBoolean(
						ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						enabledForRealizedSyncMessages));
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
