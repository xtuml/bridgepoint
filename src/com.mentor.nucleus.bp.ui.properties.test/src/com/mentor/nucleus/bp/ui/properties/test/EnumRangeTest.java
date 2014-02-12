package com.mentor.nucleus.bp.ui.properties.test;
//=====================================================================
//
//File:      $RCSfile: EnumRangeTest.java,v $
//Version:   $Revision: 1.22 $
//Modified:  $Date: 2013/05/10 05:34:58 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassAsDerivedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsDerivedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassAsLink_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleFormalizer_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleParticipant_c;
import com.mentor.nucleus.bp.core.CommunicationLink_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.SimpleAssociation_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.properties.ActionSM_ACTPropertySource;
import com.mentor.nucleus.bp.ui.properties.AssociationFormalizerEndR_FORMPropertySource;
import com.mentor.nucleus.bp.ui.properties.AssociationParticipantEndR_PARTPropertySource;
import com.mentor.nucleus.bp.ui.properties.AssociativeOneEndR_AONEPropertySource;
import com.mentor.nucleus.bp.ui.properties.AssociativeOtherEndR_AOTHPropertySource;
import com.mentor.nucleus.bp.ui.properties.AssociativeR_ASSRPropertySource;
import com.mentor.nucleus.bp.ui.properties.AttributeO_ATTRPropertySource;
import com.mentor.nucleus.bp.ui.properties.BridgeParameterS_BPARMPropertySource;
import com.mentor.nucleus.bp.ui.properties.BridgeS_BRGPropertySource;
import com.mentor.nucleus.bp.ui.properties.CommunicationLinksCOMM_LNKPropertySource;
import com.mentor.nucleus.bp.ui.properties.CoreDataTypeS_CDTPropertySource;
import com.mentor.nucleus.bp.ui.properties.DerivedAttributeO_DBATTRPropertySource;
import com.mentor.nucleus.bp.ui.properties.DerivedOneEndR_CONEPropertySource;
import com.mentor.nucleus.bp.ui.properties.DerivedOtherEndR_COTHPropertySource;
import com.mentor.nucleus.bp.ui.properties.EnumPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.EventSM_EVTPropertySource;
import com.mentor.nucleus.bp.ui.properties.FunctionParameterS_SPARMPropertySource;
import com.mentor.nucleus.bp.ui.properties.FunctionS_SYNCPropertySource;
import com.mentor.nucleus.bp.ui.properties.OperationO_TFRPropertySource;
import com.mentor.nucleus.bp.ui.properties.OperationParameterO_TPARMPropertySource;
import com.mentor.nucleus.bp.ui.properties.ReferentialAttributeO_RATTRPropertySource;
import com.mentor.nucleus.bp.ui.properties.StateSM_STATEPropertySource;
import com.mentor.nucleus.bp.ui.properties.UserDefinedDataTypeS_UDTPropertySource;

public class EnumRangeTest extends BaseTest
{
    public EnumRangeTest(String name) {
        super(null, name);
    }
    public static final String[] Return_Dimensions_vals =
    {
        "None",
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Six",
        "Seven",
        "Eight" };
    public static final String[] Dimensions_vals =
    {
        "None",
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Six",
        "Seven",
        "Eight" };
    public static final String[] Suc_Pars_vals =
        {
            "Do not parse",
            "Parse successful",
            "Parse unsuccessful",
            "Do parse" };
    public static final String[] By_Ref_vals = { "By Value", "By Reference" };
    public static final String[] Cond_vals = { "Unconditional", "Conditional" };
    public static final String[] Mult_vals = { "One", "Many" };
    public static final String[] Pfx_Mode_vals =
        { "No Prefix", "Prefix", "Referred To Prefix" };
    public static final String[] Ref_Mode_vals =
        { "Local Attribute", "Referred To Attribute" };
    public static final String[] Instance_Based_vals = { "Class Based", "Instance Based" };
    public static final String[] SM_EVT_Is_Lbl_U_vals =
        { "Class Keyletters", "Custom Keyletters" };
    public static final String[] Final_vals = { "Non-final state", "Final state" };
    public static final String[] S_EEEVT_Is_Lbl_U_vals =
        { "External Entity Keyletters", "Custom Keyletters" };
    
