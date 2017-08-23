package org.xtuml.bp.ui.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.ui.IEditorInput;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.ContextAssistanceItem_c;
import org.xtuml.bp.core.ContextAssistanceList_c;
import org.xtuml.bp.core.common.OALPersistenceUtil;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.editor.oal.OALEditor;

public class OALCompletionProcessor implements IContentAssistProcessor {
    
    OALEditor editor;
    
    public OALCompletionProcessor( OALEditor editor ) {
        this.editor = editor;
    }
    
    @Override
    public ICompletionProposal[] computeCompletionProposals( ITextViewer viewer, int position ) {
        List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
        IEditorInput editorInput = editor.getEditorInput();
        if ( editorInput instanceof AbstractModelElementPropertyEditorInput ) {
            Body_c body = OALPersistenceUtil.getOALModelElement(((AbstractModelElementPropertyEditorInput)editorInput).getModelElementContainingProperty());
            ContextAssistanceList_c list = ContextAssistanceList_c.ContextAssistanceListInstance( body.getModelRoot() );
            ContextAssistanceItem_c item = ContextAssistanceItem_c.getOneCA_IOnR1601( list );
            while ( null != item ) {
                ICompletionProposal proposal = new CompletionProposal( item.getValue(), position, 0, position+item.getValue().length() );
                proposals.add( proposal );
                item = ContextAssistanceItem_c.getOneCA_IOnR1602Precedes( item );
            }
        }
        return proposals.toArray( new ICompletionProposal[0] );
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char[] getContextInformationAutoActivationCharacters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextInformationValidator getContextInformationValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getErrorMessage() {
        // TODO Auto-generated method stub
        return null;
    }

}
