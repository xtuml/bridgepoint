//========================================================================
//
//File:      $RCSfile: BridgePointPreferencesStore.java,v $
//Version:   $Revision: 1.27.86.1 $
//Modified:  $Date: 2013/07/19 18:41:35 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package org.xtuml.bp.core.common;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.ui.preference.BasePlugin;
import org.xtuml.bp.ui.preference.IPreferenceModel;
import org.xtuml.bp.ui.preference.IPreferenceModelStore;

public class BridgePointPreferencesStore implements IPreferenceModelStore {
    public static final String PREFIX = "bridgepoint_prefs_";  //$NON-NLS-1$

    public static final String PARSE_ALL_ON_RESOURCE_CHANGE = PREFIX + "parse_all_on_resource_change"; //$NON-NLS-1$
    public static final String ALLOW_INT_TO_REAL_PROMOTION = PREFIX + "allow_int_to_real_promotion"; //$NON-NLS-1$
    public static final String ALLOW_REAL_TO_INT_COERCION = PREFIX + "allow_real_to_int_coercion"; //$NON-NLS-1$
    public static final String ALLOW_OPERATIONS_IN_WHERE = PREFIX + "allow_operations_in_where"; //$NON-NLS-1$
    public static final String ALLOW_INTERFACE_NAME_IN_IC_MESSAGE = PREFIX + "allow_interface_name_in_ic_message"; //$NON-NLS-1$
    public static final String ALLOW_IMPLICIT_COMPONENT_ADDRESSING = PREFIX + "allow_implicit_component_addressing"; //$NON-NLS-1$
    public static final String ENABLE_PARSE_ON_ACTIVITY_EDITS = PREFIX + "enable_parse_on_activity_edits"; //$NON-NLS-1$
    public static final String ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE = PREFIX + "enable_error_for_empty_synchronous_message"; //$NON-NLS-1$
    public static final String ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED = PREFIX + "enable_error_for_empty_synchronous_message_realized"; //$NON-NLS-1$
    public static final String DISABLE_GRADIENTS = PREFIX + "disable_gradients"; //$NON-NLS-1$
    public static final String INVERT_GRADIENTS = PREFIX + "invert_gradients"; //$NON-NLS-1$
    public static final String GRADIENT_BASE_COLOR = PREFIX + "gradient_base_color"; //$NON-NLS-1$
    public static final String EXPORT_OAL = PREFIX + "export_oal"; //$NON-NLS-1$
    public static final String EXPORT_GRAPHICS = PREFIX + "export_graphics"; //$NON-NLS-1$
    public static final String MESSAGE_DIRECTION = PREFIX + "message_direction";
    public static final String ACTIVITY_PERSISTENCE = PREFIX + "activity_persistence";
    public static final String DEFAULT_ACTION_LANGUAGE_DIALECT = PREFIX + "default_action_language_dialect";
    public static final String SHOW_TRANSITION_ACTIONS = PREFIX + "show_transition_actions"; //$NON-NLS-1$
    public static final String SHOW_EVENT_PARAMETERS = PREFIX + "show_event_parameters"; //$NON-NLS-1$
    public static final String ENABLE_FIXED_LENGTH_ARRAYS = PREFIX + "enable_fixed_length_arrays"; //$NON-NLS-1$
    public static final String ENABLE_DYNAMICALLY_SIZED_ARRAYS = PREFIX + "enable_dynamically_sized_arrays"; //$NON-NLS-1$
    public static final String ENABLE_DETERMINISTIC_VERIFIER = PREFIX + "enable_deterministic_verifier"; //$NON-NLS-1$
    public static final String ENABLE_INSTANCE_REFERENCES = PREFIX + "enable_instance_references"; //$NON-NLS-1$
    public static final String ENABLE_VERIFIER_AUDIT = PREFIX + "enable_verifier_audit"; //$NON-NLS-1$
    public static final String ENABLE_SELECT_AUDIT = PREFIX + "enable_select_audit"; //$NON-NLS-1$
    public static final String ENABLE_RELATE_AUDIT = PREFIX + "enable_relate_audit"; //$NON-NLS-1$
    public static final String ENABLE_UNRELATE_AUDIT = PREFIX + "enable_unrelate_audit"; //$NON-NLS-1$
    public static final String ENABLE_DELETE_AUDIT = PREFIX + "enable_delete_audit"; //$NON-NLS-1$
    public static final String START_UP_TIME = PREFIX + "start_up_time"; //$NON-NLS-1$
    public static final String SHOW_GRID = PREFIX + "showGrid"; //$NON-NLS-1$
    public static final String SNAP_TO_GRID = PREFIX + "snapToGrid"; //$NON-NLS-1$
    public static final String GRID_SPACING = PREFIX + "gridSpacing"; //$NON-NLS-1$
    public static final String EMIT_RTO_DATA = PREFIX + "emit_rto_data"; //$NON-NLS-1$
	public static final String DEFAULT_ROUTING_STYLE = PREFIX + "defaultRoutingStyle"; //$NON-NLS-1$
	public static final String SHOW_FORMALIZATIONS = PREFIX + "showFormalizations"; //$NON-NLS-1$
    public static final String SHOW_SYNC_DELETION_DIALOG = PREFIX + "show_reference_delete_warning"; //$NON-NLS-1$
    public static final String SHOW_SYNC_REPORT = PREFIX + "show_reference_sync_report"; //$NON-NLS-1$
    public static final String USE_DEFAULT_NAME_FOR_CREATION = PREFIX + "use_default_name_for_new_element_creation"; //$NON-NLS-1$
    public static final String REQUIRE_MASL_STYLE_IDENTIFIERS = PREFIX + "require_masl_style_identifiers"; //$NON-NLS-1$
    public static final String OPAQUE_COMPONENTS = PREFIX + "opaque_components"; //$NON-NLS-1$
    public static final String ENABLE_MODEL_INTEGRITY_CHECK = PREFIX + "enable_model_integrity_check"; //$NON-NLS-1$
	public final static String EnableModelIntegrityToolTip = "When enabled, model integrity checks run during most BridgePoint model opertions and validate the integrity of the model for the opertion performed. This can be useful to assure there is no model corruption, but there is performance overhead when enabled.";
	public static final String ENABLE_TABLE_BASED_ASSOCIATION_EDITING = PREFIX + "enable_table_based_association_editing"; //$NON-NLS-1$

