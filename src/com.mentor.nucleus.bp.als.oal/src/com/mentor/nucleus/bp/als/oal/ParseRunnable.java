package com.mentor.nucleus.bp.als.oal;

import java.io.StringReader;

import org.eclipse.core.resources.IMarker;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class ParseRunnable implements Runnable {
	private String m_document;
	private NonRootModelElement m_modelElement;

	/**
	 * Constructor.
	 */
	public ParseRunnable(NonRootModelElement modelElement, String document) {
		m_modelElement = modelElement;
		m_document = document;
	}

	public void run() {
		// if the model element whose activity text is to be parsed has been
		// deleted from its model, or this parse is for an editor which has
		// been disposed, then no parse should be performed
		if (m_modelElement == null || m_modelElement.isOrphaned())
			return;

		Ooaofooa modelRoot = (Ooaofooa) m_modelElement.getModelRoot();
		OalLexer lexer = new OalLexer(new StringReader(m_document));
		TextParser parser = new TextParser(modelRoot, lexer);
		boolean parseCompleted = false;
		boolean problemsFound = false;
		try {
			// Parse the input expression
			parser.action(m_modelElement, false);
			parseCompleted = true;
		} catch (TokenStreamException e) {
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			if (e instanceof TokenStreamRecognitionException) {
				TokenStreamRecognitionException tsre = (TokenStreamRecognitionException) e;
				parser.reportError(tsre.recog);
			} else {
				String errorMsg = "Internal parser error.  Token stream exception in parser.  OAL in this action home caused an exception in the parser."; //$NON-NLS-1$
				CorePlugin.logError(errorMsg, e);
			}
		} catch (RecognitionException e) {
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			parser.reportError(e);
		} catch (InterruptedException e) {
			// The parse was canceled, we don't need to report an
			// error in this situation, but we do need to note that
			// the parse did not complete (parseCompleted is false).
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
		} catch (Throwable t) {
			String errorMsg = "Internal parser error.  Parsing thread interrupted pre-maturely.  OAL in this action home caused an exception in the parser."; //$NON-NLS-1$
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);

			// This throwable catches all the un-checked exceptions that occur
			// in the thread, and logs them
			// appropriately.
			CorePlugin.logError(errorMsg, t); //$NON-NLS-1$	
		}
		
	}
};