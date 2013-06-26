//========================================================================
//
//File:      $RCSfile: GraphicalEditDomain.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:03 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
