//=====================================================================
//
// NOTE: This file was generated, but is maintained by hand.
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

package org.xtuml.bp.core.test.cpts;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.xtuml.bp.compare.ComparePlugin;
import org.xtuml.bp.compare.ModelCacheManager;
import org.xtuml.bp.compare.ModelCacheManager.ModelLoadException;
import org.xtuml.bp.compare.structuremergeviewer.ModelCompareStructureCreator.CompareDocumentRangeNode;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CoreDataType_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.core.test.cpts.CPSDTElementResolver.ParentChildContainer;
import org.xtuml.bp.core.ui.CopyCutAction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.marker.DelayedMarkerJob;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.CanvasTransactionListener;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

@RunWith(OrderedRunner.class)
public class CopyPasteSourceDestinationTests extends CanvasTest {

    static private boolean initialized = false;
    public static boolean generateResults = false;
    public static boolean useDrawResults = false;

	public String test_id = "";
	private GraphicalEditor fActiveCanvasEditor;
	private NonRootModelElement aElement;
	private String expectedB;
	private static boolean debugTests;
	
	protected String getResultName() {
		return getClass().getSuperclass().getSimpleName() + "_" + test_id;
    }
	
	protected String getResultFileName() {
		if(current_postfix != 0)
			return getResultName() + "-Child_" + current_postfix;
		else
			return getResultName();
	}

    @Override
	public void validateOrGenerateResults(GraphicalEditor editor, boolean generate) {
		graphicsModelRoot = (Ooaofgraphics) editor.getModel().getModelRoot();
		super.validateOrGenerateResults(editor, generate);
	}

	public CopyPasteSourceDestinationTests(String subTypeClassName, String subTypeArg0) {
        super(subTypeArg0);
    }

	public String getTestId(String src, String dest, String count) {
		if(src != null && dest != null) {
			ParentChildContainer srcContainer = CPSDTParentChildResolver
				.getParentChildContainer(src);
			ParentChildContainer destContainer = CPSDTParentChildResolver
				.getParentChildContainer(dest);
			return srcContainer.fChildKeyLetters + "-"
					+ srcContainer.fParentKeyLetters + "--"
					+ destContainer.fChildKeyLetters + "-"
					+ destContainer.fParentKeyLetters + ".drawcmd";
		} else {
			return "test_" + count + ".drawcmd";
		}
	}

