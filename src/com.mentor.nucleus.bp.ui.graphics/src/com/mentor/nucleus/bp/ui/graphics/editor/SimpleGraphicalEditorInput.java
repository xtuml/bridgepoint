//========================================================================
//
//File:      $RCSfile: SimpleGraphicalEditorInput.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:06:03 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;

public class SimpleGraphicalEditorInput implements IEditorInput {

	private Model_c model;

	public SimpleGraphicalEditorInput(Model_c model) {
		this.model = model;
	}
	
	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return Activator.imageDescriptorFromPlugin(CorePlugin.getDefault()
				.getBundle().getSymbolicName(), "icons/bp-green.gif");
	}

	@Override
	public String getName() {
		return "Simple Graphical Editor";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return getName();
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	public Model_c getModel() {
		return model;
	}

}