    public static final String[] StartVisibility_vals = {"Private", "Public", "Protected"};
    public static final String[] EndVisibility_vals = {"Private", "Public", "Protected"};
    
    public static final String[] Core_Typ_vals =
        {
            "void",
            "boolean",
            "integer",
            "real",
            "string",
            "unique_id",
            "state<State_Model>",
            "same_as<Base_Attribute>",
            "inst_ref<Object>",
            "inst_ref_set<Object>",
            "inst<Event>",
            "inst<Mapping>",
            "inst_ref<Mapping>",
            "component_ref"
};
    public static String[] Full_Der_vals = { "Partially Derived", "Fully Derived" };
    public static String[] Brg_Typ_vals = { "User-Defined", "Built-in" };
    public static String[] Oid_ID_vals = { "*1", "*2", "*3" };
    public static String[] Gen_Type_vals =
        { "User Defined", "date", "timestamp", "inst_ref<Timer>" };

    private void validateEnumVals(String prop, IPropertyDescriptor[] pd_set, String[] vals)
    {
        for ( int i = 0; i < pd_set.length; ++i)
        {
            if ( pd_set[i].getId().equals(prop) )
            {
                EnumPropertyDescriptor epd = (EnumPropertyDescriptor)pd_set[i];
                assertEquals( vals.length, epd.numValues() );
                for ( int j = 0; j < vals.length; ++j )
                {
                    assertEquals( vals[j], epd.enumValue(j));
                }
                return;
            }
        }
        fail("Property "+prop+" not found");
    }
    private void validateSuc_Pars(IPropertyDescriptor[] pd_set)
    {
        validateEnumVals("Suc_Pars", pd_set, Suc_Pars_vals);
    }
    private void validateBy_Ref(IPropertyDescriptor[] pd_set)
    {
        validateEnumVals("By_Ref", pd_set, By_Ref_vals);
    }
    private void validateMult(IPropertyDescriptor[] pd_set)
    {
        validateEnumVals("Mult", pd_set, Mult_vals);
    }
    private void validateOneOnlyMult(IPropertyDescriptor[] pd_set)
    {
        String[] onlyOne = { "One" };
        validateEnumVals("Mult", pd_set, onlyOne);
    }
    private void validateCond(IPropertyDescriptor[] pd_set)
    {
        validateEnumVals("Cond", pd_set, Cond_vals);
    }
    
    public void testAttributePfx_Mode() throws Exception
    {
        Attribute_c inst = Attribute_c.AttributeInstance(modelRoot);
        AttributeO_ATTRPropertySource ps = new AttributeO_ATTRPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Pfx_Mode", pd_set, Pfx_Mode_vals);
    }

    public void testReferentialAttributeRef_Mode() throws Exception
    {
        ReferentialAttribute_c inst = ReferentialAttribute_c.ReferentialAttributeInstance(modelRoot);
        ReferentialAttributeO_RATTRPropertySource ps = new ReferentialAttributeO_RATTRPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Ref_Mode", pd_set, Ref_Mode_vals);
    }

    public void testOperationInstance_Based() throws Exception
    {
        Operation_c inst = Operation_c.OperationInstance(modelRoot);
        OperationO_TFRPropertySource ps = new OperationO_TFRPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Instance_Based", pd_set, Instance_Based_vals);
    }

    public void testStateMachineEventIs_Lbl_U() throws Exception
    {
        StateMachineEvent_c inst = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
        EventSM_EVTPropertySource ps = new EventSM_EVTPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Is_Lbl_U", pd_set, SM_EVT_Is_Lbl_U_vals);
    }

    public void testStateMachineStateFinal() throws Exception
    {
        StateMachineState_c inst = StateMachineState_c.StateMachineStateInstance(modelRoot);
        StateSM_STATEPropertySource ps = new StateSM_STATEPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Final", pd_set, Final_vals);
    }

