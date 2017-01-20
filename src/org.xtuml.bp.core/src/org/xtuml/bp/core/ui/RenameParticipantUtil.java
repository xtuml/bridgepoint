package org.xtuml.bp.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
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
	
    private IStatus doBeforeRenameElement(NonRootModelElement element, String newName) {
		if(element != null) {
            String oldName = element.getName();
            if(oldName == null) 
                return Status.OK_STATUS;
            IFile file = element.getFile();
            if(file == null) 
                return Status.OK_STATUS;
            IStatus returnStatus = null;
            for(IConfigurationElement configurationElement: Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
                try {
                    IRenameElementParticipant renameParticipant = (IRenameElementParticipant) configurationElement.createExecutableExtension(CLASS_ATTRIBUTE);
                    IStatus status = renameParticipant.beforeRenameElement(element, newName);
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

    private IStatus doBeforeRenameElement(IStructuredSelection selection, String newName) {
		Object renameTarget = selection.getFirstElement();
		if(renameTarget instanceof NonRootModelElement) {
            NonRootModelElement element = (NonRootModelElement) renameTarget;
            if ( element instanceof InterfaceOperation_c ) {
            	// For interface operations call the rename refactor on each
            	// provided or required interface operation
            	InterfaceOperation_c c_io = (InterfaceOperation_c)element;
            	IStatus status;
            	ProvidedOperation_c[] spr_pos = ProvidedOperation_c.getManySPR_POsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(ExecutableProperty_c.getOneC_EPOnR4004(c_io)));
            	for ( NonRootModelElement el : spr_pos ) {
            		status = doBeforeRenameElement( el, newName );
            		if ( !status.isOK() )
            			return status;
            	}
            	RequiredOperation_c[] spr_ros = RequiredOperation_c.getManySPR_ROsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(ExecutableProperty_c.getOneC_EPOnR4004(c_io)));
            	for ( NonRootModelElement el : spr_ros ) {
            		status = doBeforeRenameElement( el, newName );
            		if ( !status.isOK() )
            			return status;
            	}
            }
            else {
                return doBeforeRenameElement( element, newName );
            }
		}
		return Status.OK_STATUS;
	}
	
	private IStatus doAfterRenameElement() {
		IStatus returnStatus = Status.OK_STATUS;
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

}
