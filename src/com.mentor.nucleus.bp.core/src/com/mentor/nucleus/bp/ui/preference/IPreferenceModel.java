//========================================================================
//
// File:      $RCSfile: IPreferenceModel.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package com.mentor.nucleus.bp.ui.preference;

/**
 * This defines a common interface for encapsulating all preference data. All 
 * preference pages will have one instance of IPreferenceModel. Both preference
 * pages and plugin component share the same model. However the control of when 
 * model should be updated is still with external classes such as preference 
 * page.
 *
 * @author babar_ali
 */
public interface IPreferenceModel extends Cloneable{
    /**
     * @return Class
     */
    public Class getStoreClass();
    public IPreferenceModelStore getStore();
    public void synchronizeTo(IPreferenceModel model);
    public void addModelChangeListener(IChangeListener listener);
    public void removeModelChangeListener(IChangeListener listener);
    public Object deepClone();
    
    public interface IChangeListener {
        public void modelChanged(IPreferenceModel model, int changeHints);
    } 
    
}
