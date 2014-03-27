//========================================================================
//
//File:      $RCSfile: GraphicsEditorListener.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 13:29:00 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.listeners;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditorInput;

public class GraphicsEditorListener extends ModelChangeAdapter implements ITransactionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.mentor.nucleus.bp.core.common.IModelChangeListener#
	 * isBatchedNotificationEnabled()
	 */
	public boolean isBatchedNotificationEnabled() {
		return false;
	}

	GraphicalEditor m_editor = null;
	/**
	 * Is set to true when a pre-reload-all arrives, to instruct this listener
	 * not to shut down its editor when the expected delete-all event arrives
	 * shortly thereafter. Instead, this listener will try to point its editor
	 * to the newly reloaded content.
	 */
	protected boolean ignoreNextDeleteAll = false;

	public GraphicsEditorListener(GraphicalEditor client) {
		m_editor = client;
	}

	@Override
	protected void performDefault(ModelChangedEvent event, IModelDelta delta) {
		// handle reload and load events, rather than setup a resource change
		// listener
		// note that all other refreshes occur at the end of a transaction
		if (m_editor.getTransactionManager() != null
				&& m_editor.getTransactionManager().getActiveTransaction() != null)
			return;
		if (event.getType() == Modeleventnotification_c.MODEL_ELEMENT_LOADED
				|| event.getType() == Modeleventnotification_c.MODEL_ELEMENT_RELOAD) {
			NonRootModelElement modelElement = (NonRootModelElement) event
					.getModelElement();
			if(modelElement == null)
				modelElement = (NonRootModelElement) delta.getModelElement();
			if (modelElement == m_editor.getModel().getRepresents()) {
				Model_c model = Model_c.ModelInstance(Ooaofgraphics.getInstance(modelElement.getModelRoot().getId()), new ClassQueryInterface_c() {
				
					@Override
					public boolean evaluate(Object candidate) {
						return ((Model_c) candidate).getOoa_id().equals(m_editor.getModel().getOoa_id());
					}
				});
				if(model != m_editor.getModel()) {
					m_editor.setModel(model);
					CanvasModelListener.setGraphicalRepresents(model);
					((GraphicalViewer) m_editor.getAdapter(GraphicalViewer.class)).setContents(model);
					m_editor.refresh();
				}
			}
		}
	}

	public void modelElementUnloaded(ModelChangedEvent event) {
		if (m_editor != null && m_editor.getCanvas() != null
				&& !m_editor.getCanvas().isDisposed()) {
			Object editorData = m_editor.getModel().getRepresents();
			if (event.getModelElement() == editorData) {
				if (ignoreNextDeleteAll) {
					ignoreNextDeleteAll = false;
					return;
				}

				closeEditor(m_editor);
			} else {
				GraphicalElement_c graph = getGraphicalElementInView(
						m_editor.getModel(), event.getModelElement());
				if (graph != null) {
					graph.setRepresents(null);
					m_editor.refresh();
				}
			}
		}
	}
	
	@Override
	public void systemAboutToBeDisabled(SystemModel_c system) {
		if (m_editor != null && m_editor.getCanvas() != null
				&& !m_editor.getCanvas().isDisposed()) {
			if (system.getFile().getParent().getFullPath().isPrefixOf(
					m_editor.getModel().getFile().getFullPath())) {
				closeEditor(m_editor);
			}
		}
	}

	// This method gives GraphicalElement_c that represents given object
	static public GraphicalElement_c getGraphicalElementInView(Model_c model,
			Object o) {
		if (o instanceof NonRootModelElement) {
			GraphicalElement_c[] ges = GraphicalElement_c
					.getManyGD_GEsOnR1(model);
			int type = GraphicsUtil.getElementOoaType(o, (Ooaofgraphics) model
					.getModelRoot());
			for (int j = 0; j < ges.length; j++) {
				if (((ges[j].getRepresents() != null && ges[j].getRepresents() == o) || (ges[j]
						.getOoa_id().equals(((NonRootModelElement) o)
						.Get_ooa_id())))
						&& ges[j].getOoa_type() == type) {
					return ges[j];
				}
			}
		}
		return null;
	}

	public void modelElementAboutToBeReloaded(ModelChangedEvent event) {
		if (m_editor != null && m_editor.getCanvas() != null
				&& !m_editor.getCanvas().isDisposed()) {
			Object editorData = m_editor.getModel().getRepresents();
			if (event.getModelElement() == editorData) {
				// this event signals that a delete-all event is forthcoming,
				// but this listener should ignore it, because it's caused
				// by a reload
				ignoreNextDeleteAll = true;
			}
		}
	}

	public void modelElementReloaded(ModelChangedEvent event) {
		if (m_editor != null && m_editor.getCanvas() != null
				&& !m_editor.getCanvas().isDisposed()) {
			// if the event concerns the element the editor is editing
			Object editorData = m_editor.getModel().getRepresents();
			if (event.getModelElement().equals(editorData)) {
				// try to locate the reloaded content of our editor,
				// and point our editor to it
				Model_c newModel = getModelFor((NonRootModelElement) event.getNewModelElement());
				if(newModel != null) {
					CanvasModelListener.setGraphicalRepresents(newModel);
					m_editor.updateModel(newModel);
					IEditorInput editorInput = m_editor.getEditorInput();
					if(editorInput instanceof GraphicalEditorInput) {
						((GraphicalEditorInput) editorInput).setInput(newModel);
					}
				} else {
					closeEditor(m_editor);
					return;
				}
				// reload is complete. If we didn't get a modelElementUnloaded
				// event, make sure the flag is cleared.
				ignoreNextDeleteAll = false;
			}
			// if the model tools are empty, we need to recreate them as well
			// as the GEF related tools
			ModelTool_c[] tools = ModelTool_c.getManyCT_MTLsOnR100(m_editor
					.getModel());
			if(tools.length == 0) {
				m_editor.refreshPalette();
			}
			CanvasModelListener.setGraphicalRepresents(m_editor.getModel());
		}
		m_editor.refresh();
	}

	private Model_c getModelFor(NonRootModelElement modelElement) {
		Model_c[] models = Model_c.ModelInstances(Ooaofgraphics
				.getInstance(modelElement.getModelRoot().getId()));
		for(int i = 0; i < models.length; i++) {
			if(models[i].getOoa_id().equals(modelElement.Get_ooa_id())) {
				return models[i];
			}
		}
		return null;
	}

	public void modelElementLoaded(ModelChangedEvent event) {
		if (m_editor != null && m_editor.getCanvas() != null
				&& !m_editor.getCanvas().isDisposed()) {
			boolean doRefresh = false;
			GraphicalElement_c graph = instanceInView(m_editor.getModel(), event
					.getModelElement());
			if (graph != null) {
				graph.setRepresents(event.getModelElement());
				doRefresh = true;
			}
			if (!doRefresh) {
				PersistableModelComponent pmc = ((NonRootModelElement) event
						.getModelElement()).getPersistableComponent();
				if (pmc != null) {
					PersistableModelComponent parent = pmc.getParent();
					if (parent != null) {
						NonRootModelElement parentME = parent
								.getRootModelElement();
						if (parentME instanceof ModelClass_c
								&& instanceInView(m_editor.getModel(), parentME) != null) {
							InstanceStateMachine_c ism = InstanceStateMachine_c
									.getOneSM_ISMOnR518(
											(ModelClass_c) parentME, false);
							ClassStateMachine_c csm = ClassStateMachine_c
									.getOneSM_ASMOnR519(
											(ModelClass_c) parentME, false);
							if (csm != null || ism != null) {
								doRefresh = true;
							}
						}
					}
				}
			}
			if (doRefresh) {
				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (window != null && window.getActivePage() != null) {
					if (window.getActivePage().getActiveEditor() == m_editor) {
						m_editor.refresh();
					}

				}
			}
			// always update the editor title
			m_editor.refreshPartName();
		}
	}

	// This method gives GraphicalElement_c that represents given object
	static public GraphicalElement_c instanceInView(Model_c model, Object o) {
		GraphicalElement_c[] ges = GraphicalElement_c.getManyGD_GEsOnR1(model);
		for (int j = 0; j < ges.length; j++) {
			ElementSpecification_c spec = ElementSpecification_c
					.getOneGD_ESOnR10(ges[j]);
			if (spec.getRepresents() == o.getClass()) {
				if (ges[j].getOoa_id().equals(Cl_c.Getooa_idfrominstance(o))) {
					return ges[j];
				}
			}
		}
		return null;
	}
	  
	public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
		if (m_editor != null && m_editor.getCanvas() != null
				&& !m_editor.getCanvas().isDisposed()) {
			ModelElement modelElement = delta.getModelElement();
			if (classInView(m_editor.getModel(), modelElement)) {
				m_editor.getModel().Elementcreated(modelElement,
						modelElement.getClass());
			}
		}
	}

	private void closeEditor(final GraphicalEditor editor) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().closeEditor(editor.getParentEditor(),
								false);	
			}
		});
	}

	// public and static for unit test visibility
	static public boolean classInView(Model_c canvas, Object o) {
		return CanvasModelListener.classInView(canvas, o);
	}

	@Override
	public void transactionCancelled(Transaction transaction) {
		// always run in the UI thread
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
		
			@Override
			public void run() {
				m_editor.refresh();
				m_editor.refreshPartName();
			}
		});
	}

	@Override
	public void transactionEnded(final Transaction transaction) {
		// always run in the UI thread
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
		
			@Override
			public void run() {
				handleDeleteDeltas(transaction);
				m_editor.refresh();
				m_editor.refreshPartName();
			}
		});
	}
	/**
	 * This method will step through the deltas from this
	 * transaction looking for a delete delta for the editors
	 * underlying model element
	 * 
	 * @param transaction
	 */
	private void handleDeleteDeltas(Transaction transaction) {
		ModelRoot[] participatingModelRoots = transaction
				.getParticipatingModelRoots();
		for (int i = 0; i < participatingModelRoots.length; i++) {
			IModelDelta[] deltas = transaction
					.getDeltas(participatingModelRoots[i]);
			for (int j = 0; j < deltas.length; j++) {
				if (deltas[j].getKind() == Modeleventnotification_c.DELTA_DELETE) {
					BaseModelDelta baseDelta = (BaseModelDelta) deltas[j];
					if (baseDelta.getModelElement() == m_editor.getModel()
							.getRepresents() || isSystemFor(baseDelta.getModelElement())) {
						closeEditor(m_editor);
					}
				}
			}
		}
	}

	private boolean isSystemFor(ModelElement modelElement) {
		if(modelElement instanceof SystemModel_c) {
			return Ooaofooa.getProjectNameFromModelRootId(
					m_editor.getModel().getModelRoot().getId()).equals(
					((SystemModel_c) modelElement).getName());
		}
		return false;
	}

	@Override
	public void transactionStarted(Transaction transaction) {
		// do nothing
	}

}
