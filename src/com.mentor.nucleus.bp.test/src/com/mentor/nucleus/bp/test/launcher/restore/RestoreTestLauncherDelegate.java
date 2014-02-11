//========================================================================
//
//File:      $RCSfile: RestoreTestLauncherDelegate.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:21:33 $
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
package com.mentor.nucleus.bp.test.launcher.restore;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

import com.mentor.nucleus.bp.test.TestPlugin;

public class RestoreTestLauncherDelegate extends JavaLaunchDelegate implements
        ILaunchConfigurationDelegate {

    String junitConfigName;

    ILaunch restoreTestLaunch;

    ILaunchConfiguration restoreTestConfig;

    ILaunch juintLaunch;

    ILaunchConfiguration junitConfig = null;

    boolean isTerminated = false;

    private long startTime;

    public RestoreTestLauncherDelegate() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void launch(ILaunchConfiguration configuration, String mode,
            ILaunch launch, IProgressMonitor monitor) throws CoreException {

        ILaunchConfigurationWorkingCopy workingCopy = null;
        isTerminated = false;
        startTime = System.currentTimeMillis();
        RestoreDebugTarget target = new RestoreDebugTarget();
        launch.addDebugTarget(target);
        restoreTestLaunch = launch;
        restoreTestConfig = configuration;

        junitConfigName = configuration.getAttribute(
                IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "");

        if (junitConfigName.equals(""))
            reportErrorAndExit("Junit configuration not specified.");

        ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
                .getLaunchManager().getLaunchConfigurations();

        for (int i = 0; i < configurations.length; i++) {
            ILaunchConfiguration c = configurations[i];
            if (c.getName().equals(junitConfigName)) {
                junitConfig = c;
                break;
            }
        }
        if (junitConfig == null) {
            reportErrorAndExit("junit configuration : " + junitConfigName
                    + " not found.");

        }

        Vector v_methods = getTestMethods();
        boolean clearWS = true;

        RestoreTestResultLogger.start(v_methods.size()*2);

        for (int i = 0; i < v_methods.size(); i++) {
            IMethod method = (IMethod) v_methods.get(i);

            workingCopy = junitConfig.copy(method.getElementName());
            // dont clear work space after 1st run
            workingCopy.setAttribute("clearws", clearWS);
            clearWS = false;

            if (isTerminated)
                break;

            launch(workingCopy, mode, launch, monitor, method.getElementName());

        }// end for
        if (RestoreTestResultLogger.getDefault() != null) {
            RestoreTestResultLogger.getDefault().finished(
                    System.currentTimeMillis() - startTime);
        }
        isTerminated = true;
    }

    /**
     * @param message
     * @throws CoreException
     */
    private void reportErrorAndExit(String message) throws CoreException {
        Status status = new Status(IStatus.ERROR, "com.mentor.nucleus.bp.test", 0, message, null);
        TestPlugin.getDefault().getLog().log(status);
        throw new CoreException(status); //exit
    }

    private Vector getTestMethods() throws CoreException {
        Vector v_methods = new Vector();
        Map testCases = restoreTestConfig.getAttribute(
                "org.eclipse.debug.core.environmentVariables", new HashMap());
        int testCaseCount = 0;

        while (true) {
            String testCaseName = (String) testCases.get("testcase"
                    + testCaseCount);
            if (testCaseName == null) {
                if (testCaseCount == 0)
                    reportErrorAndExit("No testcase class specified in "
                            + "environment tab of configuration");
                break;
            }
            testCaseCount++;

            String projectName = junitConfig.getAttribute(
                    IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
            IJavaProject project = JavaCore.create(
                    ResourcesPlugin.getWorkspace().getRoot()).getJavaProject(
                    projectName);
            IType itype = project.findType(testCaseName);

            if (itype == null) {
                reportErrorAndExit("testcase not found: " + testCaseName);
            }
            IMethod[] methods = itype.getMethods();

            for (int i = 0; i < methods.length; i++) {
                IMethod method = methods[i];
                if (isTestMethod(method))
                    v_methods.add(method);
            }
            // TODO iterate type to get test methods from all super classes

        }
        return v_methods;
    }

    protected boolean isTestMethod(IMethod method) {
        try {
            return method.getParameterNames().length == 0
                    && method.getReturnType().equals("V")
                    && method.getElementName().startsWith("test");
        } catch (JavaModelException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void launch(ILaunchConfigurationWorkingCopy workingCopy,
            String mode, ILaunch launch, IProgressMonitor monitor,
            String testMethod) throws CoreException {

        String workingCopyName = workingCopy.getName();
        // name can't be set so we have to create a new copy
        workingCopy = workingCopy.copy(workingCopyName + "_Setup");

        String vmArg = workingCopy.getAttribute("vmargs", "");
        vmArg += " -DTestCaseName=" + testMethod;
        String newVmArg = vmArg + " -DSETUP_WORKSPACE=True";

        workingCopy.setAttribute("vmargs", newVmArg);
        if (isTerminated)
            return;
        
        RestoreTestResultLogger.setInSetupWorkspace(true);
        juintLaunch = workingCopy.launch(mode, monitor, true);
        while (!juintLaunch.isTerminated() && !isTerminated) {
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        workingCopy = workingCopy.copy(workingCopyName + "_Run");
        newVmArg = vmArg + " -DSETUP_WORKSPACE=False";
        workingCopy.setAttribute("clearws", false);
        workingCopy.setAttribute("vmargs", newVmArg);
        // run actual restore test
        if (isTerminated)
            return;
        
        RestoreTestResultLogger.setInSetupWorkspace(false);
        juintLaunch = workingCopy.launch(mode, monitor, true);

        while (!juintLaunch.isTerminated() && !isTerminated) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class RestoreDebugTarget implements IDebugTarget {

        public RestoreDebugTarget() {
            super();
            // TODO Auto-generated constructor stub
        }

        public IProcess getProcess() {
            // TODO Auto-generated method stub
            return null;
        }

        public IThread[] getThreads() throws DebugException {
            // TODO Auto-generated method stub
            return new IThread[0];
        }

        public boolean hasThreads() throws DebugException {
            // TODO Auto-generated method stub
            return false;
        }

        public String getName() throws DebugException {
            // TODO Auto-generated method stub
            return "Restore Tests";
        }

        public boolean supportsBreakpoint(IBreakpoint breakpoint) {
            // TODO Auto-generated method stub
            return false;
        }

        public String getModelIdentifier() {
            // TODO Auto-generated method stub
            return null;
        }

        public IDebugTarget getDebugTarget() {
            // TODO Auto-generated method stub
            return this;
        }

        public ILaunch getLaunch() {
            // TODO Auto-generated method stub
            return restoreTestLaunch;
        }

        public Object getAdapter(Class adapter) {
            // TODO Auto-generated method stub
            return null;
        }

        public boolean canTerminate() {
            // TODO Auto-generated method stub
            return !isTerminated;
        }

        public boolean isTerminated() {
            // TODO Auto-generated method stub
            return isTerminated;
        }

        public void terminate() throws DebugException {
            isTerminated = true;
            if (juintLaunch != null)
                juintLaunch.terminate();

        }

        public boolean canResume() {
            // TODO Auto-generated method stub
            return false;
        }

        public boolean canSuspend() {
            // TODO Auto-generated method stub
            return false;
        }

        public boolean isSuspended() {
            // TODO Auto-generated method stub
            return false;
        }

        public void resume() throws DebugException {
            // TODO Auto-generated method stub

        }

        public void suspend() throws DebugException {
            // TODO Auto-generated method stub

        }

        public void breakpointAdded(IBreakpoint breakpoint) {
            // TODO Auto-generated method stub

        }

        public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
            // TODO Auto-generated method stub

        }

        public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
            // TODO Auto-generated method stub

        }

        public boolean canDisconnect() {
            // TODO Auto-generated method stub
            return false;
        }

        public void disconnect() throws DebugException {
            // TODO Auto-generated method stub

        }

        public boolean isDisconnected() {
            // TODO Auto-generated method stub
            return false;
        }

        public boolean supportsStorageRetrieval() {
            // TODO Auto-generated method stub
            return false;
        }

        public IMemoryBlock getMemoryBlock(long startAddress, long length)
                throws DebugException {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
