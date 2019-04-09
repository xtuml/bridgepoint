package org.xtuml.bp.debug.ui.locator;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

public class BPSourceLookupDirector extends AbstractSourceLookupDirector {

	public BPSourceLookupDirector() {
		super();
	}

	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[]{new BPSourceLookupParticipant()});
	}

}
