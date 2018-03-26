package org.xtuml.bp.xtext.masl.ui.dependency;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.IDependencyProvider;
import org.xtuml.bp.xtext.masl.dependency.MaslDependencyProvider;
import org.xtuml.bp.xtext.masl.lib.MASLContainerManager;

@Singleton
@SuppressWarnings("all")
public class MaslProjectDependencyProvider {
  private final static Logger LOG = Logger.getLogger(MaslProjectDependencyProvider.class);
  
  public final static String DEPENDENCY_FILE_EXTENSION = "int";
  
  public final static String DEPENDENCY_PREFIX = "dependency:";
  
  private Map<IProject, Set<String>> projectDependencies;
  
  private Map<String, Set<IProject>> referringProjects;
  
  private Map<String, Set<String>> dependencyDependencies;
  
  private Map<IProject, Integer> dependencyVersions;
  
  @Inject
  private MaslDependencyProvider internalDependencyProvider;
  
  private Map<URI, String> updateDependencies() {
    Map<URI, String> _xblockexpression = null;
    {
      final Set<String> dependencyHandles = this.internalDependencyProvider.getDependencyHandles();
      final Set<URI> dependencyUris = this.internalDependencyProvider.getDependencyUris();
      final Map<String, URI> dependencyHandleUris = this.internalDependencyProvider.getDependencyHandleUris();
      final Map<URI, String> dependencyUriHandles = this.internalDependencyProvider.getDependencyUriHandles();
      boolean _equals = Objects.equal(null, this.projectDependencies);
      if (_equals) {
        HashMap<IProject, Set<String>> _newHashMap = CollectionLiterals.<IProject, Set<String>>newHashMap();
        this.projectDependencies = _newHashMap;
      }
      boolean _equals_1 = Objects.equal(null, this.referringProjects);
      if (_equals_1) {
        HashMap<String, Set<IProject>> _newHashMap_1 = CollectionLiterals.<String, Set<IProject>>newHashMap();
        this.referringProjects = _newHashMap_1;
      }
      boolean _equals_2 = Objects.equal(null, this.dependencyDependencies);
      if (_equals_2) {
        HashMap<String, Set<String>> _newHashMap_2 = CollectionLiterals.<String, Set<String>>newHashMap();
        this.dependencyDependencies = _newHashMap_2;
      }
      boolean _equals_3 = Objects.equal(null, this.dependencyVersions);
      if (_equals_3) {
        HashMap<IProject, Integer> _newHashMap_3 = CollectionLiterals.<IProject, Integer>newHashMap();
        this.dependencyVersions = _newHashMap_3;
      }
      boolean rebuilt = false;
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceRoot _root = _workspace.getRoot();
      IProject[] _projects = _root.getProjects();
      final Function1<IProject, Boolean> _function = (IProject it) -> {
        try {
          boolean _and = false;
          boolean _isOpen = it.isOpen();
          if (!_isOpen) {
            _and = false;
          } else {
            boolean _hasNature = it.hasNature(XtextProjectHelper.NATURE_ID);
            _and = _hasNature;
          }
          return Boolean.valueOf(_and);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      Iterable<IProject> _filter = IterableExtensions.<IProject>filter(((Iterable<IProject>)Conversions.doWrapArray(_projects)), _function);
      for (final IProject project : _filter) {
        {
          boolean _containsKey = this.dependencyVersions.containsKey(project);
          boolean _not = (!_containsKey);
          if (_not) {
            this.dependencyVersions.put(project, Integer.valueOf((-1)));
          }
          IDependencyProvider _defaultDependencyProvider = CorePlugin.getDefaultDependencyProvider();
          Integer _get = this.dependencyVersions.get(project);
          final int dependencyVersion = _defaultDependencyProvider.dependenciesChanged(project, (_get).intValue());
          if ((dependencyVersion > 0)) {
            rebuilt = true;
            this.dependencyVersions.put(project, Integer.valueOf(dependencyVersion));
            final HashSet<String> projectDeps = CollectionLiterals.<String>newHashSet();
            final Set<String> oldProjectDeps = this.projectDependencies.put(project, projectDeps);
            boolean _notEquals = (!Objects.equal(null, oldProjectDeps));
            if (_notEquals) {
              for (final String oldProjectDep : oldProjectDeps) {
                {
                  final String oldHandle = (MaslProjectDependencyProvider.DEPENDENCY_PREFIX + oldProjectDep);
                  final URI oldUri = URI.createFileURI(oldProjectDep);
                  final Set<IProject> referringProjs = this.referringProjects.get(oldProjectDep);
                  referringProjs.remove(project);
                  boolean _isEmpty = referringProjs.isEmpty();
                  if (_isEmpty) {
                    dependencyHandles.remove(oldHandle);
                    dependencyHandleUris.remove(oldHandle);
                    dependencyUris.remove(oldUri);
                    dependencyUriHandles.remove(oldUri);
                  }
                }
              }
            }
            ArrayList<String> _validDependencies = this.getValidDependencies(project);
            for (final String dependency : _validDependencies) {
              {
                final String handle = (MaslProjectDependencyProvider.DEPENDENCY_PREFIX + dependency);
                final URI uri = URI.createFileURI(dependency);
                dependencyHandles.add(handle);
                dependencyHandleUris.put(handle, uri);
                dependencyUris.add(uri);
                dependencyUriHandles.put(uri, handle);
                projectDeps.add(handle);
                boolean _containsKey_1 = this.referringProjects.containsKey(handle);
                if (_containsKey_1) {
                  Set<IProject> _get_1 = this.referringProjects.get(handle);
                  _get_1.add(project);
                } else {
                  HashSet<IProject> _newHashSet = CollectionLiterals.<IProject>newHashSet(project);
                  this.referringProjects.put(handle, ((Set<IProject>) _newHashSet));
                }
              }
            }
          }
        }
      }
      if (rebuilt) {
        for (final String handle : dependencyHandles) {
          {
            final HashSet<String> depDeps = CollectionLiterals.<String>newHashSet(handle);
            boolean _containsKey = this.referringProjects.containsKey(handle);
            if (_containsKey) {
              Set<IProject> _get = this.referringProjects.get(handle);
              for (final IProject referringProj : _get) {
                boolean _containsKey_1 = this.projectDependencies.containsKey(referringProj);
                if (_containsKey_1) {
                  Set<String> _get_1 = this.projectDependencies.get(referringProj);
                  for (final String dep : _get_1) {
                    final Function1<String, String> _function_1 = (String it) -> {
                      URI _handleToUri = this.internalDependencyProvider.handleToUri(it);
                      return _handleToUri.lastSegment();
                    };
                    Iterable<String> _map = IterableExtensions.<String, String>map(depDeps, _function_1);
                    Set<String> _set = IterableExtensions.<String>toSet(_map);
                    URI _handleToUri = this.internalDependencyProvider.handleToUri(dep);
                    String _lastSegment = _handleToUri.lastSegment();
                    boolean _contains = _set.contains(_lastSegment);
                    boolean _not = (!_contains);
                    if (_not) {
                      depDeps.add(dep);
                    } else {
                      boolean _contains_1 = depDeps.contains(dep);
                      boolean _not_1 = (!_contains_1);
                      if (_not_1) {
                        URI _handleToUri_1 = this.internalDependencyProvider.handleToUri(handle);
                        String _plus = ("Ignoring duplicate dependency for \'" + _handleToUri_1);
                        String _plus_1 = (_plus + "\' at: ");
                        URI _handleToUri_2 = this.internalDependencyProvider.handleToUri(dep);
                        String _plus_2 = (_plus_1 + _handleToUri_2);
                        MaslProjectDependencyProvider.LOG.warn(_plus_2);
                      }
                    }
                  }
                }
              }
            }
            this.dependencyDependencies.put(handle, depDeps);
          }
        }
      }
      _xblockexpression = this.internalDependencyProvider.setDependencies(dependencyHandles, dependencyUris, dependencyHandleUris, dependencyUriHandles);
    }
    return _xblockexpression;
  }
  
  private ArrayList<String> getValidDependencies(final IProject project) {
    ArrayList<String> _xblockexpression = null;
    {
      final ArrayList<String> validDependencies = CollectionLiterals.<String>newArrayList();
      IDependencyProvider _defaultDependencyProvider = CorePlugin.getDefaultDependencyProvider();
      Vector<String> _dependencies = _defaultDependencyProvider.getDependencies(project);
      for (final String dependency : _dependencies) {
        {
          final File dependencyFile = new File(dependency);
          boolean _and = false;
          boolean _and_1 = false;
          boolean _exists = dependencyFile.exists();
          if (!_exists) {
            _and_1 = false;
          } else {
            boolean _isFile = dependencyFile.isFile();
            _and_1 = _isFile;
          }
          if (!_and_1) {
            _and = false;
          } else {
            boolean _endsWith = dependency.endsWith(("." + MaslProjectDependencyProvider.DEPENDENCY_FILE_EXTENSION));
            _and = _endsWith;
          }
          if (_and) {
            validDependencies.add(dependency);
          } else {
            boolean _and_2 = false;
            boolean _exists_1 = dependencyFile.exists();
            if (!_exists_1) {
              _and_2 = false;
            } else {
              boolean _isDirectory = dependencyFile.isDirectory();
              _and_2 = _isDirectory;
            }
            if (_and_2) {
              File[] _listFiles = dependencyFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(final File pathname) {
                  String _name = pathname.getName();
                  return _name.endsWith(("." + MaslProjectDependencyProvider.DEPENDENCY_FILE_EXTENSION));
                }
              });
              final Function1<File, String> _function = (File it) -> {
                return it.getAbsolutePath();
              };
              List<String> _map = ListExtensions.<File, String>map(((List<File>)Conversions.doWrapArray(_listFiles)), _function);
              Iterables.<String>addAll(validDependencies, _map);
            }
          }
        }
      }
      _xblockexpression = validDependencies;
    }
    return _xblockexpression;
  }
  
