.//====================================================================
.//
.// File:      $RCSfile: gen_export_java.inc,v $
.// Version:   $Revision: 1.47 $
.// Modified:  $Date: 2013/05/10 13:29:16 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
.//
.// Generate the Java code that exports the model in a format
.//  that Model Builder can read
.//
.//====================================================================
.//
.//
.include "../org.xtuml.bp.core/color/ooaofooa_package_spec.clr"
.include "../org.xtuml.bp.io.core/arc/export_functions.inc"
.//
.//====================================================================
.//
.function gen_export
  .param string preExpCall
  .param string postExpCall
  .param string callingProject
  .param string class_name
  .param boolean multilevel
  	.invoke var = sourceVariables()
    .assign suite = "BP"
    .assign tool = "6.1D"
 	.if (callingProject == "BP")
	  .include "arc/class_rename.inc"  	
    .elif (callingProject == "NBP")
      .assign suite = "BP"
      .assign tool = "7.1.6"
package org.xtuml.bp.io.mdl;      

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.ComponentTransactionListener;
    .end if

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
.if ( multilevel )
import java.util.HashSet;
import java.util.Set;
.end if

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;

import org.xtuml.bp.core.*;
import org.xtuml.bp.core.common.*;
import org.xtuml.bp.ui.canvas.*;
import org.xtuml.bp.io.core.*;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.IdAssigner;
public class ${class_name} extends CoreExport
{
	NonRootModelElement m_inst;

	protected PrintStream m_fh;
    protected boolean m_export_graphics;
    .if (callingProject == "NBP")
	protected Path m_path;
	private boolean m_isUpdate = false;
	private ByteArrayOutputStream m_updateData;
        .if ( multilevel )
        boolean m_updateActions = false;
        protected Map<Integer,PrintStream> m_afh; // handle for actions
	protected Map<Integer,ByteArrayOutputStream> m_actionUpdateData;
        .end if;
    .end if

    public ${class_name}(${var.main_class_name} modelRoot, String outfileName,boolean export_graphics)
    	throws FileNotFoundException 
    {
		super(modelRoot, CoreExport.YES);
    .if (callingProject == "NBP")
        determineUpdateStatus(modelRoot.getFile(), outfileName);
	.else
		Path path = new Path(outfileName);
		File fh = path.toFile();
		FileOutputStream fs = new FileOutputStream(fh);
		m_fh = new PrintStream(fs);
                .if (multilevel)
                m_afh = new HashMap<Integer,PrintStream>();
                m_actionUpdateData = new HashMap<Integer,ByteArrayOutputStream>();
                int[] dialects = ActionFile.getAvailableDialectCodes();
                for (int i = 0; i < dialects.length; i++) {
                    m_actionUpdateData.put( dialects[i], new ByteArrayOutputStream() );
		    m_afh.put( dialects[i], new PrintStream(m_actionUpdateData.get(dialects[i])) );
                }
                .end if
	.end if
        m_export_graphics = export_graphics;
    }
	.if(multilevel)

	public ExportModelComponent(String outfileName,
			NonRootModelElement element)
			throws FileNotFoundException {
		super((Ooaofooa) element.getModelRoot(), CoreExport.YES);
        m_path = new Path(outfileName);
        File fh = m_path.toFile();
        FileOutputStream fs = new FileOutputStream(fh);
        m_fh = new PrintStream(fs);
		m_inst = element;
		m_export_graphics = true;
            m_afh = new HashMap<Integer,PrintStream>();
            m_actionUpdateData = new HashMap<Integer,ByteArrayOutputStream>();
            int[] dialects = ActionFile.getAvailableDialectCodes();
            for (int i = 0; i < dialects.length; i++) {
                m_actionUpdateData.put( dialects[i], new ByteArrayOutputStream() );
                m_afh.put( dialects[i], new PrintStream(m_actionUpdateData.get(dialects[i])) );
            }
	}
	
	public ExportModelComponent(Ooaofooa modelRoot, ByteArrayOutputStream baos,
			NonRootModelElement element) {
    	super(modelRoot, CoreExport.YES);
    	m_fh = new PrintStream(baos);
        m_afh = new HashMap<Integer,PrintStream>();
        m_actionUpdateData = new HashMap<Integer,ByteArrayOutputStream>();
        int[] dialects = ActionFile.getAvailableDialectCodes();
        for (int i = 0; i < dialects.length; i++) {
            m_actionUpdateData.put( dialects[i], new ByteArrayOutputStream() );
            m_afh.put( dialects[i], new PrintStream(m_actionUpdateData.get(dialects[i])) );
        }
    	m_inst = element;
    	m_export_graphics = true;
	}
	
