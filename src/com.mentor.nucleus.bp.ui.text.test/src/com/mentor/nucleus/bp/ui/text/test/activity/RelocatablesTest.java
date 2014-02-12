//=====================================================================
//
//File:      $RCSfile: RelocatablesTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:46:34 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.text.test.activity;

import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyledText;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleParticipant_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Enumerator_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Justification_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ReferredToClassInAssoc_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

/**
 * Contains tests which exercise the functionality pertaining to relocatables.
 */
public class RelocatablesTest extends BaseTest
{
    /**
     * That on which these tests operate.
     */
    private static Subsystem_c subsystem;
    
    private boolean firstTime = true;
    
    private boolean reCopy = false;
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    
    public RelocatablesTest(String arg0){
        super("com.mentor.nucleus.bp.ui.text.relocatables", arg0);  //$NON-NLS-1$
    }
    
    public void setUp() throws Exception
    {
        // if we haven't yet imported the model used for these tests
        if (firstTime || reCopy) {
            // do so
            ensureAvailableAndLoaded("relocatablesTest", true, reCopy);
            firstTime = false;
        }
        
        // this will speed the completion of any of these tests which wait 
        // for a parse to complete
        ActivityEditor.setWaitBeforeParse(0);
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        // reset this value that we changed in setup() so that future tests
        // aren't affected
        ActivityEditor.resetWaitBeforeParse();
    }
    
    /**
     * Represents an activity on which a test case for one of the tests
     * in this class operates.  
     */
    private abstract static class Activity
    {
        /**
         * The title of an editor which is opened on this activity.  We store
         * this to be able to recognize which open editor is displaying this
         * activity.
         */
        private String editorTitle;
        
        /**
         * Returns the model element from which it is possible to open an
         * editor on this activity.
         */
        public abstract ModelElement getElement();
        
        /**
         * Constructor.
         */
        public Activity(String editorTitle)
        {
            this.editorTitle = editorTitle;
        }

        /**
         * Completes this class's template for activities which are functions.
         */
        private static class FunctionActivity extends Activity
        {
            String functionLetter;
            FunctionActivity(String functionLetter) {
                super("function" + functionLetter + ": Function Activity");
                this.functionLetter = functionLetter;
            }
            public ModelElement getElement() {
                return OoaofooaUtil.getFunction(
                    modelRoot, "function" + functionLetter);
            }
        };

        /**
         * The flyweight activities on which tests in this class operate.
         */
        public static Activity 
            functionA = new FunctionActivity("A"),
            functionB = new FunctionActivity("B"),
            functionC = new FunctionActivity("C"),
            functionD = new FunctionActivity("D"),
            functionE = new FunctionActivity("E"),
            operationAA = new Activity("operationAA: Operation Activity") {
                public ModelElement getElement() {
                    subsystem = OoaofooaUtil.getSubsystem(modelRoot, "Relocatables Test");
                    return OoaofooaUtil.getOperation(
                        OoaofooaUtil.getClass(subsystem, "ClassA"), "operationAA");
                }
            },
            bridgeA = new Activity("bridgeA: Bridge Activity") {
                public ModelElement getElement() {
                    return OoaofooaUtil.getBridge(
                        OoaofooaUtil.getExternalEntity(
                            modelRoot, "ExternalEntityA"), "bridgeA");
                }
            },
            stateAA = new Activity("stateAA: State Machine State Activity") {
                public ModelElement getElement() {
                    return OoaofooaUtil.getState(
                        OoaofooaUtil.getStateMachine(modelRoot, "ClassA"), 
                        "stateAA");
                }
            },
            derivedAttributeBB = new Activity("attributeBB: Attribute Activity") {
                public ModelElement getElement() {
                    subsystem = OoaofooaUtil.getSubsystem(modelRoot, "Relocatables Test");
                    return DerivedBaseAttribute_c.getOneO_DBATTROnR107(
                        BaseAttribute_c.getOneO_BATTROnR106(
                            OoaofooaUtil.getAttribute(
                                OoaofooaUtil.getClass(subsystem, "ClassB"), 
                                "attributeBB")));
                }
            };
    }
    
    /**
     * Represents a relocatable whose value may be changed by tests in this 
     * class.
     */
    private abstract static class Relocatable
    {
        /**
         * The model element whose attribute is this relocatable.  We cache
         * this to make it easy to find once this relocatable's value has been 
         * changed, since in some cases we use that value to locate this 
         * element.  However, this cache should be cleared after each complete
         * usage of this relocatable (by calling reset(), below) otherwise it 
         * will be held onto even after a model-reload (as is performed by some
         * of the tests in this class).
         */
        protected ModelElement element;
        
