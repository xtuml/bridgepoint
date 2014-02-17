//=====================================================================
//
//File:      $RCSfile: CanvasTestUtilities.java,v $
//Version:   $Revision: 1.44 $
//Modified:  $Date: 2013/05/10 05:41:50 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
package com.mentor.nucleus.bp.ui.canvas.test;

import java.util.UUID;

import junit.framework.TestCase;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ClassAsSubtype_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Diagram_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.SelectionTool_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class CanvasTestUtilities {
	
    /**
     * Returns the graphical-element under the given graphics-root that 
     * represents the given model-element
     */
    public static GraphicalElement_c getGraphicalElement(
        Ooaofgraphics graphicsRoot, final NonRootModelElement modelElement)
    {
        GraphicalElement_c element = GraphicalElement_c.GraphicalElementInstance(
            graphicsRoot, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    GraphicalElement_c element = (GraphicalElement_c)candidate;
                    return element.getRepresents() == modelElement;
                }
            });
        return element;
    }

    static public void openActivityEditor(final Object uut) {
    	CanvasUtilities.openActivityEditor(uut);
	}

static public GraphicalEditor getCanvasEditor(String title) {
	IWorkbenchWindow[] windows =
		PlatformUI.getWorkbench().getWorkbenchWindows();
	for (int i = 0; i < windows.length; ++i) {
		IWorkbenchPage[] pages = windows[i].getPages();
		for (int j = 0; j < pages.length; ++j) {
			IEditorReference[] editors = pages[j].getEditorReferences();
			for (int k = 0; k < editors.length; ++k) {
				if (editors[k].getPartName().equals(title)) {
					IEditorPart editor = editors[k].getEditor(false);
					if(editor instanceof ModelEditor) {
						return ((ModelEditor) editor).getGraphicalEditor();
					} else
						return null;
				}
			}
		}
	}
	return null;
}
public class findModelDTPByName implements ClassQueryInterface_c {
	public findModelDTPByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		DataTypePackage_c selected = (DataTypePackage_c) test_instance;
		return selected.getName().equals(m_name);
	}
}
public class findModelEEPByName implements ClassQueryInterface_c {
	public findModelEEPByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		ExternalEntityPackage_c selected = (ExternalEntityPackage_c) test_instance;
		return selected.getName().equals(m_name);
	}
}

public class findModelPKGByName implements ClassQueryInterface_c {
	public findModelPKGByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		Package_c selected = (Package_c) test_instance;
		return selected.getName().equals(m_name);
	}
}
public class findModelFPByName implements ClassQueryInterface_c {
	public findModelFPByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		FunctionPackage_c selected = (FunctionPackage_c) test_instance;
		return selected.getName().equals(m_name);
	}
}
public class findModelEDTByName implements ClassQueryInterface_c {
	public findModelEDTByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		EnumerationDataType_c selected = (EnumerationDataType_c) test_instance;
		return selected.Get_name().equals(m_name);
	}
}
public class findModelEEByName implements ClassQueryInterface_c {
	public findModelEEByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		ExternalEntity_c selected = (ExternalEntity_c) test_instance;
		return selected.getName().equals(m_name);
	}
}
public class findModelSSByName implements ClassQueryInterface_c {
	public findModelSSByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		Subsystem_c selected = (Subsystem_c) test_instance;
		return selected.getName().equals(m_name);
	}
}

public static void createKeyEvent(char key) {
	GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
	Event ke = new Event();
	ke.character = key;
	ce.getCanvas().notifyListeners(SWT.KeyDown, ke);
}

