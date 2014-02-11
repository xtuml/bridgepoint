package com.mentor.nucleus.bp.ui.graphics.editor;
//=====================================================================
//
//File:      $RCSfile: GraphicalEditorInput.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:02 $
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
//This class is responsible for interfacing with the Eclipse editor
//launching framework. It is this class that enables Eclipse to check
//for an editor that is already open and focus it. It also provides
//Eclipse with the name to be shown on the editor tab.
//
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil;

public class GraphicalEditorInput extends FileEditorInput
    implements IEditorInput, IPersistableElement {
  private Model_c m_input = null;
  private Color m_canvasColor = null;
  private Object m_instance = null;
  public GraphicalEditorInput(Model_c canvas, Object instance, Color color) {
  	super(getUnderlyingFile(canvas));
    m_input = canvas;
    m_canvasColor = color;
    m_instance = instance;
  }

  public static GraphicalEditorInput createInstance(Object c_input) throws PartInitException {
    ModelSpecification_c[] modelSpecs = ModelSpecification_c.ModelSpecificationInstances(Ooaofgraphics.getDefaultInstance());
    for (int i = 0; i < modelSpecs.length; i++) {
      if (modelSpecs[i].getRepresents() == c_input.getClass()) {
        final UUID ooa_id = Cl_c.Getooa_idfrominstance(c_input);
        final int modelType = modelSpecs[i].getModel_type();
        String rootName = Cl_c.getModelRootname(c_input);
        if ( rootName != null )
        {
	        Ooaofgraphics modelRoot = Ooaofgraphics.getInstance(rootName);
	        Model_c canvas = Model_c.ModelInstance(modelRoot, new ClassQueryInterface_c() {
	          public boolean evaluate(Object candidate) {
	            Model_c selected = (Model_c) candidate;
	            return selected.getModel_type() == modelType && selected.getOoa_id().equals(ooa_id);
	          }
	        });
	        if (canvas != null) {
               CanvasModelListener.setGraphicalRepresents(canvas);
	          return new GraphicalEditorInput(canvas, c_input, modelSpecs[i].getBackground());
	        }
        }
      }
    }
    return null;
  }

  public boolean exists() {
    if (m_input != null) {
      if (m_input.getRepresents() != null) {
        Object represents = m_input.getRepresents();
        if (!((NonRootModelElement)represents).isOrphaned()) {
          return true;
        }
      }
    }
    return false;
  }
  public ImageDescriptor getImageDescriptor() {
    return null;
  }
  public String getName() {
      String m_canvasName = GraphicsUtil.getCanvasEditorTitle((NonRootModelElement) m_instance);
      if(m_canvasName==null)
          return "";
      else
          return m_canvasName;
    	
  }
  public Model_c getInput() {
    return m_input;
  }
  public Color getColor() {
    return m_canvasColor;
  }
  public IPersistableElement getPersistable() {
    if (exists()) {
      return this;
    }
    else {
      return null;
    }
  }
  public String getToolTipText() {
    return getName();
  }
  
  public Object getAdapter(Class adapter) {
	if (adapter.equals(IFile.class)) {
	  return getFile();
	}
	else {
      return null;
	}
  }
  public IFile getFile() {
	  PersistableModelComponent pmc = null;
	  Model_c mdl = getInput();
	  if (mdl != null) {
	  	pmc = getInput().getPersistableComponent();
	  }
	  
	  if ((mdl != null) && (pmc != null)) {
		  return getInput().getPersistableComponent().getFile();
	  } else {
		  return super.getFile();
	  }
  }
  public IPath getPath() {
	  return this.getFile().getLocation();
  }
  public boolean equals(Object o) {
    if (o instanceof GraphicalEditorInput) {
      return ((GraphicalEditorInput) o).m_input == m_input;
    }else if(o instanceof IFileEditorInput){
        return ((IFileEditorInput)o).getFile().equals(m_input.getFile());
    }
    return false;
  }
  /* (non-Javadoc)
   * @see org.eclipse.ui.IPersistableElement#getFactoryId()
   */
  public String getFactoryId() {
    return GraphicalEditorFactory.CANVAS_FACTORY_ID;
  }  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IPersistableElement#saveState(org.eclipse.ui.IMemento)
   */
  public void saveState(IMemento memento) {
    String root_id = m_input.getModelRoot().getId();
    Ooaofooa modelRoot = Ooaofooa.getInstance(root_id, false);
    
    memento.putString(GraphicalEditorFactory.TAG_MODELROOTID, m_input.getModelRoot().getId());
    memento.putString(GraphicalEditorFactory.TAG_OOAID, m_input.getOoa_id().toString());
    memento.putInteger(GraphicalEditorFactory.TAG_OOATYPE, m_input.getOoa_type());
    memento.putInteger(GraphicalEditorFactory.TAG_MODELTYPE, m_input.getModel_type());      
    NonRootModelElement nrme = (NonRootModelElement)m_input.getRepresents();
    if ( nrme == null ) {
        nrme = (NonRootModelElement)Cl_c.Getinstancefromooa_id(
            modelRoot, m_input.getOoa_id(), m_input.getOoa_type());
  	}
  
    // if there's no way to get a component path, then don't save the tag
    // CanvasFactory.createElement() will check for the tag's existence
    // before doing anything
    if ( nrme != null ) {
        memento.putString(GraphicalEditorFactory.TAG_COMPONENTPATH, nrme.getContent());
    }
  }
  
  private static IFile getUnderlyingFile(Model_c canvas)
  {
	    IFile result = ((NonRootModelElement) canvas.getRepresents()).getFile() ;
	    // result can be null during unit tests, when DEFAULT_TEST_MODELSPACE
	    // (__default_test_root) is used
	    if ( result != null )
	    {
	        return result;
	    }
		return new CanvasDummyIFile(result);
  }

  public void setInput(Model_c newInput) {
	  m_input = newInput;
  }

}