//=====================================================================
//
//File:      $RCSfile: ModelStructureDiffViewerCreator.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:49:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.compare.structuremergeviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class ModelStructureDiffViewerCreator implements IViewerCreator {

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		ModelStructureDiffViewer viewer = ModelStructureDiffViewer.getViewer(config);
		if(viewer != null) {
			// we only require one viewer per configuration
			return null;
		}
		//Creating the diff viewer
		return new ModelStructureDiffViewer(parent, config);
	}
}
