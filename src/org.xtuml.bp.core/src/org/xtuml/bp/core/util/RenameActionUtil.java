package org.xtuml.bp.core.util;

import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.InstanceAttributeValue_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.MessageArgument_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.NonRootModelElement;

public class RenameActionUtil {

	public static void setElementName(NonRootModelElement newElement,
			String proposedName) {

		Class<? extends NonRootModelElement> classType = newElement.getClass();
		if (classType == LiteralSymbolicConstant_c.class) {
			(SymbolicConstant_c
					.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c
							.getOneCNST_LFSCOnR1503((LiteralSymbolicConstant_c) newElement)))
					.setName(proposedName);
		} else if (classType == Attribute_c.class) {
			((Attribute_c) newElement).setRoot_nam(proposedName);
		} else if (classType == StateMachineEvent_c.class) {
			((StateMachineEvent_c) newElement).setMning(proposedName);
		} else if (classType == PackageParticipant_c.class) {
			((PackageParticipant_c) newElement).setInformalname(proposedName);
		} else if (classType == ClassParticipant_c.class) {
			((ClassParticipant_c) newElement).setInformalname(proposedName);
		} else if (classType == ExternalEntityParticipant_c.class) {
			((ExternalEntityParticipant_c) newElement)
					.setInformalname(proposedName);
		} else if (classType == ComponentParticipant_c.class) {
			((ComponentParticipant_c) newElement)
					.setInformalcomponentname(proposedName);
		} else if (classType == EnumerationDataType_c.class) {
			(DataType_c.getOneS_DTOnR17((EnumerationDataType_c) newElement))
					.setName(proposedName);
		} else if (classType == UserDataType_c.class) {
			(DataType_c.getOneS_DTOnR17((UserDataType_c) newElement))
					.setName(proposedName);
		} else if (classType == StructuredDataType_c.class) {
			(DataType_c.getOneS_DTOnR17((StructuredDataType_c) newElement))
					.setName(proposedName);
		}

		else if (classType == ConstantSpecification_c.class) {
			((ConstantSpecification_c) newElement)
					.setInformalgroupname(proposedName);
		} else if (classType == MessageArgument_c.class) {
			((MessageArgument_c) newElement).setInformalname(proposedName);
		} else if (classType == InstanceAttributeValue_c.class) {
			((InstanceAttributeValue_c) newElement)
					.setInformalname(proposedName);
		} else {
			if(classType == ModelClass_c.class) {
				ModelClass_c mc = (ModelClass_c) newElement;
				mc.setKey_lett(proposedName.replaceAll(" ", ""));
			}
			newElement.setName(proposedName);
		}

	}

	public static String getAttributeNameForName(NonRootModelElement element) {
		String elementName = "";
		Class<? extends NonRootModelElement> classType = element.getClass();
		if (classType == Attribute_c.class) {
			elementName = "Root_nam"; //$NON-NLS-1$
		} else if (classType == StateMachineEvent_c.class) {
			elementName = "Mning"; //$NON-NLS-1$
		} else if (classType == ComponentParticipant_c.class) {
			elementName = "Informalcomponentname"; //$NON-NLS-1$
		} else if (classType == InstanceAttributeValue_c.class || classType == ConstantSpecification_c.class
				|| classType == MessageArgument_c.class || classType == ExternalEntityParticipant_c.class
				|| classType == ClassParticipant_c.class || classType == PackageParticipant_c.class) {
			elementName = "Informalname"; //$NON-NLS-1$
		} else {
			elementName = "Name"; //$NON-NLS-1$
		}
		return elementName;
	}
	
	public static String getElementName(NonRootModelElement newElement) {
		String elementName = "";
		Class<? extends NonRootModelElement> classType = newElement.getClass();
		if (classType == LiteralSymbolicConstant_c.class) {
			elementName = (SymbolicConstant_c
					.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c
							.getOneCNST_LFSCOnR1503((LiteralSymbolicConstant_c) newElement)))
					.getName();
		} else if (classType == Attribute_c.class) {
			elementName = ((Attribute_c) newElement).getRoot_nam();
		} else if (classType == StateMachineEvent_c.class) {
			elementName = ((StateMachineEvent_c) newElement).getMning();
		} else if (classType == PackageParticipant_c.class) {
			elementName = ((PackageParticipant_c) newElement).getInformalname();
		} else if (classType == ClassParticipant_c.class) {
			elementName = ((ClassParticipant_c) newElement).getInformalname();
		} else if (classType == ExternalEntityParticipant_c.class) {
			elementName = ((ExternalEntityParticipant_c) newElement)
					.getInformalname();
		} else if (classType == ComponentParticipant_c.class) {
			elementName = ((ComponentParticipant_c) newElement)
					.getInformalcomponentname();
		} else if (classType == EnumerationDataType_c.class) {
			elementName = (DataType_c
					.getOneS_DTOnR17((EnumerationDataType_c) newElement))
					.getName();
		} else if (classType == UserDataType_c.class) {
			elementName = (DataType_c
					.getOneS_DTOnR17((UserDataType_c) newElement)).getName();
		} else if (classType == StructuredDataType_c.class) {
			elementName = (DataType_c
					.getOneS_DTOnR17((StructuredDataType_c) newElement))
					.getName();
		}

		else if (classType == ConstantSpecification_c.class) {
			elementName = ((ConstantSpecification_c) newElement)
					.getInformalgroupname();
		} else if (classType == MessageArgument_c.class) {
			elementName = ((MessageArgument_c) newElement).getInformalname();
		} else if (classType == InstanceAttributeValue_c.class) {
			elementName = ((InstanceAttributeValue_c) newElement)
					.getInformalname();
		} else {
			elementName = newElement.getName();
		}

		return elementName;
	}

}
