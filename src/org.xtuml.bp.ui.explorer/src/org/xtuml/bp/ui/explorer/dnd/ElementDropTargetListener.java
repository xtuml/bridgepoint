package org.xtuml.bp.ui.explorer.dnd;
//========================================================================
//
//File: ElementDropTargetListener.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

public interface ElementDropTargetListener extends DropTargetListener {
	
	public static String transferSeparator = "___";

	@Override
	default public void dragEnter(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dragLeave(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dragOperationChanged(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dragOver(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dropAccept(DropTargetEvent event) {
		// override if implementation desired
	}

}
