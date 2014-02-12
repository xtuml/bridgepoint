//========================================================================
//
//File:      $RCSfile: GraphicalEditDomain.java,v $
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

package com.mentor.nucleus.bp.ui.graphics.editor;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;

public class GraphicalEditDomain extends DefaultEditDomain {

	public GraphicalEditDomain(IEditorPart editorPart, Object modelElement) {
		super(editorPart);
		if(fCommandStack == null)
			getCommandStack();
		fCommandStack.setModelElement(modelElement);
	}
	
	private GraphicalCommandStack fCommandStack;

	@Override
	public CommandStack getCommandStack() {
		if(fCommandStack == null)
			fCommandStack = new GraphicalCommandStack();
		return fCommandStack;
	}

	@Override
	public void setCommandStack(CommandStack stack) {
		if(stack instanceof GraphicalCommandStack)
			super.setCommandStack(stack);
		else
			throw new IllegalArgumentException(
					"Only instances of GraphicalCommandStack can be used with this edit domain.");
	}

	public void setElement(Object represents) {
		fCommandStack.setModelElement(represents);
	}

	public GraphicalEditor getEditor() {
		return (GraphicalEditor) getEditorPart();
	}

}