        /**
         * See field.  This method should return the field's cached value if 
         * it is non-null. 
         */
        public abstract ModelElement getElement();

        /**
         * Returns what this relocatable's original value should be.
         */
        public abstract String getOriginalValue();
        
        /**
         * Sets this relocatable's value to that given.
         */
        public abstract void setValue(String value);

        /**
         * Resets this relocatable's state after modification so it may be 
         * used again during a later test.  This is necessary as the instances
         * of this class are all stateful flyweights.  
         */
        public void reset()
        {
            setValue(getOriginalValue());
            element = null;
        }
        
        /**
         * Completes this class's template for relocatables which are 
         * model-class attribute names.
         */
        private static class AttributeRelocatable extends Relocatable
        {
            String classLetter;
            String attributeLetter;
            AttributeRelocatable(String classLetter, String attributeLetter) {
                this.classLetter = classLetter;
                this.attributeLetter = attributeLetter;
            }
            public ModelElement getElement() {
                subsystem = OoaofooaUtil.getSubsystem(modelRoot, "Relocatables Test");
                return element != null ? element : 
                    (element = OoaofooaUtil.getAttribute(
                        OoaofooaUtil.getClass(subsystem, "Class" + classLetter), 
                        getOriginalValue()));
            }
            public String getOriginalValue() {return "attribute" + classLetter + attributeLetter;}
            public void setValue(String value) {
                ((Attribute_c)getElement()).setRoot_nam(value);
            }
        }
        
        /**
         * Completes this class's template for relocatables which are 
         * model-class key-letters.
         */
        private static class ClassRelocatable extends Relocatable
        {
            String classLetter;
            ClassRelocatable(String classLetter) {
                this.classLetter = classLetter;
            }
            public ModelElement getElement() {
                subsystem = OoaofooaUtil.getSubsystem(modelRoot, "Relocatables Test");
                return element != null ? element : 
                    (element = OoaofooaUtil.getClass(
                        subsystem, "Class" + classLetter));
            }
            public String getOriginalValue() {return "SSA_" + classLetter;}
            public void setValue(String value) {
                ((ModelClass_c)getElement()).setKey_lett(value);
            }
        };

        /**
         * Completes this class's template for relocatables which are 
         * function names.
         */
        private static class FunctionRelocatable extends Relocatable
        {
            String functionLetter;
            FunctionRelocatable(String functionLetter) {
                this.functionLetter = functionLetter;
            }
            public ModelElement getElement() {
                return element != null ? element : 
                    (element = OoaofooaUtil.getFunction(modelRoot, getOriginalValue()));
            }
            public String getOriginalValue() {return "function" + functionLetter;}
            public void setValue(String value) {
                ((Function_c)getElement()).setName(value);
            }
        };

        /**
         * Completes this class's template for relocatables which are 
         * function parameter names.
         */
        private static class FunctionParameterRelocatable extends Relocatable
        {
            String functionLetter;
            String parameterLetter;
            FunctionParameterRelocatable(
                String functionLetter, String parameterLetter) {
                this.functionLetter = functionLetter;
                this.parameterLetter = parameterLetter;
            }
            public ModelElement getElement() {
                return element != null ? element : 
                    (element = OoaofooaUtil.getFunctionParameter(
                        OoaofooaUtil.getFunction(modelRoot, 
                            "function" + functionLetter), 
                        getOriginalValue()));
            }
            public String getOriginalValue() {
                return "functionParameter" + functionLetter + parameterLetter;
            }
            public void setValue(String value) {
                ((FunctionParameter_c)getElement()).setName(value);
            }
        };
        
        /**
         * Completes this class's template for relocatables which are 
         * bridge parameter names.
         */
        private static class BridgeParameterRelocatable extends Relocatable
        {
            String bridgeLetter;
            String parameterLetter;
            BridgeParameterRelocatable(
                String bridgeLetter, String parameterLetter) {
                this.bridgeLetter = bridgeLetter;
                this.parameterLetter = parameterLetter;
            }
            public ModelElement getElement() {
                return element != null ? element : 
                    (element = OoaofooaUtil.getBridgeParameter(
                        OoaofooaUtil.getBridge(
                            OoaofooaUtil.getExternalEntity(
                                modelRoot, "ExternalEntityA"),
                            "bridge" + bridgeLetter),
                        getOriginalValue()));
            }
            public String getOriginalValue() {
                return "bridgeParameter" + bridgeLetter + parameterLetter;
            }
            public void setValue(String value) {
                ((BridgeParameter_c)getElement()).setName(value);
            }
        };

