package org.xtuml.bp.debug.ui.launch;

import java.util.ArrayList;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;

public class VerifierLaunchContentProvider implements ITreeContentProvider {
  private static VerifierLaunchContentProvider instance = null;

  public Object[] getElements(Object inputElement) {
    return getChildren(inputElement);
  }

  public static VerifierLaunchContentProvider instance() {
    if (instance == null) {
      instance = new VerifierLaunchContentProvider();
    }
    return instance;
  }
  
  public void dispose() {
  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }

  public Object[] getChildren(Object parentElement) {
    org.xtuml.bp.ui.explorer.ModelContentProvider explorerProvider = new org.xtuml.bp.ui.explorer.ModelContentProvider();
    if (parentElement instanceof Ooaofooa) {
      Object[] children = explorerProvider.getChildren(parentElement);
      ArrayList<NonRootModelElement> filteredList = getFilteredRootChildList((SystemModel_c[]) children);
      return filteredList.toArray();
    } else if (parentElement instanceof IWorkspaceRoot) {
      return new Ooaofooa[] { Ooaofooa.getDefaultInstance() };
    } else if (parentElement instanceof SystemModel_c) {
      Object[] children = explorerProvider.getChildren(parentElement);
      ArrayList<NonRootModelElement> filteredList = getFilteredSystemChildList(children);
      return filteredList.toArray();
    } else if (parentElement instanceof Package_c) {
      Object[] children = explorerProvider.getChildren(parentElement);
      ArrayList<NonRootModelElement> filteredChildren = getFilteredPackageChildList(children);
      return filteredChildren.toArray();
    } else if (parentElement instanceof Component_c) {
      Object[] children = explorerProvider.getChildren(parentElement);
      ArrayList<NonRootModelElement> filteredChildren = getFilteredComponentChildList(children);
      return filteredChildren.toArray();
    }
    return null;
  }

  private ArrayList<NonRootModelElement> getFilteredComponentChildList(
      Object[] children) {
    ArrayList<NonRootModelElement> filteredList = new ArrayList<NonRootModelElement>();
    for (int i = 0; i < children.length; i++) {
      // here we filter out components that
      // are not formal
      if (children[i] instanceof Component_c) {
        filteredList.add((NonRootModelElement) children[i]);
      } else if (children[i] instanceof ComponentReference_c) {
        if (((ComponentReference_c) children[i]).Isassigned())
          filteredList.add((NonRootModelElement) children[i]);
      }
    }
    return filteredList;
  }

  private ArrayList<NonRootModelElement> getFilteredRootChildList(
      SystemModel_c[] systems) {
    ArrayList<NonRootModelElement> filteredList = new ArrayList<NonRootModelElement>();
    for (int i = 0; i < systems.length; i++) {
      if (hasChildren(systems[i])) {
        filteredList.add(systems[i]);
      }
    }
    return filteredList;
  }

  private ArrayList<NonRootModelElement> getFilteredPackageChildList(
      Object[] children) {
    ArrayList<NonRootModelElement> filteredChildren = new ArrayList<NonRootModelElement>();
    for (int i = 0; i < children.length; i++) {
      // here we filter out components that
      // are not formal
      if (children[i] instanceof Component_c) {
        filteredChildren.add((NonRootModelElement) children[i]);
      } else if (children[i] instanceof ComponentReference_c) {
        if (((ComponentReference_c) children[i]).Isassigned())
          filteredChildren.add((NonRootModelElement) children[i]);
      } else if (children[i] instanceof Package_c) {
        if (((Package_c) children[i]).Isexecutingorownsexecutableelements())
          filteredChildren.add((NonRootModelElement) children[i]);
      }
    }
    return filteredChildren;
  }

