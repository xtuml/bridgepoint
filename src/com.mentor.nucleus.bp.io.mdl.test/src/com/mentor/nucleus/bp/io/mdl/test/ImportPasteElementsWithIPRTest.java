//========================================================================
//
//File:      $RCSfile: ImportPasteElementsWithIPRTest.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 05:13:51 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
//  This class is responsible to test IPR upon import and paste
//

package com.mentor.nucleus.bp.io.mdl.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Pref_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class ImportPasteElementsWithIPRTest extends BaseTest {
	
	private String projectName = "System";
	

	public ImportPasteElementsWithIPRTest(String testName) throws Exception {
		super("System",testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void test_ImportModelWithIPR() throws CoreException{
		
		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		m_sys = getSystemModel(project.getName());

		TestUtil.yesToDialog(5000);
		
		TestingUtilities.importModelUsingWizardConvertToGenerics(m_sys, m_workspace_path + "models/"
				+ projectName+".xtuml", false);

		CorePlugin.enableParseAllOnResourceChange();

		TestingUtilities.allowJobCompletion();
		
		// TODO - the m_sys2 here is not the same as the m_sys we got earlier and imported into.
		// But the data we just imported is now connected to m_sys2 and not m_sys.  Don't know if this
		// is a testing issue or a real problem.
		m_sys = getSystemModel(project.getName());
		
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		ComponentReference_c imported = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg));
		assertNotNull(imported);
		assertFalse(imported.getAssignedcomp_id() == Gd_c.Null_unique_id());

	}

	public void testCopyPasteElementWithIPR() {
		
		// create new package to paste Component reference in
		m_sys.Newpackage();
		setIPRPreference(projectName, false);
		ModelRoot [] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		
		// Copy the component reference
		ComponentReference_c cpElement = null;
		for(ModelRoot root : roots) {
			cpElement = ComponentReference_c.ComponentReferenceInstance(root);
			if(cpElement != null) {
				break;
			}
		}
		assertNotNull(cpElement);
		assertFalse(cpElement.getAssignedcomp_id() == Gd_c.Null_unique_id());
		
		
		// select source and destination packages
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		Package_c  source = null;
		Package_c dest = null;
		for (Package_c packageC : pkgs) {
			if ( packageC.getName().equals("System")){
				source = packageC;
			}
			else if ( packageC.getName().equals("Unnamed Package")){
				dest = packageC;
			}
		}
		CanvasUtilities.openCanvasEditor(source);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		
		// copy element
		UITestingUtilities.copyElement(cpElement, ce);

		CanvasUtilities.openCanvasEditor(dest);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		TestUtil.yesToDialog(5000);
		
		// paste element
		UITestingUtilities.pasteClipboardContents(new Point(700, 100), ce);
		
		// Verification
		ComponentReference_c pasted = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getOnePE_PEOnR8000(dest));
		assertNotNull(pasted);
		assertFalse(pasted.getAssignedcomp_id() == Gd_c.Null_unique_id());
			
	}
	
    public boolean setIPRPreference(String projectName, boolean bool) {
    	boolean preferenceIPR = false;
    	IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    	if (selectedProject != null) {
    		IScopeContext projectScope = new ProjectScope(selectedProject);
    		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
    		preferenceIPR = Pref_c.Getsystemboolean( "com.mentor.nucleus.bp.ui.project.references" ,projectName);
   			projectNode.putBoolean("com.mentor.nucleus.bp.ui.project.references", bool);
    	}
    	return preferenceIPR;
    }
	
}
