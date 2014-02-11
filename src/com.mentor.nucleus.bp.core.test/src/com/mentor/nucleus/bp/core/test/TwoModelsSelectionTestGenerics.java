//=====================================================================
//
//File:      $RCSfile: TwoModelsSelectionTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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

import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c; //import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;

/**
 * Tests involving the selection of model elements when
 * two different models are open within the product.
 */
public class TwoModelsSelectionTestGenerics extends CoreTest {

	public TwoModelsSelectionTestGenerics(String name) {
		super(null, name);
	}
	/**
	 * For issue 856.  Tests that selecting a class in one model
	 * does not cause a class in a diagram of another model
	 * to appear as selected, do to their having identical ID's. 
	 */
	public void testSelectionAffectsOnlyOneModel() throws Exception {
		// setup the test project and load a model
		loadProject("odms");
		Ooaofooa modelRoot = CoreTest.modelRoot;

		// load a different model
		loadProject("MicrowaveOven");
		Ooaofooa modelRoot2 = CoreTest.modelRoot;

		// for each class in a subsystem of the first model
		Selection selection = Selection.getInstance();
		Package_c subsystem = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("odms"));
		final ModelClass_c[] classes = ModelClass_c
				.ModelClassInstances(subsystem.getModelRoot());
		for (int i = 0; i < classes.length; i++) {
			// if we can find a class with the same ID in a subsystem 
			// of the second model
			final ModelClass_c clazz = classes[i];
			Package_c subsystem2 = Package_c.PackageInstance(modelRoot2,
					new Package_by_name_c("Microwave Oven"));
			ModelClass_c class2 = ModelClass_c.ModelClassInstance(subsystem2
					.getModelRoot(), new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					return ((ModelClass_c) candidate).getObj_id().equals(
							clazz.getObj_id());
				}
			});
			if (class2 != null) {
				// select the class from the first model, only
				selection.setSelection(new StructuredSelection(clazz));

				// check that the class from the second model doesn't 
				// think it's selected 
				assertTrue("Class with matching ID is selected", !Cl_c
						.Isselected(class2));
			}
		}
	}
}
