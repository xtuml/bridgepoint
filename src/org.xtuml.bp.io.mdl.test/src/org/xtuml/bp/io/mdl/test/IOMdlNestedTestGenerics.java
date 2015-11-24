     package org.xtuml.bp.io.mdl.test;
//=====================================================================
//
//File:      $RCSfile: IOMdlNestedTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 05:13:51 $
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

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.explorer.ExplorerView;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

public class IOMdlNestedTestGenerics extends CanvasTest {

	static ExplorerView m_bp_view = null;
	static TreeViewer m_bp_tree = null;
	static SystemModel_c m_sys = null;
	String test_id = null;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	static String workspace_path = "";
    

    
	public IOMdlNestedTestGenerics(String arg0) {
		super("org.xtuml.bp.io.mdl.test", arg0);
	}
	protected String getResultName() {
		return "Nested" + "_Generics_" + test_id;
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
					m_wp = PlatformUI.getWorkbench().showPerspective("org.xtuml.bp.core.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
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
			PlatformUI.getWorkbench().showPerspective("org.xtuml.bp.core.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			m_bp_view = (ExplorerView)m_wp.findView(BridgePointPerspective.ID_MGC_BP_EXPLORER);
			m_bp_tree = m_bp_view.getTreeViewer();
			m_wp.activate(m_bp_view);
		}
 
		if (!initialized)
		{
		IProject projectHandle = TestingUtilities.createProject("test");
		String modelPath = workspace_path + Ooaofooa.MODELS_DIRNAME + "/nested_test." + Ooaofooa.MODELS_EXT;
		initialized = TestingUtilities.importModelUsingWizard( getSystemModel(projectHandle.getName()), modelPath , true);
	    modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(projectHandle, "nested_test", true), true);
		}
		graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
	 
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
	

	

	public void testUpgradeModelWithPublishReference() throws Exception{
		// Load from git
		this.loadProject("InstanceReferenceTestMatrixModel");
			
		SystemModel_c sys = getSystemModel("InstanceReferenceTestMatrixModel");
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(sys);
		Package_c pkg = null;
		for(int i = 0; i < pkgs.length; i++) {
			if(pkgs[i].getName().equalsIgnoreCase("Subsystem")) {
				pkg = pkgs[i];
				break;
			}
		}
		ModelClass_c testModelClass = ModelClass_c.ModelClassInstance(pkg.getModelRoot(), new ModelClass_by_name_c("Accident"));
		Attribute_c[] attrs = Attribute_c.getManyO_ATTRsOnR102(testModelClass);
		for (Attribute_c attribute : attrs) {
			if ( attribute.getName().equalsIgnoreCase("vehicleReference") || attribute.getName().equalsIgnoreCase("selfReference")){
				Assert.assertNotNull( InstanceReferenceDataType_c.getOneS_IRDTOnR17(DataType_c.getOneS_DTOnR114(attribute)));
			}
		}
	}
}
