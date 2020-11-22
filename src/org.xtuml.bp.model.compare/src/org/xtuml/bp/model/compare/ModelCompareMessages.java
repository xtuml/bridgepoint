package org.xtuml.bp.model.compare;

import org.eclipse.osgi.util.NLS;

public final class ModelCompareMessages extends NLS {

	private static final String BUNDLE_NAME = "org.xtuml.bp.model.compare.ComparePluginMessages";//$NON-NLS-1$

	private ModelCompareMessages() {
		// Do not instantiate
	}

	public static String NavigationEndDialogTitleNext;
	public static String NavigationEndDialogMessageNext;
	public static String NavigationEndDialogLoopMessageNext;
	public static String NavigationEndDialogNextMessageNext;
	public static String NavigationEndDialogTitlePrev;
	public static String NavigationEndDialogMessagePrev;
	public static String NavigationEndDialogLoopMessagePrev;
	public static String NavigationEndDialogNextMessagePrev;
	public static String NavigationEndDialogDoNothing;
	public static String NavigationEndDialogRemember;
	public static String NavigationEndDialogOptions;
	public static String CompareNavigationPreferencePage_title;
	public static String CompareNavigationPreferencePage_prompt;
	public static String CompareNavigationPreferencePage_loop;
	public static String CompareNavigationPreferencePage_next;
	public static String CompareNavigationPreferencePage_nothing;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ModelCompareMessages.class);
	}
}
