//=====================================================================
//
//File:      $RCSfile: RelocatableTagCreationUtil.java,v $
//Version:   $Revision: 1.13 $
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;

/**
 * Holds methods which perform the creation of tags within
 * an activity's text that reference relocatable values. 
 */
public class RelocatableTagCreationUtil
{
    /**
     * Temporarily holds data gathered for a tag which is under construction.
     */
    private static class TagSpec
    {
        /**
         * The starting character index of the tag within its activity's text,
         * from that text's start. 
         */
        int start;
        
        /**
         * The length of the portion of the activity's text which the tag 
         * will be replacing.
         */
        int length;
        
        /**
         * The full text of the tag, to be held for when it is substituted 
         * into the activity's text.
         */
        String tag;
    }

    /**
     * Returns the character index within the given string of the specified 
     * textual position.
     * 
     * @param lineNumber    The line number of the textual position.
     * @param column        The column of the textual position.
     */
    private static int getPositionWithinString(int lineNumber, int column, 
        String string)
    {
        // for each line up to the one given
        int index = 0;
        for (int i = 0; i < lineNumber; i++) {
            // add the length of this line to our result
            index = string.indexOf('\n', index) + 1;
        }
        
        // return the given column number added to our result from above
        return index + column;
    }
    
    /**
     * Replaces each mention of a relocatable within the given activity text 
     * with a referencing tag.  
     * 
     * The activity text must not already have tags in it, meaning that it must 
     * come from an editor, or from an activity that has not previously had 
     * tags created for it.  
     * 
     * The action-language model-elements rooted at the body of the activity
     * associated with the given model-element (which must be a 
     * state-machine-state, a function, an operation, or a bridge) correspond 
     * to the contents of the activity text, and are used to determine where 
     * the tags should go.
     * 
     * @param element       The model-element which owns the activity.
     * @param activityText  The text in which to create the tags.
     * 
     * @return  A copy of the given activity text, with relocatables tags 
     *          substituted.
     */
    // relocatables functionality is disabled in this release 
    public static String createRelocatableTags(
        NonRootModelElement element, String activityText)
    {
        return activityText;
    }
    private static String createRelocatableTags_(
        NonRootModelElement element, String activityText)
        {
        // get the body of the element
        Body_c body = null;
        if (element instanceof StateMachineState_c) {
            Action_c action = 
                Action_c.getOneSM_ACTOnR514(
                    ActionHome_c.getOneSM_AHOnR513(
                        MooreActionHome_c.getOneSM_MOAHOnR511(
                            (StateMachineState_c)element)));
            element = action;
            body = Body_c.getOneACT_ACTOnR698(
                StateActionBody_c.getOneACT_SABOnR691(action));
        }
        else if (element instanceof Function_c) {
            body = Body_c.getOneACT_ACTOnR698(
                FunctionBody_c.getOneACT_FNBOnR695((Function_c)element));
        }
        else if (element instanceof Operation_c) {
            body = Body_c.getOneACT_ACTOnR698(
                OperationBody_c.getOneACT_OPBOnR696((Operation_c)element));
        }
        else if (element instanceof Bridge_c) {
            body = Body_c.getOneACT_ACTOnR698(
                BridgeBody_c.getOneACT_BRBOnR697((Bridge_c)element));
        }
        else if (element instanceof Attribute_c) {
            body = Body_c.getOneACT_ACTOnR698(
                DerivedAttributeBody_c.getOneACT_DABOnR693(
                    DerivedBaseAttribute_c.getOneO_DBATTROnR107(
                        BaseAttribute_c.getOneO_BATTROnR106(
                            (Attribute_c)element))));
        }
        else return activityText;
        
        // get a list of the places where the tags will go (as well as the
        // tags themselves) within the body's block; the list is ordered 
        // as to the position within the activity text
        Block_c block = Block_c.getOneACT_BLKOnR650(body);
        List<TagSpec> tagSpecs = new ArrayList<TagSpec>();
        detmTagSpecsForBlock(block, tagSpecs, activityText);
        
        // for each tag specified above, in reverse order so that substitutions
        // do not affect the indices of the rest of the tags in the list
        StringBuffer newActivityTextBuffer = new StringBuffer(activityText);
        for (int i = tagSpecs.size() - 1; i >= 0; i--) {
            TagSpec tagSpec = (TagSpec)tagSpecs.get(i);
            
            // substitute this tag for the relocatable it references 
            // within the activity text
            newActivityTextBuffer.replace(tagSpec.start, 
                tagSpec.start + tagSpec.length, tagSpec.tag);
        }
        
        return newActivityTextBuffer.toString();
    }
    
    /**
     * Returns a relocatable tag formed from the given parameters.
     *
     * @param   activityText    The activity text into which the tag will be
     *                          substituted.
     * @param   start   The starting character index of the portion of the 
     *                  given activity-text to use as the value of the tag's 
     *                  last-known-good-value field. 
     * @param   length  The length of the activity-text portion described just 
     *                  above.                   
     */
    private static String formTag(String relocatableType,
        UUID[] instanceIds, String activityText, int start, int length)
    {
        // add the start of the tag
        StringBuffer buffer = new StringBuffer();
        buffer.append(Relocatables.tagStart);
        buffer.append(Relocatables.tagFieldSeparator);
        
        // add the type field
        buffer.append(relocatableType);
        
        // for each given instance-ID
        buffer.append(Relocatables.tagFieldSeparator);
        for (int i = 0; i < instanceIds.length; i++) {
            // append this ID
            buffer.append(instanceIds[i]);
            
            // if this isn't the last instance-ID
            if (i < instanceIds.length - 1) {
                // append a separator
                buffer.append(Relocatables.idSeparator);
            }
        }

        // add the last-known-good-value field, populated with the given
        // segment of the given activity text
        buffer.append(Relocatables.tagFieldSeparator);
        buffer.append(activityText.substring(start, start + length));

        // add the tag end
        buffer.append(Relocatables.tagEnd);
        
        return buffer.toString();
    }
    
    /**
     * Creates tag-specs for where tags should go within the given block.
     * 
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given block.
     */
    private static void detmTagSpecsForBlock(Block_c block, 
        List<TagSpec> tagSpecsHolder, String activityText)
    {
        // for each statement in the block
        Statement_c[] statements = Statement_c.getManyACT_SMTsOnR602(block);
        for (int i = 0; i < statements.length; i++) {
            Statement_c statement = statements[i];

            // detm the tag-specs for this statement; don't allow an exception
            // here to prevent the processing of later statements
            int statementPosition = getPositionWithinString(
                statement.getLinenumber() - 1, statement.getStartposition() - 1,
                activityText);
            try {
                detmTagSpecsForStatement(statement, statementPosition,
                    tagSpecsHolder, activityText);
            } catch (Exception e) {
                CorePlugin.logError("Exception during tag creation for relocatables", e);
            }
        }
    }

