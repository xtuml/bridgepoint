.//====================================================================
.//
.// File:      $RCSfile: referred_to.inc,v $
.// Version:   $Revision: 1.34.26.1 $
.// Modified:  $Date: 2013/07/19 12:26:01 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.function declare_cnonicl_rto_nav_path
  .param inst_ref rgo
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
  .//
  .invoke is_refl = is_reflexive( gen_rel )
  .assign tar_phrase = ""
  .assign loc_phrase = ""
  .if ( is_refl.result )
    .assign tar_phrase = "$cr{tar_txt_phrs}"
    .assign loc_phrase = "$cr{loc_txt_phrs}"
  .end if
  
  .invoke l = is_persistent(loc_obj)
  .invoke t = is_persistent(tar_obj)
  .assign persistent = ((l.result) and (t.result))
  
  .assign crossComp=false
  .if(persistent)
    .invoke cc=isCrossComponentAssociation(loc_obj,tar_obj,gen_rel)
    .assign crossComp=cc.result
  .end if
  
// referred to navigation

  .invoke ref_var_name = get_referential_var_name( tar_obj, tar_txt_phrs )
  .select one assoc_rel related by gen_rel->R_ASSOC[R206]
  .assign target_is_one = ((tar_mult == 0) and (empty assoc_rel))
  .assign backPointerName = "backPointer_${ref_var_name.body}$cr{tar_txt_phrs}_R${gen_rel.Numb}"
  .if ( target_is_one )
${tocn.body} ${backPointerName};
  .else
ArrayList<${tocn.body}> ${backPointerName} = new ArrayList<${tocn.body}>();
  .end if
.assign generateOrderingMethod = gen_rel.Numb != 2698
.assign generateOrderingMethod = (generateOrderingMethod and (not target_is_one))
.assign generateOrderingMethod = (generateOrderingMethod and (not is_refl.result))
.assign generateOrderingMethod = (generateOrderingMethod and (empty assoc_rel))
.if(generateOrderingMethod)
public void set$Cr{tar_obj.Name}OrderInChildListR${gen_rel.Numb}(${tocn.body} target, int index) {
	${tocn.body}[] elements = ${tocn.body}.getMany${tar_obj.Key_Lett}sOnR${gen_rel.Numb}${tar_phrase}(this);
	// unrelate backwards to support undo/redo
	for(int i = elements.length - 1; i >= 0; i--) {
	   unrelateAcrossR${gen_rel.Numb}From${tar_phrase}((${tocn.body}) elements[i]); 
	}
	for(int i = 0; i < elements.length; i++) {
	   ${tocn.body} element = (${tocn.body}) elements[i];
	   // for all cases if the index matches and we
	   // are not at the end of the list, relate the
	   // element to be moved
	   if(i == index && elements.length - 1 != index) {
	       relateAcrossR${gen_rel.Numb}To${tar_phrase}(target);
	   }
		if(element != target) {
			relateAcrossR${gen_rel.Numb}To${tar_phrase}(element);
		}
		// for the end case we need to have the last element in
		// the list at the same location as us associated before
		// us, after that we can relate the element to be moved
		if(i == index && elements.length - 1 == index) {
		    relateAcrossR${gen_rel.Numb}To${tar_phrase}(target);
		}
	}
}
.end if

public void relateAcrossR${gen_rel.Numb}To${tar_phrase}(${tocn.body} target)
{
  if (target != null) {
    target.relateAcrossR${gen_rel.Numb}To${loc_phrase}(this, true) ;
  }
}

public void relateAcrossR${gen_rel.Numb}To${tar_phrase}(${tocn.body} target, boolean notifyChanges)
{
  if (target != null) {
    target.relateAcrossR${gen_rel.Numb}To${loc_phrase}(this, notifyChanges) ;
  }
}

public void setBackPointerR${gen_rel.Numb}To${tar_phrase}(${tocn.body} target)
{
  .if ( target_is_one )
    ${backPointerName} = target;
  .else
  	synchronized (${backPointerName}) {
		${backPointerName}.add(target);
	}
  .end if
}

public void unrelateAcrossR${gen_rel.Numb}From${tar_phrase}(${tocn.body} target)
{
  if (target != null) {
    target.unrelateAcrossR${gen_rel.Numb}From${loc_phrase}(this, true) ;
  }
}

