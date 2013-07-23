//========================================================================
//
//File:      $RCSfile: AbstractStreamExportFactory.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/17 03:40:05 $
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.core.ui;

import java.io.ByteArrayOutputStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public abstract class AbstractStreamExportFactory {

	public abstract IRunnableWithProgress create(
			ByteArrayOutputStream outStream, NonRootModelElement[] elements,
			boolean exportGraphics, boolean fileExportInProgress, boolean exportUsingCachedIds);

	public abstract IRunnableWithProgress create(
			ByteArrayOutputStream outStream, NonRootModelElement[] elements,
			boolean exportGraphics, boolean fileExportInProgress);
	
	public static AbstractStreamExportFactory getInstance(String bundle,
			String className) {
		try {
			Bundle io_mdl = Platform.getBundle(bundle);
			Class<?> factoryClass = io_mdl.loadClass(bundle + "." + className);
			return (AbstractStreamExportFactory) factoryClass.newInstance();
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