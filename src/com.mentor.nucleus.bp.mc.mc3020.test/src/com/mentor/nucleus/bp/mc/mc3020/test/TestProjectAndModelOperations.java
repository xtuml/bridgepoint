//=====================================================================
//
//File:      $RCSfile: TestProjectAndModelOperations.java,v $
//Version:   $Revision: 1.21 $
//Modified:  $Date: 2013/01/10 23:22:08 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.mc.mc3020.test;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.NewDomainWizard;
import com.mentor.nucleus.bp.core.ui.NewSystemWizard;
import com.mentor.nucleus.bp.core.ui.WizardDelegateChooserPage;
import com.mentor.nucleus.bp.core.ui.WizardNewDomainCreationPage;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.mc.mc3020.MC3020Nature;

public class TestProjectAndModelOperations extends TestCase {

	public static boolean codeBuilderPresent = false;

	private static final String project1Name = "testProj1"; //$NON-NLS-1$
	private static final String project2Name = "testProj2"; //$NON-NLS-1$
	private static final String project3Name = "testProj3"; //$NON-NLS-1$
	private static final String model1Name = "test1"; //$NON-NLS-1$
	private static final String model2Name = "test2"; //$NON-NLS-1$
	private static final String model3Name = "test3"; //$NON-NLS-1$

	private static final String GEN_FOLDER = "gen"; //$NON-NLS-1$
	private static final String SRC_FOLDER = "src"; //$NON-NLS-1$
	
