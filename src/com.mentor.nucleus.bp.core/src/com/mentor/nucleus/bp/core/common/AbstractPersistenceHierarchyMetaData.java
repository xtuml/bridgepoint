package com.mentor.nucleus.bp.core.common;

public abstract class AbstractPersistenceHierarchyMetaData implements IPersistenceHierarchyMetaData{
    // just to expose me.setComponent to io.mdl project
	protected void setComponentOfME(NonRootModelElement me, PersistableModelComponent component){
        me.setComponent(component);
    }
}
