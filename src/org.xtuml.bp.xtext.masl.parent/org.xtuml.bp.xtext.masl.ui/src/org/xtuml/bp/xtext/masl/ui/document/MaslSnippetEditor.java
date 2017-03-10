package org.xtuml.bp.xtext.masl.ui.document;

import com.google.common.base.Objects;
import com.google.inject.Inject;

import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.IModelChangeListener;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.AbstractModelElementEditorInput;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.masl.MASLEditorInputFactory;
import org.xtuml.bp.ui.text.typedefinition.TypeDefinitionEditorInput;
import org.xtuml.bp.ui.text.typedefinition.TypeDefinitionEditorInputFactory;
import org.xtuml.bp.xtext.masl.ui.document.MaslDocumentProvider;

@SuppressWarnings("all")
public class MaslSnippetEditor extends XtextEditor {
  @Inject
  private IImageHelper imageHelper;
  
  private Label signatureLabel;
  private IModelChangeListener modelChangeListener;
  
  public MaslSnippetEditor() {
	  super();
	  modelChangeListener = new ModelChangeAdapter() {

		@Override
			public void modelElementReloaded(ModelChangedEvent event) {
				NonRootModelElement modelElement = ((AbstractModelElementEditorInput) getEditorInput())
						.getModelElement();
				NonRootModelElement newElement = (NonRootModelElement) event.getNewModelElement();
				if (modelElement.getPersistableComponent().equals(newElement.getPersistableComponent())) {
					try {
						if (TypeDefinitionEditorInput.isSupported(modelElement)) {
							setInput(TypeDefinitionEditorInputFactory.getDefaultInstance()
									.createInstance(modelElement.getModelRoot().getInstanceList(modelElement.getClass())
											.get(modelElement.getInstanceKey())));
						} else {
							setInput(
									MASLEditorInputFactory.getDefaultInstance().createInstance(modelElement.getModelRoot()
											.getInstanceList(modelElement.getClass()).get(modelElement.getInstanceKey())));
						}
						updateLabelAndVisibleRegion((MaslDocumentProvider) getDocumentProvider(),
								(AbstractModelElementPropertyEditorInput) getEditorInput());
					} catch (CoreException e) {
						CorePlugin.logError("Unable to reload editor content", e);
					}
				}
			}


	  };
	  Ooaofooa.getDefaultInstance().addModelChangeListener(modelChangeListener);
  }
  
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
        Label _label = new Label(comp, SWT.WRAP);
        this.signatureLabel = _label;
        Font _font = JFaceResources.getFont(JFaceResources.TEXT_FONT);
        this.signatureLabel.setFont(_font);
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
      this.updateLabelAndVisibleRegion(maslDocumentProvider, ((AbstractModelElementPropertyEditorInput)input));
    } else {
      super.createPartControl(parent);
    }
    IDocumentProvider _documentProvider_1 = this.getDocumentProvider();
    _documentProvider_1.addElementStateListener(new IElementStateListener() {
      @Override
      public void elementContentAboutToBeReplaced(final Object element) {
      }
      
      @Override
      public void elementContentReplaced(final Object element) {
        IEditorInput _editorInput = MaslSnippetEditor.this.getEditorInput();
        if ((_editorInput instanceof AbstractModelElementPropertyEditorInput)) {
          IDocumentProvider _documentProvider = MaslSnippetEditor.this.getDocumentProvider();
          IEditorInput _editorInput_1 = MaslSnippetEditor.this.getEditorInput();
          MaslSnippetEditor.this.updateLabelAndVisibleRegion(((MaslDocumentProvider) _documentProvider), ((AbstractModelElementPropertyEditorInput) _editorInput_1));
        }
      }
      
      @Override
      public void elementDeleted(final Object element) {
      }
      
      @Override
      public void elementDirtyStateChanged(final Object element, final boolean isDirty) {
      }
      
      @Override
      public void elementMoved(final Object originalElement, final Object movedElement) {
      }
    });
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
  
  @Override
  public void dispose() {
	  Ooaofooa.getDefaultInstance().removeModelChangeListener(modelChangeListener);
  }

public void updateLabelAndVisibleRegion(final MaslDocumentProvider maslDocumentProvider, final AbstractModelElementPropertyEditorInput input) {
    String _name = input.getName();
    this.setPartName(_name);
    final String prefix = maslDocumentProvider.getPrefix(input);
    final String editable = maslDocumentProvider.getEditable(input);
    ISourceViewer _sourceViewer = this.getSourceViewer();
    int _length = prefix.length();
    int _length_1 = editable.length();
    _sourceViewer.setVisibleRegion(_length, _length_1);
    boolean _notEquals = (!Objects.equal(this.signatureLabel, null));
    if (_notEquals) {
      String _header = maslDocumentProvider.getHeader(input);
      this.signatureLabel.setText(_header);
      Composite _parent = this.signatureLabel.getParent();
      _parent.layout(true);
    }
  }
}
