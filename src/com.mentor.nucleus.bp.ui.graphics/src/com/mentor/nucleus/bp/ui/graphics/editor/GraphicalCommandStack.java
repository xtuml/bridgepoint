//========================================================================
//
//File:      $RCSfile: GraphicalCommandStack.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:06:03 $
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

package com.mentor.nucleus.bp.ui.graphics.editor;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.graphics.commands.GraphicalCloneCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.IExecutionValidationCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.IValidateDeltaCommand;

public class GraphicalCommandStack extends CommandStack {

	private Object fModelElement;

	@Override
	public void execute(Command command) {
		if (command instanceof CompoundCommand) {
			CompoundCommand cCommand = (CompoundCommand) command;
			if (cCommand.getCommands().get(0) instanceof GraphicalCloneCommand) {
				super.execute(command);
				return;
			}
		}
		if (!shouldCompleteExecution(command)) {
			return;
		}
		if(command instanceof GraphicalCloneCommand) {
			// we do not need to start a transaction here just execute
			super.execute(command);
			return;
		}
		Transaction transaction = Cl_c.Starttransaction(fModelElement, command
				.getLabel());
		boolean shapeCreationTransactionSuccess = false;
		try {
			if (command instanceof IExecutionValidationCommand) {
				shapeCreationTransactionSuccess = ((IExecutionValidationCommand) command)
						.executeWithValidation();
			} else {
				super.execute(command);
			}
			// refresh if transaction null
			if (transaction == null) {
				((ModelEditor) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor()).getGraphicalEditor().refresh();
			}
		} catch (Exception e) {
			Cl_c.Canceltransaction(fModelElement, transaction);
			CorePlugin.logError("An exception occured during transaction: "
					+ transaction.getDisplayName(), e);
			transaction = null;
		}
		if (transaction != null) {
			if ((command instanceof IExecutionValidationCommand)
					&& (!shapeCreationTransactionSuccess)) {
				Cl_c.Canceltransaction(fModelElement, transaction);
			} else {
				Cl_c.Endtransaction(fModelElement, transaction);
			}
		}
	}

	/**
	 * If the command (or commands nested) are of the type {@code
	 * IValidateDeltaCommand} then check if they should be executed.
	 * 
	 * @return whether or not any command contained should be fully executed
	 */
	private boolean shouldCompleteExecution(Command command) {
		if (command instanceof IValidateDeltaCommand) {
			return ((IValidateDeltaCommand) command).shouldExecute();
		}
		if (command instanceof CompoundCommand) {
			CompoundCommand cc = (CompoundCommand) command;
			for (Object child : cc.getCommands()) {
				return shouldCompleteExecution((Command) child);
			}
		}
		return true;
	}

	public void setModelElement(Object modelElement) {
		fModelElement = modelElement;
	}
}
