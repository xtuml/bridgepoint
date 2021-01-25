package org.xtuml.bp.model.compare.contentmergeviewer;
//========================================================================
//
//File: IModelCompareConstants.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
// Keys for model compare/merge
//
public interface IModelCompareConstants {
	public final String PREFIX = "org.xtuml.bp.model.compare."; //$NON-NLS-1$

	public static final String DTOOL_NEXT= "dlcl16/next_nav.png";	//$NON-NLS-1$
	public static final String ETOOL_NEXT= "elcl16/next_nav.png";	//$NON-NLS-1$
	public static final String CTOOL_NEXT= ETOOL_NEXT;

	public static final String DTOOL_PREV= "dlcl16/prev_nav.png";	//$NON-NLS-1$
	public static final String ETOOL_PREV= "elcl16/prev_nav.png";	//$NON-NLS-1$
	public static final String CTOOL_PREV= ETOOL_PREV;

	public static final String HUNK_OBJ = "obj16/hunk_obj.png"; //$NON-NLS-1$

	public static final String ERROR_OVERLAY= "ovr16/error_ov.png"; //$NON-NLS-1$
	public static final String IS_MERGED_OVERLAY= "ovr16/merged_ov.png"; //$NON-NLS-1$
	public static final String REMOVED_OVERLAY= "ovr16/removed_ov.png"; //$NON-NLS-1$
	public static final String WARNING_OVERLAY= "ovr16/warning_ov.png"; //$NON-NLS-1$

	public static final String RETARGET_PROJECT= "eview16/compare_view.png";	//$NON-NLS-1$

	public static final String IGNORE_WHITESPACE_ENABLED= "etool16/ignorews_edit.png";	//$NON-NLS-1$
	public static final String IGNORE_WHITESPACE_DISABLED= "dtool16/ignorews_edit.png";	//$NON-NLS-1$

	public static final String PROP_ANCESTOR_VISIBLE = PREFIX + "AncestorVisible"; //$NON-NLS-1$
	public static final String PROP_IGNORE_ANCESTOR = PREFIX + "IgnoreAncestor"; //$NON-NLS-1$
	public static final String PROP_TITLE = PREFIX + "Title"; //$NON-NLS-1$
	public static final String PROP_TITLE_IMAGE = PREFIX + "TitleImage"; //$NON-NLS-1$
	public static final String PROP_SELECTED_EDITION = PREFIX + "SelectedEdition"; //$NON-NLS-1$

	public static final int COMPARE_IMAGE_WIDTH= 22;

	public static final String PREF_NAVIGATION_END_ACTION= PREFIX + "NavigationEndAction"; //$NON-NLS-1$
	public static final String PREF_NAVIGATION_END_ACTION_LOCAL= PREFIX + "NavigationEndActionLocal"; //$NON-NLS-1$
	public static final String PREF_VALUE_PROMPT = "prompt"; //$NON-NLS-1$
	public static final String PREF_VALUE_LOOP = "loop"; //$NON-NLS-1$
	public static final String PREF_VALUE_NEXT = "next"; //$NON-NLS-1$
	public static final String PREF_VALUE_DO_NOTHING = "doNothing"; //$NON-NLS-1$

	public static final String COMMAND_IGNORE_WHITESPACE = PREFIX + "ignoreWhiteSpace"; //$NON-NLS-1$
}