	public ${class_name}(${var.main_class_name} modelRoot, String outfileName,boolean export_graphics, NonRootModelElement instance)
    	throws FileNotFoundException 
    {
		super(modelRoot, CoreExport.YES);
        PersistableModelComponent mc = 
            PersistenceManager.getComponent(instance);
        IFile f = MdlPlugin.getWorkspace().getRoot().getFile(mc.getFullPath());
        determineUpdateStatus(f, outfileName);
		m_inst = instance;
        m_export_graphics = export_graphics;
    }
	.end if	
    .if (callingProject == "NBP")
    private void determineUpdateStatus(IFile mrFile, String outfileName)
    throws FileNotFoundException {
        m_path = new Path(outfileName);
        // getLocation() is null for some unit tests
        // when creating a new domain, the file doesn't yet exist
		if (mrFile != null && mrFile.getLocation() != null
				&& mrFile.getLocation().equals(m_path)) {
            // updating existing resource
            m_isUpdate = true;
            m_updateData = new ByteArrayOutputStream();
            m_fh = new PrintStream(m_updateData);
            .if (multilevel)
            m_afh = new HashMap<Integer,PrintStream>();
            m_actionUpdateData = new HashMap<Integer,ByteArrayOutputStream>();
            int[] dialects = ActionFile.getAvailableDialectCodes();
            for (int i = 0; i < dialects.length; i++) {
                m_actionUpdateData.put( dialects[i], new ByteArrayOutputStream() );
                m_afh.put( dialects[i], new PrintStream(m_actionUpdateData.get(dialects[i])) );
            }
            .end if
        } else {
            File fh = m_path.toFile();
            FileOutputStream fs = new FileOutputStream(fh);
            m_fh = new PrintStream(fs);
            .if (multilevel)
            m_afh = new HashMap<Integer,PrintStream>();
            m_actionUpdateData = new HashMap<Integer,ByteArrayOutputStream>();
            int[] dialects = ActionFile.getAvailableDialectCodes();
            for (int i = 0; i < dialects.length; i++) {
                m_actionUpdateData.put( dialects[i], new ByteArrayOutputStream() );
                m_afh.put( dialects[i], new PrintStream(m_actionUpdateData.get(dialects[i])) );
            }
            .end if
        }
    }
    .end if	
    .if (multilevel)
	Set<Object> writtenProxies = new HashSet<Object>();
    .end if
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor pm) throws InvocationTargetException {
		try {
			Ooaofgraphics graphicsModelRoot = getGraphicsModelRoot();
			pm.beginTask("Exporting data...", Model_c.ModelInstances(graphicsModelRoot).length);
            if ( m_inst != null )
            {
                .assign disableChangeListeners = false
                .if(preExpCall != "")
                  .assign disableChangeListeners = true
                .end if
                .if(disableChangeListeners)
                   ModelRoot.disableChangeNotification();
				try {
								.end if
                ${preExpCall}
                .if(disableChangeListeners)
                } finally {
										ModelRoot.enableChangeNotification();
                }
                .end if
				.if (multilevel)
        			.select many eos from instances of EO
					.assign proxy_instances = eos
					.assign proxy_instances = proxy_instances - proxy_instances
					.for each eo in eos
					    .//Check if the instance found is not already been checked, this is true in case of nested configurable
					    .//components
					    .assign is_proxy_checked = false
					    .assign proxy_instances_copy = proxy_instances
					    .if (not_empty proxy_instances_copy)
					    	.for each ins in proxy_instances_copy
					    		.if (ins.Name == eo.Name)
					    			.assign is_proxy_checked = true
					    			.break for
					    		.end if
					    	.end for
					    .end if
					    .if ((is_proxy_checked == false) and (eo.componentRoot==true))
						    .assign proxy_instances = proxy_instances | eo
					    .end if
        			.end for
        		       		
        			.for each pi in proxy_instances
                      .select any aep related by pi->AEP[R10]
                      .if(empty aep)
                        .select many otherEOs from instances of EO where (selected.Name == pi.Name)
                        .for each otherEO in otherEOs
                          .select any otherAep related by otherEO->AEP[R10]
                          .if(not_empty otherAep)
                            .assign aep = otherAep
                          .end if
                        .end for
                      .end if
        			  .select one table related by pi->EI[R3]->T[R4];
						.assign className  = "$Cr{table.Name}_c"
        				if(m_inst instanceof ${className}){
	                	    m_fh.print( get_file_header("$Cr{table.Name}", "${tool}", org.xtuml.bp.core.CorePlugin.getPersistenceVersion()) );  //$$NON-NLS-1$$ //$$NON-NLS-2$$
               	        .if(empty aep)
        				    export_${className}((${className}) m_inst, pm, false, true );
        				.else
        				    export_${className}((${className}) m_inst, pm, false, true, true );
        				.end if
        				}
        			.end for
				.end if
                .if(disableChangeListeners)
            		ModelRoot.disableChangeNotification();
					try {
							  .end if
                ${postExpCall}
                .if(disableChangeListeners)
                    } finally {
												ModelRoot.enableChangeNotification();
         		    }
         				.end if
            }
            m_fh.close();
            .if ( multilevel )
            Collection<PrintStream> actionStreams = m_afh.values();
            for ( PrintStream actionStream : actionStreams ) {
                actionStream.close();
            }
            .end if
    .if (callingProject == "NBP")
			if (m_isUpdate) {
				InputStream stream = new ByteArrayInputStream(m_updateData.toByteArray());
                PersistableModelComponent mc = PersistenceManager.getComponent(m_inst);
                IFile f = MdlPlugin.getWorkspace().getRoot().getFile(mc.getFullPath());
				if(!f.exists()) {
					f.create(stream, true, pm);
				} else {
				f.setContents(stream, true, true, pm);
			}
                                .if ( multilevel )
                                int[] dialects = ActionFile.getAvailableDialectCodes();
                                for ( int i = 0; i < dialects.length; i++ ) {
                                    InputStream action_stream = new ByteArrayInputStream(m_actionUpdateData.get(dialects[i]).toByteArray());
                                    IFile af = mc.getActionFile(dialects[i]);
                                    if ( null != af && !af.exists()) {
                                        if ( m_actionUpdateData.get(dialects[i]).toByteArray().length != 0 && m_updateActions ) {
                                            af.create(action_stream, true, pm);
                                        }
                                    } else {
                                        if ( m_actionUpdateData.get(dialects[i]).toByteArray().length != 0 && m_updateActions ) {
                                            af.setContents(action_stream, true, true, pm);
                                        }
                                        else if ( !ComponentTransactionListener.noPersistActions() ) {
                                            af.delete(true, false, pm);
                                        }
                                        else {
                                            // do nothing to the action file
                                        }
                                    }
                                }
                                .end if
			}
    .end if
			pm.done();
		}
		catch( Throwable f )
		{
            throw new InvocationTargetException(f);
		}
    .if (multilevel)
        writtenProxies.clear();
    .end if
	}

    .assign stream_export = false
    .if ( class_name == "ExportModelStream" )
      .assign stream_export = true
    .end if
    .invoke export_methods = gen_export_methods(multilevel, stream_export, "", tool, false);
${export_methods.body}\

    .invoke sql_statements = gen_sql_write_statements(callingProject, multilevel, false);
${sql_statements.body}\

    .if ( multilevel )
      .invoke action_statements = gen_action_write_statements();
${action_statements.body}\
    .end if

.invoke diagram_export = gen_diagram_export_statements();
${diagram_export.body}\

	/**
	 * Returns the connector-text of the given end from the given array
	 * of texts.
	 */
    private FloatingText_c getTextOfEnd(int end, FloatingText_c[] texts)
    {
		// for each text of those given
        for (int i = 0; i < texts.length; i++) {
        	// if this text is of the given end, return it
            if (end == texts[i].getEnd()) return texts[i];
        }
        
        return null;
    }

    /**
     * This holds the ID's of connectors whose texts have been exported. 
     * We use this because we export all three text instances the first time
     * that any one of them is encountered during the persistence tree
     * traversal.  We store the associated connector's ID in this map, so when
     * the other two texts are encountered, we know they have already been 
     * exported.
     */
    private HashMap connectorIds = new HashMap();
}
.end function
