package com.mentor.nucleus.bp.ui.properties.test;
//=====================================================================
//
//File:      $RCSfile: IsAllowedTypeTest.java,v $
//Version:   $Revision: 1.21 $
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

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ElementVisibility_c;
import com.mentor.nucleus.bp.core.Elementtypeconstants_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InstanceReferenceDataType_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.SearchResultSet_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class IsAllowedTypeTest extends BaseTest
{
    public IsAllowedTypeTest(String name) {
        super(null, name);
    }

    static boolean initialized = false;

    protected void setUp() throws Exception {
        super.setUp();
        if ( !initialized )
        {
            PersistableModelComponent domainComponent = ensureAvailableAndLoaded("testProp", false);
            modelRoot = (Ooaofooa)domainComponent.getRootModelElement().getModelRoot();
            initialized = true;
        }
    }

    private void checkAllowedType(DataType_c dt, String [] attributeTypes, boolean result, boolean allowNonCoreDT)
    {
        boolean allowedType = false;
        if ( allowNonCoreDT )
        {
            UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dt);
            EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(dt);
            StructuredDataType_c sdt = StructuredDataType_c.getOneS_SDTOnR17(dt);
            InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c.getOneS_IRDTOnR17(dt);
            if ( udt != null || edt != null || sdt != null || irdt != null )
            {
            	if(udt != null && !result) {
            		if(udt.getGen_type() != 0) {
            			allowedType = false;
            		} else {
            			allowedType = true;
            		}
            	} else {
            		allowedType = true;
            	}
            }
        }
        if ( !allowedType )
        {
            for ( int j = 0; j < attributeTypes.length; ++j )
            {
                if ( attributeTypes[j].equals(dt.getName()) )
                {
                    allowedType = true;
                    break;
                }
            }
        }
        assertEquals(dt.getName(), result, allowedType );
    }

    private static final String attributeAllowedCoreTypes[] = {
        "boolean", "integer", "real", "string", "unique_id",
        "inst<Event>", "state<State_Model>", "component_ref"
    };
    public void testAttributeIsAllowedType() throws Exception
    {
        BaseAttribute_c battr = BaseAttribute_c.BaseAttributeInstance(modelRoot);
        Attribute_c attr = Attribute_c.getOneO_ATTROnR106(battr);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(ModelClass_c.getOneO_OBJOnR102(attr)));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = attr.Isallowedtype(dt_set[i].getName());
			checkAllowedType(dt_set[i], attributeAllowedCoreTypes, x, true);
        }
    }
   
	private DataType_c[] getDataTypes(PackageableElement_c pe) {
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(pe);
		pkg.Clearscope();
		pkg.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), false,
				"", pkg.getPackage_id(), Elementtypeconstants_c.DATATYPE);
		class PETest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				SearchResultSet_c selected = (SearchResultSet_c) candidate;
				return (selected.getName().equals("") && selected.getType() == Elementtypeconstants_c.DATATYPE);
			}
		}
		SearchResultSet_c results = SearchResultSet_c.getOnePE_SRSOnR8005(pkg,
				new PETest());
		PackageableElement_c[] pes = PackageableElement_c
				.getManyPE_PEsOnR8002(ElementVisibility_c
						.getManyPE_VISsOnR8006(results));
		return DataType_c.getManyS_DTsOnR8001(pes);
	}

	private static final String refAttributeAllowedCoreTypes[] = {
        "integer"   // the type of the referred to attribute
    };
    public void testRefAttributeIsAllowedType() throws Exception
    {
        ReferentialAttribute_c rattr = ReferentialAttribute_c.ReferentialAttributeInstance(modelRoot);
        Attribute_c attr = Attribute_c.getOneO_ATTROnR106(rattr);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(ModelClass_c.getOneO_OBJOnR102(attr)));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = attr.Isallowedtype(dt_set[i].getName());
            checkAllowedType(dt_set[i], refAttributeAllowedCoreTypes, x, false);
        }
    }
    private static final String invocationAllowedReturnTypes[] = {
        "void", "boolean", "integer", "real", "string", "unique_id",
        "inst<Event>", "component_ref"
    };
    public void testBridgeIsAllowedReturnType() throws Exception
    {
        Bridge_c brg = Bridge_c.BridgeInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(ExternalEntity_c.getOneS_EEOnR19(brg)));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = brg.Isallowedreturntype(dt_set[i].getName());
            checkAllowedType(dt_set[i], invocationAllowedReturnTypes, x, true);
        }
    }
    public void testFunctionIsAllowedReturnType() throws Exception
    {
        Function_c func = Function_c.FunctionInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(func));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = func.Isallowedreturntype(dt_set[i].getName());
            checkAllowedType(dt_set[i], invocationAllowedReturnTypes, x, true);
        }
    }
    public void testOperationIsAllowedReturnType() throws Exception
    {
        Operation_c op = Operation_c.OperationInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(ModelClass_c.getOneO_OBJOnR115(op)));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = op.Isallowedreturntype(dt_set[i].getName());
            checkAllowedType(dt_set[i], invocationAllowedReturnTypes, x, true);
        }
    }
    private static final String parameterAllowedCoreTypes[] = {
        "boolean", "integer", "real", "string", "unique_id",
        "inst<Event>", "component_ref"
    };
    public void testBridgeParameterIsAllowedType()
    {
        BridgeParameter_c brg = BridgeParameter_c.BridgeParameterInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(ExternalEntity_c.getOneS_EEOnR19(Bridge_c.getOneS_BRGOnR21(brg))));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = brg.Isallowedtype(dt_set[i].getName());
            checkAllowedType(dt_set[i], parameterAllowedCoreTypes, x, true);
        }
    }
    public void testFunctionParameterIsAllowedType()
    {
        FunctionParameter_c func = FunctionParameter_c.FunctionParameterInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(Function_c.getOneS_SYNCOnR24(func)));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = func.Isallowedtype(dt_set[i].getName());
            checkAllowedType(dt_set[i], parameterAllowedCoreTypes, x, true);
        }
    }
    public void testOperationParameterIsAllowedType()
    {
        OperationParameter_c op = OperationParameter_c.OperationParameterInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(ModelClass_c.getOneO_OBJOnR115(Operation_c.getOneO_TFROnR117(op))));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = op.Isallowedtype(dt_set[i].getName());
            checkAllowedType(dt_set[i], parameterAllowedCoreTypes, x, true);
        }
    }

    public void testStateMachineEventDataItemIsAllowedType()
    {
        StateMachineEventDataItem_c edi = StateMachineEventDataItem_c.StateMachineEventDataItemInstance(modelRoot);
		DataType_c[] dt_set = getDataTypes(PackageableElement_c
				.getOnePE_PEOnR8001(ModelClass_c
						.getOneO_OBJOnR519(ClassStateMachine_c
								.getOneSM_ASMOnR517(StateMachine_c
										.getOneSM_SMOnR502(StateMachineEvent_c
												.getOneSM_EVTOnR532(edi))))));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = edi.Isallowedtype(dt_set[i].getName());
            checkAllowedType(dt_set[i], parameterAllowedCoreTypes, x, true);
        }
    }
    private static final String udtAllowedCoreTypes[] = {
        "boolean", "integer", "real", "string", "unique_id",
        "inst<Mapping>", "inst_ref<Mapping>", "component_ref"
    };
    public void testUserDataTypeIsAllowedCoreType()
    {
        UserDataType_c udt = UserDataType_c.UserDataTypeInstance(modelRoot);
        DataType_c [] dt_set = getDataTypes(PackageableElement_c.getOnePE_PEOnR8001(DataType_c.getOneS_DTOnR17(udt)));
        for ( int i = 0; i < dt_set.length; ++i)
        {
            boolean x = udt.Isallowedcoretype(dt_set[i].getName());
            checkAllowedType(dt_set[i], udtAllowedCoreTypes, x, true);
        }
    }
}
