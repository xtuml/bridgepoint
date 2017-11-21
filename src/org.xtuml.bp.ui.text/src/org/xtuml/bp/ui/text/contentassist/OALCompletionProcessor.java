package org.xtuml.bp.ui.text.contentassist;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.xtuml.bp.als.oal.ParseRunnable;
import org.xtuml.bp.als.oal.PartialParseRunnable;
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
import org.xtuml.bp.core.util.DocumentUtil;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.editor.oal.OALEditor;

public class OALCompletionProcessor implements IContentAssistProcessor {
    
    private static final ICompletionProposal[] NO_PROPOSALS = {};
    private static final Pattern SLCOMMENT = Pattern.compile( "\\/\\/.*$", Pattern.MULTILINE );
    private static final Pattern MLCOMMENT = Pattern.compile( "\\/\\*[\\s\\S]*\\*\\/", Pattern.MULTILINE );

    private OALEditor editor;
    private boolean isAutoTriggered;
    private boolean inSession;
    private char[] triggerCharacters;
    private boolean isDefaultTrigger;
    private boolean needsParse;
    
    public OALCompletionProcessor() {
        setUp();
    }
    
    public OALCompletionProcessor( OALEditor editor, ContentAssistant assistant ) {
        this();
        this.editor = editor;
        assistant.addCompletionListener( new ICompletionListener() {
            @Override
            public void assistSessionStarted( ContentAssistEvent event ) {
                if ( event.processor != OALCompletionProcessor.this ) return;
                isAutoTriggered = event.isAutoActivated;
                setInSession( true );
            }
            
            @Override
            public void assistSessionEnded( ContentAssistEvent event ) { 
                cleanUp( ((AbstractModelElementPropertyEditorInput)editor.getEditorInput()).getModelElementContainingProperty() );
            }

            @Override
            public void selectionChanged( ICompletionProposal proposal, boolean smartToggle ) {}
        });
    }

    @Override
    public ICompletionProposal[] computeCompletionProposals( ITextViewer viewer, int position ) {
        waitForSessionToStart();
        if ( isAutoTriggered && !isValidAutoTrigger( editor.getDocumentProvider().getDocument( editor.getEditorInput() ), position ) ) return NO_PROPOSALS;
        if ( !(editor.getEditorInput() instanceof AbstractModelElementPropertyEditorInput) ) return NO_PROPOSALS;
        return computeCompletionProposals( editor.getDocumentProvider().getDocument( editor.getEditorInput() ), ((AbstractModelElementPropertyEditorInput)editor.getEditorInput()).getModelElementContainingProperty(), position );
    }

