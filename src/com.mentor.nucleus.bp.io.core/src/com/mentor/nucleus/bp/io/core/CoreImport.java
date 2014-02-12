//====================================================================
//
// File:      $RCSfile: CoreImport.java,v $
// Version:   $Revision: 1.33 $
// Modified:  $Date: 2013/06/12 13:08:03 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.io.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SpecificationPackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public abstract class CoreImport implements IModelImport {
    public boolean m_success;

    public String m_errorMessage;

    protected boolean m_clear_database = true;

    private int m_numInserts = 0;

    private Ooaofooa m_modelRoot = null;

    private Ooaofgraphics m_graphicsModelRoot = null;

    protected String m_fileName;

    File m_inFile;

    private StringBuffer inputBuffer = null;

    public FileHeader m_header = null;

    protected boolean m_templateFile;

    protected NonRootModelElement rootModelElement = null;
    public static boolean createUniqueIds = true;

    /**
     * The version string found in BP SQL model files.
     */
    public static String bpSqlVersion = "6.1D";

    public CoreImport(Ooaofooa modelRoot, InputStream inStream, boolean clearDatabase, boolean templateFile)
            throws IOException {
        m_success = false;
        m_errorMessage = ""; //$NON-NLS-1$
        m_modelRoot = modelRoot;
        m_fileName = "";
        m_clear_database = clearDatabase;
        m_templateFile = templateFile;
        read(inStream);
    }

    public CoreImport(Ooaofooa modelRoot, String inFile, boolean clearDatabase, boolean templateFile)
            throws FileNotFoundException {
        m_success = false;
        m_errorMessage = ""; //$NON-NLS-1$
        m_modelRoot = modelRoot;
        m_fileName = inFile;
        m_inFile = new File(inFile);
        m_clear_database = clearDatabase;
        m_templateFile = templateFile;
        if (!m_inFile.exists() || !m_inFile.isFile())
            throw new FileNotFoundException(inFile + " not found");
    }

    public CoreImport(IPath inFile) throws FileNotFoundException {
        m_success = false;
        m_errorMessage = ""; //$NON-NLS-1$
        m_modelRoot = null;
        m_fileName = inFile.toString();
        m_inFile = inFile.toFile();
        if (!m_inFile.exists() || !m_inFile.isFile())
            throw new FileNotFoundException(inFile + " not found");
    }

    protected void readHeader() {
        if (m_header == null) {
            m_header = new FileHeader();
            try {
                if (inputBuffer != null) {
                    m_header.read(new StringReader(inputBuffer.toString()));
                } else if (m_inFile != null) {
                    m_header.read(m_inFile);
                } else {
                    IllegalStateException x = new IllegalStateException("Input file or source not set");
                    x.fillInStackTrace();
                    throw x;
                }
            } catch (IOException e) {
                IllegalStateException x = new IllegalStateException("Error reading header");
                x.fillInStackTrace();
                throw x;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.mentor.nucleus.bp.core.ui.IModelImport#getHeader()
     */
    public IFileHeader getHeader() {
        readHeader();
        return m_header;
    }

    public abstract boolean isExpectedContent(String actualContent);

    public abstract boolean isExpectedVersion(String actualVersion);

    public abstract void performCleanUp(IProgressMonitor pm) throws IOException;

    public abstract String processValue(String table, int col_num, String val);

    public abstract void processStatement(Ooaofooa modelRoot, String table, Vector parms, Vector rawParms,
            int numParms, IProgressMonitor pm);

    public abstract void preprocessStatement(String stmt, BufferedReader is) throws IOException;

    public abstract int postprocessStatements();

    protected Ooaofooa getModelRoot() {
        return m_modelRoot;
    }

    protected String getFilename() {
        return m_fileName;
    }

    protected void read(File file) throws IOException {
    	InputStreamReader reader = null;
        try {
			reader = new InputStreamReader(new FileInputStream(file),
					ResourcesPlugin.getEncoding());
            read(reader);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    protected void read(InputStream inStream) throws IOException {
    	InputStreamReader reader = new InputStreamReader(inStream);
        read(reader);
    }

    protected void read(Reader reader) throws IOException {
        inputBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(reader);
        final int bufferLength = 8192;
        char[] charBuffer = new char[bufferLength];
        int readLength;
        while ((readLength = bufferedReader.read(charBuffer, 0, bufferLength)) > 0) {
            inputBuffer.append(charBuffer, 0, readLength);
        }
    }

    public Reader getInputReader() throws IOException {
        if (inputBuffer == null)
            read(m_inFile);

        return new StringReader(inputBuffer.toString());
    }

    public int countAndValidateInsertStatements() {
        int numInserts = 0;
        try {
            BufferedReader dis = new BufferedReader(getInputReader());
            readHeader();
            String s = dis.readLine();
            while (s != null) {
                if (s.toUpperCase().indexOf("INSERT") == 0) //$NON-NLS-1$
                {
                    numInserts += 1;
                    preprocessStatement(s, dis);
                }
                s = dis.readLine();
            }
            dis.close();
        } catch (IOException x) {
            m_errorMessage = x.toString();
            return PPS_ERROR;
        }

        if (m_header == null || !m_header.isValid()) {
            if (m_fileName != null) {
                m_errorMessage = "The file " + m_fileName;
            } else {
                m_errorMessage = "The file";
            }
            m_errorMessage += " is not a valid UML model file.";
            return PPS_ERROR;
        }
        int pps = postprocessStatements();
        m_numInserts = numInserts;
        if (pps == PPS_OK)
            return numInserts;
        else
            return pps;
    }

    protected boolean doLoadSql(IProgressMonitor pm) {
        // here's where the code is actually read
        try {
            // read the version number from the file, in case
            // countAndValidateInsertStatements() hasn't been called
            Reader reader = getInputReader();
            readHeader();

            performCleanUp(pm);

            SqlLexer lexer = new SqlLexer(reader);

            // add 401 for the batchRelateAll additions (found by searching for pm.worked(1) in ImportModelComponent
            pm.beginTask("Importing data...", m_numInserts + 401);
            SqlParser parser = new SqlParser(lexer, m_modelRoot, this);
            // Parse the input expression
            parser.sql_file(pm);
            if (parser.m_output != "") //$NON-NLS-1$
            {
                m_errorMessage = parser.m_output;
                pm.done();
                return false;
            } else {
                pm.done();
                return true;
            }
        } catch (IOException e) {
            m_errorMessage = "IO exception: " + e; //$NON-NLS-1$
        } catch (TokenStreamException e) {
            m_errorMessage = "Stream exception: " + e; //$NON-NLS-1$
        } catch (RecognitionException e) {
            m_errorMessage = "Recog exception: " + e; //$NON-NLS-1$
        }
        return false;
    }

    static protected String removeTics(String p_input) {
    	if (p_input == null)
    		return "";
        String out = p_input;
        if (out.startsWith("'")) {
            out = out.substring(1);
        }
        if (out.endsWith("'")) {
            out = out.substring(0, out.length() - 1);
        }
        out = out.replaceAll("''", "'");

        return out;
    }

    protected Ooaofgraphics getGraphicsModelRoot() {
        if (m_graphicsModelRoot == null) {
            m_graphicsModelRoot = Ooaofgraphics.getInstance(m_modelRoot.getId());
        }
        return m_graphicsModelRoot;
    }

    protected void setGraphicalRepresents() {
        Model_c[] mdls = Model_c.ModelInstances(getGraphicsModelRoot());
        for (int j = 0; j < mdls.length; j++) {
            mdls[j].setRepresents(Cl_c.Getinstancefromooa_id(m_modelRoot, mdls[j].getOoa_id(), mdls[j].getOoa_type()));
        }
        GraphicalElement_c[] ges = GraphicalElement_c.GraphicalElementInstances(getGraphicsModelRoot());
        for (int j = 0; j < ges.length; j++) {
            setGraphicalRepresents(ges[j]);
        }
    }
    
    protected void setGraphicalRepresents(GraphicalElement_c element) {
    	element.setRepresents(Cl_c.Getinstancefromooa_id(m_modelRoot, element.getOoa_id(), element.getOoa_type()));
    }

    private static String defaultDataTypePackageName = Ooaofooa.Getcoredatatypespackagename(null);

    private static String defaultFunctionPackageName = "Functions"; //$NON-NLS-1$

    private static String defaultExternalEntityPackageName = "External Entities"; //$NON-NLS-1$

    protected FunctionPackage_c createDefaultFunctionPackage(Ooaofooa modelRoot) {
        FunctionPackage_c fpk = new FunctionPackage_c(modelRoot);
        fpk.relateAcrossR29To(Domain_c.DomainInstance(modelRoot));
        fpk.setName(defaultFunctionPackageName);
        return fpk;
    }

    protected FunctionPackage_c getDefaultFunctionPackage(Ooaofooa modelRoot) {
        FunctionPackage_c pkg = FunctionPackage_c.FunctionPackageInstance(modelRoot, new ClassQueryInterface_c() {
            public boolean evaluate(Object x) {
                FunctionPackage_c selected = (FunctionPackage_c) x;
                return selected.getName().equals(defaultFunctionPackageName);
            }
        });
        return pkg;
    }

    protected DataTypePackage_c createDefaultDataTypePackage(Ooaofooa modelRoot) {
        DataTypePackage_c dpk = new DataTypePackage_c(modelRoot);
		SpecificationPackage_c pkg = SpecificationPackage_c
				.getOneEP_SPKGOnR1402(dpk, false);
		if(pkg == null) {
			pkg = new SpecificationPackage_c(modelRoot);
			pkg.relateAcrossR1402To(dpk, false);
		}
        dpk.relateAcrossR40To(Domain_c.DomainInstance(modelRoot));
        dpk.setName(defaultDataTypePackageName);
        return dpk;
    }

    protected DataTypePackage_c getDefaultDataTypePackage(Ooaofooa modelRoot) {
        DataTypePackage_c pkg = DataTypePackage_c.DataTypePackageInstance(modelRoot, new ClassQueryInterface_c() {
            public boolean evaluate(Object x) {
                DataTypePackage_c selected = (DataTypePackage_c) x;
                return selected.getName().equals(defaultDataTypePackageName);
            }
        });
        return pkg;
    }

    protected ExternalEntityPackage_c createDefaultExternalEntityPackage(Ooaofooa modelRoot) {
        ExternalEntityPackage_c epk = new ExternalEntityPackage_c(modelRoot);
        epk.relateAcrossR36To(Domain_c.DomainInstance(modelRoot));
        epk.setName(defaultExternalEntityPackageName);
        return epk;
    }

    protected ExternalEntityPackage_c getDefaultExternalEntityPackage(Ooaofooa modelRoot) {
        ExternalEntityPackage_c pkg = ExternalEntityPackage_c.ExternalEntityPackageInstance(modelRoot,
                new ClassQueryInterface_c() {
                    public boolean evaluate(Object x) {
                        ExternalEntityPackage_c selected = (ExternalEntityPackage_c) x;
                        return selected.getName().equals(defaultExternalEntityPackageName);
                    }
                });
        return pkg;
    }

    public NonRootModelElement getRootModelElement() {
        return rootModelElement;
    }

    public void finishLoad(IProgressMonitor pm) {
    }

    public void finishComponentLoad(IProgressMonitor pm, boolean searchAllRootsForBatchRelate) {
    }

    class FileHeader implements IFileHeader {
        /**
         * Prior to persistence version 7.1.1, the product version number found
         * in the file header is used for fileFormatVersion. After that point,
         * there is a separate persistence/file format version number in the
         * header which is used, instead. This number usually starts with the
         * product number (e.g. "7.1") and adds on a suffix (e.g. ".1").
         */

        String productVersion;

        String fileFormatVersion;

        String componentType;

        boolean valid = false;

        public String getFileFormatVersion() {
            return fileFormatVersion;
        }

        public String getModelComponentType() {
            return componentType;
        }

        public String getProductVersion() {
            return productVersion;
        }

        public boolean isValid() {
            return valid;
        }

        public void read(File file) throws IOException {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                read(reader);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        // does not close the stream, its responsibility of caller to close
        // inputstream
        public void read(InputStream in) throws IOException {
            read(new InputStreamReader(in));
        }

        // does not close the stream, its responsibility of caller to close
        // inputstream
        public void read(Reader in) throws IOException {
            read(new BufferedReader(in));
        }

        public void read(BufferedReader reader) throws IOException {
        	String headerLine = reader.readLine();
        	String[] words = null;
        	while (headerLine != null){
        		headerLine = headerLine.trim();
        		// ignore comments
        		if(headerLine.startsWith("--")){               //$NON-NLS-1$
        			String[] splitted = headerLine.split(" "); //$NON-NLS-1$
        			if(splitted.length >= 2 && splitted[1].equals("BP")){                 //$NON-NLS-1$
        				words = splitted;
        				break;
        			}
        		}else if (headerLine.length() == 0){
        			// we hit something else which is neither comment or header
        			// so it is not valid.
        			throw new IOException("header not present");
        		}
        		headerLine = reader.readLine();
        	}

            if (words == null || words.length < 5) {
                throw new IOException("Invalid file header");
            }

            if (words.length >= 9) {
                productVersion = words[2];
                fileFormatVersion = words[8];
            } else {
                productVersion = words[2];
                fileFormatVersion = productVersion;
            }

            componentType = words[4];

            if (isExpectedVersion(productVersion) //$NON-NLS-1$
                    && words[3].equals("content:") //$NON-NLS-1$
                    && isExpectedContent(componentType)) {
                valid = true;
            }
        }
    	@Override
		public SystemModel_c readSystemModel(InputStream in) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			char[] buffer = new char[500];
			reader.readLine();
			reader.read(buffer);
			reader.close();
			String xtumlFile = String.copyValueOf(buffer);
			Pattern sysID_Pattern = Pattern.compile("(\\w+-)+\\w+");
			Matcher match_sysID = sysID_Pattern.matcher(xtumlFile);
			while (match_sysID.find()) {
				String sys_idString = match_sysID.group();
				UUID sys_id = IdAssigner.createUUIDFromString(sys_idString);
				InstanceList instances = ((ModelRoot) Ooaofooa
						.getDefaultInstance())
						.getInstanceList(SystemModel_c.class);
				SystemModel_c source = null;
				synchronized (instances) {
					source = (SystemModel_c) instances
							.get(new Object[] { sys_id });
					if (source != null) {
						return source;
					} else {
						return null;
					}
				}
			}
			return null;
		}
    }

	protected boolean isDomainIdUnique(Domain_c domain) {
		SystemModel_c systemModel = SystemModel_c.getOneS_SYSOnR28(domain);
		if (systemModel != null) {
			Object[] doms = systemModel.getChildren();
			for (int i = 0; i < doms.length; i++) {
				if (doms[i] != domain) {
					if(doms[i] instanceof Domain_c) {
						Domain_c dom = (Domain_c) doms[i];
						// if any domains exist with the same id
						if (dom.getDom_id().equals(domain.getDom_id())) {
							// this id is not unique
							return false;
						}
					}
				}
			}
			return true;
		}
		// if we couldn't find a system-model
		// we increase the id value by default
		return false;
	}
    
	public boolean getSuccessful() {
		return m_success;
	}
	
	public void upgradeStreamData(Ooaofooa root) {};
}