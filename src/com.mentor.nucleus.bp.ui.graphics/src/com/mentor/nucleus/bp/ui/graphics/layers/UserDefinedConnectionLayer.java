//========================================================================
//
//File:      $RCSfile: UserDefinedConnectionLayer.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:06:01 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.layers;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;

import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.graphics.figures.DecoratedPolylineConnection;

public class UserDefinedConnectionLayer extends ConnectionLayer {

	private Layer_c model;
	private UserDefinedLayer udl;
	static final int FLAG_REALIZED = 1 << 31;
	
	public UserDefinedConnectionLayer(Layer_c model, UserDefinedLayer udl) {
		this.model = model;
		this.udl = udl;
	}
	
	public Layer_c getModel() {
		return model;
	}
	
	public String getName() {
		return model.getLayer_name();
	}
	
	@Override
	protected void paintChildren(Graphics graphics) {
		if (getEditing()) {
			// configure the graphics to use an alpha value
			graphics.setAdvanced(true);
			graphics.setAlpha(125);
			graphics.pushState();
		}
		super.paintChildren(graphics);
	}
	
	public boolean getEditing() {
		return udl.getEditing();
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return udl.isVisible();
	}
	
	@Override
	public void add(IFigure child, Object constraint, int index) {
		// disable realized flag, as GEF does not expect a
		// figure to be added to two parents (and fails if this flag
		// is true)
		setFlag(FLAG_REALIZED, false);
		super.add(child, constraint, index);
	}
	
	@Override
	public void remove(IFigure child) {
		// do not perform this, as it comes from GEF not
		// allow multiple parent figures, the removeInternal()
		// method below is the one to use if removal is desired
		return;
	}

	public void removeInternal(IFigure child) {
		// child should be a DecoratedPolylineConnection
		if(child instanceof DecoratedPolylineConnection) {
			((DecoratedPolylineConnection) child).setTemporaryParent(this);
		}
		super.remove(child);
		if(child instanceof DecoratedPolylineConnection) {
			((DecoratedPolylineConnection) child).setTemporaryParent(null);
		}
	}
	
}
