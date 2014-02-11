package com.mentor.nucleus.bp.ui.properties.test;
//=====================================================================
//
//File:      $RCSfile: DeleteUDTWithPropertyShowing.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:15:22 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class DeleteUDTWithPropertyShowing extends BaseTest
{
	public DeleteUDTWithPropertyShowing(String name) {
        super(null, name);
    }

	public void testDeleteUDT() throws Exception
    {
		Ooaofooa.setPersistEnabled(false);
		UserDataType_c inst = UserDataType_c.UserDataTypeInstance(modelRoot);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(inst);
		DeleteAction da = new DeleteAction(null);
		da.run();
		Display d = Display.getDefault();
		while ( d.readAndDispatch() )
			;
		assertEquals(false, Selection.getInstance().contains(inst));
    }


}