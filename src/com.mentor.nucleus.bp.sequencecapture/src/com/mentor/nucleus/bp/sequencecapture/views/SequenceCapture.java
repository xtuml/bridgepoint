package com.mentor.nucleus.bp.sequencecapture.views;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.ui.part.ViewPart;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.sequencecapture.SequenceBuilder;


public class SequenceCapture extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.mentor.nucleus.bp.sequencecapture.views.SequenceCapture";

	private TableViewer viewer;
	private Action controlCapture;
  private Action linkWithView;
  private Action createTiming;
  private Action doubleClickAction;
  private static boolean capturing = false;
  private static SequenceCapture instance = null;
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public SequenceCapture() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "com.mentor.nucleus.bp.sequencecapture.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		instance = this;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SequenceCapture.this.fillContextMenu(manager);
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
		manager.add(controlCapture);
    manager.add(getLinkWithView());
    manager.add(getCreateTiming());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(controlCapture);
    manager.add(getLinkWithView());
    manager.add(getCreateTiming());
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(controlCapture);
		manager.add(getLinkWithView());
    manager.add(getCreateTiming());
	}

	private void makeActions() {
	  controlCapture = new Action() {
			public void run() {
			  if(getText().equals("Start Sequence Capture")) { 
			    setActionToDisableCapture();
			  }
			  else {
	        SequenceBuilder.setCurrentSequence(null);
	        setActionToEnableCapture();
			  }
			}
		};
		setLinkWithView(new Action("Link with captured diagram", Action.AS_CHECK_BOX) {
		  public void run() {
		  }
		});
		getLinkWithView().setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
	      getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
		getLinkWithView().setChecked(true);
    setCreateTiming(new Action("Capture timing information", Action.AS_CHECK_BOX) {
      public void run() {
      }
    });
    getCreateTiming().setImageDescriptor(CorePlugin.getImageDescriptor("newtimingmark.gif"));
		setActionToEnableCapture();
	}

	public void setActionToEnableCapture() {
    controlCapture.setText("Start Sequence Capture");
    controlCapture.setToolTipText("Start capturing activity on a new sequence diagram");
    controlCapture.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
      getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
    setCapturing(false);
	}

  private void setActionToDisableCapture() {
    controlCapture.setText("Stop Sequence Capture");
    controlCapture.setToolTipText("Stop capturing activity for sequence diagram");
    controlCapture.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
        getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
    setCapturing(true);
  }

  private void hookDoubleClickAction() {
    viewer.addDoubleClickListener(new IDoubleClickListener() {
      public void doubleClick(DoubleClickEvent event) {
        doubleClickAction.run();
      }
    });
  }

  public static SequenceCapture getInstance() {
    return instance;
  }
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

  public static void setCapturing(boolean capturing) {
    SequenceCapture.capturing = capturing;
  }

  public static boolean isCapturing() {
    return capturing;
  }

  private void setLinkWithView(Action linkWithView) {
    this.linkWithView = linkWithView;
  }

  public Action getLinkWithView() {
    return linkWithView;
  }

  private void setCreateTiming(Action createTiming) {
    this.createTiming = createTiming;
  }

  public Action getCreateTiming() {
    return createTiming;
  }
}