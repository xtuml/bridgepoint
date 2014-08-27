//=====================================================================
//
//File:      $RCSfile: TestUtil.java,v $
//Version:   $Revision: 1.38 $
//Modified:  $Date: 2013/05/10 05:37:49 $
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

package com.mentor.nucleus.bp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.progress.BlockedJobsDialog;

import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.NewDomainWizard;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.WizardNewDomainCreationPage;
import com.mentor.nucleus.bp.core.ui.dialogs.ElementSelectionDialog;
import com.mentor.nucleus.bp.core.ui.dialogs.ElementSelectionFlatView;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.FailableRunnable;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains utility methods related to automated testing of BridgePoint.
 */
public class TestUtil
{
    /**
     * This is used to store the text of a dialog just before it is dismissed.
     * It allows us to validate that the text that was in the dialog is what we expected.
     */
    public static String dialogText;
    
    /**
     * Asynchronously waits for the given duration, during which time the caller is expected
     * to cause a (likely modal) dialog to be displayed, then dismisses that dialog if it 
     * is indeed displayed.  If it's not displayed, another attempt will be made through
     * a recursive call, up to a certain number of attempts (in case the dialog is never
     * shown). 
     */
    public static void dismissDialog(final long inHowManyMillis)
    {
        dismissDialog(inHowManyMillis, 0, true);
    }

    /**
     * Cancels (via the Cancel button) a dialog after waiting for the given time 
     */
    public static void cancelDialog(final long inHowManyMillis)
    {
        dismissDialog(inHowManyMillis, 0, false);
    }
    
    /**
     * Answers no (via the No button) a dialog after waiting for the given time 
     */
    public static void noToDialog(final long inHowManyMillis)
    {
        dismissDialog(inHowManyMillis, 0, false, "&No", null, true);
    }

    /**
     * Answers yes (via the Yes button) a dialog after waiting for the given time 
     */
    public static void yesToDialog(final long inHowManyMillis)
    {
        dismissDialog(inHowManyMillis, 0, false, "&Yes", null, true);
    }
    
    /**
     * Answers OK (via the OK button) a dialog after waiting for the given time 
     */
    public static void okToDialog(final long inHowManyMillis)
    {
        dismissDialog(inHowManyMillis, 0, false, "OK", null, true);
    }

    public static void okToDialog(final long inHowManyMillis, boolean throwException)
    {
        dismissDialog(inHowManyMillis, 0, false, "OK", null, throwException);
    }

    /**
     * Selects the Next button in the active dialog.
     */
    public static void nextToDialog(final long inHowManyMillis) {
    	dismissDialog(inHowManyMillis, 0, false, "&Next >", null, false);
    }
    
    /**
     * Selects the Finish button in the active dialog
     */
    public static void finishToDialog(final long inHowManyMillis) {
    	dismissDialog(inHowManyMillis, 0, false, "&Finish", null, false);
    }   
    
    /**
     * Selects the Finish button in the active dialog
     */
    public static void mergeToDialog(final long inHowManyMillis) {
    	dismissDialog(inHowManyMillis, 0, false, "&Merge", null, false);
    }       
    
    /**
     * Selects a tree item in a dialog containing a tree
     */
    public static void selectItemInTreeDialog(final long inHowManyMillis, String treeItem) {
    	dismissDialog(inHowManyMillis, 0, false, null, treeItem, false);
    }
    
    /**
     * Presses Debug in the dialog
     */
    public static void debugToDialog(long inHowManyMillis) {
        dismissDialog(inHowManyMillis, 0, false, "&Debug", null, false);
    }

    /**
     * Select the button on the dialog with the given text 
     */
    public static void selectButtonInDialog(final long inHowManyMillis, String buttonName)
    {
        dismissDialog(inHowManyMillis, 0, false, buttonName, null, true);
    }

    /**
     * Select the button on the dialog with the given text 
     */
    public static void selectButtonInDialog(final long inHowManyMillis, String buttonName, boolean throwException)
    {
        dismissDialog(inHowManyMillis, 0, false, buttonName, null, throwException);
    }
    
    private static void dismissDialog(final long inHowManyMillis, 
            final int currentRecursionDepth, final boolean shouldDismiss) {
        dismissDialog(inHowManyMillis, currentRecursionDepth, shouldDismiss, null, null, true);
    }
    
