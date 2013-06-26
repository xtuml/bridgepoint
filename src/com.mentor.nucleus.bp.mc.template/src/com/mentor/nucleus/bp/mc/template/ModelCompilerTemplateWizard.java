package com.mentor.nucleus.bp.mc.template;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;

public class ModelCompilerTemplateWizard extends NewPluginTemplateWizard {

	protected IFieldData fData;
	
    public void init(IFieldData data) {
        super.init(data);
        fData = data;
        setWindowTitle("BridgePoint Model Compiler Template Wizard");      
    }

    public ITemplateSection[] createTemplateSections() {
        return new ITemplateSection[] {new ModelCompilerSection()};
    }
    
    
    public boolean performFinish(final IProject project, IPluginModelBase model,
			IProgressMonitor monitor) {
// TODO FIXME: Before this plugin is made public this warning must be removed.
    	MessageDialog.openWarning(null,"Warning","This functionality is currently for internal use only.\n"
    			                                +"Note that docgen will not work on this new plugin until\n"
    											+"CQ issue dts0100845396 is addressed.  Until then, any new\n"
    											+"MC plugins must be manually added to\n" 
    											+"bp/docgen/generator/Generator.java::createDocumentation()");
    	return super.performFinish(project, model, monitor);
    }
}
