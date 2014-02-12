//========================================================================
//
//File:      $RCSfile: GraphicalCloneCommand.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:45 $
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
package com.mentor.nucleus.bp.ui.graphics.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;

public class GraphicalCloneCommand extends Command {

	private GraphicalEditor fEditor;
    private ChangeBoundsRequest fRequest;
	
    public GraphicalCloneCommand(GraphicalEditor editor, ChangeBoundsRequest request) {
    	fEditor = editor;
    	fRequest = request;
    }
	
	@Override
	public void execute() {
		fEditor.getCopyAction().run();
		// unscale the move delta from the request
		Point moveDelta = fRequest.getMoveDelta();
		double zoom = ((GraphicalZoomManager) fEditor
				.getAdapter(ZoomManager.class)).getZoom();
		moveDelta.scale(1 / zoom);
		fEditor.getPasteAction().setRequest(fRequest);
		fEditor.getPasteAction().run();
	}

}
