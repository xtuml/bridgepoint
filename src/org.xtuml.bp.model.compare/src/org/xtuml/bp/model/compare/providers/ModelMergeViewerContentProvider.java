package org.xtuml.bp.model.compare.providers;
//========================================================================
//
//File: providers/ModelMergeViewerContentProvider.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
// Provider handling left/right content as well as writing data to either.
//
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.Image;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.AbstractModelExportFactory;
import org.xtuml.bp.model.compare.ComparePlugin;
import org.xtuml.bp.model.compare.ModelCacheManager;
import org.xtuml.bp.model.compare.TreeDifference;
import org.xtuml.bp.model.compare.ModelCacheManager.ModelLoadException;
import org.xtuml.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;

public class ModelMergeViewerContentProvider implements IMergeViewerContentProvider {

	private CompareConfiguration configuration;
	private ModelContentMergeViewer viewer;

	public ModelMergeViewerContentProvider(CompareConfiguration configuration, ModelContentMergeViewer viewer) {
		this.configuration = configuration;
		this.viewer = viewer;
	}

	@Override
	public String getAncestorLabel(Object input) {
		return configuration.getAncestorLabel(input);
	}

	@Override
	public Image getAncestorImage(Object input) {
		return configuration.getAncestorImage(input);
	}

	@Override
	public Object getAncestorContent(Object input) {
		if (input instanceof ICompareInput)
			return ((ICompareInput) input).getAncestor();
		return null;
	}

	@Override
	public boolean showAncestor(Object input) {
		return true;
	}

	@Override
	public String getLeftLabel(Object input) {
		return configuration.getLeftLabel(input);
	}

	@Override
	public Image getLeftImage(Object input) {
		return configuration.getLeftImage(input);
	}

	@Override
	public Object getLeftContent(Object input) {
		if (input instanceof ICompareInput)
			return ((ICompareInput) input).getLeft();
		return null;
	}

	@Override
	public boolean isLeftEditable(Object input) {
		return true;
	}

	@Override
	public void saveLeftContent(Object element, byte[] bytes) {
		setContent(element, bytes, true);
	}

	@Override
	public String getRightLabel(Object input) {
		return configuration.getRightLabel(input);
	}

	@Override
	public Image getRightImage(Object input) {
		return configuration.getRightImage(input);
	}

	@Override
	public Object getRightContent(Object input) {
		if (input instanceof ICompareInput)
			return ((ICompareInput) input).getRight();
		return null;
	}

	@Override
	public boolean isRightEditable(Object input) {
		return true;
	}

	@Override
	public void saveRightContent(Object element, byte[] bytes) {
		setContent(element, bytes, false);
	}

	private void setContent(Object element, byte[] bytes, boolean toLeft) {
		if (element instanceof ICompareInput) {
			ICompareInput node = (ICompareInput) element;
			updateSMIds(node, toLeft);
			writeData(node, toLeft);
			NonRootModelElement[] rootElements = new NonRootModelElement[0];
			try {
				rootElements = viewer.getManager().getRootElements(element, this, false,
						toLeft ? viewer.getLeftCompareRoot() : viewer.getRightCompareRoot(),
						toLeft ? ModelCacheManager.getLeftKey(element) : ModelCacheManager.getRightKey(element));
				if (rootElements.length != 0) {
					ICompareInput savedInput = viewer.getSavedModels().get(rootElements[0]);
					if (savedInput == null) {
						viewer.getSavedModels().put(rootElements[0], node);
					}
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to get root model elements from compare input.", e);
			} finally {
				viewer.getManager().releaseModel(element, this,
						toLeft ? ModelCacheManager.getLeftKey(element) : ModelCacheManager.getRightKey(element));
			}
		}
	}

	protected void writeData(ICompareInput input, boolean toLeft) {
		try {
			ITypedElement destination = input.getLeft();
			Ooaofooa root = Ooaofooa.getInstance(ModelRoot.getLeftCompareRootPrefix() + input.hashCode());
			if (!toLeft) {
				destination = input.getRight();
				root = Ooaofooa.getInstance(ModelRoot.getRightCompareRootPrefix() + input.hashCode());
			}
			final NonRootModelElement rootElement = viewer.getManager().getRootElements(destination, null, false, root,
					ModelCacheManager.getLeftKey(input))[0];
			if (destination instanceof IEditableContent) {
				// before saving copy all graphical changes that are
				// non-conflicting
				List<TreeDifference> incomingGraphicalDifferences = viewer.getIncomingGraphicalDifferences(toLeft);
				if (incomingGraphicalDifferences.size() > 0) {
					viewer.mergeIncomingGraphicalChanges(incomingGraphicalDifferences, toLeft, input);
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				AbstractModelExportFactory modelExportFactory = CorePlugin.getModelExportFactory();
				IRunnableWithProgress runnable = modelExportFactory.create(root, baos, rootElement);
				runnable.run(new NullProgressMonitor());
				((IEditableContent) destination).setContent(baos.toByteArray());
				if (destination instanceof ResourceNode) {
					((IFile) ((ResourceNode) destination).getResource()).setContents(
							new ByteArrayInputStream(baos.toByteArray()), IFile.FORCE | IFile.KEEP_HISTORY,
							new NullProgressMonitor());
				}
				WorkspaceJob job = new WorkspaceJob("Refresh workspace content") {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						NonRootModelElement elementGlobally = (NonRootModelElement) Ooaofooa.getDefaultInstance()
								.getInstanceList(rootElement.getClass()).getGlobal(rootElement.getInstanceKey());
						if (elementGlobally != null) {
							if (elementGlobally.getFile() != null) {
								elementGlobally.getFile().refreshLocal(IFile.DEPTH_INFINITE, monitor);
							}
						}
						return Status.OK_STATUS;
					}
				};
				job.schedule(1500);
			}

		} catch (FileNotFoundException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e, ModelContentMergeViewer.class);
		} catch (ModelLoadException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e, ModelContentMergeViewer.class);
		} catch (InvocationTargetException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e, ModelContentMergeViewer.class);
		} catch (InterruptedException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e, ModelContentMergeViewer.class);
		} catch (CoreException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e, ModelContentMergeViewer.class);
		}
	}

	private void updateSMIds(ICompareInput node, boolean left) {
		// reset any SM ids that were upgrade for compare/merge
		Object key = ModelCacheManager.getLeftKey(node);
		if (!left) {
			key = ModelCacheManager.getRightKey(node);
		}
		Ooaofooa compareRoot = viewer.getLeftCompareRoot();
		if (!left) {
			compareRoot = viewer.getRightCompareRoot();
		}
		UUID originalSMId = viewer.getManager().getOriginalSMIdFromEntry(key);
		if (originalSMId != null) {
			NonRootModelElement[] rootElements;
			try {
				rootElements = viewer.getManager().getRootElements(node, this, false, compareRoot, key);
				// only support case where there is only one root
				StateMachine_c sm = null;
				if (rootElements[0] instanceof InstanceStateMachine_c) {
					sm = StateMachine_c.getOneSM_SMOnR517((InstanceStateMachine_c) rootElements[0]);
				}
				if (rootElements[0] instanceof ClassStateMachine_c) {
					sm = StateMachine_c.getOneSM_SMOnR517((ClassStateMachine_c) rootElements[0]);
				}
				if (sm != null) {
					ModelCacheManager.updateIdForStateMachine(originalSMId, sm);
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to load compare data.", e);
			}
		}
	}

}
