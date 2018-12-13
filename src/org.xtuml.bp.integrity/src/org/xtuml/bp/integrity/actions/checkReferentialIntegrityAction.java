/********************************************************************************
 * Copyright (c) 2017 One Fact Inc
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache Software License
 * 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-1.0 OR Apache-2.0
 ********************************************************************************/

package org.xtuml.bp.integrity.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.xtuml.bp.integrity.generator.Generator;

public class checkReferentialIntegrityAction implements IObjectActionDelegate {

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	@Override
	public void run(IAction action) {
	  Generator.genAll();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
