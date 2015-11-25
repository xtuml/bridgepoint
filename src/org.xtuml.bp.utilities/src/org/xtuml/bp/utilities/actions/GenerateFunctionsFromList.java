package org.xtuml.bp.utilities.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;


public class GenerateFunctionsFromList implements IActionDelegate {
	private ISelection selection;
	private static final int FUNCTION = 0;
	private static final int PARAMETER = 1;
	private Package_c parentPackage;

	class ElementData {
		private String name;
		private int type;
		private ElementData[] children;
		private String dataType;
		private Function_c parentFunction;
		private NonRootModelElement createdElement;
		private String actionLanguage;

		public ElementData(String name, int type, ElementData[] children, String dataType) {
			this.name = name;
			this.type = type;
			this.children = children;
			this.dataType = dataType;
		}

		public void setActionLanguage(String actionLanguage) {
			this.actionLanguage = actionLanguage;
		}

		public void create() {
			switch (this.type) {
			case FUNCTION:
				GenerateFunctionsFromList.this.parentPackage.Newfunction();
				Function_c[] functions = Function_c.getManyS_SYNCsOnR8001(
						PackageableElement_c.getManyPE_PEsOnR8000(GenerateFunctionsFromList.this.parentPackage));
				this.createdElement = functions[(functions.length - 1)];
				this.createdElement.setName(this.name);
				((Function_c) this.createdElement).setAction_semantics_internal(this.actionLanguage);
				DataType_c returnType = getDataTypeByName();
				((Function_c) this.createdElement).relateAcrossR25To(returnType);
				createChildren(this.children);
				break;
			case PARAMETER:
				this.parentFunction.Newparameter();
				FunctionParameter_c[] params = FunctionParameter_c.getManyS_SPARMsOnR24(this.parentFunction);
				this.createdElement = params[(params.length - 1)];
				this.createdElement.setName(this.name);
				DataType_c paramType = getDataTypeByName();
				((FunctionParameter_c) this.createdElement).relateAcrossR26To(paramType);
				break;
			}
		}

		private DataType_c getDataTypeByName() {
			PackageableElement_c pe = null;
			String defaultType = "void";
			switch (this.type) {
			case FUNCTION:
				pe = PackageableElement_c.getOnePE_PEOnR8001((Function_c) this.createdElement);
				break;
			case PARAMETER:
				defaultType = "integer";
				pe = PackageableElement_c.getOnePE_PEOnR8001(this.parentFunction);
				break;
			}
			UUID dtId = pe.Resolvedatatyperelativetoself(defaultType, this.dataType);
			DataType_c dt = (DataType_c) pe.getModelRoot().getInstanceList(DataType_c.class).getGlobal(dtId);
			return dt;
		}

		public void createChildren(ElementData[] children) {
			ElementData[] arrayOfElementData;
			int j = (arrayOfElementData = children).length;
			for (int i = 0; i < j; i++) {
				ElementData data = arrayOfElementData[i];
				data.parentFunction = ((Function_c) this.createdElement);
				data.create();
			}
		}
	}

	public void run(IAction action) {
		IStructuredSelection ss = (IStructuredSelection) this.selection;
		for (Iterator<?> i = ss.iterator(); i.hasNext();) {
			this.parentPackage = ((Package_c) i.next());
			String functionListFile = getFunctionListFile();
			if ( null == functionListFile ) { return; } // dialog cancelled
			File functionFile = new File(functionListFile);
			if (functionFile.exists()) {
				Transaction transaction = null;
				try {
					transaction = TransactionManager.getSingleton().startTransaction("Create functions from list",
							new ModelElement[] { Ooaofooa.getDefaultInstance() });
					ElementData[] functions = getElementData(getFileContents(functionFile));
					ElementData[] arrayOfElementData1;
					int j = (arrayOfElementData1 = functions).length;
					for (int k = 0; k < j; k++) {
						ElementData function = arrayOfElementData1[k];
						function.create();
					}
					TransactionManager.getSingleton().endTransaction(transaction);
				} catch (Exception e) {
					if (transaction != null) {
						TransactionManager.getSingleton().cancelTransaction(transaction, e);
					}
					CorePlugin.logError("Unable to start transaction for function creation.", e);
				}
			}
		}
	}

	private ElementData[] getElementData(String[] lines) {
		List<ElementData> functions = new ArrayList();
		ElementData lastFunction = null;
		boolean collectingOAL = false;
		String oal = "";
		String[] arrayOfString1;
		int j = (arrayOfString1 = lines).length;
		for (int i = 0; i < j; i++) {
			String line = arrayOfString1[i];
			if (collectingOAL) {
				if (line.contains("@@@")) {
					collectingOAL = false;
					lastFunction.setActionLanguage(oal);
					oal = "";
				} else {
					oal = oal + line + "\n";
				}
			} else {
				String[] data = line.split("@@");
				if (data[(data.length - 1)].equals("@")) {
					collectingOAL = true;
				}
				String functionName = "";
				List<ElementData> children = new ArrayList();
				String dataType = "";
				for (int k = 0; k < data.length; k++) {
					String[] split = data[k].split("@");
					if (split.length != 0) {
						if (k == 0) {
							functionName = split[0];
							dataType = split[1];
						} else {
							ElementData parameter = new ElementData(split[0], 1, new ElementData[0], split[1]);
							children.add(parameter);
						}
					}
				}
				lastFunction = new ElementData(functionName, 0,
						(ElementData[]) children.toArray(new ElementData[children.size()]), dataType);
				functions.add(lastFunction);
			}
		}
		return (ElementData[]) functions.toArray(new ElementData[functions.size()]);
	}

	private String[] getFileContents(File functionFile) {
		List<String> contents = new ArrayList();
		try {
			Scanner in = new Scanner(new FileReader(functionFile));
			while (in.hasNextLine()) {
				contents.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			CorePlugin.logError("Unable to read function file.", e);
		}
		return (String[]) contents.toArray(new String[contents.size()]);
	}

	private String getFunctionListFile() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		dialog.setText("Select functions file to import");
		return dialog.open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
}