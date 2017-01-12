package org.xtuml.bp.xtext.masl.ui.rename

import com.google.inject.Inject
import java.util.List
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider
import org.xtuml.bp.core.ui.IRenameElementParticipant
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage

class MaslRenameParticipant implements IRenameElementParticipant {
	
	@Inject XtextResourceSetProvider rsp
	@Inject ResourceDescriptionsProvider rdp 
	@Inject extension StructurePackage 
	@Inject extension TypesPackage 
	@Inject MaslRenameRefactoringExecutor executor;
	
	IProject project
	List<EClass> eClasses
	QualifiedName oldQName
	String newName
	
	override IStatus beforeRenameElement(String oldXtumlPath, String newName, String xtumlElementType, IProject project) {
		this.project = project
		this.newName = newName
		val parsedPath = new ParsedXtumlPath(oldXtumlPath)
		this.eClasses = getMaslEClasses(xtumlElementType)
		if(eClasses.empty)
			return Status.OK_STATUS
		this.oldQName = getMaslQualifiedName(parsedPath, eClasses.head)
		if(oldQName === null)
			return Status.OK_STATUS
		val renameElementContext = getRenameElementContext(eClasses, oldQName, project)
		if(renameElementContext.empty) 
			return Status.OK_STATUS
		return executor.tryRename(renameElementContext, newName)
	}
	
	override IStatus afterRenameElement() {
		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, XtextProjectHelper.BUILDER_ID, emptyMap, new NullProgressMonitor)
		val renameElementContext = getRenameElementContext(eClasses, oldQName, project)
		if(renameElementContext.empty) 
			return Status.OK_STATUS
		return executor.doRename(renameElementContext, newName)
	}
	
	private def XtumlRenameElementContext getRenameElementContext(List<EClass> eClasses, QualifiedName name, IProject project) {
		val uri2eClass = newArrayList
		uri2eClass += eClasses
			.map [ eClass |
				val index = rdp.getResourceDescriptions(rsp.get(project))
				index.getExportedObjects(eClass as EClass, name, false)
			].flatten
			.toSet
			.filter[EObjectURI.fileExtension == 'mod' || EObjectURI.fileExtension == 'int']
			.map[EObjectURI -> EClass]
		return new XtumlRenameElementContext(uri2eClass)
	}
	
	private def List<EClass> getMaslEClasses(String xtumlClassName) {
		switch xtumlClassName {
			case 'Attribute_c':
				#[attributeDefinition]
			case 'Function_c':
				#[domainServiceDeclaration, domainFunctionDeclaration]
			case 'ModelClass_c':
				#[objectDeclaration]
			case 'Operation_c':
				#[objectServiceDeclaration, objectFunctionDeclaration]
			case 'Port_c':
				#[terminatorDefinition]
			case 'UserDataType_c':
				#[typeDeclaration]
			default:			
				null
		}
	} 
	
	private def QualifiedName getMaslQualifiedName(ParsedXtumlPath it, EClass maslEClass) {
		switch maslEClass {
			case domainServiceDeclaration, 
			case domainFunctionDeclaration,
			case terminatorDefinition,
			case typeDeclaration,
			case objectDeclaration:
				QualifiedName.create(domainName, elementName)
			case attributeDefinition,
			case objectServiceDeclaration,
			case objectFunctionDeclaration:
				QualifiedName.create(domainName, middleName, elementName)
			default: 
				null		
		}
	}
	
	@Data
	private static class ParsedXtumlPath {
		
		String domainName
		String middleName
		String elementName
		
		new(String xtumlPath) {
			val xtumlNameSegments = xtumlPath.split('::')
			domainName = if(xtumlNameSegments.head == 'models')
				xtumlNameSegments.get(1)
			else
				xtumlNameSegments.head
			middleName = xtumlNameSegments.get(xtumlNameSegments.size - 2)
			elementName = xtumlNameSegments.last
		} 
	}
}

	
	