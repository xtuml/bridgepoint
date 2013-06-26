
//=====================================================================
//
//File:      $RCSfile: CanvasSuite1.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 05:47:48 $
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCreationTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasEditorReloadContentsTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasEditorTestSuite;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasInitialNameTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestSuite1;
import com.mentor.nucleus.bp.ui.canvas.test.CreationTransitionTest;
import com.mentor.nucleus.bp.ui.canvas.test.ErrorPathsTest;
import com.mentor.nucleus.bp.ui.canvas.test.ListenerTest;
import com.mentor.nucleus.bp.ui.canvas.test.ODMSTest;
import com.mentor.nucleus.bp.ui.canvas.test.ShapeResizeTest;
import com.mentor.nucleus.bp.ui.canvas.test.SymbolTest;

/**
 * Test all areas of the canvas
 */
public class CanvasSuite1 extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new CanvasSuite1();
	}
	
	/**
	 * Construct the test suite.
	 */
	public CanvasSuite1() {
		
		// turn off autobuild
		try {
			WorkspaceUtil.setAutobuilding(false);
		} catch (CoreException e) {
			Assert.fail(e.toString());
		}
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
		
		addTest(new TestSuite(CanvasTestSuite1.class));
		addTest(new TestSuite(ErrorPathsTest.class));
        addTest(new TestSuite(CanvasInitialNameTests.class));
		addTest(new TestSuite(SymbolTest.class));
		addTest(new TestSuite(ODMSTest.class));
        addTest(new TestSuite(CanvasEditorReloadContentsTest.class));
		addTest(new TestSuite(ListenerTest.class));
		addTest(new TestSuite(CanvasCreationTest.class));	
        addTest(new TestSuite(ShapeResizeTest.class));
		addTest(new CanvasEditorTestSuite());
		addTest(new TestSuite(CreationTransitionTest.class));
		//addTest(new TestSuite(I634OutlineViewEmptySelectionTest.class));
	}

}