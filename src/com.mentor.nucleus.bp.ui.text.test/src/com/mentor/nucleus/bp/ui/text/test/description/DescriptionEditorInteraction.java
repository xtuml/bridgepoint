
//=====================================================================
//
//File:      $RCSfile: DescriptionEditorInteraction.java,v $
//Version:   $Revision: 1.21 $
//Modified:  $Date: 2013/05/10 06:03:21 $
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

package com.mentor.nucleus.bp.ui.text.test.description;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.texteditor.MarkerUtilities;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.EditorHover;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;
import com.mentor.nucleus.bp.ui.text.test.TextTestPlugin;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class DescriptionEditorInteraction extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public DescriptionEditorInteraction(String projectName, String name) throws CoreException {
		super(null, name);
	}
	
	public DescriptionEditorInteraction(String name) throws CoreException {
		super(null, name);
	}

	private static String m_oldDescrip; 
	private final static String m_updateText = "New Line \n";
	private static long m_markerId[] = new long [4];
	private static int numMarkers = 0;
	
    
	protected void setUp() throws Exception {
    	super.setUp();

        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }

    }
    
	static public void openDescriptionEditor( final Object uut )
	{
	
		try
		{
		  IWorkspaceRunnable r = new IWorkspaceRunnable()
		  {
			public void run(IProgressMonitor monitor) throws CoreException
			{
				IStructuredSelection ss = new StructuredSelection(uut);
				ShowDescriptionAction sda = new ShowDescriptionAction();
				Action a = new Action(){};
				sda.selectionChanged(a, ss);
				sda.run( a );
			}
		  };
		  TextTestPlugin.getWorkspace().run(r, null);
		}
		catch (CoreException x)
		{
		  fail("open editor problem");
		}
	
	}

	private DescriptionEditor openPackageDescriptionEditor()
	{	
		final Package_c uut = getTopPackage();
		assertNotNull(uut);

		openDescriptionEditor(uut);
		return getPackageDescriptionEditor();
	}

	private DescriptionEditor getPackageDescriptionEditor()
	{	
		return TextEditorUtils.getDescriptionEditor("testDescrip1: Package Description");
	}

	private Package_c getTopPackage()
	{	
		// In the test model we're using, there is a top level package with the same name
		// as the project itself.
        ClassQueryInterface_c pkgQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                return (((Package_c)candidate).getName().equals(testModelName));
                
            }
        };
        
		Package_c uut = Package_c.PackageInstance(modelRoot, pkgQuery);
		assertNotNull(uut);

		return uut;
	}

	public static void createMarker(final IFile file, int line, String message, String type) {
		IMarker marker = null;
		Map attributes = new HashMap(11);
		MarkerUtilities.setMessage(attributes, message);
		MarkerUtilities.setLineNumber(attributes, line);
		attributes.put(IMarker.TRANSIENT, new Boolean(true));
		final Map attrs = attributes;
		final String markerType = type;
		try {
			file.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IMarker marker = file.createMarker(markerType);
					marker.setAttributes(attrs);
					DescriptionEditorInteraction.m_markerId[DescriptionEditorInteraction.numMarkers] = marker.getId();
					++ DescriptionEditorInteraction.numMarkers;
				}
			}, null);
		} catch (CoreException x) {
			fail("Core Exception in DescriptionEditorInteraction.createMarker " + x.toString());
		}
	}

	public void testDirtyFlag()
	{	
		DescriptionEditor de = openPackageDescriptionEditor();
		
		// change data in editor
	    IDocument doc = de.getDocumentProvider().getDocument(de.getEditorInput());
	    try {
			doc.replace(0, 0, "test text ");
	    } catch ( BadLocationException e ) { fail("Bad Location Exception"); }
	    
		assertTrue( de.isSaveOnCloseNeeded() );
		assertTrue( de.isDirty() );
	}

	public void testUndo()
	{	
		DescriptionEditor de = getPackageDescriptionEditor();
		
		// undo
		de.getTextViewer().doOperation(ITextOperationTarget.UNDO);
		
		assertTrue( de.isSaveOnCloseNeeded() );
		assertTrue( de.isDirty() );
        
		Package_c uut = getTopPackage();
		assertEquals( uut.getDescrip(), de.getDocumentProvider().getDocument(de.getEditorInput()).get());

	}
	
	public void testRevert()
	{	
		DescriptionEditor de = getPackageDescriptionEditor();
		
		// revert editor
		de.doRevertToSaved();
		
		assertFalse( de.isSaveOnCloseNeeded() );
		assertFalse( de.isDirty() );
		Package_c uut = getTopPackage();
		assertEquals( uut.getDescrip(), de.getDocumentProvider().getDocument(de.getEditorInput()).get());
	}
	
	public void testSave()
	{	
		DescriptionEditor de = getPackageDescriptionEditor();
		Package_c uut = getTopPackage();
		m_oldDescrip = uut.getDescrip();
		
		// change data in editor
		IDocument doc = de.getDocumentProvider().getDocument(de.getEditorInput());
		try {
			doc.replace(0, 0, m_updateText);
		} catch ( BadLocationException e ) { fail("Bad Location Exception"); }
	    
	    de.doSave(new NullProgressMonitor());
	    
		assertFalse( de.isSaveOnCloseNeeded() );
		assertFalse( de.isDirty() );
		assertEquals( m_updateText + m_oldDescrip, de.getDocumentProvider().getDocument(de.getEditorInput()).get());
		de.getSite().getPage().closeEditor(de, false);
	}
	
	public void testOpenChangedDescription()
	{
		DescriptionEditor de = openPackageDescriptionEditor();
		assertFalse( de.isSaveOnCloseNeeded() );
		assertFalse( de.isDirty() );
		assertEquals( m_updateText + m_oldDescrip, de.getDocumentProvider().getDocument(de.getEditorInput()).get());
	}
	
	private final int marker_line = 3;
	
	public void testAddBookmark()
	{
		DescriptionEditor de = getPackageDescriptionEditor();
		DescriptionEditorInput editorInput = (DescriptionEditorInput)de.getEditorInput();
		final IFile file = editorInput.getFile();
		createMarker(file, marker_line, "test bookmark", IMarker.BOOKMARK);		
		assertFalse( de.isSaveOnCloseNeeded() );
		assertFalse( de.isDirty() );
		assertEquals( m_updateText + m_oldDescrip, de.getDocumentProvider().getDocument(editorInput).get());
		
		de.getSite().getPage().closeEditor(de, false);

		IMarker m2 = file.getMarker(m_markerId[0]);
		assertTrue(m2.exists());
		try { 
			assertEquals(marker_line, ((Integer)m2.getAttribute("lineNumber")).intValue());
			assertEquals( "test bookmark", (String)m2.getAttribute("message"));
			assertEquals(IMarker.BOOKMARK, m2.getType());
		}
		  catch (CoreException e) {	fail("Core Exception in DescriptionEditorInteraction.testAddBookmark " + e.toString()); }
		assertTrue(m2.getResource().isAccessible());
	}

	public void testBookmarkHoverText()
	{
		DescriptionEditor de = openPackageDescriptionEditor();

		DescriptionEditorInput editorInput = (DescriptionEditorInput)de.getEditorInput();
		final IFile file = editorInput.getFile();
		IMarker m2 = file.getMarker(m_markerId[0]);

        ((IGotoMarker)de.getAdapter(IGotoMarker.class)).gotoMarker(m2);
		IRegion expected = null;
		try { 
			IDocument doc = de.getDocumentProvider().getDocument(de.getEditorInput());
			expected = doc.getLineInformation(marker_line);
		}
		catch ( BadLocationException e ) {	fail("Bad Location Exception in DescriptionEditorInteraction.testGotoBookmark " + e.toString()); }
		assertNotNull( expected );

		EditorHover eh = new EditorHover();
		eh.setEditor(de);
		String hoverText = eh.getHoverInfo((ISourceViewer)de.getTextViewer(), marker_line-1);
		assertEquals( "test bookmark", hoverText);
	}
	public void testAddTaskMarker()
	{
		DescriptionEditor de = getPackageDescriptionEditor();
		DescriptionEditorInput editorInput = (DescriptionEditorInput)de.getEditorInput();
		final IFile file = editorInput.getFile();
		createMarker(file, marker_line, "test task", IMarker.TASK);		
		assertFalse( de.isSaveOnCloseNeeded() );
		assertFalse( de.isDirty() );
		assertEquals( m_updateText + m_oldDescrip, de.getDocumentProvider().getDocument(editorInput).get());
		
		de.getSite().getPage().closeEditor(de, false);

		IMarker m2 = file.getMarker(m_markerId[1]);
		assertTrue(m2.exists());
		try { 
			assertEquals(IMarker.TASK, m2.getType());
			assertEquals(marker_line, ((Integer)m2.getAttribute("lineNumber")).intValue());
			assertEquals( "test task", (String)m2.getAttribute("message"));
		}
	  catch (CoreException e) {	fail("Core Exception in DescriptionEditorInteraction.testAddBookmark " + e.toString()); }
		assertTrue(m2.getResource().isAccessible());
	}
	public void testMultipleMarkerHoverText()
	{
		DescriptionEditor de = openPackageDescriptionEditor();

		EditorHover eh = new EditorHover();
		eh.setEditor(de);
		String hoverText = eh.getHoverInfo((ISourceViewer)de.getTextViewer(), marker_line-1);
        if (hoverText.equals("Multiple markers at this line\n  - test bookmark\n  - test task")){
            return;
        }else if (hoverText.equals("Multiple markers at this line\n  - test task\n  - test bookmark")){
            return;
        }        
		assertFalse(true);
	}
	public void testDeleteBookmark()
	{
		DescriptionEditor de = getPackageDescriptionEditor();
		DescriptionEditorInput editorInput = (DescriptionEditorInput)de.getEditorInput();
		final IFile file = editorInput.getFile();
		IMarker m2 = file.getMarker(m_markerId[0]);
		
		try {
			m2.delete();
		}
		catch (CoreException e) {	fail("Core Exception in DescriptionEditorInteraction.testDeleteBookmark " + e.toString()); }

		m2 = file.getMarker(m_markerId[0]);
		assertFalse(m2.exists());
	}
	public void testTaskHoverText()
	{
		DescriptionEditor de = getPackageDescriptionEditor();

		EditorHover eh = new EditorHover();
		eh.setEditor(de);
		String hoverText = eh.getHoverInfo((ISourceViewer)de.getTextViewer(), marker_line-1);
		assertEquals( "test task", hoverText);
	}
	public void testDeleteTask()
	{
		DescriptionEditor de = getPackageDescriptionEditor();
		DescriptionEditorInput editorInput = (DescriptionEditorInput)de.getEditorInput();
		final IFile file = editorInput.getFile();
		IMarker m2 = file.getMarker(m_markerId[1]);
		
		try {
			m2.delete();
		}
		catch (CoreException e) {	fail("Core Exception in DescriptionEditorInteraction.testDeleteBookmark " + e.toString()); }

		m2 = file.getMarker(m_markerId[1]);
		assertFalse(m2.exists());
	}

}
