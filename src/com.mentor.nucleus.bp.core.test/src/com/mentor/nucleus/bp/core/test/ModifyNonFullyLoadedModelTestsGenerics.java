package com.mentor.nucleus.bp.core.test;
//========================================================================
//
//File:      $RCSfile: ModifyNonFullyLoadedModelTestsGenerics.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 04:30:28 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.TransactionListener;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class ModifyNonFullyLoadedModelTestsGenerics extends CanvasTest
{

    private static boolean initialized;

    private PersistableModelComponent domainComp;

    private String testProject = "ModifyNonFullyLoadedModelTest";
    private String testModel = "ooaofgraphics";

    private String test_id;

    public boolean generateResults = false;

    private String resultFolder;

    public ModifyNonFullyLoadedModelTestsGenerics(String name) {
        super(null, name);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        if (!initialized) {
        	/* NOTE (2013/05) - when we switched the way test models are used, we wanted
			 * here to use the actual ui.canvas project, but couldn't because the plugin 
			 * had not yet been updated to GPs.  So, for now, we use a test model from git.
			 * Later, this could be changed to use this commented out code if we wanted...
        	TestingUtilities.importDevelopmentProjectIntoWorkspace("com.mentor.nucleus.bp.ui.canvas");
			modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
					"com.mentor.nucleus.bp.ui.canvas", "ooaofgraphics", true));
        	initialized = true;
        	BaseTest.dispatchEvents(0);
        	IProject project = getProjectHandle("com.mentor.nucleus.bp.ui.canvas");
        	m_sys = getSystemModel("com.mentor.nucleus.bp.ui.canvas");
        	m_sys.getPersistableComponent().deleteSelfAndChildren();
        	PersistenceManager.getDefaultInstance().traverseProject(project);
        	PersistableModelComponent rootComponent = PersistenceManager.getRootComponent(project);
        	rootComponent.load(new NullProgressMonitor());
        	m_sys = getSystemModel("com.mentor.nucleus.bp.ui.canvas");
        	domainComp = rootComponent.getChild(testModel);
        	domainComp.load(new NullProgressMonitor());*/

        	// load test model
    		TestingUtilities.importTestingProjectIntoWorkspace(testProject);
    		BaseTest.dispatchEvents(0);
    		m_sys = getSystemModel(testProject); 
    		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys,
    				new ClassQueryInterface_c() {

    					@Override
    					public boolean evaluate(Object candidate) {
    						return ((Package_c) candidate).getName().equals(
    								testModel);
    					}
    				});
    		modelRoot = (Ooaofooa) pkg.getModelRoot();
    		domainComp = PersistenceManager.getComponent(pkg);
    		initialized = true;    		
        }
        resultFolder = m_workspace_path + "/expected_results/";
    }
    

    public void setGenerateResults() throws Exception
    {
        BaseTest.doCreateResults = true;

        setUp();

        testDeleteClassWithReferringImportedClass();

        BaseTest.doCreateResults = false;
    }
    

    /**
     *  Verifies that non-loaded imported classes are removed when
     *  referred to class is removed.
     *  
     *   Also verifies that the transaction does not include deltas from a
     *   load.
     * @throws Exception 
     */
    public void testDeleteClassWithReferringImportedClass() throws Exception
    {
        test_id = "1";

        // create a transaction listener for transaction verification
        TransactionListener listener = new TransactionListener();
        domainComp.getRootModelElement().getTransactionManager().addTransactionListener(listener);

        String testSSCompName = "UML 2.0 Diagram Interchange";

        // load the UML 2.0 Diagram Interchange subsystem
        PersistableModelComponent ssComp = domainComp.getChild(testSSCompName);
        try {
            ssComp.load(new NullProgressMonitor());
        } catch (CoreException e) {
            fail("Unable to load test component: " + testSSCompName);
        }

        // delete the GraphNode class
        Package_c ss = (Package_c) ssComp.getRootModelElement();
        ModelClass_c clazz = ModelClass_c.ModelClassInstance(ss.getModelRoot(),
            new ClassQueryInterface_c() {

                public boolean evaluate(Object candidate)
                {
                    return ((ModelClass_c) candidate).getName().equals(
                        "GraphNode");
                }

            });

        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(clazz);
        ExplorerUtil.deleteItem();

        // Verify the class was deleted
        ModelClass_c clazz2 = ModelClass_c.ModelClassInstance(ss.getModelRoot(),
                new ClassQueryInterface_c() {

                    public boolean evaluate(Object candidate)
                    {
                        return ((ModelClass_c) candidate).getName().equals(
                            "GraphNode");
                    }

                });
        assertNull(clazz2);
        
        Package_c otherSS = Package_c.PackageInstance(modelRoot,
            new ClassQueryInterface_c() {

                public boolean evaluate(Object candidate)
                {
                    return (( Package_c) candidate).getName().equals(
                        "Graphical Data");
                }

            });

        CanvasUtilities.openCanvasEditor(otherSS);
        GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
        validateOrGenerateResults(ce, generateResults);

        listener.WaitForTransactionUnderReview();
        otherSS.getTransactionManager().removeTransactionListener(listener);

        BaseTest.compareAndOutputResults(resultFolder + getResultName() + "/"
            + getResultName() + "-shape_deletion_transaction_generics.exp");
    }

    public String getResultName()
    {
        return "ModifyNonFullyLoadedModel" + "_Generic" + test_id;
    }
}
