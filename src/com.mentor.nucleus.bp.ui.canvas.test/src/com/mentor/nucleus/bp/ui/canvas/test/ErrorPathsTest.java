package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File:      $RCSfile: ErrorPathsTest.java,v $
//Version:   $Revision: 1.20 $
//Modified:  $Date: 2013/01/10 22:43:50 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
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

import junit.framework.TestCase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.AccessPath_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.CommunicationPath_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
public class ErrorPathsTest extends TestCase {
  Ooaofooa modelRoot = BaseTest.getDefaultTestInstance(); 
  private static String m_logfile_path = "";
	
  public ErrorPathsTest() {
    super();
  }
  public ErrorPathsTest(String arg0) {
    super(arg0);
  }
  protected void setUp() throws Exception {
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
  
  public void testGetConnectorText() throws Exception {
    // Method not implemented
    String text = Cl_c.Getconnectortext(0, IdAssigner.NULL_UUID, false, new Object(), IdAssigner.NULL_UUID);
    assertEquals("", text);
    assertTrue("Log file is not present", logFilePresent());
    // A class created by method is private
    text = Cl_c.Getconnectortext(0, IdAssigner.NULL_UUID, false, new Association_c(modelRoot) {
      public String Get_connector_text(UUID ooa_id, boolean ooa_type, int At, UUID parent_id) {
        try {
          Object testItem = Class.forName("com.mentor.nucleus.bp.core.EV_ACCESS_PATH");
        }
        catch (ClassNotFoundException e) {
          // Should find it OK
        }
        return "";
      }}, IdAssigner.NULL_UUID);
    assertEquals("", text);
    assertTrue("Log file is not present", logFilePresent());
    // Method throws an exception
    Tester target = new Tester(); 
    text = Cl_c.Getconnectortext(0, IdAssigner.NULL_UUID, false, target, IdAssigner.NULL_UUID);
    assertEquals("", text);
    assertTrue("Log file is not present", logFilePresent());
  }
  public void testGetCompartmentText() throws Exception {
    // Method not implemented
    String text = Cl_c.Getcompartmenttext(0, 0, 0, new Object());
    assertEquals("", text);
    assertTrue("Log file is not present", logFilePresent());
    // A class created by method is private
    text = Cl_c.Getcompartmenttext(0, 0, 0, new Association_c(modelRoot) {
      public String Get_compartment_text(int comp_id, int entry_id, int At) {
        try {
          Object testItem = Class.forName("com.mentor.nucleus.bp.core.EV_ACCESS_PATH");
        }
        catch (ClassNotFoundException e) {
          // Should find it OK
        }
        return "";
      }});
    assertEquals("", text);
    assertTrue("Log file is not present", logFilePresent());
    // Method throws an exception
    Tester target = new Tester(); 
    text = Cl_c.Getcompartmenttext(0, 0, 0, target);
    assertEquals("", text);
    assertTrue("Log file is not present", logFilePresent());
  }
  public void testGetCompartments() throws Exception {
    int testVal = -1;
    testVal = Cl_c.Getcompartments(new Object());
    assertEquals(0, testVal);
    assertTrue("Log file is not present", logFilePresent());
    // A class created by method is private
    testVal = Cl_c.Getcompartments(new Association_c(modelRoot) {
      public String Get_compartments() {
        try {
          Object testItem = Class.forName("com.mentor.nucleus.bp.core.EV_ACCESS_PATH");
        }
        catch (ClassNotFoundException e) {
          // Should find it OK
        }
        return "";
      }});
    assertEquals(0, testVal);
    assertTrue("Log file is not present", logFilePresent());
    // Method throws an exception
    Tester target = new Tester(); 
    testVal = Cl_c.Getcompartments(target);
    assertEquals(0, testVal);
    assertTrue("Log file is not present", logFilePresent());
  }
  public void testGetConnectorStyle() throws Exception {
    int testVal = -1;
    testVal = Cl_c.Getconnectorstyle(0, new Object());
    assertEquals(0, testVal);
    assertTrue("Log file is not present", logFilePresent());
    // A class created by method is private
    testVal = Cl_c.Getconnectorstyle(0, new Association_c(modelRoot) {
      public int Get_style(int At) {
        try {
          Object testItem = Class.forName("com.mentor.nucleus.bp.core.EV_ACCESS_PATH");
        }
        catch (ClassNotFoundException e) {
          // Should find it OK
        }
        return 0;
      }});
    assertEquals(0, testVal);
    assertTrue("Log file is not present", logFilePresent());
    // Method throws an exception
    Tester target = new Tester(); 
    testVal = Cl_c.Getconnectorstyle(0, target);
    assertEquals(0, testVal);
    assertTrue("Log file is not present", logFilePresent());
  }
	public void testGetOOA_IDfromInstance() throws Exception {
    Domain_c dom = new Domain_c(modelRoot);
    UUID dom_id = Cl_c.Getooa_idfrominstance(dom);
	  assertTrue( !dom_id.equals(IdAssigner.MAX_UUID) );
    Subsystem_c ssys = new Subsystem_c(modelRoot);
    UUID ssys_id = Cl_c.Getooa_idfrominstance(ssys);
   assertTrue( !ssys_id.equals(IdAssigner.MAX_UUID) );
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
    CommunicationPath_c comP = new CommunicationPath_c(modelRoot);
    UUID cp_id = Cl_c.Getooa_idfrominstance(comP);
	  assertTrue( !cp_id.equals(IdAssigner.MAX_UUID) );
    AccessPath_c accP = new AccessPath_c(modelRoot);
    UUID ap_id = Cl_c.Getooa_idfrominstance(accP);
	  assertTrue( !ap_id.equals(IdAssigner.MAX_UUID) );
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