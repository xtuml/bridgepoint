package com.mentor.nucleus.bp.io.mdl.test.dangle;

import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.AttributeReferenceInClass_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassIdentifierAttribute_c;
import com.mentor.nucleus.bp.core.ClassIdentifier_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.ReferredToClassInAssoc_c;
import com.mentor.nucleus.bp.core.ReferredToIdentifierAttribute_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.test.TestUtil;

//=====================================================================
//
//File:      $RCSfile: DanglingReferenceTest.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 23:13:31 $
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

public class DanglingReferenceTest extends DRBaseTest {

    public DanglingReferenceTest(String name) {
        super(name);
        
    }

    // /remove dangling marker by replace with history

    public void testDanglingFunctionParam() throws Exception {
        FunctionParameter_c param = FunctionParameter_c
                .FunctionParameterInstance(modelRoot,
                        new FunctionParam_by_name_c("FParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR26(param));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(param, dt);
    }

    public void testDanglingFunctionReturnType() throws Exception {
        Function_c func = Function_c.FunctionInstance(modelRoot,
                new Function_by_name_c("fFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR25(func));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(func, dt);
    }

    public void testDanglingBridgeParam() throws Exception {
        BridgeParameter_c me = BridgeParameter_c.BridgeParameterInstance(
                modelRoot, new BridgeParam_by_name_c("BParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR22(me));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(me, dt);
    }

    public void testDanglingBridgeReturnType() throws Exception {
        Bridge_c me = Bridge_c.BridgeInstance(modelRoot, new Bridge_by_name_c(
                "bFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR20(me));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(me, dt);

    }

    public void testDanglingOperationParam() throws Exception {
        OperationParameter_c me = OperationParameter_c
                .OperationParameterInstance(modelRoot,
                        new OperationParam_by_name_c("ZParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR118(me));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(me, dt);
    }

    public void testDanglingOperationReturnType() throws Exception {
        Operation_c me = Operation_c.OperationInstance(modelRoot,
                new Operation_by_name_c("zFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR116(me));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(me, dt);
    }

    public void testDanglingEvtDataItem() throws Exception {
        StateMachineEventDataItem_c me = StateMachineEventDataItem_c
                .StateMachineEventDataItemInstance(modelRoot,
                        new StateMachineEventDataItem_by_name_c("State"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR524(me));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(me, dt);

    }

    public void testDanglingAttributeDataType() throws Exception {
        Attribute_c me = Attribute_c.AttributeInstance(modelRoot,
                new Attribute_by_name_c("A_Attr"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR114(me));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithHistory(me, dt);
    }

    public void testDanglingImportedClass() throws Exception {
        ImportedClass_c me = ImportedClass_c.ImportedClassInstance(modelRoot,
                new ImportedClass_by_name_c("Z"));
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR101(me);

        // TODO - when I run with this, the dialog never shows.  Not sure why this
        // doesn't work.  But it would be great to have the dialog automatically dismissed.
        TestUtil.selectButtonInDialog(7000, "&Yes");
        performDRCheckByReplaceWithHistory(me, mc);
        checkoutProject=true;
    }

    // /remove dangling marker by replacing dangling element with other element
    public void testDanglingEvtDataItemReplace() throws Exception {
        StateMachineEventDataItem_c me = StateMachineEventDataItem_c
                .StateMachineEventDataItemInstance(modelRoot,
                        new StateMachineEventDataItem_by_name_c("State"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR524(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
    }

    public void testDanglingAttributeReplace() throws Exception {
        ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
                new ModelClass_by_name_c("A"));
        Attribute_c me = Attribute_c.getOneO_ATTROnR102(mc,
                new Attribute_by_name_c("A_Attr"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR114(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
    }

    public void testDanglingFunctionParamReplace() throws Exception {
        FunctionParameter_c me = FunctionParameter_c.FunctionParameterInstance(
                modelRoot, new FunctionParam_by_name_c("FParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR26(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
    }

    public void testDanglingFunctionReturnTypeReplace() throws Exception {
        Function_c func = Function_c.FunctionInstance(modelRoot,
                new Function_by_name_c("fFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR25(func));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(func, dt, newDT);

    }

    public void testDanglingBridgeParamReplace() throws Exception {
        BridgeParameter_c me = BridgeParameter_c.BridgeParameterInstance(
                modelRoot, new BridgeParam_by_name_c("BParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR22(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
    }

    public void testDanglingBridgeReturnTypeReplace() throws Exception {
        Bridge_c me = Bridge_c.BridgeInstance(modelRoot, new Bridge_by_name_c(
                "bFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR20(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
    }

    public void testDanglingOperationParamReplace() throws Exception {
        OperationParameter_c me = OperationParameter_c
                .OperationParameterInstance(modelRoot,
                        new OperationParam_by_name_c("ZParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR118(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
    }

    public void testDanglingOperationReturnTypeReplace() throws Exception {
        Operation_c me = Operation_c.OperationInstance(modelRoot,
                new Operation_by_name_c("zFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR116(me));
        DataType_c newDT = DataType_c.DataTypeInstance(modelRoot,
                new Datatype_by_name_c("boolean"));
        TestUtil.okToDialog(500);
        performDRCheckByReplaceWithElement(me, dt, newDT);
        checkoutProject = true;
    }

    // /remove dangling marker by removing RGO
    public void testDanglingEvtDataItemRemoveRGO() throws Exception {
        StateMachineEventDataItem_c me = StateMachineEventDataItem_c
                .StateMachineEventDataItemInstance(modelRoot,
                        new StateMachineEventDataItem_by_name_c("State"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR524(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
    }

    public void testDanglingAttributeRemoveRGO() throws Exception {
        Attribute_c me = Attribute_c.AttributeInstance(modelRoot,
                new Attribute_by_name_c("A_Attr"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR114(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
    }

    public void testDanglingFunctionParamRemoveRGO() throws Exception {
        Function_c func = Function_c.FunctionInstance(modelRoot,
                new Function_by_name_c("fFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR25(func));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(func, dt);
    }

    public void testDanglingFunctionReturnTypeRemoveRGO() throws Exception {
        FunctionParameter_c me = FunctionParameter_c.FunctionParameterInstance(
                modelRoot, new FunctionParam_by_name_c("FParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR26(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
    }

    public void testDanglingBridgeParamRemoveRGO() throws Exception {
        BridgeParameter_c me = BridgeParameter_c.BridgeParameterInstance(
                modelRoot, new BridgeParam_by_name_c("BParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR22(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
    }

    public void testDanglingBridgeReturnTypeRemoveRGO() throws Exception {
        Bridge_c me = Bridge_c.BridgeInstance(modelRoot, new Bridge_by_name_c(
                "bFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR20(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
    }

    public void testDanglingOperationParamRemoveRGO() throws Exception {
        OperationParameter_c me = OperationParameter_c
                .OperationParameterInstance(modelRoot,
                        new OperationParam_by_name_c("ZParam"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR118(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
    }

    public void testDanglingOperationReturnTypeRemoveRGO() throws Exception {
        Operation_c me = Operation_c.OperationInstance(modelRoot,
                new Operation_by_name_c("zFunc"));
        UserDataType_c dt = UserDataType_c.getOneS_UDTOnR17(DataType_c
                .getOneS_DTOnR116(me));
        TestUtil.okToDialog(500);
        performDRCheckByDeletingRGO(me, dt);
        project.delete(true, true, new NullProgressMonitor());
    }
}
