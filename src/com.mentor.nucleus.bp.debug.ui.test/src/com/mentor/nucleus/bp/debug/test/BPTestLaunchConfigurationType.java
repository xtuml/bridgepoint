package com.mentor.nucleus.bp.debug.test;

import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputer;

public class BPTestLaunchConfigurationType implements ILaunchConfigurationType {

	public boolean supportsMode(String mode) {
        if (mode.equals("debug")) {
        	return true;
        }
		return false;
	}

	public String getName() {
		return "Verifier Test Configuration";
	}

	public String getIdentifier() {
		return "com.mentor.bp.verifier.configtype";
	}

	public boolean isPublic() {
		return false;
	}

	public ILaunchConfigurationWorkingCopy newInstance(IContainer container,
			String name) throws CoreException {
		return null;
	}

	public ILaunchConfigurationDelegate getDelegate() throws CoreException {
		return null;
	}

	public ILaunchConfigurationDelegate getDelegate(String mode)
			throws CoreException {
		return null;
	}

	public String getCategory() {
		return null;
	}

	public String getAttribute(String attributeName) {
		return null;
	}

	public ISourcePathComputer getSourcePathComputer() {
		return null;
	}

	public String getSourceLocatorId() {
		return null;
	}

	public String getPluginIdentifier() {
		return null;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#getContributorName()
     */
    public String getContributorName() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#getDelegates(java.util.Set)
     */
    public ILaunchDelegate[] getDelegates(Set modes) throws CoreException {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#getPreferredDelegate(java.util.Set)
     */
    public ILaunchDelegate getPreferredDelegate(Set modes) throws CoreException {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#getSupportedModeCombinations()
     */
    public Set getSupportedModeCombinations() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#getSupportedModes()
     */
    public Set getSupportedModes() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#setPreferredDelegate(java.util.Set, org.eclipse.debug.core.ILaunchDelegate)
     */
    public void setPreferredDelegate(Set modes, ILaunchDelegate delegate)
            throws CoreException {
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfigurationType#supportsModeCombination(java.util.Set)
     */
    public boolean supportsModeCombination(Set modes) {
        return false;
    }

}
