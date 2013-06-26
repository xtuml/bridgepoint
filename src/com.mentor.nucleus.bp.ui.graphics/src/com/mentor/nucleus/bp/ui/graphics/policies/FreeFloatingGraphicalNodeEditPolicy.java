//========================================================================
//
//File:      $RCSfile: FreeFloatingGraphicalNodeEditPolicy.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:51 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.policies;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.ConnectorSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.TerminalSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.WhitespaceTerminal_c;

public class FreeFloatingGraphicalNodeEditPolicy extends ConnectionPolicy {

	@Override
	public NonRootModelElement getEndTerm(ElementSpecification_c newSpec) {
		WhitespaceTerminal_c endTerm = WhitespaceTerminal_c
				.getOneTS_WSTOnR201(TerminalSpecification_c
						.getManyTS_TSPsOnR203(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(newSpec)));
		return endTerm;
	}

	@Override
	public NonRootModelElement getStartTerm(ElementSpecification_c newSpec) {
		WhitespaceTerminal_c startTerm = WhitespaceTerminal_c
				.getOneTS_WSTOnR201(TerminalSpecification_c
						.getManyTS_TSPsOnR202(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(newSpec)));
		return startTerm;
	}

	@Override
	protected boolean isReconnectAllowed(ReconnectRequest request,
			boolean source) {
		ConnectionEditPart connectionEditPart = request.getConnectionEditPart();
		Connector_c connector = (Connector_c) connectionEditPart.getModel();
		ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(connector));
		ConnectorSpecification_c cs = ConnectorSpecification_c
				.getOneTS_CSPOnR200(spec);
		TerminalSpecification_c[] termSpecs = null;
		if (source)
			termSpecs = TerminalSpecification_c.getManyTS_TSPsOnR202(cs);
		else
			termSpecs = TerminalSpecification_c.getManyTS_TSPsOnR203(cs);
		for (int i = 0; i < termSpecs.length; i++) {
			WhitespaceTerminal_c wt = WhitespaceTerminal_c
					.getOneTS_WSTOnR201(termSpecs[i]);
			if (wt != null)
				return true;
		}
		return false;
	}

}
