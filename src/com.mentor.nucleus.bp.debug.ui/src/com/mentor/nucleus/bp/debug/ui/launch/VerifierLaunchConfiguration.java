package com.mentor.nucleus.bp.debug.ui.launch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IPageLayout;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;

public class VerifierLaunchConfiguration {
	/* Launch configuration type identifier */
	public static final String LAUNCH_ID = "com.mentor.nucleus.bp.debug.ui.BPApplication";//$NON-NLS-1$

	/* Launch configuration default name */
	public static final String DEFAULT_CONFIG_NAME = "New_Verifier_Configuration";//$NON-NLS-1$

	/* unique identifier for the launch configurations */
	public static final String ATTR_SELECTEDMODELS = LAUNCH_ID + ".models";//$NON-NLS-1$
	public static final String ATTR_LOGACTIVITY = LAUNCH_ID + ".activity";//$NON-NLS-1$
	public static final String ATTR_ENABLESIMTIME = LAUNCH_ID + ".simtime";//$NON-NLS-1$
	public static final String ATTR_ENABLEDETERMINISM = LAUNCH_ID
			+ ".deterministic";//$NON-NLS-1$
	public static final String ATTR_EXECUTIONTIMEOUT = LAUNCH_ID + ".timeout";//$NON-NLS-1$
	public static final String ENABLED_STATE = "enabled"; //$NON-NLS-1$
	public static final String DISABLED_STATE = "disabled"; //$NON-NLS-1$

	// This should remain private so the format of the file is isolated to this
	// function.
	private static final String SEPARATOR = "_TOKEN_";
	// This should remain private so the format of the file is isolated to this
	// function.
	private static final String INTERNAL_SEPARATOR = "_INTERNALTOKEN_";

	public enum ConfigurationAttribute {
		ElementID(0), Multiplicity(1), Initializer(2), State(3);

		private int filePosition;

		private ConfigurationAttribute(int val) {
			filePosition = val;
		}

		protected int getPosition() {
			return filePosition;
		}

		public static String DefaultElementID = "";
		public static String DefaultMultiplicity = "1";
		public static String DefaultInitializer = "None";
		public static String DefaultState = ENABLED_STATE;
	}

	/**
	 * Create a string suitable for writing to the launch config file
	 */
	public static String getComponentSelectionString(String elementID,
			String multiplicity, String initializer, String state) {
		String result = elementID.toString();
		result += VerifierLaunchConfiguration.INTERNAL_SEPARATOR;
		result += multiplicity;
		result += VerifierLaunchConfiguration.INTERNAL_SEPARATOR;
		result += initializer;
		result += VerifierLaunchConfiguration.INTERNAL_SEPARATOR;
		result += state;
		return result;
	}

	/**
	 * Helper function for creating a model selection string that only requires
	 * the model ID and uses defaults for the other values.
	 */
	public static String getComponentSelectionString(String elementID) {
		return getComponentSelectionString(elementID,
				ConfigurationAttribute.DefaultMultiplicity,
				ConfigurationAttribute.DefaultInitializer,
				ConfigurationAttribute.DefaultState);

	}

	/**
	 * Helper function for creating a model selection string that only requires
	 * the model ID and uses defaults for the other values.
	 */
	public static String getComponentSelectionString(UUID elementID) {
		String elementIDString = "";
		if (elementID != null) {
			elementIDString = elementID.toString();
		}
		return getComponentSelectionString(elementIDString);
	}
	
	/**
	 * This function takes the given vector of model description strings and
	 * builds a single string suitable for the launch configuration file.
	 */
	public static String convertComponentSelectionVectorToString(
			Vector<String> modelIds) {
		String modelListString = "";
		for (int i = 0; i < modelIds.size(); i++) {
			modelListString = modelListString + modelIds.get(i)
					+ VerifierLaunchConfiguration.SEPARATOR;
		}
		return modelListString;
	}

	public static String[] getModelSelectionStrings(String modelListString) {
		return modelListString.split(VerifierLaunchConfiguration.SEPARATOR);
	}