        /**
         * Completes this class's template for relocatables which are 
         * operation parameter names.
         */
        private static class OperationRelocatable extends Relocatable
        {
            String classLetter;
            String operationLetter;
            OperationRelocatable(String classLetter, String operationLetter) {
                this.classLetter = classLetter;
                this.operationLetter = operationLetter;
            }
            public ModelElement getElement() {
                subsystem = OoaofooaUtil.getSubsystem(modelRoot, "Relocatables Test");
                return element != null ? element : 
                    (element = OoaofooaUtil.getOperation(
                        OoaofooaUtil.getClass(subsystem, "Class" + classLetter), 
                        getOriginalValue()));
            }
            public String getOriginalValue() {return "operation" + classLetter + operationLetter;}
            public void setValue(String value) {
                ((Operation_c)getElement()).setName(value);
            }
        };
        
        /**
         * Completes this class's template for relocatables which are 
         * event data item names.
         */
        private static class DataItemRelocatable extends Relocatable
        {
            String itemLetter;
            DataItemRelocatable(String itemLetter) {
                this.itemLetter = itemLetter;
            }
            public ModelElement getElement() {
                return element != null ? element : 
                    (element = OoaofooaUtil.getDataItem(
                        OoaofooaUtil.getStateMachine(modelRoot, "ClassA"),
                        getOriginalValue()));
            }
            public String getOriginalValue() {return "dataItemAA" + itemLetter;}
            public void setValue(String value) {
                ((StateMachineEventDataItem_c)getElement()).setName(value);
            }
        };
        
        /**
         * Completes this class's template for relocatables which are 
         * association numbers.
         */
        private static class AssociationNumberRelocatable extends Relocatable
        {
            int number;
            AssociationNumberRelocatable(int number) {
                this.number = number;
            }
            public ModelElement getElement() {
                return element != null ? element : 
                    (element = OoaofooaUtil.getAssociation(modelRoot, number));
            }
            public String getOriginalValue() {return "" + number;}
            public void setValue(String value) {
                ((Association_c)getElement()).setNumb(Integer.parseInt(value));
            }
        };

        /**
         * Completes this class's template for relocatables which are 
         * association phrases.
         */
        private static class AssociationPhraseRelocatable extends Relocatable
        {
            int number;
            String phrase;
            AssociationPhraseRelocatable(int number, String phrase) {
                this.number = number;
                this.phrase = phrase;
            }
            public ModelElement getElement() {
                if (element != null) return element;
                ReferredToClassInAssoc_c[] toClasses = 
                    ReferredToClassInAssoc_c.getManyR_RTOsOnR203(
                        OoaofooaUtil.getClassInAssociation(
                            OoaofooaUtil.getAssociation(modelRoot, number), 
                            phrase));
                element = 
                    ClassAsSimpleParticipant_c.getOneR_PARTOnR204(toClasses);
                if (element != null) return element;
                element = 
                    ClassAsAssociatedOtherSide_c.getOneR_AOTHOnR204(toClasses);
                return element;
            }
            public String getOriginalValue() {return phrase;}
            public void setValue(String value) {
                ModelElement element = getElement();
                if (element instanceof ClassAsSimpleParticipant_c) {
                    ((ClassAsSimpleParticipant_c)getElement()).setTxt_phrs(value);
                }
                else if (element instanceof ClassAsAssociatedOtherSide_c) {
                    ((ClassAsAssociatedOtherSide_c)getElement()).setTxt_phrs(value);
                }
            }
        };

