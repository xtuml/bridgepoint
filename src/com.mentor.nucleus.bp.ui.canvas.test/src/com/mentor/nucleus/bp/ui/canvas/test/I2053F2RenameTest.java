//=====================================================================
//
//File:      $RCSfile: I2053F2RenameTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.canvas.test;

import java.util.HashMap;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class I2053F2RenameTest extends BaseTest {

    /**
     * Whether the first test of this class is the one that's currently being
     * run.
     */
    private static boolean firstTest = true;
    
	public I2053F2RenameTest(String arg0) {
		super("I2053F2RenameTest", arg0);
	}

	public void setUp() throws Exception {
        super.setUp();
		if (firstTest) {
            loadProject("ContextMenuTests");
            firstTest = false;
		}
	}
    public void testContextF2RenameSelection() {
		
    	// clear the current selection to prepare for test
		Selection.getInstance().clear();
		
		// open the domain diagram
		Package_c dom = Package_c.getOneEP_PKGOnR1401(m_sys);
		CanvasTestUtilities.openDiagramEditor(dom);
		
		// get the CanvasEditor instance
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		
		// locate External Entity Package
		Package_c eePkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() { 
				   
			public boolean evaluate(Object candidate) { 
				return ((Package_c) candidate).getName().equals("External Entities"); 
			} 
				   
		}); 
		
		// load the External Entity Package into Selection
		StructuredSelection sel = new StructuredSelection(eePkg);
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(sel);

		// get the menu from the SWT Canvas 
		ce.getCanvas().getMenu();
		
		// clear the current selection to prepare for test
		Selection.getInstance().clear();
		
		// rename event
		ICommandService adapter = (ICommandService) PlatformUI.getWorkbench().getAdapter(ICommandService.class);
		Command command = adapter.getCommand("org.eclipse.ui.edit.rename");
		ExecutionEvent evt = new ExecutionEvent(new HashMap(), new Event(), null);
		
		try {
		  command.execute(evt);
		} catch (ExecutionException e) {
			fail("Null Pointer Exception occured on F2 rename");
		} catch (NotHandledException e) {
			fail("Null Pointer Exception occured on F2 rename");
		}
    }
}