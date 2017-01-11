package org.xtuml.bp.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.UIUtil;

public class RenameParticipantUtil {

	private static final String EXTENSION_POINT_ID = "org.xtuml.bp.core.renameParticipants";
	private static final String CLASS_ATTRIBUTE = "class";
	
	private List<IRenameElementParticipant> participants = new ArrayList<>();
	
	public boolean beforeRenameElement(IStructuredSelection selection, String newName) {
		IStatus status = doBeforeRenameElement(selection, newName);
		return handleStatus(status);
	}
	
	public boolean afterRenameElement() {
		IStatus status = doAfterRenameElement();
		return handleStatus(status);
	}

	private boolean handleStatus(IStatus status) {
		switch (status.getSeverity()) {
		case IStatus.CANCEL:
			return false;
		case IStatus.ERROR:
			CorePlugin.getDefault().getLog().log(status);
			UIUtil.openWarning(null, "Error during rename", status.getMessage() + "\nSee log for details.");
			return false;
		case IStatus.WARNING:
			return UIUtil.openConfirm(null, "Problems during rename", status.getMessage() + "\nContinue refactoring?", true);
		default:
			return true;
	}

	}
	private IStatus doBeforeRenameElement(IStructuredSelection selection, String newName) {
		Object renameTarget = selection.getFirstElement();
		if(renameTarget instanceof NonRootModelElement) {
			NonRootModelElement element = (NonRootModelElement) renameTarget;
			String oldName = element.getName();
			if(oldName == null) 
				return Status.OK_STATUS;
			IFile file = element.getFile();
			if(file == null) 
				return Status.OK_STATUS;
			IProject project = file.getProject();
			String oldQualifiedName = getQualifiedName(oldName, file);
			IStatus returnStatus = null;
			for(IConfigurationElement configurationElement: Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
				try {
					IRenameElementParticipant renameParticipant = (IRenameElementParticipant) configurationElement.createExecutableExtension(CLASS_ATTRIBUTE);
					IStatus status = renameParticipant.beforeRenameElement(oldQualifiedName, newName, element.getClass().getSimpleName(), project);
					returnStatus = merge(returnStatus, status);
					if(returnStatus.getSeverity() == IStatus.ERROR)
						break;
					else 
						participants.add(renameParticipant);
				} catch (Exception exc) {
					returnStatus = merge(returnStatus, new Status(
							IStatus.ERROR,
							CorePlugin.getDefault().getBundle().getSymbolicName(),
							"Error calling refactoring participant",
							exc));
					break;
				}
			}
			return returnStatus;
		}
		return Status.OK_STATUS;
	}
	
	private IStatus doAfterRenameElement() {
		IStatus returnStatus = null;
		for(IRenameElementParticipant participant: participants) {
			IStatus status = participant.afterRenameElement();
			returnStatus = merge(returnStatus, status);
		}
		return returnStatus;
	}
	
	private static IStatus merge(IStatus returnStatus, IStatus status) {
		if(returnStatus == null) { 
			return status;
		} else if(returnStatus instanceof MultiStatus) {
			((MultiStatus) returnStatus).add(status);
			return returnStatus;
		} else { 
			return new MultiStatus(status.getPlugin(), status.getCode(), status.getMessage(), status.getException());
		}
	}

	private static String getQualifiedName(String oldName, IFile file) {
		String[] qualifiedName = file.getProjectRelativePath().removeLastSegments(1).segments();
		StringBuilder builder = new StringBuilder();
		boolean firstSegment = true;
		for(String segment: qualifiedName) {
			if(!firstSegment)
				builder.append("::");
			firstSegment = false;
			builder.append(segment);
		}
		if(!firstSegment)
			builder.append("::");
		builder.append(oldName);
		return builder.toString();
	}
}
