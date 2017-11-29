.function getNullValueForType
  .param inst_ref type
  .assign attr_null_value = "null"
  .if ( not_empty type )
    .select one edt related by type->S_EDT[R17]
    .if ( not_empty edt )
      .invoke edt_name = get_enumeration_class_name( type )
      .invoke default = GetEnumElementDefaultValueInfoString()
      .assign attr_null_value = "${edt_name.body}.${default.name}"
    .else
      .select one udt related by type->S_UDT[R17]
      .if ( not_empty udt )
        .select one type related by udt->S_DT[R18]
      .end if
      .if ( "unique_id" == type.Name )
        .assign attr_null_value = "IdAssigner.NULL_UUID"
      .end if
      .if ( "integer" == type.Name ) 
        .assign attr_null_value = "0"
      .end if
      .if ( "real" == type.Name )
        .assign attr_null_value = "0f"
      .end if
      .if ( "boolean" == type.Name )
        .assign attr_null_value = "false"
      .end if
      .if ( ( "string" == type.Name ) or ( "timestamp" == type.Name ) )
        .assign attr_null_value = """"""
      .end if
    .end if
  .end if
.end function
.//
.//
.function getRGOSelection
  .param inst_ref r_rgo
  .param inst_ref r_rto
  .param integer rgo_counter
  .assign attr_instance_name = ""
  .assign mult = "one"
  .assign role_phrase = ""
  .select one r_form related by r_rgo->R_FORM[R205]
  .if ( not_empty r_form )
    .if ( 1 == r_form.Mult )
      .assign mult = "many"
    .end if
    .if ( r_rgo.Obj_ID == r_rto.Obj_ID )
      .assign role_phrase = $cr{r_form.Txt_Phrs}
    .end if
  .end if
  .select one r_assr related by r_rgo->R_ASSR[R205]
  .if ( not_empty r_assr )
    .if ( 1 == r_assr.Mult )
      .assign mult = "many"
    .end if
    .select one r_aone related by r_assr->R_ASSOC[R211]->R_AONE[R209]
    .select one r_aoth related by r_assr->R_ASSOC[R211]->R_AOTH[R210]
    .if ( r_aone.OIR_ID != r_rto.OIR_ID )
      .if ( 1 == r_aone.Mult )
        .assign mult = "many"
      .end if
      .if ( r_aone.Obj_ID == r_aoth.Obj_ID )
        .assign role_phrase = $cr{r_aone.Txt_Phrs}
      .end if
    .end if
    .if ( r_aoth.OIR_ID != r_rto.OIR_ID )
      .if ( 1 == r_aoth.Mult )
        .assign mult = "many"
      .end if
      .if ( r_aone.Obj_ID == r_aoth.Obj_ID )
        .assign role_phrase = $cr{r_aoth.Txt_Phrs}
      .end if
    .end if
  .end if
  .select one rgo_obj related by r_rgo->R_OIR[R203]->O_OBJ[R201]
  .select one rgo_rel related by r_rgo->R_OIR[R203]->R_REL[R201]
  .invoke rgo_class_name = get_class_name( rgo_obj )
  .if ( "one" == mult )
    .assign attr_instance_name = "rgo${rgo_counter}"
                ${rgo_class_name.body} rgo${rgo_counter} = ${rgo_class_name.body}.getOne${rgo_obj.Key_Lett}OnR${rgo_rel.Numb}${role_phrase}( me );
                if ( null != rgo${rgo_counter} ) {
  .elif ( "many" == mult )
    .assign attr_instance_name = "rgo"
                ${rgo_class_name.body}[] rgos${rgo_counter} = ${rgo_class_name.body}.getMany${rgo_obj.Key_Lett}sOnR${rgo_rel.Numb}${role_phrase}( me );
                for ( ${rgo_class_name.body} rgo : rgos${rgo_counter} ) {
  .end if
.end function
.//
.//
.function genReferentialAttributeUtilClass
  .param string class_name
public class ${class_name} implements IReferentialAttributeManager {

    private static IReferentialAttributeManager defaultInstance = null;

    private ${class_name}() {
        super();
    }

    public static IReferentialAttributeManager getInstance() {
        if ( null == defaultInstance ) defaultInstance = new ${class_name}();
        return defaultInstance;
    }

    @Override
    public void generateUUIDs( NonRootModelElement element ) {
  .select many o_objs from instances of O_OBJ
  .assign test = "if"
  .for each o_obj in o_objs
    .invoke class_name = get_class_name( o_obj )
    .// select all identifying base attributes
    .select many id_attrs related by o_obj->O_ATTR[R102]->O_OIDA[R105]->O_ATTR[R105]->O_BATTR[R106]->O_ATTR[R106]
    .select any unique_id_attr related by id_attrs->S_DT[R114] where ( "unique_id" == selected.Name )
    .if ( not_empty unique_id_attr )
        ${test} ( element instanceof ${class_name.body} ) {
            ${class_name.body} me = (${class_name.body})element;
      .for each id_attr in id_attrs
        .select one attr_dt related by id_attr->S_DT[R114]
        .// only generate UUIDs for attributes of type 'unique_id'
        .if ( "unique_id" == attr_dt.Name )
            me.set$cr{id_attr.Name}( me.getIdAssigner().createUUID() );
        .end if
      .end for
        }
      .assign test = "else if"
    .end if
  .end for
    }

    @Override
    public void propagateIDs(NonRootModelElement element) {
        internalPropagateIDs(element, new HashSet<>());
    }
    
    private void internalPropagateIDs( NonRootModelElement element, Set<NonRootModelElement> visited ) {
        if ( !visited.contains( element ) ) {
            visited.add( element );
  .assign test = "if"
  .for each o_obj in o_objs
    .invoke class_name = get_class_name( o_obj )
    .select any r_rgo related by o_obj->O_ID[R104]->O_OIDA[R105]->O_RTIDA[R110]->O_REF[R111]->R_RGO[R111]
    .if ( not_empty r_rgo )
            ${test} ( element instanceof ${class_name.body} ) {
                ${class_name.body} me = (${class_name.body})element;
      .select many r_rgos related by o_obj->O_ID[R104]->O_OIDA[R105]->O_RTIDA[R110]->O_REF[R111]->R_RGO[R111]
      .// propagate each referred to identifying attribute
      .assign rgo_counter = 1
      .for each r_rgo in r_rgos
        .select one rgo_rel related by r_rgo->R_OIR[R203]->R_REL[R201]
        .select one r_rto related by r_rgo->O_REF[R111]->O_RTIDA[R111]->R_RTO[R110] where ( ( selected.Obj_ID == o_obj.Obj_ID ) and ( selected.Rel_ID == rgo_rel.Rel_ID ) )
        .invoke rgo_selection = getRGOSelection( r_rgo, r_rto, rgo_counter )
${rgo_selection.body}
        .select many o_rtidas related by r_rgo->O_REF[R111]->O_RTIDA[R111] where ( ( selected.Obj_ID == o_obj.Obj_ID ) and ( selected.Rel_ID == rgo_rel.Rel_ID ) )
        .for each o_rtida in o_rtidas
          .select one o_attr related by o_rtida->O_OIDA[R110]->O_ATTR[R105]
          .select any rgo_ref related by r_rgo->O_REF[R111] where ( selected.RAttr_ID == o_rtida.Attr_ID )
          .select one rgo_ref_attr related by rgo_ref->O_RATTR[R108]->O_ATTR[R106]
                    ${rgo_selection.instance_name}.set$cr{rgo_ref_attr.Name}( me.get$cr{o_attr.Name}() );
        .end for
                    internalPropagateIDs( ${rgo_selection.instance_name}, visited );
                }
        .assign rgo_counter = rgo_counter + 1
      .end for
            }
      .assign test = "else if"
    .end if
  .end for
        }
    }

    @Override
    public void clearReferentialAttributes(NonRootModelElement element) {
  .assign test = "if"
  .for each o_obj in o_objs
    .invoke class_name = get_class_name( o_obj )
    .// select all referential attributes
    .select many ref_attrs related by o_obj->O_ATTR[R102]->O_RATTR[R106]->O_ATTR[R106]
    .if ( not_empty ref_attrs )
        ${test} ( element instanceof ${class_name.body} ) {
            ${class_name.body} me = (${class_name.body})element;
      .for each ref_attr in ref_attrs
        .select one attr_dt related by ref_attr->S_DT[R114]
        .if ( "same_as<Base_Attribute>" == attr_dt.Name )
          .select one attr_dt related by ref_attr->O_RATTR[R106]->O_BATTR[R113]->O_ATTR[R106]->S_DT[R114]
        .end if
        .invoke null_value = getNullValueForType( attr_dt )
            me.set$cr{ref_attr.Name}( ${null_value.null_value} );
      .end for
        }
      .assign test = "else if"
    .end if
  .end for
    }

}
.end function