	private static final String systemMarkingFileNames [] = {
			"bridge.mark", //$NON-NLS-1$
			"datatype.mark", //$NON-NLS-1$
			"registry.mark", //$NON-NLS-1$
			"system.mark" //$NON-NLS-1$
	};
	private static final String modelMarkingFileNames [] = {
			"_domain.mark", //$NON-NLS-1$
			"_event.mark", //$NON-NLS-1$
			"_class.mark" //$NON-NLS-1$
	};
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		WorkspaceUtil.setAutobuilding(false);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		IPath logFile = workspacePath.append(".metadata").append(".log");  //$NON-NLS-1$  //$NON-NLS-2$
		assertFalse(".log file exists", logFile.toFile().exists());  //$NON-NLS-1$
		super.tearDown();
	}
	
	public void testCreateXtUMLProject()  throws Exception {
		createProject(project1Name);

		IProject project = getProject(project1Name);
		String args = getToolArgumentValue(project);
		assertNotNull( args );
		validateMC3020Project(project, args);
	}

	public void testAddXtUMLModel()  throws Exception {
		createModel(project1Name, model1Name);

		IProject project = getProject(project1Name);
		String args = getToolArgumentValue(project);
		assertNotNull( args );
		validateMC3020Project(project, args);
		validateDomainPresent(project, model1Name, args);
	}
	
	public void testMoveXtUMLModel()  throws Exception {
		IProject project1 = getProject(project1Name);
		IFile model_file = getModelFile(project1, model1Name);

		createProject(project2Name);

		IProject project2 = getProject(project2Name);
		IFolder dest_dir = validateMemberFolderExists(project2, Ooaofooa.MODELS_DIRNAME);

		IPath dest_path = dest_dir.getFullPath().append(model1Name + "." + Ooaofooa.MODELS_EXT);
		model_file.move(dest_path, true, null);
		
		waitForListeners();

		String args1 = getToolArgumentValue(project1);
		assertNotNull( args1 );
		validateMC3020Project(project1, args1);
		validateDomainNotPresent(project1, model1Name, args1, false);

		String args2 = getToolArgumentValue(project2);
		assertNotNull( args2 );
		validateMC3020Project(project2, args2);
		validateDomainPresent(project2, model1Name, args2);
		
	}
	
	public void testRenameXtUMLModel() throws Exception {
		IProject project = getProject(project2Name);
		IFile model_file = getModelFile(project, model1Name);
		
        IPath newPath = model_file.getFullPath().removeLastSegments(1).
			append(model2Name + "." + Ooaofooa.MODELS_EXT); //$NON-NLS-1$
        model_file.move(newPath, true, null);
		
		waitForListeners();
		
		String args = getToolArgumentValue(project);
		assertNotNull( args );
		validateMC3020Project(project, args);
		validateDomainNotPresent(project, model1Name, args, true);
		validateDomainPresent(project, model2Name, args);
	}
	
	public void testAddSecondXtUMLModel()  throws Exception {
		createModel(project2Name, model1Name);

		IProject project = getProject(project2Name);
		String args = getToolArgumentValue(project);
		assertNotNull( args );
		validateMC3020Project(project, args);
		validateDomainPresent(project, model1Name, args);
		validateDomainPresent(project, model2Name, args);
	}
	
	public void testDeleteXtUMLModel() throws Exception {
		IProject project = getProject(project2Name);
		IFile model_file = getModelFile(project, model2Name);		
		model_file.delete(true, null);
		
		waitForListeners();
		
		String args = getToolArgumentValue(project);
		assertNotNull( args );
		validateMC3020Project(project, args);
		validateDomainPresent(project, model1Name, args);
		validateDomainNotPresent(project, model2Name, args, false);
	}

	// at this point, project2/model1 is still there
	private static final String allowed_extensions [] = {
	  "user_file1.h", "user_file2.H", "user_file.hxx", "user_file.hpp", "user_file.hh", //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
	  "user_file1.c", "user_file2.C", "user_file.cxx", "user_file.cpp", "user_file.cc",  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
	  "Makefile.user" //$NON-NLS-1$
	};
	
	public void testAddAFile() throws Exception {
		IProject project = getProject(project2Name);
		IFolder gen_dir = validateMemberFolderExists(project, GEN_FOLDER);
		for ( int i = 0; i < allowed_extensions.length; ++i)
		{
			IPath dest_path = gen_dir.getLocation().append(allowed_extensions[i]);
			assertTrue(dest_path.toFile().createNewFile());
			gen_dir.refreshLocal(IResource.DEPTH_ONE, null);
			waitForListeners();
			
			String args = getToolArgumentValue(project);
			assertNotNull( args );
			IFile model_file = validateMemberFileExistsAndArgPresent(gen_dir, 
					args, allowed_extensions[i]);

		}
	}

  public void testRenameAFile() throws Exception {
    IProject project = getProject(project2Name);
    IFolder gen_dir = validateMemberFolderExists(project, GEN_FOLDER);
    for ( int i = 0; i < allowed_extensions.length; ++i)
    {
      IFile f = validateMemberFileExists(gen_dir, allowed_extensions[i]);
      IPath p = f.getFullPath().removeLastSegments(1).append("new_" + allowed_extensions[i]); //$NON-NLS-1$
      f.move(p, true, true, null);
      waitForListeners();
      
      String args = getToolArgumentValue(project);
      assertNotNull( args );
      validateMemberFileDoesNotExist(gen_dir, 
          args, allowed_extensions[i]); 
      validateMemberFileExistsAndArgPresent(gen_dir, 
          args, "new_" + allowed_extensions[i]);  //$NON-NLS-1$
    }
  }

	public void testRemoveAFile() throws Exception {
		IProject project = getProject(project2Name);
		IFolder gen_dir = validateMemberFolderExists(project, GEN_FOLDER);
		// do the deletions in reverse order
		for ( int i = allowed_extensions.length-1; i >= 0; --i)
		{
      IFile f = validateMemberFileExists(gen_dir, "new_" + allowed_extensions[i]);  //$NON-NLS-1$
      f.delete(true, true, null);
      waitForListeners();
      
      String args = getToolArgumentValue(project);
      assertNotNull( args );
      validateMemberFileDoesNotExist(gen_dir, 
          args, "new_" + allowed_extensions[i]);  //$NON-NLS-1$
		}
	}
	
	public void testBuildProject() throws Exception {
		final IProject project = getProject(project2Name);
        selectProject(project);

        project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		waitForListeners();
		
	}

	public void testDeleteLastXtUMLModel() throws Exception {
		IProject project = getProject(project2Name);
		IFile model_file = getModelFile(project, model1Name);		
		model_file.delete(true, null);
		
		waitForListeners();
		
		String args = getToolArgumentValue(project);
		assertNotNull( args );
		validateMC3020Project(project, args);
		validateDomainNotPresent(project, model1Name, args, false);
		validateDomainNotPresent(project, model2Name, args, false);
	}

	
	public void testMultiFileProject()  throws Exception {
		createProject(project3Name);
		createModel(project3Name, model3Name);
		final IProject project3 = getProject(project3Name);
		IFolder newFolder = project3.getFolder(Ooaofooa.MODELS_DIRNAME + "/" + model3Name);
		newFolder.create(true,true, null);
		waitForListeners();
		
		IFile model_file = getModelFile(project3, model3Name);
		IPath dest_path = newFolder.getFullPath().append(model3Name + "." + Ooaofooa.MODELS_EXT);
		model_file.move(dest_path, true, null);
		waitForListeners();

        selectProject(project3);
        project3.build(IncrementalProjectBuilder.FULL_BUILD, null);
        
		waitForListeners();		
	}

	private IProject getProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		assertTrue( project.exists() );
		return project;
	}

	private IFile getModelFile(IProject project, String name) {
		IFolder model_dir = validateMemberFolderExists(project, Ooaofooa.MODELS_DIRNAME);
		IFile model_file = validateMemberFileExistsAndArgPresent(model_dir, null, name + "." + Ooaofooa.MODELS_EXT); //$NON-NLS-1$

		return model_file;
	}

	private void waitForListeners() throws InterruptedException {
		// allow listeners to update
		Display d = Display.getCurrent();
		
		// 15 iterations seems to be enough time to get all the work done
		for ( int i = 0; i < 15; ++i )
		{
			while ( d.readAndDispatch() )
				/* do nothing */ ; 
			Thread.sleep(100);
		}
	}
	
	private void selectProject(final IProject project) {
		Runnable r = new Runnable() {
		public void run() {
			IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
			try {
				IViewPart part = page
					.showView("org.eclipse.ui.views.ResourceNavigator"); //$NON-NLS-1$
      			part.getSite()
					.getSelectionProvider().setSelection(
				     new StructuredSelection(project));
				} catch (PartInitException e) {
						fail("Failed to open navigator view"); //$NON-NLS-1$
               	}
            }
        };
        r.run();
	}

	private void createProject(String projectName) throws InterruptedException {
		NewSystemWizard nsw = new NewSystemWizard();
		nsw.init(PlatformUI.getWorkbench(), null);
		WizardNewProjectCreationPage wnpcp = (WizardNewProjectCreationPage) nsw
				.getStartingPage();
		wnpcp.setInitialProjectName(projectName);
		wnpcp.useDefaults();
		IWizardPage nextPage = wnpcp.getNextPage();
		if(nextPage != null) {
			if(nextPage.getTitle().equalsIgnoreCase("Multiple xtUML Model Compilers found")) {
				WizardDelegateChooserPage wdcp = (WizardDelegateChooserPage) nextPage;
				wdcp.createControl((Composite) new Shell());

				List modelCompilerList = wdcp.getModelCompilerList();
				String[] compiler = { "MC-3020 Model Compiler" };
				modelCompilerList.setSelection(compiler);
				String[] currentSelection = modelCompilerList.getSelection();
				
				assertFalse(currentSelection.length == 0);
				
				modelCompilerList.notifyListeners(SWT.Selection, null);

			}
		}
		nsw.performFinish();

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		assertTrue( project.exists() );
		assertTrue( project.isOpen() );
		
		waitForListeners();
		
	}

	private void createModel(final String projectName, String modelName) throws InterruptedException {
		SystemModel_c systemModel = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		
		NewDomainWizard ndw = new NewDomainWizard();
		ndw.init(PlatformUI.getWorkbench(),
				new StructuredSelection(systemModel));
		ndw.addPages();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), ndw);
		dialog.create();
		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
				.getStartingPage();
		wndcp.setDomainNameFieldValue(modelName);
		wndcp.setPageComplete(true);
		ndw.performFinish();

		waitForListeners();

	}
	
	private void validateMC3020Project(IProject project, String args) throws Exception {
		validateMemberFolderExists(project, SRC_FOLDER);
		IFolder gen_dir = validateMemberFolderExists(project, GEN_FOLDER);
		if ( codeBuilderPresent )
		{
			validateArgNotPresent(args, " -c ", "");//$NON-NLS-1$//$NON-NLS-2$
		}
		else
		{
			validateArgPresent(args, " -c ", "");//$NON-NLS-1$//$NON-NLS-2$
		}
        validateArgPresent(args, " -l3", "");//$NON-NLS-1$//$NON-NLS-2$
		for ( int i = 0; i < systemMarkingFileNames.length; ++i)
		{
			validateMemberFileExistsAndArgPresent(gen_dir, args, systemMarkingFileNames[i]);
		}
	}

	private void validateDomainPresent(IProject project, String modelName, String args, boolean singleFile) {
		IFolder models_dir = (IFolder)project.findMember(Ooaofooa.MODELS_DIRNAME);
		validateArgPresent(args, " -o ", modelName); //$NON-NLS-1$
		
		if (singleFile) {
			String model_file = modelName + "." + Ooaofooa.MODELS_EXT; //$NON-NLS-1$
			validateMemberFileExists( models_dir, model_file);
			validateArgPresent(args, " -m ", "../" + Ooaofooa.MODELS_DIRNAME + "/" +model_file); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		} 
		
		IFolder gen_dir = (IFolder)project.findMember(GEN_FOLDER);
		for ( int i = 0; i < modelMarkingFileNames.length; ++i)
		{
			validateMemberFileExistsAndArgPresent(gen_dir, args, modelName + modelMarkingFileNames[i]);
		}
		
	}
	private void validateDomainPresent(IProject project, String modelName, String args) {
		validateDomainPresent(project, modelName, args, true);
	}
	
	private void validateDomainNotPresent(IProject project, String modelName, String args, boolean rename) {
		IFolder models_dir = (IFolder)project.findMember(Ooaofooa.MODELS_DIRNAME);
		String model_file = modelName+"."+Ooaofooa.MODELS_EXT; //$NON-NLS-1$
		IResource member = models_dir.findMember(model_file);
		assertNull( member );
		validateArgNotPresent(args, " -o ", modelName);  //$NON-NLS-1$
		validateArgNotPresent(args, " -m ", "../" + Ooaofooa.MODELS_DIRNAME + "/" +model_file); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		
		IFolder gen_dir = (IFolder)project.findMember(GEN_FOLDER);
		for ( int i = 0; i < modelMarkingFileNames.length; ++i)
		{
      if (rename) {
        validateMemberFileDoesNotExist(gen_dir, args, modelName + modelMarkingFileNames[i]);   
      }
      else {
        validateMemberFileExistsAndArgPresent(gen_dir, args, modelName + modelMarkingFileNames[i]);
      }
		}
	}
	
	private IFolder validateMemberFolderExists(IProject project, String name) {
		IResource member = project.findMember(name);
		assertNotNull( member );
		assertTrue( member.exists() );
		assertTrue( member instanceof IFolder );
		return (IFolder)member;
	}

	private IFile validateMemberFileExists(IFolder dir, String name) {
		IResource member = dir.findMember(name);
		assertNotNull( member );
		assertTrue( member.exists() );
		assertTrue( member instanceof IFile );
		return (IFile)member;
	}
	private void validateArgPresent(String args, String flag, String name) {
		if ( args != null )
		{
			assertTrue(flag + name + " not present", -1 != args.indexOf(flag + name) );
		}
	}

	private void validateArgNotPresent(String args, String flag, String name) {
		if ( args != null )
		{
			assertTrue(flag + name + " present", -1 == args.indexOf(flag + name) );
		}
	}

	private IFile validateMemberFileExistsAndArgPresent(IFolder dir, String args, String name) {
		IFile member = validateMemberFileExists(dir, name);
		validateArgPresent(args, " -f ", name);  //$NON-NLS-1$
		return member;
	}

	private void validateMemberFileDoesNotExist(IFolder dir, String args, String name) {
		IResource member = dir.findMember(name);
		assertNull( member );
		validateArgNotPresent(args, " -f ", name);  //$NON-NLS-1$
	}
	
	private String getToolArgumentValue(IProject project) throws Exception
	{
		String tgtFilePath = project.getLocation().toString()
		+ "/" + MC3020Nature.EXTERNALTOOLBUILDER_FOLDER   //$NON-NLS-1$
		+ "/" + MC3020Nature.MC3020_LAUNCH_ID; //$NON-NLS-1$
		
		FileInputStream file = new FileInputStream(tgtFilePath);
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document document = parser.parse(file);

		file.close();

		document.getDocumentElement().normalize();

		NodeList nodes = document.getElementsByTagName("stringAttribute");  //$NON-NLS-1$
		int totalNodes = nodes.getLength();

		for (int s = 0; s < nodes.getLength(); s++) {
			Node firstNode = nodes.item(s);
			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element firstNodeElement = (Element) firstNode;
				String key = firstNodeElement.getAttribute("key");  //$NON-NLS-1$
				if (key.equals("org.eclipse.ui.externaltools.ATTR_TOOL_ARGUMENTS")) {  //$NON-NLS-1$
					String value = firstNodeElement.getAttribute("value");  //$NON-NLS-1$
					return value;
				}
			}
		}
		return null;
	}
}
