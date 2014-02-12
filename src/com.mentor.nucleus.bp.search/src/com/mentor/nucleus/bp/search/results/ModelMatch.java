package com.mentor.nucleus.bp.search.results;
//========================================================================
//
//File:      $RCSfile: ModelMatch.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:14:29 $
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
//
import org.eclipse.search.ui.text.Match;

import com.mentor.nucleus.bp.core.Match_c;

public class ModelMatch extends Match {
	
	public static final int DESCRIPTION = 0;
	public static final int ACTION_LANGUAGE = 1;
	
	private int type = DESCRIPTION;
	private Match_c modelMatch;

	public ModelMatch(Object element, int offset, int length, int type, Match_c modelMatch) {
		super(element, offset, length);
		this.type = type;
		this.modelMatch = modelMatch;
	}
	
	public int getType() {
		return type ;
	}

	public Match_c getMatch() {
		return modelMatch;
	}

}
