//========================================================================
//
//File:      $RCSfile: CutAction.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/09/13 03:38:33 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.ui;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.TransactionManager;

public abstract class CutAction extends CopyCutAction {

	public CutAction() {
		super();
	}

	public void postRun() {
		((DeleteAction)CorePlugin.getDeleteAction()).setStartTransaction(false);
		((DeleteAction) CorePlugin.getDeleteAction()).deleteSelection(Selection.getInstance().getStructuredSelection());
		((DeleteAction)CorePlugin.getDeleteAction()).setStartTransaction(true);
	}

	protected int getActionType() {
		return CUT_TYPE;
	}

	public abstract TransactionManager getTransactionManager();
}
