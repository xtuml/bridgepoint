//=====================================================================
//
//File:      $RCSfile: CanvasCCPResultSuite.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:44:02 $
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.ui.canvas.test.CanvasCopyPasteTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCopyTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCreationTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCutTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasStateMachineCopyPasteTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestResultCreator;
import com.mentor.nucleus.bp.ui.canvas.test.ConnectorToolSCTest;
import com.mentor.nucleus.bp.ui.canvas.test.FreeFloatingConnectorTest;
import com.mentor.nucleus.bp.ui.canvas.test.ModelRecreationTests;
import com.mentor.nucleus.bp.ui.canvas.test.ModelToolSCTest;
import com.mentor.nucleus.bp.ui.canvas.test.ScrollToolSCTest;
import com.mentor.nucleus.bp.ui.canvas.test.SelectionToolSCTest;
import com.mentor.nucleus.bp.ui.canvas.test.ShapeToolSCTest;
import com.mentor.nucleus.bp.ui.canvas.test.StatechartTest;

public class CanvasCCPResultSuite extends TestSuite {

    public static Test suite() {
        return new CanvasCCPResultSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public CanvasCCPResultSuite() {
    	CanvasCutTests.generateResults = true;
    	addTestSuite(CanvasCutTests.class);
    	CanvasCopyTests.generateResults = true;
    	addTestSuite(CanvasCopyTests.class);
    	CanvasCopyPasteTests.generateResults = true;
    	addTestSuite(CanvasCopyPasteTests.class);
    	CanvasStateMachineCopyPasteTests.generateResults = true;
    	addTestSuite(CanvasStateMachineCopyPasteTests.class);
    	ModelRecreationTests.generateResults = true;
    	ModelRecreationResultSuite resultSuite = new ModelRecreationResultSuite();
    	addTest(resultSuite);
    }
}
