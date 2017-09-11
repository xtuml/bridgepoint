package org.xtuml.bp.ui.text.contentassist;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;

public class OALCompletionProposal implements ICompletionProposal, ICompletionProposalExtension4 {
    
    private ICompletionProposal backingProposal;
    private int type;
    
    OALCompletionProposal( String replacementString, int replacementOffset, int replacementLength, int cursorPosition,
            Image image, String displayString, IContextInformation contextInformation, String additionalProposalInfo, int proposalType ) {
        type = proposalType;
        backingProposal = new CompletionProposal( replacementString, replacementOffset, replacementLength, cursorPosition, image,
                displayString, contextInformation, additionalProposalInfo );
    }
    
    public int getType() {
        return type;
    }

    @Override
    public void apply( IDocument arg0 ) {
        backingProposal.apply( arg0 );
    }

    @Override
    public String getAdditionalProposalInfo() {
        return backingProposal.getAdditionalProposalInfo();
    }

    @Override
    public IContextInformation getContextInformation() {
        return backingProposal.getContextInformation();
    }

    @Override
    public String getDisplayString() {
        return backingProposal.getDisplayString();
    }

    @Override
    public Image getImage() {
        return backingProposal.getImage();
    }

    @Override
    public Point getSelection(IDocument arg0) {
        return backingProposal.getSelection( arg0 );
    }

    @Override
    public boolean isAutoInsertable() {
        return Pref_c.Getboolean( BridgePointPreferencesStore.CONTENT_ASSIST_INSERT_SINGLE_PROPOSALS );
    }

}