	public static final String CONTENT_ASSIST_INCLUDE_ROLE_PHRASES = PREFIX + "content_assist_include_role_phrases"; //$NON-NLS-1$
	public static final String CONTENT_ASSIST_INSERT_SINGLE_PROPOSALS = PREFIX + "content_assist_insert_single_proposals"; //$NON-NLS-1$
	public static final String CONTENT_ASSIST_INVOCATION_FORMAT = PREFIX + "content_assist_invocation_format"; //$NON-NLS-1$
	public static final String CONTENT_ASSIST_ENABLE_AUTO_TRIGGERING = PREFIX + "content_assist_enable_auto_triggering"; //$NON-NLS-1$
	public static final String CONTENT_ASSIST_AUTO_TRIGGER_SEQUENCES = PREFIX + "content_assist_auto_trigger_sequences"; //$NON-NLS-1$
	public static final String CONTENT_ASSIST_ENABLE_PARTIAL_PARSING = PREFIX + "content_assist_enable_partial_parsing";

	public static final String ALLOW_CONCRETE_POLYS = PREFIX + "allow_concrete_polys";

	public static final String RECTILINEAR_ROUTING = "RECTILINEAR_ROUTING"; //$NON-NLS-1$

	public static final String OBLIQUE_ROUTING = "OBLIQUE_ROUTING"; //$NON-NLS-1$

	public static final String TO_PROVIDER = "to provider"; //$NON-NLS-1$
	
