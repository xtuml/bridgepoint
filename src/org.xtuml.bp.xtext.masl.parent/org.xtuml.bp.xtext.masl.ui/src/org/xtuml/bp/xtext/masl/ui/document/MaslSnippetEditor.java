package org.xtuml.bp.xtext.masl.ui.document;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.xtext.masl.ui.document.MaslDocumentProvider;

@SuppressWarnings("all")
public class MaslSnippetEditor extends XtextEditor {
  @Inject
  private IImageHelper imageHelper;
  
  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }
  
  @Override
  public void createPartControl(final Composite parent) {
    final IEditorInput input = this.getEditorInput();
    if ((input instanceof AbstractModelElementPropertyEditorInput)) {
      IDocumentProvider _documentProvider = this.getDocumentProvider();
      final MaslDocumentProvider maslDocumentProvider = ((MaslDocumentProvider) _documentProvider);
      final String header = maslDocumentProvider.getHeader(((AbstractModelElementPropertyEditorInput)input));
      boolean _notEquals = (!Objects.equal(header, null));
      if (_notEquals) {
        final Composite comp = new Composite(parent, SWT.NONE);
        GridLayout _gridLayout = new GridLayout();
        final Procedure1<GridLayout> _function = (GridLayout it) -> {
          it.numColumns = 1;
        };
        GridLayout _doubleArrow = ObjectExtensions.<GridLayout>operator_doubleArrow(_gridLayout, _function);
        comp.setLayout(_doubleArrow);
        final Label label = new Label(comp, SWT.WRAP);
        Font _font = JFaceResources.getFont(JFaceResources.TEXT_FONT);
        label.setFont(_font);
        label.setText(header);
        super.createPartControl(comp);
        Control[] _children = comp.getChildren();
        Control _get = _children[1];
        GridData _gridData = new GridData();
        final Procedure1<GridData> _function_1 = (GridData it) -> {
          it.horizontalAlignment = GridData.FILL;
          it.verticalAlignment = GridData.FILL;
          it.grabExcessHorizontalSpace = true;
          it.grabExcessVerticalSpace = true;
        };
        GridData _doubleArrow_1 = ObjectExtensions.<GridData>operator_doubleArrow(_gridData, _function_1);
        _get.setLayoutData(_doubleArrow_1);
      } else {
        super.createPartControl(parent);
      }
      final String prefix = maslDocumentProvider.getPrefix(((AbstractModelElementPropertyEditorInput)input));
      final String editable = maslDocumentProvider.getEditable(((AbstractModelElementPropertyEditorInput)input));
      ISourceViewer _sourceViewer = this.getSourceViewer();
      int _length = prefix.length();
      int _length_1 = editable.length();
      _sourceViewer.setVisibleRegion(_length, _length_1);
    } else {
      super.createPartControl(parent);
    }
  }
  
  @Override
  public Image getDefaultImage() {
    Image _xifexpression = null;
    IEditorInput _editorInput = this.getEditorInput();
    if ((_editorInput instanceof AbstractModelElementPropertyEditorInput)) {
      IEditorInput _editorInput_1 = this.getEditorInput();
      ImageDescriptor _imageDescriptor = _editorInput_1.getImageDescriptor();
      _xifexpression = this.imageHelper.getImage(_imageDescriptor);
    } else {
      _xifexpression = super.getDefaultImage();
    }
    return _xifexpression;
  }
}
