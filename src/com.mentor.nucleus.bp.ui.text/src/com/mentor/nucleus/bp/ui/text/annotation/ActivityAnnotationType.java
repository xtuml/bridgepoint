package com.mentor.nucleus.bp.ui.text.annotation;
//====================================================================
//
// File:      $RCSfile: ActivityAnnotationType.java,v $
// Version:   $Revision: 1.9 $
// Modified:  $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import org.eclipse.ui.internal.texteditor.TextEditorPlugin;
public class ActivityAnnotationType
{
  private String type;
  private ActivityAnnotationType(final String type)
  {
    this.type = type;
  }
  public final static ActivityAnnotationType UNKNOWN = new ActivityAnnotationType(TextEditorPlugin.PLUGIN_ID + ".unknown"); //$NON-NLS-1$
  public final static ActivityAnnotationType BOOKMARK = new ActivityAnnotationType(TextEditorPlugin.PLUGIN_ID + ".bookmark"); //$NON-NLS-1$
  public final static ActivityAnnotationType TASK = new ActivityAnnotationType(TextEditorPlugin.PLUGIN_ID + ".task"); //$NON-NLS-1$
  public final static ActivityAnnotationType ERROR = new ActivityAnnotationType(TextEditorPlugin.PLUGIN_ID + ".error"); //$NON-NLS-1$
  public final static ActivityAnnotationType WARNING = new ActivityAnnotationType(TextEditorPlugin.PLUGIN_ID + ".warning"); //$NON-NLS-1$
  public final static ActivityAnnotationType INFO = new ActivityAnnotationType(TextEditorPlugin.PLUGIN_ID + ".info"); //$NON-NLS-1$
  public final static ActivityAnnotationType SEARCH = new ActivityAnnotationType("org.eclipse.search.results"); //$NON-NLS-1$
  public String toString()
  {
    return type;
  }
}
