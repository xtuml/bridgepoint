.//====================================================================
.//
.// File:      $RCSfile: gen_test_skel.arc,v $
.// Version:   $Revision: 1.11 $
.// Modified:  $Date: 2013/01/10 22:44:11 $
.//
.// (c) Copyright 2004-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  Purpose: This archetype is used to generate the JUnit test
.//  skeleton to be performed on any given model.
.//
.//====================================================================
.//
.assign test_num = 0
.select many sms from instances of SM_SM
package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File:      $$RCSfile: gen_test_skel.arc,v $$
//Version:   $$Revision: 1.11 $$
//Modified:  $$Date: 2013/01/10 22:44:11 $$
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
//
//This class is responsible for performing the automatic test of the
//Graphics Domain by showing the ODMS domain canvases. The test data
//is created by the CanvasTestResultCreator class defined elsewhere
//in this package. The test creator class also creates a JPEG for
//each canvas so that the sample results may be viewed by a human. 
//This class also tests that the Graphics Domain is capable of showing
//canvases designed for display as opposed to for hard copy. It does
//this by setting the isHardCopy argument to Model_c.Draw to false.
//The consequence of this is that the background is painted in the
//selected color for the canvas type and selected symbols are shown
//with a bold outline and with selection handles.
// 
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.ActivePoller_c;

public class CanvasSCTest extends CanvasTest {
  public CanvasSCTest(String arg0) throws Exception {
    super(arg0);
  }
  private static boolean initialized = false;
  private static boolean generateResults = true;
  private static String test_id = "";
  public void setUp()throws Exception {
    setTestName("ODMS");
    setResultName(getTestName());
    super.setUp();
    if (!initialized){
      doLoadSql(getSQLFilename());
      initialized = true;
    }
  }
  public void doLoadSQL() {
    super.doLoadSql("./sql/odms.sql");
  }
  public String getResultName() {
    return super.getResultName() + test_id;
  }
  public void setGenerateResults () {
  try {
    CanvasSCTest csct = new CanvasSCTest("Canvas State Chart Test");
    generateResults = true;
    csct.setUp();
.for each sm in sms
  .select one class related by sm->SM_ISM[R517]->O_OBJ[R518]
  .if (empty class)
  	.select one class related by sm->SM_ASM[R517]->O_OBJ[R519]
  .end if
  .select many nsts related by sm->SM_TXN[R505]->SM_NSTXN[R507]
 .if (not_empty nsts)
   .select many states related by sm->SM_STATE[R501]
   .for each state in states
    .select many semes related by state->SM_SEME[R503]
      .for each seme in semes
        .select one nst related by seme->SM_NSTXN[R504]
        .if (not_empty nst)
          .select one new_state related by nst->SM_TXN[R507]->SM_STATE[R506]
          .select one evt related by seme->SM_SEVT[R503]->SM_EVT[R525]
          .assign test_num = test_num + 1
	  csct.test_${test_num}$U{class.Key_Lett}${evt.Numb}In$Cr{state.Name}();	
   		.end if
      .end for      
   .end for
 .end if
.end for
}
  catch (Exception e) {
      System.out.println("Exception encountered by test result creator: " + e);
  }
}
.assign test_num = 0
.for each sm in sms
  .select one class related by sm->SM_ISM[R517]->O_OBJ[R518]
  .if (empty class)
  	.select one class related by sm->SM_ASM[R517]->O_OBJ[R519]
  .end if
  .select many nsts related by sm->SM_TXN[R505]->SM_NSTXN[R507]
 .if (not_empty nsts)
   .select many states related by sm->SM_STATE[R501]
   .for each state in states
    .select many semes related by state->SM_SEME[R503]
      .for each seme in semes
        .select one nst related by seme->SM_NSTXN[R504]
        .if (not_empty nst)
          .select one new_state related by nst->SM_TXN[R507]->SM_STATE[R506]
          .select one evt related by seme->SM_SEVT[R503]->SM_EVT[R525]
          .assign test_num = test_num + 1
public void test_${test_num}$U{class.Key_Lett}${evt.Numb}In$Cr{state.Name}() throws Exception {
	  test_id = "test_${test_num}";
      Model_c uut = getUUT(1048578, 5);
      if (uut != null) {
      	try {
      		drawDiagram(uut, true, false, false);
      		/*
      		Insert events here
      		*/      
      		ActivePoller_c.oneShot();
      		assertTrue("Expected State: " + Model_c.ST_MODEL_$U_{new_state.Name}
        		                                                  + " got " + uut.get_current_state(),
            		                                              uut.get_current_state() == Model_c.ST_MODEL_$U_{new_state.Name});
      			if (generateResults) { 
      				CanvasTestResult result = drawDiagram(uut, true, false, false);  
      				try {
  		      		    	writeResults(result.transcript, uut, result.image);        
      				}
      				catch (Exception e) {
        			     	System.err.println("Exception encountered while writing results file " + e);
      				}
      			}
      			else {
      				doTestDiagram(1048578, 5, true, false);
      			}
      }	
				finally {
     				// Clean up code
     		}
      }	
      else {
         fail("Diagram not found");
      }
}      
		.end if
	  .end for      
   .end for
 .end if
.end for

}
.emit to file "../src/com/mentor/nucleus/bp/ui/canvas/test/CanvasSCTest.java"        