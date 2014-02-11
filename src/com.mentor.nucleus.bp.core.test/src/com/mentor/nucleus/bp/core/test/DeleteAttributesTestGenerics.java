//=====================================================================
//
//File:      $RCSfile: DeleteAttributesTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:24 $
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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.jface.viewers.StructuredSelection;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;

/**
 * Performs tests that involve deleting attributes from classes.
 */
public class DeleteAttributesTestGenerics extends CoreTest {
	/**
	 * The model used throughout these tests.
	 */
	private static boolean initialized = false;
	private static Selection selection = Selection.getInstance();

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			loadProject("odms");
			initialized = true;

		}
	}

	/**
	 * For doDeletionTest(), this specifies an attribute to delete by its class 
	 * and attribute name.
	 */
	private class AttributePath {
		public String className;
		public String attributeName;

		public AttributePath(String className, String attributeName) {
			this.className = className;
			this.attributeName = attributeName;
		}
	}

	/**
	 * Checks that the deletion of the attributes specified by the given paths
	 * is not allowed to occur.  
	 */
	public void doDeletionTest(AttributePath[] attributePaths) {
		// deselect all selected model elements
		selection.setSelection(new StructuredSelection());

		// for each attribute-path of an attribute we are to delete
		ModelClass_c[] classes = new ModelClass_c[attributePaths.length];
		final Attribute_c[] attributes = new Attribute_c[attributePaths.length];
		for (int i = 0; i < attributePaths.length; i++) {
			// get the model-class specified by this attribute-path
			final AttributePath toDelete = attributePaths[i];
			CanvasTestUtils canvasTestUtil = new CanvasTestUtils();
			classes[i] = ModelClass_c.ModelClassInstance(modelRoot,
					canvasTestUtil.new Class_by_name_c(toDelete.className));

			// get the attribute to be deleted from its model-class
			ClassQueryInterface_c query = new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					return ((Attribute_c) candidate).getName().equals(
							toDelete.attributeName);
				}
			};
			attributes[i] = Attribute_c.getManyO_ATTRsOnR102(classes[i], query)[0];

			// cause the attribute to be selected
			selection.addToSelection(attributes[i]);
		}

		// check that something (presumably, the attributes to delete)
		// was actually selected above
		assertTrue("Attributes weren't selected for deletion", !selection
				.getSelection().isEmpty());

		// attempt to delete the attribute(s), which shouldn't be allowed
		TestUtil.dismissDialog(200);
		new DeleteAction(null).run();

		// for each attribute that was to be deleted above
		for (int i = 0; i < attributes.length; i++) {
			// check that the attribute is still there
			final Attribute_c attribute = attributes[i];
			Attribute_c[] attributesLeft = Attribute_c.getManyO_ATTRsOnR102(
					classes[i], new ClassQueryInterface_c() {
						public boolean evaluate(Object candidate) {
							return ((Attribute_c) candidate).getName().equals(
									attribute.getName());
						}
					});
			assertEquals("Attribute was deleted, which is not allowed",
					attributesLeft.length, 1);
		}
	}

	/**
	 * Checks that the only attribute of a referenced identifier may not be deleted.
	 */
	public void testDeletionOfOnlyIdentifierAttribute() {
		AttributePath[] paths = {new AttributePath("Disk", "Disk_ID")};
		doDeletionTest(paths);
	}

	/**
	 * Checks that the only two attributes of a referenced identifier may not be deleted.
	 */
	public void testDeletionOfOnlyIdentifierAttributes() {
		String className = "Permanent Home in Library";
		AttributePath[] paths = {new AttributePath(className, "Row_Number"),
				new AttributePath(className, "Column_Number")};
		doDeletionTest(paths);
	}

	/**
	 * Checks that the only attribute of a referenced identifier may not be deleted, even
	 * if that attribute is selected along with another one whose deletion would otherwise
	 * be ok.  
	 */
	public void testDeletionOfOnlyIdentifierAttributeWithNonIdentifierAttribute() {
		AttributePath[] paths = {new AttributePath("Disk", "Disk_ID"),
				new AttributePath("Disk", "Serial_Number")};
		doDeletionTest(paths);
	}
}
