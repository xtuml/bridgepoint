//====================================================================
//
//File:      $RCSfile: BPToolTipHelper.java,v $
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

package com.mentor.nucleus.bp.ui.graphics.editor;


import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mentor.nucleus.bp.ui.graphics.tooltip.DetailedToolTip;

public class BPToolTipHelper extends ToolTipHelper {

	
	// Eclipse passed argument 
	private IFigure currentTipSource;
	private IFigure BPtip;
	private int BPeventX;
	private int BPeventY;
	
	protected int TooltipStyle = SWT.NONE;
	
	// Tooltip elements
	private Shell BPSimpleTooltipShell;
	private DetailedToolTip detailedtooltip;
	private LightweightSystem lws;
	
	// Flow control fields
	private boolean tipDisplayed;
	private Timer mouseInCloseTimer;
	private Timer mouseOutCloseTimer;
	protected boolean showDetailedTooltip = false;
	private static final int tooltipLocationIndent = 5;
	private static final int DetailedTipWidthIncrement = 100;
	private static final int DetailedTipHeightIncrement = 50;
	
	// Shell Style fields
	// Default ToolTip Maximum Size
	public static final Dimension MaxPreferredTooltipSize = new Dimension(400, 175);
	// Default ToolTip Minimum Size
	public static final Dimension MinPreferredTooltipSize = new Dimension(75,25);
	
	private Dimension tipSize;
	
	
	public BPToolTipHelper(Control c) {
		super(c);
	}
	
	@Override
	public void displayToolTipNear(IFigure hoverSource, IFigure tip, int eventX, int eventY) {
		if (tip != null && !isShowing()) {
			if ( showDetailedTooltip && isShowing())
				return;
			
			setTooltipText(tip);
			Point displayPoint = computeWindowLocation(tip, eventX, eventY);
			setShellBounds(displayPoint.x, displayPoint.y, tipSize.width+30,
						tipSize.height+15);
			getShell().setFocus();
			show();
			updateEclipsePassedArgument(hoverSource, tip, eventX, eventY);
		}
	}
	
	private void updateEclipsePassedArgument(IFigure hoverSource, IFigure tip,
			int eventX, int eventY) {
		currentTipSource = hoverSource;
		BPtip = tip;
		BPeventX = eventX;
		BPeventY = eventY;	
	}
	
	public IFigure getTooltipSource(){
		return currentTipSource;
	}

	private void setTooltipText(IFigure tip) {
		if (!showDetailedTooltip){
			getLightweightSystem().setContents(tip);
		}else{
			if ( tip instanceof FlowPage){
			FlowPage flowgage = (FlowPage)tip;
			TextFlow textflow = (TextFlow)flowgage.getChildren().get(0);
			detailedtooltip.setDescriptionText(textflow.getText());
			} else{
				// Do nothing
			}
		}
	}

	private Point computeWindowLocation(IFigure tip, int eventX, int eventY) {
		FigureCanvas figureCanvas = (FigureCanvas)control;
		Dimension visibleArea = figureCanvas.getViewport().getSize();
		
		Point location = new Point(eventX + tooltipLocationIndent, eventY + tooltipLocationIndent);
		tip.invalidate();
		tipSize = tip.getPreferredSize(-1, -1);
		if ( showDetailedTooltip){
			tipSize.width = tipSize.width + DetailedTipWidthIncrement; 
			tipSize.height = tipSize.height + DetailedTipHeightIncrement; 
		}
		tipSize = adjustTooltipSizeIfNeeded(tipSize);
		
		if (location.y + tipSize.height - 200 > visibleArea.height)
			location.y = eventY - tipSize.height;

		if (location.x + tipSize.width - 300 > visibleArea.width)
			location.x = eventX - tipSize.width;
		if ( location.x < 0 || location.y < 0 ){
			return new Point(eventX, eventY);
		}
		return location;
	}
	
	private Dimension adjustTooltipSizeIfNeeded(Dimension tipSize) {
		if ( tipSize.height > MaxPreferredTooltipSize.height && tipSize.width > MaxPreferredTooltipSize.width )
			return MaxPreferredTooltipSize;
		else if ( tipSize.height > MaxPreferredTooltipSize.height )
			return new Dimension(tipSize.width, MaxPreferredTooltipSize.height);
		else if ( tipSize.width > MaxPreferredTooltipSize.width )
			return new Dimension(MaxPreferredTooltipSize.width, tipSize.height);
		else if (tipSize.width < MinPreferredTooltipSize.width && tipSize.height < MinPreferredTooltipSize.height )
			return MinPreferredTooltipSize;
		return tipSize;
	}

