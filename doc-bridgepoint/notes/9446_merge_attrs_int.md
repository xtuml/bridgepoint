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
<a id="2.2"></a>2.2 [BridgePoint DEI #9747](https://support.onefact.net/issues/9747) Show Relationship of Referential ID in Combine With Dialog   

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
4.2.2  The combo box shall show referential attributes that have the same 
  type as the current attribute that started the combination process.  

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
5.2.2  Current code with addtions marked by "+"

```
select any o_attr from instances of O_ATTR where ( selected.Attr_ID == param.Attr_ID );

can_combine = false;

if ( self.Obj_ID == o_attr.Obj_ID )  // must be in the same class to combine
    select one rattr related by self->O_RATTR[R106];
    if ( not_empty rattr )
        select one other_rattr related by o_attr->O_RATTR[R106];
        if ( not_empty other_rattr )
            // two rattrs pointing to the same base
            can_combine = rattr.BAttr_ID == other_rattr.BAttr_ID and not rattr.alreadyCombinedWith( id: other_rattr.Attr_ID );
+           if ( not can_combine )
+               // allow combination with other referentials with the same type
+               type_match = self.DT_ID == o_attr.DT_ID
+               can_combine = type_match and not rattr.alreadyCombinedWith( id: other_rattr.Attr_ID );
+           end if
        else
    ...
``` 

### 6. Implementation Comments

6.1  During the analysis of this issue we noticed that an enhancement is needed to
  the "Combine With..." dialog.  Multiple referential attributes with the same name
  are currently indistinguishable.  This is captured in [2.2].  

### 7. Unit Test

7.1 TODO - Find existing Combine With tests - update for new functionality  
  * The Combine With tests are part of Core 2 Test Suite
  * CombineSplitReferentialsTestGenerics.java

### 8. User Documentation

TODO - Combine With is not described in the existing documentation.  I think we 
should add some text in the ClassDiagram doc that describes combine and split

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 9446_merge_attrs  

<pre>

</pre>

### End

