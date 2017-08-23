package org.xtuml.bp.ui.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.Proposal_c;
import org.xtuml.bp.core.ProposalList_c;
import org.xtuml.bp.core.Contentassistanceitemtypes_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.OALPersistenceUtil;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.editor.oal.OALEditor;

public class OALCompletionProcessor implements IContentAssistProcessor {
    
    OALEditor editor;
    
    public OALCompletionProcessor( OALEditor editor ) {
        this.editor = editor;
    }

    private Image getImage( int type ) {
        switch( type ) {
            case Contentassistanceitemtypes_c.Attribute:
                return CorePlugin.getImageFor( "Attribute_c" );
            case Contentassistanceitemtypes_c.Operation:
                return CorePlugin.getImageFor( "Operation_c" );
            case Contentassistanceitemtypes_c.Association:
                return CorePlugin.getImageFor( "Association_c" );
            default:
                return null;
        }
    }
    
    @Override
    public ICompletionProposal[] computeCompletionProposals( ITextViewer viewer, int position ) {
        List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
        IEditorInput editorInput = editor.getEditorInput();
        if ( editorInput instanceof AbstractModelElementPropertyEditorInput ) {
            Body_c body = OALPersistenceUtil.getOALModelElement(((AbstractModelElementPropertyEditorInput)editorInput).getModelElementContainingProperty());
            ProposalList_c list = ProposalList_c.getOneCA_LOnR1603( body );
            Proposal_c item = Proposal_c.getOneCA_IOnR1601( list );
            while ( null != item ) {
                ICompletionProposal proposal = new CompletionProposal( item.getReplacement_text(), position, 0, position+item.getReplacement_text().length(),
                                                                       getImage( item.getType() ), item.getReplacement_text(), null, null );
                proposals.add( proposal );
                item = Proposal_c.getOneCA_IOnR1602Precedes( item );
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
