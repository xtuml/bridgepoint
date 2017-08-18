//========================================================================
//
//File:      $RCSfile: BridgePointPreferencesModel.java,v $
//Version:   $Revision: 1.26 $
//Modified:  $Date: 2013/01/10 22:54:09 $
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

import org.xtuml.bp.ui.preference.IPreferenceModel;
import org.xtuml.bp.ui.preference.IPreferenceModelStore;

public class BridgePointPreferencesModel implements IPreferenceModel {

	static BridgePointPreferencesStore store = null;
	static {
		store = new BridgePointPreferencesStore();
	}

    public String parseAllOnResourceChange;
    public String allowIntToRealPromotion;
    public String allowRealToIntCoercion;
    public boolean allowImplicitComponentAddressing;
    public boolean enableParseOnActivtyEdits;
    public boolean allowOperationsInWhere;
    public boolean allowInterfaceNameInICMessage;
    public boolean enableErrorForEmptySynchronousMessage;
    public boolean enableErrorForEmptySynchronousMessageRealized;
    public boolean disableGradients;
    public boolean invertGradients;
    public long gradientBaseColor;
    public String exportOAL;
    public String exportGraphics;
    public String messageDirection;  
    public String activityPersistenceAsFiles;  
    public int defaultActionLanguageDialect;  
    public boolean showTransitionActions;
	public boolean showEventParameters;
    public boolean enableFLAs;
    public boolean enableDSAs;
    public boolean enableDeterministicVerifier;
    public boolean enableInstanceReferences;
    public boolean enableModelIntegrityCheck;
    public boolean enableVerifierAudit;
    public int enableSelectAudit;
    public int enableRelateAudit;
    public int enableUnrelateAudit;
    public int enableDeleteAudit;
    public int startUpTime;
	public boolean showGrid;
	public boolean snapToGrid;
	public int gridSpacing;
	public String defaultRoutingStyle;
    public boolean emitRTOData;
	public boolean showReferenceRemovalDialog;
	public boolean showReferenceSyncReport;
	public boolean useDefaultNamesForNewModelElements;
    public boolean requireMaslStyleIdentifiers;
    public boolean opaqueComponents;
	public boolean showFormalizations;
    public boolean enableTableBasedAssociationEditing;
    
	public Class getStoreClass() {
		return BridgePointPreferencesStore.class;
	}

	public IPreferenceModelStore getStore() {
		return store;
	}

	public void synchronizeTo(IPreferenceModel model) {
		if (!(model instanceof BridgePointPreferencesModel)) {
			throw new IllegalArgumentException("Cannot synchronize to instance of " + model.getClass().getName());
		}

		BridgePointPreferencesModel syncTo = (BridgePointPreferencesModel) model;
        parseAllOnResourceChange = syncTo.parseAllOnResourceChange;
        allowIntToRealPromotion = syncTo.allowIntToRealPromotion;
        allowRealToIntCoercion = syncTo.allowRealToIntCoercion;
        allowImplicitComponentAddressing = syncTo.allowImplicitComponentAddressing;
        enableParseOnActivtyEdits = syncTo.enableParseOnActivtyEdits;        
        allowOperationsInWhere = syncTo.allowOperationsInWhere;
        allowInterfaceNameInICMessage = syncTo.allowInterfaceNameInICMessage;
        enableErrorForEmptySynchronousMessage = syncTo.enableErrorForEmptySynchronousMessage;
        enableErrorForEmptySynchronousMessageRealized = syncTo.enableErrorForEmptySynchronousMessageRealized;
        disableGradients = syncTo.disableGradients;
        invertGradients = syncTo.invertGradients;
        gradientBaseColor = syncTo.gradientBaseColor;
        showFormalizations = syncTo.showFormalizations;

        emitRTOData = syncTo.emitRTOData;
        exportOAL = syncTo.exportOAL;
        exportGraphics = syncTo.exportGraphics;
        messageDirection = syncTo.messageDirection; 
        activityPersistenceAsFiles = syncTo.activityPersistenceAsFiles;
        defaultActionLanguageDialect = syncTo.defaultActionLanguageDialect; 
        showTransitionActions = syncTo.showTransitionActions;
        showEventParameters = syncTo.showEventParameters;
        enableFLAs = syncTo.enableFLAs;
        enableDSAs = syncTo.enableDSAs;
        enableDeterministicVerifier = syncTo.enableDeterministicVerifier;
        enableInstanceReferences = syncTo.enableInstanceReferences;
        enableModelIntegrityCheck = syncTo.enableModelIntegrityCheck;
        enableTableBasedAssociationEditing = syncTo.enableTableBasedAssociationEditing;

        enableVerifierAudit = syncTo.enableVerifierAudit;
        enableSelectAudit = syncTo.enableSelectAudit;
        enableRelateAudit = syncTo.enableRelateAudit;
        enableUnrelateAudit = syncTo.enableUnrelateAudit;
        enableDeleteAudit = syncTo.enableDeleteAudit;
        showGrid = syncTo.showGrid;
        snapToGrid = syncTo.snapToGrid;
        gridSpacing = syncTo.gridSpacing;
        defaultRoutingStyle = syncTo.defaultRoutingStyle;
        
        startUpTime = syncTo.startUpTime;
        showReferenceRemovalDialog = syncTo.showReferenceRemovalDialog;
        showReferenceSyncReport = syncTo.showReferenceSyncReport;
        useDefaultNamesForNewModelElements = syncTo.useDefaultNamesForNewModelElements;
        requireMaslStyleIdentifiers = syncTo.requireMaslStyleIdentifiers;;
        opaqueComponents = syncTo.opaqueComponents;
	}

