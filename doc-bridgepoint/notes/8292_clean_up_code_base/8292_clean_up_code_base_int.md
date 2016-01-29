---

This work is licensed under the Creative Commons CC0 License

---

# Clean up the code base following the work done to remove specialized packages from BridgePoint
### xtUML Project Implementation Note


1. Abstract
-----------
As described in the design note for the issue where specialized packages were removed, refrenced at [[2.2]](#2.2).  
There were some "breadcrumbs" left behind where it was observed that further work could be done to clean up after removal of specialized packages.  
The "breadcrumb" left behind was a comment: TODO: BOB
This issue is raised to visit those breadcrumbs and take the action needed to remove them.

2. Document References
---------------------- 
<a id="2.1"></a>2.1 [BridgePoint DEI #8292](https://support.onefact.net/issues/8292) Clean up the code base following the work done to remove specialized packages from BridgePoint  
<a id="2.2"></a>2.2 [BridgePoint DEI #7769](https://support.onefact.net/issues/7769) Meta-model cleanup from generic package migration (remove specialized package support)
<a id="2.3"></a>2.3 [BridgePoint DEI #7769](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/7769_remove_sp/7769_remove_sp.dnt.md) Design note.  

3. Background
-------------

4. Requirements
---------------
 4.1 Remove places where they contain the comment TODO: BOB
 
5. Work Required
----------------
5.1 At Data Type.xtuml, remove operation "isAllowedReturnType".

5.2 At PersistableModelComponent.java remove the following java code:

		public static void ensureCoreDataTypesAvailable(ModelRoot modelRoot) {
			//TODO: BOB REmove this obsolete function
		}    

		public static void ensureSystemCoreDataTypesAvailable(final SystemModel_c system) {
			//TODO: BOB REmove this obsolete function
		}
		
		public static void ensureComponentCoreDataTypesAvailable(Component_c component) {
			//TODO: BOB REmove this obsolete function
		}
	
5.3 At function_body.inc remove the following code:

    PersistableModelComponent.ensureCoreDataTypesAvailable(v_${context_item.Name}.getModelRoot());	
    PersistableModelComponent.ensureSystemCoreDataTypesAvailable(v_${context_item.Name});

	   .if(class.Key_Lett == "S_SYS")
	PersistableModelComponent.ensureSystemCoreDataTypesAvailable(v_${context_item.Name});
	  .else
		.invoke loadCode = gen_code_for_loading_core_types(class.Key_Lett, "v_${context_item.Name}");
	${loadCode.result}
	  .end if
	
	 .function gen_code_for_loading_core_types
	.param String kl
	.param String var
	.assign attr_result = ""
	.if(kl == "C_C")
	.assign attr_result = "		PersistableModelComponent.ensureComponentCoreDataTypesAvailable(${var});\n"
	.elif(kl == "CP_CP")
	.assign attr_result = "		PersistableModelComponent.ensureComponentPackageCoreDataTypesAvailable(${var});\n"
	.end if
	.end function

   
5.4 At setValueOnConstantAction.java remove the following code:

    PersistableModelComponent.ensureCoreDataTypesAvailable(v_lsc.getModelRoot());

5.5 At ooaofooaBase.java remove the following code:

    public boolean isLoaded()
	{
		// TODO: BOB remove this obsolete function
		return false;
	}   
	public class SystemModelChangeListener extends ModelChangeAdapter 
	{
		//TODO: BOB This listener is doing nothing, remove it
		public void modelElementUnloaded(ModelChangedEvent event) {
		}

		public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
		}
		
	}

5.6  At java.arc remove the following code:
 
	public Object[] getChildren(boolean load) {
		// TODO: BOB remove this obsolete function
        List dom_set = new ArrayList();
        Object[] obj_set = dom_set.toArray();
        return obj_set;
	}
	public boolean hasChildren() {
		return 	hasChildren(true);
	}
	public boolean hasChildren(boolean load) {
		// TODO: BOB remove this obsolete function
		return false;
	}
5.7 At function.body.inc remove the following code:

	.if ((cme.Key_Lett =="C_C")and ((cme.Specialism == "New") or(cme.Specialism == "Components")))
	 // TODO: BOB FIXME.  This is a place where I want to see the generated
	 // code before updating the function_body.inc archetype.  This condition was:
	 // if (v_${context_item.Name}.isInGenericPackage())
	 if (true) 
	     {
	.end if 
	
	
		.if ((cme.Key_Lett =="C_C")and ((cme.Specialism == "New") or(cme.Specialism == "Components")))
	  
	 }
	 .end if 		
	 
	 
	 
5.8 At ImportHelper.java remove the following code:

    /**
     * Relates all external entity packages across the
     * associations found in the package linking subsystem
     */
    public void formalizeExternalEntityPackageLinkingAssociations(Ooaofooa modelRoot)
    {
    	//TODO: BOB remove this
    }
    /**
     * Relates all function packages across the
     * associations found in the package linking subsystem
     */
    public void formalizeFunctionPackageLinkingAssociations(Ooaofooa modelRoot)
    {
    	//TODO: BOB remove this
    }
	
	    /**
     * Migrate the date (genType 1), timestamp (genType 2), and inst_ref<Timer> (genType 3)
     * system level datatypes if their core types are not correct.
     * 
     * @parameter system An instance of the system model class
     */
    public void migrateSLDTs(SystemModel_c system)
    {
    	// TODO: BOB remove this
    }    
     
	 private void setupSatisfactionInComponentPackage(ModelRoot modelRoot, SICP sicp) {
    	// TODO: Bob Remove this
    }
	
	    /**
     * This method will find data types and associate them
     * with the correct domain, if at the domain level
     */
    public void associateDTsWithDomain(Ooaofooa modelRoot) {
    	// TODO: BOB REmove this
    }


5.9 At ModelImportWizard.java remove the following code:

	public void convertCoreTypesToProxies() {
		// TODO: Bob Fixme
	}


5.10 Remove the following files as importing and exporting files with the extension "*.sql" are not supported anymore:
	- CoreExportPage.java
	- CoreExportWizard.java
	- CoreImportPage.java
	- CoreImportWizard.java
	
 

6. Implementation Comments
--------------------------


7. Unit Test
------------
7.1 Run Junit tests   



8. Code Changes
---------------
Branch name: < 8292_clean_up_code_base >

<pre>

doc-bridgepoint/notes/8292_clean_up_code_base/8292_clean_up_code_base_int.md

org.xtuml.bp.core/arc/function_body.inc
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Data Type/
    Data Type.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/EclipseOoaofooa.java
org.xtuml.bp.core/src/org/xtuml/bp/core/OoaofooaBase.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/SetValueOnConstantAction.java

org.xtuml.bp.io.core/arc/gen_import_java.inc
org.xtuml.bp.io.core/arc/import_functions.inc
org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreExportPage.java
org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreExportWizard.java
org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImportPage.java
org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImportWizard.java
org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/ImportHelper.java

org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/wizards/ModelImportWizard.java


</pre>

End
---