    public void testDerivedBaseAttributeSuc_Pars() throws Exception
    {
        DerivedBaseAttribute_c inst = DerivedBaseAttribute_c.DerivedBaseAttributeInstance(modelRoot);
        DerivedAttributeO_DBATTRPropertySource ps = new DerivedAttributeO_DBATTRPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateSuc_Pars(pd_set);
    }

    public void testImportedClassModl_Typ() throws Exception
    {
        // readonly ... no tests
        // also the enum values are 5, 6, 7 which aren't supported 
        // by the archetypes currently
    }
    public void testOperationParameterBy_Ref() throws Exception
    {
        OperationParameter_c inst = OperationParameter_c.OperationParameterInstance(modelRoot);
        OperationParameterO_TPARMPropertySource ps = new OperationParameterO_TPARMPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateBy_Ref(pd_set);
    }

    public void testOperationSuc_Pars() throws Exception
    {
        Operation_c inst = Operation_c.OperationInstance(modelRoot);
        OperationO_TFRPropertySource ps = new OperationO_TFRPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateSuc_Pars(pd_set);
    }

    public void testAoneMultCond() throws Exception
    {
        ClassAsAssociatedOneSide_c inst = ClassAsAssociatedOneSide_c.ClassAsAssociatedOneSideInstance(modelRoot);
        AssociativeOneEndR_AONEPropertySource ps = new AssociativeOneEndR_AONEPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        validateCond(pd_set);
    }

    public void testAOthMultCond() throws Exception
    {
        ClassAsAssociatedOtherSide_c inst = ClassAsAssociatedOtherSide_c.ClassAsAssociatedOtherSideInstance(modelRoot);
        AssociativeOtherEndR_AOTHPropertySource ps = new AssociativeOtherEndR_AOTHPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        validateCond(pd_set);
    }

    public void testCOneMultCond() throws Exception
    {
        ClassAsDerivedOneSide_c inst = ClassAsDerivedOneSide_c.ClassAsDerivedOneSideInstance(modelRoot);
        DerivedOneEndR_CONEPropertySource ps = new DerivedOneEndR_CONEPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        validateCond(pd_set);
    }

    public void testCOthMult() throws Exception
    {
        ClassAsDerivedOtherSide_c inst = ClassAsDerivedOtherSide_c.ClassAsDerivedOtherSideInstance(modelRoot);
        DerivedOtherEndR_COTHPropertySource ps = new DerivedOtherEndR_COTHPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        validateCond(pd_set);
    }

    public void testLinkMult() throws Exception
    {
        ClassAsLink_c inst = ClassAsLink_c.ClassAsLinkInstance(modelRoot);
        AssociativeR_ASSRPropertySource ps = new AssociativeR_ASSRPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
    }

    public void testPartMultCond() throws Exception
    {
        ClassAsSimpleParticipant_c inst = ClassAsSimpleParticipant_c.ClassAsSimpleParticipantInstance(modelRoot);
        AssociationParticipantEndR_PARTPropertySource ps = new AssociationParticipantEndR_PARTPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateOneOnlyMult(pd_set);
        validateCond(pd_set);
        
        // get an unformalized relationship
        Association_c assoc = Association_c.AssociationInstance(modelRoot, 
            new ClassQueryInterface_c(){
				public boolean evaluate(Object candidate)
				{
					Association_c selected = (Association_c) candidate;
					return selected.getNumb() == 5;
				}
            }
        );
        ClassAsSimpleParticipant_c [] part_set = ClassAsSimpleParticipant_c.getManyR_PARTsOnR207(
           SimpleAssociation_c.getOneR_SIMPOnR206(assoc));
        assertEquals( 2, part_set.length);

        ps = new AssociationParticipantEndR_PARTPropertySource(part_set[0]);
        pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        ps = new AssociationParticipantEndR_PARTPropertySource(part_set[1]);
        pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);

        // set the first one to many
        part_set[0].setMult(1);

