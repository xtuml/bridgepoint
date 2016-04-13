package org.xtuml.bp.io.mdl.test;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author campbell
 *
 * This class is useful for testing a single test,
 * before integrating it into the CanvasTest class.
 * 
 */
public class IOMdlResultCreator extends TestCase {

	@Test
	public void testCreateTestResults() throws Exception{
		
	try {
	
	IOMdlUnicodeTestGenerics ut = new IOMdlUnicodeTestGenerics();
    ut.setUp();
    ut.setGenerateResults();
	  System.out.println("IOMdlTestResultCreator: Test results created OK");
	}
	catch (Exception e) {
	  System.err.println("Exception encountered by test result creator: " + e);
	  throw e;
	}
    
	}
}
