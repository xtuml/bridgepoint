//=====================================================================
//
//File:      $RCSfile: UITestingUtilities.java,v $
//Version:   $Revision: 1.35 $
//Modified:  $Date: 2013/05/10 13:29:06 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.test.common;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import junit.framework.Assert;

import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.team.core.diff.IThreeWayDiff;
import org.eclipse.team.internal.ui.mapping.CommonViewerAdvisor.NavigableCommonViewer;
import org.eclipse.team.internal.ui.mapping.DiffTreeChangesSection;
import org.eclipse.team.internal.ui.mapping.ModelSynchronizePage;
import org.eclipse.team.internal.ui.synchronize.SynchronizeView;
import org.eclipse.team.ui.synchronize.ISynchronizeView;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ElementInSuppression_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.explorer.ui.actions.ExplorerCopyAction;
import com.mentor.nucleus.bp.ui.explorer.ui.actions.ExplorerCutAction;
import com.mentor.nucleus.bp.ui.explorer.ui.actions.ExplorerPasteAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCopyAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCutAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasPasteAction;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class UITestingUtilities {
	private static Point fDownLocation;
	
	public static void printControl(Composite parent, String intend) throws Exception {
		System.out.print(intend);
		printOject("Parent", parent); //$NON-NLS-1$

		intend = intend + "  "; //$NON-NLS-1$

		printItems(parent, intend);

		Control[] children = parent.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Composite) {
				printControl((Composite) children[i], intend);
			} else {
				System.out.print(intend);
				printOject("Child", children[i]); //$NON-NLS-1$
			}
		}
	}

	public static void printItems(Widget parent, String intend) throws Exception {
		Item[] items = (Item[]) invokeMethod(parent, "getItems", new Object[0]); //$NON-NLS-1$
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				System.out.print(intend + "Item"); //$NON-NLS-1$
				printProperties(items[i]);
				System.out.println();
				printItems(items[i], intend + "  "); //$NON-NLS-1$
			}
		}
	}
	
	protected static void printOject(String label, Object target) throws Exception{
		System.out.print(label);
		printProperties(target);
		System.out.println();
	}

	public static void printProperties(Object target) throws InvocationTargetException, IllegalAccessException {
		System.out.print("["); //$NON-NLS-1$
		try {
			PropertyDescriptor[] propertyInfos = Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors();
			for (int i = 0; i < propertyInfos.length; i++) {
				Class propertyType = propertyInfos[i].getPropertyType();
				if (propertyType != null && !propertyType.isArray()) {
					Method readMethod = propertyInfos[i].getReadMethod();

					if(readMethod != null){
						Object propertyValue = readMethod.invoke(target, new Object[0]);
						if (i > 0) {
							System.out.print("; "); //$NON-NLS-1$
						}
						System.out.print(propertyInfos[i].getName() + ":" + propertyValue); //$NON-NLS-1$
					}
				}
			}
		} catch (IntrospectionException e) {
		}
		System.out.print("]"); //$NON-NLS-1$
	}

	public static Object getProperty(Object target, String propertyName)
		throws InvocationTargetException, IllegalAccessException {
		return invokeMethod(target, "get" + propertyName, new Object[0]); //$NON-NLS-1$
	}

	public static Object invokeMethod(Object target, String methodName, Object[] arguments)
		throws InvocationTargetException, IllegalAccessException {
		Class targetClass = target.getClass();
		Class[] argumentTypes = new Class[arguments.length];
		for (int i = 0; i < argumentTypes.length; i++) {
			argumentTypes[i] = arguments[i].getClass();
		}

		try {
			Method method = targetClass.getMethod(methodName, argumentTypes);
			return method.invoke(target, arguments);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}

		return null;
	}

	public static MenuItem[] getMenuItems(final Menu menu, String childMenu) {

		Event event = new Event();
		event.x = 5;
		event.y = 5;
		menu.notifyListeners(SWT.Show, event);

		MenuItem[] items;

		// get child items if item in question
		// is contained in a child menu
		if (!childMenu.equals("")) {
			items = getChildMenuItems(menu, childMenu);
		} else {
			items = menu.getItems();
		}

		event.doit = true;
		menu.notifyListeners(SWT.Hide, event);

		return items;

	}
	
	private static MenuItem[] getChildMenuItems(Menu menu, String childMenu) {
		MenuItem[] parentItems = menu.getItems();
		for (int i = 0; i < parentItems.length; i++) {
			if (removeAccelerator(parentItems[i].getText()).equals(childMenu)) {
				MenuManager manager = (MenuManager) parentItems[i].getData();
				Menu child = manager.getMenu();
				Event event = new Event();
				event.x = 5;
				event.y = 5;
				child.notifyListeners(SWT.Show, event);
				return child.getItems();
			}
		}
		return null;
	}
	private static String removeAccelerator(String withAccelerator){
		return withAccelerator.replaceFirst("&", "");
	}
	
	public static boolean checkItemStatusInContextMenu(Menu menu, String text, String childMenu, boolean readonly) {
    	
    	MenuItem[] items = getMenuItems(menu, childMenu);
    	
    	// find the item in question
    	for(int i = 0; items != null && i < items.length; i++) {
    		if(removeAccelerator(items[i].getText()).indexOf(text) != -1) {
    			// verify the item
    			// if item is enabled
    			if(items[i].isEnabled()) {
    				// and is read only
    				if(readonly) {
    					// item should be disabled
    					return false;
    				} else {
    					// item is fine
    					return true;
    				}
    			} else {
    				// if item is disabled
    				// switch the returns
    				if(readonly) {
    					return true;
    				} else {
    					return false;
    				}
    			}
    		}
    	}
    	// action was not found
    	return false;
    }
	
	public static boolean menuItemExists(Menu menu, String childMenu, String item) {
		MenuItem[] menuItems = getMenuItems(menu, childMenu);
		for(int i = 0; menuItems != null && i < menuItems.length; i++) {
			if(removeAccelerator(menuItems[i].getText()).equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	public static IEditorPart getActiveEditor() {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(activeEditor instanceof ModelEditor) {
			return ((ModelEditor) activeEditor).getActivePart();
		} else
			return activeEditor;
	}
	
	public static void copyElement(NonRootModelElement element, GraphicalEditor ce) {
		clearGraphicalSelection(); addElementToGraphicalSelection(element);
		CanvasCopyAction canvascopyaction = new CanvasCopyAction(ce);
		canvascopyaction.run();
		BaseTest.waitForTransaction();
	}
	public static void copyElements(List elements, GraphicalEditor ce) {
		clearGraphicalSelection();
		Iterator iterator = elements.iterator();
		while(iterator.hasNext()) {
			addElementToGraphicalSelection(iterator.next());
		}
		CanvasCopyAction canvascopyaction = new CanvasCopyAction(ce);
		canvascopyaction.run();
		BaseTest.waitForTransaction();
	}
	public static void pasteClipboardContents(Point location, GraphicalEditor ce) {
		CanvasPasteAction canvaspasteaction = new CanvasPasteAction(ce);
		CanvasTestUtils.doMouseMove(location.x, location.y);
		CanvasTestUtils.doMouseContextPress(location.x, location.y);
		canvaspasteaction.run();
		BaseTest.dispatchEvents(0);
	}

	public static GraphicalEditor getGraphicalEditorFor(final NonRootModelElement columnInstance, boolean isRootOf) {
		return getGraphicalEditorFor(columnInstance, isRootOf, true);
	}
	
	public static GraphicalEditor getGraphicalEditorFor(final NonRootModelElement columnInstance, boolean isRootOf, boolean validateOpen) {
		if(columnInstance == null) return null;
		if(isRootOf) {
			IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getEditorReferences();
			int count = editorReferences.length;
			CanvasTestUtils.openCanvasEditor(columnInstance);
			editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getEditorReferences();
			int count_after = editorReferences.length;
			if(validateOpen) {
				if(count < count_after) {
					return (GraphicalEditor) getActiveEditor();	
				} else {
					// no editor was opened
					return null;
				}
			} else {
				return (GraphicalEditor) getActiveEditor();
			}
		}
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(columnInstance.getModelRoot().getId());
		GraphicalElement_c[] elements = GraphicalElement_c.GraphicalElementInstances(graphicsRoot);
		GraphicalEditor editor = getGraphicalEditorFrom(columnInstance, elements);
		if(editor == null) {
			// try system level elements
			elements = GraphicalElement_c.GraphicalElementInstances(Ooaofgraphics.getDefaultInstance());
			editor = getGraphicalEditorFrom(columnInstance, elements);
		}
		return editor;
	}
	
	private static GraphicalEditor getGraphicalEditorFrom(final NonRootModelElement columnInstance, GraphicalElement_c[] elements) {
		for(int i = 0; i < elements.length; i++) {
			if(elements[i].getRepresents() == null) {
				elements[i].setRepresents(Cl_c.Getinstancefromooa_id((Ooaofooa)
						columnInstance.getModelRoot(), elements[i].getOoa_id(),
						elements[i].getOoa_type()));
			}
			if(elements[i].getRepresents() == columnInstance) {
				Model_c model = Model_c.getOneGD_MDOnR1(elements[i]);
				CanvasTestUtils.openCanvasEditor(model.getRepresents());
				return (GraphicalEditor) getActiveEditor();
			}
		}
		return null;
	}

	public static GraphicalElement_c getGraphicalElementFor(NonRootModelElement element, boolean excludeSuppressedGraphics) {
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(element.getModelRoot().getId());
		if (PersistenceManager.findComponent(element.getModelRoot().getId())
				.getRootModelElement() == element) {
			graphicsRoot = Ooaofgraphics.getDefaultInstance();
		}
		GraphicalElement_c[] elements = GraphicalElement_c.GraphicalElementInstances(graphicsRoot);
		for(int i = 0; i < elements.length; i++) {
			ElementInSuppression_c eis = ElementInSuppression_c.getOneGD_EISOnR32(elements[i]);
			if(excludeSuppressedGraphics && eis != null)
				continue;
			if(elements[i].getRepresents() == null) {
				elements[i].setRepresents(Cl_c.Getinstancefromooa_id((Ooaofooa)
						element.getModelRoot(), elements[i].getOoa_id(),
						elements[i].getOoa_type()));
			}
			if(elements[i].getRepresents() == element) {
				return elements[i];
			}
		}
		return null;
	}

	public static MenuItem getMenuItem(Menu menu, String childMenu, String name) {
		MenuItem[] items = getMenuItems(menu, childMenu);
		for(int i = 0; items != null && i < items.length; i++) {
			if(items[i].getText().equals(name)) {
				return items[i];
			}
		}
		return null;		
	}
	
	public static MenuItem getMenuItem(Menu menu, String name) {
		MenuItem[] items = getMenuItems(menu, "");
		for(int i = 0; items != null && i < items.length; i++) {
			if(items[i].getText().equals(name)) {
				return items[i];
			}
		}
		return null;
	}
	
	public static void activateMenuItem(MenuItem item) {
		item.notifyListeners(SWT.Selection, null);
	}

	public static void activateMenuItem(Menu menu, String childMenu, String name) {
		MenuItem item = getMenuItem(menu, childMenu, name);
		activateMenuItem(item);
	}
	
	public static void activateMenuItem(Menu menu, String name) {
		// if the given name is a menu path, split it and execute
		// the final menu item
		String[] menuPath = name.split("::");
		if(menuPath.length > 1) {
			MenuItem item = null;
			for(int i = 0; i < menuPath.length; i++) {
				MenuItem[] children = getMenuItems(menu, "");
				for(int j = 0; j < children.length; j++) {
					if(removeAccelerator(children[j].getText()).equals(menuPath[i])) {
						item = children[j];
						if(item.getData() instanceof MenuManager) {
							MenuManager manager = (MenuManager) item.getData();
							menu = manager.getMenu();
						}
						break;
					}
				}
				if(item != null && !(item.getData() instanceof MenuManager)) {
					break;
				}
			}
			activateMenuItem(item);
		} else {
		MenuItem item = getMenuItem(menu, name);
		activateMenuItem(item);
	}
		UIUtil.dispatchAll();
	}

	public class Tool_by_id_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ModelTool_c selected = (ModelTool_c) candidate;
			return (selected.getTool_id().equals(m_id));
		}
		public Tool_by_id_c(UUID id) {
			m_id = id;
		}
		private UUID m_id;
	}
	
	public static void activateTool(AbstractTool tool) {
		CanvasUtilities.activateTool(tool);
	}
	
	public static void deactivateTool(AbstractTool tool) {
		CanvasUtilities.deactivateTool(tool);
	}
	
	public static boolean ctrlDown = false;
	public static void createMouseEvent(int x, int y, String eventType) {
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Event me = new Event();
		if(ctrlDown) {
			me.stateMask = SWT.CTRL;
		}

		if (eventType.equals("MouseMove")) {
			me.x = x;
			me.y = y;
			if(fDownLocation != null) {
				me.stateMask |= SWT.BUTTON1;
				// GEF has a drag event, which we need
				// to imitate.
				// we will move the mouse 10 times, to reach
				// the final destination
				int xDownLocation = fDownLocation.x;
				int yDownLocation = fDownLocation.y;
				int xDelta = x - xDownLocation;
				int yDelta = y - yDownLocation;
				int xIncrement = xDelta / 10;
				int yIncrement = yDelta / 10;
				boolean crossedThreshold = false;
				int xIncrementRealValue = xIncrement;
				if(xIncrementRealValue < 0)
					xIncrementRealValue = xIncrementRealValue * -1;
				int yIncrementRealValue = yIncrement;
				if(yIncrementRealValue < 0)
					yIncrementRealValue = yIncrementRealValue * -1;
				boolean reverse = x < xDownLocation;
				if(yIncrementRealValue >= xIncrementRealValue)
					reverse = y < yDownLocation;
				while(!crossedThreshold) {
					xDownLocation = xDownLocation + xIncrement;
					yDownLocation = yDownLocation + yIncrement;
					me.x = xDownLocation;
					me.y = yDownLocation;
					if(xIncrementRealValue > yIncrementRealValue) {
						if(reverse) {
							// check that the current x < expected x
							if(xDownLocation < x)
								crossedThreshold = true;
						} else {
							if(xDownLocation > x)
								crossedThreshold = true;
						}
					} else {
						if(reverse) {
							// check that the current x < expected x
							if(yDownLocation < y)
								crossedThreshold = true;
						} else {
							if(yDownLocation > y)
								crossedThreshold = true;
						}
					}
					if(crossedThreshold) {
						// If we crossed the expected location, do not
						// send this location. Instead send the expected
						// location
						me.x = x;
						me.y = y;
					}
					ce.getCanvas().notifyListeners(SWT.MouseMove, me);
				}
			} else
				ce.getCanvas().notifyListeners(SWT.MouseMove, me);
		} else if (eventType.equals("MouseDown")) {
			// with GEF we must create a mouse move first,
			// this will set the proper source edit part
			// for any creation request
			createMouseEvent(x, y, "MouseMove"); // $NON-NLS-1$
			me.x = x;
			me.y = y;
			me.button = 1;
			fDownLocation = new Point(x, y);
			ce.getCanvas().notifyListeners(SWT.MouseDown, me);
		} else if (eventType.equals("MouseDownRetainSelection")) {
			me.x = x;
			me.y = y;
			me.button = 1;
			me.stateMask |= SWT.CTRL;
			ce.getCanvas().notifyListeners(SWT.MouseDown, me);
		} else if (eventType.equals("MouseContextDown")) {
			me.x = x;
			me.y = y;
			me.button = 3;
			ce.getCanvas().notifyListeners(SWT.MouseDown, me);
		} else if (eventType.equals("MouseUp")) {
			me.x = x;
			me.y = y;
			me.button = 1;
			fDownLocation = null;
			ce.getCanvas().notifyListeners(SWT.MouseUp, me);
			// allow any transaction to complete if started
			// this will allow graphical symbols to be fully
			// configured before a test proceeds
			BaseTest.waitForTransaction();
		} else if (eventType.equals("MouseDoubleClick")) {
			// with GEF we must create a mouse move first,
			// this will set the proper source edit part
			// for any creation request
			createMouseEvent(x, y, "MouseMove"); // $NON-NLS-1$
			createMouseEvent(x, y, "MouseDown"); // $NON-NLS-1$
			fDownLocation = null;
			me.x = x;
			me.y = y;
			me.button = 1;
			ce.getCanvas().notifyListeners(SWT.MouseDoubleClick, me);
		}
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
	}	

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMousePress(int x, int y)
    {
		createMouseEvent(x, y, "MouseDown");
    }

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseRelease(int x, int y)
    {
		createMouseEvent(x, y, "MouseUp");
    }

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseMove(int x, int y)
    {
		createMouseEvent(x, y, "MouseMove");
    }

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseContextPress(int x, int y)
    {
		createMouseEvent(x, y, "MouseContextDown");
    }
    
    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMousePressRetainSelection(int x, int y)
    {
		createMouseEvent(x, y, "MouseDownRetainSelection");
    }
    
    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseDoubleClick(int x, int y)
    {
        createMouseEvent(x, y, "MouseDoubleClick");
    }
	
    private static void createElementInDiagram(GraphicalEditor ce, String toolSet , String toolName, int x1, int y1, int x2, int y2) {
		AbstractTool tool = null;
		if (toolSet != null) { 
			tool = ce.getTool(toolSet, toolName);
		} else {
			tool = ce.getTool(toolName);
		}
		if(tool != null) {
			activateTool(tool);
			doMouseMove(x1, y1);
			doMousePress(x1, y1);
			doMouseMove(x2,y2);
			doMouseRelease(x2,y2);
			deactivateTool(tool);
		} else {
			Assert.fail("Unable to locate tool for given diagram.");
		}
    }
    
	public static void createShapeInDiagram(GraphicalEditor ce, Rectangle rectangle, String toolName) {
		createShapeInDiagram(ce, rectangle, null, toolName);
	}
	public static void createShapeInDiagram(GraphicalEditor ce, Rectangle rectangle, String toolSet , String toolName) {
		createElementInDiagram(ce, toolSet, toolName, rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
	}
	
	public static void createConnectorInDiagram(GraphicalEditor ce, Point start,
			Point end, String toolSet, String toolName) 
	{
		createElementInDiagram(ce, toolSet, toolName, start.x, start.y, end.x, end.y);
	}
	
	public static void createConnectorInDiagram(GraphicalEditor ce, Point start,
			Point end, String toolName) 
	{
		createConnectorInDiagram(ce, start, end, null, toolName );
	}

	public static void cutElement(NonRootModelElement element, GraphicalEditor ce) {
		clearGraphicalSelection(); addElementToGraphicalSelection(element);
		CanvasCutAction canvascutaction = new CanvasCutAction(ce);
		canvascutaction.run();
		BaseTest.waitForTransaction();
	}
	
	public static void cutElementInExplorer(NonRootModelElement element, ExplorerView explorer) {
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(element);
		ExplorerCutAction cut = new ExplorerCutAction(explorer.getTreeViewer());
		cut.run();
		BaseTest.dispatchEvents(0);
	}
	
	public static AbstractTool getTool(String toolName) {
		return CanvasUtilities.getTool(toolName);
	}
	public static AbstractTool getTool(String toolSet, String toolName) {
		return CanvasUtilities.getTool(toolSet, toolName);
		
	}

	/**
	 * This method will select the appropriate editor part in
	 * the current editor, if the current editor is not the appropriate
	 * one it tries to locate it.  If a different editor was opened
	 * the method will return it
	 * 
	 * @param obj
	 */
	public static GraphicalEditor addElementToGraphicalSelection(Object obj) {
		GraphicalEditor editor = (GraphicalEditor) getActiveEditor();
		GraphicalEditor originalEditor = editor;
		GraphicalViewer viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
		if(obj instanceof GraphicalEditPart) {
			viewer.appendSelection((EditPart) obj);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			return editor;
		}
		Model_c model = (Model_c) viewer.getContents().getModel();
		GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(model);
		GraphicalElement_c element = null;
		for(int i = 0; i < elements.length; i++) {
			if(elements[i].getRepresents() == obj) {
				element = elements[i];
				break;
			}
		}
		if(element == null) {
			editor = getGraphicalEditorFor((NonRootModelElement) obj, false);
			if(editor == null)
				return null;
			viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
			model = (Model_c) viewer.getContents().getModel();
			elements = GraphicalElement_c.getManyGD_GEsOnR1(model);
			for(int i = 0; i < elements.length; i++) {
				if(elements[i].getRepresents() == obj) {
					element = elements[i];
					break;
				}
			}
		}
		if(element == null) {
			return originalEditor;
		}
		Object gObject = Shape_c.getOneGD_SHPOnR2(element);
		if(gObject == null)
			gObject = Connector_c.getOneGD_CONOnR2(element);
		GraphicalEditPart part = (GraphicalEditPart) viewer.getEditPartRegistry().get(gObject);
		viewer.appendSelection(part);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		return editor;
	}

	public static void clearGraphicalSelection() {
		GraphicalEditor editor = (GraphicalEditor) getActiveEditor();
		GraphicalViewer viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
		viewer.deselectAll();
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}

	public static void revealElementInGraphicalEditor(Shape_c shape) {
		GraphicalEditor editor = (GraphicalEditor) getActiveEditor();
		GraphicalViewer viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
		viewer.reveal(getEditorPartFor(shape));
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}

	public static EditPart getEditorPartFor(Object element) {
		final Object initialElement = element;
		if(element instanceof NonRootModelElement) {
			ModelRoot root = ((NonRootModelElement) element).getModelRoot();
			if(root instanceof Ooaofooa) {
				// locate the shape or connector that represents the
				// given element
				Ooaofgraphics graphicRoot = Ooaofgraphics.getInstance(root.getId());
				GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(graphicRoot, new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == initialElement;
					}
				});
				Shape_c shape = Shape_c.getOneGD_SHPOnR2(ge);
				if(shape != null) {
					element = shape;
				}
				Connector_c con = Connector_c.getOneGD_CONOnR2(ge);
				if(con != null) {
					element = con;
				}
			}
		}
		GraphicalEditor editor = (GraphicalEditor) getActiveEditor();
		GraphicalViewer viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
		return (GraphicalEditPart) viewer.getEditPartRegistry().get(element);
	}

	public static void removeElementFromGraphicalSelection(EditPart part) {
		GraphicalEditor editor = (GraphicalEditor) getActiveEditor();
		GraphicalViewer viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
		viewer.deselect(part);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}

	public static void pasteClipboardContentsInExplorer(Object destination) {
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(destination);
		ExplorerPasteAction paste = new ExplorerPasteAction();
		paste.run();
		BaseTest.dispatchEvents(0);
	}
	
	public static void pasteClipboardContentsInExplorer() {
		ExplorerPasteAction paste = new ExplorerPasteAction();
		paste.run();
		BaseTest.dispatchEvents(0);
	}

	public static void copyElementInExplorer(ExplorerView explorer) {
		ExplorerCopyAction copy = new ExplorerCopyAction(explorer.getTreeViewer());
		copy.run();
		BaseTest.dispatchEvents(0);
	}

	/**
	 * This function closes (hides) the property view if it is visible.
	 */
	public static void hidePropertyView() {
		PropertySheet propSheet = UITestingUtilities.getPropertySheet();
		if (propSheet != null) {
			propSheet.getSite().getPage().hideView(propSheet);
		}
	}
	
	/**
	 * This function finds the property view and returns it.
	 * 
	 * @return The property sheet view or null if the property sheet view is
	 *         not visible
	 */
	public static PropertySheet getPropertySheet() {
		PropertySheet propSheet = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (int i = 0; propSheet==null && i < windows.length; ++i) {
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int j = 0; propSheet==null && j < pages.length; ++j) {
				IViewReference[] views = pages[j].getViewReferences();
				for (int k = 0; propSheet==null && k < views.length; ++k) {
					if (views[k].getPart(false) instanceof PropertySheet) {
						propSheet = (PropertySheet)views[k].getPart(false);
					}
				}
			}
		}
		return propSheet;
	}
	
    public static Point getClearPoint(GraphicalEditor rowEditor) {
    	Point point = new Point(100, 100);
		List<GraphicalEditPart> allSymbols = GraphicalEditor.getAllSymbols(
				rowEditor.getGraphicalViewer(), rowEditor.getModel()
						.Hascontainersymbol());
		org.eclipse.draw2d.geometry.Rectangle extentRectangle = GraphicalZoomManager.getExtentRectangle(allSymbols);
		if(extentRectangle == null) {
			// no need to change position
			return point;
		}
		((DiagramEditPart) rowEditor.getGraphicalViewer().getContents())
				.getFigure().translateToAbsolute(extentRectangle);
		point.x = extentRectangle.getRight().x + 100;
		return point;
	}

	public static void cutElementsInExplorer(Object[] cuttableElements,
			ExplorerView explorer) {
		Selection.getInstance().clear();
		for(Object element : cuttableElements) {
			Selection.getInstance().addToSelection(element);
		}
		ExplorerCutAction cut = new ExplorerCutAction(explorer.getTreeViewer());
		cut.run();
		BaseTest.dispatchEvents(0);
	}

	public static String findInputDialogErrorText(InputDialog dialog) {
		List<Text> texts = findTextInControl(dialog.getShell());
		return texts.get(1).getText();
	}

	private static List<Text> findTextInControl(Composite composite) {
		List<Text> texts = new ArrayList<Text>();
		Control[] children = composite.getChildren();
		for(Control control : children) {
			if(control instanceof Text) {
				texts.add((Text) control);
			} else if(control instanceof Composite) {
				texts.addAll(findTextInControl((Composite) control));
			}
		}
		return texts;
	}

	public static Text findInputDialogTextField(InputDialog dialog) {
		List<Text> texts = findTextInControl(dialog.getShell());
		return texts.get(0);
	}
	
	/**
	 * This method returns a tree item in the given tree that contains
	 * the given text.  It does not do an exact match.
	 */
	public static TreeItem findItemInTree(Tree tree, String name) {
		TreeItem[] items = tree.getItems();
		for(TreeItem item : items) {
			TreeItem foundItem = findItemInTree(item, name);
			if(foundItem != null) {
				return foundItem;
			}
		}
		return null;
	}

	/**
	 * This method returns a tree item as a child of the given item
	 * that contains the given text.  It does not do an exact match.
	 */
	public static TreeItem findItemInTree(TreeItem item, String name) {
		if(item.getText().contains(name)) {
			return item;
		} else {
			TreeItem[] children = item.getItems();
			for(TreeItem child : children) {
				TreeItem foundItem = findItemInTree(child, name);
				if(foundItem != null) {
					return foundItem;
				}
			}
		}
		return null;
	}

    public static Tree findTree(Composite parent) {
        Control [] child_set = parent.getChildren();
        for ( int i = 0; i < child_set.length; ++i )
        {
            if ( child_set[i] instanceof Tree )
            {
            	return (Tree) child_set[i];
            }
            else if ( child_set[i] instanceof Composite )
            {
                Tree result = findTree((Composite)child_set[i]);
                if ( result != null )
                {
                   return result;   
                }
            }
        }
        return null;
    }

	public static void resizeMainWindow(int x, int y) {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.setSize(x, y);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.redraw();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.update();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}
	
}
