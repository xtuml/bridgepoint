//========================================================================
//
//File:      $RCSfile: ModelEditor.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/13 19:02:46 $
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

package org.xtuml.bp.core.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.xtuml.bp.core.editors.focus.pages.MetamodelPage;
import org.xtuml.bp.core.ui.Selection;

public class ModelEditor extends MultiPageEditorPart {

	private List<Composite> pages = new ArrayList<Composite>();

	@Override
	protected void createPages() {
		MetamodelPage page = new MetamodelPage(getContainer(), Selection.getInstance());
		pages.add(page);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// no need
	}

	@Override
	public void doSaveAs() {
		// no need
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
}
