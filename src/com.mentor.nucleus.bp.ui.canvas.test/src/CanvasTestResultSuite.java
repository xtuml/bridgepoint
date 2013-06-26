//=====================================================================
//
//File:      $RCSfile: CanvasTestResultSuite.java,v $
//Version:   $Revision: 1.16 $
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

import com.mentor.nucleus.bp.ui.canvas.test.AutoReconciliationTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCreationTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestResultCreator;
import com.mentor.nucleus.bp.ui.canvas.test.FreeFloatingConnectorTest;


public class CanvasTestResultSuite extends TestSuite {

    public static Test suite() {
        return new CanvasTestResultSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public CanvasTestResultSuite() {
      addTest(new TestSuite(CanvasTestResultCreator.class));
      CanvasCreationTest.generateResults = true;
      addTest(new TestSuite(CanvasCreationTest.class));	
      FreeFloatingConnectorTest.generateResults = true;
      addTest(new TestSuite(FreeFloatingConnectorTest.class));
      AutoReconciliationTests.generateResults = true;
      addTest(new TestSuite(AutoReconciliationTests.class));
    }
}
