//=====================================================================
//
//File:      $RCSfile: ModelCheckedTreeContentProvider.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:35:11 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.io.mdl.tree;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.sorter.AlphaSorter;
import org.xtuml.bp.core.sorter.MetadataSortingManager;
import org.xtuml.bp.ui.explorer.adapters.PackagesAdapter;
import org.xtuml.bp.ui.explorer.adapters.RootAdapter;
import org.xtuml.bp.ui.explorer.adapters.SystemAdapter;

public class ModelCheckedTreeContentProvider implements ITreeContentProvider {

	public ModelCheckedTreeContentProvider(boolean considerSystemChildren) {
		fConsiderSystemChildren = considerSystemChildren;
	}

	protected static MetadataSortingManager.ISorter sorter = new AlphaSorter();

	public boolean fConsiderSystemChildren = false;

public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Ooaofooa) {
			return RootAdapter.getInstance().getChildren(parentElement);
		}
		if (fConsiderSystemChildren && parentElement instanceof SystemModel_c) {
			Object[] children = SystemAdapter.getInstance().getChildren(parentElement);
			ArrayList<Object> list = new ArrayList<Object>();
			for(int i = 0; i < children.length; i++) {
						list.add(children[i]);
				}
			return list.toArray(new Object[list.size()]);
		}
		return null;
	}	

	public Object getParent(Object element) {
		if (element instanceof Ooaofooa) {
			return RootAdapter.getInstance().getParent(element);
		}
		if (element instanceof SystemModel_c) {
			return SystemAdapter.getInstance().getParent(element);
		}
		if (element instanceof Package_c) {
			return PackagesAdapter.getInstance().getParent(element);
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof Ooaofooa) {
			return RootAdapter.getInstance().hasChildren(element);
		}
		if (fConsiderSystemChildren && element instanceof SystemModel_c) {
			return SystemAdapter.getInstance().hasChildren(element);
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Singleton model, nothing to do
	}

	public static void sort(Object[] elements) {
		sorter.sort(elements);
	}

	public void dispose() {
		// No resources allocated, nothing to dispose.
	}
	
}
