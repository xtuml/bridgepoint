//=====================================================================
//
//File:      $RCSfile: Relocatables.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:29:27 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.core.relocatables;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelElement;

/**
 * Holds values and methods whose usage is common to the other classes in
 * this package.
 */
public class Relocatables
{
    /**
     * Signifies the start of a relocatable tag within an activity text.
     */
    public static final String tagStart = "${#@!";
    
    /**
     * Signifies the end of a relocatable tag within an activity text.
     */
    public static final char tagEnd = '}';
    
    /**
     * Separates fields within a relocatable tag.
     */
    public static final char tagFieldSeparator = ':';

    /**
     * Separates ID's within the instance-IDs field of a relocatable tag.
     */
    public static final char idSeparator = ',';
    
    /**
     * What the different fields of a relocatable tag signify.
     */
    public static class TagFields
    {
        public static final int  
            type = 0,
            instanceIds = 1,
            lastKnownGoodValue = 2;
    }

    /**
     * The different types of relocatables.
     */
    public static class Types
    {
        public static final String 
            associationNumber = "associationNumber",
            attributeName = "attributeName",
            bridgeName = "bridgeName",
            bridgeParameterName = "bridgeParameterName",
            dataTypeName = "dataTypeName",
            enumeratorName = "enumeratorName",
            externalEntityEventDataItemName = "externalEntityEventDataItemName",
            externalEntityEventDerivedLabel = "externalEntityEventDerivedLabel",
            externalEntityEventMeaning = "externalEntityEventMeaning",
            externalEntityKeyLetters = "externalEntityKeyLetters",
            functionName = "functionName",
            functionParameterName = "functionParameterName",
            modelClassKeyLetters = "modelClassKeyLetters",
            operationName = "operationName",
            operationParameterName = "operationParameterName",
            associationPhrase = "phrase",
            stateMachineEventDataItemName = "stateMachineEventDataItemName",
            stateMachineEventDerivedLabel = "stateMachineEventDerivedLabel",
            stateMachineEventMeaning = "stateMachineEventMeaning"
            ; 
    }
    
    /**
     * Returns whether the given model-delta represents a change to the 
     * value of a relocatable.
     */
    public static boolean doesDeltaAffectRelocatable(IModelDelta delta)
    {
        // of the various kinds of model-deltas, only a change to an 
        // attribute can affect a relocatable's value
        if (!(delta instanceof AttributeChangeModelDelta)) return false;
        
        // detm the model element and its attribute whose value was changed
        AttributeChangeModelDelta attributeDelta = 
            (AttributeChangeModelDelta)delta;
        ModelElement element = delta.getModelElement();
        String attributeName = attributeDelta.getAttributeName();
        
        // in what's below, we are looking for a match between the 
        // model-element and attribute detm'd above, and one of the 
        // relocatable types
        
        // detm if the modified attribute is an attribute's name
        boolean relocatableAffected = false;
        relocatableAffected |= 
            element instanceof Attribute_c && attributeName.equals("Root_nam");

        // detm if the modified attribute is the name of an element with 
        // a standard-named name attribute
        relocatableAffected |= attributeName.equals("Name") &&
            (element instanceof FunctionParameter_c 
                || element instanceof OperationParameter_c
                || element instanceof BridgeParameter_c
                || element instanceof Function_c 
                || element instanceof Operation_c
                || element instanceof Bridge_c
                || element instanceof StateMachineEventDataItem_c
                || element instanceof DataType_c
                || element instanceof Enumerator_c);
        
        // detm if the modified attribute is the key-letters of a model-class
        // or external entity
        relocatableAffected |= 
            (element instanceof ModelClass_c 
                || element instanceof ExternalEntity_c)
            && attributeName.equals("Key_lett");

        // detm if the modified attribute is the number of an association 
        // or event
        relocatableAffected |= 
            (element instanceof Association_c
                || element instanceof StateMachineEvent_c
                || element instanceof ExternalEntityEvent_c)
            && attributeName.equals("Numb");

        // detm if the modified attribute is an event's meaning
        relocatableAffected |= 
            (element instanceof StateMachineEvent_c
                || element instanceof ExternalEntityEvent_c)
            && attributeName.equals("Mning");

        // detm if the modified attribute is a text-phrase
        relocatableAffected |= attributeName.equals("Txt_phrs");

        return relocatableAffected;
    }
}