public static void createMouseEvent(int x, int y, String eventType) {
	UITestingUtilities.createMouseEvent(x, y, eventType);
}
public static GraphicalEditor openDomainCanvasEditor(Ooaofooa modelRoot) {
	Domain_c uut = Domain_c.DomainInstance(modelRoot);
	TestCase.assertNotNull(uut);
	CanvasUtilities.openCanvasEditor(uut);
	return getDomainCanvasEditor();
}
public static GraphicalEditor openClassDiagramEditor(Ooaofooa modelRoot) {
	Package_c uut = Package_c.PackageInstance(modelRoot);
	TestCase.assertNotNull(uut);
	CanvasUtilities.openCanvasEditor(uut);
	return getClassDiagramEditor();
}
private static GraphicalEditor getClassDiagramEditor() {
	return getCanvasEditor("Odms: Package Diagram");
}
private static GraphicalEditor getDomainCanvasEditor() {
	return getCanvasEditor("odms: Package Diagram");
}

public static void updateTreeItem(Object t1, String newValue ) throws Exception
{
	StructuredSelection sel = new StructuredSelection(t1);
	Selection.getInstance().setSelection(sel);
	IWorkbenchPage m_wp = PlatformUI.getWorkbench().showPerspective("com.mentor.nucleus.bp.core.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	ExplorerView m_bp_view = (ExplorerView)m_wp.findView(BridgePointPerspective.ID_MGC_BP_EXPLORER);
	TreeViewer m_bp_tree = m_bp_view.getTreeViewer();
    m_bp_tree.refresh();
	m_bp_tree.expandAll();
	m_bp_tree.getTree().selectAll();
	TreeItem x [] = m_bp_tree.getTree().getSelection();
	TestCase.assertNotNull( "Tree is empty", x );
	for ( int i = 0; i < x.length; ++i )
	{
		if ( x[i].getData() == t1 )
		{
			TreeItem [] x_set = { x[i] };
			m_bp_tree.getTree().setSelection(x_set);
			RenameAction t2 = (RenameAction)CorePlugin.getRenameAction(m_bp_tree);
			t2.run();
			t2.getTextEditor().setText(newValue);
			Event e = new Event();
			e.type = SWT.Traverse;
			e.detail = SWT.TRAVERSE_RETURN;
			e.widget = t2.getTextEditor();
			t2.getTextEditor().notifyListeners(e.type, e);
			Display d = Display.getCurrent();
			while ( d.readAndDispatch() ) ;
			return;
		}
	}
	TestCase.fail( "Tree item not found" );
}

public static SelectionTool_c getSelectionTool (Ooaofgraphics graphicsModelRoot, String toolName) {
	GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
	Model_c mdl = ce.getModel();
	ModelTool_c mt = null;
	SelectionTool_c selTool = null;
	UITestingUtilities util = new UITestingUtilities();
	UUID tid = mdl.Gettoolidbyname(toolName);
	mt = ModelTool_c.ModelToolInstance(graphicsModelRoot, util.new Tool_by_id_c(tid));
	selTool = SelectionTool_c.getOneCT_SELOnR102(mt);
		
	return selTool;
	
}

public class findModelClassByName implements ClassQueryInterface_c {
	public findModelClassByName(String name) {
		m_name = name;
	}
	private String m_name;
	public boolean evaluate(Object test_instance) {
		ModelClass_c selected = (ModelClass_c) test_instance;
		return selected.getName().equals(m_name);
	}
}
public class findModelStateByName implements ClassQueryInterface_c {
	public findModelStateByName(String name) {
		m_name = name;
	} 
	private String m_name;
	public boolean evaluate(Object test_instance) {
		StateMachineState_c selected = (StateMachineState_c) test_instance;
		return selected.getName().equals(m_name);
	}
}

public class findGraphicalElementByOOAID implements ClassQueryInterface_c {
	public findGraphicalElementByOOAID(UUID ooaid, int ooatype) {
		m_ooaid = ooaid;
		m_ooatype = ooatype;
	}
	private UUID m_ooaid;
	private int m_ooatype;
	public boolean evaluate(Object test_instance) {
		GraphicalElement_c selected = (GraphicalElement_c) test_instance;
		return selected.getOoa_id().equals(m_ooaid)
			&& selected.getOoa_type() == m_ooatype;
	}
}
public static Shape_c getModelClassShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	ModelClass_c mc =
		ModelClass_c.ModelClassInstance(modelRoot, ctu.new findModelClassByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()),
			ctu.new findGraphicalElementByOOAID(mc.getObj_id(), 21));
	return Shape_c.getOneGD_SHPOnR2(ge);
}
public static Shape_c getModelStateShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	StateMachineState_c sms =
		StateMachineState_c.StateMachineStateInstance(modelRoot, ctu.new findModelStateByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(sms.getSmstt_id(), 41));
	return Shape_c.getOneGD_SHPOnR2(ge);
}

