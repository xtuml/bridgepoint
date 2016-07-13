package org.xtuml.bp.io.mdl.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IPersistenceHierarchyMetaData;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;

@RunWith(OrderedRunner.class)
public class MultilevelIntermTest extends BaseTest {
	boolean setupDone = false;

	Ooaofooa modelRoot = null;
	IFile file = null;

	public MultilevelIntermTest() throws CoreException {
		super("multilevel", null);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!setupDone) {
			file = importFile(Ooaofooa.MODELS_DIRNAME
				+ "/graphics4MLPersistence." + Ooaofooa.MODELS_EXT);//$NON-NLS-1$
			setupDone = true;
		}
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
	
	@After
	public void tearDown() throws Exception {
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
