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
package com.mentor.nucleus.bp.io.mdl.tree;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SystemDatatypeInPackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.sorter.AlphaSorter;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.ui.explorer.adapters.ComponentPackagesAdapter;
import com.mentor.nucleus.bp.ui.explorer.adapters.DataTypePackagesAdapter;
import com.mentor.nucleus.bp.ui.explorer.adapters.DomainsAdapter;
import com.mentor.nucleus.bp.ui.explorer.adapters.InterfacePackagesAdapter;
import com.mentor.nucleus.bp.ui.explorer.adapters.PackagesAdapter;
import com.mentor.nucleus.bp.ui.explorer.adapters.RootAdapter;
import com.mentor.nucleus.bp.ui.explorer.adapters.SystemAdapter;

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
					if(children[i] instanceof DataTypePackage_c) {
						DataTypePackage_c dtPkg = (DataTypePackage_c) children[i];
						if (dtPkg.getName().equals(
								Ooaofooa.Getcoredatatypespackagename(Ooaofooa
										.getDefaultInstance()))) {
							if(datatypePackageContainsUDT(dtPkg)) {
								list.add(children[i]);
							}
						} else  {
							list.add(children[i]);
						}
					} else {
						list.add(children[i]);
					}
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
		if (element instanceof Domain_c) {
			return DomainsAdapter.getInstance().getParent(element);
		}
		if (element instanceof DataTypePackage_c) {
			return DataTypePackagesAdapter.getInstance().getParent(element);
		}
		if (element instanceof ComponentPackage_c) {
			return ComponentPackagesAdapter.getInstance().getParent(element);
		}
		if (element instanceof InterfacePackage_c) {
			return InterfacePackagesAdapter.getInstance().getParent(element);
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
	
	public static boolean datatypePackageContainsUDT(DataTypePackage_c dtPackage) {
		DataType_c edts[] = DataType_c.getManyS_DTsOnR17(EnumerationDataType_c
				.getManyS_EDTsOnR17(DataType_c
						.getManyS_DTsOnR4401(SystemDatatypeInPackage_c
								.getManySLD_SDINPsOnR4401(dtPackage))));
		if(edts.length != 0) {
			// return true if at least one enumeration data type
			return true;
		}
		DataType_c sdts[] = DataType_c.getManyS_DTsOnR17(StructuredDataType_c
				.getManyS_SDTsOnR17(DataType_c
						.getManyS_DTsOnR4401(SystemDatatypeInPackage_c
								.getManySLD_SDINPsOnR4401(dtPackage))));
		if(sdts.length != 0) {
			// return true if at least one structured data type
			return true;
		}
		DataType_c udts[] = DataType_c.getManyS_DTsOnR17(UserDataType_c
				.getManyS_UDTsOnR17(DataType_c
						.getManyS_DTsOnR4401(SystemDatatypeInPackage_c
								.getManySLD_SDINPsOnR4401(dtPackage)), new ClassQueryInterface_c() {
								
									public boolean evaluate(Object candidate) {
										return ((UserDataType_c)candidate).getGen_type() == 0;
									}
								
								}));
		if(udts.length != 0) {
			// return true if at least one user defined data type
			// that is not built in
			return true;
		}
		return false;
	}
}
