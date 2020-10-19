package org.xtuml.bp.ui.explorer.dnd;
//========================================================================
//
//File: OrderedElementDragSourceListener.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.StringJoiner;
import java.util.stream.Stream;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.explorer.ModelContentProvider;

public class OrderedElementDragSourceListener implements ElementDragSourceListener {

	ITreeContentProvider provider = new ModelContentProvider();

	@Override
	public ITreeContentProvider getProvider() {
		return provider;
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IRunnableWithProgress exporter = CorePlugin.getStreamExportFactory().create(out,
				Selection.getInstance().getSelectedNonRootModelElements(), true, true);
		try {
			exporter.run(new NullProgressMonitor());
		} catch (InvocationTargetException | InterruptedException e) {
			CorePlugin.logError("Unable to copy data.", e);
		}
		StringJoiner data = new StringJoiner(ElementDropTargetListener.transferSeparator);
		Stream.of(Selection.getInstance().getSelectedNonRootModelElements()).map(e -> getDataSetForElement(e))
				.forEach(ds -> data.add(ds[0]).add(ds[1]).add(ds[2]));
		data.add(new String(out.toByteArray()));
		event.data = data.toString();
	}

	private String[] getDataSetForElement(NonRootModelElement e) {
		return new String[] { serializeKey(e), e.getClass().getName(),
				getProvider().getParent(e).getClass().getName().toString() };
	}

	private String serializeKey(NonRootModelElement e) {
		Object[] keys = (Object[]) e.getInstanceKey();
		StringJoiner joiner = new StringJoiner(",");
		Stream.of(keys).forEach(k -> joiner.add(k.toString()));
		return joiner.toString();
	}

}
