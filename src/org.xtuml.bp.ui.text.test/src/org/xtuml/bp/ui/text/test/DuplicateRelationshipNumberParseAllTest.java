//=====================================================================
//
//File:      $RCSfile: DuplicateRelationshipNumberParseAllTest.java,v $
//Version:   $Revision: 1.14 $
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.activity.AllActivityModifier;

/**
 * Holds tests related to performing a parse-all on a model
 * which contains relationships with duplicate numbers.
 */
@RunWith(OrderedRunner.class)
public class DuplicateRelationshipNumberParseAllTest extends UITextTest
{
	public DuplicateRelationshipNumberParseAllTest() throws CoreException {
		super();
	}

	private static boolean firstSetup = true;
	private static String testModelName = "odms";
	
    /**
     * Constructor.
     */
//    public DuplicateRelationshipNumberParseAllTest(String name) throws CoreException
//    {
//        super(null, name);
//    }
    
    @Before
	public void setUp() throws Exception {
        super.setUp();
        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }     
    
    /**
     * For issue 860.  Tests that problems introduced during a parse-all
     * after the addition of a duplicate-numbered relationship to a model
     * are removed by a second parse-all following the relationship 
     * having been renumbered to no longer conflict. 
     */
    @Test
	public void testProblemsRemovedByParseAllAfterDuplicateRelationshipNumberChanged()
    {
        // add a relationship to the model which has the same
        // number as an existing relationship
        Association_c r1 = Association_c.AssociationInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Association_c) candidate).getNumb() != 1;
			}
		});
        r1.setNumb(1);
        
        // have all activities parsed, so that the duplicate relationship
        // number causes problem markers to show up in the problems view
        AllActivityModifier modifier = new AllActivityModifier(
            m_sys, new NullProgressMonitor());
        modifier.processAllActivities(AllActivityModifier.PARSE);
        
        Package_c pkg = Package_c.PackageInstance(modelRoot);
        PersistenceManager manager = PersistenceManager.getDefaultInstance();
        
        IPath path = manager.getComponent(pkg).getContainingDirectoryPath();
        
        path = path.append("Odms/Disk Ownership/ClassStateMachine/");
        path = path.append("Disk_Ownership__Establishing_Ownership_of_an_Offline_Disk.oal_err");
        
        // check that there are now problems associated with an
        // activity file for which we know there should be problems
        // after the parse-all
        IWorkspace workspace = TextPlugin.getWorkspace();
        IFile file = workspace.getRoot().getFile(path);

        if (!file.exists()) {
        	path = new Path(path.toString().replace("0", ""));
            file = workspace.getRoot().getFile(path);
        }
        assertTrue("Activity file has no problems after adding a relationship with a duplicate number",
            TextEditorUtils.getMarkers(file).length > 0);

        // change the number of the new relationship so that it is no 
        // longer a duplicate
        r1.setNumb(88);
        
        // do another parse-all
        modifier.processAllActivities(AllActivityModifier.PARSE);
        
        // check that there are now no problems associated with the
        // activity file for which there were problems, above
        assertTrue("Activity file still has problem markers after relationship renumbering",
            TextEditorUtils.getMarkers(file).length == 0);
    }
}
