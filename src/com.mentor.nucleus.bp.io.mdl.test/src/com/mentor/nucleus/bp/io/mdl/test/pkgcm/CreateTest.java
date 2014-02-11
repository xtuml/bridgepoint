package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

//=====================================================================
//
//File:      $RCSfile: CreateTest.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/05/10 05:16:04 $
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

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.test.TigerNatureTestGenerics;
import com.mentor.nucleus.bp.core.ui.NewInstanceStateMachineOnO_OBJAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public abstract class CreateTest extends PkgCMBaseTest {

    protected NonRootModelElement meBeingTested;

    protected PersistableModelComponent pmcBeingTested;

    protected GraphicalEditor baseEditor = null;

    protected String compTypeBeingCreated;

    protected int editorToTest;

    protected PersistableModelComponent parentPMC;

    public CreateTest(String project, String name) {
        super(project, name);
    }

    protected void initTest(String parentPmcType, String parentPmcName,
            int focusedEditor, String compType) throws Exception {
        compTypeBeingCreated = compType;
        parentPMC = getComponent(parentPmcType, parentPmcName);

        editorToTest = focusedEditor;
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .closeAllEditors(false);

        if (focusedEditor == EditorTestUtilities.EDITOR_TYPE_CANVAS) {
            PersistableModelComponent editorPMC=parentPMC;
            if(parentPmcType.equals("ModelClass")){ //$NON-NLS-1$
                editorPMC=parentPMC.getParent();
            }
            TestingUtilities.allowJobCompletion();
            TestUtil.sleepWithDispatchOfEvents(5000);
            baseEditor = (GraphicalEditor) EditorTestUtilities.openRandomEditor(
                    editorPMC, focusedEditor);
            if (baseEditor == null)
                assertNotNull("Canvas editor could not be opened", baseEditor);
            else
                m_wp.activate(baseEditor);
        }

        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;
    }

    protected void performCreateComponentThruME(String parentPmcType,
            String parentPmcName, int focusedEditor, String componentType,
            String newCompName) throws Exception {

        initTest(parentPmcType, parentPmcName, focusedEditor, componentType);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return;

        if (componentType.equals("SystemModel")) { //$NON-NLS-1$
            pmcBeingTested = PersistenceManager
                    .getRootComponent(TigerNatureTestGenerics
                            .createXtUMLProject(newCompName));
        } else if (componentType.equals("Domain")) {
            pmcBeingTested = TigerNatureTestGenerics.createNewDomain(newCompName,
                    (SystemModel_c) parentPMC.getRootModelElement());
        } else if (componentType.equals("ISM")) {
            createISM(parentPMC.getRootModelElement());
        } else {
            fail("Given component Type '" + componentType
                    + "' cannot be created through Model Explorer");
        }
        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;
        meBeingTested = pmcBeingTested.getRootModelElement();

        performCreateChecks();
    }
    protected void performCreateComponentThruMEGenerics(String parentPmcType,
            String parentPmcName, int focusedEditor, String componentType,
            String newCompName) throws Exception {

        initTest(parentPmcType, parentPmcName, focusedEditor, componentType);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return;

        if (componentType.equals("SystemModel")) { //$NON-NLS-1$
            pmcBeingTested = PersistenceManager
                    .getRootComponent(TigerNatureTestGenerics
                            .createXtUMLProject(newCompName));
        } else if (componentType.equals("Package")) {
            pmcBeingTested = TigerNatureTestGenerics.createNewPackage(newCompName,
                    (SystemModel_c) parentPMC.getRootModelElement());
        } else if (componentType.equals("ISM")) {
            createISM(parentPMC.getRootModelElement());
        } else {
            fail("Given component Type '" + componentType
                    + "' cannot be created through Model Explorer");
        }
        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;
        meBeingTested = pmcBeingTested.getRootModelElement();

        performCreateChecksGenerics();
    }
    /**
     * Creates a componentType Component as child of parentComponent
     * 
     * @param parentComponent
     *            Parent Component
     * @param componentType
     *            Shape tool name for new component being created
     * @throws Exception
     */
    protected PersistableModelComponent performCreateComponentThruCE(
            String parentPmcType, String parentPmcName, String componentType)
            throws Exception {

        initTest(parentPmcType, parentPmcName,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, componentType);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return null;

        return createComponentThruCE();
    }
    protected PersistableModelComponent performCreateComponentThruCEGenerics(
            String parentPmcType, String parentPmcName, String componentType)
            throws Exception {

        initTest(parentPmcType, parentPmcName,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, componentType);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return null;

        return createComponentThruCEGenerics();
    }
    protected void performCreateChecks() {
        performCreateChecks(baseEditor, pmcBeingTested);
    }
    protected void performCreateChecksGenerics() {
        performCreateChecksGenerics(baseEditor, pmcBeingTested);
    }
    protected PersistableModelComponent createComponentThruCE() {
        meBeingTested = createShape(baseEditor, compTypeBeingCreated);
        pmcBeingTested = PersistenceManager.getComponent(meBeingTested);
        assertNotNull("Newly created component does not exist.", pmcBeingTested);
        while (Display.getCurrent().readAndDispatch())
            ;
        performCreateChecks();
        return pmcBeingTested;
    }
    protected PersistableModelComponent createComponentThruCEGenerics() {
    	if (compTypeBeingCreated.equalsIgnoreCase("Class"))
    	{
        meBeingTested = createShape(baseEditor,"Classes", compTypeBeingCreated);
    	}
    	else 
        meBeingTested = createShape(baseEditor, compTypeBeingCreated);
        pmcBeingTested = PersistenceManager.getComponent(meBeingTested);
        assertNotNull("Newly created component does not exist.", pmcBeingTested);
        while (Display.getCurrent().readAndDispatch())
            ;
        performCreateChecksGenerics();
        return pmcBeingTested;
    }
    protected void createISM(NonRootModelElement parentME) {
        Selection.getInstance().setSelection(new StructuredSelection(parentME));
        NewInstanceStateMachineOnO_OBJAction newISMAction = new NewInstanceStateMachineOnO_OBJAction();
        newISMAction.run(null);
        InstanceStateMachine_c ism = InstanceStateMachine_c
                .getOneSM_ISMOnR518((ModelClass_c) parentME);
        pmcBeingTested = PersistenceManager.getComponent(ism);

    }

}