package org.xtuml.bp.ui.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.xtuml.bp.als.oal.ParseRunnable;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.BridgeBody_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.Proposal_c;
import org.xtuml.bp.core.ProvidedOperationBody_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignalBody_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperationBody_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignalBody_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateActionBody_c;
import org.xtuml.bp.core.TransitionActionBody_c;
import org.xtuml.bp.core.ProposalList_c;
import org.xtuml.bp.core.Contentassistanceitemtypes_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DerivedAttributeBody_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.FunctionBody_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.OperationBody_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.OALPersistenceUtil;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.editor.oal.OALEditor;

public class OALCompletionProcessor implements IContentAssistProcessor {
    
    private static final ICompletionProposal[] NO_PROPOSALS= {};
    private static final String[] TRIGGER_SEQUENCES = { "->", ".", "::" };

    private static boolean autoTriggerAllowed = true;

    private boolean isAutoTriggered;
    private boolean inSession;
    
    OALEditor editor;
    
    public OALCompletionProcessor( OALEditor editor, ContentAssistant assistant ) {
        this.editor = editor;
        this.isAutoTriggered = false;
        this.setInSession(false);
        assistant.addCompletionListener( new ICompletionListener() {
            @Override
            public void assistSessionStarted( ContentAssistEvent event ) {
                if ( event.processor != OALCompletionProcessor.this ) return;
                isAutoTriggered = event.isAutoActivated;
                setInSession( true );
            }
            
            @Override
            public void assistSessionEnded( ContentAssistEvent event ) { 
                setInSession( false );
            }

            @Override
            public void selectionChanged( ICompletionProposal proposal, boolean smartToggle ) {}
        });
    }

    @Override
    public ICompletionProposal[] computeCompletionProposals( ITextViewer viewer, int position ) {
        waitForSessionToStart();
        if ( isAutoTriggered && !isValidAutoTrigger( position ) ) return NO_PROPOSALS;
        if ( !(editor.getEditorInput() instanceof AbstractModelElementPropertyEditorInput) ) return NO_PROPOSALS;
        
        // parse the body
        Body_c body = parseActivity( ((AbstractModelElementPropertyEditorInput)editor.getEditorInput()).getModelElementContainingProperty() );

        // get the list
        ProposalList_c[] lists = ProposalList_c.getManyCA_LsOnR1603( body, new ClassQueryInterface_c() {
            @Override
            public boolean evaluate( Object selected ) {
                return lineAndColumnToPosition( ((ProposalList_c)selected).getLine(), ((ProposalList_c)selected).getCol() ) <= position;
            }
        });
        ProposalList_c list = null;
        for ( int i = 0, max = 0; i < lists.length; i++ ) {
            if ( lineAndColumnToPosition( lists[i].getLine(), lists[i].getCol() ) > max ) list = lists[i];
        }

        // add proposals
        List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
        Proposal_c item = Proposal_c.getOneCA_IOnR1601( list );
        while ( null != item ) {
            int listPosition = lineAndColumnToPosition( list.getLine(), list.getCol() );
            String existingText = editor.getDocumentProvider().getDocument( editor.getEditorInput() ).get().substring( listPosition, position );
            if ( item.getReplacement_text().toLowerCase().startsWith( existingText.toLowerCase() ) ) {
                ICompletionProposal proposal = new CompletionProposal( item.getReplacement_text(), listPosition, existingText.length(), item.getCursor_position(),
                                                                       getImage( item.getType() ), item.getReplacement_text(), null, null );
                proposals.add( proposal );
            }
            else {
                System.out.printf( "%s does not match %s\n", existingText, item.getReplacement_text() );
            }
            item = Proposal_c.getOneCA_IOnR1602Precedes( item );
        }
        
        // return the proposal array
        return proposals.toArray( new ICompletionProposal[0] );
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer arg0, int arg1) {
        return null;
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        String triggerChars = "";
        for ( String seq : TRIGGER_SEQUENCES ) {
            char lastChar = seq.charAt( seq.length() - 1 );
            if ( -1 == triggerChars.indexOf( lastChar ) ) triggerChars += Character.toString( lastChar );
        }
        return triggerChars.toCharArray();
    }

    @Override
    public char[] getContextInformationAutoActivationCharacters() {
        return null;
    }

    @Override
    public IContextInformationValidator getContextInformationValidator() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
    
