package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: IPersistableElementParentDetails.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:39:27 $
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

public interface IPersistableElementParentDetails {
	public NonRootModelElement getChild();
	public NonRootModelElement getParent();
	public String getAssociationNumber();
	public String getAssociationPhrase();
	public String getChildKeyLetters();
	public boolean isMany();
}
