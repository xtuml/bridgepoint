package com.mentor.nucleus.bp.core.ui;
//========================================================================
//
//File:      $RCSfile: PtWizardPage.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:28:00 $
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