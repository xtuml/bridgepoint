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
package org.xtuml.bp.ui.canvas.util;

import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.util.CoreUtil;
import org.xtuml.bp.core.util.EditorUtil;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.ElementInResize_c;
import org.xtuml.bp.ui.canvas.ElementSpecification_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.ModelSpecification_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Ooatype_c;

public class GraphicsUtil {
	public static String getCanvasEditorTitle(final NonRootModelElement element) {

		String title = CoreUtil.getName(element);
		if (title != null) {
			return title;	
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
        
        if(ooaType == Ooatype_c.StateMachine) {
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
        } else if(ooaType == Ooatype_c.Component) {
            classInspector = inspector.getInspector(Component_c.class);
            parent = classInspector.getParent(elem);
        } else if(ooaType == Ooatype_c.Package) {
            classInspector = inspector.getInspector(Package_c.class);
            parent = classInspector.getParent(elem);
        } 
        
        if ( parent != null ) {
            NonRootModelElement parentElem = (NonRootModelElement) parent;
            Class parentClass = parent.getClass();
            if(parentClass == Component_c.class) {
                rVal = getContainerList(parentElem, Ooatype_c.Component) + parentElem.getName() + "-"; //$NON-NLS-1$
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
