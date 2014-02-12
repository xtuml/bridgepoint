package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File:      $RCSfile: CanvasTest.java,v $
//Version:   $Revision: 1.64 $
//Modified:  $Date: 2013/03/13 23:49:22 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
//
//The supertype of all the test classes, this class abstracts the common
//testing behaviors. 
//
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

import junit.framework.AssertionFailedError;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Diagram_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public abstract class CanvasTest extends BaseTest {
  private String modelName = "";
  private IPreferenceStore store=JFacePreferences.getPreferenceStore();
  
  public CanvasTest(String arg0)  {
    super(null, arg0);
    store.setValue("com.mentor.nucleus.bp.canvas.font","1|Tahoma|12|0|WINDOWS|1|-11|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma;");
    
  }
  
  public CanvasTest(final String projectName, String arg0)  {
    super(projectName, arg0);
    store.setValue("com.mentor.nucleus.bp.canvas.font","1|Tahoma|12|0|WINDOWS|1|-11|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma;");
  }
  
  protected String getModelName() {
    return modelName;
  }
  protected void setModelName(String name) {
    modelName = name;
  }
  protected abstract String getResultName();
  protected String getResultFileName() {
	  return "";
  }
  protected String resultNamePostFix="";
  
  protected void setUp() throws Exception {
	  super.setUp();
	  CorePlugin.getDefault().getPluginPreferences().setValue(
	  	      BridgePointPreferencesStore.SHOW_EVENT_PARAMETERS, true);
  }
  
  protected void doTestDiagram(GraphicalEditor editor, final int model_type, boolean zoomGroup, boolean zoomSelected, boolean isHardCopy) throws Exception {
  		// create the unit under test (uut)
		assertNotNull("Diagram not found", editor.getModel()); //$NON-NLS-1$
        
		CanvasTestResult result = drawDiagram(editor, zoomGroup, zoomSelected, isHardCopy, new Rectangle(0, 0, 1231, 861));

		// load the expected results for the test being performed
		String path = m_workspace_path + "expected_results/" + getResultName() + "/";
		String filename = editor.getModel().getOoa_idLongBased() + "-" + editor.getModel().getModel_type();
		
		String expectedResults = null;
		//After UIID long id is not guaranteed, so we need some hard coded names
       if(!isFileExists(path, filename)){
           filename=getFileName(editor.getModel().getModel_type());
       }
		File file = new File(path + filename + ".txt");
		file = file.getCanonicalFile();
		if(file.exists()){
	        expectedResults = TestUtil.getTextFileContents(file);
		}else{
			file = new File(path + filename);
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				// for each drawing command in the expected results file
				int size = ois.readInt();
				String[] expected = new String[size];
				for (int i = 0; i < size; i++) {
					// read this command into our array of the commands from the file 
					expected[i] = (String) ois.readObject();
				}
				ois.close();

				expectedResults = generateSingleString(expected);
			} catch (java.io.StreamCorruptedException e) {
				expectedResults = TestUtil.getTextFileContents(file);
			}catch(EOFException e){
                expectedResults = TestUtil.getTextFileContents(file);
            }
            catch(Exception e){
		       fail(e.getMessage());         
            }
		}
		
		// check that the actual results match those that are expected
        String actualResults = TestUtil.join(result.transcript);
        try {
			assertEquals("Actual results differ from those expected, file: \n" + file.getName() + "\n",
			    expectedResults, actualResults);
		} catch (AssertionFailedError e) {
			writeResults(editor.getModel(), expectedResults, result.transcript, result.image);
			throw e;
		}
  }
  protected void doTestDiagramGenerics(GraphicalEditor editor, final int model_type, boolean zoomGroup, boolean zoomSelected, boolean isHardCopy) throws Exception {
		// create the unit under test (uut)
		assertNotNull("Diagram not found", editor.getModel()); //$NON-NLS-1$
      
		CanvasTestResult result = drawDiagram(editor, zoomGroup, zoomSelected, isHardCopy, new Rectangle(0, 0, 1231, 861));

		// load the expected results for the test being performed
		String path = m_workspace_path + "expected_results/" + getResultName() + "Generics/";
		String filename = editor.getModel().getOoa_idLongBased() + "-" + editor.getModel().getModel_type();
		
		String expectedResults = null;
		//After UIID long id is not guaranteed, so we need some hard coded names
     if(!isFileExists(path, filename)){
         filename=getFileName(editor.getModel().getModel_type());
     }
		File file = new File(path + filename + ".txt");
		file = file.getCanonicalFile();
		if(file.exists()){
	        expectedResults = TestUtil.getTextFileContents(file);
		}else{
			file = new File(path + filename);
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				// for each drawing command in the expected results file
				int size = ois.readInt();
				String[] expected = new String[size];
				for (int i = 0; i < size; i++) {
					// read this command into our array of the commands from the file 
					expected[i] = (String) ois.readObject();
				}
				ois.close();

				expectedResults = generateSingleString(expected);
			} catch (java.io.StreamCorruptedException e) {
				expectedResults = TestUtil.getTextFileContents(file);
			}catch(EOFException e){
              expectedResults = TestUtil.getTextFileContents(file);
          }
          catch(Exception e){
		       fail(e.getMessage());         
          }
		}
		
		// check that the actual results match those that are expected
      String actualResults = TestUtil.join(result.transcript);
      try {
			assertEquals("Actual results differ from those expected, file: \n" + file.getName() + "\n",
			    expectedResults, actualResults);
		} catch (AssertionFailedError e) {
			writeResultsGenerics(editor.getModel(), expectedResults, result.transcript, result.image);
			throw e;
		}
}
  private boolean isFileExists(String path, String filename){
      File file = new File(path + filename + ".txt");
      if(!file.exists()){
          file = new File(path + filename );
          if(!file.exists())
              return false;
      }
      return true;
  }
  private String generateSingleString(String[] strings){
	  String result = "";
	  if(strings != null){
		  for (int i = 0; i < strings.length; i++) {
			  if(i > 0){
				  result = result + "\r\n";
			  }
			  result = result + strings[i];
		  }
	  }
	  return result;
  }
  
  protected Model_c getUUT(final UUID ooa_id, final int model_type) {
    /*
     * Create the unit under test (uut)
     */
    Model_c model = Model_c.ModelInstance(graphicsModelRoot, new ClassQueryInterface_c() {
      public boolean evaluate(Object selected) {
        return ((Model_c) selected).getOoa_id().equals(ooa_id) && ((Model_c) selected).getModel_type() == model_type;
      }
    });
    if(model == null) {
    	// try the default root for system level instances
    	model = Model_c.ModelInstance(Ooaofgraphics.getDefaultInstance(), new ClassQueryInterface_c() {
    	      public boolean evaluate(Object selected) {
    	        return ((Model_c) selected).getOoa_id().equals(ooa_id) && ((Model_c) selected).getModel_type() == model_type;
    	      }
    	    });
    }
    return model;
  }
  
  protected Model_c getUUT(final long ooa_id, final int model_type) {
	    /*
	     * Create the unit under test (uut)
	     */
	    
	  Model_c model = Model_c.ModelInstance(graphicsModelRoot, new ClassQueryInterface_c(){
	      public boolean evaluate(Object selected) {
	        return ((Model_c) selected).getOoa_idLongBased() == ooa_id && ((Model_c) selected).getModel_type() == model_type;
	      }
	  });
	  return model;
  }

  protected void doLoadSql(String inFile) throws FileNotFoundException{
	String x = m_workspace_path + Ooaofooa.MODELS_DIRNAME + "/" + inFile + "." + Ooaofooa.MODELS_EXT; //$NON-NLS-1$ //$NON-NLS-2$
    System.out.println("Loading :" + x); //$NON-NLS-1$
    ImportModel importer = new ImportModel(x, modelRoot, getSystemModel(), true, true, false);
    int i = importer.countAndValidateInsertStatements();
    assertTrue ( i > 0 );
    importer.run(new NullProgressMonitor());
    assertEquals("", importer.m_errorMessage );
	modelRoot.setLoadPathForTests(x);
  }
  public void createExpectedResults(boolean zoomGroup, boolean zoomSelected, boolean isHardCopy) throws Exception {
    /*
     * Create the unit under test (uut)
     */
    Model_c[] uuts = Model_c.ModelInstances(graphicsModelRoot);
    for (int i = 0; i < uuts.length; i++) {
      // special case canvas test state machine to not show event data
      if (uuts[i].getOoa_idLongBased() == 8388624 && getResultName().equals("canvastest")) {
    	  CorePlugin.getDefault().getPluginPreferences().setValue(
    	  	      BridgePointPreferencesStore.SHOW_EVENT_PARAMETERS, false);
      }
      else {
    	  CorePlugin.getDefault().getPluginPreferences().setValue(
    	  	      BridgePointPreferencesStore.SHOW_EVENT_PARAMETERS, true);
      }
      CanvasTestResult result = drawDiagram(UITestingUtilities
					.getGraphicalEditorFor((NonRootModelElement) uuts[i]
							.getRepresents(), true), zoomGroup, zoomSelected,
					isHardCopy, new Rectangle(0, 0, 1231, 861));
      writeResults(result.transcript, uuts[i], result.image);
    }
  }
  protected void writeResults(String[] results, Model_c uut, ImageData imgData) throws Exception {
	String folder = m_workspace_path + "expected_results/" + getResultName() + "/"; //$NON-NLS-1$ //$NON-NLS-2$
	if (testGlobals)
	{
		folder = m_workspace_path + "expected_results/" + getResultName() + "Globals/"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	if(!(new File(folder).exists())) {
		(new File(folder)).mkdirs();
	}
	String pathName = uut.getOoa_idLongBased() + "-" + uut.getModel_type();
	
	//After UIID long id is not guaranteed, so we need some hard coded names
	if(!isFileExists(folder, pathName)){
       pathName=getFileName(uut.getModel_type());
   	}

	pathName = folder + pathName;
	
    if ( TestUtil.writeToFile(results, pathName) )
    {
	    ImageLoader il = new ImageLoader();
	    il.data = new ImageData[1];
	    il.data[0] = imgData;
	    il.save(pathName + ".jpg", 4); //$NON-NLS-1$
	    System.out.println("Wrote :" + pathName); //$NON-NLS-1$
    }
  }
  protected void writeResultsGenerics(String[] results, Model_c uut, ImageData imgData) throws Exception {
		String folder = m_workspace_path + "expected_results/" + getResultName() + "Generics/"; //$NON-NLS-1$ //$NON-NLS-2$
		if(!(new File(folder).exists())) {
			(new File(folder)).mkdirs();
		}
		String pathName = uut.getOoa_idLongBased() + "-" + uut.getModel_type();
		
		//After UIID long id is not guaranteed, so we need some hard coded names
		if(!isFileExists(folder, pathName)){
	       pathName=getFileName(uut.getModel_type());
	   	}

		pathName = folder + pathName;
		
	    if ( TestUtil.writeToFile(results, pathName) )
	    {
		    ImageLoader il = new ImageLoader();
		    il.data = new ImageData[1];
		    il.data[0] = imgData;
		    il.save(pathName + ".jpg", 4); //$NON-NLS-1$
		    System.out.println("Wrote :" + pathName); //$NON-NLS-1$
	    }
	  }
  public CanvasTestResult drawDiagram(final GraphicalEditor editor, boolean zoomGroup, boolean zoomSelected, boolean isHardCopy, Rectangle size) {
	  FontData prefFontData = new FontData("1|Tahoma|8|0|WINDOWS|1|-11|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma"); //$NON-NLS-1$
	  Font displayFont = new Font(Display.getDefault(), prefFontData);
	  store.setValue("org.eclipse.jface.dialogfont", "1|Tahoma|8|0|WINDOWS|1|-11|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma");
	  store.setValue("org.eclipse.ui.workbench.TAB_TEXT_FONT", "1|Tahoma|8|0|WINDOWS|1|-11|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma");
	  store.setValue("org.eclipse.ui.workbench.VIEW_MESSAGE_TEXT_FONT", "1|Tahoma|8|0|WINDOWS|1|-11|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma");
	  CanvasModelListener.setGraphicalRepresents(editor.getModel());
	  Image img = new Image(Display.getDefault(), size);
	  final TestGC tester = new TestGC(new SWTGraphics(new GC(img)));
	  tester.setFont(displayFont);
	  FigureCanvas canvas = (FigureCanvas) editor.getCanvas();
	  canvas.setBounds(size);
	  canvas.getViewport().setBounds(new org.eclipse.draw2d.geometry.Rectangle(size));
	  editor.zoomAll();
	  while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	  paint(tester, editor);
	  CanvasTestResult result = new CanvasTestResult();
	  result.transcript = tester.getResults();
	  result.image = img.getImageData();
	  tester.dispose();
	  img.dispose();
	  displayFont.dispose();
	  return result;
  }
  
  	protected void paint(TestGC tester, GraphicalEditor editor) {
  		((FigureCanvas) editor.getCanvas()).getLightweightSystem()
				.getUpdateManager().performValidation();
		((FigureCanvas) editor.getCanvas())
				.getLightweightSystem().getRootFigure().paint(tester);
  	}

	/**
  	 * Writes the given array of expected results out to one text file, 
  	 * and the given array of actual results to another.  Both files will
  	 * be written at the given path, and their names will be prepended
  	 * with the name of the currently running test.
  	 * 
  	 * Doing this allows the two text files to be compared by a diff tool, 
  	 * for better understanding of what the discrepencies are.
  	 */
  	private void writeResults(Model_c uut, String expected, String[] actual, ImageData imgData) throws IOException
	{
        String path = CanvasUtilities.getWorkspace().getRoot().getLocation().toString();
        String folderStr = path + "/actual_results/" + getResultName() + "/"; //$NON-NLS-1$ //$NON-NLS-2$
        
        File folder = new File(folderStr);
        if(!folder.exists()){
        	folder.mkdirs();
        }
        
    	String filename = uut.getOoa_idLongBased() + "-" + uut.getModel_type(); //$NON-NLS-1$
    	
        FileWriter writer = new FileWriter(folderStr  + filename+"_exp.txt");
		writer.write(expected);
    	writer.flush();
    	writer.close();
        
		TestUtil.writeToFile(actual, folderStr  + filename+"_act.txt");
		
        ImageLoader il = new ImageLoader();
        il.data = new ImageData[1];
        il.data[0] = imgData;
        il.save(folderStr + filename + "_act.jpg", 4); //$NON-NLS-1$//$NON-NLS-2$
        
//      copy expected results image file to actual folder
		String path_exp = m_workspace_path + "expected_results/" + getResultName() + "/"; //$NON-NLS-1$ //$NON-NLS-2$
        if(!isFileExists(path_exp, filename)){
               filename=getFileName(uut.getModel_type());
           }
        String filename_exp = path_exp+ filename;
        
		FileInputStream fis = new FileInputStream(filename_exp+".jpg");
		FileOutputStream fos = new FileOutputStream(folderStr  + filename+"_exp.jpg");
		
		int b=fis.read();
		
		while (b!=-1) {
			fos.write(b);
			b= fis.read();
		}
		
		fos.close();
	}

  	private void writeResultsGenerics(Model_c uut, String expected, String[] actual, ImageData imgData) throws IOException
	{
        String path = CanvasUtilities.getWorkspace().getRoot().getLocation().toString();
        String folderStr = path + "/actual_results/" + getResultName() + "Generics/"; //$NON-NLS-1$ //$NON-NLS-2$
        
        File folder = new File(folderStr);
        if(!folder.exists()){
        	folder.mkdirs();
        }
        
    	String filename = uut.getOoa_idLongBased() + "-" + uut.getModel_type(); //$NON-NLS-1$
    	
        FileWriter writer = new FileWriter(folderStr  + filename+"_exp.txt");
		writer.write(expected);
    	writer.flush();
    	writer.close();
        
		TestUtil.writeToFile(actual, folderStr  + filename+"_act.txt");
		
        ImageLoader il = new ImageLoader();
        il.data = new ImageData[1];
        il.data[0] = imgData;
        il.save(folderStr + filename + "_act.jpg", 4); //$NON-NLS-1$//$NON-NLS-2$
        
//      copy expected results image file to actual folder
		String path_exp = m_workspace_path + "expected_results/" + getResultName() + "Generics/"; //$NON-NLS-1$ //$NON-NLS-2$
        if(!isFileExists(path_exp, filename)){
               filename=getFileName(uut.getModel_type());
           }
        String filename_exp = path_exp+ filename;
        
		FileInputStream fis = new FileInputStream(filename_exp+".jpg");
		FileOutputStream fos = new FileOutputStream(folderStr  + filename+"_exp.jpg");
		
		int b=fis.read();
		
		while (b!=-1) {
			fos.write(b);
			b= fis.read();
		}
		
		fos.close();
	}
  	
    private String getFileName(int model_type) {
        String name = getResultName() + "-" + model_type;
        if(!resultNamePostFix.equals(""))
            name = name + "-" + resultNamePostFix;
        if(getResultFileName() != "") {
        	name = getResultFileName();
        }
        return name;
    }

    /**
     * A convenience method.  See method called.
     */
    public void validateOrGenerateResults(GraphicalEditor editor, boolean generate)
    {
        validateOrGenerateResults(editor, generate, false);
    }
    
    /**
     * Calls drawDiagram/writeResults if generate is true, doTestDiagram
     * otherwise.  Captures a block of code that would otherwise be 
     * oft-repeated throughout the canvas tests.
     * 
     * @param canvas      	The canvas for which results are to validated or 
     *                      generated.
     * @param preserveDiagramValues		
     * 						As the viewport-position and zoom values
     * 						of the canvas's associated diagram object
     * 						are modified during the validation/generation,
     * 						whether to restore these values afterwards. 
  	 */
    public void validateOrGenerateResults(GraphicalEditor editor, boolean generate,
        boolean preserveDiagramValues)
    {
		// remember the diagram zoom and viewport location values, 
		// as they will be changed during the calls below
		Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(editor.getModel());
		float zoom = diagram.getZoom();
		float viewportX = diagram.getViewportx();
		float viewportY = diagram.getViewporty();
		
		try {
			if (generate) {
	            CanvasTestResult result = drawDiagram(editor, true, false, false,
	                new Rectangle(0, 0, 1231, 861));
	            try {
	                writeResults(result.transcript, editor.getModel(), result.image);
	            } catch (Exception e) {
	                CanvasPlugin.logError(
	                    "Exception encountered while writing results file", e);
	            }
	        } else {
	            try {
	                doTestDiagram(editor, editor.getModel().getModel_type(), 
	                    true, false, true);
	            } catch (Exception e) {
	                CanvasPlugin.logError(
	                    "Exception encountered while reading results file", e);
	            }
	        }
		}
		finally {
	        // if specified to do so, restore the diagram zoom and viewport 
		    // location values, which were altered by the calls above
		    if (preserveDiagramValues) {
			    diagram.setZoom(zoom);
			    diagram.setViewportx(viewportX);
			    diagram.setViewporty(viewportY);
		    }
		}
    }
        public void validateOrGenerateResultsGenerics(GraphicalEditor editor, boolean generate)
    {
        validateOrGenerateResultsGenerics(editor, generate, false);
    }
    public void validateOrGenerateResultsGenerics(GraphicalEditor editor, boolean generate,
            boolean preserveDiagramValues)
        {
    		// remember the diagram zoom and viewport location values, 
    		// as they will be changed during the calls below
    		Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(editor.getModel());
    		float zoom = diagram.getZoom();
    		float viewportX = diagram.getViewportx();
    		float viewportY = diagram.getViewporty();
    		
    		try {
    			if (generate) {
    	            CanvasTestResult result = drawDiagram(editor, true, false, false,
    	                new Rectangle(0, 0, 1231, 861));
    	            try {
    	                writeResultsGenerics(result.transcript, editor.getModel(), result.image);
    	            } catch (Exception e) {
    	                CanvasPlugin.logError(
    	                    "Exception encountered while writing results file", e);
    	            }
    	        } else {
    	            try {
    	                doTestDiagramGenerics(editor, editor.getModel().getModel_type(), 
    	                    true, false, true);
    	            } catch (Exception e) {
    	                CanvasPlugin.logError(
    	                    "Exception encountered while reading results file", e);
    	            }
    	        }
    		}
    		finally {
    	        // if specified to do so, restore the diagram zoom and viewport 
    		    // location values, which were altered by the calls above
    		    if (preserveDiagramValues) {
    			    diagram.setZoom(zoom);
    			    diagram.setViewportx(viewportX);
    			    diagram.setViewporty(viewportY);
    		    }
    		}
        }
	protected static UUID getSameAsBaseAttributeUUID(ModelRoot modelRoot) {
		DataType_c[] dataTypes = DataType_c.DataTypeInstances(
			modelRoot,
			new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					return (((DataType_c) candidate).getName()
						.equals("same_as<Base_Attribute>"));
				}
			});
		assertNotNull(dataTypes);
		assertEquals(dataTypes.length, 1);
		return dataTypes[0].getDt_id();
	}
}