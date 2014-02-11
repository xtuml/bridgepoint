//=====================================================================
//
//File:      $RCSfile: ModelCacheManager.java,v $
//Version:   $Revision: 1.32 $
//Modified:  $Date: 2013/01/10 22:49:51 $
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
package com.mentor.nucleus.bp.compare;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.compare.structuremergeviewer.ModelCompareStructureCreator.CompareDocumentRangeNode;
import com.mentor.nucleus.bp.compare.text.DefaultTextBuffer;
import com.mentor.nucleus.bp.compare.text.ITextGenerator;
import com.mentor.nucleus.bp.compare.text.ModelClassTextGenerator;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.inspector.IModelInspectorRegistry;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.io.mdl.ImportModelComponent;

public class ModelCacheManager {

	public static final String BODY_ID = "BodyForInternalUse????"; //$NON-NLS-1$

	Hashtable<Integer, ModelMapEntry> models = new Hashtable<Integer, ModelMapEntry>();

	ModelInspector modelInspector;

	public static boolean disableProgressMonitor = false;

	public ModelCacheManager(){
		MetadataSortingManager sortingManager = MetadataSortingManager.createDefault();
		modelInspector  = new ModelInspector(sortingManager);
	}

	public synchronized IStreamContentAccessor getModel(
		Object inputObject,
		Object requester)
		throws ModelLoadException {

		Integer inputId = new Integer(System.identityHashCode(inputObject));

		ModelMapEntry entry = models.get(inputId);
		if (entry == null) {
			entry = createModelMapEntry(inputObject);
			if(entry != null) {
				models.put(inputId, entry);
				if (!entry.isValid()) {
					throw new ModelLoadException(
							entry.errorMessage,
							entry.throwable);
				}
				entry.references.add(requester);
				return entry.rootNode;
			}
			
		} else {
			if(!entry.references.contains(requester)) {
				entry.references.add(requester);
			}
			return entry.rootNode;
		}
		return null;
	}

	public IModelInspectorRegistry getModelInspectorRegistry(){
		return modelInspector;
	}
	
	public void releaseModel(Object inputObject) {
		if(inputObject == null) {
			return;
		}

		Integer inputId = new Integer(System.identityHashCode(inputObject));
		ModelMapEntry entry = models.get(inputId);
		if(entry != null) {
			entry.references.clear();
			models.remove(inputId);
		}
	}
	
	public void releaseModel(Object inputObject, Object requester) {
		Integer inputId = new Integer(System.identityHashCode(inputObject));
		ModelMapEntry entry = models.get(inputId);
		if (entry != null) {
			entry.references.remove(requester);
			if (entry.references.isEmpty()) {
				releaseModelEntry(entry);
				models.remove(inputId);
			}
		}
	}

	protected ModelMapEntry createModelMapEntry(Object input) {
		ModelMapEntry modelMapEntry = new ModelMapEntry(input);
		if(!modelMapEntry.loadModel()) {
			return null;
		}
		return modelMapEntry;
	}

	public int getCachedModelCount() {
		return models.size();
	}

	protected void releaseModelEntry(ModelMapEntry entry) {
	}

	class ModelMapEntry {
		IStreamContentAccessor rootNode;
		String errorMessage;
		Throwable throwable;
		List<Object> references = new Vector<Object>();
		private Object input;

		ModelMapEntry(Object input) {
			this.input = input;
		}

