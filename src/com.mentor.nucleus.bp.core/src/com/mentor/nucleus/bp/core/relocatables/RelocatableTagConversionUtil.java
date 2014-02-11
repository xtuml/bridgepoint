//=====================================================================
//
//File:      $RCSfile: RelocatableTagConversionUtil.java,v $
//Version:   $Revision: 1.9 $
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

import java.util.UUID;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;

/**
 * Holds methods which perform the conversion of relocatables tags within
 * an activity's text to the relocatable values they reference. 
 */
public class RelocatableTagConversionUtil
{
    /**
     * Returns the value of the given field of the given tag. 
     */
    private static String getTagFieldValue(String tag, int field)
    {
        // for each field up to the one requested; the -1 starting index is
        // because there's a field-separator before the first field
        int startIndex = 2;
        char tagFieldSeparator = Relocatables.tagFieldSeparator;
        for (int i = -1; i < field; i++) {
            // find the start of this field
            startIndex = tag.indexOf(tagFieldSeparator, startIndex) + 1;
            
            // if we've run out of fields, there's nothing to return
            if (startIndex == -1) return null;
        }
        
        // find the end of the requested field
        int endIndex = tag.indexOf(tagFieldSeparator, startIndex);
        if (endIndex == -1) endIndex = tag.length() - 1;
        
        // extract and return the requested field
        return tag.substring(startIndex, endIndex);
    }
    
    /**
     * Returns an array of the instance IDs found in the given tag-field value.
     * 
     * Right now, for performance (in terms of not having to create a 
     * list into which to store the IDs as they are being parsed), the 
     * array's length is fixed at one element (since that is all that is 
     * needed at this time.  This value can be increased (if necessary) 
     * without affecting existing clients.  Clients are expected to know how 
     * many of the array's slots actually contain IDs, based on the relocatable 
     * type.
     */
    private static UUID[] getInstanceIds(String fieldValue)
    {
        // keep doing this
        int startIndex = 0;
        UUID[] ids = new UUID[1];
        int idsIndex = 0;
        while (true) {
            // find the next ID-separator in the field, if there is one
            int endIndex = fieldValue.indexOf(
                Relocatables.idSeparator, startIndex);
            
            // extract the next ID from the field
            ids[idsIndex] = UUID.fromString(
                fieldValue.substring(startIndex, 
                    endIndex >= 0 ? endIndex : fieldValue.length()));
            
            // if this was the last ID in the field, we're done
            if (endIndex < 0) break;
            
            // advance to the next ID 
            startIndex = endIndex + 1;
            idsIndex++;
        }
        
        return ids;
    }
    