	@Override
	public void dispose() {
		if (isShowing()) {
			hide();
		}
		BPSimpleTooltipShell.dispose();
		if ( detailedtooltip != null){
			if (! detailedtooltip.getShell().isDisposed())
				detailedtooltip.getShell().dispose();
		}
	}
	
	@Override 
	protected void hookShellListeners(){
		// Skip
	}
	
	protected void hookSimpleShellListeners() {
		getShell().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				if (showDetailedTooltip)
					return;
				mouseOutCloseTimer = new Timer(true);
				mouseOutCloseTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								hide();
								mouseOutCloseTimer.cancel();
							}
						});
					}
				}, 50);
			}

			@Override
			public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
				if (showDetailedTooltip)
					return;
				if ( mouseInCloseTimer != null)
					mouseInCloseTimer.cancel();
				getShell().setVisible(true);
			}
		});
		
		getShell().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// Skip
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if ( BPtip instanceof org.eclipse.draw2d.Label){
					return;
				}
				hide();
				if (!showDetailedTooltip)
					showDetailedTooltip = true;
				detailedtooltip = createDetailedShell();
				displayToolTipNear(currentTipSource, BPtip, BPeventX, BPeventY);
				getShell().setActive();
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// Skip
			}
		});
	}
	
	private DetailedToolTip createDetailedShell() {
		return new DetailedToolTip(control, this);
	}
	
	@Override
	public void updateToolTip(IFigure figureUnderMouse, IFigure tip,
			int eventX, int eventY) {
	
		if (figureUnderMouse != null){
			if (!showDetailedTooltip)
				if ( mouseOutCloseTimer != null)
					mouseOutCloseTimer.cancel();
		}
		
		if (figureUnderMouse == null) {
			if (!showDetailedTooltip)
				if (isShowing()) {
					mouseInCloseTimer = new Timer(true);
					mouseInCloseTimer.schedule(new TimerTask() {
						@Override
						public void run() {
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									hide();
									mouseInCloseTimer.cancel();
								}
							});
						}
					}, 50);


				}
		}
		if ( figureUnderMouse == lws){
			return;
		}
		if (isShowing() && figureUnderMouse != currentTipSource && figureUnderMouse != null) {
			if (showDetailedTooltip)
				return;
			hide();
			displayToolTipNear(figureUnderMouse, tip, eventX, eventY);
		} else if (!isShowing() && figureUnderMouse != currentTipSource) {
			currentTipSource = null;	
		}
	}

	@Override
	protected Shell createShell() {
		return new Shell(control.getShell(), TooltipStyle);
	}
	
	@Override
	protected Shell getShell() {
		if (showDetailedTooltip){
			if ( detailedtooltip == null){
				detailedtooltip = createDetailedShell();
			}else if (detailedtooltip.getShell() == null || detailedtooltip.getShell().isDisposed() ) {
				detailedtooltip = createDetailedShell();
			}
			return detailedtooltip.getShell();
		}
			
		else{
			if (BPSimpleTooltipShell == null || BPSimpleTooltipShell.isDisposed() ) {
				BPSimpleTooltipShell = createShell();
				hookSimpleShellListeners();
			}
			return BPSimpleTooltipShell;
		}
	}
	

	@Override
	public void hide() {
		if (getShell() != null && !getShell().isDisposed())
		
		if (showDetailedTooltip){
			getShell().dispose();
			detailedtooltip = null;
		}else{
			getShell().setVisible(false);
		}
		tipDisplayed = false;
		showDetailedTooltip = false;
	}
	
	@Override
	protected Dimension getShellTrimSize() {
		Rectangle trim = getShell().computeTrim(0, 0, 0, 0);
		return new Dimension(trim.width, trim.height);
	}
	
	@Override
	public boolean isShowing() {
		return tipDisplayed;
	}
	
	@Override
	protected void show() {
		getShell().setVisible(true);
		tipDisplayed = true;
	}
	
	@Override
	protected LightweightSystem getLightweightSystem() {
		if (lws == null) {
			lws = createLightweightSystem();
			lws.setControl(getShell());
		}
		return lws;
	}
	
	public void deactivate(){
		if (tipDisplayed)
			getShell().setVisible(false);
		
	}
	public void activate(){
		if (tipDisplayed)
			getShell().setVisible(true);
	}

	public void hideSimpleToolTip() {
		if (!showDetailedTooltip && isShowing())
		{
			hide();
		}
		
	}
}
