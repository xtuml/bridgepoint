.//====================================================================
.//
.// File:      $RCSfile: referring.inc,v $
.// Version:   $Revision: 1.41.26.1 $
.// Modified:  $Date: 2013/07/19 12:26:00 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.function output_referential_setter
  .param boolean is_reflexive
  .param inst_ref gen_rel   .// R_REL
  .param inst_ref ario      .// O_REF
  .param string text_phrase
  .//
  .assign attr_result = false
  .if ( is_reflexive )
    .select one assoc related by gen_rel->R_ASSOC[R206]
    .if ( not_empty assoc )
      .select one rto related by ario->O_RTIDA[R111]->R_RTO[R110]
      .select one aone related by rto->R_AONE[R204]
      .if ( not_empty aone )
        .if ( aone.Txt_Phrs == text_phrase) )
          .assign attr_result = true
        .end if
      .else
        .select one aoth related by rto->R_AOTH[R204]
        .if ( not_empty aoth )
          .if ( aoth.Txt_Phrs == text_phrase) )
            .assign attr_result = true
          .end if
        .end if
      .end if
    .else
      .assign attr_result = true
    .end if
  .else
    .assign attr_result = true
  .end if
.end function
.//
.function declare_canonical_nav_path
  .param inst_ref loc_obj
  .param string loc_txt_phrs
  .param integer loc_mult
  .param inst_ref tar_obj
  .param string tar_txt_phrs
  .param integer tar_mult
  .param inst_ref gen_rel
  .param frag_ref package
.//
  .invoke package = get_package()
  .invoke tocn = get_class_name( tar_obj )
  .invoke locn = get_class_name( loc_obj )
  .invoke ref_var_name = get_referential_var_name( tar_obj, tar_txt_phrs )
  .invoke is_refl = is_reflexive( gen_rel )
  .assign func_suffix = ""
  .assign backPointSuffix = ""
  .if ( is_refl.result )
    .assign func_suffix = "$cr{tar_txt_phrs}"
    .assign backPointSuffix = "$cr{loc_txt_phrs}"
  .end if
  .//

  .invoke l = is_persistent(loc_obj)
  .invoke t = is_persistent(tar_obj)
  .assign persistent = ((l.result) and (t.result))
    
  .assign crossComp=false
  .if(persistent)
    .invoke cc=isCrossComponentAssociation(loc_obj,tar_obj,gen_rel)
    .assign crossComp=cc.result
  .end if
// referring navigation