	public static final String PERSIST_ACTIVITY_FILES = "persist_activity_files";
	public static final String NO_PERSIST_ACTIVITY_FILES = "no_persist_activity_files";
		
    public Class getModelClass() {
        return BridgePointPreferencesModel.class;
    }

    public void saveModel(IPreferenceStore store, IPreferenceModel model) {
        if (!(model instanceof BridgePointPreferencesModel)) {
            throw new IllegalArgumentException("Cannot save instance of " + model.getClass().getName());
        }

        BridgePointPreferencesModel prefs = (BridgePointPreferencesModel) model;

        store.setValue(PARSE_ALL_ON_RESOURCE_CHANGE, prefs.parseAllOnResourceChange);
        store.setValue(ALLOW_INT_TO_REAL_PROMOTION, prefs.allowIntToRealPromotion);
        store.setValue(ALLOW_REAL_TO_INT_COERCION, prefs.allowRealToIntCoercion);
        store.setValue(ALLOW_IMPLICIT_COMPONENT_ADDRESSING, prefs.allowImplicitComponentAddressing);
        store.setValue(ENABLE_PARSE_ON_ACTIVITY_EDITS, prefs.enableParseOnActivtyEdits);
        store.setValue(ALLOW_OPERATIONS_IN_WHERE, prefs.allowOperationsInWhere);
        store.setValue(ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, prefs.allowInterfaceNameInICMessage);
        store.setValue(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE, prefs.enableErrorForEmptySynchronousMessage);
        store.setValue(ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED, prefs.enableErrorForEmptySynchronousMessageRealized);
        store.setValue(DISABLE_GRADIENTS, prefs.disableGradients);
        store.setValue(INVERT_GRADIENTS, prefs.invertGradients);
        store.setValue(GRADIENT_BASE_COLOR, prefs.gradientBaseColor);
        store.setValue(EXPORT_OAL, prefs.exportOAL);
        store.setValue(EXPORT_GRAPHICS, prefs.exportGraphics);
        store.setValue(MESSAGE_DIRECTION, prefs.messageDirection); 
        store.setValue(ACTIVITY_PERSISTENCE, prefs.activityPersistenceAsFiles);
        store.setValue(DEFAULT_ACTION_LANGUAGE_DIALECT, prefs.defaultActionLanguageDialect); 
        store.setValue(SHOW_TRANSITION_ACTIONS, prefs.showTransitionActions);
        store.setValue(SHOW_EVENT_PARAMETERS, prefs.showEventParameters);
        store.setValue(ENABLE_FIXED_LENGTH_ARRAYS, prefs.enableFLAs);
        store.setValue(ENABLE_DYNAMICALLY_SIZED_ARRAYS, prefs.enableDSAs);
        store.setValue(ENABLE_DETERMINISTIC_VERIFIER, prefs.enableDeterministicVerifier);
        store.setValue(ENABLE_INSTANCE_REFERENCES, prefs.enableInstanceReferences);
        store.setValue(ENABLE_MODEL_INTEGRITY_CHECK, prefs.enableModelIntegrityCheck);
        store.setValue(ENABLE_TABLE_BASED_ASSOCIATION_EDITING, prefs.enableTableBasedAssociationEditing);

        store.setValue(CONTENT_ASSIST_INCLUDE_ROLE_PHRASES, prefs.contentAssistIncludeRolePhrases);
        store.setValue(CONTENT_ASSIST_INSERT_SINGLE_PROPOSALS, prefs.contentAssistInsertSingleProposals);
        store.setValue(CONTENT_ASSIST_INVOCATION_FORMAT, prefs.contentAssistInvocationFormat);
        store.setValue(CONTENT_ASSIST_ENABLE_AUTO_TRIGGERING, prefs.contentAssistEnableAutoTriggering);
        store.setValue(CONTENT_ASSIST_AUTO_TRIGGER_SEQUENCES, prefs.contentAssistAutoTriggerSequences);
        store.setValue(CONTENT_ASSIST_ENABLE_PARTIAL_PARSING, prefs.contentAssistEnablePartialParsing);

        store.setValue(ALLOW_CONCRETE_POLYS, prefs.allowConcretePolys);
                

        store.setValue(ENABLE_VERIFIER_AUDIT, prefs.enableVerifierAudit);
        store.setValue(ENABLE_SELECT_AUDIT, prefs.enableSelectAudit);
        store.setValue(ENABLE_RELATE_AUDIT, prefs.enableRelateAudit);
        store.setValue(ENABLE_UNRELATE_AUDIT, prefs.enableUnrelateAudit);
        store.setValue(ENABLE_DELETE_AUDIT, prefs.enableDeleteAudit);
        store.setValue(START_UP_TIME, prefs.startUpTime);
        
        store.setValue(SHOW_GRID, prefs.showGrid);
        store.setValue(SNAP_TO_GRID, prefs.snapToGrid);
        store.setValue(GRID_SPACING, prefs.gridSpacing);
        store.setValue(DEFAULT_ROUTING_STYLE, prefs.defaultRoutingStyle);
        store.setValue(EMIT_RTO_DATA, prefs.emitRTOData);
        store.setValue(SHOW_SYNC_DELETION_DIALOG, prefs.showReferenceRemovalDialog);
        store.setValue(SHOW_SYNC_REPORT, prefs.showReferenceSyncReport);
        store.setValue(USE_DEFAULT_NAME_FOR_CREATION, prefs.useDefaultNamesForNewModelElements);        
        store.setValue(REQUIRE_MASL_STYLE_IDENTIFIERS, prefs.requireMaslStyleIdentifiers);
        store.setValue(OPAQUE_COMPONENTS, prefs.opaqueComponents);
        store.setValue(SHOW_FORMALIZATIONS, prefs.showFormalizations);
    }

