package com.mentor.nucleus.bp.core.ui;
//========================================================================
//
//File:      $RCSfile: BinaryFormalizeOnR_RELWizard.java,v $
//Version:   $Revision:$
//Modified:  $Date:$
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.wizard.*;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.*;
import com.mentor.nucleus.bp.core.util.TransactionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * BinaryFormalizeOnR_RELWizard class
 */
public class BinaryFormalizeOnR_RELWizard extends Wizard {
	public static final String copyright = "(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.";

	// workbench selection when the wizard was started
	protected IStructuredSelection m_selection;

	// the workbench instance
	protected IWorkbench m_workbench;

	// the viewer the wizard was started from. This must be replaced with a listener system
	protected Viewer m_viewer;

	// Pages stack
	protected Stack m_pagesStack = new Stack();

	// wizard pages
	BinaryFormalizeOnR_RELWizardPage1 R_REL_BinaryFormalizePage1;
	BinaryFormalizeOnR_RELWizardPage2 R_REL_BinaryFormalizePage2;

	// wizard state
	// constants
	protected static final int INITIAL_WIZARD_STATE = -1;
	protected static final int R_REL_BINARYFORMALIZE0_WIZARD_STATE = 0;
	protected static final int R_REL_BINARYFORMALIZE1_WIZARD_STATE = 1;
	// member
	protected int m_state = INITIAL_WIZARD_STATE;
	// end wizard state

	// cache for contextual selections (public for use by unit tests)
	public Association_c v_rel = null;

	// cache for the users selections (public for use by unit tests)
	public ClassAsSimpleParticipant_c v_non_formalizer = null;
	public ClassIdentifier_c v_Identifier = null;
	public String v_IdentifierPrefix = "";

	/**
	 * Constructor for BinaryFormalizeOnR_RELWizard.
	 */
	public BinaryFormalizeOnR_RELWizard() {
		super();
	}

	/**
	 * See field.
	 */
	public IStructuredSelection getSelection() {
		return m_selection;
	}

	public void addPages() {
		R_REL_BinaryFormalizePage1 = new BinaryFormalizeOnR_RELWizardPage1(
				"BinaryFormalizeOnR_RELWizardPage1");
		addPage(R_REL_BinaryFormalizePage1);
		R_REL_BinaryFormalizePage2 = new BinaryFormalizeOnR_RELWizardPage2(
				"BinaryFormalizeOnR_RELWizardPage2");
		addPage(R_REL_BinaryFormalizePage2);
	}

	/**
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection,
			Viewer viewer) {
		this.m_workbench = workbench;
		this.m_selection = selection;
		this.m_viewer = viewer;
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			Object context = iterator.next();
			if (context instanceof Association_c) {
				v_rel = (Association_c) context;
			}
		}
	}

	public boolean canFinish() {
		boolean pagesComplete = true;
		pagesComplete = pagesComplete
				&& R_REL_BinaryFormalizePage1.isPageComplete();
		pagesComplete = pagesComplete
				&& R_REL_BinaryFormalizePage2.isPageComplete();
		return pagesComplete;
	}

	public IWizardPage getNextPage(IWizardPage page) {
		List pages = new ArrayList(getPages().length);
		for (int i = 0; i < getPages().length; i++) {
			pages.add(getPages()[i]);
		}
		switch (pages.indexOf(page)) {
			case R_REL_BINARYFORMALIZE0_WIZARD_STATE :
				m_state = pages.indexOf(R_REL_BinaryFormalizePage2);
				R_REL_BinaryFormalizePage2.onPageEntry();
				return R_REL_BinaryFormalizePage2;

			case R_REL_BINARYFORMALIZE1_WIZARD_STATE :
				return null;
		}
		return null;
	}

	public IWizardPage getPreviousPage(IWizardPage page) {
		if (m_pagesStack.empty()) {
			return null;
		} else {
			List pages = new ArrayList(getPages().length);
			for (int i = 0; i < getPages().length; i++) {
				pages.add(getPages()[i]);
			}
			m_state = pages.indexOf((IWizardPage) m_pagesStack.peek());
			((PtWizardPage) m_pagesStack.peek()).onPageEntry();
			return (IWizardPage) m_pagesStack.pop();
		}
	}

	private void setPrefixs(SimpleAssociation_c vARel) {
		if (v_IdentifierPrefix  == "" ){
			return;
		}
		ReferringClassInAssoc_c other = ReferringClassInAssoc_c.getOneR_RGOOnR205(ClassAsSimpleFormalizer_c.getOneR_FORMOnR208(vARel));
		AttributeReferenceInClass_c[] ids = AttributeReferenceInClass_c.getManyO_REFsOnR111(other); 
		for (AttributeReferenceInClass_c id : ids) {
			Attribute_c attr = Attribute_c.getOneO_ATTROnR106(ReferentialAttribute_c.getOneO_RATTROnR108(id));
			if (!v_IdentifierPrefix.equalsIgnoreCase("")){
				attr.setPrefix(v_IdentifierPrefix);
				attr.setPfx_mode(1);
			}
		}
	}
	public boolean performFinish() {
		TransactionUtil.TransactionGroup transactionGroup = null;
		ModelRoot modelRoot = v_rel.getModelRoot();
		try {
			transactionGroup = TransactionUtil
					.startTransactionsOnSelectedModelRoots("Binary Formalize"); //$NON-NLS-1$
			SimpleAssociation_c v_simp = SimpleAssociation_c
					.getOneR_SIMPOnR206(v_rel);

			if (((v_non_formalizer != null))) {

				if (((v_Identifier != null))) {

					if (v_simp != null) {
						v_simp.Formalize(v_Identifier.getOid_id(),
								v_non_formalizer.getOir_id());
					} else {
						Throwable t = new Throwable();
						t.fillInStackTrace();
						CorePlugin
								.logError(
										"Attempted to call an operation on a null instance.",
										t);
					}

				}

			}

			setPrefixs(v_simp);
			// catch all exceptions and cancel the transaction
		} catch (Exception e) {
			if (transactionGroup != null)
				TransactionUtil.cancelTransactions(transactionGroup, e);
			CorePlugin.logError("Transaction: Binary Formalize failed", e);//$NON-NLS-1$
			// return true so that the wizard will
			// close
			return true;
		}
		if (transactionGroup != null)
			TransactionUtil.endTransactions(transactionGroup);
		if (m_viewer != null) {
			if (m_viewer instanceof StructuredViewer) {
				((StructuredViewer) m_viewer).refresh(v_rel);
			} else {
				m_viewer.refresh();
			}
		}
		return true;
	}
}
