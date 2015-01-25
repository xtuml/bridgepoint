.//========================================================================
.// Header
.//========================================================================
.function Header
  .param string name
<html>
<body>
<title>Subsystem '${name}'</title>
<link rel="StyleSheet" href="../../../techpub.css">
.end function
.//
.//========================================================================
.// Footer
.//========================================================================
.function Footer
</body>
</html>
.end function
.//============================================================================
.// SortSetAlphabeticallyByNameAttr
.// Given a set of instances, sets the attribute Order to
.// a value corresponding to the alphabetical order its Name attribute
.// is in the set
.//
.// <item_set> - from instances of anything with Name and Order attributes
.//============================================================================
.function SortSetAlphabeticallyByNameAttr
  .param inst_ref_set item_set
  .//
  .// Clear the 'Order' attribute of all set members.
  .for each item in item_set
    .assign item.Order = 0
  .end for
  .//
  .assign item_set_copy = item_set
  .for each item in item_set
    .for each item_copy in item_set_copy
      .if ( item.Name != item_copy.Name )
        .if ( item_copy.Name > item.Name )
          .assign item_copy.Order = item_copy.Order + 1
        .end if
      .end if
    .end for
  .end for
.end function
.//========================================================================
.// Set up cross references
.//========================================================================
.function SetUpCrossReferences
  .select many objects from instances of O_OBJ
  .for each object in objects
    .select one cur_ss related by object->S_SS[R2]
    .select many attrs related by object->O_ATTR[R102]
    .for each attr in attrs
      .select any attr_typ related by attr->O_RATTR[R106]
      .if ( not_empty attr_typ )
        .// referential attribute.  Get base attribute and put
        .// info in cross-reference object
        .select any base_attr related by attr->O_RATTR[R106]->O_BATTR[R113]->O_ATTR[R106]
        .select any base_obj related by base_attr->O_OBJ[R102]
        .// if already in D_CROSS, don't add again
        .select any cross from instances of D_CROSS where ( selected.ID == "${base_obj.Name}.${base_attr.Name}" )
        .if ( empty cross )
          .select one base_ss related by base_obj->S_SS[R2]
          .assign base_ss_name="$_{base_ss.Name}"
          .create object instance cross of D_CROSS
          .assign id = "${base_obj.Name}.${base_attr.Name}"
          .assign cross.ID = "${id}"
          .assign cross.Text = "${info.unique_num}_${id}"
          .assign cross.Target = "${base_ss_name}.htm#${cross.Text}"
        .end if
      .end if
    .end for .// attr in attrs
  .end for  .// object in objects
.end function
.//
.//========================================================================
.// Get Heading 1 using the subsystem name
.//========================================================================
.function GetHeading1ForSubsystem
  .param inst_ref subsystem
  .param string section
  .param integer ss_count
  .//
<h1>
<a name="a_${info.unique_num}"></a>
  .if (not_empty subsystem) 
    .print "Subsystem: ${subsystem.Name}"
Subsystem '${subsystem.Name}'
</h1>
  .else
    .print "Subsystem not found at count: ${ss_count}"
  .end if
.end function
.//
.//========================================================================
.// Get Heading 2
.//========================================================================
.function GetHeading2
  .param string section
  .param integer ss_count
  .param string text
  .//
<h2>
<a name="a_${info.unique_num}"></a>
${text}
</h2>
.end function
.//
.//========================================================================
.// Get Heading 3 for Object
.//========================================================================
.function GetHeading3ForObject
  .param inst_ref object
<h3>
.//<a name="a_${info.unique_num}"></a>
<a name="a_${object.Numb}.$r{object.Name}"></a>
${object.Numb}. ${object.Name}
</h3>
.end function
.//
.//========================================================================
.// Get Description for Entity
.//========================================================================
.function GetDescriptionForEntity
  .param inst_ref entity
  .assign attr_result = TRUE
  .if ( "${entity.Descrip}" != "" )
<p>
<a name="a_${info.unique_num}"></a>
$tnonl{entity.Descrip}
</p>
  .else
    .assign attr_result = FALSE
  .end if
