/** 
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtuml.bp.xtext.masl.ui.validation

import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.jface.dialogs.IDialogSettings
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Composite
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock
import org.eclipse.xtext.ui.validation.AbstractValidatorConfigurationBlock
import org.eclipse.xtext.validation.SeverityConverter

import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*

class MaslValidationConfigurationBlock extends AbstractValidatorConfigurationBlock {
	
	public static val SETTINGS_SECTION_NAME = "MaslValidatorConfigurationBlock"

	override protected void fillSettingsPage(Composite composite, int nColumns, int defaultIndent) { 
		createSection('Syntax', composite, nColumns).fillSyntaxPage(defaultIndent)
		createSection('Structure', composite, nColumns).fillStructurePage(defaultIndent)
		createSection('Types', composite, nColumns).fillTypesPage(defaultIndent)
	}

	def protected fillSyntaxPage(Composite composite, int defaultIndent) {
		addComboBox(ILLEGAL_EMPTY_LIST, 'Illegal empty lists', composite, defaultIndent)
		addComboBox(INCONSISTENT_RELATIONSHIP_ENDS, 'Inconsistent relationship ends', composite, defaultIndent) 
		addComboBox(INCONSISTENT_RELATIONSHIP_NAVIGATION, 'Inconsistent relationship navigation', composite, defaultIndent) 
		addComboBox(DUPLICATE_NAME, 'Duplicate names', composite, defaultIndent) 
		addComboBox(NAMING_CONVENTION, 'Bad names', composite, defaultIndent) 
	}

	def protected fillStructurePage(Composite composite, int defaultIndent) {
		addComboBox(MISSING_DEFINITION, 'Missing definitions', composite, defaultIndent) 
		addComboBox(MISSING_DECLARATION, 'Missing declarations', composite, defaultIndent) 
		addComboBox(MISSING_IMPLEMENTATION, 'Missing implementation', composite, defaultIndent) 
		addComboBox(WRONG_STRUCTURE, 'Wrong file contents', composite, defaultIndent) 
		addComboBox(INVALID_THIS, "Invalid use of 'this' literal", composite, defaultIndent) 
		addComboBox(UNREACHABLE_CODE, "Unreachable code", composite, defaultIndent) 
	}
	
	def protected fillTypesPage(Composite composite, int defaultIndent) {
		addComboBox(DECLARATION_MISSMATCH, 'Declaration type mismatch', composite, defaultIndent)
		addComboBox(CYCLIC_INHERITANCE, 'Cyclic inheritance', composite, defaultIndent)
		addComboBox(WRONG_TYPE, 'Incompatible types', composite, defaultIndent)
		addComboBox(FUNCTION_CALLED_AS_SERVICE, 'Function called as service', composite, defaultIndent)
		addComboBox(INVALID_FEATURE_CALL, 'Invalid feature calls', composite, defaultIndent)
		addComboBox(INVISIBLE_FEATURE, 'Visibility problems', composite, defaultIndent)
		addComboBox(INVALID_ACTION_CALL, 'Invalid action calls', composite, defaultIndent)
		addComboBox(INVALID_LINK_EXPRESSION, 'Invalid relationship in link expression', composite, defaultIndent)
		addComboBox(INVALID_INDEXED_EXPRESSION, 'Invalid indexed expression', composite, defaultIndent)
	}

	protected def Combo addComboBox(String prefKey, String label, Composite parent, int indent) {
	  val values = #[ 
		  	SeverityConverter.SEVERITY_ERROR, 
		  	SeverityConverter.SEVERITY_WARNING,
		 	SeverityConverter.SEVERITY_IGNORE ]
	 	val valueLabels = #[ 'Error', 'Warning', 'Ignore']
	 	val comboBox = addComboBox(parent, label, prefKey, indent, values, valueLabels)
	 	return comboBox
	 }
	
	override protected Job getBuildJob(IProject project) {
		var buildJob = new OptionsConfigurationBlock.BuildJob("Rebuilding", project)
		buildJob.rule = ResourcesPlugin.workspace.ruleFactory.buildRule
		buildJob.user = true
		return buildJob
	}

	override protected String[] getFullBuildDialogStrings(
		boolean workspaceSettings) {
		val message =
		if (workspaceSettings) {
			"The MASL validation settings have changed. A full rebuild is required for changes to take effect. Do the full build now?"
		} else {
			"The MASL validation settings have changed. A rebuild of the project is required for changes to take effect. Build the project now?"
		}
		return #["Validation Settings Changed", message]
	}

	override protected void validateSettings(String changedKey, String oldValue, String newValue) {
	}

	override void dispose() {
		storeSectionExpansionStates(dialogSettings)
		super.dispose()
	}

	override protected IDialogSettings getDialogSettings() {
		var dialogSettings = super.dialogSettings
		var section = dialogSettings.getSection(SETTINGS_SECTION_NAME)
		if (section === null) 
			return dialogSettings.addNewSection(SETTINGS_SECTION_NAME)
		return section
	}
}
