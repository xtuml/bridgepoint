package org.xtuml.bp.xtext.masl.ui.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.validation.DefaultResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.LineAndColumn;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
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
	
	@Inject
	private MarkerCreator markerCreator;
	
	@Inject
	private IResourceValidator resourceValidator;
	
	private Logger log = Logger.getLogger(DefaultResourceUIValidatorExtension.class);

	@Inject(optional = true)
	private IssueResolutionProvider resolutionProvider;
	// Note this is copied from the MarkerCreator class
	// we cannot simply use it to create the marker as
	// it will not return it and allow us to xtUMLify it
	private static final String FIXABLE_KEY = "FIXABLE_KEY";

	/**
	 * We override marker creation so we can associate the problem markers
	 * with xtuml resources. 
	 * 
	 * If you want to see what the default marker creation would be if we do NOT
	 * do this you would simply comment out this routine's marker creation and
	 * call the super() be overridden here.
	 * 
	 */
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
			// if we could not find an action host node skip the problem
			// this will prevent creating duplicate problems against the
			// masl resource itself
			if(actionHostNode == null) {
				continue;
			}
			int lineNumber = issue.getLineNumber() - actionHostNode.getStartLine() + 1;
			int charStart = issue.getOffset() - actionHostNode.getOffset();
			LineAndColumn lineAndColumn = NodeModelUtils.getLineAndColumn(actualNode, issue.getLineNumber());
			// ignore if we did not find a service definition
			if (topLevelElement == null) {
				continue;
			}
			// get masl signature in xtUML format
			String signatureFromMasl = getMaslSignature(topLevelElement);
			NonRootModelElement xtUMLRootElement = getXtumlElementFromProblemElement(resource, topLevelElement,
					signatureFromMasl);
			if(xtUMLRootElement != null) {
				IMarker marker = xtUMLRootElement.getPersistableComponent().getFile()
						.createMarker(markerTypeProvider.getMarkerType(issue));
				setMarkerAttributes(issue, resource, marker, xtUMLRootElement, lineNumber, lineAndColumn.getColumn(), charStart);
			} else {
				// If we can't find the xtuml resource then use the default marker creation
				// Note that we ignore errors in resource files that are not xtuml
				// unless the errors are associated with xtuml model elements.
				// The case where elements are associated with xtuml elements is handled 
				// above.
				String fileExtension = resource.getFileExtension();
				if (fileExtension == Ooaofooa.MODELS_EXT) {
					markerCreator.createMarker(issue, resource, markerTypeProvider.getMarkerType(issue));
				} 				
			}
		}
	}

	private String getMaslSignature(AbstractTopLevelElement serviceDefinition) {
		String signature = "";
		if(serviceDefinition instanceof AbstractService) {
			AbstractTypeReference returnType = ((AbstractService) serviceDefinition).getReturnType();
			// if return type is null, use void
			if(returnType != null) {
				signature += ((AbstractNamed) returnType).getName();
			}
		}
		if(!signature.equals("")) {
			signature += " ";
		}
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

	/**
	 * This routine is used to find the xtuml file resource associated with the 
	 * given "IFile fileContaininghError". When the given IFile is an
	 * xtuml file this is easy. However when it is a masl resource this routine
	 * attempts to map to the xtuml resource file associated with the given
	 * IFile.
	 * 
	 * Errors markers are ALWAYS associated with file resources. Even the masl 
	 * snippet editor associates its error markers with a file resource. This 
	 * routine allows us to map errors in a ".masl" file to xtuml resources so we
	 * can open the snippet editor for error markers associated  with masl.
	 * 
	 * @param fileContaininghError This is the file resource that the error marker is 
	 *                 associated with.                 
	 * @param serviceDefinition
	 * @param signature
	 * @return
	 */
	private NonRootModelElement getXtumlElementFromProblemElement(IFile fileContaininghError, AbstractTopLevelElement serviceDefinition,
			String signature) {

		// This assumes that the xtuml resource associated with the 
		// resource where the error is marked
		IPath path = new Path(serviceDefinition.eResource().getURI().toPlatformString(true));
		String fileExtension = path.getFileExtension();
		if (fileExtension != Ooaofooa.MODELS_EXT) {
			// When a masl error is named with the same prefix and in the same
			// folder this allows us to look into the xtuml root to find the
			// associated xtuml instance
			path = path.removeFileExtension().addFileExtension(Ooaofooa.MODELS_EXT);
		} 
		
		PersistableModelComponent pmc = PersistenceManager.findOrCreateComponent(path);
		if (pmc != null) {
			// get the name of the xtUML element first
			// then if more than one get its signature and
			// compare with the signature from the service
			// definition
			String name = serviceDefinition.getName();
			List<NonRootModelElement> elementsByName = getModelElementsByName(pmc, name);
			for(NonRootModelElement element : elementsByName) {
				NonRootModelElement signatureElement = getSignatureElement(element);
				String xtUMLSignature = getXtumlSignature(signatureElement);
				if(signature.equals(xtUMLSignature)) {
					return element;
				}
			}
		}
		return null;
	}

	private NonRootModelElement getSignatureElement(NonRootModelElement element) {
		NonRootModelElement signatureElement = element;
		if(element instanceof ProvidedOperation_c) {
			ProvidedOperation_c op = (ProvidedOperation_c) element;
			signatureElement = InterfaceOperation_c.getOneC_IOOnR4004(
					ExecutableProperty_c.getOneC_EPOnR4501(ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(op)));
		}
		if(element instanceof RequiredOperation_c) {
			RequiredOperation_c op = (RequiredOperation_c) element;
			signatureElement = InterfaceOperation_c.getOneC_IOOnR4004(
					ExecutableProperty_c.getOneC_EPOnR4500(RequiredExecutableProperty_c.getOneSPR_REPOnR4502(op)));			
		}
		if(element instanceof ProvidedSignal_c) {
			ProvidedSignal_c sig = (ProvidedSignal_c) element;
			signatureElement = InterfaceSignal_c.getOneC_ASOnR4004(
					ExecutableProperty_c.getOneC_EPOnR4501(ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(sig)));			
		}
		if(element instanceof RequiredSignal_c) {
			RequiredSignal_c sig = (RequiredSignal_c) element;
			signatureElement = InterfaceSignal_c.getOneC_ASOnR4004(
					ExecutableProperty_c.getOneC_EPOnR4500(RequiredExecutableProperty_c.getOneSPR_REPOnR4502(sig)));			
		}
		return signatureElement;
	}

	private String getXtumlSignature(NonRootModelElement element) {
		try {
			Method method = element.getClass().getMethod("Getsignature", new Class[] {Integer.TYPE});
			String signature = (String) method.invoke(element, new Object[] {0});
			// strip void out as it is not used in the masl signature
			// and for interface op/sig there is no way to know which
			// should have it
			return signature.replaceAll("void ", ""); //$NON-NLS-1$
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
	
	/**
	 * Override addMarkers to delete markers against the xtuml Resource
	 */
	@Override
	protected void addMarkers(IFile file, Resource resource, CheckMode mode, IProgressMonitor monitor)
			throws OperationCanceledException {
		try {
			List<Issue> list = resourceValidator.validate(resource, mode, getCancelIndicator(monitor));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			// we need to delete markers against the xtuml resource
			IFile xtumlResource = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(file.getFullPath().removeFileExtension().addFileExtension(Ooaofooa.MODELS_EXT));
			if(xtumlResource.exists()) {
				file = xtumlResource;
			}
			deleteMarkers(file, mode, monitor);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			createMarkers(file, list, monitor);
		} catch (OperationCanceledError error) {
			throw error.getWrapped();
		} catch (CoreException e) {
			log.error(e.getMessage(), e);
		}
	}
}