    public Object deepClone() {
        BridgePointPreferencesModel prefs = new BridgePointPreferencesModel();
        prefs.parseAllOnResourceChange = parseAllOnResourceChange;
        prefs.allowIntToRealPromotion = allowIntToRealPromotion;
        prefs.allowRealToIntCoercion = allowRealToIntCoercion;
        prefs.allowImplicitComponentAddressing = allowImplicitComponentAddressing;
        prefs.enableParseOnActivtyEdits = enableParseOnActivtyEdits;       
        prefs.allowOperationsInWhere = allowOperationsInWhere;
        prefs.allowInterfaceNameInICMessage = allowInterfaceNameInICMessage;
        prefs.enableErrorForEmptySynchronousMessage = enableErrorForEmptySynchronousMessage;
        prefs.enableErrorForEmptySynchronousMessageRealized = enableErrorForEmptySynchronousMessageRealized;
        prefs.disableGradients = disableGradients;
        prefs.invertGradients = invertGradients;
        prefs.gradientBaseColor = gradientBaseColor;
        prefs.showFormalizations = showFormalizations;

        prefs.emitRTOData = emitRTOData;
        prefs.exportOAL = exportOAL;
        prefs.exportGraphics = exportGraphics;
        prefs.messageDirection = messageDirection;
        prefs.activityPersistenceAsFiles = activityPersistenceAsFiles;
        prefs.defaultActionLanguageDialect = defaultActionLanguageDialect;
        prefs.showTransitionActions = showTransitionActions;
        prefs.showEventParameters = showEventParameters;
        prefs.enableFLAs = enableFLAs;
        prefs.enableDSAs = enableDSAs;
        prefs.enableDeterministicVerifier = enableDeterministicVerifier;
        prefs.enableInstanceReferences = enableInstanceReferences;
        prefs.enableModelIntegrityCheck = enableModelIntegrityCheck;
        prefs.enableTableBasedAssociationEditing = enableTableBasedAssociationEditing;

        prefs.enableVerifierAudit = enableVerifierAudit;
        prefs.enableSelectAudit = enableSelectAudit;
        prefs.enableRelateAudit = enableRelateAudit;
        prefs.enableUnrelateAudit = enableUnrelateAudit;
        prefs.enableDeleteAudit = enableDeleteAudit;

        prefs.startUpTime = startUpTime;

        prefs.showGrid = showGrid;
        prefs.snapToGrid = snapToGrid;
        prefs.gridSpacing = gridSpacing;
        prefs.defaultRoutingStyle = defaultRoutingStyle;
        prefs.showReferenceRemovalDialog = showReferenceRemovalDialog;
        prefs.showReferenceSyncReport = showReferenceSyncReport;
        prefs.useDefaultNamesForNewModelElements = useDefaultNamesForNewModelElements;
        prefs.requireMaslStyleIdentifiers = requireMaslStyleIdentifiers;
        prefs.opaqueComponents = opaqueComponents;

        return prefs;
    }

    public void addModelChangeListener(IChangeListener listener) {
        // do nothing
    }

    public void removeModelChangeListener(IChangeListener listener) {
        // do nothing
    }

}
