//========================================================================
//
// File:      $RCSfile: IPreferenceModelEditor.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.preference;

/**
 * @author babar_ali
 */
public interface IPreferenceModelEditor {
    public void setModel(IPreferenceModel model);
    public IPreferenceModel updateModel();
}
