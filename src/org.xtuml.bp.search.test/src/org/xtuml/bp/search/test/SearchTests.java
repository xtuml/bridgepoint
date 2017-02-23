package org.xtuml.bp.search.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SearchResult_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.SearchUtilities;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class SearchTests extends BaseTest {
	private static SystemModel_c otherSystem;
	private static ModelClass_c searchClass;
	private static Operation_c searchOperation;
	
	public static boolean firstTime = true;
	
	public SearchTests() throws Exception {
		super(null, null);
	}

	private static String projectName = "TestSearch";

	@Override
	@Before
	public void initialSetup() throws CoreException {

		if (!firstTime)
			return;
		loadProject(projectName);
		m_sys = getSystemModel(projectName);
		m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals(
						project.getName());
			}
		});

		CorePlugin.enableParseAllOnResourceChange();

		TestingUtilities.allowJobCompletion();

		IProject otherProject = TestingUtilities.createProject("TestSearchNoResults");
		otherSystem = TestingUtilities.getSystemModel(otherProject.getName());
		otherSystem.Newpackage();
		Package_c otherPack = Package_c.getOneEP_PKGOnR1401(otherSystem);
		otherPack.setName("NoResultPackage");
		
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		searchClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
		searchOperation = Operation_c.getOneO_TFROnR115(searchClass);
	}
	
	@Test
	public void testCaseSensitivity() {
		SearchUtilities.configureAndRunSearch("find me", false, false, true,
				true, ISearchPageContainer.WORKSPACE_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		SearchUtilities.configureAndRunSearch("find me", false, true, true,
				true, ISearchPageContainer.WORKSPACE_SCOPE, "");	
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("find me", false,
						true, true, true, ISearchPageContainer.WORKSPACE_SCOPE,
						""));
	}
	
	@Test
	public void testRegularExpression() {
		SearchUtilities.configureAndRunSearch("Fi.*e", true, false, true,
				true, ISearchPageContainer.WORKSPACE_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		SearchUtilities.configureAndRunSearch("fi.*e", true, true, true,
				true, ISearchPageContainer.WORKSPACE_SCOPE, "");	
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("fi.*e", true,
						true, true, true, ISearchPageContainer.WORKSPACE_SCOPE,
						""));
	}
	
	@Test
	public void testActionLanguageOnlyUnderComponent() {
		SearchUtilities.configureAndRunSearch("Searched Text", false, false, true,
				false, ISearchPageContainer.WORKSPACE_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchedText"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("Searched Text"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Searched Text", false,
						false, true, false, ISearchPageContainer.WORKSPACE_SCOPE,
						""));		
	}
	
	@Test
	public void testActionLanguageOnly() {
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				false, ISearchPageContainer.WORKSPACE_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Find me", false,
						false, true, false, ISearchPageContainer.WORKSPACE_SCOPE,
						""));		
	}
	
	@Test
	public void testDescriptionsOnly() {
		SearchUtilities.configureAndRunSearch("Find me", false, false, false,
				true, ISearchPageContainer.WORKSPACE_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Find me", false,
						false, false, true, ISearchPageContainer.WORKSPACE_SCOPE,
						""));		 
	}
	@Test
	public void testDescriptionsOnlyUnderComponent() {
		SearchUtilities.configureAndRunSearch("Searched Text", false, false, false,
				true, ISearchPageContainer.WORKSPACE_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("Searched Text"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchedText"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Searched Text", false,
						false, false, true, ISearchPageContainer.WORKSPACE_SCOPE,
				""));		
	}

	@Test
	public void testSelectedResourcesScope() {
		// add the two test elements to the selection
		ExplorerUtil.expandAll();
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { searchClass,
						searchOperation }));
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				true, ISearchPageContainer.SELECTION_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { otherSystem }));
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				true, ISearchPageContainer.SELECTION_SCOPE, "");	
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { otherSystem }));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Find me", false,
						false, true, true, ISearchPageContainer.SELECTION_SCOPE,
						""));
	}
	@Test
	public void testEnclosingProjectScope() {
		// add the two test elements to the selection
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { searchClass,
						searchOperation }));
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				true, ISearchPageContainer.SELECTED_PROJECTS_SCOPE, "");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { otherSystem }));
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				true, ISearchPageContainer.SELECTED_PROJECTS_SCOPE, "");	
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { otherSystem }));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Find me", false,
						false, true, true, ISearchPageContainer.SELECTED_PROJECTS_SCOPE,
						""));
	}
	
	@Test
	public void testWorkingSetScope() {
		IWorkingSet workingSet = PlatformUI.getWorkbench()
				.getWorkingSetManager().createWorkingSet("searchWorkingSet",
						new IAdaptable[] { searchClass.getFile(), searchOperation.getFile() });
		PlatformUI.getWorkbench().getWorkingSetManager().addWorkingSet(
				workingSet);
		IWorkingSet otherWorkingSet = PlatformUI.getWorkbench()
				.getWorkingSetManager().createWorkingSet("otherWorkingSet",
						new IAdaptable[] { otherSystem.getFile() });
		PlatformUI.getWorkbench().getWorkingSetManager().addWorkingSet(
				otherWorkingSet);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				true, ISearchPageContainer.WORKING_SET_SCOPE, "searchWorkingSet");
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNotNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		SearchUtilities.configureAndRunSearch("Find me", false, false, true,
				true, ISearchPageContainer.WORKING_SET_SCOPE, "otherWorkingSet");	
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchClass"));
		assertNull("Search did not produce expected results.", SearchUtilities.findResultInView("SearchOperation"));
		assertTrue("Values were not persisted in Search dialog.",
				SearchUtilities.checkSearchDialogSettings("Find me", false,
						false, true, true, ISearchPageContainer.WORKING_SET_SCOPE,
						"otherWorkingSet"));
	}
	
	@Test
	public void testSelectedResourcesMultipleTimes() throws CoreException {
		// import the MicrowaveOven
		loadProject("MicrowaveOven");
		// select the MicrowaveOven domain
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals("MicrowaveOven");
			}
		}));
		assertNotNull(pkg);
		// add the two test elements to the selection
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { pkg }));
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		SearchUtilities.configureAndRunSearch("tube", false, false, true,
				true, ISearchPageContainer.SELECTION_SCOPE, "");
		assertEquals("Search did not return expected result count.", 18,
				SearchResult_c.SearchResultInstances(Ooaofooa
						.getDefaultInstance(), null, false).length);
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { pkg }));
		SearchUtilities.configureAndRunSearch("watt", false, false, true,
				true, ISearchPageContainer.SELECTION_SCOPE, "");
		assertEquals("Search did not return expected result count.", 5,
				SearchResult_c.SearchResultInstances(Ooaofooa
						.getDefaultInstance(), null, false).length);
		ExplorerUtil.getView().setFocus();
		ExplorerUtil.getView().getSite().getSelectionProvider().setSelection(
				new StructuredSelection(new Object[] { pkg }));
		SearchUtilities.configureAndRunSearch("tube", false, false, true,
				true, ISearchPageContainer.SELECTION_SCOPE, "");
		assertEquals("Search did not return expected result count.", 18, 
				SearchResult_c.SearchResultInstances(Ooaofooa
						.getDefaultInstance(), null, false).length);
	}
}
