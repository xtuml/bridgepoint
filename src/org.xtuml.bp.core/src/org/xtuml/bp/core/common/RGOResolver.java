package org.xtuml.bp.core.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.ui.IModelImport;

/**
 * Hook up RGOs to RTOs during paste
 *
 */
public class RGOResolver {
	protected static HashMap<NonRootModelElement, NonRootModelElement> fOldProxyMap = new HashMap<NonRootModelElement, NonRootModelElement>();
	static class DowngradedElement {
		DowngradedElement(NonRootModelElement rto, NonRootModelElement rgo, String associationDowngraded) {
			this.rto = rto;
			this.rgo = rgo;
			this.associationDowngraded = associationDowngraded;
		}
		NonRootModelElement rto;
		NonRootModelElement rgo;
		String associationDowngraded;
	}
	/**
	 * This is how we add elements to the list to be hooked back up during paste.
	 * Note that we clear this list
	 */
	private static ArrayList<DowngradedElement> downgradedElements = new ArrayList<DowngradedElement>(); 
	
	public static void addDownGradedElement(NonRootModelElement rto, NonRootModelElement rgo, String associationDowngraded) {
		DowngradedElement element = new DowngradedElement(rto, rgo, associationDowngraded);
		downgradedElements.add(element);
	}
	
	/**
	 * We need to clear this list at the very start of the paste operation
	 * @see PasteAction
	 */
	public static void clearDowngradedElementsList() {
		downgradedElements.clear();
	}
	
	/**
	 * @param monitor
	 * @param fImporter This importer contains the elements that have, at this point, been 
	 * 				imported and hooked up to the destination model roots.
	 */
	protected void callResolutionOperations(IProgressMonitor monitor, IModelImport fImporter) {
		NonRootModelElement[] elements = fImporter.getLoadedInstances();
		monitor.beginTask("Resolving referred to elements...", downgradedElements.size());
		for (DowngradedElement downgradedElement : RGOResolver.downgradedElements ) {
			//TODO: Do something useful
			
			// If we fail to connect the element we need to report it to the user.
			// so they can decide if they want to abort the transaction or not
			TransactionManager.reportElementDowngraded(downgradedElement.rgo, downgradedElement.rto,
					downgradedElement.associationDowngraded, true);
			monitor.worked(1);
		}

	}

//	void callResolutionOperations(IProgressMonitor monitor, IModelImport fImporter) {
//	NonRootModelElement[] elements = getProxies();
//	monitor.beginTask("Resolving referred to elements...", elements.length
//			* 2 + fImporter.getLoadedInstances().length);
//	for (int i = 0; i < elements.length; i++) {
//		List<?> list = PersistenceManager.getHierarchyMetaData()
//				.findExternalRGOs(elements[i], false, false);
//		for (int j = 0; j < list.size(); j++) {
//			String refClassName = elements[i].getClass().getSimpleName()
//					.replaceAll("_c", "").toLowerCase();
//			try {
//				Method resolutionMethod = list.get(j).getClass().getMethod(
//						"Resolve" + refClassName, new Class[] {});
//				resolutionMethod.invoke(list.get(j), new Object[] {});
//			} catch (SecurityException e) {
//				// do nothing
//			} catch (NoSuchMethodException e) {
//				// do nothing
//			} catch (IllegalArgumentException e) {
//				// do nothing
//			} catch (IllegalAccessException e) {
//				// do nothing
//			} catch (InvocationTargetException e) {
//				CorePlugin.logError("Unable to invoke resolution method.", e);
//			}
//		}
//		monitor.worked(1);
//	}
//	
//	// resolve RGOs
//	NonRootModelElement[] exportedElements = fImporter.getLoadedInstances();
//	for (int i = 0; i < exportedElements.length; i++) {
//		NonRootModelElement element = getRTOElementForResolution(exportedElements[i]);
//		// if the old proxy map contains an element
//		// we must use it to find the RGOs
//		NonRootModelElement oldProxy = fOldProxyMap
//				.get(exportedElements[i]);
//		HashMap<String, List<NonRootModelElement>> list = null;
//		if (oldProxy != null) {
//			list = PersistenceManager.getHierarchyMetaData()
//					.getAssociationMapOfExternalRGOs(oldProxy);
//		} else {
//			list = PersistenceManager.getHierarchyMetaData()
//					.getAssociationMapOfExternalRGOs(element);
//		}
//		Set<String> keySet = list.keySet();
//		for(String key : keySet) {
//			List<NonRootModelElement> elementList = list.get(key);
// 			for (int j = 0; j < elementList.size(); j++) {
//				NonRootModelElement proxyElement = (NonRootModelElement) elementList
//						.get(j);
//				if (proxyElement.isProxy()) {
//					String refClassName = proxyElement.getClass()
//							.getSimpleName().replaceAll("_c", "").toLowerCase();
//					try {
//						Method resolutionMethod = element.getClass().getMethod(
//								"Resolve" + refClassName + "for" + key,
//								new Class[] { NonRootModelElement.class });
//						resolutionMethod.invoke(element,
//								new Object[] { proxyElement });
//					} catch (SecurityException e) {
//						// do nothing
//					} catch (NoSuchMethodException e) {
//						// do nothing
//					} catch (IllegalArgumentException e) {
//						// do nothing
//					} catch (IllegalAccessException e) {
//						// do nothing
//					} catch (InvocationTargetException e) {
//						// do nothing
//					}
//				}
//			}
//		}
//		monitor.worked(1);
//	}
//	for (int i = 0; i < elements.length; i++) {
//		// clean up all proxies
//		elements[i].batchUnrelate();
//		elements[i].delete_unchecked();
//		monitor.worked(1);
//	}
//}
	
	
//	private NonRootModelElement getRTOElementForResolution(
//			NonRootModelElement element) {
//		if (element instanceof UserDataType_c) {
//			DataType_c dt = DataType_c
//					.getOneS_DTOnR17((UserDataType_c) element);
//			return dt;
//		} else if (element instanceof StructuredDataType_c) {
//			DataType_c dt = DataType_c
//					.getOneS_DTOnR17((StructuredDataType_c) element);
//			return dt;
//		} else if (element instanceof EnumerationDataType_c) {
//			DataType_c dt = DataType_c
//					.getOneS_DTOnR17((EnumerationDataType_c) element);
//			return dt;
//		} else {
//			return element;
//		}
//	}

//	private NonRootModelElement[] getProxies() {
//	NonRootModelElement[] loadedInstances = fImporter.getLoadedInstances();
//	ArrayList<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
//	for (int i = 0; i < loadedInstances.length; i++) {
//		if (loadedInstances[i].isProxy()) {
//			list.add(loadedInstances[i]);
//		}
//	}
//	return list.toArray(new NonRootModelElement[list.size()]);
//	}

	

}