public static Shape_c getModelDTPShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	DataTypePackage_c dtp =
		DataTypePackage_c.DataTypePackageInstance(modelRoot, ctu.new findModelDTPByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(dtp.Get_ooa_id(), 53));
	return Shape_c.getOneGD_SHPOnR2(ge);
}
public static Shape_c getModelFPShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	FunctionPackage_c fp =
		FunctionPackage_c.FunctionPackageInstance(modelRoot, ctu.new findModelFPByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(fp.Get_ooa_id(), 54));
	return Shape_c.getOneGD_SHPOnR2(ge);
}
public static Shape_c getModelEEPShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	ExternalEntityPackage_c eep =
		ExternalEntityPackage_c.ExternalEntityPackageInstance(modelRoot, ctu.new findModelEEPByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(eep.Get_ooa_id(), 55));
	return Shape_c.getOneGD_SHPOnR2(ge);
}

public static Shape_c getModelPKGShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	Package_c eep =Package_c.PackageInstance(modelRoot, ctu.new findModelPKGByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(eep.Get_ooa_id(), 108));
	return Shape_c.getOneGD_SHPOnR2(ge);
}
public static Shape_c getModelEEShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	ExternalEntity_c ee =
		ExternalEntity_c.ExternalEntityInstance(modelRoot, ctu.new findModelEEByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(ee.getEe_id(), 12));
	return Shape_c.getOneGD_SHPOnR2(ge);
}
public static Shape_c getModelEDTShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	EnumerationDataType_c edt =
		EnumerationDataType_c.EnumerationDataTypeInstance(modelRoot, ctu.new findModelEDTByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(edt.Get_ooa_id(), 52));
	return Shape_c.getOneGD_SHPOnR2(ge);
}

public static Shape_c getModelSSShape(Ooaofooa modelRoot, String name) {
	CanvasTestUtilities ctu = new CanvasTestUtilities();
	Subsystem_c ss =
		Subsystem_c.SubsystemInstance(modelRoot, ctu.new findModelSSByName(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(Ooaofgraphics.getInstance(modelRoot.getId()), 
			ctu.new findGraphicalElementByOOAID(ss.Get_ooa_id(), 11));
	return Shape_c.getOneGD_SHPOnR2(ge);
}

public static Point getShapeNWCorner(Shape_c shp) {
    Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(shp);
    Graphelement_c element = Graphelement_c.getOneDIM_GEOnR301(node);
	return getNWCorner(element);
}
public static Point getNWCorner(Graphelement_c element) {
	return new Point((int)(element.getPositionx()), 
	    (int)(element.getPositiony()));
}

public static Point getShapeSECorner (Shape_c shp) {
    Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(shp);
    Graphelement_c element = Graphelement_c.getOneDIM_GEOnR301(node);
	return getSECorner(element, node);
}
public static Point getSECorner(Graphelement_c element, Graphnode_c node) {
	return new Point(
	    (int)(element.getPositionx() + node.getWidth()), 
	    (int)(element.getPositiony() + node.getHeight()));
}
public static Point getConnectorCenter(Connector_c cut) {
	LineSegment_c line = LineSegment_c.getOneGD_LSOnR6(cut);
    return getSegmentCenter(line);
	}	
public static Point getSegmentCenter(LineSegment_c line) {
	int resultx;
	int resulty;
		
	Waypoint_c lineStart = Waypoint_c.getOneDIM_WAYOnR21(line);
	Waypoint_c lineEnd = Waypoint_c.getOneDIM_WAYOnR22(line);
	if ((int)lineEnd.getPositionx() == (int)lineStart.getPositionx()) {
		resultx = (int)lineEnd.getPositionx();
	} else {
		resultx = ((int)(lineEnd.getPositionx() - lineStart.getPositionx())) / 2;
		resultx = (int)(lineEnd.getPositionx() - resultx);
	}	
	
	if ((int)lineEnd.getPositiony() == (int)lineStart.getPositiony()) {
		resulty = (int)lineEnd.getPositiony();
	} else {
		resulty = ((int)(lineEnd.getPositiony() - lineStart.getPositiony())) / 2;
		resulty = (int)(lineEnd.getPositiony() - resulty);
	}	
	return new Point(resultx, resulty);	
}
public static Point convertToMouseCoor(Point center, Model_c mdl) {
	return CanvasUtilities.convertToMouseCoor(center, mdl);
}
public static class ClassByName implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		ModelClass_c selected = (ModelClass_c) candidate;
		return (selected.getName().equals(m_name));
	}
	public ClassByName(String name) {
		m_name = name;		
	}
	private String m_name;
}

