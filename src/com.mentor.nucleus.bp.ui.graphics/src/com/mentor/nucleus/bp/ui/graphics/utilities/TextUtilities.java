//========================================================================
//
//File:      $RCSfile: TextUtilities.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:06:24 $
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
package com.mentor.nucleus.bp.ui.graphics.utilities;

import java.util.UUID;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Font;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.Justification_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Os_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;

public class TextUtilities {

	public static String wrapText(String value, Font font, int width) {
		String result = "";//$NON-NLS-1$
		String[] lines = value.split("\n");//$NON-NLS-1$
		for (int i = 0; i < lines.length; i++) {
			String[] words = lines[i].split(" ");//$NON-NLS-1$
			String intermediateResult = "";//$NON-NLS-1$
			int wordCount = words.length;
			int currentWord = 0;
			while (currentWord < wordCount) {
				String line = "";//$NON-NLS-1$
				int wordWidth = FigureUtilities.getTextExtents(
						words[currentWord], font).width;
				if (wordWidth > width) {
					// there is only room for one word on this line . . .
					line = words[currentWord];
					currentWord++;
				} else {
					// at least one word will fit on this line, but how many?
					boolean atLineStart = true;
					int lineWidth = wordWidth;
					while (lineWidth <= width && currentWord < wordCount) {
						// total line width <= wrap width and there are more
						// words ...
						if (!atLineStart) {
							// add the space back that was removed by the word
							// split above
							line = line + " ";//$NON-NLS-1$
						}
						if (lineWidth <= width) {
							// this word will fit, add it to the line ...
							line = line + words[currentWord];
							currentWord++; // ... move the cursor to the next
											// word ...
							if (currentWord < wordCount) {
								// ... and recompute the line width with the
								// addition
								lineWidth = FigureUtilities.getTextExtents(
										line, font).width;
							}
						}
						atLineStart = false;
					}
				}
				intermediateResult = intermediateResult + line;
				if (currentWord < wordCount) {
					intermediateResult = intermediateResult + "\n";//$NON-NLS-1$
				}
			} // while len < count
			if (i < lines.length - 1) {
				intermediateResult = intermediateResult + "\n";//$NON-NLS-1$
			}
			result = result + intermediateResult;
		} // for each line
		return result;
	}

	public static String getTextValueFor(FloatingText_c text, AbstractGraphicalEditPart part) {
		Shape_c shape = Shape_c.getOneGD_SHPOnR27(text);
		if (shape != null) {
			GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(shape);
			return Cl_c.Getcompartmenttext(Justification_c.Floating, 0, 0, elem
					.getRepresents());
		}
		Connector_c connector = Connector_c.getOneGD_CONOnR8(text);
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(connector);
		if (connector != null
				&& elem != null
				&& elem.getRepresents() != null
				&& (elem.getRepresents() instanceof NonRootModelElement && !((NonRootModelElement) elem
						.getRepresents()).isOrphaned())) {
			boolean endHidden = isEndHidden((ConnectorEditPart) part);
			GraphicalElement_c conElem = GraphicalElement_c
					.getOneGD_GEOnR2(connector);
			if (text.getEnd() == End_c.Middle) {
				return Cl_c.Getconnectortext(text.getEnd(), Os_c
						.Null_unique_id(), false, conElem.getRepresents(), Cl_c
						.Getooaid(Model_c.getOneGD_MDOnR1(conElem)
								.getRepresents()), endHidden);
			}
			if (text.getEnd() == End_c.Start)
				return Cl_c.Getconnectortext(text.getEnd(),
						getElementIdAt(true, part), isElementAtImported(true, part),
						conElem.getRepresents(), Cl_c.Getooaid(Model_c
								.getOneGD_MDOnR1(conElem).getRepresents()), endHidden);
			if (text.getEnd() == End_c.End)
				return Cl_c.Getconnectortext(text.getEnd(),
						getElementIdAt(false, part), isElementAtImported(false, part),
						conElem.getRepresents(), Cl_c.Getooaid(Model_c
								.getOneGD_MDOnR1(conElem).getRepresents()), endHidden);
		}
		return "";
	}
	
	public static boolean isEndHidden(ConnectorEditPart part) {
		EditPart target = part.getTarget();
		if(target == null) {
			return false;
		}
		return !((AbstractGraphicalEditPart) target).getFigure().getParent()
				.isVisible();
	}

	private static boolean isElementAtImported(boolean isSource, AbstractGraphicalEditPart part) {
		ConnectorEditPart connectorPart = (ConnectorEditPart) part;
		if (isSource) {
			if (connectorPart.getSource() == null)
				return false;
		} else {
			if (connectorPart.getTarget() == null)
				return false;
		}
		Object source = null;
		if (isSource)
			source = connectorPart.getSource().getModel();
		else
			source = connectorPart.getTarget().getModel();
		if (source instanceof Shape_c) {
			// only shapes require this check
			Shape_c shape = (Shape_c) source;
			GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shape);
			// during project deletes this ge can be null as the editor is
			// being refreshed after an object's deletion, yet before its 
			// element has been removed
			if(ge != null) {
				return ge.getOoa_type() == Ooatype_c.ImportedClass;
			}
		}
		return false;
	}

	private static UUID getElementIdAt(boolean isSource, AbstractGraphicalEditPart part) {
		ConnectorEditPart connectorPart = (ConnectorEditPart) part;
		if (isSource) {
			if (connectorPart.getSource() == null)
				return Os_c.Null_unique_id();
		} else {
			if (connectorPart.getTarget() == null)
				return Os_c.Null_unique_id();
		}
		Object source = null;
		if (isSource)
			source = connectorPart.getSource().getModel();
		else
			source = connectorPart.getTarget().getModel();
		if (source instanceof Shape_c) {
			Shape_c shape = (Shape_c) source;
			GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shape);
			// during project deletes this ge can be null as the editor is
			// being refreshed after an object's deletion, yet before its 
			// element has been removed
			if(ge != null) {
				return Cl_c.Getooaid(ge.getRepresents());
			}
		}
		if (source instanceof Connector_c) {
			Connector_c connector = (Connector_c) source;
			if(GraphicalElement_c.getOneGD_GEOnR2(connector) == null) {
				// this can occur during auto reconciling, just ignore
				// here
				return Os_c.Null_unique_id();
			}
			return Cl_c.Getooaid(GraphicalElement_c.getOneGD_GEOnR2(connector)
					.getRepresents());
		}
		return Os_c.Null_unique_id();
	}

	
}
