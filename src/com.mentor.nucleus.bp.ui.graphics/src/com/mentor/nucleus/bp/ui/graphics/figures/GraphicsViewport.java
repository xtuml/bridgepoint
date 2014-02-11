//========================================================================
//
//File:      $RCSfile: GraphicsViewport.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:06:17 $
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
package com.mentor.nucleus.bp.ui.graphics.figures;

import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.geometry.Point;

public class GraphicsViewport extends FreeformViewport {

	private Point fViewLocationAtConfiguration = null;
	
	@Override
	protected void readjustScrollBars() {
		super.readjustScrollBars();
		FreeformFigure ff = (FreeformFigure)getContents();
		if(ff.getBounds().width > 100 && fViewLocationAtConfiguration != null) {
			setViewLocation(fViewLocationAtConfiguration);
			fViewLocationAtConfiguration = null;
		}
	}
	
	public void setViewportLocationOnceConfigured(Point newLocation) {
		fViewLocationAtConfiguration = newLocation;
	}
}
