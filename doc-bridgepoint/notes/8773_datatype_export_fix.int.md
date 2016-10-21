---

This work is licensed under the Creative Commons CC0 License

---

# Datatypes not included in MASL exported data
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the fix for an issue with datatype export discovered during
testing.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8773](https://support.onefact.net/issues/8773) - Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #8411](https://support.onefact.net/issues/8411) - Type and TypeRef   

3. Background
-------------
The type system for MASL idiom models in the Raven production release relies on
user data types (UDTs) heavily.  During testing we noted that UDTs created 
directly inside BridgePoint (not having come in from an imported MASL model) 
were not being exported to MASL at all, they simply did not show up in the 
output data.  The reason was that the UDTs created directly in BridgePoint did
not have the ```<definition> ... </definition>``` tags in the description
field.   

The underlying key point is that MASL has both types and typerefs. The former
has a definition and the latter does not.  For usability, we have decided that 
a UDT should be able to be created and exported with little effort 
(i.e. without definition tags).   

Also of note is that the type ```MASLtype``` is created when converting MASL 
models to xtUML.  The rest of the types encountered during the conversion 
process are made into xtUML UDTs whose base type is ```MASLtype```.  Models that
begin life inside BridgePoint will not have ```MASLtype```.  
 
4. Requirements
---------------
4.1 If the UDT is based on MASLtype and there is no definition in the 
  description, then treat it as a typeref and do not export it to textual MASL.  
4.2 If the UDT is based on MASLtype and there is a definition, then export it as
  a type definition in the textual MASL.   
4.3 If the UDT is not based on MASLtype, then treat it as a type definition and
  export it to textual MASL using the name of the underlying type 
  (i.e. ```private type myUDT is string```).   

5. Work Required
----------------
5.1  Add the required logic to implement the requirements in 
  ```maslout/lib/xtuml2masl/maslout_imported/type2type```. Here is the OAL for
  the new flow:      
```
  select one s_irdt related by s_dt->S_IRDT[R17];
  // Do not output type definitions for instance reference data types.
  if ( empty s_irdt )
    select one s_udt related by s_dt->S_UDT[R17];
    select one s_edt related by s_dt->S_EDT[R17];

    value[0] = s_dt.Name;
    value[1] = "private";
    
    if ( not_empty s_udt )
      // see what type the UDT is based on
      select one base_s_dt related by s_udt->S_DT[R18];
    
      // get body of type
      start_index = STRING::indexof( haystack:s_dt.Descrip, needle:"<definition>" );
      end_index = STRING::indexof( haystack:s_dt.Descrip, needle:"</definition>" );
      if ( -1 != start_index and -1 != end_index )
        // handle case where there is a definition
        value[2] = STRING::substr( s:s_dt.Descrip, begin:start_index+12, end:end_index );
        descrip_start = STRING::substr( s:s_dt.Descrip, begin:0, end:start_index );
        descrip_end = STRING::substr( s:s_dt.Descrip, begin:end_index+13, end:-1 );
        s_dt.Descrip = descrip_start + descrip_end;
      else
        // There is no definition, use the underlying type.  If the underlying type
        // is MASLtype, treat this as a typeref and do not output it.  Otherwise
        // output using the underlying type as the definition.
        if ( (base_s_dt.Name == "MASLtype") or (s_dt.Name == "MASLtype") )
          continue;
        else
          value[2] = base_s_dt.Name;
        end if;
      end if;
    elif ( not_empty s_edt )
      select any s_enum from instances of S_ENUM where (false);
      // get to the first entry
      select any prev_s_enum related by s_edt->S_ENUM[R27];
      while (not_empty prev_s_enum)
        assign s_enum = prev_s_enum;
        select one prev_s_enum related by s_enum->S_ENUM[R56.'precedes'];
      end while;
      // now iterate over the entries building up the list
      enum_definition = "enum (";
      next_s_enum = s_enum;
      first = true;
      while (not_empty next_s_enum)
        if (not first)
          enum_definition = enum_definition + ", ";
        end if;
        first = false;
        enum_definition = enum_definition + next_s_enum.Name;
        assign s_enum = next_s_enum;
        select one next_s_enum related by s_enum->S_ENUM[R56.'succeeds'];
      end while;
      enum_definition = enum_definition + ")";      
      value[2] = enum_definition;
    else
      continue;
    end if;
  
      out::populate( element:"type", value:value );

      // output pragmas
      search_term = "declaration";
      len = STRING::strlen( s:search_term );
      i = STRING::indexof( haystack:s_dt.Descrip, needle:"<"+search_term+">" );
      j = STRING::indexof( haystack:s_dt.Descrip, needle:"</"+search_term+">" );
      if ( -1 != i and -1 != j and j > i )
        pragma_string = STRING::substr( s:s_dt.Descrip, begin:i+len+2, end:j );
        s = ::parsepragmas( s:pragma_string, list:search_term );
        descrip_begin = STRING::substr( s:s_dt.Descrip, begin:0, end:i );
        descrip_end = STRING::substr( s:s_dt.Descrip, begin:j+len+3, end:-1 );
        s_dt.Descrip = descrip_begin + descrip_end;
      end if;
      s_dt.Descrip = ::parsepragmas( s:s_dt.Descrip, list:"" );

      ::Descrip2description( Descrip:s_dt.Descrip );
      out::populate( element:"type", value:emptyvalue );
  end if;
```

6. Implementation Comments
--------------------------
6.1 As can be seen above, the code now has the ability to output an xtUML 
  enumerated data type.  This is not required to fix the original issue, but 
  it was easy to add now.  It should be noted that this won't really be used
  at this time because the original "MASL-idiom" is to rely on the UDT type
  structured explained previously.  We have another issue [[2.2]](#2.2) that 
  will greatly enhance the type system in a future version.     

7. Unit Test
------------
7.1 Get the branch and build a new x2m, put it into place  
7.2 Download the UDTexport model attached to [[2.1]](#2.1)   
7.3 Test steps  
  * Launch BridgePoint
  * Create a new project UDTexport
  * Import the downloaded model (from 7.2)
  * Expand the model until you see the "comp" component
  * Right-click and select "Export MASL domain"
  * __R__ comp.mod is created in the project's ```masl/``` folder
  * __R__ There are 7 types in the exported model. 

8. User Documentation
---------------------
None.

9. Code Changes
---------------
Fork/Repository: keithbrown/mc   
Branch: 8773_datatype_export_fix   

<pre>
model/maslout/models/maslout/lib/xtuml2masl/maslout_imported/maslout_imported.xtuml 
</pre>

End
---

