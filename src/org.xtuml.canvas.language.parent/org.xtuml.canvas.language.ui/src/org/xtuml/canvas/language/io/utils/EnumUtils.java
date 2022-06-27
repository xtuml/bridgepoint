package org.xtuml.canvas.language.io.utils;

import java.lang.reflect.Field;

import org.xtuml.bp.core.AcceptEventAction_c;
import org.xtuml.bp.core.AcceptTimeEventAction_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.ActivityDiagramAction_c;
import org.xtuml.bp.core.ActivityEdge_c;
import org.xtuml.bp.core.ActivityFinalNode_c;
import org.xtuml.bp.core.ActivityPartition_c;
import org.xtuml.bp.core.ActorParticipant_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AsynchronousMessage_c;
import org.xtuml.bp.core.BinaryAssociation_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSubtype_c;
import org.xtuml.bp.core.ClassInstanceParticipant_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CommunicationLink_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.DecisionMergeNode_c;
import org.xtuml.bp.core.Delegation_c;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.End_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Exception_c;
import org.xtuml.bp.core.Extend_c;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FlowFinalNode_c;
import org.xtuml.bp.core.ForkJoinNode_c;
import org.xtuml.bp.core.Generalization_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.ImportedProvision_c;
import org.xtuml.bp.core.ImportedRequirement_c;
import org.xtuml.bp.core.Include_c;
import org.xtuml.bp.core.InitialNode_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Lifespan_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.ObjectNode_c;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.ReturnMessage_c;
import org.xtuml.bp.core.SendSignal_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.SynchronousMessage_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TimeSpan_c;
import org.xtuml.bp.core.TimingMark_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UseCaseParticipant_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Modeltype_c;
import org.xtuml.bp.ui.canvas.Ooatype_c;
import org.xtuml.canvas.language.ui.CanvasUiModule;

public class EnumUtils {

	public static String endFor(int end) {
		switch (end) {
		case End_c.Start:
			return "start";
		case End_c.End:
			return "end";
		case End_c.Additional:
			return "additional";
		case End_c.End_Fixed:
			return "end_fixed";
		case End_c.Start_Fixed:
			return "start_fixed";
		case End_c.None:
			return "none";
		case End_c.Middle:
			return "middle";
		case End_c.Floating:
			return "floating";
		default:
			return "none";
		}
	}

