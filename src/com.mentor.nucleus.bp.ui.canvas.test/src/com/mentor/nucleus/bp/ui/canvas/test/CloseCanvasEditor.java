
//=====================================================================
//
//File:      $RCSfile: CloseCanvasEditor.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.canvas.test;

import junit.framework.TestCase;

import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class CloseCanvasEditor extends TestCase {

	public CloseCanvasEditor(String arg0) {
		super(arg0);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void closeCanvasEditor( String title )
	{
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(title);
		if ( ce != null )
		{
			assertFalse( ce.isDirty() );
			ce.getSite().getPage().closeEditor(ce, false);
		}
		else
		{
			fail("Unable to find editor with title: " + title);
		}
	}
	
	public void testClosePD()
	{	
		closeCanvasEditor("odms: Package Diagram");
	}

	public void testCloseSC()
	{	
		closeCanvasEditor("Disk: Instance State Machine");
	}
	
}