    /**
     * See shorter signature method.
     */
    public static void dismissDialog(final long inHowManyMillis, 
            final int currentRecursionDepth, final boolean shouldDismiss, final String button, final String treeItem, final boolean throwException)
    {
        // run this on a separate thread, so that the dialog invocation to be performed
        // by the caller may occur
        final int maxRecursionDepth = 10;
        Thread dismissThread = new Thread(new Runnable() {
        
            @Override
            public void run() {

                // wait to give the expected modal dialog time to get displayed 
                sleep(inHowManyMillis);
                dialogText = "";
                
                // if the currently active shell is a dialog
                if(PlatformUI.getWorkbench().getDisplay().isDisposed()) {
                	return;
                }
                
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                
                    @Override
                    public void run() {
                        Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
                        if (shell != null && shell.getData() instanceof Dialog) {
                            // close the dialog
                            if (!(shell.getData() instanceof ProgressMonitorDialog)
                                    && !(shell.getData() instanceof BlockedJobsDialog)) {
                                Control[] ctrls = ((Dialog)shell.getData()).getShell().getChildren();
                                for (int i = 0; i< ctrls.length; i++) {
                                    Control ctrl = ctrls[i];
                                    
                                    if (ctrl instanceof Label) {
                                        dialogText = dialogText + ((Label)ctrl).getText();
                                    }
                                }
                                if ( shouldDismiss )
                                {
                                    ((Dialog)shell.getData()).close();
                                }
                                else if ( button != null )
                                {
                                    Button foundButton = findButton(shell, button);
                                    if(foundButton != null) {
	                                    foundButton.setSelection(true);
	                                    foundButton.notifyListeners(SWT.Selection, null);
                                    }
                                }
                                else if ( treeItem != null ) {
									Tree tree = UITestingUtilities.findTree(shell);
									if(tree != null) {
										TreeItem item = UITestingUtilities
												.findItemInTree(tree, treeItem);
										if(item != null) {
											tree.select(item);
										} else {
											CorePlugin.logError(
													"Unable to locate tree item in tree: "
															+ treeItem, null);
										}
									} else {
										CorePlugin
												.logError(
														"Unable to locate a tree in the dialog.",
														null);
									}
                            	} else {
                                   cancelDialog( (Dialog)shell.getData() );   
                                }
                            } else {
                                dismissDialog(inHowManyMillis, currentRecursionDepth + 1, shouldDismiss, button, treeItem, throwException);
                            }
                            
                        }
                        // otherwise, call this recursively, so that further attempts 
                        // may be made, with each one allowing the UI-thread to take
                        // back control for awhile to get the dialog shown
                        else if (currentRecursionDepth <= maxRecursionDepth) {
                            Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
                            for (int i = 0; i < shells.length; i++) {
                                if (shells[i].getData() instanceof LaunchConfigurationsDialog) {
                                    shells[i].forceActive();
                                }
                                else if (shells[i].getData() instanceof MessageDialog) {
                                    if (((MessageDialog)shells[i].getData()).getShell().getText().equals("Upgrade model to global data types")) {
                                        shells[i].forceActive();
                                    }
                                }
                            }
                            dismissDialog(inHowManyMillis, currentRecursionDepth + 1, shouldDismiss, button, treeItem, throwException);
                        }
                        else {
                        	if(throwException) {
	                            Throwable t = new Throwable();
	                            t.setStackTrace(Thread.currentThread().getStackTrace());
	                            CorePlugin.logError("Failed to dismiss dialog", t);
                        	}
                        }
                    }
                });
            }
        });
        dismissThread.start();
    }

	//
    public static void checkTableItems(final long inHowManyMillis, 
            final int currentRecursionDepth, final boolean shouldDismiss, final String actualResultFilePath )
    {
        // run this on a separate thread, so that the dialog invocation to be performed
        // by the caller may occur
        final int maxRecursionDepth = 10;
        Thread dismissThread = new Thread(new Runnable() {
        
            @Override
            public void run() {

                // wait to give the expected modal dialog time to get displayed 
                sleep(inHowManyMillis);
                dialogText = "";
                
                // if the currently active shell is a dialog 
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                
                    @Override
                    public void run() {
                        Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
                        if (shell != null && shell.getData() instanceof Dialog) {
                            // close the dialog
                            if (!(shell.getData() instanceof ProgressMonitorDialog)) {
                                Control[] ctrls = ((Dialog) shell.getData())
                                        .getShell().getChildren();
                                for (int i = 0; i < ctrls.length; i++) {
                                    Control ctrl = ctrls[i];

                                    if (ctrl instanceof Label) {
                                        dialogText = dialogText + ((Label) ctrl).getText();
                                    }
                                }
                                if (shouldDismiss) {
                                    ((Dialog) shell.getData()).close();
                                } else {
                                    TableItem[] items = getTableItems(shell);
                                    if (items.length > 0) {
                                        String[] actualFirstColumn = new String[items.length];
                                        String[] actualSecondColumn = new String[items.length];

                                        for (int i = 0; i < items.length; i++) {
                                            actualFirstColumn[i] = items[i].getText(0);
                                            actualSecondColumn[i] = items[i].getText(1);
//                                          Assert.assertEquals(firstColumn[i], actualFirstColumn[i]);
//                                          Assert.assertEquals(secondColumn[i], actualSecondColumn[i]);
                                        }
                                        String output = createTable(actualFirstColumn, actualSecondColumn);
                                         try {
                                            FileWriter writer = new FileWriter(actualResultFilePath);
                                            writer.write(output);
                                            writer.flush();
                                        } catch (IOException e) {
                                            
                                            e.printStackTrace();
                                        }
                                        
                                        
                                    }
                                    okToDialog(0);
                                }
                            } else {
                                checkTableItems(inHowManyMillis, currentRecursionDepth + 1, shouldDismiss, actualResultFilePath);
                            }
                            
                        }
                        // otherwise, call this recursively, so that further attempts 
                        // may be made, with each one allowing the UI-thread to take
                        // back control for awhile to get the dialog shown
                        else if (currentRecursionDepth <= maxRecursionDepth) {
                            checkTableItems(inHowManyMillis, currentRecursionDepth + 1, shouldDismiss, actualResultFilePath);
                        }
                    }

                    
                });
            }
        });
        dismissThread.start();
    }

    private static String createTable(String[] firstColumn,
            String[] secondColumn) {
        int shorterLenght;
        int difference = firstColumn.length - secondColumn.length;
        
        if ( difference > 0){
            shorterLenght =  secondColumn.length;
        }
        else{
            shorterLenght =  firstColumn.length;
        }
        String result = "";
        int i = 0;
        for ( ; i < shorterLenght; i++) {
            result += firstColumn[i] + "\t" + secondColumn[i] + "\r\n";
        }
        
        return result;
    }
    //
    private static void cancelDialog(Dialog dialog) {
        Control bb = dialog.buttonBar;
        Button cb = findButton(bb.getParent(), "Cancel");
        cb.notifyListeners(SWT.Selection, null);
    }

    public static Button findButton(Composite parent, String buttonName) {
        Control [] child_set = parent.getChildren();
        for ( int i = 0; i < child_set.length; ++i )
        {
            if ( child_set[i] instanceof Button )
            {
                Button cc = (Button) child_set[i];
                String l = cc.getText();
                if ( l.equals(buttonName))
                {
                 return cc;   
                }
            }
            else if ( child_set[i] instanceof Composite )
            {
                Button result = findButton((Composite)child_set[i], buttonName);
                if ( result != null )
                {
                   return result;   
                }
            }
        }
        return null;
    }
    
    public static TableItem[] getTableItems(Composite parent){
        Control [] child_set = parent.getChildren();
        for ( int i = 0; i < child_set.length; ++i )
        {
            if ( child_set[i] instanceof ElementSelectionFlatView)
            {
                ElementSelectionFlatView page = (ElementSelectionFlatView)child_set[i];
                Table table = page.getTable();
                TableItem[] items = table.getItems();
                return items;
            }
            else if ( child_set[i] instanceof Composite )
            {
                TableItem[] result = getTableItems((Composite)child_set[i]);
                if ( result != null )
                {
                   return result;   
                }
            }
        }
        return null;
        
    }

    /**
     * A shorthand method for telling the current thread to sleep for the given 
     * amount of milliseconds.
     */
    public static void sleep(long millis)
    {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
    
    /**
     * Sleeps the current thread for the given amount of milliseconds, but 
     * periodically dispatches all pending UI events.  Inserting a temporary 
     * call to this should "pause" the current test for the given duration, 
     * while still allowing the UI to be seen and manipulated during that time, 
     * which may be an aid in debugging.
     */
    public static void sleepWithDispatchOfEvents(long millis)
    {
        // the given duration must be at least as long as our sleep interval, 
        // otherwise no sleeping will be done, below
        final int sleepInterval = 10;
        if (millis < sleepInterval) millis = sleepInterval;
        
        // for each interval that makes up the given duration
        for (int i = 0; i < millis / sleepInterval; i++) {
            // sleep for this interval
            sleep(sleepInterval);
            
            // dispatch any pending UI events
            while (Display.getCurrent().readAndDispatch());
        }
    }
    
    /**
     * Copies the given file to a new one at the given
     * destination path.  If a file is already at that 
     * location, it will be overwritten.  
     * 
     * For copying an Eclipse IFile, IFile.create() should 
     * be used instead of this. 
     */
    public static void copyFile(File file, String destPath)
    {
        try {
            // open streams on the file and the 
            // destination location
            File copy = new File(destPath);
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(copy);

            // transfer the bytes from the file to the copy
            int c;
            while ((c = in.read()) != -1)
               out.write(c);

            // close the streams
            in.close();
            out.close();
        } catch (IOException e) {
            CorePlugin.logError("Could not copy file", e);
        }
    }
    /**
     * Creates a new domain with the given name, for the
     * given system.
     */
    public static void createNewModel(String domainName, SystemModel_c sys) {
        NewDomainWizard ndw = new NewDomainWizard();
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(sys);
        ndw.init(null, Selection.getInstance().getStructuredSelection());
        ndw.addPages();
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), ndw);
        dialog.create();
        WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
                .getStartingPage();
        wndcp.setDomainNameFieldValue(domainName);
        wndcp.setUseTemplate(false);
        ndw.setContainer(null);
        ndw.performFinish();
    }
    
    /**
     * Dispatches outstanding events when progress is complete. 
     */
    public static class DispatchOnDoneProgressMonitor extends NullProgressMonitor
    {
        /**
         * Is set to true when the done() method completes.
         */
        public boolean done = false;
        
        /* (non-Javadoc)
         * @see org.eclipse.core.runtime.IProgressMonitor#done()
         */
        public void done() {
            Display display = Display.getCurrent();
            while (display.readAndDispatch());
            
            done = true;
        }
    }
    
    /**
     * Copies the domain file of the given name from the development
     * workspace project of the given name into the given test 
     * workspace project.  
     */
    public static IFile copyTestDomainIntoProject(String domainName,
        String copyFromProjectName, IProject toProject)
    {
        // locate the test domain's file in the development workspace
        File workspaceSource = TestingUtilities.getSourceDirectory().toFile().getParentFile();
        File file = new File(workspaceSource, copyFromProjectName + "/" 
            + Ooaofooa.MODELS_DIRNAME + "/" + domainName 
            + "." + Ooaofooa.MODELS_EXT);
        
        // if a file of the same name already exists in the given project
        IFile resource = toProject.getFile(Ooaofooa.MODELS_DIRNAME + "/" 
                + file.getName());
        File copyFile = resource.getLocation().toFile();
        if (resource.exists()) {
            // copy the test domain's file over the existing file at the 
            // Java-file level, as Eclipse won't let us overwrite 
            // it at the IFile level (due to a bug in IFile.create())
            copyFile(file, copyFile.getAbsolutePath());
            
            // get Eclipse to notice the changed file (and wait
            // until the resulting model-events have been dispatched, 
            // before proceeding)
            try {
                DispatchOnDoneProgressMonitor monitor = 
                    new DispatchOnDoneProgressMonitor();
                resource.refreshLocal(0, monitor);
                while (!monitor.done) TestUtil.sleep(10);
            } catch (CoreException e) {
                CorePlugin.logError("Could not get Eclipse to recognize new copy of test domain in project", e);
            }
        }

        // otherwise
        else {
            // copy the test domain's file into the given project (and wait
            // until the resulting model-events have been dispatched, 
            // before proceeding)
            copyFile(file, copyFile.getAbsolutePath());
            try {
                FileInputStream stream = new FileInputStream(file);
                DispatchOnDoneProgressMonitor monitor = 
                    new DispatchOnDoneProgressMonitor();
                resource.create(stream, true, monitor);
                while (!monitor.done) TestUtil.sleep(10);
                stream.close();
            } catch (Exception e) {
                CorePlugin.logError("Could not copy test domain into project", e);
            }
        }
        
        return resource;
    }    
    
    /**
     * Copies a class file of the given name from the development
     * workspace project of the given name into the given test 
     * workspace project.  
     */
    public static IFile copyClassFileIntoProject(String className,
            String copyFromProjectName, IProject toProject)
        {
            // locate the required file in the development workspace
            File workspaceSource = TestingUtilities.getSourceDirectory().
                                                       toFile().getParentFile();
            File file = new File(workspaceSource, copyFromProjectName +
                                            "/bin/lib/" + className + ".class");
            IFile resource = toProject.getFile("/bin/lib/" 
                    + file.getName());
            File copyFile = resource.getLocation().toFile();
            // copy the test file into the given project (and wait
            // until the resulting model-events have been dispatched, 
            // before proceeding)
            copyFile(file, copyFile.getAbsolutePath());
            try {
               FileInputStream stream = new FileInputStream(file);
               DispatchOnDoneProgressMonitor monitor = 
                        new DispatchOnDoneProgressMonitor();
               resource.create(stream, true, monitor);
               while (!monitor.done) TestUtil.sleep(10);
                    stream.close();
                } catch (Exception e) {
                    CorePlugin.logError("Could not copy test class into project. reason: ", e);
                }
            return resource;
        }    
        
    /**
     * A convenience method for opening the given project.
     */
    public static void openProject(IProject project)
    {
        try {
            project.open(new NullProgressMonitor());
        } catch (CoreException e) {
            CorePlugin.logError("Could not open project", e);
        }
    }
    
    /**
     * A convenience method for closing the given project.
     */
    public static void closeProject(IProject project)
    {
        try {
            project.close(new NullProgressMonitor());
        } catch (CoreException e) {
            CorePlugin.logError("Could not close project", e);
        }
    }
    
    /**
     * A convenience method for closing the given project.
     */
    public static void deleteProject(IProject project)
    {
        try {
            project.delete(false, true, new NullProgressMonitor());
        } catch (CoreException e) {
            CorePlugin.logError("Could not delete project", e);
        }
    }
    
    /**
     * Makes the BridgePoint perspective the one that is currently active
     * within the Eclipse IDE.  Returns the page on which the perspective
     * is shown.
     */
    public static IWorkbenchPage showBridgePointPerspective()
    {
        return CanvasUtilities.showBridgePointPerspective();
    }
    
    /**
     * Is the multi-valued return value of 
     * createTestProjectAndImportModel(), below.
     */
    public static class Result1
    {
        public Ooaofooa modelRoot;
        public IProject project;
        public IFile file;
    }
    
    /**
     * Creates (and returns) a test project of the name of the given test class,
     * copies into it the model of the given name found in the project of the 
     * given name, and (also) returns the root of the model imported from
     * that copy. 
     */
    public static Result1 createTestProjectAndImportModel(
        Class testClass, String testModelName, String testModelProjectName) 
    {
        Result1 result = new Result1();
        
        // create the test project
        String className = testClass.getName();
        IProject project = null;
        try {
            project = result.project = TestingUtilities.createProject(
            className.substring(className.lastIndexOf(".") + 1));
        } catch (CoreException e) {
            CorePlugin.logError("Core Exception", e);
        }

        // copy the test domain into our test project
        IFile file = copyTestDomainIntoProject(testModelName, 
            testModelProjectName, project);
        result.file = file;
        
        // import the test domain from the copy in the test project
        result.modelRoot = Ooaofooa.getInstance(
            Ooaofooa.createModelRootId(project, testModelName, true), true);
        return result;
    }
    
    /**
     * Changes the given file's readonly status
     * 
     * @param readonly - A boolean used to determine what status to set the file
     *                   to
     * @param modelFile - The file in which the status should be altered
     */
    public static void changeFileReadonlyStatus(boolean readonly, IFile modelFile) {
        ResourceAttributes resourceAttributes = modelFile
                .getResourceAttributes();
        if (resourceAttributes != null) {
            resourceAttributes.setReadOnly(readonly);
            try {
                modelFile.setResourceAttributes(resourceAttributes);
            } catch (CoreException e) {
                CorePlugin.logError("Core Exception", e);
            }
        }
    }
    
    /**
     * Creates void and integer data types for the given model-root
     * (which is presumed to not already possess them), which are to 
     * be used with the given domain (which may be null).
     * 
     * This is useful for tests that don't load a model, but instead
     * directly create all the model elements they manipulate. 
     */
    public static void createMockDefaultDataTypes(Ooaofooa modelRoot, 
        Domain_c forDomain)
    {
        // create a void data type
        DataType_c dataType = new DataType_c(modelRoot);
        dataType.setName("void"); 
        dataType.relateAcrossR14To(forDomain);
        dataType.relateAcrossR17To(new CoreDataType_c(modelRoot));

        // create an integer data type
        dataType = new DataType_c(modelRoot);
        dataType.setName("integer"); 
        dataType.relateAcrossR14To(forDomain);
        
        dataType.relateAcrossR17To(new CoreDataType_c(modelRoot));
    }
    
    /**
     * Returns the concatenation of the given array of strings into one string,
     * with each of the smaller strings on a new line.
     * 
     * Note that any line-feed characters within the strings will be 
     * stripped out before writing, to be consistent for comparison
     * purposes with what this class's writeToFile() method does.
     */
    public static String join(String[] strings)
    {
        // for each string in the given array
        StringBuffer buffer = new StringBuffer();
        String lineSeparator = System.getProperty("line.separator");
        for (int i = 0; i < strings.length; i++) {
            // strip out any line-feeds from this string, to be consistent
            // with what this class's writeToFile() does, in case a result 
            // from this method is compared with one of that method
            String string = strings[i].replaceAll("\n", "");
            
            // append this string to the joined string we are building
            if (i > 0) buffer.append(lineSeparator);
            buffer.append(string);
        }
        
        return buffer.toString();
    }
    
    /**
     * Returns the result of joining the two arrays of strings together
     * into one array.
     */
    public static String[] join(String[] strings1, String[] strings2)
    {
        // if the second array is empty, just return the first
        if (strings2.length == 0) return strings1;
        
        // for each string in the given two arrays
        String[] result = new String[strings1.length + strings2.length];
        for (int i = 0; i < strings1.length + strings2.length; i++) {
            // put this string into our result, at the right place
            result[i] = (i < strings1.length) ? 
                strings1[i] : strings2[i - strings1.length];
        }

        return result;
    }
    
    /**
     * Returns the contents of the given text file as a string.
     */
    public static String getTextFileContents(File file)
    {
        try {
            // open a reader on the file
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            // keep doing this 
            StringBuffer contents = new StringBuffer();
            String lineSeparator = System.getProperty("line.separator");
            int linesRead = 0;
            while (true) {
                // get the next line of text from the file
                String line = reader.readLine();
                
                // if we've hit the end of the file, we're done
                if (line == null) break;
                
                // add the line to the results string we're building
                if (linesRead > 0) contents.append(lineSeparator);
                contents.append(line);
                
                linesRead++;
            }
            
            reader.close();
            
            return contents.toString();
        } catch (IOException e) {
            String message = "Could not load text file contents";
            TestCase.fail(message);
            return null;
        }
    }
    
    /**
     * Writes the given array of strings out to a text file of the given name
     * (including path), one string per line.
     * 
     * Note that any line-feed characters within the strings will be 
     * stripped out before writing, as they tend to complicate comparisons 
     * of the contents of the file after they are read back in.
     * 
     * @return  Whether the write was successful.
     */
    public static boolean writeToFile(String[] strings, String pathName) 
    {
        try {
            // get the current file contents for comparison
            // if there is no difference then we don't need
            // to update the result
            File resultFile = new File(pathName);
            if(resultFile.exists()) {
                byte[] fileBytes = new byte[(int)resultFile.length()];
                FileInputStream fis = new FileInputStream(resultFile);
                fis.read(fileBytes);
                fis.close();
                String fileContents = new String(fileBytes);
                String[] currentContents = null;
                if(fileContents.indexOf("\r") != -1) {
                    currentContents = fileContents.split("\r\n");
                } else {
                    currentContents = fileContents.split("\n");
                }
                
                if(stringArraysAreEqual(currentContents, strings)) {
                    return false;
                }
            }
            
            // create the file of the given path and name
            FileWriter writer = new FileWriter(pathName);

            // for each element in the given strings array
            String lineSeparator = System.getProperty("line.separator");
            for (int i = 0; i < strings.length; i++) {
                // strip out any line-feeds from this string, as they 
                // will cause it to be treated as two or more separate 
                // strings when it is read back in, which screws up
                // comparisons
                String string = strings[i].replaceAll("\n", "");

                // write this string out to a new line in the text file
                if (i > 0) writer.write(lineSeparator);
                writer.write(string);
            }
            
            writer.flush();
        } 
        catch (Exception e) {
            CorePlugin.logError("Could not write strings to text file", e);
            return false;
        }
        
        return true;
    }

    private static boolean stringArraysAreEqual(String[] currentContents, String[] strings) {
        // if the lengths are different then they are
        // not equal
        if(currentContents.length != strings.length) {
            return false;
        }
        // otherwise compare each array value
        for(int i = 0; i < currentContents.length; i++) {
            String string = strings[i].replaceAll("\n", "");
            String currentString = currentContents[i].replaceAll("\n", "");
            if(!currentString.equals(string)) {
                return false;
            }
        }
        return true;
    }

    public static FailableRunnable chooseItemInDialog(final int sleep, final String item) {
        return chooseItemInDialog(sleep, item, false);
    }

    public static FailableRunnable chooseItemInDialog(final int sleep, final String item, final boolean locateOnly) {
        return chooseItemInDialog(sleep, item, locateOnly, false);
    }
            
    public static FailableRunnable chooseItemInDialog(final int sleep, final String item, final boolean locateOnly, final boolean testNonExistence) {
        return chooseItemInDialog(sleep, null, item, locateOnly, testNonExistence);
    }

    public static FailableRunnable toggleButtonInElementSelectionDialog(final int sleep, final String buttonName) {
        return toggleButtonInElementSelectionDialog(sleep, null, buttonName);
    }
    
    public static void cancelElementSelectionDialog(final int sleep) {
        cancelElementSelectionDialog(sleep, null);
    }
    public static void cancelElementSelectionDialog(final int sleep, final FailableRunnable waitRunnable) {
        Thread cancelThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                sleep(sleep);
                waitForRunnable(waitRunnable);
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                    
                    @Override
                    public void run() {
                        // locate the dialog
                        Shell[] shells = PlatformUI.getWorkbench().getDisplay()
                                .getShells();
                        Shell shell = null;
                        for(int i = 0; i < shells.length; i++) {
                            if(shells[i].getData() instanceof ElementSelectionDialog) {
                                shell = shells[i];
                            }
                        }
                        if (shell != null) {
                            ElementSelectionDialog dialog = (ElementSelectionDialog) shell.getData();
                            dialog.close();
                        }
                    }
                });
            }
        });
        cancelThread.start();
    }

    protected static void waitForRunnable(FailableRunnable waitRunnable) {
        // if there is a runnable given, wait for
        // it to complete
        if(waitRunnable != null) {
            // wait for a max of 60s
            int sleepTime = 0;
            while(!waitRunnable.getComplete()) {
                if(sleepTime > 60000) {
                    break;
                }
                sleep(1000);
                sleepTime = sleepTime + 1000;
            }
        }
    }

    public static void okElementSelectionDialog(final FailableRunnable runnable) {
        Thread cancelThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                waitForRunnable(runnable);
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                    
                    @Override
                    public void run() {
                        int iterations = 10;
                        int count = 0;
                        while (count <= iterations) {
                            // locate the dialog
                            Shell[] shells = PlatformUI.getWorkbench()
                                    .getDisplay().getShells();
                            Shell shell = null;
                            for (int i = 0; i < shells.length; i++) {
                                if (shells[i].getData() instanceof ElementSelectionDialog) {
                                    shell = shells[i];
                                }
                            }
                            if (shell != null) {
                                ElementSelectionDialog dialog = (ElementSelectionDialog) shell
                                        .getData();
                                if (dialog.getOkButton().isEnabled()) {
                                    dialog.getOkButton().notifyListeners(
                                            SWT.Selection, new Event());
                                    break;
                                } else {
                                    sleep(300);
                                    count++;
                                }
                            } else {
                                sleep(300);
                                count++;
                            }
                        }
                    }
                });
            }
        });
        cancelThread.start();
    }

    public static FailableRunnable chooseItemInDialog(
            FailableRunnable runnable, String item, boolean locateOnly) {
        return chooseItemInDialog(0, runnable, item, locateOnly, false);
    }

    public static FailableRunnable chooseItemInDialog(final int sleep, final FailableRunnable waitRunnable,
            final String item, final boolean locateOnly, final boolean testNonExistence) {
        FailableRunnable runnable = new FailableRunnable() {    
            @Override
            public void run() {
                sleep(sleep);
                waitForRunnable(waitRunnable);
                FailableRunnable innerRunnable = new FailableRunnable() {
                    @Override
                    public void run() {
                        boolean found = false;
                        // locate the dialog
                        Shell[] shells = PlatformUI.getWorkbench().getDisplay()
                                .getShells();
                        Shell shell = null;
                        for(int i = 0; i < shells.length; i++) {
                            if(shells[i].getData() instanceof ElementSelectionDialog) {
                                shell = shells[i];
                                break;
                            } else if(shells[i].getText().contains("Import Projects from Git Repository")) {
                            	shell = shells[i];
                            	break;
                            }
                        }
                        if (shell != null) {
                            Dialog dialog = (Dialog) shell
                                    .getData();
                            Control[] children = dialog.getShell().getChildren();
							for (int i = 0; i < children.length; i++) {
								Table table = findTable(children);
								if(table != null) {
									// if a deselect all button is present
									// press it before selecting the desired
									// item
									Button deselect = findButton(shell, "&Deselect All");
									if(deselect != null) {
										deselect.notifyListeners(SWT.Selection, new Event());
										while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
									}
									TableItem[] items = table.getItems();
									for (int j = 0; j < items.length; j++) {
										if (items[j].getText().equals(item)) {
											// do not select if locateOnly is
											// true
											if (!locateOnly) {
												table.setSelection(items[j]);
												Event event = new Event();
												event.item = items[j];
												table.notifyListeners(
														SWT.Selection, event);
												while (PlatformUI.getWorkbench()
														.getDisplay()
														.readAndDispatch())
													;
											}
											found = true;
											break;
										}
									}
									break;
								}
							}
						}
                        if (testNonExistence) {
                            if(found) {
                                setFailure("Found the unexpected item in the selection dialog ("
                                        + item + ").");
                            }
                        } else {
                            if(!found) {
                                setFailure("Could not locate the expected item in the selection dialog ("
                                        + item + ").");
                            }
                        }                       
                        setComplete();
                    }

					private Table findTable(Control[] children) {
						for (Control child : children) {
							if (child instanceof Table) {
								return (Table) child;
							} else if (child instanceof Composite) {
								Table result = findTable(((Composite) child)
										.getChildren());
								if(result != null) {
									return result;
								}
							}
						}
						return null;
					}
                };
                // must be run in the UI thread
                PlatformUI.getWorkbench().getDisplay().syncExec(innerRunnable);
                if(!innerRunnable.getFailure().equals("")) {
                    setFailure(innerRunnable.getFailure());
                }
                setComplete();
            }
        };
        Thread chooserThread = new Thread(runnable);
        chooserThread.start();      
        return runnable;
    }

    public static FailableRunnable toggleButtonInElementSelectionDialog(final int sleep,
            final FailableRunnable waitRunnable, final String buttonName) {
        FailableRunnable runnable = new FailableRunnable() {
            
            @Override
            public void run() {
                sleep(sleep);
                waitForRunnable(waitRunnable);
                FailableRunnable innerRunnable = new FailableRunnable() {
                    
                    @Override
                    public void run() {
                        // locate the dialog
                        Shell[] shells = PlatformUI.getWorkbench().getDisplay()
                                .getShells();
                        Shell shell = null;
                        for(int i = 0; i < shells.length; i++) {
                            if(shells[i].getData() instanceof ElementSelectionDialog) {
                                shell = shells[i];
                            }
                        }
                        if (shell != null) {
                            ElementSelectionDialog dialog = (ElementSelectionDialog) shell.getData();
                            ElementSelectionFlatView view = dialog.getFlatView();
                            Control[] children = view.getChildren();
                            for(int i = 0; i < children.length; i++) {
                                if(children[i] instanceof Button) {
                                    Button button = (Button) children[i];
                                    if(button.getText().equals(buttonName)) {
                                        button.setSelection((button.getSelection()) ? false:true);
                                        button.notifyListeners(SWT.Selection, new Event());
                                        view.redraw();
                                        view.update();
                                        setComplete();
                                        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
                                        return;
                                    }
                                }
                            }
                            // if we get here add an error to the thread, as
                            // the button could not be found
                            setFailure("Unable to locate button in selection dialog: "
                                            + buttonName);
                            setComplete();
                        }
                    }
                };
                // must be run in the UI thread
                PlatformUI.getWorkbench().getDisplay().syncExec(innerRunnable);
                if(!innerRunnable.getFailure().equals("")) {
                    setFailure(innerRunnable.getFailure());
                }
                setComplete();
            }
        };
        Thread chooserThread = new Thread(runnable);
        chooserThread.start();
        return runnable;
    }
    
	public static void executeInTransaction(NonRootModelElement element, String method, Object[] parameters) {
		Class<?>[] paramClasses = new Class<?>[parameters.length];
		for(int i = 0; i < parameters.length; i++) {
			if(parameters[i] instanceof Integer) {
				paramClasses[i] = Integer.TYPE;
			} else if(parameters[i] instanceof Boolean) {
				paramClasses[i] = Boolean.TYPE;
			} else {
				paramClasses[i] = parameters[i].getClass();
			}
		}
		Transaction transaction = null;
		TransactionManager manager = TransactionManager.getSingleton();
		try {
			transaction = manager.startTransaction("test transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			Method m = element.getClass().getMethod(method, paramClasses);
			m.invoke(element, parameters);
			manager.endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			CorePlugin.logError("Unable to complete transaction.", e);
		}
		BaseTest.dispatchEvents(0);
	}

	
}
