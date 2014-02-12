//=====================================================================
//
//File:      $RCSfile: ModelClassTextGenerator.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/01/17 03:42:31 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//========================================================================
package com.mentor.nucleus.bp.compare.text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.core.AttributeReferenceInClass_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.IModelClassInspector;
import com.mentor.nucleus.bp.core.inspector.IModelInspectorRegistry;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public class ModelClassTextGenerator
	implements ITextGenerator { //Text Generator per object
	public static final int SELFID_PART_INDEX = 0;
	public static final int ATTRIBUTE_PART_INDEX = 1;
	public static final int REFERENTIAL_PART_INDEX = 2;
	public static final int CHILDREN_PART_INDEX = 3;

	protected IModelInspectorRegistry registry;

	public ModelClassTextGenerator(IModelInspectorRegistry aRegistry) {
		registry = aRegistry;
	}

	public int getPartCount() {
		return 4;
	}

	public void generateFor(
		Object object,
		ITextBuffer buffer,
		int indentationLevel,
		IProgressMonitor monitor) {
		monitor.worked(1);
		generateFor(object, SELFID_PART_INDEX, buffer, indentationLevel, monitor);
		generateFor(object, ATTRIBUTE_PART_INDEX, buffer, indentationLevel, monitor);
		generateFor(object, REFERENTIAL_PART_INDEX, buffer, indentationLevel, monitor);
		generateFor(object, CHILDREN_PART_INDEX, buffer, indentationLevel + 1, monitor);
	}

	public void generateFor(
		Object object,
		int partIndex,
		ITextBuffer buffer,
		int indentationLevel,
		IProgressMonitor monitor) {
		IModelClassInspector modelClassInspector =
			registry.getInspector(object.getClass());
		if (modelClassInspector == null)
			throw new IllegalArgumentException(
				"Cannot generate text for "
					+ object.getClass().getName()
					+ ": IModelClassInspector NOT FOUND");

		switch (partIndex) {
			case SELFID_PART_INDEX :
				appendIndentingString(buffer, indentationLevel, 0);

				buffer.append(
					ComparePlugin.getResourceString(
						stripPackageName(object) + ".longname")); //$NON-NLS-1$
				buffer.append("\r\n"); //$NON-NLS-1$
				break;
			case ATTRIBUTE_PART_INDEX :
				generateForAttributes(
					modelClassInspector,
					object,
					buffer,
					indentationLevel + 1);
				break;
			case REFERENTIAL_PART_INDEX :
				generateForReferentials(
					modelClassInspector,
					object,
					buffer,
					indentationLevel + 1);
				break;
			case CHILDREN_PART_INDEX :
				if (modelClassInspector.hasChildRelations(object)) {
					ObjectElement childObjects[] =
						modelClassInspector.getChildRelations(object);
					for (int i = 0; i < childObjects.length; i++) {
						NonRootModelElement child = (NonRootModelElement)
												childObjects[i].getValue();
						if(!child.isProxy())
						{
							generateFor( childObjects[i].getValue(),
									buffer, indentationLevel + 1, monitor);
						}
					}
				}
				break;
			default :
				throw new IllegalArgumentException(
					"Invalid part Index:" + partIndex);
		}
	}

	public void generateForAttributes(
		IModelClassInspector inspector,
		Object object,
		ITextBuffer buffer,
		int indentationLevel) {

		ObjectElement[] attributes = getAttributefromInspector(object,inspector);
		for (int i = 0; i < attributes.length; i++) {
			if(attributes[i] == null) {
				continue;
			}
			//addding indentation
			appendIndentingString(buffer, indentationLevel, 0);

			String attribName =
				ComparePlugin.getResourceString(
					stripPackageName(object)
						+ ".attribute_"            //$NON-NLS-1$
						+ attributes[i].getName()
						+ ".longname");            //$NON-NLS-1$

			buffer.append(attribName);
			buffer.append(" : ");                  //$NON-NLS-1$
            //The +3 in bias is for " : " at the end of tag
			appendIndentedMultilineString(
				buffer,
				indentationLevel,
				attribName.length() + 3,
				String.valueOf(attributes[i].getValue()));

			buffer.append("\r\n");                   //$NON-NLS-1$
		}
	}

	private ObjectElement[] getAttributefromInspector(Object object, IModelClassInspector inspector){
		ObjectElement[] referentials;
		referentials = inspector.getAttributes(object);
		if (object instanceof ReferentialAttribute_c) {
			ObjectElement attrSet[] = new ObjectElement[1];
			attrSet[0] = referentials[0];			
			return attrSet;
		}
		for (int i = 0 ; i < referentials.length; i++)
		{	
			if(referentials[i] == null) {
				continue;
			}
			if( referentials[i].getValue() == null){
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)"ORPH1", referentials[i].getParent(), false);//$NON-NLS-1$ 
			}else if( referentials[i].getValue().toString().indexOf("ORPH1")+1 > 0 )	//$NON-NLS-1$
			{
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)referentials[i].getValue().toString().replaceAll("ORPH1", ""), referentials[i].getParent(), false);//$NON-NLS-1$
			}else if( referentials[i].getValue().toString().indexOf("ORPH2")+1 > 0 )	//$NON-NLS-1$
			{
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)referentials[i].getValue().toString().replaceAll("ORPH2", ""), referentials[i].getParent(), false);//$NON-NLS-1$
			}			
		}
		return referentials;			
	}
	public void generateForReferentials(
		IModelClassInspector inspector,
		Object object,
		ITextBuffer buffer,
		int indentationLevel) {

		ObjectElement[] referentials = inspector.getReferentials(object);
		if (object instanceof AttributeReferenceInClass_c) {
			return ;
		}

		for (int i = 0; i < referentials.length; i++) {

			appendIndentingString(buffer, indentationLevel, 0);

			buffer.append(
				ComparePlugin.getResourceString(
					stripPackageName(object)
						+ "."                        //$NON-NLS-1$
						+ referentials[i].getName()
						+ ".longname"));             //$NON-NLS-1$
			buffer.append(" : ");                    //$NON-NLS-1$

			if (referentials[i].getValue() != null)
				buffer.append(
					getTextRepresentation(referentials[i].getValue()));
			else
				buffer.append("null");               //$NON-NLS-1$
			buffer.append("\r\n");                     //$NON-NLS-1$
		}
	}

	protected static String stripPackageName(Object object) {
		String className = object.getClass().getName();

		int indexOfPoint = className.lastIndexOf('.');
		if (indexOfPoint >= 0)
			className = className.substring(indexOfPoint + 1);
		return className;
	}

	static final String indentFactorString = "  ";    //$NON-NLS-1$
	static final String singleSpace = " ";            //$NON-NLS-1$
	protected void appendIndentingString(
		ITextBuffer buffer,
		int indentLevel,
		int bias) {

		String indentString = "";                    //$NON-NLS-1$

		for (int i = 0; i < indentLevel; i++) {
			buffer.append(indentFactorString);
		}

		if (bias > 0) {
			for (int i = 0; i < bias; i++) {
				buffer.append(singleSpace);
			}
		}
	}

	protected void appendIndentedMultilineString(
		ITextBuffer buffer,
		int indentLevel,
		int tagLength,
		String attribStr) {
		char[] attributeCharArray = attribStr.toCharArray();

		int startIndex = 0;
		int endIndex = findEOLIndex(attributeCharArray, startIndex);
		while (startIndex < attributeCharArray.length) {
			if (startIndex > 0) {
				appendIndentingString(buffer, indentLevel, tagLength);
			}
			buffer.append(
				attributeCharArray,
				startIndex,
				endIndex - startIndex + 1);
			startIndex = endIndex + 1;
			endIndex = findEOLIndex(attributeCharArray, startIndex);
		}
	}

	private int findEOLIndex(char[] buffer, int startIndex) {
		int endIndex = buffer.length - 1;
		for (int i = startIndex; i <= endIndex; i++) {
			if (buffer[i] == '\r') {
				if (buffer[i + 1] == '\n')
					i++;
				return i;
			} else if (buffer[i] == '\n') {
				return i;
			}
		}
		return endIndex;
	}

	public static String getTextRepresentation(Object object) {
		return getTextRepresentation(object, true);
	}
	
	public static String getTextRepresentation(Object object, boolean loadDefault){
		String value = getStringByMethodDynamically(object, "getName");      //$NON-NLS-1$
		if (value == null || value == "")                                    //$NON-NLS-1$
			value = getStringByMethodDynamically(object, "Get_name");        //$NON-NLS-1$
		if ((value == null || value == "") && loadDefault){                  //$NON-NLS-1$
			value = getLongName(object);
		}
		return value;
	}
	
	public static String getLongName(Object object){
		return ComparePlugin.getResourceString(stripPackageName(object) + ".longname"); //$NON-NLS-1$
	}

	static String getStringByMethodDynamically(
		Object object,
		String methodName) {
		try {
			Method method =
				object.getClass().getMethod(methodName, new Class[0]);
			if (method != null) {
				Object value = method.invoke(object, new Object[0]);
				if( value.toString().indexOf("ORPH1")+1 > 0 ) 	//$NON-NLS-1$
				{
					value = (Object)value.toString().replaceAll("ORPH1", "");//$NON-NLS-1$ //$NON-NLS-2$
					if (value.equals(""))//$NON-NLS-1$ 
						return null;
				}else if( value.toString().indexOf("ORPH2")+1 > 0 ) 	//$NON-NLS-1$
				{
					value = (Object)value.toString().replaceAll("ORPH2", "");//$NON-NLS-1$ //$NON-NLS-2$
					if (value.equals(""))//$NON-NLS-1$ 
						return null;
				}else if (value.toString().indexOf("Orphaned")+1 > 0){//$NON-NLS-1$
					value = (Object)value.toString().replaceAll("Orphaned", "");//$NON-NLS-1$ //$NON-NLS-2$
					if (value.equals(""))//$NON-NLS-1$ 
						return null;
				}
				return value.toString();				
			}
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}

		return null;
	}

			}

