package com.mentor.nucleus.bp.ui.graphics.parts;

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