
---

This work is licensed under the Creative Commons CC0 License

---

# Analysis of text editor cross references   
### xtUML Project Analysis Note
 

1. Abstract
-----------
This note analyzes the approaches available to support cross reference renaming.
This can also be known as rename refactoring.    

2. Document References
----------------------
[1] [BridgePoint DEI #7964](https://support.onefact.net/redmine/issues/7964)  
[2] https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20090903/technical/Action_Semantics/i1872.dnt [relocatables design note]   
[3] https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20090903/technical/Action_Semantics/i1872.int [relocatables implementation note]   
[4] https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20090903/technical/Action_Semantics/i2322.int [relocatables removed implementation note]   
[5] http://koehnlein.blogspot.com/2011/06/rename-refactoring-in-xtext-20.html [xtext refactoring]   
[6] https://eclipse.org/articles/Article-LTK/ltk.html [LTK]   
[7] https://www.eclipse.org/forums/index.php/t/485483/   

3. Background
-------------
Tools which describe and use languages need to support refactoring at least on   
a name based level.  The old BridgePoint used a concept that was termed   
relocatables.  Such support did not make it to the eclipse based version of   
BridgePoint.  This support needs to exist in the newer tool version.   

4. Requirements
---------------
4.1 Renaming a referred to element in Bridgepoint shall trigger refactoring of   
    referencing element's in OAL.    
4.2 Renaming a referred to or referring element in the OAL editor shall not   
    trigger refactoring.   

5. Analysis
-----------   

5.1 Referenced elements   

   - Attribute   
   - Function Parameter   
   - Operation Parameter   
   - Bridge Parameter
   - Bridge      
   - Signal   
   - Interface Operation   
   - Signal Parameter   
   - Interface Operation Parameter   
   - Function   
   - Operation   
   - Class Key Letters   
   - External Entity Key Letters   
   - Association Number   
   - Association Phrase   
   - State Machine Event Derived Label   
   - State Machine Event Meaning   
   - State Machine Event Data Item   
   - Data Type         
   - Enumerator   

5.2 Reference-based approach   

One approach is to reuse the previously done work to introduce relocatables into   
the eclipse based tool [2][3].

In this approach the old branch code shall be restored.  With a bit of work this   
shall allow for tags in the model that are persisted.  They are stored in the   
already existing Action_semantics_internal attribute for each element.  These      
tags are then used when the element editor is opened, allowing resolution of the   
referred to element.

They are updated upon save of a referring element editor or parse all.  In the   
parse all case reference's files will be persisted.    

5.3 LTK approach   

JDT provides an API for refactoring at a generic level.  The Language ToolKit   
is the infrastructure that allows generic languages to use the JDT refactoring
support.  This API provides an infrastructure that ties into the Refactor menu.    

It provides a life cycle as seen here [6].   

In short form you have:   

1. User right clicks Refactor > Rename...   
2. LTK asks the provider to check initial conditions   
3. A dialog is displayed asking for the new name    
4. The user enters a new name   
5. Final conditions are checked   
6. A preview dialog is shown   
7. Finish is pressed and all elements are updated

With JDT all referencing elements are persisted on completion of this dialog.   

With this approach we need to consider persisting multiple files for one element   
change.  This is the same problem faced with both relocatables and the LTK.   
   
5.4 xtext approach [6]   

xtext uses the LTK support to handle its refactoring.  If using xtext we would   
have to have a fully working xtext editor.  xtext builds an EMF model to   
traverse while JDT builds an AST model.  xtext should only be used when we have   
a fully working xtext editor (both semantically and textually).   

In addition, xtext has known problems with refactoring as seen here [7].

6. Work Required
----------------   

Depending on our choice only one section will be completed.   

6.1 Referenced base approach   

6.1.2 Merge code from old branch   
6.1.3 Update code for any recent changes   
6.1.3.1 Update any api changes   
6.1.3.2 Add new elements (component based)   

6.2 LTK approach   

6.2.1 Introduce extension for LTK   
6.2.2 Define initial condition checks   
6.2.3 Define final condition checks   
6.2.4 Define method to update all references on finish   
6.2.5 Persist all modified elements   

6.3  xtext approach   

6.3.1 Needs discussion (would require investigation further and may never work)      

7. Acceptance Test
------------------
_- For the following elements:   
   - Attribute   
   - Function Parameter   
   - Operation Parameter   
   - Bridge Parameter
   - Bridge      
   - Signal   
   - Interface Operation   
   - Signal Parameter   
   - Interface Operation Parameter   
   - Function   
   - Operation   
   - Class Key Letters   
   - External Entity Key Letters   
   - Association Number   
   - Association Phrase   
   - State Machine Event Derived Label   
   - State Machine Event Meaning   
   - State Machine Event Data Item   
   - Data Type         
   - Enumerator   
_- Rename the element
_R All references in OAL are updated.  Either by updating a reference on the   
   referring element or by updating all referring elements with persistence.   
_R The next time an editor is opened or a parse is performed the tool shall use   
   updated data.

End
---

