//=====================================================================
//
//File:      $RCSfile: ConvertModels.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:13:03 $
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

import java.io.File;

import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.io.mdl.ExportModel;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.io.sql.ImportBPSql;
import com.mentor.nucleus.bp.test.common.BaseTest;

/**
 * This is actually a utility program, rather than a test class.
 * It converts BP SQL files and earlier-format Tiger model files 
 * to latest-format Tiger model files.  
 * To use this program:
 * 
 * 1) Create a new JUnit plug-in test debug configuration, pointing
 *      at this class
 * 2) Place the files to be converted in the root of the workspace
 *      you associate with the above debug configuration;
 *      also, create a folder called "converted" in that root
 * 3) Run the debug configuration
 * 
 * The converted model files will be written to the "converted" folder
 * in the workspace root.
 * 
 * The JUnit TestCase is employed here merely as a wrapper which 
 * provides integration with the other plug-ins referenced by this
 * class's parent plug-in.  
 */
public class ConvertModels extends TestCase
{
    public void testConvertModels() throws Exception
    {
        // for each file in the workspace root
        String rootPath = CorePlugin.getWorkspace().getRoot().getLocation().toString();
        File folder = new File(rootPath);
        String[] filenames = folder.list();
        final String sqlExtension = "sql";
        final int sqlExtensionLength = sqlExtension.length();
        Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
        SystemModel_c system = new SystemModel_c(Ooaofooa.getDefaultInstance());
        for (int i = 0; i < filenames.length; i++) {
            // if this is not a sql or model file, skip it
            boolean sql = filenames[i].endsWith(sqlExtension);
            boolean model = filenames[i].endsWith(Ooaofooa.MODELS_EXT);
            if (!(sql || model)) continue;
            
            // if this is a sql file
            String path = rootPath + "/" + filenames[i];
            if (sql) {
                // import the model contained in this sql file
                ImportBPSql importSql = new ImportBPSql(path, modelRoot, system, true, true, false);
                importSql.run(new NullProgressMonitor());
            }

            // otherwise
            else {
                // import the earlier-format model contained in this model file
                ImportModel importModel = 
                    new ImportModel(path, modelRoot, system, true, true, false);
                importModel.run(new NullProgressMonitor());
            }
            
            // export the model to a latest-format model file 
            String modelPath = rootPath + "/converted/" 
                + filenames[i].substring(0, filenames[i].length() 
                - (sql ? sqlExtensionLength : Ooaofooa.MODELS_EXT.length()))
                + Ooaofooa.MODELS_EXT;
            ExportModel exportModel = new ExportModel(modelRoot, modelPath, true); 
            exportModel.run(new NullProgressMonitor());
        }
    }
}
