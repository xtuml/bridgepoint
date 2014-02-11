.//====================================================================
.//
.// File:      $RCSfile: generate_configurable_components.inc,v $
.// Version:   $Revision: 1.12 $
.// Modified:  $Date: 2013/01/10 23:07:23 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.// Generate the Java code that validates/translates data from BP SQL
.// into a format that Tiger uses.
.//
.//====================================================================
.//
.include "../com.mentor.nucleus.bp.io.core/arc/crosslink_validation.inc"
.//
.//////////////////////////////////////////////////////////////////////////////////////////////
.// This recursive function is used to get childrens of an eo. Considering the nature of the
.// model the parameter 'traverse_sibling' is used to get siblings of a child of a configurable
.// component. 
.// This is a utility function only, to be used internally by this archetype
.// Parameters:
.// inst_ref eo                   The 'eo' for which the to find children and/or sibling
.// inst_ref_set children_list    The set of 'eo' which cotains those model elements which fall
.//                               under a specific configurable component. The initial value for
.//                               this set must be 'empty'
.// string indent                 Indentation string used to to output the tree like structure
.// boolean traverse_sibling      This value must be 'false' when calling this method with the
.//                               'eo' representing the parent of the configurable component
.//                                since we do not add its siblings in that components. 
.//                                This value should be 'true' for other values of 'eo'
.///////////////////////////////////////////////////////////////////////////////////////////////
.function get_children_eos
	.param inst_ref eo
	.param inst_ref_set children_list
	.param string indent
	.param boolean traverse_sibling
.//	
	.assign attr_children_list = children_list
	.if (not_empty eo)
		.if(eo.componentRoot != true)
${indent}|_${eo.Name}
			.select one child related by eo->EO[R1.'is_first_child_of']	
			.if (not_empty child)
				.if(not child.componentRoot)
					.assign attr_children_list = attr_children_list | child
				.end if
				.assign indent_string = indent + "| "
				.invoke more_eos = get_children_eos(child, attr_children_list, indent_string, true)
				.assign attr_children_list = more_eos.children_list
				.if (more_eos.body != "")
${more_eos.body}\
    			.end if 
	        .end if
        .end if
        .if (traverse_sibling)		 
			.select one sibling related by eo->EO[R2.'precedes']
			.if (not_empty sibling)
		 		.if (not sibling.componentRoot)
		    		.assign attr_children_list = attr_children_list | sibling
			    .end if
		    	.invoke more_eos = get_children_eos(sibling, attr_children_list, indent, true)
			    .if (more_eos.body != "")
${more_eos.body}\
    	    	.end if
	    	    .assign attr_children_list = more_eos.children_list		
  		    .end if
		.end if
	.end if
.end function
.//
./////////////////////////////////////////////////////////////////////////////////////////////////////////
.// This function gets the children of a component in the 'children_list' fragment variable. The parent of 
.// the component is parent_eo parameter.
.// This method shall be called by the archetype creating the import and export code.
./////////////////////////////////////////////////////////////////////////////////////////////////////////
.function children_of_component
  .param inst_ref parent_eo
.//
	.select many eos from instances of EO
	.assign attr_children_list = eos
	.assign attr_children_list = attr_children_list & parent_eo
	.assign indent_string = "  "
	.assign parent_eo.componentRoot = false
	.//Creating temporary list of eos
	.invoke eos_list = get_children_eos(parent_eo, attr_children_list, indent_string, false)
	.assign attr_children_list = eos_list.children_list
	.assign parent_eo.componentRoot = true   
${eos_list.body}
.end function
.//
.//////////////////////////////////////////////////////////////////////////////
.//  This function creates the instances of class 'Cross Link' (CRL) from
.//  the provided data which is verified using the crosslink_validation.inc
.//  This is a utility method only. 
./////////////////////////////////////////////////////////////////////////////
.function create_crl_instances
  .param string from_eo_id
  .param string to_eo_id
  .param integer relation_numb	
.//
	.create object instance crosslink of CRL
	.assign crosslink.fromId = from_eo_id
	.assign crosslink.toId = to_eo_id
	.assign crosslink.Numb = relation_numb
.end function
.//
.///////////////////////////////////////////////////////////////////////////////
.// The following function populates the cross links (CRL) table. This shall be 
.// queried to get the classes to be cached in a particular component.
.// The following algorithm is followed:
.// Get all objects that fall into one configurable component
.// For each object in these objects
.//    Find all the relations
.//    for each relation in these realtions
.//        Get the other model element (reffered_to or reffering), call it 'A'
.//        if the model element 'A' does not fall in the same configurable component
.//           Set the source object, the destination object ('A') and the relation
.//           number in a new created CRL instance.
.////////////////////////////////////////////////////////////////////////////////
.function populate_cross_links
.select many eos from instances of EO
.for each eo in eos
	.select any top related by eo->EO[R1.'is_parent_of']
    .select any pre_sibling related by eo->EO[R2.'precedes']
    .select any suc_sibling related by eo->EO[R2.'follows']
    .//Check if the instance found is not already been checked, this is true in case of nested configurable
    .//components, in this case the 'writePostion' attribute is set to none in the PEI file
    .if (((eo.componentRoot) or ((empty top) and ( (empty pre_sibling)and (empty suc_sibling))) ) and (eo.writePosition != "none"))
    	.select any parent_obj from instances of O_OBJ where (selected.Name == eo.Name) 
        .// The children list 
        .invoke child_list = children_of_component(eo)	    
        .assign children_list = child_list.children_list
