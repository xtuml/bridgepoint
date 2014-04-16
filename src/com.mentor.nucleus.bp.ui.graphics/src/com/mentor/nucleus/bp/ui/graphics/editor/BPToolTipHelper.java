package com.mentor.nucleus.bp.ui.graphics.editor;

import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.actions.OpenGraphicsEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.DecoratedPolylineConnection;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;

public class BPToolTipHelper extends ToolTipHelper {

	
	// Eclipse passed argument 
	private IFigure currentTipSource;
	private IFigure BPtip;
	private int BPeventX;
	private int BPeventY;
	
	protected int TooltipStyle = SWT.NONE;
	
	// Tooltip elements
	private Shell BPSimpleTooltipShell;
	private Shell BPDetailedTooltipShell;
	private LightweightSystem lws;
	protected Text styledText;
	
	// Flow control fields
	private boolean tipShowing;
	private boolean  ReplaceShell = false;
	private Timer mouseInCloseTimer;
	private Timer mouseOutCloseTimer;
	protected boolean showDetailedTooltip = false;
	
	
	// Shell Style fields
	public static final Dimension MaxPreferredTooltipSize = new Dimension(400, 175);
	private Dimension tipSize;
	private int resizeImageSize = -1;
	
	@Override
	public void displayToolTipNear(IFigure hoverSource, IFigure tip,
			int eventX, int eventY) {
		if (tip != null && hoverSource != currentTipSource || ReplaceShell) {
			
			setTooltipText(tip);
			Point displayPoint = computeWindowLocation(tip, eventX, eventY);
			if(showDetailedTooltip){
				setShellBounds(displayPoint.x, displayPoint.y, tipSize.width,
						tipSize.height+15);
			}
			else{ 
				setShellBounds(displayPoint.x, displayPoint.y, tipSize.width,
						tipSize.height+15);
			}
			getShell().setFocus();
			show();
			updateEclipsePassedArgument(hoverSource, tip, eventX, eventY);
			ReplaceShell = false;
		}
	}
	
	private void updateEclipsePassedArgument(IFigure hoverSource, IFigure tip,
			int eventX, int eventY) {
		currentTipSource = hoverSource;
		BPtip = tip;
		BPeventX = eventX;
		BPeventY = eventY;		
	}

	private void setTooltipText(IFigure tip) {
		if (!showDetailedTooltip){
			getLightweightSystem().setContents(tip);
		}else{
			if ( tip instanceof FlowPage){
			FlowPage flowgage = (FlowPage)tip;
			TextFlow textflow = (TextFlow)flowgage.getChildren().get(0);
			styledText.setText(textflow.getText());
			} else if ( tip instanceof org.eclipse.draw2d.Label){
				org.eclipse.draw2d.Label label = (org.eclipse.draw2d.Label)tip;
				styledText.setText(label.getText());
			}
		}
	}

