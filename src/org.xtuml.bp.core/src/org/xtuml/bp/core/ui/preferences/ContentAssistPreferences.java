package org.xtuml.bp.core.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesModel;
import org.xtuml.bp.core.ui.ICoreHelpContextIds;
import org.xtuml.bp.ui.preference.IPreferenceModel;

public class ContentAssistPreferences extends PreferencePage implements IWorkbenchPreferencePage {

    private Group insertionGroup;
    private Button rolePhraseCheck;
    private Button singleProposalCheck;
    private Group invocationGroup;
    private Button invocationEmptyRadio;
    private Button invocationLabelRadio;
    private Group autoTriggerGroup;
    private Button autoTriggerCheck;
    private Text autoTriggerTextbox;

    private Group parsingGroup;
    private Button parsingCheck;

    protected IPreferenceModel model;

    public ContentAssistPreferences() {
        super();
    }

    public ContentAssistPreferences(String title) {
        super(title);
    }

    public ContentAssistPreferences(String title, ImageDescriptor image) {
        super(title, image);
    }

    protected Control createContents(Composite parent) {
        // Create the composite to hold the widgets
        Composite composite = new Composite( parent, SWT.NULL );

        // Create the desired layout for this wizard page
        GridLayout gl = new GridLayout();
        gl.numColumns = 1;
        
        composite.setLayout( gl );

        // Create the insertion preferences group
        insertionGroup = new Group( composite, SWT.SHADOW_ETCHED_IN );
        insertionGroup.setLayout( gl );
        GridData data1 = new GridData( GridData.FILL_HORIZONTAL );
        insertionGroup.setLayoutData( data1 );
        insertionGroup.setText( "Insertion" );

        // Create role phrase checkbox
        rolePhraseCheck = new Button( insertionGroup, SWT.CHECK | SWT.LEFT );
        rolePhraseCheck.setText( "Include role phrases in association proposals by default" );

        // Create single proposal checkbox
        singleProposalCheck = new Button( insertionGroup, SWT.CHECK | SWT.LEFT );
        singleProposalCheck.setText( "Insert single proposals automatically" );

        // Create invocation group
        invocationGroup = new Group( composite, SWT.SHADOW_ETCHED_IN );
        invocationGroup.setLayout( gl );
        GridData data2 = new GridData( GridData.FILL_HORIZONTAL );
        invocationGroup.setLayoutData( data2 );
        invocationGroup.setText( "Parameterized invocations" );

        // Create invocation format radio button
        invocationEmptyRadio = new Button( invocationGroup, SWT.RADIO | SWT.LEFT );
        invocationEmptyRadio.setText( "Insert invocations with empty parentheses" );
        invocationLabelRadio = new Button( invocationGroup, SWT.RADIO | SWT.LEFT );
        invocationLabelRadio.setText( "Insert invocations with parameter labels" );

        // Create the auto trigger group
        autoTriggerGroup = new Group( composite, SWT.SHADOW_ETCHED_IN );
        autoTriggerGroup.setLayout( gl );
        GridData data3 = new GridData( GridData.FILL_BOTH );
        autoTriggerGroup.setLayoutData( data3 );
        autoTriggerGroup.setText( "Auto Activation" );

        // Create auto trigger checkbox
        autoTriggerCheck = new Button( autoTriggerGroup, SWT.CHECK | SWT.LEFT );
        autoTriggerCheck.setText( "Enable auto activation" );
        autoTriggerCheck.addSelectionListener( new SelectionListener() {
            @Override
            public void widgetDefaultSelected( SelectionEvent e ) {}
            @Override
            public void widgetSelected( SelectionEvent e ) {
                if ( autoTriggerCheck.getSelection() ) {
                    autoTriggerTextbox.setEnabled( true );
                }
                else {
                    autoTriggerTextbox.setEnabled( false );
                }
            }
        });
        
        // Create auto trigger textbox
        Label autoTriggerTextboxLabel = new Label( autoTriggerGroup, SWT.None );
        autoTriggerTextboxLabel.setText( "Auto activation sequences" );
        autoTriggerTextbox = new Text( autoTriggerGroup, SWT.MULTI | SWT.LEFT | SWT.V_SCROLL );
        GridData data4 = new GridData( GridData.FILL_BOTH );
        autoTriggerTextbox.setLayoutData( data4 );

        /*
        // Create the partial parsing preferences group
        parsingGroup = new Group( composite, SWT.SHADOW_ETCHED_IN );
        parsingGroup.setLayout( gl );
        GridData data5 = new GridData( GridData.FILL_HORIZONTAL );
        parsingGroup.setLayoutData( data5 );
        parsingGroup.setText( "Parsing" );

        // Create partial parsing checkbox
        parsingCheck = new Button( parsingGroup, SWT.CHECK | SWT.LEFT );
        parsingCheck.setText( "Enable partial parsing for content assist" );
        parsingCheck.setToolTipText( "Partial parsing improves content assist performance by only parsing a small part" +
                                     " of the body for each invocation of content assist. However, parsing only part of" +
                                     " the body can result in stale data in proposal boxes. This is an experimental feature." );
        */

        model = new BridgePointPreferencesModel();
        model.getStore().loadModel( getPreferenceStore(), null, model );
        
        syncUIWithPreferences();
        return composite;
    }

