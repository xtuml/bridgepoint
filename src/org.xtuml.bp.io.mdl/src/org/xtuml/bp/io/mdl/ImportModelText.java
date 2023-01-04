package org.xtuml.bp.io.mdl;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.PersistableModelComponent;

public class ImportModelText extends ImportModelComponent {

	public ImportModelText(IFile file, Ooaofooa aModelRoot, PersistableModelComponent component, boolean parseAll,
			boolean clearDatabase, boolean parseGraphics, boolean isTemplate) throws IOException {
		super(file, aModelRoot, component, parseAll, clearDatabase, parseGraphics, isTemplate);
	}

	public ImportModelText(InputStream inStream, Ooaofooa aModelRoot, PersistableModelComponent component,
			boolean parseAll, boolean clearDatabase, boolean parseGraphics, boolean isTemplate) throws IOException {
		super(inStream, aModelRoot, component, parseAll, clearDatabase, parseGraphics, isTemplate);
	}

	@Override
	public void run(IProgressMonitor pm) {
		helper.eesToUpgradeForIsRealized.clear();
		m_success = doLoadSql(pm);
	}

}
