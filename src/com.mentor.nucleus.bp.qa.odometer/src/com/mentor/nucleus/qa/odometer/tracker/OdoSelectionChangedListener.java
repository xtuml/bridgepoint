package com.mentor.nucleus.qa.odometer.tracker;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.mentor.nucleus.qa.odometer.timer.OdoTimer;

public class OdoSelectionChangedListener implements ISelectionChangedListener, ISelectionListener {

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		OdoTimer.kick(event.getSource(), event.getSelection());
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		OdoTimer.kick(part, selection);
		
	}

	private static OdoSelectionChangedListener self = null;
    public static OdoSelectionChangedListener getInstance() {
    	if (self == null) {
    		self = new OdoSelectionChangedListener();
    	}
    	return self;
	}
}
