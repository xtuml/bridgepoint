//=====================================================================
//
//File:      $RCSfile: I835OpenDiagramEditorWithSearchView.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 05:41:51 $
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

package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.search.internal.ui.text.FileSearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.FileTextSearchScope;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class I835OpenDiagramEditorWithSearchView extends BaseTest {

	public I835OpenDiagramEditorWithSearchView(String name)	throws CoreException {
		super("OpenEditorWithSearchView", name);
	}
	
	protected void setUp() throws Exception {
        super.setUp();
				
		TestingUtilities.importModelUsingWizard(m_sys, new Path(
				m_workspace_path + "../com.mentor.nucleus.bp.welcome/models"),
				"xtUML_Metamodel.xtuml", false);
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(m_sys.getName(), "ooaofooa", true));
	}
	
	public void testOpenDiagramEditorWithSearchView() throws CoreException{
		
		BaseTest.dispatchEvents(0);
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
				IResource.DEPTH_INFINITE, new NullProgressMonitor());
		
		//Loading the Search view
		IViewPart searchView = NewSearchUI.activateSearchResultView();

        //Search anything in the project, we search "ooaofooa" so that there
        //are always some results available
        FileSearchQuery query = new FileSearchQuery("ooaofooa", false, true, //$NON-NLS-1$
                FileTextSearchScope.newWorkspaceScope(null, false));
        
		//Now Open CanvasEditor for any subysytem
		class Subsystem_by_Name implements ClassQueryInterface_c{

			public boolean evaluate(Object candidate) {
				Package_c ss = (Package_c)candidate;
				return ss.getName().equals("Subsystem");	//$NON-NLS-1$
			}
			
		}
		Package_c uut = Package_c.PackageInstance(modelRoot, new Subsystem_by_Name());
		CanvasTestUtils.openDiagramEditor(uut);
		
		Display d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		GraphicalEditor ed = CanvasTestUtils.getCanvasEditor("Subsystem: Package Diagram");
		assertNotNull(ed);	
		
		// Running the search query now
		// The test will fail with an IllegalArgumentException at this point
		// if the problem is not fixed.
		NewSearchUI.runQueryInBackground(query);
		
		NewSearchUI.activateSearchResultView();
		
		d = Display.getCurrent();
		while(d.readAndDispatch()){}						
	}	
}
