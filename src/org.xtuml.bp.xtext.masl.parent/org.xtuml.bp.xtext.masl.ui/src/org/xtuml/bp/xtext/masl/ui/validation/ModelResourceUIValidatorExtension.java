package org.xtuml.bp.xtext.masl.ui.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.validation.DefaultResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.LineAndColumn;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Issue;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.InstanceList;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.xtext.masl.masl.structure.AbstractNamed;
import org.xtuml.bp.xtext.masl.masl.structure.AbstractService;
import org.xtuml.bp.xtext.masl.masl.structure.AbstractTopLevelElement;
import org.xtuml.bp.xtext.masl.masl.structure.Parameter;
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized;
import org.xtuml.bp.xtext.masl.masl.types.AbstractTypeReference;
import org.xtuml.bp.xtext.masl.masl.types.NamedTypeReference;
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration;

import com.google.inject.Inject;

public class ModelResourceUIValidatorExtension extends DefaultResourceUIValidatorExtension {

	@Inject
	private MarkerTypeProvider markerTypeProvider;

	@Inject(optional = true)
	private IssueResolutionProvider resolutionProvider;
	// Note this is copied from the MarkerCreator class
	// we cannot simply use it to create the marker as
	// it will not return it and allow us to xtUMLify it
	private static final String FIXABLE_KEY = "FIXABLE_KEY";

	@Override
	protected void createMarkers(IFile resource, List<Issue> issues, IProgressMonitor monitor) throws CoreException {
		/**
		 * Create a marker for every issue
		 */
		for (Issue issue : issues) {
			ResourceSet resSet = new ResourceSetImpl();
			Resource res = resSet.getResource(issue.getUriToProblem(), true);
			EObject eObject = res.getEObject(issue.getUriToProblem().fragment());
			// look for the service definition in the hierarchy
			AbstractTopLevelElement topLevelElement = getServiceDefinition(eObject);
			ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(eObject);
			ICompositeNode actionHostNode = NodeModelUtils.findActualNodeFor(topLevelElement);
			int lineNumber = issue.getLineNumber() - actionHostNode.getStartLine();
			int charStart = issue.getOffset();
			if(issue.getOffset() > actualNode.getOffset()) {
				charStart = issue.getOffset() - actualNode.getOffset();
			}
			LineAndColumn lineAndColumn = NodeModelUtils.getLineAndColumn(actualNode, issue.getLineNumber());
			// ignore if we did not find a service definition
			if (topLevelElement == null) {
				continue;
			}
			// get masl signature in xtUML format
			String signatureFromMasl = getMaslSignature(topLevelElement);
			NonRootModelElement xtUMLRootElement = getXtumlElementFromProblemElement(topLevelElement,
					signatureFromMasl);
			if(xtUMLRootElement == null) {
				return;
			}
			IMarker marker = xtUMLRootElement.getPersistableComponent().getFile()
					.createMarker(markerTypeProvider.getMarkerType(issue));
			setMarkerAttributes(issue, resource, marker, xtUMLRootElement, lineNumber, lineAndColumn.getColumn(), charStart);
		}
	}

	private String getMaslSignature(AbstractTopLevelElement serviceDefinition) {
		String signature = "";
		if(serviceDefinition instanceof AbstractService) {
			AbstractTypeReference returnType = ((AbstractService) serviceDefinition).getReturnType();
			// if return type is null, use void
			if(returnType != null) {
				signature += ((AbstractNamed) returnType).getName();
			} else {
				signature += "void"; //$NON-NLS-1$
			}
		} else {
			signature += "void"; //$NON-NLS-1$
		}
		signature += " ";
		signature += serviceDefinition.getName();
		if(serviceDefinition instanceof Parameterized) {
			String parameterList = "";
			EList<Parameter> parameters = ((Parameterized) serviceDefinition).getParameters();
			if(!parameters.isEmpty()) {
				parameterList += "(";
			}
			int count = 0;
			for(Parameter parameter : parameters) {
				String name = parameter.getName();
				TypeDeclaration type = ((NamedTypeReference) parameter.getType()).getType();
				parameterList += name;
				parameterList += ":";
				parameterList += type.getName();
				if(count != parameters.size() - 1) {
					parameterList += ",";
				}
				count++;
			}
			if(!parameters.isEmpty()) {
				parameterList += ")";
				signature += parameterList;
			}
		}
		return signature;
	}

	private AbstractTopLevelElement getServiceDefinition(EObject eObject) {
		// go up the hierarchy until we find a top level
		// element
		EObject element = eObject.eContainer();
		while (element != null) {
			if (element instanceof AbstractTopLevelElement) {
				return (AbstractTopLevelElement) element;
			}
			element = element.eContainer();
		}
		return null;
	}