public void unrelateAcrossR${gen_rel.Numb}From${tar_phrase}(${tocn.body} target, boolean notifyChanges)
{
  if (target != null) {
    target.unrelateAcrossR${gen_rel.Numb}From${loc_phrase}(this, notifyChanges) ;
  }
}

public void clearBackPointerR${gen_rel.Numb}To${tar_phrase}(${tocn.body} target)
{
  .if ( target_is_one )
	if (target == ${backPointerName}) {
		${backPointerName} = null;
	}
  .else
  synchronized (${backPointerName}) {
	    ${backPointerName}.remove(target);
	}
  .end if
}

  .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "one" )
public static ${locn.body} ${nfn.body}${loc_phrase}(${tocn.body} [] targets)
{
    return ${nfn.body}${loc_phrase}(targets, null);
}

  .invoke gtcn = get_test_class_name()
public static ${locn.body} ${nfn.body}${loc_phrase}(${tocn.body} [] targets, ${gtcn.body} test)
{
    return ${nfn.body}${loc_phrase}(targets, test, true);
}

public static ${locn.body} ${nfn.body}${loc_phrase}(${tocn.body} [] targets, ${gtcn.body} test, boolean loadComponent)
{
    ${locn.body} ret_val = null;
    if (targets != null)
    {
        for (int i = 0; i < targets.length && ret_val == null; ++i )
        {
           if (test != null) {
             ${locn.body} candidate_val = ${nfn.body}${loc_phrase}(targets[i],true);
             if ( candidate_val != null && test.evaluate(candidate_val) )
             {
               ret_val = candidate_val;
               break;
             }
           }
           else {
             ret_val = ${nfn.body}${loc_phrase}(targets[i], loadComponent);
             if (ret_val != null) {
               break;
             }
           }
        }
    }
    return ret_val;
}

public static ${locn.body} ${nfn.body}${loc_phrase}(${tocn.body} target)
{
	return ${nfn.body}${loc_phrase}(target, true);
}
.// method override to allow for choosing component loads
public static ${locn.body} ${nfn.body}${loc_phrase}(${tocn.body} target, boolean loadComponent)
{
  if (target != null) {
  .if (persistent)
	if(loadComponent){
	     target.loadProxy();
      .if(crossComp)
	    if(target.$cr{loc_txt_phrs}$cr{loc_obj.Name} != null){
		target.$cr{loc_txt_phrs}$cr{loc_obj.Name}.loadProxy();
	}
	.end if
	}
	.end if
    return target.$cr{loc_txt_phrs}$cr{loc_obj.Name} ;
  } else {
    return null;
  }
}

  .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "many" )
public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} [] targets,
    ClassQueryInterface_c test)
{
	return ${nfn.body}${loc_phrase}(targets, test, true);
}

.// override method to allow choosing of component loads
public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} [] targets,
    ClassQueryInterface_c test, boolean loadComponent)
{
  if(targets == null || targets.length == 0 || targets[0] == null)
    return new ${locn.body}[0];
  
  
  LinkedHashSet<${locn.body}> elementsSet = new LinkedHashSet<${locn.body}>();
  for (int i = 0; i < targets.length; i++) {
  .if ((persistent))
	if(loadComponent && targets[i] != null && targets[i].$cr{loc_txt_phrs}$cr{loc_obj.Name} == null)
	  targets[i].loadProxy();
  .end if	
    ${locn.body} associate = targets[i].$cr{loc_txt_phrs}$cr{loc_obj.Name};
    if (targets[i] != null && associate != null
    	&& (test == null || test.evaluate(associate))) {
        	if (elementsSet.add(associate)){ 
  .if ((persistent) and (crossComp))
                if(loadComponent){
                   associate.loadProxy();
                }
  .end if
            }
    }
  }

  ${locn.body}[] result = new ${locn.body}[elementsSet.size()];
  elementsSet.toArray(result);
  return result;
}

public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} [] targets)
{
  return ${nfn.body}${loc_phrase}(targets, null);
}

  .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "many" )
