package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.core.runtime.CoreException

interface IMaslSnippetEditorInput extends IXtumlElementEditorInput {
	
	def String getPropertyValue() throws CoreException
	def void setPropertyValue(String value) throws CoreException
}