//========================================================================
//
//File:      $RCSfile: NewCBTestWizard2.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2011/05/30 20:29:50 $
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
package com.mentor.nucleus.internal.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

public class NewCBTestWizard2 extends Wizard implements IWorkbenchWizard {

  private class TestPage extends WizardPage {
    
    public TestPage() {
      super("TestPage");
      setPageComplete(false);
    }
    public void createControl(Composite comp) {
      Composite composite = new Composite(comp, SWT.NULL);
      composite.setFont(comp.getFont());
      composite.addMouseTrackListener(new MouseTrackListener() {
        public void mouseEnter(MouseEvent e) {
          setPageComplete(true);
        }
        public void mouseHover(MouseEvent e) {
          
        }
        public void mouseExit(MouseEvent e) {
        }
        
      });
      setControl(composite);
    }
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    // do nothing for this test
  }
  public void addPages() { 
    IWizardPage testPage1 = new TestPage();
    testPage1.setTitle("Code Builder 2 TestPage 1");
    testPage1.setDescription("Code Builder 2 Test Description 1");
    this.addPage(testPage1);
    IWizardPage testPage2 = new TestPage();
    testPage2.setTitle("Code Builder 2 TestPage 2");
    testPage2.setDescription("Code Builder 2 Test Description 2");
    this.addPage(testPage2);
  }
  /* (non-Javadoc)
   * @see com.mentor.nucleus.bp.core.ui.DelegatingWizard#getExtensionPoint()
   */
  public String getExtensionPoint() {
    return "";
  }

  public static IWizard getWizard() {
    return new NewCBTestWizard2();
  }

  public static IWizard getWizard( Object arguments ) {
	    return new NewCBTestWizard2();
	  }

  private static final String CB_ID = "com.mentor.nucleus.internal.test.NewMCTestWizard2.launch"; //$NON-NLS-1$
  private static final String CUST_BUILDER_ID = "org.eclipse.ui.externaltools.ExternalToolBuilder"; //$NON-NLS-1$
  private static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$
  
  private static final String launchFileContents [] = {
  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
  		"<launchConfiguration type=\"org.eclipse.ui.externaltools.ProgramBuilderLaunchConfigurationType\"> ",
		   "<booleanAttribute key=\"org.eclipse.ui.externaltools.ATTR_BUILDER_ENABLED\" value=\"true\"/> ",
		   "<booleanAttribute key=\"org.eclipse.debug.ui.ATTR_LAUNCH_IN_BACKGROUND\" value=\"true\"/> ",
		   "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_LOCATION\" value=\"c:\\cygwin\\bin\\pdksh.exe\"/> ",
		"</launchConfiguration>"
  };
  
  public boolean performFinish(IProject newProject) {
    System.out.println("CBTestWizard 2 Finishing project: " + newProject.getName());
	IPath dest_path = newProject.getLocation().append(EXTERNALTOOLBUILDER_FOLDER).append(CB_ID);
	try {
		FileOutputStream fos = new FileOutputStream(dest_path.toFile());
        BufferedWriter fos_wr =
            new BufferedWriter(new OutputStreamWriter(fos));
		
		for ( int i = 0; i < launchFileContents.length; ++i )
		{
	        fos_wr.write(launchFileContents[i]);
	        fos_wr.newLine();
		}
		fos_wr.close();
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
	try {
		addBuilderToBuildSpec(newProject, CB_ID );
	} catch (CoreException e1) {
		e1.printStackTrace();
		return false;
	}
    return true;
  }
  public boolean performFinish() {
   return true; 
  }

	static public void addBuilderToBuildSpec(IProject project, String launchId) throws CoreException {

		// Get project description and then the associated build commands
		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();


		ICommand custCommand = desc.newCommand();
		custCommand.setBuilderName(CUST_BUILDER_ID);
		// Create map with arguments specific to builder in project here
		// custCommand.setArguments(Map args);
		final Map buildsetting;
		buildsetting = new HashMap();
		buildsetting
				.put(
						"LaunchConfigHandle", "<project>/" + EXTERNALTOOLBUILDER_FOLDER + "/" + launchId); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

		custCommand.setArguments(buildsetting);

		ICommand[] newCommands = new ICommand[commands.length + 1];

		// Add it before other builders.
		System.arraycopy(commands, 0, newCommands, 1, commands.length);

		newCommands[0] = custCommand;
		desc.setBuildSpec(newCommands);
		project.setDescription(desc, null);

	}
  
}
