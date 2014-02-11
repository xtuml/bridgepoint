package com.mentor.nucleus.bp.ui.text;
//====================================================================
//
// File:      $RCSfile: EditorHover.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;


public class EditorHover implements ITextHover, IAnnotationHover {
	IEditorPart m_editor;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset, 1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.IAnnotationHover#getHoverInfo(ISourceViewer sourceViewer, int lineNumber)
	 */
	public String getHoverInfo(ISourceViewer textViewer, int lineNumber) {
		
		if ( lineNumber < 0 ) return null;
		  
		if (m_editor == null) return null;
		
		if (m_editor instanceof TextEditor) {
			TextEditor ae = (TextEditor) m_editor;
			IDocumentProvider provider = ae.getDocumentProvider();
			IRegion r = null;
			try
			{
				r = provider.getDocument(m_editor.getEditorInput()).getLineInformation(lineNumber);
			}
			catch ( BadLocationException e ){
				return null;
			}
			return getHoverInfo(textViewer, r);
		}
		return null;
	}
	
	private String add_message_to_hover_text(
		String all_msg,	String msg, int num_msg) {
		if (num_msg == 0)
			all_msg = msg;
		else if (num_msg == 1)
			all_msg = "Multiple markers at this line\n  - " + all_msg + "\n  - " + msg;
		else if (num_msg > 1)
			all_msg = all_msg + "\n  - " + msg;
		return all_msg;
	}
	
	/*
	 * @see ITextHover#getHoverInfo(ITextViewer, IRegion)
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (m_editor == null)
			return null;
		if (m_editor instanceof TextEditor) {
			TextEditor ae = (TextEditor) m_editor;
			IDocumentProvider provider = ae.getDocumentProvider();
			IAnnotationModel model =
				provider.getAnnotationModel(m_editor.getEditorInput());
			if (model != null) {
				String all_msg = null;
				int num_msg = 0;
				Iterator e = new EditorAnnotationIterator(model, true);
				while (e.hasNext()) {
					Annotation a = (Annotation) e.next();
					Position p = model.getPosition(a);
					if (p.overlapsWith(
							hoverRegion.getOffset(), hoverRegion.getLength())) {
					    if ( a instanceof IActivityProblemAnnotation )
					    {
							String msg = ((IActivityProblemAnnotation) a).getMessage();
							if (msg != null && msg.trim().length() > 0){
								all_msg = add_message_to_hover_text( all_msg, msg, num_msg );
								++num_msg;
							}
						}
						else if ( a instanceof MarkerAnnotation )
						{
							try {
								MarkerAnnotation ma = (MarkerAnnotation)a;
								IMarker m = ma.getMarker();
								Object attr = m.getAttribute("message");
								String msg = attr.toString();
								if (msg != null && msg.trim().length() > 0)
								{
									all_msg = add_message_to_hover_text( all_msg, msg, num_msg );
									++num_msg;
								}
							}
							catch(CoreException ce)
							{
								return null;
							}
						}
					}
				}
				return all_msg;
			}
		}
		return null;
	}

	public void setEditor(IEditorPart editor) {
		if (editor instanceof ActivityEditor)
			m_editor = editor;
		else if (editor instanceof DescriptionEditor)
			m_editor = editor;
		else
			m_editor = null;
	}
}
