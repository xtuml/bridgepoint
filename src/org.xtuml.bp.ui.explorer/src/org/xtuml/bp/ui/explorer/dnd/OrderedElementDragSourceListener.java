package org.xtuml.bp.ui.explorer.dnd;

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
