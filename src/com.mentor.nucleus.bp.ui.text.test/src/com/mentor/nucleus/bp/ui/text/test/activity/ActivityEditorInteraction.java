
//=====================================================================
//
//File:      $RCSfile: ActivityEditorInteraction.java,v $
//Version:   $Revision: 1.30 $
//Modified:  $Date: 2013/05/10 06:02:36 $
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

package com.mentor.nucleus.bp.ui.text.test.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerUtilities;

import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.EditorAnnotationIterator;
import com.mentor.nucleus.bp.ui.text.EditorHover;
import com.mentor.nucleus.bp.ui.text.activity.ActivityAnnotationModel;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityAnnotationType;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityMarkerAnnotation;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityProblemAnnotation;
import com.mentor.nucleus.bp.ui.text.test.TextTestPlugin;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class ActivityEditorInteraction extends UITextTest {
  
	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public ActivityEditorInteraction(String projectName, String name) throws CoreException {
		super(null, name);
	}
	
	public ActivityEditorInteraction(String name) throws CoreException {
		super(null, name); //$NON-NLS-1$
	}

	private static String m_oldActionSemantics;
	private final static String m_updateText = "// test text \n";
	private static long m_markerId[] = new long[4];
	private static int m_numMarkers = 0;

    protected void setUp() throws Exception {
    	super.setUp();
    	if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }	

    protected void tearDown() throws Exception {
    	
    	// parse all activities after performing test
    	// to verify it doesn't affect editor/problem list state
		AllActivityModifier aam = new AllActivityModifier(Package_c.PackageInstance(modelRoot), 
				new NullProgressMonitor());
		aam.processAllActivities(AllActivityModifier.PARSE);
    	
    	super.tearDown();
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
			TextTestPlugin.getWorkspace().run(r, null);
		} catch (CoreException x) {
			fail("open editor problem");
		}

	}


	static public IViewPart getProblemView() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().
            getActivePage().findView(IPageLayout.ID_PROBLEM_VIEW);
	}

	private ActivityEditor openFunctionActivityEditor() {
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertNotNull(uut);

		openActivityEditor(uut);
		return getFunctionActivityEditor();
	}

	private ActivityEditor getFunctionActivityEditor() {
		return TextEditorUtils.getActivityEditor("test_function: Function Activity");
	}
	private static void createMarker(
		final IFile file,
		int line,
		String message,
		String type) {
		IMarker marker = null;
		Map attributes = new HashMap(11);
		MarkerUtilities.setMessage(attributes, message);
		MarkerUtilities.setLineNumber(attributes, line);
		attributes.put(IMarker.TRANSIENT, new Boolean(true));
		final Map attrs = attributes;
		final String markerType = type;
		try {
			file.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor)
					throws CoreException {
					IMarker marker = file.createMarker(markerType);
					marker.setAttributes(attrs);
					m_markerId[m_numMarkers] = marker.getId();
					++m_numMarkers;
				}
			}, null);
		} catch (CoreException x) {
			fail(
				"Core Exception in DescriptionEditorInteraction.createMarker "
					+ x.toString());
		}
	}
	
	public void testDirtyFlag() {
		ActivityEditor ae = openFunctionActivityEditor();

		// change data in editor
		IDocument doc =
			ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try {
			doc.replace(0, 0, "// test text \n");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());
	}

	public void testUndo() {
		ActivityEditor ae = getFunctionActivityEditor();

		// undo
		ae.getTextViewer().doOperation(ITextOperationTarget.UNDO);

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertEquals(
			uut.getAction_semantics(),
			ae.getDocumentProvider().getDocument(ae.getEditorInput()).get());
	}

	public void testRevert() {
		ActivityEditor ae = getFunctionActivityEditor();

		// undo
		ae.doRevertToSaved();

		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertEquals(
			uut.getAction_semantics(),
			ae.getDocumentProvider().getDocument(ae.getEditorInput()).get());
	}

	public void testSave() {
		ActivityEditor ae = getFunctionActivityEditor();
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		m_oldActionSemantics = uut.getAction_semantics();

		// change data in editor
		IDocument doc =
			ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try {
			doc.replace(0, 0, m_updateText);
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		ae.doSave(new NullProgressMonitor());

		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		assertEquals(
			m_updateText + m_oldActionSemantics,
			ae.getDocumentProvider().getDocument(ae.getEditorInput()).get());
		ae.getSite().getPage().closeEditor(ae, false);
	}

	public void testOpenChangedDescription() {
		ActivityEditor ae = openFunctionActivityEditor();
		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		assertEquals(
			m_updateText + m_oldActionSemantics,
			ae.getDocumentProvider().getDocument(ae.getEditorInput()).get());
	}

	private final int marker_line = 1;

	public void testAddBookmark() {
		ActivityEditor ae = getFunctionActivityEditor();
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		final IFile file = editorInput.getFile();
		createMarker(
			file,
			marker_line,
			"test activity bookmark",
			IMarker.BOOKMARK);
		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		assertEquals(
			m_updateText + m_oldActionSemantics,
			ae.getDocumentProvider().getDocument(editorInput).get());

		ae.getSite().getPage().closeEditor(ae, false);

		IMarker m2 = file.getMarker(m_markerId[0]);
		assertTrue(m2.exists());
		try {
			assertEquals(
				marker_line,
				((Integer) m2.getAttribute("lineNumber")).intValue());
			assertEquals(
				"test activity bookmark",
				(String) m2.getAttribute("message"));
			assertEquals(IMarker.BOOKMARK, m2.getType());
		} catch (CoreException e) {
			fail(
				"Core Exception in ActivityEditorInteraction.testAddBookmark "
					+ e.toString());
		}
		assertTrue(m2.getResource().isAccessible());
	}

	public void testBookmarkHoverText() {
		ActivityEditor ae = openFunctionActivityEditor();

		EditorHover eh = new EditorHover();
		eh.setEditor(ae);
		String hoverText =
			eh.getHoverInfo(
				(ISourceViewer) ae.getTextViewer(),
				marker_line - 1);
		assertEquals("test activity bookmark", hoverText);
	}
	public void testAddTaskMarker() {
		ActivityEditor ae = openFunctionActivityEditor();
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		final IFile file = editorInput.getFile();
		createMarker(file, marker_line, "test activity task", IMarker.TASK);
		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		assertEquals(
			m_updateText + m_oldActionSemantics,
			ae.getDocumentProvider().getDocument(editorInput).get());

		ae.getSite().getPage().closeEditor(ae, false);

		IMarker m2 = file.getMarker(m_markerId[1]);
		assertTrue(m2.exists());
		try {
			assertEquals(IMarker.TASK, m2.getType());
			assertEquals(
				marker_line,
				((Integer) m2.getAttribute("lineNumber")).intValue());
			assertEquals(
				"test activity task",
				(String) m2.getAttribute("message"));
		} catch (CoreException e) {
			fail(
				"Core Exception in ActivityEditorInteraction.testAddBookmark "
					+ e.toString());
		}
		assertTrue(m2.getResource().isAccessible());
	}
	public void testMultipleMarkerHoverText() {
		ActivityEditor ae = openFunctionActivityEditor();

		EditorHover eh = new EditorHover();
		eh.setEditor(ae);
		String hoverText =
			eh.getHoverInfo(
				(ISourceViewer) ae.getTextViewer(),
				marker_line - 1);
        if (hoverText.equals("Multiple markers at this line\n  - test activity bookmark\n  - test activity task")){
            return;
        }else if (hoverText.equals("Multiple markers at this line\n  - test activity task\n  - test activity bookmark")){
            return;
	}
        assertFalse(true);
	}
	public void testDeleteBookmark() {
		ActivityEditor ae = getFunctionActivityEditor();
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		final IFile file = editorInput.getFile();
		IMarker m2 = file.getMarker(m_markerId[0]);

		try {
			m2.delete();
		} catch (CoreException e) {
			fail(
				"Core Exception in ActivityEditorInteraction.testDeleteBookmark "
					+ e.toString());
		}

		m2 = file.getMarker(m_markerId[0]);
		assertFalse(m2.exists());
	}
	public void testTaskHoverText() {
		ActivityEditor ae = getFunctionActivityEditor();

		EditorHover eh = new EditorHover();
		eh.setEditor(ae);
		String hoverText =
			eh.getHoverInfo(
				(ISourceViewer) ae.getTextViewer(),
				marker_line - 1);
		assertEquals("test activity task", hoverText);
	}
	public void testDeleteTask() {
		ActivityEditor ae = getFunctionActivityEditor();
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		final IFile file = editorInput.getFile();
		IMarker m2 = file.getMarker(m_markerId[1]);

		try {
			m2.delete();
		} catch (CoreException e) {
			fail(
				"Core Exception in ActivityEditorInteraction.testDeleteBookmark "
					+ e.toString());
		}

		m2 = file.getMarker(m_markerId[1]);
		assertFalse(m2.exists());
	}
	public void testSyntaxError() {
		ActivityEditor ae = getFunctionActivityEditor();
		assertNotNull(ae);

		// change data in editor
		IDocument doc =
			ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try {
			doc.replace(0, 0, "bad;\n");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		// wait for parsing to complete
		ae.waitForParseThread();

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		IAnnotationModel annotationModel =
			ae.getDocumentProvider().getAnnotationModel(editorInput);
		assertTrue(annotationModel instanceof ActivityAnnotationModel);
		ActivityAnnotationModel model = (ActivityAnnotationModel) annotationModel;
		Iterator e = new EditorAnnotationIterator(model, true);
		int num_problems = 0;
		while (e.hasNext()) {
			Annotation a = (Annotation) e.next();
			if (a instanceof ActivityProblemAnnotation) {
				if (num_problems == 0) {
					ActivityProblemAnnotation apa =
						(ActivityProblemAnnotation) a;
					assertEquals(ActivityAnnotationType.ERROR, apa.getAnnotationType());
					assertEquals(1, apa.fProblem.getSourceLineNumber());
					assertEquals("unexpected token: bad", apa.getMessage());
					++num_problems;
				} else {
					fail("More than one ActivityProblem");
				}
			} else {
				fail("Unknown Annotation");
			}
		}
	}
	public void testActivityProblemErrorHoverText() {
		ActivityEditor ae = getFunctionActivityEditor();
		assertNotNull(ae);

		Display d = Display.getCurrent();
		while(d.readAndDispatch()){}

		EditorHover eh = new EditorHover();
		eh.setEditor(ae);
		String hoverText = eh.getHoverInfo((ISourceViewer) ae.getTextViewer(), new Region(1, 1));
		assertEquals("unexpected token: bad", hoverText);
	}

	public void testSaveWithError() {
		ActivityEditor ae = getFunctionActivityEditor();
		ae.doSave(new NullProgressMonitor());

		validateUnchangedWithError(ae);
		ae.getSite().getPage().closeEditor(ae, false);
	}

	public void testGotoError() {
		ActivityEditor ae = openFunctionActivityEditor();
		assertNotNull(ae);
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		final IFile file = editorInput.getFile();
		ae.getSite().getPage().closeEditor(ae, false);
		ae = null;
		
		IMarker m2 = file.getMarker(m_markerId[2]);
		assertTrue("error marker does not exist!", m2.exists());
		IViewPart task_list = getProblemView();
		IWorkbenchPage page = task_list.getSite().getPage();
		try {
			IDE.openEditor(page, m2,true);
		} catch (PartInitException e) {
			fail(e.getMessage());
		}
		ae = getFunctionActivityEditor();
		assertEquals("bad",ae.getTextViewer().getTextWidget().getSelectionText());
	}
	public void testMarkerErrorHoverText() {
		ActivityEditor ae = getFunctionActivityEditor();
		assertNotNull(ae);
		
		EditorHover eh = new EditorHover();
		eh.setEditor(ae);
		String hoverText = eh.getHoverInfo((ISourceViewer) ae.getTextViewer(), new Region(1, 1));
		assertEquals("unexpected token: bad", hoverText);
	}

	public void testRemoveError() {
		ActivityEditor ae = getFunctionActivityEditor();
		assertNotNull(ae);

		// change data in editor
		IDocument doc =
			ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try {
			doc.replace(0, 5, "");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		// wait for parsing to complete
		ae.waitForParseThread();

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());

		validateErrorFree(ae, true);

	}
	public void testRevertAfterErrorRemoved() {
		ActivityEditor ae = getFunctionActivityEditor();
		assertNotNull(ae);
		ae.doRevertToSaved();
		
		// wait for parsing to complete
		ae.waitForParseThread();

		validateUnchangedWithError(ae);
	}
	public void testSaveAfterErrorRemoved() {
		ActivityEditor ae = getFunctionActivityEditor();
		assertNotNull(ae);
		// change data in editor
		IDocument doc =
			ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try {
			doc.replace(0, 5, "");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		ae.doSave(new NullProgressMonitor());

		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertEquals(m_updateText + m_oldActionSemantics,uut.getAction_semantics());

		validateErrorFree(ae, false);
	}
	public void testEditorOpenedWhenFileLocatedOutsideOfWS() {
		IProject project = null;
		String tmp_location = System.getenv("tmp");
		IPath path = new Path(tmp_location);
		try {
			project = TestingUtilities.createProject("ProjectOutsideOfWS", path.toString());
		} catch (CoreException e) {
			fail("Unable to create project.");
		};
		try {
			if(project != null) {
				TestingUtilities.getSystemModel(project.getName());
				IFile modelFile = TestUtil
					.copyTestDomainIntoProject("default",
												"com.mentor.nucleus.bp.core",
												project);
				Ooaofooa modelRoot = Ooaofooa.getInstance(modelFile, true);
				Bridge_c bridge = Bridge_c.BridgeInstance(modelRoot);
				openActivityEditor(bridge);
				IEditorPart editor = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				assertTrue("Activity editor was not opened.", editor.getTitle()
						.equals(bridge.getName() + ": Bridge Activity"));
			}
		} finally {
			try {
				project.delete(true, true, new NullProgressMonitor());
			} catch (CoreException e) {
				fail("Unable to delete project: " + project.getFullPath());
			}
		}
	}
	private void validateUnchangedWithError(ActivityEditor ae)
	{
		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertEquals(
			"bad;\n" + m_updateText + m_oldActionSemantics,
			uut.getAction_semantics());
		
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		IAnnotationModel annotationModel =
			ae.getDocumentProvider().getAnnotationModel(editorInput);
		assertTrue(annotationModel instanceof ActivityAnnotationModel);
		ActivityAnnotationModel model =
			(ActivityAnnotationModel) annotationModel;
		Iterator e = new EditorAnnotationIterator(model, true);
		int num_problems = 0;
		while (e.hasNext()) {
			Annotation a = (Annotation) e.next();
			if (a instanceof ActivityProblemAnnotation) {
				fail("ActivityProblemAnnotation present after save");
			} else if (a instanceof ActivityMarkerAnnotation) {
				if (num_problems == 0) {
					try {
						ActivityMarkerAnnotation ma = (ActivityMarkerAnnotation) a;
						assertTrue( ma.isRelevant() );
						IMarker m = ma.getMarker();
						assertEquals(IMarker.PROBLEM, m.getType());
						assertEquals(1,((Integer) m.getAttribute("lineNumber")).intValue());
						assertEquals("unexpected token: bad", (String) m.getAttribute("message"));
						m_markerId[m_numMarkers] = m.getId();
						++m_numMarkers;
						++num_problems;
					} catch (CoreException ce) {
						fail(
							"Core Exception in ActivityEditorInteraction.testSaveWithError "
								+ ce.toString());
					}
				} else {
					fail("More that one MarkerAnnotation");
				}
			} else {
				fail("Unknown Annotation");
			}
		}
	}
	private void validateErrorFree(ActivityEditor ae, boolean markerPresent)
	{
		ActivityEditorInput editorInput =
			(ActivityEditorInput) ae.getEditorInput();
		IAnnotationModel annotationModel =
			ae.getDocumentProvider().getAnnotationModel(editorInput);
		assertTrue(annotationModel instanceof ActivityAnnotationModel);
		ActivityAnnotationModel model =
			(ActivityAnnotationModel) annotationModel;
		Iterator e = new EditorAnnotationIterator(model, true);
		int num_problems = 0;
		while (e.hasNext()) {
			Annotation a = (Annotation) e.next();
			if (a instanceof ActivityProblemAnnotation) {
				fail("ActivityProblemAnnotation present");
			} else if (a instanceof ActivityMarkerAnnotation) {
				fail("ActivityMarkerAnnotation present");
            } else {
				fail("Unknown Annotation");
			}
		}
		final IFile file = editorInput.getFile();
		IMarker m2 = file.getMarker(m_markerId[m_numMarkers-1]);
        if (markerPresent)
            assertTrue("error marker existence is incorrect", m2.exists() == true);
        else
            assertTrue("error marker existence is incorrect", m2 == null || m2.exists() == false);
	}
}
