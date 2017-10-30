package org.xtuml.bp.ui.text.editor;

import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
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
		return super.findWord(document, offset);
	}

}