		public boolean loadModel() {
			if (!(input instanceof IStreamContentAccessor)) {
				errorMessage = "Not a valid input";
			} else {
				InputStream sca=null;
				try {
					sca =
						((IStreamContentAccessor) input).getContents();

					if (sca == null) {
						return false;
					}
					IProgressMonitor progressMonitor = null;
					ProgressMonitorDialog processRunner = null;
					if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null
							|| PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell() == null || disableProgressMonitor ) {
						progressMonitor = new NullProgressMonitor();
					} else {
						processRunner =
							new ProgressMonitorDialog(
								PlatformUI
									.getWorkbench()
									.getActiveWorkbenchWindow()
									.getShell());
	
						progressMonitor =
							processRunner.getProgressMonitor();
					}

					Ooaofooa compareRoot = Ooaofooa.getInstance(
							ModelRoot.COMPARE_MODEL_ROOT_NAME, false);	
					SystemModel_c sys = new SystemModel_c(compareRoot);
					PersistableModelComponent com = new PersistableModelComponent(sys);
					final ImportModelComponent modelInputLoader =
						new ImportModelComponent(
							sca,
							compareRoot, com,
							false, true, false, false);
                    final int ret_val = modelInputLoader.countAndValidateInsertStatements();
                    if (ret_val > 0) {
                    	if(processRunner == null) {
                    		modelInputLoader.run(progressMonitor);
                    	} else {
                    		processRunner.run(true, false, modelInputLoader);
                    	}
                    }
                    else
                    {
                        modelInputLoader.m_success = false;
                    }
                    
                    modelInputLoader.finishComponentLoad(progressMonitor, false);
                    
					if (modelInputLoader.m_success) {
						if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null
								|| PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell() == null || disableProgressMonitor) {
							progressMonitor = new NullProgressMonitor();
						} else {
							processRunner =
								new ProgressMonitorDialog(
									PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getShell());
		
							progressMonitor =
								processRunner.getProgressMonitor();
						}
						if(processRunner != null) {
							processRunner.run(true, false, new IRunnableWithProgress() {
								
								@Override
								public void run(IProgressMonitor monitor) throws InvocationTargetException,
										InterruptedException {
									monitor.beginTask("Generating textual tree...", ret_val);
									ModelStructuredTextGenerator structuredTextGenerator =
										new ModelStructuredTextGenerator(modelInspector);

									Ooaofooa modelRoot =
										Ooaofooa.getInstance(
											ModelRoot.COMPARE_MODEL_ROOT_NAME, false);

									NonRootModelElement ele =  modelInputLoader.getRootModelElement();
									rootNode =
											structuredTextGenerator.createNodeTree(ele, monitor);
									ele.batchUnrelate();
									modelRoot.clearDatabase(monitor);									
								}
							});
						} else {
							ModelStructuredTextGenerator structuredTextGenerator =
								new ModelStructuredTextGenerator(modelInspector);

							Ooaofooa modelRoot =
								Ooaofooa.getInstance(
										ModelRoot.COMPARE_MODEL_ROOT_NAME, false);

							NonRootModelElement ele =  modelInputLoader.getRootModelElement();
							rootNode =
								structuredTextGenerator.createNodeTree(ele, progressMonitor);
							ele.batchUnrelate();
							modelRoot.clearDatabase(progressMonitor);
						}
					} else {
						errorMessage = modelInputLoader.m_errorMessage;
					}
				} catch (InvocationTargetException e) {
					throwable = e;
					errorMessage = "Internal Error";
					ComparePlugin.writeToLog(errorMessage, e, this);
				} catch (InterruptedException e) {
					throwable = e;
					errorMessage = "Process Interrupted";
					ComparePlugin.writeToLog(errorMessage, e, this);
				} catch (IOException e) {
					throwable = e;
					errorMessage = "Error while reading input resource";
					ComparePlugin.writeToLog(errorMessage, e, this);
				} catch (CoreException e) {
					throwable = e;
					errorMessage =e.getMessage(); 
					ComparePlugin.writeToLog(errorMessage, e, this);
				}
                finally
                {
                    if (sca != null)
                    {
                        try
                        {
                            sca.close();
                        }
                        catch (IOException e1)
                        {
                            throwable = e1;
                            errorMessage = "Error while closing input stream";
                            ComparePlugin.writeToLog(errorMessage, e1, this);
                        }
                    }
				}
			}
			return true;
		}

		public boolean isValid() {
			return (errorMessage == null);
		}
	}

	public static class ModelLoadException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ModelLoadException(String message, Throwable e) {
			super(message, e);
		}
	}

	public static class ModelStructuredTextGenerator extends ModelClassTextGenerator {
		Document compareDocument = new Document();
		ParentNode root;
		Stack<Object> compareNodesStack;

		public ModelStructuredTextGenerator(IModelInspectorRegistry registry) {
			super(registry);
			compareNodesStack = new Stack<Object>();
		}

		public CompareDocumentRangeNode createNodeTree(Object object, IProgressMonitor progressMonitor) {

			root =
				new ParentNode(
				    "root",   //$NON-NLS-1$
					"root",   //$NON-NLS-1$
					"root",   //$NON-NLS-1$
					compareDocument,
					0,
					0);

			ITextGenerator.ITextBuffer textBuffer = new DefaultTextBuffer();

			compareNodesStack.push(root);
			generateFor(object, textBuffer, 0, progressMonitor);
			compareNodesStack.pop();

			root.finallyRelateWithParent();
			root.setLength(textBuffer.getLength());
			return root.node;
		}

		public void generateFor(
			Object object,
		    ITextGenerator.ITextBuffer buffer,
			int indentationLevel,
			IProgressMonitor monitor) {
			
			monitor.worked(1);
			int offset = buffer.getLength();

			generateFor(object, SELFID_PART_INDEX, buffer, indentationLevel, monitor);
			generateFor(object, ATTRIBUTE_PART_INDEX, buffer, indentationLevel, monitor);
			generateFor(
				object,
				REFERENTIAL_PART_INDEX,
				buffer,
				indentationLevel, monitor);

			compareDocument.set(buffer.getText());
			
			int bodyLength = buffer.getLength() - offset;

			String objectName =
				ModelClassTextGenerator.getTextRepresentation(object, false);
				
			String objectType = object.getClass().getName();
			String objectId = objectType;
			
			if(objectName == null || objectName.trim().length() < 1){
				objectName = ModelClassTextGenerator.getLongName(object);
			}else{
				objectId = objectId + objectName;
			}
				
			ParentNode parentNode = (ParentNode) compareNodesStack.peek();
			ParentNode objectNode =
				new ParentNode(
				    objectId,
				    objectName,
				    objectType,
					compareDocument,
					offset,
					bodyLength);

			CompareDocumentRangeNode objectPropertiesNode =
				new CompareDocumentRangeNode(
				    BODY_ID,
				    objectName,
				    objectType,
					compareDocument,
					offset,
					bodyLength);

			parentNode.addToChildList(objectNode.node);
			objectNode.addToChildList(objectPropertiesNode);

			compareNodesStack.push(objectNode);
			generateFor(object, CHILDREN_PART_INDEX, buffer, indentationLevel, monitor);
			ParentNode popedNode = (ParentNode)compareNodesStack.pop();
			popedNode.finallyRelateWithParent();
			objectNode.setLength(buffer.getLength() - offset);
		}

		public String getText() {
			return compareDocument.get();
		}
		
		static class ParentNode {
			CompareDocumentRangeNode node = null;
			List<Object> children;
			
			ParentNode(String id, String name, String type, IDocument document, int start,  int length){
				node = new CompareDocumentRangeNode(id, name, type, document, start, length); 
			}
			
			public void addToChildList(CompareDocumentRangeNode child){
				if(children == null){
					children = new ArrayList<Object>();
					children.add(child);
				}else{
					CompareDocumentRangeNode existingChild = (CompareDocumentRangeNode)findObjectWithSameHash(child.hashCode());
					children.add(child);
					if(existingChild != null){
						child.setIndex(existingChild.getIndex() + 1);								
					}
				}
			}
			
			private Object findObjectWithSameHash(int hashCode){
				Object child = null;
				for(int i=children.size()-1; i>=0; i--){
					child = children.get(i);
					if(child.hashCode() == hashCode){
						return child;
					}
				}
				return null;
			}
			
			public void finallyRelateWithParent(){
				CompareDocumentRangeNode child = null;
				for(int i=0; i<children.size(); i++){
					child = (CompareDocumentRangeNode)children.get(i);
					child.setSortingIndex(i);
					child.setIncludeIndexInHash(true);
					node.addChild(child);
				}
				children.clear();
				children = null;
			}
			
			public void setLength(int length){
				node.setLength(length);
			}
		}
	}

}
