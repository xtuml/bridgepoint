//========================================================================
//
//File:      ElementOrderingTests.java
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.model.compare.test;

import java.io.InputStream;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Enumerator_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.StructureMember_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.Util_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.model.compare.ITreeDifferencerProvider;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareContentProvider;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CompareTestUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class ElementOrderingTests extends BaseTest {

	private static String projectName = "ElementOrderTesting";
	
	@Override
	public void initialSetup() throws Exception {
		loadProject(projectName);
		modelRoot = Ooaofooa.getInstance("/ElementOrderTesting/models/ElementOrderTesting/Package/Package.xtuml");
		StateMachine_c sm = StateMachine_c.StateMachineInstance(modelRoot);
		ModelClass_c clazz = ModelClass_c
				.getOneO_OBJOnR518(InstanceStateMachine_c
						.getOneSM_ISMOnR517(sm));
		// we need to update the in-memory SM ids which is what the compare
		// editor will do, otherwise some tests cannot find the elements they
		// need
		UUID smId = Util_c.Getuniquestatemachineid(clazz.getObj_id(), "i");
		ModelCacheManager.updateIdForStateMachine(smId, sm);
	}
	
	@Override
	public void tearDown() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
	}
	
	public void testFunctionParameterOrder() {
		Function_c function = Function_c.FunctionInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(function, FunctionParameter_c.class);
		FunctionParameter_c one = (FunctionParameter_c) children[0];
		FunctionParameter_c two = (FunctionParameter_c) children[1];
		FunctionParameter_c three = (FunctionParameter_c) children[2];
		assertTrue("Function Parameter was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Function Parameter was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Function Parameter was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testOperationOrder() {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(clazz, Operation_c.class);
		Operation_c one = (Operation_c) children[0];
		Operation_c two = (Operation_c) children[1];
		Operation_c three = (Operation_c) children[2];
		assertTrue("Operation was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Operation was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Operation was not in the correct location.", three
				.getName().contains("three"));
	}

	public void testOperationParameterParameterOrder() {
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(op, OperationParameter_c.class);
		OperationParameter_c one = (OperationParameter_c) children[0];
		OperationParameter_c two = (OperationParameter_c) children[1];
		OperationParameter_c three = (OperationParameter_c) children[2];
		assertTrue("Operation Parameter was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Operation Parameter was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Operation Parameter was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testEventParameterOrder() {
		StateMachineEvent_c evt = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(evt, StateMachineEventDataItem_c.class);
		StateMachineEventDataItem_c one = (StateMachineEventDataItem_c) children[0];
		StateMachineEventDataItem_c two = (StateMachineEventDataItem_c) children[1];
		StateMachineEventDataItem_c three = (StateMachineEventDataItem_c) children[2];
		assertTrue("Event Parameter was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Event Parameter was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Event Parameter was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testAttributeOrder() {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(clazz, Attribute_c.class);
		Attribute_c one = (Attribute_c) children[0];
		Attribute_c two = (Attribute_c) children[1];
		Attribute_c three = (Attribute_c) children[2];
		assertTrue("Attribute was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Attribute was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Attribute was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testEnumeratorOrder() {
		EnumerationDataType_c edt = EnumerationDataType_c.EnumerationDataTypeInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(edt, Enumerator_c.class);
		Enumerator_c one = (Enumerator_c) children[0];
		Enumerator_c two = (Enumerator_c) children[1];
		Enumerator_c three = (Enumerator_c) children[2];
		assertTrue("Enumerator was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Enumerator was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Enumerator was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testBridgeParameterOrder() {
		Bridge_c brg = Bridge_c.BridgeInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(brg, BridgeParameter_c.class);
		BridgeParameter_c one = (BridgeParameter_c) children[0];
		BridgeParameter_c two = (BridgeParameter_c) children[1];
		BridgeParameter_c three = (BridgeParameter_c) children[2];
		assertTrue("Bridge Parameter was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Bridge Parameter was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Bridge Parameter was not in the correct location.", three
				.getName().contains("three"));
	}

	public void testSignalOrder() {
		Interface_c iface = Interface_c.InterfaceInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(iface, InterfaceSignal_c.class);
		InterfaceSignal_c one = (InterfaceSignal_c) children[0];
		InterfaceSignal_c two = (InterfaceSignal_c) children[1];
		InterfaceSignal_c three = (InterfaceSignal_c) children[2];
		assertTrue("Interface Signal was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Interface Signal was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Interface Signal was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testInterfaceOperationOrder() {
		Interface_c iface = Interface_c.InterfaceInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(iface, InterfaceOperation_c.class);
		InterfaceOperation_c one = (InterfaceOperation_c) children[0];
		InterfaceOperation_c two = (InterfaceOperation_c) children[1];
		InterfaceOperation_c three = (InterfaceOperation_c) children[2];
		assertTrue("Interface Operation was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Interface Operation was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Interface Operation was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testPropertyParameterOrder() {
		InterfaceOperation_c op = InterfaceOperation_c.InterfaceOperationInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(op, PropertyParameter_c.class);
		PropertyParameter_c one = (PropertyParameter_c) children[0];
		PropertyParameter_c two = (PropertyParameter_c) children[1];
		PropertyParameter_c three = (PropertyParameter_c) children[2];
		assertTrue("Property Parameter was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Property Parameter was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Property Parameter was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testMemberOrder() {
		StructuredDataType_c sdt = StructuredDataType_c.StructuredDataTypeInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(sdt, StructureMember_c.class);
		StructureMember_c one = (StructureMember_c) children[0];
		StructureMember_c two = (StructureMember_c) children[1];
		StructureMember_c three = (StructureMember_c) children[2];
		assertTrue("Structure Member was not in the correct location.", one
				.getName().contains("one"));
		assertTrue("Structure Member was not in the correct location.", two
				.getName().contains("two"));
		assertTrue("Structure Member was not in the correct location.", three
				.getName().contains("three"));
	}
	
	public void testMergePositionalChangeTop() throws CoreException {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		// persist the class file to upgrade any new attributes added
		clazz.getPersistableComponent().persist();
		Operation_c[] ops = Operation_c.getManyO_TFRsOnR115(clazz);
		Operation_c op = ops[0];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			op.Moveup();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		performMergeTest(op.getFile());
	}
	
	public void testMergePositionalChangeBottom() throws CoreException {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		Operation_c[] ops = Operation_c.getManyO_TFRsOnR115(clazz);
		Operation_c op = ops[0];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			op.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		performMergeTest(op.getFile());		
	}
	
	public void testMoveUpNotAvailable() throws CoreException {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		Operation_c[] ops = Operation_c.getManyO_TFRsOnR115(clazz);
		Operation_c op = ops[0];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			op.Moveup();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(op.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			assertFalse(
					"Move Up action was available when it should not have been.",
					UITestingUtilities.checkItemStatusInContextMenu(
							leftViewer.getTree().getMenu(), "Move Up", "", false));
		} finally {
			if(copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}

	public void testMoveDownNotAvailable() throws CoreException {
		loadProject(projectName);
		modelRoot = Ooaofooa.getInstance("/ElementOrderTesting/models/ElementOrderTesting/Package/Package.xtuml");
		StateMachine_c sm = StateMachine_c.StateMachineInstance(modelRoot);
		ModelClass_c smClass = ModelClass_c
				.getOneO_OBJOnR518(InstanceStateMachine_c
						.getOneSM_ISMOnR517(sm));
		// we need to update the in-memory SM ids which is what the compare
		// editor will do, otherwise some tests cannot find the elements they
		// need
		UUID smId = Util_c.Getuniquestatemachineid(smClass.getObj_id(), "i");
		ModelCacheManager.updateIdForStateMachine(smId, sm);
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		Operation_c[] ops = Operation_c.getManyO_TFRsOnR115(clazz);
		Operation_c op = ops[0];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			op.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(op.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(op), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertFalse(
					"Move Down action was available when it should not have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}

	public void testNonWritableMoveUpAndDown() throws CoreException {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		Operation_c[] ops = Operation_c.getManyO_TFRsOnR115(clazz);
		Operation_c op = ops[0];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			op.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(op.getFile());
		ResourceAttributes attributes = copy.getResourceAttributes();
		attributes.setReadOnly(true);
		copy.setResourceAttributes(attributes);
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer rightViewer = viewer.getRightViewer();
			rightViewer.getTree().setFocus();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) rightViewer
							.getContentProvider())
							.getComparableTreeObject(op), rightViewer);
			rightViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertFalse(
					"Move Up action was available when it should not have been.",
					UITestingUtilities.checkItemStatusInContextMenu(rightViewer
							.getTree().getMenu(), "Move Up", "", false));
			assertFalse(
					"Move Up action was available when it should not have been.",
					UITestingUtilities.checkItemStatusInContextMenu(rightViewer
							.getTree().getMenu(), "Move Down", "", false));
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}

	public void testFunctionParameterMoveUpandDown() throws CoreException {
		Function_c function = Function_c.FunctionInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(function, FunctionParameter_c.class);
		FunctionParameter_c two = (FunctionParameter_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem funcItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(function), leftViewer);
			function = (Function_c) ((NonRootModelElementComparable) funcItem.getData()).getRealElement();
			children = provider.getChildrenOfType(function, FunctionParameter_c.class);
			FunctionParameter_c new_two = (FunctionParameter_c) children[1];
			assertTrue("Function Parameter did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(function, FunctionParameter_c.class);
			new_two = (FunctionParameter_c) children[2];
			assertTrue("Function Parameter did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testOperationMoveUpandDown() throws CoreException {
		loadProject(projectName);
		modelRoot = Ooaofooa.getInstance("/ElementOrderTesting/models/ElementOrderTesting/Package/Package.xtuml");
		StateMachine_c sm = StateMachine_c.StateMachineInstance(modelRoot);
		ModelClass_c smClass = ModelClass_c
				.getOneO_OBJOnR518(InstanceStateMachine_c
						.getOneSM_ISMOnR517(sm));
		// we need to update the in-memory SM ids which is what the compare
		// editor will do, otherwise some tests cannot find the elements they
		// need
		UUID smId = Util_c.Getuniquestatemachineid(smClass.getObj_id(), "i");
		ModelCacheManager.updateIdForStateMachine(smId, sm);
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(clazz, Operation_c.class);
		Operation_c two = (Operation_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(clazz), leftViewer);
			clazz = (ModelClass_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(clazz, Operation_c.class);
			Operation_c new_two = (Operation_c) children[01];
			assertTrue("Operation did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(clazz, Operation_c.class);
			assertTrue("Operation did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}

	public void testOperationParameterMoveUpAndDown() throws CoreException {
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(op, OperationParameter_c.class);
		OperationParameter_c two = (OperationParameter_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(op), leftViewer);
			op = (Operation_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(op, OperationParameter_c.class);
			OperationParameter_c new_two = (OperationParameter_c) children[1];
			assertTrue("Operation Parameter did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(op, OperationParameter_c.class);
			assertTrue("Operation did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testEventParameterMoveUpAndDown() throws CoreException {
		StateMachineEvent_c evt = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(evt, StateMachineEventDataItem_c.class);
		StateMachineEventDataItem_c two = (StateMachineEventDataItem_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(evt), leftViewer);
			evt = (StateMachineEvent_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(evt, StateMachineEventDataItem_c.class);
			StateMachineEventDataItem_c new_two = (StateMachineEventDataItem_c) children[1];
			assertTrue("Event parameter did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(evt, StateMachineEventDataItem_c.class);
			assertTrue("Event parameter did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testAttributeMoveUpAndDown() throws CoreException {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(clazz, Attribute_c.class);
		Attribute_c two = (Attribute_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(clazz), leftViewer);
			clazz = (ModelClass_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(clazz, Attribute_c.class);
			Attribute_c new_two = (Attribute_c) children[1];
			assertTrue("Attribute did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(clazz, Attribute_c.class);
			assertTrue("Attribute did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testEnumeratorMoveUpAndDown() throws CoreException {
		EnumerationDataType_c edt = EnumerationDataType_c.EnumerationDataTypeInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(edt, Enumerator_c.class);
		Enumerator_c two = (Enumerator_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(edt), leftViewer);
			edt = (EnumerationDataType_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(edt, Enumerator_c.class);
			Enumerator_c new_two = (Enumerator_c) children[1];
			assertTrue("Enumerator did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(edt, Enumerator_c.class);
			assertTrue("Enumerator did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testBridgeParameterMoveUpAndDown() throws CoreException {
		Bridge_c brg = Bridge_c.BridgeInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(brg, BridgeParameter_c.class);
		BridgeParameter_c two = (BridgeParameter_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(brg), leftViewer);
			brg = (Bridge_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(brg, BridgeParameter_c.class);
			BridgeParameter_c new_two = (BridgeParameter_c) children[1];
			assertTrue("Bridge Parameter did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(brg, BridgeParameter_c.class);
			assertTrue("Bridge Parameter did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}

	public void testSignalMoveUpAndDown() throws CoreException {
		Interface_c iface = Interface_c.InterfaceInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(iface, InterfaceSignal_c.class);
		InterfaceSignal_c two = (InterfaceSignal_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(iface), leftViewer);
			iface = (Interface_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(iface, InterfaceSignal_c.class);
			InterfaceSignal_c new_two = (InterfaceSignal_c) children[1];
			assertTrue("Interface Signal did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(iface, InterfaceSignal_c.class);
			assertTrue("Interface Signal did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testInterfaceOperationMoveUpAndDown() throws CoreException {
		Interface_c iface = Interface_c.InterfaceInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(iface, InterfaceOperation_c.class);
		InterfaceOperation_c two = (InterfaceOperation_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(iface), leftViewer);
			iface = (Interface_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(iface, InterfaceOperation_c.class);
			InterfaceOperation_c new_two = (InterfaceOperation_c) children[1];
			assertTrue("Interface Operation did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(iface, InterfaceOperation_c.class);
			assertTrue("Interface Operation did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testPropertyParameterMoveUpAndDown() throws CoreException {
		InterfaceOperation_c iface = InterfaceOperation_c.InterfaceOperationInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(iface, PropertyParameter_c.class);
		PropertyParameter_c two = (PropertyParameter_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(iface), leftViewer);
			iface = (InterfaceOperation_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(iface, PropertyParameter_c.class);
			PropertyParameter_c new_two = (PropertyParameter_c) children[1];
			assertTrue("Property Parameter did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(iface, PropertyParameter_c.class);
			assertTrue("Property Parameter did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	public void testMemberMoveUpAndDown() throws CoreException {
		StructuredDataType_c iface = StructuredDataType_c.StructuredDataTypeInstance(modelRoot);
		ITreeDifferencerProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildrenOfType(iface, StructureMember_c.class);
		StructureMember_c two = (StructureMember_c) children[1];
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			two.Movedown();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
		}
		IFile copy = CompareTestUtilities.openCompareEditor(two.getFile());
		try {
			ModelContentMergeViewer viewer = ModelContentMergeViewer
					.getInstance(null);
			SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
			TreeItem item = SynchronizedTreeViewer.getMatchingItem(
					((ITreeDifferencerProvider) leftViewer
							.getContentProvider())
							.getComparableTreeObject(two), leftViewer);
			leftViewer.setSelection(new StructuredSelection(item.getData()), false);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			assertTrue(
					"Move Up action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Up", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Up");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			TreeItem classItem = SynchronizedTreeViewer.getMatchingItem(provider.getComparableTreeObject(iface), leftViewer);
			iface = (StructuredDataType_c) ((NonRootModelElementComparable) classItem.getData()).getRealElement();
			children = provider.getChildrenOfType(iface, StructureMember_c.class);
			StructureMember_c new_two = (StructureMember_c) children[1];
			assertTrue("Member did not move up.", new_two
					.getName().contains("two"));
			assertTrue(
					"Move Down action was not available when it should have been.",
					UITestingUtilities.checkItemStatusInContextMenu(leftViewer
							.getTree().getMenu(), "Move Down", "", false));
			UITestingUtilities.activateMenuItem(leftViewer.getTree().getMenu(), "Move Down");
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			children = provider.getChildrenOfType(iface, StructureMember_c.class);
			assertTrue("Member did not move up.", new_two
					.getName().contains("two"));			
		} finally {
			if (copy != null) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
	
	private void performMergeTest(IFile file) throws CoreException {
		IFile copy = null;
		try {
			// grab the copy from local history
			IFileState[] history = file.getHistory(new NullProgressMonitor());
			IFileState state = history[0];
			InputStream contents = state.getContents();
			copy = file.getProject().getFile(file.getName());
			copy.create(contents, true, new NullProgressMonitor());
			TreeDifferencer differencer = CompareTestUtilities
					.compareElementWithLocalHistory(file, copy);
			assertTrue("A difference was not created to allow testing of position change",
					!differencer.getLeftDifferences().isEmpty());
			ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
			viewer.setCopySelection(false);
			viewer.copy(false);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor().doSave(new NullProgressMonitor());
			BaseTest.dispatchEvents(0);
			differencer.refresh();
			assertTrue("Differences remained after copying positional change",
					differencer.getLeftDifferences().isEmpty());		
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.closeAllEditors(true);
			// compare the files, they should be identical
			String fileContents = TestUtil.getTextFileContents(file.getLocation().toFile());
			String copyContent = TestUtil.getTextFileContents(copy.getLocation().toFile());
			assertEquals("Model file contents were different after a merge.", fileContents, copyContent);
		} finally {
			if(copy != null && copy.exists()) {
				copy.delete(true, new NullProgressMonitor());
			}
		}
	}
}