        /**
         * The flyweight relocatables whose value the tests in this class 
         * change.
         * 
         * Note: A test will fail if the relocatable whose value is to changed
         * appears more than once in the test's activity. 
         */
        public static Relocatable
            classA = new ClassRelocatable("A"),
            classB = new ClassRelocatable("B"),
            classC = new ClassRelocatable("C"),
            classD = new ClassRelocatable("D"),
            attributeAA = new AttributeRelocatable("A", "A"),
            attributeAB = new AttributeRelocatable("A", "B"),
            attributeAD = new AttributeRelocatable("A", "D"),
            attributeBA = new AttributeRelocatable("B", "A"),
            externalEntityA = new Relocatable() {
                public ModelElement getElement() {
                    return element != null ? element : 
                        (element = OoaofooaUtil.getExternalEntity(
                            modelRoot, "ExternalEntityA"));
                }
                public String getOriginalValue() {return "EE_A";}
                public void setValue(String value) {
                    ((ExternalEntity_c)getElement()).setKey_lett(value);
                }
            },
            bridgeA = new Relocatable() {
                public ModelElement getElement() {
                    return element != null ? element : 
                        (element = (Bridge_c)Activity.bridgeA.getElement());
                }
                public String getOriginalValue() {return "bridgeA";}
                public void setValue(String value) {
                    ((Bridge_c)getElement()).setName(value);
                }
            },
            bridgeParameterAA = new BridgeParameterRelocatable("A", "A"),
            bridgeParameterAB = new BridgeParameterRelocatable("A", "B"),
            functionA = new FunctionRelocatable("A"),
            functionB = new FunctionRelocatable("B"),
            functionE = new FunctionRelocatable("E"),
            functionParameterAA = new FunctionParameterRelocatable("A", "A"),
            functionParameterAB = new FunctionParameterRelocatable("A", "B"),
            functionParameterBA = new FunctionParameterRelocatable("B", "A"),
            functionParameterBB = new FunctionParameterRelocatable("B", "B"),
            dataTypeA = new Relocatable() {
                public ModelElement getElement() {
                    return element != null ? element : 
                        (element = OoaofooaUtil.getDataType(modelRoot, getOriginalValue()));
                }
                public String getOriginalValue() {return "DataTypeA";}
                public void setValue(String value) {
                    ((DataType_c)getElement()).setName(value);
                }
            },
            enumeratorA = new Relocatable() {
                public ModelElement getElement() {
                    return element != null ? element : 
                        (element = OoaofooaUtil.getEnumerator(
                            OoaofooaUtil.getDataType(modelRoot, dataTypeA.getOriginalValue()),
                            getOriginalValue()));
                }
                public String getOriginalValue() {return "enumeratorA";}
                public void setValue(String value) {
                    ((Enumerator_c)getElement()).setName(value);
                }
            },
            operationAA = new OperationRelocatable("A", "A"),
            operationCA = new OperationRelocatable("C", "A"),
            operationParameterCAA = new Relocatable() {
                public ModelElement getElement() {
                    return element != null ? element : 
                        (element = OoaofooaUtil.getOperationParameter(
                            (Operation_c)operationCA.getElement(), 
                            getOriginalValue()));
                }
                public String getOriginalValue() {return "operationParameterCAA";}
                public void setValue(String value) {
                    ((OperationParameter_c)getElement()).setName(value);
                }
            },
            r2 = new AssociationNumberRelocatable(2),
            r4 = new AssociationNumberRelocatable(4),
            r5 = new AssociationNumberRelocatable(5),
            r4Phrase = new AssociationPhraseRelocatable(4, "start"),
            r5Phrase = new AssociationPhraseRelocatable(5, "next"),
            eventLabelAAModelClass = new Relocatable() {
                public ModelElement getElement() {
                    subsystem = OoaofooaUtil.getSubsystem(modelRoot, "Relocatables Test");
                    return OoaofooaUtil.getClass(subsystem, "ClassA");
                }
                public String getOriginalValue() {return "SSA_A1";}
                public void setValue(String value) {
                    ModelClass_c clazz = (ModelClass_c)getElement();
                    clazz.setKey_lett(
                        clazz.getKey_lett().equals("SSA_A") ? "SSA_A1" : "SSA_A");
                }
            },
            eventLabelAANumber = new Relocatable() {
                public ModelElement getElement() {
                    return OoaofooaUtil.getEvent(
                        OoaofooaUtil.getStateMachine(modelRoot, "ClassA"),
                        "eventAA");
                }
                public String getOriginalValue() {return "SSA_A1";}
                public void setValue(String value) {
                    StateMachineEvent_c event = (StateMachineEvent_c)getElement();
                    event.setNumb(event.getNumb() == 1 ? 11 : 1);
                }
            },
            eventMeaningAA = new Relocatable() {
                public ModelElement getElement() {
                    return element != null ? element : 
                        (element = OoaofooaUtil.getEvent(
                            OoaofooaUtil.getStateMachine(modelRoot, "ClassA"),
                            getOriginalValue()));
                }
                public String getOriginalValue() {return "eventAA";}
                public void setValue(String value) {
                    ((StateMachineEvent_c)getElement()).setMning(value);
                }
            },
            dataItemAAA = new DataItemRelocatable("A"),
            dataItemAAC = new DataItemRelocatable("C"),
            dataItemAAD = new DataItemRelocatable("D");
    }
    
