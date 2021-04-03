.function attr_is_persistent
  .param inst_ref attr   .// O_ATTR
  .//
  .select one type related by attr->S_DT[R114]
  .assign attr_result = ("$l{attr.Descrip:Persistent}" != "false") and (type.Name != "state<State_Model>")
.end function
.function is_single_unique_id
  .param inst_ref attr   .// O_ATTR
  .//
  .assign attr_result = false
  .select one class related by attr->O_OBJ[R102]
  .select any id related by class->O_ID[R104] where (selected.Oid_ID == 0)
  .select many attrs related by id->O_OIDA[R105]->O_ATTR[R105]
  .if((cardinality attrs) == 1)
    .select any idAttr related by id->O_OIDA[R105]->O_ATTR[R105]
    .if(idAttr == attr)
      .select one type related by attr->S_DT[R114]
      .select one refAttr related by attr->O_RATTR[R106]
      .if(not_empty refAttr)
        .select one baseAttr related by refAttr->O_BATTR[R113]->O_ATTR[R106]
        .select one type related by baseAttr->S_DT[R114]
      .end if
      .if(type.Name == "unique_id")
        .assign attr_result = true
      .end if
    .end if
  .end if
.end function