${tocn.body} ${ref_var_name.body} ;
public void relateAcrossR${gen_rel.Numb}To${func_suffix}(${tocn.body} target)
{
	relateAcrossR${gen_rel.Numb}To${func_suffix}(target, true);
}
public void relateAcrossR${gen_rel.Numb}To${func_suffix}(${tocn.body} target, boolean notifyChanges)
{
	if (target == null) return;
	
	if (target == ${ref_var_name.body}) return;  // already related

    if ( ${ref_var_name.body} != target ) {
    
    Object oldKey = getInstanceKey();

    if (${ref_var_name.body} != null) {
.if ( package.is_eclipse_plugin )
      .if ((persistent) and (crossComp))
        UmlProblem.removeXtUMLProblem(this, ${ref_var_name.body});
      .end if  
.end if
    
        ${ref_var_name.body}.clearBackPointerR${gen_rel.Numb}To${backPointSuffix}(this);
        
		if (Boolean.valueOf(System.getenv("PTC_MCC_ENABLED")) == true) {   //$$NON-NLS-1$$
			.invoke className = get_class_name(loc_obj)
		${package.application_root_class}.log.println(ILogger.CONSISTENCY, 
			"${className.body}.relateAcrossR${gen_rel.Numb}To${func_suffix}(${tocn.body} target)", 
			"Relate performed across R${gen_rel.Numb} from ${loc_obj.Name} to ${tar_obj.Name} without unrelate of prior instance.");  
		}
    }
                
    	${ref_var_name.body} = target ;
    .assign tempCount = 0
    .select many ario_set related by loc_obj->R_OIR[R201]->R_RGO[R203]->O_REF[R111] where ( selected.Rel_ID == gen_rel.Rel_ID )
    .for each ario in ario_set
      .if ( ario.RObj_ID == tar_obj.Obj_ID )
        .invoke output_code = output_referential_setter(is_refl.result, gen_rel, ario, tar_txt_phrs)
        .if ( output_code.result )
          .select one ref_attr related by ario->O_RATTR[R108]->O_ATTR[R106]
          .select one b_attr related by ario->O_RTIDA[R111]->O_OIDA[R110]->O_ATTR[R105]
          .invoke an = get_attribute_name(ref_attr)
          .invoke aa = get_attribute_accessor(b_attr)
          .select one r_attr related by b_attr->O_RATTR[R106]
          .if(not_empty r_attr)
            .assign default_value = "${ref_attr.Descrip:Not_participating_value}"
            .invoke result = get_base_attribute_type(ref_attr)
            .assign orig_type = result.type
            .if (orig_type.Name == "string")
        if(target.${aa.body}().equals("${default_value}"))
            .elif ( orig_type.Name == "unique_id" )
        if(IdAssigner.NULL_UUID.equals(target.${aa.body}()))
            .elif ( default_value != "" )
        if(target.${aa.body}() == ${default_value})
            .else
        if(target.${aa.body}() == 0)
            .end if
        {
            // do not update cached value
        } else {
          // update cached value
          ${an.body} = target.${aa.body}CachedValue();
        }
          .else
            .// the base attribute is not a referential
            .// update with the non-cached value
        ${an.body} = target.${aa.body}();
          .end if
        .end if
      .end if
    .end for
    .// update the key for the hash map
      updateInstanceKey(oldKey, getInstanceKey());
	    target.setBackPointerR${gen_rel.Numb}To${backPointSuffix}(this);
.if ( package.is_eclipse_plugin )
        target.addRef();
.end if
.invoke prop_delta_domain = notifies_changes(loc_obj, "O_OBJ")
.invoke assocNotifiesChanges = notifies_changes(gen_rel, "R_REL")
.if ( ((package.is_eclipse_plugin) and (prop_delta_domain.result)) and (assocNotifiesChanges.result))
      .if ((persistent) and (crossComp))
        UmlProblem.handleDanglingReference(this, target);
      .end if 
      if(notifyChanges) {
	    	RelationshipChangeModelDelta change = new RelationshipChangeModelDelta(Modeleventnotification_c.DELTA_ELEMENT_RELATED, this, target, "${gen_rel.Numb}", "${func_suffix}");
        ${package.application_root_class}.getDefaultInstance().fireModelElementRelationChanged(change);
      }
.end if
    }
}
public void unrelateAcrossR${gen_rel.Numb}From${func_suffix}(${tocn.body} target)
{
	unrelateAcrossR${gen_rel.Numb}From${func_suffix}(target, true);
}
public void unrelateAcrossR${gen_rel.Numb}From${func_suffix}(${tocn.body} target, boolean notifyChanges)
{
	if (target == null) return;
	
	if (${ref_var_name.body} == null) return;  // already unrelated
	
	if (target != ${ref_var_name.body}) {
.if ( package.is_eclipse_plugin )
		Exception e = new Exception();
		e.fillInStackTrace();
		${package.plugin_classname}.logError("Tried to unrelate from non-related instance across R${gen_rel.Numb}", e);
.end if
		return;
	}

    if (target != null) {
        target.clearBackPointerR${gen_rel.Numb}To${backPointSuffix}(this);
    }
    
.invoke prop_delta_domain = notifies_changes(loc_obj, "O_OBJ")
.//

      if(${ref_var_name.body} != null) {
      
.select many ario_set related by loc_obj->R_OIR[R201]->R_RGO[R203]->O_REF[R111] where ( selected.Rel_ID == gen_rel.Rel_ID )
.for each ario in ario_set
  .if ( ario.RObj_ID == tar_obj.Obj_ID )
    .select one ref_attr related by ario->O_RATTR[R108]->O_ATTR[R106]
    .select one b_attr related by ario->O_RTIDA[R111]->O_OIDA[R110]->O_ATTR[R105]
    .invoke attribute_name = get_attribute_name(ref_attr)
    .invoke attribute_accessor = get_attribute_accessor(b_attr)
    .select one r_attr related by b_attr->O_RATTR[R106]
        ${attribute_name.body} = ${ref_var_name.body}.${attribute_accessor.body}();
    .if(not_empty r_attr)
        .assign default_value = "${ref_attr.Descrip:Not_participating_value}"
        .invoke result = get_base_attribute_type(ref_attr)
        .assign orig_type = result.type
        .if (orig_type.Name == "string")
        if(${attribute_name.body}.equals("${default_value}"))
        .elif ( orig_type.Name == "unique_id" )
        if(IdAssigner.NULL_UUID.equals(${attribute_name.body}))
        .elif ( default_value != "" )
        if(${attribute_name.body} == ${default_value})
        .else
        if(${attribute_name.body} == 0)
        .end if
        {
          ${attribute_name.body} = ${ref_var_name.body}.${attribute_accessor.body}CachedValue();
        }       
    .end if
  .end if
.end for
.//
        ${ref_var_name.body} = null ;
.if ( package.is_eclipse_plugin )
        target.removeRef();
.end if
.invoke prop_delta_domain = notifies_changes(loc_obj, "O_OBJ")
.invoke assocNotifiesChanges = notifies_changes(gen_rel, "R_REL")
.if (((package.is_eclipse_plugin) AND (prop_delta_domain.result)) and (assocNotifiesChanges.result))
	  .if ((persistent) and (crossComp))
        UmlProblem.removeXtUMLProblem(this, target);
      .end if
      if(notifyChanges) { 
        RelationshipChangeModelDelta change = new RelationshipChangeModelDelta(Modeleventnotification_c.DELTA_ELEMENT_UNRELATED, this, target, "${gen_rel.Numb}", "${func_suffix}");
        ${package.application_root_class}.getDefaultInstance().fireModelElementRelationChanged(change);
      }
.end if
    }
}
.end function
.//
.function declare_canonical_reference
  .param inst_ref rto
  .param inst_ref loc_obj
  .param string loc_txt_phrs
  .param integer loc_mult
  .param inst_ref tar_obj
  .param string tar_txt_phrs
  .param integer tar_mult
  .param inst_ref gen_rel
  .param frag_ref package
  .//
  .invoke tocn = get_class_name( tar_obj )
  .invoke locn = get_class_name( loc_obj )
  .invoke ref_var_name = get_referential_var_name( tar_obj, tar_txt_phrs )
  .//
  .invoke is_refl = is_reflexive( gen_rel )
  .assign func_suffix = ""
  .if ( is_refl.result )
    .assign func_suffix = "$cr{loc_txt_phrs}"
    .select one arel related by gen_rel->R_ASSOC[R206]
    .if ( not_empty arel )
      .// associative reflexive uses the other phrase
      .invoke ref_var_name = get_referential_var_name( tar_obj, loc_txt_phrs )
    .end if
  .end if
  .//
  .select one assoc_rel related by gen_rel->R_ASSOC[R206]
  .assign target_is_one = ((loc_mult == 0) and (empty assoc_rel))
  .invoke loc_ref_var_name = get_referential_var_name( loc_obj, loc_txt_phrs )
  .assign backPointerName = "backPointer_${loc_ref_var_name.body}$cr{loc_txt_phrs}_R${gen_rel.Numb}"
  .//
  .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "one" )
  .invoke isContainment = isContainmentAssociation(gen_rel)
  
  .invoke l = is_persistent(loc_obj)
  .invoke t = is_persistent(tar_obj)
  .assign persistent = ((l.result) and (t.result))
    
  .assign crossComp=false
  .if(persistent)
    .invoke cc=isCrossComponentAssociation(loc_obj,tar_obj,gen_rel)
    .assign crossComp=cc.result
  .end if
      
