//========================================================================
//
//File:      $RCSfile: DiagramElement.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:43:53 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
