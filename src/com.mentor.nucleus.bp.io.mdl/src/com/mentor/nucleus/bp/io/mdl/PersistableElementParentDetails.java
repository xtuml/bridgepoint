package com.mentor.nucleus.bp.io.mdl;
//=====================================================================
//
//File:      $RCSfile: PersistableElementParentDetails.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:34:42 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import com.mentor.nucleus.bp.core.common.IPersistableElementParentDetails;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class PersistableElementParentDetails implements IPersistableElementParentDetails {
	public String associationNumber = "";
	public String associationPhrase = "";
	public String childKeyLetters = "";
	public NonRootModelElement parent = null;
	public NonRootModelElement child = null;
	public boolean isMany;
	@Override
	public String getAssociationNumber() {
		return associationNumber;
	}
	@Override
	public String getAssociationPhrase() {
		return associationPhrase;
	}
	@Override
	public NonRootModelElement getChild() {
		return child;
	}
	@Override
	public NonRootModelElement getParent() {
		return parent;
	}
	@Override
	public String getChildKeyLetters() {
		return childKeyLetters;
	}
	@Override
	public boolean isMany() {
		return isMany;
	}
}