    public ICompletionProposal[] computeCompletionProposals( IDocument document, NonRootModelElement element, int position ) {
        if ( isInsideComment( document, position ) ) return NO_PROPOSALS;

        // parse from the closest parsed statement
        parseActivityPartial( document, element, position );
        Body_c body = getBody( element );

        // get the list
        ProposalList_c list = ProposalList_c.getOneP_PLOnR1603( body, new ClassQueryInterface_c() {
            @Override
            public boolean evaluate( Object selected ) {
                return DocumentUtil.lineAndColumnToPosition( ((ProposalList_c)selected).getLine(), ((ProposalList_c)selected).getCol(), document ) <= position;
            }
        });
        if ( null == list ) return NO_PROPOSALS;

        // add proposals
        List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
        int listPosition = DocumentUtil.lineAndColumnToPosition( list.getLine(), list.getCol(), document );
        String existingText = document.get().substring( listPosition, position );
        String leadingText = "";
        if ( "".equals( trimWhitespaceAndComments( existingText ) ) ) leadingText = existingText;
        else leadingText = existingText.substring( 0, existingText.lastIndexOf( trimWhitespaceAndComments( existingText ) ) );
        Proposal_c[] items = Proposal_c.getManyP_PsOnR1601( list );
        for ( Proposal_c item : items ) {
            boolean insertSpace = false;
            if ( "".equals( leadingText ) && item.getNeeds_space() ) insertSpace = true;
            if ( item.getReplacement_text().toLowerCase().startsWith( existingText.substring( leadingText.length() ).toLowerCase() ) &&
               ( !item.getReplacement_text().toLowerCase().equals( existingText.substring( leadingText.length() ).toLowerCase() ) ) ) {
                String replacementText = leadingText + item.getReplacement_text();
                int cursorPosition = leadingText.length() + item.getCursor_position();
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
    
    private boolean isValidAutoTrigger( IDocument document, int position ) {
        if ( Pref_c.Getboolean( BridgePointPreferencesStore.CONTENT_ASSIST_ENABLE_AUTO_TRIGGERING ) ) {
            if ( isDefaultTrigger ) {
                for ( String seq : getTriggerSequences() ) {
                    if ( document.get().substring( 0, position ).endsWith( seq ) ) return true;
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
            case Proposaltypes_c.Bridge:
                return CorePlugin.getImageFor( Bridge_c.class );
            case Proposaltypes_c.EDT:
                return CorePlugin.getImageFor( EnumerationDataType_c.class );
            case Proposaltypes_c.Enumerator:
                return CorePlugin.getImageFor( Enumerator_c.class );
            case Proposaltypes_c.Constant:
                return CorePlugin.getImageFor( SymbolicConstant_c.class );
            case Proposaltypes_c.Literal:
                return null;
            case Proposaltypes_c.SignalToProvider:
                return CorePlugin.getImageFor( "InterfaceSignal_cClientServer", false, null, true );
            case Proposaltypes_c.SignalFromProvider:
                return CorePlugin.getImageFor( "InterfaceSignal_cServerClient", false, null, true );
            case Proposaltypes_c.OperationToProvider:
                return CorePlugin.getImageFor( "InterfaceOperation_cClientServer", false, null, true );
            case Proposaltypes_c.OperationFromProvider:
                return CorePlugin.getImageFor( "InterfaceOperation_cServerClient", false, null, true );
            case Proposaltypes_c.Keyword:
                return null;
            default:
                return null;
        }
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
    
    private void parseActivityPartial( IDocument document, NonRootModelElement element, int position ) {
        if ( null != element && getNeedsParse() ) {
            ParseRunnable parseRunner = new PartialParseRunnable( element, document.get(),
                    DocumentUtil.positionToLine( position, document ), DocumentUtil.positionToCol( position, document ) );
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
            if ( !"".equals(seq) ) {
                char lastChar = seq.charAt( seq.length() - 1 );
                if ( -1 == triggerChars.indexOf( lastChar ) ) triggerChars += Character.toString( lastChar );
            }
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
    
    private String trimWhitespaceAndComments( String input ) {
        String whitespaceAndCommentPattern = "(" + SLCOMMENT.pattern() + "|" + MLCOMMENT.pattern() + "|\\s)*";
        return Pattern.compile( "\\A" + whitespaceAndCommentPattern + "|" + whitespaceAndCommentPattern + "\\z", SLCOMMENT.flags() | MLCOMMENT.flags() ).matcher( input ).replaceAll( "" );
    }
    
    public void setUp() {
        this.editor = null;
        this.isAutoTriggered = false;
        this.setInSession(false);
        setDefaultTriggerChars();
        setNeedsParse(true);
    }
    
    public void cleanUp( NonRootModelElement element ) {
        Body_c body = getBody( element );
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
    
    private List<IRegion> getAllComments( IDocument document ) {
        List<IRegion> comments = new ArrayList<>();
        Matcher slmatcher = SLCOMMENT.matcher( document.get() );
        while ( slmatcher.find() ) {
            // single line comments get one extra length to account for the line break character
            comments.add( new Region( slmatcher.start(), slmatcher.end()-slmatcher.start() + 1 ) );
        }
        Matcher mlmatcher = MLCOMMENT.matcher( document.get() );
        while ( mlmatcher.find() ) {
            comments.add( new Region( mlmatcher.start(), mlmatcher.end()-mlmatcher.start() ) );
        }
        return comments;
    }
    
    private boolean isInsideComment( IDocument document, int position ) {
        List<IRegion> comments = getAllComments( document );
        for ( IRegion comment : comments ) {
            if ( position > comment.getOffset() && position < comment.getOffset() + comment.getLength() ) return true;
        }
        return false;
    }

}
