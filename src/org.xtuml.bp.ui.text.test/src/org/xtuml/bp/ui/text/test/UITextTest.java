//=====================================================================
//
//File:      $RCSfile: UITextTest.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/05/10 06:02:35 $
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
package org.xtuml.bp.ui.text.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.ExternalEntityEventDataItem_c;
import org.xtuml.bp.core.ExternalEntityEvent_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.text.ModelAdapter;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.placeholder.PlaceHolderManager;

@RunWith(OrderedRunner.class)
public class UITextTest extends BaseTest
{
    public UITextTest(String projectName, String name) throws CoreException {
		super(projectName, name);	
		
		// Historically, these tests were written in a way that expected parse on editing.  So turn on parser pref on
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.ENABLE_PARSE_ON_ACTIVITY_EDITS,
				true);
	}
    public UITextTest() throws CoreException {
	}

    @Before
	public void setUp() throws Exception {
        super.setUp();
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
    }

    @After
	public void tearDown() throws Exception {
        super.tearDown();
    }
    
    public static void openActivityEditorUsingMarker(IMarker marker){
		try {
			IEditorPart ed = IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), marker);
			assertNotNull(ed);
			
		} catch (PartInitException e) {
			fail(e.toString());
		}
	}
    
    private static IMarker marker = null;
	public static final int INTEGER_TYPE_ATTR = 1;
	public static final int STRING_TYPE_ATTR = 2;
	public static final int DOUBLE_TYPE_ATTR = 3;
    
    public static IMarker createNewMarker(final IFile file, int line, String message,String type) {
    	Map attributes = new HashMap(11);
    	MarkerUtilities.setMessage(attributes, message);
    	MarkerUtilities.setLineNumber(attributes, line);
    	attributes.put(IMarker.TRANSIENT, new Boolean(true));
    	final Map attrs = attributes;
    	final String markerType = type;
    	try {
    		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
    			public void run(IProgressMonitor monitor) throws CoreException {
    				marker = file.createMarker(markerType);
    				marker.setAttributes(attrs);    					
    			}
    		};
    		IWorkspace workspace = ResourcesPlugin.getWorkspace();
    		IResourceRuleFactory ruleFactory = workspace.getRuleFactory();
    		
    		ISchedulingRule markerRule = ruleFactory.markerRule(file);
    		file.getWorkspace().run(runnable, markerRule, IWorkspace.AVOID_UPDATE, null);
    		
    	} catch (CoreException x) {
    		fail("Core Exception in UITextTest.createMarker "+ x.toString()); //$NON-NLS-1$
    	}
    	return marker;
    }
    
   
    /**
     * Used by the i673Test.rename only
     * 
     * @param input
     * @return
     */
    protected String getName(Object input) {
		String result = null;
		Class inputClass = input.getClass();
		Class[] type = new Class[0];
		Method getName = null;
		try {
			getName = inputClass.getMethod("Get_label", type); //$NON-NLS-1$
		} catch (NoSuchMethodException e1) {
			try {
			getName = inputClass.getMethod("getName", type); //$NON-NLS-1$
			} catch (NoSuchMethodException e2) {
			// There is no accessor, try an explicit operation . . .
			try {
				getName = inputClass.getMethod("Get_name", type); //$NON-NLS-1$
				} catch (NoSuchMethodException e3) {
				TextPlugin.logError("Class does not implement any name accessor", e2); //$NON-NLS-1$
			}
		}
		}
		if (getName != null) {
			try {
				Object[] args = new Object[0];
				result = (String) getName.invoke(input, args);
			} catch (InvocationTargetException e) {
				TextPlugin.logError("Invocation Target exception in getName", e); //$NON-NLS-1$
			} catch (IllegalAccessException e) {
				TextPlugin.logError("Illegal Access exception in getName", e); //$NON-NLS-1$
			}
		}
		return result;
	}
    
    protected void renameObject(Object uut, int attributeType){
		String newName = null;
		int newIntValue = 0;
		double newDoubleValue = 0.0;
		
		switch(attributeType){
		    case  INTEGER_TYPE_ATTR:
		    	break;
		    case STRING_TYPE_ATTR:
		    	newName = getName(uut);
				if (newName != null){
					newName += " New"; //$NON-NLS-1$
				}
		    	break;
		    case  DOUBLE_TYPE_ATTR:
		    	break;
		}
		
		TransactionManager manager = ((NonRootModelElement)uut).getTransactionManager();
		Transaction transaction = null; 
		try {
			transaction = manager.startTransaction("Renaming ME thru test", Ooaofooa.getDefaultInstance());
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		
		if (uut instanceof ExternalEntity_c){
			ExternalEntity_c ee = (ExternalEntity_c) uut;
			ee.setName(newName);
		}
		//Doesnt uses the newName. Special case added for this test 
		else if (uut instanceof ExternalEntityEvent_c){
			ExternalEntityEvent_c eeevt = (ExternalEntityEvent_c) uut;
			if (attributeType == INTEGER_TYPE_ATTR)
				eeevt.setNumb((eeevt.getNumb() + 111));
			else 
				eeevt.setUnq_lbl(eeevt.getUnq_lbl() + " New"); //$NON-NLS-1$
		}
		else if (uut instanceof ExternalEntityEventDataItem_c){
			ExternalEntityEventDataItem_c eevtdi = (ExternalEntityEventDataItem_c) uut;
			eevtdi.setName(newName);
		}
		else if (uut instanceof Bridge_c){
			Bridge_c brg = (Bridge_c) uut;
			brg.setName(newName);
		}
		else if (uut instanceof Enumerator_c){
			Enumerator_c v_enum = (Enumerator_c) uut;
			v_enum.setName(newName);
		}
		else if (uut instanceof Function_c){
			Function_c func = (Function_c) uut;
			func.setName(newName);
		}
		else if (uut instanceof Association_c){
			Association_c assoc = (Association_c) uut;
			assoc.setNumb(assoc.getNumb() + 100);
		}
		else if (uut instanceof StateMachineEventDataItem_c){
			StateMachineEventDataItem_c smevtdi = (StateMachineEventDataItem_c) uut;
			smevtdi.setName(newName);
		}
		else if (uut instanceof Operation_c){
			Operation_c op = (Operation_c) uut;
			op.setName(newName);
		}
		else if (uut instanceof Attribute_c){
			Attribute_c attr = (Attribute_c) uut;
			attr.setRoot_nam(newName);
		}
		else if (uut instanceof ModelClass_c){
			ModelClass_c obj = (ModelClass_c) uut;
			obj.setName(newName);
		}
		else if (uut instanceof UserDataType_c){
			UserDataType_c udt = (UserDataType_c) uut;
			DataType_c dt = DataType_c.getOneS_DTOnR17(udt); 
			dt.setName(newName);
		}
		else if (uut instanceof EnumerationDataType_c){
			EnumerationDataType_c edt = (EnumerationDataType_c) uut;
			DataType_c dt = DataType_c.getOneS_DTOnR17(edt);			
			dt.setName(newName);
		}
		else if (uut instanceof StateMachineState_c){
			StateMachineState_c state = (StateMachineState_c) uut;
			state.setName(newName);
		}
		else if (uut instanceof StateMachineEvent_c){
			StateMachineEvent_c event = (StateMachineEvent_c) uut;
			event.setMning(newName);
		}
		else if (uut instanceof Package_c){
			Package_c pkg = (Package_c) uut;
			pkg.setName(newName);
		}
		
		if(transaction != null){
			manager.endTransaction(transaction);
		}
				
		dispatchEvents(100);	
	}
    
    /**
     * This method is used to PlaceHolderLifeCycle tests defined in i673.placeholder package
     * @param manager   PlaceHolderManager instnace
     * @param modelElement  ModelElement whose editor/placeholder is being accessed
     * @param type  Type of PlaceHolder
     * @return
     */
    public static IFile getExistingPlaceHolderFromManager(NonRootModelElement modelElement, String type, Object requester){
    	PlaceHolderManager manager = PlaceHolderManager.getDefaultInstance();
    	ModelElementID modelElementID = ModelAdapter.getModelElementAdapter(modelElement).createModelElementID(modelElement);
		assertNotNull(modelElementID);
		
		IFile file = manager.getPlaceholderFile(modelElementID, type, requester, false);
		
		//Removing the requester
		manager.releasePlaceholderFile(modelElementID, type, requester);
		
		return file;
	}
    
}
