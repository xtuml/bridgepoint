package com.mentor.nucleus.bp.ui.properties.test;
//=====================================================================
//
//File:      $RCSfile: DeleteUDTWithPropertyShowing.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:15:22 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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