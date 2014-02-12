package com.mentor.nucleus.bp.ui.properties.test;
//=====================================================================
//
//File:      $RCSfile: NumberRangeTest.java,v $
//Version:   $Revision: 1.13 $
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

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.properties.AssociationR_RELPropertySource;
import com.mentor.nucleus.bp.ui.properties.ClassO_OBJPropertySource;
import com.mentor.nucleus.bp.ui.properties.EventSM_EVTPropertySource;
import com.mentor.nucleus.bp.ui.properties.IntegerPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.StateSM_STATEPropertySource;

public class NumberRangeTest extends BaseTest
{
    public NumberRangeTest(String name) {
        super(null, name);
    }
    private void verifyRange(String prop, IPropertyDescriptor[] pd_set)
    {
        for ( int i = 0; i < pd_set.length; ++i)
        {
            if ( pd_set[i].getId().equals(prop) )
            {
                IntegerPropertyDescriptor ipd = (IntegerPropertyDescriptor)pd_set[i];
                IntegerPropertyDescriptor.IntegerCellValidator icv = 
                   (IntegerPropertyDescriptor.IntegerCellValidator)ipd.getValidator();
                String result = icv.isValid("");
                assertEquals( "Value between 0 and 9999 required", result);
                result = icv.isValid("x");
                assertEquals( "Value is not a number", result);
                result = icv.isValid("-1");
                assertEquals( "Value must be >= 0", result);
                result = icv.isValid("0");
                assertNull(result);
                result = icv.isValid("1");
                assertNull(result);
                result = icv.isValid("9999");
                assertNull(result);
                result = icv.isValid("10000");
                assertEquals( "Value must be <= 9999", result);
                break;
            }
        }
    }
    public void testModelClass() throws Exception
    {
        // Numb: 0 .. 9999
        ModelClass_c inst = ModelClass_c.ModelClassInstance(modelRoot);
        ClassO_OBJPropertySource ps = new ClassO_OBJPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
		verifyRange("Numb", pd_set);
    }
    public void testAssociation() throws Exception
    {
        // Numb: 0 .. 9999
        Association_c inst = Association_c.AssociationInstance(modelRoot);
        AssociationR_RELPropertySource ps = new AssociationR_RELPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        verifyRange("Numb", pd_set);
    }
    public void testStateMachineEvent() throws Exception
    {
        // Numb: 0 .. 9999
        StateMachineEvent_c inst = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
        EventSM_EVTPropertySource ps = new EventSM_EVTPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        verifyRange("Numb", pd_set);
    }
    public void testStateMachineState() throws Exception
    {
        // Numb: 0 .. 9999
        StateMachineState_c inst = StateMachineState_c.StateMachineStateInstance(modelRoot);
        StateSM_STATEPropertySource ps = new StateSM_STATEPropertySource(inst);
        IPropertyDescriptor[] pd_set = ps.getPropertyDescriptors();
        verifyRange("Numb", pd_set);
    }
    public void testStateMachineConfig_ID() throws Exception
    {
      // readonly ... no tests
    }


}