    public void init(IWorkbench workbench) {
        // Initialize the Core preference store
        setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
    }

    public void createControl(Composite parent) {
        super.createControl(parent);
        // add F1 context support to main BridgePoint preference page
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ICoreHelpContextIds.corePreferencesId);
    }

    public boolean performOk() {
        super.performOk();
        model.getStore().loadModel(getPreferenceStore(), null, model);
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        
        // set role phrases
        bpPrefs.contentAssistIncludeRolePhrases = rolePhraseCheck.getSelection();
        
        // set insert single proposals
        bpPrefs.contentAssistInsertSingleProposals = singleProposalCheck.getSelection();
        
        // set invocation format
        if ( invocationEmptyRadio.getSelection() ) {
            bpPrefs.contentAssistInvocationFormat = "empty";
        }
        else if ( invocationLabelRadio.getSelection() ) {
            bpPrefs.contentAssistInvocationFormat = "labels";
        }
        
        // set auto triggering
        bpPrefs.contentAssistEnableAutoTriggering = autoTriggerCheck.getSelection();
        
        // set trigger sequences
        bpPrefs.contentAssistAutoTriggerSequences = formatTriggerSequences( autoTriggerTextbox.getText() );

        // set partial parsing
        /*
        bpPrefs.contentAssistEnablePartialParsing = parsingCheck.getSelection();
        */
        
        model.getStore().saveModel(getPreferenceStore(), model);
        return true;
    }

    public void performDefaults() {
        super.performDefaults();
        model.getStore().restoreModelDefaults(model);
        syncUIWithPreferences();
    }

    private void syncUIWithPreferences() {
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        
        // set role phrases
        rolePhraseCheck.setSelection( bpPrefs.contentAssistIncludeRolePhrases );
        
        // set insert single proposals
        singleProposalCheck.setSelection( bpPrefs.contentAssistInsertSingleProposals );

        // set invocation format
        invocationEmptyRadio.setSelection( false );
        invocationLabelRadio.setSelection( false );
        if ( "empty".equals( bpPrefs.contentAssistInvocationFormat ) ) {
            invocationEmptyRadio.setSelection( true );
        }
        else if ( "labels".equals( bpPrefs.contentAssistInvocationFormat ) ) {
            invocationLabelRadio.setSelection( true );
        }
        
        // set auto triggering
        autoTriggerCheck.setSelection( bpPrefs.contentAssistEnableAutoTriggering );
        
        // set trigger sequences
        autoTriggerTextbox.setText( bpPrefs.contentAssistAutoTriggerSequences );
        if ( autoTriggerCheck.getSelection() ) {
            autoTriggerTextbox.setEnabled( true );
        }
        else {
            autoTriggerTextbox.setEnabled( false );
        }

        // set partial parsing
        /*
        parsingCheck.setSelection( bpPrefs.contentAssistEnablePartialParsing );
        */
    }
    
    // Assure that there is no leading or trailing whitespace and
    // all sequences are separated by a newline character
    private String formatTriggerSequences( String input ) {
        if ( null == input ) return null;
        String output = "";
        String sep = "";
        String[] triggers = input.split( "\n" );
        for ( String trigger : triggers ) {
            output += sep + trigger.trim();
            sep = "\n";
        }
        return output;
    }

}
