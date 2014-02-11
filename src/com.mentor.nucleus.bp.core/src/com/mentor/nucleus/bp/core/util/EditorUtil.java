//=====================================================================
//
//File:      $RCSfile: EditorUtil.java,v $
//Version:   $Revision: 1.22 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.core.util;

import java.util.Iterator;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ActorParticipant_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionPackageParticipant_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.Lifespan_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;

/**
 * Holds utility methods that apply in some way to all editors
 * used in the product.
 */
public class EditorUtil
{
    /**
     * Returns the editor that is currently active in the workbench.
     */
    public static IEditorPart getCurrentEditor()
    {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().
            getActivePage().getActiveEditor();
    }
    
    /**
     * Closes the given editor, without saving its contents.
     */
    public static void closeEditor(IEditorPart editor)
    {
        editor.getSite().getPage().closeEditor(editor, false);
    }
    
    /**
     * Closes all editors that are open in the active workbench page.
     */
    public static void closeAllOpenEditors()
    {
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
            closeAllEditors(false);
    }
    
    /**
     * Callers to pointEditorToReloadedContent() below must 
     * implement this interface. 
     */
    public interface CallerToPointEditorToReloadedContent
    {
        /**
         * Creates an editor-input for the given editor that represents the 
         * given model-element.
         */
        IEditorInput createInput(IEditorPart editor, NonRootModelElement element) 
            throws Exception;
        
        /**
         * Sets the given input into the given editor.
         */
        void setInput(IEditorPart editor, IEditorInput input);
    }
    
    /**
     * Tries to find an updated version (under the given model-root)
     * of the old model-element given, and switches the given editor to 
     * edit that updated version.  The old model-element may be null
     * (which represents the case where the element does not exist in 
     * the reloaded model), in which case the editor will simply be closed.
     * 
     * @param caller    See parameter type.  
     */
    public static void pointEditorToReloadedContent(IEditorPart editor, 
        NonRootModelElement oldElement, NonRootModelElement newElement,  ModelRoot modelRoot,
        CallerToPointEditorToReloadedContent caller)
    {
        // if we were given an old-element that the editor was editing
        if (oldElement != null && newElement == null) {
            // for each new (from the reload) instance of the 
            // model element type of the element the editor is editing
            InstanceList list = modelRoot.getInstanceList(oldElement.getClass());
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                NonRootModelElement element = (NonRootModelElement) iter.next();
                
                // if this instance is the reloaded version of the 
                // element the editor is editing
                if (element.equals(oldElement)) {
                    // we've found a match
                    newElement = element;
                    break;
                }
            }
        }
            
        // if a reloaded version of the element this editor is editing
        // was found above, or passed in
        if (newElement != null) {
            // create a new editor input using the reloaded version
            IEditorInput newInput;
            try {
                newInput = caller.createInput(editor, newElement);
            } catch (Exception ex) {
                CorePlugin.logError("Could not create editor input for reloaded model element", ex);
                return;
            }
            
            // set the new input into the editor
            caller.setInput(editor, newInput);
        }
        
