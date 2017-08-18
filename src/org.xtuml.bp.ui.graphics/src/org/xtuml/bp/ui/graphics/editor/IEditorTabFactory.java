//========================================================================
//
//File:      $RCSfile: IEditorTabFactory.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:03 $
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

package org.xtuml.bp.ui.graphics.editor;

import org.eclipse.swt.widgets.Composite;

public interface IEditorTabFactory {
	public Composite createEditorTab(Composite parent, Object editorInput, String text);
	default public String getTabText() { return ""; }
	default public boolean created() {
		return true;
	}
	default public boolean isEnabled() {
		return true;
	}
	default public boolean isPreferenceControlled() {
		return false;
	}
	default public void setCreated(boolean created) {}
	default public void setTabText(String text) {}
}
