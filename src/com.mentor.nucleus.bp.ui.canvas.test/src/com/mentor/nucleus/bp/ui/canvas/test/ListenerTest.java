package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File:      $RCSfile: ListenerTest.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/05/10 05:41:51 $
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
//
//This class is responsible for performing part of the automatic test
//of the Graphics Domain by testing dependencies between symbols 
//on the canvas and client domain classes.
// 
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.ActualParameter_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.AttributeReferenceInClass_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.ClassIdentifierAttribute_c;
import com.mentor.nucleus.bp.core.ClassIdentifier_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.GenerateEventStatement_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.LiteralInteger_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.NewBaseAttribute_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.ReferredToIdentifierAttribute_c;
import com.mentor.nucleus.bp.core.Select_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.listeners.GraphicsEditorListener;

public class ListenerTest extends CanvasTest {
  public ListenerTest(String arg0) {
    super("com.mentor.nucleus.bp.ui.canvas.test", arg0);
  }
  
  public void setUp() throws Exception {
    super.setUp();
    
    loadProject("odms");
  }
  protected String getResultName() {
    return super.getModelName();
  }

  public void testPackageListener() {
    Package_c pkg = Package_c.PackageInstance(modelRoot);
    assertNotNull(pkg);
    GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor(pkg, true);
    Model_c uut = editor.getModel();
    assertNotNull("Diagram not found", uut);

    // validSymbols: Subsystem, External Entity
    assertTrue ( GraphicsEditorListener.classInView(uut, new Package_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ModelClass_c(modelRoot)) );

    assertTrue ( GraphicsEditorListener.classInView(uut, new UserDataType_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new EnumerationDataType_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ExternalEntity_c(modelRoot)) );

    assertTrue ( GraphicsEditorListener.classInView(uut, new UserDataType_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new EnumerationDataType_c(modelRoot)) );

    assertTrue ( GraphicsEditorListener.classInView(uut, new ImportedClass_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new Association_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ClassInAssociation_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new Operation_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new OperationParameter_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new Attribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new BaseAttribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ReferentialAttribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new DerivedBaseAttribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new NewBaseAttribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new AttributeReferenceInClass_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ClassIdentifier_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ClassIdentifierAttribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ReferredToIdentifierAttribute_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new ModelClass_c(modelRoot)) );
    
    validateIgnoreStateMachineChanges(uut);
    validateIgnoreActivityChanges(uut);
  }

  public void testISDListener() {
	InstanceStateMachine_c pkg = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
	assertNotNull(pkg);
	GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor(pkg, true);
	Model_c uut = editor.getModel();
    assertNotNull("Diagram not found", uut);

    // validSymbols: State
    assertTrue ( GraphicsEditorListener.classInView(uut, new StateMachineState_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new Action_c(modelRoot)) );
    
    validateIgnoreAllSubsystemChanges(uut);
    validateIgnoreActivityChanges(uut);
    validateIgnoreDataTypePackageChanges(uut);
    validateIgnoreExternalEntityPackageChanges(uut);
  }
  
  public void testCSDListener() {
	ClassStateMachine_c pkg = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
	assertNotNull(pkg);
	GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor(pkg, true);
	Model_c uut = editor.getModel();
    assertNotNull("Diagram not found", uut);

    // validSymbols: State
    assertTrue ( GraphicsEditorListener.classInView(uut, new StateMachineState_c(modelRoot)) );
    assertTrue ( GraphicsEditorListener.classInView(uut, new Action_c(modelRoot)) );

    validateIgnoreAllSubsystemChanges(uut);
    validateIgnoreActivityChanges(uut);
    validateIgnoreDataTypePackageChanges(uut);
    validateIgnoreExternalEntityPackageChanges(uut);

  }
  
  private void validateIgnoreDataTypePackageChanges( Model_c uut )
  {
    assertFalse ( GraphicsEditorListener.classInView(uut, new UserDataType_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new EnumerationDataType_c(modelRoot)) );
  }
  private void validateIgnoreExternalEntityPackageChanges( Model_c uut )
  {
    assertFalse ( GraphicsEditorListener.classInView(uut, new ExternalEntity_c(modelRoot)) );
  }
  
  private void validateIgnoreAllSubsystemChanges( Model_c uut )
  {
   assertFalse ( GraphicsEditorListener.classInView(uut, new ImportedClass_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new DataType_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new Association_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new ClassInAssociation_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new Operation_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new OperationParameter_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new Attribute_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new BaseAttribute_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new ReferentialAttribute_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new DerivedBaseAttribute_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new NewBaseAttribute_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new AttributeReferenceInClass_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new ClassIdentifier_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new ClassIdentifierAttribute_c(modelRoot)) );
   assertFalse ( GraphicsEditorListener.classInView(uut, new ReferredToIdentifierAttribute_c(modelRoot)) );
  }
  private void validateIgnoreStateMachineChanges( Model_c uut )
  {
    assertFalse ( GraphicsEditorListener.classInView(uut, new StateMachineState_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new Action_c(modelRoot)) );
  }
  private void validateIgnoreActivityChanges( Model_c uut )
  {
    // this is just a sampling of classes
    assertFalse ( GraphicsEditorListener.classInView(uut, new Body_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new Statement_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new Block_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new LiteralInteger_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new ActualParameter_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new GenerateEventStatement_c(modelRoot)) );
    assertFalse ( GraphicsEditorListener.classInView(uut, new Select_c(modelRoot)) );
    
  }
}