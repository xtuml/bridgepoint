package com.mentor.nucleus.bp.ui.graphics.editor;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class BPToolTipHelper extends ToolTipHelper {

	private Timer timer;
	private IFigure currentTipSource;
	private boolean tooltipClicked = false;
	@Override
	public void displayToolTipNear(IFigure hoverSource, IFigure tip,
			int eventX, int eventY) {
		if (tip != null && hoverSource != currentTipSource) {
			getLightweightSystem().setContents(tip);
			Point displayPoint = computeWindowLocation(tip, eventX, eventY);
			Dimension shellSize = getLightweightSystem().getRootFigure()
					.getPreferredSize().getExpanded(getShellTrimSize());
			setShellBounds(displayPoint.x, displayPoint.y, 300,
					150);
//			getShell()
//			getLightweightSystem().getRootFigure() .getPreferredSize().setSize(10, 10);
//			getLightweightSystem().getRootFigure().setFocusTraversable(true);
//			getLightweightSystem().getRootFigure().setMaximumSize(new Dimension(11, 11));
			//getShell().setMenuBar(new Menu(getShell()));
//			getShell().setActive();
			getShell().setFocus();
			show();
			currentTipSource = hoverSource;
			timer = new Timer(true);
			timer.schedule(new TimerTask() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							hide();
							timer.cancel();
						}
					});
				}
			}, 50000);
		}
	}
	
	private Point computeWindowLocation(IFigure tip, int eventX, int eventY) {
		org.eclipse.swt.graphics.Rectangle clientArea = control.getDisplay()
				.getClientArea();
		Point preferredLocation = new Point(eventX, eventY + 26);

		Dimension tipSize = getLightweightSystem().getRootFigure()
				.getPreferredSize().getExpanded(getShellTrimSize());

		// Adjust location if tip is going to fall outside display
		if (preferredLocation.y + tipSize.height > clientArea.height)
			preferredLocation.y = eventY - tipSize.height;

		if (preferredLocation.x + tipSize.width > clientArea.width)
			preferredLocation.x -= (preferredLocation.x + tipSize.width)
					- clientArea.width;
		if ( preferredLocation.x < 0 || preferredLocation.y < 0 ){
			new Point(eventX, eventY + 26);
		}
		return preferredLocation;
	}

	@Override
	public void dispose() {
		if (isShowing()) {
			timer.cancel();
			hide();
		}
		getShell().dispose();
	}

	@Override
	protected void hookShellListeners() {
//		// Close the tooltip window if the mouse enters the tooltip
//		getShell().addMouseTrackListener(new MouseTrackAdapter() {
//			public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
////				hide();
////				currentTipSource = null;
//				if (timer != null) {
////					timer.cancel();
//				}
//			}
//		});
		getShell().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				int a = 1;
				int b = a;
				int c = b + a;
				a = b + c;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				tooltipClicked = true;
				getShell().setActive();
				getShell().setFocus();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				int a = 1;
				int b = a; 
				int c = b + a;
				a = b + c;
			}
		});
		getShell().addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				hide();
				timer.cancel();	
				tooltipClicked = false;
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void updateToolTip(IFigure figureUnderMouse, IFigure tip,
			int eventX, int eventY) {
		/*
		 * If the cursor is not on any Figures, it has been moved off of the
		 * control. Hide the tool tip.
		 */
		if (figureUnderMouse == null) {
			if (isShowing()) {
//				hide();
//				timer.cancel();
			}
		}
		// Makes tooltip appear without a hover event if a tip is currently
		// being displayed
		getLightweightSystem();
//		if ( figureUnderMouse == lws){
//			return;
//		}
		if (isShowing() && figureUnderMouse != currentTipSource && figureUnderMouse != null) {
			if (tooltipClicked)
				return;
			hide();
			timer.cancel();
			displayToolTipNear(figureUnderMouse, tip, eventX, eventY);
		} else if (!isShowing() && figureUnderMouse != currentTipSource)
			currentTipSource = null;
	}

	public BPToolTipHelper(Control c) {
		super(c);
//		getShell().
	}
	
	@Override
	protected Shell createShell() {
		return new Shell(control.getShell(), SWT.RESIZE );
	}

}
