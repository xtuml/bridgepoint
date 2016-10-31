---

This work is licensed under the Creative Commons CC0 License

---

# Identifier Handling for MASL
### xtUML Project Analysis Note


1. Abstract
-----------
This note describes issues around MASL export due that are related to element
names in the model and proposes a solution.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8753](https://support.onefact.net/issues/8753) Squash blanks from classes, role phrases, etc on MASL export    
<a id="2.2"></a>2.2 [BridgePoint DEI #8771](https://support.onefact.net/issues/8771) Problem characters prevent MASL export     
<a id="2.3"></a>2.3 [BridgePoint DEI #8774](https://support.onefact.net/issues/8774) Problem with MASL export and referentials     
<a id="2.4"></a>2.4 [MASL Grammar - Identifier rule](https://github.com/xtuml/mc/blob/master/masl/parser/src/MaslLexer.g#L288)      

3. Background
-------------
The format and allowable characters in element names are very important. 
BridgePoint and downstream tools must work correctly.  During the initial 
implementation of x2m (xtuml to masl transformation tool) it was noted that
the tool should remove spaces in names and role phrases [[2.1]](#2.1).      

Additional testing of the xtUML to MASL flow identified additional failure modes
[[2.2]](#2.2)[[2.3]](#2.3) that are ultimately related to the naming conventions
that are enforced (or not) by the tool.   

4. Requirements
---------------
4.1 BridgePoint shall produce valid MASL output  

5. Analysis
-----------
5.1 The original failure mode that started this investigation [[2.3]](#2.3) lead
  to the discovery that there are three references to attribute ```Txt_Phrs``` 
  inside maslout in ```attribute2attribute()``` that are not wrapped in 
  ```T::underscore()``` like other references to this variable.  Fixing these 
  three places would solve [[2.3]](#2.3), but we discovered that this is just 
  part of a bigger picture issue.   

5.2  This investigation found that there are lots of places where ```Name``` 
  attributes are not run through a ```T::<modifier>()``` substitution operation.   

5.3  This lead to a search of the MASL reference manual and MASL grammar 
  [[2.4]](#2.4) to find out if MASL allows spaces in names.  The answer is "no".  
  
5.4  As we reasoned about this issue, it was noted that we have a general issue 
  that our ```x2m``` tooling is producing _the MASL model itself_.   
5.4.1  BridgePoint is persisting _the model_ and it should support round trip 
  analysis with 0 differences.  If x2m conversion removes spaces, BridgePoint
  is breaking this support because the model data that comes out is not 
  identical to the source model.  On re-import, the element names would 
  not be the same as in the original model.   
5.4.2  The upshot of this is that BridgePoint really can't allow spaces in
  model element names inside the editor and have the tool persist without them. 
  This output of the MASL conversion tools is not just input to downstream 
  tooling.    
5.4.2.1 MC-3020 uses ```$_{}``` and ```$r{}```, but those conversions feed into 
  output that is throw-away.  The output of x2m feeds downstream, but is also 
  meaningful and persistent.
    * It is useful for round-trip analysis.  
    * It (the MASL ```.mod``` file) is read by the MASL syntax editor.  
    * It is editable on disk.  

5.5 The next step was to step back and see what the language reference and 
  grammar say.  The answer is found in [[2.4]](#2.4).  
5.5.1  The identifier rule is used for almost all names in MASL.      
5.5.2  Spaces are not allowed in identifiers.  In fact, the rules is quite
  restrictive.  It is reproduced from the grammar here:
```   
Identifier : ( Letter | '_' ) ( Letter | Digit | '_' )*;
```   

5.6  If BridgePoint enforced this rule in the editor, it would not need to use 
  any ```T::<modifier>()``` substitutions in the x2m flow since the input to the 
  x2m tool coming from the editor would already be valid. The current usages 
  of the substitutions in x2m could be removed.   
5.6.1  Note that BridgePoint could choose to leave some strategic substitutions
  in place in x2m that would give it a reasonable chance at succeeding in the 
  x2m flow given and input model that was not created with restricted 
  identifiers.  However, this would still mean that round trip analysis of the
  model is broken and the MASL xtext-based editor would be broken.   
5.6.2  Given these drawbacks, BridgePoint shall not perform substitutions in
  x2m.  x2m shall fail with an invalid identifier error and not produce MASL
  output.

5.7 The solution suggested in this note will resolve all three of the related
  issues called out in section 2.   
  
6. Work Required
----------------
6.1 Add New Preference   
6.1.1  Add a new preference "Enforce MASL identifiers for model elements"   
6.1.1.1  Add explanatory hover text 
```
This preference restricts element names to conform to the MASL standard 
following the BNF rule: ( Letter | '_' )( Letter | Digit | '_' )*;

Identifiers may only contain letters, numbers, and underscores.  Spaces and
hyphens are not allowed.
```  

6.2 Use New Preference   
6.2.1  Find current enforcement rules in ```RenameAction::isNameValid()```.         
6.2.2  Modify this to check the preference and enforce the identifier
  restrictions stated in the BNF rule.   
6.2.3  Modify the error message to provide appropriate error text.   

6.3 Remove usages of ```T::``` substitution in masl & maslout   

7. Acceptance Test
------------------
7.1  Verify the preference turns the MASL identifier checking on and off 
  properly.   
7.2  With the preference turned on, try to create identifiers (names, roles
  phrases, etc) with spaces and hyphens.  Verify that an error is noted and
  the invalid name is not persisted in the model.   

End
---