----------        
>> ${eo.Name}	
${child_list.body}
		.for each child in children_list
**${child.Name}	
			.select any obj from instances of O_OBJ where (selected.Name == child.Name)             
			.if (not_empty obj)
				.select many relations related by obj->R_OIR[R201]->R_REL[R201]
				.if (not_empty relations)
					.for each relation in relations
						.select many other_objs related by relation->R_OIR[R201]->O_OBJ[R201] where (selected.Obj_ID != obj.Obj_ID)
   						.for each other_obj	in other_objs
   							.//Finding out if this object is part of this component or not
   							.assign is_child = false
   							.assign children_list_copy = children_list
   							.for each child1 in children_list_copy
   								.select any new_obj from instances of O_OBJ where (selected.Name == child1.Name)
   								.if (not_empty new_obj)
   									.if (new_obj.Key_Lett == other_obj.Key_lett) 
										.assign is_child = true
										.//If a reffered object (i.e. other_obj) is the same as the parent
			   							.//object of the configurable component, and there is also an entry 
   										.//in the EO table, then this is a nested self reference and must be 
   										.//included as the cross-link
									.elif (other_obj.Key_Lett == parent_obj.Key_Lett)
										.select any proxy_child_element from instances of EO where ((selected.Name == other_obj.Name) AND (selected.Numb == relation.Numb))
										.if (not_empty proxy_child_element)
											.assign is_child = false
										.end if
									.end if
								.end if
   							.end for
   							.if (is_child == false)
	   							.select any to_obj from instances of O_OBJ where (selected.Name == obj.Name)
   								.select any from_obj from instances of O_OBJ where (selected.Name == other_obj.Name)
   								.invoke isCrossLinkValid = validateCrossLink(to_obj.Key_Lett, from_obj.Key_Lett, "R${relation.Numb}", "")
   								.if (isCrossLinkValid.result)   								
    R${relation.Numb}: ${other_obj.Name} -- ${isCrossLinkValid.from}
   									.// Selecting the associated eo of the other_obj. Although there can be several 
   									.// EO having the same name i.e. referring the same instance of O_OBJ, we are 
   									.//not concenrned about which ever object be selected since we are only interested 
   									.//in sepecifying cross link between the two Model Classes.
    								.select any to_eo from instances of EO where (selected.Name == other_obj.Name)
    								.if (not_empty to_eo)
	    								.invoke create_crl = create_crl_instances(child.Id, to_eo.Id, relation.Numb) 
									.end if	    								
     							.else 
	R${relation.Numb}: ${other_obj.Name} -- ${isCrossLinkValid.from} ******* REMOVED
     							.end if
   							.end if  															
   						.end for			
					.end for
				.end if
			.end if
		.end for   
    .end if
.end for
.//
.end function
.//
.//////////////////////////////////////////////////////////////////////////////////////////////////
.// Given an EO, this function returns all immediate parent of that eo. The fragment variable
.// "attr_parent_eos" have the resultant set
.//////////////////////////////////////////////////////////////////////////////////////////////////
.function get_parent_eos
  .param inst_ref eo
  .//	
	.//First see if the given eo has a direct parent
	.assign attr_has_parent = false
	.select many attr_parent_eos related by eo->EO[R1.'is_parent_of']
  .//Get the first sibling 				
	.select any pre_sibling related by eo->EO[R2.'follows']
	.if (not_empty pre_sibling)
	 	.assign first_sibling = pre_sibling
		.while (not_empty pre_sibling)
		  .assign first_sibling = pre_sibling
		  .select any pre_sibling related by first_sibling->EO[R2.'follows']
	  .end while
	  .select many parent_eos related by first_sibling->EO[R1.'is_parent_of']
    .assign attr_parent_eos = attr_parent_eos | parent_eos
	.end if
	
	.if (not_empty attr_parent_eos)
	    .assign attr_has_parent = true
	.end if
.end function 
.//////////////////////////////////////////////////////////////////////////////////////////////////
.// Given a root element of a component, this function returns the root of its parent component
.//////////////////////////////////////////////////////////////////////////////////////////////////
.function get_parent_component_of_eo
  .param inst_ref comp_root_eo
.//
	.invoke get_parent = get_parent_eos(comp_root_eo)
	.assign parents = get_parent.parent_eos
	.// We need to select any parent. Since no single component can be a child of multiple 	
	.// components.
	.assign attr_parent_component_of_eo = comp_root_eo
	.for each parent in parents
		.assign attr_parent_component_of_eo = parent
		.break for
	.end for	 
	.while (attr_parent_component_of_eo.componentRoot != true)
		.invoke get_parent = get_parent_eos(attr_parent_component_of_eo)
		.assign parents = get_parent.parent_eos
		.for each parent in parents
			.assign attr_parent_component_of_eo = parent
			.break for
		.end for
	.end while