    /**
     * Tests that modifying the value of a relocatable updates a mention of it
     * in an open activity editor.
     */
    public void testRelocatableChangeUpdatesOpenActivity()
    {
        // specify the cases this test will execute; each case consists of 
        // an activity, as well as a relocatable mentioned exactly once in that
        // activity
        class TestCase {
            public Activity activity;
            public Relocatable relocatable;
            TestCase(Activity activity, Relocatable relocatable) {
                this.activity = activity;
                this.relocatable = relocatable;
            }
        }
        TestCase[] testCases = {
            // context: the attribute in an assign-to-attribute statement
            new TestCase(Activity.functionA, Relocatable.attributeAA),
            // context: the value being assigned in an assign-to-attribute statement
            new TestCase(Activity.functionA, Relocatable.attributeAB),
            // context: the value being assigned in an assign-to-transient statement
            new TestCase(Activity.bridgeA, Relocatable.bridgeParameterAA),
            // context: the parameter in an assign-to-parameter statement
            new TestCase(Activity.functionA, Relocatable.functionParameterAA),
            // context: the value being assigned in an assign-to-parameter statement
            new TestCase(Activity.bridgeA, Relocatable.functionA),
            // context: the model-class key-letters in a select-from-instances 
            // statement
            new TestCase(Activity.operationAA, Relocatable.classA),
            // context: the model-class key-letters in a 
            // select-from-instances-where statement
            new TestCase(Activity.bridgeA, Relocatable.classA),
            // context: the where-value in a select-from-instances-where 
            // statement
            new TestCase(Activity.bridgeA, Relocatable.enumeratorA),
            // context: the first chain link in a select statement
            new TestCase(Activity.operationAA, Relocatable.classC),
            // context: the second chain link in a select statement
            new TestCase(Activity.operationAA, Relocatable.r2),
            // context: the first chain link in a select statement
            new TestCase(Activity.operationAA, Relocatable.r4Phrase),
            // context: the where-value in a select statement
            new TestCase(Activity.operationAA, Relocatable.dataTypeA),
            // context: the model-class key-letters in an operation invocation
            new TestCase(Activity.functionA, Relocatable.classC),
            // context: the operation name in an operation invocation
            new TestCase(Activity.functionA, Relocatable.operationCA),
            // context: the label of the first parameter in an operation invocation
            new TestCase(Activity.functionA, Relocatable.operationParameterCAA),
            // context: the function name in a function invocation (as well 
            // as being the value of the operation parameter in the previous 
            // test-case)
            new TestCase(Activity.functionA, Relocatable.functionB),
            // context: the label of the first parameter in a function invocation
            new TestCase(Activity.functionA, Relocatable.functionParameterBA),
            // context: the external-entity key-letters in a bridge invocation
            // (as well as being the value of the function parameter in the 
            // previous test-case)
            new TestCase(Activity.functionA, Relocatable.externalEntityA),
            // context: the bridge name in a bridge invocation
            new TestCase(Activity.functionA, Relocatable.bridgeA),
            // context: the label of the second parameter in a bridge invocation
            new TestCase(Activity.functionA, Relocatable.bridgeParameterAB),
            // context: the value of the third parameter in a bridge invocation
            // (since there is a different code path for parameters after the 
            // first), as well as being the value within a unary operator value
            new TestCase(Activity.functionA, Relocatable.functionParameterAB),
            // context: the return-value of a return statement
            new TestCase(Activity.bridgeA, Relocatable.operationAA),
            // context: the model-class key-letters in a create statement
            new TestCase(Activity.stateAA, Relocatable.classC),
            // context: the model-class key-letters in a create-with-no-variable 
            // statement
            new TestCase(Activity.stateAA, Relocatable.classD),
            // context: the model-class part of the event derived label in an 
            // event specification statement
            new TestCase(Activity.stateAA, Relocatable.eventLabelAAModelClass),
            // context: the event-number part of the event derived label in an 
            // event specification statement
            new TestCase(Activity.stateAA, Relocatable.eventLabelAANumber),
            // context: the event meaning in an event specification statement
            new TestCase(Activity.stateAA, Relocatable.eventMeaningAA),
            // context: the label of the first data item in an event 
            // specification statement
            new TestCase(Activity.stateAA, Relocatable.dataItemAAA),
            // context: the value of the second data item in an event 
            // specification statement, as well being the value of a data
            // item supplied to an event
            new TestCase(Activity.stateAA, Relocatable.dataItemAAD),
            // context: the label of the third data item in an event 
            // specification statement (since there is a different code path 
            // for data items after the first) 
            new TestCase(Activity.stateAA, Relocatable.dataItemAAC),
            // context: the model-class key-letters of the event target 
            // in an event specification statement
            new TestCase(Activity.stateAA, Relocatable.classB),
            // context: the attribute name in a generate-preexisting-event 
            // statement
            new TestCase(Activity.stateAA, Relocatable.attributeAD),
            // context: the association number in a relate statement
            new TestCase(Activity.functionB, Relocatable.r4),
            // context: the association phrase in a relate statement
            new TestCase(Activity.functionB, Relocatable.r4Phrase),
            // context: the association number in an unrelate statement
            new TestCase(Activity.functionC, Relocatable.r4),
            // context: the association phrase (with ticks) in an unrelate 
            // statement
            new TestCase(Activity.functionC, Relocatable.r4Phrase),
            // context: the association number in a relate-using statement
            new TestCase(Activity.functionD, Relocatable.r5),
            // context: the association phrase in a relate-using statement
            new TestCase(Activity.functionD, Relocatable.r5Phrase),
            // context: the association number in an unrelate-using statement
            new TestCase(Activity.functionE, Relocatable.r5),
            // context: the association phrase (with ticks) in an 
            // unrelate-using statement
            new TestCase(Activity.functionE, Relocatable.r5Phrase),
            // context: the test value of an if statement
            new TestCase(Activity.functionB, Relocatable.functionParameterBA),
            // context: the controlled block of an if statement
            new TestCase(Activity.functionB, Relocatable.attributeAA),
            // context: the test value of an else-if statement
            new TestCase(Activity.functionB, Relocatable.functionParameterBB),
            // context: the controlled block of an else-if statement
            new TestCase(Activity.functionB, Relocatable.attributeAB),
            // context: the controlled block of an else statement
            new TestCase(Activity.functionB, Relocatable.enumeratorA),
            // context: the test value of a while statement
            new TestCase(Activity.functionC, Relocatable.functionE),
            // context: the controlled block of a while statement
            new TestCase(Activity.functionC, Relocatable.operationAA),
            // context: the test value of a for statement
            new TestCase(Activity.functionC, Relocatable.functionE),
            // context: the controlled block of a for statement
            new TestCase(Activity.functionC, Relocatable.dataTypeA),
            // context: the value being assigned in an assign-to-attribute,
            // but within a derived attribute's body
            new TestCase(Activity.derivedAttributeBB, Relocatable.attributeBA),
        };
        
        // for each relocatable change we're going to test
        for (int i = 0; i < testCases.length; i++) {
            // open an activity containing a mention of the relocatable we're 
            // going to change
            Activity activity = testCases[i].activity;
            ActivityEditorInteraction.openActivityEditor(activity.getElement());
            ActivityEditor editor = 
            	TextEditorUtils.getActivityEditor(activity.editorTitle);
            
            // wait for the parse that occurs when an activity editor is opened
            // to complete
            editor.waitForParseThread();
            
            // remember the current editor text, for the comparison below
            IDocument document = editor.getTextViewer().getDocument();
            String oldText = document.get();

            // change the relocatable's value, which should cause the 
            // mention of that relocatable in the editor to be updated to match
            Relocatable relocatable = testCases[i].relocatable;
            String oldValue = relocatable.getOriginalValue();
            String newValue = oldValue + "1";
            relocatable.setValue(newValue);
            
            // check that nothing changed in the activity text up to the 
            // location of the relocatable
            String newText = document.get();
            int relocatableIndex = oldText.indexOf(oldValue);
            assertEquals(
                "Unintended change occurred to activity text in test-case " + i,
                oldText.substring(0, relocatableIndex),
                newText.substring(0, relocatableIndex));

            // check that the relocatable was updated properly within the 
            // activity text
            int relocatableLength = oldValue.length();
            assertEquals("Relocatable not updated correctly in test-case " + i,
                newValue, newText.substring(relocatableIndex, 
                    relocatableIndex + relocatableLength + 1));

            // check that nothing changed in the activity text after the 
            // location of the relocatable
            assertEquals(
                "Unintended change occurred to activity text in test-case " + i,
                oldText.substring(
                    relocatableIndex + relocatableLength, oldText.length()),
                newText.substring(
                    relocatableIndex + relocatableLength + 1, newText.length()));
            
            // reset the relocatable so that later test-cases may use it
            relocatable.reset();
            
            // wait for the parse thread spun up for the relocatable-reset 
            // above to finish, then close the editor, as leaving either around
            // will often interfere with the operation of the succeeding 
            // test-cases that operate on the same activity
            editor.waitForParseThread();
            EditorUtil.closeEditor(editor);
        }     
        reCopy = true;
    }
    
