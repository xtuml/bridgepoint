//========================================================================
//
//File:      $RCSfile: SynchronizationTestUtils.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:12 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
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
//======================================================================== 
//
package com.mentor.nucleus.bp.core.test.synchronization;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.actions.PublishSynchronizationChanges;
import com.mentor.nucleus.bp.core.ui.actions.PullSynchronizationChanges;
import com.mentor.nucleus.bp.core.ui.dialogs.ScrolledTextDialog;
import com.mentor.nucleus.bp.test.TestUtil;

public class SynchronizationTestUtils {
	public static void deleteExecutableProperty(ExecutableProperty_c ep) {
		ep.getPersistableComponent();
		ep.Dispose();
	}
	
	public static Component_c createComponent(NonRootModelElement pkg, String name) {
		if(pkg instanceof Package_c) {
			Package_c cPkg = (Package_c) pkg;
			cPkg.Newcomponent();
			Component_c[] comps = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(cPkg));
			Component_c comp = comps[comps.length - 1];
			comp.setName(name);
			return comp;
		} else if(pkg instanceof ComponentPackage_c) {
			ComponentPackage_c cPkg = (ComponentPackage_c) pkg;
			cPkg.Newcomponent();
			Component_c[] comps = Component_c.getManyC_CsOnR4604(cPkg);
			Component_c comp = comps[comps.length - 1];
			comp.setName(name);
			return comp;
		}
		return null;
	}

	public static boolean requirementContainsOperation(Requirement_c requirement,
			InterfaceOperation_c operation) {
		return referenceContainsOperation(InterfaceReference_c
				.getOneC_IROnR4009(requirement), operation, false);
	}
	
	public static boolean requirementContainsSignal(Requirement_c requirement,
			InterfaceSignal_c signal) {
		return referenceContainsSignal(InterfaceReference_c
				.getOneC_IROnR4009(requirement), signal, false);
	}
	
	public static boolean provisionContainsOperation(Provision_c provision,
			InterfaceOperation_c operation) {
		return referenceContainsOperation(InterfaceReference_c
				.getOneC_IROnR4009(provision), operation, true);
	}

	public static boolean provisionContainsSignal(Provision_c provision,
			InterfaceSignal_c signal) {
		return referenceContainsSignal(InterfaceReference_c
				.getOneC_IROnR4009(provision), signal, true);
	}

	public static boolean referenceContainsOperation(
			InterfaceReference_c reference,
			final InterfaceOperation_c operation, final boolean provided) {
		ProvidedExecutableProperty_c[] peps = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(reference));
		RequiredExecutableProperty_c[] reps = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(reference));
		if (provided) {
			for(int i = 0; i < peps.length; i++) {
				ExecutableProperty_c epProxy = ExecutableProperty_c
						.getOneC_EPOnR4501(peps[i]);
				if(epProxy.Getcachedname().equals(operation.getName())) {
					return true;
				}
			}
		} else {
			for(int i = 0; i < reps.length; i++) {
				ExecutableProperty_c epProxy = ExecutableProperty_c
						.getOneC_EPOnR4500(reps[i]);
				if(epProxy.Getcachedname().equals(operation.getName())) {
					return true;
				}
			}			
		}
		return false;
	}

	public static boolean referenceContainsSignal(InterfaceReference_c reference,
			final InterfaceSignal_c signal, boolean provided) {
		ProvidedExecutableProperty_c[] peps = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(reference));
		RequiredExecutableProperty_c[] reps = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(reference));
		if (provided) {
			for (int i = 0; i < peps.length; i++) {
				ExecutableProperty_c epProxy = ExecutableProperty_c
						.getOneC_EPOnR4501(peps[i]);
				if (epProxy.Getcachedname().equals(signal.getName())) {
					return true;
				}
			}
		} else {
			for (int i = 0; i < reps.length; i++) {
				ExecutableProperty_c epProxy = ExecutableProperty_c
						.getOneC_EPOnR4500(reps[i]);
				if (epProxy.Getcachedname().equals(signal.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static InterfaceOperation_c createInterfaceOperation(Interface_c iface,
			String name) {
		iface.Newexecutableproperty(false);
		ExecutableProperty_c[] eps = ExecutableProperty_c
				.getManyC_EPsOnR4003(iface);
		ExecutableProperty_c newEp = eps[eps.length - 1];
		newEp.getPersistableComponent();
		InterfaceOperation_c operation = InterfaceOperation_c.getOneC_IOOnR4004(newEp);
		operation.setName(name);
		// there is no persistence occurring, which means the cached
		// name is never configured, simulate that here
		newEp.getName();
		return operation;
	}

	public static InterfaceSignal_c createInterfaceSignal(Interface_c iface,
			String name) {
		iface.Newexecutableproperty(true);
		ExecutableProperty_c[] eps = ExecutableProperty_c
				.getManyC_EPsOnR4003(iface);
		ExecutableProperty_c newEp = eps[eps.length - 1];
		newEp.getPersistableComponent();
		InterfaceSignal_c signal = InterfaceSignal_c.getOneC_ASOnR4004(newEp);
		signal.setName(name);
		// there is no persistence occurring, which means the cached
		// name is never configured, simulate that here
		newEp.getName();
		return signal;
	}
	
	public static Requirement_c createAndFormalizeNewRequirement(
			Component_c component, Interface_c iface) {
		boolean created = component.Newrequirement(component.getId(), false, Gd_c.Null_unique_id(), false);
		TestCase.assertTrue("Test requirement was not created.", created);
		Requirement_c[] requirements = Requirement_c
				.getManyC_RsOnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(component)));
		Requirement_c requirement = requirements[requirements.length - 1];
		requirement.Formalize(iface.getId(), false);
		// make sure C_IR has component set
		InterfaceReference_c ir = InterfaceReference_c.getOneC_IROnR4009(requirement);
		ir.getPersistableComponent();
		return requirement;
	}
	
	public static Provision_c createAndFormalizeNewProvision(Component_c component, Interface_c iface) {
		boolean created = component.Newprovision(component.getId(), false, Gd_c.Null_unique_id(), false);
		TestCase.assertTrue("Test provision was not created.", created);
		Provision_c[] provisions = Provision_c
				.getManyC_PsOnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(component)));
		Provision_c provision = provisions[provisions.length - 1];
		provision.Formalize(iface.getId(), false);
		// make sure C_IR has component set
		InterfaceReference_c ir = InterfaceReference_c.getOneC_IROnR4009(provision);
		ir.getPersistableComponent();
		return provision;
	}

	public static Provision_c createNewProvision(Component_c component) {
		boolean created = component.Newprovision(component.getId(), false, Gd_c.Null_unique_id(), false);
		TestCase.assertTrue("Test provision was not created.", created);
		Provision_c[] provisions = Provision_c
				.getManyC_PsOnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(component)));
		Provision_c provision = provisions[provisions.length - 1];
		// make sure C_IR has component set
		InterfaceReference_c ir = InterfaceReference_c.getOneC_IROnR4009(provision);
		ir.getPersistableComponent();
		return provision;
	}
	
	public static Requirement_c createNewRequirement(Component_c component) {
		boolean created = component.Newrequirement(component.getId(), false, Gd_c.Null_unique_id(), false);
		TestCase.assertTrue("Test requirement was not created.", created);
		Requirement_c[] requirements = Requirement_c
				.getManyC_RsOnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(component)));
		Requirement_c requirement = requirements[requirements.length - 1];
		// make sure C_IR has component set
		InterfaceReference_c ir = InterfaceReference_c.getOneC_IROnR4009(requirement);
		ir.getPersistableComponent();
		return requirement;
	}
	
	public static Interface_c createInterface(NonRootModelElement pkg, String name,
			String[] operationNames, String[] signalNames) {
		if(pkg instanceof Package_c) {
			Package_c iPkg = (Package_c) pkg;
			iPkg.Newinterface();
			Interface_c[] ifaces = Interface_c.getManyC_IsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(iPkg));
			Interface_c iface = ifaces[ifaces.length - 1];
			for(String operationName : operationNames) {
				createInterfaceOperation(iface, operationName);
			}
			for(String signalName : signalNames) {
				createInterfaceSignal(iface, signalName);
			}
			iface.setName(name);
			return iface;
		} else if(pkg instanceof InterfacePackage_c) {
			InterfacePackage_c iPkg = (InterfacePackage_c) pkg;
			iPkg.Newinterface();
			Interface_c[] ifaces = Interface_c.getManyC_IsOnR4303(iPkg);
			Interface_c iface = ifaces[ifaces.length - 1];
			for(String operationName : operationNames) {
				createInterfaceOperation(iface, operationName);
			}
			for(String signalName : signalNames) {
				createInterfaceSignal(iface, signalName);
			}
			iface.setName(name);
			return iface;			
		}
		return null;
	}

	public static Package_c createContainer(String name, SystemModel_c parent) {
		parent.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(parent);
		Package_c pkg = pkgs[pkgs.length - 1];
		pkg.setName(name);
		return pkg;
	}

	public static void synchronizeByPushing(SystemModel_c[] systems) {
		PublishSynchronizationChanges action = new PublishSynchronizationChanges();
		action.selectionChanged(null, new StructuredSelection(systems));
		action.run(null);
	}
	
	public static void synchronizeByPulling(SystemModel_c[] systems) {
		PullSynchronizationChanges action = new PullSynchronizationChanges();
		action.selectionChanged(null, new StructuredSelection(systems));
		action.run(null);
	}

	public static InterfacePackage_c createSpecializedInterfacePackage(String name,
			SystemModel_c system) {
		system.Newinterfacepackage();
		InterfacePackage_c[] pkgs = InterfacePackage_c
				.getManyIP_IPsOnR4302(system);
		InterfacePackage_c pkg = pkgs[pkgs.length - 1];
		pkg.setName(name);
		return pkg;
	}
	
	public static ComponentPackage_c createSpecializedComponentPackage(
			String name, SystemModel_c system) {
		system.Newcomponentpackage();
		ComponentPackage_c[] pkgs = ComponentPackage_c.getManyCP_CPsOnR4602(system);
		ComponentPackage_c pkg = pkgs[pkgs.length - 1];
		pkg.setName(name);
		return pkg;
	}

	protected static List<String> dialogContents = new ArrayList<String>();

	public static void noToSynchronizationDialog() {
		PlatformUI.getWorkbench().getDisplay().timerExec(500, new Runnable() {
			
			@Override
			public void run() {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
				ScrolledTextDialog dialog = null;
				for(int i = 0; i < shells.length; i++) {
					if(shells[i].getData() instanceof ScrolledTextDialog) {
						dialog = (ScrolledTextDialog) shells[i].getData();
					}
				}
				if(dialog != null) {
					Control[] children = dialog.getShell().getChildren();
					Text textField = null;
					for(int i = 0; i < children.length; i++) {
						textField = getTextFromControl(children[i]);
						if(textField != null) {
							break;
						}
					}
					if(textField != null) {
						dialogContents.add(textField.getText());
					}
					Button no = TestUtil.findButton(dialog.getShell(), "Cancel");
					no.notifyListeners(SWT.Selection, new Event());
				}
			}
		});
	}

	protected static Text getTextFromControl(Control control) {
		if(control instanceof Text) {
			return (Text) control;
		}
		if(control instanceof Composite) {
			Composite composite = (Composite) control;
			Control[] children = composite.getChildren();
			for(int i = 0; i < children.length; i++) {
				Text text = getTextFromControl(children[i]);
				if(text != null) {
					return text;
				}
			}
		}
		return null;
	}

	public static String getSynchronizationDialogText() {
		return dialogContents.remove(0);
	}

	public static void okToSynchronizationDialog() {
		PlatformUI.getWorkbench().getDisplay().timerExec(500, new Runnable() {
			
			@Override
			public void run() {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
				ScrolledTextDialog dialog = null;
				for(int i = 0; i < shells.length; i++) {
					if(shells[i].getData() instanceof ScrolledTextDialog) {
						dialog = (ScrolledTextDialog) shells[i].getData();
					}
				}
				if(dialog != null) {
					Control[] children = dialog.getShell().getChildren();
					Text textField = null;
					for(int i = 0; i < children.length; i++) {
						textField = getTextFromControl(children[i]);
						if(textField != null) {
							break;
						}
					}
					if(textField != null) {
						dialogContents.add(textField.getText());
					}
					Button okButton = TestUtil.findButton(dialog.getShell(), "OK");
					okButton.notifyListeners(SWT.Selection, new Event());
				}
			}
		});
	}

	private static int attempts = 0;	
	public static void okWithDoNotShowCheckedToSynchronizationDialog(final String doNotShowLabel, final int timeout) {
		attempts = 0;
		PlatformUI.getWorkbench().getDisplay().timerExec(timeout, new Runnable() {
			
			@Override
			public void run() {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
				ScrolledTextDialog dialog = null;
				for(int i = 0; i < shells.length; i++) {
					if(shells[i].getData() instanceof ScrolledTextDialog) {
						dialog = (ScrolledTextDialog) shells[i].getData();
						break;
					}
				}
				if(dialog != null) {
					Control[] children = dialog.getShell().getChildren();
					Text textField = null;
					for(int i = 0; i < children.length; i++) {
						textField = getTextFromControl(children[i]);
						if(textField != null) {
							break;
						}
					}
					if(textField != null) {
						dialogContents.add(textField.getText());
					}
					Button doNotShow = TestUtil.findButton(dialog.getShell(), doNotShowLabel);
					doNotShow.setSelection(true);
					Button okButton = TestUtil.findButton(dialog.getShell(), "OK");
					okButton.notifyListeners(SWT.Selection, new Event());
				} else {
					if(attempts < 5) {
						attempts++;
						okWithDoNotShowCheckedToSynchronizationDialog(doNotShowLabel, timeout);
					}
				}
			}
		});
	}

	public static void addOALToProvision(Provision_c provision) {
		ProvidedOperation_c po = ProvidedOperation_c
				.getOneSPR_POOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		if(po != null) {
			po.setAction_semantics_internal("// test OAL");
		}
		ProvidedSignal_c ps = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		if(ps != null) {
			ps.setAction_semantics_internal("// test OAL");
		}
	}

	public static void addOALToRequirement(Requirement_c requirement) {
		RequiredOperation_c ro = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		if(ro != null) {
			ro.setAction_semantics_internal("// test OAL");
		}
		RequiredSignal_c rs = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		if(rs != null) {
			rs.setAction_semantics_internal("// test OAL");
		}
	}

	public static void validateOALInProvision(Provision_c provision) {
		ProvidedOperation_c po = ProvidedOperation_c
				.getOneSPR_POOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		if (po != null) {
			Assert.assertTrue("OAL did not remain unchanged.", po
					.getAction_semantics_internal().equals("// test OAL"));
		} else {
			Assert.fail("Could not locate provided operation.");
		}
		ProvidedSignal_c ps = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		if (ps != null) {
			Assert.assertTrue("OAL did not remain unchanged.", ps
					.getAction_semantics_internal().equals("// test OAL"));
		} else {
			Assert.fail("Could not locate provided signal.");
		}

	}

	public static void validateOALInRequirement(Requirement_c requirement) {
		RequiredOperation_c ro = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		if (ro != null) {
			Assert.assertTrue("OAL did not remain unchanged.", ro
					.getAction_semantics_internal().equals("// test OAL"));
		} else {
			Assert.fail("Could not locate required operation.");
		}
		RequiredSignal_c rs = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		if (rs != null) {
			Assert.assertTrue("OAL did not remain unchanged.", rs
					.getAction_semantics_internal().equals("// test OAL"));
		} else {
			Assert.fail("Could not locate required signal.");
		}

	}

	public static ComponentReference_c createComponentReferenceAndAssignToComponent(NonRootModelElement container,
			Component_c component) {
		if(container instanceof Package_c) {
			Package_c pkg = (Package_c) container;
			pkg.Newimportedcomponent();
			ComponentReference_c[] references = ComponentReference_c
					.getManyCL_ICsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pkg));
			ComponentReference_c ref = references[references.length - 1];
			ref.Assigntocomp(component.getId());
			return ref;
		} else {
			ComponentPackage_c pkg = (ComponentPackage_c) container;
			pkg.Newimportedcomponent();
			ComponentReference_c[] references = ComponentReference_c
					.getManyCL_ICsOnR4605(pkg);
			ComponentReference_c ref = references[references.length - 1];
			ref.Assigntocomp(component.getId());
			return ref;
		}
	}
	
	public static void deleteComponent(Component_c component) {
		component.Dispose();
	}

}
