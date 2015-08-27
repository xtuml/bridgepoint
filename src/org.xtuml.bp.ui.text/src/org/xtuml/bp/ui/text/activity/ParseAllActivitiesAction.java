
//=====================================================================
//
//File:      $RCSfile: ParseAllActivitiesAction.java,v $
//Version:   $Revision: 1.23 $
//Modified:  $Date: 2013/01/10 23:20:48 $
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

package org.xtuml.bp.ui.text.activity;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;

import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.text.TextPlugin;

public class ParseAllActivitiesAction implements IActionDelegate
{
    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action)
    {
        IRunnableWithProgress rwp = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor) 
            {
                IStructuredSelection selection =
                        Selection.getInstance().getStructuredSelection();
                for (Iterator iterator = selection.iterator(); iterator.hasNext();)
                {
                    Object context = iterator.next();
                    if(context instanceof Component_c) {
                        ModelRoot.disableChangeNotification();
                        try {
                        AllActivityModifier aam = new AllActivityModifier((Component_c)context, monitor);
                        aam.processAllActivities(AllActivityModifier.PARSE);
                        }
                        finally {
                        	ModelRoot.enableChangeNotification();
                        }
                    } else if(context instanceof Package_c) {
                        ModelRoot.disableChangeNotification();
                        try {
                        AllActivityModifier aam = new AllActivityModifier((Package_c)context, monitor);
                        aam.processAllActivities(AllActivityModifier.PARSE);
                        }
                        finally {
                        	ModelRoot.enableChangeNotification();
                        }
                    }
                    else if(context instanceof SystemModel_c) {
                        ModelRoot.disableChangeNotification();
                        try {
                        AllActivityModifier aam = new AllActivityModifier((SystemModel_c)context, monitor);
                        aam.processAllActivities(AllActivityModifier.PARSE);
                        }
                        finally {
                        	ModelRoot.enableChangeNotification();
                        }
                    }
                }
                }
            };
        try {
        	// can't fork this as we need this to run in main thread when
        	// a domain load is forced
            new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(false, true, rwp);
        }
        catch( InterruptedException ie )
        {
            TextPlugin.logError( "ParseAllActivitiesAction", ie );
        }
        catch( InvocationTargetException ite )
        {
            TextPlugin.logError( "ParseAllActivitiesAction", ite );
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection)
    { 
        // We cache our selection in core, so no action needed here.
    }

}
