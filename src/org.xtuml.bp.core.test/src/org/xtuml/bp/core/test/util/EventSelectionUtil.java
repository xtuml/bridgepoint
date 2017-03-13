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

package org.xtuml.bp.core.test.util;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.ui.GenericPackageAssignEventOnSM_CRTXNAction;
import org.xtuml.bp.core.ui.GenericPackageAssignEventOnSM_CRTXNWizardPage1;
import org.xtuml.bp.core.ui.Selection;

import junit.framework.TestCase;

/**
 * Contains various utility methods related to testing event selection in the 
 * user interface.
 */
public class EventSelectionUtil
{
    /**
     * A cache of the selection object to make it quicker to reference it
     * throughout this class.
     */
    private static final Selection selection = Selection.getInstance();

    public static IWizard checkForPresenceOfEventInAssignListForCreationTransitionGenerics(
            String eventLabel, CreationTransition_c transition)
        {
            return checkForPresenceOrAbsenceOfEventInAssignListForCreationTransitionGenerics(
                eventLabel, transition, true);
        }
         
    private static IWizard checkForPresenceOrAbsenceOfEventInAssignListForCreationTransitionGenerics(
            String eventLabel, CreationTransition_c transition, boolean checkForPresence)
        {
            // select the given transition
            selection.clear();
            selection.addToSelection(transition);

            // get the assign-event-on-transition wizard displayed
            GenericPackageAssignEventOnSM_CRTXNAction action = new GenericPackageAssignEventOnSM_CRTXNAction();
            action.setActivePart(new Action() {}, 
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .getActivePart());
            WizardDialog dialog = action.SM_CRTXN_GenericPackageAssignEvent(selection
                .getStructuredSelection());

            // check that the event of the given label is (or isn't, 
            // according to the given switch) one of the available choices
            GenericPackageAssignEventOnSM_CRTXNWizardPage1 page = (GenericPackageAssignEventOnSM_CRTXNWizardPage1) 
                dialog.getCurrentPage();
            checkForPresenceOrAbsenceOfEventInList(eventLabel, page.EventCombo,
                checkForPresence, page.getWizard());

            return page.getWizard();
        }

    /**
     * Asserts that the event of the given label is present (or absent,
     * depending on the given parameter) in the given combo being shown in the
     * given wizard. If absence is checked for, the dialog is cancelled.
     */
    private static void checkForPresenceOrAbsenceOfEventInList(
        String eventLabel, Combo combo, boolean checkForPresence, 
        IWizard wizard)
    {
        // check that the event of the given label is (or isn't, 
        // according to the given switch) one of the available choices
        int index = combo.indexOf(eventLabel);
        TestCase.assertTrue("Event " + eventLabel + " is " 
            + (checkForPresence ? "not " : "") + "available for selection.", 
            checkForPresence ? index != -1 : index == -1);

        // if absence is being checked for
        if (!checkForPresence) {
            // cancel the dialog, since we know there will be nothing to select
            wizard.performCancel();
        }

        // otherwise
        else {
            // select the event of the given label in the combo, in case the 
            // caller is going to finish the wizard
            combo.select(index);
        }
    }
}
