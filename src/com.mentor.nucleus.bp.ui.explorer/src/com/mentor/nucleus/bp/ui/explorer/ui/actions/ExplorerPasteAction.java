//========================================================================
//
//File:      $RCSfile: ExplorerPasteAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:15:51 $
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
package com.mentor.nucleus.bp.ui.explorer.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.PasteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;

public class ExplorerPasteAction extends PasteAction {

	@Override
	public List<NonRootModelElement> getDestinations() {
		List<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		NonRootModelElement[] selected = Selection.getInstance()
				.getSelectedNonRootModelElements();
		for (int i = 0; i < selected.length; i++) {
			list.add(selected[i]);
		}
		return list;
	}

	@Override
	public TransactionManager getTransactionManager() {
		return TransactionManager.getSingleton();
	}

	@Override
	public void runSubtypeProcessing(NonRootModelElement destination) {
		// move graphical data if present
		Class<?> canvasPasteActionClass = OoaofgraphicsUtil
				.getCanvasPasteActionClass();
		try {
			Method method = canvasPasteActionClass.getMethod(
					"handleNonDiagramElementAsDestination", new Class<?>[] {
							NonRootModelElement.class, HashMap.class });
			method.invoke(null,
					new Object[] { destination, processorMap });
		} catch (SecurityException e) {
			CorePlugin.logError("Unable to move graphical data on paste", e);
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Unable to move graphical data on paste", e);
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to move graphical data on paste", e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to move graphical data on paste", e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to move graphical data on paste", e);
		}
	}

	private boolean clipboardCanBePastedIntoSelection() {
		Clipboard cb = CorePlugin.getSystemClipboard();
		if(cb == null || cb.isDisposed()) return false;
		boolean result = true;
		Object contents = cb
				.getContents(TextTransfer.getInstance());
		for(NonRootModelElement destination : getDestinations()) {
			if (contents instanceof String) {
				String types[] = getClipboardTypes((String) contents, destination);
				for (int i = 0; i < types.length; i++) {
					result = CoreUtil.supportsPaste(destination, types[i], true);
					if(!result)
						break;
				}
				if(types.length == 0)
					result = false;
				if(!result) {
					return false;
				}
			} else {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean isEnabled() {
		if(!super.isEnabled()) {
			return false;
		}
		return clipboardCanBePastedIntoSelection();
	}
	
}
