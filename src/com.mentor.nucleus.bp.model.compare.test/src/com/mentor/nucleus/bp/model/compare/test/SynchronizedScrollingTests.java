package com.mentor.nucleus.bp.model.compare.test;
//=====================================================================
//
//File:      $RCSfile: SynchronizedScrollingTests.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/03/14 17:13:07 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import java.io.File;
import java.io.IOException;

import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.team.internal.core.history.LocalFileRevision;
import org.eclipse.team.internal.ui.actions.CompareRevisionAction;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.history.HistoryPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CompareTestUtilities;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

@SuppressWarnings("restriction")
public class SynchronizedScrollingTests extends BaseTest {

	@Override
	public void initialSetup() throws CoreException, IOException {
		// load test model 
		WorkspaceUtil.setAutobuilding(false);

		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("HierarchyTestModel");

		File sourceProject = new File(m_workspace_path
				+ "../HierarchyTestModel");

		CorePlugin.disableParseAllOnResourceChange();

		TestingUtilities.copyProjectContents(sourceProject, project);

		TestingUtilities.allowJobCompletion();

		m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals(
						project.getName());
			}

		});
		PersistableModelComponent sys_comp = m_sys
				.getPersistableComponent();
		sys_comp.loadComponentAndChildren(new NullProgressMonitor());
		CorePlugin.enableParseAllOnResourceChange();
		TestingUtilities.allowJobCompletion();
		while (!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(
				IProject.DEPTH_INFINITE)) {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					IProject.DEPTH_INFINITE, new NullProgressMonitor());
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
		}

	}

	public void testSynchronizedScrollingDown() throws CoreException {

	}

	public void testSynchronizedScrollingUp() {
		
	}
}