public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} target,
    ClassQueryInterface_c test, boolean loadComponent)
{
  if (target != null) {
    ${tocn.body} [] targetArray = new ${tocn.body}[1];
    targetArray[0] = target;
    return ${nfn.body}${loc_phrase}(targetArray, test, loadComponent);
  } else {
    ${locn.body} [] result = new ${locn.body} [0] ;
    return result ;
  }
  
}

public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} target,
    ClassQueryInterface_c test)
{
    return ${nfn.body}${loc_phrase}(target, null, true);
}

public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} target)
{
    return ${nfn.body}${loc_phrase}(target, null, true);
}

public static ${locn.body} [] ${nfn.body}${loc_phrase}(${tocn.body} target, boolean loadComponent)
{
    return ${nfn.body}${loc_phrase}(target, null, loadComponent);
}
.end function
.//
.function declare_cnonicl_assoc_nav_path
  .param inst_ref loc_obj
  .param string loc_txt_phrs
  .param integer loc_mult
  .param inst_ref tar_obj
  .param string tar_txt_phrs
  .param integer tar_mult
  .param inst_ref gen_rel
  .param inst_ref assr_obj
.//
  .invoke is_refl = is_reflexive( gen_rel )
  .assign tar_phrase = ""
  .assign loc_phrase = ""
  .if ( is_refl.result )
    .assign tar_phrase = "$cr{tar_txt_phrs}"
    .assign loc_phrase = "$cr{loc_txt_phrs}"
  .end if
  .invoke tocn = get_class_name( tar_obj )
  .invoke locn = get_class_name( loc_obj )
  .invoke aocn = get_class_name( assr_obj )
.//
  .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "one" )
  .invoke anfn = get_nav_func_name( assr_obj, gen_rel, "one" )
public static ${locn.body} ${nfn.body}${tar_phrase}(${tocn.body} target)
{
  return ${locn.body}.${nfn.body}${tar_phrase}(${aocn.body}.${anfn.body}${loc_phrase}(target)) ;
}

  .invoke anfn = get_nav_func_name( assr_obj, gen_rel, "many" )
public static ${locn.body} ${nfn.body}${tar_phrase}(${tocn.body} target,
    ClassQueryInterface_c test)
{
  return ${locn.body}.${nfn.body}${tar_phrase}(${aocn.body}.${anfn.body}${loc_phrase}(target), test) ;
}

  .if (loc_mult == 1)
    .// Multiplicity is MANY, need to return a set too
    .invoke nfn = get_nav_func_name( loc_obj, gen_rel, "many" )
    .invoke anfn = get_nav_func_name( assr_obj, gen_rel, "many" )
public static ${locn.body} [] ${nfn.body}${tar_phrase}(${tocn.body} target,
    ClassQueryInterface_c test)
{
	return ${nfn.body}${tar_phrase}(target, test, true);
}
.// method override to allow for choosing to load component
public static ${locn.body} [] ${nfn.body}${tar_phrase}(${tocn.body} target,
    ClassQueryInterface_c test, boolean loadComponent)
{

  ${aocn.body} [] associators = ${aocn.body}.${anfn.body}${loc_phrase}(target, loadComponent) ;
    .invoke onfn = get_nav_func_name( loc_obj, gen_rel, "many" )
    return ${locn.body}.${onfn.body}${tar_phrase}(associators, test, loadComponent) ;
}
public static ${locn.body} [] ${nfn.body}${tar_phrase}(${tocn.body} target)
{
    return ${nfn.body}${tar_phrase}(target, null, true);
}
public static ${locn.body} [] ${nfn.body}${tar_phrase}(${tocn.body} target, boolean loadComponent)
{
    return ${nfn.body}${tar_phrase}(target, null, loadComponent);
}
  .end if
.end function
.//
.function xlate_referred_to_objects
  .param inst_ref ref_to_obj
  .param frag_ref package
