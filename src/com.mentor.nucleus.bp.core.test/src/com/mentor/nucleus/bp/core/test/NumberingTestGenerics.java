//========================================================================
//
//File:      $RCSfile: NumberingTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:25 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.test;

import java.util.UUID;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.test.TestUtil;

/**
 * Tests the numbering of certain kinds of model elements.
 */
public class NumberingTestGenerics extends CoreTest {

	/**
	 * That used as part of the test fixture.
	 */
	private static Package_c subsystem;

	private static boolean initialized = false;
	
	public NumberingTestGenerics() {
		super("NumberingTestGenerics", "");
	}

	/**
	 * See parent method overridden.
	 */
	protected void setUp() throws Exception {
		// if this is the first test being run within this class, set up the
		// fixture
		// that all these tests will use
		if (!initialized) {
			setupFixture();
			initialized = true;
		}

	}

	/**
	 * Sets up the test fixture used by all tests in this class.
	 */
	protected void setupFixture() throws Exception {
		// create and resolve a subsystem on the system model
		m_sys.Newpackage();
		subsystem = Package_c.getOneEP_PKGOnR1401(m_sys);
		modelRoot = (Ooaofooa) subsystem.getModelRoot();
		// assign a non-zero starting value for the range of numbers allowed
		// to be assigned to model entities owned by the subsystem, so we can
		// test
		// whether it will be taken into account
		subsystem.setNum_rng(100);
		TestUtil.createMockDefaultDataTypes(modelRoot, null);
	}

	/**
	 * Checks that when a new element is added to a presumedly already-existing
	 * list of model elements (which are of homogeneous type - class,
	 * association, etc. - and are the only ones of that type existing within
	 * the subsystem) that the element's number is one greater than that of the
	 * last in the list. The numbers of the elements in the initial list are
	 * modified to put increasing gaps between them, to make this more of a
	 * test.
	 * 
	 * @param numPredecessors
	 *            How many elements are in the pre-existing list.
	 * @param template
	 *            See class description.
	 * @param checkSubstemRange
	 *            Whether to check that the number assigned to the new element
	 *            is within the range allowed by the owning subsystem
	 */
	private void checkNumberingWithGaps(int numPredecessors,
			CheckNumberingWithGapsTemplate template, boolean checkSubstemRange) {
		// for each predecessor
		int max = 0;
		for (int i = 0; i < numPredecessors; i++) {
			// add a small amount to this predecessor's number
			// that grows to make increasingly big gaps between them
			int number = template.getElementNumber(i) + (int) Math.pow(i, 3);
			template.setElementNumber(i, number);

			// if this is the last iteration, remember the maximum number
			// that is assigned to this last predecessor
			if (i == numPredecessors - 1)
				max = number;
		}

		template.createLastElement();

		// check that the number of last element created is one greater
		// than the highest number assigned to the predecessors, above
		int number = template.getElementNumber(numPredecessors);
		assertEquals(max + 1, number);

		// if it was specified to do so
		if (checkSubstemRange) {
			// check that the number assigned to the last element meets or
			// exceeds the start of the range allowed by the owning subsystem
			assertTrue(
					"Number " + number + " less than range " + subsystem.getNum_rng(), //$NON-NLS-1$ //$NON-NLS-2$  
					number >= subsystem.getNum_rng());
		}

	}

	/**
	 * Provides callbacks for checkNumberingWithGaps() to interact with its
	 * caller.
	 */
	private static interface CheckNumberingWithGapsTemplate {
		/**
		 * Returns the number of the element of the given index within the
		 * pre-existing list held by this template's implementation.
		 */
		int getElementNumber(int index);

		/**
		 * Sets the number of the element of the given index within the
		 * pre-existing list held by this template's implementation.
		 */
		void setElementNumber(int index, int number);

		/**
		 * Instructs this template's implementation to create the final element,
		 * whose number will be tested for correctness.
		 */
		void createLastElement();
	}

