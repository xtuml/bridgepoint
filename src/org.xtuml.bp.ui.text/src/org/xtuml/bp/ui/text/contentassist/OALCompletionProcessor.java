package org.xtuml.bp.ui.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.xtuml.bp.als.oal.ParseRunnable;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.BridgeBody_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DerivedAttributeBody_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.FunctionBody_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.OperationBody_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProposalList_c;
import org.xtuml.bp.core.Proposal_c;
import org.xtuml.bp.core.Proposaltypes_c;
import org.xtuml.bp.core.ProposalCalculationCue_c;
import org.xtuml.bp.core.ProvidedOperationBody_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignalBody_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperationBody_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignalBody_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateActionBody_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.TransitionActionBody_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.editor.oal.OALEditor;

public class OALCompletionProcessor implements IContentAssistProcessor {
    
    private static final ICompletionProposal[] NO_PROPOSALS = {};

    private OALEditor editor;
    private boolean isAutoTriggered;
    private boolean inSession;
    private char[] triggerCharacters;
    private boolean isDefaultTrigger;
    private boolean needsParse;
    
    public OALCompletionProcessor( OALEditor editor, ContentAssistant assistant ) {
        this.editor = editor;
        this.isAutoTriggered = false;
        this.setInSession(false);
        setDefaultTriggerChars();
        setNeedsParse(true);
        assistant.addCompletionListener( new ICompletionListener() {
            @Override
            public void assistSessionStarted( ContentAssistEvent event ) {
                if ( event.processor != OALCompletionProcessor.this ) return;
                isAutoTriggered = event.isAutoActivated;
                setInSession( true );
            }
            
            @Override
            public void assistSessionEnded( ContentAssistEvent event ) { 
                Body_c body = getBody( ((AbstractModelElementPropertyEditorInput)editor.getEditorInput()).getModelElementContainingProperty() );
                ProposalList_c list = ProposalList_c.getOneP_PLOnR1603( body );
                if ( null != list ) {
                    list.Dispose();
                }
                ProposalCalculationCue_c[] cues = ProposalCalculationCue_c.getManyP_PCCsOnR1602( body );
                for ( ProposalCalculationCue_c cue : cues ) {
                    cue.Dispose();
                }
                setDefaultTriggerChars();
                setNeedsParse( true );
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
        parseActivity( ((AbstractModelElementPropertyEditorInput)editor.getEditorInput()).getModelElementContainingProperty(), position );
        Body_c body = getBody( ((AbstractModelElementPropertyEditorInput)editor.getEditorInput()).getModelElementContainingProperty() );

        // get the list
        ProposalList_c list = ProposalList_c.getOneP_PLOnR1603( body, new ClassQueryInterface_c() {
            @Override
            public boolean evaluate( Object selected ) {
                return lineAndColumnToPosition( ((ProposalList_c)selected).getLine(), ((ProposalList_c)selected).getCol() ) <= position;
            }
        });
        if ( null == list ) return NO_PROPOSALS;

        // add proposals
        List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
        int listPosition = lineAndColumnToPosition( list.getLine(), list.getCol() );
        String existingText = editor.getDocumentProvider().getDocument( editor.getEditorInput() ).get().substring( listPosition, position );
        String leadingWhitespace = "";
        if ( "".equals( existingText.trim() ) ) leadingWhitespace = existingText;
        else leadingWhitespace = existingText.substring( 0, existingText.indexOf( existingText.trim() ) );
        Proposal_c[] items = Proposal_c.getManyP_PsOnR1601( list );
        for ( Proposal_c item : items ) {
            boolean insertSpace = false;
            if ( "".equals( leadingWhitespace ) && item.getNeeds_space() ) insertSpace = true;
            if ( item.getReplacement_text().toLowerCase().startsWith( existingText.substring( leadingWhitespace.length() ).toLowerCase() ) &&
               ( !item.getReplacement_text().toLowerCase().equals( existingText.substring( leadingWhitespace.length() ).toLowerCase() ) ) ) {
                String replacementText = leadingWhitespace + item.getReplacement_text();
                int cursorPosition = leadingWhitespace.length() + item.getCursor_position();
                if ( insertSpace ) {
                    replacementText = " " + replacementText;
                    cursorPosition += 1;
                }
                ICompletionProposal proposal = new OALCompletionProposal( replacementText, listPosition, existingText.length(),
                                                                       cursorPosition, getImage( item.getType() ),
                                                                       item.getDisplay_text(), null, null, item.getType(), item.getOrder() );
                proposals.add( proposal );
            }
        }
        
        // set the auto trigger characters
        if ( proposals.size() > 0 ) setProposalTriggerChars( proposals.toArray( new ICompletionProposal[0] ), existingText.length() );
        
        // return the proposal array
        return proposals.toArray( new ICompletionProposal[0] );
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer arg0, int arg1) {
        return null;
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        if ( isDefaultTrigger ) return getDefaultTriggerChars();
        else return triggerCharacters;
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
        if ( Pref_c.Getboolean( BridgePointPreferencesStore.CONTENT_ASSIST_ENABLE_AUTO_TRIGGERING ) ) {
            if ( isDefaultTrigger ) {
                for ( String seq : getTriggerSequences() ) {
                    if ( editor.getDocumentProvider().getDocument( editor.getEditorInput() ).get().substring( 0, position ).endsWith( seq ) ) return true;
                }
            }
            else return true;
        }
        return false;
    }

    private Image getImage( int type ) {
        switch( type ) {
            case Proposaltypes_c.Attribute:
                return CorePlugin.getImageFor( Attribute_c.class );
            case Proposaltypes_c.Operation:
                return CorePlugin.getImageFor( Operation_c.class );
            case Proposaltypes_c.Association:
                return CorePlugin.getImageFor( Association_c.class );
            case Proposaltypes_c.Variable:
                //return CorePlugin.getImageFor( Association_c.class );
                return null;
            case Proposaltypes_c.Class:
                return CorePlugin.getImageFor( ModelClass_c.class );
            case Proposaltypes_c.EE:
                return CorePlugin.getImageFor( ExternalEntity_c.class );
            case Proposaltypes_c.Port:
                return CorePlugin.getImageFor( Port_c.class );
            case Proposaltypes_c.Event:
                return CorePlugin.getImageFor( StateMachineEvent_c.class );
            case Proposaltypes_c.FunctionParameter:
                return CorePlugin.getImageFor( FunctionParameter_c.class );
            case Proposaltypes_c.OperationParameter:
                return CorePlugin.getImageFor( OperationParameter_c.class );
            case Proposaltypes_c.BridgeParameter:
                return CorePlugin.getImageFor( BridgeParameter_c.class );
            case Proposaltypes_c.EventDataItem:
                return CorePlugin.getImageFor( StateMachineEventDataItem_c.class );
            case Proposaltypes_c.PropertyParameter:
                return CorePlugin.getImageFor( PropertyParameter_c.class );
            case Proposaltypes_c.Function:
                return CorePlugin.getImageFor( Function_c.class );
            case Proposaltypes_c.EDT:
                return CorePlugin.getImageFor( EnumerationDataType_c.class );
            case Proposaltypes_c.Enumerator:
                return CorePlugin.getImageFor( Enumerator_c.class );
            case Proposaltypes_c.Constant:
                return CorePlugin.getImageFor( SymbolicConstant_c.class );
            case Proposaltypes_c.Literal:
                //return CorePlugin.getImageFor( Function_c.class );
                return null;
            default:
                return null;
        }
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

    private int positionToLine( int position ) {
        IDocument document = editor.getDocumentProvider().getDocument( editor.getEditorInput() );
        for ( int i = 0, count = 0; i < document.getNumberOfLines(); i++ ) {
            try {
                count += document.getLineLength(i);
                if ( count >= position ) return i + 1;
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    
    private int positionToCol( int position ) {
        IDocument document = editor.getDocumentProvider().getDocument( editor.getEditorInput() );
        for ( int i = 0, count = 0; i < document.getNumberOfLines(); i++ ) {
            try {
                if ( position - count <= document.getLineLength(i) ) return position - count + 1;
                count += document.getLineLength(i);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private synchronized void setInSession(boolean inSession) {
        this.inSession = inSession;
        notifyAll();
    }
    
    public synchronized boolean getNeedsParse() {
        return needsParse;
    }

    public synchronized void setNeedsParse( boolean needsParse ) {
        this.needsParse = needsParse;
    }

    private synchronized void waitForSessionToStart() {
        while ( !inSession ) {
            try {
                wait();
            }
            catch ( InterruptedException e ) {}
        }
    }
    
    private Body_c getBody( NonRootModelElement element ) {
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
    
    private void parseActivity( NonRootModelElement element, int position ) {
        if ( null != element && getNeedsParse() ) {
            ParseRunnable parseRunner = new ParseRunnable( element, editor.getDocumentProvider().getDocument( editor.getEditorInput() ).get(),
                    positionToLine( position ), positionToCol( position ) );
            parseRunner.run();
            setNeedsParse(false);
        }
    }
    
    private synchronized void setDefaultTriggerChars() {
        isDefaultTrigger = true;
    }

    private char[] getDefaultTriggerChars() {
        String triggerChars = "";
        for ( String seq : getTriggerSequences() ) {
            char lastChar = seq.charAt( seq.length() - 1 );
            if ( -1 == triggerChars.indexOf( lastChar ) ) triggerChars += Character.toString( lastChar );
        }
        return triggerChars.toCharArray();
    }

    private synchronized char[] setProposalTriggerChars( ICompletionProposal[] currentProposals, int existingTextLenth ) {
        isDefaultTrigger = false;
        String triggerChars = "";
        for ( ICompletionProposal proposal : currentProposals ) {
            try {
                char lastChar = proposal.getDisplayString().charAt( existingTextLenth );
                if ( -1 == triggerChars.indexOf( lastChar ) ) triggerChars += Character.toString( lastChar );
            } catch ( IndexOutOfBoundsException e ) {}
        }
        return triggerChars.toCharArray();
    }
    
    private static String[] getTriggerSequences() {
        return Pref_c.Getstring( BridgePointPreferencesStore.CONTENT_ASSIST_AUTO_TRIGGER_SEQUENCES ).split( "\n" );
    }

}
