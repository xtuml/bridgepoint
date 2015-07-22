---

This work is licensed under the Creative Commons CC0 License

---

# Unable to relaunch when using inter-project referred EE
### xtUML Project Design Note


1. Abstract
-----------
When a project that uses a shared realized external entity by inter-project
is terminated in verifier. Java LinkingError exception is thrown when re-execute 
this model in verifier and invoke the shared external entity.  

2. Document References
----------------------
[1] [BridgePoint DEI #7744] (https://support.onefact.net/redmine/issues/7744)
[2] [BridgePoint DEI #7800] (https://support.onefact.net/redmine/issues/7800)
[3] [BridgePoint DEI #7801] (https://support.onefact.net/redmine/issues/7801)

3. Background
-------------
  When one of realized external entity bridges is invoked in verifier, a class 
definition is created for the external entity mapped class -if definition does 
not exist- and stored in a definitions list that is used to check later if the 
class is defined or not. Also a class loader object is created and added to 
a Map named "vmclMap" with external entity parent SystemModel as a key, and the 
class loader as a value.

  When a project is terminated in the verifier, all class loader object entries 
in the vmclMap map with terminated project SystemModel key will be deleted, and 
the definition list will be cleared.

  If Projects A and B are running in verifier, and project A uses a realized
external entity defined in project B. When project B got terminated, the 
realized class definition and class loader will be deleted. But as the Project A
still running in verifier and uses the shared realized external entity, the 
class definition and class loader will be recreated with the first bridge 
invocation after project B termination. This may cause undesired behavior for 
project A, as all realized class data is reset to original value with this 
definition recreation. If project A is terminated after that. the definitions 
list will be cleared again, but the class loader entry in the map will not be 
removed, as the SystemModel used as a search key to get the class loader Map 
entry is Project A SystemModel -the terminated project- while the class loader 
entry key is Project B SystemMOdel -The parent of the realized external entity.  

  If Project C and D have realized external entity, and each one of them only 
uses its own realized external entity. When both launched in verifier, and one 
of them -e.g. Project C- is terminated. The definitions list will be cleared 
which contains both Project C and Project D realized classes definitions.  

  If the code tries to redefine class, without deleting the class loader object 
first, this will cause a linkingError error. 

	Every time a class definition and class loader are deleted, and recreated, 
all of the class static variables are set to original values, also all 
class's objects are deleted.  

4. Requirements
---------------
4.1  User shall be able to terminate projects simulation and relaunch it without 
	the need to restart the tool.

5. Analysis
-----------
5.1  If user did not launch the project that contains a shared realized external 
	entity, and only simulated the project that uses it. The class loader object 
	will not be deleted by simulation termination as described in Section 3. 
	When restart the simulation, since the definition list is cleared, a new 
	class definition will be created, but class loader object was not deleted 
	which will cause a LinkingError reported in the issue.  
	
	In order to avoid such error, user needs to launch both projects in verifier.  
	 
5.2 The two scenarios mentioned in Section 3 will also cause LinkingError. The 
error can be triggered also without need to restart the verifier.
  	 	 
	 
5.3 In order to avoid the linkingError, there are different approaches, but none 
	of them can guarantee a consistent simulation result if the user terminate 
	some projects in the middle of simulation. These approaches are:

5.3.1 Do not delete any class loader or class definition until every simulated 
	project is terminated.
	
	But if the user terminates one of the project (even the project with the 
	realized external entity), and relaunch it. This project may not behave as 
	desired/expected since the class definition is not in the original state.

5.3.2 Delete the definitions and class loader that are related only with the 
	terminated project, and clear all definitions and delete all class loader 
	map entries when the last project is terminated in verifier to avoid 
	Section 3 scenarios.  
	
	But if user terminates the project that contains a shared reliazed external 
	entity, the class definition and class loader associated with it will be 
	removed as expected. When the remaining projects in simulations invoke this 
	shared realized external entity -owned by the terminated project- the 
	simulation behavior may not as desired/expected since the new class 
	definition will be created with the original status. Also when the 
	terminated project relaunched, it may not behave as desired/expected as 
	class definition is already created and no longer in the original state.  
	
5.3.3  Delete the definitions and class loader related only with the terminated
	project. Also, if any project tries to invoke model element that are defined
	in a project that is not running in verifier, the invocation is canceled 
	and warning invocation failure	message will be printed in the console 
	asking the user to add the project to the verifier session.  
	
6. Design
---------
6.1  Approach 5.3.1 is considered the best approach, as this approach only 
	effects terminated projects that own shared library. while approach 5.3.2 
	effects effects all projects that use the shared realized external entity 
	including the owner project. Approach 5.3.3 is not chosen, because it is 
	outside the issue scope as it affects not only the realized external entity, 
	and the tool will be more restricted. Issues [2] and [3] are submitted
	to address this.

7. Design Comments
------------------
None.  

8. Unit Test
------------
8.1  Starting with a workspace that contains two projects, Project B has a 
	shared realized external entity, and Project A invokes this external entity.
	The realized class has a static counter, that increment each time getCounter()
	method is invoked. When the class definition is deleted, the counter value 
	is reset to 1.
8.2 Launch only project A in verifier  
8.3 Execute test function that invokes the realized external entity  
8.4 Terminate and relaunch the verifier  
8.5 Re-execute the test function  
_R No LinkingError.  
8.6 Launch both projects in verifier  
8.7 terminate project B  
8.8 Execte test function in Project A  
_R No linkingError, and result shows the class definition is not reseted.  

End
---

