//========================================================================
//
//File:      $RCSfile: OALTokenTypes.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
