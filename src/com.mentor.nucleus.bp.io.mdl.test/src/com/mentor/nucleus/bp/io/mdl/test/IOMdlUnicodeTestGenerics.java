package com.mentor.nucleus.bp.io.mdl.test;
//=====================================================================
//
//File:      $RCSfile: IOMdlUnicodeTestGenerics.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 05:13:51 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class IOMdlUnicodeTestGenerics extends CanvasTest {

	IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	static ExplorerView m_bp_view = null;
	static TreeViewer m_bp_tree = null;
	String test_id = null;
	private static boolean generateResults = false;
	
	static String workspace_path = "";
    
	public IOMdlUnicodeTestGenerics(String arg0) {
		super("com.mentor.nucleus.bp.io.mdl.test", arg0);
	}
	protected String getResultName() {
		return "Unicode" + "_" + test_id;
	}	

	protected void setUp() throws Exception {
		super.setUp();
		if (workspace_path == null || workspace_path.equals(""))
		{
			workspace_path = System.getProperty("WORKSPACE_PATH");
		}
		assertNotNull( workspace_path );
		if ( m_wp == null )			
		{
			try
			{
			  IWorkspaceRunnable r = new IWorkspaceRunnable()
			  {
				public void run(IProgressMonitor monitor) throws CoreException
				{
					m_wp = PlatformUI.getWorkbench().showPerspective("com.mentor.nucleus.bp.core.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
					m_bp_view = (ExplorerView)m_wp.findView(BridgePointPerspective.ID_MGC_BP_EXPLORER);
					m_bp_tree = m_bp_view.getTreeViewer();
					m_wp.activate(m_bp_view);
				}
			  };
			  IOMdlTestPlugin.getWorkspace().run(r, null);
			}
			catch (CoreException x)
			{
			  CanvasPlugin.logError("create perspective problem", x);
			}
		}
		if (m_bp_view == null) {
			PlatformUI.getWorkbench().showPerspective("com.mentor.nucleus.bp.core.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			m_bp_view = (ExplorerView)m_wp.findView(BridgePointPerspective.ID_MGC_BP_EXPLORER);
			m_bp_tree = m_bp_view.getTreeViewer();
			m_wp.activate(m_bp_view);
		}
		
		loadProject("unicodetest");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.testUnicode();
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}

    public void testUnicode() {
        test_id = "testGenerics";
        Package_c uut = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((Package_c) candidate).getName().equals("Odms");
            }
        });
        //
        // We just need to draw the Diagram to prove we printed the right thing
        //
        CanvasTestUtils.openCanvasEditor(uut);
        GraphicalEditor ce = CanvasTestUtils.getCanvasEditor("Odms: Package Diagram");
        validateOrGenerateResultsGenerics(ce, generateResults);
    }
}
