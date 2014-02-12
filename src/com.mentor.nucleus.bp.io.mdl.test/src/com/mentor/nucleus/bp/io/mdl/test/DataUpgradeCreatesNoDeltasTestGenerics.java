//=====================================================================
//
//File:      $RCSfile: DataUpgradeCreatesNoDeltasTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 05:13:51 $
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

package com.mentor.nucleus.bp.io.mdl.test;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class DataUpgradeCreatesNoDeltasTestGenerics extends BaseTest {
    static PersistenceManager manager = null;

	final String[] unaffectedDomNames = {"array_test",
			                             "asc",
			                             "br1",
			                             "br2",
			                             "br2f",
			                             "bridges",
			                             "dogs",
			                             "odms",
			                             "wim2",
			                             "wim3",
			                             "wims",
			                             "wimx"};
	
	public void testUpgradeCreatesNoDeltas() throws Exception {
		loadProject("CommunicationTestModel");
		for (int i = 0; i < unaffectedDomNames.length; i++) {
		  loadProject(unaffectedDomNames[i]);
		}
		Display display= Display.getCurrent();
		while (display.readAndDispatch());
		m_sys = getSystemModel("CommunicationTestModel");
		Package_c dom = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("CommunicationTestModel");
			}
		});
		assertNotNull("No test Domain found", dom);
		
		Package_c [] unaffectedDoms = new Package_c[unaffectedDomNames.length];
		for (int i = 0; i < unaffectedDomNames.length; i++) {
		  final String name = unaffectedDomNames[i];
		  m_sys = getSystemModel(name);
		  unaffectedDoms[i] = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(name);
			}
		  });
		  assertNotNull("No reference Domain found", unaffectedDoms[i]);
		}
		Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dom), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Subsystem");
			}
		});
		assertNotNull("No Subsystem found", ss);
		
		IWorkbenchPage page = TestUtil.showBridgePointPerspective();
		ExplorerView explorer = null;
		try {
			explorer = (ExplorerView)page.showView(
			     "com.mentor.nucleus.bp.ui.explorer.ExplorerView", null, //$NON-NLS-1$
			     IWorkbenchPage.VIEW_CREATE);
		} catch (PartInitException e) {
			fail(e.toString());
		}
		TreeViewer viewer = explorer.getTreeViewer();
        viewer.expandToLevel(2);
        UIUtil.refreshViewer(viewer); 
        while(display.readAndDispatch());
        // select the only domain node that should be present
        viewer.setSelection(new StructuredSelection(
                new Object[] {ss}), false);
        while(display.readAndDispatch()){}
        TransactionManager manager = getSystemModel().getTransactionManager();
        DeltaCheckingTransactionListener dctl = 
        	         new DeltaCheckingTransactionListener(
        			                        unaffectedDomNames, unaffectedDoms);
        manager.addTransactionListener(dctl);
		explorer.doDelete();
        while (manager.getActiveTransaction()!= null || display.readAndDispatch()) ;
	    assertTrue("Unexpected deltas found on domains, " +
                              dctl.getAffected(), dctl.getAffected().isEmpty());
	}

    class DeltaCheckingTransactionListener implements ITransactionListener {
        String [] unaffectedDomNamesf;
        Package_c [] unaffectedDomsf;
        String affected = "";
        public DeltaCheckingTransactionListener(String [] p_unaffectedDomNames,
                                                Package_c [] p_unaffectedDoms) {
        	unaffectedDomNamesf = p_unaffectedDomNames;
            unaffectedDomsf = p_unaffectedDoms;
        }
		@Override
		public void transactionCancelled(Transaction transaction) {
			// do nothing
		}

		@Override
		public void transactionEnded(Transaction transaction) {
		  IModelDelta[] deltas = transaction.getDeltas(
                                             Ooaofooa.getDefaultInstance());
		  if (deltas != null) {
		    for (int i = 0; i < deltas.length; i++) {
			  Object element = deltas[i].getModelElement(); 
			  if ( element instanceof NonRootModelElement) {
			    NonRootModelElement nrme = (NonRootModelElement)element;
			    for (int j = 0; j < unaffectedDomsf.length; j++) {
				  if (nrme.getModelRoot().getId().contains(unaffectedDomsf[j].getModelRoot().getId())) {
					if (!affected.contains(unaffectedDomsf[j].getName())) {
					  affected = affected + " " + unaffectedDomsf[j].getName();
					}
				  }
			    }
		      }
		    }
		  }
		}

		@Override
		public void transactionStarted(Transaction transaction) {
			// do nothing
		}
        public String getAffected() {
        	return affected;
        }
    }
}