        // otherwise
        else {
            // close the editor, since we could not find a replacement
            // for the editor's element; the element might no longer
            // exist in the changed model-file
            editor.getSite().getPage().closeEditor(editor, false);
        }
    }
    
    /**
     * Returns the actual model element to edit when the given element
     * is selected for editing.
     */
    public static Object getElementToEdit(Object forElement)
    {
        // special case: if the given element is a model-class
        if (forElement instanceof ModelClass_c) {
            // edit the class's associated instance state 
            // machine, instead
            ModelClass_c clazz = (ModelClass_c)forElement;
            ModelElement editee = 
                InstanceStateMachine_c.getOneSM_ISMOnR518(clazz);
            
            // if there is no associated instance state machine
            if (editee == null) {
                // edit the class's associated class state 
                // machine, instead
                editee = ClassStateMachine_c.getOneSM_ASMOnR519(clazz);
            }
            if(editee != null)
            return editee;
        } else if(forElement instanceof Component_c) {
        	Component_c component = (Component_c) forElement;
        	Domain_c domain = Domain_c.getOneS_DOMOnR4204(DomainAsComponent_c
					.getOneCN_DCOnR4204(component));
        	if(domain != null) {
        		return domain;
        	}
        } else if(forElement instanceof Domain_c) {
        	Component_c component = Component_c
					.getOneC_COnR4204(DomainAsComponent_c
							.getOneCN_DCOnR4204((Domain_c) forElement));
        	if(component != null) {
        		return component;
        	}
        } else if(forElement instanceof Lifespan_c) {
        	// if a lifespan was double clicked, open
        	// the element that it is attached to
        	InteractionParticipant_c part = InteractionParticipant_c
					.getOneSQ_POnR940((Lifespan_c) forElement);
        	ComponentParticipant_c cop = ComponentParticipant_c
					.getOneSQ_COPOnR930(part);
        	if(cop != null) {
        		return getElementToEdit(cop);
        	}
        	ActorParticipant_c ap = ActorParticipant_c.getOneSQ_APOnR930(part);
        	if(ap != null) {
        		return getElementToEdit(ap);
        	}
        	ExternalEntityParticipant_c eep = ExternalEntityParticipant_c
					.getOneSQ_EEPOnR930(part);
        	if(eep != null) {
        		return getElementToEdit(eep);
        	}
        	FunctionPackageParticipant_c fpp = FunctionPackageParticipant_c
					.getOneSQ_FPPOnR930(part);
        	if(fpp != null) {
        		return getElementToEdit(fpp);
        	}
        	PackageParticipant_c pp = PackageParticipant_c
			.getOneSQ_PPOnR930(part);
	          if(pp != null) {
		        return getElementToEdit(pp);
	        }
        	ClassParticipant_c cp = ClassParticipant_c.getOneSQ_CPOnR930(part);
        	if(cp != null) {
        		return getElementToEdit(cp);
        	}
        	ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
					.getOneSQ_CIPOnR930(part);
        	if(cip != null) {
        		return getElementToEdit(cip);
        	}
        } else if(forElement instanceof ComponentParticipant_c) {
        	Component_c comp = Component_c
					.getOneC_COnR955((ComponentParticipant_c) forElement);
        	if(comp != null) {
        		return getElementToEdit(comp);
        	}
        } else if(forElement instanceof ExternalEntityParticipant_c) {
        	ExternalEntity_c ee = ExternalEntity_c
					.getOneS_EEOnR933((ExternalEntityParticipant_c) forElement);
        	if(ee != null) {
        		return getElementToEdit(ee);
        	}
        } else if(forElement instanceof FunctionPackageParticipant_c) {
        	FunctionPackage_c fp = FunctionPackage_c
					.getOneS_FPKOnR932((FunctionPackageParticipant_c) forElement);
        	if(fp != null) {
        		return getElementToEdit(fp);
        	}
        } else if(forElement instanceof ClassParticipant_c) {
        	ModelClass_c clazz = ModelClass_c
					.getOneO_OBJOnR939((ClassParticipant_c) forElement);
        	if(clazz != null) {
        		return getElementToEdit(clazz);
        	}
        } else if(forElement instanceof ClassInstanceParticipant_c) {
        	ModelClass_c clazz = ModelClass_c
					.getOneO_OBJOnR934((ClassInstanceParticipant_c) forElement);
        	if(clazz != null) {
        		return getElementToEdit(clazz);
        	}
        } else if (forElement instanceof PackageParticipant_c) {
			Package_c pkg = Package_c.getOneEP_PKGOnR956((PackageParticipant_c) forElement);
			if (pkg != null) {
				return getElementToEdit(pkg);
			}
		} else if (forElement instanceof ComponentReference_c) {
			Component_c component = Component_c.getOneC_COnR4201((ComponentReference_c) forElement);
			if (component != null) {
				// We call into this routine recursively because we do not necessarily 
				// always want to open the assigned component.  For example,
				// in specialized packages, if this is a formalized component we will
				// open the domain package diagram of the domain this component is
				// formalized to.
				return getElementToEdit(component);
			}
		}
        
        return forElement;
    }
}
