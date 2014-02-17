package com.mentor.nucleus.bp.debug.ui;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IPageLayout;


//====================================================================
//
// File:      $RCSfile: IBPDebugUIPluginConstants.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:17:54 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
public interface IBPDebugUIPluginConstants {
    /* the BP debug plugin id */
    public static final String PLUGIN_ID = "com.mentor.nucleus.bp.debug.ui"; //$NON-NLS-1$

    /* BP debug perspective id*/
    public static final String BPPERSPECTIVE_ID = "com.mentor.nucleus.bp.debug.ui.DebugPerspective";
    public static final String PREFIX = PLUGIN_ID + "."; //$NON-NLS-1$

    /* Preference attribute names */
    public static final String PREF_ENABLELOGGINGBUTTON = PREFIX +
        "enableLoggingButton"; //$NON-NLS-1$
    public static final String PREF_FILENAME = PREFIX + "filename"; //$NON-NLS-1$
    public static final String PREF_FILELOCATION = PREFIX + "filelocation"; //$NON-NLS-1$
    public static final String PREF_BROWSEBUTTON = PREFIX + "browsebutton"; //$NON-NLS-1$
    public static final String PREF_USEFORMATLOGBUTTON = PREFIX +
        "formatlogbutton"; //$NON-NLS-1$
    public static final String PREF_STYLELISTCOMBONAME = PREFIX +
        "stylelistcombo"; //$NON-NLS-1$
    public static final String PREF_ADDNEWFORMATBUTTON = PREFIX +
        "addnewformatbutton"; //$NON-NLS-1$
    public static final String PREF_USERAWLOGBUTTON = PREFIX +
        "rawloggingbutton"; //$NON-NLS-1$
    public static final String PREF_STATEBUTTON = PREFIX + "statebutton"; //$NON-NLS-1$
    public static final String PREF_ACTIONBUTTON = PREFIX + "actionbutton"; //$NON-NLS-1$
    public static final String PREF_EXPRESSIONBUTTON = PREFIX +
        "expressionbutton"; //$NON-NLS-1$
    public static final String PREF_FORMATLISTs = PREFIX + "formatlist"; //$NON-NLS-1$
    public static final String PREF_FORMATLISTCURRENTINDEX = "formatlistindex"; //$NON-NLS-1$
    public static final String PREF_EDITFORMAT = PREFIX + "editformat"; //$NON-NLS-1$
    public static final String PREF_DETAILFORMAT = PREFIX + "detailformat"; //$NON-NLS-1$
    public static final String PREF_ALLBRIDGEBUTTON = PREFIX +
        "allbridgebutton"; //$NON-NLS-1$
    public static final String PREF_BRIDGENOACTION = PREFIX + "bridgenoaction"; //$NON-NLS-1$
    public static final String PREF_BRIDGEACTIONEXEC = PREFIX +
        "bridgeactionexec"; //$NON-NLS-1$
    public static final String PREF_BRIDGEWIRED = PREFIX + "bridgewired"; //$NON-NLS-1$

    /*System properties*/
    public static final String PROPERTY_LAUNCH_VERIFIER = PREFIX +
        "canLaunchVerifier"; //$NON-NLS-1$
    
	// ////////// DEBUG PERSPECTIVE /////////////////
	// IDs for views added to the BP Debug perspective
	public static final String ID_VIEW_EXPLORER = "com.mentor.nucleus.bp.ui.explorer.ExplorerView";//$NON-NLS-1$
	public static final String ID_VIEW_INSTANCE = "com.mentor.nucleus.bp.ui.session.views.SessionExplorerView";//$NON-NLS-1$
	public static final String ID_VIEW_MONITOR = "com.mentor.nucleus.bp.ui.explorer.MonitorExplorerView";//$NON-NLS-1$
	public static final String ID_VIEW_DEBUG = IDebugUIConstants.ID_DEBUG_VIEW;
	public static final String ID_VIEW_VARIABLE = IDebugUIConstants.ID_VARIABLE_VIEW;
	public static final String ID_VIEW_BREAKPOINT = IDebugUIConstants.ID_BREAKPOINT_VIEW;
	public static final String ID_VIEW_EXPRESSION = IDebugUIConstants.ID_EXPRESSION_VIEW;
	public static final String ID_VIEW_OUTLINE = IPageLayout.ID_OUTLINE;
	public static final String ID_VIEW_TASK_LIST = IPageLayout.ID_TASK_LIST;

	/* folder for instance, tasklist, property view */
	public static final String FOLDER_CONSOLE = "commandFolder";//$NON-NLS-1$

	/* folder for debug, model explorer view */
	public static final String FOLDER_DEBUG = "debugFolder";//$NON-NLS-1$

	/* folder for project explorer view */
	public static final String FOLDER_PJTEXPLORER = "projectExplorerFolder";//$NON-NLS-1$

	/* folder for variable, breakpoint and expression views */
	public static final String FOLDER_TOOLS = "toolsFolder";//$NON-NLS-1$
}
