package org.xtuml.qa.odometer.tracker;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditorInput;
import org.xtuml.qa.odometer.timer.OdoTimer;

public class OdoPartListener implements IPartListener {

	@Override
	public void partActivated(IWorkbenchPart part) {
		OdoTimer.kick(part);
		if (part instanceof IEditorPart) {
			listenTo((IEditorPart)part);
		}

	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		OdoTimer.kick(part);

	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		OdoTimer.kick(part);
		if (part instanceof ITextEditor) {
			IEditorInput ei = ((ITextEditor)part).getEditorInput();
			if (ei != null) {
			  if (part instanceof ITextEditor) {
			    IDocument doc = ((ITextEditor)part).getDocumentProvider().getDocument(ei);
			    if (doc != null) {
				  doc.removeDocumentListener(OdoDocumentListener.getInstance());
			    }
			    else {
				  System.out.println("partClosed: Document was null");
			    }
			  }
			  else if (ei instanceof GraphicalEditorInput) {
				  Model_c model = ((GraphicalEditorInput)ei).getInput();
				  if (model != null) {
					  ((NonRootModelElement)model.getRepresents()).getTransactionManager().removeTransactionListener(OdoTransactionListener.getInstance());
				  }
				  else {
					System.out.println("partClosed: No model found.");
				  }
			  }
			}
			else {
				System.out.println("partClosed: Editor input was null");
			}
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		OdoTimer.kick(part);

	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		OdoTimer.kick(part);
		if (part instanceof IEditorPart) {
			listenTo((IEditorPart)part);
		}
	}

	private static OdoPartListener self = null;
	public static IPartListener getInstance() {
		if (self == null) {
			self = new OdoPartListener();
		}
		return self;
	}
    private void listenTo(IEditorPart part) {
		IEditorInput ei = ((IEditorPart)part).getEditorInput();
		if (ei != null) {
		  if (part instanceof ITextEditor) {
		    IDocumentProvider docP = ((ITextEditor)part).getDocumentProvider();
		    if (docP != null) {
		      IDocument doc = docP.getDocument(ei);
		      if (doc != null) {
		        OdoDocumentListener.setTarget(docP);
			    doc.addDocumentListener(OdoDocumentListener.getInstance());
		      }
		      else {
				  System.out.println("partOpened: Document was null");
		      }
		    }
		    else {
			  System.out.println("partOpened: Document Provider was null");
		    }
		  }
		  else {
			  if (ei instanceof GraphicalEditorInput) {
				  Model_c model = ((GraphicalEditorInput)ei).getInput();
				  if (model != null) {
					  ((NonRootModelElement)model.getRepresents()).getTransactionManager().addTransactionListener(OdoTransactionListener.getInstance());
				  }
				  else {
					  System.out.println("partOpened: No model found.");
				  }
			  }
		  }
		}
		else {
		  System.out.println("partOpened: Editor input was null");
	    }
    }
}