    /**
     * Tests that a parse-all on a model creates relocatables tags within
     * the model's activities.
     */
    public void testParseAllCreatesRelocatableTags()
    {
        // close any open editors, as if the editor that we're going to open
        // for this test is already open, it will interfere with this test's
        // operation
        EditorUtil.closeAllOpenEditors();
        
        // perform a parse-all on the test model 
        AllActivityModifier modifier = new AllActivityModifier(
            Domain_c.DomainInstance(modelRoot), new NullProgressMonitor());
        modifier.processAllActivities(AllActivityModifier.PARSE);

        // change the value of a relocatable 
        Relocatable relocatable = Relocatable.attributeAA;
        String newValue = relocatable.getOriginalValue() + "1";
        relocatable.setValue(newValue);

        // open an activity which contains a mention of the relocatable
        Activity activity = Activity.functionA;
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        ActivityEditor editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // check that the mention of the relocatable reflects the changed value
        IDocument document = editor.getTextViewer().getDocument();
        assertTrue("Relocatable value not updated in editor.", 
            document.get().contains(newValue));
        
        // reset the relocatable so that later tests may use it
        relocatable.reset();
    }
    
    /**
     * Tests that the diagram text that would be shown for a state action 
     * reflects a change to a relocatable it mentions.
     *  
     * Note: This test relies on the parse-all having been done in the test 
     * above.
     */
    public void testStateActionTextOnDiagramChangesOnRelocatableChange()
    {
        // change the value of a relocatable that we know appears in a 
        // state action
        Relocatable relocatable = Relocatable.classC;
        String newValue = relocatable.getOriginalValue() + "1";
        relocatable.setValue(newValue);

        // check that the text that would be displayed for the state action
        // on a diagram reflects the relocatable's changed value
        String text = Cl_c.Getcompartmenttext(
            Justification_c.Left, 2, 1, Activity.stateAA.getElement());
        assertTrue(text.contains(newValue));

        // reset the relocatable so that later tests may use it
        relocatable.reset();
        reCopy = true;
    }

