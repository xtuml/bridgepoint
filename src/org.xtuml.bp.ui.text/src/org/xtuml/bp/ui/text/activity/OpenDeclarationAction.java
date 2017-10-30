package org.xtuml.bp.ui.text.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.VariableLocation_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.OALPersistenceUtil;
import org.xtuml.bp.core.util.EditorUtil;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.ui.text.TextPlugin;

/**
 * This action opens the declaration of model element references
 *
 */
public class OpenDeclarationAction implements IEditorActionDelegate {
	private IEditorPart targetEditor;

	@Override
	public void run(IAction arg0) {
		ActivityEditor editor;
		if (targetEditor instanceof ActivityEditor) {
			editor = (ActivityEditor) targetEditor;
		} else {
			throw new RuntimeException("Expecting Activity editor. Found: " + targetEditor.getClass().getName());
		}

		ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());

		try {
			int startLine = selection.getStartLine() + 1;
			int startOffset = selection.getOffset();
			IRegion findWord = editor.findWord(doc, startOffset);
			startOffset = findWord.getOffset();
			int lineOffset = doc.getLineOffset(startLine - 1);
			int offsetWithinLine = startOffset - lineOffset + 1;
			ActivityEditorInput input = (ActivityEditorInput) editor.getEditorInput();
			Body_c bdy = OALPersistenceUtil.getOALModelElement(input.getModelElement());
			if (bdy == null) {
				// not parsed just return
				return;
			}
			NonRootModelElement declarationElement = (NonRootModelElement) bdy.Finddeclaration(offsetWithinLine,
					startLine);
			if (declarationElement != null) {
				if (declarationElement instanceof VariableLocation_c) {
					VariableLocation_c location = (VariableLocation_c) declarationElement;
				    lineOffset = doc.getLineOffset(location.getLinenumber() - 1);
					editor.selectAndReveal(lineOffset + location.getStartposition() - 1,
							location.getEndposition() + 1 - location.getStartposition());
				} else {
					boolean showInME = shouldShowInME(declarationElement);
					if (showInME) {
						StructuredViewer viewer = UIUtil.getViewer();
						viewer.setSelection(new StructuredSelection(declarationElement), true);
						IViewPart explorerView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.findView("org.xtuml.bp.ui.explorer.ExplorerView");
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(explorerView);
					} else {
						Package_c pkg = declarationElement.getFirstParentPackage();
						IEditorPart editorPart = EditorUtil.openEditorForElement(pkg);
						editorPart.getSite().getSelectionProvider()
								.setSelection(new StructuredSelection(declarationElement));
						zoomToSelected(editorPart);
					}
				}
			}
		} catch (BadLocationException e) {
			TextPlugin.logError("Unable to locate line information for the text editor selection.", e);
		}

	}

	private void zoomToSelected(IEditorPart editorPart) {
		// open the editor using the explorer view
		final String packageName = "org.xtuml.bp.ui.graphics"; //$NON-NLS-1$
		Bundle bundle = Platform.getBundle(packageName);
		try {
			// use reflection due to dependency loops, i.e, we
			// cannot import
			// ui.graphics
			Class<?> loadClass = bundle.loadClass(packageName + ".editor." + "ModelEditor"); //$NON-NLS-1$ //$NON-NLS-2$
			Method method = loadClass.getMethod("getGraphicalEditor", new Class[0]); //$NON-NLS-1$
			Object result = method.invoke(editorPart, new Object[0]);
			// we need to wait for the graphical editor to be
			// fully configured in order to zoom to selected
			// do this by waiting until the horizontal control
			// has an extent
			Method getCanvas = result.getClass().getMethod("getCanvas", new Class[0]);
			Object canvas = getCanvas.invoke(result, new Object[0]);
			Method addControlListener = canvas.getClass().getMethod("addControlListener",
					new Class[] { ControlListener.class });
			Method getViewport = canvas.getClass().getMethod("getViewport", new Class[0]);
			Object viewport = getViewport.invoke(canvas, new Object[0]);
			Method getHorizontalRangeModel = viewport.getClass().getMethod("getHorizontalRangeModel",
					new Class[0]);
			Object horizontalRangeModel = getHorizontalRangeModel.invoke(viewport, new Object[0]);
			Method getExtent = horizontalRangeModel.getClass().getMethod("getExtent", new Class[0]);
			Integer extent = (Integer) getExtent.invoke(horizontalRangeModel, new Object[0]);
			if(extent > 100) {
				// just resize, otherwise the canvas is initializing
				// and we must do it in a control listener
				performZoom(result);
			}
			addControlListener.invoke(canvas, new Object[] { new ControlListener() {

				@Override
				public void controlResized(ControlEvent event) {
					try {
						Method getExtent = horizontalRangeModel.getClass().getMethod("getExtent", new Class[0]);
						Integer extent = (Integer) getExtent.invoke(horizontalRangeModel, new Object[0]);
						if (extent > 100) {
							performZoom(result);
						}
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e1) {
						TextPlugin.logError("Unable to zoom to selected when opening declaration.", e1);
					}
				}

				@Override
				public void controlMoved(ControlEvent e) {
					// nothing to do
				}
			} });			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			CorePlugin.logError("Could not access ModelEditor class.", e);
		}
	}

	private void performZoom(Object result) {
		Method zoom;
		try {
			zoom = result.getClass().getMethod("zoomSelected", new Class[0]);
			zoom.invoke(result, new Object[0]);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			TextPlugin.logError("Unable to zoom to selected when opening declaration.", e);
		}
	}

	private boolean shouldShowInME(NonRootModelElement declarationElement) {
		if (declarationElement instanceof Function_c) {
			return true;
		}
		if (declarationElement instanceof Bridge_c) {
			return true;
		}
		if (declarationElement instanceof Operation_c) {
			return true;
		}
		if (declarationElement instanceof StateMachineEvent_c) {
			return true;
		}
		if (declarationElement instanceof InterfaceSignal_c) {
			return true;
		}
		if (declarationElement instanceof InterfaceOperation_c) {
			return true;
		}
		if (declarationElement instanceof PropertyParameter_c) {
			return true;
		}
		if (declarationElement instanceof BridgeParameter_c) {
			return true;
		}
		if (declarationElement instanceof FunctionParameter_c) {
			return true;
		}
		if (declarationElement instanceof OperationParameter_c) {
			return true;
		}
		if (declarationElement instanceof Attribute_c) {
			return true;
		}
		if (declarationElement instanceof StateMachineEventDataItem_c) {
			return true;
		}
		if (declarationElement instanceof Enumerator_c) {
			return true;
		}
		return false;
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		arg0.setEnabled(targetEditor instanceof ActivityEditor);
	}

	@Override
	public void setActiveEditor(IAction arg0, IEditorPart arg1) {
		this.targetEditor = arg1;
	}

}
