//=====================================================================
//
//File:      $RCSfile: DissatisfactionTests.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 22:43:50 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.canvas.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Point;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class DissatisfactionTests extends CanvasTest {
	
	private String test_id;
	public static boolean generateResults = false;

	public DissatisfactionTests(String arg0) {
		super(arg0);
	}

	@Override
	protected String getResultName() {
		return "AutoReconciliationTests" + "_" + test_id;
	}

	public void testGraphicalRepresentsSet() throws Exception {
		test_id = "1";
		
		// test that the graphical represents
		// is set prior to auto reconciliation
		// if it is not the diagram at the end
		// of this test will be missing some
		// graphics
		IProject testProject = TestingUtilities.createProject("Dissatisfaction");

		File sourceProject = new File(m_workspace_path + "../Dissatisfaction");
		
		CorePlugin.disableParseAllOnResourceChange();
		
		TestingUtilities.copyProjectContents(sourceProject, testProject);
		
		TestingUtilities.allowJobCompletion();
		
		Ooaofooa testRoot = Ooaofooa.getInstance(
				"/Dissatisfaction/models/Dissatisfaction/Local Local Pairs/Local Local Pairs.xtuml", true);
		ComponentPackage_c cp = ComponentPackage_c.ComponentPackageInstance(testRoot);
		assertNotNull(cp);
		CanvasUtilities.openCanvasEditor(cp);
		Component_c [] comps = Component_c.getManyC_CsOnR4604(cp);
		Ooaofgraphics ooag = Ooaofgraphics.getInstance(testRoot.getId());
		for (int i=0; i < comps.length; i++) {
			Requirement_c [] reqs = Requirement_c.getManyC_RsOnR4009(InterfaceReference_c.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comps[i])));
			for (int j=0; j < reqs.length; j++) {
				if (comps[i].getName().equals("Row 1 R")) {
					Provision_c [] provs = Provision_c.getManyC_PsOnR4002(reqs[j]);
					assertTrue(provs.length == 1);
					// Do the deed
					TransactionManager tm = getSystemModel("Dissatisfaction").getTransactionManager();
					Transaction transaction = null;
					try {
						transaction = tm.startTransaction("Test Disposal", Ooaofooa.getDefaultInstance());
					reqs[j].Dissatisfy();
					tm.endTransaction(transaction);
					} catch (Exception e) {
						fail(e.toString());
					}
					
					TestingUtilities.allowJobCompletion();
					//
					Selection.getInstance().addToSelection(reqs[j]);
					GraphicalElement_c elem = CanvasTestUtilities.getGraphicalElement(ooag, (NonRootModelElement)reqs[j]);
					Connector_c reqConn = Connector_c.getOneGD_CONOnR2(elem);
					assertTrue(reqConn != null);
					elem = CanvasTestUtilities.getGraphicalElement(ooag, (NonRootModelElement)provs[0]);
					Connector_c provConn = Connector_c.getOneGD_CONOnR2(elem);
					assertTrue(provConn != null);
					int x = reqConn.Getendx();
					int y = reqConn.Getendy();
					assertTrue(x == provConn.Getendx());
					assertTrue(y == provConn.Getendy());
			        Point connEnd = CanvasTestUtilities.convertToMouseCoor(new Point(x,y), Model_c.getOneGD_MDOnR1(elem));
                    CanvasTestUtilities.doMouseMove(connEnd.x, connEnd.y);
					CanvasTestUtilities.doMousePress(connEnd.x, connEnd.y);
					CanvasTestUtilities.doMouseMove(100, 100);
					CanvasTestUtilities.doMouseRelease(connEnd.x+100, connEnd.y+100);
					// Check (ex-) partner connector did not move
					assertTrue(reqConn.Getendx() != provConn.Getendx() );
					assertTrue(reqConn.Getendy() != provConn.Getendy() );
				}
			}
		}
		testRoot = Ooaofooa.getInstance(
				"/Dissatisfaction/models/Dissatisfaction/Local Local Pairs/Local Local Pairs.xtuml", true);
		graphicsModelRoot = Ooaofgraphics.getInstance(testRoot.getId());
		
		cp = ComponentPackage_c.ComponentPackageInstance(testRoot);
		assertNotNull(cp);
		CanvasUtilities.openCanvasEditor(cp);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		
		validateOrGenerateResults(ce, generateResults );
	}
	
}
