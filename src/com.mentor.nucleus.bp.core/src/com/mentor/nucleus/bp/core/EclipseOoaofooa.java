//=====================================================================
//
//File:      $RCSfile: EclipseOoaofooa.java,v $
//Version:   $Revision: 1.32 $
//Modified:  $Date: 2013/05/10 13:26:44 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelChangeListener;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.core.util.PlaceHolderUtil;

/**
 * Holds all the Eclipse-specific, Java-only methods of Ooaofooa.  
 * All instances of this class must also be instances of Ooaofooa.  
 * Externally, other classes should only know about Ooaofooa, and not 
 * this class.  Call this class's thisAsOoaofooa() method as a 
 * convenient way to cast an instance of this class to an Ooaofooa.   
 */
public class EclipseOoaofooa extends OoaofooaBase {
    /**
     * Whether we are running a unit test.
     * The ui.text.test needs to know this in order to prevent 
     * ResourceExceptions due to resources being locked.
     */
    private static boolean inUnitTest = false;

    /**
     * Whether calls to this class's persist() method should actually persist the model.
     * This is turned off for most unit tests, as persisting the model after each 
     * change slows the tests' execution, without providing much extra testing value.
     */
    private static boolean persistEnabled = true;
    
    /**
     * The 'create new' Action Tests test only that the new model element is created
     * as expected.  The may not (and usually don't) result in a completely consistent
     * model.  Similar to 'persistEnabled', 'consistencyEnabled' will provide a way
     * to disable consistency checking during unit tests.  The unit tests must then
     * call consistency checking for each model element actually under test.
     */
    
    private static boolean consistencyEnabled = true;

    public static volatile Display m_display = null;

    /**
     * Constructor 
     */
    protected EclipseOoaofooa(String aRootId) 
    {
        super(aRootId);

        if (!CoreUtil.IsRunningHeadless && isFileBasedID(aRootId)) {
            PlaceHolderUtil.initPlaceholderManager();
        }
    }

