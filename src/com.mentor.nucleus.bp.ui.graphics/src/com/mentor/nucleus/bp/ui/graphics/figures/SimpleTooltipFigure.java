//====================================================================
//
//File:      $RCSfile: SimpleTooltipFigure.java,v $
//Version:   $Revision:$
//Modified:  $Date:$
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//========================================================================

package com.mentor.nucleus.bp.ui.graphics.figures;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.InlineFlow;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.SimpleTextLayout;
import org.eclipse.draw2d.text.TextFlow;

public class SimpleTooltipFigure extends FlowPage {
	    private final Border TOOLTIP_BORDER = new MarginBorder(3, 3, 3, 3);
	    private TextFlow descriptionText;
	     
	    public SimpleTooltipFigure() {
	   
	    	setOpaque(true);
	        setBorder(TOOLTIP_BORDER);
	        
	        descriptionText = new TextFlow("");
	        add(descriptionText);
	        
	    }
	     
	    @Override
	    public Dimension getPreferredSize(int w, int h) {
	        Dimension d =  super.getPreferredSize(-1 , -1);;
	        return d;
	    }
	     
	    public void setMessage(String txt) {
	        descriptionText.setText(txt);
	        revalidate();
	        repaint();
	    }
	}