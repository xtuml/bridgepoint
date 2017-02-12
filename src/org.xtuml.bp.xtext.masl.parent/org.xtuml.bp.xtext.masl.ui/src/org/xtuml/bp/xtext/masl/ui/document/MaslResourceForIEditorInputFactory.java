package org.xtuml.bp.xtext.masl.ui.document;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;

@SuppressWarnings("all")
public class MaslResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {
  @Override
  public Resource createResource(final IEditorInput editorInput) {
    try {
      Resource _xifexpression = null;
      if ((editorInput instanceof AbstractModelElementPropertyEditorInput)) {
        IFile _file = ((AbstractModelElementPropertyEditorInput)editorInput).getFile();
        _xifexpression = super.createResource(_file);
      } else {
        _xifexpression = super.createResource(editorInput);
      }
      return _xifexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