public class Class_by_name_c extends ClassByName
{
    public Class_by_name_c(String name)
    {
        super(name);
    }
}

public static class AssociationByNumber implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		Association_c selected = (Association_c) candidate;
		return (selected.getNumb() == m_numb);
	}
  public AssociationByNumber(int numb) {
	  m_numb = numb;
  }
  private int m_numb;
}

public class Association_by_numb_c extends AssociationByNumber 
{
    public Association_by_numb_c(int numb) 
    {
        super(numb);
    }
}
  
public static class ConnectorByOoaid implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
	  Connector_c con = (Connector_c) candidate;
	  GraphicalElement_c selected = GraphicalElement_c.getOneGD_GEOnR2(con); 
		return (selected.getOoa_id().equals(m_ooa_id) && selected.getOoa_type() == m_ooa_type);
	}
  public ConnectorByOoaid(UUID ooa_id, int ooa_type) {
	  m_ooa_id = ooa_id;
      m_ooa_type = ooa_type;
  }
  private UUID m_ooa_id;
  private int m_ooa_type;
}

public class Connector_by_ooaid_c extends ConnectorByOoaid
{
    public Connector_by_ooaid_c(UUID ooa_id, int ooa_type)
    {
        super(ooa_id, ooa_type);
    }
}

public class SuperTypeConnector_by_ooaid_c implements ClassQueryInterface_c {
  public boolean evaluate(Object candidate) {
    Connector_c con = (Connector_c) candidate;
    GraphicalElement_c selected = GraphicalElement_c.getOneGD_GEOnR2(con); 
    return (selected.getOoa_id().equals(m_ooa_id) && selected.getOoa_type() == 36);
  }
  SuperTypeConnector_by_ooaid_c(UUID ooa_id) {
    m_ooa_id = ooa_id;
  }
  private UUID m_ooa_id;
}
public class SubTypeConnector_by_instance_c implements ClassQueryInterface_c {
  public boolean evaluate(Object candidate) {
    Connector_c con = (Connector_c) candidate;
    GraphicalElement_c selected = GraphicalElement_c.getOneGD_GEOnR2(con); 
    return (selected.getRepresents() == m_cas && selected.getOoa_type() == 35);
  }
  SubTypeConnector_by_instance_c(ClassAsSubtype_c element) {
     m_cas = element;
  }
  private ClassAsSubtype_c m_cas; 
}
public class Associative_by_ooaid_c implements ClassQueryInterface_c {
  public boolean evaluate(Object candidate) {
    Connector_c con = (Connector_c) candidate;
    GraphicalElement_c selected = GraphicalElement_c.getOneGD_GEOnR2(con); 
    return (selected.getOoa_id().equals(m_ooa_id) && selected.getOoa_type() == 34);
  }
  Associative_by_ooaid_c(UUID ooa_id) {
    m_ooa_id = ooa_id;
  }
  private UUID m_ooa_id;
}

