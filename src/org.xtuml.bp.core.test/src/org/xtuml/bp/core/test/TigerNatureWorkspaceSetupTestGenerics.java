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
package org.xtuml.bp.core.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

public class TigerNatureWorkspaceSetupTestGenerics extends CanvasTest {

	String test_id = null;
	private boolean generateResults = getGenerateResults();

	public TigerNatureWorkspaceSetupTestGenerics(String name) throws CoreException {
		super("org.xtuml.bp.core.test", name);

		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
	}

	private static boolean getGenerateResults() {
		String env = System.getenv("generateResults");
		if(env == null) {
			return false;
		} else {
			boolean result = Boolean.parseBoolean(env);
			return result;
		}
	}

	protected String getResultName() {
		return "TigerNatureTestSetup" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch())
			;
		Ooaofooa.setPersistEnabled(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static void createNewPackage(String name, SystemModel_c systemModel) {
        
		Transaction t = Cl_c.Starttransaction(systemModel, "Create Package");
		systemModel.Newpackage();
		Cl_c.Endtransaction(systemModel, t);
		
		 Package_c pkgs[]= Package_c.getManyEP_PKGsOnR1401(systemModel);
          for (int i=0 ;i<pkgs.length;i++ )
          { 
        	  if (pkgs[i].getName().equals("Unnamed Package"))
        	  {
        		    Transaction t2 = Cl_c.Starttransaction(pkgs[i], "Rename Package");
        		    pkgs[i].setName(name);
        	        Cl_c.Endtransaction(pkgs[i], t2);
        		  
        	  }
        	  
          }
	}

	public File getDomainFile(IProject project, String domainName) {
		File modelFile = new File(project.getLocation() + File.separator
				+ Ooaofooa.MODELS_DIRNAME + File.separator + domainName + "." + Ooaofooa.MODELS_EXT);
		return modelFile;
	}
	public class Package_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Package_c selected = (Package_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Package_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
	}
	
	// Enforce ordering of the test functions
	public void testWorkspaceSetup() throws CoreException {
		dotestSetupForInterfaceAssignment();
		dotestSetupForComponentAssignment();
		dotestSetupForDataTypeAssignment();
	}

	public void dotestSetupForInterfaceAssignment() {
		// create an interface in one package
		test_id = "InterfaceAssignmentSetup-1";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		Package_c ifacePkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPackage))));
		assertNotNull("Unable to find Interface Package for setup", ifacePkg);

		CanvasUtilities.openCanvasEditor(ifacePkg);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Interface");

		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ifacePkg));
		assertNotNull("Interface was not successfully created.", iface);
        Transaction t = Cl_c.Starttransaction(iface, "Rename test element");
		iface.setName(test_id);
		Cl_c.Endtransaction(iface, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(ifacePkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);

		// create an interface reference in another
		// package
		test_id = "InterfaceAssignmentSetup-2";
		// create the package
		CanvasUtilities.openCanvasEditor(system);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 400,
				200, 200), "Package");
		Package_c otherPackage = Package_c.getOneEP_PKGOnR1401(system, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Unnamed Package");
			}

		});
		assertNotNull("Other package was not created successfully.", otherPackage);
        t = Cl_c.Starttransaction(otherPackage, "Rename test element");
		otherPackage.setName(test_id);
		Cl_c.Endtransaction(otherPackage, t);

		dispatchEvents(0);

		// create a component package
		CanvasUtilities.openCanvasEditor(otherPackage);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");
		Package_c compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((otherPackage)));
		assertNotNull("Package was not created successfully.", compPkg);
		Transaction tt = Cl_c.Starttransaction(compPkg, "Rename test element");
		compPkg.setName("Component Package");
		Cl_c.Endtransaction(compPkg, tt);
  
		
		// create a component
		CanvasUtilities.openCanvasEditor(compPkg);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		
		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,200, 200), "Components", "Component");
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Component was not successfully created.", component);

		// create an interface reference
		UITestingUtilities.createConnectorInDiagram(ce, new Point(150, 150),
				new Point(450, 150), "Provided Interface");

		graphicsModelRoot = Ooaofgraphics.getInstance(compPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void dotestSetupForComponentAssignment() {
		// create a component in one package
		test_id = "ComponentAssignmentSetup-1";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		Package_c compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPackage))));
		assertNotNull("Unable to find Component Package for setup", compPkg);

		
//		
//		Transaction tt = null;
//		PackageableElement_c pes[] =PackageableElement_c.getManyPE_PEsOnR8000(compPkg);
//         for (int i = 0; i< pes.length ; i++)
//         {
//        	 tt = Cl_c.Starttransaction(pes[i], "Delete pckageable element");
//        	 pes[i].Dispose();
//        	 Cl_c.Endtransaction(compPkg,tt);
//         }
//         
		
		CanvasUtilities.openCanvasEditor(compPkg);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		
		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(500, 500,
				1300, 1300), "Components", "Component");
		

		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Component was not successfully created.", comp);
        Transaction t = Cl_c.Starttransaction(comp, "Rename test element");
		comp.setName(test_id);
		Cl_c.Endtransaction(comp, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(compPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);

		// create an imported component in another
		// package
		test_id = "ComponentAssignmentSetup-2";
		// create the package
		CanvasUtilities.openCanvasEditor(system);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 400,
				200, 200), "Package");
		Package_c otherPackage = Package_c.getOneEP_PKGOnR1401(system, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Unnamed Package");
			}

		});
		assertNotNull("Other package was not created successfully.", otherPackage);
        t = Cl_c.Starttransaction(otherPackage, "Rename test element");
		otherPackage.setName(test_id);
		Cl_c.Endtransaction(otherPackage, t);

		dispatchEvents(0);

		// create a component package
		CanvasUtilities.openCanvasEditor(otherPackage);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");
		compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((otherPackage)));
		assertNotNull("Component Package was not created successfully.", compPkg);
		
		Transaction tt = Cl_c.Starttransaction(compPkg, "Rename test element");
		compPkg.setName("Component Reference Package");
		Cl_c.Endtransaction(compPkg, tt);

		

		// create an imported component
		CanvasUtilities.openCanvasEditor(compPkg);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Components","Component Reference");
		ComponentReference_c icomp = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Imported Component was not successfully created.", icomp);

		graphicsModelRoot = Ooaofgraphics.getInstance(compPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void dotestSetupForDataTypeAssignment() {
		test_id = "DataTypeAssignmentSetup";
		// create a user data type under a package
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		Package_c dtPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPackage))));
		assertNotNull("Unable to find Data Type Package for setup.", dtPkg);

		CanvasUtilities.openCanvasEditor(dtPkg);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "User Data Type");

		UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(DataType_c
				.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dtPkg)));
		assertNotNull("User Data Type was not successfully created.",
				udt);
        DataType_c dt = DataType_c.getOneS_DTOnR17(udt);
        Transaction t = Cl_c.Starttransaction(udt, "Rename test element");
		dt.setName(test_id);
		Cl_c.Endtransaction(udt, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(dtPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

}
