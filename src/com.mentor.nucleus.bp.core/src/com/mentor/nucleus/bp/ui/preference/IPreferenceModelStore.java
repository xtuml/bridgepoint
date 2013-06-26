//========================================================================
//
// File:      $RCSfile: IPreferenceModelStore.java,v $
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

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author babar_ali
 */
public interface IPreferenceModelStore {
    public Class getModelClass(); 
    public void saveModel(IPreferenceStore store, IPreferenceModel model);
    public IPreferenceModel loadModel(IPreferenceStore store, BasePlugin plugin, IPreferenceModel model);
    public void restoreModelDefaults(IPreferenceModel model);
}
