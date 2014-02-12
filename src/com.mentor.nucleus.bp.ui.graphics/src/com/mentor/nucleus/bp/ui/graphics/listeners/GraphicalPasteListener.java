//========================================================================
//
//File:      $RCSfile: GraphicalPasteListener.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:06:05 $
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
package com.mentor.nucleus.bp.ui.graphics.listeners;

import java.util.List;

import com.mentor.nucleus.bp.core.common.IPasteListener;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class GraphicalPasteListener implements IPasteListener {

	@Override
	public void pasteCompleted(NonRootModelElement destination, List<NonRootModelElement> loadedInstances) {
		// for all loaded graphical instances that are
		// still on the clipboard root, move them to the
		// destination root
		for(NonRootModelElement element : loadedInstances) {
			if (element.getModelRoot() instanceof Ooaofgraphics
					&& element.getModelRoot().getId().equals(
							Ooaofgraphics.CLIPBOARD_MODEL_ROOT_NAME)) {
				InstanceList list = element.getModelRoot().getInstanceList(
						element.getClass());
				synchronized (list) {
					list.remove(element);
				}
				InstanceList parentList = Ooaofgraphics.getInstance(
						destination.getModelRoot().getId()).getInstanceList(
						element.getClass());
				synchronized (list) {
					parentList.add(element);
				}
				parentList.put(element.getInstanceKey(), element);
				element.setModelRoot(Ooaofgraphics.getInstance(destination
						.getModelRoot().getId()));
			}
		}
	}

}
