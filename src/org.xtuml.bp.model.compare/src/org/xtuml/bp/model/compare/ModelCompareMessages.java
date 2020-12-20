package org.xtuml.bp.model.compare;
//========================================================================
//
//File: ModelCompareMessages.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
// Access to properties
//
import org.eclipse.osgi.util.NLS;

public final class ModelCompareMessages extends NLS {

	private static final String BUNDLE_NAME = "org.xtuml.bp.model.compare.ComparePluginMessages";//$NON-NLS-1$

	private ModelCompareMessages() {
		// Do not instantiate
	}

	public static String NavigationEndDialogTitleNext;
	public static String NavigationEndDialogMessageNext;
	public static String NavigationEndDialogLoopMessageNext;
	public static String NavigationEndDialogNextMessageNext;
	public static String NavigationEndDialogTitlePrev;
	public static String NavigationEndDialogMessagePrev;
	public static String NavigationEndDialogLoopMessagePrev;
	public static String NavigationEndDialogNextMessagePrev;
	public static String NavigationEndDialogDoNothing;
	public static String NavigationEndDialogRemember;
	public static String NavigationEndDialogOptions;
	public static String CompareNavigationPreferencePage_title;
	public static String CompareNavigationPreferencePage_prompt;
	public static String CompareNavigationPreferencePage_loop;
	public static String CompareNavigationPreferencePage_next;
	public static String CompareNavigationPreferencePage_nothing;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ModelCompareMessages.class);
	}
}
