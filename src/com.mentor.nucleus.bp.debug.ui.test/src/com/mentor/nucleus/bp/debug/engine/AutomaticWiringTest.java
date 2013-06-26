package com.mentor.nucleus.bp.debug.engine;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.debug.test.VerifierTest;
import com.mentor.nucleus.bp.test.TestUtil;


//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

public class AutomaticWiringTest extends VerifierTest {

	public AutomaticWiringTest() {
		super(new String[]{"Wiring","SAMP", "CMN"});
	}

	public void testWiringVerifyingDomains()throws InterruptedException {
	    redirectOutput("Wiring1");
	    setIsVerifyingAttribute(true);
	    setupWirings();
		executeModel();
		endRedirection();
		compareOutput("Wiring1");
	}
	
	public void testWiringNotVerifyingDomains()throws InterruptedException {
	    redirectOutput("Wiring2");
	    setIsVerifyingAttribute(false);
	    setupWirings();
		executeModel();
		endRedirection();
		compareOutput("Wiring2");
		TestUtil.deleteProject(project);
	}	
	
	public void tearDown() throws InterruptedException {
		super.tearDown();
	}
	
	private void setIsVerifyingAttribute(boolean verifying)
	{
		SystemModel_c[] sysmodels = SystemModel_c
		.SystemModelInstances(Ooaofooa.getDefaultInstance());

		Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(sysmodels);
		for (int i = 0; i < domains.length; i++)
		{
			ComponentInstance_c exEng = new ComponentInstance_c(domains[i].getModelRoot());
			exEng.Initializearchitecture();
		}
	}
	
	private void setupWirings()
	{
		
		SystemModel_c[] sysmodels = SystemModel_c
		.SystemModelInstances(Ooaofooa.getDefaultInstance());

		Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(sysmodels);		
		for (int i = 0; i < domains.length; i++)
		{
			domains[i].Removewirings();
			domains[i].Setupwirings();
		}	
	}
	
}