.end function
.//
.//========================================================================
.// Get Description for Entity Indent
.//========================================================================
.function GetDescriptionForEntityIndent
  .param inst_ref entity
  .assign attr_result = TRUE
  .if ( "${entity.Descrip}" != "" )
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Description: $tnonl{entity.Descrip}
</p>
  .else
    .assign attr_result = FALSE
  .end if
.end function
.//
.//========================================================================
.// Get Object Data
.//========================================================================
.function GetObjectData
  .param inst_ref object
  .// first handle object and attribute signature
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
$C{object.Name}(\
  .select many attrs related by object->O_ATTR[R102]
  .for each attr in attrs
$_{attr.Name}\
    .if (not_last attrs)
, \
    .end if
  .end for
)
</p>
  .// now handle identifiers
  .select many ids related by object->O_ID[R104]
  .for each id in ids
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Identifier *\
    .if (id.Oid_ID != 0)
      .assign idnum = id.Oid_ID +1
${idnum}\
    .end if
: \
    .select many id_attrs related by id->O_OIDA[R105]->O_ATTR[R105]
    .for each id_attr in id_attrs
${id_attr.Name}\
      .if (not_last id_attrs)
, \
      .else
</p>
      .end if
    .end for
  .end for
.end function
.//
.//========================================================================
.// Get Attribute Data
.//========================================================================
.function GetAttributeData
  .param inst_ref object
  .select many attrs related by object->O_ATTR[R102]
  .for each attr in attrs
<h4>
    .// only gen cross-ref if identified as used as cross-reference
    .select any cross from instances of D_CROSS where ( selected.ID == "${object.Name}.${attr.Name}" )
    .if ( not_empty cross )
<a name="${cross.Text}">
    .end if
${object.Name}.${attr.Name}
</a>
</h4>
    .//
    .select any attr_typ related by attr->O_BATTR[R106]
    .if (not_empty attr_typ)
      .// Handle Base Attribute
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
    .if ( "${attr.Descrip:Full Name}" != "" )
Full Name: ${attr.Descrip:Full Name}
    .else
      .select any attr_typ related by attr->O_BATTR[R106]
      .if ( not_empty attr_typ )
        .// only whine if it is a base attribute
Full Name: ERROR - Full Name not in Model!!!
      .end if
    .end if
</p>

<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
      .select any dbattr related by attr_typ->O_DBATTR[R107]
      .if (not_empty dbattr)
Attribute Type: Derived Base Attribute
      .else
Attribute Type: Base Attribute
      .end if
</p>
      .// Data Type
      .select any data_type related by attr->S_DT[R114]
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Data Type: ${data_type.Name}
</p>
      .// Description
      .if ( "${attr.Descrip:Description}" != "" )
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Description: $tfull_descrip{attr.Descrip}
</p>
      .else
        .// don't care - some descriptions aren't there
      .end if
      .// Data Domain
      .if ( "${attr.Descrip:Data Domain}" != "" )
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Data Domain: $txmlclean{attr.Descrip:Data Domain}
</p>
      .else
        .// don't care - some data domain's aren't there
      .end if
    .else
    .// Handle Referential Attribute
      .// Attribute Type
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Attribute Type: Referential Attribute
</p>
      .// Refers to information
      .select any base_attr related by attr->O_RATTR[R106]->O_BATTR[R113]->O_ATTR[R106]
      .select any base_obj related by base_attr->O_OBJ[R102]
      .select any rel related by attr->O_RATTR[R106]->O_REF[R108]->R_RGO[R111]->R_OIR[R203]->R_REL[R201]
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
      .select any cross from instances of D_CROSS where ( selected.ID == "${base_obj.Name}.${base_attr.Name}" )
      .if ( not_empty cross )
Refers to: <a href="${cross.Target}">${base_obj.Name}.${base_attr.Name} (R${rel.Numb})</a>
      .else
        .// ERROR should have been set in SetUpCrossReferences
        .print "ERROR: Cross-reference doesn't exist for Attribute: ${base_obj.Name}.${base_attr.Name}"
        .exit 100
      .end if
</p>
    .end if
  .end for  .// each object attribute
.end function
.//
.//========================================================================
.// Get Heading 3 for Relationship
.//========================================================================
.function GetHeading3ForRelationship
  .param inst_ref rel
