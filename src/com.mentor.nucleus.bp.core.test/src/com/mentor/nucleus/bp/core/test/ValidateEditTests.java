package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.CanvasTransactionListener;

public class ValidateEditTests extends BaseTest {

	private boolean foundDialog = false;
	private String dialogContents = "";

	public void testValidateEditOnElementRename() throws CoreException {
		CanvasTransactionListener.disableReconciler();
		// create a project for testing
		IProject testProject = TestingUtilities.createProject(getName());
		SystemModel_c system = getSystemModel(testProject.getName());
		/**
		 * Create a component, with a nested component and have it referenced in
		 * another file through a component reference
		 */
		Component_c component = null;
		TransactionManager manager = TransactionManager.getSingleton();
		Transaction transaction = null;
		try {
			transaction = manager.startTransaction("Create test elements.",
					Ooaofooa.getDefaultInstance());
			system.Newpackage();
			Package_c pkg = Package_c.getOneEP_PKGOnR1401(system);
			pkg.setName("TestPackage");
			pkg.Newcomponent();
			component = Component_c.getOneC_COnR8001(PackageableElement_c
					.getManyPE_PEsOnR8000(pkg));
			component.setName("TestComponent");
			component.Newcomponent();
			Component_c nestedComponent = Component_c
					.getOneC_COnR8001(PackageableElement_c
							.getManyPE_PEsOnR8003(component));
			nestedComponent.setName("TestNestedComponent");
			system.Newpackage();
			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(system);
			Package_c refContainer = pkgs[pkgs.length - 1];
			refContainer.setName("ReferenceContainer");
			refContainer.Newimportedcomponent();
			ComponentReference_c componentRef = ComponentReference_c
					.getOneCL_ICOnR8001(PackageableElement_c
							.getOnePE_PEOnR8000(refContainer));
			componentRef.Assigntocomp(component.getId());
			manager.endTransaction(transaction);
			TestUtil.changeFileReadonlyStatus(true, componentRef.getFile());
			TestUtil.changeFileReadonlyStatus(true, nestedComponent.getFile());
		} catch (Exception e) {
			// cancel transaction and fail
			if (transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			fail(e.getMessage());
		}
		assertNotNull(component);
		/**
		 * During creation we set the appropriate files to read-only, now
		 * attempt some changes and check for edit validation
		 */
		// scan for read only dialog, if found fail the test
		// first assert that a non-rename change does not result in a
		// read-only warning dialog
		final int duration = 200;
		Thread checkingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
				}
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						Shell activeShell = PlatformUI.getWorkbench()
								.getDisplay().getActiveShell();
						if (activeShell != null && activeShell.getData() instanceof MessageDialog) {
							foundDialog = true;
							((Dialog) activeShell.getData()).close();
						}
					}
				});
			}
		});
		checkingThread.start();
		transaction = null;
		try {
			transaction = manager.startTransaction("Non-rename transaction",
					Ooaofooa.getDefaultInstance());
			component.setMult(99);
			manager.endTransaction(transaction);
			dispatchEvents(0);
		} catch (Exception e) {
			// cancel transaction and fail
			if (transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			fail(e.getMessage());
		}
		assertFalse(
				"Found edit validation dialog when change should not have affected read-only files.",
				foundDialog);
		// now test for a validation dialog
		checkingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
				}
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						Shell activeShell = PlatformUI.getWorkbench()
								.getDisplay().getActiveShell();
						if (activeShell.getData() instanceof Dialog) {
							// compare expected contents with dialog contents
							Dialog dialog = (Dialog) activeShell.getData();
							List list = findList(activeShell);
							if (list == null) {
								// first pass was to enable the extended data
								list = findList(activeShell);
							}
							// fail if list is still empty
							assertTrue(
									"Unexpected dialog encountered, test likely needs modification.",
									list != null);
							String[] items = list.getItems();
							for (int i = 0; i < items.length; i++) {
								dialogContents = dialogContents + items[i]
										+ "\n";
							}
							dialog.close();
						}
					}

					private List findList(Composite composite) {
						Control[] children = composite.getChildren();
						for (int i = 0; i < children.length; i++) {
							if (children[i] instanceof Button) {
								if (((Button) children[i]).getText().equals(
										"&Details >>")) {
									children[i].notifyListeners(SWT.Selection,
											new Event());
									while (PlatformUI.getWorkbench()
											.getDisplay().readAndDispatch())
										;
								}
							}
							if (children[i] instanceof List) {
								return (List) children[i];
							} else if (children[i] instanceof Composite) {
								List result = findList((Composite) children[i]);
								if (result != null) {
									return result;
								}
							}
						}
						return null;
					}
				});
			}
		});
		checkingThread.start();
		transaction = null;
		try {
			transaction = manager.startTransaction("Rename transaction",
					Ooaofooa.getDefaultInstance());
			component.setName("TestingComponentRenamed");
			manager.endTransaction(transaction);
			dispatchEvents(0);
		} catch (Exception e) {
			// cancel transaction and fail
			if (transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			fail(e.getMessage());
		}
		String expected = "/testValidateEditOnElementRename/models/testValidateEditOnElementRename/"
				+ "TestPackage/TestComponent/TestNestedComponent/TestNestedComponent.xtuml\n"
				+ "/testValidateEditOnElementRename/models/testValidateEditOnElementRename/"
				+ "ReferenceContainer/ReferenceContainer.xtuml\n";
		assertEquals("Expected read-only files not listed.", expected,
				dialogContents);
		CanvasTransactionListener.enableReconciler();
	}
}
