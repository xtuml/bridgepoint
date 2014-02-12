     package com.mentor.nucleus.bp.io.mdl.test;
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

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.InstanceReferenceDataType_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class IOMdlNestedTestGenerics extends CanvasTest {

	static ExplorerView m_bp_view = null;
	static TreeViewer m_bp_tree = null;
	static SystemModel_c m_sys = null;
	String test_id = null;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	static String workspace_path = "";
    

    
	public IOMdlNestedTestGenerics(String arg0) {
		super("com.mentor.nucleus.bp.io.mdl.test", arg0);
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
 
		if (!initialized)
		{
		IProject projectHandle = TestingUtilities.createProject("test");
		String modelPath = workspace_path + Ooaofooa.MODELS_DIRNAME + "/nested_test." + Ooaofooa.MODELS_EXT;
		initialized = TestingUtilities.importModelUsingWizardConvertToGenerics( getSystemModel(projectHandle.getName()), modelPath , true);
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
			this.testCreateNestedDTP();
			this.testCreateNestedFP();
			this.testCreateNestedEEP();
			this.testCreateNestedSS();			
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
	
	public void testCreateNestedDTP()
	{	
		test_id = "test_1";
		IOMdlUtilities mtu = new IOMdlUtilities();
		Package_c uut = Package_c.PackageInstance(modelRoot, (mtu.new Package_by_name_c("Test DPK")));
		CanvasTestUtils.openCanvasEditor(uut);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor("Test DPK: Package Diagram");
		AbstractTool tool = UITestingUtilities.getTool("Package");		
		UITestingUtilities.activateTool(tool);
		
		CanvasTestUtils.createMouseEvent(500, 500, "MouseDown");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseMove");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseUp");
		
        validateOrGenerateResultsGenerics(ce, generateResults);
		UITestingUtilities.deactivateTool(tool);
		
	}
	public void testCreateNestedFP()
	{	
		test_id = "test_2";
		IOMdlUtilities mtu = new IOMdlUtilities();
		Package_c uut = Package_c.PackageInstance(modelRoot, (mtu.new Package_by_name_c("Test Nested FPK")));
		
		CanvasTestUtils.openCanvasEditor(uut);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor("Test Nested FPK: Package Diagram");
		AbstractTool tool = UITestingUtilities.getTool("Package");		
		UITestingUtilities.activateTool(tool);
		
		CanvasTestUtils.createMouseEvent(500, 500, "MouseDown");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseMove");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseUp");
		
        validateOrGenerateResultsGenerics(ce, generateResults);
		UITestingUtilities.deactivateTool(tool);
		
	}
	public void testCreateNestedEEP()
	{	
		test_id = "test_3";
		IOMdlUtilities mtu = new IOMdlUtilities();
		Package_c uut = Package_c.PackageInstance(modelRoot, (mtu.new Package_by_name_c("Test Nested EEPK")));
		
		CanvasTestUtils.openCanvasEditor(uut);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor("Test Nested EEPK: Package Diagram");
		AbstractTool tool = UITestingUtilities.getTool("Package");		
		UITestingUtilities.activateTool(tool);
		
		CanvasTestUtils.createMouseEvent(500, 500, "MouseDown");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseMove");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseUp");
		
        validateOrGenerateResultsGenerics(ce, generateResults);
		UITestingUtilities.deactivateTool(tool);
		
	}
	public void testCreateNestedSS()
	{	
		test_id = "test_4";
		IOMdlUtilities mtu = new IOMdlUtilities();
		Package_c uut = Package_c.PackageInstance(modelRoot, (mtu.new Package_by_name_c("Test Nested SS")));
		
		CanvasTestUtils.openCanvasEditor(uut);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor("Test Nested SS: Package Diagram");
		AbstractTool tool = UITestingUtilities.getTool("Package");		
		UITestingUtilities.activateTool(tool);
		
		CanvasTestUtils.createMouseEvent(500, 500, "MouseDown");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseMove");
		CanvasTestUtils.createMouseEvent(700, 700, "MouseUp");
		
        validateOrGenerateResultsGenerics(ce, generateResults);
		UITestingUtilities.deactivateTool(tool);
		
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
