package com.mentor.nucleus.bp.io.mdl.test.dangle;

//=====================================================================
//
//File:      $RCSfile: DRBaseTest.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/01/10 23:13:31 $
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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.marker.DelayedMarkerJob;
import com.mentor.nucleus.bp.core.ui.marker.UmlProblem;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMBaseTest;
import com.mentor.nucleus.bp.test.common.CVSUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class DRBaseTest extends PkgCMBaseTest {
    private static String projectName = "TestModel";

    protected static boolean checkoutProject = true;
    
    GraphicalEditor baseEditor = null;

    public DRBaseTest(String name) {
        super(null, name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setupProjectAndTestModel();
    }

    protected void tearDown() throws Exception {
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .closeAllEditors(true);
        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;
        super.tearDown();
    }

    protected void setupProjectAndTestModel() throws Exception {
        CorePlugin.disableParseAllOnResourceChange();

        if (checkoutProject) { // only checkout once
            CVSUtils.checkoutProject(projectName);
            dispatchEvents(0);
            modelRoot = Ooaofooa.getInstance("/" + projectName + "/" + Ooaofooa.MODELS_DIRNAME +
            				"/" + projectName + "/" + "MGC/" + "MGC." + Ooaofooa.MODELS_EXT);
            checkoutProject = false;
            m_bp_tree.expandAll();
            dispatchEvents(0);
        }

        setupProject(projectName);
    }

    protected void performDRCheckByReplaceWithHistory(NonRootModelElement rgo,
            NonRootModelElement rto) throws Exception {
        deleteME(rto);        
        
        checkProblemMarkerRemoved(rgo);

        // replace the file of RGO element to create a dangling reference
        replaceWithHistory(rgo.getFile());

        checkProblemMarkerExist(rgo);
        // replace missing component file
        replaceWithHistory(rto.getFile());
        checkProblemMarkerRemoved(rgo);
    }

    private void replaceWithHistory(IFile file) {
        try {
            if (file.exists()) {
                IFileState[] history = file.getHistory(null);
                file.setContents(history[history.length - 1], IFile.NONE, null);
            }else{
                CVSUtils.checkoutFolder(file.getParent().getFullPath());
            }
        } catch (Exception e) {
            fail("Could not replace file with history");
        }
        waitForDelayedMarkerJob();
    }

    protected void performDRCheckByReplaceWithElement(NonRootModelElement rgo,
            NonRootModelElement rto, NonRootModelElement newRTO)
            throws Exception {

        deleteME(rto);

        checkProblemMarkerRemoved(rgo);
        String rgoName = CoreUtil.getName(rgo);

        // replace the file of RGO element to create a dangling reference
        replaceWithHistory(rgo.getFile());
        rgo = getReloadedModelElement(rgo, rgoName);
        checkProblemMarkerExist(rgo);

        // Replace RTO with some available real inst to correct the problem
        replaceRTO(rgo, newRTO);

        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;
        checkProblemMarkerRemoved(rgo);
        // restore file for next tests
        replaceWithHistory(rto.getFile());
        replaceWithHistory(rgo.getFile());
        rgo = getReloadedModelElement(rgo, rgoName);
    }

    protected void performDRCheckByDeletingRGO(NonRootModelElement rgo,
            NonRootModelElement rto) throws Exception {

        deleteME(rto);
        
        checkProblemMarkerRemoved(rgo);

        // replace the file of RGO element to create a dangling reference
        replaceWithHistory(rgo.getFile());

        checkProblemMarkerExist(rgo);
        String name=CoreUtil.getName(rgo);
        rgo=getReloadedModelElement(rgo, name);
        // Delete the RGO to remove the Problem
        deleteME(rgo);

        checkProblemMarkerRemoved(rgo);
        // restore file for next tests
        replaceWithHistory(rto.getFile());
        replaceWithHistory(rgo.getFile());
        rgo=getReloadedModelElement(rgo, name);
    }

    protected void checkProblemMarkerExist(NonRootModelElement me) {
    	waitForDelayedMarkerJob();
    	IMarker markers[] = new IMarker[]{};
		try {
			markers = me.getFile().findMarkers(UmlProblem.xtUMLMarker, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			fail("Exception obtaining Markers: " + e.toString()); //$NON-NLS-1$
		}
        assertTrue("Expected marker not found", (markers.length > 0));
    }

    protected void checkProblemMarkerRemoved(NonRootModelElement me) {
    	waitForDelayedMarkerJob();
    	IMarker markers[] = new IMarker[]{};
		try {
			markers = me.getFile().findMarkers(UmlProblem.xtUMLMarker, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			fail("Exception obtaining Markers: " + e.toString()); //$NON-NLS-1$
		}
        assertTrue("Marker does not removed", (markers.length == 0));
    }

    protected void deleteME(NonRootModelElement ele) {
        ExplorerUtil.selectMEInModelExplorer(ele.getFile().getFullPath());
        TreeItem treeItem1 = ExplorerUtil.selectItem(ele);
        assertNotNull(treeItem1);
        doDeleteThruMExplorer();
    }

    protected NonRootModelElement getReloadedModelElement(
            NonRootModelElement element, String name) throws Exception {
        NonRootModelElement me = null;
        if (element instanceof ImportedClass_c) {
            me = ImportedClass_c.ImportedClassInstance(modelRoot,
                    new ImportedClass_by_name_c(name));

        } else if (element instanceof ModelClass_c) {
            me = ModelClass_c.ModelClassInstance(modelRoot,
                    new ModelClass_by_name_c(name));
        } else if (element instanceof UserDataType_c) {
            me = UserDataType_c.UserDataTypeInstance(modelRoot,
                    new UserDatatype_by_name_c(name));
        } else if (element instanceof StateMachineEventDataItem_c) {
            me = StateMachineEventDataItem_c.StateMachineEventDataItemInstance(
                    modelRoot, new StateMachineEventDataItem_by_name_c(name));
        } else if (element instanceof Function_c) {
            me = Function_c.FunctionInstance(modelRoot, new Function_by_name_c(
                    name));
        } else if (element instanceof FunctionParameter_c) {
            me = FunctionParameter_c.FunctionParameterInstance(modelRoot,
                    new FunctionParam_by_name_c(name));
        } else if (element instanceof Subsystem_c) {
            me = Subsystem_c.SubsystemInstance(modelRoot,
                    new Subsystem_by_name_c(name));
        } else if (element instanceof Bridge_c) {
            me = Bridge_c.BridgeInstance(modelRoot, new Bridge_by_name_c(name));
        } else if (element instanceof BridgeParameter_c) {
            me = BridgeParameter_c.BridgeParameterInstance(modelRoot,
                    new BridgeParam_by_name_c(name));
        } else if (element instanceof Operation_c) {
            me = Operation_c.OperationInstance(modelRoot,
                    new Operation_by_name_c(name));
        } else if (element instanceof OperationParameter_c) {
            me = OperationParameter_c.OperationParameterInstance(modelRoot,
                    new OperationParam_by_name_c(name));
        } else if (element instanceof Attribute_c) {
            me = Attribute_c.AttributeInstance(modelRoot,
                    new Attribute_by_name_c(name));
        } else {
            fail("Element is not in if structure");
        }

        return me;
    }

    protected DataType_c getDataType(NonRootModelElement prevDT) {
        DataType_c dataType = null;
        if (prevDT instanceof UserDataType_c) {
            dataType = DataType_c.getOneS_DTOnR17((UserDataType_c) prevDT);
        } else if (prevDT instanceof CoreDataType_c) {
            dataType = DataType_c.getOneS_DTOnR17((CoreDataType_c) prevDT);
        }
        return dataType;
    }

    protected void replaceRTO(NonRootModelElement rgo,
            NonRootModelElement newRTO) {

        if (rgo instanceof Function_c) {
            Function_c func = (Function_c) rgo;
            func.relateAcrossR25To((DataType_c) newRTO);
        } else if (rgo instanceof FunctionParameter_c) {
            FunctionParameter_c funcParam = (FunctionParameter_c) rgo;
            funcParam.relateAcrossR26To((DataType_c) newRTO);
        } else if (rgo instanceof Operation_c) {
            Operation_c operation = (Operation_c) rgo;
            operation.relateAcrossR116To((DataType_c) newRTO);
        } else if (rgo instanceof OperationParameter_c) {
            OperationParameter_c operParam = (OperationParameter_c) rgo;
            operParam.relateAcrossR118To((DataType_c) newRTO);
        } else if (rgo instanceof Bridge_c) {
            Bridge_c bridge = (Bridge_c) rgo;
            bridge.relateAcrossR20To((DataType_c) newRTO);
        } else if (rgo instanceof BridgeParameter_c) {
            BridgeParameter_c bridParam = (BridgeParameter_c) rgo;
            bridParam.relateAcrossR22To((DataType_c) newRTO);
        } else if (rgo instanceof StateMachineEventDataItem_c) {
            StateMachineEventDataItem_c dataItem = (StateMachineEventDataItem_c) rgo;
            dataItem.relateAcrossR524To((DataType_c) newRTO);
        } else if (rgo instanceof Attribute_c) {
            Attribute_c attr = (Attribute_c) rgo;
            attr.relateAcrossR114To((DataType_c) newRTO);
        } else {
            fail("element type not included in if structure");
        }
    }
    
    public static void waitForDelayedMarkerJob() {
    	while(DelayedMarkerJob.isRunning()) {
    		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    	}
    	waitForDecorator();
    }
}