    public static Ooaofooa getInstance(String id, boolean forceLoad) 
    {
        Ooaofooa modelRoot = Ooaofooa.getInstance(id);

        if (forceLoad && isFileBasedID(id) && !modelRoot.isLoaded()) {
            String projectName = getProjectNameFromModelRootId(id);
            String fileName = getDomainFileNameFromModelRootId(id);
            IProject[] project_set = CorePlugin.getWorkspace().getRoot().getProjects();
            IFile content = null;
            for (int i = 0; i < project_set.length; ++i) {
                if (XtUMLNature.hasNature(project_set[i])) {
                    if (project_set[i].getName().equals(projectName)) {
                        IResource mdl_folder = project_set[i].findMember(MODELS_DIRNAME);
                        IResource[] mdl_content = null;
                        try {
                            mdl_content = ((IFolder) mdl_folder).members();
                        } catch (Exception e) {
                            CorePlugin.logError("Problem accessing workspace ", e);
                        }
                        if (mdl_content != null) {
                            for (int j = 0; j < mdl_content.length; ++j) {
                                if (mdl_content[j] instanceof IFile) {
                                    IFile selected = (IFile) mdl_content[j];
                                    if (selected.getName().equals(fileName)) {
                                        content = selected;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (content != null) {
                modelRoot = getInstance(content, true);
                if (!modelRoot.loadedOk()) {
                    modelRoot.delete();
                    modelRoot = null;
                }
            }
        }

        return modelRoot;
    }

    /**
     * Returns the model-root associated with the content of the
     * given file.
     */
    public static Ooaofooa getInstance(IFile file, boolean forceLoad) 
    {
    	boolean addExtension = false;
    	String domainName = file.getName();
    	IPath filePath = file.getFullPath();
    	if(filePath.segmentCount() > 5) {
    		domainName = filePath.removeLastSegments(filePath.segmentCount() - 4).lastSegment();
    		addExtension = true;
    	}
    	
        final String id = createModelRootId(file.getProject(), domainName, addExtension);
        Ooaofooa modelRoot = (Ooaofooa) rootInstanceMap.get(id);
        if (modelRoot == null) {
            modelRoot = getInstance(id, false);
        }

        if (forceLoad && !modelRoot.isLoaded()) {
            modelRoot = loadInstanceFromFile(file);
            }
        return modelRoot;
    }

    public static IPath getResourcePathFromModelRootId(String id) 
    {
        if (!isFileBasedID(id)) {
            Ooaofooa modelRoot = (Ooaofooa) rootInstanceMap.get(id);
            if (modelRoot != null) {
                return modelRoot.getResourcePath();
            }
            return null;
        }

        String projectName = getProjectNameFromModelRootId(id);

        IPath path = new Path(projectName + "/" + MODELS_DIRNAME); //$$NON-NLS-1$$
        File file = path.toFile();

        try {
            path = new Path(file.getCanonicalPath());
        } catch (IOException e) {
            path = null;
        }

        return path;
    }

    /**
     * See similar method wrapped by this one.  The given project's
     * name is substituted for the system-name.
     */
    public static String createModelRootId(IProject project, String domainName, boolean addExtension) {
        PersistenceManager manager = PersistenceManager.getDefaultInstance();
        PersistableModelComponent root = manager.getRootComponent(project);
        String systemName = project.getName();
        if(root != null){
            systemName = root.getName();
    }

        return createModelRootId(systemName, domainName, addExtension);
    }

    public void delete() 
    {
        // if this is a default model-root (and thus has no domain segment)
        if (new Path(rootId).segmentCount() <= 1) {
            // don't allow the deletion; in particular, legacy tests
            // modify the default test root and expect it to never go 
            // away
            return;
        }

        super.delete();
        }

    private IPath m_resourceFileForTests = null;

    public IPath getResourcePath() 
    {
        if (m_resourceFileForTests != null) {
            return m_resourceFileForTests.removeLastSegments(1);
        }
        SystemModel_c parent = getRoot();
        if (parent != null) {
            IResource pr = (IResource) parent.getAdapter(IResource.class);
            if (pr != null) {
                if ( pr.getFullPath().segmentCount() == 1 )
                {
                    // this is for single file domains in test cases
                    // TODO Remove when test cases are converted to multi-file
                IPath loc = pr.getLocation();
                return loc.append(MODELS_DIRNAME);
            }
                else
                {
                    IPath loc = pr.getLocation();
                    return loc.removeLastSegments(1);
        }
            }
        }
        return null;
    }

    public void setLoadPathForTests(String fileName) {
        // TODO this is here temporarily, as this method gets
        // called at some point by most tests; it should be moved
        // into BaseTest when all the tests are refactored to 
        // extend from that class
        persistEnabled = false;

        IPath x = new Path(fileName);
        File f = x.toFile();
        try {
            m_resourceFileForTests = new Path(f.getCanonicalPath());
        } catch (IOException io_except) {
            m_resourceFileForTests = null;
        }
    }

    public void clearDatabase(IProgressMonitor pm) 
    {
        // clear the corresponding graphics-root's database
        OoaofgraphicsUtil.clearGraphicsDatabase(rootId, pm);

        Ooaofooa.getDefaultInstance().fireModelElementUnloaded(this);
    }

    protected void callFireMethod(Runnable runnable) 
    {
      // always notify synchronous listeners
      Object[] listeners = getModelChangedListeners().getListeners();      
      for (int i = 0; i < listeners.length; ++i) {
        try {
          IModelChangeListener listener = (IModelChangeListener)listeners[i];
          if ((isChangeNotificationEnabled() || !listener.isMaskable()) &&
                  listener.isSynchronous()) {
            super.callFireMethodSynchronously(runnable, listener);
          }
        } catch (Throwable e) {
          logError("error while dispatching model change event", e);
        }
      }
      if (isChangeNotificationEnabled()) {
  		if (runnable instanceof ListenerMethodInvoker) {
			((ModelChangedEvent)((ListenerMethodInvoker)runnable).getEvent()).setStackTrace();
		}
        Display d = Display.getCurrent();
        if (d != null || CoreUtil.IsRunningHeadless) {
            // it's ok, we're in the UI thread
            runnable.run();
        } else {
            if (m_display != null && !m_display.isDisposed()) {
                m_display.asyncExec(runnable);
            } else {
	                // fire the change on the default UI-thread (having one
	                // created, if none currently exists), when there's no
	                // other choice, such as when this model-root's
	                // resource-change-listener gets notified of a change
	                // made to the model's file in the filesystem (as such
	                // notification doesn't occur on the UI-thread)
	                Display.getDefault().asyncExec(runnable);
            }
        }
      }
    }

	/**
     * Returns the file (if any) in which the contents of the model
     * associated with this model-root are persisted.
     */
    public IFile getFile() {
        if (!isFileBasedID(rootId)) {
            return null;
        }
        IPath rootIdPath = new Path(rootId);
        String projectName = rootIdPath.uptoSegment(1).lastSegment();
        String domainName = rootIdPath.removeFileExtension().lastSegment();
        
        IPath filePath = rootIdPath.uptoSegment(1).append("/" + MODELS_DIRNAME 
                + "/" + projectName 
                + "/" + domainName 
                + "/" + rootIdPath.lastSegment());
        return CorePlugin.getWorkspace().getRoot().getFile(filePath);
    }

    public String loadErrorMessage = ""; //$$NON-NLS-1$$
    public boolean loadedOk() {
        return loadErrorMessage.equals(""); //$$NON-NLS-1$$
    }

    /**
     * Loads (and returns) the instance of this class from the given 
     * file to which it previously had been persisted.
     */
    private static Ooaofooa loadInstanceFromFile(IFile file) {
        // find the system-model that corresponds to the project
        // containing the given file
        final String projectName = file.getProject().getName();
        final SystemModel_c system = SystemModel_c.SystemModelInstances(
            Ooaofooa.getDefaultInstance(),
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    SystemModel_c selected = (SystemModel_c) candidate;
                    return selected.getName().equals(projectName);
                }
            })[0];

        // open an input-stream on the given file
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(file.getLocation().toString());
        } catch (FileNotFoundException e) {
            CorePlugin.logError("Internal error: failed to open " + file.getLocation().toString(), e);//$$NON-NLS-1$$
            return null;
        }

        // detm the instance's ID from the file's path
        final String id = createModelRootId(file.getProject(), file.getName(), false);

        // the enclosed could take a bit of time, so put up a busy indicator
        final InputStream finalInStream = inStream;
        BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {
            public void run() {
                // load the instance from the stream and have it be associated
                // with the system-model found above
                String result = CorePlugin.getDefault().doLoad(
                    system,
                    id,
                    finalInStream,
                    new NullProgressMonitor(),
                    false, false);
                if (!result.equals("")) {
                    Ooaofooa modelRoot = getInstance(id, false);
                    modelRoot.loadErrorMessage = result;
                }
            }
        });

        // close the input-stream from which the model was loaded
        try {
            inStream.close();
        } catch (IOException e) {
            CorePlugin.logError("Error closing stream after import", e);
        }

        // make sure the domain and file name are in sync
        Ooaofooa modelRoot = getInstance(id, false);
        if (modelRoot.loadedOk()) {
            Domain_c dom = Domain_c.DomainInstance(modelRoot);
            String fn = file.getName();
            String domName = fn.substring(0, fn.length() - MODELS_EXT.length() - 1);
            
            ModelRoot.disableChangeNotification();
            try {
            dom.setName(domName);
            }
            finally {
            ModelRoot.enableChangeNotification();
        }
        }

        // return the loaded instance 
        return modelRoot;
    }

    /** 
     * See field.
     */
    public static void setPersistEnabled(boolean persist) {
        persistEnabled = persist;
    }

    public boolean persistEnabled() {
        return persistEnabled;
    }

    public static void setConsistencyEnabled(boolean consistency) {
        consistencyEnabled = consistency;
    }

    public static boolean getConsistencyEnabled() {
        return consistencyEnabled;
    }
    
    /**
     * See field.
     */
    public static void setInUnitTest(boolean inTest) {
        inUnitTest = inTest;
    }

    public static boolean inUnitTest() {
        return inUnitTest;
    }

    protected void logError(String msg, Exception e){
        CorePlugin.logError(msg, e);
    }

    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.core.common.ModelRoot#getPersistenceFile()
     */
    public Object getPersistenceFile()
    {
        return getFile();
    }

    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.core.common.ModelRoot#isPersisting()
     */
    public boolean isPersisting()
    {
        IFile file = getFile();
        //file can be null in case of default model root and default test model
        //root.
        //file may not exist if component is yet only created.
        if(file == null || !file.exists()){
            return false;
        }
        
        PersistableModelComponent component = PersistenceManager.findComponent(getFile().getFullPath());
        
        return component.isPersisting();
    }
    
    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.core.common.ModelRoot#dispatchPlatformEvents()
     */
    protected void dispatchPlatformEvents()
    {
        while (Display.getCurrent().readAndDispatch());        
    }
    
    public static Ooaofooa findInstance(String id) {
    	return (Ooaofooa) rootInstanceMap.get(id);
    }

	public static void addInstance(ModelRoot modelRoot) {
		rootInstanceMap.put(modelRoot.getId(), (OoaofooaBase) modelRoot);
	}
}