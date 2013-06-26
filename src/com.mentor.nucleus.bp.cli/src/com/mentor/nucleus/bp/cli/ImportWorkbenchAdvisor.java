package com.mentor.nucleus.bp.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.PlatformUI;

public class ImportWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {

	private String projectPath;

	protected ImportWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		projectPath = cmdLine.getStringValue("-project");
	}

	@Override
	public void postStartup() {
		super.postStartup();
		Thread runner = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					importProject();
				} catch (CoreException e) {
					System.err.println(e.getMessage());
				} catch (BPCLIException e) {
					System.err.println(e.getMessage());
				} catch (IOException e) {
					System.err.println(e.getMessage());
				} finally {
					System.out.println("Import complete.  Exiting.");
					// Unless running in debug exit after the build
					if (!debug) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(
								new Runnable() {

									@Override
									public void run() {
										PlatformUI.getWorkbench().close();
									}
								});
					}
				}
			}
		});
		runner.setName("BP CLI Import");
		runner.start();
	}

	private void importProject() throws CoreException, BPCLIException, IOException {
		// verifier the source exists and is a Folder
		File source = new File(projectPath);
		if(!source.exists() || !source.isDirectory()) {
			throw new BPCLIException("The source project does not exist.");
		}
		// Get the source project
		String projectName = getProjectNameFromPath();
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		if(project.exists()) {
			if(cmdLine.getBooleanValue("-deleteExisting")) {
				project.delete(true, true, new NullProgressMonitor());
			} else {
				throw new BPCLIException("The specified project already exists in the workspace.");
			}
		}
		System.out.println("Importing project: " + projectName);
		// create the project
		project.create(new NullProgressMonitor());
		// Copy source project contents
		copyFolder(new File(projectPath), project.getLocation().toFile());
		project.close(new NullProgressMonitor());
		project.open(new NullProgressMonitor());
	}

	private void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			if (!dest.exists()) {
				dest.createNewFile();
			}
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}		
	}

	private String getProjectNameFromPath() {
		Path path = new Path(projectPath);
		return path.lastSegment();
	}

	
	
}
