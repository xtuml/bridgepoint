package com.mentor.nucleus.bp.core.util;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.ConstantSpecification_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.InstanceAttributeValue_c;
import com.mentor.nucleus.bp.core.LeafSymbolicConstant_c;
import com.mentor.nucleus.bp.core.LiteralSymbolicConstant_c;
import com.mentor.nucleus.bp.core.MessageArgument_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SymbolicConstant_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

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
