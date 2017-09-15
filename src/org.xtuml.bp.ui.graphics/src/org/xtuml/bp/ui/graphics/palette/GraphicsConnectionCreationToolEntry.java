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
package org.xtuml.bp.ui.graphics.palette;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.resource.ImageDescriptor;

import org.xtuml.bp.ui.graphics.tools.GraphicsConnectionCreationTool;

public class GraphicsConnectionCreationToolEntry extends
		ConnectionCreationToolEntry {

	private int type;
	private static String PROPERTY_PREFIX = "bridgepoint.";

	public GraphicsConnectionCreationToolEntry(String label, String shortDesc,
			CreationFactory factory, ImageDescriptor iconSmall,
			ImageDescriptor iconLarge, int ooaType) {
		super(label, shortDesc, factory, iconSmall, iconLarge);
		type = ooaType;
	
		String propertyLabel = label.replaceAll("\\/+", "");
		propertyLabel = propertyLabel.replaceAll("\\s+","");
		
		// Special case names of tools that have naming duplication
		if ( type == 87 ) {
			propertyLabel = "UseCaseAssociation";
		}
		// End special case

		String propertyKey = PROPERTY_PREFIX + propertyLabel;
        String actualPropertyValue = System.getProperty(propertyKey, "enabled");
        if ( actualPropertyValue.equals("disabled") ) {
        	setVisible(false);
        }	
	}

	@Override
	public Tool createTool() {
		return new GraphicsConnectionCreationTool(
				(CreationFactory) getToolProperty(CreationTool.PROPERTY_CREATION_FACTORY),
				type);
	}
}
