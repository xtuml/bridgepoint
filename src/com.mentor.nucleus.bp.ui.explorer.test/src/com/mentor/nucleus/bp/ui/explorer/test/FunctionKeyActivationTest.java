//=====================================================================
//
//File:      $RCSfile: FunctionKeyActivationTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/05/10 05:36:38 $
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

package com.mentor.nucleus.bp.ui.explorer.test;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class FunctionKeyActivationTest extends BaseTest {
	
	public FunctionKeyActivationTest(String name) {
		super(packageName, name);
	}
	/**
     * The name of the test domain used during most of these tests.
     */
    private static String testModelName = "small";

    /**
     * The name of the test workspace project in which these 
     * tests operate.
     */
    private static final String packageName = "com.mentor.nucleus.bp.ui.explorer.test";
    
    /**
     * The path within the test workspace to which a log file will be written
     * if any errors are logged during the tests' operation. 
     */
    private static final String logPath = System.getProperty("LOGFILE_PATH"); 
    
    static {
        ExplorerUtil.showModelExplorer();
    }
    protected void setUp() throws Exception
    {
        super.setUp();
        
        // make sure the user isn't prompted to do a parse all
        // disable parsing as resource locked exceptions occur
        CorePlugin.disableParseAllOnResourceChange();
        }
    
    protected void tearDown() throws Exception 
    {
        // fail if any errors were written to the log file
        if (new Path(logPath).toFile().exists()) {
            fail( "Log file is not empty"); 
        }
    }
    public void testF2Activation() throws CoreException,IOException {
    	// setup the test project and model
        PersistableModelComponent domainComponent = ensureAvailableAndLoaded(packageName, testModelName, false, false, "Package");
        modelRoot = (Ooaofooa)domainComponent.getRootModelElement().getModelRoot();

        
        ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
            
            public boolean evaluate(Object candidate) {
                if(((ModelClass_c)candidate).getName().equals("A"))
                    return true;
                else
                    return false;
            }
        } );
        mc.ensureLoaded(true);
    	InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(mc);
    	    	
        // open the class diagram editor
        Package_c testpkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
            
            public boolean evaluate(Object candidate) {
                if(((Package_c)candidate).getName().equals("Test"))
                    return true;
                else
                    return false;
            }
        } );
        CanvasTestUtils.openPackageCanvasEditor(testpkg);
        
		m_bp_tree.refresh();
        TreeViewer viewer = ExplorerView.getExplorerTreeViewer();
        viewer.expandAll();
        StructuredSelection sel=new StructuredSelection(uut);
        viewer.setSelection(sel);
		Selection.getInstance().setSelection(sel);
		UIUtil.dispatchAll();
		viewer.getTree();
		UIUtil.dispatchAll();
		Menu menu = viewer.getTree().getMenu();
		UIUtil.dispatchAll();
		UITestingUtilities.activateMenuItem(menu, "Rename");
		uut=InstanceStateMachine_c.getOneSM_ISMOnR518(mc);
		assertEquals(uut.Get_name(), InstanceStateMachine_c.getOneSM_ISMOnR518(mc).Get_name());
		sel=new StructuredSelection(mc);
		viewer.setSelection(sel);
		Selection.getInstance().setSelection(sel);
		
        ExplorerUtil.renameItem("A2");
		
		mc = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
		            
		            public boolean evaluate(Object candidate) {
		                if(((ModelClass_c)candidate).getName().equals("A2"))
		                    return true;
		                else
		                    return false;
		}
        } );
		assertNotNull(mc);
		
		sel=new StructuredSelection(mc);
		viewer.setSelection(sel);
		Selection.getInstance().setSelection(sel);
		
        ExplorerUtil.renameItem("A");
		
		mc = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
		            
		       public boolean evaluate(Object candidate) {
		                if(((ModelClass_c)candidate).getName().equals("A"))
		                    return true;
		                else
		                    return false;
		}
		} );
		assertNotNull(mc);
    }
    
    public void testDeleteActivation() throws CoreException,IOException {
    	
	
		 ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
	            
	            public boolean evaluate(Object candidate) {
	                if(((ModelClass_c)candidate).getName().equals("A"))
	                    return true;
	                else
	                    return false;
	            }
	        } );
	    	InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(mc);
	    	StructuredSelection sel=new StructuredSelection(uut);
	    	TreeViewer viewer = ExplorerView.getExplorerTreeViewer();
	        viewer.setSelection(sel);
			Selection.getInstance().setSelection(sel);
			DeleteAction da=(DeleteAction)CorePlugin.getDeleteAction();
			if(da.isEnabled()){
				da.run();
			}
			uut = InstanceStateMachine_c.getOneSM_ISMOnR518(mc);
			assertNull(uut);
    }
    
}
