package org.xtuml.bp.xtext.masl.ui.rename

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.naming.QualifiedName
import org.xtuml.bp.core.Attribute_c
import org.xtuml.bp.core.Component_c
import org.xtuml.bp.core.FunctionParameter_c
import org.xtuml.bp.core.Function_c
import org.xtuml.bp.core.InstanceStateMachine_c
import org.xtuml.bp.core.InterfaceReference_c
import org.xtuml.bp.core.ModelClass_c
import org.xtuml.bp.core.OperationParameter_c
import org.xtuml.bp.core.Operation_c
import org.xtuml.bp.core.Port_c
import org.xtuml.bp.core.ProvidedExecutableProperty_c
import org.xtuml.bp.core.ProvidedOperation_c
import org.xtuml.bp.core.Provision_c
import org.xtuml.bp.core.RequiredExecutableProperty_c
import org.xtuml.bp.core.RequiredOperation_c
import org.xtuml.bp.core.Requirement_c
import org.xtuml.bp.core.Function_c
import org.xtuml.bp.core.InstanceStateMachine_c
import org.xtuml.bp.core.RequiredOperation_c
import org.xtuml.bp.core.ProvidedOperation_c
import org.xtuml.bp.core.ModelClass_c
import org.xtuml.bp.core.Operation_c
import org.xtuml.bp.core.Port_c
import org.xtuml.bp.core.StateMachineEvent_c
import org.xtuml.bp.core.StateMachineState_c
import org.xtuml.bp.core.StateMachine_c
import org.xtuml.bp.core.UserDataType_c
import org.xtuml.bp.core.common.NonRootModelElement
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage

class XtumlToMaslMapper {
	
	@Inject extension StructurePackage 
	@Inject extension TypesPackage

	public def List<EClass> getMaslDefinitionEClasses(NonRootModelElement xtumlElement) {
		switch xtumlElement{
			Attribute_c:
				#[attributeDefinition]
			Component_c:
				#[domainDefinition]
			Function_c:
				#[domainServiceDefinition]
			FunctionParameter_c:
				#[parameter]
			RequiredOperation_c:
				#[terminatorServiceDefinition]
			ProvidedOperation_c:
				#[terminatorServiceDefinition]
			ModelClass_c:
				#[objectDeclaration]
			Operation_c:
				#[objectServiceDefinition]
			OperationParameter_c:
				#[parameter]
			Port_c:
				#[terminatorDefinition]
			StateMachineState_c:
				#[stateDeclaration]
			StateMachineEvent_c:
				#[eventDefinition]
			UserDataType_c:
				#[typeDeclaration]
			default:			
				#[]
		}
	} 
	
	public def List<EClass> getMaslEClasses(NonRootModelElement xtumlElement) {
		switch xtumlElement{
			Attribute_c:
				#[attributeDefinition]
			Component_c:
				#[domainDefinition]
			Function_c:
				#[domainServiceDeclaration]
			FunctionParameter_c:
				#[parameter]
			RequiredOperation_c:
				#[terminatorServiceDeclaration]
			ProvidedOperation_c:
				#[terminatorServiceDeclaration]
			ModelClass_c:
				#[objectDeclaration]
			Operation_c:
				#[objectServiceDeclaration]
			OperationParameter_c:
				#[parameter]
			Port_c:
				#[terminatorDefinition]
			StateMachineState_c:
				#[stateDeclaration]
			StateMachineEvent_c:
				#[eventDefinition]
			UserDataType_c:
				#[typeDeclaration]
			default:			
				#[]
		}
	} 
	
	public def QualifiedName getMaslQualifiedName(NonRootModelElement xtumlElement) {
		switch xtumlElement{
			Component_c:
				QualifiedName.create(xtumlElement.name)
			StateMachineEvent_c:
				xtumlElement.maslQualifiedNamePrefix.append(xtumlElement.mning)
			default:			
				xtumlElement.maslQualifiedNamePrefix.append(xtumlElement.name)
		}
	} 

    public def QualifiedName getMaslQualifiedName(NonRootModelElement xtumlElement, String name) {
        val prefix = xtumlElement.maslQualifiedNamePrefix
        if ( null != prefix )
		    prefix.append(name)
		else
	        QualifiedName.create(name)
	}

	private def QualifiedName getMaslQualifiedNamePrefix(NonRootModelElement xtumlElement) {
		switch xtumlElement{
			Attribute_c:
				ModelClass_c.getOneO_OBJOnR102(xtumlElement).maslQualifiedName
			Function_c:
				xtumlElement.component.maslQualifiedName
			FunctionParameter_c:
				Function_c.getOneS_SYNCOnR24(xtumlElement).maslQualifiedName
			RequiredOperation_c:
                Port_c.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(Requirement_c.getOneC_ROnR4500(
                    RequiredExecutableProperty_c.getOneSPR_REPOnR4502(xtumlElement))))
                    .maslQualifiedName
			ProvidedOperation_c:
                Port_c.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(Provision_c.getOneC_POnR4501(
                    ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(xtumlElement))))
                    .maslQualifiedName
			ModelClass_c:
				xtumlElement.component.maslQualifiedName
			Operation_c:
				ModelClass_c.getOneO_OBJOnR115(xtumlElement).maslQualifiedName
			OperationParameter_c:
				Operation_c.getOneO_TFROnR117(xtumlElement).maslQualifiedName
			Port_c:
				xtumlElement.component.maslQualifiedName
			StateMachineState_c:
				ModelClass_c.getOneO_OBJOnR518(InstanceStateMachine_c.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR501(xtumlElement))).maslQualifiedName
			StateMachineEvent_c:
				ModelClass_c.getOneO_OBJOnR518(InstanceStateMachine_c.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR502(xtumlElement))).maslQualifiedName
			UserDataType_c:
				xtumlElement.component.maslQualifiedName
			default:			
				null
		}
	}
	
	
	private def getComponent(NonRootModelElement xtumlElement) {
		var parent = xtumlElement.persistableComponent
		while (parent != null) {
			if(parent.rootModelElement instanceof Component_c)
				return parent.rootModelElement
			else 
				parent = parent.parent 
		}
		return null
		
	}
}
