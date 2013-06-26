//=====================================================================
//
//File:      $RCSfile: RenameTest2Generics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:26 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.core.test;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.test.TestUtil;

/**
 * Contains other tests related to renaming model elements.
 */
public class RenameTest2Generics extends CoreTest {
	private static boolean initialized = false;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			loadProject("testRename1");
			initialized = true;

		}
	}

	/**
	 * Tests that the renaming of a model element to include a space in its 
	 * name does not succeed for element types which don't support spaces.
	 */
	public void testSpaceInModelElementNamesNotAllowed() {
		// try to rename an attribute with a space included
		final Attribute_c attribute = Attribute_c.AttributeInstance(modelRoot);
		checkSpaceInModelElementNameNotAllowed(attribute, new NameProvider() {
			public String getName() {
				return attribute.getName();
			}
		});

		// try to rename a bridge with a space included
		final Bridge_c bridge = Bridge_c.BridgeInstance(modelRoot);
		checkSpaceInModelElementNameNotAllowed(bridge, new NameProvider() {
			public String getName() {
				return bridge.getName();
			}
		});

		// try to rename an operation with a space included
		final OperationParameter_c parameter = OperationParameter_c
				.OperationParameterInstance(modelRoot);
		checkSpaceInModelElementNameNotAllowed(parameter, new NameProvider() {
			public String getName() {
				return parameter.getName();
			}
		});
	}

	/**
	 * Callers to checkSpaceInModelElementNameNotAllowed() are required to 
	 * supply an instance of this that will provide the current name of the 
	 * model element that is also supplied to that method. 
	 */
	private interface NameProvider {
		String getName();
	}

	/**
	 * Checks that renaming the given element to a name which contains
	 * spaces does not succeed.
	 * 
	 * @param nameProvider      See parameter's type.  
	 */
	public void checkSpaceInModelElementNameNotAllowed(ModelElement element,
			NameProvider nameProvider) {
		// help validate the calling test by checking that renaming the given
		// element to a name without spaces is ok
		String oldName = nameProvider.getName();
		String newName = oldName + "1";
		RenameAction.getRenameQuery(element, newName, oldName, true).run();
		assertTrue("Changing name in valid way unsuccessful.", nameProvider
				.getName().equals(newName));

		// check that renaming the given element to a name which contains 
		// spaces does not succeed, and dismiss the error dialog that appears
		oldName = newName;
		newName = newName + " ";
		TestUtil.dismissDialog(500);
		RenameAction.getRenameQuery(element, newName, oldName, true).run();
		assertEquals("Adding space to element name succeeded.", oldName,
				nameProvider.getName());
	}
}
