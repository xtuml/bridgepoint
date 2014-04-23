package com.mentor.nucleus.bp.ui.canvas;
//=====================================================================
//
//File:      $RCSfile: CanvasModelListener.java,v $
//Version:   $Revision: 1.33 $
//Modified:  $Date: 2013/01/17 03:33:47 $
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
//
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import com.mentor.nucleus.bp.core.Activity_c;
import com.mentor.nucleus.bp.core.Communication_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackageParticipant_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Sequence_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UseCaseDiagram_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil;

public class CanvasModelListener extends ModelChangeAdapter  {
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelChangeListener#isBatchedNotificationEnabled()
	 */
	public boolean isBatchedNotificationEnabled() {
		return false;
	}
	
	@Override
	public void modelElementAboutToBeDeleted(ModelChangedEvent event) {
		NonRootModelElement[] modelElements = null;
		if(event.getModelElement() instanceof ModelRoot) {
			// systems shall be chosen by the current selection
			modelElements = Selection.getInstance().getSelectedNonRootModelElements();
		} else {
			final NonRootModelElement modelElement = (NonRootModelElement) event.getModelElement();
			modelElements = new NonRootModelElement[] {modelElement};
		}
		for(int i = 0 ; i < modelElements.length; i++) {
	        // set graphical represents for the system in case the element being deleted
	        // is represented on a system level diagram
	        final SystemModel_c system = ((Ooaofooa) modelElements[i].getModelRoot()).getRoot();
	        if(system != null) {
	        	Model_c sysModel = Model_c.ModelInstance(Ooaofgraphics.getDefaultInstance(), new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
						return ((Model_c) candidate).getRepresents() == system;
					}
				});
	        	if(sysModel != null) {
	        		setGraphicalRepresents(sysModel);
	        	}
	        }
		}
	}

	public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
	    final NonRootModelElement modelElement = (NonRootModelElement) delta.getModelElement();
        TransactionManager tm = modelElement.getTransactionManager();
	    if(tm!=null){
	    	Transaction at = tm.getActiveTransaction();
	    	if(at != null && !(at.getType().equals(Transaction.DEFAULT_TYPE)))
	    		return; //UNDO/REDO will revert transaction it self   
        }
			
			ModelSpecification_c[] mss = ModelSpecification_c.ModelSpecificationInstances(Ooaofgraphics
									.getDefaultInstance());
					for (int i = 0; i < mss.length; i++) {
						if (mss[i].getRepresents() == modelElement.getClass()) {
							mss[i].Elementcreated(modelElement);
							break;
						}
					}
		}	
	
	public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
        final NonRootModelElement modelElement = (NonRootModelElement) delta.getModelElement();
        TransactionManager tm = modelElement.getTransactionManager();
	    if(tm!=null){
	    	Transaction at = tm.getActiveTransaction();
	    	if(at != null && !(at.getType().equals(Transaction.DEFAULT_TYPE)))
	    		return; //UNDO/REDO will revert transaction it self   
        }
        
        Model_c[] mdls = getGraphicsModels(event);
        Model_c[] newMdls = new Model_c[mdls.length + 1];
        GraphicalElement_c graphicalElement = getGraphicalElement(Ooaofgraphics.getDefaultInstance(), modelElement);
        
        Model_c sysModel = null;
        if(graphicalElement != null) {
        	sysModel = Model_c.getOneGD_MDOnR1(graphicalElement);
        	System.arraycopy(mdls, 0, newMdls, 0, mdls.length);
        	newMdls[mdls.length] = sysModel;
        } else {
        	newMdls = mdls;
        }
	    
		if (newMdls != null){
		    for (int i = 0; i < newMdls.length; i++) {            
		        if (classInView(newMdls[i], modelElement)){
		        	setGraphicalRepresents(newMdls[i]);
                    newMdls[i].Elementdeleted(modelElement);
                }else{
                    UUID id = Cl_c.Getooa_idfrominstance(modelElement);
		            int  type=GraphicsUtil.getOoaType(modelElement);
                    if(modelElement instanceof InstanceStateMachine_c)
                        id=((InstanceStateMachine_c)modelElement).getSm_idCachedValue();
                    
                    if(newMdls[i].getOoa_id().equals(id) && newMdls[i].getOoa_type()==type) {
                    	setGraphicalRepresents(newMdls[i]);
                    	newMdls[i].setRepresents(modelElement);
                    	newMdls[i].Elementdeleted(modelElement);
                    }
		        }
		    }
		}
	}
	
	public static void setGraphicalRepresents(final Model_c model) {
		Ooaofooa modelRoot = Ooaofooa.getInstance(model.getModelRoot().getId());
		if (model.getRepresents() == null) {
			ModelSpecification_c ms = ModelSpecification_c.getOneGD_MSOnR9(model);
			if (ms == null) {
				ms = ModelSpecification_c
						.ModelSpecificationInstance(Ooaofgraphics
								.getDefaultInstance(),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((ModelSpecification_c) candidate)
										.getModel_type() == model
										.getModel_typeCachedValue()
										&& ((ModelSpecification_c) candidate)
												.getOoa_type() == model
												.getOoa_typeCachedValue();
							}
						});

				if (ms != null) {
					ms.relateAcrossR9To(model);
				}
			}
			model.setRepresents(Cl_c.Getinstancefromooa_id(modelRoot,
					model.getOoa_id(), model.getOoa_type()));
		}
		ArrayList<GraphicalElement_c> ges = 
				new ArrayList<GraphicalElement_c>(
						Arrays.asList(GraphicalElement_c.getManyGD_GEsOnR1(model)));
		ges.addAll(Arrays.asList(GraphicalElement_c.getManyGD_GEsOnR32(
				ElementInSuppression_c.getManyGD_EISsOnR32(model))));
		for (Iterator<GraphicalElement_c> it = ges.iterator(); it.hasNext();) {
			final GraphicalElement_c ge = it.next();
			ElementSpecification_c es = ElementSpecification_c.getOneGD_ESOnR10(ge);
			if (es == null) {
				ElementSpecification_c spec = ElementSpecification_c
						.ElementSpecificationInstance(Ooaofgraphics
								.getDefaultInstance(),
								new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((ElementSpecification_c) candidate)
												.getOoa_type() == ge
												.getOoa_typeCachedValue();
									}
								});
				if (spec != null) {
					spec.relateAcrossR10To(ge);
				}
			}
			Object instanceFromId = null;
			if (model.getRepresents() instanceof SystemModel_c) {
				instanceFromId = Cl_c.Getinstancefromooa_id(
						(SystemModel_c) model.getRepresents(), ge.getOoa_id(),
						ge.getOoa_type());
			} else {
				instanceFromId = Cl_c.Getinstancefromooa_id(modelRoot,
						ge.getOoa_id(), ge.getOoa_type());
			}
			if (instanceFromId != null
					&& (ge.getRepresents() == null || ge.getRepresents() != instanceFromId)) {
				ge.setRepresents(instanceFromId);
			}
		}
	}
	
    public static boolean classInView(Model_c canvas,
			Object o) {
		if (canvas != null) {
			if (canvas.getRepresents() == null) {
				if (Cl_c.Getooa_idfrominstance(o).equals(canvas.getOoa_id())
						&& GraphicsUtil.getOoaType(o) == canvas.getOoa_type())
					return true;
			} else {
				if (canvas.getRepresents() == o)
					return true;
			}
			ModelSpecification_c ms = ModelSpecification_c
					.getOneGD_MSOnR9(canvas);
			ElementInModelSpecification_c[] ems_set = ElementInModelSpecification_c
					.getManyGD_EMSsOnR11(ms);
			ElementSpecification_c[] es_set = ElementSpecification_c
					.getManyGD_ESsOnR11(ems_set);
			ClientClassDependency_c[] ccd_set = ClientClassDependency_c
					.getManyGD_CCDsOnR17(es_set);
			final Class<?> depClass = o.getClass();
			for (int i = 0; i < ccd_set.length; ++i) {
				if (ccd_set[i].getRepresents() == depClass) {
					return true;
				}
			}
			// Special casing to bridge the transition away from specialized packages.
			// Remove this code when SPs are gone.  The following comment is our search hook
			// that should land the SP remover here.  After removing this special case, don't
			// forget to "Organize imports" (Ctrl-Shift-O) to remove unnecessary imports.
			// PE_PE navigation is present (isInGenericPackage).  Do not remove this comment.
			if (canvas.getRepresents() instanceof Domain_c ) {
			    if (depClass == Subsystem_c.class) { return true; }
                if (depClass == ExternalEntityPackage_c.class) { return true; }
                if (depClass == FunctionPackage_c.class) { return true; }
                if (depClass == DataTypePackage_c.class) { return true; }    
                if (depClass == Sequence_c.class) { return true; }
                if (depClass == Communication_c.class) { return true; }
                if (depClass == UseCaseDiagram_c.class) { return true; }
                if (depClass == Activity_c.class) { return true; }
                if (depClass == ModelClass_c.class) { return true; }
            }
			if (canvas.getRepresents() instanceof SystemModel_c ) {
                if (depClass == Package_c.class) { return true; }
                if (depClass == DataTypePackage_c.class) { return true; }    
                if (depClass == InterfacePackage_c.class) { return true; }
                if (depClass == ComponentPackage_c.class) { return true; }
                if (depClass == Sequence_c.class) { return true; }
                if (depClass == Communication_c.class) { return true; }
                if (depClass == UseCaseDiagram_c.class) { return true; }
                if (depClass == Activity_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof ComponentPackage_c ) {
                if (depClass == ComponentPackage_c.class) { return true; }
                if (depClass == InterfacePackage_c.class) { return true; }
                if (depClass == Sequence_c.class) { return true; }
                if (depClass == Communication_c.class) { return true; }
                if (depClass == UseCaseDiagram_c.class) { return true; }
                if (depClass == Activity_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof Component_c ) {
                if (depClass == InterfacePackage_c.class) { return true; }
                if (depClass == Sequence_c.class) { return true; }
                if (depClass == Communication_c.class) { return true; }
                if (depClass == UseCaseDiagram_c.class) { return true; }
                if (depClass == Activity_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof InterfacePackage_c ) {
                if (depClass == InterfacePackage_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof Activity_c ) {
                if (depClass == Activity_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof Subsystem_c ) {
                if (depClass == Subsystem_c.class) { return true; }
                if (depClass == Sequence_c.class) { return true; }
                if (depClass == Communication_c.class) { return true; }
                if (depClass == UseCaseDiagram_c.class) { return true; }
                if (depClass == Activity_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof Sequence_c ) {
                if (depClass == Sequence_c.class) { return true; }
                if (depClass == FunctionPackageParticipant_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof Communication_c ) {
                if (depClass == Communication_c.class) { return true; }
                if (depClass == FunctionPackageParticipant_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof UseCaseDiagram_c ) {
                if (depClass == UseCaseDiagram_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof DataTypePackage_c ) {
                if (depClass == DataTypePackage_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof FunctionPackage_c ) {
                if (depClass == FunctionPackage_c.class) { return true; }
            }
            if (canvas.getRepresents() instanceof ExternalEntityPackage_c ) {
                if (depClass == ExternalEntityPackage_c.class) { return true; }
            }
			// end special casing
		}
		return false;
	}
	
	public void modelElementLoaded(ModelChangedEvent event) {
        NonRootModelElement modelElement = (NonRootModelElement) event.getModelElement();
        Ooaofgraphics mr = Ooaofgraphics.getInstance(modelElement.getModelRoot().getId());
        
        GraphicalElement_c ge = getGraphicalElement(mr, modelElement);
                 if(ge!=null){
        	             // reset the represents for the entire diagram as the
        	             // element that was replaced may contain additional data
        	             // that requires refreshing, an example is the case of an
        	             // interface reference.  In this example the interface
        	             // reference was replaced as well and its represents
        	             // also requires updating
        	         	setGraphicalRepresents(Model_c.getOneGD_MDOnR1(ge));
        	         }        

    }
	public void modelElementRelationChanged(ModelChangedEvent event, IModelDelta delta) {
		handleElementChanged(event, delta);
		}
	
	public void modelElementAttributeChanged(ModelChangedEvent event, IModelDelta delta) {
		handleElementChanged(event, delta);
	}
	
	public void modelRootChanged(ModelChangedEvent event) {
		NonRootModelElement element = (NonRootModelElement) event
				.getModelElement();
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(event.getOldRootId());
		Model_c[] models = Model_c.ModelInstances(graphicsRoot);
		for(int i = 0; i < models.length; i++) {
			if(models[i].getRepresents() == element) {
				Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(models[i]);
				diagram.updateModelRoot(Ooaofgraphics.getInstance(element.getModelRoot().getId()));
				models[i].updateModelRoot(Ooaofgraphics.getInstance(element.getModelRoot().getId()));
			}
		}
	}
	
	private void handleElementChanged(ModelChangedEvent event, IModelDelta delta){
		Model_c mdls[] = getGraphicsModels(event);
		if (mdls != null){
			ModelElement modelElement = delta.getModelElement();
			
			for (int i = 0; i < mdls.length; i++) {
				if (classInView(mdls[i], modelElement)) {
					mdls[i].Elementchanged(modelElement);
				}
			}
		
		}		
	}
    
    static public GraphicalElement_c getGraphicalElement(Ooaofgraphics modelRoot, Object o){   
        GraphicalElement_c[] ges = GraphicalElement_c.GraphicalElementInstances(modelRoot);
        int type=GraphicsUtil.getElementOoaType(o, modelRoot);
        for (int j = 0; j < ges.length; j++) {
			if ((ges[j].getRepresents() != null && ges[j].getRepresents() == o)
					|| (ges[j].getOoa_id().equals(
							((NonRootModelElement) o).Get_ooa_id()) && ges[j]
							.getOoa_type() == type)) {
                return ges[j];
            }
        }
        return null;
    }
    
	private Model_c[] getGraphicsModels(ModelChangedEvent event){
		ModelElement me = event.getModelElement();
		if(me == null)
			me = event.getModelDelta().getModelElement();
		if(me instanceof NonRootModelElement) {
			NonRootModelElement nrme = (NonRootModelElement) me;
			String rootName = nrme.getModelRoot().getId();
			if (rootName != null){
				Ooaofgraphics graphicsModelRoot = Ooaofgraphics
				.getInstance(rootName);
				return Model_c.ModelInstances(graphicsModelRoot);
			}
		}
		return null;
	}		
  
  protected Ooaofgraphics getAssociatedGraphics(Ooaofooa modelRoot){
  	return Ooaofgraphics.getInstance(modelRoot.getId());
  }

}
