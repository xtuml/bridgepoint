package com.mentor.nucleus.bp.mc.template;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.core.plugin.IPluginBase;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelFactory;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.internal.ui.IHelpContextIds;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.AbstractNewPluginTemplateWizard;
import org.eclipse.pde.ui.templates.OptionTemplateSection;
import org.eclipse.pde.ui.templates.PluginReference;
import org.eclipse.pde.ui.templates.TemplateOption;

@SuppressWarnings("restriction")
public class ModelCompilerSection extends OptionTemplateSection {

	private static final String KEY_COMPILER_NAME = "_COMPILER_NAME"; //$NON-NLS-1$
	private String packageName = "";
	
	public ModelCompilerSection() {
		super();
		setPageCount(1);
		createOptions();
	}
	
	private void createOptions() {
		addOption(KEY_COMPILER_NAME, "Model Compiler Name ", "Model Compiler", 0);
	}
	
	
	public void addPages(Wizard wizard) {
		WizardPage page = createPage(0, IHelpContextIds.TEMPLATE_INTRO);
		page.setTitle("BridgePoint Model Compiler Template");
		page.setDescription("Creates a Model Compiler project for use in BridgePoint.");
		wizard.addPage(page);
		markPagesAdded();
	}

	/**
	 * This is the folder relative to your install url and template directory
	 * where the templates are stored.
	 */
	public String getSectionId() {
		return "model_compiler"; //$NON-NLS-1$
	}
	
	
	
	@Override
	protected String getTemplateDirectory() {
		return "templates"; //$NON-NLS-1$
	}

	protected void initializeFields(IFieldData data) {
		String id = data.getId();
		initializeOption(KEY_PACKAGE_NAME, getFormattedPackageName(id));
		packageName = getFormattedPackageName(id);
	}

	public void validateOptions(TemplateOption changed) {
		// no validation required at this time
	}


	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		if(packageName.equals("")) {
			initializeFields(((AbstractNewPluginTemplateWizard) getPage(0)
					.getWizard()).getData());
		}
		IPluginBase plugin = model.getPluginBase();
		IPluginModelFactory factory = model.getPluginFactory();
        		
		IPluginExtension modelCompilerExt = createExtension(getUsedExtensionPoint(), true);
		
		IPluginElement element = factory.createElement(modelCompilerExt);
		element.setName("model-compiler"); //$NON-NLS-1$
		element.setAttribute("class", packageName
				+ ".MCNewProjectWizard"); //$NON-NLS-1$
		element.setAttribute("name", getStringOption(KEY_COMPILER_NAME));
		
		modelCompilerExt.add(element);
		
		IPluginExtension mcNatureExt = createExtension("org.eclipse.core.resources.natures", true);//$NON-NLS-1$
		mcNatureExt.setId("MCNature"); //$NON-NLS-1$
		mcNatureExt.setName("model compiler nature"); //$NON-NLS-1$
		element = factory.createElement(mcNatureExt);
		element.setName("runtime"); //$NON-NLS-1$
		IPluginElement runElement = factory.createElement(element);
		runElement.setName("run"); //$NON-NLS-1$
		runElement.setAttribute("class", packageName + ".MCNature"); //$NON-NLS-1$
		element.add(runElement);
		IPluginElement builderElement = factory.createElement(mcNatureExt);
		builderElement.setName("builder"); //$NON-NLS-1$
		builderElement.setAttribute("id", packageName + ".export_builder"); //$NON-NLS-1$		
		
		mcNatureExt.add(element);
		mcNatureExt.add(builderElement);

		IPluginExtension natureImageExt = createExtension("org.eclipse.ui.ide.projectNatureImages", true);//$NON-NLS-1$
		IPluginElement imageElement = factory.createElement(natureImageExt);
		imageElement.setName("image"); //$NON-NLS-1$
		imageElement.setAttribute("icon", "../com.mentor.nucleus.bp.core/icons/xtuml-nature.gif"); //$NON-NLS-1$//$NON-NLS-2$
		imageElement.setAttribute("natureId", packageName + ".MCNature"); //$NON-NLS-1$ //$NON-NLS-2$
		imageElement.setAttribute("id", "com.mentor.nucleus.resoures.natureimage"); //$NON-NLS-1$ //$NON-NLS-2$
		natureImageExt.add(imageElement);   

		IPluginExtension buildersExt = createExtension("org.eclipse.core.resources.builders", true);//$NON-NLS-1$
		buildersExt.setId("export_builder"); //$NON-NLS-1$
		buildersExt.setName("Model Compiler Pre-Build"); //$NON-NLS-1$
		builderElement = factory.createElement(buildersExt);
		builderElement.setName("builder");
		runElement = factory.createElement(builderElement);
		runElement.setName("run");//$NON-NLS-1$
		runElement.setAttribute("class", packageName + ".ExportBuilder"); //$NON-NLS-1$//$NON-NLS-2$
		builderElement.add(runElement);
		buildersExt.add(builderElement);
		
		plugin.add(mcNatureExt);
		plugin.add(natureImageExt);
		plugin.add(buildersExt);
		plugin.add(modelCompilerExt);
	}

	public String getUsedExtensionPoint() {
		return "com.mentor.nucleus.bp.core.model-compilers";
	}

	/**
	 * The location of your plugin supplying the template content
	 */
	protected URL getInstallURL() {
		return Activator.getDefault().getBundle().getEntry("/");
	}

	protected ResourceBundle getPluginResourceBundle() {
		return Platform.getResourceBundle(Activator.getDefault().getBundle());
	}

	/**
	 * You can use this method to add files relative to your section id
	 */
	public String[] getNewFiles() {
		return new String[0];
	}
	
	public IPluginReference[] getDependencies(String schemaVersion) {
		ArrayList<PluginReference> result = new ArrayList<PluginReference>();

		result.add(new PluginReference("org.eclipse.core.runtime", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.core.resources", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.core.variables", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("com.mentor.nucleus.bp.core", null, 0)); //$NON-NLS-1$
		result
				.add(new PluginReference(
						"com.mentor.nucleus.bp.io.core", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("com.mentor.nucleus.bp.mc", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.debug.ui", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.ui", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.ui.ide", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.cdt.core", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.cdt.ui", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference(
				"org.eclipse.cdt.managedbuilder.ui", null, 0)); //$NON-NLS-1$

		return (IPluginReference[]) result.toArray(new IPluginReference[result
				.size()]);
	}
	
	public boolean isDependentOnParentWizard() {
		return true;
	}
	
	public int getNumberOfWorkUnits() {
		return super.getNumberOfWorkUnits() + 1;
	}
	
	public String getStringOption(String name) {
		return super.getStringOption(name);
	}
	
	protected String getFormattedPackageName(String id){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < id.length(); i++) {
			char ch = id.charAt(i);
			if (buffer.length() == 0) {
				if (Character.isJavaIdentifierStart(ch))
					buffer.append(Character.toLowerCase(ch));
			} else {
				if (Character.isJavaIdentifierPart(ch) || ch == '.')
					buffer.append(ch);
			}
		}
		return buffer.toString().toLowerCase(Locale.ENGLISH);
	}
	

	public String getDescription() {
		return "Creates a simple view";
	}

	public String getLabel() {
		return "Simple View";
	}

}
