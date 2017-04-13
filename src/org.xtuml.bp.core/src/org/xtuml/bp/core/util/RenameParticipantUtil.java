package org.xtuml.bp.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.InstanceAttributeValue_c;
import org.xtuml.bp.core.MessageArgument_c;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.ui.IRenameElementParticipant;
import org.xtuml.bp.core.util.UIUtil;

public class RenameParticipantUtil {

	private static final String EXTENSION_POINT_ID = "org.xtuml.bp.core.renameParticipants";
	private static final String CLASS_ATTRIBUTE = "class";
	
	private List<IRenameElementParticipant> participants = new ArrayList<>();

	private static class MASLChangeDelta {
		String oldName;
		String newName;
		NonRootModelElement elementToChange;
		
		public MASLChangeDelta(NonRootModelElement p_elementToChange, String p_oldName, String p_newName) {
			oldName = p_oldName;
			newName = p_newName;
			elementToChange = p_elementToChange;
		}
	}

	/**
	 * See if doRenameElement will run if given the following NRME and
	 * 
	 * @param modelDelta This is the BridgePoint change delta for the element changed
	 * @param attrName
	 * @return The MASLChangeDelta instance or null if there will be no masl change
	 */
	private static MASLChangeDelta getMASLChangeDelta(IModelDelta modelDelta) {
		if (modelDelta == null) {
			return null;
		}
		
		NonRootModelElement nrmeChanged = (NonRootModelElement)modelDelta.getModelElement();
		if (nrmeChanged == null) {
			return null;
		}
		
		MASLChangeDelta maslDelta = null;
		if (modelDelta instanceof AttributeChangeModelDelta) {
			AttributeChangeModelDelta attrModelDelta = (AttributeChangeModelDelta) modelDelta;
			if ( attrModelDelta.getOldValue() instanceof String && ((String)attrModelDelta.getOldValue()).equals("") ) {
				// this is the element creation case
				maslDelta = null;
			} else if (nrmeChanged instanceof Attribute_c) {
				Attribute_c o_attr = (Attribute_c) nrmeChanged;

				if (o_attr.getPfx_mode() == 0 && "Root_nam".equals(attrModelDelta.getAttributeName())) {
					maslDelta = new MASLChangeDelta(o_attr, (String) attrModelDelta.getOldValue(),
							(String) attrModelDelta.getNewValue());
				} else if (o_attr.getPfx_mode() == 1) {
					if ("Root_nam".equals(attrModelDelta.getAttributeName())) {
						maslDelta = new MASLChangeDelta(o_attr,
								o_attr.getPrefix() + (String) attrModelDelta.getOldValue(),
								o_attr.getPrefix() + (String) attrModelDelta.getNewValue());
					} else if ("Prefix".equals(attrModelDelta.getAttributeName())) {
						maslDelta = new MASLChangeDelta(o_attr,
								(String) attrModelDelta.getOldValue() + o_attr.getRoot_nam(),
								(String) attrModelDelta.getNewValue() + o_attr.getRoot_nam());
					}
				}
			} else if (((nrmeChanged instanceof StateMachineEvent_c)
					&& "Mning".equals(attrModelDelta.getAttributeName()))
					|| ((nrmeChanged instanceof ComponentParticipant_c)
							&& "Informalcomponentname".equals(attrModelDelta.getAttributeName()))) {
				maslDelta = new MASLChangeDelta(nrmeChanged, (String) attrModelDelta.getOldValue(),
						(String) attrModelDelta.getNewValue());
			} else if (((nrmeChanged instanceof InstanceAttributeValue_c
					|| nrmeChanged instanceof ConstantSpecification_c || nrmeChanged instanceof MessageArgument_c
					|| nrmeChanged instanceof ExternalEntityParticipant_c || nrmeChanged instanceof ClassParticipant_c
					|| nrmeChanged instanceof PackageParticipant_c))
					&& "Informalname".equals(attrModelDelta.getAttributeName())) {
				maslDelta = new MASLChangeDelta(nrmeChanged, (String) attrModelDelta.getOldValue(),
						(String) attrModelDelta.getNewValue());
			} else if ("Name".equals(attrModelDelta.getAttributeName())
					|| "Txt_phrs".equals(attrModelDelta.getAttributeName())) {
				maslDelta = new MASLChangeDelta(nrmeChanged, (String) attrModelDelta.getOldValue(),
						(String) attrModelDelta.getNewValue());
			}
		}

		return maslDelta;
	}
	
