package org.xtuml.bp.model.compare.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.xtuml.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import org.xtuml.bp.model.compare.providers.AbstractTreeDifferenceProvider;

public class EnableGraphicsAction extends Action {

	private ModelContentMergeViewer viewer;

	public EnableGraphicsAction(ModelContentMergeViewer viewer) {
		super("Enable Graphical Compare", IAction.AS_CHECK_BOX);
		this.viewer = viewer;
	}

	@Override
	public void run() {
		viewer.enableGraphics = !viewer.enableGraphics;
		AbstractTreeDifferenceProvider leftDiffProvider = (AbstractTreeDifferenceProvider) viewer.getLeftViewer()
				.getContentProvider();
		AbstractTreeDifferenceProvider rightDiffProvider = (AbstractTreeDifferenceProvider) viewer.getRightViewer()
				.getContentProvider();
		AbstractTreeDifferenceProvider ancDiffProvider = (AbstractTreeDifferenceProvider) viewer.getAncestorTree()
				.getContentProvider();
		leftDiffProvider.setIncludeNonTreeData(viewer.enableGraphics);
		rightDiffProvider.setIncludeNonTreeData(viewer.enableGraphics);
		ancDiffProvider.setIncludeNonTreeData(viewer.enableGraphics);
		viewer.getDifferencer().refresh();
		viewer.refresh();
	}
}
