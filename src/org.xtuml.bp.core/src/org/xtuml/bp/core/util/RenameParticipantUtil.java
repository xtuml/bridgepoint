package org.xtuml.bp.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.IRenameElementParticipant;
import org.xtuml.bp.core.util.UIUtil;

public class RenameParticipantUtil {

	private static final String EXTENSION_POINT_ID = "org.xtuml.bp.core.renameParticipants";
	private static final String CLASS_ATTRIBUTE = "class";
	
	private List<IRenameElementParticipant> participants = new ArrayList<>();

    public boolean renameElement( AttributeChangeModelDelta modelDelta ) {
        if ( null == modelDelta ) return false;
        NonRootModelElement element = (NonRootModelElement)modelDelta.getModelElement();
        if ( null == element ) return false;

        IStatus status = null;

        // special cases:
        // Terminator service, Attribute, Event param
        if ( element instanceof InterfaceOperation_c ) {
            if ( "Name".equals(modelDelta.getAttributeName()) ) {
                // For interface operations call the rename refactor on each
                // provided or required interface operation
                InterfaceOperation_c c_io = (InterfaceOperation_c)element;
                ProvidedOperation_c[] spr_pos = ProvidedOperation_c.getManySPR_POsOnR4503(
                                                    ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(
                                                    ExecutableProperty_c.getOneC_EPOnR4004(c_io)));
                for ( NonRootModelElement el : spr_pos ) {
                    status = merge(status, doRenameElement( el, 
                                           (String)modelDelta.getNewValue(),
                                           (String)modelDelta.getOldValue() ) );
                }
                RequiredOperation_c[] spr_ros = RequiredOperation_c.getManySPR_ROsOnR4502(
                                                    RequiredExecutableProperty_c.getManySPR_REPsOnR4500(
                                                    ExecutableProperty_c.getOneC_EPOnR4004(c_io)));
                for ( NonRootModelElement el : spr_ros ) {
                    status = merge(status, doRenameElement( el, 
                                           (String)modelDelta.getNewValue(),
                                           (String)modelDelta.getOldValue() ) );
                }
            }
        }
        // Terminator service param
        else if ( element instanceof PropertyParameter_c ) {
            // TODO implement
        }
        // DataType
        else if ( element instanceof DataType_c ) {
            if ( "Name".equals(modelDelta.getAttributeName()) ) {
                UserDataType_c s_udt = UserDataType_c.getOneS_UDTOnR17((DataType_c)element);
                status = merge(status, doRenameElement( s_udt, 
                        (String)modelDelta.getNewValue(),
                        (String)modelDelta.getOldValue() ) );
            }
        }
        // Attribute
        else if ( element instanceof Attribute_c ) {
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

        // regular cases:
        // Domain, Domain service, Domain service param, Terminator,
        // Object, Object service, Object service param, State, Event param
        else if ("Name".equals(modelDelta.getAttributeName())) {
            status = merge(status, doRenameElement( element, 
                                   (String)modelDelta.getNewValue(),
                                   (String)modelDelta.getOldValue() ) );
        }
        // Event
        else if ("Mning".equals(modelDelta.getAttributeName())) {
            status = merge(status, doRenameElement( element, 
                                   (String)modelDelta.getNewValue(),
                                   (String)modelDelta.getOldValue() ) );
        }
        // Relationship specification
        else if ("Txt_phrs".equals(modelDelta.getAttributeName())) {
            // TODO implement
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
