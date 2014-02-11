//========================================================================
//
//File:      $RCSfile: SubtypeSupertypeFormalizeTest.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 22:49:04 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//======================================================================== 
package com.mentor.nucleus.bp.core.test;

import java.io.FileInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.InheritanceFormalizeOnR_SUBAction;
import com.mentor.nucleus.bp.core.ui.InheritanceFormalizeOnR_SUBWizardPage1;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class SubtypeSupertypeFormalizeTest extends CanvasTest {

	IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	String test_id = null;
	private static boolean generateResults = false;
	
	static String workspace_path = "";
    
	private static Selection selection = Selection.getInstance();

	public SubtypeSupertypeFormalizeTest(String name) {
		super("com.mentor.nucleus.bp.core.test", name);
	}

	protected String getResultName() {
		return "SubtypeSupertypeFormalize" + "_" + test_id;
	}	
	private IProject createProject(	String name )
	{
		IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(
			name);

		// try to open a currently existing project
		try {
			projectHandle.open(new NullProgressMonitor());
			return projectHandle;
		}
		catch (CoreException e) {
			if (e.getStatus().getPlugin() != ResourcesPlugin.PI_RESOURCES
				|| e.getStatus().getCode() != IResourceStatus.RESOURCE_NOT_FOUND)
				CanvasPlugin.logError("open project '" + name + "' error", e);
		}
		  
		// project doesn't exist, create a new project
		try {
			final IProjectDescription myTestProject = ResourcesPlugin.getWorkspace().newProjectDescription(name);
			myTestProject.setLocation(null);   // default location
			projectHandle.create(myTestProject, new NullProgressMonitor());
			projectHandle.open(new NullProgressMonitor());
			return projectHandle;
		}
		catch (CoreException e) {
			CanvasPlugin.logError("create project '" + name + "' error", e);
		}
		return null;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;

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
				ExplorerUtil.showModelExplorer();
			}
		  };
		  CoreTestPlugin.getWorkspace().run(r, null);
		}
		catch (CoreException x)
		{
		  CanvasPlugin.logError("create perspective problem", x);
		}
		IProject projectHandle = createProject("SubtypeSupertypeFormalize");
		IPath p1 = projectHandle.getLocation();

		IPath model_path = p1.append("subtypeSupertypeFormalizeTest." + Ooaofooa.MODELS_EXT);
		java.io.File systemFile = model_path.toFile();
		if (!systemFile.exists())
		{
			IFile targetResource = projectHandle.getFile("subtypeSupertypeFormalizeTest." + Ooaofooa.MODELS_EXT);
			FileInputStream is = new FileInputStream(workspace_path +  Ooaofooa.MODELS_DIRNAME + "/subtypeSupertypeFormalizeTest." + Ooaofooa.MODELS_EXT);
			targetResource.create(is, true, new NullProgressMonitor());
		}
		
		Domain_c d2 = Domain_c.DomainInstance(modelRoot);
		if (d2 == null) {
			ImportModel impMod = new ImportModel(model_path.toString(), modelRoot, 
					new SystemModel_c(Ooaofooa.getDefaultInstance()), false, true, false);
			impMod.run(new NullProgressMonitor());
			assertEquals( "", impMod.m_errorMessage ); //$NON-NLS-1$
	        modelRoot.setLoadPathForTests(model_path.toString());
		}
	} else if (ExplorerUtil.getTreeViewer() == null) {
		PlatformUI.getWorkbench().showPerspective("com.mentor.nucleus.bp.core.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		ExplorerUtil.showModelExplorer();

		IProject projectHandle = createProject("SubtypeSupertypeFormalize");
		IPath p1 = projectHandle.getLocation();

		IPath model_path = p1.append("subtypeSupertypeFormalizeTest." + Ooaofooa.MODELS_EXT);
		java.io.File systemFile = model_path.toFile();
		if (!systemFile.exists())
		{
			IFile targetResource = projectHandle.getFile("subtypeSupertypeFormalizeTest." + Ooaofooa.MODELS_EXT);
			FileInputStream is = new FileInputStream(workspace_path +  Ooaofooa.MODELS_DIRNAME + "/subtypeSupertypeFormalizeTest." + Ooaofooa.MODELS_EXT);
			targetResource.create(is, true, new NullProgressMonitor());
		}


		ImportModel impMod = new ImportModel(model_path.toString(), modelRoot, 
				new SystemModel_c(Ooaofooa.getDefaultInstance()), true, true, false);
		impMod.run(new NullProgressMonitor());
		assertEquals( "", impMod.m_errorMessage ); //$NON-NLS-1$
        modelRoot.setLoadPathForTests(model_path.toString());
			
			
	}
}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public class Subsystem_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Subsystem_c selected = (Subsystem_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Subsystem_by_name_c(String name) {
			m_name = name;		
		}
		private String m_name;
		}
		public class ModelClass_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ModelClass_c selected = (ModelClass_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public ModelClass_by_name_c(String name) {
			m_name = name;		
		}
		private String m_name;
		}
		
		public Shape_c getModelClassShape(String name) {
		CanvasTestUtils ctu = new CanvasTestUtils();
		ModelClass_c mc =
			ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c(name));
		GraphicalElement_c ge =
			GraphicalElement_c.GraphicalElementInstance(graphicsModelRoot,
				ctu.new findGraphicalElementByOOAID(mc.getObj_id(), 21));
		return Shape_c.getOneGD_SHPOnR2(ge);
		}
		
		public void openTestSSDiagram(String title) {
			CanvasTestUtils ctu = new CanvasTestUtils();
			Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot, new Subsystem_by_name_c(title));
			CanvasTestUtils.openCanvasEditor(uut);
		}
		public void openTestISCDiagram(String title) {
			CanvasTestUtils ctu = new CanvasTestUtils();
			InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(ModelClass_c.ModelClassInstance(modelRoot, ctu.new Class_by_name_c("Test Import Class")));
			CanvasTestUtils.openCanvasEditor(uut);
		}
		public void setGenerateResults() {
			try {
				generateResults = true;
				setUp();
				testDeleteSubtypeTwoSubtypes();
	  			testFormalizeNoSubtype();
	  			testDeleteSubtypeOneSubtype();
	  			testDeleteSubtypeConnectorOneSubtype();

	  			
			} catch (Exception e) {
				System.out.println(
					"Exception encountered by test result creator: " + e);
			}

		}
		
    	public void testDeleteSubtypeTwoSubtypes() {
	  		test_id = "test_1";
	  		openTestSSDiagram("TestSubsystem1");
	  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
					.getGraphicalEditor();
	  		Shape_c shp = getModelClassShape("subClass2");
	  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
	  		Cl_c.Clearselection();	
	      	selection.addToSelection(ge.getRepresents());
			DeleteAction da = new DeleteAction (null);
	  		da.run();
	  		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c("subClass1"));
	  		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc);
	  		assertFalse (attr.getName().equals("Orphaned"));
	  		
	  		ModelClass_c mc2 = ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c("supClass1"));
	  		Association_c assoc = Association_c.getOneR_RELOnR201(mc);
	  		assertTrue (assoc.Actionfilter("formalized", "true"));
	  		
            validateOrGenerateResults(ce, generateResults);
    	}
    	
    	public void testFormalizeNoSubtype() {
	  		test_id = "test_2";
	  		openTestSSDiagram("TestSubsystem2");
	  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
					.getGraphicalEditor();
	  		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c("supClass1"));
	  		Association_c assoc = Association_c.getOneR_RELOnR201(mc);
	  		
	  		Action a = new Action() {};
	        InheritanceFormalizeOnR_SUBAction ifa = new InheritanceFormalizeOnR_SUBAction();
	        ifa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
	        IStructuredSelection structuredSelection =
	            (IStructuredSelection) Selection.getInstance().getSelection();
	        WizardDialog wd = ifa.R_SUB_InheritanceFormalize(structuredSelection);
	        InheritanceFormalizeOnR_SUBWizardPage1 page = (InheritanceFormalizeOnR_SUBWizardPage1) wd.getCurrentPage();
	        page.IdentifierCombo.select(0);
	        IWizard w = page.getWizard();        
			w.performFinish();
			wd.close();
			assertTrue (assoc.Actionfilter("formalized", "true"));
			
            validateOrGenerateResults(ce, generateResults);
    	}
    	
    	public void testDeleteSubtypeOneSubtype() {
	  		test_id = "test_3";
	  		openTestSSDiagram("TestSubsystem2");
	  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
					.getGraphicalEditor();
	  		Shape_c shp = getModelClassShape("subClass2");
	  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
	  		Cl_c.Clearselection();	
	      	selection.addToSelection(ge.getRepresents());
			DeleteAction da = new DeleteAction (null);
	  		da.run();
	  		
	  		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c("supClass2"));
	  		Association_c assoc = Association_c.getOneR_RELOnR201(mc);
	  		assertTrue (assoc.Actionfilter("formalized", "true"));
	  		
            validateOrGenerateResults(ce, generateResults);
    	}
    	
    	public void testDeleteSubtypeConnectorOneSubtype() {
	  		test_id = "test_4";
	  		openTestSSDiagram("TestSubsystem2");
	  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
					.getGraphicalEditor();
	  		ModelClass_c subMc = ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c("subClass3"));
	  		
	  		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "subClass3");
	  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
	  		Cl_c.Clearselection();	
            Connector_c con = Connector_c.getOneGD_CONOnR20(
                Graphedge_c.getOneDIM_EDOnR320(
                    Graphconnector_c.getManyDIM_CONsOnR311(
                        Graphelement_c.getOneDIM_GEOnR23(ge))));
	  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);		
	     	selection.addToSelection(ge2.getRepresents());
	  		
			DeleteAction da = new DeleteAction (null);
	  		da.run();
	  		
	  		ModelClass_c supMc = ModelClass_c.ModelClassInstance(modelRoot, new ModelClass_by_name_c("supClass3"));
	  		Association_c supAssoc = Association_c.getOneR_RELOnR201(supMc);
	  		assertTrue (supAssoc.Actionfilter("formalized", "true"));
	  		
            validateOrGenerateResults(ce, generateResults);
    	}
	  		
    	
    	
}
	    
	    	