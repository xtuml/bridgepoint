//=====================================================================
//
//File:      $RCSfile: RefreshTestCoreGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:27 $
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

package org.xtuml.bp.core.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.AsynchronousMessage_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SynchronousMessage_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.properties.AsynchronousMessagesMSG_AMPropertySource;
import org.xtuml.bp.ui.properties.SynchronousMessagesMSG_SMPropertySource;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

@RunWith(OrderedRunner.class)
public class RefreshTestCoreGenerics extends BaseTest {

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean initialized = false;

	public RefreshTestCoreGenerics(){
		super(null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (!initialized) {
			loadProject("SequenceTestModel");
			initialized = true;
		}
	}

	@Test
	public void testRefreshBOperationSynchronousMessage() {

		// open the canvas editor to test on
		openPackageDiagram();

		// select a Synchronous Message
		SynchronousMessage_c SynMsg = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message1");
							}
						});
		assertNotNull(SynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(SynMsg);

		SynchronousMessagesMSG_SMPropertySource ps = new SynchronousMessagesMSG_SMPropertySource(
				SynMsg);

		assertEquals(
				"IsFormal returned false while Synchronous Message was Formal",
				Boolean.valueOf("true"), ps.getPropertyValue("isFormal"));

		SynMsg.Unformalize();

		assertEquals(
				"IsFormal returned true while Synchronous Message was Informal",
				Boolean.valueOf("false"), ps.getPropertyValue("isFormal"));
	}

	@Test
	public void testRefreshFunctionSynchronousMessage() {

		// open the canvas editor to test on
		openPackageDiagram();

		// select a Synchronous Message
		SynchronousMessage_c SynMsg = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message2");
							}
						});
		assertNotNull(SynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(SynMsg);

		SynchronousMessagesMSG_SMPropertySource ps = new SynchronousMessagesMSG_SMPropertySource(
				SynMsg);

		assertEquals(
				"IsFormal returned false while Synchronous Message was Formal",
				Boolean.valueOf("true"), ps.getPropertyValue("isFormal"));

		SynMsg.Unformalize();

		assertEquals(
				"IsFormal returned true while Synchronous Message was Informal",
				Boolean.valueOf("false"), ps.getPropertyValue("isFormal"));
	}

	@Test
	public void testRefreshClassSynchronousMessage() {

		// open the canvas editor to test on
		openPackageDiagram();

		// select a Synchronous Message
		SynchronousMessage_c SynMsg = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message3");
							}
						});
		assertNotNull(SynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(SynMsg);

		SynchronousMessagesMSG_SMPropertySource ps = new SynchronousMessagesMSG_SMPropertySource(
				SynMsg);

		assertEquals(
				"IsFormal returned false while Synchronous Message was Formal",
				Boolean.valueOf("true"), ps.getPropertyValue("isFormal"));

		SynMsg.Unformalize();

		assertEquals(
				"IsFormal returned true while Synchronous Message was Informal",
				Boolean.valueOf("false"), ps.getPropertyValue("isFormal"));
	}

	@Test
	public void testRefreshClassAsynchronousMessage() {

		// open the canvas editor to test on
		openPackageDiagram();

		// select a Synchronous Message
		AsynchronousMessage_c AsynMsg = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message4");
							}
						});
		assertNotNull(AsynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(AsynMsg);

		AsynchronousMessagesMSG_AMPropertySource ps = new AsynchronousMessagesMSG_AMPropertySource(
				AsynMsg);

		assertEquals(
				"IsFormal returned false while Asynchronous Message was Formal",
				Boolean.valueOf("true"), ps.getPropertyValue("isFormal"));

		AsynMsg.Unformalize();

		assertEquals(
				"IsFormal returned true while Asynchronous Message was Informal",
				Boolean.valueOf("false"), ps.getPropertyValue("isFormal"));
	}

	/**
	 * Returns a sequence with the given name
	 */
	private void openPackageDiagram() {
		Package_c sequence = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals("RefreshTest");
					}

				});
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor("RefreshTest");
	}

}
