//====================================================================
//
// File:      $RCSfile: EditorConfiguration.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;

import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALEditorConfiguration;

public class EditorConfiguration extends OALEditorConfiguration {

	private EditorHover m_hover;

	public EditorConfiguration(IEditorPart ep, SyntaxHighlightingPreferences prefs) {
		super(prefs);
		m_hover = new EditorHover();
		m_hover.setEditor(ep);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAnnotationHover(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return m_hover;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getTextHover(org.eclipse.jface.text.source.ISourceViewer, java.lang.String)
	 */
	public ITextHover getTextHover(
		ISourceViewer sourceViewer,
		String contentType) {
		return m_hover;
	}

}