	/**
	 * Helper function to safely split the configuration string into it's parts
	 * and retrieve the specified element.
	 * 
	 * @param elementString
	 *            An INTERNAL_SEPARATOR delimited string that holds the model
	 *            attributes persisted by our launch configuration.
	 * @return
	 */
	static String getInternalElement(String elementString,
			ConfigurationAttribute attribute) {
		String resultAttribute = "";

		if (elementString != null && !elementString.isEmpty()) {
			String[] modelSelectionAttributes = elementString
					.split(VerifierLaunchConfiguration.INTERNAL_SEPARATOR);
			switch (attribute) {
			case ElementID:
				resultAttribute = modelSelectionAttributes[ConfigurationAttribute.ElementID
						.getPosition()];
				break;
			case Multiplicity:
				resultAttribute = modelSelectionAttributes[ConfigurationAttribute.Multiplicity
						.getPosition()];
				break;
			case Initializer:
				resultAttribute = modelSelectionAttributes[ConfigurationAttribute.Initializer
						.getPosition()];
				break;
			case State:
				resultAttribute = modelSelectionAttributes[ConfigurationAttribute.State
						.getPosition()];
				break;
			}
		}
		return resultAttribute;
	}

	static String updateAllComponentSelectionStrings(String stringToChange,
			ConfigurationAttribute attribute, String newAttributeValue) 
	{	
		String resultString = "";
		String componentStrings[] = getModelSelectionStrings(stringToChange);
		for (int i = 0; i < componentStrings.length; i++) {
			if (!resultString.isEmpty()) {
				resultString = resultString + VerifierLaunchConfiguration.SEPARATOR;
			}
			resultString = resultString + updateOneComponentSelectionString(componentStrings[i], attribute, newAttributeValue);
		}
		return resultString;
	}
	
	/**
	 * Given a ComponentSelectionstring update the specified attribute with the
	 * given value and return the result.
	 * 
	 * @param stringToChange
	 * @param attribute
	 * @param newAttributeValue
	 * @return
	 */
	static String updateOneComponentSelectionString(String stringToChange,
			ConfigurationAttribute attribute, String newAttributeValue) {
		String resultString = stringToChange;
		if (stringToChange != null && !stringToChange.isEmpty()) {
			String[] modelSelectionAttributes = stringToChange
					.split(VerifierLaunchConfiguration.INTERNAL_SEPARATOR);
			String elementID = modelSelectionAttributes[ConfigurationAttribute.ElementID
			                    						.getPosition()];
			String multiplicity = modelSelectionAttributes[ConfigurationAttribute.Multiplicity
			                       						.getPosition()];
			String initializer = modelSelectionAttributes[ConfigurationAttribute.Initializer
			                      						.getPosition()];
			String state = modelSelectionAttributes[ConfigurationAttribute.State
			                						.getPosition()];
			switch (attribute) {
			case ElementID:
				elementID = newAttributeValue;
				break;
			case Multiplicity:
				multiplicity = newAttributeValue;
				break;
			case Initializer:
				initializer = newAttributeValue;
				break;
			case State:
				state = newAttributeValue;
				break;
			}
			resultString = getComponentSelectionString(elementID, multiplicity, initializer, state);
		}
		return resultString;
	}
	
	public static boolean isDefaultEntry(String modelString) {
		String[] modelSelectionAttributes = modelString
				.split(VerifierLaunchConfiguration.INTERNAL_SEPARATOR);
		if (!modelSelectionAttributes[ConfigurationAttribute.Multiplicity
				.getPosition()]
				.equals(ConfigurationAttribute.DefaultMultiplicity)) {
			return false;
		} else if (!modelSelectionAttributes[ConfigurationAttribute.Initializer
				.getPosition()]
				.equals(ConfigurationAttribute.DefaultInitializer)) {
			return false;
		} else if (!modelSelectionAttributes[ConfigurationAttribute.State
				.getPosition()].equals(DISABLED_STATE)) {
			return false;
		}
		return true;
	}

	/**
	 * Update the given vector of model configurations with the given 
	 * modelString.  If the given modelString is not found in the vector, 
	 * then it is added.
	 * 
	 * @param modelString The string to find and update in the given vector
	 * @param vector The vector to search
	 */
	public static void updateEntryInVector(String modelString, Vector<String> vector) {
		if (modelString.indexOf(VerifierLaunchConfiguration.INTERNAL_SEPARATOR) == -1) {
			// if string does not contain a separator, then this is an old
			// configuration
			// update accordingly
			modelString = getComponentSelectionString(modelString);
		}
		Iterator<String> iterator = vector.iterator();
		String match = "";
		while (iterator.hasNext()) {
			String entry = (String) iterator.next();
			if (VerifierLaunchConfiguration.getInternalElement(entry,
					ConfigurationAttribute.ElementID).equals(
					VerifierLaunchConfiguration.getInternalElement(modelString,
							ConfigurationAttribute.ElementID))) {
				match = entry;
				break;
			}
		}
		if (!match.equals("")) {
			// found the matching entry, replace the vectors entry
			vector.remove(match);
		}
		vector.add(modelString);
	}

