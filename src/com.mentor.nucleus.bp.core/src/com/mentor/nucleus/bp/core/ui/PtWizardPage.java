package com.mentor.nucleus.bp.core.ui;
//========================================================================
//
//File:      $RCSfile: PtWizardPage.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:28:00 $
//
//(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
import org.eclipse.jface.wizard.WizardPage;

abstract class PtWizardPage extends WizardPage
{
    public PtWizardPage()
    {
        super("");
    }
    public PtWizardPage(String name)
    {
        super(name);
    }
    public abstract void onPageEntry();
}