    private boolean isValidAutoTrigger( int position ) {
        if ( autoTriggerAllowed ) {
            for ( String seq : TRIGGER_SEQUENCES ) {
                if ( editor.getDocumentProvider().getDocument( editor.getEditorInput() ).get().substring( 0, position ).endsWith( seq ) ) return true;
            }
        }
        return false;
    }

    private Image getImage( int type ) {
        switch( type ) {
            case Contentassistanceitemtypes_c.Attribute:
                return CorePlugin.getImageFor( Attribute_c.class );
            case Contentassistanceitemtypes_c.Operation:
                return CorePlugin.getImageFor( Operation_c.class );
            case Contentassistanceitemtypes_c.Association:
                return CorePlugin.getImageFor( Association_c.class );
            default:
                return null;
        }
    }

    private int positionToLine( int position ) {
        IDocument document = editor.getDocumentProvider().getDocument( editor.getEditorInput() );
        int numLines = document.getNumberOfLines();
        for ( int i = 0, c = 0; i < numLines; i++ ) {
            try {
                c += document.getLineLength( i );
                if ( c >= position ) return i + 1;
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private int positionToColumn( int position ) {
        IDocument document = editor.getDocumentProvider().getDocument( editor.getEditorInput() );
        int numLines = document.getNumberOfLines();
        for ( int i = 0, c = 0; i < numLines; i++ ) {
            try {
                c += document.getLineLength( i );
                if ( c >= position ) return document.getLineLength( i ) - ( c - position ) + 1;
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private int lineAndColumnToPosition( int line, int column ) {
        IDocument document = editor.getDocumentProvider().getDocument( editor.getEditorInput() );
        int position = 0;
        for ( int i = 0; i < line-1; i++ ) {
            try {
                position += document.getLineLength( i );
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        position += column - 1;
        return position;
    }

    private synchronized void setInSession(boolean inSession) {
        this.inSession = inSession;
        notifyAll();
    }
    
    private synchronized void waitForSessionToStart() {
        while ( !inSession ) {
            try {
                wait();
            }
            catch ( InterruptedException e ) {}
        }
    }
    
    private Body_c parseActivity( NonRootModelElement element ) {
        if ( null != element ) {
            ParseRunnable parseRunner = new ParseRunnable( element, editor.getDocumentProvider().getDocument( editor.getEditorInput() ).get() );
            parseRunner.run();
            Body_c body = null;
            if ( element instanceof RequiredSignal_c ) {
                body = Body_c.getOneACT_ACTOnR698(RequiredSignalBody_c.getOneACT_RSBOnR684((RequiredSignal_c)element));
            }
            else if ( element instanceof RequiredOperation_c ) {
                body = Body_c.getOneACT_ACTOnR698(RequiredOperationBody_c.getOneACT_ROBOnR685((RequiredOperation_c)element));
            }
            else if ( element instanceof ProvidedSignal_c ) {
                body = Body_c.getOneACT_ACTOnR698(ProvidedSignalBody_c.getOneACT_PSBOnR686((ProvidedSignal_c)element));
            }
            else if ( element instanceof ProvidedOperation_c ) {
                body = Body_c.getOneACT_ACTOnR698(ProvidedOperationBody_c.getOneACT_POBOnR687((ProvidedOperation_c)element));
            }
            else if ( element instanceof Action_c ) {
                body = Body_c.getOneACT_ACTOnR698(StateActionBody_c.getOneACT_SABOnR691((Action_c)element));
                if ( null == body ) body = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c.getOneACT_TABOnR688((Action_c)element));
            }
            else if ( element instanceof DerivedBaseAttribute_c ) {
                body = Body_c.getOneACT_ACTOnR698(DerivedAttributeBody_c.getManyACT_DABsOnR693((DerivedBaseAttribute_c)element));
            }
            else if ( element instanceof Function_c ) {
                body = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getManyACT_FNBsOnR695((Function_c)element));
            }
            else if ( element instanceof Operation_c ) {
                body = Body_c.getOneACT_ACTOnR698(OperationBody_c.getManyACT_OPBsOnR696((Operation_c)element));
            }
            else if ( element instanceof Bridge_c ) {
                body = Body_c.getOneACT_ACTOnR698(BridgeBody_c.getManyACT_BRBsOnR697((Bridge_c)element));
            }
            return body;
        }
        else return null;
    }

}
