package com.mentor.nucleus.bp.io.mdl.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class MultilevelIntermTest extends BaseTest {
	boolean setupDone = false;

	Ooaofooa modelRoot = null;
	IFile file = null;

	public MultilevelIntermTest(String name) throws CoreException {
		super("multilevel", name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!setupDone) {
			file = importFile(Ooaofooa.MODELS_DIRNAME
				+ "/graphics4MLPersistence." + Ooaofooa.MODELS_EXT);//$NON-NLS-1$
			setupDone = true;
		}
	}

	public void testComponentCreation() throws Exception {
		
		modelRoot = Ooaofooa.getInstance("graphics4MLPersistence-forexport");
		SystemModel_c sysModel = getSystemModel(file.getProject());
		if(sysModel == null){
			sysModel = new SystemModel_c(Ooaofooa.getDefaultInstance());
			sysModel.setName(file.getProject().getName());
		}
		
		importModel(new NullProgressMonitor(), sysModel, file, modelRoot, false, false, true);

		
		
		PersistenceManager manager = PersistenceManager.getDefaultInstance();
		PersistableModelComponent rootComponent = manager.registerModel(
			getDomain(modelRoot),
			file.getProject());
		
		PersistableModelComponent parent = rootComponent.getParent();
		if(!parent.isPersisted()){
			rootComponent = parent;
		}
		
		writeComponentAndChildren(rootComponent);
		putSharedResult("loaded-domain", getDomain(modelRoot));
	}

	private void writeComponentAndChildren(PersistableModelComponent component)
		throws CoreException {
		component.persist();
		for (Iterator iterator = component.getChildren().iterator(); iterator
			.hasNext();) {
			PersistableModelComponent child = (PersistableModelComponent) iterator
				.next();
			writeComponentAndChildren(child);
		}
	}
	
	protected void tearDown() throws Exception {
	}

	
	public void xtestHeirarchyMetaData() throws Exception{
		SystemModel_c system = getSystemModel(file.getProject());
		printME("", system);
	}
	
	private void printME(String tab, NonRootModelElement me) {
		IPersistenceHierarchyMetaData persistenceMetadata = PersistenceManager.getHierarchyMetaData();
		
		String name = "Instance of " + me.getClass().getName();
		if(persistenceMetadata.isComponentRoot(me)){
			name = persistenceMetadata.getRootElementName(me);
		}
		
		System.out.println(tab + name);
		
		List children = persistenceMetadata.getChildren(me, true);
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			NonRootModelElement element = (NonRootModelElement) iterator.next();
			printME(tab + "  ", element);
		}
	}

	private SystemModel_c getSystemModel(final IProject project){
		return SystemModel_c.SystemModelInstance(
				Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals(
							project.getName());
					}
				});
	}
	
	private Domain_c getDomain(Ooaofooa modelRoot){
		return Domain_c.DomainInstance(modelRoot);
	}
	
	private boolean compareModels(Domain_c domain1, Domain_c domain2){
		return compareModelElement(domain1, domain2);
	}
	
	private boolean compareModelElement(NonRootModelElement me1, NonRootModelElement me2){
        if(me1.identityEquals(me2)){
    		IPersistenceHierarchyMetaData metaData = PersistenceManager.getHierarchyMetaData();
            List children1 = metaData.getChildren(me1, false);
            List children2 = metaData.getChildren(me2, false);
            
            
            
            if(children1.size() != children2.size()){
            	System.err.println("children of " + getName(me1) + " does not matches with children count of " + getName(me2) + " (" + children1.size() + ":" + children2.size() + ")");
            	return false;
            }

            for (int i = 0; i<children1.size(); i++) {
            	NonRootModelElement childFrom1 = (NonRootModelElement)children1.get(i);
            	NonRootModelElement childFrom2 = find(childFrom1, children2);
            	
            	boolean equal = false;
            	if(childFrom2 != null){
            		children2.remove(childFrom2);

            		equal = compareModelElement(childFrom1, childFrom2);
                	if(!equal){
                		System.err.println("      parents:" + getName(me1) + "<->" + getName(me2));
                		return false;
                	}
            	}else{
            		System.err.println("could not find sibling of " + getName(childFrom1));
            		return false;
            	}

			}
            return true;
        }
        System.err.println(">>>>>>>>>>>>>" + getName(me1) + " does not matches with " + getName(me2));
        return false;
	}
	
	private NonRootModelElement find(NonRootModelElement me, List list){
		for (int i = 0; i<list.size(); i++) {
			if(me.identityEquals(list.get(i))){
				return (NonRootModelElement)list.get(i);
			}
		}
		return null;
	}
	
	
	private String getName(NonRootModelElement me){
		Class clazz = me.getClass();
		Method method = null;
		try {
			method = clazz.getMethod("getName", null);
		} catch (Exception e) {
		}
		
		if(method == null){
			try {
				method = clazz.getMethod("get_Name", null);
			} catch (Exception e) {
			}
		}
		
		String name = null;
		
		if(method != null){
			try {
				name = clazz.getName() + "(" + (String)method.invoke(me, null) + ")";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(name == null){
			name = "Instance of " + clazz.getName();
		}
		return name;
	}
}