.end function
.//
.function get_all_parent_components_of_eo
  .param inst_ref eo
  .param inst_ref_set visited_nr_parents
  .param boolean checkSameEos
    .select many eos from instances of EO
    .select many childEos from instances of EO where ((selected.Name == eo.Name) and (not selected.ignoreParent))
    .if(not checkSameEos)
      .assign childEos = childEos - childEos
      .assign childEos = childEos | eo
    .end if
    .assign attr_parents = eos - eos
    .for each childEo in childEos
      .//
	    .invoke result = get_parent_eos(childEo)
    	.assign parents = result.parent_eos
    	.for each parent in parents
    	  .assign visited = false
    	  .for each found_parent in attr_parents
    	    .if(found_parent.Id == parent.Id)
    	      .// already visited, skip
    	      .assign visited = true
    	      .break for
    	    .end if
    	  .end for
    	  .if(not visited)
    	    .if(parent.componentRoot == true)
	          .assign attr_parents = attr_parents | parent
	        .else
	          .assign visited = false
	          .for each visited_nr_parent in visited_nr_parents
	            .if(visited_nr_parent.Id == childEo.Id)
	              .assign visited = true
  	          .end if
	          .end for
  	        .if(not visited)
	            .invoke result = get_all_parent_components_of_eo(parent, visited_nr_parents, true)
	            .assign attr_parents = attr_parents | result.parents
	            .assign visited_nr_parents = visited_nr_parents | childEo
	          .end if
  	      .end if
  	    .end if
	    .end for
	  .end for
.end function
.//
.function get_path_from_parent_component
  .param inst_ref eo
.//
    .assign attr_path_to_parent_component = eo - eo
    .assign attr_parent_component_eo = eo
    .//
		.invoke result = get_parent_eos(eo)
		.if (result.has_parent)
			.assign parents = result.parent_eos			
			.// We need to select any parent. Since no single component can be a child of multiple 	
			.// components.
			.if(empty parents)
			    .print "ERROR: given eo does not have a parent"
			.else
			    .assign parent_eo = eo    
			    .for each parent in parents
				    .assign parent_eo = parent
				    .break for
			    .end for
		        .if(parent_eo.componentRoot == true)
		            .assign attr_parent_component_eo = parent_eo
		            .assign attr_path_to_parent_component = parent_eo | attr_path_to_parent_component
		        .else
		            .invoke result = get_path_from_parent_component(parent)
		            .assign attr_parent_component_eo = result.parent_component_eo
		            .assign attr_path_to_parent_component = attr_path_to_parent_component | result.path_to_parent_component
		        .end if
	            .assign attr_path_to_parent_component = attr_path_to_parent_component | eo
			.end if
		.end if
    .//
.end function

.//////////////////////////////////////////////////////////////////////////////////////////////////
.// This method returns the set of EOs that are root elements of seperately configurable components
.//////////////////////////////////////////////////////////////////////////////////////////////////
.function get_all_seperately_configurable_elements
.//
	.select many eos from instances of EO
	.assign proxy_instances = eos
	.assign proxy_instances = proxy_instances - proxy_instances
	.for each eo in eos
	    .//Check if the instance found is not already been checked, this is true in case of nested configurable
	    .//components, in this case the 'writePosition' attribute is already set to 'none'
	    .//in the PEI file
	    .if ((eo.componentRoot == true) and (eo.writePosition != "none"))
		    .assign proxy_instances = proxy_instances | eo
	    .end if
    .end for
    .assign attr_value = proxy_instances 
.end function
.//
.function is_configurable_element
		.param inst_ref table
		.assign attr_result = false	
		.select many eos from instances of EO where (selected.Name == "${table.Name}")
		.for each eo in eos 
			 .if (eo.componentRoot == true)
			    .assign attr_result = true	
			    .break for
		    .end if
		.end for
.end function
.//
.//
.function is_member_of_component
  .param inst_ref obj
  .param inst_ref_set component_members
  .//
  .assign attr_is_member = false
  .for each eo in component_members
     .if (eo.Name == obj.Name)
         .assign attr_is_member = true
         .break for
     .end if
  .end for
.end function
.//
.//====================================================================
.//
.function firstOccurrence
  .param inst_ref eo
  
  .assign attr_firstOccurrence=true
	.select many eos from instances of EO where (selected.Name == eo.Name)
	
	.if((cardinality eos) > 1)
		.for each e in eos
			.if((first eos) and (e == eo))
				.break for
			.else
				.assign attr_firstOccurrence=false
				.break for
			.end if
		.end for
	.end if
.end function
.//
.//
.//select many crosslinks from instances of CRL
.//for each cl in crosslinks
	.//select any from_eo from instances of EO where (selected.Id == cl.fromId)
	.//select any to_eo from instances of EO where (selected.Id == cl.toId)
.//From: ${from_eo.Name}    To: ${to_eo.Name}    Relation: R${cl.Numb}
.//end for
.//emit to file "cross_links.txt"
