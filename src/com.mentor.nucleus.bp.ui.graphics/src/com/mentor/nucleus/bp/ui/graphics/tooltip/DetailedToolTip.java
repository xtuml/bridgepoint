//====================================================================
//
//File:      $RCSfile: DetailedToolTip.java,v $
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

package com.mentor.nucleus.bp.ui.graphics.tooltip;

import java.lang.reflect.Method;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

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
import com.mentor.nucleus.bp.ui.graphics.editor.BPToolTipHelper;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.DecoratedPolylineConnection;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class DetailedToolTip {

	public static final int ToolTip_Shell_Style = SWT.TOOL | SWT.RESIZE;
	public static final int Tooltip_Text_Style = SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP | SWT.MULTI;
	public static final String ACTIVITY_EDITOR_ID = "com.mentor.nucleus.bp.ui.text.activity.ActivityEditor";
	public static final String ACTIVITY_EDITOR_INPUT_ID = "com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput";
	public static final String DESCRIPTION_EDITOR_ID = "com.mentor.nucleus.bp.ui.text.description.DescriptionEditor";
	public static final String DESCRIPTION_EDITOR_INPUT_ID = "com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput";
	public static final String Text_Editors_Extension_ID = "com.mentor.nucleus.bp.ui.text.com.mentor.nucleus.bp.ui.text.editors";
	protected Shell detailedShell;
	protected Text styledText;
	private int resizeImageSize = -1;
	private BPToolTipHelper tooltipHelper;


	public DetailedToolTip(Control parent, BPToolTipHelper bpToolTipHelper){
		tooltipHelper = bpToolTipHelper; 

		detailedShell = createDetailedShell(parent);
		hookDetailedShellListeners();
	}

	public Shell getShell()
	{
		return detailedShell;
	}

	private Shell createDetailedShell(Control control) {

		Shell detailedShell = new Shell(control.getShell(), ToolTip_Shell_Style);

		Display display  = detailedShell.getDisplay();

		Color foreground= display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		Color background= display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);

		setColor(detailedShell, foreground, background);

		createTitleCompartment(detailedShell);
		
		createImageCompartment(detailedShell);

		createDescriptionTextCompartment(detailedShell);

		createActionsCompartment(detailedShell);

		return detailedShell;
	}

	private void hookDetailedShellListeners() {
		detailedShell.addShellListener(new ShellListener() {
			
			@Override
			public void shellIconified(ShellEvent e) {
				// Do nothing 
			}
			
			@Override
			public void shellDeiconified(ShellEvent e) {
				// Do nothing 
			}
			
			@Override
			public void shellDeactivated(ShellEvent e) {
				// Do nothing 
			}
			
			@Override
			public void shellClosed(ShellEvent e) {
				tooltipHelper.hide();
			}
			
			@Override
			public void shellActivated(ShellEvent e) {
				// Do nothing 
			}
		});
	}



	public static void setColor(Control control, Color foreground, Color background) {
		control.setForeground(foreground);
		control.setBackground(background);
	}


	public void createImageCompartment(Shell detailedShell) {
		/* 
		 * Use this stub to add a image viewer section in the tooltip window. 
		 * This section will be between the title section (if exsit) and the 
		 * description text section. Create a composite on the top of the passed 
		 * argument 'detailedShell' and other graphical elements as needed
		*/
	}

	private void createTitleCompartment(Shell detailedShell) {
		/*
		 * Use this stub to add a title section for the tooltip window at the 
		 * top. Create a composite on the top of the passed 
		 * argument 'detailedShell' and other graphical elements as needed
		 */
	}
	public void createDescriptionTextCompartment(Shell detailedShell) {
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

		styledText = new Text(descriptionTextComposite, Tooltip_Text_Style);
		setColor(styledText, foreground, background);
		styledText.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// Do nothing				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				GraphicalEditor gEditor = null;
				if(activeEditor instanceof ModelEditor) {
					ModelEditor editor = (ModelEditor)activeEditor;
					gEditor = editor.getGraphicalEditor();
				}					
				if(activeEditor instanceof GraphicalEditor) {
					gEditor = (GraphicalEditor)activeEditor;
				}
				if ( gEditor != null){	
					StructuredSelection sel = new StructuredSelection(getTooltipModelElement());
					gEditor.getGraphicalViewer().getSelectionManager().deselectAll();
					gEditor.getGraphicalViewer().getSelectionManager().setSelection(sel);
				}
			}
		});

	}

	public void createActionsCompartment(Shell detailedShell) {


		// Create Action Compartment
		Composite bottomComposite= new Composite(detailedShell, SWT.NONE);
		GridData gridData= new GridData(SWT.FILL, SWT.BOTTOM, true, false);
		bottomComposite.setLayoutData(gridData);
		GridLayout bottomLayout= new GridLayout(1, false);
		bottomLayout.marginHeight= 0;
		bottomLayout.marginWidth= 0;
		bottomLayout.verticalSpacing= 3;
		bottomComposite.setLayout(bottomLayout);

		Label lineSeperator= new Label(bottomComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineSeperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//  Create Action Composite
		Composite actionComposite= new Composite(bottomComposite, SWT.NONE);
		actionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		GridLayout actionCompositeLayout= new GridLayout(3, false);
		actionCompositeLayout.marginHeight= 0;
		actionCompositeLayout.marginWidth= 0;
		actionCompositeLayout.horizontalSpacing= 0;
		actionCompositeLayout.verticalSpacing= 0;
		actionComposite.setLayout(actionCompositeLayout);

		
		// Create ToolBar Composite
		Composite toolbarComposite = new Composite(actionComposite, SWT.NONE);
		GridData toolbarCompositeData = new GridData(SWT.BEGINNING,SWT.BEGINNING, false,false);
		toolbarComposite.setLayoutData(toolbarCompositeData);

		GridLayout toolbarLayout= new GridLayout(1, false);
		toolbarLayout.marginHeight = 0;
		toolbarLayout.marginWidth = 0;
		toolbarComposite.setLayout(toolbarLayout);
		
		
		ToolBarManager actionManager = new ToolBarManager(SWT.FLAT);

		// Add action button to the tool bar 
		setTooltipActions(toolbarComposite, actionManager);

		
		addMoveSupport(detailedShell, actionComposite);
		
		addResizeSupport(detailedShell, actionComposite);
	}

	public Composite addMoveSupport(Shell detailedShell,
			Composite actionComposite) {
		Composite moveSupportCanvas= new Composite(actionComposite, SWT.FILL);
		GridData moveCompositeLayout= new GridData(SWT.FILL, SWT.FILL, true, true);
		moveCompositeLayout.widthHint= 0;
		moveCompositeLayout.heightHint= 0;
		moveSupportCanvas.setLayoutData(moveCompositeLayout);

		setMoveSupportAction(detailedShell, moveSupportCanvas);
		return moveSupportCanvas;
	}

	public void addResizeSupport(final Shell detailedShell,final Composite actionsComposite) {
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


	public int computeResizeHandleSize(Composite actionsComposite) {
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

	private Object getTooltipModelElement() {
		Object currentTipSource = getToolltipSource();
		
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


	protected void openDescriptionEditor(final Object uut) {
		getEditor(Text_Editors_Extension_ID,
				DESCRIPTION_EDITOR_INPUT_ID,
				DESCRIPTION_EDITOR_ID,
				getTooltipModelElement());
	}

	public void setTooltipActions(Composite toolbarComposite, ToolBarManager actionManager) {
		ToolBar bar= actionManager.createControl(toolbarComposite);
		GridData toolBarLayout= new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		toolBarLayout.horizontalIndent = 0;
		toolBarLayout.verticalIndent= 0;
		bar.setLayoutData(toolBarLayout);
		
		
		Object element = getTooltipModelElement();
		
		if ( element == null){
			addCloseAction(bar);
			return;
		}
		
		
		NonRootModelElement modelElement = null;
		if (element instanceof NonRootModelElement){
			modelElement= (NonRootModelElement)element;
		}else{
			addCloseAction(bar);
			return;
		}

		addOpenDescriptionAction(bar, modelElement);

		addExtraTooltipActions(bar, modelElement);

		new ToolItem(bar, SWT.SEPARATOR);
		addCloseAction(bar);
	}

	public void addOpenDescriptionAction(ToolBar bar,
			final NonRootModelElement modelElement) {
		ToolItem openDescriptionAction = new ToolItem(bar, SWT.None);
		openDescriptionAction.setToolTipText("Open Description Editor");
		openDescriptionAction.setImage(CorePlugin.getImageDescriptor("edit_dsc.gif").createImage());


		openDescriptionAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				tooltipHelper.hide();
				if (modelElement != null )
					openDescriptionEditor(modelElement);
			}

		});
	}





	public void addCloseAction(ToolBar bar) {

		ToolItem closeActionTooltip = new ToolItem(bar, SWT.NONE);
		closeActionTooltip.setImage(CorePlugin.getImageDescriptor("delete_edit.gif").createImage());
		closeActionTooltip.setToolTipText("Close Tooltip Window");
		closeActionTooltip.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tooltipHelper.hide();
			}
		});
	}


	public void openCanvasEditor(final Object uut) {
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


	public void addExtraTooltipActions(ToolBar bar, NonRootModelElement modelElement) {


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



	public void addOpenInstanceStateMachineDiagram(ToolBar bar, final InstanceStateMachine_c ism) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openISMmAction = new ToolItem(bar, SWT.NONE);
		openISMmAction.setImage(CorePlugin.getImageDescriptor("metadata/InstanceStateChart.gif").createImage());
		openISMmAction.setToolTipText("Open Instance State Machine");
		openISMmAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tooltipHelper.hide();
				openCanvasEditor(ism);
			}
		});

	}

	public void addOpenOALEditorAction(ToolBar bar, final NonRootModelElement modelElement) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openActivityEditorAction = new ToolItem(bar, SWT.NONE);
		openActivityEditorAction.setImage(CorePlugin.getImageDescriptor("edit_oal.gif").createImage());
		openActivityEditorAction.setToolTipText("Open Activity Editor");
		openActivityEditorAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tooltipHelper.hide();
				openActivityEditor(modelElement);
			}
		});

	}


	public void addOpenDiagramAction(ToolBar bar, final NonRootModelElement modelElement) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openDiagramAction = new ToolItem(bar, SWT.NONE);
		openDiagramAction.setImage(CorePlugin.getImageDescriptor("green-bp.gif").createImage());
		openDiagramAction.setToolTipText("Open Diagram Editor");
		openDiagramAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tooltipHelper.hide();
				openCanvasEditor(modelElement);
			}
		});

	}



	public void openActivityEditor(final Object uut) {
		getEditor(Text_Editors_Extension_ID, 
				ACTIVITY_EDITOR_INPUT_ID,
				ACTIVITY_EDITOR_ID,
				getTooltipModelElement());
	}



	public void setMoveSupportAction(final Shell detailedShell, final Control control) {
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


	public void addOpenClassStateMachineDiagram(ToolBar bar, final ClassStateMachine_c csm) {
		new ToolItem(bar, SWT.SEPARATOR);
		ToolItem openCSMAction = new ToolItem(bar, SWT.NONE);
		openCSMAction.setImage(CorePlugin.getImageDescriptor("metadata/ClassStateChart.gif").createImage());
		openCSMAction.setToolTipText("Open Class State Machine");
		openCSMAction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tooltipHelper.hide();
				openCanvasEditor(csm);
			}
		});

	}


	public void setDescriptionText(String text){
		styledText.setText(text);
	}

	public Object getToolltipSource(){
		return tooltipHelper.getTooltipSource();
	}


	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	protected IEditorPart getEditor(String ExtensionID, String ClassPath, String EditorId, Object ModelElement){
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint editorExtPt = reg .getExtensionPoint("com.mentor.nucleus.bp.core.editors");//$NON-NLS-1$
		IExtension[] extensions = editorExtPt.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			if ( !extensions[i].getUniqueIdentifier().equals(ExtensionID))
				continue;

			try{
				Bundle bundle2 = Platform
						.getBundle(extensions[i].getNamespace());
				Class inputClass = bundle2.loadClass(ClassPath);
				Class[] type = new Class[1];
				type[0] = Object.class;
				//
				// Dynamically get the method createInstance, the supplied class must implement this
				//
				Method createInstanceMethod = inputClass
						.getMethod("createInstance", type); //$NON-NLS-1$
				Object[] args = new Object[1];
				args[0] = ModelElement;
				//
				// Invoke the method.
				// The method is static; no instance is needed, so first argument is null
				//
				IEditorInput input = (IEditorInput) createInstanceMethod
						.invoke(null, args);
				//
				// pass the input to the Eclipse editor, along with the class name supplied by
				// the extending plugin.
				//
				if (input != null) {
					return PlatformUI
							.getWorkbench()
							.getActiveWorkbenchWindow()
							.getActivePage()
							.openEditor(
									input,
									EditorId); //$NON-NLS-1$`
				}
			}catch (Exception e) {
				continue;
			}

		}
		return null;
	}







}
