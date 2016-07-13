package org.xtuml.bp.debug.engine;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.debug.test.VerifierTest;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.OrderedRunner;


//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//========================================================================

@RunWith(OrderedRunner.class)
public class AutomaticWiringTest extends VerifierTest {

	public AutomaticWiringTest() {
		super(new String[]{"Wiring","SAMP", "CMN"});
	}

	@Test
	public void testWiringVerifyingDomains()throws InterruptedException {
	    redirectOutput("Wiring1");
		executeModel();
		endRedirection();
		compareOutput("Wiring1");
	}
	
	@Test
	public void testWiringNotVerifyingDomains()throws InterruptedException {
	    redirectOutput("Wiring2");
		executeModel();
		endRedirection();
		compareOutput("Wiring2");
		TestUtil.deleteProject(project);
	}	
	
	@After
	public void tearDown() throws InterruptedException {
		super.tearDown();
	}
	
}
