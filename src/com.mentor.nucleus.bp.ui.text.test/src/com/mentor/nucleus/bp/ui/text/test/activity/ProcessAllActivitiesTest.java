//=====================================================================
//
//File:      $RCSfile: ProcessAllActivitiesTest.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 06:02:36 $
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

package com.mentor.nucleus.bp.ui.text.test.activity;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

public class ProcessAllActivitiesTest extends BaseTest {
	
	private static boolean firstSetup = true;
	private static String testModelName = "i592_ProcessAllActivitiesTest";
	
	public ProcessAllActivitiesTest(String name) throws Exception {
		super(null, name); //$NON-NLS-1$
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
        // make sure the user isn't prompted to do a parse all
        CorePlugin.enableParseAllOnResourceChange();

        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
	}
	
    boolean errorLogged = false;
    
	Throwable badLocEx= null;
	public void testProcessAllActivitiesNPE() throws Throwable{
        AllActivityModifier aam = new AllActivityModifier(Package_c.PackageInstance(modelRoot), new NullProgressMonitor());
		class UITextLogListener implements ILogListener{
			public void logging(IStatus status, String plugin) {
				if (status.getException() instanceof BadLocationException){
					badLocEx = status.getException();
				}
			}		
		}
        ILogListener ll = new UITextLogListener();
        Platform.addLogListener(ll);
		aam.processAllActivities(AllActivityModifier.PARSE);
		if (badLocEx != null){
			throw badLocEx;		
		}
        Platform.removeLogListener(ll);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
