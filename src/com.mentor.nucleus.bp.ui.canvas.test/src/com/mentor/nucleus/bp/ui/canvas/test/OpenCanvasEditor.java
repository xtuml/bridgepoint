
//=====================================================================
//
//File:      $RCSfile: OpenCanvasEditor.java,v $
//Version:   $Revision: 1.30 $
//Modified:  $Date: 2013/05/10 05:41:50 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class OpenCanvasEditor extends BaseTest {
	
    private static boolean initialized = false;

	public OpenCanvasEditor(String arg0) {
		super("com.mentor.nucleus.bp.ui.canvas.test", arg0);
	}
	
	protected void setUp() throws Exception {
		super.setUp();

        if ( !initialized )
        {
            initialized = true;

            // reload model used by this class
            loadProject("odms");
        }
			
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public static void validateCanvasEditor(String title, String contents) {
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		  
		
		assertNotNull (ce);
		assertEquals( title, ce.getTitle());
	// What is equivalent for Canvas ?
		//assertEquals( contents, ce.getDocumentProvider().getDocument(ce.getEditorInput()).get());

	}	
	
	public void testOpenPackageDiagram()
	{	
		final Package_c uut = Package_c.PackageInstance(modelRoot);
		CanvasTestUtils.openDiagramEditor(uut);
		validateCanvasEditor("odms: Package Diagram", uut.getDescrip());
	}
	
	public void testOpenInstanceStateChartDiagram()
	{
		CanvasTestUtils ctu = new CanvasTestUtils();
		final InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(ModelClass_c.ModelClassInstance(modelRoot, ctu.new Class_by_name_c("Disk")));
		CanvasTestUtils.openDiagramEditor(uut);		
		validateCanvasEditor( "Disk: Instance State Machine", uut.Get_name());

	}
			
}