  private ArrayList<NonRootModelElement> getFilteredComponentPackageChildList(
      Object[] children) {
    ArrayList<NonRootModelElement> filteredChildren = new ArrayList<NonRootModelElement>();
    org.xtuml.bp.ui.explorer.ModelContentProvider explorerProvider = new org.xtuml.bp.ui.explorer.ModelContentProvider();
    for (int i = 0; i < children.length; i++) {
      // here we filter out components that
      // are not formal
      if (children[i] instanceof Component_c) {
        filteredChildren.add((NonRootModelElement) children[i]);
      } else if (children[i] instanceof ComponentReference_c) {
        if (((ComponentReference_c) children[i]).Isassigned())
          filteredChildren.add((NonRootModelElement) children[i]);
      } else if (children[i] instanceof Package_c) {
        if (((Package_c) children[i]).Isexecutingorownsexecutableelements())
          filteredChildren.add((NonRootModelElement) children[i]);
      }
    }
    return filteredChildren;
  }

  private ArrayList<NonRootModelElement> getFilteredSystemChildList(
      Object[] children) {
    ArrayList<NonRootModelElement> filteredList = new ArrayList<NonRootModelElement>();
    org.xtuml.bp.ui.explorer.ModelContentProvider explorerProvider = new org.xtuml.bp.ui.explorer.ModelContentProvider();
    for (int i = 0; i < children.length; i++) {
      // here we filter everything but domains,
      // packages and component packages
      if (children[i] instanceof Package_c) {
        if (((Package_c) children[i]).Isexecutingorownsexecutableelements()) {
          filteredList.add((NonRootModelElement) children[i]);
        }
      }    }
    return filteredList;
  }

  public Object getParent(Object element) {
    if (element instanceof Ooaofooa) {
      return ResourcesPlugin.getWorkspace().getRoot();
    } else if (element instanceof SystemModel_c) {
      return Ooaofooa.getDefaultInstance();
    } else if (element instanceof Component_c) {
      Package_c parentPkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
          .getOnePE_PEOnR8001((Component_c) element));
      if (parentPkg != null) {
        return parentPkg;
      }
      Component_c parentComp = Component_c
          .getOneC_COnR8003(PackageableElement_c
              .getOnePE_PEOnR8001((Component_c) element));
      if (parentComp != null) {
        return parentComp;
      }
    } else if (element instanceof ComponentReference_c) {
      Package_c parentPkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
          .getOnePE_PEOnR8001((ComponentReference_c) element));
      if (parentPkg != null) {
        return parentPkg;
      }
      Component_c parentComp = Component_c
          .getOneC_COnR8003(PackageableElement_c
              .getOnePE_PEOnR8001((ComponentReference_c) element));
      if (parentComp != null) {
        return parentComp;
      }
    } else if (element instanceof Package_c) {
      Package_c parent = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
          .getOnePE_PEOnR8001((Package_c) element));
      if (parent != null) {
        return parent;
      }
      Component_c comp = Component_c.getOneC_COnR8003(PackageableElement_c
          .getOnePE_PEOnR8001((Package_c) element));
      if (comp != null) {
        return comp;
      }
      SystemModel_c system = SystemModel_c
          .getOneS_SYSOnR1401((Package_c) element);
      if (system != null) {
        return system;
      }
    }
    return null;
  }

  public boolean hasChildren(Object element) {
    org.xtuml.bp.ui.explorer.ModelContentProvider provider = new org.xtuml.bp.ui.explorer.ModelContentProvider();
    if (element instanceof Ooaofooa) {
      SystemModel_c[] systems = SystemModel_c.SystemModelInstances(Ooaofooa
          .getDefaultInstance());
      ArrayList<NonRootModelElement> filteredList = getFilteredRootChildList(systems);
      return filteredList.size() != 0;
    } else if (element instanceof SystemModel_c) {
      Object[] children = provider.getChildren(element);
      return getFilteredSystemChildList(children).size() != 0;
    } else if (element instanceof IWorkspaceRoot) {
      return true;
    } else if (element instanceof Package_c) {
        Object[] children = provider.getChildren(element);
        return getFilteredPackageChildList(children).size() != 0;
    } else if (element instanceof Component_c) {
      Object[] children = provider.getChildren(element);
      return getFilteredComponentChildList(children).size() != 0;
    }
    return false;
  }
}
