package com.mentor.nucleus.bp.welcome.cheatsheets.utilities;
//====================================================================
//
//File: $RCSfile: CheatSheetsUtilities.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:10 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.rmi.activation.Activatable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.internal.dtree.ObjectNotFoundException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class CheatSheetsUtilities {

	public static Shape_c[] filterShape(Shape_c[] shapes, int ooaType) {
		if (shapes.length == 0) {
			return null;
		}
		Vector<Shape_c> filtered = new Vector<Shape_c>();
		for (int i = 0; i < shapes.length; i++) {
			GraphicalElement_c ge = GraphicalElement_c
			.getOneGD_GEOnR2(shapes[i]);
			if (ge.getOoa_type() == ooaType) {
				filtered.add(shapes[i]);
			}
		}
		Shape_c[] result = new Shape_c[filtered.size()];
		filtered.copyInto(result);
		return result;
	}

	public static Shape_c getDesiredShape(Shape_c[] shapes, String name) {
		for (int i = 0; i < shapes.length; i++) {
			GraphicalElement_c ge = GraphicalElement_c
			.getOneGD_GEOnR2(shapes[i]);
			Object represents = ge.getRepresents();
			if (represents instanceof NonRootModelElement) {
				NonRootModelElement element = (NonRootModelElement) represents;
				if (element.getName().equalsIgnoreCase(name)) {
					return shapes[i];
				}
			}
		}
		return null;
	}

	public static Connector_c[] filterConnector(Connector_c[] connectors,
			int ooaType) {
		if (connectors.length == 0) {
			return null;
		}
		Vector<Connector_c> filtered = new Vector<Connector_c>();
		for (int i = 0; i < connectors.length; i++) {
			GraphicalElement_c ge = GraphicalElement_c
			.getOneGD_GEOnR2(connectors[i]);
			if (ge.getOoa_type() == ooaType) {
				filtered.add(connectors[i]);
			}
		}
		Connector_c[] result = new Connector_c[filtered.size()];
		filtered.copyInto(result);
		return result;
	}

	public static Connector_c getDesiredConnector(Connector_c[] connectors,
			String name) {
		for (int i = 0; i < connectors.length; i++) {
			GraphicalElement_c ge = GraphicalElement_c
			.getOneGD_GEOnR2(connectors[i]);
			Object represents = ge.getRepresents();
			if (represents instanceof NonRootModelElement) {
				NonRootModelElement element = (NonRootModelElement) represents;
				if (element.getName().equalsIgnoreCase(name)) {
					return connectors[i];
				}
			}
		}
		return null;
	}

	public static NonRootModelElement findElement(String ProjectName,
			final String ContainerPackageName, String OoaType, final String Name,
			String IndirectParentName, String DirectParentName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				ProjectName);
		SystemModel_c systemModel = ProjectUtilities.getSystemModel(project);
		if (OoaType.equalsIgnoreCase("System Model")) {
			return systemModel;

		}
		if (OoaType.equalsIgnoreCase("Package")) {

			Package_c pkg = Package_c.getOneEP_PKGOnR1405(systemModel,
					new ClassQueryInterface_c() {
				@Override
				public boolean evaluate(Object candidate) {
					Package_c selected = (Package_c) candidate;
					return selected.getName().equals(Name);
				}
			});
			return pkg;

		} else if (OoaType.equalsIgnoreCase("Component")) {
			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(systemModel);
			Component_c component = null;
			for (int i = 0; i < pkgs.length; i++) {
				Component_c[] candidates = Component_c
				.ComponentInstances(pkgs[i].getModelRoot());
				for (int j = 0; j < candidates.length; j++) {
					if (candidates[j].getName().equals(Name)) {
						component = candidates[j];
						return component;
					}
				}
			}
			return null;
		} else if (OoaType.equalsIgnoreCase("Component Reference")) {

		} else {
			Package_c[] pkgs = null;
			if ( ContainerPackageName == null){
				pkgs = Package_c.getManyEP_PKGsOnR1405(systemModel);
			}else {
				pkgs = Package_c.getManyEP_PKGsOnR1405(systemModel, new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c)candidate;
						return selected.getName().equals(ContainerPackageName);
					}
				});
			}
			return findElementInOpennedCanvas(pkgs, ContainerPackageName,
					OoaType, Name, IndirectParentName, DirectParentName);
		}

		return null;
	}



	private static NonRootModelElement getOpennedCanvasInstance() {
		IEditorPart editor = CheatSheetsUIUtilities.getActiveEditor();
		if (editor instanceof GraphicalEditor) {
			GraphicalEditor activeEditor = (GraphicalEditor) editor;
			Model_c model = activeEditor.getModel();
			PersistableModelComponent component = model
			.getPersistableComponent();
			NonRootModelElement container = component.getRootModelElement();
			return container;
		}
		if (editor instanceof ActivityEditor) {
			IEditorInput input = editor.getEditorInput();
			ActivityEditorInput activityInput = (ActivityEditorInput) input;
			return activityInput.getModelElement();
		}
		return null;
	}

	public static NonRootModelElement findElementInOpennedCanvas(
			Package_c[] Pkgs, String PkgName, String OoaType,
			final String Name, final String IndirectParentName,
			final String DirectParentName) {

		if (OoaType.equalsIgnoreCase("ModelClass")) {
			ModelClass_c element = ModelClass_c.getOneO_OBJOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ModelClass_c selected = (ModelClass_c) candidate;
							return selected.getName().equals(Name);
						}
					});
			return element;

		} else if (OoaType.equalsIgnoreCase("External Entity")) {
			ExternalEntity_c element = ExternalEntity_c.getOneS_EEOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ExternalEntity_c selected = (ExternalEntity_c) candidate;
							return selected.getName().equals(Name);
						}
					});
			return element;

		}else if (OoaType.equalsIgnoreCase("Bridge")){
			ExternalEntity_c entity = ExternalEntity_c.getOneS_EEOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ExternalEntity_c selected = (ExternalEntity_c) candidate;
							return selected.getName().equals(DirectParentName);
						}
					});
			Bridge_c element = Bridge_c.getOneS_BRGOnR19(entity, new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					Bridge_c selected = (Bridge_c)candidate;
					return selected.getName().equals(Name);
				}
			});
			return element;
		}else if (OoaType.equalsIgnoreCase("Bridge Parameter")){
			ExternalEntity_c entity = ExternalEntity_c.getOneS_EEOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ExternalEntity_c selected = (ExternalEntity_c) candidate;
							return selected.getName().equals(IndirectParentName);
						}
					});
			Bridge_c bridge = Bridge_c.getOneS_BRGOnR19(entity, new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					Bridge_c selected = (Bridge_c)candidate;
					return selected.getName().equals(DirectParentName);
				}
			});
			BridgeParameter_c element = BridgeParameter_c.getOneS_BPARMOnR21(bridge,new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					BridgeParameter_c selected = (BridgeParameter_c )candidate;
					return selected.getName().equals(Name);
				}
			});
			return element;
			
		}
		
		else if (OoaType.equalsIgnoreCase("Structured DataType")) {
			StructuredDataType_c element = StructuredDataType_c.getOneS_SDTOnR17(
					DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
							new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							DataType_c selected = (DataType_c) candidate;
							return selected.getName().equals(Name);
						}
					}));
			return element;

		} else if (OoaType.equalsIgnoreCase("Enumeration DataType")) {
			EnumerationDataType_c element = EnumerationDataType_c.getOneS_EDTOnR17(
					DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
							new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							DataType_c selected = (DataType_c) candidate;
							return selected.getName().equals(Name);
						}
					}));
			return element;

		}

		else if (OoaType.equalsIgnoreCase("Instance State Machine")) {
			ModelClass_c clazzs = ModelClass_c.getOneO_OBJOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ModelClass_c selected = (ModelClass_c) candidate;
							return selected.getName().equals(Name);
						}
					});
			InstanceStateMachine_c element = InstanceStateMachine_c
			.getOneSM_ISMOnR518(clazzs);
			return element;

		} else if (OoaType.equalsIgnoreCase("Interface")) {
			Interface_c[] elements = Interface_c.getManyC_IsOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							Interface_c selected = (Interface_c) candidate;
							return selected.getName().equals(Name);
						}
					});
			return elements[0];

		} else if (OoaType.equalsIgnoreCase("Instance State Machine")) {
			ModelClass_c[] clazz = ModelClass_c.getManyO_OBJsOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ModelClass_c selected = (ModelClass_c) candidate;
							return selected.getName()
							.equals(IndirectParentName);
						}
					});
			InstanceStateMachine_c element = InstanceStateMachine_c
			.getOneSM_ISMOnR518(clazz);
			return element;

		} else if (OoaType.equalsIgnoreCase("State Machine")) {
			ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ModelClass_c selected = (ModelClass_c) candidate;
							return selected.getName()
							.equals(IndirectParentName);
						}
					});
			if (clazz == null)
				return null;
			InstanceStateMachine_c ism = InstanceStateMachine_c
			.getOneSM_ISMOnR518(clazz);
			StateMachineState_c state = StateMachineState_c
			.getOneSM_STATEOnR501(
					StateMachine_c.getOneSM_SMOnR517(ism),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							StateMachineState_c selected = (StateMachineState_c) candidate;
							return selected.getName().equals(Name);
						}
					});
			return state;

		} else if (OoaType.equalsIgnoreCase("Event")) {
			ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ModelClass_c selected = (ModelClass_c) candidate;
							return selected.getName()
							.equals(IndirectParentName);
						}
					});
			InstanceStateMachine_c ism = InstanceStateMachine_c
			.getOneSM_ISMOnR518(clazz);
			StateMachineEvent_c element = StateMachineEvent_c.getOneSM_EVTOnR502(StateMachine_c.getManySM_SMsOnR517(ism),new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					StateMachineEvent_c selected = (StateMachineEvent_c)candidate;
					return selected.getMning().equalsIgnoreCase(Name);
				}
			});
			return element;
			

		} else if (OoaType.equalsIgnoreCase("Function")) {
			Function_c[] elements = Function_c.getManyS_SYNCsOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							Function_c selected = (Function_c) candidate;
							return selected.getName().equalsIgnoreCase(Name);

						}
					});
			return elements[0];

		} else if (OoaType.equalsIgnoreCase("Interface Operation")) {
			Component_c component = null;
			boolean found = false;
			for (int i = 0; i < Pkgs.length; i++) {
				if (found)
					break;
				Component_c[] candidates = Component_c
				.ComponentInstances(Pkgs[i].getModelRoot());
				for (int j = 0; j < candidates.length; j++) {
					if (candidates[j].getName().equals(IndirectParentName)) {
						component = candidates[j];
						found = true;
						break;
					}
				}
			}
			if (component == null){
				Interface_c inter = Interface_c.getOneC_IOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Pkgs),new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
						Interface_c selected = (Interface_c)candidate;
						return selected.getName().equals(DirectParentName);
					}
				});
				InterfaceOperation_c element = InterfaceOperation_c.getOneC_IOOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inter),new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
						InterfaceOperation_c selected = (InterfaceOperation_c)candidate;
						return selected.getName().equals(Name);
					}
				});
				return element;
			}
			
			Port_c port = Port_c.getOneC_POOnR4010(component,
					new ClassQueryInterface_c() {

				@Override
				public boolean evaluate(Object candidate) {
					Port_c selected = (Port_c) candidate;
					return selected.getName().equals(DirectParentName);
				}
			});
			if (port == null)
				return null;

			InterfaceReference_c intRef = InterfaceReference_c
			.getOneC_IROnR4016(port);
			Requirement_c req = Requirement_c.getOneC_ROnR4009(intRef);
			Provision_c prov = Provision_c.getOneC_POnR4009(intRef);
			if (req != null) {

				// Required Interface
				RequiredExecutableProperty_c[] REPs = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(req);
				RequiredOperation_c intOperation = RequiredOperation_c
				.getOneSPR_ROOnR4502(REPs, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						RequiredOperation_c selected = (RequiredOperation_c) candidate;
						return selected.getName().equals(Name);

					}
				});

				return intOperation;

			} else if (prov != null) {
				// Provided Interface
				ProvidedExecutableProperty_c[] REPs = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(prov);
				ProvidedOperation_c intOperation = ProvidedOperation_c
				.getOneSPR_POOnR4503(REPs, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						ProvidedOperation_c selected = (ProvidedOperation_c) candidate;
						return selected.getName().equals(Name);

					}
				});

				return intOperation;

			} else {
				return null;
			}
		}

		return null;
	}

	public static void canNotFindElementReport(String activityType,
			String activityName) {
		String name = "";
		String type = "";
		if ( activityType != null)
			type = activityType;
		if (activityName!= null)
			name = activityName;
		MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
				.getActiveShell(), "Element not found", "Cound not find : "
				+ name + "  " + type
				+"\n\nMake sure that you followed the previous steps correctly");
		throw new ObjectNotFoundException("Cound not find :" + name
				+ "     " + type);
	}

}
