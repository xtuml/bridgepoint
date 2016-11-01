package org.xtuml.bp.internal.tools.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.EclipseOoaofooa;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.InstanceList;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.CoreUtil;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.debug.ui.model.BPDebugTarget;
import org.xtuml.bp.debug.ui.model.BPThread;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.OoaofgraphicsBase;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class BPInstancePopulationView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	
	// The number of instance lists to show in each root
	private final int MAX_NUMBER_OF_LISTS = 25;


	/**
	 * This represents the BridgePoint type summaries that
	 * get shown under each model root.
	 *
	 */
	public enum BPTreeType {
		OoaofooaModelElements("Ooaofooa Model Elements"),
		OoaDefaultRootModelElements("Ooaofooa workspace __default_root"),		
		OoaofoogModelElements("Graphical Model Elements"),
		OogDefaultRootModelElements("Graphical workspace __default_root"),		
		ParserInstances("Parser Instances"),
		RuntimeInstances("Runtime Instances");

		private String treeType;

		private BPTreeType(String type) {
			treeType = type;
		}

		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : treeType.equals(otherName);
		}

		public String toString() {
			return this.treeType;
		}
	};
	
	/**
	 * 
	 * This class represents one of the summary items under the tree. 
	 * 
	 */
	class BPProjectTree {
		private BPTreeType treeType;  // One of the types defined above
		
		IProject parent = null;
		private List<InstanceList > summaryList = new ArrayList<InstanceList >();

		
		BPProjectTree(IProject proj, BPTreeType type) {
			treeType = type;
			parent = proj;
		}
		
		final BPTreeType getTreeType() {
			return treeType;
		}
		
		void addtoSummaryList(BPInstanceListTree element) {
			int elementPosition = -1;
			for (int i=0; i < summaryList.size()&& elementPosition==-1;i++) {
				InstanceList curElement = summaryList.get(i);
				if (curElement.getType().getName().equals(element.instanceList.getType().getName())) {
					elementPosition=i;
				}
			}
			
			InstanceList itemToInsert = element.instanceList;
			if (elementPosition>-1) {
				InstanceList itemtoUpdate = summaryList.remove(elementPosition);
				itemToInsert.addAll(itemtoUpdate);
			}
			summaryList.add(itemToInsert);
		}
		
		List<InstanceList> getSummaryList() {
			summaryList = sortInstanceLists(summaryList);
			return summaryList;
		}
	}

	List<InstanceList> sortInstanceLists(List<InstanceList> listsBySize) {
		
		Collections.sort(listsBySize, new Comparator<InstanceList>() {
			public int compare(InstanceList o1, InstanceList o2) {
				if (equals(o1, o2)) {
					return 0;
				}
				if (o1.toArray().length < o2.toArray().length) {
					return 1;
				} else {
					return -1;
				}
	
			}
	
			public boolean equals(InstanceList o1, InstanceList o2) {
				if (o1 instanceof InstanceList
						&& o1 instanceof InstanceList) {
					if (((InstanceList) o1).toArray().length == ((InstanceList) o2)
							.toArray().length) {
						return true;
					}
				}
				return false;
			}
		});
		return listsBySize;
	}
	
	
	/**
	 * Simple wrapper for a model root that also helps us track tree's parent
	 * 
	 */
	class BPModelRootTree {
		public ModelRoot modelRoot;
		public BPProjectTree treeParent;
		
		BPModelRootTree(ModelRoot root, BPProjectTree te) {
			modelRoot = root;
			treeParent = te;
		}
	}
	
	class BPInstanceListTree {
		public BPModelRootTree treeParent;
		public InstanceList instanceList;
		
		BPInstanceListTree(InstanceList list, BPModelRootTree te) {
			treeParent = te;
			instanceList = list;
		}
	}
	
	class BPSummaryTreeItem {
		public BPSummaryTree treeParent;
		public InstanceList instanceList;
		
		BPSummaryTreeItem(InstanceList list, BPSummaryTree te) {
			treeParent = te;
			instanceList = list;
		}
	}
	
	class BPSummaryTree {
		public BPProjectTree treeParent;
		public List<BPInstanceListTree> instanceLists;
		
		BPSummaryTree(BPProjectTree parent) {
			treeParent = parent;
			instanceLists = new ArrayList<BPInstanceListTree>();
		}
	}
	
	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			// nothing to change
		}

		public void dispose() {
			// nothing to dispose
		}

		/**
		 * Get the root elements of the tree. In our case, the root elements are
		 * the separate projects in the workspace
		 */
		public Object[] getElements(Object parent) {
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IProject[] allProjects = ws.getRoot().getProjects();
		    ArrayList<IProject> openProjects = new ArrayList<IProject>();
			for (int i = 0; i < allProjects.length; i++) {
				if (allProjects[i].isOpen()) {
					openProjects.add(allProjects[i]);
				}				
			}
			return openProjects.toArray(new IProject[openProjects.size()]);
		}

		public Object getParent(Object child) {
			Object result = null;
			if (child instanceof NonRootModelElement) {
				result = ((NonRootModelElement) child).getParentRoot();
			} else if (child instanceof BPProjectTree) {
				result = ((BPProjectTree)child).parent;
			} else if (child instanceof BPModelRootTree) {
				result = ((BPModelRootTree)child).treeParent;
			}else if (child instanceof BPSummaryTree) {
				result = ((BPSummaryTree)child).treeParent;
			}else if (child instanceof BPSummaryTreeItem) {
				result = ((BPSummaryTreeItem)child).treeParent;
			}
			return result;
		}

		/**
		 * Gets the children of the specified object
		 * 
		 * @param arg0
		 *            the parent object
		 * @return Object[]
		 */
		public Object[] getChildren(Object parent) {
			List<Object> result = new ArrayList<Object>();
			if (parent instanceof IProject) {
				IProject proj = (IProject)parent;
				result.add(new BPProjectTree(proj, BPTreeType.OoaofooaModelElements));
				result.add(new BPProjectTree(proj, BPTreeType.OoaDefaultRootModelElements));				
				result.add(new BPProjectTree(proj, BPTreeType.OoaofoogModelElements));
				result.add(new BPProjectTree(proj, BPTreeType.OogDefaultRootModelElements));
				result.add(new BPProjectTree(proj, BPTreeType.ParserInstances));
				result.add(new BPProjectTree(proj, BPTreeType.RuntimeInstances));
				
			} else if (parent instanceof BPProjectTree) {
				BPProjectTree projectTree = (BPProjectTree) parent;
				if (projectTree.getTreeType() == BPTreeType.OoaofooaModelElements) { // Ooa of ooa
					IProject proj = projectTree.parent;
					Ooaofooa[] ooas = EclipseOoaofooa
							.getInstancesUnderSystem(proj.getName());
					
					for (Ooaofooa ooa : ooas) {
						result.add(new BPModelRootTree(ooa, projectTree));
					}
				} else if (projectTree.getTreeType() == BPTreeType.OoaDefaultRootModelElements) { // Ooa of ooa __default_root (note while this is shown on each project, it is actually per workspace)
					Ooaofooa root = Ooaofooa.getInstance(ModelRoot.DEFAULT_WORKING_MODELSPACE);
					result.add(new BPModelRootTree(root, projectTree));
				} else if (projectTree.getTreeType() == BPTreeType.OoaofoogModelElements) { // Ooa of graphics
					IProject proj = projectTree.parent;
					// An Ooaofooa represents a model root
					Ooaofooa[] ooas = EclipseOoaofooa
							.getInstancesUnderSystem(proj.getName());

					Ooaofgraphics root = Ooaofgraphics.getInstance(ModelRoot.DEFAULT_WORKING_MODELSPACE);
					result.add(new BPModelRootTree(root, projectTree));
					for (Ooaofooa ooa : ooas) {
						Ooaofgraphics oog = Ooaofgraphics.getInstance(ooa.getId());
						result.add(new BPModelRootTree(oog, projectTree));
					}
				} else if (projectTree.getTreeType() == BPTreeType.OogDefaultRootModelElements) { // Ooa of graphics __default_root // Ooa of ooa __default_root (note while this is shown on each project, it is actually per workspace)
					Ooaofgraphics root = Ooaofgraphics.getInstance(ModelRoot.DEFAULT_WORKING_MODELSPACE);
					result.add(new BPModelRootTree(root, projectTree));
				} else if (projectTree.getTreeType() == BPTreeType.ParserInstances) {
					IProject proj = projectTree.parent;
					Ooaofooa[] ooas = EclipseOoaofooa
							.getInstancesUnderSystem(proj.getName());
					
					// Add all the system model roots in this project 
					for (Ooaofooa ooa : ooas) {
						result.add(new BPModelRootTree(ooa, projectTree));
					}
				} else if (projectTree.getTreeType() == BPTreeType.RuntimeInstances) {					
					IDebugTarget[] targets = DebugPlugin.getDefault()
							.getLaunchManager().getDebugTargets();
					for (int i = 0; i < targets.length; i++) {
						if (targets[i] instanceof BPDebugTarget) {
							BPDebugTarget target = (BPDebugTarget) targets[i];
							try {
								IThread[] threads = target.getThreads();
								for (int j = 0; j < threads.length; j++) {
									if (threads[j] instanceof BPThread) {
										ModelRoot ooa = ((BPThread) threads[j])
										.getEngine().getModelRoot();
										result.add(new BPModelRootTree(ooa, projectTree));
										break;
									}
								}
							} catch (Exception e) {
								// Should not happen
								System.out.println("Error! Exception in getChildren().  " + e.getMessage());
							}
						}
					}
				}

//
// See Redmine issue 8810 (https://support.onefact.net/issues/8810)
// Summary data is causing instance reporting AND NOT JUST SUMMARY REPORTING
// to return incorrect data after a refresh. Given a simple model (foo):
//
//	foo
//		P1
//			P1_1
//		P2
//				
// The Instance Viewer should show that model root P1 contains 2 PE_PE instances 
// and 2 EP_PKG instances, and root P2 contains 1 PE_PE instance and 1 EP_PKG
// instance. Additionally, the summary for foo should show 3 EP_PKG instances 
// and 3 PE_PE instances.
// When the model is first loaded the above IS what is shown. However, each time
// refresh is performed the first model root in the display remains correct, but
// all roots "under it" show incorrect values. What seems to be happening is that
// The values show are a summation of all the roots that appeared before the refresh.
// Because of this bug I have turned off summary data. 				
//				
// When we are ready to spend time fixing this bug, the line below can be uncommented,
// and the summary data will be enabled again: 								
//				// Add an entry for the summary to this level
//				result.add(new BPSummaryTree(projectTree));
			} else if (parent instanceof BPModelRootTree) {
				// Add all the instances from this model root to the tree.
				ModelRoot thisParent = ((BPModelRootTree) parent).modelRoot;
				Map<Class, InstanceList> iLMap = thisParent.getILMap();
				List<InstanceList> lists = new ArrayList<InstanceList>(iLMap
						.values());
				List<InstanceList> listsBySize = sortInstanceLists(lists);
				// Only look at the top MAX_NUMBER_OF_LISTS , and don't include empty lists.
				for (int i = 0; i < listsBySize.size() && i < MAX_NUMBER_OF_LISTS ; i++) {
					InstanceList list = listsBySize.get(i);
					if (list.size() == 0) {
						break;
					}
					result.add(new BPInstanceListTree(list,(BPModelRootTree)parent));
				}
			} else if (parent instanceof BPInstanceListTree) {
				// Nothing to do here, no children				
			} else if (parent instanceof BPSummaryTree) {
				BPSummaryTree thisParent = (BPSummaryTree)parent;
				List<InstanceList> unsortedLists = new ArrayList<InstanceList>();
				
				// Need to get all the children from the BPModelRootTrees of this
				// BPProjectTree.
				// go to the project tree (BPProjectTree)
				// go to all the children (BPModelRootTrees)
				// get the InstanceLists from each of these BPModelRootTrees
				// and return them here
				thisParent.treeParent.summaryList.clear();
				Object[] roots = getChildren(thisParent.treeParent);
				for (Object root: roots) {
					if (root instanceof BPModelRootTree) {
							BPModelRootTree modelRoot = (BPModelRootTree)root;
							Object[] instanceLists = getChildren(modelRoot);
							for (Object iList: instanceLists) {
								BPInstanceListTree instanceList = (BPInstanceListTree)iList;
								thisParent.treeParent.addtoSummaryList(instanceList);
							}					
					}
				}
				

				List<InstanceList> listsBySize = thisParent.treeParent.getSummaryList();
				// Only look at the top MAX_NUMBER_OF_LISTS , and don't include empty lists.
				for (int i = 0; i < listsBySize.size() && i < MAX_NUMBER_OF_LISTS ; i++) {
					InstanceList list = listsBySize.get(i);
					if (list.size() == 0) {
						break;
					}
					result.add(new BPSummaryTreeItem(list, thisParent));
				}

			} else if (parent instanceof BPSummaryTreeItem) {
				// Nothing to do here, no children				
			} else {
				// I don't think we should be here. This is just a dummy-line
				// to set a breakpoint on
				result.isEmpty();
			}
			return result.toArray();
		}

		/**
		 * Returns whether the passed object has children
		 * 
		 * @param arg0
		 *            the parent object
		 * @return boolean
		 */
		public boolean hasChildren(Object arg0) {
			// Get the children
			Object[] obj = getChildren(arg0);

			// Return whether the parent has children
			return obj == null ? false : obj.length > 0;
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
		    if (obj instanceof IProject) {
		    	return ((IProject) obj).getName();
			} else if (obj instanceof BPProjectTree) {
				BPProjectTree projTree = (BPProjectTree)obj;
				return projTree.getTreeType().toString();
			} else if (obj instanceof BPModelRootTree) {
				return ((BPModelRootTree) obj).modelRoot.getId();
		    } else if (obj instanceof BPInstanceListTree) {
				String label = ((BPInstanceListTree) obj).instanceList.getType().getName() + " - "
						+ ((BPInstanceListTree) obj).instanceList.toArray().length;
				return label;
		    } else if (obj instanceof BPSummaryTree) {
				return "Summary";
		    } else if (obj instanceof BPSummaryTreeItem) {
				String label = ((BPSummaryTreeItem) obj).instanceList.getType().getName() + " - "
				+ ((BPSummaryTreeItem) obj).instanceList.toArray().length;
		return label;
			} else {
				return "Unexpected tree element in ViewLabelProvider.getText() for \"obj.toString()\"";
			}
		}

		public Image getImage(Object obj) {
			String imageKey = "";
			if (obj instanceof IProject) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			} else if (obj instanceof BPProjectTree) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			} else if (obj instanceof BPModelRootTree) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			} else if (obj instanceof BPInstanceListTree) {
				imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			} else if (obj instanceof BPSummaryTree) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			} else if (obj instanceof BPSummaryTreeItem) {
				imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			} else {
				System.out
						.println("Unexpected tree element in ViewLabelProvider.getText() for \"obj.toString()\"");
			}

			return PlatformUI.getWorkbench().getSharedImages().getImage(
					imageKey);
		}
	}

	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public BPInstancePopulationView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(null);
		viewer.setInput(getViewSite());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				BPInstancePopulationView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				viewer.refresh();
			}
		};
		action1.setText("Update View");
		action1.setToolTipText("Update population display");
		action1.setImageDescriptor(CorePlugin.getImageDescriptor("refresh.gif"));
	}

	private void hookDoubleClickAction() {
	}

	private void showMessage(String message) {
		UIUtil.openInformation(viewer.getControl().getShell(),
				"BP Instance Population Monitor", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}