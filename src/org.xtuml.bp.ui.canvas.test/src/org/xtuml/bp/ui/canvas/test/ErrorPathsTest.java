package org.xtuml.bp.ui.canvas.test;
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
//
//This class is responsible for performing part of the automatic test
//of the Graphics Domain by forcing the canvas source through its error
//paths.
// 
import java.io.File;
import java.util.UUID;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

import junit.framework.TestCase;
@RunWith(OrderedRunner.class)
public class ErrorPathsTest extends TestCase {
  Ooaofooa modelRoot = BaseTest.getDefaultTestInstance(); 
  private static String m_logfile_path = "";
	
  public ErrorPathsTest() {
    super();
  }
  @Before
	public void setUp() throws Exception {
    super.setUp();
    if (m_logfile_path == null || m_logfile_path.equals(""))
    {
        m_logfile_path = System.getProperty("LOGFILE_PATH"); //$NON-NLS-1$
    }
    assertNotNull( m_logfile_path );
  }
  private boolean logFilePresent()
  {
	  IPath in_path = new Path(m_logfile_path);
	  File in_fh = in_path.toFile();
	  boolean ret_val = in_fh.exists();
	  if ( ret_val )
	  {
	  	in_fh.delete();
	  }
	  return ret_val;
  }
  
  /**
   * The following tests are disabled until we remove the comment in 
   * 
   * Cl_c.findMethod() -
   * 		// TODO: ClientNotFound operations are ignored right now because
   * 		//                 OAL added in operations made inside GD_ARS.Reconcile are being made
   * @throws Exception
   */
//  @Test
//	public void testGetConnectorText() throws Exception {
//    // Method not implemented
//    String text = Cl_c.Getconnectortext(0, IdAssigner.NULL_UUID, false, new Object(), IdAssigner.NULL_UUID);
//    assertEquals("", text);
//    assertTrue("Log file is not present", logFilePresent());
//    // A class created by method is private
//    text = Cl_c.Getconnectortext(0, IdAssigner.NULL_UUID, false, new Association_c(modelRoot) {
//      public String Get_connector_text(UUID ooa_id, boolean ooa_type, int At, UUID parent_id) {
//        try {
//          Object testItem = Class.forName("org.xtuml.bp.core.EV_ACCESS_PATH");
//        }
//        catch (ClassNotFoundException e) {
//          // Should find it OK
//        }
//        return "";
//      }}, IdAssigner.NULL_UUID);
//    assertEquals("", text);
//    assertTrue("Log file is not present", logFilePresent());
//    // Method throws an exception
//    Tester target = new Tester(); 
//    text = Cl_c.Getconnectortext(0, IdAssigner.NULL_UUID, false, target, IdAssigner.NULL_UUID);
//    assertEquals("", text);
//    assertTrue("Log file is not present", logFilePresent());
//  }
//  @Test
//	public void testGetCompartmentText() throws Exception {
//    // Method not implemented
//    String text = Cl_c.Getcompartmenttext(0, 0, 0, new Object());
//    assertEquals("", text);
//    assertTrue("Log file is not present", logFilePresent());
//    // A class created by method is private
//    text = Cl_c.Getcompartmenttext(0, 0, 0, new Association_c(modelRoot) {
//      public String Get_compartment_text(int comp_id, int entry_id, int At) {
//        try {
//          Object testItem = Class.forName("org.xtuml.bp.core.EV_ACCESS_PATH");
//        }
//        catch (ClassNotFoundException e) {
//          // Should find it OK
//        }
//        return "";
//      }});
//    assertEquals("", text);
//    assertTrue("Log file is not present", logFilePresent());
//    // Method throws an exception
//    Tester target = new Tester(); 
//    text = Cl_c.Getcompartmenttext(0, 0, 0, target);
//    assertEquals("", text);
//    assertTrue("Log file is not present", logFilePresent());
//  }
//  @Test
//	public void testGetCompartments() throws Exception {
//    int testVal = -1;
//    testVal = Cl_c.Getcompartments(new Object());
//    assertEquals(0, testVal);
//    assertTrue("Log file is not present", logFilePresent());
//    // A class created by method is private
//    testVal = Cl_c.Getcompartments(new Association_c(modelRoot) {
//      public String Get_compartments() {
//        try {
//          Object testItem = Class.forName("org.xtuml.bp.core.EV_ACCESS_PATH");
//        }
//        catch (ClassNotFoundException e) {
//          // Should find it OK
//        }
//        return "";
//      }});
//    assertEquals(0, testVal);
//    assertTrue("Log file is not present", logFilePresent());
//    // Method throws an exception
//    Tester target = new Tester(); 
//    testVal = Cl_c.Getcompartments(target);
//    assertEquals(0, testVal);
//    assertTrue("Log file is not present", logFilePresent());
//  }
//  @Test
//	public void testGetConnectorStyle() throws Exception {
//    int testVal = -1;
//    testVal = Cl_c.Getconnectorstyle(0, new Object());
//    assertEquals(0, testVal);
//    assertTrue("Log file is not present", logFilePresent());
//    // A class created by method is private
//    testVal = Cl_c.Getconnectorstyle(0, new Association_c(modelRoot) {
//      public int Get_style(int At) {
//        try {
//          Object testItem = Class.forName("org.xtuml.bp.core.EV_ACCESS_PATH");
//        }
//        catch (ClassNotFoundException e) {
//          // Should find it OK
//        }
//        return 0;
//      }});
//    assertEquals(0, testVal);
//    assertTrue("Log file is not present", logFilePresent());
//    // Method throws an exception
//    Tester target = new Tester(); 
//    testVal = Cl_c.Getconnectorstyle(0, target);
//    assertEquals(0, testVal);
//    assertTrue("Log file is not present", logFilePresent());
//  }
	@Test
	public void testGetOOA_IDfromInstance() throws Exception {
    ExternalEntity_c ee = new ExternalEntity_c(modelRoot);
    UUID ee_id = Cl_c.Getooa_idfrominstance(ee);
	  assertTrue( !ee_id.equals(IdAssigner.MAX_UUID) );
	ModelClass_c obj = new ModelClass_c(modelRoot); 
	UUID obj_id = Cl_c.Getooa_idfrominstance(obj);
	  assertTrue( !obj_id.equals(IdAssigner.MAX_UUID) );
    ImportedClass_c iobj = new ImportedClass_c(modelRoot);
    UUID iobj_id = Cl_c.Getooa_idfrominstance(iobj);
	  assertTrue( !iobj_id.equals(IdAssigner.MAX_UUID) );
    Association_c rel = new Association_c(modelRoot);
    UUID rel_id = Cl_c.Getooa_idfrominstance(rel);
	  assertTrue( !rel_id.equals(IdAssigner.MAX_UUID) );
    StateMachineState_c sms = new StateMachineState_c(modelRoot);
    UUID sm_id = Cl_c.Getooa_idfrominstance(sms);
	  assertTrue( !sm_id.equals(IdAssigner.MAX_UUID) );
    Transition_c tr = new Transition_c(modelRoot);
    UUID tr_id = Cl_c.Getooa_idfrominstance(tr);
	  assertTrue( !tr_id.equals(IdAssigner.MAX_UUID) );
    InstanceStateMachine_c ism = new InstanceStateMachine_c(modelRoot);
    UUID ism_id = Cl_c.Getooa_idfrominstance(ism);
	  assertTrue( !ism_id.equals(IdAssigner.MAX_UUID) );
  }
}