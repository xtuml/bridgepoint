//=====================================================================
//
//File:      $RCSfile: GraphicalEditorFactory.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:06:02 $
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
//
package com.mentor.nucleus.bp.ui.graphics.editor;

import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.part.FileEditorInput;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.NullEditorInput;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;

public class GraphicalEditorFactory implements IElementFactory {
  public static final String CANVAS_FACTORY_ID = "com.mentor.nucleus.bp.ui.graphics.editorFactory"; //$NON-NLS-1$
  public static final String TAG_MODELROOTID = "modelRootId"; //$NON-NLS-1$
  public static final String TAG_MODELTYPE = "modelType"; //$NON-NLS-1$
  public static final String TAG_OOATYPE = "ooaType"; //$NON-NLS-1$
  public static final String TAG_OOAID = "ooaId"; //$NON-NLS-1$
  public static final String TAG_COMPONENTPATH = "componentPath"; //$NON-NLS-1$
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IElementFactory#createElement(org.eclipse.ui.IMemento)
   */
  public IAdaptable createElement(IMemento memento) {
    String rootId = memento.getString(TAG_MODELROOTID);
  	// if the modelRootID is of the old format
  	String[] parts = rootId.split("/");
  	if(parts.length == 3) {
  		// get an id which would match the new format
  		String projectName = parts[1];
  		String domainName = parts[2];
  		rootId = Ooaofooa.createModelRootId(projectName, domainName, false);
  	}
    Ooaofooa modelRoot = Ooaofooa.getInstance(rootId, false);
    String componentPath = memento.getString(TAG_COMPONENTPATH);
    if ( componentPath == null )
        return new NullEditorInput("Editor refers to old format Model element");
    PersistenceManager.loadAndFinishComponent(
            componentPath);
    modelRoot = Ooaofooa.getInstance(rootId, false);
    if (modelRoot != null) {
    	String raw_ooa_id = memento.getString(TAG_OOAID);
    	UUID ooa_id= IdAssigner.NULL_UUID;
    	if ( IdAssigner.isUUID(raw_ooa_id) ) {
    		ooa_id = UUID.fromString(raw_ooa_id);
    	}
    	
    	final int ooa_type = memento.getInteger(TAG_OOATYPE).intValue();
    	final Object nrme = Cl_c.Getinstancefromooa_id(modelRoot, ooa_id, ooa_type);
    	if (nrme != null) {
    		try {
    			GraphicalEditorInput cei = GraphicalEditorInput.createInstance(nrme);
    			return cei;
    		}
    		catch (Exception e) {
    			CorePlugin.logError("Exception creating Diagram Editor", e);
    		}
    	}
    }
    IFile dummyFile=ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(componentPath));
    FileEditorInput fi = new FileEditorInput(dummyFile);
    return fi; 
  }

}
