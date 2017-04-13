package org.xtuml.bp.xtext.masl.cli

import com.google.inject.Inject
import com.google.inject.Provider
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.LinkedList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.resource.FileExtensionProvider
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.IResourceValidator
import org.xtuml.bp.xtext.masl.MASLStandaloneSetup

class MaslCliChecker {
	def static void main(String[] args) {
		try {
			val injector = new MASLStandaloneSetup().createInjectorAndDoEMFRegistration
			injector.getInstance(MaslCliChecker).check(args)
		} catch (Throwable e) {
			System.err.println('Error: ' + e.message)
			e.printStackTrace
		}
	}

	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject FileExtensionProvider fileExtensionProvider
	@Inject IResourceValidator resourceValidator

	Severity minimumSeverity = Severity.ERROR
	String inputDir
	String modelDir
	File outputDir

	int numModels = 0
	int numFiles = 0
	int numIssues = 0

	def void check(String[] args) {
		var theArgs = new LinkedList(args)
		var showHelp = false
		while (theArgs.size > 1) {
			val option = theArgs.poll
			val arg = theArgs.poll
			switch option {
				case '-I':
					inputDir = arg
				case '-i':
					modelDir = arg
				case '-o':
					outputDir = new File(arg)
				case '-s':
					minimumSeverity = Severity.valueOf(arg)
				case '-?',
				case '-h',
				case '-help':
					showHelp = true
			}
		}
		if (showHelp || theArgs.size > 0 || !((inputDir == null).xor(modelDir == null))) {
			println('''
				MaslCliCkecker
				Checks MASL files in a given directory for syntax type and linking errors.
				
				Usage
				 -I <inputDir> check all MASL models in <inputDir>
				 -i <modelDir> check all files in <modelDir>
				 -o <outputDir> write output to <outputDir> (default: stderr)
				 -s (ERROR|WARNING) severity threshold      (default ERROR)
				 -? show this help
			''')
			return
		}
		if (outputDir != null) {
			if (!outputDir.exists)
				outputDir.mkdirs
			if (!outputDir.isDirectory)
				throw new IllegalArgumentException("Output directory must be a directory.")
		}
		if (modelDir != null)
			checkModel(modelDir)
		else
			checkInputDir(inputDir)
		println('''«numIssues» issues found in «numFiles» files in «numModels» models.''')
	}

	def void checkInputDir(String folderName) {
		val dir = new File(folderName)
		if (!dir.exists || !dir.directory)
			throw new IllegalArgumentException("Input directory must exist and be a directory.")
		dir.listFiles.filter[directory].map [
			println('Testing ' + it)
			try {
				checkModel(absolutePath)
			} catch (AssertionError e) {
				println(e.message)
				return false
			}
			return true
		].reduce[$0 && $1]
	}

	protected def checkModel(String folderName) {
		numModels++
		val buffer = new StringBuffer
		val resourceSet = resourceSetProvider.get
		val folder = new File(folderName)
		if(folder.listFiles.exists[directory])
			buffer.append('''
				WARNING: Nested models are likely to produce linking errors due to duplicate names.
				Please check them as individual models.
			''')		
		load(folder, resourceSet)
		var i = 0
		while (resourceSet.resources.size > i) {
			numFiles++
			val resource = resourceSet.resources.get(i)
			val issues = resourceValidator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl).filter [
				severity <= minimumSeverity
			]
			if (!issues.empty) {
				buffer.append('''
					«resource.URI.lastSegment»:
					«FOR it : issues»
						(line «lineNumber») «severity» «message»
					«ENDFOR»
				''')
				numIssues += issues.size
			}
			i++
		}
		if (i == 0)
			buffer.append('Model dir is empty.')
		val errorMessages = buffer.toString
		if (!errorMessages.empty) {
			if (outputDir == null) {
				System.err.println(errorMessages)
			} else {
				val s = new PrintStream(new FileOutputStream(new File(outputDir, folder.name + '.err')))
				s.println(errorMessages)
				s.close()
			}
		}
	}

	protected def void load(File file, ResourceSet resourceSet) {
		if (file.isDirectory) {
			file.listFiles [
				isDirectory || fileExtensionProvider.fileExtensions.contains(name.split('\\.').last)
			].forEach [
				load(resourceSet)
			]
		} else {
			resourceSet.getResource(URI.createFileURI(file.canonicalPath), true)
		}
	}
}
