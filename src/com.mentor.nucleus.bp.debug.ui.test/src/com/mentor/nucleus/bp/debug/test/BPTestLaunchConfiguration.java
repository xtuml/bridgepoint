package com.mentor.nucleus.bp.debug.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchConfiguration;

public class BPTestLaunchConfiguration implements ILaunchConfiguration {
	
	private String m_testProjectName;
	private Vector m_testModelNames;
	private Vector<Ooaofooa> m_modelRoots;
	
	public BPTestLaunchConfiguration(String projName, Vector mdlNames, Vector modelRoots) {
		m_testProjectName = projName;
		m_testModelNames = mdlNames;
		m_modelRoots = modelRoots;
	}

	public ILaunch launch(String mode, IProgressMonitor monitor)
			throws CoreException {
		return launch(mode, monitor, false);
	}

	public ILaunch launch(String mode, IProgressMonitor monitor, boolean build)
			throws CoreException {
		return launch(mode, monitor, build, true);
	}

	public ILaunch launch(String mode, IProgressMonitor monitor, boolean build,
			boolean register) throws CoreException {
		return null;
	}

	public boolean supportsMode(String mode) throws CoreException {
		if (mode.equals("debug")) {
		  return true;
		}
		else {
		  return false;
		}
	}

	public String getName() {
		return "BPTestLaunchConfiguration";
	}

	public IPath getLocation() {
		// We don't need a launch file on disk . . . . 
		return null;
	}

	public boolean exists() {
		// . . . . so the file does not exist
		return false;
	}

	public int getAttribute(String attributeName, int defaultValue)
			throws CoreException {
		return 0;
	}

	public String getAttribute(String attributeName, String defaultValue)
			throws CoreException {
		if (attributeName.equals("com.mentor.nucleus.bp.debug.ui.BPApplication.package")) {
			return m_testProjectName;
		}
		else {
			return null;
		}
	}

	public boolean getAttribute(String attributeName, boolean defaultValue)
			throws CoreException {
		if (attributeName.equals("com.mentor.nucleus.bp.debug.ui.BPApplication.activity") ) {
			return false;
		}
		else
			return false;
	}

	public List getAttribute(String attributeName, List defaultValue)
			throws CoreException {
		return null;
	}

	public Map getAttribute(String attributeName, Map defaultValue)
			throws CoreException {
		Map result = new HashMap();
		Vector<String> modelList = new Vector<String>();
		Iterator it = m_testModelNames.iterator();
		while(it.hasNext()) {
			final String name = (String) it.next(); 
			// get the domain from the model root with
			// the given name
			Ooaofooa modelRoot = getMatchingModelRoot(name);
			Component_c component = Component_c.ComponentInstance(modelRoot, new ClassQueryInterface_c() {
			
				public boolean evaluate(Object candidate) {
					return ((Component_c)candidate).getName().equals(name);
				}
			
			});
			if(IdAssigner.isUUIDDummy(component.getId())) {
				UUID newId = UUID.randomUUID();
				component.updateInstanceKey(component.getInstanceKey(),
						new Object[] { newId });
				component.setId(newId);
			}
			
			modelList.add(VerifierLaunchConfiguration.getComponentSelectionString(component.getId().toString()));
		}
		result.put(m_testProjectName, VerifierLaunchConfiguration.convertComponentSelectionVectorToString(modelList));
		return result;
	}

	private Ooaofooa getMatchingModelRoot(String domainName) {
		Iterator iterator = m_modelRoots.iterator();
		Ooaofooa root = null;
		while(iterator.hasNext()) {
			Ooaofooa currentRoot = (Ooaofooa) iterator.next();
			if(currentRoot.getId().indexOf(domainName) != -1) {
				root = currentRoot;
				break;
			}
		}
		return root;
	}

	public IFile getFile() {
		// File doesn't exist . . . 
		return null;
	}

	public ILaunchConfigurationType getType() throws CoreException {
		return new BPTestLaunchConfigurationType();
	}

	public boolean isLocal() {
		return false;
	}

	public ILaunchConfigurationWorkingCopy getWorkingCopy()
			throws CoreException {
		return null;
	}

	public ILaunchConfigurationWorkingCopy copy(String name)
			throws CoreException {
		return null;
	}

	public boolean isWorkingCopy() {
		return false;
	}

	public void delete() throws CoreException {
	}

	public String getMemento() throws CoreException {
		return null;
	}

	public boolean contentsEqual(ILaunchConfiguration configuration) {
		return false;
	}

	public String getCategory() throws CoreException {
		return null;
	}

	public Map getAttributes() throws CoreException {
		return null;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#getPreferredDelegate(java.util.Set)
     */
    public ILaunchDelegate getPreferredDelegate(Set modes) throws CoreException {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#getAttribute(java.lang.String, java.util.Set)
     */
    public Set getAttribute(String attributeName, Set defaultValue)
            throws CoreException {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#getMappedResources()
     */
    public IResource[] getMappedResources() throws CoreException {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#getModes()
     */
    public Set getModes() throws CoreException {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#isMigrationCandidate()
     */
    public boolean isMigrationCandidate() throws CoreException {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#isReadOnly()
     */
    public boolean isReadOnly() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchConfiguration#migrate()
     */
    public void migrate() throws CoreException {
    }

    @Override
    public boolean hasAttribute(String attributeName) throws CoreException {
        return false;
    }
}
