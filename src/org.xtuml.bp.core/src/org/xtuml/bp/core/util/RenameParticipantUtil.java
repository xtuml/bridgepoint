package org.xtuml.bp.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.ui.IRenameElementParticipant;
import org.xtuml.bp.core.util.UIUtil;

public class RenameParticipantUtil {

	private static final String EXTENSION_POINT_ID = "org.xtuml.bp.core.renameParticipants";
	private static final String CLASS_ATTRIBUTE = "class";
	
	private List<IRenameElementParticipant> participants = new ArrayList<>();

    public boolean renameElement( Transaction transaction ) {
        if ( null == transaction ) return false;

        IStatus status = null;

		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		for (int i = 0; i < modelRoots.length; i++) {
			if (modelRoots[i].persistEnabled()) {
				IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]);
				for (int j = 0; j < modelDeltas.length; j++) {
                    if ( modelDeltas[j] instanceof AttributeChangeModelDelta ) {
                    	AttributeChangeModelDelta modelDelta = (AttributeChangeModelDelta)modelDeltas[j];

						NonRootModelElement element = (NonRootModelElement)modelDelta.getModelElement();
						if ( null == element ) return false;
						
						// check the creation case
						if ( modelDelta.getOldValue() instanceof String && ((String)modelDelta.getOldValue()).equals("") ) {
							return false;
						}

						// Attribute special case
						// For attributes, the name can change if the prefix or the root name is changed
						if ( element instanceof Attribute_c ) {
							Attribute_c o_attr = (Attribute_c)element;
							if ( o_attr.getPfx_mode() == 0 && "Root_nam".equals(modelDelta.getAttributeName()) ) {
								status = merge(status, doRenameElement( o_attr, 
													   (String)modelDelta.getNewValue(),
													   (String)modelDelta.getOldValue() ) );
							}
							else if ( o_attr.getPfx_mode() == 1 ) {
								String oldName = "";
								String newName = "";
								if ( "Root_nam".equals(modelDelta.getAttributeName()) ) {
									oldName = o_attr.getPrefix() + (String)modelDelta.getOldValue();
									newName = o_attr.getPrefix() + (String)modelDelta.getNewValue();
									status = merge(status, doRenameElement( o_attr, newName, oldName ) );
								}
								else if ( "Prefix".equals(modelDelta.getAttributeName()) ) {
									oldName = (String)modelDelta.getOldValue() + o_attr.getRoot_nam();
									newName = (String)modelDelta.getNewValue() + o_attr.getRoot_nam();
									status = merge(status, doRenameElement( o_attr, newName, oldName ) );
								}
							}
						}
						else if ( "Name".equals(modelDelta.getAttributeName()) ||
								  "Mning".equals(modelDelta.getAttributeName()) ||
								  "Txt_phrs".equals(modelDelta.getAttributeName()) ) {
							status = merge(status, doRenameElement( element, 
												   (String)modelDelta.getNewValue(),
												   (String)modelDelta.getOldValue() ) );
						}
                    }
                }
            }
        }

        return handleStatus( status );
    }

	private boolean handleStatus(IStatus status) {
		if ( null == status ) return false;
		switch (status.getSeverity()) {
		case IStatus.CANCEL:
			return false;
		case IStatus.ERROR:
			if(status.getException() != null) {
				CorePlugin.getDefault().getLog().log(status);
				UIUtil.openWarning(null, "Error during rename", status.getMessage() + "\nSee log for details.");
			}
			return false;
		case IStatus.WARNING:
			return UIUtil.openConfirm(null, "Problems during rename", status.getMessage() + "\nContinue refactoring?", true);
		default:
			return true;
	}

	}
	
    private IStatus doRenameElement(NonRootModelElement element, String newName, String oldName) {
		if(element != null) {
            IFile file = element.getFile();
            if(file == null) 
                return Status.OK_STATUS;
            IStatus returnStatus = null;
            for(IConfigurationElement configurationElement: Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
                try {
                    IRenameElementParticipant renameParticipant = (IRenameElementParticipant) configurationElement.createExecutableExtension(CLASS_ATTRIBUTE);
                    IStatus status = renameParticipant.renameElement(element, newName, oldName);
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