public static void openDiagramEditor(Object uut) {
		
	TestCase.assertNotNull(uut);
	CanvasUtilities.openCanvasEditor(uut);
		
}

    /**
     * Opens the instance state machine diagram for the class of the
     * given name within the given model-root.
     */
    public static void openInstanceStateDiagram(Ooaofooa modelRoot, 
        String className)
    {
        InstanceStateMachine_c machine = 
            InstanceStateMachine_c.getOneSM_ISMOnR518(
                ModelClass_c.ModelClassInstance(modelRoot, 
                    new ClassByName(className)));
        openDiagramEditor(machine);
    }
    
    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMousePress(int x, int y)
    {
		UITestingUtilities.doMousePress(x, y);
    }

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseRelease(int x, int y)
    {
    	UITestingUtilities.doMouseRelease(x, y);
    }

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseMove(int x, int y)
    {
    	UITestingUtilities.doMouseMove(x, y);
    }

    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseContextPress(int x, int y)
    {
    	UITestingUtilities.doMouseContextPress(x, y);
    }
    
    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMousePressRetainSelection(int x, int y)
    {
    	UITestingUtilities.doMousePressRetainSelection(x, y);
    }
    
    /**
     * A convenience method.  See method wrapped.
     */
    public static void doMouseDoubleClick(int x, int y)
    {
    	UITestingUtilities.doMouseDoubleClick(x, y);
    }
    
    /**
     * Sets the zoom factor of the diagram object associated with the given
     * canvas such that no scaling is performed, and zeroes the diagram's
     * viewport offset, so that the canvas coordinate space exactly matches 
     * that of the diagram elements it contains.
     */
    public static void matchCanvasSpaceToModelSpace(Model_c canvas)
    {
		Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(canvas);
		diagram.setZoom(1f);
		diagram.setViewportx(0);
		diagram.setViewporty(0);
    }
    
    /**
     * Returns the first connector that is found to be attached
     * to the given shape.
     */
    public static Connector_c getAnyConnectorAttachedToShape(Shape_c shape)
    {
        Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(shape);
        Graphelement_c elem = Graphelement_c.getOneDIM_GEOnR301(node);
        Graphconnector_c graphConn = Graphconnector_c.getOneDIM_CONOnR311(elem);
        Graphedge_c edge = Graphedge_c.getOneDIM_EDOnR320(graphConn);
        return Connector_c.getOneGD_CONOnR20(edge);
    }
    
    /**
     * Returns the connector (from the given graphics-root) which represents the 
     * association (from the given model-root) of the given number.
     */
    public static Connector_c getAssociationConnector(Ooaofooa modelRoot,
        Ooaofgraphics graphicsRoot, final int assocNumber)
    {
	    // find the association of the given number
		final Association_c relation =
			Association_c.AssociationInstance(
				modelRoot,
				new ClassQueryInterface_c() { 
					public boolean evaluate(Object candidate) {
						return ((Association_c)candidate).getNumb() == assocNumber;
					}
				});
				
		// find the connector associated with the association found above
		Connector_c connector =
			Connector_c.ConnectorInstance(
				graphicsRoot,
				new ClassQueryInterface_c() { 
					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected =
							GraphicalElement_c.getOneGD_GEOnR2((Connector_c)candidate);
						return selected.getOoa_id().equals(relation.getRel_id())	
							&& selected.getOoa_type() == 24;
					}
				});
		
		return connector;
    }
    
    /**
     * Returns the starting segment of the given connector within the
     * given graphics-root.
     */
    public static LineSegment_c getStartingSegment(Ooaofgraphics graphicsRoot,
        Connector_c connector)
    {
	    final UUID segmentId = connector.Getstartingsegmentid();
	    LineSegment_c segment = LineSegment_c.LineSegmentInstance(
	        graphicsRoot, 
	        new ClassQueryInterface_c() {
	            public boolean evaluate(Object candidate) {
	                return ((LineSegment_c)candidate).getElementid().equals(segmentId);
	            }
	        });
        return segment;
    }
}