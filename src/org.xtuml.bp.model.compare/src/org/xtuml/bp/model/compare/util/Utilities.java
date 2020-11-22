package org.xtuml.bp.model.compare.util;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.patch.IHunk;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.xtuml.bp.model.compare.ComparePlugin;

public class Utilities {

	/**
	 * 
	 * @param <code>IAction<code> Action to initialize
	 * @param prefix,             prefix for the properties file configuration
	 *                            option
	 */
	public static void initAction(IAction a, String prefix) {
		String labelKey = "label"; //$NON-NLS-1$
		String tooltipKey = "tooltip"; //$NON-NLS-1$
		String imageKey = "image"; //$NON-NLS-1$
		String descriptionKey = "description"; //$NON-NLS-1$

		if (prefix != null && prefix.length() > 0) {
			labelKey = prefix + labelKey;
			tooltipKey = prefix + tooltipKey;
			imageKey = prefix + imageKey;
			descriptionKey = prefix + descriptionKey;
		}

		a.setText(ComparePlugin.getResourceString(labelKey));
		a.setToolTipText(ComparePlugin.getResourceString(tooltipKey));
		a.setDisabledImageDescriptor(getImageDescriptor(imageKey, true));
		a.setImageDescriptor(getImageDescriptor(imageKey, false));
		a.setHoverImageDescriptor(getImageDescriptor(imageKey, false));
	}

	private static ImageDescriptor getImageDescriptor(String key, boolean disabled) {
		String root = "elcl16/";
		if(disabled) {
			root = "dlcl16/";
		}
		return ComparePlugin.getImageDescriptor(
				"icons/full/" + root + ComparePlugin.getResourceString(key));
	}

	public static boolean getBoolean(CompareConfiguration cc, String key, boolean fallback) {
		if (cc != null) {
			Object value = cc.getProperty(key);
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			}
		}
		return fallback;
	}

	public static boolean isHunk(Object input) {
		if (input != null && input instanceof DiffNode) {
			ITypedElement right = ((DiffNode) input).getRight();
			if (Adapters.adapt(right, IHunk.class) != null) {
				return true;
			}
			ITypedElement left = ((DiffNode) input).getLeft();
			if (Adapters.adapt(left, IHunk.class) != null) {
				return true;
			}
		}
		return false;
	}
}