        // they both can still be many
        ps = new AssociationParticipantEndR_PARTPropertySource(part_set[0]);
        pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        ps = new AssociationParticipantEndR_PARTPropertySource(part_set[1]);
        pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);

        // set the second one to many
        part_set[1].setMult(1);

        // formalize is no longer an option
        assertFalse( assoc.Actionfilter("type", "unform simp") );
    }

    public void testFormMultCond() throws Exception
    {
        ClassAsSimpleFormalizer_c inst = ClassAsSimpleFormalizer_c.ClassAsSimpleFormalizerInstance(modelRoot);
        AssociationFormalizerEndR_FORMPropertySource ps = new AssociationFormalizerEndR_FORMPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateMult(pd_set);
        validateCond(pd_set);
    }

    public void testBridgeParameterBy_Ref() throws Exception
    {
        BridgeParameter_c inst = BridgeParameter_c.BridgeParameterInstance(modelRoot);
        BridgeParameterS_BPARMPropertySource ps = new BridgeParameterS_BPARMPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateBy_Ref(pd_set);
    }

    public void testBridgeBrg_Typ() throws Exception
    {
        Bridge_c inst = Bridge_c.BridgeInstance(modelRoot);
        BridgeS_BRGPropertySource ps = new BridgeS_BRGPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Brg_Typ", pd_set, Brg_Typ_vals);
    }
    public void testBridgeSuc_Pars() throws Exception
    {
        Bridge_c inst = Bridge_c.BridgeInstance(modelRoot);
        BridgeS_BRGPropertySource ps = new BridgeS_BRGPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateSuc_Pars(pd_set);
    }

    public void testCoreDataTypeCore_Typ() throws Exception
    {
        CoreDataType_c inst = CoreDataType_c.CoreDataTypeInstance(Ooaofooa.getDefaultInstance());
        CoreDataTypeS_CDTPropertySource ps = new CoreDataTypeS_CDTPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Core_Typ", pd_set, Core_Typ_vals);
    }

    public void testFunctionParameterBy_Ref() throws Exception
    {
        FunctionParameter_c inst = FunctionParameter_c.FunctionParameterInstance(modelRoot);
        FunctionParameterS_SPARMPropertySource ps = new FunctionParameterS_SPARMPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateBy_Ref(pd_set);
    }

    public void testFunctionSuc_Pars() throws Exception
    {
        Function_c inst = Function_c.FunctionInstance(modelRoot);
        FunctionS_SYNCPropertySource ps = new FunctionS_SYNCPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateSuc_Pars(pd_set);
    }

    public void testUserDataTypeGen_Type() throws Exception
    {
        UserDataType_c inst = UserDataType_c.UserDataTypeInstance(modelRoot);
        UserDefinedDataTypeS_UDTPropertySource ps = new UserDefinedDataTypeS_UDTPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateEnumVals("Gen_Type", pd_set, Gen_Type_vals);
    }
    public void testActionSuc_Pars() throws Exception
    {
        Action_c inst = Action_c.ActionInstance(modelRoot);
        ActionSM_ACTPropertySource ps = new ActionSM_ACTPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        validateSuc_Pars(pd_set);
    }
    public void testLinkStartVisibility() throws Exception
    {
    	CommunicationLink_c inst = CommunicationLink_c.CommunicationLinkInstance(modelRoot);
    	CommunicationLinksCOMM_LNKPropertySource ps = new CommunicationLinksCOMM_LNKPropertySource(inst);
    	IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
    	validateEnumVals("StartVisibility", pd_set, StartVisibility_vals);
    }
    public void testLinkEndVisibility() throws Exception
    {
    	CommunicationLink_c inst = CommunicationLink_c.CommunicationLinkInstance(modelRoot);
    	CommunicationLinksCOMM_LNKPropertySource ps = new CommunicationLinksCOMM_LNKPropertySource(inst);
    	IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
    	validateEnumVals("EndVisibility", pd_set, EndVisibility_vals);
    }
}