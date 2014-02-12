//========================================================================
//
//File:      $RCSfile: IPersistenceHierarchyMetaData.java,v $
//Version:   $Revision: 1.12 $
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
//
package com.mentor.nucleus.bp.core.common;

import java.util.HashMap;
import java.util.List;

public interface IPersistenceHierarchyMetaData {
	public boolean isComponentRoot(NonRootModelElement rootElement);
	
	public String getComponentType(NonRootModelElement componentRoot);
	
	public NonRootModelElement getParent(NonRootModelElement element);
	public IPersistableElementParentDetails getParentDetails(NonRootModelElement element);
	public List getChildren(NonRootModelElement element, boolean componentOnly);
	
    public NonRootModelElement getParentComponentRootModelElement(NonRootModelElement componentRoot);
    public NonRootModelElement getParentComponentRootModelElement(NonRootModelElement element, boolean loadComponent);
        
    public List getChildrenComponentRootModelElements(NonRootModelElement componentRoot);
    public List getChildrenComponentRootModelElements(NonRootModelElement componentRoot, Class type);
    
    public NonRootModelElement getComponentRootModelElement(NonRootModelElement element);
    public NonRootModelElement getAssociationRootModelElement(String associationName,
            NonRootModelElement object1, NonRootModelElement object2);
    
    public boolean isRootElementRenamable(PersistableModelComponent component);
    public String getRootElementName (NonRootModelElement rootElement);
    public void setRootElementName (NonRootModelElement rootElement, String newName);
    
    public NonRootModelElement[] getActivityModelElements(NonRootModelElement element, Class type);
       
    public String[] getComponentRootRootModelElementTypes(Class meClass);

    public List findExternalRGOs(NonRootModelElement element);
    public List findExternalRGOs(NonRootModelElement element, boolean loadComponent);
    public List findExternalRGOs(NonRootModelElement element, boolean loadComponent, boolean checkSameComponent);
    public HashMap<String, List<NonRootModelElement>> getAssociationMapOfExternalRGOs(NonRootModelElement element);
    
    //These are helper methods to determine whether given element has any RGO
    //This is an efficient method then findExternalRGOs() as it returns immediately when it
    //finds any RGO 
    public boolean hasExternalRGO(NonRootModelElement element);
    public boolean hasExternalRGO(NonRootModelElement element, boolean loadComponent);
    
    public List findExternalRGOsToContainingComponent(NonRootModelElement element);
    public List findExternalRGOsToContainingComponent(NonRootModelElement element,boolean loadComponent);
}