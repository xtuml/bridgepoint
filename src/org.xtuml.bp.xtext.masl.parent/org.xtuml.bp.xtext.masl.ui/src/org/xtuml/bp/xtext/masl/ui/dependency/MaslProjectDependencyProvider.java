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
import org.xtuml.bp.core.common.DependencyData;
import org.xtuml.bp.xtext.masl.dependency.MaslDependencyProvider;
import org.xtuml.bp.xtext.masl.lib.MASLContainerManager;

@Singleton
@SuppressWarnings("all")
public class MaslProjectDependencyProvider {
  public final static String DEPENDENCY_FILE_EXTENSION = "int";
  
  private final static String DEPENDENCY_PREFIX = "dependency:";
  
  private Map<IProject, Set<String>> projectDependencies;
  
  private boolean reload = true;
  
  @Inject
  private MaslDependencyProvider internalDependencyProvider;
  
  private Map<URI, String> updateDependencies() {
    Map<URI, String> _xifexpression = null;
    if (this.reload) {
      Map<URI, String> _xblockexpression = null;
      {
        final HashSet<String> dependencyHandles = CollectionLiterals.<String>newHashSet();
        final HashSet<URI> dependencyUris = CollectionLiterals.<URI>newHashSet();
        final HashMap<String, Set<URI>> dependencyHandleUris = CollectionLiterals.<String, Set<URI>>newHashMap();
        final HashMap<URI, String> dependencyUriHandles = CollectionLiterals.<URI, String>newHashMap();
        HashMap<IProject, Set<String>> _newHashMap = CollectionLiterals.<IProject, Set<String>>newHashMap();
        this.projectDependencies = _newHashMap;
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
            final HashSet<String> projectDeps = CollectionLiterals.<String>newHashSet();
            this.projectDependencies.put(project, projectDeps);
            final Vector<String> dependencies = DependencyData.getDependencies(project);
            final Function1<String, Boolean> _function_1 = (String it) -> {
              return Boolean.valueOf(this.isValidDependency(it));
            };
            Iterable<String> _filter_1 = IterableExtensions.<String>filter(dependencies, _function_1);
            for (final String dependency : _filter_1) {
              {
                String _name = project.getName();
                String _plus = (_name + MASLContainerManager.CONTAINER_HANDLE_SEPARATOR);
                String _plus_1 = (_plus + MaslProjectDependencyProvider.DEPENDENCY_PREFIX);
                final String handle = (_plus_1 + dependency);
                ArrayList<String> _validDependencies = this.getValidDependencies(dependency);
                final Function1<String, URI> _function_2 = (String it) -> {
                  return URI.createFileURI(it);
                };
                List<URI> _map = ListExtensions.<String, URI>map(_validDependencies, _function_2);
                for (final URI uri : _map) {
                  boolean _add = dependencyUris.add(uri);
                  if (_add) {
                    dependencyUriHandles.put(uri, handle);
                    boolean _add_1 = dependencyHandles.add(handle);
                    if (_add_1) {
                      HashSet<URI> _newHashSet = CollectionLiterals.<URI>newHashSet(uri);
                      dependencyHandleUris.put(handle, ((Set<URI>) _newHashSet));
                    } else {
                      Set<URI> _get = dependencyHandleUris.get(handle);
                      _get.add(uri);
                    }
                  }
                }
                projectDeps.add(handle);
              }
            }
          }
        }
        _xblockexpression = this.internalDependencyProvider.setDependencies(dependencyHandles, dependencyUris, dependencyHandleUris, dependencyUriHandles);
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  private boolean isValidDependency(final String dependency) {
    boolean _xblockexpression = false;
    {
      final File dependencyFile = new File(dependency);
      boolean _or = false;
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
        _or = true;
      } else {
        boolean _and_2 = false;
        boolean _exists_1 = dependencyFile.exists();
        if (!_exists_1) {
          _and_2 = false;
        } else {
          boolean _isDirectory = dependencyFile.isDirectory();
          _and_2 = _isDirectory;
        }
        _or = _and_2;
      }
      _xblockexpression = _or;
    }
    return _xblockexpression;
  }
  
  private ArrayList<String> getValidDependencies(final String dependency) {
    ArrayList<String> _xblockexpression = null;
    {
      final ArrayList<String> validDependencies = CollectionLiterals.<String>newArrayList();
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
  
  public Set<URI> handleToUris(final String handle) {
    Set<URI> _xblockexpression = null;
    {
      this.updateDependencies();
      _xblockexpression = this.internalDependencyProvider.handleToUris(handle);
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
}
