package com.mentor.nucleus.bp.core.ui.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelStreamProcessor;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;

public class CreateBuiltInExternalEntitiesAction implements IActionDelegate {

	private IStructuredSelection selection;

	@Override
	public void run(IAction action) {
		// get the string contents for the paste
		String ees = getEEString();
		// perform the entire action in a Transaction so that it is 
		// undoable
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create built-in external entities.",
					new ModelElement[] {
							Ooaofooa.getDefaultInstance(),
							(ModelElement) OoaofgraphicsUtil.getGraphicsRoot(
									Ooaofooa.getDefaultInstance().getId(),
									OoaofgraphicsUtil.getGraphicsClass()) });
			// the UI will guarantee that the selection contains only packages
			for(Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
				Package_c pkg = (Package_c) iterator.next();
				ModelStreamProcessor processor = new ModelStreamProcessor();
				processor.setDestinationElement(pkg);
				processor.setContents(ees);
				ByteArrayInputStream in = new ByteArrayInputStream(
							ees.getBytes());
				IModelImport importer = CorePlugin
							.getStreamImportFactory()
							.create(
									in,
									Ooaofooa
											.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME),
									true,
									pkg
											.getPersistableComponent()
											.getFile().getFullPath());
				processor.runImporter(importer, new NullProgressMonitor());
				processor.processFirstStep(new NullProgressMonitor());
				processor.processSecondStep(new NullProgressMonitor());
				TransactionManager.getSingleton().endTransaction(transaction);
			}
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction);
			}
			CorePlugin.logError("Unable to create built-in external entities.", e);
		}
	}

	private String getEEString() {
		Bundle bpBundle = Platform.getBundle("com.mentor.nucleus.bp.pkg");
		Path eePath = new Path("Built-inExternalEntities/ee.xtuml");
		URL fileURL = FileLocator.find(bpBundle, eePath, null);
		String fileName = null;
		try {
			fileURL = FileLocator.resolve(fileURL);
			fileName = fileURL.getFile();
			StringBuffer buffer = read(new FileReader(new File(fileName)));
			return buffer.toString();
		} catch (IOException e) {
			String msg = "Unable to locate: Built-inExternalEntities/ee.xtuml.  ";
			if (bpBundle == null) {
				msg += "Unable to get bundle: com.mentor.nucleus.bp.pkg  ";
			}
			if (fileURL == null) {
				msg += "The file URL is null. ";
			} else {
				msg += "The file URL is: \n";
				msg += "\tProtocol: " + fileURL.getProtocol() + "\n";
				msg += "\tPort: " + fileURL.getPort() + "\n";
				msg += "\tHost: " + fileURL.getHost() + "\n";
				msg += "\tFile: " + fileURL.getFile() + "\n";
				msg += "\tExternalForm: " + fileURL.toExternalForm() + "\n";
			}
			CorePlugin.logError(msg, e); //$NON-NLS-1$
		}
		return "";
	}
	
    protected StringBuffer read(Reader reader) throws IOException {
        StringBuffer inputBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(reader);
        final int bufferLength = 8192;
        char[] charBuffer = new char[bufferLength];
        int readLength;
        while ((readLength = bufferedReader.read(charBuffer, 0, bufferLength)) > 0) {
            inputBuffer.append(charBuffer, 0, readLength);
        }
        return inputBuffer;
    }

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// just cache the selection
		this.selection = (IStructuredSelection) selection;
	}

}