public static ${locn.body} ${nfn.body}${func_suffix}(${tocn.body} [] targets)
{
    return ${nfn.body}${func_suffix}(targets, null);
}

  .invoke gtcn = get_test_class_name()
public static ${locn.body} ${nfn.body}${func_suffix}(${tocn.body} [] targets, ${gtcn.body} test)
{
    ${locn.body} ret_val = null;
    if (targets != null)
    {
        for (int i = 0; i < targets.length && ret_val == null; ++i )
        {
           ret_val = ${nfn.body}${func_suffix}(targets[i], test);
        }
    }

    return ret_val;
}

public static ${locn.body} ${nfn.body}${func_suffix}(${tocn.body} target)
{
  return ${nfn.body}${func_suffix}(target, null);
}

public static ${locn.body} ${nfn.body}${func_suffix}(${tocn.body} target, boolean loadComponent)
{
  .if ( "$l{loc_obj.Descrip:PEI}" == "true" )
      return ${nfn.body}${func_suffix}(${package.application_root_class}.getDefaultInstance(), target, null, loadComponent);
  .else  
    return ${nfn.body}${func_suffix}(target.getModelRoot(), target, null, loadComponent);
  .end if
}

public static ${locn.body} ${nfn.body}${func_suffix}(${tocn.body} target, ${gtcn.body} test)
{
  if (target != null) {
  .if ( "$l{loc_obj.Descrip:PEI}" == "true" )
    return ${nfn.body}${func_suffix}(${package.application_root_class}.getDefaultInstance(), target, test);
  .else  
    return ${nfn.body}${func_suffix}(target.getModelRoot(), target, test);
  .end if
  }
  return null;
}

