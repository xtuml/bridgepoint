//========================================================================
//
//File:      $RCSfile: ConnectorElement.java,v $
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

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.Style_c;

public class ConnectorElement {
	
	private UUID id = UUID.randomUUID();

	public String Get_connector_text(UUID objectId, boolean objImported, int at, UUID parentId) {
		if(at == End_c.Middle) {
			return "";
		}
		return "";
	}
	
	public UUID Get_ooa_id() {
		return id ;
	}
	
	public int Get_style(int at) {
		if(at == End_c.End) {
			return Style_c.OpenArrow;
		}
		if(at == End_c.Start) {
			return Style_c.FilledSquare;
		}
		return Style_c.Solid;
	}
}
