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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.xtuml.bp.als.oal.ParseRunnable;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.VariableLocation_c;
import org.xtuml.bp.core.common.MultipleOccurrenceElement;
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
			NonRootModelElement modelElement = input.getModelElement();
			Body_c bdy = OALPersistenceUtil.getOALModelElement(modelElement);
		    boolean parsed = false;
		    
			if (bdy == null) {
				parseBody(modelElement, offsetWithinLine, doc);
				bdy = OALPersistenceUtil.getOALModelElement(modelElement);
				if (bdy == null) {
					// element not found, just return
					return;
				}
				parsed = true;
			}
			NonRootModelElement declarationElement = (NonRootModelElement) bdy.Finddeclaration(offsetWithinLine,
					startLine);
			if (declarationElement == null && !parsed) {
				// If the user edited the OAL, it may need to be parsed  
				parseBody(modelElement, offsetWithinLine, doc);
				declarationElement = (NonRootModelElement) bdy.Finddeclaration(offsetWithinLine,
						startLine);
			}
			if (declarationElement != null) {
				if (declarationElement instanceof VariableLocation_c) {
					VariableLocation_c location = (VariableLocation_c) declarationElement;
				    lineOffset = doc.getLineOffset(location.getLinenumber() - 1);
					editor.selectAndReveal(lineOffset + location.getStartposition() - 1,
							location.getEndposition() + 1 - location.getStartposition());
				} else {
					boolean showInME = shouldShowInME(declarationElement);
					if (showInME) {
						showInModelExplorer(declarationElement);
					} else {
						showInModelExplorer(declarationElement);
						Package_c pkg = declarationElement.getFirstParentPackage();
                        if ( null == pkg ) {
                            pkg = declarationElement.getFirstParentComponent().getFirstParentPackage();
                        }
						IEditorPart editorPart = EditorUtil.openEditorForElement(pkg);
                        NonRootModelElement selectionElement = declarationElement;
                        if ( declarationElement instanceof Port_c ) {
                              selectionElement = Requirement_c.getOneC_ROnR4009(InterfaceReference_c.getOneC_IROnR4016((Port_c)declarationElement));
                              if ( null == selectionElement ) {
                                  selectionElement = Provision_c.getOneC_POnR4009(InterfaceReference_c.getOneC_IROnR4016((Port_c)declarationElement));
                              }
                        }
						editorPart.getSite().getSelectionProvider()
								.setSelection(new StructuredSelection(selectionElement));
						zoomToSelected(editorPart);
					}
				}
			}
		} catch (BadLocationException | PartInitException e) {
			TextPlugin.logError("Unable to locate line information for the text editor selection.", e);
		}

	}
	
	private void parseBody(NonRootModelElement modelElement, int offsetWithinLine, IDocument doc) {
		// run a parse of this body
		ParseRunnable parseRunner = new ParseRunnable(modelElement, doc.get());
		parseRunner.run();						
	}

	private void showInModelExplorer(NonRootModelElement declarationElement) throws PartInitException {
		String explorerViewId = "org.xtuml.bp.ui.explorer.ExplorerView";
		IViewPart explorerView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.findView(explorerViewId);
		if(explorerView == null) {
			// not opened, open now
			explorerView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(explorerViewId);
		}
		StructuredViewer viewer = UIUtil.getViewer();
		Object selectionElement = declarationElement;
		if ( selectionElement instanceof PropertyParameter_c ) {
			// for C_PP instances, must get the instance of MultipleOccurrenceElement
			// for this work, we always want the C_PP instance in the interface definition itself
			NonRootModelElement parent = InterfaceOperation_c.getOneC_IOOnR4004(ExecutableProperty_c.getOneC_EPOnR4006((PropertyParameter_c)selectionElement));
			if ( null == parent ) parent = InterfaceSignal_c.getOneC_ASOnR4004(ExecutableProperty_c.getOneC_EPOnR4006((PropertyParameter_c)selectionElement));
			if ( null != parent ) selectionElement = MultipleOccurrenceElement.getElement( (NonRootModelElement)selectionElement, parent );
		}
		viewer.setSelection(new StructuredSelection(selectionElement), true);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(explorerView);

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
		if (declarationElement instanceof ProvidedSignal_c) {
			return true;
		}
		if (declarationElement instanceof ProvidedOperation_c) {
			return true;
		}
		if (declarationElement instanceof RequiredSignal_c) {
			return true;
		}
		if (declarationElement instanceof RequiredOperation_c) {
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
		if (targetEditor instanceof ActivityEditor) {
			arg0.setEnabled(true);
		}
	}

	@Override
	public void setActiveEditor(IAction arg0, IEditorPart arg1) {
		if (arg1 == null && arg0 != null) {
			arg0.setEnabled(false);
		}
		if (arg1 instanceof ActivityEditor) {
			this.targetEditor = arg1;
			if (arg0 != null) {
				arg0.setEnabled(true);
			}
		}
	}

}
