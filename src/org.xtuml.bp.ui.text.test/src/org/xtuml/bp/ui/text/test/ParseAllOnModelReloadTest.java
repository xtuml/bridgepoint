//=====================================================================
//
//File:      $RCSfile: ParseAllOnModelReloadTest.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/05/10 06:02:35 $
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

package org.xtuml.bp.ui.text.test;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.ui.text.TextPlugin;

/**
 * Holds tests pertaining to the parse-all action that occurs 
 * when a model is reloaded from disk.
 */
@RunWith(OrderedRunner.class)
public class ParseAllOnModelReloadTest extends UITextTest
{
    public ParseAllOnModelReloadTest() throws CoreException {
		super();
	}

	/**
     * Constructor.
     */
//    public ParseAllOnModelReloadTest(String name) throws CoreException
//    {
//        super(null, name);
//    }
    
    @Before
	public void setUp() throws Exception {
        super.setUp();
        loadProject("small");
    }    

    /**
     * For issue 863.  Tests that a parse-all occurs when a model is reloaded
     * from disk due to its having changed outside of Eclipse.
     */
    @Test
	public void testParseAllOccursOnModelReload() throws Exception
    {
        // ensure the parse on resource change preference is enabled
        CorePlugin.enableParseAllOnResourceChange();
        
    	StateMachineState_c state = StateMachineState_c.
    	                               StateMachineStateInstance(modelRoot, new ClassQueryInterface_c() {
										
										@Override
										public boolean evaluate(Object candidate) {
											return ((StateMachineState_c) candidate).getName().equals("first");
										}
									});
    	PersistenceManager manager = PersistenceManager.getDefaultInstance();
    	PersistableModelComponent component = manager.getComponent(state);
    	IPath componentPath = component.getContainingDirectoryPath();
    	
        IProject project = getProject();
        
        // check that there are no problems associated with an activity
        // that we know will have problems when the bad version of the 
        // model is loaded, below
        IWorkspace workspace = TextPlugin.getWorkspace();
        IFile placeholder = workspace.getRoot().getFile(
        		componentPath.append("A__first.oal_err"));

        assertTrue("Activity file which should have no errors has problem markers",
            TextEditorUtils.getMarkers(placeholder).length == 0);
        // copy over the model's file with a version of the same model
        // that has problems
    	File badModelFile = new File(m_workspace_path + "/models/pre_716_files/InstanceStateMachine.xtuml"); 

    	TestingUtilities.importFile(workspace.getRoot().getFolder(componentPath), 
    			                                                  badModelFile);

        // alert Eclipse to the file's being changed, so it reloads the model,
        // which should also cause a parse-all
        try {
            project.refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            TextPlugin.logError("Could not refresh copied-over model file", e);
        }
        
        // while we haven't reached a timeout limit, and the problem
        // markers for the activity which we know has errors have yet
        // to appear 
        int loops = 0; 
        while (loops++ < 10 && TextEditorUtils.getMarkers(placeholder).length == 0) {
            // dispatch pending events (which likely includes the running of 
            // the above-mentioned parse itself)
            while (Display.getCurrent().readAndDispatch());
            
            // sleep a bit, to wait for the parse to create the markers 
            TestUtil.sleep(300);
        }
        
        // check that there are problems associated with an activity
        // in the bad version of the model that is known to have errors 
        assertTrue("Activity file which has errors has no problem markers",
            TextEditorUtils.getMarkers(placeholder).length > 0);
    }
    
}
