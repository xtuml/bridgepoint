//========================================================================
//
// File:      $RCSfile: TabbedPreferenceContainer.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package com.mentor.nucleus.bp.ui.preference;

import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author babar_ali
 */
public abstract class TabbedPreferenceContainer extends PreferencePage implements IPreferencePageContainer, IWorkbenchPreferencePage {

	TabFolder tabFolder;
	PreferencePageTabItem selectedTab;
	IWorkbench workbench;

	public void init(IWorkbench aWorkbench) {
		workbench = aWorkbench;
	}

	// Delegating to actual PreferencePageContainer -----  START
	public void updateButtons() {
		getContainer().updateButtons();
	}

	public void updateMessage() {
		getContainer().updateMessage();
	}

	public void updateTitle() {
		getContainer().updateTitle();
	}
	//  Delegating to actual PreferencePageContainer -----  END

	//  Delegating to actual SubPage -----  START
	public boolean isValid() {
		return selectedTab.getPreferencePage().isValid();
	}

	public boolean okToLeave() {
		return selectedTab.getPreferencePage().okToLeave();
	}

	public boolean performCancel() {
		return selectedTab.getPreferencePage().performCancel();
	}

	public boolean performOk() {
		return selectedTab.getPreferencePage().performOk();
	}
	
	protected void performDefaults() {
		IPreferencePage page = selectedTab.getPreferencePage();
		if(page instanceof BaseModelEditor){
			((BaseModelEditor)page).performDefaults();	
		}
	}
	
	//  Delegating to actual SubPage -----  END

	protected void addPage(IPreferencePage page) {
		if(workbench == null)
			throw new IllegalStateException("local variable workbench has not been initialized; init(IWorkbench aWorkbench) has been overridden");
		
		if(page instanceof IWorkbenchPreferencePage){
			((IWorkbenchPreferencePage)page).init(workbench);
		}
		
		PreferencePageTabItem tabItem = new PreferencePageTabItem(tabFolder, SWT.NULL, page);

		Composite tabFolderPage = new Composite(tabFolder, SWT.NULL);
		tabFolderPage.setLayout(new FillLayout());
		
		page.createControl(tabFolderPage);
		page.setContainer(this);
		tabItem.setControl(tabFolderPage);
	}

	protected abstract IPreferencePage[] createSubPages();

	protected abstract boolean willCreateDefaultAndApplyButton();

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		if (!willCreateDefaultAndApplyButton())
			noDefaultAndApplyButton();

		tabFolder = new TabFolder(parent, SWT.NULL);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Widget selectedWidget = e.item;
				if (selectedWidget instanceof PreferencePageTabItem) {
					selectedTab = (PreferencePageTabItem) selectedWidget;
				}
			}
		});

		IPreferencePage[] subPages = createSubPages();
		if (subPages.length < 1)
			throw new IllegalStateException("createSubPages() implementation should create atleast one sub page");

		for (int i = 0; i < subPages.length; i++) {
			addPage(subPages[i]);
		}

		tabFolder.setSelection(0);

		selectedTab = (PreferencePageTabItem) tabFolder.getItem(tabFolder.getSelectionIndex());

		return tabFolder;
	}

	class PreferencePageTabItem extends TabItem {

		IPreferencePage page;

		PreferencePageTabItem(TabFolder parent, int style, IPreferencePage aPage) {
			super(parent, style);
			page = aPage;

			setText(page.getTitle());
			Image image = page.getImage();
			if (image != null) {
				setImage(image);
			}

			String description = page.getDescription();
			if (description != null && description.length() > 0)
				setToolTipText(description);

		}

		public IPreferencePage getPreferencePage() {
			return page;
		}

		protected void checkSubclass() {
		}
	}
}