<h3>
.//<a name="a_${info.unique_num}"></a>
<a name="a_R${rel.Numb}"></a>
R${rel.Numb}
</h3>
.end function
.//
.//========================================================================
.// Get Relationship Data for Simple Relationship
.//========================================================================
.function GetRelationshipDataForSimpleRelationship
  .param inst_ref simp
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Relationship Type: Simple
</p>
  .// multiplicity and conditionality
  .select any form related by simp->R_FORM[R208]
  .assign fmult = "M"
  .if (form.Mult == 0)
    .assign fmult = "1"
  .end if
  .assign fcond = "c"
  .if (form.Cond == 0)
    .assign fcond = ""
  .end if
  .select any part related by simp->R_PART[R207]
  .assign pmult = "M"
  .if (part.Mult == 0)
    .assign pmult = "1"
  .end if
  .assign pcond = "c"
  .if (part.Cond == 0)
    .assign pcond = ""
  .end if   
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Multiplicity/Conditionality: ${pmult}${pcond}: ${fmult}${fcond}
</p>
  .// text phrases
  .select any rtoObj related by part->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select any rgoObj related by form->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
${rtoObj.Name} <em>${form.Txt_Phrs}</em> ${rgoObj.Name}
</p>
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
${rgoObj.Name} <em>${part.Txt_Phrs} ${rtoObj.Name}
</p>
  .// Formalization
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Formalized By: \
  .select many id_attrs related by form->R_RGO[R205]->O_REF[R111]->O_RTIDA[R111]->O_OIDA[R110]->O_ATTR[R105]
  .for each id_attr in id_attrs
${rtoObj.Name}.${id_attr.Name}\
    .if (not_last id_attrs)
, \
    .else

    .end if
  .end for
</p>
.end function
.//
.//========================================================================
.// Get Relationship Data For Subtype Supertype Relationship
.//========================================================================
.function GetRelationshipDataForSubtypeSupertypeRelationship
  .param inst_ref subsup
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Relationship Type: Subtype/Supertype
</p>
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Subtypes: \
  .select many subs related by subsup->R_SUB[R213]
  .for each sub in subs
    .select one subObj related by sub->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
${subObj.Name}(${subObj.Key_Lett})\
    .if (not_last subs)
, \
    .else

    .end if
  .end for
</p>
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Formalized By: \
  .select one rtoObj related by subsup->R_SUPER[R212]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select many id_attrs related by subsup->R_SUB[R213]->R_RGO[R205]->O_REF[R111]->O_RTIDA[R111]->O_OIDA[R110]->O_ATTR[R105]
  .for each id_attr in id_attrs
${rtoObj.Name}.${id_attr.Name}\
    .if (not_last id_attrs)
, \
    .else

    .end if
  .end for
</p>
.end function
.//
.//========================================================================
.// Get Relationship Data For Associative Relationship
.//========================================================================
.function GetRelationshipDataForAssociativeRelationship
  .param inst_ref assoc
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Relationship Type: Associative
</p>
  .select any assr related by assoc->R_ASSR[R211]
  .assign mult = "M"
  .if (assr.Mult == 0)
    .assign mult = "1"
  .end if
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Multiplicity/Conditionality: ${mult}-(\
  .select any aone related by assoc->R_AONE[R209]
  .assign onemult = "M"
  .if (aone.Mult == 0)
    .assign onemult = "1"
  .end if
  .assign onecond = "c"
  .if (aone.Cond == 0)
    .assign onecond = ""
  .end if
${onemult}${onecond}:\
  .select any aoth related by assoc->R_AOTH[R210]
  .assign othermult = "M"
  .if (aoth.Mult == 0)
    .assign othermult = "1"
  .end if
  .assign othercond = "c"
  .if (aoth.Cond == 0)
    .assign othercond = ""
  .end if   
${othermult}${othercond})
</p>
  .select any oneObj related by aone->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select any othObj related by aoth->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select any assrObj related by assr->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
${oneObj.Name} <em>${aoth.Txt_Phrs}</em> ${othObj.Name}
</p>
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
${othObj.Name} <em>${aone.Txt_Phrs}</em> ${oneObj.Name}
</p>
<p style="margin-left: 40px;">
<a name="a_${info.unique_num}"></a>
Formalized By: \
  .select many id_attrs related by assr->R_RGO[R205]->O_REF[R111]->O_RTIDA[R111]->O_OIDA[R110]->O_ATTR[R105]
  .for each id_attr in id_attrs
