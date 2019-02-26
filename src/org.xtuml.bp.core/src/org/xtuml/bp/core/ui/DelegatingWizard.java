package org.xtuml.bp.core.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.CorePlugin;

public abstract class DelegatingWizard extends Wizard implements IWizard {

    private Map<String, IDelegateWizard> delegateWizards = new HashMap<>();
    private Map<String, Boolean> wizardEnablements = new HashMap<>();

    public DelegatingWizard() {
        IExtensionPoint extPt = Platform.getExtensionRegistry().getExtensionPoint(getExtensionPoint());
        if (extPt != null) {
            for (IExtension extension : extPt.getExtensions()) {
                for (IConfigurationElement element : extension.getConfigurationElements()) {
                    String delegateName = element.getAttribute("name"); //$NON-NLS-1$
                    String className = element.getAttribute("wizard-class"); //$NON-NLS-1$
                    if (className != null) {
                        String bundleID = extension.getNamespaceIdentifier();
                        Bundle bundle = Platform.getBundle(bundleID);
                        if (bundle != null) {
                            try {
                                Class<?> wizardClass = bundle.loadClass(className);
                                IDelegateWizard wizard = (IDelegateWizard) wizardClass.newInstance();
                                for (IWizardPage page : wizard.getPages()) {
                                    page.setWizard(this);
                                }
                                delegateWizards.put(delegateName, wizard);
                            } catch (Throwable e) {
                                CorePlugin.logError("Unable to initialize Model Compiler:", e);
                            }
                        } else {
                            CorePlugin.logError("Bundle not found for client package", null);
                        }
                    } else {
                        CorePlugin.logError("Class name attribute not supplied in code builder declaration", null);
                    }
                }
            }
        }
        clearEnabledWizards();
    }

    public abstract String getExtensionPoint();

    public String[] getChoices() {
        return delegateWizards.keySet().stream().sorted().collect(Collectors.toList()).toArray(new String[0]);
    }

    public IDelegateWizard setDelegateWizardEnabled(String choice, boolean enabled) {
        wizardEnablements.put(choice, enabled);
        return delegateWizards.get(choice);
    }

    public void clearEnabledWizards() {
        wizardEnablements = new HashMap<>();
        for (String choice : getChoices()) {
            wizardEnablements.put(choice, false);
        }
    }

    public Collection<IDelegateWizard> getDelegateWizards() {
        return delegateWizards.values();
    }

    private List<IDelegateWizard> getEnabledWizards() {
        return delegateWizards.entrySet().stream().filter(entry -> wizardEnablements.get(entry.getKey()))
                .sorted((a, b) -> a.getKey().compareTo(b.getKey())).map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        IWizardPage next = null;
        IWizardPage[] pages = getPages();
        for (int i = 0; i < pages.length; i++) {
            if (i > 0 && pages[i - 1].equals(page)) {
                next = pages[i];
            }
        }
        return next;
    }

    @Override
    public IWizardPage getPage(String name) {
        for (IWizardPage page : getPages()) {
            if (page.getName().equals(name)) {
                return page;
            }
        }
        return null;
    }

    @Override
    public int getPageCount() {
        return getPages().length;
    }

    @Override
    public IWizardPage[] getPages() {
        return Stream
                .concat(Stream.of(super.getPages()),
                        getEnabledWizards().stream().flatMap(wizard -> Stream.of(wizard.getPages())))
                .collect(Collectors.toList()).toArray(new IWizardPage[0]);
    }

    @Override
    public IWizardPage getPreviousPage(IWizardPage page) {
        IWizardPage prev = null;
        IWizardPage[] pages = getPages();
        for (int i = pages.length - 1; i >= 0; i--) {
            if (i < pages.length - 1 && pages[i + 1].equals(page)) {
                prev = pages[i];
            }
        }
        return prev;
    }

    @Override
    public IWizardPage getStartingPage() {
        IWizardPage startingPage = super.getStartingPage();
        if (null == startingPage && getEnabledWizards().size() > 0) {
            startingPage = getEnabledWizards().iterator().next().getStartingPage();
        }
        return startingPage;
    }

    @Override
    public boolean performFinish() {
        boolean result = true;
        for (IDelegateWizard wizard : getEnabledWizards()) {
            result = result && wizard.performFinish();
        }
        return result;
    }

    @Override
    public boolean performCancel() {
        boolean result = super.performCancel();
        for (IDelegateWizard wizard : getEnabledWizards()) {
            result = result && wizard.performCancel();
        }
        return result;
    }

    @Override
    public boolean canFinish() {
        for (IWizardPage page : getPages()) {
            if (!page.isPageComplete()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean needsPreviousAndNextButtons() {
        return true; // Always return true because delegate wizards may add pages later
    }

}