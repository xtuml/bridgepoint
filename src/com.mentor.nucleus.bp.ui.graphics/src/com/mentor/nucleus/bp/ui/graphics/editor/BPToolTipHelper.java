package com.mentor.nucleus.bp.ui.graphics.editor;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.actions.OpenGraphicsEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;

public class BPToolTipHelper extends ToolTipHelper {

	private Timer timer;
	private IFigure currentTipSource;
	private boolean tooltipClicked = false;
	protected int TooltipStyle = SWT.NONE;
	private Shell BPTooltipShell;
	private Shell BPTooltipFocusShell;
	private boolean tipShowing;
	private boolean  Reshow = false;
	private int BPeventX;
	private int BPeventY;
	private IFigure BPtip;
	private LightweightSystem lws;
	private TextLayout fTextLayout;
	private Dimension MaxPreferredTooltipSize = new Dimension(350, 125);
	private Dimension tipSize;
	private Timer mouseInCloseTimer;
	private Timer mouseOutCloseTimer;
	protected boolean showDetailedTooltip = false;

	
	@Override
	public void displayToolTipNear(IFigure hoverSource, IFigure tip,
			int eventX, int eventY) {
		if (tip != null && hoverSource != currentTipSource || Reshow) {
			if (!Reshow)
				getLightweightSystem().setContents(tip);
			Point displayPoint = computeWindowLocation(tip, eventX, eventY);
			Dimension shellSize = getLightweightSystem().getRootFigure()
					.getPreferredSize().getExpanded(getShellTrimSize());
			setShellBounds(displayPoint.x, displayPoint.y, tipSize.width,
					tipSize.height);
//			getShell()
//			getLightweightSystem().getRootFigure() .getPreferredSize().setSize(10, 10);
//			getLightweightSystem().getRootFigure().setFocusTraversable(true);
//			getLightweightSystem().getRootFigure().setMaximumSize(new Dimension(11, 11));
			//getShell().setMenuBar(new Menu(getShell()));
//			getShell().setActive();
			getShell().setFocus();
			show();
			currentTipSource = hoverSource;
			BPtip = tip;
			BPeventX = eventX;
			BPeventY = eventY;
			Reshow = false;
			
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
		
		FigureCanvas figureCanvas = (FigureCanvas)control;
		Dimension visibleArea = figureCanvas.getViewport().getSize();
		
		Point preferredLocation = new Point(eventX, eventY + 26);

		tipSize = getLightweightSystem().getRootFigure()
				.getPreferredSize().getExpanded(getShellTrimSize());
		tipSize = CropTipSizeIfNeeded(tipSize);

		
		// Adjust location if tip is going to fall outside display
		if (preferredLocation.y + tipSize.height - 200 > visibleArea.height)
			preferredLocation.y = eventY - tipSize.height;

		if (preferredLocation.x + tipSize.width - 300 > visibleArea.width)
			preferredLocation.x = eventX - tipSize.width;
//			preferredLocation.x -= (preferredLocation.x + tipSize.width)
//					- clientArea.width;
		if ( preferredLocation.x < 0 || preferredLocation.y < 0 ){
			return new Point(eventX, eventY + 26);
		}
		return preferredLocation;
	}
	
	private Dimension CropTipSizeIfNeeded(Dimension tipSize) {
		if ( tipSize.height > MaxPreferredTooltipSize.height && tipSize.width > MaxPreferredTooltipSize.width )
			return MaxPreferredTooltipSize;
		else if ( tipSize.height > MaxPreferredTooltipSize.height )
			return new Dimension(tipSize.width, MaxPreferredTooltipSize.height);
		else if ( tipSize.width > MaxPreferredTooltipSize.width )
			return new Dimension(MaxPreferredTooltipSize.width, tipSize.height);
		return tipSize;
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

		getShell().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				mouseOutCloseTimer = new Timer(true);
				mouseOutCloseTimer.schedule(new TimerTask() {
					public void run() {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								hide();
								mouseOutCloseTimer.cancel();
							}
						});
					}
				}, 500);
			}

			public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
