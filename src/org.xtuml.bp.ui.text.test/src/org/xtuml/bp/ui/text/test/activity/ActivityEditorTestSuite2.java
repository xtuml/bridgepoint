
//=====================================================================
//
//File:      $RCSfile: ActivityEditorTestSuite.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:46:35 $
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

package org.xtuml.bp.ui.text.test.activity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	OpenActivityEditor.class,	
	CloseActivityEditor.class,
	ActivityEditorInteraction2.class,
	ParseActivity.class,
	I697OpenActivityEditorFromMarker.class,
	ProcessAllActivitiesTest.class
})
public class ActivityEditorTestSuite2 {
	  // the class remains empty,
	  // used only as a holder for the above annotations
}