	/**
	 * Tests the numbering of model-classes added to a subsystem.
	 */
	public void testModelClassNumbering() throws Exception {
		// create some model-classes within the subsystem
		final int numPredecessors = 3;
		for (int i = 0; i < numPredecessors; i++)
			subsystem.Newclass();

		// check the numbering of a new model-class added to those
		// created above
		checkNumberingWithGaps(numPredecessors,
				new CheckNumberingWithGapsTemplate() {
					private PackageableElement_c[] pckElements = PackageableElement_c
							.getManyPE_PEsOnR8000(subsystem);

					private ModelClass_c[] classes = ModelClass_c
							.getManyO_OBJsOnR8001(pckElements);

					public int getElementNumber(int index) {
						return classes[index].getNumb();
					}

					public void setElementNumber(int index, int number) {
						classes[index].setNumb(number);
					}

					public void createLastElement() {
						subsystem.Newclass();
						pckElements = PackageableElement_c
								.getManyPE_PEsOnR8000(subsystem);
						classes = ModelClass_c
								.getManyO_OBJsOnR8001(pckElements);
					}
				}, true);
	}

	/**
	 * Tests the numbering of associations added to a subsystem.
	 */
	public void testAssociationNumbering() throws Exception {
		// resolve the first model-class created previously

		PackageableElement_c[] pckElements = PackageableElement_c
				.getManyPE_PEsOnR8000(subsystem);

		ModelClass_c[] classes = ModelClass_c.getManyO_OBJsOnR8001(pckElements);
		ModelClass_c clazz = classes[0];

		// create some reflexive associations from/to that model-class
		final int numPredecessors = 3;
		final UUID classId = clazz.getObj_id();
		for (int i = 0; i < numPredecessors; i++) {
			subsystem.Newassociation(classId, false, classId, false);
		}

		// check the numbering of a new association added to those
		// created above
		checkNumberingWithGaps(numPredecessors,
				new CheckNumberingWithGapsTemplate() {
					PackageableElement_c[] pckElements = PackageableElement_c
							.getManyPE_PEsOnR8000(subsystem);
					private Association_c[] assocs = Association_c
							.getManyR_RELsOnR8001(pckElements);

					public int getElementNumber(int index) {
						return assocs[index].getNumb();
					}

					public void setElementNumber(int index, int number) {
						assocs[index].setNumb(number);
					}

					public void createLastElement() {
						subsystem
								.Newassociation(classId, false, classId, false);
						pckElements = PackageableElement_c
								.getManyPE_PEsOnR8000(subsystem);
						assocs = Association_c
								.getManyR_RELsOnR8001(pckElements);
					}
				}, true);
	}

	/**
	 * Tests the numbering of states added to a model-class instance state
	 * machine.
	 */
	public void testInstanceStatesNumbering() throws Exception {
		// resolve the first model-class created previously
		PackageableElement_c[] pckElements = PackageableElement_c
				.getManyPE_PEsOnR8000(subsystem);

		ModelClass_c[] classes = ModelClass_c.getManyO_OBJsOnR8001(pckElements);
		ModelClass_c clazz = classes[0];

		// create an instance state-machine for that model-class
		clazz.Create_sm("ISM");

		// create some states for the state machine
		final int numPredecessors = 3;
		final InstanceStateMachine_c instanceMachine = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		for (int i = 0; i < numPredecessors; i++) {
			instanceMachine.Newstate();
		}

		// check the numbering of a new state added to those
		// created above
		final StateMachine_c machine = StateMachine_c
				.getOneSM_SMOnR517(instanceMachine);
		checkNumberingWithGaps(numPredecessors,
				new CheckNumberingWithGapsTemplate() {
					private StateMachineState_c[] states = StateMachineState_c
							.getManySM_STATEsOnR501(machine);

					public int getElementNumber(int index) {
						return states[index].getNumb();
					}

					public void setElementNumber(int index, int number) {
						states[index].setNumb(number);
					}

					public void createLastElement() {
						instanceMachine.Newstate();
						states = StateMachineState_c
								.getManySM_STATEsOnR501(machine);
					}
				}, false);
	}

