//========================================================================
//
//File:      $RCSfile: ConnectorCreationFactory.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:52 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.factories;

import org.eclipse.gef.requests.CreationFactory;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;

public class ConnectorCreationFactory implements CreationFactory {

	@Override
	public Object getNewObject() {
		return Connector_c.class;
	}

	@Override
	public Object getObjectType() {
		return Connector_c.class;
	}

}
