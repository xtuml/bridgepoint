//========================================================================
//
//File:      $RCSfile: AbstractStreamImportFactory.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/06/21 02:38:40 $
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.core.ui;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;

public abstract class AbstractStreamImportFactory {

	public abstract IModelImport create(InputStream inStream,
			Ooaofooa modelRoot, boolean clearDatabase, IPath projectRelativePath) throws IOException;

	public static AbstractStreamImportFactory getInstance(String bundle,
			String className) {
		try {
			Bundle io_mdl = Platform.getBundle(bundle);
			Class factoryClass = io_mdl.loadClass(bundle + "." + className);
			return (AbstractStreamImportFactory) factoryClass.newInstance();
		} catch (ClassNotFoundException cnf) {
			CorePlugin.logError("Problem creating factory " + className, cnf); //$NON-NLS-1$
			return null;
		} catch (IllegalAccessException i) {
			CorePlugin.logError("Problem creating factory " + className, i); //$NON-NLS-1$
			return null;
		} catch (InstantiationException i) {
			CorePlugin.logError("Problem creating factory " + className, i); //$NON-NLS-1$
			return null;
		}
	}

}