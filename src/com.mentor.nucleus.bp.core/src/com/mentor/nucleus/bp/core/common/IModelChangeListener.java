package com.mentor.nucleus.bp.core.common;

import com.mentor.nucleus.bp.core.SystemModel_c;
//========================================================================
//
//File:      $RCSfile: IModelChangeListener.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 13:26:32 $
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

public interface IModelChangeListener {
	
    /**
     * This method assumes that the 'event' object has the batched deltas. Its default 
     * implementation shall iterate the modelDeltas in the event and call the respective
     * method as per delta kind.     * 
     */
	void modelChanged(ModelChangedEvent event);
	
	void modelElementDeleted(ModelChangedEvent event, IModelDelta delta);
	void modelElementAttributeChanged(ModelChangedEvent event, IModelDelta delta);
	void modelElementCreated(ModelChangedEvent event, IModelDelta delta);
	void modelElementRecreated(ModelChangedEvent event, IModelDelta delta);
	void modelElementRelationChanged(ModelChangedEvent event, IModelDelta delta);
	
	void modelElementLoaded(ModelChangedEvent event);
	void modelElementUnloaded(ModelChangedEvent event);
	
	void modelRootChanged(ModelChangedEvent event);
	
	void modelElementReloaded(ModelChangedEvent event);
	void modelElementAboutToBeReloaded(ModelChangedEvent event);
	
	void modelElementAboutToBeDeleted(ModelChangedEvent event);
	void systemAboutToBeDisabled(SystemModel_c system);
	
	/**
	 * This method is used to handle all other event types for which a specific method does 
	 * not exist. One such event is "UNKNOWN". 
	 */
	void modelEventReceived(ModelChangedEvent event);
	
	boolean isBatchedNotificationEnabled();

	/**
	 * This method is used to indicate to the dispatcher that the listener needs
	 * to be run synchronously. Note that specifying a synchronous listener has
     * the following threading and performance implications:
     *
     * - A synchronously invoked listener may or may not be invoked on the UI
     *   thread. By contrast, an asynchronous listener invocation always occurs
     *   on the UI thread.
     * - In the case of a listener synchronously invoked from Verifier,
     *   execution completes entirely in the Verifier execution thread. This
     *   will therefore directly degrade Verifier performance.
     *   
     *   All uses of a synchronous listener must consider these consequences
     *   a) return control as quickly as possible, b) do not attempt any UI
     *   updates without using a Display.*exec() call (and preferably use the
     *   asyncExec variant).
	 */
	boolean isSynchronous();
	
	/**
	 * This method is used to indicate to the dispatcher that the listener must
	 * not be masked during Verifier runtime. This has performance implications
	 * and should a) be used sparingly b) return control as quickly as possible.
	 */
	boolean isMaskable();

}