	/**
	 * Tests the numbering of states added to a model-class class state machine.
	 */
	public void testClassStatesNumbering() throws Exception {
		// resolve the first model-class created previously
		PackageableElement_c[] pckElements = PackageableElement_c
				.getManyPE_PEsOnR8000(subsystem);

		ModelClass_c[] classes = ModelClass_c.getManyO_OBJsOnR8001(pckElements);
		ModelClass_c clazz = classes[0];

		// now, create a class state-machine for the model-class
		clazz.Create_sm("ASM"); //$NON-NLS-1$

		// create some states for the class state-machine
		final int numPredecessors = 3;
		final ClassStateMachine_c classMachine = ClassStateMachine_c
				.getOneSM_ASMOnR519(clazz);
		for (int i = 0; i < numPredecessors; i++) {
			classMachine.Newstate();
		}

		// check the numbering of a new state added to those
		// created above
		final StateMachine_c machine = StateMachine_c
				.getOneSM_SMOnR517(classMachine);
		CheckNumberingWithGapsTemplate template = new CheckNumberingWithGapsTemplate() {
			private StateMachineState_c[] states = StateMachineState_c
					.getManySM_STATEsOnR501(machine);

			public int getElementNumber(int index) {
				return states[index].getNumb();
			}

			public void setElementNumber(int index, int number) {
				states[index].setNumb(number);
			}

			public void createLastElement() {
				classMachine.Newstate();
				states = StateMachineState_c.getManySM_STATEsOnR501(machine);
			}
		};
		checkNumberingWithGaps(numPredecessors, template, false);
	}

	/**
	 * Tests the numbering of events added to a model-class's instance
	 * state-machine.
	 */
	public void testInstanceEventsNumbering() throws Exception {
		// resolve the first model-class created previously
		PackageableElement_c[] pckElements = PackageableElement_c
				.getManyPE_PEsOnR8000(subsystem);

		ModelClass_c[] classes = ModelClass_c.getManyO_OBJsOnR8001(pckElements);
		ModelClass_c clazz = classes[0];

		// resolve the class's instance-state-machine
		final InstanceStateMachine_c instanceMachine = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);

		// create some events for the state machine
		final int numPredecessors = 3;
		for (int i = 0; i < numPredecessors; i++) {
			instanceMachine.Newevent();
		}

		// check the numbering of a new event added to those
		// created above
		final StateMachine_c machine = StateMachine_c
				.getOneSM_SMOnR517(instanceMachine);
		checkNumberingWithGaps(numPredecessors,
				new CheckNumberingWithGapsTemplate() {
					private StateMachineEvent_c[] events = StateMachineEvent_c
							.getManySM_EVTsOnR502(machine);

					public int getElementNumber(int index) {
						return events[index].getNumb();
					}

					public void setElementNumber(int index, int number) {
						events[index].setNumb(number);
					}

					public void createLastElement() {
						instanceMachine.Newevent();
						events = StateMachineEvent_c
								.getManySM_EVTsOnR502(machine);
					}
				}, false);
	}

	/**
	 * Tests the numbering of events added to a model-class's class
	 * state-machine.
	 */
	public void testClassEventsNumbering() throws Exception {
		// resolve the first model-class created previously
		PackageableElement_c[] pckElements = PackageableElement_c
				.getManyPE_PEsOnR8000(subsystem);

		ModelClass_c[] classes = ModelClass_c.getManyO_OBJsOnR8001(pckElements);
		ModelClass_c clazz = classes[0];

		// resolve the class's class-state-machine
		final ClassStateMachine_c classMachine = ClassStateMachine_c
				.getOneSM_ASMOnR519(clazz);

		// create some events for the state machine
		final int numPredecessors = 3;
		for (int i = 0; i < numPredecessors; i++) {
			classMachine.Newevent();
		}

		// check the numbering of a new event added to those
		// created above
		final StateMachine_c machine = StateMachine_c
				.getOneSM_SMOnR517(classMachine);
		checkNumberingWithGaps(numPredecessors,
				new CheckNumberingWithGapsTemplate() {
					private StateMachineEvent_c[] events = StateMachineEvent_c
							.getManySM_EVTsOnR502(machine);

					public int getElementNumber(int index) {
						return events[index].getNumb();
					}

					public void setElementNumber(int index, int number) {
						events[index].setNumb(number);
					}

					public void createLastElement() {
						classMachine.Newevent();
						events = StateMachineEvent_c
								.getManySM_EVTsOnR502(machine);
					}
				}, false);
	}
}