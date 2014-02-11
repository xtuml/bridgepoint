//========================================================================
//
//File:      $RCSfile: OffsetImageFigure.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:16 $
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

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

public class OffsetImageFigure extends ImageFigure {
	int xOffset = 0;
	int yOffset = 0;
	
	public OffsetImageFigure(Image image, int xOffset, int yOffset) {
		super(image);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		if (isOpaque())
			graphics.fillRectangle(getBounds());
		if (getBorder() instanceof AbstractBackground)
			((AbstractBackground) getBorder())
					.paintBackground(this, graphics,
							NO_INSETS);
		
		if (getImage() == null)
			return;

		// draw the image at the given position + the parent
		// position
		graphics.drawImage(getImage(), getBounds().x, getBounds().y);
	}

	@Override
	public void setBounds(Rectangle value) {
		super.setBounds(value);
	}
	
	@Override
	public Rectangle getBounds() {
		if(getParent() == null)
			return bounds;
		Rectangle temp = getParent().getBounds().getCopy();
		temp.x = temp.x + xOffset;
		temp.y = temp.y + yOffset;
		temp.width = getImage().getBounds().width;
		temp.height = getImage().getBounds().height;
		return temp;
	}
	
	
}