${assrObj.Name}.${id_attr.Name}\
    .if (not_last id_attrs)
, \
    .else

    .end if
  .end for
</p>
.end function
.//
.//========================================================================
.// MAIN Body
.//========================================================================
.assign section = "3"
.invoke SetUpCrossReferences()
.// Do each of the subsystems
.select many subsystems from instances of S_SS
.//assign ss_max = cardinality subsystems
.assign ss_max = 4
.assign cur_ss_name = ""
.assign ss_count = 0
.while (ss_count < ss_max)
  .assign ss_count = ss_count + 1
  .if (cur_ss_name == "")
    .assign cur_ss_name = "Domain"
  .elif (cur_ss_name == "Domain")
    .assign cur_ss_name = "Subsystem"
  .elif (cur_ss_name == "Subsystem")
    .assign cur_ss_name = "Association"
  .elif (cur_ss_name == "Association")
    .assign cur_ss_name = "State_Machine"
  .else
    .print "Subsystem '${cur_ss_name}' missing from specified order"
  .end if
  .// Header
  .invoke header = Header(cur_ss_name)
${header.body}
  .select any subsystem from instances of S_SS where ("$l_{selected.Name}" == "$l_{cur_ss_name}")
  .invoke heading1 = GetHeading1ForSubsystem( subsystem, section, ss_count )
${heading1.body}\
  .invoke description = GetDescriptionForEntity( subsystem )
  .if ( description.result )
${description.body}\
  .end if
  .// Links to Classes and Associations
  .invoke heading2 = GetHeading2( section, ss_count, "Links to Classes" )
${heading2.body}\
  .// Classes
  .select many object_set related by subsystem->O_OBJ[R2]
  .if (not_empty object_set)
    .// sort
    .invoke SortSetAlphabeticallyByNameAttr(object_set)
    .assign item_count = cardinality object_set
    .assign item_number = 0
    .while ( item_number < item_count )
      .for each object in object_set
        .if ( object.Order == item_number )
          .assign item_number = item_number + 1
<p>${item_number}. <a href="#a_${object.Numb}.$r{object.Name}">${object.Name} (${object.Key_Lett})</a></p>
        .end if
      .end for
    .end while
</p>
  .end if
  .invoke heading2 = GetHeading2( section, ss_count, "Links to Associations" )
${heading2.body}\
.// Associations
  .select many rels related by subsystem->R_REL[R4]
  .if (not_empty rels)
    .// sort
    .assign max_rel_numb = 0
    .select any rel related by subsystem->R_REL[R4]
    .assign min_rel_numb = rel.Numb
    .for each rel in rels
      .if (rel.Numb > max_rel_numb)
        .assign max_rel_numb = rel.Numb
      .end if
      .if (rel.Numb < min_rel_numb)
        .assign min_rel_numb = rel.Numb
      .end if
    .end for
    .// Put out the links for associations
    .assign rel_numb = min_rel_numb
    .assign count = 0;
<p>
    .while(rel_numb <= max_rel_numb)
      .select any rel from instances of R_REL where (selected.Numb == rel_numb)
      .if (not_empty rel)
<a href="#a_R${rel.Numb}">R${rel.Numb}</a>\
        .if (count > 16)
          .assign count = 0;
</p><p>
        .elif (rel_numb == max_rel_numb)
</p>
        .else
          .assign count = count + 1;
, \
        .end if
      .end if
      .assign rel_numb = rel_numb + 1
    .end while
  .end if
  .// Objects
  .select many objects related by subsystem->O_OBJ[R2]
  .if (not_empty objects)
    .// sort
    .assign max_obj_numb = 0
    .select any obj related by subsystem->O_OBJ[R2]
    .assign min_obj_numb = obj.Numb
    .for each obj in objects
      .if (obj.Numb > max_obj_numb)
        .assign max_obj_numb = obj.Numb
      .end if
      .if (obj.Numb < min_obj_numb)
        .assign min_obj_numb = obj.Numb
      .end if
    .end for
  .invoke heading2 = GetHeading2( section, ss_count, "Class and Attribute Descriptions" )
