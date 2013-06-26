package com.mentor.nucleus.bp.welcome.gettingstarted;

//====================================================================
//
//	File: $RCSfile: ShowCheatSheetWindowAction.java,v $
//	Version: $Revision: 1.4 $
//	Modified: $Date: 2013/01/10 23:32:19 $
//
//	(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.OpenCheatSheetAction;
import org.eclipse.ui.cheatsheets.OpenCheatSheetFromHelpAction;
import org.eclipse.ui.internal.cheatsheets.handlers.OpenCheatSheetHandler;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizard;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.mc.mc3020.MCBuilderArgumentHandler;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;
import com.mentor.nucleus.bp.welcome.WelcomePlugin;

public class ShowCheatSheetWindowAction implements IIntroAction {

    public void run(IIntroSite site, Properties params) {
  
  
	
 }
}
