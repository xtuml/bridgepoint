package com.mentor.nucleus.bp.core.ui;
//========================================================================
//
//File:      $RCSfile: BinaryFormalizeOnR_RELWizardPage1.java,v $
//Version:   $Revision:$
//Modified:  $Date:$
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
	public static final String copyright = "(c) Copyright 2003-2013 Mentor Graphics Corporation  All rights reserved.";

	IWorkbench workbench;
	IStructuredSelection selection;

	// widgets on this page (public for unit tests)
	public Combo Non_formalizerCombo;
	public Label Non_formalizerLabel;

	// cache for user choices
	ModelClass_c Non_formalizer = null;
	ModelClass_c[] Non_formalizerInstances;

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
		setTitle("Formalize");
		setDescription("Select the class whose identifier will be used to formalize the association");
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

		// select related by where USER::selectOne
		ModelClass_c[] v_non_formalizerInstances = ModelClass_c
				.getManyO_OBJsOnR201(ClassInAssociation_c
						.getManyR_OIRsOnR203(ReferredToClassInAssoc_c
								.getManyR_RTOsOnR204(ClassAsSimpleParticipant_c
										.getManyR_PARTsOnR207(v_simp))));
		ModelClass_c v_non_formalizer = null;
		int non_formalizerInstCount;
		int non_formalizerResultCount = 0;
		for (non_formalizerInstCount = 0; non_formalizerInstCount < v_non_formalizerInstances.length; non_formalizerInstCount++) {
			ModelClass_c selected = v_non_formalizerInstances[non_formalizerInstCount];
			if (User_c.Selectone(selected.getObj_id())
					&& v_simp.Canparticipate(selected.getObj_id())) {
				non_formalizerResultCount++;
			}
		}
		this.Non_formalizerInstances = new ModelClass_c[non_formalizerResultCount];
		non_formalizerResultCount = 0;
		for (non_formalizerInstCount = 0; non_formalizerInstCount < v_non_formalizerInstances.length; non_formalizerInstCount++) {
			ModelClass_c selected = v_non_formalizerInstances[non_formalizerInstCount];
			if (User_c.Selectone(selected.getObj_id())
					&& v_simp.Canparticipate(selected.getObj_id())) {
				this.Non_formalizerInstances[non_formalizerResultCount] = selected;
				non_formalizerResultCount++;
			}
		}
		Non_formalizerCombo.removeAll();
		for (non_formalizerInstCount = 0; non_formalizerInstCount < non_formalizerResultCount; non_formalizerInstCount++)
			Non_formalizerCombo
					.add(((ModelClass_c) this.Non_formalizerInstances[non_formalizerInstCount])
							.getName());
		if (non_formalizerResultCount == 1) {
			Non_formalizerCombo.select(0);
			updateSelectedNon_formalizer();
		}

		if (((v_non_formalizer != null))) {

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

		Non_formalizerLabel = new Label(composite, SWT.NONE);
		Non_formalizerLabel.setText("Non_formalizer");
		Non_formalizerCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		Non_formalizerCombo
				.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// set the composite as the control for this page
		setControl(composite);
		onPageEntry(); // Initialize the ui widget contents
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