	private NonRootModelElement getXtumlElementFromProblemElement(AbstractTopLevelElement serviceDefinition,
			String signature) {
		// get the name of the xtUML element first
		// then if more than one get its signature and
		// compare with the signature from the service
		// definition
		String name = serviceDefinition.getName();
		IPath path = new Path(serviceDefinition.eResource().getURI().toPlatformString(true)).removeFileExtension()
				.addFileExtension(Ooaofooa.MODELS_EXT);
		PersistableModelComponent pmc = PersistenceManager.findOrCreateComponent(path);
		List<NonRootModelElement> elementsByName = getModelElementsByName(pmc, name);
		for(NonRootModelElement element : elementsByName) {
			String xtUMLSignature = getXtumlSignature(element);
			if(signature.equals(xtUMLSignature)) {
				return element;
			}
		}
		return null;
	}

	private String getXtumlSignature(NonRootModelElement element) {
		try {
			Method method = element.getClass().getMethod("Getsignature", new Class[] {Integer.TYPE});
			return (String) method.invoke(element, new Object[] {0});
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// throw an error as this logic assumes that all
			// elements which require a marker also require a
			// getSignature operation in xtUML
			CorePlugin.logError("Unable to get signature method from xtUML class.", e);
		}
		return "";
	}

	Class<?>[] serviceTypes = new Class[] { Function_c.class, Operation_c.class, ProvidedOperation_c.class,
			ProvidedSignal_c.class, RequiredOperation_c.class, RequiredSignal_c.class, Attribute_c.class,
			StateMachineState_c.class, Transition_c.class, CreationTransition_c.class, Bridge_c.class };

	private List<NonRootModelElement> getModelElementsByName(PersistableModelComponent pmc, String name) {
		List<NonRootModelElement> elementsByName = new ArrayList<NonRootModelElement>();
		// check all possible types
		ModelRoot modelRoot = pmc.getRootModelElement().getModelRoot();
		for (Class<?> type : serviceTypes) {
			InstanceList instanceList = modelRoot.getInstanceList(type);
			for (NonRootModelElement element : instanceList) {
				if (element.getName().equals(name)) {
					elementsByName.add(element);
				}
			}
		}
		return elementsByName;
	}

	/**
	 * See comment above around FIXABLE_KEY, same applies here... except for the
	 * addition of the NRME
	 * 
	 * @param xtUMLRootElement
	 * @param actualNode 
	 * 
	 * @since 2.0
	 */
	protected void setMarkerAttributes(Issue issue, IResource resource, IMarker marker,
			NonRootModelElement xtUMLRootElement, int lineNumber, int column, int charStart) throws CoreException {
		String lineNR = "";
		if (issue.getLineNumber() != null) {
			lineNR = "line: " + lineNumber + " ";
		}
		marker.setAttribute(IMarker.LOCATION, lineNR + xtUMLRootElement.getPath());
		marker.setAttribute(Issue.CODE_KEY, issue.getCode());
		marker.setAttribute(IMarker.SEVERITY, getSeverity(issue));
		marker.setAttribute(IMarker.CHAR_START, charStart);
		if (issue.getOffset() != null && issue.getLength() != null)
			marker.setAttribute(IMarker.CHAR_END, charStart + issue.getLength());
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		marker.setAttribute(Issue.COLUMN_KEY, column);
		marker.setAttribute(IMarker.MESSAGE, issue.getMessage());

		if (issue.getUriToProblem() != null)
			marker.setAttribute(Issue.URI_KEY, issue.getUriToProblem().toString());
		if (issue.getData() != null && issue.getData().length > 0) {
			marker.setAttribute(Issue.DATA_KEY, Strings.pack(issue.getData()));
		}
		if (resolutionProvider != null && resolutionProvider.hasResolutionFor(issue.getCode())) {
			marker.setAttribute(FIXABLE_KEY, true);
		}

		// here we add xtuml related data for editor navigation support
		marker.setAttribute(IntegrityChecker.PATH_TYPE, xtUMLRootElement.getFile().getFullPath().toString());
		Object[] key = (Object[]) xtUMLRootElement.getInstanceKey();
		String keyString = "";
		String sep = "";
		for (Object elem : key) {
			keyString = keyString + sep + elem.toString();
			sep = "%";
		}
		marker.setAttribute(IntegrityChecker.ID_TYPE, keyString);
		marker.setAttribute(IntegrityChecker.TYPE_TYPE, xtUMLRootElement.getClass().toString());
		marker.setAttribute(IDE.EDITOR_ID_ATTR, "org.xtuml.bp.core.ui.editor.RoutingEditor"); //$NON-NLS-1$
	}

	/**
	 * See comment above around FIXABLE_KEY, same applies here...
	 */
	private Object getSeverity(Issue issue) {
		switch (issue.getSeverity()) {
		case ERROR:
			return IMarker.SEVERITY_ERROR;
		case WARNING:
			return IMarker.SEVERITY_WARNING;
		case INFO:
			return IMarker.SEVERITY_INFO;
		default:
			throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
		}
	}

}
