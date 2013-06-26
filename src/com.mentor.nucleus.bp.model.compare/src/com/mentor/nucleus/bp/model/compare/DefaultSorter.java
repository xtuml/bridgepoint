package com.mentor.nucleus.bp.model.compare;

import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public class DefaultSorter implements Comparator<Object> {

	private static Comparator<? super Object> singleton;

	@Override
	public int compare(Object first, Object second) {
		if (first instanceof NonRootModelElement
				&& second instanceof NonRootModelElement) {
			if (first.getClass().isInstance(second)) {
				NonRootModelElement firstElement = (NonRootModelElement) first;
				NonRootModelElement secondElement = (NonRootModelElement) second;
				UUID firstId = firstElement.Get_ooa_id();
				UUID secondId = secondElement.Get_ooa_id();
				return firstId.compareTo(secondId);
			} else {
				return -1;
			}
		}
		if (first instanceof ObjectElement && second instanceof ObjectElement) {
			ObjectElement firstObjEle = (ObjectElement) first;
			ObjectElement secondObjEle = (ObjectElement) second;
			if (firstObjEle.getValue() instanceof NonRootModelElement
					&& secondObjEle.getValue() instanceof NonRootModelElement) {
				NonRootModelElement firstParent = (NonRootModelElement) ((ObjectElement) first)
						.getValue();
				NonRootModelElement secondParent = (NonRootModelElement) ((ObjectElement) second)
						.getValue();
				if (firstParent.getClass().isInstance(secondParent)) {
					// for transitions sort by name
					if(firstParent instanceof Transition_c) {
						return ((Transition_c) firstParent).Get_name().compareTo(
								((Transition_c) secondParent).Get_name());
					}
					UUID firstId = firstParent.Get_ooa_id();
					UUID secondId = secondParent.Get_ooa_id();
					return firstId.compareTo(secondId);
				} else {
					return -1;
				}
			}
		}
		return -1;
	}

	public static void sort(Object[] elements) {
		Arrays.sort(elements, getSingleton());
	}

	private static Comparator<? super Object> getSingleton() {
		if (singleton == null) {
			singleton = new DefaultSorter();
		}
		return singleton;
	}
}