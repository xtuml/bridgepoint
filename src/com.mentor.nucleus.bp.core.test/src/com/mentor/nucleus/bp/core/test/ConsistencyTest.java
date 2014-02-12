//========================================================================
//
//File:      $RCSfile: ConsistencyTest.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/05/10 04:30:26 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.EclipseOoaofooa;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.test.consistency.Consistency;
import com.mentor.nucleus.bp.core.test.consistency.Identifiertest_c;
import com.mentor.nucleus.bp.core.test.consistency.Linkleftside_c;
import com.mentor.nucleus.bp.core.test.consistency.Linkrightside_c;
import com.mentor.nucleus.bp.core.test.consistency.Linktest_c;
import com.mentor.nucleus.bp.core.test.consistency.Singleformalizertest_c;
import com.mentor.nucleus.bp.core.test.consistency.Singleparticipanttest_c;
import com.mentor.nucleus.bp.core.test.consistency.Subtypeleft_c;
import com.mentor.nucleus.bp.core.test.consistency.Subtyperight_c;
import com.mentor.nucleus.bp.core.test.consistency.Supertypetest_c;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class ConsistencyTest extends CoreTest {
	
    private static Consistency cst = Consistency.getDefaultInstance();

    // A primary identifier check
	public void testPrimaryID()throws Exception {
		
	    initialize("blankTest"); //$NON-NLS-1$
		
		Identifiertest_c idt1 = new Identifiertest_c(cst,1,1,1);
		Identifiertest_c idt2 = new Identifiertest_c(cst,1,2,2);
		if ( idt1.checkConsistency() == true){
	    	clear_log();
			fail ("Primary Identifier check failed"); //$NON-NLS-1$
		}
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());

	    read_error_log("!MESSAGE Consistency: Object: identifierTest: Cardinality of an identifier is greater than 1. Actual Value: 2 primaryID: 1",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();
	    
	}
	// A secondary identifier check
	public void testSecondaryID()throws Exception {
		
	    initialize("blankTest"); //$NON-NLS-1$
		
		Identifiertest_c idt1 = new Identifiertest_c(cst,1,1,1);
		Identifiertest_c idt2 = new Identifiertest_c(cst,2,1,2);
		if ( idt1.checkConsistency() == true){
	    	clear_log();
			fail ("Secondary Identifier check failed"); //$NON-NLS-1$
		}
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());

	    read_error_log("!MESSAGE Consistency: Object: identifierTest: Cardinality of an identifier is greater than 1. Actual Value: 2 secondaryID: 1",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();
	    
	}
	// A tertiary identifier check
	public void testTertiaryID()throws Exception {
		
	    initialize("blankTest"); //$NON-NLS-1$
		
		Identifiertest_c idt1 = new Identifiertest_c(cst, 1,1,1);
		Identifiertest_c idt2 = new Identifiertest_c(cst, 2,2,1);
		if ( idt1.checkConsistency() == true){
	    	clear_log();
			fail ("Tertiary Identifier check failed"); //$NON-NLS-1$
		}
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());

	    read_error_log("!MESSAGE Consistency: Object: identifierTest: Cardinality of an identifier is greater than 1. Actual Value: 2 tertiaryID: 1",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();

	}
	// A test of an unconditional mutiplicity one participant
	public void testUnconSinglePart()throws Exception {
	
	    initialize("blankTest"); //$NON-NLS-1$
	    
	    Singleparticipanttest_c spt1 = new Singleparticipanttest_c(cst,1);
	    Singleformalizertest_c sft = new Singleformalizertest_c(cst);
	    sft.setSftid(1);
	    sft.relateAcrossR1To(spt1);
	    Singleparticipanttest_c spt2 = new Singleparticipanttest_c(cst,1);
	    if (sft.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single Participant check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: singleFormalizerTest: Association: 1: Cardinality of a participant is not equal to 1. Actual Value: 2 sptID: 1",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();
	}
	public void testZeroPart() throws Exception {
	    initialize("blankTest"); //$NON-NLS-1$
	    
	    Singleformalizertest_c sft = new Singleformalizertest_c(cst);
	    if (sft.checkConsistency() == true){
	    	clear_log();
	    	fail ("Zero Participant check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: singleFormalizerTest: Association: 1: Cardinality of a participant is not equal to 1. Actual Value: 0 sptID: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();
	
	}
	// A test of an unconditional multiplicity one formalizer
	public void testUnconSingleForm()throws Exception {

		initialize("blankTest"); //$NON-NLS-1$
	    
	    Singleformalizertest_c sft1 = new Singleformalizertest_c(cst);
	    sft1.setSftid(1);
	    Singleparticipanttest_c spt = new Singleparticipanttest_c(cst,1);
	    sft1.relateAcrossR1To(spt);
	    Singleformalizertest_c sft2 = new Singleformalizertest_c(cst);
	    sft2.setSftid(1);
	    sft2.relateAcrossR1To(spt);
	    if (spt.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single Formalizer check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: singleParticipantTest: Association: 1: Cardinality of a formalizer is not equal to one. Actual Value: 2",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();
	}
	public void testZeroFormalizer() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
	    
	    Singleparticipanttest_c spt = new Singleparticipanttest_c(cst,1);
	    if (spt.checkConsistency() == true){
	    	clear_log();
	    	fail ("Zero Formalizer check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: singleParticipantTest: Association: 1: Cardinality of a formalizer is not equal to one. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
	    clear_log();		
	}
	public void testMultSubtype()throws Exception {

		initialize("blankTest"); //$NON-NLS-1$
	    
	    Supertypetest_c supt = new Supertypetest_c(cst,1);
	    Subtypeleft_c subtl = new Subtypeleft_c(cst);
	    subtl.relateAcrossR3To(supt);
	    Subtyperight_c subtr1 = new Subtyperight_c(cst);
	    subtr1.relateAcrossR3To(supt);
	    if (supt.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single Subtype check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: supertypeTest: Association: 3: Cardinality of subtype is not equal to 1. Actual Value: 2",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();
	}
	public void testZeroSubtype() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
		Supertypetest_c supt = new Supertypetest_c(cst,1);
		if (supt.checkConsistency() == true){
			clear_log();
			fail ("Zero Subtype check failed"); //$NON-NLS-1$
		}
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: supertypeTest: Association: 3: Cardinality of subtype is not equal to 1. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();	
	}
	public void testMultSupertype() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
	    
	    Supertypetest_c supt1 = new Supertypetest_c(cst,1);
	    Subtypeleft_c subt = new Subtypeleft_c(cst);
	    subt.relateAcrossR3To(supt1);
	    Supertypetest_c supt2 = new Supertypetest_c(cst,1);
	    subt.relateAcrossR3To(supt2);
	    
	    if (subt.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single Supertype check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: subtypeLeft: Association: 3: Cardinality of a supertype is not equal to 1. Actual Value: 2",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();		
	}
	public void testZeroSupertype() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
		Subtyperight_c subt = new Subtyperight_c(cst,1);
		if (subt.checkConsistency() == true){
			clear_log();
			fail ("Zero Supertype check failed"); //$NON-NLS-1$
		}
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: subtypeRight: Association: 3: Cardinality of a supertype is not equal to 1. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();			
	}
	public void testMultLink() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
	    Linktest_c lnk1 = new Linktest_c(cst);
	    Linkleftside_c lsid = new Linkleftside_c(cst);
	    lnk1.relateAcrossR2To(lsid);
	    Linkrightside_c rsid = new Linkrightside_c(cst);
	    lnk1.relateAcrossR2To(rsid);
	    Linktest_c lnk2 = new Linktest_c(cst);
	    lnk2.relateAcrossR2To(lsid);	    
	    if (lsid.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single Link check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: linkLeftSide: Association: 2: Cardinality of a link is not equal to 1. Actual Value: 2",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();			
	}
	public void testZeroLink() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
		Linkleftside_c lsid = new Linkleftside_c(cst);
		
	    if (lsid.checkConsistency() == true){
	    	clear_log();
	    	fail ("Zero Link check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: linkLeftSide: Association: 2: Cardinality of a link is equal to zero. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency: Object: linkLeftSide: Association: 2: Cardinality of a link is not equal to 1. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",2); //$NON-NLS-1$
		clear_log();			

	}
	public void testMultOneSide() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
		Linktest_c lnk = new Linktest_c(cst);
	    Linkleftside_c lsid1 = new Linkleftside_c(cst);
	    Linkrightside_c rsid = new Linkrightside_c(cst);
	    
	    lnk.relateAcrossR2To(lsid1);
	    lnk.relateAcrossR2To(rsid);

	    Linkleftside_c lsid2 = new Linkleftside_c(cst);
	    
	    if (lnk.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single One Side check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: linkTest: Association: 2: Cardinality of one side of link is not equal to 1. Actual Value: 2",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();				
	}
	public void testZeroOneSide() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
		Linktest_c lnk = new Linktest_c(cst);
	    Linkrightside_c rsid1 = new Linkrightside_c(cst);
	    
	    lnk.relateAcrossR2To(rsid1);
	    
	    if (lnk.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single One Side check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: linkTest: Association: 2: Cardinality of one side of link is not equal to 1. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();						
	}
	public void testMultOtherSide() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
	    Linktest_c lnk = new Linktest_c(cst);
	    Linkleftside_c lsid = new Linkleftside_c(cst);
	    lnk.relateAcrossR2To(lsid);
	    Linkrightside_c rsid1 = new Linkrightside_c(cst);
	    lnk.relateAcrossR2To(rsid1);
	    Linkrightside_c rsid2 = new Linkrightside_c(cst);
	    lnk.relateAcrossR2To(rsid2);
	    if (lnk.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single Other Side check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: linkTest: Association: 2: Cardinality of other side of link is not equal to 1. Actual Value: 2",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();		
	}
	public void testZeroOtherSide() throws Exception {
		initialize("blankTest"); //$NON-NLS-1$
		
		Linktest_c lnk = new Linktest_c(cst);
	    Linkleftside_c lsid1 = new Linkleftside_c(cst);
	    
	    lnk.relateAcrossR2To(lsid1);
	    
	    if (lnk.checkConsistency() == true){
	    	clear_log();
	    	fail ("Single One Side check failed"); //$NON-NLS-1$
	    }
	    modelRoot.checkDomainConsistency();
	    OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
	    	    
	    read_error_log("!MESSAGE Consistency: Object: linkTest: Association: 2: Cardinality of other side of link is not equal to 1. Actual Value: 0",1); //$NON-NLS-1$
	    read_error_log("!MESSAGE Consistency:",1); //$NON-NLS-1$
		clear_log();						
	}

	protected void read_error_log(String consistencyMessage, int consistencyCount) throws Exception {

      IPath in_path = new Path(m_logfile_path);
      File in_fh = in_path.toFile();
      if ( !in_fh.exists() )
      {
          fail( ".log file not created"); //$NON-NLS-1$
          return;
      }

      int counter = 0;
      FileInputStream in = new FileInputStream(in_fh);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      while (true) {
          // get the next line of text from the file
          String line = reader.readLine();
            
          // if we've hit the end of the file, we're done
          if (line == null) break;
            
          if (line.indexOf(consistencyMessage)!= -1){
          	counter++;
          }
      }
      reader.close();
      if (counter != consistencyCount){
      	String message = "Consistency count incorrect: " + Integer.toString(counter)+ ", should be: " + Integer.toString(consistencyCount); //$NON-NLS-1$ //$NON-NLS-2$
  	  fail(message);
      }
    }


	protected void clear_log() throws Exception {
		Ooaofooa.getDefaultInstance().clearDatabase(new NullProgressMonitor());
		SystemModel_c.clearInstances(Ooaofooa.getDefaultInstance());
		modelRoot.clearDatabase(new NullProgressMonitor());
		Consistency.getDefaultInstance().clearDatabase(new NullProgressMonitor());

        IPath in_path = new Path(m_logfile_path);
        File in_fh = in_path.toFile();
        if ( !in_fh.delete() )
        {
            fail( ".log file not cleared"); //$NON-NLS-1$
        }
		
	}
	protected void setUp() throws Exception {
		super.setUp();
		EclipseOoaofooa.setPersistEnabled(true);
	}
	protected void tearDown() throws Exception {
    // CoreTest expects the log to be empty
	// overriding tearDown so that it does not check
    }	
	
    protected void initialize(String name) throws Exception {
    	switchPerspective("com.mentor.nucleus.bp.core.perspective");
    	if(!initialized){
            setupProject(name);

            TestingUtilities.importTestingProjectIntoWorkspace(name);
            m_sys = getSystemModel(name);
            modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(name, name, true));
            initialized = true;
        }
    }

	
}
