package org.xtuml.bp.ui.text.editor;

import java.text.BreakIterator;

import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
/**
 * This class exists simply to make use of Eclipse's default word find
 * capabilities when double clicking.  An example of use is when using a
 * mouse cursor location, one can simply use the BP text and get its
 * double click strategy to call findWord from eclipse's
 * DefaultTextDoubleClickStrategy.  This class must exist as findWord is
 * protected, this override allows it to be public. 
 *
 */
public class BPTextDefaultTextDoubleClickStategy extends DefaultTextDoubleClickStrategy {

	@Override
	public IRegion findWord(IDocument document, int offset) {
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText( document.get() );
		int start = boundary.isBoundary(offset) ? offset : boundary.preceding(offset);
		int end = boundary.following(offset);
		// don't allow the period character in a word
		int periodIndex = document.get().indexOf( '.', start );
		if ( periodIndex != -1 && periodIndex < end ) {
			if ( periodIndex < offset ) start = periodIndex+1;
			else if ( periodIndex > offset ) end = periodIndex;
			else { start = periodIndex; end = periodIndex+1; }
		}
		return new Region( start, end-start );
	}

}