.//
.//set up local and target objects
.select any loc_obj related by ref_to_obj->R_OIR[R203]->O_OBJ[R201]
.select any tar_obj from instances of O_OBJ
.select any gen_rel from instances of R_REL
.assign loc_txt_phrs = ""
.assign loc_mult = 0
.assign tar_txt_phrs = ""
.assign tar_mult = 0
.//
.select any par_ref_end related by ref_to_obj->R_PART[R204]
.if (not_empty par_ref_end)
  .select any spc_rel related by par_ref_end->R_SIMP[R207]
  .select any tar_rel_end related by spc_rel->R_FORM[R208]
  .select any rgo related by tar_rel_end->R_RGO[R205]
  .select any tar_obj related by rgo->R_OIR[R203]->O_OBJ[R201]
  .select any gen_rel related by spc_rel->R_REL[R206]
  .assign loc_txt_phrs = "${par_ref_end.Txt_Phrs}"
  .assign loc_mult = par_ref_end.Mult
  .assign tar_txt_phrs = "${tar_rel_end.Txt_Phrs}"
  .assign tar_mult = tar_rel_end.Mult
  .invoke np_ref = declare_cnonicl_rto_nav_path(rgo, loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
.end if
.select any aone_ref_end related by ref_to_obj->R_AONE[R204]
.if (not_empty aone_ref_end)
  .select any spc_rel related by aone_ref_end->R_ASSOC[R209]
  .select any aoth_rel_end related by spc_rel->R_AOTH[R210]
  .select any tar_rel_end related by spc_rel->R_ASSR[R211]
  .select any rgo related by tar_rel_end->R_RGO[R205]
  .select any tar_obj related by rgo->R_OIR[R203]->O_OBJ[R201]
  .select any gen_rel related by spc_rel->R_REL[R206]
  .assign loc_txt_phrs = "${aone_ref_end.Txt_Phrs}"
  .assign loc_mult = aone_ref_end.Mult
  .assign tar_txt_phrs = "${aoth_rel_end.Txt_Phrs}"
  .assign tar_mult = aoth_rel_end.Mult
  .invoke np_ref = declare_cnonicl_rto_nav_path(rgo, loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
  .select any aoth_obj related by aoth_rel_end->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .invoke np_ref = declare_cnonicl_assoc_nav_path(loc_obj, loc_txt_phrs, loc_mult, aoth_obj, tar_txt_phrs, tar_mult, gen_rel, tar_obj)
${np_ref.body}
.end if
.select any aoth_ref_end related by ref_to_obj->R_AOTH[R204]
.if (not_empty aoth_ref_end)
  .select any spc_rel related by aoth_ref_end->R_ASSOC[R210]
  .select any aone_rel_end related by spc_rel->R_AONE[R209]
  .select any tar_rel_end related by spc_rel->R_ASSR[R211]
  .select any rgo related by tar_rel_end->R_RGO[R205]
  .select any tar_obj related by rgo->R_OIR[R203]->O_OBJ[R201]
  .select any gen_rel related by spc_rel->R_REL[R206]
  .assign loc_txt_phrs = "${aoth_ref_end.Txt_Phrs}"
  .assign loc_mult = aoth_ref_end.Mult
  .assign tar_txt_phrs = "${aone_rel_end.Txt_Phrs}"
  .assign tar_mult = aone_rel_end.Mult
  .invoke np_ref = declare_cnonicl_rto_nav_path(rgo, loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
  .select any aone_obj related by aone_rel_end->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .invoke np_ref = declare_cnonicl_assoc_nav_path(loc_obj, loc_txt_phrs, loc_mult, aone_obj, tar_txt_phrs, tar_mult, gen_rel, tar_obj)
${np_ref.body}
.end if
.select any sup_rel_end related by ref_to_obj->R_SUPER[R204]
.if (not_empty sup_rel_end)
  .select any spc_rel related by sup_rel_end->R_SUBSUP[R212]
  .select many tar_rel_ends related by spc_rel->R_SUB[R213]
  .for each tar_rel_end in tar_rel_ends
    .select any rgo related by tar_rel_end->R_RGO[R205]
    .select any tar_obj related by rgo->R_OIR[R203]->O_OBJ[R201]
    .select any gen_rel related by spc_rel->R_REL[R206]
    .assign loc_txt_phrs = "is supertype"
    .assign loc_mult = 0
    .assign tar_txt_phrs = "is subtype"
    .assign tar_mult = 0
    .invoke np_ref = declare_cnonicl_rto_nav_path(rgo, loc_obj, loc_txt_phrs, loc_mult, tar_obj, tar_txt_phrs, tar_mult, gen_rel, package)
${np_ref.body}
  .end for
.end if
.//
.end function
