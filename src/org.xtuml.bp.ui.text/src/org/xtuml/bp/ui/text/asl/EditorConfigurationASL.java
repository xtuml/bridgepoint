package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;
import org.xtuml.bp.ui.text.editor.BPTextDefaultTextDoubleClickStategy;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;

public class EditorConfigurationASL extends ASLEditorConfiguration {

// TODO	private EditorHover m_hover;

	public EditorConfigurationASL(IEditorPart ep, SyntaxHighlightingPreferences prefs, ASLEditor editor) {
		super(prefs, editor);
//		m_hover = new EditorHover();
//		m_hover.setEditor(ep);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAnnotationHover(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
//		return m_hover;
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getTextHover(org.eclipse.jface.text.source.ISourceViewer, java.lang.String)
	 */
	public ITextHover getTextHover( ISourceViewer sourceViewer, String contentType) {
//		return m_hover;
		return null;
	}


	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		return new BPTextDefaultTextDoubleClickStategy();
	}

}