    /**
     * Returns the result of replacing each tag within the given text 
     * with the value of the relocatable it references.
     * 
     * Each tag's relocatable is located using the IDs found in the tag.
     * If the relocatable cannot be located, the tag is replaced with the 
     * value of the tag's last-known-good-value field.
     * 
     * @param modelRoot     That to which the relocatables belong.     
     */
    // relocatables functionality is disabled in this release 
    public static String convertRelocatableTags(
        ModelRoot modelRoot, String text)
    {
        return text;
    }
    private static String convertRelocatableTags_(
        ModelRoot modelRoot, String text)
    {
        StringBuffer result = new StringBuffer(text);
        
        // keep doing this, starting from the end of the given text
        int startIndex = text.length();
        while (true) {
            // find the next relocatable reference to convert, going backwards;
            // if there is none, we are done
            startIndex = text.lastIndexOf(Relocatables.tagStart, startIndex - 1);
            if (startIndex == -1) break;
            int endIndex = text.indexOf(Relocatables.tagEnd, startIndex);
            String tag = text.substring(startIndex, endIndex + 1);

            // if we can detm the text to substitute for the tag within the 
            // result
            String substitution = getTextToSubstituteForTag(
                modelRoot, tag);
            if (substitution != null) {
                // perform the substitution 
                result.replace(startIndex, endIndex + 1, substitution);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Returns the value of the relocatable referenced by the given tag, or 
     * with the tag's last-known-good-value if the relocatable cannot be 
     * located.
     * 
     * @param modelRoot     That to which the relocatable belongs.     
     */
    private static String getTextToSubstituteForTag(
        ModelRoot modelRoot, String tag)
    {
        // find the relocatable type specified in the relocatable reference
        String relocatableType = getTagFieldValue(tag, Relocatables.TagFields.type);

        // detm the instance-IDs specified in the relocatable reference
        final UUID[] instanceIds = getInstanceIds(
            getTagFieldValue(tag, Relocatables.TagFields.instanceIds));

        // if the relocatable is an attribute name
        String text = null;
        if (relocatableType.equals(Relocatables.Types.attributeName)) {
            // if we can find an attribute instance using the IDs detm'd above 
            Attribute_c attribute = Attribute_c.AttributeInstance(
                modelRoot,
                new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        Attribute_c attribute = (Attribute_c)candidate;
                        return attribute.getAttr_id().equals(instanceIds[0]);
                    }
                });
            if (attribute != null) {
                // the text to substitute is the attribute's name
                text = attribute.getName();
            }
        }

        // else, if the relocatable is a function parameter name
        else if (relocatableType.equals(Relocatables.Types.functionParameterName)) {
            // if we can find a function parameter instance using the IDs 
            // detm'd above 
            FunctionParameter_c param = 
                FunctionParameter_c.FunctionParameterInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((FunctionParameter_c)candidate).
                                getSparm_id().equals(instanceIds[0]);
                        }
                    });
            if (param != null) {
                // the text to substitute is the parameter's name
                text = param.getName();
            }
        }
        
        // else, if the relocatable is an operation parameter name
        else if (relocatableType.equals(Relocatables.Types.operationParameterName)) {
            // if we can find an operation parameter instance using the IDs 
            // detm'd above 
            OperationParameter_c param = 
                OperationParameter_c.OperationParameterInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((OperationParameter_c)candidate).
                                getTparm_id().equals(instanceIds[0]);
                        }
                    });
            if (param != null) {
                // the text to substitute is the parameter's name
                text = param.getName();
            }
        }
        
        // else, if the relocatable is a bridge parameter name
        else if (relocatableType.equals(Relocatables.Types.bridgeParameterName)) {
            // if we can find a bridge parameter instance using the IDs 
            // detm'd above 
            BridgeParameter_c param = 
                BridgeParameter_c.BridgeParameterInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((BridgeParameter_c)candidate).
                                getBparm_id().equals(instanceIds[0]);
                        }
                    });
            if (param != null) {
                // the text to substitute is the parameter's name
                text = param.getName();
            }
        }
        
        // else, if the relocatable is a function name
        else if (relocatableType.equals(Relocatables.Types.functionName)) {
            // if we can find a function instance using the IDs 
            // detm'd above 
            Function_c function = 
                Function_c.FunctionInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((Function_c)candidate).getSync_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (function != null) {
                // the text to substitute is the function's name
                text = function.getName();
            }
        }
        
        // else, if the relocatable is an operation name
        else if (relocatableType.equals(Relocatables.Types.operationName)) {
            // if we can find an operation instance using the IDs 
            // detm'd above 
            Operation_c operation = 
                Operation_c.OperationInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((Operation_c)candidate).getTfr_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (operation != null) {
                // the text to substitute is the operation's name
                text = operation.getName();
            }
        }
        
        // else, if the relocatable is a bridge name
        else if (relocatableType.equals(Relocatables.Types.bridgeName)) {
            // if we can find a bridge instance using the IDs 
            // detm'd above 
            Bridge_c bridge = 
                Bridge_c.BridgeInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((Bridge_c)candidate).getBrg_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (bridge != null) {
                // the text to substitute is the bridge's name
                text = bridge.getName();
            }
        }

        // else, if the relocatable is a model-class's key-letters
        else if (relocatableType.equals(Relocatables.Types.modelClassKeyLetters)) {
            // if we can find the model class instance using the IDs 
            // detm'd above 
            ModelClass_c clazz = 
                ModelClass_c.ModelClassInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((ModelClass_c)candidate).getObj_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (clazz != null) {
                // the text to substitute is the model-class's key-letters
                text = clazz.getKey_lett();
            }
        }

        // else, if the relocatable is an external-entity's key-letters
        else if (relocatableType.equals(
            Relocatables.Types.externalEntityKeyLetters)) {
            // if we can find the external-entity instance using the IDs 
            // detm'd above 
            ExternalEntity_c entity = 
                ExternalEntity_c.ExternalEntityInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((ExternalEntity_c)candidate).getEe_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (entity != null) {
                // the text to substitute is the external-entity's key-letters
                text = entity.getKey_lett();
            }
        }

        // else, if the relocatable is an association number
        else if (relocatableType.equals(Relocatables.Types.associationNumber)) {
            // if we can find the association instance using the IDs 
            // detm'd above 
            Association_c assoc = 
                Association_c.AssociationInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((Association_c)candidate).getRel_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (assoc != null) {
                // the text to substitute is the association's number
                text = Integer.toString(assoc.getNumb());
            }
        }

        // else, if the relocatable is an association phrase
        else if (relocatableType.equals(Relocatables.Types.associationPhrase)) {
            // if we can find the class-in-association instance using the IDs 
            // detm'd above 
            ClassInAssociation_c inAssoc = 
                ClassInAssociation_c.ClassInAssociationInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            ClassInAssociation_c inAssoc = 
                                (ClassInAssociation_c)candidate;
                            return inAssoc.getOir_id().equals(instanceIds[0]);
                        }
                    });
            if (inAssoc != null) {
                // the text to substitute is the class-in-association's 
                // text phrase
                text = inAssoc.Get_text_phrase();
            }
        }

        // else, if the relocatable is a state-machine-event derived-label
        // or meaning
        else if (relocatableType.equals(
            Relocatables.Types.stateMachineEventDerivedLabel)
            || relocatableType.equals(Relocatables.Types.stateMachineEventMeaning)) {
            // if we can find the event instance using the IDs 
            // detm'd above 
            StateMachineEvent_c event = 
                StateMachineEvent_c.StateMachineEventInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            StateMachineEvent_c event = 
                                (StateMachineEvent_c)candidate;
                            return event.getSmevt_id().equals(instanceIds[0]);
                        }
                    });
            if (event != null) {
                // the text to substitute is the event's derived label 
                // or meaning
                text = relocatableType.equals(
                    Relocatables.Types.stateMachineEventDerivedLabel) ?
                    event.getDrv_lbl() : event.getMning();
            }
        }

        // else, if the relocatable is a state-machine-event data item name
        else if (relocatableType.equals(
            Relocatables.Types.stateMachineEventDataItemName)) {
            // if we can find an event data item instance using the IDs 
            // detm'd above 
            StateMachineEventDataItem_c item = 
                StateMachineEventDataItem_c.StateMachineEventDataItemInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            StateMachineEventDataItem_c item = 
                                (StateMachineEventDataItem_c)candidate;
                            return item.getSmedi_id().equals(instanceIds[0]);
                        }
                    });
            if (item != null) {
                // the text to substitute is the event data item's name
                text = item.getName();
            }
        }

        // else, if the relocatable is an external-entity-event derived-label
        // or meaning 
        // [NOTE: there is currently no UI-visible support for 
        // external-entity events in the tool, so this code is untested]
        else if (relocatableType.equals(
            Relocatables.Types.externalEntityEventDerivedLabel)
            || relocatableType.equals(
                Relocatables.Types.externalEntityEventMeaning)) {
            // if we can find the event instance using the IDs 
            // detm'd above 
            ExternalEntityEvent_c event = 
                ExternalEntityEvent_c.ExternalEntityEventInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            ExternalEntityEvent_c event = 
                                (ExternalEntityEvent_c)candidate;
                            return event.getEeevt_id().equals(instanceIds[0]);
                        }
                    });
            if (event != null) {
                // the text to substitute is the event's derived label 
                // or meaning
                text = relocatableType.equals(
                    Relocatables.Types.externalEntityEventDerivedLabel) ?
                    event.getDrv_lbl() : event.getMning();
            }
        }

        // else, if the relocatable is an external-entity-event data item name
        // [NOTE: there is currently no UI-visible support for 
        // external-entity events in the tool, so this code is untested]
        else if (relocatableType.equals(
            Relocatables.Types.externalEntityEventDataItemName)) {
            // if we can find an event data item instance using the IDs 
            // detm'd above 
            ExternalEntityEventDataItem_c item = 
                ExternalEntityEventDataItem_c.ExternalEntityEventDataItemInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            ExternalEntityEventDataItem_c item = 
                                (ExternalEntityEventDataItem_c)candidate;
                            return item.getEeedi_id().equals(instanceIds[0]);
                        }
                    });
            if (item != null) {
                // the text to substitute is the event data item's name
                text = item.getName();
            }
        }

        // else, if the relocatable is a data-type name
        else if (relocatableType.equals(Relocatables.Types.dataTypeName)) {
            // if we can find a data-type instance using the IDs 
            // detm'd above 
            DataType_c dataType = 
                DataType_c.DataTypeInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((DataType_c)candidate).getDt_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (dataType != null) {
                // the text to substitute is the data-type's name
                text = dataType.getName();
            }
        }

        // else, if the relocatable is an enumerator name
        else if (relocatableType.equals(Relocatables.Types.enumeratorName)) {
            // if we can find an enumerator instance using the IDs 
            // detm'd above 
            Enumerator_c enumerator = 
                Enumerator_c.EnumeratorInstance(
                    modelRoot,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((Enumerator_c)candidate).getEnum_id().
                                equals(instanceIds[0]);
                        }
                    });
            if (enumerator != null) {
                // the text to substitute is the enumerator's name
                text = enumerator.getName();
            }
        }

        // if no text could be detm'd for the tag, above
        if (text == null) {
            // go with what the tag says is the last known good value
            text = getTagFieldValue(tag, Relocatables.TagFields.lastKnownGoodValue);
        }
        
        return text;
    }
}