	@Before
	public void setUp() throws Exception {
        super.setUp();
        
    	if(!initialized ) {
    		// turn off autobuild to stop MC-3020 builders from running
    		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
    		
    		CorePlugin.disableParseAllOnResourceChange();
	    	// initialize test model
	    	final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					"CopyPasteTestModel");

			File sourceProject = new File(m_workspace_path + "../CopyPasteTestModel");

			CorePlugin.disableParseAllOnResourceChange();
			
			TestingUtilities.copyProjectContents(sourceProject, project);

	    	// initialize test model
	    	IProject projectDest = ResourcesPlugin.getWorkspace().getRoot().getProject(
					"destination_SystemModel_c");
			
			File destinationProject = new File(m_workspace_path + "../destination_SystemModel_c");

			TestingUtilities.copyProjectContents(destinationProject, projectDest);
			
			TestingUtilities.allowJobCompletion();

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
			
				public boolean evaluate(Object candidate) {
					return ((SystemModel_c)candidate).getName().equals(project.getName());
				}
			
			});
	    	PersistableModelComponent sys_comp = m_sys.getPersistableComponent();
	    	sys_comp.loadComponentAndChildren(new NullProgressMonitor());
	    	CorePlugin.enableParseAllOnResourceChange();
	    	TestingUtilities.allowJobCompletion();
	    	while(!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(IProject.DEPTH_INFINITE)) {
	    		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
	    		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	    	}
	    	
	    	// set the generate results flag based on
	    	// the environment variable generate_results
	    	String gen_results_env = System.getenv("generate_results");
	    	if(gen_results_env != null) {
	    		Boolean result = Boolean.valueOf(gen_results_env);
	    		if(result != null) {
	    			generateResults = result;
	    		}
	    	}
	    	
	    	Ooaofooa.setPersistEnabled(true);
	    	
	    	// turn off auto-reconciler
	    	CanvasTransactionListener.disableReconciler();
	    	// turn off problem job
	    	DelayedMarkerJob.disableProblemMarkerJob();
	    	
	    	// close the properties view, outline view and problems view
	    	// they are uninteresting to this test
	    	IViewReference[] refs = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getViewReferences();
	    	for(int i = 0; i < refs.length; i++) {
	    		if (refs[i].getPartName().equals("Outline")) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().hideView(refs[i]);
				}
				if (refs[i].getPartName().equals("Properties")) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().hideView(refs[i]);
				}
				if (refs[i].getPartName().equals("Problems")) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().hideView(refs[i]);					
				}
	    	}
	    	
	    	initialized = true;
    	}
    	
    	// clear active editor from previous test
    	fActiveCanvasEditor = null;
    	
    	// clear the current clipboard contents
    	CorePlugin.getSystemClipboard().clearContents();

    	// this test requires no change listeners
    	Ooaofooa.disableChangeNotification();
    	
    }

    @After
	public void tearDown() throws Exception {
    	// close any editors that were opened
    	// during the test
    	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
    	
    	// clear existing child variable
    	existingChild = null;
    	
    	// clear selection failure variable
    	selectionFailure = "";
    	
    	if(pasteSuccessful) {
    		// if we had a successful paste we undo
    		// it here to clear the destination
    		TransactionManager.getSingleton().getUndoAction().run();
    		pasteSuccessful = false;
    	}
    	
    	// allow all events gerenated by this test
    	// to get processed
    	BaseTest.dispatchEvents(0);
    	
    	// re-enable change listeners, for following
    	// tests
    	Ooaofooa.enableChangeNotification();
    	
        super.tearDown();
    }

    /**
     * "B" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "B" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @return A model element used in the test as specified by the test matrix
     */
    public NonRootModelElement selectB(String element) {
    	expectedB = element;
        return selectElement(element, false);
    }

    
    public NonRootModelElement selectA(String element) {
        NonRootModelElement source = selectElement(element, true);
        if(source == null && !selectionFailure.equals("")) {
        	fail(selectionFailure);
        }
        aElement = source;
        return source;
    }
    
    static HashMap<String, NonRootModelElement> sourceMap = new HashMap<String, NonRootModelElement>();
    
    /**
     * "A" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "A" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @return A model element used in the test as specified by the test matrix
     */
    NonRootModelElement selectElement(String element, boolean source) {
    	if(debugTests && !shouldRunTest()) {
    		return null;
    	}
        NonRootModelElement nrme = null;
        
		String key = getKeyForElement(element, source);
		
		nrme = sourceMap.get(key);
		// if the element is not already found, search all
		// roots
		if(nrme == null) {
			nrme = getElementFromName(m_sys, element, source, false, aElement);
			// We need to develop a better key with the new naming
			// conventions, until then this optimization is commented
			// out
			//
			//sourceMap.put(key, nrme);
		}
        
        return nrme;
    }
    
    private String[] testsToDebug = {"testA23_B8"};
    
    private boolean shouldRunTest() {
    	for(int i = 0; i < testsToDebug.length; i++) {
    		if(testsToDebug[i].equals(getName())) {
    			return true;
    		}
    	}
    	return false;
	}

	private String getKeyForElement(String element, boolean source) {
		ParentChildContainer pcContainer = CPSDTParentChildResolver
			.getParentChildContainer(element);
		return pcContainer.fParentKeyLetters + "_" + CPSDTElementResolver.getElementName(element, source);
	}

	private NonRootModelElement getElementFromName(SystemModel_c system,
			String element, boolean source, boolean falseDestination,
			NonRootModelElement sourceElement) {
    	return CPSDTElementResolver.getElement(element, system, source, falseDestination, sourceElement);
	}

    // this variable stores the existing child with the
    // same name, so that we can get the actual pasted
    // element for result checking
    NonRootModelElement existingChild = null;
	private boolean specializedToGeneric;
	private boolean genericToSpecialized;
	private static boolean pasteSuccessful;
    
	/**
     * This routine performs the action associated with a matrix cell.
     * The parameters represent model instances aquired based on the specifed
     * column instance and row instance.
     * 
     * @param columnInstance Model instance from the column
     * @param rowInstance Model instance from the row
     */
    public void A_B_Action(final NonRootModelElement columnInstance, final NonRootModelElement rowInstance) {
    	if(debugTests && !shouldRunTest()) {
    		return;
    	}
    	// disable parse on paste, otherwise the compare will
    	// show differences from any elements that were not previously
    	// parsed
    	ModelStreamProcessor.setParseDisabled(true);
    	NonRootModelElement editorColumnInstance = getActualElement(columnInstance, true);
    	NonRootModelElement editorRowInstance = getActualElement(rowInstance, false);
    	if(columnInstance == null || rowInstance == null) return;
        GraphicalEditor columnEditor = UITestingUtilities.getGraphicalEditorFor(editorColumnInstance, false);
        if(columnEditor != null) {
        	// process all display events prior to
        	// selection, at some point a selection event
        	// overrides the selection below
        	while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        	Selection.getInstance().clear();
        	Selection.getInstance().addToSelection(editorColumnInstance);
        	boolean copyEnabled = UITestingUtilities
					.checkItemStatusInContextMenu(columnEditor.getCanvas()
							.getMenu(), "Copy", "", false);
        	if(copyEnabled) {
        		UITestingUtilities.copyElement(editorColumnInstance, columnEditor);
        	}
        	Selection.getInstance().clear();
        } else {
        	// the test instance is only displayed in explorer
        	// process all display events prior to
        	// selection, at some point a selection event
        	// overrides the selection below
        	while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        	Selection.getInstance().clear();
        	Selection.getInstance().addToSelection(editorColumnInstance);
        	boolean copyEnabled = UITestingUtilities
					.checkItemStatusInContextMenu(getExplorerView().getTreeViewer().getTree()
							.getMenu(), "Copy", "", false);
        	if(copyEnabled) {
        		UITestingUtilities.copyElementInExplorer(getExplorerView());
        	} else {
        		// copy was not supported, so do not try to paste
        		return;
        	}
        	Selection.getInstance().clear();        	
        }
        if(!(rowInstance instanceof SystemModel_c)) {
        	// NOTE system models are special as they support
        	// both flavors
        	//
	        // if the copy/paste would result in a specialized -> generic package
	        // move, or a generic -> specialized package move then check that paste
	        // is disabled
	        ArrayList<NonRootModelElement> destinations = new ArrayList<NonRootModelElement>();
	        destinations.add(rowInstance);
			boolean isGenericPackagingCopy = false;
			// destination is under generic packages
			Clipboard cb = CorePlugin.getSystemClipboard();
			if (cb != null && !cb.isDisposed()) {
				Object contents = cb.getContents(TextTransfer.getInstance());
				if (contents instanceof String) {
					String stringContents = (String) contents;
					isGenericPackagingCopy = stringContents
							.contains(CopyCutAction.GENERIC_PACKAGE_HEADER);
				}
			}
        }
        GraphicalEditor rowEditor = UITestingUtilities.getGraphicalEditorFor(editorRowInstance, true);
        if(rowEditor != null) {
        	boolean pasteEnabled = UITestingUtilities
				.checkItemStatusInContextMenu(rowEditor.getCanvas()
					.getMenu(), "Paste", "", false);
        	if(specializedToGeneric) {
        		assertFalse("Copy from specialized packages to generic packages was allowed.", pasteEnabled);
        		return;
        	}
        	if(genericToSpecialized) {
        		assertFalse("Copy from generic packages to specialized packages was allowed.", pasteEnabled);
        		return;
        	}
        	if(pasteEnabled) {
            	existingChild = getChildWithName(rowInstance, columnInstance.getName());
        		UITestingUtilities.pasteClipboardContents(UITestingUtilities.getClearPoint(rowEditor), rowEditor);
            	fActiveCanvasEditor = rowEditor;
        	}        	
        } else {
        	// this will be a model explorer paste
			boolean pasteEnabled = UITestingUtilities
					.checkItemStatusInContextMenu(getExplorerView().getTreeViewer().getTree()
							.getMenu(), "Paste", "", false);
			if (pasteEnabled) {
				existingChild = getChildWithName(rowInstance, columnInstance
						.getName());
				UITestingUtilities.pasteClipboardContentsInExplorer(rowInstance);
				fActiveCanvasEditor = rowEditor;
			}
        }
        ModelStreamProcessor.setParseDisabled(false);
    }

	private NonRootModelElement getChildWithName(NonRootModelElement rowInstance, String name) {
		ModelInspector inspector = new ModelInspector();
		IModelClassInspector elementInspector = inspector.getInspector(rowInstance.getClass());
		ObjectElement[] children = elementInspector.getChildRelations(rowInstance);
		for(int i = 0; i < children.length; i++) {
			NonRootModelElement child = (NonRootModelElement) children[i].getValue();
			if(child.getName().equals(name) && !pastedElements.contains(child)) {
				return child;
			}
		}
		return null;
	}
    
	private NonRootModelElement getActualElement(NonRootModelElement element, boolean source) {
    	if (element instanceof DataType_c) {
			UserDataType_c udt = UserDataType_c
					.getOneS_UDTOnR17((DataType_c) element);
			if (udt != null) {
				element = udt;
			} else {
				EnumerationDataType_c edt = EnumerationDataType_c
						.getOneS_EDTOnR17((DataType_c) element);
				if (edt != null) {
					element = edt;
				} else {
					StructuredDataType_c sdt = StructuredDataType_c
							.getOneS_SDTOnR17((DataType_c) element);
					if (sdt != null) {
						element = sdt;
					} else {
						CoreDataType_c cdt = CoreDataType_c
								.getOneS_CDTOnR17((DataType_c) element);
						if (cdt != null) {
							element = cdt;
						}
					}
				}
			}
		}
    	return element;
	}

	/**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    public boolean checkResult_pasteNotAllowed(NonRootModelElement source, NonRootModelElement destination) {
    	if(debugTests && !shouldRunTest()) {
    		return true;
    	}
    	if(!selectionFailure.equals("")) {
    		// we could not find the destination, and this is expected
    		// as we do not want to create more test elements then
    		// necessary
    		destination = getAnyDestinationOfExpectedType();
    		if(destination == null) {;
    			fail(selectionFailure);
    		}
    		selectionFailure = "";
    	}
    	// assert that the source is copiable, but that paste
    	// is not allowed at the given destination
    	boolean result = false;
        GraphicalEditor columnEditor = UITestingUtilities.getGraphicalEditorFor(source, false);
        if(columnEditor != null) {
        	Selection.getInstance().clear();
        	Selection.getInstance().addToSelection(source);
        	result = UITestingUtilities
        		.checkItemStatusInContextMenu(columnEditor.getCanvas()
        				.getMenu(), "Copy", "", false);
        	Selection.getInstance().clear();
        } else {
        	// no graphical editor, try model explorer
        	Selection.getInstance().clear();
        	Selection.getInstance().addToSelection(source);
        	result = UITestingUtilities
        		.checkItemStatusInContextMenu(getExplorerView().getTreeViewer().getTree()
        				.getMenu(), "Copy", "", false);
        	Selection.getInstance().clear();        	
        }
        if(result) {
	        GraphicalEditor rowEditor = UITestingUtilities.getGraphicalEditorFor(destination, true);
	        if(rowEditor != null) {
	        	result = UITestingUtilities
					.checkItemStatusInContextMenu(rowEditor.getCanvas()
						.getMenu(), "Paste", "", false);
	        	assertFalse("Destination was valid, expected invalid destination", result);
	        	result = true;
	        } else {
				result = UITestingUtilities.checkItemStatusInContextMenu(
						getExplorerView().getTreeViewer().getTree().getMenu(), "Paste", "", false);
				assertFalse(
						"Destination was valid, expected invalid destination",
						result);
				result = true;	        	
	        }
        }
        return result;
    }

	private NonRootModelElement getAnyDestinationOfExpectedType() {
        NonRootModelElement nrme = null;
        
		String key = getKeyForElement(expectedB, false);
		
		nrme = sourceMap.get(key);
		// if the element is not already found, search all
		// roots
		if(nrme == null) {
			nrme = getElementFromName(m_sys, expectedB, false, true, aElement);
			// See comment in selectElement for the reason behind
			// the commented out code below
			//
			// sourceMap.put(key, nrme);
		}
        
        return nrme;		
	}

	/**
     * This function verifies an expected result.
     *
     * @param source A model element instance aquired through a action taken
     *               on a column of the matrix.
     * @param destination A model element instance aquired through a action taken
     *                    taken on a row of the matrix.
     * @return true if the test succeeds, false if it fails
     */
     boolean checkResult_destinationNotApplicable(NonRootModelElement source, NonRootModelElement destination) {
     	if(debugTests && !shouldRunTest()) {
    		return true;
    	}
     	// assert that the source is copiable, but that paste
     	// is not allowed at the given destination
     	boolean result = false;
         GraphicalEditor columnEditor = UITestingUtilities.getGraphicalEditorFor(source, false);
         if(columnEditor != null) {
         	Selection.getInstance().clear();
         	Selection.getInstance().addToSelection(source);
         	result = UITestingUtilities
         		.checkItemStatusInContextMenu(columnEditor.getCanvas()
         				.getMenu(), "Copy", "", false);
         	Selection.getInstance().clear();
         } else {
         	// no editor was found for the source
         	// this element has copy support
         	// for export reasons
         	// just return a result of destination
         	// not applicable
         	return true;
         }
         if(result) {
 	        GraphicalEditor rowEditor = UITestingUtilities.getGraphicalEditorFor(destination, true);
 	        if(rowEditor != null) {
 	        	result = UITestingUtilities
 					.checkItemStatusInContextMenu(rowEditor.getCanvas()
 						.getMenu(), "Paste", "", false);
 	        	assertFalse("Destination was valid, expected invalid destination", result);
 	        	result = true;
 	        } else {
 	        	return true;
 	        }
         }
         return result;
     }
     
    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    public boolean checkResult_pasteSuccessful(NonRootModelElement source, NonRootModelElement destination) {
    	if(debugTests && !shouldRunTest()) {
    		return true;
    	}
    	if(!selectionFailure.equals("")) {
    		fail(selectionFailure);
    	}
    	// we need to make sure that any new resources have been created
    	BaseTest.dispatchEvents(0);
    	// if the source element does not come from a package
    	// and the destination is a package then we simply
    	// return as the action method performed the necessary
    	// check
    	if(specializedToGeneric || genericToSpecialized) {
    		specializedToGeneric = false;
    		genericToSpecialized = false;
    		return true;
    	}
    	
    	// We need to verify the following:
    	//
    	// 1. That all child elements that refer
    	//    to non-copied elements are resolved
    	//    appropriately
    	// 2. That the pasted elements name is
    	//    renamed when a duplicate is found
    	
		ModelInspector inspector = new ModelInspector();
		IModelClassInspector destInspector = inspector.getInspector(destination.getClass());
		ObjectElement[] childRelations = destInspector.getChildRelations(destination);
		
		NonRootModelElement pastedElement = getPastedElementFromChildren(childRelations, source);

	    assertNotNull("The source element was not successfully pasted in the destination.", pastedElement);
		
   		if(!compareExcluded(source, destination)) {
   			comparePastedModel(source, pastedElement, destination);
   		}
		current_postfix = 0;
		
    	// If we determine that this element should have a unique
    	// file name, then verify that the pasted element was
    	// correctly renamed
   		if (PersistenceManager.getHierarchyMetaData().isComponentRoot(
   				source)) {
   			// to aid in test setup fail the test if the
   			// destination does not have another element
   			// with a matching name
   			boolean foundMatch = false;
   			for(int i = 0; i < childRelations.length; i++) {
				if (((NonRootModelElement) childRelations[i].getValue())
						.getName().equals(source.getName())
						&& childRelations[i].getValue() != pastedElement) {
					foundMatch = true;
					break;
				}
   			}
			assertTrue(
					"Missing a duplicate element in the destination.  Add the following data to the test model:"
							+ "\n  Element name: "
							+ source.getName()
							+ "\n  Element Container: " + destination.getName(),
					foundMatch);
   	   		// see that the file name has a
   	   		// -1 appended
   			assertTrue("File name was not uniquely named.",
   					!pastedElement.getName().equals(source.getName()));
   		}
   		
   		pasteSuccessful = true;
   		
    	return true;
    }

    /**
     * Some elements can be pasted, but resolution will not work.  For those
     * elements we do not want to do the compare because we know they will fail
     * 
     * @return
     */
	private boolean compareExcluded(NonRootModelElement source, NonRootModelElement destination) {
		if (source instanceof StateMachineEvent_c
				&& destination instanceof InstanceStateMachine_c
				&& source.getPersistableComponent().getRootModelElement() instanceof ClassStateMachine_c) {
			return true;
		}
		if (source instanceof StateMachineState_c
				&& destination instanceof InstanceStateMachine_c
				&& source.getPersistableComponent().getRootModelElement() instanceof ClassStateMachine_c) {
			return true;
		}
		if (source instanceof StateMachineEvent_c
				&& destination instanceof ClassStateMachine_c
				&& source.getPersistableComponent().getRootModelElement() instanceof InstanceStateMachine_c) {
			return true;
		}
		if (source instanceof StateMachineState_c
				&& destination instanceof ClassStateMachine_c
				&& source.getPersistableComponent().getRootModelElement() instanceof InstanceStateMachine_c) {
			return true;
		}
		return false;
	}

	static List<NonRootModelElement> pastedElements = new ArrayList<NonRootModelElement>();
	public static String selectionFailure = "";
    
    private NonRootModelElement getPastedElementFromChildren(ObjectElement[] children, NonRootModelElement source) {
    	for(int i = 0; i < children.length; i++) {
			NonRootModelElement newSource = (NonRootModelElement) children[i].getValue();
			if(source instanceof StateMachineEvent_c && newSource instanceof StateMachineEvent_c) {
				StateMachineEvent_c sourceEvt = (StateMachineEvent_c) source;
				StateMachineEvent_c newSourceEvt = (StateMachineEvent_c) newSource;
				if(sourceEvt.getMning().startsWith(newSourceEvt.getMning()) && newSource != existingChild) {
					if(!pastedElements.contains(newSource)) {
						pastedElements.add(newSource);
						return newSource;
					}
				}
			} else {
				if(newSource.getName().startsWith(source.getName()) && newSource != existingChild) {
					if(!pastedElements.contains(newSource)) {
						pastedElements.add(newSource);
						// this is the pasted element
						return newSource;
					}
				}
			}
		}
		return null;
	}

	int current_postfix = 0;

	private void comparePastedModel(NonRootModelElement source, NonRootModelElement pasted, NonRootModelElement destination) {
		compareModels((IFolder) source.getFile().getParent(), (IFolder) pasted
				.getFile().getParent(), source, pasted, destination);
	}

	private void compareModels(IFolder originalFolder, IFolder copiedFolder, NonRootModelElement source, NonRootModelElement pasted, NonRootModelElement destination) {
		IResource[] resources = null;
		try {
			resources = originalFolder.members();
		} catch (CoreException e1) {
			fail(e1.toString());
		}
		for(int i = 0; i < resources.length; i++) {
			if(copiedFolder == null) {
				fail("Unable to match source and destination, missing folder: " + originalFolder.getName());
				return;
			}
			// skip OAL files
			if(resources[i] instanceof IFile) {
				if(resources[i].getFileExtension().equals("oal")) {
					continue;
				}
			}
			IResource copiedResource = copiedFolder.findMember(resources[i].getName());
			if(copiedResource == null) {
				// if the element is not a file root, then we
				// need to rename the source container to match
				// so that comparison can occur
		   		if (!PersistenceManager.getHierarchyMetaData().isComponentRoot(
		   				source)) {
					ComponentResourceListener.setIgnoreResourceChanges(true);
					try {
						InputStream contents = ((IFile) resources[i]).getContents();
						byte[] fileBytes = new byte[contents.available()];
						contents.read(fileBytes);
						String fileContents = new String(fileBytes);
						fileContents = fileContents.replaceAll("container_", "destination_");
												String sourceName = source.getName();
						String remainder = sourceName.replaceFirst("source_.*_c", "");
						sourceName = sourceName.replaceAll(remainder, "");
						fileContents = fileContents.replaceAll(sourceName
								+ "_dup", sourceName);
						contents.close();
						TransactionManager.getSingleton().setIgnoreResourceChange(true);
						((IFile) resources[i]).setContents(new ByteArrayInputStream(fileContents
								.getBytes()), true, false,
								new NullProgressMonitor());
						copiedResource = copiedFolder.findMember(resources[i]
								.getName().replaceAll("container_",
										"destination_"));
					} catch (FileNotFoundException e) {
					} catch (IOException e) {
					} catch (CoreException e) {
					}
					ComponentResourceListener.setIgnoreResourceChanges(false);
		   		} else {
		   			if(!source.getName().equals(pasted.getName())) {
						// append a -1 to the name
						copiedResource = copiedFolder.findMember(pasted.getName() + "."
								+ Ooaofooa.MODELS_EXT);
						if(copiedResource != null && copiedResource instanceof IFile) {
							IFile file = (IFile) copiedResource;
							ComponentResourceListener.setIgnoreResourceChanges(true);
							// Match the source and copy name, so the compare will
							// pass
							try {
								InputStream contents = file.getContents();
								byte[] fileBytes = new byte[contents.available()];
								contents.read(fileBytes);
								String fileContents = new String(fileBytes);
								fileContents = fileContents.replaceAll(pasted.getName(), source.getName());
								contents.close();
								TransactionManager.getSingleton().setIgnoreResourceChange(true);
								file.setContents(new ByteArrayInputStream(fileContents
										.getBytes()), true, false,
										new NullProgressMonitor());
							} catch (FileNotFoundException e) {
							} catch (IOException e) {
							} catch (CoreException e) {
							}
							ComponentResourceListener.setIgnoreResourceChanges(false);
						}
		   			}
		   		}
			}
			if(resources[i] instanceof IFile && resources[i].getName().indexOf(Ooaofooa.MODELS_EXT) != -1) {
				if (copiedResource == null) {
					fail("Unable to locate matching file in destination.  File name from source: "
							+ resources[i].getName());
					return;
				}
				DiffNode obj;
				ModelCacheManager modelCacheManager = ComparePlugin.getDefault().getModelCacheManager();
				
				ResourceNode fileNodeOne = new ResourceNode(resources[i]);
				ResourceNode fileNodeTwo = new ResourceNode(copiedResource);
				
				CompareDocumentRangeNode rootOne = null, rootTwo = null;
				try {
					rootOne = (CompareDocumentRangeNode) modelCacheManager.getModel(fileNodeOne, this);
					rootTwo= (CompareDocumentRangeNode) modelCacheManager.getModel(fileNodeTwo, this);
				}catch (ModelLoadException e) {
					fail(e.toString());
				}
		
				Differencer diff = new Differencer();
				obj = (DiffNode)
					diff.findDifferences(
						false,
						new NullProgressMonitor(),
						null,
						null,
						rootOne,
						rootTwo);
				
				modelCacheManager.releaseModel(fileNodeOne, this);
				modelCacheManager.releaseModel(fileNodeTwo, this);
			
				if(obj != null) {
				
					CompareDocumentRangeNode left = (CompareDocumentRangeNode) obj.getLeft();
					CompareDocumentRangeNode right = (CompareDocumentRangeNode) obj.getRight();
					
					assertEquals("Copied model element: "
							+ resources[i].getName().replaceAll("." + Ooaofooa.MODELS_EXT, "")
							+ "had differences.", left.getDocument().get(), right
							.getDocument().get());
				}
			} else {
				compareModels((IFolder)resources[i], (IFolder)copiedResource, source, pasted, destination);
			}
		}
	}
	
	protected GraphicalEditor getActiveEditor() {
		return fActiveCanvasEditor;
	}

}
