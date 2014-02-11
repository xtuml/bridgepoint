package com.mentor.nucleus.bp.core.ui.cells;
//========================================================================
//
//File:      $RCSfile: ICellProvider.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:39:58 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public interface ICellProvider {

	public CellEditor getCellEditor(NonRootModelElement element, Composite parent, ObjectElement data);

	public boolean supportsEdit(NonRootModelElement element,
			ObjectElement data, Composite parent);
	
	public String getValue(NonRootModelElement element, ObjectElement data);
	
	public void setValue(NonRootModelElement element, ObjectElement data, Object value);
}
