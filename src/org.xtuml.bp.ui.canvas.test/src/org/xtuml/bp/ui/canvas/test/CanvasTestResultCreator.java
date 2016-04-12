package org.xtuml.bp.ui.canvas.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.test.common.OrderedRunner;

import junit.framework.TestCase;

/**
 * @author campbell
 *
 * This class is useful for testing a single test,
 * before integrating it into the CanvasTest class.
 * 
 */
@RunWith(OrderedRunner.class)
public class CanvasTestResultCreator extends TestCase {

	@Test
	public void testCreateTestResults() throws Exception{
		
    try {
	
//      MultipleSupertypeTest mst = new MultipleSupertypeTest("Multiple Supertype Test Result Creator");
//      mst.setUp();
//      mst.setGenerateResults();
      
      ODMSTest odmst = new ODMSTest();
      odmst.setUp();
      odmst.setGenerateResults();
      
//      SymbolTest symt = new SymbolTest("Symbol Test Result Creator");
//      symt.setUp();
//      symt.createExpectedResults(true, false, true);
//      
//      CreationTransitionTest ctt = new CreationTransitionTest("Creation Transition Test Result Creator");
//      ctt.setUp();
//      ctt.setGenerateResults();
//      
//      ConnectorsAsAnchorsTest cat = new ConnectorsAsAnchorsTest("Connectors as Anchors Result Creator");
//      cat.setGenerateResults();
      
	  System.out.println("CanvasTestResultCreator: Test results created OK");
    }
    catch (Exception e) {
      System.err.println("Exception encountered by test result creator: " + e);
      throw e;
    }
    
	}
}
