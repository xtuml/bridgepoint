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
<a id="2.3"></a>2.3 [BridgePoint DEI #9765](https://support.onefact.net/issues/9765) Manual test for this work     
<a id="2.4"></a>2.4 [BridgePoint DEI #9766](https://support.onefact.net/issues/9766) Document Combine With and Split  

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
```
      // combining is allowed with other referential attributes of the same type
      //   - Get the set of referential attributes
      //   - If there's more than one (because we don't want to count ourself),
      //       iterate over the set and see if any are of the same type.
      select many rattr_candidate_set related by self->O_OBJ[R102]->O_ATTR[R102]->O_RATTR[R106] where ( selected.Attr_ID != self.Attr_ID );
      for each rattr_candidate in rattr_candidate_set          
        select one other_attr related by rattr_candidate->O_ATTR[R106];
        dtid_other = other_attr.DT_ID;
        select one rattr_candidate_dt related by rattr_candidate->O_BATTR[R113]->O_ATTR[R106]->S_DT[R114];
        if ( not_empty rattr_candidate_dt )
          dtid_other = rattr_candidate_dt.DT_ID;
        end if;
        if ( dtid_other == dtid_self )
          return true;
        end if;
      end for;
```

5.2 ```Attribute.canCombineWith()```   
5.2.1  The CME PEI data (```bp.core/sql/context_menu.pei.sql```) causes a dialog 
  Java class (```CombineWithOnO_ATTRWizardPage1```) to be created during code 
  generation.  This dialog calls Attribute.canCombineWith() to present the user
  with a combo box of attributes to combine with.  
5.2.2  Current code with additions marked by "+"

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
+             // We have to be careful of baseless referentials. For those, the datatype
+             // is found on the referential attribute itself and not through the base.
+             // If there is an associated base attribute, we get the datatype through it.
+             dtid_self = self.DT_ID;
+             dtid_other = o_attr.DT_ID;
+             // allow combination with other referentials with the same type
+             select one self_dt related by rattr->O_BATTR[R113]->O_ATTR[R106]->S_DT[R114];
+             if ( not_empty self_dt )
+               dtid_self = self_dt.DT_ID;
+             end if;
+             select one other_rattr_dt related by other_rattr->O_BATTR[R113]->O_ATTR[R106]->S_DT[R114];
+             if ( not_empty other_rattr_dt )
+               dtid_other = other_rattr_dt.DT_ID;
+             end if;
+             type_match = dtid_self == dtid_other;
+             can_combine = type_match and not rattr.alreadyCombinedWith( id: other_rattr.Attr_ID );
+           end if;             
        else
    ...
``` 

### 6. Implementation Comments

6.1  During the analysis of this issue we noticed that an enhancement is needed to
  the "Combine With..." dialog.  Multiple referential attributes with the same name
  are currently indistinguishable.  This is captured in [2.2].  

### 7. Unit Test

7.1  Existing Junits must pass  
7.2  Run manual test captured in [2.3]  

### 8. User Documentation

Created a follow-on issue to document Combine With and Split [2.4].  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 9446_merge_attrs  

<pre>
 doc-bridgepoint/notes/9446_merge_attrs_int.md                                                                      | 139 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/Attribute.xtuml                        |  98 +++++++++++++++++++++++++++++------------
</pre>

### End