  public Set<String> getDependencyHandles() {
    Set<String> _xblockexpression = null;
    {
      this.updateDependencies();
      _xblockexpression = this.internalDependencyProvider.getDependencyHandles();
    }
    return _xblockexpression;
  }
  
  public Set<URI> getDependencyUris() {
    Set<URI> _xblockexpression = null;
    {
      this.updateDependencies();
      _xblockexpression = this.internalDependencyProvider.getDependencyUris();
    }
    return _xblockexpression;
  }
  
  public String uriToHandle(final URI uri) {
    String _xblockexpression = null;
    {
      this.updateDependencies();
      _xblockexpression = this.internalDependencyProvider.uriToHandle(uri);
    }
    return _xblockexpression;
  }
  
  public URI handleToUri(final String handle) {
    URI _xblockexpression = null;
    {
      this.updateDependencies();
      _xblockexpression = this.internalDependencyProvider.handleToUri(handle);
    }
    return _xblockexpression;
  }
  
  public Set<String> getProjectDependencies(final String containerHandle) {
    Set<String> _xblockexpression = null;
    {
      this.updateDependencies();
      String[] _split = containerHandle.split(MASLContainerManager.CONTAINER_HANDLE_SEPARATOR);
      final String projectName = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(_split)));
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceRoot _root = _workspace.getRoot();
      IProject[] _projects = _root.getProjects();
      final Function1<IProject, Boolean> _function = (IProject it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, projectName));
      };
      Iterable<IProject> _filter = IterableExtensions.<IProject>filter(((Iterable<IProject>)Conversions.doWrapArray(_projects)), _function);
      final IProject project = IterableExtensions.<IProject>head(_filter);
      Set<String> _xifexpression = null;
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(null, project));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _containsKey = this.projectDependencies.containsKey(project);
        _and = _containsKey;
      }
      if (_and) {
        _xifexpression = this.projectDependencies.get(project);
      } else {
        _xifexpression = CollectionLiterals.<String>newHashSet();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public Set<String> getDependencyDependencies(final String containerHandle) {
    Set<String> _xblockexpression = null;
    {
      this.updateDependencies();
      Set<String> _xifexpression = null;
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(null, containerHandle));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _containsKey = this.dependencyDependencies.containsKey(containerHandle);
        _and = _containsKey;
      }
      if (_and) {
        _xifexpression = this.dependencyDependencies.get(containerHandle);
      } else {
        _xifexpression = CollectionLiterals.<String>newHashSet();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
