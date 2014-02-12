//=====================================================================
//
//File:      $RCSfile: ModelCacheManager.java,v $
//Version:   $Revision: 1.4.26.3 $
//Modified:  $Date: 2013/07/29 21:05:52 $
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
package com.mentor.nucleus.bp.model.compare;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.ModelStreamProcessor;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.inspector.IModelInspectorRegistry;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.io.core.CoreImport;
import com.mentor.nucleus.bp.io.core.ImportHelper;
import com.mentor.nucleus.bp.io.mdl.ImportModelComponent;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.inspector.GraphicalModelInspector;

public class ModelCacheManager {

	public static final String BODY_ID = "BodyForInternalUse????"; //$NON-NLS-1$

	private static final int LEFT = 0;

	private static final int ANCESTOR = 2;

	private static final int RIGHT = 1;

	public static boolean disableProgressMonitor = false;

	Hashtable<Object, Object> models = new Hashtable<Object, Object>();

	ModelInspector modelInspector;
	GraphicalModelInspector graphicalModelInspector;

	private HashMap<Object, NonRootModelElement[]> rootElementMap = new HashMap<Object, NonRootModelElement[]>();
	private List<Object> readonlyInputs = new ArrayList<Object>();

	public ModelCacheManager() {
		MetadataSortingManager sortingManager = MetadataSortingManager
				.createDefault();
		modelInspector = new ModelInspector(sortingManager);
		graphicalModelInspector = new GraphicalModelInspector(sortingManager);
	}

	public IStreamContentAccessor getModel(Object inputObject,
			Object requester, Ooaofooa compareRoot, Object key)
			throws ModelLoadException {

		// if input is null, could be an ancestor that is not available
		if (inputObject == null) {
			return null;
		}

		ModelMapEntry entry = (ModelMapEntry) models.get(key);
		if (entry == null) {
			entry = createModelMapEntry(inputObject, compareRoot, key);
			if (entry != null) {
				models.put(key, entry);
				if (!entry.isValid()) {
					throw new ModelLoadException(entry.errorMessage,
							entry.throwable);
				}
				if(requester != null) {
					entry.references.add(requester);
				}

				return entry.rootNode;
			}

		} else {
			return entry.rootNode;
		}
		return null;
	}

	public Model_c getLoadedGraphicalModelsForElements(Object element) {
		for (Object key : models.keySet()) {
			ModelMapEntry entry = (ModelMapEntry) models.get(key);
			Model_c[] loadedModels = entry.loadedModels;
			if (loadedModels == null) {
				continue;
			}
			for (Model_c model : loadedModels) {
				if (model.getRepresents() == element) {
					return model;
				}
			}
		}
		return null;
	}

	public NonRootModelElement[] getRootElements(Object inputObject,
			Object requester, boolean reload, Ooaofooa compareRoot, Object key)
			throws ModelLoadException {
		NonRootModelElement[] rootElements = rootElementMap.get(key);
		if (rootElements == null && requester != null) {
			getModel(inputObject, requester, compareRoot, key);
			rootElements = rootElementMap.get(key);
		}
		return rootElements;
	}

	public IModelInspectorRegistry getModelInspectorRegistry() {
		return modelInspector;
	}

	public void releaseModel(Object inputObject, Object requester,
			Ooaofooa compareRoot, Object key) {
		if (inputObject == null) {
			return;
		}
		ModelMapEntry entry = (ModelMapEntry) models.get(key);
		if (entry != null) {
			entry.references.remove(requester);
			if (entry.references.isEmpty()) {
				releaseModelEntry(entry);
				models.remove(key);
				NonRootModelElement[] rootElements = rootElementMap.get(key);
				if (rootElements != null) {
					for (NonRootModelElement rootElement : rootElements) {
						rootElement.batchUnrelate();
					}
					rootElementMap.remove(key);
				}
				if (entry != null) {
					if (entry.loadedModels != null) {
						for (Model_c model : entry.loadedModels) {
							model.batchUnrelate();
						}
					}
				}
				readonlyInputs.remove(key);
				deleteModelRoot(compareRoot);
			}
		}
	}

	private void deleteModelRoot(Ooaofooa root) {
		root.batchUnrelateAll();
		// must batchUnrelate the graphics elements
		// before calling clearDatabase as that method
		// clears the graphics root as well
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(root.getId());
		graphicsRoot.batchUnrelateAll();
		root.clearDatabase(new NullProgressMonitor());
		root.delete(true);
	}

	protected ModelMapEntry createModelMapEntry(Object input,
			Ooaofooa compareRoot, Object key) {
		ModelMapEntry modelMapEntry = new ModelMapEntry(input);
		if (!modelMapEntry.loadModel(compareRoot, key)) {
			return null;
		}
		return modelMapEntry;
	}