//				hide();
				if ( mouseInCloseTimer != null)
					mouseInCloseTimer.cancel();
				getShell().setVisible(true);
			}
		});
		
		
		getShell().addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				int a= 1;
				int b = a;
				a = b;
			}
		});
		
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
				getShell().setVisible(false);
				createAnotherShell();
				getShell().setActive();
				getShell().setFocus();
				Reshow = true;
				lws = null;
				displayToolTipNear(currentTipSource, BPtip, BPeventX, BPeventY);
				
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
	
	private Shell fShell;
	private Composite fStatusComposite;
	private Label fSeparator;
	private ToolBar fToolBar;
	private int fResizeHandleSize = -1;
	private Composite fContentComposite;

	
	private void createAnotherShell() {
		
		/* JDT tooltip look like  
		 * \
		 *
		BPTooltipShell= new Shell(control.getShell(), 16404);
		Display display = control.getShell().getDisplay();
		Color foreground= display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		Color background= display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		
		BPTooltipShell.setForeground(foreground);
		BPTooltipShell.setBackground(background);
		
		
		GridLayout layout= new GridLayout(1, false);
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		layout.verticalSpacing= 0;
		BPTooltipShell.setLayout(layout);

		Composite fContentComposite= new Composite(BPTooltipShell, SWT.NONE);
		fContentComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		fContentComposite.setLayout(new FillLayout());
		
		fContentComposite.setForeground(foreground);
		fContentComposite.setBackground(background);
		
		
		
		Browser fBrowser= new Browser(fContentComposite, SWT.NONE);
		fBrowser.setJavascriptEnabled(false);

		fBrowser.setForeground(foreground);
		fBrowser.setBackground(background);
		
		fBrowser.setMenu(new Menu(getShell(), SWT.NONE));
		
		
		fTextLayout= new TextLayout(fBrowser.getDisplay());

		// Initialize fonts
		Font font= JFaceResources.getFont("org.eclipse.jdt.ui.javadocfont");
		fTextLayout.setFont(font);
		fTextLayout.setWidth(-1);
		font= JFaceResources.getFontRegistry().getBold("org.eclipse.jdt.ui.javadocfont");

		// Compute and set tab width
		fTextLayout.setText("    "); //$NON-NLS-1$
		int tabWidth= fTextLayout.getBounds().width;
		fTextLayout.setTabs(new int[] { tabWidth });
		String  text = "This is descrtiot" +
		"\n This is descrtiot This is descrtiot This is descrtiot This is descrtiot " +
		"\n This is descrtiot This is descrtiot This is descrtiot " +
		"\n This is descrtiot This is descrtiot This is descrtiot This is descrtiot This is descrtiot This is descrtiot " +
		"\n This is descrtiot This is descrtiot This is descrtiot This is descrtiot "; 
		
//		fTextLayout.setText(text); //$NON-NLS-1$
		fBrowser.setText(text, false);
		
		*/
		
		
		
		
		
//				TooltipStyle = SWT.RESIZE;
		fShell = BPTooltipShell = new Shell(control.getShell(), SWT.TOOL | SWT.ON_TOP | SWT.RESIZE);
//				getShell().setVisible(true);
//				tipShowing = true;
		Display display  = fShell.getDisplay();
		Color foreground= display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		Color background= display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		setColor(fShell, foreground, background);

		GridLayout layout= new GridLayout(1, false);
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		layout.verticalSpacing= 0;
		fShell.setLayout(layout);

		fContentComposite= new Composite(fShell, SWT.NONE);
		fContentComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		fContentComposite.setLayout(new FillLayout());
		setColor(fContentComposite, foreground, background);
		
		StyledText styledText = new StyledText(fContentComposite, SWT.BORDER | SWT.V_SCROLL | SWT.WRAP );
//		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		setColor(styledText, foreground, background);
		
		FlowPage flowgage = (FlowPage)BPtip;
		List list = flowgage.getChildren();
		TextFlow textflow = (TextFlow)list.get(0);
		
		styledText.setText(textflow.getText());
		
		///  toolbar 
		fStatusComposite= new Composite(fShell, SWT.NONE);
		GridData gridData= new GridData(SWT.FILL, SWT.BOTTOM, true, false);
		fStatusComposite.setLayoutData(gridData);
		GridLayout statusLayout= new GridLayout(1, false);
		statusLayout.marginHeight= 0;
		statusLayout.marginWidth= 0;
		statusLayout.verticalSpacing= 1;
		fStatusComposite.setLayout(statusLayout);

		fSeparator= new Label(fStatusComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		fSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//
		final Composite bars= new Composite(fStatusComposite, SWT.NONE);
		bars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		GridLayout glayout= new GridLayout(3, false);
		glayout.marginHeight= 0;
		glayout.marginWidth= 0;
		glayout.horizontalSpacing= 0;
		glayout.verticalSpacing= 0;
		bars.setLayout(glayout);

		ToolBarManager tbm= new ToolBarManager(SWT.FLAT);
		
		fToolBar= tbm.createControl(bars);
		GridData gd= new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		fToolBar.setLayoutData(gd);

		Composite spacer= new Composite(bars, SWT.NONE);
		gd= new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint= 0;
		gd.heightHint= 0;
		spacer.setLayoutData(gd);

		addMoveSupport(spacer);
		addResizeSupportIfNecessary(bars);
		

		ToolItem tltmOpenCanvas = new ToolItem(fToolBar, SWT.NONE);
		tltmOpenCanvas.setText("Open Canvas");
		
		ToolItem tltmOpenDescription = new ToolItem(fToolBar, SWT.NONE);
		tltmOpenDescription.setText("Open Description");
		
		tltmOpenDescription.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ShapeEditPart part = ((ShapeImageFigure)currentTipSource).getPart();
				Shape_c shape = (Shape_c) part.getModel();
//				Model_c model = Model_c.getOneGD_MDOnR1(GraphicalElement_c.getManyGD_GEsOnR2(shape));
				Object modelElement = GraphicalElement_c.getOneGD_GEOnR2(shape).getRepresents();
				
				hide();
				openDescriptionEditor(modelElement);
				
			}
		});
		
		tltmOpenCanvas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ShapeEditPart part = ((ShapeImageFigure)currentTipSource).getPart();
				Shape_c shape = (Shape_c) part.getModel();
