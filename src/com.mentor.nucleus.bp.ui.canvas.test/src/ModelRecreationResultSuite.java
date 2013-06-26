//=====================================================================
//
//File:      $RCSfile: ModelRecreationResultSuite.java,v $
//Version:   $Revision: 1.8 $
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

import com.mentor.nucleus.bp.ui.canvas.test.ModelRecreationTests;

public class ModelRecreationResultSuite extends TestSuite {

	public static Test suite() {
        return new ModelRecreationResultSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public ModelRecreationResultSuite() {
    	ModelRecreationTests.generateResults = true;
    	ModelRecreationTestSuite.initializeRecreationSuite(this);
    }
}