public static ${locn.body} ${nfn.body}${func_suffix}(ModelRoot modelRoot, ${tocn.body} target, ${gtcn.body} test)
{
	return ${nfn.body}${func_suffix}(modelRoot, target, test, true);
}

public static ${locn.body} ${nfn.body}${func_suffix}(ModelRoot modelRoot, ${tocn.body} target, ${gtcn.body} test, boolean loadComponent)
{
  .if ((persistent) and (crossComp))
  ${locn.body} result = null;
  
  result=find_${nfn.body}${func_suffix}(modelRoot,target,test);
  if (result==null && (loadComponent)) {
  .if(isContainment.result))
     // Containment Relation
     List comps =  PersistenceManager.findAllChildComponents(target.getPersistableComponent(),modelRoot,${locn.body}.class);
  .else
     // not a Containment Relation
     List comps =  PersistenceManager.findAllComponents(modelRoot,${locn.body}.class);
  .end if
    for (int i = 0; i < comps.size(); i++) {
      PersistableModelComponent component = (PersistableModelComponent) comps.get(i);
      if (!component.isLoaded()) {
        try {
          component.load(new NullProgressMonitor());
          result=find_${nfn.body}${func_suffix}(modelRoot,target,test);
          if(result!=null) return result;
        } catch (Exception e) {
          CorePlugin.logError("Error Loading component", e);
        }
      }
    }
  }
  
  return result;
  .else
   return find_${nfn.body}${func_suffix}(modelRoot,target,test);
  .end if
}
private static ${locn.body} find_${nfn.body}${func_suffix}(ModelRoot modelRoot, ${tocn.body} target, ${gtcn.body} test)
{
  if (target != null) {
  .if ( target_is_one )
    ${locn.body} source = (${locn.body})target.${backPointerName};
    if (source != null && (test == null || test.evaluate(source))) {
      return source;
    }
  .else
    synchronized(target.${backPointerName}) {
	    for ( int i = 0; i < target.${backPointerName}.size(); ++i ) {
	      ${locn.body} source = (${locn.body})target.${backPointerName}.get(i) ;
	      if (source != null && (test == null || test.evaluate(source))) {
	        return source ;
      }
    }
  }
  .end if
  }
  // not found
  return null ;
}

  .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "many" )
public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} [] targets)
{
  return ${nfn.body}${func_suffix}(targets, null);
}
public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} [] targets, boolean loadComponent)
{
  return ${nfn.body}${func_suffix}(targets, null, loadComponent);
}
public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} [] targets, ${gtcn.body} test)
{
	return ${nfn.body}${func_suffix}(targets, test, true);
}

