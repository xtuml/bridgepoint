---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# BridgePoint Editor Navigation enhancements and fixes
### xtUML Project Design Note


1. Abstract
-----------
A provided a screenshot for BrdigePoint UI shows 3 bugs and 1 needed 
enhancement, this note describe how to address them.

2. Document References
----------------------
[1] Issues 229, https://github.com/xtuml/internal/issues/229  
[2] CQ DEI dts0101055471 - BridgePoint Editor Navigation enhancements and fixes  
[3] Redmine Issue 7535, https://support.onefact.net/redmine/issues/7535

3. Background
-------------
3.1 Each editor tab has tooltip text that is displayed when the mouse cursor 
  hovers over it.

3.2 Eclipse provides a "Switch to Editor" dialog that lists all of the opened 
  editors and their corresponding paths.


4. Requirements
---------------
4.1 Get benefit of provided Eclipse "Switch to Editor" functionality, to be used
  in xtUML Editor 

4.2 The user shall be able to determine, for each opened editor, the exact model 
  element that is being viewed by this editor.

5. Analysis
-----------
5.1 First bug: reports the possibility to have more than one editor with exact title
  and tooltip which make it nearly impossible to determine which editor belongs
  to which element.
	
5.1.1 For tooltip:  
	When mouse cursor hovers editor tab for a package, the full path of this 
  package will be displayed in the tooltip pop-up window. For all other elements 
  the tooltip text is same as the editor tab title.
   To resolve this, we shall follow the same approach taken for Packages, by
  display the model element path in the editor tab tooltip.
  
5.1.2 For editor title:  
	As operations are most likely to have exact names under different model 
  classes, it is better to add the parent name next to the operation
  name in the editor title instead of operation name only. this applies for
  all OAL action homes.
	
  Example for current editor title for an operation:  
  getValue: Operation Activity  
  
  Example for desired editor title for an operation:   
  Value::getValue  Operation Activity  

5.2 Second bug: reports that any opened graphical editor, the editor name in "Switch 
  to Editor" dialog is "xtUML Graphical Editor" which is wrong.  
  The editor name in this dialog shall be the same as editor tab title.
	
5.3 Third bug: all model elements -except Package- that opened in editor, do not 
  have path value in "Switch to Editor" dialog, but instead a duplicate value of 
  the editor name. The path field for all model elements in "Switch to Editor" 
  shall view elements full path.
		
5.4 Enhancement: xtUML Editor graphical, activity and description editors use
  the editor icon instead of the viewed model element icon in the editor tab. 
  Unlike JDT that uses the element icon. It is required to view the model
  element icon in the editor tab instead of the editor icon.
  
5.5 Enhancement : Omit the diagram type from the tab title, and add icon decoration
	for element description editor.
	

6. Design
---------
6.1 First bug fix:  
6.1.1 Fix the tooltip    
6.1.1.1 Graphical Editor: The associated elements with this editor are: Packages 
  and Components. The signature of getPath() operation in Component class has 
  two parameters (String path, and Boolean includeSelf). Unlike getPath 
  signature in Package class has only one parameter (String path).
  The getPath invocation by reflection in GraphicalEditor.getTitleToolTip() 
  is failed due to this extra parameter. As extra parameter (Boolean 
  includeSelf) is always passed with true. This extra parameter shall be 
  deleted and update all operation references.

6.1.1.2 Activity/Description Editor: Add getPath() operations for all elements
  that can be opened by these editors to return the element path.  Also, in 
  ActivityEditor and DescriptionEditor, override getTitleTooltip()
  method, and invoke the created getPath() methods by reflection.

6.1.2 Fix editor title:  
6.1.2.1 Graphical Editor: no changes required.  
6.1.2.2 Activity/Description Editor: instead of using getName() method to form
	The editor title, use the added getPath() operations, and crop the returned
	path to the first parent.

6.2 Second bug fix: in ModelEditor class, override getTitle() method that is invoked
	by Eclipse to return the editor name to be viewed in "Switch to Editor" 
	dialog.

6.3 Third bug fix: 6.1.1.2 fix will resolve this bug indirectly.

6.4 Enhancement: in ModelEditor, ActivityEditor and DescriptionEditor 
	override getTitleImage() method to return the corresponding icon for the
	model element opened by editor.

7. Design Comments
------------------
7.1 Eclipse has a preference (EDITOR_MINIMUM_CHARACTERS) that determine the 
	maximum visible characters in the editor tab. As the change in 6.1.2 would 
	likely cause to push the OAL home name out of visible section.  
	Change EDITOR_MINIMUM_CHARACTERS value from the default 20 characters to
	40 characters.

8. Unit Test
------------
8.1 Run all Junit tests.
8.2 Add test suite that perform the following:   
    - Open model element in editor  
    - Validate the editor title and tooltip  


End
---

