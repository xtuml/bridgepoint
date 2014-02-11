package com.mentor.nucleus.bp.core.ui;
//========================================================================
//
//File:      $RCSfile: BinaryFormalizeOnR_RELWizardPage1.java,v $
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

public class BinaryFormalizeOnR_RELWizardPage1 extends PtWizardPage
		implements
			Listener {
	public static final String copyright = "(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.";

	IWorkbench workbench;
	IStructuredSelection selection;

	// widgets on this page (public for unit tests)
	public Combo Non_formalizerCombo;
	public Label Non_formalizerLabel;
	public String Non_formalizerTextPhrase = "";
	public boolean Reflexsive = false;
	public Label Hint;
	public String Hint_message = "";

	// cache for user choices
	ClassAsSimpleParticipant_c Non_formalizer = null;
	ClassAsSimpleParticipant_c[] Non_formalizerInstances;

	/**
	 * Constructors for BinaryFormalizeOnR_RELWizardPage1.
	 */
	public BinaryFormalizeOnR_RELWizardPage1() {
		super("");
		init();
	}

	public BinaryFormalizeOnR_RELWizardPage1(String name) {
		super(name);
		init();
	}

	private void init() {
		setTitle("Formalize Association");
		setDescription("Select the participating class whose identifier will be used to formalize the association");
	}

	public void onPageEntry() {
		Ooaofooa modelRoot = Selection
				.getModelRoot((StructuredSelection) ((BinaryFormalizeOnR_RELWizard) getWizard())
						.getSelection());

		// cache for previous user selections
		ClassIdentifier_c v_Identifier = ((BinaryFormalizeOnR_RELWizard) getWizard()).v_Identifier;

		// cache for context
		Association_c v_rel = ((BinaryFormalizeOnR_RELWizard) getWizard()).v_rel;

		SimpleAssociation_c v_simp = SimpleAssociation_c
				.getOneR_SIMPOnR206(v_rel);

		Reflexsive = false;

		ClassAsSimpleParticipant_c[] v_non_formalizerInstances = ClassAsSimpleParticipant_c .getManyR_PARTsOnR207(v_simp);
		if (v_non_formalizerInstances.length == 2
				&& (v_non_formalizerInstances[0].getObj_id() .equals(v_non_formalizerInstances[1].getObj_id()))) {
			Reflexsive = true;
			setDescription("Select the class based on the text phrase at the end of the association line");
			if (v_non_formalizerInstances[0].getTxt_phrs().equalsIgnoreCase("")
					&& v_non_formalizerInstances[1].getTxt_phrs() .equalsIgnoreCase(""))
				Hint_message = "Hint:  Enter association text phrases to be able to differentiate between association ends.";
		}

		ModelClass_c v_non_formalizer = null;
		int non_formalizerInstCount;
		int non_formalizerResultCount = 0;
		for (non_formalizerInstCount = 0; non_formalizerInstCount < v_non_formalizerInstances.length; non_formalizerInstCount++) {
			ClassAsSimpleParticipant_c selected = v_non_formalizerInstances[non_formalizerInstCount];
			if (User_c.Selectone(selected.getObj_id())
					&& v_simp.Canparticipate(selected.getObj_id())) {
				non_formalizerResultCount++;
			}
		}
		this.Non_formalizerInstances = new ClassAsSimpleParticipant_c[non_formalizerResultCount];
		non_formalizerResultCount = 0;
		for (non_formalizerInstCount = 0; non_formalizerInstCount < v_non_formalizerInstances.length; non_formalizerInstCount++) {
			ClassAsSimpleParticipant_c selected = v_non_formalizerInstances[non_formalizerInstCount];
			if (User_c.Selectone(selected.getObj_id())
					&& v_simp.Canparticipate(selected.getObj_id())) {
				this.Non_formalizerInstances[non_formalizerResultCount] = selected;
				non_formalizerResultCount++;
			}
		}
		Non_formalizerCombo.removeAll();
		for (non_formalizerInstCount = 0; non_formalizerInstCount < non_formalizerResultCount; non_formalizerInstCount++) {
			ModelClass_c v_class = ModelClass_c .getOneO_OBJOnR201(ClassInAssociation_c.getOneR_OIROnR203(
					ReferredToClassInAssoc_c .getOneR_RTOOnR204(this.Non_formalizerInstances[non_formalizerInstCount])));
			if (Reflexsive)
				Non_formalizerCombo.add(v_class.getName() + "  '" + this.Non_formalizerInstances[non_formalizerInstCount].getTxt_phrs() + "'");
			else
				Non_formalizerCombo.add(v_class.getName());

			if (non_formalizerResultCount == 1) {
				Non_formalizerCombo.select(0);
				updateSelectedNon_formalizer();
			}
		}

	}
	public void createControl(Composite parent) {
		// create the composite to hold the widgets   
		GridData gd = null;
		Composite composite = new Composite(parent, SWT.NULL);

		// create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);

		Non_formalizerLabel = new Label(composite, SWT.NONE);
		Non_formalizerLabel.setText("Non_formalizer");
		Non_formalizerCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		Non_formalizerCombo .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// set the composite as the control for this page
		setControl(composite);
		onPageEntry(); // Initialize the ui widget contents
		if (Reflexsive) {
			Non_formalizerLabel.setText("Direction");
			if (!Hint_message.equalsIgnoreCase("")) {
				new Label(composite, SWT.None);
				new Label(composite, SWT.None);
				new Label(composite, SWT.None);
				new Label(composite, SWT.None);
				Hint = new Label(composite, SWT.NONE);
				Hint.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
				Hint.setText(Hint_message);
			}
		} else
			Non_formalizerLabel.setText("Class");

		addListeners();
	}

	private void addListeners() {
		Non_formalizerCombo.addListener(SWT.Selection, this);
	}

	// public for unit tests
	public void updateSelectedNon_formalizer() {
		this.Non_formalizer = Non_formalizerInstances[Non_formalizerCombo
				.getSelectionIndex()];
		((BinaryFormalizeOnR_RELWizard) getWizard()).v_non_formalizer = this.Non_formalizer;
	}

	/**
	 * @see Listener#handleEvent(Event)
	 */
	public void handleEvent(Event event) {
		if (event.widget == Non_formalizerCombo) {
			updateSelectedNon_formalizer();
		}
		getWizard().getContainer().updateButtons();
	}

	public boolean isPageComplete() {
		boolean isPageComplete = true;
		if (Non_formalizer == null)
			isPageComplete = false;
		return isPageComplete;
	}

	public IWizardPage getNextPage() {
		((BinaryFormalizeOnR_RELWizard) getWizard()).v_non_formalizer = Non_formalizer;
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
		if (Non_formalizer == null)
			return false;
		return true;
	}

}
