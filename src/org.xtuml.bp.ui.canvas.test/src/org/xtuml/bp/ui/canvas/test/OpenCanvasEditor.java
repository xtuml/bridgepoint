
//=====================================================================
//
//File:      $RCSfile: OpenCanvasEditor.java,v $
//Version:   $Revision: 1.30 $
//Modified:  $Date: 2013/05/10 05:41:50 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package org.xtuml.bp.ui.canvas.test;

import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class OpenCanvasEditor extends BaseTest {
	
    private static boolean initialized = false;

	public OpenCanvasEditor(String arg0) {
		super("org.xtuml.bp.ui.canvas.test", arg0);
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
		final Package_c uut = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("odms");
			}
		});
		CanvasTestUtils.openDiagramEditor(uut);
		validateCanvasEditor("odms", uut.getDescrip());
	}
	
	public void testOpenInstanceStateChartDiagram()
	{
		CanvasTestUtils ctu = new CanvasTestUtils();
		final InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(ModelClass_c.ModelClassInstance(modelRoot, ctu.new Class_by_name_c("Disk")));
		CanvasTestUtils.openDiagramEditor(uut);		
		validateCanvasEditor( "Disk", uut.Get_name());

	}
			
}
