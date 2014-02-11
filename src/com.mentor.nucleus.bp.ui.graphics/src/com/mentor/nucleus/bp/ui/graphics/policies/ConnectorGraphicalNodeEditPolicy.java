//========================================================================
//
//File:      $RCSfile: ConnectorGraphicalNodeEditPolicy.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:51 $
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
package com.mentor.nucleus.bp.ui.graphics.policies;

import java.lang.reflect.Method;
import java.util.UUID;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ConnectorSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ConnectorTerminal_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.TerminalSpecification_c;
import com.mentor.nucleus.bp.ui.graphics.commands.CreateConnectionCommand;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class ConnectorGraphicalNodeEditPolicy extends ConnectionPolicy {

	@Override
	public NonRootModelElement getEndTerm(ElementSpecification_c newSpec) {
		Connector_c connector = (Connector_c) getHost().getModel();
		final ElementSpecification_c sourceSpec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(connector));
		ConnectorTerminal_c endTerm = ConnectorTerminal_c.getOneTS_CNTOnR201(
				TerminalSpecification_c
						.getManyTS_TSPsOnR203(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(newSpec)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ConnectorTerminal_c) candidate).getOoa_type() == sourceSpec
								.getOoa_type();
					}
				});
		return endTerm;
	}

	@Override
	public NonRootModelElement getStartTerm(ElementSpecification_c newSpec) {
		Connector_c connector = (Connector_c) getHost().getModel();
		final ElementSpecification_c sourceSpec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(connector));
		ConnectorTerminal_c startTerm = ConnectorTerminal_c.getOneTS_CNTOnR201(
				TerminalSpecification_c
						.getManyTS_TSPsOnR202(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(newSpec)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ConnectorTerminal_c) candidate).getOoa_type() == sourceSpec
								.getOoa_type();
					}
				});
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
		Connector_c host = (Connector_c) getHost().getModel();
		ElementSpecification_c conSpec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(host));
		for (int i = 0; i < termSpecs.length; i++) {
			ConnectorTerminal_c ct = ConnectorTerminal_c
					.getOneTS_CNTOnR201(termSpecs[i]);
			if (ct != null) {
				if (ct.getOoa_type() == conSpec.getOoa_type())
					return true;
			}
		}
		return false;
	}

	@Override
	protected Command getSpecializedReconnectTargetCommand(
			final ReconnectRequest request) {
		// currently only support connector anchorage, and when moving from
		// whitespace to a connector
		if (getHost() != request.getConnectionEditPart().getTarget()
				&& request.getConnectionEditPart().getTarget() instanceof DiagramEditPart) {
			// if the metamodel contains a linkConnector, or dissatisfy
			// operation
			// then we allow the reconnection
			// additionally if the metamodel element has a Canlinkconnector
			// method
			// call that first
			final Method canLinkMethod = Cl_c.findMethod(
					getConnectionRepresents(request), "Canlinkconnector", // $NON-NLS-1$
					new Class[] { UUID.class });
			if (canLinkMethod != null) {
				Boolean bResult = (Boolean) Cl_c.doMethod(canLinkMethod,
						getConnectionRepresents(request), new Object[] { Cl_c
								.Getooa_idfrominstance(getHostRepresents()) });
				boolean result = bResult.booleanValue();
				if (!result)
					return null;
			}
			final Method method = Cl_c.findMethod(
					getConnectionRepresents(request), "Linkconnector", // $NON-NLS-1$
					new Class[] { UUID.class });
			if (method != null)
				return new Command("Retarget Connection") {

					@Override
					public void execute() {
						Object result = Cl_c
								.doMethod(
										method,
										getConnectionRepresents(request),
										new Object[] { Cl_c
												.Getooa_idfrominstance(getHostRepresents()) });
						if (result instanceof Boolean) {
							if (!((Boolean) result).booleanValue())
								return;
						}
						// finalize the connector, this call will create the
						// necessary
						// graphical elements
						ModelTool_c tool = ModelTool_c
								.getOneCT_MTLOnR100(getHostModel());
						associateTerminalSpecs(GraphicalElement_c
								.getOneGD_GEOnR2((Connector_c) request
										.getConnectionEditPart().getModel()));
						tool.Finalizeconnector(((Connector_c) getHost()
								.getModel()).getElementid(),
								((Connector_c) request.getConnectionEditPart()
										.getModel()).getElementid());
						ElementSpecification_c spec = ElementSpecification_c
								.getOneGD_ESOnR10(GraphicalElement_c
										.getOneGD_GEOnR2((Connector_c) request
												.getConnectionEditPart()
												.getModel()));
						if (spec.getIsanchorhost()) {
							// if the element being reconnected is a host
							// then switch the start and end elements so
							// that the correct anchor host is used
							CreateConnectionCommand
									.moveGraphicalConnectors(
											(Connector_c) getHost().getModel(),
											(Connector_c) request
													.getConnectionEditPart()
													.getModel());
						}
					}

				};
		}
		return null;
	}

	protected Object getHostRepresents() {
		Object model = getHost().getModel();
		if (model instanceof Connector_c) {
			return GraphicalElement_c.getOneGD_GEOnR2((Connector_c) model)
					.getRepresents();
		}
		return null;
	}

	protected void associateTerminalSpecs(GraphicalElement_c sourceElement) {
		ElementSpecification_c newSpec = ElementSpecification_c
				.getOneGD_ESOnR10(sourceElement);
		ConnectorTerminal_c endTerm = (ConnectorTerminal_c) getEndTerm(newSpec);
		TerminalSpecification_c term = TerminalSpecification_c
				.getOneTS_TSPOnR201(endTerm);
		term.relateAcrossR206To(GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getHost().getModel()));
	}

	protected Model_c getHostModel() {
		Connector_c model = (Connector_c) getHost().getModel();
		return Model_c.getOneGD_MDOnR1(GraphicalElement_c
				.getOneGD_GEOnR2(model));
	}

}