	public boolean isInputReadonly(Object key) {
		return readonlyInputs.contains(key);
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
		private Model_c[] loadedModels = null;

		ModelMapEntry(Object input) {
			this.input = input;
		}

		public Model_c[] getLoadedGraphicalModels() {
			return loadedModels;
		}

		public boolean loadModel(Ooaofooa compareRoot, Object key) {
			if (!(input instanceof IStreamContentAccessor)) {
				errorMessage = "Not a valid input";
			} else {
				InputStream sca = null;
				try {
					sca = ((IStreamContentAccessor) input).getContents();
					rootNode = ((IStreamContentAccessor) input);

					if (sca == null) {
						return false;
					}
					if(sca.available() == 0) {
						return false;
					}
					// configure IdAssigners to have upgrade elements
					// created with the same id
					IdAssigner.setSeedOfAllInstances(1, false);

					SystemModel_c sys = new SystemModel_c(compareRoot);
					sys.setParentModelRoot(Ooaofooa.getDefaultInstance());
					sys.setModelRoot(Ooaofooa.getDefaultInstance());
					ImportHelper helper = new ImportHelper(null);
					helper.setUpGlobals(sys);
					sys.setParentModelRoot(compareRoot);
					sys.setModelRoot(compareRoot);
					
					ModelStreamProcessor streamProcessor = null;
					PersistableModelComponent com = new PersistableModelComponent(
							sys);
					IModelImport modelInputLoader = new ImportModelComponent(
							sca, compareRoot, com, false, true, true, false);
					boolean isSingleFile = isSingleFile((CoreImport) modelInputLoader);
					boolean isSingleFileOld = isSingleFileOld((CoreImport) modelInputLoader);
					if (isSingleFile) {
						readonlyInputs.add(key);
						sca.close();
						sca = ((IStreamContentAccessor) input).getContents();
						Scanner scanner = new java.util.Scanner(sca)
								.useDelimiter("\\A");
						String contents = "";
						if (scanner.hasNext()) {
							contents = scanner.next();
						}
						sca.close();
						sca = ((IStreamContentAccessor) input).getContents();
						streamProcessor = new ModelStreamProcessor();
						modelInputLoader = CorePlugin.getStreamImportFactory()
								.create(sca, compareRoot, true, new Path(""));
						CoreImport.createUniqueIds = false;
						streamProcessor.setContents(contents);
						streamProcessor.setImporter(modelInputLoader);
					}
					if (isSingleFileOld) {
						readonlyInputs.add(key);
						sca.close();
						sca = ((IStreamContentAccessor) input).getContents();
						// dealing with old single file models
						modelInputLoader = CorePlugin.getModelImportFactory()
								.create(sca, compareRoot, sys, false, false,
										true, false);
					}
					int ret_val = 0;
					if (!isSingleFile) {
						ret_val = modelInputLoader
								.countAndValidateInsertStatements();
					}
					boolean successful = true;
					if (ret_val > 0 || isSingleFile) {
						modelInputLoader.run(new NullProgressMonitor());
					} else {
						successful = false;
					}
					if (isSingleFile || isSingleFileOld) {
						modelInputLoader.finishLoad(new NullProgressMonitor());
					} else {
						modelInputLoader.finishComponentLoad(
								new NullProgressMonitor(), false);
					}
					if ((successful && modelInputLoader.getSuccessful())
							|| (successful && isSingleFile)) {
						NonRootModelElement ele = modelInputLoader
								.getRootModelElement();
						NonRootModelElement[] rootElements = null;
						if (isSingleFile) {
							rootElements = streamProcessor
									.getExportedElements();
						} else {
							rootElements = new NonRootModelElement[] { ele };
						}
						rootElementMap.put(key, rootElements);
						// store loaded model before clearing the
						// database as they have no association
						// connection to the real elements
						loadedModels = Model_c.ModelInstances(Ooaofgraphics
								.getInstance(compareRoot.getId()));
						for (Model_c model : loadedModels) {
							model.setRepresents(Cl_c.Getinstancefromooa_id(
									compareRoot, model.getOoa_id(),
									model.getOoa_typeCachedValue()));
						}
						// we need to associate graphical elements and the model
						// with their specification classes otherwise
						// persistence will
						// not work properly as ooa_types will not be
						// determinable
						associateGraphicalSpecifications(loadedModels);
					} else {
						errorMessage = modelInputLoader.getErrorMessage();
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
					errorMessage = e.getMessage();
					ComparePlugin.writeToLog(errorMessage, e, this);
				} finally {
					CoreImport.createUniqueIds = true;
					IdAssigner.setSeedOfAllInstances(0, false);
					if (sca != null) {
						try {
							sca.close();
						} catch (IOException e1) {
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

	public boolean isSingleFile(CoreImport importer) {
		if (importer.getHeader().getModelComponentType().equals("StreamData")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public void associateGraphicalSpecifications(Model_c[] loadedModels) {
		for (final Model_c model : loadedModels) {
			ModelSpecification_c ms = ModelSpecification_c
					.ModelSpecificationInstance(
							Ooaofgraphics.getDefaultInstance(),
							new ClassQueryInterface_c() {

								@Override
								public boolean evaluate(Object candidate) {
									return ((ModelSpecification_c) candidate)
											.getModel_type() == model
											.getModel_typeCachedValue()
											&& ((ModelSpecification_c) candidate)
													.getOoa_type() == model
													.getOoa_typeCachedValue();
								}
							});
			if (ms != null) {
				ms.relateAcrossR9To(model);
			}
			GraphicalElement_c[] elements = GraphicalElement_c
					.getManyGD_GEsOnR1(model);
			for (final GraphicalElement_c element : elements) {
				ElementSpecification_c spec = ElementSpecification_c
						.ElementSpecificationInstance(
								Ooaofgraphics.getDefaultInstance(),
								new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((ElementSpecification_c) candidate)
												.getOoa_type() == element
												.getOoa_typeCachedValue();
									}
								});
				if (spec != null) {
					spec.relateAcrossR10To(element);
				}
				element.setRepresents(Cl_c.Getinstancefromooa_id(
						Ooaofooa.getInstance(element.getModelRoot().getId()),
						element.getOoa_id(), element.getOoa_type()));
			}
		}
	}

	public boolean isSingleFileOld(CoreImport importer) {
		if (importer.getHeader().getModelComponentType().equals("domain")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public static Object getAncestorKey(Object inputElement) {
		return ANCESTOR + inputElement.hashCode();
	}

	public static Object getLeftKey(Object inputElement) {
		return LEFT + inputElement.hashCode();
	}

	public static Object getRightKey(Object inputElement) {
		return RIGHT + inputElement.hashCode();
	}
}
