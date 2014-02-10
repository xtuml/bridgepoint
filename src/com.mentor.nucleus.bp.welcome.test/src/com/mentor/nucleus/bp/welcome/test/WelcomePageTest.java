package com.mentor.nucleus.bp.welcome.test;
//=====================================================================
//
//File:      $RCSfile: WelcomePageTest.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/01/10 23:05:14 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
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

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.welcome.gettingstarted.GettingStartedLiveHelpAction;
import com.mentor.nucleus.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;

public class WelcomePageTest extends TestCase {

	private IProject project;

	private String[] expectedXtUMLFiles = {".externalToolBuilders/Model Compiler.launch",
	"models/MicrowaveOven/MicrowaveOven.xtuml"};

	private String[] expectedFiles =expectedXtUMLFiles;
	
	private String markingFolder = "gen/";

	private String[] MC3020Files = {
            markingFolder + "datatype.mark",
            markingFolder + "system.mark",
            markingFolder + "class.mark",
            markingFolder + "domain.mark"
            };
	
	public WelcomePageTest() {
		super();
	}

	public void runGettingStartedAction() {
		// create and run new instances of GettingStartedAction
		GettingStartedLiveHelpAction gsAction = new GettingStartedLiveHelpAction();
		gsAction.run();
	}

	public boolean projectExists(String projectName) {
		// Check that project exists in the workspace
		// and that it is indeed an xtUML project
		boolean projectExists = false;
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		projectExists = project.exists();
		assertTrue("Project: " + projectName + " does not exist.",
				projectExists);
		projectExists = project.isOpen();
		assertTrue("Project: " + projectName + " is not open.", projectExists);
		return projectExists;
	}

	public void isxtUMLProject(IProject project) {
		try {
			assertTrue("Project: " + project.getName()
					+ " is not an xtUML project.", project
					.hasNature(XtUMLNature.ID));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public void containsProjectMembers() {
		/*
		 * verify that the project contains all of the necessary xtUML, Edge,
		 * and 3020 files
		 */
		for (int i = 0; i < expectedFiles.length; i++) {
			IFile file = project.getFile(expectedFiles[i]);
			assertTrue("Expected file: " + file.getName() + " does not exist.",
					file.exists());
		}
		for (int i = 0; i < MC3020Files.length; i++) {
			IFile file = project.getFile(MC3020Files[i]);
			assertTrue("Expected file: " + file.getName() + " does not exist.",
					file.exists());
		}
	}

	public void verifyProjectCreated() {
		boolean projectExists = projectExists("MicrowaveOven");
		if (projectExists)
			containsProjectMembers();
	}

	public void testProjectCreation() {
		runGettingStartedAction();
		verifyProjectCreated();
	}
}
