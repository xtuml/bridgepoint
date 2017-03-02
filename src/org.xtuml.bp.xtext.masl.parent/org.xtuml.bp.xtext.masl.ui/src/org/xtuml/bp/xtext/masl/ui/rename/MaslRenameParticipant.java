package org.xtuml.bp.xtext.masl.ui.rename;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
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
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
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
  
  private List<IProject> projects;
  
  private List<EClass> eClasses;
  
  private List<QualifiedName> oldQNames;
  
  private String newName;
  
  @Override
  public IStatus renameElement(final NonRootModelElement xtumlElement, final String newName, final String oldName) {
    List<IProject> _projects = this.getProjects(xtumlElement);
    this.projects = _projects;
    this.newName = newName;
    List<EClass> _maslEClasses = this._xtumlToMaslMapper.getMaslEClasses(xtumlElement);
    this.eClasses = _maslEClasses;
    boolean _isEmpty = this.eClasses.isEmpty();
    if (_isEmpty) {
      return Status.CANCEL_STATUS;
    }
    List<QualifiedName> _maslQualifiedNames = this._xtumlToMaslMapper.getMaslQualifiedNames(xtumlElement, oldName);
    this.oldQNames = _maslQualifiedNames;
    boolean _or = false;
    boolean _equals = Objects.equal(this.oldQNames, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _isEmpty_1 = this.oldQNames.isEmpty();
      _or = _isEmpty_1;
    }
    if (_or) {
      return Status.CANCEL_STATUS;
    }
    final XtumlRenameElementContext renameElementContext = this.getRenameElementContext(this.eClasses, this.oldQNames, this.projects);
    boolean _isEmpty_2 = renameElementContext.isEmpty();
    if (_isEmpty_2) {
      return Status.CANCEL_STATUS;
    }
    return this.executor.doRename(renameElementContext, newName);
  }
  
  private XtumlRenameElementContext getRenameElementContext(final List<EClass> eClasses, final List<QualifiedName> names, final List<IProject> projects) {
    final ArrayList<Pair<URI, EClass>> uri2eClass = CollectionLiterals.<Pair<URI, EClass>>newArrayList();
    final Function1<EClass, Iterable<IEObjectDescription>> _function = (EClass eClass) -> {
      final Function1<IProject, Iterable<IEObjectDescription>> _function_1 = (IProject it) -> {
        Iterable<IEObjectDescription> _xblockexpression = null;
        {
          IProject _project = it.getProject();
          ResourceSet _get = this.rsp.get(_project);
          final IResourceDescriptions index = this.rdp.getResourceDescriptions(_get);
          final Function1<QualifiedName, Iterable<IEObjectDescription>> _function_2 = (QualifiedName name) -> {
            return index.getExportedObjects(((EClass) eClass), name, false);
          };
          List<Iterable<IEObjectDescription>> _map = ListExtensions.<QualifiedName, Iterable<IEObjectDescription>>map(names, _function_2);
          _xblockexpression = Iterables.<IEObjectDescription>concat(_map);
        }
        return _xblockexpression;
      };
      List<Iterable<IEObjectDescription>> _map = ListExtensions.<IProject, Iterable<IEObjectDescription>>map(projects, _function_1);
      return Iterables.<IEObjectDescription>concat(_map);
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
  
  private List<IProject> getProjects(final NonRootModelElement xtumlElement) {
    List<IProject> _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof InterfaceOperation_c) {
        _matched=true;
        ArrayList<IProject> _xblockexpression = null;
        {
          final ArrayList<IProject> projects = CollectionLiterals.<IProject>newArrayList();
          ExecutableProperty_c _oneC_EPOnR4004 = ExecutableProperty_c.getOneC_EPOnR4004(((InterfaceOperation_c)xtumlElement));
          RequiredExecutableProperty_c[] _manySPR_REPsOnR4500 = RequiredExecutableProperty_c.getManySPR_REPsOnR4500(_oneC_EPOnR4004);
          RequiredOperation_c[] _manySPR_ROsOnR4502 = RequiredOperation_c.getManySPR_ROsOnR4502(_manySPR_REPsOnR4500);
          final Function1<RequiredOperation_c, IProject> _function = (RequiredOperation_c spr_ro) -> {
            IFile _file = spr_ro.getFile();
            return _file.getProject();
          };
          List<IProject> _map = ListExtensions.<RequiredOperation_c, IProject>map(((List<RequiredOperation_c>)Conversions.doWrapArray(_manySPR_ROsOnR4502)), _function);
          Iterables.<IProject>addAll(projects, _map);
          ExecutableProperty_c _oneC_EPOnR4004_1 = ExecutableProperty_c.getOneC_EPOnR4004(((InterfaceOperation_c)xtumlElement));
          ProvidedExecutableProperty_c[] _manySPR_PEPsOnR4501 = ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(_oneC_EPOnR4004_1);
          ProvidedOperation_c[] _manySPR_POsOnR4503 = ProvidedOperation_c.getManySPR_POsOnR4503(_manySPR_PEPsOnR4501);
          final Function1<ProvidedOperation_c, IProject> _function_1 = (ProvidedOperation_c spr_po) -> {
            IFile _file = spr_po.getFile();
            return _file.getProject();
          };
          List<IProject> _map_1 = ListExtensions.<ProvidedOperation_c, IProject>map(((List<ProvidedOperation_c>)Conversions.doWrapArray(_manySPR_POsOnR4503)), _function_1);
          Iterables.<IProject>addAll(projects, _map_1);
          _xblockexpression = projects;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof PropertyParameter_c) {
        _matched=true;
        ArrayList<IProject> _xblockexpression = null;
        {
          final ArrayList<IProject> projects = CollectionLiterals.<IProject>newArrayList();
          ExecutableProperty_c _oneC_EPOnR4006 = ExecutableProperty_c.getOneC_EPOnR4006(((PropertyParameter_c)xtumlElement));
          RequiredExecutableProperty_c[] _manySPR_REPsOnR4500 = RequiredExecutableProperty_c.getManySPR_REPsOnR4500(_oneC_EPOnR4006);
          RequiredOperation_c[] _manySPR_ROsOnR4502 = RequiredOperation_c.getManySPR_ROsOnR4502(_manySPR_REPsOnR4500);
          final Function1<RequiredOperation_c, IProject> _function = (RequiredOperation_c spr_ro) -> {
            IFile _file = spr_ro.getFile();
            return _file.getProject();
          };
          List<IProject> _map = ListExtensions.<RequiredOperation_c, IProject>map(((List<RequiredOperation_c>)Conversions.doWrapArray(_manySPR_ROsOnR4502)), _function);
          Iterables.<IProject>addAll(projects, _map);
          ExecutableProperty_c _oneC_EPOnR4006_1 = ExecutableProperty_c.getOneC_EPOnR4006(((PropertyParameter_c)xtumlElement));
          ProvidedExecutableProperty_c[] _manySPR_PEPsOnR4501 = ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(_oneC_EPOnR4006_1);
          ProvidedOperation_c[] _manySPR_POsOnR4503 = ProvidedOperation_c.getManySPR_POsOnR4503(_manySPR_PEPsOnR4501);
          final Function1<ProvidedOperation_c, IProject> _function_1 = (ProvidedOperation_c spr_po) -> {
            IFile _file = spr_po.getFile();
            return _file.getProject();
          };
          List<IProject> _map_1 = ListExtensions.<ProvidedOperation_c, IProject>map(((List<ProvidedOperation_c>)Conversions.doWrapArray(_manySPR_POsOnR4503)), _function_1);
          Iterables.<IProject>addAll(projects, _map_1);
          _xblockexpression = projects;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      IFile _file = xtumlElement.getFile();
      IProject _project = _file.getProject();
      _switchResult = Collections.<IProject>unmodifiableList(CollectionLiterals.<IProject>newArrayList(_project));
    }
    return _switchResult;
  }
}