    /**
     * Tests that an activity is parsed (and has relocatables tags created
     * for it) when it is first opened.
     */
    public void testParseOccursOnEditorOpening()
    {
        // close any open editors, as if the editor that we're going to open
        // for this test is already open, it will interfere with this test's
        // operation
        EditorUtil.closeAllOpenEditors();

        // open an activity which contains a mention of a relocatable
        Activity activity = Activity.functionA;
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        ActivityEditor editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // wait for the parse that occurs when an activity editor is opened
        // to complete
        editor.waitForParseThread();
        
        // change the value of the relocatable 
        Relocatable relocatable = Relocatable.attributeAA;
        String newValue = relocatable.getOriginalValue() + "1";
        relocatable.setValue(newValue);
        
        // check that the mention of the relocatable in the activity reflects 
        // the changed value
        IDocument document = editor.getTextViewer().getDocument();
        assertTrue("Relocatable value not updated in editor.", 
            document.get().contains(newValue));
        
        // reset the relocatable so that later tests may use it
        relocatable.reset();
    }
    
    /**
     * Tests that an activity editor's dirty flag is not set when its contents
     * are updated to a change in the value of a relocatable.
     */
    public void testEditorDirtyFlagNotSetOnRelocatableChange()
    {
        // close any open editors, as if the editor that we're going to open
        // for this test is already open, it will interfere with this test's
        // operation
        EditorUtil.closeAllOpenEditors();

        // open an activity which contains a mention of a relocatable
        Activity activity = Activity.functionB;
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        ActivityEditor editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // wait for the parse that occurs when an activity editor is opened
        // to complete
        editor.waitForParseThread();
        
        // change the value of the relocatable 
        Relocatable relocatable = Relocatable.attributeAA;
        String newValue = relocatable.getOriginalValue() + "1";
        relocatable.setValue(newValue);
        
        // check that the mention of the relocatable in the activity reflects 
        // the changed value
        IDocument document = editor.getTextViewer().getDocument();
        assertTrue("Relocatable value not updated in editor.", 
            document.get().contains(newValue));
        
        // check that the editor's dirty flag is not set
        assertTrue("Editor's dirty flag is set after relocatable change.",
            !editor.isDirty());
        
        // reset the relocatable so that later tests may use it
        relocatable.reset();
    }

