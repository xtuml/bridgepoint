//====================================================================
//
// File:      $RCSfile: DocGenTask.java,v $
// Version:   $Revision: 1.5 $
// Modified:  $Date: 2013/01/10 23:43:42 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.docgen.ant.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.mentor.nucleus.bp.core.EclipseOoaofooa;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.docgen.generator.Generator;


public class DocGenTask extends Task {

	public void execute() throws BuildException {
		 if (system != null) {
			 Generator.genAll(system);
		 }
		 else {
			 if (systemName == null) {
				 System.err.println("No System defined for document generation.");
			 }
			 else {
				 System.err.println("System " + systemName + " not found.");
			 }
		 }
	 }

	private SystemModel_c system = null;
	private String systemName = null;
	public void setSystem(String systemName) {
		try {
		this.systemName = systemName;
		if (this.systemName.startsWith("\\")) {
			this.systemName = this.systemName.substring(1);
		}
		System.out.println("Looking for system: " + systemName);
		PersistenceManager.getDefaultInstance();
		Ooaofooa sysRoot = Ooaofooa.getDefaultInstance();
		InstanceList instances = sysRoot.getInstanceList(SystemModel_c.class);
		if (instances == null) {
			System.err.println("No System Model instance list found");
		}
		else {
			System.out.println("Found:" + instances.toString());
		}
		for (Object inst: instances) {
			if (inst instanceof SystemModel_c) {
				SystemModel_c sys = (SystemModel_c) inst;
				if (sys.getName().equals(this.systemName)) {
					system = sys;
					break;
				}
				else {
					System.out.println(sys.getName());
				}
			}
		}
		}catch (Exception e ) {
		  System.err.println("Error encountered: " + e);
		}
	}


}
