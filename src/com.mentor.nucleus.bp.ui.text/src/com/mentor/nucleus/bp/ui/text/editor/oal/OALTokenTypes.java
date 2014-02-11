//========================================================================
//
//File:      $RCSfile: OALTokenTypes.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.text.editor.oal;

/**
 * @author babar_ali
 */
public interface OALTokenTypes {
	public static final int TOKEN_TYPE_other = -1;
	public static final int TOKEN_TYPE_single_line_comment = 0;
	public static final int TOKEN_TYPE_multi_line_comment = 1;
	public static final int TOKEN_TYPE_string = 2;
	public static final int TOKEN_TYPE_keyword = 3;
}
