---

This work is licensed under the Creative Commons CC0 License

---

# Satisfactions created in incorrect places
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the changes put in place to fix an issue with the persistence
of satisfactions.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8795](https://support.onefact.net/issues/8795) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #8814](https://support.onefact.net/issues/8814) Move satisfactions on import.     
<a id="2.3"></a>2.3 [BridgePoint DEI #8815](https://support.onefact.net/issues/8815) Missing provisions/requirements     
<a id="2.4"></a>2.4 [BridgePoint DEI #8816](https://support.onefact.net/issues/8816) Satisfaction instance persistence location test        

3. Background
-------------
In diagrams where component references and component definitions are mixed, it 
was observed that if an imported provision/requirement on the component 
reference is dragged to the provision/requirement on a component definition, the
satisfaction is created in the parent package of the component definition to 
which the component reference is pointing. This is a problem because in the case
that the component reference is referring to a component in another project, it 
is possible that the satisfaction could be nonexistent in the project. This is a
big problem then if either of the projects is deleted from the workspace.   

4. Requirements
---------------
4.1 In all cases where satisfactions may be created, they shall be persisted
  inside the package or component where they are created.     

5. Work Required
----------------
5.1 Analysis   
5.1.1  Satisfactions are created in OAL inside Component in the 
  ```initializeProvision()``` and ```initializeRequirement()``` functions.   
5.1.2  Inside each of these functions, there is a single place where the 
  Satisfaction (C_SF) is created.  A corresponding PE_PE is also created.  Then
  there is logic to get the containing package or component for the provision
  or requirement that is being satisfied.   
  
5.2  Fix   
5.2.1  There is a logic error based on the variable being used.  Taking 
  ```initializeProvision()``` as an example... when the function is called it 
  creates transient variables ```importedRef``` and ```importedProvisionRef```.
  ```importedRef``` contains the ```CL_IIR``` for the Requirement on the other 
  side of the satisfaction-to-be.  ```importedProvisionRef``` contains the 
  ```CL_IIR``` of the Provision that is being dragged and dropped by the user to
  create the satisfaction.   
5.2.2  Previously, the code then looks at the ```importedRef``` to see if the 
  attached component is an imported component or not.   This is the root error,
  as it is looking at the "drop target" to see if it is imported or not.  If 
  this "drop target" component/interface is not imported, it gets the containing
  parent of the underlying component for the component reference that is
  the source of the drag for the satisfaction creation.  This is all rather
  convoluted because the logic is wrong.     
5.2.3  What should happen is, if the imported provision that is the source for
  the drag-n-drop satisfaction creation is attached to a component reference, 
  then get the parent (package or component) of this component reference and
  store the satisfaction in there.   
5.2.4  Here is the code change for the ```initializeProvision()``` case:   
```
      create object instance satisfaction of C_SF;
      create object instance pe of PE_PE;
      relate satisfaction to pe across R8001;
-     if(not_empty importedRef)
-       select one importedComp related by importedRef->CL_POR[R4708]->CL_IC[R4707];
+     if(not_empty importedProvisionRef)
+       select one importedComp related by importedProvisionRef->CL_POR[R4708]->CL_IC[R4707];
        select one epPkg related by importedComp->PE_PE[R8001]->EP_PKG[R8000];
        select one comp related by importedComp->PE_PE[R8001]->C_C[R8003];
        if not_empty epPkg
          relate pe to epPkg across R8000;
        elif not_empty comp
          relate pe to comp across R8003;
        end if;
      else
        select one epPkg related by self->PE_PE[R8001]->EP_PKG[R8000];
        select one comp related by self->PE_PE[R8001]->C_C[R8003];
        if not_empty epPkg
          relate pe to epPkg across R8000;
        elif not_empty comp
          relate pe to comp across R8003;
        end if;
      end if;
```
5.2.5  The explanation and code change are the same for 
  ```initializeRequirement()```   

  
6. Implementation Comments
--------------------------
None.   

7. Unit Test
------------
This test is captured as a manual test [[2.4]](#2.4).  It could be automated.

7.1 Test connections under package   
1. Clone http://github.com/xtuml/models.git  
2. Import the 8795_satisfaction model   
3. Open the xtUML Modeling perspective  
4. Open the system package  
5. Drag the interface from library::foo to test to create a satisfaction
6. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.  This tests the original error case.  
7. Reset the model back to undirtied state  
8. Drag the interface from test to library::foo to create a satisfaction
9. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
10. Reset the model back to undirtied state  
11. Drag the interface from library::foo to library::bar to create a satisfaction
12. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
13. Reset the model back to undirtied state  
14. Drag the interface from library::bar to library::foo to create a satisfaction
15. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
16. Reset the model back to undirtied state  
17. Drag the interface from test2 to test to create a satisfaction
18. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
19. Reset the model back to undirtied state  
20. Drag the interface from test to test2 to create a satisfaction
21. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
22. Reset the model back to undirtied state  
23. Drag the interface from library::bar to test2 to create a satisfaction
24. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
25. Reset the model back to undirtied state  
26. Drag the interface from test2 to library::bar to create a satisfaction
27. Verify the C_SF instance is created in system.xtuml and that the library
  package was not dirtied.    
28. Reset the model back to undirtied state  


7.2 Test connections under component   
__NOTE:__ Because of a bug [[2.3]](#2.3), the component test cannot be performed.  
When that issue is fixed, the component test should be set up and performed the
same as the package test.  

8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8795_satisfactions

<pre>

doc-bridgepoint/notes/8795_satisfactions.int.md
src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component/Component.xtuml

</pre>

Fork/Repository: xtuml/models   
Branch: 8795_satisfaction

<pre>

- Added test model 8795_satisfaction

</pre>

End
---

