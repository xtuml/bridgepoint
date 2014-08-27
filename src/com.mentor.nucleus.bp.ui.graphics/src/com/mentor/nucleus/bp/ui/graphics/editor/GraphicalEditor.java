//========================================================================
//
//File:      $RCSfile: GraphicalEditor.java,v $
//Version:   $Revision: 1.24 $
//Modified:  $Date: 2013/05/10 05:37:55 $
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

package com.mentor.nucleus.bp.ui.graphics.editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.Tool;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.print.PrintGraphicalViewerOperation;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Diagram_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCopyAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCutAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasPasteAction;
import com.mentor.nucleus.bp.ui.graphics.actions.GraphicalActionConstants;
import com.mentor.nucleus.bp.ui.graphics.factories.ConnectorCreationFactory;
import com.mentor.nucleus.bp.ui.graphics.factories.ShapeCreationFactory;
import com.mentor.nucleus.bp.ui.graphics.figures.DecoratedPolylineConnection;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;
import com.mentor.nucleus.bp.ui.graphics.listeners.GraphicsEditorListener;
import com.mentor.nucleus.bp.ui.graphics.outline.GraphicalOutlinePage;
import com.mentor.nucleus.bp.ui.graphics.palette.GraphicsConnectionCreationToolEntry;
import com.mentor.nucleus.bp.ui.graphics.palette.GraphicsCreationToolEntry;
import com.mentor.nucleus.bp.ui.graphics.palette.ZoomToolEntry;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicsEditPartFactory;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicsScalableFreeformEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.print.PrintDiagramOperation;
import com.mentor.nucleus.bp.ui.graphics.properties.GraphicsPropertySourceProvider;
import com.mentor.nucleus.bp.ui.graphics.providers.CanvasEditorContextMenuProvider;
import com.mentor.nucleus.bp.ui.graphics.selection.GraphicalSelectionManager;
import com.mentor.nucleus.bp.ui.graphics.tools.GraphicalPanningSelectionTool;
import com.mentor.nucleus.bp.ui.properties.BridgepointPropertySheetPage;