    public IPreferenceModel loadModel(IPreferenceStore store, BasePlugin plugin, IPreferenceModel model) {
        BridgePointPreferencesModel prefs = null;

        if (model == null)
            prefs = new BridgePointPreferencesModel();
        else {
            if (!(model instanceof BridgePointPreferencesModel)) {
                throw new IllegalArgumentException("Cannot load instance of " + model.getClass().getName());
            }
            prefs = (BridgePointPreferencesModel) model;
        }

        prefs.parseAllOnResourceChange =
            store.getString(BridgePointPreferencesStore.PARSE_ALL_ON_RESOURCE_CHANGE);
        prefs.allowIntToRealPromotion =
            store.getString(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION);
        prefs.allowRealToIntCoercion =
            store.getString(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION);
        prefs.allowImplicitComponentAddressing =
            store.getBoolean(BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING);
        prefs.enableParseOnActivtyEdits =
                store.getBoolean(BridgePointPreferencesStore.ENABLE_PARSE_ON_ACTIVITY_EDITS);
        prefs.allowOperationsInWhere =
            store.getBoolean(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE);
        prefs.allowInterfaceNameInICMessage = 
        	store.getBoolean(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE);
		prefs.enableErrorForEmptySynchronousMessage = store
				.getBoolean(BridgePointPreferencesStore.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE);
		prefs.enableErrorForEmptySynchronousMessageRealized = store
				.getBoolean(BridgePointPreferencesStore.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED);
		prefs.showFormalizations = store.getBoolean(BridgePointPreferencesStore.SHOW_FORMALIZATIONS);
        prefs.disableGradients =
            store.getBoolean(BridgePointPreferencesStore.DISABLE_GRADIENTS);
        prefs.invertGradients =
            store.getBoolean(BridgePointPreferencesStore.INVERT_GRADIENTS);
        prefs.gradientBaseColor =
            store.getLong(BridgePointPreferencesStore.GRADIENT_BASE_COLOR);
        if ( prefs.gradientBaseColor == 0L ) {
            prefs.gradientBaseColor = 0xc8c8c8;
        }
        prefs.exportOAL =
            store.getString(BridgePointPreferencesStore.EXPORT_OAL);
        prefs.exportGraphics =
            store.getString(BridgePointPreferencesStore.EXPORT_GRAPHICS);
        prefs.messageDirection =
            store.getString(BridgePointPreferencesStore.MESSAGE_DIRECTION);
        prefs.activityPersistenceAsFiles =
            store.getString(BridgePointPreferencesStore.ACTIVITY_PERSISTENCE);
        prefs.showTransitionActions =
            store.getBoolean(BridgePointPreferencesStore.SHOW_TRANSITION_ACTIONS);
        prefs.showEventParameters =
            store.getBoolean(BridgePointPreferencesStore.SHOW_EVENT_PARAMETERS);

    	prefs.enableFLAs =
    		store.getBoolean(BridgePointPreferencesStore.ENABLE_FIXED_LENGTH_ARRAYS);
        prefs.enableDSAs =
            store.getBoolean(BridgePointPreferencesStore.ENABLE_DYNAMICALLY_SIZED_ARRAYS);

        prefs.enableDeterministicVerifier =
            store.getBoolean(BridgePointPreferencesStore.ENABLE_DETERMINISTIC_VERIFIER);

        prefs.enableInstanceReferences =
            store.getBoolean(BridgePointPreferencesStore.ENABLE_INSTANCE_REFERENCES);
        
        prefs.enableModelIntegrityCheck =
                store.getBoolean(ENABLE_MODEL_INTEGRITY_CHECK);
        
        prefs.enableTableBasedAssociationEditing =
        		store.getBoolean(ENABLE_TABLE_BASED_ASSOCIATION_EDITING);
        
        prefs.contentAssistIncludeRolePhrases = store.getBoolean( CONTENT_ASSIST_INCLUDE_ROLE_PHRASES );
        prefs.contentAssistInsertSingleProposals = store.getBoolean( CONTENT_ASSIST_INSERT_SINGLE_PROPOSALS );
        prefs.contentAssistInvocationFormat = store.getString( CONTENT_ASSIST_INVOCATION_FORMAT );
        prefs.contentAssistEnableAutoTriggering = store.getBoolean( CONTENT_ASSIST_ENABLE_AUTO_TRIGGERING );
        prefs.contentAssistAutoTriggerSequences = store.getString( CONTENT_ASSIST_AUTO_TRIGGER_SEQUENCES );
        prefs.contentAssistEnablePartialParsing = store.getBoolean( CONTENT_ASSIST_ENABLE_PARTIAL_PARSING );

        prefs.allowConcretePolys = store.getBoolean( ALLOW_CONCRETE_POLYS );
        
        prefs.enableVerifierAudit =
          store.getBoolean(BridgePointPreferencesStore.ENABLE_VERIFIER_AUDIT);
        prefs.enableSelectAudit =
          store.getInt(BridgePointPreferencesStore.ENABLE_SELECT_AUDIT);
        prefs.enableRelateAudit =
          store.getInt(BridgePointPreferencesStore.ENABLE_RELATE_AUDIT);
        prefs.enableUnrelateAudit =
          store.getInt(BridgePointPreferencesStore.ENABLE_UNRELATE_AUDIT);
        prefs.enableDeleteAudit =
          store.getInt(BridgePointPreferencesStore.ENABLE_DELETE_AUDIT);
        prefs.startUpTime =
          store.getInt(BridgePointPreferencesStore.START_UP_TIME);
        prefs.defaultActionLanguageDialect =
            store.getInt(BridgePointPreferencesStore.DEFAULT_ACTION_LANGUAGE_DIALECT);
        
        prefs.showGrid = store.getBoolean(BridgePointPreferencesStore.SHOW_GRID);
        prefs.snapToGrid = store.getBoolean(BridgePointPreferencesStore.SNAP_TO_GRID);
        prefs.gridSpacing = store.getInt(BridgePointPreferencesStore.GRID_SPACING);
        prefs.defaultRoutingStyle = store.getString(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE);
        prefs.emitRTOData = store.getBoolean(BridgePointPreferencesStore.EMIT_RTO_DATA);
        prefs.showReferenceRemovalDialog = store.getBoolean(BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG);
        prefs.showReferenceSyncReport = store.getBoolean(BridgePointPreferencesStore.SHOW_SYNC_REPORT);
        
        prefs.useDefaultNamesForNewModelElements =
            store.getBoolean(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION);
        prefs.requireMaslStyleIdentifiers =
        	store.getBoolean(BridgePointPreferencesStore.REQUIRE_MASL_STYLE_IDENTIFIERS);
        prefs.opaqueComponents =
        	store.getBoolean(BridgePointPreferencesStore.OPAQUE_COMPONENTS);
                
        return prefs;
    }

