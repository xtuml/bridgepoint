package com.mentor.nucleus.qa.odometer.tracker;

import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

public class OdoWindowListener implements IWindowListener {

	@Override
	public void windowActivated(IWorkbenchWindow window) {
		OdoWindowListener.setUp(window);
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
		OdoWindowListener.tearDown(window);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		OdoWindowListener.tearDown(window);
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
		OdoWindowListener.setUp(window);
	}

	public static void setUp (IWorkbenchWindow window) {
		IPartService partService = (IPartService)window.getService(IPartService.class);
		partService.addPartListener(OdoPartListener.getInstance());
		ISelectionService selService = (ISelectionService)window.getService(ISelectionService.class);
		selService.addSelectionListener(OdoSelectionChangedListener.getInstance());
	}
	
	public static void tearDown(IWorkbenchWindow window) {
		IPartService partService = (IPartService)window.getService(IPartService.class);
		partService.removePartListener(OdoPartListener.getInstance());
		ISelectionService selService = (ISelectionService)window.getService(ISelectionService.class);
		selService.removeSelectionListener(OdoSelectionChangedListener.getInstance());
	}

	static OdoWindowListener self = null;
	public static IWindowListener getInstance() {
		if (self == null) {
			self = new OdoWindowListener();
		}
		return self;
	}

}