public class GraphicalEditor extends GraphicalEditorWithFlyoutPalette implements
		IPartListener, IPropertyChangeListener {

	private static ArrayList<GraphicalEditor> fInstances = new ArrayList<GraphicalEditor>();
	private ModelEditor fParentEditor;

	public GraphicalEditor(ModelEditor parent) {
		super();
		fInstances.add(this);
		fParentEditor = parent;
	}

	public ModelEditor getParentEditor() {
		return fParentEditor;
	}

	public static GraphicalEditor getEditor(Model_c model) {
		for (GraphicalEditor editor : fInstances) {
			if (editor.getModel() == model)
				return editor;
		}
		return null;
	}

	Model_c fModel;
	private PaletteRoot fPaletteRoot;
	private PaletteDrawer fToolDrawer;
	private static final String OPEN = "open";
	protected Action undo, redo, selectAll;
	protected CanvasPasteAction paste;
	protected CanvasCutAction cut;
	protected CanvasCopyAction copy;
	protected Action open, delete, rename;
	protected Action print;
	private GraphicsEditorListener fEditorListener;
	private String DIAGRAM_VIEWPORT_X = "__DIAGRAM_VIEWPORT_X"; //$NON-NLS-1$
	private String DIAGRAM_VIEWPORT_Y = "__DIAGRAM_VIEWPORT_Y"; //$NON-NLS-1$
	private String DIAGRAM_ZOOM = "__DIAGRAM_ZOOM"; //$NON-NLS-1$
	private static Font diagramFont;
	private HashMap<IFigure, BPToolTipHelper> tooltipMap = new HashMap<IFigure, BPToolTipHelper>();


	@Override
	protected FlyoutPreferences getPalettePreferences() {
		FlyoutPreferences preferences = super.getPalettePreferences();
		preferences.setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
		return preferences;
	}

	public void refreshPalette() {
		fPaletteRoot = null;
		getEditDomain().setPaletteRoot(getPaletteRoot());
	}
	
	@Override
	protected PaletteRoot getPaletteRoot() {
		if (fPaletteRoot == null || fPaletteRoot.getChildren().isEmpty()) {
			if (fPaletteRoot == null)
				fPaletteRoot = new PaletteRoot();
			PaletteGroup controlGroup = new PaletteGroup("");
			ToolEntry tool = new PanningSelectionToolEntry() {

				@Override
				public Tool createTool() {
					Tool tool;
					try {
						tool = (Tool) GraphicalPanningSelectionTool.class
								.newInstance();
					} catch (IllegalAccessException iae) {
						return null;
					} catch (InstantiationException ie) {
						return null;
					}
					tool.setProperties(getToolProperties());
					return tool;
				}

			};
			getEditDomain().setActiveTool(tool.createTool());
			controlGroup.add(tool);
			ToolEntry zoomToolEntry = new ZoomToolEntry(
					"Zoom Tool",
					"- Left click to zoom in.  "
							+ "\n- Hold shift and left click to zoom out. "
							+ "\n- Select a group of symbols to zoom selection. "
							+ "\n- Hold ctrl and left click to zoom all. "
							+ "\n- Hold ctrl and use the mouse wheel to zoom in and out.",
					CorePlugin.getImageDescriptor("zoomAll.gif"), CorePlugin.getImageDescriptor("zoomAll.gif"), this); //$NON-NLS-1$ $NON-NLS-2$
			controlGroup.add(zoomToolEntry);
			fPaletteRoot.add(controlGroup);
			fPaletteRoot.setDefaultEntry(tool);
			fToolDrawer = new PaletteDrawer("Default Toolset");
			fPaletteRoot.add(fToolDrawer);
			fillPalette(fToolDrawer);
		}
		return fPaletteRoot;
	}

	private void fillPalette(PaletteDrawer drawer) {
		// have the editor's canvas create the tool objects that perform the
		// tools' functionality
		Model_c canvas = fModel;
		if (canvas == null)
			return;
		canvas.Initializetools();

		// for each tool employed by the canvas
		ModelTool_c[] tools = ModelTool_c.getManyCT_MTLsOnR100(canvas);
		ModelTool_c tool = null;
		ArrayList<PaletteDrawer> createdDrawers = new ArrayList<PaletteDrawer>();
		for (int i1 = 0; i1 < tools.length; i1++) {
			tool = tools[i1];

			// do not allow tool button if the element specification
			// indicates that this element is created along with
			// the canvas
			final ElementSpecification_c elem = ElementSpecification_c
					.getOneGD_ESOnR103(tool);
			if (elem != null && !elem.getCreationrule().equals("manual")) // $NON-NLS-1$
				continue;

			// if there is a element-specification associated with this tool's
			// function
			// (because it is a shape-tool)
			if (elem != null) {
				boolean isPackage = false;
				if (getModel().getRepresents() instanceof Package_c
						|| getModel().getRepresents() instanceof Component_c) {
					isPackage = true;

					if (getModel().getRepresents() instanceof Component_c) {
						Component_c self = (Component_c) getModel()
								.getRepresents();

						// If this component is under a EP_PKG then we do NOT
						// want to allow specialized packages to be created in
						// it.
						//
						//
						// If the component is NOT under a EP_PKG then we do
						// allow
						// specialized packages, but we do NOT allow an eP_PKG
						// to be
						// created in the component.
						Package_c pkg = Package_c
								.getOneEP_PKGOnR8000(PackageableElement_c
										.getOnePE_PEOnR8001(self));
						Component_c comp = Component_c
								.getOneC_COnR8003(PackageableElement_c
										.getOnePE_PEOnR8001(self));
						boolean isInGenericPackage = ((pkg != null) || (comp != null));
						int toolType = tool.getOoa_type();
						if (isInGenericPackage) {
							switch (toolType) {
							case Ooatype_c.InterfacePackage:
							case Ooatype_c.Sequence:
							case Ooatype_c.Communication:
							case Ooatype_c.UseCaseDiagram:
							case Ooatype_c.Activity:
								continue;
							default:
								break;
							}
						} else {
							switch (toolType) {
							case Ooatype_c.Package:
								continue;
							case Ooatype_c.Interface:
								continue;
							case Ooatype_c.UserDataType:
								continue;
							default:
								break;
							}
						}

					}
				}
				String category = elem.getToolcategory();
				ArrayList<PaletteDrawer> drawers = new ArrayList<PaletteDrawer>();
				if (category.equals("") || !isPackage) {
					drawers.add(drawer);
				}
				if (isPackage) {
					String[] categories = category.split(","); // $NON-NLS-1$
					for (int i = 0; i < categories.length; i++) {
						String label = categories[i].trim();
						if (label.equals(""))
							continue;
						boolean found = false;
						for (PaletteDrawer created : createdDrawers) {
							if (created.getLabel().equals(label)) {
								found = true;
								drawers.add(created);
								break;
							}
						}
						if (!found) {
							PaletteDrawer newDrawer = new PaletteDrawer(label);
							newDrawer
									.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
							fPaletteRoot.add(newDrawer);
							createdDrawers.add(newDrawer);
							drawers.add(newDrawer);
						}
					}
				}
				if (elem.getSymboltype().equals("shape")) {
					for (PaletteDrawer group : drawers) {
						GraphicsCreationToolEntry entry = new GraphicsCreationToolEntry(
								elem.getName(), "New " + elem.getName(),
								new ShapeCreationFactory(),
								CorePlugin.getImageDescriptor(elem
										.getIconname()),
								CorePlugin.getImageDescriptor(elem
										.getIconname()), tool.getOoa_type());
						entry.setSmallIcon(CorePlugin.getImageDescriptor(elem
								.getIconname()));
						entry.setLargeIcon(CorePlugin.getImageDescriptor(elem
								.getIconname()));
						group.add(entry);
					}
				} else if (elem.getSymboltype().equals("connector")) {
					for (PaletteDrawer group : drawers) {
						ConnectionCreationToolEntry entry = new GraphicsConnectionCreationToolEntry(
								elem.getName(), "New " + elem.getName(),
								new ConnectorCreationFactory(),
								CorePlugin.getImageDescriptor(elem
										.getIconname()),
								CorePlugin.getImageDescriptor(elem
										.getIconname()), tool.getOoa_type());
						entry.setSmallIcon(CorePlugin.getImageDescriptor(elem
								.getIconname()));
						entry.setLargeIcon(CorePlugin.getImageDescriptor(elem
								.getIconname()));
						group.add(entry);
					}
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			return getContentOutline();
		}
		if (adapter.equals(IPropertySheetPage.class)) {
			return getPropertySheet();
		}
		if (adapter.equals(ZoomManager.class)) {
			return (ZoomManager) getGraphicalViewer().getProperty(
					ZoomManager.class.toString());
		}
		return super.getAdapter(adapter);
	}

	private Object getPropertySheet() {
		PropertySheetPage pss = new BridgepointPropertySheetPage();
		pss.setPropertySourceProvider(new GraphicsPropertySourceProvider());
		return pss;
	}

	private Object getContentOutline() {
		return new GraphicalOutlinePage(getGraphicalViewer(), this);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// autosaved, nothing to do here
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {

		if (input instanceof GraphicalEditorInput) {
			GraphicalEditorInput canvasInput = (GraphicalEditorInput) input;
			fModel = canvasInput.getInput();
			fEditorListener = new GraphicsEditorListener(this);
			CanvasModelListener.setGraphicalRepresents(fModel);
			Ooaofooa.getDefaultInstance().addModelChangeListener(
					fEditorListener);
			Ooaofgraphics.getDefaultInstance().addModelChangeListener(
					fEditorListener);
			site.getPage().addPartListener(this);
			getTransactionManager().addTransactionListener(fEditorListener);
			setName(canvasInput.getName());
			setEditDomain(new GraphicalEditDomain(this, fModel.getRepresents()));
			Gr_c.cur_model = fModel;
			super.init(site, input);
		} else if (input instanceof FileEditorInput) {
			PersistableModelComponent pmc = PersistenceManager
					.findOrCreateComponent(((FileEditorInput) input).getFile()
							.getFullPath());
			if (pmc != null) {
				if (!pmc.isLoaded()) {
					try {
						pmc.load(new NullProgressMonitor());
					} catch (CoreException e) {
						PartInitException pie = new PartInitException(
								CorePlugin.createImportErrorStatus(true,
										"Error loading model element"));
						pie.fillInStackTrace();
						throw pie;
					}
				}
				GraphicalEditorInput cei = GraphicalEditorInput
						.createInstance(pmc.getRootModelElement());
				init(site, cei);
			}
		} else if(input instanceof SimpleGraphicalEditorInput) {
			SimpleGraphicalEditorInput sgei = (SimpleGraphicalEditorInput) input;
			fModel = sgei.getModel();
			fEditorListener = new GraphicsEditorListener(this);
			Ooaofgraphics.getDefaultInstance().addModelChangeListener(fEditorListener);
			site.getPage().addPartListener(this);
			setName(sgei.getName());
			setEditDomain(new GraphicalEditDomain(this, fModel.getRepresents()));
			Gr_c.cur_model = fModel;
			super.init(site, input);
		}
	}

	public Control getCanvas() {
		if (getGraphicalViewer() == null)
			return null;
		return getGraphicalControl();
	}

	public void setName(String name) {
		setPartName(name);
	}

	@Override
	public String getTitleToolTip() {
		Object element = getModel().getRepresents();
		Method method = null;
		try {
			method = element.getClass().getMethod("Getpath",
					new Class[] { String.class });
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		if (method != null) {
			String result = getPartName();
			try {
				result = (String) method.invoke(element, new Object[] { "" });
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			return result;
		}
		return getPartName();
	}

	public void setEditorInput(IEditorInput input) {
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		// for now all BP diagrams are auto-persisted
		return false;
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();
		Gr_c.cur_canvas = (Canvas) getCanvas();

		viewer.setRootEditPart(new GraphicsScalableFreeformEditPart());

		// Zoom
		GraphicalZoomManager manager = (GraphicalZoomManager) getGraphicalViewer()
				.getProperty(ZoomManager.class.toString());
		if (manager != null) {
			manager.setModel(getModel());
			manager.addZoomListener(new ZoomListener() {

				@Override
				public void zoomChanged(double zoom) {
					storeZoomValue(zoom);
				}
			});
			List<String> zoomLevels = new ArrayList<String>(3);
			zoomLevels.add(ZoomManager.FIT_ALL);
			zoomLevels.add(ZoomManager.FIT_WIDTH);
			zoomLevels.add(ZoomManager.FIT_HEIGHT);
			zoomLevels.add(GraphicalZoomManager.FIT_SELECTION);

			manager.setZoomLevelContributions(zoomLevels);
			manager.setZoomLevels(new double[] { .10, .20, .25, .50, .75, 1.00,
					1.25, 1.50, 1.75, 2.00, 2.25, 2.50, 3.00, 3.50, 4.00 });
			double zoom = getZoom();
			if (zoom != -1) {
				manager.configureZoomAtStartup(zoom);
			}
		}

		// Actions
		IAction zoomIn = new ZoomInAction(manager);
		IAction zoomOut = new ZoomOutAction(manager);
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);

		// Scroll-wheel Zoom
		getGraphicalViewer().setProperty(
				MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);

		viewer.setEditPartFactory(new GraphicsEditPartFactory());
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer)
				.setParent(getCommonKeyHandler()));
		ContextMenuProvider cmProvider = new CanvasEditorContextMenuProvider(
				viewer, this);
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(cmProvider, viewer);

		viewer.setSelectionManager(new GraphicalSelectionManager());
	}

	@Override
	protected void createGraphicalViewer(Composite parent) {
		// if the diagram to be opened has no elements, or
		// in the case of a component diagram only one then
		// we initialize the scroll and zoom values to defaults
		GraphicalElement_c[] children = GraphicalElement_c
				.getManyGD_GEsOnR1(fModel);
		if (children.length == 0
				|| (children.length == 1 && fModel.Hascontainersymbol())) {
			storeZoomValue(1.0);
			setViewportLocationInStorage(4000, 3000);
		}
		GraphicalViewer viewer = new ScrollingGraphicalViewer() {

			@Override
			public void reveal(EditPart part) {
				// do not support reveal at this time
				// as the only time it is used is during
				// selection of a graphical element when
				// it is not completely visible
				// We at this time do not believe that this
				// is a "good feature"
			}
			
			/*
			 * override setEditDomain where the event dispatcher object is 
			 * created in order to use BridgePoint custom tooltip helper
			 */
			@Override
			public void setEditDomain(EditDomain domain){
				super.setEditDomain(domain);
				getLightweightSystem().setEventDispatcher(new DomainEventDispatcher(domain, this){

					// Override the creation of ToolTip helper object 
					BPToolTipHelper defaultHelper;
					@Override 
					protected ToolTipHelper getToolTipHelper() {
						/*
						 * Create new helper each time to support multi tool tip
						 * window. In order to associate their tooltip helper
						 * with their editor to hide when the editor is not 
						 * visible, reshow when it is visible, a hash map 
						 * shall be created to store created helper, to be
						 * notified by editor visiblity change
						 */
						IFigure hoverSource = this.getCursorTarget();
						
						if(hoverSource instanceof TextFlow) {
							hoverSource = hoverSource.getParent();
						}
						if(hoverSource instanceof FlowPage) {
							hoverSource = hoverSource.getParent();
						}
						
						if (hoverSource instanceof ShapeImageFigure || hoverSource instanceof DecoratedPolylineConnection){
							BPToolTipHelper existedHelper = tooltipMap.get(hoverSource);
							if ( existedHelper != null)
								return existedHelper;

							BPToolTipHelper newHelper = new BPToolTipHelper(control);
							tooltipMap.put(hoverSource,newHelper);
							return newHelper;
						}
						
						if (defaultHelper == null)
							defaultHelper = new BPToolTipHelper(control);
						
						// Notify all editor helpers to close their simple tooltip if up
						Collection<BPToolTipHelper> helpers = tooltipMap.values();
						for (BPToolTipHelper helper : helpers) {
							helper.hideSimpleToolTip();
						}
						
						return defaultHelper;
						
					}
				});
			}

		};
		viewer.createControl(parent);
		setGraphicalViewer(viewer);
		configureGraphicalViewer();
		hookGraphicalViewer();
		initializeGraphicalViewer();
		((FigureCanvas) getCanvas()).getVerticalBar().addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						storeViewportLocation();
					}
				});
		((FigureCanvas) getCanvas()).getHorizontalBar().addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						storeViewportLocation();
					}
				});
		// add a control listener to zoom fit once resized
		((FigureCanvas) getCanvas()).addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				if (shouldZoomFit()
						&& ((FigureCanvas) getCanvas()).getViewport()
								.getHorizontalRangeModel().getExtent() > 100) {
					((FigureCanvas) getCanvas()).getViewport().revalidate();
					((FigureCanvas) getCanvas()).getViewport()
							.getUpdateManager().performUpdate();
					zoomAll();
				}
			}

			@Override
			public void controlMoved(ControlEvent e) {
				// do nothing
			}
		});
		((FigureCanvas) getCanvas()).setFont(getFont());
	}

	protected boolean shouldZoomFit() {
		if (getZoom() == -1) {
			return true;
		}
		return false;
	}

	protected Diagram_c getDiagram() {
		Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(getModel());
		return diagram;
	}

	public double getZoom() {
		double zoom = -1;
		try {
			zoom = Activator.getDefault().getDialogSettings().getDouble(
					getZoomKey());
		} catch (NumberFormatException e) {
			// value was never set or was set incorrectly
			// do not worry about logging an error just return
			// a non set value
			return -1;
		}
		return zoom;
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		if (fModel != null) {
			getGraphicalViewer().setContents(fModel);
		}
		configureGridOptions();
		// add self to font property change listener list
		JFaceResources.getFontRegistry().addListener(this);
	}

	public void configureGridOptions() {
		boolean showGrid = CorePlugin.getDefault().getPreferenceStore()
				.getBoolean(BridgePointPreferencesStore.SHOW_GRID);
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
				new Boolean(showGrid));
		boolean snapGrid = CorePlugin.getDefault().getPreferenceStore()
				.getBoolean(BridgePointPreferencesStore.SNAP_TO_GRID);
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED,
				new Boolean(snapGrid));
		int gridSpacing = CorePlugin.getDefault().getPreferenceStore().getInt(
				BridgePointPreferencesStore.GRID_SPACING);
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_SPACING,
				new Dimension(gridSpacing, gridSpacing));
	}

	private KeyHandler getCommonKeyHandler() {
		return null;
	}

	public IAction getOpenAction() {
		return open;
	}

	public IAction getUndoAction() {
		return undo;
	}

	public IAction getRedoAction() {
		return redo;
	}

	public CanvasCutAction getCutAction() {
		return cut;
	}

	public CanvasCopyAction getCopyAction() {
		return copy;
	}

	public CanvasPasteAction getPasteAction() {
		return paste;
	}

	public IAction getSelectAllAction() {
		return selectAll;
	}

	public IAction getDeleteAction() {
		return delete;
	}

	public IAction getRenameAction() {
		return rename;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		super.createActions();
		// 'New' is provided as a sub-menu only. See 'createMenus'
		open = new Action(OPEN) {
			public void run() {
				handleOpen(null, fModel,
						(IStructuredSelection) getGraphicalViewer()
								.getSelectionManager().getSelection());
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this model Element");
		// 'Open With' is provided as a sub-menu only. See 'createMenus'

		cut = new CanvasCutAction(this);
		cut.setId(ActionFactory.CUT.getId());
		copy = new CanvasCopyAction(this);
		copy.setId(ActionFactory.COPY.getId());
		getActionRegistry().registerAction(copy);
		getActionRegistry().registerAction(cut);
		
		// line
		paste = new CanvasPasteAction(this);
		paste.setId(ActionFactory.PASTE.getId());
		getActionRegistry().registerAction(paste);

		TransactionManager manager = getTransactionManager();
		undo = manager.getUndoAction();
		undo.setId(ActionFactory.UNDO.getId());
		redo = manager.getRedoAction();
		redo.setId(ActionFactory.REDO.getId());
		getActionRegistry().registerAction(undo);
		getActionRegistry().registerAction(redo);

		selectAll = new SelectAllAction(this) {

			@Override
			public void run() {
				GraphicalViewer viewer = (GraphicalViewer) getAdapter(GraphicalViewer.class);
				if (viewer != null) {
					viewer
							.setSelection(new StructuredSelection(filterOutTextEditParts(
									getAllSymbols(getGraphicalViewer(), fModel
											.Hascontainersymbol()))));
				}
			}

			private List<GraphicalEditPart> filterOutTextEditParts(
					List<GraphicalEditPart> allSymbols) {
				List<GraphicalEditPart> filtered = new ArrayList<GraphicalEditPart>();
				for(GraphicalEditPart part : allSymbols) {
					// we filter text edit parts as they are not really selectable,
					// which in-turn causes duplicates in the selection list as they
					// are migrated to their owning part
					if(!(part instanceof TextEditPart)) {
						filtered.add(part);
					}
				}
				return filtered;
			}

		};
		selectAll.setId(ActionFactory.SELECT_ALL.getId());
		getActionRegistry().registerAction(selectAll);

		//
		// Delete and Rename are retargetable actions defined by core.
		//
		delete = new Action() {
			public void run() {
				Transaction transaction = null;
				TransactionManager manager = getTransactionManager();
				try {
					transaction = manager.startTransaction(
							Transaction.DELETE_TRANS, new ModelRoot[] {
									Ooaofooa.getDefaultInstance(),
									Ooaofgraphics.getDefaultInstance() });

					IStructuredSelection structuredSelection = Selection
							.getInstance().getStructuredSelection();
					Iterator<?> iterator = structuredSelection.iterator();
					while (iterator.hasNext()) {
						NonRootModelElement element = (NonRootModelElement) iterator
								.next();
							if (element instanceof GraphicalElement_c) {
								((GraphicalElement_c) element).Dispose();
							Selection.getInstance()
									.removeFromSelection(element);
						}

					}
					if (!Selection.getInstance().getStructuredSelection()
							.isEmpty()) {
						CorePlugin.getDeleteAction().run();
					}								

					manager.endTransaction(transaction);
				} catch (TransactionException e) {
					if (transaction != null && manager != null
							&& manager.getActiveTransaction() == transaction)
						manager.cancelTransaction(transaction);
					CorePlugin.logError("Unable to start transaction", e);
				}
				for (Object part : getGraphicalViewer().getSelectedEditParts()) {
					if (part instanceof EditPart) {
						if(((EditPart) part).getParent() != null) {
							((EditPart) part).getParent().refresh();
						}
					}
				}
			}

			@Override
			public boolean isEnabled() {
				return CanvasEditorContextMenuProvider
						.enableDelete((IStructuredSelection) getSite()
								.getSelectionProvider().getSelection());
			}

		};
		delete.setText("Delete");
		delete.setToolTipText("Delete the Model Element");
		delete.setId(ActionFactory.DELETE.getId());
		getActionRegistry().registerAction(delete);

		// rename = CorePlugin.getRenameAction(treeViewer); <- need to
		// generalize renameAction first
		rename = new Action() {
			public void run() {
				Object selection = Selection.getInstance().getStructuredSelection()
						.getFirstElement();
				if (selection != null) {
					String oldName = Cl_c.Getname(selection);
					Shell sh = getSite().getShell();
					RenameAction.handleRename(selection, oldName, sh);
				}
			}

			@Override
			public boolean isEnabled() {
				if(CanvasCutAction.selectionContainsOnlyCoreElements()) {
					return RenameAction.canRenameAction();
				}
				return false;
			}

		};
		rename.setText("Rename");
		rename.setToolTipText("Rename the Model Element");
		rename.setEnabled(true); // Retargetable Actions work removes this line
		rename.setId(ActionFactory.RENAME.getId());
		getActionRegistry().registerAction(rename);

		print = new Action() {
			public void run() {
				handlePrint();
			}
		};
		print.setText("Print");
		print.setToolTipText("Print the Diagram");
		print.setEnabled(true);
		print.setId(ActionFactory.PRINT.getId());
		getActionRegistry().registerAction(print);

		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new Action() {

			@Override
			public void run() {
				zoomAll();
			}

		};
		action.setText("Zoom Page");
		action.setImageDescriptor(CorePlugin.getImageDescriptor("zoomAll.gif")); // $NON-NLS-1$
		action.setId(GraphicalActionConstants.ZOOM_PAGE);
		action.setToolTipText("Click to zoom the entire contents");
		registry.registerAction(action);

		action = new Action() {
			@Override
			public void run() {
				zoomSelected();
			}
		};
		action.setText("Zoom Selection");
		action.setImageDescriptor(CorePlugin.getImageDescriptor("zoomSel.gif")); // $NON-NLS-1$
		action.setId(GraphicalActionConstants.ZOOM_SEL);
		action.setToolTipText("Click to zoom the current selection");
		registry.registerAction(action);

		action = new MatchWidthAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MatchHeightAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new DirectEditAction((IWorkbenchPart) this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	public TransactionManager getTransactionManager() {
		return fModel.getTransactionManager();
	}

	protected void handlePrint() {
		int style = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell().getStyle();

		Shell shell = new Shell((style & SWT.MIRRORED) != 0 ? SWT.RIGHT_TO_LEFT
				: SWT.NONE);

		PrintDialog dialog = new PrintDialog(shell, SWT.NULL);

		PrinterData data = dialog.open();
		if (data != null) {
			PrintGraphicalViewerOperation operation = new PrintDiagramOperation(
					new Printer(data), getGraphicalViewer(), this);

			operation.setPrintMode(PrintGraphicalViewerOperation.FIT_PAGE);

			operation.run("Print canvas.");
		}
	}

	/**
	 * Fire up an editor
	 */
	public static void handleOpen(Point location, Model_c model,
			IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			Object current = selection.iterator().next();
			if (current instanceof EditPart) {
				current = ((EditPart) current).getModel();
				if (current instanceof FloatingText_c) {
					FloatingText_c text = (FloatingText_c) current;
					Connector_c connector = Connector_c.getOneGD_CONOnR8(text);
					if (connector != null)
						current = connector;
					Shape_c shape = Shape_c.getOneGD_SHPOnR27(text);
					if (shape != null)
						current = shape;
				}
				if (current instanceof Model_c) {
					current = ((Model_c) current).getRepresents();
				} else if (current instanceof Shape_c) {
					GraphicalElement_c elem = GraphicalElement_c
							.getOneGD_GEOnR2((Shape_c) current);
					current = elem.getRepresents();
				} else if (current instanceof Connector_c) {
					GraphicalElement_c elem = GraphicalElement_c
							.getOneGD_GEOnR2((Connector_c) current);
					current = elem.getRepresents();
				}
			}

			// if a mouse event was given, and the selected element is a
			// model-class
			if (location != null && current instanceof ModelClass_c) {
				// find the graphical element that represents the selected
				// model-class
				final Object finalCurrent = current;
				GraphicalElement_c element = GraphicalElement_c
						.GraphicalElementInstance(model.getModelRoot(),
								new ClassQueryInterface_c() {
									public boolean evaluate(Object candidate) {
										return ((GraphicalElement_c) candidate)
												.getRepresents() == finalCurrent;
									}
								});

				// ask the shape associated with the above graphical-element
				// what
				// the mouse-event represents a double-click on, since the shape
				// may be displaying an icon which is a link to a different
				// model
				// element
				Shape_c shape = Shape_c.getOneGD_SHPOnR2(element);
				Graphelement_c graphElement = Graphelement_c
						.getOneDIM_GEOnR23(element);
				current = shape.Getrepresents((int) (location.x - graphElement
						.getPositionx()), (int) (location.y - graphElement
						.getPositiony()));
			}

			// see if the current element should open
			// something other than itself
			current = EditorUtil.getElementToEdit(current);

			String name = current.getClass().getName();
			//
			// Get the registry
			//
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			//
			// Get all the plugins that have extended this point
			//
			IExtensionPoint extPt = reg
					.getExtensionPoint("com.mentor.nucleus.bp.core.editors"); //$NON-NLS-1$
			IExtension[] exts = extPt.getExtensions();
			// Repeat for each extension until we find a default editor
			for (int i = 0; i < exts.length; i++) {
				IConfigurationElement[] elems = exts[i]
						.getConfigurationElements();
				for (int j = 0; j < elems.length; j++) {
					// Find the editor elements
					if (elems[j].getName().equals("editor")) { //$NON-NLS-1$
						IConfigurationElement[] edElems = elems[j]
								.getChildren();
						for (int k = 0; k < edElems.length; k++) {
							//
							// Is this editor the default for the current model
							// element ?
							//
							if (edElems[k].getName().equals("defaultFor") && //$NON-NLS-1$
									edElems[k].getAttribute("class").equals(
											name)) {
								try {
									//
									// Get the class supplied for the input
									//
									// always use this bundle, other graphical
									// plug-ins
									// will provide their own open method
									Bundle bundle = Platform.getBundle(elems[j]
											.getContributor().getName());
									Class<?> inputClass = bundle
											.loadClass(elems[j]
													.getAttribute("input")); //$NON-NLS-1$
									Class<?>[] type = new Class[1];
									type[0] = Object.class;
									//
									// Dynamically get the method
									// createInstance, the supplied class must
									// implement this
									//
									Method createInstance = inputClass
											.getMethod("createInstance", type); //$NON-NLS-1$
									Object[] args = new Object[1];
									args[0] = current;

									//
									// Invoke the method.
									// The method is static; no instance is
									// needed, so first argument is null
									//
									IEditorInput input = (IEditorInput) createInstance
											.invoke(null, args);
									//
									// pass the input to the Eclipse editor,
									// along with the class name supplied by
									// the extending plugin.
									//
									if (input != null) {
										PlatformUI
												.getWorkbench()
												.getActiveWorkbenchWindow()
												.getActivePage()
												.openEditor(
														input,
														elems[j]
																.getAttribute("class")); //$NON-NLS-1$
									}
									return;
								} catch (ClassNotFoundException e) {
									CanvasPlugin.logError(
											"Input Class not found", e); //$NON-NLS-1$
								} catch (NoSuchMethodException e) {
									CanvasPlugin
											.logError(
													"Class does not implement static method createInstance", e); //$NON-NLS-1$
								} catch (InvocationTargetException e) {
									CanvasPlugin
											.logError(
													"Exception occured on invocation of static method createInstance of the Target", e.getTargetException()); //$NON-NLS-1$
								} catch (IllegalAccessException e) {
									CanvasPlugin
											.logError(
													"Target does not support static method createInstance", e); //$NON-NLS-1$
								} catch (PartInitException e) {
									CanvasPlugin.logError(
											"Could not activate Editor", e); //$NON-NLS-1$
								}
							}
						}
					}
				}
			}
		}
	}

	public Model_c getModel() {
		return fModel;
	}

	public void refresh() {
		// this refresh will update contents, but
		// not visually refresh children
		if (getGraphicalViewer() != null
				&& getGraphicalViewer().getContents() != null) {
			getGraphicalViewer().getContents().refresh();
			for (Object child : getGraphicalViewer().getContents()
					.getChildren()) {
				EditPart part = (EditPart) child;
				part.refresh();
			}
		}
		refreshPartName();
	}

	public void refreshPartName() {
		if (fModel != null && fModel.getRepresents() instanceof NonRootModelElement) {
			setName(GraphicsUtil
					.getCanvasEditorTitle((NonRootModelElement) fModel
							.getRepresents()));
		}
	}

	@Override
	public void dispose() {
		getEditorSite().getPage().removePartListener(this);
		super.dispose();
		Ooaofooa.getDefaultInstance()
				.removeModelChangeListener(fEditorListener);
		Ooaofgraphics.getDefaultInstance().removeModelChangeListener(
				fEditorListener);
		if (getTransactionManager() != null)
			getTransactionManager().removeTransactionListener(fEditorListener);
		fInstances.remove(this);
		if(fInstances.isEmpty()) {
			// if we are the last diagram editor, then dispose
			// the font used
			if (diagramFont != null
					&& diagramFont != PlatformUI.getWorkbench().getDisplay()
							.getSystemFont()) {
				diagramFont.dispose();
				diagramFont = null;
			}
		}
		Collection<BPToolTipHelper> helpers = tooltipMap.values();
		for (BPToolTipHelper helper : helpers) {
			helper.dispose();
		}
		JFaceResources.getFontRegistry().removeListener(this);
	}

	public static Font getFont() {
		if(diagramFont == null || diagramFont.isDisposed()) {
			String prefFont = JFacePreferences.getPreferenceStore().getString(
					"com.mentor.nucleus.bp.canvas.font");//$NON-NLS-1$
			if(prefFont == null || prefFont.equals("")) {
				// something strange has happened, should not occur
				// but to be safe set a default
				diagramFont = PlatformUI.getWorkbench().getDisplay()
						.getSystemFont();
			} else {
				prefFont = prefFont.substring(0, prefFont.indexOf(';'));
				FontData prefFontData = new FontData(prefFont);
				int fontSize = (int) (prefFontData.getHeight());
				prefFontData.setHeight(fontSize);
				diagramFont = new Font(PlatformUI.getWorkbench().getDisplay(), prefFontData);
			}
		}
		return diagramFont;
	}
	
	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part == this || part == getParentEditor()) {
			// when activated, synchronize the graphical selection
			// with the core selection
			((GraphicalSelectionManager) getGraphicalViewer()
					.getSelectionManager()).synchronizeSelectionToCore();
			// additionally reset the current canvas variable
			// in the Gr_c class
			Gr_c.cur_canvas = (Canvas) getCanvas();
			
			// Notify all editor tooltip helpers to redisplay the tooltip if 
			// possible
			Collection<BPToolTipHelper> helpers = tooltipMap.values();
			for (BPToolTipHelper helper : helpers) {
				helper.activate();
			}
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// do nothing
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		// do nothing
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// Notify all editor tooltip helpers to hide the tooltips if 
		// visible
		Collection<BPToolTipHelper> helpers = tooltipMap.values();
		if (part == this || part == getParentEditor()) {
			for (BPToolTipHelper helper : helpers) {
				helper.deactivate();
			}
		}
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// do nothing
	}

	public static void redrawAll() {
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorReferences.length; i++) {
			IEditorPart editor = editorReferences[i].getEditor(false);
			if (editor instanceof ModelEditor) {
				GraphicalEditor gEditor = ((ModelEditor) editor)
						.getGraphicalEditor();
				gEditor.redraw();
			}
		}
	}

	private void redraw() {
		((GraphicalEditPart) getGraphicalViewer().getRootEditPart())
				.getFigure().erase();
		((GraphicalEditPart) getGraphicalViewer().getRootEditPart())
				.getFigure().getUpdateManager().performUpdate();
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// If not the active editor, ignore selection changed.
		if (this.equals(getSite().getPage().getActiveEditor()))
			updateActions(getSelectionActions());
		else if (getSite().getPage().getActiveEditor() instanceof ModelEditor) {
			ModelEditor editor = (ModelEditor) getSite().getPage()
					.getActiveEditor();
			if (this.equals(editor.getActivePart())) {
				updateActions(getSelectionActions());
			}
		}
	}

	public AbstractTool getTool(String toolSet, String toolName) {

		for (Object entry : fPaletteRoot.getChildren()) {
			if (entry instanceof PaletteDrawer) {
				PaletteDrawer drawer = (PaletteDrawer) entry;
				if (drawer.getLabel().equals(toolSet)) {
					AbstractTool tool = getTool(toolName, drawer.getChildren());
					if (tool != null)
						return tool;
				}
			}
		}
		return null;
	}

	public void setModel(Model_c model) {
		fModel = model;
	}

	public AbstractTool getTool(String toolName) {
		return getTool(toolName, fPaletteRoot.getChildren());
	}

	public AbstractTool getTool(String toolName, List<?> children) {
		for (Object entry : children) {
			if (entry instanceof PaletteDrawer) {
				PaletteDrawer drawer = (PaletteDrawer) entry;
				AbstractTool tool = getTool(toolName, drawer.getChildren());
				if (tool != null)
					return tool;
			} else {
				if (entry instanceof ToolEntry) {
					ToolEntry pEntry = (ToolEntry) entry;
					if (pEntry.getLabel().equals(toolName)) {
						return (AbstractTool) pEntry.createTool();
					}
				}
			}
		}
		return null;
	}

	public void doDelete() {
		getDeleteAction().run();
	}

	public void zoomAll() {
		ZoomManager manager = (ZoomManager) getAdapter(ZoomManager.class);
		manager.setZoomAsText(ZoomManager.FIT_ALL);
	}

	public void zoomOut() {
		ZoomManager manager = (ZoomManager) getAdapter(ZoomManager.class);
		manager.zoomOut();
	}

	public void zoomSelected() {
		GraphicalZoomManager manager = (GraphicalZoomManager) getAdapter(ZoomManager.class);
		manager.setZoomAsText(GraphicalZoomManager.FIT_SELECTION);
	}

	public DefaultEditDomain getDomain() {
		return super.getEditDomain();
	}

	public void updateModel(Model_c newModel) {
		newModel.Initializetools();
		GraphicalViewer viewer = (GraphicalViewer) getAdapter(GraphicalViewer.class);
		setModel(newModel);
		Gr_c.cur_model = newModel;
		GraphicalZoomManager zoomManager = (GraphicalZoomManager) getAdapter(ZoomManager.class);
		zoomManager.setModel(newModel);
		viewer.setContents(newModel);
		GraphicalEditDomain domain = (GraphicalEditDomain) getEditDomain();
		domain.setElement(newModel.getRepresents());
	}

	@SuppressWarnings("unchecked")
	public static List<GraphicalEditPart> getAllSymbols(GraphicalViewer root,
			boolean modelHasContainer) {
		List<GraphicalEditPart> symbols = new ArrayList<GraphicalEditPart>();
		symbols.addAll(root.getContents().getChildren());
		if (modelHasContainer) {
			symbols.addAll(((GraphicalEditPart) root.getContents()
					.getChildren().get(0)).getChildren());
		}
		ArrayList<GraphicalEditPart> shapeText = new ArrayList<GraphicalEditPart>();
		ArrayList<GraphicalEditPart> allConnections = new ArrayList<GraphicalEditPart>();
		for (GraphicalEditPart child : symbols) {
			AbstractGraphicalEditPart childPart = (AbstractGraphicalEditPart) child;
			allConnections.addAll(childPart.getSourceConnections());
			shapeText.addAll(child.getChildren());
		}
		symbols.addAll(shapeText);
		// add connections that start on connections
		for (Object child : allConnections) {
			AbstractGraphicalEditPart childPart = (AbstractGraphicalEditPart) child;
			symbols.addAll(childPart.getSourceConnections());
			// add all text for the source connections
			for(Object sourceCon : childPart.getSourceConnections()) {
				AbstractGraphicalEditPart source = (AbstractGraphicalEditPart) sourceCon;
				symbols.addAll(source.getChildren());
			}
			// add all text for this connector
			symbols.addAll(childPart.getChildren());
		}

		// add any free floating connectors
		allConnections.addAll(((GraphicalEditPart) root.getRootEditPart()
				.getContents()).getSourceConnections());
		for(Object child : ((GraphicalEditPart) root.getRootEditPart()
				.getContents()).getSourceConnections()) {
			AbstractGraphicalEditPart childPart = (AbstractGraphicalEditPart) child;
			// add all text for the free floating connectors;
			allConnections.addAll(childPart.getChildren());	
		}
		symbols.addAll(allConnections);
		// filter out all hidden elements
		List<GraphicalEditPart> filteredList = new ArrayList<GraphicalEditPart>();
		for(GraphicalEditPart part : symbols) {
			if (part.getFigure().getParent().isVisible()) {
				filteredList.add(part);
			}
		}
		return filteredList;
	}

	/**
	 * Generates an {@link Image} of the contents of this editor.
	 * 
	 * @param fitRectangle
	 * @return
	 */
	public Image getDiagramImage(Rectangle fitRectangle) {
		if (fitRectangle == null)
			fitRectangle = GraphicalZoomManager
					.getExtentRectangle(getAllSymbols(getGraphicalViewer(),
							getModel().Hascontainersymbol()));
		Image image = new Image(Display.getDefault(), fitRectangle.width,
				fitRectangle.height);
		PrintDiagramOperation.printImage(image, getGraphicalViewer(),
				fitRectangle, getModel().Hascontainersymbol(),
				PrintDiagramOperation.FIT_PAGE);
		return image;
	}

	public GraphicalViewer getGraphicalViewer() {
		return super.getGraphicalViewer();
	}

	public Point getPersistedViewportLocation() {
		IDialogSettings dialogSettings = Activator.getDefault()
				.getDialogSettings();
		try {
			float viewportX = dialogSettings.getInt(getViewportXValueKey());
			float viewportY = dialogSettings.getInt(getViewportYValueKey());
			return new Point(viewportX, viewportY);
		} catch (NumberFormatException e) {
			// value was never set or was incorrectly
			// set, do not worry about logging an error
			// just return a default value
			return new Point(-1, -1);
		}
	}

	private void storeZoomValue(double zoom) {
		Activator.getDefault().getDialogSettings().put(getZoomKey(), zoom);
	}

	public void storeViewportLocation() {
		Point location = ((FigureCanvas) getCanvas()).getViewport()
				.getViewLocation().getCopy();
		Activator.getDefault().getDialogSettings().put(getViewportXValueKey(),
				location.x);
		Activator.getDefault().getDialogSettings().put(getViewportYValueKey(),
				location.y);
	}

	private String getViewportXValueKey() {
		return getDiagramId() + ":" + DIAGRAM_VIEWPORT_X;
	}

	private String getViewportYValueKey() {
		return getDiagramId() + ":" + DIAGRAM_VIEWPORT_Y;
	}

	private String getZoomKey() {
		return getDiagramId() + ":" + DIAGRAM_ZOOM;
	}

	private String getDiagramId() {
		return Cl_c.Getooa_idfrominstance(getModel().getRepresents())
				.toString()
				+ "-" + getModel().getOoa_type();
	}

	/**
	 * Convience method for manually setting the persisted scroll values
	 */
	public void setViewportLocationInStorage(int x, int y) {
		Activator.getDefault().getDialogSettings().put(getViewportXValueKey(),
				x);
		Activator.getDefault().getDialogSettings().put(getViewportYValueKey(),
				y);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getProperty().equals("com.mentor.nucleus.bp.canvas.font")) {
			if(diagramFont != null) {
				FontData fontData = diagramFont.getFontData()[0];
				if(!((FontData[]) event.getNewValue())[0].equals(fontData)) {
					diagramFont.dispose();
					diagramFont = null;
				}
				// update the figure canvas with the new font
				((FigureCanvas) getCanvas()).setFont(getFont());
				refresh();
			}
		}
	}

	public static int getGridSpacing() {
		return CorePlugin.getDefault().getPreferenceStore().getInt(
				BridgePointPreferencesStore.GRID_SPACING);
	}

}
