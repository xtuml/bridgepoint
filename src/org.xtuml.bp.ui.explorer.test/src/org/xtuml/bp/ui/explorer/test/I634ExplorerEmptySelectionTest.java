//=====================================================================
//
//File:      $RCSfile: I634ExplorerEmptySelectionTest.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:19:40 $
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

package org.xtuml.bp.ui.explorer.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.OrderedRunner;

@RunWith(OrderedRunner.class)
	public class I634ExplorerEmptySelectionTest extends BaseTest{
	
	static IWorkbenchPage m_wp = null;

//	public I634ExplorerEmptySelectionTest(String name) {
//		super("org.xtuml.bp.ui.explorer.test", name);		//$NON-NLS-1$
//	}
	public I634ExplorerEmptySelectionTest() {
		super("org.xtuml.bp.ui.explorer.test", null);		//$NON-NLS-1$
	}

	@Before
	public void setUp() throws Exception {
		m_wp= switchPerspective("org.xtuml.bp.core.perspective"); //$NON-NLS-1$
		ExplorerUtil.showModelExplorer();
        super.setUp();
	}
	
	@Test
	public void testOpenOnEmptyExplorer() throws CoreException{
		sendEventAndCheckLog(ExplorerUtil.getTreeViewer().getTree(), SWT.DefaultSelection, new Event());	    	
	}
	
	@Test
	public void testOpenOnLoadedModelInExplorer() throws Throwable{
        ExplorerTest.restoreProject();
        ensureAvailableAndLoaded("small", false);

        IStructuredSelection sel =(IStructuredSelection)Selection.getInstance().getSelection(); 
		if (!sel.isEmpty()){
			Selection.getInstance().removeFromSelection(sel.iterator().next());	
		}
		assertTrue((Selection.getInstance().getSelection()).isEmpty());
		sendEventAndCheckLog(ExplorerUtil.getTreeViewer().getTree(), SWT.DefaultSelection, new Event());
	}

}
