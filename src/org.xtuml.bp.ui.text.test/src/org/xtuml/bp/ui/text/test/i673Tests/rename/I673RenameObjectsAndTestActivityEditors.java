//=====================================================================
//
//File:      $RCSfile: I673RenameObjectsAndTestActivityEditors.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:46:33 $
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
package org.xtuml.bp.ui.text.test.i673Tests.rename;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.test.UITextTest;
import org.xtuml.bp.ui.text.test.activity.ActivityEditorInteraction;

public class I673RenameObjectsAndTestActivityEditors extends UITextTest {
	
	public I673RenameObjectsAndTestActivityEditors(String projectName, String name) throws CoreException {
		super("test", name); //$NON-NLS-1$
	}
	
	public I673RenameObjectsAndTestActivityEditors(String name) throws CoreException {
		super("test", name); //$NON-NLS-1$
	}

	IEditorPart renameObjectAndCheckActivityEditor(String editorTitlePreFix, Object obj){
		ActivityEditor ae = (ActivityEditor)openAndReturnActivityEditor(obj);
		assertNotNull(ae);
		
		//Rename the Domain object
		renameObject(obj);
		
		String newName = getName(obj);
		
		ae = TextEditorUtils.getActivityEditor( editorTitlePreFix + newName);
		assertNotNull("Activity Editor for Name: " + newName + " not renamed", ae);		 //$NON-NLS-1$ //$NON-NLS-2$
		return ae;
	}
	
	IEditorPart openAndReturnActivityEditor(Object obj){
		ActivityEditorInteraction.openActivityEditor(obj);
		
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}
	
	public void testRenameBridge(){
		Bridge_c brg = Bridge_c.BridgeInstance(modelRoot);
		assertNotNull(brg);
		
		IEditorPart ed = renameObjectAndCheckActivityEditor("Test External Entity New::", brg);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameFunction(){
		Function_c func = Function_c.FunctionInstance(modelRoot);
		assertNotNull(func);
		
		IEditorPart ed = renameObjectAndCheckActivityEditor("Functions::", func );
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameOperation(){
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		assertNotNull(op);
		
		IEditorPart ed = renameObjectAndCheckActivityEditor("Test Class New::", op) ;
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameStateMachineState(){
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot);
		assertNotNull(state);
		
		IEditorPart ed = renameObjectAndCheckActivityEditor("Test Class New::", state);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameAttribute(){
		class FindDerviedAttribute implements ClassQueryInterface_c{

			public boolean evaluate(Object candidate) {
				DerivedBaseAttribute_c dbattr = DerivedBaseAttribute_c.getOneO_DBATTROnR107(BaseAttribute_c.getOneO_BATTROnR106((Attribute_c)candidate));
				return (dbattr != null);
			}
			
		}
		Attribute_c attr = Attribute_c.AttributeInstance(modelRoot, new FindDerviedAttribute());
		assertNotNull(attr);
		
		IEditorPart ed = renameObjectAndCheckActivityEditor("Test Class New::", attr);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	private void renameObject(Object uut){
		renameObject(uut, UITextTest.STRING_TYPE_ATTR);
	}
}
