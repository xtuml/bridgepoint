package com.mentor.nucleus.bp.model.compare.contentmergeviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.ui.text.OALEditorPlugin;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALEditorConfiguration;

public class ActionLanguageMergeViewerCreator implements IViewerCreator {

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new TextMergeViewer(parent, config) {
			
			@Override
			protected void createControls(Composite composite) {
				super.createControls(composite);
				// set title of the compare window
				composite.setData(CompareUI.COMPARE_VIEWER_TITLE, "Action Language Compare");
			}

			@Override
			protected void configureTextViewer(TextViewer textViewer) {
				if (textViewer instanceof ISourceViewer) {
					((ISourceViewer) textViewer)
							.configure(new OALEditorConfiguration(
									OALEditorPlugin.getDefaultOALPlugin()
											.getSyntaxHighlightingPreferences()));
				}
			}
			
		};
	}	

}
