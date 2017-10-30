package org.xtuml.bp.ui.text.contentassist;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.Proposaltypes_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.ui.text.OALEditorConstants;
import org.xtuml.bp.ui.text.OALEditorPlugin;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences.TokenTypeInfo;

public class OALCompletionProposal implements ICompletionProposal, ICompletionProposalExtension4, ICompletionProposalExtension6 {
    
    private ICompletionProposal backingProposal;
    private int type;
    private int order;
    private String replacementString;
    private StyledString displayString;
    
    OALCompletionProposal( String replacementString, int replacementOffset, int replacementLength, int cursorPosition,
            Image image, String displayString, IContextInformation contextInformation, String additionalProposalInfo,
            int proposalType, int proposalOrder ) {
        
        // set fields
        type = proposalType;
        order = proposalOrder;
        this.replacementString = replacementString;

        // style keyword proposals
        if ( Proposaltypes_c.Keyword == type || ( Proposaltypes_c.Variable == type && ( "sender".equals(displayString) ||
                                                                                        "self".equals(displayString) ||
                                                                                        "selected".equals(displayString) ) ) ) {
            this.displayString = new StyledString( displayString, new Styler() {
                @Override
                public void applyStyles( TextStyle textStyle ) {
                    TextStyle keywordStyle = getKeywordStyle();
                    textStyle.foreground = keywordStyle.foreground;
                    textStyle.font = keywordStyle.font;
                }
            });
        }
        else this.displayString = new StyledString( displayString );
        
        // create backing proposal
        backingProposal = new CompletionProposal( replacementString, replacementOffset, replacementLength, cursorPosition, image,
                displayString, contextInformation, additionalProposalInfo );
    }
    
    public int getType() {
        return type;
    }

    public int getOrder() {
        return order;
    }
    
    public String getReplacementString() {
        return replacementString;
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

    @Override
    public StyledString getStyledDisplayString() {
        return displayString;
    }
    
    private TextStyle getKeywordStyle() {
        // get keyword style
        TextStyle keywordStyle = new TextStyle();
        SyntaxHighlightingPreferences prefs = OALEditorPlugin.getDefaultOALPlugin().getSyntaxHighlightingPreferences();
        for ( int i = 0; i < 10; i++ ) {
            try {
                TokenTypeInfo tokenTypeInfo = prefs.getTokenTypeInfo( i );
                if ( OALEditorConstants.DEFAULT_LABEL_KEYWORD.equals( tokenTypeInfo.getTypeDescription() ) ) {
                      keywordStyle.foreground = tokenTypeInfo.getTextAttribute().getForeground();
                      FontData[] fontData = JFaceResources.getFont( JFaceResources.TEXT_FONT ).getFontData();
                      if ( tokenTypeInfo.isBold() ) for ( int j = 1; j < fontData.length; j++ ) fontData[j].setStyle( SWT.BOLD );
                      keywordStyle.font = new Font( Display.getDefault(), fontData );
                      break;
                }
            } catch ( IllegalArgumentException e ) { /* do nothing */ }
        }
        return keywordStyle;
    }

}
