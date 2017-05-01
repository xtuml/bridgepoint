package org.xtuml.bp.ui.canvas;
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

import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.canvas.util.GraphicsUtil;

public class CanvasModelListener extends ModelChangeAdapter  {
	
	/* (non-Javadoc)
	 * @see org.xtuml.bp.core.common.IModelChangeListener#isBatchedNotificationEnabled()
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
	        		CanvasPlugin.setGraphicalRepresents(sysModel);
	        	}
	        }
		}
	}

	public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
		final NonRootModelElement modelElement = (NonRootModelElement) delta.getModelElement();
		TransactionManager tm = modelElement.getTransactionManager();
		if (tm != null) {
			Transaction at = tm.getActiveTransaction();
			if (at != null && !(at.getType().equals(Transaction.DEFAULT_TYPE)))
				return; // UNDO/REDO will revert transaction it self
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
		GraphicalElement_c graphicalElement = CanvasPlugin
				.getGraphicalElement(Ooaofgraphics.getInstance(modelElement.getModelRoot().getId()), modelElement);
        if(graphicalElement == null) {
        	// try the system root
        	graphicalElement = CanvasPlugin.getGraphicalElement(Ooaofgraphics.getDefaultInstance(), modelElement);
        }
        
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
		    	if(newMdls[i].isOrphaned()) {
		    		// there are some events received at
		    		// times where a persist is called first
		    		// this triggers listeners to receive 
		    		// orphaned elements
		    		continue;
		    	}
				CanvasPlugin.setGraphicalRepresents(newMdls[i]);
				newMdls[i].Elementdeleted(modelElement);
				UUID id = Cl_c.Getooa_idfrominstance(modelElement);
				int type = GraphicsUtil.getOoaType(modelElement);
				if (modelElement instanceof InstanceStateMachine_c)
					id = ((InstanceStateMachine_c) modelElement).getSm_idCachedValue();

				if (newMdls[i].getOoa_id().equals(id) && newMdls[i].getOoa_type() == type) {
					CanvasPlugin.setGraphicalRepresents(newMdls[i]);
					newMdls[i].setRepresents(modelElement);
					newMdls[i].Elementdeleted(modelElement);
				}
		        
		    }
		}
	}
	
	public void modelElementLoaded(ModelChangedEvent event) {
        NonRootModelElement modelElement = (NonRootModelElement) event.getModelElement();
        Ooaofgraphics mr = Ooaofgraphics.getInstance(modelElement.getModelRoot().getId());
        
        GraphicalElement_c ge = CanvasPlugin.getGraphicalElement(mr, modelElement);
                 if(ge!=null){
        	             // reset the represents for the entire diagram as the
        	             // element that was replaced may contain additional data
        	             // that requires refreshing, an example is the case of an
        	             // interface reference.  In this example the interface
        	             // reference was replaced as well and its represents
        	             // also requires updating
                	 CanvasPlugin.setGraphicalRepresents(Model_c.getOneGD_MDOnR1(ge));
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
				if (CanvasPlugin.classInView(mdls[i], modelElement)) {
					mdls[i].Elementchanged(modelElement);
				}
			}
		
		}		
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