	public static String getElementFromConfiguration(
			ILaunchConfiguration configuration, NonRootModelElement element,
			ConfigurationAttribute attribute) {
		String result = "";
		try {
			HashMap map = (HashMap) configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
					new HashMap());
			Set<String> systemSet = map.keySet();
			Iterator<String> iterator = systemSet.iterator();
			while (iterator.hasNext()) {
				String modelListString = (String) map.get(iterator.next());
				if (modelListString != null) {
					String[] modelStrings = modelListString
							.split(VerifierLaunchConfiguration.SEPARATOR);
					for (int i = 0; i < modelStrings.length; i++) {
						if (modelStrings[i].startsWith(element.Get_ooa_id()
								.toString())) {
							switch (attribute) {
							case ElementID:
								result = getInternalElement(modelStrings[i],
										ConfigurationAttribute.ElementID);
								break;
							case Multiplicity:
								result = getInternalElement(modelStrings[i],
										ConfigurationAttribute.Multiplicity);
								break;
							case Initializer:
								result = getInternalElement(modelStrings[i],
										ConfigurationAttribute.Initializer);
								break;
							case State:
								result = getInternalElement(modelStrings[i],
										ConfigurationAttribute.State);
								break;
							}
						}
					}
				}
			}
		} catch (CoreException e) {
			CorePlugin
					.logError(
							"Unable to get selected models in launch configuration.",
							e);
		}
		return result;
	}

	public static int getElementMultiplicityFromConfiguration(
			ILaunchConfiguration configuration, NonRootModelElement element) {
		int result = 0;
		result = Integer.valueOf(VerifierLaunchConfiguration
				.getElementFromConfiguration(configuration, element,
						ConfigurationAttribute.Multiplicity));
		return result;
	}

	/**
	 * Safely look at the configuration string to see if the enable element is
	 * true
	 * 
	 * @param elementString
	 * @return
	 */
	static boolean elementIsEnabled(String elementString) {
		boolean isEnabled = false;
		String enabledString = getInternalElement(elementString,
				ConfigurationAttribute.State);
		isEnabled = enabledString
				.equals(VerifierLaunchConfiguration.ENABLED_STATE);
		return isEnabled;
	}

	static String[] getEnabledIdsFromElementStrings(String[] elementStrings) {
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < elementStrings.length; i++) {
			if (elementIsEnabled(elementStrings[i]))
				idList.add(getInternalElement(elementStrings[i],
						ConfigurationAttribute.ElementID));
		}
		return idList.toArray(new String[idList.size()]);
	}

	public static NonRootModelElement[] getElementsSelectedInConfiguration(
			ILaunchConfiguration configuration) throws CoreException {
		List<NonRootModelElement> fullList = new ArrayList<NonRootModelElement>();
		HashMap map = (HashMap) configuration.getAttribute(
				VerifierLaunchConfiguration.ATTR_SELECTEDMODELS, new HashMap());
		Set systemSet = map.keySet();
		Iterator iterator = systemSet.iterator();
		while (iterator.hasNext()) {
			String s = (String) map.get(iterator.next());
			if (s != null) {
				String[] ids = s.split(VerifierLaunchConfiguration.SEPARATOR);
				List<NonRootModelElement> list = BPDebugUtils
						.getVerifiableElements(getEnabledIdsFromElementStrings(ids));
				for (int i = 0; i < list.size(); i++) {
					fullList.add(list.get(i));
				}
			}
		}
		return fullList.toArray(new NonRootModelElement[fullList.size()]);
	}

	public static NonRootModelElement[] getElementsSelectedInConfigurationForProject(
			ILaunchConfiguration configuration, String projectName) {
		List<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		NonRootModelElement[] elements;
		try {
			elements = getElementsSelectedInConfiguration(configuration);
			for (int i = 0; i < elements.length; i++) {
				if (BPDebugUtils.getElementsSystem(elements[i]).getName()
						.equals(projectName)) {
					list.add(elements[i]);
				}
			}
		} catch (CoreException e) {
			CorePlugin.logError(
					"Unable to get elements from launch configuration.", e);
		}
		return list.toArray(new NonRootModelElement[list.size()]);
	}

	public static Vector<NonRootModelElement> getInitializers(
			ILaunchConfiguration configuration) {
		Vector<NonRootModelElement> initializerElements = new Vector<NonRootModelElement>();
		try {
			HashMap map = (HashMap) configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
					new HashMap());
			Set systemSet = map.keySet();
			Iterator keyIterator = systemSet.iterator();
			while (keyIterator.hasNext()) {
				String launchString = (String) map.get(keyIterator.next());
				if (launchString != null) {
					String[] elementsInConfiguration = launchString
							.split(VerifierLaunchConfiguration.SEPARATOR);
					for (int i = 0; i < elementsInConfiguration.length ; i++) {
						if (elementIsEnabled(elementsInConfiguration[i])) {
							String initializer = getInternalElement(elementsInConfiguration[i],
									ConfigurationAttribute.Initializer);
							if (!initializer.equalsIgnoreCase(ConfigurationAttribute.DefaultInitializer)) {
								// Get the NonRootModelElement for the given ID string
								String parentString = getInternalElement(elementsInConfiguration[i],
										ConfigurationAttribute.ElementID);
								NonRootModelElement parent = BPDebugUtils.getVerifiableElement(parentString);
								initializerElements.add(VerifiableElementInitializerDialog.getInitializerMessageFromUUIDString(parent, initializer));
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			BPDebugUIPlugin
			.logError(
					"Failed to get initializer from the launch config.",
					e);
		}

		return initializerElements;
	}

	private static void addToLaunchVector(NonRootModelElement elements[],
			Vector<String> vector) {
		for (int i = 0; i < elements.length; i++) {
			vector.add(getComponentSelectionString(elements[i].Get_ooa_id()));
		}
	}

	/**
	 * list of models in the Project(SystemModel_c)
	 */
	public static Vector<String> getVerifiableModelIdsInProject(
			String projectname) {
		// list of projects
		SystemModel_c[] x = SystemModel_c.SystemModelInstances(Ooaofooa
				.getDefaultInstance());

		for (int j = 0; j < x.length; j++) {
			if (x[j].getName().equals(projectname)) {
				Vector<String> temp = new Vector<String>();

				addToLaunchVector(Domain_c.getManyS_DOMsOnR28(x[j]), temp);
				addToLaunchVector(Component_c
						.getManyC_CsOnR4608(ComponentPackage_c
								.getManyCP_CPsOnR4606(x[j])), temp);
				Component_c[] components = Component_c
						.getManyC_CsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1405(x[j])));
				addToLaunchVector(components, temp);
				addToLaunchVector(Component_c
						.getManyC_CsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(components)), temp);
				addToLaunchVector(ComponentReference_c
						.getManyCL_ICsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(components)), temp);
				addToLaunchVector(ComponentReference_c
						.getManyCL_ICsOnR4605(ComponentPackage_c
								.getManyCP_CPsOnR4606(x[j])), temp);
				addToLaunchVector(ComponentReference_c
						.getManyCL_ICsOnR4205(Component_c
								.getManyC_CsOnR4608(ComponentPackage_c
										.getManyCP_CPsOnR4606(x[j]))), temp);
				addToLaunchVector(ComponentReference_c
						.getManyCL_ICsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1405(x[j]))), temp);
				return temp;
			}
		}

		return null;
	}

	public static String generateUniqueLaunchConfigurationName(String baseName)
			throws CoreException {
		int index = 1;
		int length = baseName.length();
		int copyIndex = baseName.lastIndexOf(" ("); //$NON-NLS-1$

		if ((copyIndex > -1) && (length > (copyIndex + 2))
				&& (baseName.charAt(length - 1) == ')')) {
			String trailer = baseName.substring(copyIndex + 2, length - 1);

			if (isNumber(trailer)) {
				try {
					index = Integer.parseInt(trailer);
					baseName = baseName.substring(0, copyIndex);
				} catch (NumberFormatException nfe) {
				}
			}
		}

		String newName = baseName;

		StringBuffer buffer = null;
		ILaunchManager mgr = DebugPlugin.getDefault().getLaunchManager();

		while (mgr.isExistingLaunchConfigurationName(newName)) {
			buffer = new StringBuffer(baseName);
			buffer.append(" ("); //$NON-NLS-1$
			buffer.append(String.valueOf(index));
			index++;
			buffer.append(')');
			newName = buffer.toString();
		}

		return newName;
	}

	private static boolean isNumber(String string) {
		int numChars = string.length();

		if (numChars == 0) {
			return false;
		}

		for (int i = 0; i < numChars; i++) {
			if (!Character.isDigit(string.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	public static void updateLaunchConfigForProject(IProject project,
			ILaunchConfiguration config, String mode) {
		ILaunchConfigurationWorkingCopy copy = new BPMainTabGroup()
				.getWorkingCopy(mode);

		Vector modelIds = VerifierLaunchConfiguration
				.getVerifiableModelIdsInProject(project.getName());
		modelIds.trimToSize();

		Map<String, String> map = new HashMap<String, String>();
		String ids = VerifierLaunchConfiguration
				.convertComponentSelectionVectorToString(modelIds);
		map.put(project.getName(), ids);
		copy.setAttribute(VerifierLaunchConfiguration.ATTR_SELECTEDMODELS, map);

		DebugUITools.launch(copy, mode);
	}

	/**
	 * Return true if the given configuration has each element selected for
	 * launch. Only true if the selections are an exact match.
	 * 
	 * @param file
	 *            - element's file
	 * @param configuration
	 *            - Launch configuration
	 * @return
	 */
	public static boolean configurationHasExactElementsSelected(
			NonRootModelElement[] elements, ILaunchConfiguration configuration) {
		try {
			Map<?, ?> map = configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
					new Hashtable<Object, Object>());
			Set<?> projectSet = map.keySet();
			List<String> allIds = new ArrayList<String>();
			Iterator<?> projectIterator = projectSet.iterator();
			while (projectIterator.hasNext()) {
				String s = (String) map.get(projectIterator.next());
				if (s != null) {
					String[] ids = s
							.split(VerifierLaunchConfiguration.SEPARATOR);
					for (int i = 0; i < ids.length; i++) {
						allIds.add(ids[i]);
					}
				}
			}

			ArrayList<NonRootModelElement> verifiableElements = BPDebugUtils
					.getVerifiableElements(allIds.toArray(new String[allIds
							.size()]));
			if (verifiableElements.size() == elements.length) {
				// first exact check
				for (int i = 0; i < elements.length; i++) {
					boolean found = false;
					for (int j = 0; j < verifiableElements.size(); j++) {
						if (elements[i] == verifiableElements.get(j)) {
							found = true;
							break;
						}
					}
					if (!found) {
						return false;
					}
				}
				// if we reach this point we
				// have found a match for each
				// selected element
				return true;
			}
		} catch (CoreException e) {
			CorePlugin.logError("Unable to read launch configuration.", e);
		}
		return false;
	}

	public static StringBuffer getVerifiableElementId(
			NonRootModelElement element) {
		StringBuffer buffer = new StringBuffer();
		if (element instanceof Package_c) {
			NonRootModelElement[] children = BPDebugUtils
					.getPackageChildren((Package_c) element);
			for (int i = 0; i < children.length; i++) {
				buffer
						.append(getComponentSelectionString(children[i]
								.Get_ooa_id()));
				buffer.append(VerifierLaunchConfiguration.SEPARATOR);
			}
		} else if (element instanceof ComponentPackage_c) {
			NonRootModelElement[] children = BPDebugUtils
					.getComponentPackageChildren((ComponentPackage_c) element);
			for (int i = 0; i < children.length; i++) {
				buffer
						.append(getComponentSelectionString(children[i]
								.Get_ooa_id()));
				buffer.append(VerifierLaunchConfiguration.SEPARATOR);
			}
		} else if (element instanceof Component_c) {
			NonRootModelElement[] children = BPDebugUtils
					.getComponentChildren((Component_c) element);
			if (children.length > 0) {
				buffer.append(element.Get_ooa_id().toString()
						+ VerifierLaunchConfiguration.SEPARATOR);
				for (int i = 0; i < children.length; i++) {
					buffer.append(getComponentSelectionString(children[i]
							.Get_ooa_id()));
					buffer.append(VerifierLaunchConfiguration.SEPARATOR);
				}
			} else {
				buffer.append(getComponentSelectionString(element.Get_ooa_id()));
				buffer.append(VerifierLaunchConfiguration.SEPARATOR);
			}
		} else if (element instanceof Domain_c
				|| element instanceof ComponentReference_c) {
			buffer.append(getComponentSelectionString(element.Get_ooa_id()));
			buffer.append(VerifierLaunchConfiguration.SEPARATOR);
		}
		return buffer;
	}

}