    /**
     * Tests that a relocatable change does not cause an editor which contains 
     * a mention of that relocatable to have its contents immediately saved 
     * to the model.
     */
    public void testRelocatableChangeInEditorNotImmediatelySavedToModel()
    {
        // close any open editors, as if the editor that we're going to open
        // for this test is already open, it will interfere with this test's
        // operation
        EditorUtil.closeAllOpenEditors();

        // open an activity which contains a mention of a relocatable
        Activity activity = Activity.functionB;
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        ActivityEditor editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // wait for the parse that occurs when an activity editor is opened
        // to complete
        editor.waitForParseThread();
        
        // make some arbitrary change to the editor's contents, such that its
        // dirty flag gets set
        IDocument document = editor.getTextViewer().getDocument();
        String change = "\nblah = 25;";
        document.set(document.get() + change);
        assertTrue("Editor's dirty flag not set after textual change.",
            editor.isDirty());
        
        // change the value of the relocatable 
        Relocatable relocatable = Relocatable.attributeAA;
        String newValue = relocatable.getOriginalValue() + "1";
        relocatable.setValue(newValue);
        
        // check that the mention of the relocatable in the activity reflects 
        // the changed value
        assertTrue("Relocatable value not updated in editor.", 
            document.get().contains(newValue));
        
        // close, then re-open the editor
        EditorUtil.closeEditor(editor);
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // check that the arbitrary change made to the editor's contents above
        // does not appear within the reopened editor
        document = editor.getTextViewer().getDocument();
        assertTrue(!document.get().contains(change));
        
        // reset the relocatable so that later tests may use it
        relocatable.reset();
    }

    /**
     * Tests that a relocatable change does not affect an editor's current 
     * caret and scroll positions.
     */
    public void testRelocatableChangeDoesNotAffectEditorCaretAndScrollPosition()
    {
        // close any open editors, as if the editor that we're going to open
        // for this test is already open, it will interfere with this test's
        // operation
        EditorUtil.closeAllOpenEditors();

        // open a lengthy activity which contains a mention of a relocatable
        Activity activity = Activity.functionD;
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        ActivityEditor editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // wait for the parse that occurs when an activity editor is opened
        // to complete
        editor.waitForParseThread();
        
        // move the editor's caret and scroll position to the bottom of the
        // document, such that the top part is no longer visible
        IDocument document = editor.getTextViewer().getDocument();
        ISourceViewer viewer = (ISourceViewer)editor.getTextViewer();
        viewer.setTopIndex(document.getNumberOfLines());
        int topIndex = viewer.getTopIndex();
        StyledText widget = viewer.getTextWidget();
        widget.setCaretOffset(document.get().length());
        int caretOffset = widget.getCaretOffset();
        
        // change the value of the relocatable 
        Relocatable relocatable = Relocatable.classD;
        String newValue = relocatable.getOriginalValue() + "1";
        relocatable.setValue(newValue);
        
        // check that the mention of the relocatable in the activity reflects 
        // the changed value
        assertTrue("Relocatable value not updated in editor.", 
            document.get().contains(newValue));
        
        // check that the editor's caret and scroll positions have not changed
        assertTrue("Editor's caret position has changed.",
            widget.getCaretOffset() == caretOffset);
        assertTrue("Editor's scroll position has changed.",
            viewer.getTopIndex() == topIndex);

        // reset the relocatable so that later tests may use it
        relocatable.reset();
    }

    /**
     * Tests that the contents of the last-known-good-value field of
     * a relocatable tag are displayed in an editor after the relocatable
     * has been deleted from the model.
     * 
     * This test relies on the state of the previous test, above.
     */
    public void testLastKnownGoodValueDisplayedForDeletedRelocatable()
    {
        // close any open editors, as if the editor that we're going to open
        // for this test is already open, it will interfere with this test's
        // operation
        EditorUtil.closeAllOpenEditors();

        // delete a relocatable for which we know there are already 
        // relocatable tags in the activity we're going to open, below
        ((ModelClass_c)Relocatable.classD.getElement()).Dispose();
        
        // open an activity which contains a mention of the relocatable 
        // deleted above
        Activity activity = Activity.functionD;
        ActivityEditorInteraction.openActivityEditor(activity.getElement());
        ActivityEditor editor = 
        	TextEditorUtils.getActivityEditor(activity.editorTitle);
        
        // check that the last known good value is displayed in the editor
        // for the deleted relocatable 
        IDocument document = editor.getTextViewer().getDocument();
        assertTrue(
            "Last known good value not displayed in editor for deleted relocatable.",
            document.get().contains(Relocatable.classD.getOriginalValue()));
    }
}
