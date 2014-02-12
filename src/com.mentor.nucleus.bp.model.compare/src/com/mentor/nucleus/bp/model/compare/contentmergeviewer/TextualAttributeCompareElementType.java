package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: TextualAttributeCompareElementType.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:34 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public class TextualAttributeCompareElementType implements ITypedElement,
		IStreamContentAccessor, IEditableContent {

	private static final String OAL_TYPE = "OAL";
	private ObjectElement element;
	private SynchronizedTreeViewer viewer;
	private boolean isLeft;
	private ObjectElement otherElement;
	private CompareConfiguration config;

	public TextualAttributeCompareElementType(ObjectElement element,
			SynchronizedTreeViewer viewer, boolean isLeft,
			ObjectElement otherElement, CompareConfiguration config) {
		this.element = element;
		this.viewer = viewer;
		this.isLeft = isLeft;
		this.otherElement = otherElement;
		this.config = config;
	}

	@Override
	public Image getImage() {
		if (element == null) {
			return CorePlugin.getImageFor(otherElement);
		}
		return CorePlugin.getImageFor(element.getParent());
	}

	@Override
	public String getName() {
		if (element == null) {
			return ((NonRootModelElement) otherElement.getParent()).getName();
		}
		return ((NonRootModelElement) element.getParent()).getName();
	}

	@Override
	public String getType() {
		if (element == null && otherElement != null
				&& otherElement.getName().contains("Action_")) {
			return OAL_TYPE;
		}
		if (element != null && element.getName().contains("Action_")) {
			return OAL_TYPE;
		} else {
			return TEXT_TYPE;
		}
	}

	@Override
	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(getValue().getBytes());
	}

	private String getValue() {
		if (element == null) {
			return "";
		}
		return (String) element.getValue();
	}

	@Override
	public boolean isEditable() {
		if (element == null) {
			return false;
		}
		// only allow if input is local
		boolean editable = config.isLeftEditable();
		if (!isLeft) {
			editable = config.isRightEditable();
		}
		if (editable) {
			if (element.getName().contains("Descrip")
					|| element.getName().contains("Action_")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ITypedElement replace(ITypedElement dest, ITypedElement src) {
		return dest;
	}

	@Override
	public void setContent(byte[] newContent) {
		String newContentString = new String(newContent);
		if(newContentString.equals(element.getValue())) {
			return;
		}
		// get a transaction from the compare transaction manager
		Transaction transaction = viewer.getMergeViewer().getCompareTransactionManager()
				.startCompareTransaction();
		if (isLeft) {
			viewer.getMergeViewer().markLeftDirty(true);
		} else {
			viewer.getMergeViewer().markRightDirty(true);
		}
		String attributeName = "Descrip";
		if (element.getName().contains("Action_")) {
			attributeName = "Action_semantics_internal";
		}
		String methodName = "set" + attributeName; //$NON-NLS-1$
		try {
			Method method = element.getParent().getClass()
					.getMethod(methodName, new Class[] { String.class });
			method.invoke(element.getParent(),
					new Object[] { newContentString });
		} catch (SecurityException e) {
			CorePlugin.logError("Unable to invoke "
					+ element.getParent().getClass().getSimpleName()
					+ methodName + " method for textual compare input.", e);
			return;
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Unable to invoke "
					+ element.getParent().getClass().getSimpleName()
					+ methodName + " method for textual compare input.", e);
			return;
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to invoke "
					+ element.getParent().getClass().getSimpleName()
					+ methodName + " method for textual compare input.", e);
			return;
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to invoke "
					+ element.getParent().getClass().getSimpleName()
					+ methodName + " method for textual compare input.", e);
			return;
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to invoke "
					+ element.getParent().getClass().getSimpleName()
					+ methodName + " method for textual compare input.", e);
			return;
		}
		element.setValue(newContentString);
		if(transaction != null) {
			viewer.getMergeViewer().getCompareTransactionManager()
					.endTransaction(transaction);
		}
	}

}