${heading2.body}\
    .assign obj_numb = min_obj_numb
    .while(obj_numb <= max_obj_numb)
      .select any object from instances of O_OBJ where (selected.Numb == obj_numb)
      .if (not_empty object)
        .invoke heading3 = GetHeading3ForObject( object )
${heading3.body}\
        .invoke object_data = GetObjectData( object )
${object_data.body}\
        .invoke object_description = GetDescriptionForEntityIndent( object )
        .if ( object_description.result )
${object_description.body}\
        .end if
        .invoke attribute_data = GetAttributeData( object )
${attribute_data.body}\
      .end if .// not empty object with number obj_numb
      .assign obj_numb = obj_numb + 1
    .end while .// obj_numb is less than max_obj_numb
  .end if .// not empty objects
.// Relationships
  .select many rels related by subsystem->R_REL[R4]
  .if (not_empty rels)
    .// sort
    .assign max_rel_numb = 0
    .select any rel related by subsystem->R_REL[R4]
    .assign min_rel_numb = rel.Numb
    .for each rel in rels
      .if (rel.Numb > max_rel_numb)
        .assign max_rel_numb = rel.Numb
      .end if
      .if (rel.Numb < min_rel_numb)
        .assign min_rel_numb = rel.Numb
      .end if
    .end for
    .invoke heading2 = GetHeading2( section, ss_count, "Association Descriptions" )
${heading2.body}\
    .assign rel_numb = min_rel_numb
    .while(rel_numb <= max_rel_numb)
      .select any rel from instances of R_REL where (selected.Numb == rel_numb)
      .if (not_empty rel)
        .invoke heading3 = GetHeading3ForRelationship( rel )
${heading3.body}\
        .// R_SIMP
        .select any simp related by rel->R_SIMP[R206]
        .if (not_empty simp)
          .invoke rel_data = GetRelationshipDataForSimpleRelationship( simp )
${rel_data.body}\
        .end if
        .// R_SUBSUP
        .select any subsup related by rel->R_SUBSUP[R206]
        .if (not_empty subsup)
          .invoke rel_data = GetRelationshipDataForSubtypeSupertypeRelationship( subsup )
${rel_data.body}\
        .end if
        .// R_ASSOC
        .select any assoc related by rel->R_ASSOC[R206]
        .if (not_empty assoc)
         .invoke rel_data = GetRelationshipDataForAssociativeRelationship( assoc )
${rel_data.body}\
        .end if
      .end if
      .assign rel_numb = rel_numb + 1
    .end while
  .end if  .// not_empty rels
.// Footer
.invoke footer = Footer()
${footer.body}
.select any domain from instances of S_DOM
.print "${cur_ss_name}.htm"
.emit to file "HTML/${cur_ss_name}.htm"
.end while
.// topics_Reference_Metamodel.xml
.select many subsystems from instances of S_SS
.//assign ss_max = cardinality subsystems
.assign ss_max = 4
.assign cur_ss_name = ""
.assign ss_count = 0
    <topic label="Metamodel Reference">
.while (ss_count < ss_max)
  .assign ss_count = ss_count + 1
  .if (cur_ss_name == "")
    .assign cur_ss_name = "Domain"
  .elif (cur_ss_name == "Domain")
    .assign cur_ss_name = "Subsystem"
  .elif (cur_ss_name == "Subsystem")
    .assign cur_ss_name = "Association"
  .elif (cur_ss_name == "Association")
    .assign cur_ss_name = "State_Machine"
  .else
    .print "Subsystem missing from specified order"
  .end if
      <topic label="${cur_ss_name}" href="Reference/Metamodel/HTML/${cur_ss_name}.htm"/>
.end while
    </topic>
.emit to file "HTML/topics_Reference_Metamodel.xml"
.// emit files to be spell checked
.clear
.select many classes from instances of O_OBJ
.select many attributes from instances of O_ATTR
.for each class in classes
${class.Name}
${class.Descrip}
.end for
.for each attribute in attributes
  .select one class related by attribute->O_OBJ[R102]
${class.Name}::${attribute.Name}
${attribute.Descrip}
.end for
.emit to file "HTML/spell_check.txt"
