package com.mentor.nucleus.qa.odometer.tracker;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.mentor.nucleus.qa.odometer.timer.OdoTimer;

public class OdoDocumentListener implements IDocumentListener {

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
      // Do nothing		
	}

	@Override
	public void documentChanged(DocumentEvent event) {
		OdoTimer.kick(target);
		
	}

	private static OdoDocumentListener self = null;
	private static IDocumentProvider target = null;
	public static OdoDocumentListener getInstance() {
		if (self == null) {
			self = new OdoDocumentListener();
		}
		return self;
	}
    public static void setTarget(IDocumentProvider docP) {
    	target = docP;
    }
}