	private Point computeWindowLocation(IFigure tip, int eventX, int eventY) {
		FigureCanvas figureCanvas = (FigureCanvas)control;
		Dimension visibleArea = figureCanvas.getViewport().getSize();
		
		Point location = new Point(eventX +5, eventY + 5);
		tip.invalidate();
		tipSize = tip.getPreferredSize(-1, -1);
		if ( showDetailedTooltip){
			tipSize.width = tipSize.width + 100; 
			tipSize.height = tipSize.height + 50; 
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
		else if (tipSize.width < 75 && tipSize.height < 25 )
			return new Dimension(75,25);
		return tipSize;
	}

	@Override
	public void dispose() {
		if (isShowing()) {
			hide();
		}
		getShell().dispose();
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
				}, 500);
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
				hide();
				ReplaceShell = true;
				if (!showDetailedTooltip)
					showDetailedTooltip = true;
				getShell();
				displayToolTipNear(currentTipSource, BPtip, BPeventX, BPeventY);
				getShell().setActive();
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// Skip
			}
		});
	}
	
	

	
	private Shell createDetailedShell() {
		
		Shell detailedShell = new Shell(control.getShell(), SWT.TOOL | SWT.ON_TOP | SWT.RESIZE);
		
		Display display  = detailedShell.getDisplay();
		
		Color foreground= display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		Color background= display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		
		setColor(detailedShell, foreground, background);

		CreateImageCompartment(detailedShell);
		
		CreateDescriptionTextCompartment(detailedShell);
		
		CreateActionsCompartment(detailedShell);

		return detailedShell;
	}
	
	
	private void CreateActionsCompartment(Shell detailedShell) {
		
		
		///  toolbar 
		Composite bottomComposite= new Composite(detailedShell, SWT.NONE);
		GridData gridData= new GridData(SWT.FILL, SWT.BOTTOM, true, false);
		bottomComposite.setLayoutData(gridData);
		GridLayout statusLayout= new GridLayout(1, false);
		statusLayout.marginHeight= 0;
		statusLayout.marginWidth= 0;
		statusLayout.verticalSpacing= 1;
		bottomComposite.setLayout(statusLayout);

		Label lineSeperator= new Label(bottomComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineSeperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//
		final Composite actionComposite= new Composite(bottomComposite, SWT.NONE);
		actionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		GridLayout actionCompositeLayout= new GridLayout(3, false);
		actionCompositeLayout.marginHeight= 0;
		actionCompositeLayout.marginWidth= 0;
		actionCompositeLayout.horizontalSpacing= 0;
		actionCompositeLayout.verticalSpacing= 0;
		actionComposite.setLayout(actionCompositeLayout);

		ToolBarManager actionManager= new ToolBarManager(SWT.FLAT);
		
		ToolBar bar= actionManager.createControl(actionComposite);
		GridData toolBarLayout= new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		toolBarLayout.horizontalIndent = 0;
		toolBarLayout.verticalIndent= 0;
		bar.setLayoutData(toolBarLayout);

		
		Composite moveSupportCanvas= new Composite(actionComposite, SWT.FILL);
		GridData spacerLayout= new GridData(SWT.FILL, SWT.FILL, true, true);
		spacerLayout.widthHint= 0;
		spacerLayout.heightHint= 0;
		moveSupportCanvas.setLayoutData(spacerLayout);

		addResizeSupportIfNecessary(detailedShell, actionComposite);

		
		addMoveSupport(detailedShell, moveSupportCanvas);
		

		
	}
	private Object getTooltipModelElement() {
		if (currentTipSource instanceof ShapeImageFigure ){
		ShapeEditPart part = ((ShapeImageFigure)currentTipSource).getPart();
		Shape_c shape = (Shape_c) part.getModel();
		Object modelElement = GraphicalElement_c.getOneGD_GEOnR2(shape).getRepresents();
		return modelElement;
		}
		else if ( currentTipSource instanceof DecoratedPolylineConnection){
			
			ConnectorEditPart part = ((DecoratedPolylineConnection)currentTipSource).getPart();
			Connector_c connector = (Connector_c)part.getModel();
			Object modelElement = GraphicalElement_c.getOneGD_GEOnR2(connector).getRepresents();
			return modelElement;
		}
		else {
			CorePlugin.logError("Tooltip is created for unsupported canvas element", null);
			return null;
		}
	}

	private void addTooltipAction(ToolBar bar) {
		Object element = getTooltipModelElement();
		if ( element == null){
			addCloseAction(bar);
			return;
		}
		NonRootModelElement temp = null;
		if (element instanceof NonRootModelElement){
			temp= (NonRootModelElement)element;
		}
		final NonRootModelElement modelElement = temp;
		
		ToolItem openDescriptionAction = new ToolItem(bar, SWT.None);
		openDescriptionAction.setToolTipText("Open Description Editor");
		openDescriptionAction.setImage(CorePlugin.getImageDescriptor("edit_dsc.gif").createImage());
		
		
		openDescriptionAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				hide();
				if (modelElement != null )
					openDescriptionEditor(modelElement);
			}

		});
		
		addExtraTooltipActions(bar, modelElement);
		
		new ToolItem(bar, SWT.SEPARATOR);
		addCloseAction(bar);
	}

	private void addCloseAction(ToolBar bar) {

		ToolItem closeActionTooltip = new ToolItem(bar, SWT.NONE);
		closeActionTooltip.setImage(CorePlugin.getImageDescriptor("delete_edit.gif").createImage());
		closeActionTooltip.setToolTipText("Close Tooltip Window");
		closeActionTooltip.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
			}
		});
	}

	private void addExtraTooltipActions(ToolBar bar, NonRootModelElement modelElement) {
		
		
		if (modelElement instanceof ModelClass_c){
			InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518((ModelClass_c)modelElement);
			ClassStateMachine_c csm = ClassStateMachine_c.getOneSM_ASMOnR519((ModelClass_c)modelElement);
			if (ism != null){
				addOpenInstanceStateMachineDiagram(bar, ism);
			}
			if ( csm != null){
				addOpenClassStateMachineDiagram(bar, csm);
			}
		}
			
		else if (modelElement instanceof Component_c){
			addOpenDiagramAction(bar, modelElement);
		}
		
		else if (modelElement instanceof  ComponentReference_c){
			addOpenDiagramAction(bar, modelElement);
		}
		else if (modelElement instanceof  Package_c){
			addOpenDiagramAction(bar, modelElement);
		}
		else if (modelElement instanceof  StateMachineState_c){
			addOpenOALEditorAction(bar, modelElement);
		}
		else if (modelElement instanceof Transition_c){
			addOpenOALEditorAction(bar, modelElement);
		}
			
			
	}

	private void addOpenClassStateMachineDiagram(ToolBar bar, final ClassStateMachine_c csm) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openCSMAction = new ToolItem(bar, SWT.NONE);
		openCSMAction.setImage(CorePlugin.getImageDescriptor("metadata/ClassStateChart.gif").createImage());
		openCSMAction.setToolTipText("Open Class State Machine");
		openCSMAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
				openCanvasEditor(csm);
			}
		});
		
	}

	private void addOpenInstanceStateMachineDiagram(ToolBar bar, final InstanceStateMachine_c ism) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openISMmAction = new ToolItem(bar, SWT.NONE);
		openISMmAction.setImage(CorePlugin.getImageDescriptor("metadata/InstanceStateChart.gif").createImage());
		openISMmAction.setToolTipText("Open Instance State Machine");
		openISMmAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
				openCanvasEditor(ism);
			}
		});
		
	}

	private void addOpenOALEditorAction(ToolBar bar, final NonRootModelElement modelElement) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openActivityEditorAction = new ToolItem(bar, SWT.NONE);
		openActivityEditorAction.setImage(CorePlugin.getImageDescriptor("edit_oal.gif").createImage());
		openActivityEditorAction.setToolTipText("Open Activity Editor");
		openActivityEditorAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
				openActivityEditor(modelElement);
			}
		});
		
	}

	private void addOpenDiagramAction(ToolBar bar, final NonRootModelElement modelElement) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openDiagramAction = new ToolItem(bar, SWT.NONE);
		openDiagramAction.setImage(CorePlugin.getImageDescriptor("green-bp.gif").createImage());
		openDiagramAction.setToolTipText("Open Diagram Editor");
		openDiagramAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
				openCanvasEditor(modelElement);
			}
		});
		
	}

	private void CreateDescriptionTextCompartment(Shell detailedShell) {
		Display display  = detailedShell.getDisplay();
		Color foreground= display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		Color background= display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		
		GridLayout layout= new GridLayout(1, false);
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		layout.verticalSpacing= 0;
		detailedShell.setLayout(layout);

		Composite descriptionTextComposite= new Composite(detailedShell, SWT.NONE);
		descriptionTextComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		descriptionTextComposite.setLayout(new FillLayout());
		setColor(descriptionTextComposite, foreground, background);
		
		styledText = new Text(descriptionTextComposite, SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP | SWT.MULTI);
		setColor(styledText, foreground, background);
		
	}

	private void CreateImageCompartment(Shell detailedShell) {
		// Add Composite and Label here for photos
		
	}

	protected void openDescriptionEditor(final Object uut) {
		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				@Override
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
				@Override
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
	
	static public void openActivityEditor(final Object uut) {

		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor)
				throws CoreException {
					IStructuredSelection ss = new StructuredSelection(uut);
					ShowActivityAction sda = new ShowActivityAction();
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
					}, 500);


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
		} else if (!isShowing() && figureUnderMouse != currentTipSource)
			currentTipSource = null;
	}

	public BPToolTipHelper(Control c) {
		super(c);
	}
	
	@Override
	protected Shell createShell() {
		return new Shell(control.getShell(), TooltipStyle);
	}
	
	@Override
	protected Shell getShell() {
		if (showDetailedTooltip){
			if (BPDetailedTooltipShell == null || BPDetailedTooltipShell.isDisposed() ) {
				BPDetailedTooltipShell = createDetailedShell();
				
				hookDetailedShellListeners();
			}
			return BPDetailedTooltipShell;
		}
			
		else{
			if (BPSimpleTooltipShell == null || BPSimpleTooltipShell.isDisposed() ) {
				BPSimpleTooltipShell = createShell();
				hookSimpleShellListeners();
			}
			return BPSimpleTooltipShell;
		}
	}
	
	private void hookDetailedShellListeners() {
		BPDetailedTooltipShell.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Gain");
			}
			@Override
			public void focusLost(FocusEvent e) {

				if ( e.getSource() == getShell()){
					System.out.println("Lost");
					getShell().setActive();
					return;
				}
				hide();
				showDetailedTooltip = false;
			}

		});
		
	}

	@Override
	protected void hide() {
		if (getShell() != null && !getShell().isDisposed())
			getShell().setVisible(false);
		tipShowing = false;
		showDetailedTooltip = false;
	}
	
	@Override
	protected Dimension getShellTrimSize() {
		Rectangle trim = getShell().computeTrim(0, 0, 0, 0);
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
	
	@Override
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
	
	
	private void addMoveSupport(final Shell detailedShell, final Control control) {
		MouseAdapter moveSupport= new MouseAdapter() {
			private MouseMoveListener mouseMoveListener;

			@Override
			public void mouseDown(MouseEvent e) {
				final Point currentShellLocation= detailedShell.getLocation();
				final Point oldMouseLocation= control.toDisplay(e.x, e.y);
				mouseMoveListener = new MouseMoveListener() {
					
					@Override
					public void mouseMove(MouseEvent e2) {
						Point newMouseLocation= control.toDisplay(e2.x, e2.y);
						int dx= newMouseLocation.x - oldMouseLocation.x;
						int dy= newMouseLocation.y - oldMouseLocation.y;
						detailedShell.setLocation(currentShellLocation.x + dx, currentShellLocation.y + dy);
					}
				};
				control.addMouseMoveListener(mouseMoveListener);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				control.removeMouseMoveListener(mouseMoveListener);
				mouseMoveListener= null;
			}
		};
		control.addMouseListener(moveSupport);		
	}

	

	private void addResizeSupportIfNecessary(final Shell detailedShell,final Composite actionsComposite) {
		final Label resizer= new Label(actionsComposite, SWT.NONE);
		
		
		resizer.setImage(CorePlugin.getImageDescriptor("resize.gif").createImage());
		int resizeImageSize= computeResizeHandleSize(actionsComposite);

		GridData grid= new GridData(SWT.END, SWT.END, false, true);
		grid.widthHint= resizeImageSize;
		grid.heightHint= resizeImageSize;
		resizer.setLayoutData(grid);

		resizer.setCursor(resizer.getDisplay().getSystemCursor(SWT.CURSOR_SIZESE));
		MouseAdapter resizeSupport= new MouseAdapter() {
			private MouseMoveListener mouseMoveListener;

			@Override
			public void mouseDown(MouseEvent e) {
				final Rectangle bounds= detailedShell.getBounds();
				final Point oldMouseLocation= resizer.toDisplay(e.x, e.y);
				mouseMoveListener= new MouseMoveListener() {
					@Override
					public void mouseMove(MouseEvent e2) {
						Point newMouseLocation= resizer.toDisplay(e2.x, e2.y);
						int dx= newMouseLocation.x - oldMouseLocation.x;
						int dy= newMouseLocation.y - oldMouseLocation.y;
						detailedShell.setSize(bounds.width + dx, bounds.height + dy);
					}
				};
				resizer.addMouseMoveListener(mouseMoveListener);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				resizer.removeMouseMoveListener(mouseMoveListener);
				mouseMoveListener= null;
			}
		};
		resizer.addMouseListener(resizeSupport);
	
	}
	
	
	private int computeResizeHandleSize(Composite actionsComposite) {
		if (resizeImageSize  == -1) {
			Slider virtecalSlide = new Slider(actionsComposite, SWT.VERTICAL);
			Slider horeizontalSlide = new Slider(actionsComposite, SWT.HORIZONTAL);
			int width = virtecalSlide.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			int height = horeizontalSlide .computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			virtecalSlide.dispose();
			horeizontalSlide.dispose();
			resizeImageSize = Math.min(width, height);
		}

		return resizeImageSize;
	}

}
