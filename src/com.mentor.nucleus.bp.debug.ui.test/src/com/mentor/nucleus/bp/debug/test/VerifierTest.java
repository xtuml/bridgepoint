package com.mentor.nucleus.bp.debug.test;
//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
//

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

import junit.framework.Assert;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.Launch;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Runstatetype_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ILogger;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.debug.ui.launch.BPLaunchDelegate;
import com.mentor.nucleus.bp.debug.ui.model.BPDebugTarget;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class VerifierTest extends BaseTest {

	private Ooaofooa thisRoot = null;
	
	private IPath outputFilePathRoot = null;

	private IPath expectedFilePathRoot = null;

	private Vector<String> modelNames = new Vector<String>();
	
	private Vector<Ooaofooa> modelRoots = new Vector<Ooaofooa>();	
	
	private PrintStream preserveOut = null;

	private PrintStream preserveErr = null;
	
	private PrintStream preserveCoreOut = null;

	private PrintStream preserveCoreErr = null;
	
	private ILogger preserveLogger = null;
	
	private BPDebugTarget target = null;
	
	private boolean result = false;
		
	public VerifierTest(String name) {
		super(null, name);
		modelNames.add(name);
	}
	
	public VerifierTest(String[] names) {
		for (int i = 0; i < names.length; i++)
		{
			modelNames.add(names[i]);
		}
		
	}
		
	protected void setUp() throws Exception {
		super.setUp();
		// if we haven't yet imported the model used for these tests
		if (!result) {
			// do so

			loadProject(modelNames.elementAt(0));
			thisRoot = modelRoot;
			modelRoots.add(thisRoot);
			result = true;
						
			// if we have more than 1 model name, load all the additional models
			if (modelNames.size() > 1)
			{
				for (int i = 1; i < modelNames.size(); i++)
				{
					// import the test domain from the copy in the test project
					loadProject(modelNames.elementAt(i));
					getModelRoots().add(i, modelRoot);					
				}				
			}				
			setOutputPath();
		}
		target = startVerifier();
	}

	protected void setOutputPath() {
		// Find the path to the workspace for results output
		String workspace_path = System.getProperty("WORKSPACE_PATH");//$NON-NLS-1$
		assertNotNull(workspace_path);
		outputFilePathRoot = new Path(workspace_path);
		expectedFilePathRoot = outputFilePathRoot
				.append("expected_results").append("verifier")
				.addTrailingSeparator();
		outputFilePathRoot = outputFilePathRoot.append("actual_results")
				.addTrailingSeparator();
        File folder = new File(outputFilePathRoot.toString());
        if (!folder.exists()) {
            // create them
            folder.mkdirs();
        }
        
        TestUtil.sleepWithDispatchOfEvents(100);
        
		
	}
	public void executeModel() throws InterruptedException {
		executeSetupFunction();
		executeTestFunction();
		ComponentInstance_c ee = null;
		ee = ComponentInstance_c.ComponentInstanceInstance(thisRoot);
		if (ee != null) {
		  synchronized (ee) {
			  ee.notify();
		  }
  	      Assert.assertNotNull("No DebugTarget found starting Verifier test.",target);
    	  target.Notify();
		  int retries = 25;
		  while (ee != null && ee.getRunning() && retries > 0) {
			// wait for test to complete
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// do nothing
			}
			retries--;
		  }
		  if (retries == 0) {
			ee.Stop();
			ee.Reset();
			ee.delete();
            Assert.assertNotNull("No DebugTarget found force stopping Verifier test.", target);
            target.Notify();
			fail("Model did not complete execution.");
		  }
          try {
        	if (target != null) {
              target.terminate();
        	}
          }
          catch(DebugException de) {
            fail("Debug Exception encountered shutting down test vereifier session reason: " + de);
          }
		}
	}

    public BPDebugTarget startVerifier() {
		BPTestLaunchConfiguration bplc = new BPTestLaunchConfiguration(project.getName(), modelNames, modelRoots);
		Launch testLaunch = new Launch(bplc, "debug", null);
		BPLaunchDelegate bpld = new BPLaunchDelegate();
		try {
		  bpld.launch(bplc, "debug", testLaunch, new NullProgressMonitor());
		}
		catch (CoreException ce) {
			fail(ce.toString());
		}
		BPDebugTarget bpdbt = null; 
		while (bpdbt == null || bpdbt.isTerminated()) {
			try {
			  Thread.sleep(1000);
			}
			catch (InterruptedException ie) {
				// do nothing
			}
			bpdbt = (BPDebugTarget)testLaunch.getDebugTarget();
		}
		return bpdbt;
    }

    public void executeSetupFunction() throws InterruptedException {
		selectFunction("setup");
		executeFunctionSynchronous();
	}

	public void executeTestFunction() throws InterruptedException {
		selectFunction("test");
		executeFunctionSynchronous();
	}

	private void executeFunctionSynchronous() {
		// Get the execution engine, create if needed
	    ComponentInstance_c ee = ComponentInstance_c.ComponentInstanceInstance(Selection.getInstance().getModelRoot());
	    if (ee == null)
	    {
	    	// First run, create an execution engine and initialize the system
	    	// architecture(create stacks and etc.)
	    	ee = new ComponentInstance_c(Selection.getInstance().getModelRoot());
	    	ee.Initializearchitecture();
	    }
	    Ooaofooa.beginVerifierExecution(ee.getExecution_engine_id());
		Function_c fn = (Function_c) Selection.getInstance()
				.getStructuredSelection().getFirstElement();
		FunctionBody_c fnbdy = FunctionBody_c.getOneACT_FNBOnR695(fn);
		Body_c bdy = Body_c.getOneACT_ACTOnR698(fnbdy);
  	    try {
  	      ModelRoot.disableChangeNotification();
  	      Statement_c stmt = Statement_c.getOneACT_SMTOnR602(Block_c.getManyACT_BLKsOnR666(bdy));
  	      if (stmt != null) {
		    Stack_c stack = Stack_c.StackInstance(thisRoot);
		    bdy.Createstackframe(false, Gd_c.Null_unique_id(), stack.getStack_id());
		    StackFrame_c cur_stack_frame = StackFrame_c.
		                                    getOneI_STFOnR2929(stack);
		    while (stack.getRunstate() == Runstatetype_c.Running &&
		                              cur_stack_frame != null &&
				                         !cur_stack_frame.Isdisposed()){
		    	stack.Execute(true);
			    cur_stack_frame = StackFrame_c.getOneI_STFOnR2929(stack);
			}
  	      }
  	      synchronized (ee) {
			  ee.notify();
  	      }
  	    }
  	    finally {
            ModelRoot.enableChangeNotification();
      	}
  	    Ooaofooa.endVerifierExecution(ee.getExecution_engine_id());
	}

	protected void tearDown() throws InterruptedException {
		DebugUITestUtilities.clearDebugView();
	}

	protected void validateEngineDoesNotExist() {
		ComponentInstance_c ee = ComponentInstance_c
				.ComponentInstanceInstance(thisRoot);
		assertTrue("Component Instance already exists", ee == null);
	}

	protected void validateEngineExists(boolean running) {
		ComponentInstance_c ee = ComponentInstance_c
				.ComponentInstanceInstance(thisRoot);
		assertNotNull("Component Instance does not exist", ee);
		assertEquals(running, ee.getRunning());
	}

	protected void selectDomain(String modelName) {
		Component_c obj = Component_c.ComponentInstance(thisRoot);

		assertNotNull("Unable to load component", obj);
		assertEquals("Wrong component loaded", modelName, obj.getName());

		StructuredSelection sel = new StructuredSelection(obj);
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(sel);
	}
	
	protected void selectDomain(String modelName, Ooaofooa root) {
		Component_c obj = Component_c.ComponentInstance(root);

		assertNotNull("Unable to load component", obj);
		assertEquals("Wrong component loaded", modelName, obj.getName());

		StructuredSelection sel = new StructuredSelection(obj);
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(sel);
	}

	protected void selectFunction(final String name) {
		Function_c f = Function_c.FunctionInstance(thisRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						Function_c selected = (Function_c) candidate;
						return selected.getName().equals(name);
					}
				});
		assertNotNull(f);
		StructuredSelection sel = new StructuredSelection(f);
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(sel);
	}

	protected void redirectOutput(String fileName) {
		IPath outputFilePath = outputFilePathRoot.append(fileName)
				.addFileExtension("result");
		if (!outputFilePath.toFile().exists()) {
			try {
				outputFilePath.toFile().createNewFile();
			} catch (IOException e) {
				fail("Could not create " + outputFilePath.toFile());
			}
		}
		try {
			OutputStream os = new FileOutputStream(outputFilePath.toFile());
			PrintStream ps = new PrintStream(os);
			if (preserveOut == null) {
			  preserveOut = System.out;
			}
			System.setOut(ps);
			if (preserveErr == null) {
			  preserveErr = System.err;
			}
			System.setErr(ps);
			if (preserveCoreOut == null) {
				preserveCoreOut = CorePlugin.out; 
			}
			CorePlugin.out = ps;
			if (preserveCoreErr == null) {
				preserveCoreErr = CorePlugin.err; 
			}
			CorePlugin.err = ps;
		} catch (FileNotFoundException e) {
			fail("Could not open expected results file: "
					+ outputFilePath.toFile().toString());
		}
	}

    public void endRedirection() {
		if (preserveOut != null) {
			System.out.close();
			System.setOut(preserveOut);
			preserveOut = null;
		}
		if (preserveErr != null) {
			System.setErr(preserveErr);
			preserveErr = null;
		}
		if (preserveLogger != null) {
			Ooaofooa.log = preserveLogger;
			preserveLogger = null;
		}
		if (preserveCoreOut != null) {
			CorePlugin.err = preserveCoreOut;
			preserveCoreOut = null;
		}
		if (preserveCoreErr != null) {
			CorePlugin.err = preserveCoreErr;
			preserveCoreErr = null;
		}
    }
	
	
	public void finalize() {
		//
		// undo the log redirection, if necessary
		// repeated here to ensure that redirection
		// is removed in the event of abnormal exit.
		//
		endRedirection();
	}
	
	protected void compareOutput(String fileName) {
		//
		// Ensure results are flushed to disk
		//
		IPath outputFilePath = outputFilePathRoot.append(fileName)
                                                       .addFileExtension("result");
        int retries = 10;
        IOException ioe = null;
        FileInputStream in = null;
        while (--retries > 0 && in == null) {
          try {
            in = new FileInputStream(outputFilePath.toFile());
          } catch (IOException e) {
            ioe = e;
          }
        }
        if (retries == 0) {
          fail("Could not open output results file: "
                         + outputFilePath.toString() + "\n" + "Reason: " + ioe);
        }
		//
		// Compare actual and expected results to ensure the test passed
		//
		String expected = null;
		String actual = null;
		IPath expectedFilePath = expectedFilePathRoot.append(fileName)
		.addFileExtension("result");
        expected = TestUtil.getTextFileContents(expectedFilePath.toFile());
		actual = TestUtil.getTextFileContents(outputFilePath.toFile());
		assertEquals("Actual and expected files differ for "
				+ outputFilePath.toString(), expected, actual);

	}
	protected Vector<Ooaofooa> getModelRoots() {
		return modelRoots;
	}
	
}
