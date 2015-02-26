//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

package lib;

public class BT {
	
	// ========================================================================
	// Bridge: test_pass_int_by_val_realized
	// ========================================================================
	public static void test_pass_by_val_realized(int Int1, boolean Bool1, float Real1, long Unique_ID1, String String1) {
		
		System.out.println("Enter test_pass_by_val_realized");
		
		if (Int1 == 15)
			System.out.println("LogSuccess:  param Int1 in test_pass_by_val_realized does equal expected value, " + Int1);
		else
			System.out.println("LogFailure:  param Int1 in test_pass_by_val_realized does not equal expected value, " + Int1);
		
		if (Bool1 == false)
			System.out.println("LogSuccess:  param Bool1 in test_pass_by_val_realized does equal expected value, " + Bool1);
		else
			System.out.println("LogFailure:  param Bool1 in test_pass_by_val_realized does not equal expected value, " + Bool1);
		
		if (Real1 == 1.1F)
			System.out.println("LogSuccess:  param Real1 in test_pass_by_val_realized does equal expected value, " + Real1);
		else
			System.out.println("LogFailure:  param Real1 in test_pass_by_val_realized does not equal expected value, " + Real1);
		
		System.out.println("LogInfo:  param Unique_ID1 in test_pass_by_val_realized is " + Unique_ID1);		
		
		if (String1.equals("test"))
			System.out.println("LogSuccess:  param String1 in test_pass_by_val_realized does equal expected value, " + String1);
		else
			System.out.println("LogFailure:  param String1 in test_pass_by_val_realized does not equal expected value, " + String1);		
		
		// Attempt to modify the values here. The changes should only effect the
		// values in this scope
		Int1 = 99;
		Bool1 = true;
		Real1 = 10.9F;
		Unique_ID1 = 800;
		String1 = "fail";
		
		System.out.println("Exiting test_pass_by_val_realized");
	}
	
	// ========================================================================
	// Bridge: test_pass_int_by_ref_realized
	// ========================================================================
	public static void test_pass_by_ref_realized(BPInteger Int1, BPBoolean Bool1, BPFloat Real1, BPUniqueId Unique_ID1, BPUniqueId Unique_ID2, BPString String1) {

		System.out.println("Enter test_pass_by_ref_realized");
		
		System.out.println("test int pass by ref, before modification:  " + Int1.getValue());
		Int1.setValue(Int1.getValue() + 500);
		System.out.println("test int pass by ref, after modification:  " + Int1.getValue());
		
		System.out.println("test bool pass by ref, before modification:  " + Bool1.getValue());
		Bool1.setValue(true);
		System.out.println("test bool pass by ref, after modification:  " + Bool1.getValue());
		
		System.out.println("test real pass by ref, before modification:  " + Real1.getValue());
		Real1.setValue(5.5F);
		System.out.println("test real pass by ref, after modification:  " + Real1.getValue());
		
		System.out.println("test unique_id pass by ref, before modification:  " + Unique_ID1.getValue());
		Unique_ID1.setValue(Unique_ID2.getValue());
		System.out.println("test unique_id pass by ref, after modification:  " + Unique_ID1.getValue());
		
		System.out.println("test String pass by ref, before modification:  " + String1.getValue());
		String1.setValue("passed");
		System.out.println("test String pass by ref, after modification:  " + String1.getValue());
		
		System.out.println("Exiting test_pass_by_ref_realized");
	}
}  // class BT
