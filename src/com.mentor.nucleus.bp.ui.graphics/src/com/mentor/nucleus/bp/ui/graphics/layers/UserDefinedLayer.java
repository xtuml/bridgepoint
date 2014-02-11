//========================================================================
//
//File:      $RCSfile: UserDefinedLayer.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:01 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.layers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphicalelementinlayer_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class UserDefinedLayer extends DefaultLayer {

	private static HashMap<Layer_c, HashMap<DiagramEditPart, UserDefinedLayer>> layers = new HashMap<Layer_c, HashMap<DiagramEditPart, UserDefinedLayer>>();
	private Layer_c model;
	private UserDefinedConnectionLayer connectionLayer;
	private boolean editing = false;
	static final int FLAG_REALIZED = 1 << 31;

	public UserDefinedLayer(Layer_c model,
			DiagramEditPart diagramEditPart) {
		super(diagramEditPart);
		this.model = model;
		HashMap<DiagramEditPart, UserDefinedLayer> innerMap = layers.get(model);
		if (innerMap == null) {
			innerMap = new HashMap<DiagramEditPart, UserDefinedLayer>();
			layers.put(model, innerMap);
		}
		innerMap.put(diagramEditPart, this);
		setFocusTraversable(true);
	}

	public String getName() {
		return model.getLayer_name();
	}

	public UserDefinedConnectionLayer getConnectionLayer() {
		return connectionLayer;
	}

	public Layer_c getModel() {
		return model;
	}

	public boolean getEditing() {
		return !getModel().getVisible() && getEditingValue();
	}

	public boolean getEditingValue() {
		UserDefinedLayer inheritsFrom = getInheritsFrom();
		if(inheritsFrom != null) {
			return inheritsFrom.getEditingValue();
		} else {
			return editing;
		}
	}
	
	/**
	 * The inherits from is determined by finding the UDL for the
	 * Model_c containing the Layer_c instances.
	 * 
	 * @return Returns UDL for diagram containing the Layer_c
	 */
	private UserDefinedLayer getInheritsFrom() {
		
		UserDefinedLayer layer = getLayer(getModel(), diagramPart);
		if(layer != this) {
			return layer;
		} else {
			return null;
		}
	} 

	@Override
	protected void paintChildren(Graphics graphics) {
		if (getEditing()) {
			// configure the graphics to use an alpha value
			graphics.setAdvanced(true);
			graphics.setAlpha(125);
			graphics.pushState();
		}
		super.paintChildren(graphics);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		if (getModel() == null) {
			// return false for now, until the layer is completely
			// configured
			return false;
		}
		// override to use the stored value in the model
		return getEditingValue() || getModel().getVisible();
	}

	public static UserDefinedLayer getLayer(Layer_c layer, DiagramEditPart diagramPart) {
		HashMap<DiagramEditPart, UserDefinedLayer> innerMap = layers.get(layer);
		if(innerMap == null) {
			return null;
		}
		return innerMap.get(diagramPart);
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public static List<UserDefinedLayer> getLayers(DiagramEditPart diagramPart) {
		List<UserDefinedLayer> list = new ArrayList<UserDefinedLayer>();
		Set<Entry<Layer_c, HashMap<DiagramEditPart, UserDefinedLayer>>> entrySet = layers
				.entrySet();
		for (Entry<Layer_c, HashMap<DiagramEditPart, UserDefinedLayer>> entry : entrySet) {
			HashMap<DiagramEditPart, UserDefinedLayer> value = entry.getValue();
			Set<Entry<DiagramEditPart, UserDefinedLayer>> innerSet = value.entrySet();
			for (Entry<DiagramEditPart, UserDefinedLayer> innerEntry : innerSet) {
				DiagramEditPart testDiagram = innerEntry.getKey();
				if (testDiagram == diagramPart) {
					list.add(innerEntry.getValue());
				}
				if (testDiagram == null) {
					// compare ids
					if (entry.getKey().getDiagramidCachedValue().equals(
							((Model_c) diagramPart.getModel()).getDiagramid())) {
						list.add(innerEntry.getValue());
					}
				}
			}
		}
		return list;
	}

	public void removeLayerFromModelMap(DiagramEditPart diagramPart) {
		HashMap<DiagramEditPart, UserDefinedLayer> innerMap = layers.get(getModel());
		innerMap.remove(diagramPart);
		if(innerMap.isEmpty()) {
			layers.remove(getModel());
		}
	}

	@Override
	public void add(IFigure child, Object constraint, int index) {
		// disable realized flag, as GEF does not expect a
		// figure to be added to two parents (and fails if this flag
		// is true)
		setFlag(FLAG_REALIZED, false);
		super.add(child, constraint, index);
	}

	@Override
	public void remove(IFigure child) {
		// do not perform this, as it comes from GEF and GEF
		// does not allow multiple parent figures, the removeInternal()
		// method below is the one to use if removal is desired
		return;
	}

	public void removeInternal(IFigure child) {
		// child should be a ShapeImageFigure
		if(child instanceof ShapeImageFigure) {
			((ShapeImageFigure) child).setTemporaryParent(this);
		}
		super.remove(child);
		if(child instanceof ShapeImageFigure) {
			((ShapeImageFigure) child).setTemporaryParent(null);
		}
	}

	public void setConnectionLayer(UserDefinedConnectionLayer connectionLayer) {
		this.connectionLayer = connectionLayer;
	}

	public boolean getInherited(GraphicalEditPart editPart) {
		if(Model_c.getOneGD_MDOnR34(getModel()) != diagramPart.getModel()) {
			return true;
		}
		// also check to see if the GraphicalElement is modeled to be in
		// the layer, which only occurs when it is not inherited
		GraphicalElement_c elem = null;
		if(editPart instanceof ShapeEditPart) {
			elem = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) editPart.getModel());
		} else if(editPart instanceof ConnectorEditPart) {
			elem = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) editPart.getModel());
		}
		if(elem != null) {
			Layer_c[] layers = Layer_c
					.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
							.getManyGD_GLAYsOnR35(elem));
			for(int i = 0; i < layers.length; i++) {
				if(layers[i].equals(getModel())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
