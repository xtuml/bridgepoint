package org.xtuml.bp.core.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import org.xtuml.bp.core.CorePlugin;

public class WizardNewSystemCreationPage extends WizardNewProjectCreationPage {

    /**
     * Creates a new project creation wizard page.
     *
     * @param pageName
     *            the name of this page
     */
    public WizardNewSystemCreationPage(String pageName) {
        super(pageName);
    }

    /**
     * Returns whether the chosen system name is valid (specifically, it checks that
     * the name is unique for OS's that cannot distinguish files with the same name
     * in a different case).
     *
     * @return <code>true</code> if all controls are valid, and <code>false</code>
     *         if at least one is invalid
     */
    public boolean validateSystemName(final String projectNameContents) {
        if (CorePlugin.osIsCaseInsensitive()) {
            // can't be the same as an existing project
            IProject[] sys_set = CorePlugin.getWorkspace().getRoot().getProjects();
            for (int i = 0; i < sys_set.length; ++i) {
                if (projectNameContents.toLowerCase().equals((sys_set[i]).getName().toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns whether this page's controls currently all contain valid values.
     *
     * @return <code>true</code> if all controls are valid, and <code>false</code>
     *         if at least one is invalid
     */
    protected boolean validatePage() {
        if (super.validatePage()) {
            // super.validatePage() has checked for an exact name match
            if (!validateSystemName(super.getProjectName())) {
                setErrorMessage(
                        "The underlying operating system is case insensitive\nThe project name must be unique in this workspace");
                setMessage(null);
                return false;
            }
            setErrorMessage(null);
            setMessage(null);
            return true;
        }
        return false;
    }

    public void createControl(Composite parent) {
        // call the supertype createControl to setup control
        super.createControl(parent);
        // add the F1 context support to created control
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), "org.xtuml.bp.core.newProjectDialogId");
    }
}