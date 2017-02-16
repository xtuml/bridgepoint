package org.xtuml.bp.xtext.masl.ui.rename;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.IRenameElementParticipant;
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage;
import org.xtuml.bp.xtext.masl.ui.rename.MaslRenameRefactoringExecutor;
import org.xtuml.bp.xtext.masl.ui.rename.XtumlRenameElementContext;
import org.xtuml.bp.xtext.masl.ui.rename.XtumlToMaslMapper;

@SuppressWarnings("all")
public class MaslRenameParticipant implements IRenameElementParticipant {
  @Inject
  private XtextResourceSetProvider rsp;
  
  @Inject
  private ResourceDescriptionsProvider rdp;
  
  @Inject
  private MaslRenameRefactoringExecutor executor;
  
  @Inject
  @Extension
  private XtumlToMaslMapper _xtumlToMaslMapper;
  
  @Inject
  @Extension
  private StructurePackage _structurePackage;
  
  private IProject project;
  
  private List<EClass> eClasses;
  
  private QualifiedName oldQName;
  
  private String newName;
  
  @Override
  public IStatus renameElement(final NonRootModelElement xtumlElement, final String newName, final String oldName) {
    IFile _file = xtumlElement.getFile();
    IProject _project = _file.getProject();
    this.project = _project;
    this.newName = newName;
    List<EClass> _maslEClasses = this._xtumlToMaslMapper.getMaslEClasses(xtumlElement);
    this.eClasses = _maslEClasses;
    boolean _isEmpty = this.eClasses.isEmpty();
    if (_isEmpty) {
      return Status.OK_STATUS;
    }
    QualifiedName _maslQualifiedName = this._xtumlToMaslMapper.getMaslQualifiedName(xtumlElement, oldName);
    this.oldQName = _maslQualifiedName;
    if ((this.oldQName == null)) {
      return Status.OK_STATUS;
    }
    final XtumlRenameElementContext renameElementContext = this.getRenameElementContext(this.eClasses, this.oldQName, this.project);
    boolean _isEmpty_1 = renameElementContext.isEmpty();
    if (_isEmpty_1) {
      return Status.OK_STATUS;
    }
    return this.executor.doRename(renameElementContext, newName);
  }
  
  private XtumlRenameElementContext getRenameElementContext(final List<EClass> eClasses, final QualifiedName name, final IProject project) {
    final ArrayList<Pair<URI, EClass>> uri2eClass = CollectionLiterals.<Pair<URI, EClass>>newArrayList();
    final Function1<EClass, Iterable<IEObjectDescription>> _function = (EClass eClass) -> {
      Iterable<IEObjectDescription> _xblockexpression = null;
      {
        ResourceSet _get = this.rsp.get(project);
        final IResourceDescriptions index = this.rdp.getResourceDescriptions(_get);
        _xblockexpression = index.getExportedObjects(((EClass) eClass), name, false);
      }
      return _xblockexpression;
    };
    List<Iterable<IEObjectDescription>> _map = ListExtensions.<EClass, Iterable<IEObjectDescription>>map(eClasses, _function);
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(_map);
    Set<IEObjectDescription> _set = IterableExtensions.<IEObjectDescription>toSet(_flatten);
    final Function1<IEObjectDescription, Boolean> _function_1 = (IEObjectDescription it) -> {
      boolean _and = false;
      boolean _and_1 = false;
      URI _eObjectURI = it.getEObjectURI();
      boolean _isPlatformResource = _eObjectURI.isPlatformResource();
      if (!_isPlatformResource) {
        _and_1 = false;
      } else {
        URI _eObjectURI_1 = it.getEObjectURI();
        String _segment = _eObjectURI_1.segment(2);
        boolean _equals = Objects.equal(_segment, "models");
        _and_1 = _equals;
      }
      if (!_and_1) {
        _and = false;
      } else {
        boolean _or = false;
        boolean _or_1 = false;
        EClass _eClass = it.getEClass();
        EClass _parameter = this._structurePackage.getParameter();
        boolean _equals_1 = Objects.equal(_eClass, _parameter);
        if (_equals_1) {
          _or_1 = true;
        } else {
          URI _eObjectURI_2 = it.getEObjectURI();
          String _fileExtension = _eObjectURI_2.fileExtension();
          boolean _equals_2 = Objects.equal(_fileExtension, "mod");
          _or_1 = _equals_2;
        }
        if (_or_1) {
          _or = true;
        } else {
          URI _eObjectURI_3 = it.getEObjectURI();
          String _fileExtension_1 = _eObjectURI_3.fileExtension();
          boolean _equals_3 = Objects.equal(_fileExtension_1, "int");
          _or = _equals_3;
        }
        _and = _or;
      }
      return Boolean.valueOf(_and);
    };
    Iterable<IEObjectDescription> _filter = IterableExtensions.<IEObjectDescription>filter(_set, _function_1);
    final Function1<IEObjectDescription, Pair<URI, EClass>> _function_2 = (IEObjectDescription it) -> {
      URI _eObjectURI = it.getEObjectURI();
      EClass _eClass = it.getEClass();
      return Pair.<URI, EClass>of(_eObjectURI, _eClass);
    };
    Iterable<Pair<URI, EClass>> _map_1 = IterableExtensions.<IEObjectDescription, Pair<URI, EClass>>map(_filter, _function_2);
    Iterables.<Pair<URI, EClass>>addAll(uri2eClass, _map_1);
    return new XtumlRenameElementContext(uri2eClass);
  }
}
