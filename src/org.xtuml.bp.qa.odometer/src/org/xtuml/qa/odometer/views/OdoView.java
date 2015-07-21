package org.xtuml.qa.odometer.views;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.TimeZone;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.qa.odometer.timer.OdoTimer;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class OdoView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.xtuml.qa.odometer.views.OdoView";

	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Date elapsed = new Date(0);
    private static OdoView self = null;
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
			String formattedDate = formatDate(elapsed);
			return new String[] { " Elapsed time: " + formattedDate };
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
	public OdoView() {
		self = this;
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
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "org.xtuml.qa.odometer.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		show(OdoTimer.getMillitime());
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				OdoView.this.fillContextMenu(manager);
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
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				if (OdoTimer.getState() == OdoTimer.State.DISABLED) {
					setActionToDisableCapture();
				}
				else {
					setActionToEnableCapture();
				}
			}
		};
		setActionToDisableCapture();
		
		action2 = new Action() {
			public void run() {
              Clipboard clip = new Clipboard(Display.getCurrent());
			  String todaysDate =  new Date().toString();
			  String hostName = getHostName();
			  String workSpLoc = getEclipseVar("eclipse_home");
			  String[] aboutMappings = loadAboutMappings();
			  String release = "Unknown";
			  String buildId = "Unknown";
			  if (aboutMappings.length > 0) {
			    release = aboutMappings[0];
			    if (aboutMappings.length > 1) {
			      buildId = aboutMappings[1];
			    }
			  }
			  String op = hostName + ", " + workSpLoc + ", " + release + ", "+ buildId + ", "  + todaysDate + ", " + formatDate(elapsed);
              clip.setContents(new Object[] {op}, new Transfer[]{TextTransfer.getInstance()});
			}
		};
		action2.setText("Copy to clipboard");
		action2.setToolTipText("Transfer elspsed time to clipboard");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private String formatDate(Date toFormat) {
	  SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
	  String formattedMinsAndSecs = formatter.format(toFormat);
	  Calendar cal = Calendar.getInstance(Locale.US);
	  cal.setTimeZone(TimeZone.getTimeZone("UTC"));
	  cal.setTime(elapsed);
      int days = cal.get(Calendar.DAY_OF_YEAR) - 1;
      String formattedDay = Integer.toString(days);
      while (formattedDay.length() < 3) {
    	  formattedDay = "0" + formattedDay;
      }
      int hours = cal.get(Calendar.HOUR_OF_DAY);
      String formattedHour = Integer.toString(hours);
      while (formattedHour.length() < 2) {
    	  formattedHour = "0" + formattedHour;
      }
      return formattedDay + ":" + formattedHour + ":" + formattedMinsAndSecs;
	}
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		UIUtil.openInformation(
			viewer.getControl().getShell(),
			"BridgePoint Odometer",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public static void show(long millitime) {
		if (self != null) {			
		  self.elapsed.setTime(millitime);
		  if (!self.viewer.getControl().isDisposed()) {
		    self.viewer.refresh();
		  }
		}
	}
	
	public void setActionToEnableCapture() {
	    action1.setText("Start Odometer");
	    action1.setToolTipText("Start capturing activity");
	    action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
	      getImageDescriptor(ISharedImages.IMG_OBJS_ERROR_TSK));
	    OdoTimer.setState(OdoTimer.State.DISABLED);
	}

	  private void setActionToDisableCapture() {
	    action1.setText("Stop Odometer");
	    action1.setToolTipText("Stop capturing activity");
	    action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
	        getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
	    OdoTimer.setState(OdoTimer.State.STOPPED);
	  }

	  private String getHostName() {
		  try {
			   InetAddress addr = InetAddress.getLocalHost();
			   return addr.getHostName();
			} catch (UnknownHostException e) {
			  return e.toString();
			}
	}
		public static String getEclipseVar(String varName) {
			try {
			  return VariablesPlugin.getDefault().getStringVariableManager().
			                    performStringSubstitution("${" + varName + "}");
			}
			catch (CoreException ce){
				CorePlugin.logError(
					    "Error obtaining the value of eclipse variable, '" +
	                                                         varName +"'.", ce);			
			}
			return null;
		}
		
		final static String ABOUT_MAPPINGS = "../org.xtuml.bp.pkg/about.mappings";

		private static String[] loadAboutMappings() {
			URL location = FileLocator.find(CorePlugin.getDefault().getBundle(),
		          new Path(ABOUT_MAPPINGS), null);
			PropertyResourceBundle bundle = null;
			if (location != null) {
				InputStream is = null;
				try {
					is = location.openStream();
					bundle = new PropertyResourceBundle(is);
				} catch (IOException e) {
					// Nothing we can do here
				} finally {
					try {
						if (is != null)
							is.close();
					} catch (IOException e) {
						// do nothing if we fail to close
					}
				}
			}

			ArrayList<String> mappingsList = new ArrayList<String>(3);
			if (bundle != null) {
				for (int i = 0; ;++i) {
					try {				
		mappingsList.add(bundle.getString(Integer.toString(i)));
					} catch (MissingResourceException e) {
						break; // No more keys!
					}
				}
			}

			String[] result = new String[mappingsList.size()];
			mappingsList.toArray(result);
			return result;
		}

}