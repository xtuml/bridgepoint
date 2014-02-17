package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File:      $RCSfile: MultipleSupertypeTest.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 05:41:51 $
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
//
// 
import org.eclipse.jface.viewers.ISelection;

import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

/**
 * Performs various scenario tests dealing with connectors. 
 */
public class MultipleSupertypeTest extends CanvasTest 
{
	String test_id = null;
	private static boolean generateResults = false;
	private static Selection selection = Selection.getInstance();

	/**
	 * Constructor
	 */
	public MultipleSupertypeTest(String arg0) 
	{
		super(null, arg0);
	}
	
	/**
	 * See parent method overridden.
	 */
	public void setUp() throws Exception 
	{
		super.setUp();
		
		// we reload the model each time for these tests to make the preconditions
		// for successive tests clearer; otherwise, debugging gets difficult;
		// there are so few tests that the performance hit isn't really noticeable 
		super.setModelName("MultipleSupertypeTest");
		loadProject(getModelName());
	}

	/**
	 * See parent method overridden.
	 */
	protected String getResultName() 
	{
		return super.getModelName() + "_" + test_id;
	}
	
	public class ImportedClass_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ImportedClass_c selected = (ImportedClass_c) candidate;
			return (selected.Get_name().equals(m_name));
		}
		public ImportedClass_by_name_c(String name) {
			m_name = name;		
		}
		private String m_name;
		}
	
	public Shape_c getModelIClassShape(String name) {
		CanvasTestUtils ctu = new CanvasTestUtils();
		ImportedClass_c ic =
			ImportedClass_c.ImportedClassInstance(modelRoot, new ImportedClass_by_name_c(name));
		GraphicalElement_c ge =
			GraphicalElement_c.GraphicalElementInstance(graphicsModelRoot,
				ctu.new findGraphicalElementByOOAID(ic.getIobj_id(), 23));
		return Shape_c.getOneGD_SHPOnR2(ge);
		}

	/**
	 * Tests selection of a subtype line to a class with multiple supertypes.  
	 */
	public void test_selectSubtype() throws Exception 
	{
		test_id = "1";
  		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "Subtype");
  		doSelectSubtypeConnector(shp);
	}

	/**
	 * Tests selection of a subtype line to an imported class with multiple supertypes.  
	 */
	public void test_selectImportedSubtype() throws Exception 
	{
		test_id = "2";
  		Shape_c shp = getModelIClassShape("Imported Subtype");
  		doSelectSubtypeConnector(shp);
	}

	private void doSelectSubtypeConnector(Shape_c shp) {
  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor((NonRootModelElement) ge.getRepresents(), false);
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);		
  		Cl_c.Clearselection();	
      	UITestingUtilities.addElementToGraphicalSelection(ge2.getRepresents());
  		doDiagram(editor);
	}

	private void doDiagram(GraphicalEditor editor) {
        validateOrGenerateResultsGenerics(editor, generateResults);
	}
    
	  public void setGenerateResults() {
		try {
			generateResults = true;
			ISelection old_sel = Selection.getInstance().getSelection();
			test_selectSubtype();
			test_selectImportedSubtype();
			selection.setSelection(old_sel);
		} catch (Exception e) {
		  System.out.println("Exception encountered by test result creator: " + e);
		}
	  }
}