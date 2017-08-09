---

This work is licensed under the Creative Commons CC0 License

---

# Relax Rules for Combining Attributes 
### xtUML Project Implementation Note


### 1. Abstract

This note describes a change to BridgePoint that relaxes the rules
that govern user ability to combine attributes.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9446](https://support.onefact.net/issues/9446) Headline issue    

### 3. Background

This issue was originally raised by MASL users who are used to the way iUML allows
for combining referential attributes.  The initial report is:

> In the situation where a subtype is participating in two generalisation 
> relationships (i.e. it has two super types), it does not appear to be 
> possible to merge the relational identifier from each super type class 
> together. Instead a second identifier is added, which cannot be merged 
> with the first.

During discussion of the problem report, the development team decided that
the requested change to relax the rules should be applied in all cases and
should not be a MASL-specific setting.  

### 4. Requirements

4.1  The ```Combine With...``` CME shall be present in all cases where the class
  contains valid attributes to combine with.  
4.2  The ```Combine With``` dialog shall populate the combo box with all valid
  attributes to combine with.  
4.2.1  Currently supported cases shall not change.  
4.2.2  The combo box shall show referential attributes that have the same name
  and type as the current attribute that started the combination process.  
4.2.3  Referential attributes are currently shown in the combo box with only
  their name.  The combo box shall be enhanced to also show the participating 
  relationship information next to the name in ```{Rn}``` form as it appears 
  in the canvas.  (Maybe make a follow-on issue?)    

### 5. Work Required

5.1 ```Attribute.actionFilter()```   
5.1.1  The "combine" stanza must be extended to return true if there are other
  referential attributes in the class that have the same type as the current 
  attribute.  

5.2 ```Attribute.canCombineWith()```   
5.2.1  The CME PEI data (```bp.core/sql/context_menu.pei.sql```) causes a dialog 
  Java class (```CombineWithOnO_ATTRWizardPage1```) to be created during code 
  generation.  This dialog calls Attribute.canCombineWith() to present the user
  with a combo box of attributes to combine with.  
5.2.2  TODO - What modifications needed?

5.3  TODO - where do we put the enhancement to show the relationship in the combo?  
  The reason for this relationship is that if the class contains more than two 
  attributes with the same name it is impossible in the combine with dialog to determine
  which one you are formalizing with.  We may want to handle this as a follow-on issue
  and solve the original issue first...     

### 6. Implementation Comments

None.  

### 7. Unit Test

7.1 TODO - Find existing Combine With tests - update for new functionality  

### 8. User Documentation

TODO - look into the existing docs and see if Combine With is
described anywhere and if it should be modified

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 9446_merge_attrs  

<pre>

</pre>

### End

