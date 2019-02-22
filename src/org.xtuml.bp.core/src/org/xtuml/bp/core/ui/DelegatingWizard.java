package org.xtuml.bp.core.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.xtuml.bp.core.CorePlugin;

public abstract class DelegatingWizard extends Wizard implements INewWizard {

    private List<WizardDelegate> delegateWizards = new ArrayList<>();

    // Reference to the pages provided by this wizard
    private IWorkbench m_workbench = null;
    private IStructuredSelection m_selection = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        m_workbench = workbench;
        m_selection = selection;
    }

    /*
     * get the extension point this wizard should obtain its delegate from
     */
    public abstract String getExtensionPoint();

    public List<WizardDelegate> getDelegatingWizards() {
        return delegateWizards;
    }

    public void addPages() {
        super.addPages();
        for (WizardDelegate delegateWizard : delegateWizards) {
            delegateWizard.addPages();
        }
    }

    public boolean canFinish() {
        boolean result = super.canFinish();
        for (WizardDelegate delegateWizard : delegateWizards) {
            result = result && delegateWizard.canFinish();
        }
        return result;
    }

    /**
     * Returns the successor of the given page.
     * <p>
     * This method is typically called by a wizard page
     * </p>
     *
     * @param page
     *            the page
     * @return the next page, or <code>null</code> if none
     */
    public IWizardPage getNextPage(IWizardPage page) {
        IWizardPage next = null;
        IWizardPage[] pages = getPages();
        for (int i = 0; i < pages.length; i++) {
            if (i > 0 && pages[i - 1].equals(page)) {
                next = pages[i];
            }
        }
        if (next != null) {
            next.setWizard(this);
        }
        return next;
    }

    /**
     * Returns the wizard page with the given name belonging to this wizard.
     *
     * @param pageName
     *            the name of the wizard page
     * @return the wizard page with the given name, or <code>null</code> if none
     */
    public IWizardPage getPage(String pageName) {
        IWizardPage page = super.getPage(pageName);
        for (WizardDelegate delegateWizard : delegateWizards) {
            if (page == null) {
                page = delegateWizard.getPage(pageName);
                break;
            }
        }
        if (page != null) {
            page.setWizard(this);
        }
        return page;
    }

    /**
     * Returns the number of pages in this wizard.
     *
     * @return the number of wizard pages
     */
    public int getPageCount() {
        int pageCount = super.getPageCount();
        for (WizardDelegate delegateWizard : delegateWizards) {
            pageCount += delegateWizard.getPageCount();
        }
        return pageCount;
    }

    /**
     * Returns all the pages in this wizard.
     *
     * @return a list of pages
     */
    public IWizardPage[] getPages() {
        return Stream
                .concat(Stream.of(super.getPages()),
                        delegateWizards.stream().flatMap(delegate -> Stream.of(delegate.getPages())))
                .collect(Collectors.toList()).toArray(new IWizardPage[0]);
    }

    /**
     * Returns the predecessor of the given page.
     * <p>
     * This method is typically called by a wizard page
     * </p>
     *
     * @param page
     *            the page
     * @return the previous page, or <code>null</code> if none
     */
    public IWizardPage getPreviousPage(IWizardPage page) {
        IWizardPage prev = null;
        IWizardPage[] pages = getPages();
        for (int i = pages.length - 1; i >= 0; i--) {
            if (i < pages.length - 1 && pages[i + 1].equals(page)) {
                prev = pages[i];
            }
        }
        if (prev != null) {
            prev.setWizard(this);
        }
        return prev;
    }

    /**
     * Returns the first page inserted into the wizard or its downstream delegate.
     */
    public IWizardPage getStartingPage() {
        IWizardPage start = super.getStartingPage();
        if (start == null && delegateWizards.size() > 0) {
            // The current wizard has no pages of its own,
            // see if the delegate has any.
            start = delegateWizards.iterator().next().getStartingPage();
        }
        if (start != null) {
            start.setWizard(this);
        }
        return start;
    }

    /**
     * Returns whether this wizard needs Previous and Next buttons.
     * <p>
     * The result of this method is typically used by the container.
     * </p>
     *
     * @return <code>true</code> if Previous and Next buttons are required, and
     *         <code>false</code> if none are needed
     */
    public boolean needsPreviousAndNextButtons() {
        return getPages().length > 1;
    }

    /**
     * Returns whether this wizard needs a progress monitor.
     * <p>
     * The result of this method is typically used by the container.
     * </p>
     *
     * @return <code>true</code> if a progress monitor is required, and
     *         <code>false</code> if none is needed
     */
    public boolean needsProgressMonitor() {
        boolean result = super.needsProgressMonitor();
        for (WizardDelegate delegateWizard : delegateWizards) {
            result = result || delegateWizard.needsProgressMonitor();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        //
        // Since this is a delegating wizard, only the performFinish(IProject)
        // variant of this method should be called. This version does nothing.
        //
        CorePlugin.logError("DelegatingWizard.performFinish called illegally.", null);
        return false;
    }

    public boolean performFinish(IProject newProject) {
        boolean result = true;
        for (WizardDelegate delegateWizard : delegateWizards) {
            result = result && delegateWizard.performFinish(newProject);
        }
        return result;
    }

    /**
     * Performs any actions appropriate in response to the user having pressed the
     * Cancel button, or refuse if canceling now is not permitted.
     *
     * @return <code>true</code> to indicate the cancel request was accepted, and
     *         <code>false</code> to indicate that the cancel request was refused
     */
    public boolean performCancel() {
        boolean result = super.performCancel();
        for (WizardDelegate delegateWizard : delegateWizards) {
            result = result && delegateWizard.performCancel();
        }
        return result;
    }

    /*
     * Returns the list of available model compilers
     */
    public String[] getChoices() {
        IExtensionPoint extPt = Platform.getExtensionRegistry().getExtensionPoint(getExtensionPoint());
        if (extPt != null) {
            return Stream.of(extPt.getExtensions())
                    .map(extension -> extension.getConfigurationElements()[0].getAttribute("name")).sorted()
                    .collect(Collectors.toList()).toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

    /*
     * Sets the selected delegate wizard
     */
    public WizardDelegate addDelegate(String Name) {
        WizardDelegate delegateWizard = new WizardDelegate(getExtensionPoint(), Name);
        delegateWizard.init(m_workbench, m_selection);
        delegateWizards.add(delegateWizard);
        return delegateWizard;
    }

    /*
     * Sets the selected delegate wizard with a required toolset
     */
    public WizardDelegate addDelegate(String Name, String toolset) {
        WizardDelegate delegateWizard = new WizardDelegate(getExtensionPoint(), Name, toolset);
        delegateWizard.init(m_workbench, m_selection);
        delegateWizards.add(delegateWizard);
        return delegateWizard;
    }

    public void clearDelegates() {
        delegateWizards = new ArrayList<>();
    }

    /**
     * This can be called right after setDelegate to see if the delegate was set or
     * not.
     * 
     * @return The delegate help by this wizard or null if there is not one.
     */
    public List<IWorkbenchWizard> getDelegates() {
        return delegateWizards.stream().flatMap(delegate -> Stream.of(delegate.getDelegate()))
                .collect(Collectors.toList());
    }

}