public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} [] targets, ${gtcn.body} test, boolean loadComponent)
{

  if(targets == null || targets.length == 0 || targets[0] == null)
  	return new ${locn.body}[0];

  .if ( "$l{loc_obj.Descrip:PEI}" == "true" )
  ModelRoot modelRoot = ${package.application_root_class}.getDefaultInstance();
  .else  
  ModelRoot modelRoot = targets[0].getModelRoot();
  .end if
  .if ((persistent) and (crossComp))
  if(loadComponent){
    .if(isContainment.result)
     //Containment Relation
     PersistableModelComponent[] pmcs= new PersistableModelComponent[targets.length];
            for (int i = 0; i < targets.length; i++) {
               pmcs[i]=targets[i].getPersistableComponent();
            }
	 PersistenceManager.ensureAllChildInstancesLoaded(pmcs,modelRoot, ${locn.body}.class);
  .else
     //not a Containment Relation
	 PersistenceManager.ensureAllInstancesLoaded(modelRoot, ${locn.body}.class);
  .end if
  
  }
  .end if
 
   InstanceList instances = modelRoot.getInstanceList(${locn.body}.class);

  Vector matches = new Vector();
  for (int i = 0 ; i < targets.length ; i++) {
  .if (target_is_one)
      ${locn.body} source = (${locn.body})targets[i].${backPointerName};
      if (source != null && (test == null || test.evaluate(source))) {
        matches.add(source);
      }
  .else
    synchronized(targets[i].${backPointerName}) {
	    for (int j = 0; j < targets[i].${backPointerName}.size(); ++j) {
	      ${locn.body} source = (${locn.body})targets[i].${backPointerName}.get(j) ;
          if (source != null && (test == null || test.evaluate(source))) {
	        matches.add(source);
    }
  }
    }
  .end if
  }
  if (matches.size() > 0) {
	${locn.body}[] ret_set = new ${locn.body}[matches.size()];
	matches.copyInto(ret_set);
	return ret_set;
  } else {
	return new ${locn.body}[0];
  }
  }

.if (loc_mult == 0)
public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} target)
{
  if (target != null) {
    ${tocn.body} [] targetArray = new ${tocn.body}[1];
    targetArray[0] = target;
    return ${nfn.body}${func_suffix}(targetArray);
  } else {
    ${locn.body} [] result = new ${locn.body} [0] ;
  return result ;
}
}

public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} target, boolean loadComponent)
{
  if (target != null) {
    ${tocn.body} [] targetArray = new ${tocn.body}[1];
    targetArray[0] = target;
    return ${nfn.body}${func_suffix}(targetArray, loadComponent);
  } else {
    ${locn.body} [] result = new ${locn.body} [0] ;
  return result ;
}
}

.end if
.if (loc_mult == 1)
  .// Multiplicity is MANY, need to return a set too
public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} target)
{
  return ${nfn.body}${func_suffix}(target, null);
}

public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} target, boolean loadComponent)
{
  return ${nfn.body}${func_suffix}(target, null, loadComponent);
}

public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} target, ${gtcn.body} test)
{
	return ${nfn.body}${func_suffix}(target, test, true);
}

public static ${locn.body} [] ${nfn.body}${func_suffix}(${tocn.body} target, ${gtcn.body} test, boolean loadComponent)
{
  if(target == null)
    return new ${locn.body}[0];    

  .if ( "$l{loc_obj.Descrip:PEI}" == "true" )
  ModelRoot modelRoot = ${package.application_root_class}.getDefaultInstance();
  .else  
  ModelRoot modelRoot = target.getModelRoot();
  .end if
  .if ((persistent) and (crossComp))
  if(loadComponent){
  .if(isContainment.result)
     //Containment Relation
 	 PersistenceManager.ensureAllChildInstancesLoaded(target.getPersistableComponent(),modelRoot, ${locn.body}.class);
  .else
     //not a Containment Relation
	 PersistenceManager.ensureAllInstancesLoaded(modelRoot, ${locn.body}.class);
  .end if
  }
  .end if
  Vector matches = new Vector();
  .if ( target_is_one )
    ${locn.body} source = (${locn.body})target.${backPointerName};
    if (source != null && (test == null || test.evaluate(source))) {
      matches.add(target.${backPointerName});
    }
  .else
  synchronized(target.${backPointerName}) {
    for (int i = 0; i < target.${backPointerName}.size(); ++i) {
      ${locn.body} source = (${locn.body})target.${backPointerName}.get(i);
      if (source != null && (test == null || test.evaluate(source))) {

        matches.add(source);
	  }
	}
  }
  .end if
  
	if (matches.size() > 0) {
		${locn.body}[] ret_set = new ${locn.body}[matches.size()];
		matches.copyInto(ret_set);
		return ret_set;
	} else {
		return new ${locn.body}[0];
	}
  }
