package com.mentor.nucleus.bp.core.ui;
//========================================================================
//
//File:      $RCSfile: BinaryFormalizeOnR_RELWizardPage2.java,v $
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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

import java.util.ArrayList;
import java.util.List;

public class BinaryFormalizeOnR_RELWizardPage2 extends PtWizardPage
		implements
			Listener {
	public static final String copyright = "(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.";

	IWorkbench workbench;
	IStructuredSelection selection;

	// widgets on this page (public for unit tests)
	public Combo IdentifierCombo;
	public Label IdentifierLabel;
	public Text IdentifierPrefix;
	public Label IdentifierPrefixLabel;
	public Label Note;

	// cache for user choices
	ClassIdentifier_c Identifier = null;
	ClassIdentifier_c[] IdentifierInstances;

	/**
	 * Constructors for BinaryFormalizeOnR_RELWizardPage2.
	 */
	public BinaryFormalizeOnR_RELWizardPage2() {
		super("");
		init();
	}

	public BinaryFormalizeOnR_RELWizardPage2(String name) {
		super(name);
		init();
	}

	private void init() {
		setTitle("Formalize Referential");
		setDescription("Select the identifier which will be used to formalize the association.\n" + 
		"Adding a prefix to the referential identifier is optional");
	}

	public void onPageEntry() {
		Ooaofooa modelRoot = Selection
				.getModelRoot((StructuredSelection) ((BinaryFormalizeOnR_RELWizard) getWizard())
						.getSelection());

		// cache for previous user selections
		ClassAsSimpleParticipant_c v_part = ((BinaryFormalizeOnR_RELWizard) getWizard()).v_non_formalizer;
		ModelClass_c v_non_formalizer = ModelClass_c .getOneO_OBJOnR201(ClassInAssociation_c .getOneR_OIROnR203(ReferredToClassInAssoc_c .getOneR_RTOOnR204(v_part)));

		// cache for context
		Association_c v_rel = ((BinaryFormalizeOnR_RELWizard) getWizard()).v_rel;

		SimpleAssociation_c v_simp = SimpleAssociation_c
				.getOneR_SIMPOnR206(v_rel);

		if (((v_non_formalizer != null))) {

			// select related by where USER::selectOne
			ClassIdentifier_c[] v_IdentifierInstances = ClassIdentifier_c
					.getManyO_IDsOnR104(v_non_formalizer);
			ClassIdentifier_c v_Identifier = null;
			int IdentifierInstCount;
			int IdentifierResultCount = 0;
			for (IdentifierInstCount = 0; IdentifierInstCount < v_IdentifierInstances.length; IdentifierInstCount++) {
				ClassIdentifier_c selected = v_IdentifierInstances[IdentifierInstCount];
				if (User_c.Selectone(selected.getObj_id())
						&& ClassIdentifier_c.Hasattributes(modelRoot,
								selected.getObj_id(), selected.getOid_id())) {
					IdentifierResultCount++;
				}
			}
			this.IdentifierInstances = new ClassIdentifier_c[IdentifierResultCount];
			IdentifierResultCount = 0;
			for (IdentifierInstCount = 0; IdentifierInstCount < v_IdentifierInstances.length; IdentifierInstCount++) {
				ClassIdentifier_c selected = v_IdentifierInstances[IdentifierInstCount];
				if (User_c.Selectone(selected.getObj_id())
						&& ClassIdentifier_c.Hasattributes(modelRoot,
								selected.getObj_id(), selected.getOid_id())) {
					this.IdentifierInstances[IdentifierResultCount] = selected;
					IdentifierResultCount++;
				}
			}
			IdentifierCombo.removeAll();
			for (IdentifierInstCount = 0; IdentifierInstCount < IdentifierResultCount; IdentifierInstCount++)
				IdentifierCombo
						.add(((ClassIdentifier_c) this.IdentifierInstances[IdentifierInstCount])
								.Get_name());
			if (IdentifierResultCount == 1) {
				IdentifierCombo.select(0);
				updateSelectedIdentifier();
			}

			if (((v_Identifier != null))) {

			}

		}

	}

	public void createControl(Composite parent) {
		// create the composite to hold the widgets   
		GridData gd = null;
		Composite composite = new Composite(parent, SWT.NULL);

		// create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 4;
		gl.numColumns = ncol;
		composite.setLayout(gl);

		IdentifierPrefixLabel = new Label(composite, SWT.NONE);
		IdentifierPrefixLabel.setText("Prefix");
		IdentifierPrefix = new Text(composite, SWT.BORDER);
		IdentifierPrefix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		IdentifierPrefix.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				((BinaryFormalizeOnR_RELWizard) getWizard()).v_IdentifierPrefix = BinaryFormalizeOnR_RELWizardPage2.this.IdentifierPrefix
						.getText();
			}
		});
		IdentifierLabel = new Label(composite, SWT.NONE);
		IdentifierLabel.setText("goes with identifier");
		IdentifierCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		IdentifierCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(composite, SWT.None);
		new Label(composite, SWT.None);
		new Label(composite, SWT.None);
		new Label(composite, SWT.None);
		Note = new Label(composite, SWT.None);
		Note.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		Note.setText("Note:  The optional prefix text will be added to the beginning of the referential attribute name." +
				"\nThe prefix is essential in a reflexive association to distinguish the referential from the identifying attribute.");

		// set the composite as the control for this page
		setControl(composite);
		onPageEntry(); // Initialize the ui widget contents
		addListeners();
	}

	private void addListeners() {
		IdentifierCombo.addListener(SWT.Selection, this);
	}

	// public for unit tests
	public void updateSelectedIdentifier() {
		this.Identifier = IdentifierInstances[IdentifierCombo
				.getSelectionIndex()];
		((BinaryFormalizeOnR_RELWizard) getWizard()).v_Identifier = this.Identifier;
	}

	/**
	 * @see Listener#handleEvent(Event)
	 */
	public void handleEvent(Event event) {
		if (event.widget == IdentifierCombo) {
			updateSelectedIdentifier();
		}
		getWizard().getContainer().updateButtons();
	}

	public boolean isPageComplete() {
		boolean isPageComplete = true;
		if (Identifier == null)
			isPageComplete = false;
		return isPageComplete;
	}

	public IWizardPage getNextPage() {
		((BinaryFormalizeOnR_RELWizard) getWizard()).v_Identifier = Identifier;
		PtWizardPage page = (PtWizardPage) getWizard().getNextPage(this);
		page.onPageEntry();
		return page;
	}

	/**
	 * @see IWizardPage#canFlipToNextPage()
	 */
	public boolean canFlipToNextPage() {
		if ((PtWizardPage) getWizard().getNextPage(this) == null)
			return false;
		if (Identifier == null)
			return false;
		return true;
	}

}
