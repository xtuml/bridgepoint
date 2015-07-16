---

This work is licensed under the Creative Commons CC0 License

---

# Parse all activities in a project easily
### xtUML Project Design Note
 

1. Abstract
-----------
Frequently there is a need to parse all action language in the entire system 
(several projects). This is a very tedious operation currently. 
The "Parse all activities" operation is not available when selecting a project.
The user needs to go through all projects and expand them and then
multi-select each top-level package for each project each time the user wants to 
parse the entire system.

To solve this it should be possible to select one (or many) projects and the 
choose "Parse all activities".
 

2. Document References
----------------------
[1] [BridgePoint DEI #7673](https://support.onefact.net/redmine/issues/7673)  
[2] [BridgePoint DEI #7782] (https://support.onefact.net/redmine/issues/7782)  
    "Disable "Parse all activities" menu entry in case of having a nested package selected along with its parent."
    


3. Background
-------------
 N/A

4. Requirements
---------------
4.1  Add a new Menu Entry to the context menu of the project in the Model Explorer.  
     This Entry is named "Parse all activities".  
     
4.2  Make the "Parse all activities" menu entry available in case of
     multi-project selection.
 

5. Analysis
-----------
5.1 Add a "Parse all activities" menu entry to all System Models in the Model Explorer view
    
5.2 There is an already existing parse all functionality available at the 
    packages and component levels that just needs to be extended to include
    System Model instances 
    
5.3 The support for System Model instances needs to be added to the 
    ParseALLActivities action 
    
5.4 All methods collecting action language home instances need to support
    System Model navigation.
    

6. Design
---------
6.1 Modify the file create_plugin_xml.arc to add an objectContribution for 
    System Model instances to show the "parse all activities" menu entry. 
    
6.2 Add New Constructor for AllActivityModifier class that takes as the first 
    parameter an instance of SystemModel_c.
        
6.3 Add new Constructor for ParserAllActivityModifier that takes as the first 
    parameter an instance of SystemModel_c that sets the parseAll attribute 
    to true.
    
6.4 Add new attribute m_sysMod of type SystemModel_c to ParserAllActivityModifier
    class  that gets populated with the NonRootModelElement instance.
    
6.5 Modify resetAllPackages() method to use the m_sysMod and retrieve all 
    packages related to it across the relation R1401 and reset each package.
    
6.6 Modify all the following functions to use the packages related to system model
    instance across R1401 to collect activities below these packages, 
   - getAllFunctions() 
   - getAllBridges()
   - getAllOperations() 
   - getAllMDAttrs() 
   - getAllStateActivities() 
   - getAllTransitionActivities() 
   - getAllInterfaceOperations()  
   - getAllInterfaceSignals()    
    

7. Design Comments
------------------
7.1 Single project selection is allowed.  
    When a single project is selected the parse all menu entry shows,  
    it causes all the packages under that project to be parsed.  
    When a project is selected the other elements under it do Not get selected automatically.     
    When a project is selected and another element either a package or a component   
    under it gets selected the parse all menu entry does Not show.  
    
    
7.2 Multi-project selection is allowed.   
    When multi-projects are selected the parse all menu entry shows,
    it causes all the packages under the selected projects to be parsed.
    When multi-projects are selected the other elements under these projects do Not get selected automatically.    
    When multi-projects are selected and another element under either of them gets selected the parse all menu  
    item does not show.
    

7.3 In case of nested packages selected oal action homes get parsed twice  
    a separate issue to handle this case is raised [2]


8. Unit Test
------------
8.1 The existing parse all tests should pass.  
8.2 The existing UI text test should pass.

8.3 Modify UI Text test suite and add the following tests :  
8.3.1 Add test case for parse all to test for single project.  
   _- Add xtuml project with three levels of packages root, nested , leaf  
   _- Add oal action home at each level  
   _- Invoke parse all action for the project  
   _- validate each action home gets parsed properly at each level.   
  
8.3.2 Add test case for parse all to test for multi-projects.    
   _- Add two xtuml projects with three levels of packages root, nested , leaf  
   _- Add oal action home at each level for each project  
   _- Select both projects  
   _- Invoke parse all action for the two projects  
   _- validate each action home gets parsed properly in both projects   

End
---

