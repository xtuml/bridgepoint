//========================================================================
//
//File:      $RCSfile: ITreeDifferencerProvider.java,v $
//Version:   $Revision: 1.2.30.1 $
//Modified:  $Date: 2013/07/08 14:38:14 $
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
package com.mentor.nucleus.bp.model.compare;

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * This interface allows clients to provide a tree that allows the "real"
 * elements to become wrapped in comparable objects.
 * 
 * @author Travis London
 *
 */
public interface ITreeDifferencerProvider extends ITreeContentProvider {
	public ComparableTreeObject getComparableTreeObject(Object realElement);

	public Object[] getChildrenOfType(Object parent, Class<?> type);
}
