package com.mentor.nucleus.bp.internal.tools.actions;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.io.mdl.upgrade.UpgradeUtil;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class CreateTestProjectAndImportTestModel implements IViewActionDelegate {

	@Override
	public void run(IAction action) {
		// ask for the name of the project, which must match the model file
		// to import
		InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
				.getDisplay().getActiveShell(), "Create Test Project",
				"Enter the name(s) of the test model(s) separated by commas", "", null);
		int result = dialog.open();
		if(result == Window.CANCEL) {
			return;
		}
		String value = dialog.getValue();
		String[] split = value.split(",");
		for(String file : split) {
			// try to locate the model file in c:/Models/OldDomainsWithLongBasedUUIDs
			File modelFile = new File("c:/Models/OldDomainsWithLongBasedUUIDs/" + file + ".xtuml");
			if(!modelFile.exists()) {
				MessageDialog
						.openError(
								PlatformUI.getWorkbench().getDisplay()
										.getActiveShell(),
								"Could not find model",
								"You must have the model at this location: " + modelFile.getAbsolutePath());
			} else {
				String projectName = readProjectNameFromModelFile(modelFile);
				if(projectName.equals("")) {
					MessageDialog
					.openError(
							PlatformUI.getWorkbench().getDisplay()
									.getActiveShell(),
							"Could not determine project name",
							"The project name is determined from the S_DOM entry in the model file.");
					continue;
				}
				IProject newProject = ProjectUtilities.createProject(ResourcesPlugin.getWorkspace()
						.getRoot().getProject(projectName));
				// automatically upgrade the model
				UpgradeUtil.allowAutomaticUpgradeForNextJob();
				ProjectUtilities.importModelUsingWizard(
						ProjectUtilities.getSystemModel(newProject),
						modelFile.getAbsolutePath(), true);
			}
		}
	}

	private String readProjectNameFromModelFile(File modelFile) {
		try {
			RandomAccessFile ra = new RandomAccessFile(modelFile, "rw");
			byte[] b = new byte[(int)modelFile.length()];
			try {
				ra.read(b);
			} catch (Exception e) {
				CorePlugin.logError("Unable to read model file.", e);
				ra.close();
				return "";
			}
			String result = new String(b);
			String[] split = result.split("\n");
			String domainNameLine = split[5];
			String[] domainSplit = domainNameLine.split("'");
			if(domainSplit.length == 1) {
				return modelFile.getName().replaceAll(".xtuml", "");
			}
			return domainSplit[1];
		} catch (IOException e) {
			CorePlugin.logError("Unable to read model file.", e);
		}
		return "";
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void init(IViewPart view) {
	}

}
