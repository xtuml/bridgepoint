package org.xtuml.bp.xtext.masl.ui.document;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.IModelChangeListener;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.ui.text.AbstractModelElementEditorInput;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.masl.MASLEditorInputFactory;

@SuppressWarnings("all")
public class MaslDocumentProvider extends XtextDocumentProvider {
  @Override
  public String getEncoding(final Object element) {
    try {
      String _xifexpression = null;
      if ((element instanceof AbstractModelElementPropertyEditorInput)) {
        IFile _file = ((AbstractModelElementPropertyEditorInput)element).getFile();
        _xifexpression = _file.getCharset();
      } else {
        _xifexpression = super.getEncoding(element);
      }
      return _xifexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  protected boolean setDocumentContent(final IDocument document, final IEditorInput editorInput, final String encoding) throws CoreException {
    boolean _xifexpression = false;
    if ((editorInput instanceof AbstractModelElementPropertyEditorInput)) {
      boolean _xblockexpression = false;
      {
        StringInputStream _extendedInputStream = this.getExtendedInputStream(((AbstractModelElementPropertyEditorInput)editorInput));
        super.setDocumentContent(document, _extendedInputStream, encoding);
        this.setDocumentResource(((XtextDocument) document), editorInput, encoding);
        _xblockexpression = true;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = super.setDocumentContent(document, editorInput, encoding);
    }
    return _xifexpression;
  }
  
  @Override
  public boolean isDeleted(final Object element) {
    boolean _xifexpression = false;
    if ((element instanceof AbstractModelElementPropertyEditorInput)) {
      return false;
    } else {
      _xifexpression = super.isDeleted(element);
    }
    return _xifexpression;
  }
  
  public StringInputStream getExtendedInputStream(final AbstractModelElementPropertyEditorInput editorInput) {
    StringConcatenation _builder = new StringConcatenation();
    String _prefix = this.getPrefix(editorInput);
    _builder.append(_prefix, "");
    _builder.newLineIfNotEmpty();
    String _editable = this.getEditable(editorInput);
    _builder.append(_editable, "");
    _builder.newLineIfNotEmpty();
    String _suffix = this.getSuffix(editorInput);
    _builder.append(_suffix, "");
    _builder.newLineIfNotEmpty();
    return new StringInputStream(_builder.toString());
  }
  
  private String getDomain(final NonRootModelElement context) {
    IFile _file = context.getFile();
    final IPath fullPath = _file.getFullPath();
    String _xifexpression = null;
    int _segmentCount = fullPath.segmentCount();
    boolean _greaterThan = (_segmentCount > 2);
    if (_greaterThan) {
      _xifexpression = fullPath.segment(2);
    } else {
      _xifexpression = fullPath.segment(0);
    }
    return _xifexpression;
  }
  
  public String getHeader(final AbstractModelElementPropertyEditorInput editorInput) {
    String _xblockexpression = null;
    {
      final NonRootModelElement modelElement = editorInput.getModelElement();
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (modelElement instanceof UserDataType_c) {
          _matched=true;
          _switchResult = null;
        }
      }
      if (!_matched) {
        String _prefix = this.getPrefix(editorInput);
        _switchResult = _prefix.trim();
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public String getPrefix(final AbstractModelElementPropertyEditorInput editorInput) {
    String _xblockexpression = null;
    {
      final NonRootModelElement modelElement = editorInput.getModelElement();
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (modelElement instanceof UserDataType_c) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("domain ");
          NonRootModelElement _modelElement = editorInput.getModelElement();
          String _domain = this.getDomain(_modelElement);
          _builder.append(_domain, "");
          _builder.append(" is");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("type ");
          String _name = ((UserDataType_c)modelElement).getName();
          _builder.append(_name, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("type ");
          String _name_1 = ((UserDataType_c)modelElement).getName();
          _builder.append(_name_1, "\t");
          _builder.append(" is ");
          _builder.newLineIfNotEmpty();
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        StringConcatenation _builder = new StringConcatenation();
        ModelRoot _modelRoot = modelElement.getModelRoot();
        String _Get_formatted_body = Ooaofooa.Get_formatted_body(_modelRoot, modelElement, true);
        _builder.append(_Get_formatted_body, "");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        _switchResult = _builder.toString();
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public String getEditable(final AbstractModelElementPropertyEditorInput editorInput) {
    try {
      return editorInput.getPropertyValue();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String getSuffix(final AbstractModelElementPropertyEditorInput editorInput) {
    String _xblockexpression = null;
    {
      final NonRootModelElement modelElement = editorInput.getModelElement();
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (modelElement instanceof UserDataType_c) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(";");
          _builder.newLine();
          _builder.append("end;");
          _builder.newLine();
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        _switchResult = "";
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  @Override
  protected void doSaveDocument(final IProgressMonitor monitor, final Object element, final IDocument document, final boolean overwrite) throws CoreException {
    try {
      if ((element instanceof AbstractModelElementPropertyEditorInput)) {
        String _prefix = this.getPrefix(((AbstractModelElementPropertyEditorInput)element));
        final int prefixLength = _prefix.length();
        int _length = document.getLength();
        String _suffix = this.getSuffix(((AbstractModelElementPropertyEditorInput)element));
        int _length_1 = _suffix.length();
        int _minus = (_length - _length_1);
        int _minus_1 = (_minus - prefixLength);
        String _get = document.get(prefixLength, _minus_1);
        final String editable = _get.trim();
        String _xifexpression = null;
        boolean _and = false;
        NonRootModelElement _modelElement = ((AbstractModelElementPropertyEditorInput)element).getModelElement();
        if (!(_modelElement instanceof UserDataType_c)) {
          _and = false;
        } else {
          boolean _endsWith = editable.endsWith(";");
          _and = _endsWith;
        }
        if (_and) {
          int _length_2 = editable.length();
          int _minus_2 = (_length_2 - 1);
          _xifexpression = editable.substring(0, _minus_2);
        } else {
          _xifexpression = editable;
        }
        final String newDefinition = _xifexpression;
        Display _default = Display.getDefault();
        final Runnable _function = () -> {
          try {
            ((AbstractModelElementPropertyEditorInput)element).setPropertyValue(newDefinition);
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        };
        _default.asyncExec(_function);
      } else {
        super.doSaveDocument(monitor, element, document, overwrite);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
