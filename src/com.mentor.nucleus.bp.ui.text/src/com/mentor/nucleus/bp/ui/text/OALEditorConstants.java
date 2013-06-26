//========================================================================
//
//File:      $RCSfile: OALEditorConstants.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:20:56 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;


/**
 * @author Siddique_ahmed
 *
 */
public class OALEditorConstants {
	
    public static final RGB DEFAULT_BACKGROUND = new RGB(255,255,255);
    public static final boolean DEFAULT_BACKGROUND_ISSYSTEMDEFAULT = true;
    
    public static final RGB DEFAULT_FOREGROUND_SINGLE_LINE_COMMENT = new RGB(63, 127, 95);
    public static final RGB DEFAULT_FOREGROUND_MULTI_LINE_COMMENT = new RGB(63, 127, 95);
    public static final RGB DEFAULT_FOREGROUND_KEYWORD = new RGB(127, 0, 85);
    public static final RGB DEFAULT_FOREGROUND_STRING = new RGB(42, 0, 255);
    public static final RGB DEFAULT_FOREGROUND_OTHER = new RGB(0,0,0);
    
    public static final int  DEFAULT_STYLE_SINGLE_LINE_COMMENT = SWT.NULL;
    public static final int  DEFAULT_STYLE_MULTI_LINE_COMMENT = SWT.BOLD;
    public static final int  DEFAULT_STYLE_KEYWORD = SWT.BOLD;
    public static final int  DEFAULT_STYLE_STRING = SWT.NULL;
    public static final int  DEFAULT_STYLE_OTHER = SWT.NULL;
    
    public static final String  DEFAULT_LABEL_SINGLE_LINE_COMMENT = "Single-line comments";
    public static final String  DEFAULT_LABEL_MULTI_LINE_COMMENT = "Multi-line comments";
    public static final String  DEFAULT_LABEL_KEYWORD = "Keywords";
    public static final String  DEFAULT_LABEL_STRING = "Strings";
    public static final String  DEFAULT_LABEL_OTHER = "Others";

}