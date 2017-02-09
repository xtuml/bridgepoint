package org.xtuml.bp.xtext.masl.ui.rename

import com.google.inject.Inject
import java.util.List
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider
import org.xtuml.bp.core.common.NonRootModelElement
import org.xtuml.bp.core.ui.IRenameElementParticipant

class MaslRenameParticipant implements IRenameElementParticipant {
	
	@Inject XtextResourceSetProvider rsp
	@Inject ResourceDescriptionsProvider rdp 
	@Inject MaslRenameRefactoringExecutor executor
	@Inject extension XtumlToMaslMapper 
	
	IProject project
	List<EClass> eClasses
	QualifiedName oldQName
	String newName
	
	override IStatus renameElement(NonRootModelElement xtumlElement, String newName, String oldName) {
		this.project = xtumlElement.file.project
		this.newName = newName
		this.eClasses = xtumlElement.maslEClasses
		if(eClasses.empty)
			return Status.OK_STATUS
		this.oldQName = getMaslQualifiedName(xtumlElement, oldName)
		if(oldQName === null)
			return Status.OK_STATUS
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
}

	
	