	public static String typeFor(int type) {
		for (Field field : Ooatype_c.class.getFields()) {
			try {
				int value = field.getInt(Ooatype_c.class);
				if (type == value) {
					if(field.getName().equals("EE")) {
						return "ee";
					}
					return field.getName().replaceAll("((?!^)([A-Z]))", "_$1").toLowerCase();
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				CanvasUiModule.logError("Failed to get Ooatype_c enumeration value.", e);
			}
		}
		return "none";
	}

	public static int typeFor(String type) {
		for (Field field : Ooatype_c.class.getFields()) {
			try {
				String fieldName = field.getName().replaceAll("((?!^)([A-Z]))", "_$1").toLowerCase();
				if(fieldName.equals("e_e")) {
					// double capital letters should not add underscore
					fieldName = "ee";
				}
				if (type.equals(fieldName)) {
					return field.getInt(Ooatype_c.class);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				CanvasUiModule.logError("Failed to get Ooatype_c enumeration value.", e);
			}
		}
		return Ooatype_c.None;
	}

	public static int getModelType(Object parent) {
		if (parent instanceof SystemModel_c) {
			return Modeltype_c.SystemModelPackage;
		}
		if (parent instanceof Package_c) {
			return Modeltype_c.Package;
		}
		if (parent instanceof InstanceStateMachine_c) {
			return Modeltype_c.InstanceStateChartDiagram;
		}
		if (parent instanceof ClassStateMachine_c) {
			return Modeltype_c.ClassStateChartDiagram;
		}
		if (parent instanceof Component_c) {
			return Modeltype_c.ComponentDiagram;
		}
		if (parent instanceof Model_c) {
			return Modeltype_c.TestDiagram;
		}
		return Modeltype_c.OOA_UNINITIALIZED_ENUM;
	}

	public static int getTypeFromReference(NonRootModelElement represents, boolean container) {
		if (represents instanceof ModelClass_c) {
			return Ooatype_c.Class;
		}
		if (represents instanceof Action_c) {
			return Ooatype_c.GenericAction;
		}
		if (represents instanceof Package_c) {
			return Ooatype_c.Package;
		}
		if (represents instanceof Association_c) {
			SubtypeSupertypeAssociation_c subSuper = SubtypeSupertypeAssociation_c
					.getOneR_SUBSUPOnR206((Association_c) represents);
			if (subSuper != null) {
				return Ooatype_c.Supertype;
			}
			return Ooatype_c.Association;
		}
		if (represents instanceof ClassAsSubtype_c) {
			return Ooatype_c.Subtype;
		}
		if (represents instanceof ImportedClass_c) {
			return Ooatype_c.ImportedClass;
		}
		if (represents instanceof Transition_c) {
			return Ooatype_c.Transition;
		}
		if (represents instanceof CreationTransition_c) {
			return Ooatype_c.CreationTransition;
		}
		if (represents instanceof StateMachineState_c) {
			return Ooatype_c.State;
		}
		if (represents instanceof UserDataType_c) {
			return Ooatype_c.UserDataType;
		}
		if (represents instanceof EnumerationDataType_c) {
			return Ooatype_c.EnumerationDataType;
		}
		if (represents instanceof StructuredDataType_c) {
			return Ooatype_c.StructuredDataType;
		}
		if (represents instanceof ExternalEntity_c) {
			return Ooatype_c.EE;
		}
		if (represents instanceof ClassAsLink_c) {
			return Ooatype_c.AssociativeLink;
		}
		if (represents instanceof AcceptTimeEventAction_c) {
			return Ooatype_c.AcceptTimeEventAction;
		}
		if (represents instanceof Component_c) {
			if (container) {
				return Ooatype_c.ComponentContainer;
			}
			return Ooatype_c.Component;
		}
		if (represents instanceof SynchronousMessage_c) {
			return Ooatype_c.SynchronousMessage;
		}
		if (represents instanceof AsynchronousMessage_c) {
			return Ooatype_c.AsynchronousMessage;
		}
		if (represents instanceof ReturnMessage_c) {
			return Ooatype_c.ReturnMessage;
		}
		if (represents instanceof AcceptEventAction_c) {
			return Ooatype_c.AcceptEventAction;
		}
		if (represents instanceof ConstantSpecification_c) {
			return Ooatype_c.ConstantSpecification;
		}
		if (represents instanceof Exception_c) {
			return Ooatype_c.Exception;
		}
		if (represents instanceof ComponentReference_c) {
			return Ooatype_c.ComponentReference;
		}
		if (represents instanceof Interface_c) {
			return Ooatype_c.Interface;
		}
		if (represents instanceof Requirement_c) {
			return Ooatype_c.RequiredInterface;
		}
		if (represents instanceof Provision_c) {
			return Ooatype_c.ProvidedInterface;
		}
		if (represents instanceof ImportedRequirement_c) {
			return Ooatype_c.ImportedRequiredInterface;
		}
		if (represents instanceof ImportedProvision_c) {
			return Ooatype_c.ImportedProvidedInterface;
		}
		if (represents instanceof Deployment_c) {
			return Ooatype_c.Deployment;
		}
		if (represents instanceof DecisionMergeNode_c) {
			return Ooatype_c.DecisionMergeNode;
		}
		if (represents instanceof ObjectNode_c) {
			return Ooatype_c.ObjectNode;
		}
		if (represents instanceof InitialNode_c) {
			return Ooatype_c.InitialNode;
		}
		if (represents instanceof FlowFinalNode_c) {
			return Ooatype_c.FlowFinalNode;
		}
		if (represents instanceof SendSignal_c) {
			return Ooatype_c.SendSignalAction;
		}
		if (represents instanceof ActivityDiagramAction_c) {
			return Ooatype_c.GenericAction;
		}
		if (represents instanceof InitialNode_c) {
			return Ooatype_c.InitialNode;
		}
		if (represents instanceof ActivityFinalNode_c) {
			return Ooatype_c.ActivityFinalNode;
		}
		if (represents instanceof ActivityEdge_c) {
			return Ooatype_c.ActivityEdge;
		}
		if (represents instanceof ForkJoinNode_c) {
			return Ooatype_c.ForkJoin;
		}
		if (represents instanceof ActivityPartition_c) {
			return Ooatype_c.Partition;
		}
		if (represents instanceof ClassParticipant_c) {
			return Ooatype_c.ClassParticipant;
		}
		if (represents instanceof ClassInstanceParticipant_c) {
			return Ooatype_c.ClassInstanceParticipant;
		}
		if (represents instanceof PackageParticipant_c) {
			return Ooatype_c.PackageParticipant;
		}
		if (represents instanceof ExternalEntityParticipant_c) {
			return Ooatype_c.ExternalEntityParticipant;
		}
		if (represents instanceof ComponentParticipant_c) {
			return Ooatype_c.ComponentParticipant;
		}
		if (represents instanceof ActorParticipant_c) {
			return Ooatype_c.Actor;
		}
		if (represents instanceof CommunicationLink_c) {
			return Ooatype_c.CommunicationLink;
		}
		if (represents instanceof Lifespan_c) {
			return Ooatype_c.Lifeline;
		}
		if (represents instanceof TimingMark_c) {
			return Ooatype_c.TimingMark;
		}
		if (represents instanceof TimeSpan_c) {
			return Ooatype_c.TimeSpan;
		}
		if (represents instanceof UseCaseParticipant_c) {
			return Ooatype_c.UseCase;
		}
		if (represents instanceof BinaryAssociation_c) {
			return Ooatype_c.UseCaseBinaryAssociation;
		}
		if (represents instanceof Generalization_c) {
			return Ooatype_c.Generalization;
		}
		if (represents instanceof Extend_c) {
			return Ooatype_c.Extend;
		}
		if (represents instanceof Include_c) {
			return Ooatype_c.Include;
		}
		if (represents instanceof Delegation_c) {
			return Ooatype_c.Delegation;
		}
		return Ooatype_c.OOA_UNINITIALIZED_ENUM;
	}
}
