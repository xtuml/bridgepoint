package com.mentor.nucleus.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: OverlayCompositeImageDescriptor.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:40 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class OverlayCompositeImageDescriptor extends CompositeImageDescriptor {

	private ImageData overlay;
	private ImageData base;
	private Point SIZE = new Point(16, 16);

	public OverlayCompositeImageDescriptor(ImageData base, ImageData overlay) {
		this.base = base;
		this.overlay = overlay;
	}
	
	@Override
	protected void drawCompositeImage(int width, int height) {
		super.drawImage(base, 0, 0);
		super.drawImage(overlay, 0, 0);
	}

	@Override
	protected Point getSize() {
		return SIZE ;
	}

}