//				Model_c model = Model_c.getOneGD_MDOnR1(GraphicalElement_c.getManyGD_GEsOnR2(shape));
				Object modelElement = GraphicalElement_c.getOneGD_GEOnR2(shape).getRepresents();
				
				hide();
				openCanvasEditor(modelElement);
				
			}
		});

		
		/*
		BPTooltipShell.setLayout(new FillLayout(SWT.VERTICAL));
		
		// MULTI, READ_ONLY, SINGLE, WRAP
		StyledText styledText = new StyledText(BPTooltipShell, SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY | SWT.MULTI);
		styledText.setText("This is descrtiot" +
				"\n This is descrtiot This is descrtiot This is descrtiot This is descrtiot " +
				"\n This is descrtiot\n\n\n This is descrtiot This is descrtiot " +
				"\n This is descrtiot This is descrtiot\n\n\n This is descrtiot This is descrtiot This is descrtiot This is descrtiot " +
				"\n This is descrtiot This is descrtiot This is descrtiot\n\n\n\n This is descrtiot "); 
		

		
		ToolBar toolBar = new ToolBar(BPTooltipShell, SWT.FLAT | SWT.RIGHT);
		
		ToolItem tltmOpenCanvas = new ToolItem(toolBar, SWT.NONE);
		tltmOpenCanvas.setText("Open Canvas");
		
		ToolItem tltmOpenDescription = new ToolItem(toolBar, SWT.NONE);
		tltmOpenDescription.setText("Open Description");
		*/
	}
	
	
	protected void openDescriptionEditor(final Object uut) {
		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					
					IStructuredSelection ss = new StructuredSelection(uut);
					ShowDescriptionAction sda = new ShowDescriptionAction();
					Action a = new Action() {
					};
					sda.selectionChanged(a, ss);
					sda.run(a);
				}
			};
			ResourcesPlugin.getWorkspace().run(r, null);
		} catch (CoreException x) {
			CorePlugin.logError("Unable to open activity editor.", x);
		}
	}
	
	
	static public void openCanvasEditor(final Object uut) {
		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					
					IStructuredSelection ss = new StructuredSelection(uut);
	                Selection selection = Selection.getInstance();
	                selection.addToSelection(ss);
					OpenGraphicsEditor sca = new OpenGraphicsEditor();
					selection.setSelection(ss);
					Action a = new Action() {
					};
					sca.run(a);
				}
			};
			ResourcesPlugin.getWorkspace().run(r, null);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().update();
		} catch (CoreException x) {
			CorePlugin.logError("Unable to open canvas editor.", x);
		}
	}
	

	@Override
	public void updateToolTip(IFigure figureUnderMouse, IFigure tip,
			int eventX, int eventY) {
		/*
		 * If the cursor is not on any Figures, it has been moved off of the
		 * control. Hide the tool tip.
		 */
		if (figureUnderMouse != null){
			if ( mouseOutCloseTimer != null)
				mouseOutCloseTimer.cancel();
			
		}
		
		if (figureUnderMouse == null) {
			if (isShowing()) {
//				hide();
//				timer.cancel();
				mouseInCloseTimer = new Timer(true);
				mouseInCloseTimer.schedule(new TimerTask() {
					public void run() {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								hide();
								mouseInCloseTimer.cancel();
							}
						});
					}
				}, 500);
				
				
			}
		}
		// Makes tooltip appear without a hover event if a tip is currently
		// being displayed