    /**
     * Creates tag-specs for where tags should go within the given statement.
     * 
     * @param statementPosition The character index of the given statement 
     *                          within the given activity text. 
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given statement.
     */
    private static void detmTagSpecsForStatement(Statement_c statement, 
        int statementPosition, List<TagSpec> tagSpecsHolder, 
        String activityText)
    {
        // if the given statement is an if-statement
        IfStmt_c ifStatement = IfStmt_c.getOneACT_IFOnR603(statement);
        if (ifStatement != null) {
            // process the test result value
            detmTagSpecsForValue(Value_c.getOneV_VALOnR625(ifStatement), 
                tagSpecsHolder, activityText);

            // if the if-statement has a block that it controls
            Block_c block = Block_c.getOneACT_BLKOnR607(ifStatement);
            if (block != null) {
                // process that block
                detmTagSpecsForBlock(block, tagSpecsHolder, activityText);
            }
            
            return;
        }

        // if the given statement is an else-if-statement
        ElseifStmt_c elseifStatement = ElseifStmt_c.getOneACT_ELOnR603(statement);
        if (elseifStatement != null) {
            // process the test result value
            detmTagSpecsForValue(Value_c.getOneV_VALOnR659(elseifStatement), 
                tagSpecsHolder, activityText);

            // if the else-if-statement has a block that it controls
            Block_c block = Block_c.getOneACT_BLKOnR658(elseifStatement);
            if (block != null) {
                // process that block
                detmTagSpecsForBlock(block, tagSpecsHolder, activityText);
            }
            
            return;
        }

        // if the given statement is an else-statement
        ElseStmt_c elseStatement = ElseStmt_c.getOneACT_EOnR603(statement);
        if (elseStatement != null) {
            // if the else-statement has a block that it controls
            Block_c block = Block_c.getOneACT_BLKOnR606(elseStatement);
            if (block != null) {
                // process that block
                detmTagSpecsForBlock(block, tagSpecsHolder, activityText);
            }
            
            return;
        }

        // if the given statement is a for-statement
        ForStmt_c forStatement = ForStmt_c.getOneACT_FOROnR603(statement);
        if (forStatement != null) {
            // if the for-statement has a block that it controls
            Block_c block = Block_c.getOneACT_BLKOnR605(forStatement);
            if (block != null) {
                // process that block
                detmTagSpecsForBlock(block, tagSpecsHolder, activityText);
            }
            
            return;
        }

        // if the given statement is a while statement
        WhileStmt_c whileStatement = WhileStmt_c.getOneACT_WHLOnR603(statement);
        if (whileStatement != null) {
            // process the continue-result value
            detmTagSpecsForValue(Value_c.getOneV_VALOnR626(whileStatement), 
                tagSpecsHolder, activityText);

            // if the while-statement has a block that it controls
            Block_c block = Block_c.getOneACT_BLKOnR608(whileStatement);
            if (block != null) {
                // process that block
                detmTagSpecsForBlock(block, tagSpecsHolder, activityText);
            }
            
            return;
        }

        // if the given statement is an assign-to-member statement
        AssignToMember_c assignToMember = 
            AssignToMember_c.getOneACT_AIOnR603(statement);
        if (assignToMember != null) {
            // create a tag-spec for the attribute's name 
            Attribute_c attribute = 
                Attribute_c.getOneO_ATTROnR806(
                		        AttributeValueReference_c.getOneV_AVLOnR801(
                		            Value_c.getOneV_VALOnR689(assignToMember)));
            TagSpec tagSpec = new TagSpec();
            tagSpecsHolder.add(tagSpec);
            tagSpec.start = getPositionWithinString(
                assignToMember.getAttributelinenumber() - 1,
                assignToMember.getAttributecolumn() - 1, activityText);
            tagSpec.length = attribute.getName().length();
            tagSpec.tag = formTag(Relocatables.Types.attributeName,
                new UUID[] {attribute.getAttr_id()}, 
                activityText, tagSpec.start, tagSpec.length);
            
            // process the value being assigned
            detmTagSpecsForValue(Value_c.getOneV_VALOnR609(assignToMember), 
                tagSpecsHolder, activityText);
            
            return;
        }
        
        // if the given statement is a select-from-instances statement
        SelectFromInstances_c selectFrom = 
            SelectFromInstances_c.getOneACT_FIOOnR603(statement);
        if (selectFrom != null) {
            // if there's an associated model class extent
            ModelClass_c extent = ModelClass_c.getOneO_OBJOnR677(selectFrom);
            if (extent != null) {
                // create a tag-spec for the model class's key-letters 
                TagSpec tagSpec = new TagSpec();
                tagSpecsHolder.add(tagSpec);
                tagSpec.start = getPositionWithinString(
                    selectFrom.getExtentlinenumber() - 1,
                    selectFrom.getExtentcolumn() - 1, activityText);
                tagSpec.length = extent.getKey_lett().length();
                tagSpec.tag = formTag(Relocatables.Types.modelClassKeyLetters,
                    new UUID[] {extent.getObj_id()}, 
                    activityText, tagSpec.start, tagSpec.length);
            }
            
            return;
        }
        
        // if the given statement is a select-from-instances-where statement
        SelectFromInstancesWhere_c selectWhere = 
            SelectFromInstancesWhere_c.getOneACT_FIWOnR603(statement);
        if (selectWhere != null) {
            // if there's an associated model class extent
            ModelClass_c extent = ModelClass_c.getOneO_OBJOnR676(selectWhere);
            if (extent != null) {
                // create a tag-spec for the model class's key-letters 
                TagSpec tagSpec = new TagSpec();
                tagSpecsHolder.add(tagSpec);
                tagSpec.start = getPositionWithinString(
                    selectWhere.getExtentlinenumber() - 1,
                    selectWhere.getExtentcolumn() - 1, activityText);
                tagSpec.length = extent.getKey_lett().length();
                tagSpec.tag = formTag(Relocatables.Types.modelClassKeyLetters,
                    new UUID[] {extent.getObj_id()}, 
                    activityText, tagSpec.start, tagSpec.length);
            }
            
            // if there's an associated where value
            Value_c whereValue = Value_c.getOneV_VALOnR610(selectWhere);
            if (whereValue != null) {
                // process it
                detmTagSpecsForValue(whereValue, tagSpecsHolder, activityText);
            }
            
            return;
        }
        
        // if the given statement is a select statement
        Select_c select = Select_c.getOneACT_SELOnR603(statement);
        if (select != null) {
            // process the starting point value
            Value_c startingPoint = Value_c.getOneV_VALOnR613(select);
            detmTagSpecsForValue(startingPoint, tagSpecsHolder, activityText);
            
            // process the chain links
            ChainLink_c link = ChainLink_c.getOneACT_LNKOnR637(select);
            detmTagSpecsForChainLink(link, tagSpecsHolder, activityText);
            
            // if this select statement is a select-related-where statement
            SelectRelatedWhere_c where = 
                SelectRelatedWhere_c.getOneACT_SRWOnR664(select);
            if (where != null) {
                // if there's an associated where value
                Value_c whereValue = Value_c.getOneV_VALOnR611(where);
                if (whereValue != null) {
                    // process it
                    detmTagSpecsForValue(whereValue, tagSpecsHolder, activityText);
                }
            }
            
            return;
        }
        
        // if the given statement is a bridge invocation
        BridgeInvocation_c bridgeInvoc = 
            BridgeInvocation_c.getOneACT_BRGOnR603(statement);
        if (bridgeInvoc != null) {
            // process the invocation
            Bridge_c bridge = Bridge_c.getOneS_BRGOnR674(bridgeInvoc);
            ActualParameter_c firstParam = 
                ActualParameter_c.getOneV_PAROnR628(bridgeInvoc,
                    firstActualParameterFinder);
            ExternalEntity_c entity = ExternalEntity_c.getOneS_EEOnR19(bridge);
            detmTagSpecsForInvocation(
                Relocatables.Types.bridgeName, bridge.getName(), 
                bridge.getBrg_id(), 
                bridgeInvoc.getBridgenamelinenumber(), 
                bridgeInvoc.getBridgenamecolumn(),
                entity.getKey_lett(), entity.getEe_id(), 
                bridgeInvoc.getExternalentitykeyletterslinenumber(), 
                bridgeInvoc.getExternalentitykeyletterscolumn(),
                firstParam, tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is an operation invocation
        OperationInvocation_c operationInvoc = 
            OperationInvocation_c.getOneACT_TFMOnR603(statement);
        if (operationInvoc != null) {
            // process the invocation
            Operation_c operation = Operation_c.getOneO_TFROnR673(operationInvoc);
            ActualParameter_c firstParam = 
                ActualParameter_c.getOneV_PAROnR627(operationInvoc, 
                    firstActualParameterFinder);
            boolean classBased = operation.getInstance_based() == Scope_c.Class;
            ModelClass_c clazz = classBased ?
                ModelClass_c.getOneO_OBJOnR115(operation) : null;
            detmTagSpecsForInvocation(
                Relocatables.Types.operationName, operation.getName(), 
                operation.getTfr_id(), 
                operationInvoc.getOperationnamelinenumber(), 
                operationInvoc.getOperationnamecolumn(),
                classBased ? clazz.getKey_lett() : null,
                classBased ? clazz.getObj_id() : null,
                classBased ? operationInvoc.getModelclasskeyletterslinenumber() : 0,
                classBased ? operationInvoc.getModelclasskeyletterscolumn() : 0,
                firstParam, tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is a function invocation
        FunctionInvocation_c functionInvoc = 
            FunctionInvocation_c.getOneACT_FNCOnR603(statement);
        if (functionInvoc != null) {
            // process the invocation
            Function_c function = Function_c.getOneS_SYNCOnR675(functionInvoc);
            ActualParameter_c firstParam = 
                ActualParameter_c.getOneV_PAROnR669(functionInvoc);
            detmTagSpecsForInvocation(
                Relocatables.Types.functionName, function.getName(), 
                function.getSync_id(), 
                functionInvoc.getFunctionnamelinenumber(), 
                functionInvoc.getFunctionnamecolumn(), 
                null, null, 0, 0, 
                firstParam, tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is a return statement
        ReturnStmt_c returnStatement =  
            ReturnStmt_c.getOneACT_RETOnR603(statement);
        if (returnStatement != null) {
            // if there's a value being returned
            Value_c value = Value_c.getOneV_VALOnR668(returnStatement);
            if (value != null) {
                // process it
                detmTagSpecsForValue(value, tagSpecsHolder, activityText);
            }
            
            return;
        }

        // if the given statement is a create statement
        Create_c create = Create_c.getOneACT_CROnR603(statement);
        if (create != null) {
            // create a tag-spec for the model class's key-letters
            ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR671(create);
            TagSpec tagSpec = new TagSpec();
            tagSpecsHolder.add(tagSpec);
            tagSpec.start = getPositionWithinString(
                create.getModelclasskeyletterslinenumber() - 1,
                create.getModelclasskeyletterscolumn() - 1, activityText);
            tagSpec.length = clazz.getKey_lett().length();
            tagSpec.tag = formTag(Relocatables.Types.modelClassKeyLetters,
                new UUID[] {clazz.getObj_id()}, 
                activityText, tagSpec.start, tagSpec.length);
            
            return;
        }

        // if the given statement is a create-with-no-variable statement
        CreateNoVariable_c createNoVariable = 
            CreateNoVariable_c.getOneACT_CNVOnR603(statement);
        if (createNoVariable != null) {
            // create a tag-spec for the model class's key-letters
            ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR672(createNoVariable);
            TagSpec tagSpec = new TagSpec();
            tagSpecsHolder.add(tagSpec);
            tagSpec.start = getPositionWithinString(
                createNoVariable.getModelclasskeyletterslinenumber() - 1,
                createNoVariable.getModelclasskeyletterscolumn() - 1, activityText);
            tagSpec.length = clazz.getKey_lett().length();
            tagSpec.tag = formTag(Relocatables.Types.modelClassKeyLetters,
                new UUID[] {clazz.getObj_id()}, 
                activityText, tagSpec.start, tagSpec.length);
            
            return;
        }

        // if the given statement is an event-specification statement
        EventSpecificationStatement_c eventSpec =  
            EventSpecificationStatement_c.getOneE_ESSOnR603(statement);
        if (eventSpec != null) {
            // process the event
            detmTagSpecsForEvent(eventSpec, tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is a generate-preexisting-event statement
        GeneratePreexistingEvent_c generatePreexisting =  
            GeneratePreexistingEvent_c.getOneE_GPROnR603(statement);
        if (generatePreexisting != null) {
            // if there's an attribute value specified
            Value_c value = Value_c.getOneV_VALOnR801( 
                AttributeValueReference_c.getOneV_AVLOnR801(Value_c.getOneV_VALOnR714(generatePreexisting)));
            if (value != null) {
                // process it
                detmTagSpecsForValue(value, tagSpecsHolder, activityText);
            }
            
            return;
        }
        
        // if the given statement is a relate statement
        Relate_c relate = Relate_c.getOneACT_RELOnR603(statement);
        if (relate != null) {
            // process the statement
            detmTagSpecsForRelateStatement(
                Association_c.getOneR_RELOnR653(relate), 
                relate.getAssociationnumberlinenumber(), 
                relate.getAssociationnumbercolumn(),
                relate.getRelationship_phrase(), 
                relate.getAssociationphraselinenumber(), 
                relate.getAssociationphrasecolumn(), 
                tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is a relate-using statement
        RelateUsing_c relateUsing = RelateUsing_c.getOneACT_RUOnR603(statement);
        if (relateUsing != null) {
            // process the statement
            detmTagSpecsForRelateStatement(
                Association_c.getOneR_RELOnR654(relateUsing), 
                relateUsing.getAssociationnumberlinenumber(), 
                relateUsing.getAssociationnumbercolumn(),
                relateUsing.getRelationship_phrase(), 
                relateUsing.getAssociationphraselinenumber(), 
                relateUsing.getAssociationphrasecolumn(), 
                tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is an unrelate statement
        Unrelate_c unrelate = Unrelate_c.getOneACT_UNROnR603(statement);
        if (unrelate != null) {
            // process the statement
            detmTagSpecsForRelateStatement(
                Association_c.getOneR_RELOnR655(unrelate), 
                unrelate.getAssociationnumberlinenumber(), 
                unrelate.getAssociationnumbercolumn(),
                unrelate.getRelationship_phrase(), 
                unrelate.getAssociationphraselinenumber(), 
                unrelate.getAssociationphrasecolumn(), 
                tagSpecsHolder, activityText);
            
            return;
        }

        // if the given statement is an unrelate-using statement
        UnrelateUsing_c unrelateUsing = 
            UnrelateUsing_c.getOneACT_URUOnR603(statement);
        if (unrelateUsing != null) {
            // process the statement
            detmTagSpecsForRelateStatement(
                Association_c.getOneR_RELOnR656(unrelateUsing), 
                unrelateUsing.getAssociationnumberlinenumber(), 
                unrelateUsing.getAssociationnumbercolumn(),
                unrelateUsing.getRelationship_phrase(), 
                unrelateUsing.getAssociationphraselinenumber(), 
                unrelateUsing.getAssociationphrasecolumn(), 
                tagSpecsHolder, activityText);
            
            return;
        }
    }
    
    /**
     * Creates tag-specs for where tags should go within an invocation.
     * 
     * @param relocatableType   The relocatable type that is most closely 
     *                          associated with the invocatee 
     *                          (e.g. functionName, operationName).  Is used
     *                          to tell whether the invocatee is a function,
     *                          bridge, or operation.
     * @param invocateeName     The name of the function, operation, or bridge 
     *                          being invoked.
     * @param invocateeId       The ID of the function, operation, or bridge 
     *                          being invoked.                     
     * @param invocateeNameLineNumber, invocateeNameColumn 
     *                          The line number and column of the invocatee's 
     *                          name within within the given activity text.
     * @param invocateeKeyLetters   
     *                          The key-letters of the owner of the invocatee, 
     *                          if the invocatee is a class operation or a 
     *                          bridge.  Should be null otherwise.                       
     * @param invocateeKeyLettersEntityId   
     *                          The ID of the owner of the invocatee, 
     *                          if the invocatee is a class operation or a 
     *                          bridge.  Should be zero otherwise.                       
     * @param invocateeNameLineNumber, invocateeNameColumn 
     *                          The line number and column of the key-letters 
     *                          of the invocatee's owner (if there is one) 
     *                          within the given activity text.  Should be zero 
     *                          otherwise.
     * @param firstParam        The first parameter (if any) of those supplied
     *                          to the invocatee.                           
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given invocation.
     */
    private static void detmTagSpecsForInvocation(
        String relocatableType, String invocateeName, UUID invocateeId,
        int invocateeNameLineNumber, int invocateeNameColumn,
        String invocateeKeyLetters, UUID invocateeKeyLettersEntityId,
        int invocateeKeyLettersLineNumber, int invocateeKeyLettersColumn,
        ActualParameter_c firstParam, List<TagSpec> tagSpecsHolder, 
        String activityText)
    {
        // if key-letters were given
        if (invocateeKeyLetters != null) {
            // create a tag-spec for the key-letters of the invocatee 
            TagSpec tagSpec = new TagSpec();
            tagSpecsHolder.add(tagSpec);
            tagSpec.start = getPositionWithinString(
                invocateeKeyLettersLineNumber - 1,
                invocateeKeyLettersColumn - 1, activityText);
            tagSpec.length = invocateeKeyLetters.length();
            tagSpec.tag = formTag(
                relocatableType == Relocatables.Types.operationName ?
                    Relocatables.Types.modelClassKeyLetters : 
                    Relocatables.Types.externalEntityKeyLetters,
                new UUID[] {invocateeKeyLettersEntityId}, 
                activityText, tagSpec.start, tagSpec.length);
        }
        
        // create a tag-spec for the name of the invocatee 
        TagSpec tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        tagSpec.start = getPositionWithinString(
            invocateeNameLineNumber - 1,
            invocateeNameColumn - 1, activityText);
        tagSpec.length = invocateeName.length();
        tagSpec.tag = formTag(relocatableType,
            new UUID[] {invocateeId}, activityText, tagSpec.start, tagSpec.length);
        
        // keep doing this while there are more actual parameters to process
        ActualParameter_c param = firstParam;
        while (param != null) {
            // process this parameter
            detmTagSpecsForActualParameter(
                param, relocatableType, null, null, tagSpecsHolder, activityText);
            
            // proceed to the next parameter
            param = ActualParameter_c.getOneV_PAROnR816Precedes(param);
        }
    }


    /**
     * Creates tag-specs for where tags should go within the given actual
     * parameter.
     * 
     * Note that we can't use the actual-parameter's ID in the tag since
     * an actual-parameter is a Body subsystem instance and is therefore
     * not persisted to disk with the model.
     * 
     * @param invocateeNameRelocatableType   
     *                          The relocatable type that is most closely 
     *                          associated with the invocatee 
     *                          (e.g. functionName, operationName).  Is used
     *                          to tell whether the invocatee is a function,
     *                          bridge, operation, state machine event, or 
     *                          external entity event.
     * @param stateMachineEvent If the invocatee is a state machine event, 
     *                          this is that event.                         
     * @param externalEntityEvent 
     *                          If the invocatee is an external entity event, 
     *                          this is that event.                         
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given invocation.
     */
    private static void detmTagSpecsForActualParameter(
        ActualParameter_c param, 
        String invocateeNameRelocatableType, 
        StateMachineEvent_c stateMachineEvent, 
        ExternalEntityEvent_c externalEntityEvent, 
        List<TagSpec> tagSpecsHolder, String activityText)
    {
        // if the invocatee is a function
        String paramRelocatableType = null;
        UUID paramId = null;
        final String paramName = param.getName();
        if (invocateeNameRelocatableType == Relocatables.Types.functionName) {
            // use the corresponding relocatable type for the parameter
            paramRelocatableType = Relocatables.Types.functionParameterName;

            // find the function being invoked
            Function_c function = 
                Function_c.getOneS_SYNCOnR827(
                    FunctionValue_c.getOneV_FNVOnR817(param));
            if (function == null) {
                function = Function_c.getOneS_SYNCOnR675(
                    FunctionInvocation_c.getOneACT_FNCOnR669(param));
            }
            
            // detm the id of the function parameter whose name matches that
            // of the given actual parameter
            FunctionParameter_c functionParam = 
                FunctionParameter_c.getOneS_SPARMOnR24(function,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((FunctionParameter_c)candidate).
                                getName().equals(paramName);
                        }
                    });
            paramId = functionParam.getSparm_id();
        }

        // else, if the invocatee is an operation
        else if (invocateeNameRelocatableType == Relocatables.Types.operationName) {
            // use the corresponding relocatable type for the parameter
            paramRelocatableType = Relocatables.Types.operationParameterName;

            // find the operation being invoked
            Operation_c operation = 
                Operation_c.getOneO_TFROnR829(
                    OperationValue_c.getOneV_TRVOnR811(param));
            if (operation == null) {
                operation = Operation_c.getOneO_TFROnR673(
                    OperationInvocation_c.getOneACT_TFMOnR627(param));
            }
            
            // detm the id of the operation parameter whose name matches that
            // of the given actual parameter
            OperationParameter_c operationParam = 
                OperationParameter_c.getOneO_TPARMOnR117(operation,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((OperationParameter_c)candidate).
                                getName().equals(paramName);
                        }
                    });
            paramId = operationParam.getTparm_id();
        }

        // else, if the invocatee is a bridge
        else if (invocateeNameRelocatableType == Relocatables.Types.bridgeName) {
            // use the corresponding relocatable type for the parameter
            paramRelocatableType = Relocatables.Types.bridgeParameterName;

            // find the bridge being invoked
            Bridge_c bridge = Bridge_c.getOneS_BRGOnR828(
                BridgeValue_c.getOneV_BRVOnR810(param));
            if (bridge == null) {
                bridge = Bridge_c.getOneS_BRGOnR674(
                    BridgeInvocation_c.getOneACT_BRGOnR628(param));
            }
            
            // detm the id of the bridge parameter whose name matches that
            // of the given actual parameter
            BridgeParameter_c bridgeParam = 
                BridgeParameter_c.getOneS_BPARMOnR21(bridge,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((BridgeParameter_c)candidate).
                                getName().equals(paramName);
                        }
                    });
            paramId = bridgeParam.getBparm_id();
        }
        
        // else, if the invocatee is a state machine event
        else if (invocateeNameRelocatableType == 
            Relocatables.Types.stateMachineEventDerivedLabel) {
            // use the corresponding relocatable type for the event data item
            paramRelocatableType = Relocatables.Types.stateMachineEventDataItemName;

            // detm the id of the event data item whose name matches that
            // of the given actual parameter
            StateMachineEventDataItem_c item = 
                StateMachineEventDataItem_c.getOneSM_EVTDIOnR522(
                    EventSupplementalData_c.getOneSM_SUPDTOnR520(stateMachineEvent),
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((StateMachineEventDataItem_c)candidate).
                                getName().equals(paramName);
                        }
                    });
            paramId = item.getSmedi_id();
        }
        
        // else, if the invocatee is an external entity event
        else if (invocateeNameRelocatableType == 
            Relocatables.Types.externalEntityEventDerivedLabel) {
            // use the corresponding relocatable type for the event data item
            paramRelocatableType = Relocatables.Types.externalEntityEventDataItemName;

            // detm the id of the event data item whose name matches that
            // of the given actual parameter
            ExternalEntityEventDataItem_c item = 
                ExternalEntityEventDataItem_c.getOneS_EEEDIOnR13(
                    externalEntityEvent,
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate) {
                            return ((ExternalEntityEventDataItem_c)candidate).
                                getName().equals(paramName);
                        }
                    });
            paramId = item.getEeedi_id();
        }
        
        // create a tag-spec for the parameter's name 
        TagSpec tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        int textIndex = getPositionWithinString(
            param.getLabellinenumber() - 1,
            param.getLabelcolumn() - 1, activityText);
        tagSpec.start = textIndex;
        tagSpec.length = paramName.length();
        tagSpec.tag = formTag(paramRelocatableType,
            new UUID[] {paramId}, activityText, tagSpec.start, tagSpec.length);

        // process the parameter's value
        detmTagSpecsForValue(Value_c.getOneV_VALOnR800(param),
            tagSpecsHolder, activityText);
    }
    
    /**
     * Creates tag-specs for where tags should go within the given value.
     * 
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given value.
     */
    private static void detmTagSpecsForValue(
        Value_c value, List<TagSpec> tagSpecsHolder, String activityText)
    {
        // these values will record infomation about the tag to be created,
        // during the code below 
        String relocatableType = null;
        UUID instanceId = null;
        int startWithinLine = value.getStartposition(), length = 0;

        // here we'll gather data for a tag that needs to be created (if any),
        // according to the given's value type; below, we'll actually
        // create the tag in a finally block
        try {
            // if the given value represents an attribute value
            AttributeValueReference_c attributeValue = 
                AttributeValueReference_c.getOneV_AVLOnR801(value);
            if (attributeValue != null) {
                // gather the data for an attribute-name tag 
                Attribute_c attribute = 
                    Attribute_c.getOneO_ATTROnR806(attributeValue);
                relocatableType = Relocatables.Types.attributeName;
                instanceId = attribute.getAttr_id();
                length = attribute.getName().length();
                
                return;
            }
    
            // if the given value represents an event datum value
            EventDatumValue_c datumValue = 
                EventDatumValue_c.getOneV_EDVOnR801(value);
            if (datumValue != null) {
                // gather the data for an event-data-item-name tag 
                StateMachineEventDataItem_c item = 
                    StateMachineEventDataItem_c.getOneSM_EVTDIOnR846(EventParameterReference_c.getOneV_EPROnR834(datumValue));
                relocatableType = Relocatables.Types.stateMachineEventDataItemName;
                instanceId = item.getSmedi_id();
                length = item.getName().length();
                
                return;
            }
    
            // if the given value represents a parameter value
            ParameterValue_c paramValue = ParameterValue_c.getOneV_PVLOnR801(value);
            if (paramValue != null) {
                // if it's a bridge parameter value
                BridgeParameter_c bridgeParam = 
                    BridgeParameter_c.getOneS_BPARMOnR831(paramValue);
                if (bridgeParam != null) {
                    // gather the data for a tag of that type
                    relocatableType = Relocatables.Types.bridgeParameterName;
                    instanceId = bridgeParam.getBparm_id();
                }
    
                // if it's a function parameter value
                FunctionParameter_c functionParam = 
                    FunctionParameter_c.getOneS_SPARMOnR832(paramValue);
                if (functionParam != null) {
                    // gather the data for a tag of that type
                    relocatableType = Relocatables.Types.functionParameterName;
                    instanceId = functionParam.getSparm_id();
                }
    
                // if it's an operation parameter value
                OperationParameter_c operationParam = 
                    OperationParameter_c.getOneO_TPARMOnR833(paramValue);
                if (operationParam != null) {
                    // gather the data for a tag of that type
                    relocatableType = Relocatables.Types.operationParameterName;
                    instanceId = operationParam.getTparm_id();
                }
                
                return;
            }
        
            // if the given value represents a binary operation
            BinaryOperation_c binary = BinaryOperation_c.getOneV_BINOnR801(value);
            if (binary != null) {
                // process the left value of the operation recursively
                Value_c leftValue = Value_c.getOneV_VALOnR802(binary);
                detmTagSpecsForValue(leftValue, tagSpecsHolder, activityText);
    
                // process the right value of the operation recursively
                Value_c rightValue = Value_c.getOneV_VALOnR803(binary);
                detmTagSpecsForValue(rightValue, tagSpecsHolder, activityText);
                
                return;
            }
            
            // if the given value represents a unary operation
            UnaryOperation_c unary = UnaryOperation_c.getOneV_UNYOnR801(value);
            if (unary != null) {
                // process the value of the single operand recursively
                Value_c leftValue = Value_c.getOneV_VALOnR804(unary);
                detmTagSpecsForValue(leftValue, tagSpecsHolder, activityText);
                
                return;
            }
            
            // if the given value represents a function value
            FunctionValue_c functionValue = FunctionValue_c.getOneV_FNVOnR801(value);
            if (functionValue != null) {
                // process that value
                Function_c function = Function_c.getOneS_SYNCOnR827(functionValue);
                ActualParameter_c firstParam = 
                    ActualParameter_c.getOneV_PAROnR817(functionValue,
                        firstActualParameterFinder);
                detmTagSpecsForInvocation(
                    Relocatables.Types.functionName,
                    function.getName(), function.getSync_id(),
                    value.getLinenumber(), value.getStartposition(),
                    null, null, 0, 0,
                    firstParam, tagSpecsHolder, activityText);
                
                return;
            }
            
            // if the given value represents an operation value
            OperationValue_c operationValue = OperationValue_c.getOneV_TRVOnR801(value);
            if (operationValue != null) {
                // process that value
                Operation_c operation = Operation_c.getOneO_TFROnR829(operationValue);
                ActualParameter_c firstParam = 
                    ActualParameter_c.getOneV_PAROnR811(operationValue,
                        firstActualParameterFinder);
                boolean classBased = operation.getInstance_based() == Scope_c.Class;
                ModelClass_c clazz = classBased ?
                    ModelClass_c.getOneO_OBJOnR115(operation) : null;
                detmTagSpecsForInvocation(
                    Relocatables.Types.operationName, 
                    operation.getName(), operation.getTfr_id(), 
                    value.getLinenumber(), value.getStartposition(),
                    classBased ? clazz.getKey_lett() : null,
                    classBased ? clazz.getObj_id() : null,
                    classBased ? operationValue.getModelclasskeyletterslinenumber() : 0,
                    classBased ? operationValue.getModelclasskeyletterscolumn() : 0,
                    firstParam, tagSpecsHolder, activityText);
                
                return;
            }
            
            // if the given value represents a bridge value
            BridgeValue_c bridgeValue = BridgeValue_c.getOneV_BRVOnR801(value);
            if (bridgeValue != null) {
                // process that value
                Bridge_c bridge = Bridge_c.getOneS_BRGOnR828(bridgeValue);
                ActualParameter_c firstParam = 
                    ActualParameter_c.getOneV_PAROnR810(bridgeValue,
                        firstActualParameterFinder);
                ExternalEntity_c entity = ExternalEntity_c.getOneS_EEOnR19(bridge);
                detmTagSpecsForInvocation(
                    Relocatables.Types.bridgeName,
                    bridge.getName(), bridge.getBrg_id(), 
                    value.getLinenumber(), value.getStartposition(),
                    entity.getKey_lett(), entity.getEe_id(), 
                    bridgeValue.getExternalentitykeyletterslinenumber(), 
                    bridgeValue.getExternalentitykeyletterscolumn(),
                    firstParam, tagSpecsHolder, activityText);
                
                return;
            }
            
            // if the given value represents a selected-reference value
            SelectedReference_c selectedReference = 
                SelectedReference_c.getOneV_SLROnR801(value);
            if (selectedReference != null) {
                // if an attribute is being accessed by the reference 
                Attribute_c attribute = 
                    Attribute_c.getOneO_ATTROnR812(selectedReference);
                if (attribute != null) {
                    // gather the data for an attribute-name tag 
                    relocatableType = Relocatables.Types.attributeName;
                    instanceId = attribute.getAttr_id();
                    length = attribute.getName().length();
                }
                
                // otherwise
                else {
                    // if an operation is being invoked by the reference 
                    operationValue =
                        OperationValue_c.getOneV_TRVOnR825(selectedReference);
                    if (operationValue != null) {
                        // process that value
                        detmTagSpecsForValue(
                            Value_c.getOneV_VALOnR801(operationValue), 
                            tagSpecsHolder, activityText);
                    }
                }
                
                return;
            }
            
            // if the given value represents a literal enumerator
            LiteralEnumerator_c literalEnumerator = 
                LiteralEnumerator_c.getOneV_LENOnR801(value);
            if (literalEnumerator != null) {
                // create a tag-spec for the data type name
                Enumerator_c enumerator = 
                    Enumerator_c.getOneS_ENUMOnR824(literalEnumerator);
                DataType_c dataType = DataType_c.getOneS_DTOnR17(
                    EnumerationDataType_c.getOneS_EDTOnR27(enumerator));
                TagSpec tagSpec = new TagSpec();
                tagSpecsHolder.add(tagSpec);
                startWithinLine = value.getStartposition();
                tagSpec.start = getPositionWithinString(
                    literalEnumerator.getDatatypenamelinenumber() - 1,
                    literalEnumerator.getDatatypenamecolumn() - 1, activityText);
                tagSpec.length = dataType.getName().length();
                tagSpec.tag = formTag(Relocatables.Types.dataTypeName,
                    new UUID[] {dataType.getDt_id()}, 
                    activityText, tagSpec.start, tagSpec.length);

                // gather the data for an enumerator-name tag 
                relocatableType = Relocatables.Types.enumeratorName;
                instanceId = enumerator.getEnum_id();
                length = enumerator.getName().length();
            }
            
            return;
        }
        
        // here, we (perhaps) create a tag from data gathered above 
        finally {
            // if data for a tag for the given value was detm'd above
            if (relocatableType != null) {
                // create a tag-spec for the value using the data detm'd above
                TagSpec tagSpec = new TagSpec();
                tagSpecsHolder.add(tagSpec);
                startWithinLine = value.getStartposition();
                tagSpec.start = getPositionWithinString(
                    value.getLinenumber() - 1,
                    startWithinLine - 1, activityText);
                tagSpec.length = length != 0 ? 
                    length : value.getEndposition() - startWithinLine + 1;
                tagSpec.tag = formTag(relocatableType,
                    new UUID[] {instanceId}, 
                    activityText, tagSpec.start, tagSpec.length);
            }
        }
    }
    
    /**
     * Creates tag-specs for where tags should go within the given chain-link.
     * 
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given chain-link.
     */
    private static void detmTagSpecsForChainLink(
        ChainLink_c link, List<TagSpec> tagSpecsHolder, String activityText)
    {
        // create a tag-spec for the model class's key-letters
        ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR678(link);
        TagSpec tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        tagSpec.start = getPositionWithinString(
            link.getModelclasskeyletterslinenumber() - 1,
            link.getModelclasskeyletterscolumn() - 1, activityText);
        tagSpec.length = clazz.getKey_lett().length();
        tagSpec.tag = formTag(Relocatables.Types.modelClassKeyLetters,
            new UUID[] {clazz.getObj_id()}, 
            activityText, tagSpec.start, tagSpec.length);

        // create a tag-spec for the association number; 
        // note that we don't subtract one from the column because 
        // we want the tag to start after the "R" 
        Association_c assoc = Association_c.getOneR_RELOnR681(link);
        tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        tagSpec.start = getPositionWithinString(
            link.getAssociationnumberlinenumber() - 1,
            link.getAssociationnumbercolumn(), activityText);
        tagSpec.length = Integer.toString(assoc.getNumb()).length();
        tagSpec.tag = formTag(Relocatables.Types.associationNumber,
            new UUID[] {assoc.getRel_id()}, 
            activityText, tagSpec.start, tagSpec.length);

        // if the link has a phrase
        String phrase = link.getRel_phrase();
        if (!phrase.equals("")) {
            // process the phrase
            detmTagSpecsForAssociationPhrase(assoc, phrase, 
                link.getPhraselinenumber(), link.getPhrasecolumn(), 
                tagSpecsHolder, activityText);
        }
        
        // if there's a chain-link that succeeds the one given
        ChainLink_c nextLink = ChainLink_c.getOneACT_LNKOnR604Precedes(link);
        if (nextLink != null) {
            // process that next chain-link 
            detmTagSpecsForChainLink(nextLink, tagSpecsHolder, activityText);
        }
    }
    
    /**
     * Creates tag-specs for where tags should go within the given event-
     * specification statement.
     * 
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given event-specification statement.
     */
    private static void detmTagSpecsForEvent(
        EventSpecificationStatement_c eventSpec, 
        List<TagSpec> tagSpecsHolder, String activityText)
    {
        // [NOTE: there is currently no UI-visible support for 
        // external-entity events in the tool, so the path through the
        // code below which supports them is untested]
        
        // of these two statements, one will be non-null and the other null; 
        // we'll use the non-null one below
        GenerateEventStatement_c generateEvent = 
            GenerateEventStatement_c.getOneE_GESOnR701(eventSpec);
        CreateEventStatement_c createEvent = 
            CreateEventStatement_c.getOneE_CESOnR701(eventSpec);

        // of these four statements, one will be non-null and the others null; 
        // we'll use the non-null one below
        GenerateSmEventStatement_c generateStateMachineEvent =
            GenerateSmEventStatement_c.getOneE_GSMEOnR703(generateEvent);
        GenerateToExternalEntity_c generateToExternalEntity =
            GenerateToExternalEntity_c.getOneE_GEEOnR703(generateEvent);
        CreateSmEventStatement_c createStateMachineEvent =
            CreateSmEventStatement_c.getOneE_CSMEOnR702(createEvent);
        CreateEventToExternalEntity_c createToExternalEntity =
            CreateEventToExternalEntity_c.getOneE_CEEOnR702(createEvent);

        // of these two events, one will be non-null and the other null; 
        // we'll use the non-null one below
        StateMachineEvent_c stateMachineEvent = 
            generateStateMachineEvent != null ?
            StateMachineEvent_c.getOneSM_EVTOnR707(generateStateMachineEvent) :
            StateMachineEvent_c.getOneSM_EVTOnR706(createStateMachineEvent);
        ExternalEntityEvent_c externalEntityEvent =
            generateToExternalEntity != null ?
            ExternalEntityEvent_c.getOneS_EEEVTOnR709(generateToExternalEntity) :
            ExternalEntityEvent_c.getOneS_EEEVTOnR708(createToExternalEntity);
        
        // create a tag-spec for the event's derived label
        TagSpec tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        tagSpec.start = getPositionWithinString(
            eventSpec.getEventderivedlabellinenumber() - 1,
            eventSpec.getEventderivedlabelcolumn() - 1, 
            activityText);
        tagSpec.length = (stateMachineEvent != null) ?
            stateMachineEvent.getDrv_lbl().length() :
            externalEntityEvent.getDrv_lbl().length();
        UUID[] ids = (stateMachineEvent != null) ?
            new UUID[] {stateMachineEvent.getSmevt_id()} :
            new UUID[] {externalEntityEvent.getEeevt_id()};
        tagSpec.tag = formTag((stateMachineEvent != null) ?
            Relocatables.Types.stateMachineEventDerivedLabel :
            Relocatables.Types.externalEntityEventDerivedLabel,
            ids, activityText, tagSpec.start, tagSpec.length);
        
        // if an event meaning was given in the statement
        int meaningLineNumber = eventSpec.getEventmeaninglinenumber();
        int meaningColumn = eventSpec.getEventmeaningcolumn();
        if (meaningLineNumber != 0 || meaningColumn != 0) {
            // create a tag-spec for the event's meaning, taking 
            // into account that it might be ticked
            String meaning = (stateMachineEvent != null) ?
                stateMachineEvent.getMning() : externalEntityEvent.getMning();
            tagSpec = new TagSpec();
            tagSpecsHolder.add(tagSpec);
            tagSpec.start = getPositionWithinString(
                meaningLineNumber - 1, meaningColumn - 1, 
                activityText);
            tagSpec.length = meaning.length();
            if (activityText.charAt(tagSpec.start) == '\'') {
                tagSpec.start++;
            }
            tagSpec.tag = formTag((stateMachineEvent != null) ?
                Relocatables.Types.stateMachineEventMeaning :
                Relocatables.Types.externalEntityEventMeaning,
                ids, activityText, tagSpec.start, tagSpec.length);
        }
        
        // for each of the event's actual parameters, in their
        // modeled order
        ActualParameter_c param = 
            ActualParameter_c.getOneV_PAROnR700(
                eventSpec, firstActualParameterFinder);
        int paramIndex = 0;
        while (param != null) {
            // process this parameter
            detmTagSpecsForActualParameter(param, 
                (stateMachineEvent != null) ?
                    Relocatables.Types.stateMachineEventDerivedLabel :
                    Relocatables.Types.externalEntityEventDerivedLabel, 
                stateMachineEvent, externalEntityEvent, 
                tagSpecsHolder, activityText);
            
            // proceed to the next parameter
            param = ActualParameter_c.getOneV_PAROnR816Precedes(param);
            paramIndex++;
        }
        
        // if the statement is one that generates or creates an event to
        // a class state machine or class instance creator 
        GenerateToClass_c generateToClass = 
            GenerateToClass_c.getOneE_GAROnR705(generateStateMachineEvent);
        GenerateToCreator_c generateToCreator = 
            GenerateToCreator_c.getOneE_GECOnR705(generateStateMachineEvent);
        CreateEventToClass_c createToClass = 
            CreateEventToClass_c.getOneE_CEAOnR704(createStateMachineEvent);
        CreateEventToCreator_c createToCreator = 
            CreateEventToCreator_c.getOneE_CECOnR704(createStateMachineEvent);
        if (generateToClass != null || generateToCreator != null
            || createToClass != null || createToCreator != null) {
            // create a tag-spec for the target's key-letters
            StateMachine_c machine =  
                StateMachine_c.getOneSM_SMOnR502(stateMachineEvent);
            ModelClass_c clazz = OoaofooaUtil.getClass(machine);
            tagSpec = new TagSpec();
            tagSpecsHolder.add(tagSpec);
            tagSpec.start = getPositionWithinString(
                eventSpec.getEventtargetkeyletterslinenumber() - 1,
                eventSpec.getEventtargetkeyletterscolumn() - 1, 
                activityText);
            tagSpec.length = clazz.getKey_lett().length();
            tagSpec.tag = formTag(
                Relocatables.Types.modelClassKeyLetters,
                new UUID[] {clazz.getObj_id()}, 
                activityText, tagSpec.start, tagSpec.length);
        }
    }
    
    /**
     * Creates tag-specs for where tags should go within the given phrase of 
     * the given association.
     * 
     * @param phraseLineNumber, phraseColumn 
     *                          The line number and column of the phrase within 
     *                          the given activity text.
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          given phrase of the given association.
     */
    private static void detmTagSpecsForAssociationPhrase(
        Association_c assoc, String phrase, 
        int phraseLineNumber, int phraseColumn,
        List<TagSpec> tagSpecsHolder, String activityText)
    {
        // find the class-in-association instance which is related to 
        // the association and has the same phrase; its ID will be put
        // into the tag, below
        ClassInAssociation_c inAssoc =
            OoaofooaUtil.getClassInAssociation(assoc, phrase);

        // create a tag-spec for the phrase, taking into account whether
        // the phrase is ticked
        TagSpec tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        tagSpec.start = getPositionWithinString(
            phraseLineNumber - 1, phraseColumn - 1, activityText);
        tagSpec.length = phrase.length();
        if (phrase.charAt(0) == '\'') {
            tagSpec.start++;
            tagSpec.length -= 2;
        }
        tagSpec.tag = formTag(Relocatables.Types.associationPhrase,
            new UUID[] {inAssoc.getOir_id()}, 
            activityText, tagSpec.start, tagSpec.length);
    }
    
    /**
     * Creates tag-specs for where tags should go within a relate, unrelate,
     * relate-using, or unrelate-using statement.
     * 
     * @param assoc             The association specified in the relate 
     *                          statement.
     * @param assocNumberLineNumber, assocNumberColumn 
     *                          The line number and column within the given 
     *                          activity text of the association specified in 
     *                          the relate statement.
     * @param phrase            The phrase (if any) specified on the 
     *                          association.
     * @param phraseLineNumber, phraseColumn 
     *                          The line number and column of the phrase 
     *                          (if one is specified) within the given activity 
     *                          text.
     * @param tagSpecsHolder    Will have added to it the tag-specs created 
     *                          by this call.
     * @param activityText      The text of the activity which contains the 
     *                          relate statement.
     */
    private static void detmTagSpecsForRelateStatement(
        Association_c assoc, int assocNumberLineNumber, int assocNumberColumn,
        String phrase, int phraseLineNumber, int phraseColumn, 
        List<TagSpec> tagSpecsHolder, String activityText)
    {
        // create a tag-spec for the association number; 
        // note that we don't subtract one from the column because 
        // we want the tag to start after the "R" 
        TagSpec tagSpec = new TagSpec();
        tagSpecsHolder.add(tagSpec);
        tagSpec.start = getPositionWithinString(
            assocNumberLineNumber - 1, assocNumberColumn, activityText);
        tagSpec.length = Integer.toString(assoc.getNumb()).length();
        tagSpec.tag = formTag(Relocatables.Types.associationNumber,
            new UUID[] {assoc.getRel_id()}, 
            activityText, tagSpec.start, tagSpec.length);
    
        // if the relate-statement has a phrase
        if (!phrase.equals("")) {
            // process the phrase
            detmTagSpecsForAssociationPhrase(assoc, phrase, 
                phraseLineNumber,phraseColumn, tagSpecsHolder, activityText);
        }
    }
    
    /**
     * A test to be used when trying to find the first actual parameter 
     * instance in a sequence of such parameters (as in, for an invocation). 
     */
    private static ClassQueryInterface_c firstActualParameterFinder =
        new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ActualParameter_c.getOneV_PAROnR816Succeeds(
                    (ActualParameter_c)candidate) == null;
            }
        };
}
