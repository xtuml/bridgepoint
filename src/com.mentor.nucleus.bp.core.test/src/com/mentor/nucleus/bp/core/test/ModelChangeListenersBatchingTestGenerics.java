//========================================================================
//
//File:      $RCSfile: ModelChangeListenersBatchingTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 22:49:04 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class ModelChangeListenersBatchingTestGenerics extends CoreTest {

	public ModelChangeListenersBatchingTestGenerics() {
		super();
	}
	protected void setUp() throws Exception {
		m_wp = null; // make sure widgets are updated and not disposed
		super.setUp();
	}

	TestModelChangeListener instantListener = new TestModelChangeListener(false);
	TestModelChangeListener batchedListener = new TestModelChangeListener(true);

	public void testCreateModelElement() throws Exception {
		ActionTestGenerics test = new ActionTestGenerics();
		test.setUp();
		modelRoot = BaseTest.getDefaultTestInstance();
		Ooaofooa.getDefaultInstance().addModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance().addModelChangeListener(batchedListener);
		Ooaofgraphics c = Ooaofgraphics.getInstance(modelRoot.getId());
		c.addModelChangeListener(instantListener);
		c.addModelChangeListener(batchedListener);
		try {
			//Test add Instance State Machine
			// Must turn off ConsistencyTransactionListener - the test will check the
			// consistency of the relevant individual components
			Ooaofooa.setConsistencyEnabled(false);
			test.testNewO_OBJInstanceStateMachine();
			// Turn consistency checking back on.
			Ooaofooa.setConsistencyEnabled(true);

			Display d = Display.getDefault();
			while (d.readAndDispatch()) {
			}

		} catch (Exception e) {
			fail("Test creation of model Element Failed: " + e.toString()); //$NON-NLS-1$
		}

		//Comparing Events	        
		assertTrue(
				"No events heard by instant listener", instantListener.eventsList.size() > 0);//$NON-NLS-1$
		assertTrue(
				"No events heard by batched listener", batchedListener.eventsList.size() > 0);//$NON-NLS-1$
		// the number of events will be different by one
		// this is due to the fact that deltas that affect
		// the same element's name are not collected only the
		// first delta found is updated.  This test collects
		// events that were fired, in the case of an instant
		// listener the update needs to be fired, for a batched
		// listener it does not.
		assertEquals(
				"Number of Events in the instant Listener: " + instantListener.eventsList.size() + " is not same as in batched listener: " + batchedListener.eventsList.size(), instantListener.eventsList.size() - 1, batchedListener.eventsList.size()); //$NON-NLS-1$//$NON-NLS-2$

		// the events should be in a different order, the instant listener updates Model_c.OOA_ID 
		// at a different time that the batch listener
		assertFalse(
				"Batched events are in same order as instance events", instantListener.eventsList.equals(batchedListener.eventsList)); //$NON-NLS-1$//$NON-NLS-2$

		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(batchedListener);
	}

	public void testDeleteModelElement() throws Exception {
		ActionTestGenerics delAction = new ActionTestGenerics();
		delAction.setUp();
		modelRoot = BaseTest.getDefaultTestInstance();

		Ooaofooa.getDefaultInstance().addModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance().addModelChangeListener(batchedListener);
		Ooaofgraphics c = Ooaofgraphics.getInstance(modelRoot.getId());
		c.addModelChangeListener(instantListener);
		c.addModelChangeListener(batchedListener);
		try {
			//delete Subsystem
			delAction.testDeleteS_SS();

			Display d = Display.getDefault();
			while (d.readAndDispatch()) {
			}

		} catch (Exception e) {
			fail("Test deletion of model Element Failed: " + e.toString()); //$NON-NLS-1$
		}

		//Comparing Events	        
		assertTrue(
				"No events heard by instant listener", instantListener.eventsList.size() > 0);//$NON-NLS-1$
		assertTrue(
				"No events heard by batched listener", batchedListener.eventsList.size() > 0);//$NON-NLS-1$
		assertEquals(
				"Number of Events in the instant Listener: " + instantListener.eventsList.size() + " is not same as in batched listener: " + batchedListener.eventsList.size(), instantListener.eventsList.size(), batchedListener.eventsList.size()); //$NON-NLS-1$ //$NON-NLS-2$
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(batchedListener);
	}

	public void testRenameS_SS() throws Exception {
//		if (m_sys!=null) {
//			unloadComponentAndChildren(m_sys.getPersistableComponent());
//			super.tearDown();
//		}
		RenameTestGenerics renTest = new RenameTestGenerics();
		renTest.setUp();
		Display d = Display.getDefault();
		while (d.readAndDispatch());

		Ooaofooa.getDefaultInstance().addModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance().addModelChangeListener(batchedListener);
		Ooaofgraphics c = Ooaofgraphics.getInstance(modelRoot.getId());
		c.addModelChangeListener(instantListener);
		c.addModelChangeListener(batchedListener);

		try {
			renTest.testRenameSQ_CP();

			while (d.readAndDispatch()) {
			}
		} catch (Exception e) {
			fail("Test rename of model Elements Failed: " + e.toString()); //$NON-NLS-1$
		}

		//Comparing Events	        
		assertTrue(
				"No events heard by instant listener", instantListener.eventsList.size() > 0);//$NON-NLS-1$
		assertTrue(
				"No events heard by batched listener", batchedListener.eventsList.size() > 0);//$NON-NLS-1$
		assertEquals(
				"Number of Events in the instant Listener: " + instantListener.eventsList.size() + " is not same as in batched listener: " + batchedListener.eventsList.size(), instantListener.eventsList.size(), batchedListener.eventsList.size()); //$NON-NLS-1$//$NON-NLS-2$
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(batchedListener);
	}

	public void testRenameO_ATTR() throws Exception {
		RenameTestGenerics renTest = new RenameTestGenerics();
		renTest.setUp();
		Display d = Display.getDefault();
		while (d.readAndDispatch());

		Ooaofooa.getDefaultInstance().addModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance().addModelChangeListener(batchedListener);
		Ooaofgraphics c = Ooaofgraphics.getInstance(modelRoot.getId());
		c.addModelChangeListener(instantListener);
		c.addModelChangeListener(batchedListener);

		try {
			renTest.testRenameO_ATTR();

			while (d.readAndDispatch()) {
			}
		} catch (Exception e) {
			fail("Test rename of model Elements Failed: " + e.toString()); //$NON-NLS-1$
		}

		//Comparing Events	        
		assertTrue(
				"No events heard by instant listener", instantListener.eventsList.size() > 0);//$NON-NLS-1$
		assertTrue(
				"No events heard by batched listener", batchedListener.eventsList.size() > 0);//$NON-NLS-1$
		assertEquals(
				"Number of Events in the instant Listener: " + instantListener.eventsList.size() + " is not same as in batched listener: " + batchedListener.eventsList.size(), instantListener.eventsList.size(), batchedListener.eventsList.size()); //$NON-NLS-1$//$NON-NLS-2$
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(instantListener);
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(batchedListener);
	}

	class TestModelChangeListener extends ModelChangeAdapter {

		List eventsList = new Vector(10);

		private boolean batchNotificationEnabled = false;

		public TestModelChangeListener(boolean isBatched) {
			batchNotificationEnabled = isBatched;
		}

		public boolean isBatchedNotificationEnabled() {
			return batchNotificationEnabled;
		}

		protected void performDefault(ModelChangedEvent event, IModelDelta delta) {
			eventsList.add(delta);
		}
	}
}
