//=====================================================================
//
//File:      $RCSfile: TestingUtilities.java,v $
//Version:   $Revision: 1.41 $
//Modified:  $Date: 2013/05/10 05:37:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.test.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.Thread.State;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class TestingUtilities {

	private static IPath sourceDirectory = null;
	private static boolean archiveFileOptionSet = false; 

	public static IPath getSourceDirectory() {
		if (sourceDirectory == null) {
			String sourceDirectoryPath = System.getProperty("WORKSPACE_PATH"); //$NON-NLS-1$
			if (sourceDirectoryPath == null) {
				throw new IllegalStateException(
						"environment variable WORKSPACE_PATH not set"); //$NON-NLS-1$
			}
			File directory = new File(sourceDirectoryPath);
			if (!directory.exists() || !directory.isDirectory()) {
				throw new IllegalStateException(
						"Invalid source directory given as WORKSPACE_PATH=" + sourceDirectoryPath); //$NON-NLS-1$
			}
			sourceDirectory = new Path(sourceDirectoryPath);
		}
		return sourceDirectory;
	}

	public static IProject createProject(String name) throws CoreException {
		return createProject(name, null);
	}

	public static IProject createProject(IProject projectHandle) {
		try {
			return createProject(projectHandle, null);
		} catch (CoreException e) {
			return null;
		}
	}

	public static IProject createProject(String name, String location)
			throws CoreException {
		IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(name);

		return createProject(projectHandle);
	}

	public static IProject createProject(final IProject projectHandle,
			String location) throws CoreException {
	    return ProjectUtilities.createProject(projectHandle, location);
	}

	public static boolean deleteProject(String name) throws CoreException {
		IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(name);

		return deleteProject(projectHandle);
	}

	public static boolean deleteProject(final IProject projectHandle)
			throws CoreException {
		return ProjectUtilities.deleteProject(projectHandle);
	}

	public static void allowJobCompletion() {
		ProjectUtilities.allowJobCompletion();
	}

	public static IFolder importFolder(IContainer container, File folder,
			String dest) throws CoreException, IOException {
		return importFolder(container, folder, dest, false);
	}

	public static IFolder importFolder(IContainer container, File folder,
			String dest, boolean removeFirst) throws CoreException, IOException {
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IllegalArgumentException("Should be a valid folder"); //$NON-NLS-1$
		}

		IPath toPath = null;
		if (dest != null) {
			toPath = new Path(dest);
			toPath = toPath.append(folder.getName());
		} else {
			toPath = new Path(folder.getName());
		}

		IFolder containerFolder = container.getFolder(toPath);

		if (removeFirst && containerFolder.exists()) {
			containerFolder.delete(true, false, new NullProgressMonitor());
		}

		if (!containerFolder.exists()) {
			containerFolder.create(true, true, new NullProgressMonitor());
		}

		File[] childs = folder.listFiles();
		// first bring in all files at this level
		for (int i = 0; i < childs.length; i++) {
			if (!childs[i].isDirectory()) {
				importFile(containerFolder, childs[i]);
			}
		}
		// then bring in any sub-folders
		for (int i = 0; i < childs.length; i++) {
			if (childs[i].isDirectory()) {
				importFolder(containerFolder, childs[i], "", removeFirst);
			}
		}
		return containerFolder;
	}

	public static IFile importFile(IContainer container, File file)
			throws CoreException, IOException {
		if (!file.exists() || file.isDirectory()) {
			throw new IllegalArgumentException("Should be a valid file"); //$NON-NLS-1$
		}

		IFile containerFile = container.getFile(new Path(file.getName()));
		if (!containerFile.exists()) {
			FileInputStream is = new FileInputStream(file);
			containerFile.create(is, true, new NullProgressMonitor());
		} else {
			FileInputStream is = new FileInputStream(file);
			containerFile.setContents(is, true, false,
					new NullProgressMonitor());
		}
		BaseTest.dispatchEvents(0);
		return containerFile;
	}

	public static boolean isTextSame(Reader fileStream, Reader withFileStream)
			throws IOException {
		BufferedReader sourceReader = new BufferedReader(fileStream);
		String line = null;
		Vector sourceLines = new Vector(20);
		while ((line = sourceReader.readLine()) != null) {
			sourceLines.add(line);
		}
		int lastLineIndex = sourceLines.size() - 1;

		if (lastLineIndex < 0) {
			throw new IllegalArgumentException(
					"Content of first argument file is empty"); //$NON-NLS-1$
		}

		BufferedReader targetReader = new BufferedReader(withFileStream);
		int index = 0;

		boolean similiar = true;

		while ((line = targetReader.readLine()) != null) {
			if (index <= lastLineIndex) {
				if (!line.equals(sourceLines.get(index))) {
					CorePlugin
							.logError(
									"Expected {" + sourceLines.get(index) + "} but found {" + line + "} at line" + (index + 1), null); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
					similiar = false;
				}
			} else {
				CorePlugin
						.logError(
								"Expected {} but found {" + line + "} at line" + (index + 1), null); //$NON-NLS-1$ //$NON-NLS-2$
				similiar = false;
			}
			index++;
		}

		if (index <= lastLineIndex) {
			while (index <= lastLineIndex) {
				CorePlugin
						.logError(
								"Expected {" + sourceLines.get(index) + "} but found {} at line" + (index + 1), null); //$NON-NLS-1$//$NON-NLS-2$
				similiar = false;
				index++;
			}
		}

		return similiar;
	}

	public static void waitForThread(String threadName) {
		int num_threads = Thread.activeCount();
		Thread[] t_set = new Thread[num_threads];
		Thread.enumerate(t_set);
		for (int i = 0; i < num_threads; ++i) {
			if (t_set[i] != null && t_set[i].getName() != null
					&& t_set[i].getName().equals(threadName)) {
				Display d = Display.getCurrent();
				while (t_set[i].isAlive()) {
					while (d.readAndDispatch()) {
					}
				}
				return;
			}
		}
	}

	/**
	 * 
	 * @param expected_file
	 * @param actual_file
	 * @param checkForEquality if true check for equality, if false check for inequality
	 */
	public static void fileContentsCompare(String expected_file,
			String actual_file, boolean checkForEquality) {
		String expectedResults = TestUtil.getTextFileContents(new File(
				expected_file));
		String actualResults = TestUtil.getTextFileContents(new File(
				actual_file));
		if (checkForEquality) {
			TestCase.assertEquals("File contents are unequal", expectedResults,
					actualResults);
		} else {
			TestCase.assertTrue("File contents are equal and shouldn't be", !actualResults.equals(expectedResults));
		}
	}
	
	public static void fileContentsCompare(String expected_file,
			String actual_file) {
		fileContentsCompare(expected_file, actual_file, true);
	}
	
	public static void fileSizesCompare(String expected_file, String actual_file) {
		String expectedResults = TestUtil.getTextFileContents(new File(
				expected_file));
		String actualResults = TestUtil.getTextFileContents(new File(
				actual_file));
		TestCase.assertEquals("File Sizes are unequal", expectedResults
				.length(), actualResults.length());
	}

	public static void fileContentsCompare(String expected_file,
			String actual_file, String message) {
		String expectedResults = TestUtil.getTextFileContents(new File(
				expected_file));
		String actualResults = TestUtil.getTextFileContents(new File(
				actual_file));
		TestCase.assertEquals("File contents are unequal " + message,
				expectedResults, actualResults);
	}

	public static void fileContentsCompareEx(String expected_file,
			String actual_file) {
		try {
			IPath actual_path = new Path(actual_file);
			File actual_fh = actual_path.toFile();
			FileInputStream actual_fs = new FileInputStream(actual_fh);
			BufferedReader actual_br = new BufferedReader(
					new InputStreamReader(actual_fs));

			IPath expected_path = new Path(expected_file);
			File expected_fh = expected_path.toFile();
			FileInputStream expected_fs = new FileInputStream(expected_fh);
			BufferedReader expected_br = new BufferedReader(
					new InputStreamReader(expected_fs));

			String expected = getSingleString(expected_br);
			String actual = getSingleString(actual_br);

			TestCase.assertEquals("Files " + expected_file + " and "
					+ actual_file + " are different", expected, actual);

			expected_br.close();
			actual_br.close();
		} catch (FileNotFoundException f) {
			TestCase.fail(f.toString());
		} catch (IOException f) {
			TestCase.fail(f.toString());
		}
	}

	private static String getSingleString(BufferedReader reader)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append("\r\n");
		}
		return buffer.toString();
	}

	public static void sortFile(String in_file, String out_file) {
		try {
			String in_str = '"' + in_file.replace('/', '\\') + '"';
			String out_str = '"' + out_file.replace('/', '\\') + '"';
			String cmd = "sort /REC 65535 /O " + out_str + " " + in_str;
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			TestCase.assertEquals(0, p.exitValue());
		} catch (IOException f) {
			TestCase.fail(f.toString());
		} catch (InterruptedException ie) {
			TestCase.fail(ie.toString());
		}
	}

	public static void makeInsertsOneLine(String in_file, String out_file) {
		try {
			IPath in_path = new Path(in_file);
			File in_fh = in_path.toFile();
			FileInputStream in_fs = new FileInputStream(in_fh);
			BufferedReader in_br = new BufferedReader(new InputStreamReader(
					in_fs));

			IPath out_path = new Path(out_file);
			File out_fh = out_path.toFile();
			FileOutputStream out_fs = new FileOutputStream(out_fh);
			BufferedWriter out_bw = new BufferedWriter(new OutputStreamWriter(
					out_fs));

			String in_line = in_br.readLine();
			String out_line = in_line;
			while (in_line != null) {
				if (in_line.startsWith("INSERT")) {
					String full_stmt = in_line;
					while (!in_line.endsWith(");")) {
						in_line = in_br.readLine();
						full_stmt = full_stmt + in_line;
						if (in_line.startsWith("\t'")) {
							// string attributes can be on multiple lines, so read it all in
							while (!in_line.endsWith("',")
									&& !in_line.endsWith("');")) {
								in_line = in_br.readLine();
								full_stmt = full_stmt + in_line;
							}
						}
					}
					out_line = full_stmt.replaceAll("\n\tVALUES", "\tVALUES")
							.replaceAll(",\n\t", ",\t");
				}
				out_bw.write(out_line);
				out_bw.newLine();
				in_line = in_br.readLine();
				out_line = in_line;
			}
			in_br.close();
			out_bw.close();
		} catch (FileNotFoundException f) {
			TestCase.fail(f.toString());
		} catch (IOException f) {
			TestCase.fail(f.toString());
		}
	}

	public static boolean compareModels(Ooaofooa domain1, Ooaofooa domain2) {
		return compareModels(Domain_c.DomainInstance(domain1), Domain_c
				.DomainInstance(domain2));
	}

	public static boolean compareModels(Domain_c domain1, Domain_c domain2) {
		System.err.println("Comparing " + getAllChildrenCount(domain1)
				+ " children of domain " + domain1.getName() + " with "
				+ getAllChildrenCount(domain2) + " children of domain "
				+ domain2.getName());
		return compareModelElement(domain1, domain2);
	}

	private static int getAllChildrenCount(NonRootModelElement me) {
		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();
		List children = metaData.getChildren(me, false);

		int count = children.size();

		for (int i = 0; i < children.size(); i++) {
			NonRootModelElement child = (NonRootModelElement) children.get(i);
			count = count + getAllChildrenCount(child);
		}

		return count;
	}

	public static boolean compareModelElement(NonRootModelElement me1,
			NonRootModelElement me2) {
		if (me1.identityEquals(me2)) {
			IPersistenceHierarchyMetaData metaData = PersistenceManager
					.getHierarchyMetaData();
			List children1 = metaData.getChildren(me1, false);
			List children2 = metaData.getChildren(me2, false);

			if (children1.size() != children2.size()) {
				System.err.println("children of " + getName(me1)
						+ " does not matches with children count of "
						+ getName(me2) + " (" + children1.size() + ":"
						+ children2.size() + ")");
				return false;
			}

			for (int i = 0; i < children1.size(); i++) {
				NonRootModelElement childFrom1 = (NonRootModelElement) children1
						.get(i);
				NonRootModelElement childFrom2 = find(childFrom1, children2);

				boolean equal = false;
				if (childFrom2 != null) {
					children2.remove(childFrom2);

					equal = compareModelElement(childFrom1, childFrom2);
					if (!equal) {
						System.err.println("      parents:" + getName(me1)
								+ "<->" + getName(me2));
						return false;
					}
				} else {
					System.err.println("could not find sibling of "
							+ getName(childFrom1));
					return false;
				}

			}
			return true;
		}
		System.err.println(">>>>>>>>>>>>>" + getName(me1)
				+ " does not matches with " + getName(me2));
		return false;
	}

	private static NonRootModelElement find(NonRootModelElement me, List list) {
		for (int i = 0; i < list.size(); i++) {
			if (me.identityEquals(list.get(i))) {
				return (NonRootModelElement) list.get(i);
			}
		}
		return null;
	}

	public static String getName(NonRootModelElement me) {
		Class clazz = me.getClass();
		Method method = null;
		try {
			method = clazz.getMethod("getName", null);
		} catch (Exception e) {
		}

		if (method == null) {
			try {
				method = clazz.getMethod("get_Name", null);
			} catch (Exception e) {
			}
		}

		String name = null;

		if (method != null) {
			try {
				name = clazz.getName() + "(" + (String) method.invoke(me, null)
						+ ")";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (name == null) {
			name = "Instance of " + clazz.getName();
		}
		return name;
	}

	public static void copyFolder(File sourceFolder, IResource target,
			boolean synchronize) throws CoreException {
		if (!sourceFolder.exists()) {
			throw new IllegalArgumentException("sourceFolder does not exist");
		}

		if (!sourceFolder.isDirectory()) {
			throw new IllegalArgumentException(
					"sourceFolder must be a directory");
		}

		IProgressMonitor monitor = new NullProgressMonitor();

		IFolder targetFolder = null;
		IProject targetProject = null;
		if (target.getType() == IResource.FOLDER) {
			targetFolder = (IFolder) target;
		} else if (target.getType() == IResource.PROJECT) {
			targetProject = (IProject) target;
		}

		if (!target.exists()) {
			if (targetFolder != null)
				targetFolder.create(true, true, monitor);
			if (targetProject != null)
				createProject(targetProject);
		}

		target.refreshLocal(1, monitor);

		File[] sourceMembers = sourceFolder.listFiles();
		IResource[] targetMembers = null;
		if (targetFolder != null)
			targetMembers = targetFolder.members();
		if (targetProject != null)
			targetMembers = targetProject.members();

		for (int i = 0; i < sourceMembers.length; i++) {
			IResource targetResource = findMemberAndSetNull(targetMembers,
					sourceMembers[i]);
			if (sourceMembers[i].isFile()) {
				IFile file = (IFile) targetResource;
				if (file == null) {
					if (targetFolder != null)
						file = targetFolder.getFile(sourceMembers[i].getName());
					if (targetProject != null)
						file = targetProject
								.getFile(sourceMembers[i].getName());
				}
				copyFile(sourceMembers[i], file, synchronize);
			} else {
				if (!sourceMembers[i].getName().equals(".svn")) {
					IFolder folder = (IFolder) targetResource;
					if (folder == null) {
						if (targetFolder != null)
							folder = targetFolder.getFolder(sourceMembers[i]
									.getName());
						if (targetProject != null)
							folder = targetProject.getFolder(sourceMembers[i]
									.getName());
					}
					copyFolder(sourceMembers[i], folder, synchronize);
				}
			}
		}

		if (synchronize) {
			for (int i = 0; i < targetMembers.length; i++) {
				if (targetMembers[i] != null) {
					targetMembers[i].delete(true, new NullProgressMonitor());
				}
			}
		}
	}

	protected static void copyFile(File sourceFile, IFile targetResource,
			boolean synchronize) throws CoreException {
		FileInputStream is = null;
		try {
			if (!targetResource.exists()) {
				is = new FileInputStream(sourceFile);
				targetResource.create(is, true, new NullProgressMonitor());
				targetResource.setLocalTimeStamp(sourceFile.lastModified());
			} else if (synchronize
					&& targetResource.getLocation().toFile().lastModified() > sourceFile
							.lastModified()) {
				is = new FileInputStream(sourceFile);
				targetResource.setContents(is, true, false,
						new NullProgressMonitor());
				targetResource.setLocalTimeStamp(sourceFile.lastModified());
			}

		} catch (FileNotFoundException e) {
			throw new WorkbenchException("file not found", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static IResource findMemberAndSetNull(IResource[] targetMembers,
			File source) {
		IResource resource = null;
		for (int i = 0; i < targetMembers.length; i++) {
			if (source.isDirectory()) {
				if (targetMembers[i] instanceof IFolder) {
					if (targetMembers[i].getName().equals(source.getName())) {
						resource = targetMembers[i];
						targetMembers[i] = null;
						break;
					}
				}
			} else {
				if (targetMembers[i] instanceof IFile) {
					if (targetMembers[i].getName().equals(source.getName())) {
						resource = targetMembers[i];
						targetMembers[i] = null;
						break;
					}
				}
			}
		}
		return resource;
	}

	/**
	 * Return's SystemModel_c instance related to
	 * the given project
	 *
	 * @param projectName
	 * @return SystemModel_c
	 */
	public static SystemModel_c getSystemModel(final String projectName) {
	    return ProjectUtilities.getSystemModel(projectName);
	}

	public static void copyProjectContents(File sourceProject,
			final IProject testProject) throws CoreException, IOException {
		final File finalSourceProject = sourceProject.getCanonicalFile();
		// create the project before the below code, otherwise we end
		// up in a dead lock as the code waits for our job to complete
		// before proceeding
		if(!testProject.exists()) {
			createProject(testProject);
		}
		// perform in a workspace operation
		WorkspaceJob job = new WorkspaceJob("Copy Project Contents") {
			
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				// must be performed on the UI thread
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					
					@Override
					public void run() {
						// always use the canonical file to prevent
						// java path length issues
						try {
							copyFolder(finalSourceProject, testProject, true);
						} catch (CoreException e) {
							// log error to allow test failure
							CorePlugin.logError("Unable to copy project contents.", e);
						}	
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			// log error to allow test failure
			CorePlugin.logError("Unable to copy project contents.", e);
		}
		// allow for any upgrades to occur
		BaseTest.dispatchEvents(0);
	}
	
	public static boolean setFileOptions(Control[] allChildren, String fqFilePath, String option) {
		boolean optionsWereSet = false;
		for (int i = 0; !optionsWereSet && i < allChildren.length; i++) {
			if (allChildren[i] instanceof Composite) {	
				optionsWereSet = setFileOptions(((Composite)allChildren[i]).getChildren(), fqFilePath, option);
			} else if (allChildren[i] instanceof Button) {
				((Button)allChildren[i]).setEnabled(false);
				((Button)allChildren[i]).setSelection(false);
				((Button)allChildren[i]).notifyListeners(SWT.SELECTED, new Event());
			}
		}
		for (int i = 0; !optionsWereSet && i < allChildren.length; i++) {
			if (allChildren[i] instanceof Composite) {	
				optionsWereSet = setFileOptions(((Composite)allChildren[i]).getChildren(), fqFilePath, option);
			} else if (allChildren[i] instanceof Button) {
			
				String btnText = ((Button)allChildren[i]).getText();
				if (btnText.equalsIgnoreCase(option)) {
					((Button)allChildren[i]).setEnabled(true);
					((Button)allChildren[i]).setSelection(true);
					((Button)allChildren[i]).notifyListeners(SWT.SELECTED, new Event());
					((Text)allChildren[i+1]).setText(fqFilePath);					
					((Text)allChildren[i+1]).notifyListeners(SWT.FocusOut, new Event());

					optionsWereSet = true;
				}
			} else {
				Control temp = allChildren[i];
				temp.getToolTipText();
			}			
		}
		return optionsWereSet;

	}
	
	public static boolean importModelUsingWizard(SystemModel_c systemModel,
			String fullyQualifiedSingleFileModel, boolean parseOnImport) {
	    return ProjectUtilities.importModelUsingWizard(systemModel, fullyQualifiedSingleFileModel, parseOnImport);
	}
	
	public static boolean importModelUsingWizard(SystemModel_c systemModel,
			IPath srcDir, String modelName, boolean parseOnImport) 
	{
		String fqName = srcDir.append(modelName).toString();
		return importModelUsingWizard(systemModel, fqName, parseOnImport);
	}

	public static boolean importModelUsingWizardConvertToGenerics(SystemModel_c systemModel,
			String fullyQualifiedSingleFileModel, boolean convertToGenericsOnImport) {
    	// 3/27/13 - The "Convert to generics" option in the import dialog is removed.  So
    	// this function is now redundant.  Rather than delete it and have lots of
    	// code change fallout, it lives on and simply directs to another version
    	// to remove the redundancy.		
	    boolean wasSuccessful = ProjectUtilities.importModelUsingWizardConvertToGenerics(systemModel, fullyQualifiedSingleFileModel, convertToGenericsOnImport);
    	if (wasSuccessful) {
    		BaseTest.dispatchEvents(0);
    	}
	    return wasSuccessful;
	}
	

	// Export the model elements that are part of the Selection singleton
	public static boolean exportModelUsingWizard(String destination,
			boolean overwrite) {
	    return ProjectUtilities.exportModelUsingWizard(destination, overwrite);
	}

	public static void processDisplayEvents() {
		try {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			processPlatformJobs();
		} catch (SWTException e) {
			// ignore and continue processing, the test
			// code completes before allowing a progress
			// dialog from properly closing
			processDisplayEvents();
		}
	}

	public static void processPlatformJobs() {
		Job[] jobs = Job.getJobManager().find(null);
		for(int i = 0; i < jobs.length; i++) {
			boolean wait = jobs[i].getState() == Job.RUNNING;
			Thread thread = jobs[i].getThread();
			if(wait) {
				if(thread != null)
					wait = thread.getState() != State.WAITING;
			}
			while(wait) {
				PlatformUI.getWorkbench().getDisplay().readAndDispatch();
				wait = jobs[i].getState() == Job.RUNNING;
				if(wait) {
					thread = jobs[i].getThread();
					if(thread != null)
						wait = thread.getState() != State.WAITING;
				}
			}
		}
	}

	public static void importDevelopmentProjectIntoWorkspace(
			String developmentWorkspaceProject) {
		String workspace_location = System.getenv("XTUML_DEVELOPMENT_REPOSITORY");
		if(workspace_location == null || workspace_location.equals("")) {
			workspace_location = BaseTest.DEFAULT_XTUML_DEVELOPMENT_REPOSITORY;
		}
		String pathToProject = workspace_location + "/src/" + developmentWorkspaceProject;
		File file = new File(pathToProject);
		if(!file.exists()) {
			Assert.fail("Could not locate test model at: " + pathToProject);
			return;
		}
		ProjectUtilities.importExistingProject(pathToProject, true);
		BaseTest.dispatchEvents(0);
	}

	public static void importTestingProjectIntoWorkspace(String testProject) {
		String testProjectPath = "";
		String repository_location = System.getenv("XTUML_TEST_MODEL_REPOSITORY");
		if (repository_location == null || repository_location.equals("")) {
			// use the default location
			testProjectPath = BaseTest.DEFAULT_XTUML_TEST_MODEL_REPOSITORY
					+ "/" + testProject;
		} else {
			testProjectPath = repository_location + "/" + testProject;
		}
		File file = new File(testProjectPath);
		if(!file.exists()) {
			// check the private repository
			repository_location = System.getenv("XTUML_PRIVATE_MODEL_REPOSITORY");
			if(repository_location == null || repository_location.equals("")) {
				testProjectPath = BaseTest.DEFAULT_PRIVATE_MODEL_REPOSITORY
						+ "/" + testProject;				
			} else {
				testProjectPath = repository_location + "/" + testProject;
			}
			file = new File(testProjectPath);
			if(!file.exists()) {
				Assert.fail("Could not locate test model at: " + testProjectPath);
				return;
			}
		}
		ProjectUtilities.importExistingProject(testProjectPath, true);
		BaseTest.dispatchEvents(0);
	}

}
