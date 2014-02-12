//========================================================================
//
//File:      $RCSfile: BPDebugModelPresentation.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 23:17:55 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.debug.ui;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataItemValue_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.LocalReference_c;
import com.mentor.nucleus.bp.core.LocalValue_c;
import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPProcess;
import com.mentor.nucleus.bp.debug.ui.model.BPStackFrame;
import com.mentor.nucleus.bp.debug.ui.model.BPThread;
import com.mentor.nucleus.bp.debug.ui.model.BPVariable;
import com.mentor.nucleus.bp.debug.ui.model.VerifierExceptionBreakpoint;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;


public class BPDebugModelPresentation implements IDebugModelPresentation {
    // Image identifiers
    private static final String IMAGE_TARGET_CONNECTED = "edit_oal.gif"; //$NON-NLS-1$
    private static final String IMAGE_TARGET_DISCONNECTED = "target_stopped.gif"; //$NON-NLS-1$
    private static final String IMAGE_THREAD_EXCEPTED_DOMAIN = "metadata/Domain.gif"; //$NON-NLS-1$
    private static final String IMAGE_THREAD_EXCEPTED_COMPONENT = "metadata/InstanceOfComponent.gif"; //$NON-NLS-1$
    private static final String IMAGE_THREAD_IMPORTED_COMPONENT = "metadata/InstanceOfImportedComponent.gif"; //$NON-NLS-1$
    private static final String IMAGE_THREAD_UNKNOWN = "ins_unknown.gif"; //$NON-NLS-1$
    private static final String IMAGE_THREAD_CURRENT = "ins_current.gif"; //$NON-NLS-1$
    private static final String IMAGE_PROCESS = "green-bp.gif"; //$NON-NLS-1$
    private static final String IMAGE_EVENT = "metadata/Event.gif"; //$NON-NLS-1$
    private static final String IMAGE_ERROR = "error.gif"; //$NON-NLS-1$

    protected HashMap attributes = new HashMap();
    protected HashMap<String,Image> images = new HashMap<String,Image>();

    public BPDebugModelPresentation() {
        super();
    }

    public void setAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    public Image getImage(Object element) {
        Image image = null;

        // Target
        if (element instanceof IDebugTarget) {
            IDebugTarget target = (IDebugTarget) element;
            if (target != null) {
            	image = images.get(IMAGE_TARGET_CONNECTED);
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor(IMAGE_TARGET_CONNECTED).createImage();
                    images.put(IMAGE_TARGET_CONNECTED, image);            		
            	}
            }
        }
        // Thread
        else if (element instanceof IThread) {
            IThread debugThread = (IThread) element;
            if (debugThread != null) {
				String imageString = IMAGE_THREAD_EXCEPTED_DOMAIN;
            	if(debugThread instanceof BPThread) {
            		Component_c component = Component_c
            			.getOneC_COnR2955(((BPThread) debugThread)
            						.getEngine());
            		if(component != null)
            			imageString = IMAGE_THREAD_EXCEPTED_COMPONENT;
            		else {
            			ComponentReference_c icomponent = ComponentReference_c
								.getOneCL_ICOnR2963(((BPThread) debugThread)
										.getEngine());
            			if(icomponent != null) {
            				imageString = IMAGE_THREAD_IMPORTED_COMPONENT;
            			}
            		}
            	}
            	image = images.get(imageString);
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor(imageString).createImage();
                    images.put(imageString, image);            		
            	}
            }
        }
        else if (element instanceof BPStackFrame) {
        	if (((BPStackFrame)element).isEvent()) {
            	image = images.get(IMAGE_EVENT);
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor(IMAGE_EVENT).createImage();
                    images.put(IMAGE_EVENT, image);            		
            	}
        	}
        }
        else if (element instanceof BPProcess) {
        	image = images.get(IMAGE_PROCESS);
        	if (image == null) {
                image = CorePlugin.getImageDescriptor(IMAGE_PROCESS).createImage();
                images.put(IMAGE_PROCESS, image);
        	}
        }
        else if (element instanceof BPVariable) {
            BPVariable debugVariable = (BPVariable) element;
            if (debugVariable.getType() == DataItemValue_c.class ) {
            	image = images.get("metadata/eventdata.gif");
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor("metadata/eventdata.gif").createImage();
                    images.put("metadata/eventdata.gif", image);
            	}
            }
            else if (debugVariable.getType() == LocalValue_c.class ) {
            	image = images.get("metadata/transient.gif");
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor("metadata/transient.gif").createImage();
                    images.put("metadata/transient.gif", image);
            	}
            }
            else if (debugVariable.getType() == LocalReference_c.class ) {
            	image = images.get("metadata/operation.gif");
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor("metadata/operation.gif").createImage();
                    images.put("metadata/operation.gif", image);
            	}
            }
            else {
            	image = images.get("metadata/attribute.gif");
            	if (image == null) {
                    image = CorePlugin.getImageDescriptor("metadata/attribute.gif").createImage();
                    images.put("metadata/attribute.gif", image);
            	}
            }
        }
        if ((image == null) || (image.isDisposed())) {
            return null;
        } else {
            return image;
        }
    }

    public String getText(Object element) {
        if (element instanceof BPProcess) {
        	BPProcess bpp = (BPProcess)element;
        	String result = "Verifier ";
        	
        	if (bpp.isTerminated()) {
        		result = "<terminated>" + result;
        	}
         	  return result + bpp.getLabel();
        }
    	if ( element instanceof BPBreakpoint ) {
    		return ((BPBreakpoint)element).getText();
    	}
    	if ( element instanceof VerifierExceptionBreakpoint) {
    		return ((VerifierExceptionBreakpoint)element).getLabel(element);
    	}
        return null;
    }

    public void computeDetail(IValue value, IValueDetailListener listener) {
        String detail = ""; //$NON-NLS-1$

        try {
            detail = value.getValueString();
        } catch (DebugException e) {
        }

        listener.detailComputed(value, detail);
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public void removeListener(ILabelProviderListener listener) {
    }

   public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public IEditorInput getEditorInput(Object element) {
		if (element instanceof IFile) {
			IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI.getWorkbench()
			.getElementFactory(com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput.FACTORY_ID);
			try {
			  ActivityEditorInput aei = (ActivityEditorInput)factory.createInstance(element);
			  return aei;
			}
			catch (CoreException ce) {
				DebugPlugin.log(ce);
			}
		}

        if (element instanceof ILineBreakpoint) {
            return new FileEditorInput((IFile) ((ILineBreakpoint) element).getMarker()
                                                .getResource());
        }

        return null;
    }

	public String getEditorId(IEditorInput input, Object element) {
      return ActivityEditorInput.EDITOR_ID;
	}
}