.end if
.end function
.//
.function xlate_referring_objects
  .param inst_ref ref_obj
  .param frag_ref package
.//
.//set up local and target objects
.select any loc_obj related by ref_obj->R_OIR[R203]->O_OBJ[R201]
.select any tar_obj from instances of O_OBJ
.select any gen_rel from instances of R_REL
.assign loc_txt_phrs = ""
.assign loc_mult = 0
.assign tar_txt_phrs = ""
.assign tar_mult = 0
.//
.select any frm_ref_end related by ref_obj->R_FORM[R205]
.if (not_empty frm_ref_end)
  .select any spc_rel related by frm_ref_end->R_SIMP[R208]
  .select any tar_rel_end related by spc_rel->R_PART[R207]
  .select any rto related by tar_rel_end->R_RTO[R204]
  .select any tar_obj related by rto->R_OIR[R203]->O_OBJ[R201]
  .select any gen_rel related by spc_rel->R_REL[R206]
  .assign loc_txt_phrs = "${frm_ref_end.Txt_Phrs}"
  .assign loc_mult = frm_ref_end.Mult
  .assign tar_txt_phrs = "${tar_rel_end.Txt_Phrs}"
  .assign tar_mult = tar_rel_end.Mult
  .invoke np_ref = declare_canonical_nav_path(loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
  .invoke can_ref = declare_canonical_reference(rto, loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${can_ref.body}
.end if
.select any ass_rel_end related by ref_obj->R_ASSR[R205]
.if (not_empty ass_rel_end)
  .select any spc_rel related by ass_rel_end->R_ASSOC[R211]
  .select any ass_obj related by ass_rel_end->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
  .select any tar_rel_end related by spc_rel->R_AOTH[R210]
  .select any tar_rto related by tar_rel_end->R_RTO[R204]
  .select any tar_obj related by tar_rto->R_OIR[R203]->O_OBJ[R201]
  .select any loc_rel_end related by spc_rel->R_AONE[R209] 
  .select any loc_rto related by loc_rel_end->R_RTO[R204]
  .select any loc_obj related by loc_rto->R_OIR[R203]->O_OBJ[R201]
  .select any gen_rel related by spc_rel->R_REL[R206]
  .assign loc_txt_phrs = "${loc_rel_end.Txt_Phrs}"
  .assign loc_mult = loc_rel_end.Mult
  .assign tar_txt_phrs = "${tar_rel_end.Txt_Phrs}"
  .assign tar_mult = tar_rel_end.Mult
  .invoke np_ref = declare_canonical_nav_path(ass_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
  .invoke np_ref = declare_canonical_nav_path(ass_obj, tar_txt_phrs, tar_mult, loc_obj, loc_txt_phrs, loc_mult, gen_rel, package)
${np_ref.body}
  .invoke can_ref = declare_canonical_reference(tar_rto, ass_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${can_ref.body}
  .invoke can_ref = declare_canonical_reference(loc_rto, ass_obj, tar_txt_phrs, tar_mult, loc_obj, loc_txt_phrs, loc_mult, gen_rel, package)
${can_ref.body}
.end if
.select any sub_rel_end related by ref_obj->R_SUB[R205]
.if (not_empty sub_rel_end)
  .select any spc_rel related by sub_rel_end->R_SUBSUP[R213]
  .select any tar_rel_end related by spc_rel->R_SUPER[R212]
  .select any rto related by tar_rel_end->R_RTO[R204]
  .select any tar_obj related by rto->R_OIR[R203]->O_OBJ[R201]
  .select any gen_rel related by spc_rel->R_REL[R206]
  .assign loc_txt_phrs = "is subtype"
  .assign loc_mult = 0
  .assign tar_txt_phrs = "is supertype"
  .assign tar_mult = 0
  .invoke np_ref = declare_canonical_nav_path(loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
  .invoke can_ref = declare_canonical_reference(rto, loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${can_ref.body}
.end if
.//
.end function