//		getLightweightSystem();
		if ( figureUnderMouse == lws){
			return;
		}
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
		return new Shell(control.getShell(), TooltipStyle);
	}
	
	@Override
	protected Shell getShell() {
		if (BPTooltipShell == null || BPTooltipShell.isDisposed() ) {
			BPTooltipShell = createShell();
			hookShellListeners();
		}
		return BPTooltipShell;
	}
	
	@Override
	protected void hide() {
		if (BPTooltipShell != null && !BPTooltipShell.isDisposed())
			BPTooltipShell.setVisible(false);
		tipShowing = false;
	}
	
	protected Dimension getShellTrimSize() {
		Rectangle trim = BPTooltipShell.computeTrim(0, 0, 0, 0);
		return new Dimension(trim.width, trim.height);
	}
	
	@Override
	public boolean isShowing() {
		return tipShowing;
	}
	
	@Override
	protected void show() {
		getShell().setVisible(true);
		tipShowing = true;
	}
	
	protected LightweightSystem getLightweightSystem() {
		if (lws == null) {
			lws = createLightweightSystem();
			lws.setControl(getShell());
		}
		return lws;
	}
	
	private static void setColor(Control control, Color foreground, Color background) {
		control.setForeground(foreground);
		control.setBackground(background);
	}
	
	
	private void addMoveSupport(final Control control) {
		MouseAdapter moveSupport= new MouseAdapter() {
			private MouseMoveListener fMoveListener;

			public void mouseDown(MouseEvent e) {
				Point shellLoc= fShell.getLocation();
				final int shellX= shellLoc.x;
				final int shellY= shellLoc.y;
				Point mouseLoc= control.toDisplay(e.x, e.y);
				final int mouseX= mouseLoc.x;
				final int mouseY= mouseLoc.y;
				fMoveListener= new MouseMoveListener() {
					public void mouseMove(MouseEvent e2) {
						Point mouseLoc2= control.toDisplay(e2.x, e2.y);
						int dx= mouseLoc2.x - mouseX;
						int dy= mouseLoc2.y - mouseY;
						fShell.setLocation(shellX + dx, shellY + dy);
					}
				};
				control.addMouseMoveListener(fMoveListener);
			}

			public void mouseUp(MouseEvent e) {
				control.removeMouseMoveListener(fMoveListener);
				fMoveListener= null;
			}
		};
		control.addMouseListener(moveSupport);		
	}

	

	private void addResizeSupportIfNecessary(final Composite bars) {

		// XXX: workarounds for
		// - https://bugs.eclipse.org/bugs/show_bug.cgi?id=219139 : API to add resize grip / grow box in lower right corner of shell
		// - https://bugs.eclipse.org/bugs/show_bug.cgi?id=23980 : platform specific shell resize behavior
		String platform= SWT.getPlatform();
		final boolean isWin= platform.equals("win32"); //$NON-NLS-1$
		if (!isWin && !platform.equals("gtk")) //$NON-NLS-1$
			return;

		final Canvas resizer= new Canvas(bars, SWT.NONE);

		int size= getResizeHandleSize(bars);

		GridData data= new GridData(SWT.END, SWT.END, false, true);
		data.widthHint= size;
		data.heightHint= size;
		resizer.setLayoutData(data);
		resizer.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Point s= resizer.getSize();
				int x= s.x - 2;
				int y= s.y - 2;
				int min= Math.min(x, y);
				if (isWin) {
					// draw dots
					e.gc.setBackground(resizer.getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
					int end= min - 1;
					for (int i= 0; i <= 2; i++)
						for (int j= 0; j <= 2 - i; j++)
							e.gc.fillRectangle(end - 4 * i, end - 4 * j, 2, 2);
					end--;
					e.gc.setBackground(resizer.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
					for (int i= 0; i <= 2; i++)
						for (int j= 0; j <= 2 - i; j++)
							e.gc.fillRectangle(end - 4 * i, end - 4 * j, 2, 2);

				} else {
					// draw diagonal lines
					e.gc.setForeground(resizer.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
					for (int i= 1; i < min; i+= 4) {
						e.gc.drawLine(i, y, x, i);
					}
					e.gc.setForeground(resizer.getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
					for (int i= 2; i < min; i+= 4) {
						e.gc.drawLine(i, y, x, i);
					}
				}
			}
		});

		final boolean isRTL= (resizer.getShell().getStyle() & SWT.RIGHT_TO_LEFT) != 0;
		resizer.setCursor(resizer.getDisplay().getSystemCursor(isRTL ? SWT.CURSOR_SIZESW : SWT.CURSOR_SIZESE));
		MouseAdapter resizeSupport= new MouseAdapter() {
			private MouseMoveListener fResizeListener;

			public void mouseDown(MouseEvent e) {
				Rectangle shellBounds= fShell.getBounds();
				final int shellX= shellBounds.x;
				final int shellY= shellBounds.y;
				final int shellWidth= shellBounds.width;
				final int shellHeight= shellBounds.height;
				Point mouseLoc= resizer.toDisplay(e.x, e.y);
				final int mouseX= mouseLoc.x;
				final int mouseY= mouseLoc.y;
				fResizeListener= new MouseMoveListener() {
					public void mouseMove(MouseEvent e2) {
						Point mouseLoc2= resizer.toDisplay(e2.x, e2.y);
						int dx= mouseLoc2.x - mouseX;
						int dy= mouseLoc2.y - mouseY;
						if (isRTL) {
							fShell.setLocation(new Point(shellX + dx, shellY));
							fShell.setSize(shellWidth - dx, shellHeight + dy);
						} else {
							fShell.setSize(shellWidth + dx, shellHeight + dy);
						}
					}
				};
				resizer.addMouseMoveListener(fResizeListener);
			}

			public void mouseUp(MouseEvent e) {
				resizer.removeMouseMoveListener(fResizeListener);
				fResizeListener= null;
			}
		};
		resizer.addMouseListener(resizeSupport);
	
	}
	
	
	private int getResizeHandleSize(Composite parent) {
		if (fResizeHandleSize  == -1) {
			Slider sliderV= new Slider(parent, SWT.VERTICAL);
			Slider sliderH= new Slider(parent, SWT.HORIZONTAL);
			int width= sliderV.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			int height= sliderH.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			sliderV.dispose();
			sliderH.dispose();
			fResizeHandleSize= Math.min(width, height);
		}

		return fResizeHandleSize;
	}

}
