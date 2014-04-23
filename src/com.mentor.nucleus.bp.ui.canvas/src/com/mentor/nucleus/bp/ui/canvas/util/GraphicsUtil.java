//=====================================================================
//
//File:      $RCSfile: GraphicsUtil.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 23:19:03 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.canvas.util;

import com.mentor.nucleus.bp.core.Activity_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Communication_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Sequence_c;
import com.mentor.nucleus.bp.core.SubsystemInSubsystem_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.UseCaseDiagram_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.inspector.IModelClassInspector;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ElementInResize_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;

public class GraphicsUtil {
	public static String getCanvasEditorTitle(final NonRootModelElement element) {

		String title = CoreUtil.getName(element);
		if (title != null) {
			return title + getCanvasEditorPostTitle(element, true);		
		}
		return null;
	}
	
	public static String getCanvasEditorPostTitle(final NonRootModelElement element, boolean checkElementToEdit) {
		ModelSpecification_c[] modelSpecs = ModelSpecification_c
			.ModelSpecificationInstances(Ooaofgraphics
				.getDefaultInstance());
		Class clazz = element.getClass();
		if(checkElementToEdit) {
			Object elementToEdit = EditorUtil.getElementToEdit(element);
			if(elementToEdit.getClass() != clazz) {
				clazz = elementToEdit.getClass();
			}
		}
		for (int i = 0; i < modelSpecs.length; i++) {
			if (modelSpecs[i].getRepresents() == clazz) {
				return ": " + modelSpecs[i].getName();
		
			}
		}
		return "";
	}
    
    public static PersistableModelComponent getComponentOfElementInResize(final NonRootModelElement me) {
        Model_c model=null;
        if(me instanceof ElementInResize_c){
            final ElementInResize_c elementInResize=(ElementInResize_c) me;
            model=Model_c.ModelInstance(me.getModelRoot(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
               return ((Model_c)candidate).getDiagramid().equals(elementInResize.getDiagramidCachedValue());
            }
        
          },false);
        }
        
        if(model!=null)
            return model.getPersistableComponent();
        else
            return null;
    }
    public static int getOoaType(Object element){
        ModelSpecification_c[] modelSpecs = ModelSpecification_c.ModelSpecificationInstances(Ooaofgraphics.getDefaultInstance());
        for (int i = 0; i < modelSpecs.length; i++) {
          if (modelSpecs[i].getRepresents() == element.getClass()) {
              return modelSpecs[i].getOoa_type();
}
        }
        return -1;
    }
    public static int getElementOoaType(final Object element, Ooaofgraphics root){
    	GraphicalElement_c gElement = GraphicalElement_c.GraphicalElementInstance(root, new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				GraphicalElement_c elem = (GraphicalElement_c) candidate;
				if(elem.getRepresents() != null) {
					return elem.getRepresents().equals(element);
				}
				return false;
			}
		
		});
    	ElementSpecification_c oneGD_ESOnR10 = ElementSpecification_c.getOneGD_ESOnR10(gElement);
    	if(oneGD_ESOnR10 != null) {
    		return oneGD_ESOnR10.getOoa_type();
    	}
        return -1;
    }
    
    /*
     * Recursively traverse bottom-up building the "-" separated list of names
     * of the parent elements.
     */
    public static String getContainerList(NonRootModelElement elem, int ooaType) {
        String rVal = "";
        Object parent = null;
        ModelInspector inspector = new ModelInspector();
        IModelClassInspector classInspector = null;
        
        if(ooaType == Ooatype_c.DataTypePackage) {
            classInspector = inspector.getInspector(DataTypePackage_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.ExternalEntityPackage) {
            classInspector = inspector.getInspector(ExternalEntityPackage_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.FunctionPackage) {
            classInspector = inspector.getInspector(FunctionPackage_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.Subsystem) {
            Subsystem_c subsystemParent = Subsystem_c.getOneS_SSOnR41(SubsystemInSubsystem_c
                    .getOneS_SISOnR42((Subsystem_c) elem));
            if (subsystemParent != null) {
                parent = subsystemParent;
            } else {
                classInspector = inspector.getInspector(Subsystem_c.class);
                parent = classInspector.getParent(elem);
            }
        } else if(ooaType == Ooatype_c.Sequence) {
            classInspector = inspector.getInspector(Sequence_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.Communication) {
            classInspector = inspector.getInspector(Communication_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.UseCaseDiagram) {
            classInspector = inspector.getInspector(UseCaseDiagram_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.Activity) {
            classInspector = inspector.getInspector(Activity_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.StateMachine) {
            ModelClass_c obj = null;
            Class eClass = elem.getClass();
            if (eClass == ClassStateMachine_c.class) {
                obj = ModelClass_c.getOneO_OBJOnR519((ClassStateMachine_c) elem);
            }
            if (obj == null) {
                obj = ModelClass_c.getOneO_OBJOnR518((InstanceStateMachine_c) elem);
            }
            if (obj != null) {
                classInspector = inspector.getInspector(ModelClass_c.class);
                parent = classInspector.getParent(obj);
            }
        } else if(ooaType == Ooatype_c.Domain) {
            classInspector = inspector.getInspector(Domain_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.Component) {
            classInspector = inspector.getInspector(Component_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.ComponentPackage) {
            classInspector = inspector.getInspector(ComponentPackage_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.InterfacePackage) {
            classInspector = inspector.getInspector(InterfacePackage_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.Package) {
            classInspector = inspector.getInspector(Package_c.class);
            parent = classInspector.getParent(elem);
        } 
        
        if ( parent != null ) {
            NonRootModelElement parentElem = (NonRootModelElement) parent;
            Class parentClass = parent.getClass();
            if(parentClass == DataTypePackage_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.DataTypePackage) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == ExternalEntityPackage_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.ExternalEntityPackage) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == FunctionPackage_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.FunctionPackage) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Subsystem_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Subsystem) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Sequence_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Sequence) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Communication_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Communication) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == UseCaseDiagram_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.UseCaseDiagram) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Activity_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Activity) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Domain_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Domain) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Component_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Component) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == ComponentPackage_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.ComponentPackage) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == InterfacePackage_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.InterfacePackage) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else if(parentClass == Package_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Package) + parentElem.getName() + "-"; //$NON-NLS-1$
            } else {
                rVal = parentElem.getName() + "-";
            }
        }
        return rVal;

    }
   
    /*
     * Get the "-" separated list of names of the parent elements.
     */
    public static String getContainerList(Model_c model, Ooaofooa modelRoot) {
        int ooaType = model.getOoa_type();
        NonRootModelElement elem = (NonRootModelElement) Cl_c.Getinstancefromooa_id(modelRoot,
                model.getOoa_id(), ooaType);

        return getContainerList(elem, ooaType);
    }

}
