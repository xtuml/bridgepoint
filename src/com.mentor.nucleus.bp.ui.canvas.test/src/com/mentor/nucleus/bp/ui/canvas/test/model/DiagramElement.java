//========================================================================
//
//File:      $RCSfile: DiagramElement.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:43:53 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.ui.canvas.test.model;

import java.util.UUID;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class DiagramElement {

	private static DiagramElement defaultInstance;
	UUID id = UUID.randomUUID();

	public UUID Get_ooa_id() {
		return id;
	}

	public void createShape() {
		ShapeElement newElement = new ShapeElement();
		// no listener or events, so we must find the latest graphical element
		// and set the represents
		Model_c model = Model_c.ModelInstance(Ooaofgraphics.getDefaultInstance(), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Model_c) candidate).getRepresents() == getDefaultInstance();
			}
		});
		GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(model);
		elements[elements.length - 1].setRepresents(newElement);
	}

	public boolean createConnector(UUID from, boolean importedFrom, UUID to,
			boolean importedTo) {
		ConnectorElement newElement = new ConnectorElement();
		Model_c model = Model_c.ModelInstance(Ooaofgraphics.getDefaultInstance(), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Model_c) candidate).getRepresents() == getDefaultInstance();
			}
		});
		GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(model);
		elements[elements.length - 1].setRepresents(newElement);
		return true;
	}

	public static DiagramElement getDefaultInstance() {
		if(defaultInstance == null) {
			defaultInstance = new DiagramElement();
		}
		return defaultInstance;
	}

}
