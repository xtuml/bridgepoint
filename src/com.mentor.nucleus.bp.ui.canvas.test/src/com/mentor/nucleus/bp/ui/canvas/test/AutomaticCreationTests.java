//=====================================================================
//
//File:      com.mentor.nucleus.bp.ui.canvas.test/src/com/mentor/nucleus/bp/ui/canvas/test/AutomaticCreationTests.java
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.Satisfaction_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class AutomaticCreationTests extends BaseTest {

	public void testProvidedInterfaceTool() {
		// create a new package and two new components
		Transaction transaction = startTransaction();
		m_sys.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		Package_c testPackage = pkgs[pkgs.length - 1];
		testPackage.Newcomponent();
		Component_c[] comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		final Component_c startComp = comps[comps.length - 1];
		testPackage.Newcomponent();
		comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		final Component_c endComp = comps[comps.length - 1];
		endTransaction(transaction);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		// select the provided interface tool and draw
		// between the two connectors
		CanvasTestUtilities.openDiagramEditor(testPackage);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		Model_c model = editor.getModel();
		GraphicalElement_c sourceEle = GraphicalElement_c.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((GraphicalElement_c) candidate).getRepresents() == startComp;
			}
		});
		GraphicalEditPart sourcePart = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(Shape_c
				.getOneGD_SHPOnR2(sourceEle));
		Rectangle bounds = sourcePart.getFigure().getBounds();
		int startCompX = bounds.getCenter().x;
		int startCompY = bounds.getCenter().y;
		GraphicalElement_c targetEle = GraphicalElement_c.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((GraphicalElement_c) candidate).getRepresents() == endComp;
			}
		});
		GraphicalEditPart targetPart = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(Shape_c
				.getOneGD_SHPOnR2(targetEle));
		bounds = targetPart.getFigure().getBounds();
		int endCompX = bounds.getCenter().x;
		int endCompY = bounds.getCenter().y;
		AbstractTool tool = UITestingUtilities.getTool("Components", "Provided Interface");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.getEditorPartFor(startComp);
		UITestingUtilities.doMousePress(startCompX, startCompY);
		UITestingUtilities.doMouseMove(endCompX, endCompY);
		UITestingUtilities.doMouseRelease(endCompX, endCompY);
		// verify that both a provided interface and
		// required interface are created and satisfied
		InterfaceReference_c[] references = InterfaceReference_c
				.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comps));
		assertTrue(
				"Automatic creation was not performed for the required interface.",
				references.length == 2);
		Satisfaction_c[] sats = Satisfaction_c.getManyC_SFsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		assertTrue("A satisfaction was not created.", sats.length == 1);
		// verify that the provided interface has the
		// first selected component as its source
		Provision_c pro = Provision_c.getOneC_POnR4009(InterfaceReference_c
				.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(startComp)));
		assertNotNull("The provision was not created on the starting component.", pro);
	}
	
	public void testRequiredInterfaceTool() {
		// create a new package and two new components
		Transaction transaction = startTransaction();
		m_sys.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		Package_c testPackage = pkgs[pkgs.length - 1];
		testPackage.Newcomponent();
		Component_c[] comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		final Component_c startComp = comps[comps.length - 1];
		testPackage.Newcomponent();
		comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		final Component_c endComp = comps[comps.length - 1];
		endTransaction(transaction);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		// select the required interface tool and draw
		// between the two connectors
		CanvasTestUtilities.openDiagramEditor(testPackage);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		Model_c model = editor.getModel();
		GraphicalElement_c sourceEle = GraphicalElement_c.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((GraphicalElement_c) candidate).getRepresents() == startComp;
			}
		});
		GraphicalEditPart sourcePart = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(Shape_c
				.getOneGD_SHPOnR2(sourceEle));
		Rectangle bounds = sourcePart.getFigure().getBounds();
		int startCompX = bounds.getCenter().x;
		int startCompY = bounds.getCenter().y;
		GraphicalElement_c targetEle = GraphicalElement_c.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((GraphicalElement_c) candidate).getRepresents() == endComp;
			}
		});
		GraphicalEditPart targetPart = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(Shape_c
				.getOneGD_SHPOnR2(targetEle));
		bounds = targetPart.getFigure().getBounds();
		int endCompX = bounds.getCenter().x;
		int endCompY = bounds.getCenter().y;
		AbstractTool tool = UITestingUtilities.getTool("Components", "Required Interface");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.getEditorPartFor(startComp);
		UITestingUtilities.doMousePress(startCompX, startCompY);
		UITestingUtilities.doMouseMove(endCompX, endCompY);
		UITestingUtilities.doMouseRelease(endCompX, endCompY);
		// verify that both a provided interface and
		// required interface are created and satisfied
		InterfaceReference_c[] references = InterfaceReference_c
				.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comps));
		assertTrue(
				"Automatic creation was not performed for the provided interface.",
				references.length == 2);
		Satisfaction_c[] sats = Satisfaction_c.getManyC_SFsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		assertTrue("A satisfaction was not created.", sats.length == 1);
		// verify that the provided interface has the
		// first selected component as its source
		Requirement_c req = Requirement_c.getOneC_ROnR4009(InterfaceReference_c
				.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(startComp)));
		assertNotNull("The requirement was not created on the starting component.", req);
	}
}
