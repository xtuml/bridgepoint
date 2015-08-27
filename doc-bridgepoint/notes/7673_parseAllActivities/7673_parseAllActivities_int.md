---

This work is licensed under the Creative Commons CC0 License

---

# Parse all activities in a project easily  
### xtUML Project Implementation Note
 
 
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
 

5. Work Required
----------------
5.1 Modify the file create_plugin_xml.arc to add an objectContribution for 
    System Model instances to show the "parse all activities" menu entry. 
    
5.2 Add New Constructor for AllActivityModifier class that takes as the first 
    parameter an instance of SystemModel_c.
        
5.3 Add new Constructor for ParserAllActivityModifier that takes as xthe first 
    parameter an instance of SystemModel_c that sets the parseAll attribute 
    to true.
    
5.4 Add new attribute m_sysMod of type SystemModel_c to ParserAllActivityModifier
    class  that gets populated with the NonRootModelElement instance.
    
5.5 Modify resetAllPackages() method to use the m_sysMod and retrieve all 
    packages related to it across the relation R1401 and reset each package.
    
5.6 Modify all the following functions to use the packages related to system model
    instance across R1401 to collect activities below these packages, 
   - getAllFunctions() 
   - getAllBridges()
   - getAllOperations() 
   - getAllMDAttrs() 
   - getAllStateActivities() 
   - getAllTransitionActivities() 
   - getAllInterfaceOperations()  
   - getAllInterfaceSignals()    
    
    

6. Implementation Comments
--------------------------
6.1 The issue 7782 [2], is not related to the work introduced for this issue.  
    so it will be treated as a separate issue .  
    The work that will be done for[2] will have its own branch.   
    
    
 
7. Unit Test
------------
7.1 The existing parse all tests should pass.  
7.2 The Updated UI text test should pass.
 

8. Code Changes
---------------
Branch name: 7673_parseAllActivities  

<pre>
 
org.xtuml.bp.als.oal [bridgepoint 7673_parseAllActivities]/src/org/xtuml/bp/
    als/oal/ParserAllActivityModifier.java

org.xtuml.bp.ui.text [bridgepoint 7673_parseAllActivities]/arc/
    create_plugin_xml.arc
org.xtuml.bp.ui.text [bridgepoint 7673_parseAllActivities]/src/org/xtuml/bp/ui/
    text/activity/AllActivityModifier.java
org.xtuml.bp.ui.text [bridgepoint 7673_parseAllActivities]/src/org/xtuml/bp/ui/
    text/activity/ParseAllActivitiesAction.java

org.xtuml.bp.ui.text.test [bridgepoint 7673_parseAllActivities]/src/
    UITextGlobalsSuite.java
org.xtuml.bp.ui.text.test [bridgepoint 7673_parseAllActivities]/src/org/xtuml/
    bp/ui/text/test/ParseAllActivitiesOnSystemModelTest.java



</pre>

Branch name: 7673_parseAllActivities  

<pre>
 ParseAllOnSystemTestModels [models 7673_parseAllActivities]/ProjectOne.xtuml
 ParseAllOnSystemTestModels [models 7673_parseAllActivities]/ProjectTwo.xtuml
</pre>



End
---

