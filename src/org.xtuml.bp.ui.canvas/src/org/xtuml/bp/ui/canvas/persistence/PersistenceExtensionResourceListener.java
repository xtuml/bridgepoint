package org.xtuml.bp.ui.canvas.persistence;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;

/**
 * We listen for resource changes to any registered persistence, by set file
 * extension
 *
 */
public class PersistenceExtensionResourceListener implements IResourceChangeListener {

	private static Map<String, IGraphicalLoader> extensions = new HashMap<>();

	public void addExtension(IGraphicalLoader loader, String extension) {
		extensions.put(extension, loader);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// do nothing if textual persistence is not enabled
		String textualSerialization = CorePlugin.getDefault().getPreferenceStore()
				.getString(BridgePointPreferencesStore.GRAPHICS_TEXTUAL_SERIALIZATION);
		if(MessageDialogWithToggle.NEVER.equals(textualSerialization)) {
			return;
		}
		// do not want to do anything in response to normal brigepoint ops
		if ( !ComponentResourceListener.ignoreResourceChanges() && !ComponentResourceListener.isIgnoreResourceChangesMarkerSet()) {
			if (event.getType() != IResourceChangeEvent.POST_CHANGE)
				return;
			IResourceDelta rootDelta = event.getDelta();
			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) {			        
					// only interested in changed resources (not added or removed)
					if (delta.getKind() != IResourceDelta.CHANGED)
						return true;
					// only interested in content changes
					if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
						return true;
					IResource resource = delta.getResource();
					if (resource.getType() == IResource.FILE && extensions.containsKey(resource.getFileExtension())) {
						IGraphicalLoader loader = extensions.get(resource.getFileExtension());
						PersistableModelComponent pmc = PersistenceManager.findOrCreateComponent(new Path(resource
								.getFullPath().toString().replaceAll(resource.getFileExtension(), Ooaofooa.MODELS_EXT)));
						String textualSerialization = CorePlugin.getDefault().getPreferenceStore()
								.getString(BridgePointPreferencesStore.GRAPHICS_TEXTUAL_SERIALIZATION);
						if(MessageDialogWithToggle.ALWAYS.equals(textualSerialization)) {
							Model_c reloaded = loader.reload(pmc.getRootModelElement());
							// update any graphical editor inputs that match
							Ooaofgraphics.getDefaultInstance().fireModelElementReloaded(reloaded, reloaded);
						}
						return true;
					}
					return false;
				}
			};
			try {
				rootDelta.accept(visitor);
			} catch (CoreException e) {
				CanvasPlugin.logError("Unable to listen for persistence extension resource changes", e);
			}
		}
	}

}