    public void restoreModelDefaults(IPreferenceModel model) {

        if (model == null)
            throw new IllegalArgumentException("Cannot restore a null model");
        else if (!(model instanceof BridgePointPreferencesModel)) {
            throw new IllegalArgumentException("Cannot load instance of " + model.getClass().getName());
        }

        BridgePointPreferencesModel prefs = (BridgePointPreferencesModel) model;

        prefs.parseAllOnResourceChange = MessageDialogWithToggle.NEVER;
        prefs.allowIntToRealPromotion = MessageDialogWithToggle.ALWAYS;
        prefs.allowRealToIntCoercion = MessageDialogWithToggle.ALWAYS;
        prefs.allowImplicitComponentAddressing = false;
        prefs.enableParseOnActivtyEdits = true;
        prefs.allowOperationsInWhere = false;
        prefs.allowInterfaceNameInICMessage = false;
        prefs.enableErrorForEmptySynchronousMessage = false;
        prefs.enableErrorForEmptySynchronousMessageRealized = false;
        prefs.disableGradients = true;
        prefs.invertGradients = false;
        prefs.gradientBaseColor = 0xc8c8c8;
        prefs.showTransitionActions = false;
        prefs.showEventParameters = true;
        prefs.enableFLAs = true;
        prefs.enableDSAs = true;
        prefs.enableInstanceReferences = true;
        prefs.enableModelIntegrityCheck = true;
        prefs.emitRTOData = true;        
        prefs.exportOAL = MessageDialogWithToggle.NEVER;
        prefs.exportGraphics = MessageDialogWithToggle.ALWAYS;
        prefs.messageDirection = TO_PROVIDER;
        prefs.activityPersistenceAsFiles = PERSIST_ACTIVITY_FILES;
        prefs.defaultActionLanguageDialect = Actiondialect_c.oal;
        prefs.enableTableBasedAssociationEditing = false;
        
        prefs.contentAssistIncludeRolePhrases = true;
        prefs.contentAssistInsertSingleProposals = true;
        prefs.contentAssistInvocationFormat = "labels";
        prefs.contentAssistEnableAutoTriggering = true;
        prefs.contentAssistAutoTriggerSequences = ".\n::\n->\n,";
        prefs.contentAssistEnablePartialParsing = false;

        prefs.allowConcretePolys = false;
        
        prefs.enableVerifierAudit = false;
        prefs.enableSelectAudit = 1;
        prefs.enableRelateAudit = 1;
        prefs.enableUnrelateAudit = 1;
        prefs.enableDeleteAudit = 1;
        
        prefs.startUpTime = 5;
        
        prefs.showGrid = false;
        prefs.snapToGrid = true;
        prefs.gridSpacing = 12;
        prefs.defaultRoutingStyle = OBLIQUE_ROUTING;
        prefs.enableDeterministicVerifier = true;
        prefs.showReferenceRemovalDialog = true;
        prefs.showReferenceSyncReport = true;
        prefs.useDefaultNamesForNewModelElements = false;
        prefs.requireMaslStyleIdentifiers = false;
        prefs.opaqueComponents = false;
        prefs.showFormalizations = true;
    }
}
