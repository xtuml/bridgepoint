5.x Implement editor enhancements using existing framework   

Using the existing OAL editor we can add auto-completion support be designing content assistant processors.  These processors are executed with a document region where context help was requested.  There shall be at least a keyword processor, an invocation processor, and a local variable processor.  Using a token scanner we can determine from the given region what processor is required.  For instance if the token is white space or not a keyword then we shall use the keyword processor and local variable processor.  If the token is the '.' character or "::" characters we shall use the invocation processor.  

5.x.1 Keyword processor   

The keyword processor shall only show during whitespace content assist requests.  It also must locate the previous token to further filter what is shown.  For instance if content assist is requested for whitespace and the previous token is a keyword such as select, only one, any or many shall be given as proposals.  
  
In addition to filtering based on the previous token, the proposals shall be filtered based on current text.  An example would be sel, which should only show select.   

5.x.2 Invocation processor   

The invocation processor shall only show after the appropriate characters as stated above.  The proposal list must be determined by the token preceeding the character.  Once this information is present its region shall be determined and the appropriate Statement class shall be located by matching the region values to the statements line number and start position.  With this information the available invocations can be determined via the associated variable.   

5.x.3 Local variable processor   

As stated above this processor shall be used with whitespace requests.  All Variable instances shall be considered as long as they are within scope.  This will be all values within the current Block and any others until the Body.  No variables defined below the current scope shall be considered.   

5.x.4 Parameter processor   

We should eventually have a parameter processor, which would allow completion of required parameters.  This is more work then should be for this issue.  It would require the tool to consider which parameters have been used to filter the available list.  
5.x.5 Comments   

When a request is made within any comment token, not proposals shall be given.   

5.x.6 Proposal display and completion   

The proposal display shall be as such:  

<element to be added> - Additional information such as owning element, next page xtUML element description  

Here is an example for an operation invocation:  

Pane 1                                                                      | Pane 2         
operation(param1: type, param2: type) - Model::Classes::ClassOne::operation   This operation performs this.  

5.x.6.1 Completion   

When a user chooses a proposal the tool needs to insert text.  For keywords and variables this is simply the keyword or variable name.  For invocations it is a bit more complicated.  JDT is very elegant with their completion, allowing current edit spots to allow the user to fully complete the assist.  For this work I suggest we simply enter something like empty or maybe the parameter names.  

5.x Declaration and Reference searching   

The search infrastructure already considers declaration and reference searching in its design.  Further work would have to be completed to make use here.  The classes under the Search package of ooaofooa starting with Declarations and References would need to be fully implemented.  The engine classes would require a processQuery() operation.  This operation needs to search the participants associated with the Engine, for each create a match if the signature matches.  The Query classes would require a configureParticipants and createParticipant operations.  Using the same logic as with the content assist processors, we can determine what element to search for.  A new context menu entry would be defined and present anytime a searchable reference was selected in the editor.  When executed the search infrastructure shall be called locating that element by signature.  All results will show in the search view as they do with JDT.  Those entries already allow traversal to the model element where expected.  
