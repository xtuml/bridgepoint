//========================================================================
//
//File:      $RCSfile: CoreTestResultCreator.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/05/10 04:30:27 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.core.test;

import junit.framework.TestCase;

public class CoreTestResultCreator extends TestCase {

	public void testCreateTestResults() throws Exception {

		try {

			DeleteTestGenerics dt = new DeleteTestGenerics("Core Delete Test Result Creator");
			dt.setUp();
			dt.setGenerateResults();

			FormalizeUnformalizeTest fut = new FormalizeUnformalizeTest(
					"Core Formalize Unformalize Test Result Creator");
			fut.setUp();
			fut.setGenerateResults();

			AssignClassTestGenerics act = new AssignClassTestGenerics(
					"Core Assign Class Test Result Creator");
			act.setUp();
			act.setGenerateResults();

			AssignRemoveEventsGenerics are = new AssignRemoveEventsGenerics(
					"Core Assign Remove Events and Transitions");
			are.setUp();
			are.setGenerateResults();

			AttributeMenuItemTestGenerics ami = new AttributeMenuItemTestGenerics(
					"Attribute Menu Item Test Result Creator");
			ami.setUp();
			ami.setGenerateResults();

			CombineSplitReferentialsTestGenerics csr = new CombineSplitReferentialsTestGenerics(
					"Combine Split Referentials Test Result Creator");
			csr.setUp();
			csr.setGenerateResults();

			SubtypeSupertypeFormalizeTest ssf = new SubtypeSupertypeFormalizeTest(
					"Subtype Supertype Formalize Test Result Creator");
			ssf.setUp();
			ssf.setGenerateResults();

			ModelTransactionTestGenerics mtt = new ModelTransactionTestGenerics("test");
			mtt.setUp();

			SequenceTestsGenerics st = new SequenceTestsGenerics("Sequence Test Results");
			st.setGenerateResults();

			CommunicationLinkTestsGenerics clt = new CommunicationLinkTestsGenerics(
					"Communication Link Test Results");
			clt.setGenerateResults();

			CommunicationMessageTestsGenerics cmt = new CommunicationMessageTestsGenerics(
					"Communication Message Test Results");
			cmt.setGenerateResults();

			CommunicationTestsGenerics cts = new CommunicationTestsGenerics(
					"Communication Test Results");
			cts.setGenerateResults();

            ModifyNonFullyLoadedModelTestsGenerics mnflm = new ModifyNonFullyLoadedModelTestsGenerics(
                "Modify Non Fully Loaded Test Results");
            mnflm.setGenerateResults();
            
			System.out
					.println("CoreTestResultCreator: Test results created OK");
		} catch (Exception e) {
			System.err.println("Exception encountered by test result creator: "
					+ e);
			throw e;
		}

	}
}