	/**
	 * 
	 * @param modelDelta The BridgePoint change delta to check
	 * @return Returns true is a masl rename/refactor will be performed on the 
	 *         given BridgePoint IModelDelta and false if not
	 */
	public static boolean isMASLChange(IModelDelta modelDelta) {
		MASLChangeDelta maslDelta = getMASLChangeDelta(modelDelta);
		return maslDelta != null;
	}
	
	/**
	 * 
	 * @param transaction
	 * @return true if refactoring ran and was successful, false otherwise
	 */
	public boolean renameElement(IModelDelta modelDelta) {
		if (null == modelDelta)
			return false;

		boolean result = false;

		MASLChangeDelta maslDelta = getMASLChangeDelta(modelDelta);
		if (maslDelta == null) {
			NonRootModelElement modelEl = (NonRootModelElement)modelDelta.getModelElement();
			
			String ooaClassType = "<unknown>";
			String oldName = "<unknown>";
			String newName = "<unknown>";
			String ooaAttrName = "<unknown>";
			if (modelEl != null) {
				Class<? extends NonRootModelElement> classType = modelEl.getClass();
				ooaClassType = classType.getSimpleName();
				if (modelDelta instanceof AttributeChangeModelDelta ) {
					AttributeChangeModelDelta attrChangeDelta = (AttributeChangeModelDelta)modelDelta;
					oldName = (String) attrChangeDelta.getOldValue();
					newName = (String) attrChangeDelta.getNewValue();
					ooaAttrName = attrChangeDelta.getAttributeName();
				}
			}
			IStatus status = new Status(IStatus.ERROR, CorePlugin.getDefault().getBundle().getSymbolicName(),
								"MASL refactoring was called without a valid changeset.\n" +
								"\tOOA Class Type: " + ooaClassType +
								"\tOOA Attr Name: " + ooaAttrName +
								"\tOld Name: " + oldName +
								"\tNew Name: " + newName 
								, null);
			result = handleStatus(status);
		} else {
			// This is just used to be able to change the boolean value
			// inside the thread that we are about to create.
			// The xtext editor's refactoring has to run on the Display thread.
			AtomicBoolean syncExecResult = new AtomicBoolean();
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					IStatus status = doRenameElement(maslDelta);
					boolean result = handleStatus(status);
					syncExecResult.set(result);
				}
			});
			result = syncExecResult.get();
		}

		return result;
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
	
    private IStatus doRenameElement(MASLChangeDelta maslDelta) {
    	NonRootModelElement element = maslDelta.elementToChange;
    	String newName = maslDelta.newName;
    	String oldName = maslDelta.oldName;
		if(element != null) {
            IFile file = element.getFile();
            if(file == null) 
                return Status.OK_STATUS;
            IStatus returnStatus = null;
            for(IConfigurationElement configurationElement: Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
                try {
                    IRenameElementParticipant renameParticipant = (IRenameElementParticipant) configurationElement.createExecutableExtension(CLASS_ATTRIBUTE);
                    IStatus status = renameParticipant.renameElement(element, newName, oldName);
                    returnStatus = mergeStatus(returnStatus, status);
                    if(returnStatus.getSeverity() == IStatus.ERROR)
                        break;
                    else 
                        participants.add(renameParticipant);
                } catch (Exception exc) {
                    returnStatus = mergeStatus(returnStatus, new Status(
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

	private static IStatus mergeStatus(IStatus returnStatus, IStatus status) {
		if(returnStatus == null) { 
			return status;
		} else if(returnStatus instanceof MultiStatus) {
			((MultiStatus) returnStatus).add(status);
			return returnStatus;
		} else { 
			return new MultiStatus(status.getPlugin(), status.getCode(), status.getMessage(), status.getException());
		}
	}

	public static void synchronizeMaslEditors() {
		Display.getDefault().syncExec( new Runnable() {
			@Override
			public void run() {
				IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
				for(IEditorReference editorReference: editorReferences) {
					IEditorPart editor = editorReference.getEditor(false);
					if(editor instanceof XtextEditor) {
						IEditorInput editorInput = editor.getEditorInput();
						IDocumentProvider documentProvider = ((XtextEditor)editor).getDocumentProvider();
						if(documentProvider instanceof IDocumentProviderExtension) {
							try {
								((IDocumentProviderExtension)documentProvider).synchronize(editorInput);
							} catch(CoreException exc) {
								CorePlugin.getDefault().getLog().log(
										new Status(IStatus.ERROR, CorePlugin.getDefault().getBundle().getSymbolicName(), 
												"Error synchronizing editors after refactoring", exc));
							}
						}
					}
				}				
			}
		});
	}
	
}
