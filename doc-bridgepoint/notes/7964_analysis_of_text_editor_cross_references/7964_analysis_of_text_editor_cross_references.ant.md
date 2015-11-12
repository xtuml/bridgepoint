
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
[7] https://www.eclipse.org/forums/index.php/t/485483/ [limitations to xtext refactoring]  

3. Background
-------------
Tools which describe and use languages need to support refactoring at least on   
a name based level.  The old BridgePoint used a concept that was termed   
relocatables.  Such support did not make it to the eclipse based version of   
BridgePoint.  This support needs to exist in the newer tool version.    

The original support for relocatables was removed due to the fact that the model   
compilers were not yet ready for the new tag approach.  It was due to the new   
tag format.  The model changes to capture both variations of the action_semantic   
attribute remain.  What was pulled out was the utility classes to convert the   
text to and from relocatable tagged data.   

4. Requirements
---------------
4.1 Renaming a referred to element in Bridgepoint shall automatically keep the   
    elements in OAL consistent.        
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
   - Interface Signal   
   - Interface Operation   
   - Interface Signal Parameter   
   - Interface Operation Parameter
   - Interface   
   - Port   
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

This list was provide
5.2 Reference-based approach   

One approach is to reuse the previously done work to introduce relocatables into   
the eclipse based tool [2][3].

In this approach the old branch code shall be restored.  With a bit of work this   
shall allow for tags in the model that are persisted.  They are stored in the   
already existing Action_semantics_internal attribute for each element.  These      
tags are then used when the element editor is opened, allowing resolution of the   
referred to element.  With this work, and in current code, we have two action   
semantic variables.  One is action_semantics, the other   
action_semantics_internal.  The reason is related to persistence versus display.   
The action_semantics variable is used to display the text without any reference    
markers.  The action_semantics_internal attribute is used to store the raw data.   
With this approach the StyledTextEditor class may be used to store date in one   
place and programmatically remove the tags.  This would leave the model in a    
state where the text and tags are only persisted but when read with the tool    
only show the text.   

This approach will provide a separate parser for the textual data, which is   
currently implemented in java.  The utility class will pull out the tag data   
and look up the model element to update the name before displaying text in the   
editor.

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

The LTK framework allows you to register entry points.  When called the above   
will occur.  As for this analysis it appears that this entry point call can    
occur in any place that imports the LTK framework.  This means that LTK should   
work on the following editors:   

- Rename in Canvas
- Rename in Model Explorer   
- Rename in properties   
- F2 shortcut   
   
This should not be an issue as rename is done through the same path in all   
cases.  An LTK hook would need to be placed in one location and should trigger   
refactoring in each case.  The F2 shortcut is a bit different as the user does    
not use the menu to refactor.  JDT does not provide such a shortcut, it uses a    
longer one.  We could create a new shortcut and have that trigger refactoring,    
or we could try to trigger the LTK refactor when F2 is pressed.  We could also   
allow the change textually (like in ME) and provide the refactoring dialog after    
enter is pressed, or the focus is lost for the in-place editor.      
   
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

6.1.2 Re-apply code changes from the old branch from reference [2]     
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
For the elements listed in section 5.1 do the following:    

_- Rename the element
_R All references in OAL are updated.  Either by updating a reference on the   
   referring element or by updating all referring elements with persistence.   
_R The next time an editor is opened or a parse is performed the tool shall use   
   updated data.

End
---

