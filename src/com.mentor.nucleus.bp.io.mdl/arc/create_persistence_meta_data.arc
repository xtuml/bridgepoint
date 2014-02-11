.//====================================================================
.//
.// File:      $RCSfile: create_persistence_meta_data.arc,v $
.// Version:   $Revision: 1.31 $
.// Modified:  $Date: 2013/01/17 03:34:48 $
.//
.// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.invoke arc_env1 = GET_ENV_VAR( "IO_CORE_ARC_DIR" )
.assign io_core_archetypes = arc_env1.result
.if ( io_core_archetypes == "" )
  .print "\nERROR: Environment variable IO_CORE_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "${io_core_archetypes}/generate_configurable_components.inc"
.//
.assign class_name = "PersistenceHierarchyMetaData"
.//
.function get_path_to_child
  .param inst_ref parent_eo
  .param inst_ref child_eo
  .param boolean isRoot
    .assign attr_found = false
    .select many attr_path from instances of EO where (selected.Name == "")
    .if(isRoot or ((parent_eo.writePosition != "none" ) and (not parent_eo.componentRoot)))
      .assign attr_path = parent_eo | attr_path
      .select one first_child related by parent_eo->EO[R1.'is_first_child_of']
      .if(first_child == child_eo)
        .assign attr_found = true
        .assign attr_path = attr_path | child_eo
      .end if
      .if((not_empty first_child) and (not attr_found))
        .invoke recursive = get_path_to_child(first_child, child_eo, false)
        .if(recursive.found)
          .assign attr_path = attr_path | recursive.path
          .assign attr_found = true
        .end if
        .if(not attr_found)
          .select one next_child related by first_child->EO[R2.'precedes']
          .while(not_empty next_child)
            .if(next_child == child_eo)
              .assign attr_path = attr_path | next_child
              .assign attr_found = true
              .break while
            .else
              .invoke recursive = get_path_to_child(next_child, child_eo, false)
              .if(recursive.found)
                .assign attr_path = attr_path | recursive.path
                .assign attr_found = true
                .break while
              .end if            
            .end if
            .select one next_child related by next_child->EO[R2.'precedes']
          .end while
        .end if
      .end if
    .end if
.end function
.function get_non_root_children
  .param inst_ref parent
    .select many attr_path_from_parent from instances of EO where (selected.Name == "")
    .assign attr_path_from_parent = attr_path_from_parent | parent
    .select many attr_children from instances of EO where (selected.Name == "")
    .select any first_child related by parent->EO[R1.'is_first_child_of']
    .if(not_empty first_child)
      .if(not first_child.componentRoot)
        .assign attr_children = attr_children | first_child
        .invoke recursive = get_non_root_children(first_child)
        .assign attr_path_from_parent = attr_path_from_parent | recursive.path_from_parent
        .assign attr_children = attr_children | recursive.children
      .end if
      .select one next_child related by first_child->EO[R2.'precedes']
      .while(not_empty next_child)
        .if(not next_child.componentRoot)
          .assign attr_children = attr_children | next_child
          .invoke recursive = get_non_root_children(next_child)
          .assign attr_path_from_parent = attr_path_from_parent | recursive.path_from_parent
          .assign attr_children = attr_children | recursive.children
        .end if
        .select one next_child related by next_child->EO[R2.'precedes']
      .end while
    .end if
.end function
.function generate_nav_to_parent
    .param string       eo_name
    .param inst_ref     root_eo
    .invoke result = get_non_root_children(root_eo)
    .assign component_members = result.children;
    .//
    .for each child in component_members
      .if(child.Name == eo_name)
        .assign activity_eo = child;
    .assign attr_chain_text = ""
    .invoke class_name = get_class_name(root_eo)
        .invoke result = get_path_to_child(root_eo, activity_eo, true)
        .assign path_to_parent = result.path
	    .if (not_empty path_to_parent)
	      .assign step_count = cardinality path_to_parent
	      .if(step_count > 1)
	          .assign attr_chain_text = "(${class_name.body})element"
	      .else
	          .assign attr_chain_text = "new ${class_name.body}[]{(${class_name.body})element}"
	      .end if
	      .for each element in path_to_parent
	         .if (not_first path_to_parent)
	 		     .select one table related by element->EI[R3]->T[R4]
	             .if(element.rel_chain != "")
                   .invoke nav = generate_backward_rel_chain_nav_from_kl_with_query_(table.Key_Lett, element.rel_chain, element.Card, "(${class_name.body}) element", "", false, "", false);
                   .assign attr_chain_text = "${nav.body}"
	             .else
 			     .invoke class_name = get_class_name(element) 
			     .invoke nav = get_nav_func_name(table, element, "many")
			     .assign attr_chain_text = "${class_name.body}.${nav.body}${element.rel_phrase}(${attr_chain_text})"
	        .end if
	        .end if
	      .end for
	      .invoke activity_eo_cn = get_class_name(activity_eo)
	        if(type == ${activity_eo_cn.body}.class){
                return ${attr_chain_text};
	        }   
	    .end if
   .end if
	.end for
.end function

.//This method returns only persistable children only
.//If all children all required in hierarchy, override this method by removing .if((child.ComponentRoot) or (child.WritePosition!=none))
.//This is because we are keeping two instances for some of the elements, to store them some where outside navigation hierarchy
.function get_children
    .param inst_ref eo
    .//
    .select many eos from instances of EO
    .assign attr_children = eos - eos
    .//
    .select one child related by eo->EO[R1.'is_first_child_of']	
    .if(not_empty child)
        .if((child.ComponentRoot) or (child.WritePosition!="none"))
        .assign attr_children = attr_children | child
        .end if
        .select one sibling related by child->EO[R2.'precedes']
        .while(not_empty sibling)
           .if((sibling.ComponentRoot) or (sibling.WritePosition!="none"))
            .assign attr_children = attr_children | sibling
           .end if 
            .select one sibling related by sibling->EO[R2.'precedes']
        .end while
    .end if
.end function
.//
.function reverse_rel_chain_sense
  .param string parent_kl
  .param string rel_chain
    .// example of starting rel chain
    .//   ->EP_SPKG[R1400]->A_A[R1402]
    .// parent key letters
    .//   EP_PKG
    .// example of ending rel chain
    .//   ->EP_SPKG[R1402]->E_PKG[R1400]
    .assign attr_reversed_chain = ""
    .assign last_association = ""
    .assign count = 0
    .while ("$r{rel_chain}" != "")
      .assign count = count + 1
      .invoke nav = parse_chain(rel_chain, "backward")
      .assign relPhrase = nav.phrase
      .if(count != 1)
        .if(relPhrase != "")
          .assign relPhrase = ".'${relPhrase}'"
        .end if
        .assign attr_reversed_chain = attr_reversed_chain + "->${nav.key_lett}[R${last_association}]"
      .end if
      .assign rel_chain = nav.remainder
      .assign last_association = "${nav.rel_num_str}${relPhrase}"
    .end while
    .assign attr_reversed_chain = attr_reversed_chain + "->${parent_kl}[R${last_association}]"
.end function
.function generate_chain
    .param inst_ref_set path
    .param string child_var
    .param string params
    .param string cardinality
    .//     
    .assign parent_picked = true
    .select any parent_element from instances of EO where ( selected.Id == "-1" )
    .select one parent_table related by parent_element->EI[R3]->T[R4]
    .assign child_element = parent_element
    .assign inner_path = path
    .assign rel_chain = ""
    .assign count = 0
    .// 
    .for each element in path
      .assign count = count + 1
      .if(last path)
        .assign child_element = element
      .else
        .// find the rel number to get
        .// to the parent, it is the
        .// element's number value previous
        .// to the current element
        .assign innerCount = 0
        .assign relNumb = element.Numb
        .assign expected = count + 1
        .assign next_rel_chain = ""
        .assign nextRelPhrase = ""
        .for each e in inner_path
          .assign innerCount = innerCount + 1
          .if(innerCount == expected)
            .assign relNumb = e.Numb
            .assign next_rel_chain = e.rel_chain
            .assign nextRelPhrase = e.rel_phrase
          .end if
        .end for
   		  .select one table related by element->EI[R3]->T[R4]
        .if(next_rel_chain != "")
          .invoke reversed_chain = reverse_rel_chain_sense(table.Key_Lett, next_rel_chain)
          .assign rel_chain = reversed_chain.reversed_chain + rel_chain
        .else
          .assign relNumbAndPhrase = "R${relNumb}"
          .if(nextRelPhrase != "")
            .assign relNumbAndPhrase = relNumbAndPhrase + ".'${nextRelPhrase}'"
          .end if
          .assign rel_chain = "->${table.Key_Lett}[${relNumbAndPhrase}]" + rel_chain
        .end if
        .if(first path)
          .assign parent_element = element
          .assign parent_table = table
        .end if
      .end if
    .end for
    .invoke child_class_name = get_class_name(child_element)
    .if(params != "")
      .assign params = ", " + params
    .end if
    .invoke nav = generate_backward_rel_chain_nav_from_kl(parent_table.Key_Lett, rel_chain, cardinality, "(${child_class_name.body})${child_var}${params}", "", false);
${nav.body}
.end function
.//
.// this function serves as a wrapper around
.// do_search_root_components_in_children that 
.// fixes the failure of do_search_root_components_in_children
.// to find the Subsystem in Subsystem child component
.//
.function search_root_components_in_children
  .param inst_ref eo
  .//
  .invoke result = do_search_root_components_in_children(eo)
  .assign attr_roots = result.roots
  .if(eo.Name == "Subsystem")
    .// special case
    .// the subsystem in subsystem isn't detected by do_search_root_components_in_children
    .// so we need to add it to the roots
    .// find the Subsystem EO that isn't this Subsystem EO instance
    .// that will be the Subsystem In Subsystem EO
    .select any other_ss from instances of EO where ((selected.Name == "Subsystem") and (selected.Id != eo.Id))
    .assign attr_roots = attr_roots | other_ss
  .end if
.end function
.//
.// this method is internal and is used by only search_root_components_in_children
.function do_search_root_components_in_children
  .param inst_ref eo
  .select many attr_roots from instances of EO
  .assign attr_roots = attr_roots - attr_roots		
  .select one child related by eo->EO[R1.'is_first_child_of']
  .if(not_empty child)
    .if (child.componentRoot == true)
      .assign attr_roots = attr_roots | child
    .else
	  .invoke result = search_in_sibling_and_children(child)
      .assign attr_roots = attr_roots | result.roots
    .end if
  .end if
.end function
.//
.//
.// this method is internal and is used by only do_search_root_components_in_children
.function search_in_sibling_and_children
  .param inst_ref eo
  .select many attr_roots from instances of EO
  .assign attr_roots = attr_roots - attr_roots

  .invoke result = do_search_root_components_in_children(eo)
  .assign attr_roots =attr_roots | result.roots
	
  .select one child related by eo->EO[R2.'precedes']
  .while(not_empty child)
    .if (child.componentRoot == true)
      .assign attr_roots =attr_roots | child
    .else
      .invoke result = do_search_root_components_in_children(child)
      .assign attr_roots = attr_roots | result.roots
    .end if
   
   	.select one childsibling related by child->EO[R2.'precedes']
	.assign child = childsibling 
  .end while		
.end function
.function get_parent_eo
  .param inst_ref component_eo
  .param inst_ref child_eo
  .param inst_ref_set children_visited
    .assign children_visited = children_visited | child_eo
    .select any attr_parent from instances of EO where (selected.Name == "None")
    .assign attr_found_path = false
    .if(component_eo == child_eo)
      attr_parent = child_eo;
      attr_found_path = true;
    .else
      .select many parent_eos related by child_eo->EO[R1.'is_parent_of']
      .select one next_child related by child_eo->EO[R2.'follows']
      .while(empty parent_eos)
        .if(empty next_child)
          .break while
        .end if
        .select many parent_eos related by next_child->EO[R1.'is_parent_of']
        .if(empty parent_eos)
          .select one next_child related by next_child->EO[R2.'follows']
        .end if
      .end while
      .assign result = false;
      .// traverse until we find the component_eo
      .// given
      .for each parent in parent_eos
        .// if the parent found is a child
        .// that we have already visited
        .// ignore it
        .assign visited = false
        .for each child in children_visited
          .if(child == parent)
            .assign visited = true
            .break for
          .end if
        .end for
        .if(not visited)
          .if(parent == component_eo)
            .assign attr_found_path = true;
            .assign attr_parent = parent
            .break for
          .end if
          .invoke get_parent = get_parent_eo(component_eo, parent, children_visited)
          .if(get_parent.found_path)
            .// found the correct eo
            .assign attr_found_path = true
            .assign attr_parent = parent
            .break for
          .end if;
        .end if
      .end for
    .end if
.end function
.function getTypesForEO
  .param Inst_Ref eo
  .assign attr_types = "return new String[]{""$Cr{eo.Name}""};"
  .if(eo.Name == "Component Package")
     .assign attr_types = "\tString type1 = ""$Cr{eo.Name}"";\n"
     .assign attr_types = attr_types + "\tString type2 = ""ComponentDiagram"";\n"
     .assign attr_types = attr_types + "return new String[] {type1, type2};\n"
  .elif(eo.Name == "Interface Package")
     .assign attr_types = "\tString type1 = ""$Cr{eo.Name}"";\n"
     .assign attr_types = attr_types + "\tString type2 = ""InterfaceDiagram"";\n"
     .assign attr_types = attr_types + "return new String[] {type1, type2};\n"
  .end if 
.end function
.//
//========================================================================
// 
// File: ${class_name}.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.31 $$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
package com.mentor.nucleus.bp.io.mdl;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.*;
import com.mentor.nucleus.bp.core.util.SupertypeSubtypeUtil;
import com.mentor.nucleus.bp.ui.canvas.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class ${class_name} extends AbstractPersistenceHierarchyMetaData{

    public ${class_name}() {
    }

	public HashMap<String, List<NonRootModelElement>> getAssociationMapOfExternalRGOs(NonRootModelElement element) {
		return ExternalLinkEvaluator.getAssociationMapOfExternalRGOs(element);
	}
    public List findExternalRGOs(NonRootModelElement element) {
        return findExternalRGOs(element, false);
    }
    public List findExternalRGOs(NonRootModelElement element, boolean loadComponent, boolean checkSameComponent) {
        	return ExternalLinkEvaluator.findExternalRGOs(element, loadComponent,
							false, checkSameComponent);
    }
    public List findExternalRGOs(
        NonRootModelElement element,
        boolean loadComponent) {
        return ExternalLinkEvaluator.findExternalRGOs(
            element,
            loadComponent,false, true);
    }
    public List findExternalRGOsToContainingComponent(NonRootModelElement element) {
        return findExternalRGOsToContainingComponent(element, false);
    }
    public List findExternalRGOsToContainingComponent(
        NonRootModelElement element,
        boolean loadComponent) {
        return ExternalLinkEvaluator.findExternalRGOs(
            element,
            loadComponent,true, false);
    }
    public boolean hasExternalRGO(NonRootModelElement element) {
		return hasExternalRGO(element,false);
	}

	public boolean hasExternalRGO(NonRootModelElement element, boolean loadComponent) {
		return ExternalLinkEvaluator.hasExternalRGO(element,loadComponent);
	}
    protected void setComponentOfME(NonRootModelElement me, PersistableModelComponent component){
        super.setComponentOfME(me, component);
    }    

	public boolean isComponentRoot(NonRootModelElement rootElement){
.select many component_eos from instances of EO where (selected.componentRoot == true)
.select many filtered_component_eos from instances of EO where (selected.componentRoot == true)
.assign filtered_component_eos = filtered_component_eos - filtered_component_eos
.for each component_eo in component_eos
  .assign matchFound = false
  .for each eo in filtered_component_eos
    .if(component_eo.Name == eo.Name)
      .assign matchFound = true
      .break for
    .end if
  .end for
  .if(not matchFound)
    .assign filtered_component_eos = filtered_component_eos | component_eo
  .end if
.end for
.for each component_eo in filtered_component_eos
	.invoke cn = get_class_name(component_eo)
		if (rootElement instanceof ${cn.body}){
	.if (component_eo.Name == "Domain")
			Component_c parent = Component_c
					                 ..getOneC_COnR4204((Domain_c) rootElement);
			if (parent == null) {
				return true;
			}
			return false;
	.else
			return true;
	.end if	
		}
.end for
		return false;	
	}

    public String getComponentType(NonRootModelElement componentRoot){
.for each component_eo in filtered_component_eos
	.invoke cn = get_class_name(component_eo)
		if (componentRoot instanceof ${cn.body}){			
			return "$Cr{component_eo.Name}";	//$$NON-NLS-1$$			
		}
.end for 
		return null;   	
    }
    
    public String[] getComponentRootRootModelElementTypes(Class meClass){
.for each eo in component_eos
	.invoke cn = get_class_name(eo)
		if (meClass == ${cn.body}.class){
        .invoke eoTypes = getTypesForEO(eo)
		    ${eoTypes.types}
		}
.end for
.select many eos from instances of EO where ((selected.Card != "") AND (selected.writePosition != "none"))
.assign eos = eos - component_eos
.//
.assign non_graphics_eos = eos - eos
.for each eo in eos
    .assign addResult = true
    .for each ngeo in non_graphics_eos
      .if(eo.Name == ngeo.Name)
        .assign addResult = false
      .end if
    .end for
    .if(addResult)
      .select one table related by eo->EI[R3]->T[R4]
      .if (table.DomainName != "ooaofgraphics")
          .assign non_graphics_eos = non_graphics_eos | eo
      .end if
    .end if
.end for
.//
.for each eo in non_graphics_eos
	.invoke cn = get_class_name(eo)
    .//
  .select many visited_parents from instances of EO where (selected.Name == "NULL")
	.invoke result = get_all_parent_components_of_eo(eo, visited_parents, true)
	.assign component_eos = result.parents
		if (meClass == ${cn.body}.class){
		    return new String[]{\
    .assign unique_parents = component_eos - component_eos
    .for each eo1 in component_eos
		.assign addFlag=true    
	    .for each eo2 in unique_parents
	        .// Note that it is ok to use name instead of Id to assure this 
	        .// list is unique because it is only the Name attribute that we 
	        .// are using from the eo.  There many be many entries with the 
	        .// same name but different IDs at this point (many parents of the 
	        .// same type but peristed through a different path).
	        .// 
    		.if(eo1.Name == eo2.Name)
    		    .assign addFlag=false
    		.end if
    	.end for
    	.if(addFlag)
    	.assign unique_parents = unique_parents | eo1
    	.end if
    .end for
    
    .// TODO: FIXME: This is a special-case hack that allows specialized
    .//       functionality to operate as it did prior to generic packages.
    .//       As long as this special-case hack is in place, generic 
    .//       package persistence will not work properly for these
    .//       special-cased model elements.  The reason is that these
    .//       elements had only 1 possible root before generic packages
    .//       were introduced.  However, with generic packages, there
    .//       are multiple possible roots, but the code generation is
    .//       not correct for the case where there are multiple roots.
    .//       Too see this, look at the generated code for these model elements. 
	.if ( (eo.Name == "Data Type") or (eo.Name == "Core Data Type") ) 
"Domain"
      .assign unique_parents = unique_parents - unique_parents
	.end if
	.// TODO: FIXME: This is a special-case hack that is being allowed
	.//       only for an internal build.
	.if ( (eo.Name == "User Data Type") or (eo.Name == "Enumeration Data Type") )
"Domain"
      .assign unique_parents = unique_parents - unique_parents
	.end if    
	.// TODO: FIXME: This is a special-case hack that is being allowed
	.//       only for an internal build.
	.if ( eo.Name == "Dimensions")
"Interface"
      .assign unique_parents = unique_parents - unique_parents
	.end if
    .for each component_eo in unique_parents
        .if(not_first unique_parents)
,\
        .end if
"$Cr{component_eo.Name}"\
    .end for
};
		}
.end for 
		return null;   	
    }

    /**
     * 
     * @param element
     * @return the Root model element of the component in which the 'element' exists
     */
    public NonRootModelElement getComponentRootModelElement (NonRootModelElement element){
        // special case for PEs as their parent is not where they are stored
        // they are persisted with their subtype
        if(element instanceof PackageableElement_c) {
    		List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(element);
    		if(subtypes.size() != 0) {
    			return subtypes.get(0);
    		}
    	}
		NonRootModelElement parent = getParent(element);
		while(parent != null && !isComponentRoot(parent)){
			parent = getParent(parent);
		}
		
		return parent;
    }        

    public boolean isRootElementRenamable(PersistableModelComponent component) {
        String type = component.getComponentType();
        if(type.equals("DataTypePackage"))
	        if ( component.getName().equals(Ooaofooa.Getcoredatatypespackagename(null)))
	        	return false;
				return true;
		}

    public String getRootElementName (NonRootModelElement rootElement){
.for each component_eo in filtered_component_eos
	.invoke cn = get_class_name(component_eo)
		if (rootElement instanceof ${cn.body}){
	.if ((component_eo.Name != "Instance State Machine") and (component_eo.Name != "Class State Machine"))
			${cn.body} inst = (${cn.body})rootElement;
			return inst.getName();
	.else			
			return "$Cr{component_eo.Name}"; 	//$$NON-NLS-1$$	
	.end if
        }               
.end for
		return null;
    }
    
    public void setRootElementName(NonRootModelElement rootElement, String newName){
.assign first_if_block = true
.for each component_eo in filtered_component_eos
	.invoke cn = get_class_name(component_eo)
	.if ((component_eo.Name != "Instance State Machine") and (component_eo.Name != "Class State Machine"))	
		.if (first_if_block)
		    .assign first_if_block = false
		.else
		else     
		.end if
		if (rootElement instanceof ${cn.body}){
			${cn.body} inst = (${cn.body})rootElement;
			.if(component_eo.Name == "Model Class")
			inst.Rename(newName);
			.else
			inst.setName(newName);
			.end if
		}	
	.end if
.end for
        else{
            throw new IllegalArgumentException("set name not allowed on given model element:" + rootElement.getClass().getName());
        }
	}

   public List getChildrenComponentRootModelElements(NonRootModelElement modelElement) {
		return getChildrenComponentRootModelElements(modelElement, null);
   }

   public List getChildrenComponentRootModelElements(NonRootModelElement modelElement, Class type){
		Vector childrenComponent = new Vector();		
	.for each component_eo in filtered_component_eos
	  .invoke cn = get_class_name(component_eo)
	  .invoke result = search_root_components_in_children(component_eo)
	  .assign roots = result.roots
	  .if(not_empty roots)
		  .if(not_first filtered_component_eos)
		else
		  .end if
		if (modelElement instanceof ${cn.body}){
			.for each eo_child in roots 
        .select one child_table related by eo_child->EI[R3]->T[R4]
	   		.invoke child_name = get_class_name( child_table)

        .select many visitedChildren from instances of EO where (selected.Name == "None")
        .invoke get_parent = get_parent_eo(component_eo, eo_child, visitedChildren);
        .assign parent_eo = get_parent.parent
			  .assign result_card = eo_child.Card
			  
			  .assign rel_chain = ""
			  .if(eo_child.rel_chain != "")
			    .assign rel_chain = eo_child.rel_chain
			  .else
			    .assign relAndPhrase = "R${eo_child.Numb}"
			    .if(eo_child.rel_phrase != "")
			      .assign relAndPhrase = relAndPhrase + ".'${eo_child.rel_phrase}'"
			    .end if
			    .assign rel_chain = "->${child_table.Key_Lett}[${relAndPhrase}]"
			  .end if
			  
			  .//
			  .// The parent component and the parent in the tree are not the same,
        .// so we have to iterate over all the parents.
	      .//
	      .// build up a relationship chain
			  .if ((not_empty parent_eo) and (parent_eo != component_eo))
			    .assign middle_eo = eo_child
			    .while (not_empty middle_eo)      
			      .if((middle_eo != component_eo) and (middle_eo != eo_child))
			        .select one middle_table related by middle_eo->EI[R3]->T[R4]
			        .if(middle_eo.rel_chain != "")
			          .assign rel_chain = middle_eo.rel_chain + rel_chain
			        .else
			          .assign relAndPhrase = "R${middle_eo.Numb}"
			          .if(middle_eo.rel_phrase != "")
			            .assign relAndPhrase = relAndPhrase + ".'${middle_eo.rel_phrase}'"
			          .end if
			          .assign rel_chain = "->${middle_table.Key_Lett}[${relAndPhrase}]" + rel_chain
   			      .end if			        
  		      .end if
			      .// Get next parent
			      .select many visitedChildren from instances of EO where (selected.Name == "None")
            .invoke get_parent = get_parent_eo(component_eo, middle_eo, visitedChildren)
            .assign middle_eo = get_parent.parent
			    .end while
			  .end if
        
        .assign variableName = "${child_name.body}"
        .if(result_card == "many")
          .assign variableName = variableName + "_set"
        .else
          .assign variableName = variableName + "_inst"  
        .end if
        
   	    .invoke nav = generate_backward_rel_chain_nav_from_kl(child_table.Key_Lett, rel_chain, eo_child.Card, "(${cn.body})modelElement", variableName, false);
   	    .assign navigation = nav.body

  		  .//
		    .if ( result_card == "many" )
					    if(type == null || type ==  ${child_name.body}.class){
				  		  ${navigation}
								for ( int ${variableName}_index = 0;
					          ${variableName}_index < ${variableName}.length;
					          ++${variableName}_index )
						    {
						      childrenComponent.add(${variableName}[${variableName}_index]);
						    }
					    }
			  .else				     
					    if(type == null || type ==  ${child_name.body}.class){
				  		  ${navigation}
					     if(${variableName} != null){
						         childrenComponent.add(${variableName});
						     }
					    }
			  .end if
			.end for
		}
	  .end if   .// not_empty roots
	.end for
	return childrenComponent;		
   }
   public NonRootModelElement getParentComponentRootModelElement(NonRootModelElement element){
			return getParentComponentRootModelElement(element,true);
   }     
   public NonRootModelElement getParentComponentRootModelElement(NonRootModelElement element, boolean loadComponent){
       NonRootModelElement parent = null;
.//
.for each component_eo in filtered_component_eos
	.invoke cn = get_class_name(component_eo)
	.if (not_first filtered_component_eos)
        else
	.end if
		if (element instanceof ${cn.body}){
    .select many same_eos from instances of EO where ((selected.Name == component_eo.Name))
    .for each same_eo in same_eos
       .invoke result = get_path_from_parent_component(same_eo)
       .assign path_to_parent = result.path_to_parent_component
       .if (not_empty path_to_parent)
	       .assign parent = result.parent_component_eo
	       .invoke result = generate_chain(path_to_parent, "element", "loadComponent", "one")
	       parent = ${result.body};
	       if(parent != null){
	           return parent;
	       }
       .end if
    .end for
    .if (component_eo.Name == "Package")
           parent = Component_c.getOneC_COnR8003(PackageableElement_c
                          ..getOnePE_PEOnR8001((Package_c) element, loadComponent));
           if (parent != null) {
             return parent;
           }
    .elif (component_eo.Name == "Interface")
           parent = Component_c.getOneC_COnR8003(PackageableElement_c
                          ..getOnePE_PEOnR8001((Interface_c) element, loadComponent));
           if (parent != null) {
             return parent;
           }
    .elif (component_eo.Name == "Component")
           parent = Component_c.getOneC_COnR8003(PackageableElement_c
                          ..getOnePE_PEOnR8001((Component_c) element, loadComponent));
           if (parent != null) {
             return parent;
           }
    .elif (component_eo.Name == "Model Class")
           parent = Component_c.getOneC_COnR8003(PackageableElement_c
                          ..getOnePE_PEOnR8001((ModelClass_c) element, loadComponent));
           if (parent != null) {
             return parent;
           }
    .end if
       return null;
        }
.end for
return null;
    }
   
    public NonRootModelElement getParent(NonRootModelElement element){
        PersistableElementParentDetails details = getParentDetails(element);
        if(details == null) {
           return null;
        }
        return details.parent;
   }
    public PersistableElementParentDetails getParentDetails(NonRootModelElement element){
        PersistableElementParentDetails details = new PersistableElementParentDetails();
        details.child = element;
        NonRootModelElement parent = null;
.select many eos from instances of EO
.assign non_root_eos = eos - eos
.for each eo in eos
    .// excluding diagrams and repeated model elements
    .if ((eo.Card != "") and (eo.writePosition != "none"))
	    .assign non_root_eos = non_root_eos | eo
    .end if
.end for
.for each eo in non_root_eos
    .invoke me_class_name = get_class_name(eo)
    .if (not_first non_root_eos)
        else
    .end if
        if(element instanceof ${me_class_name.body}){
    .select one table related by eo->EI[R3]->T[R4]
       details.childKeyLetters = "${table.Key_Lett}";
       .if((eo.Card == "many") or (me_class_name.body == "PackageableElement_c")) 
       details.isMany = true;
       .else
       details.isMany = false;
       .end if
    .if(me_class_name.body == "PackageableElement_c")
      parent = Package_c.getOneEP_PKGOnR8000((PackageableElement_c) element);
      if(parent != null) {
      		details.parent = parent;
      		details.associationNumber = "8000";
			return details;
      }
      parent = Component_c.getOneC_COnR8003((PackageableElement_c) element);
      if(parent != null) {
      		details.parent = parent;
      		details.associationNumber = "8003";
			return details;
      }
    .else
    .select many same_eos from instances of EO where ((selected.Name == eo.Name) and (not selected.ignoreParent))
    .select many path_eos from instances of EO where (selected.Name == "None")
    .for each same_eo in same_eos
	    .invoke result = get_parent_eos(same_eo)
 	    .assign parent_eos = result.parent_eos
	    .if (not_empty parent_eos)
	        .for each parent in parent_eos
            .assign path_eos = parent | same_eo
	            .invoke result = generate_chain(path_eos, "element", "", "one")
	        parent = ${result.body};
	        if(parent != null){
	            details.parent = parent;
	            .if(same_eo.Numb != -1)
	            details.associationNumber = "${same_eo.Numb}";
	            details.associationPhrase = "${same_eo.rel_phrase}";
	          .else
                  .assign key_lett = ""
                  .assign child_child_chain = ""
                  .invoke parse = parse_chain(same_eo.rel_chain, "backward")
                  .invoke forward_parse = parse_chain(same_eo.rel_chain, "forward")
                  .assign relNumb = forward_parse.rel_num_str
                  .assign relPhrase = forward_parse.phrase
                  .assign child_chain = forward_parse.remainder
                  .while(parse.remainder != "")
                    .invoke parse = parse_chain(parse.remainder, "backward")
                    .assign key_lett = parse.key_lett
                    .assign relPhrase = parse.phrase
                    .assign relNumb = parse.rel_num_str
                  .end while
                  .invoke nav = generate_forward_rel_chain_nav(child_chain, key_lett, "${me_class_name.body}", "element", "")
	            details.child = ${nav.body};
	            details.childKeyLetters = "${key_lett}";
	            details.associationNumber = "${relNumb}";
	            details.associationPhrase = "${relPhrase}";
	          .end if
	            return details;
	        }
	        .end for
	    .end if
    .end for
    .end if
            return null;
        }
.end for
        else if (element instanceof Model_c){
            final Model_c model = (Model_c)element;
            ModelRoot modelRoot = getModelRoot(model);
            switch(model.getModel_type()){
                .select many gd_set from instances of GD
                .for each gd in gd_set
                    .select one model_data related by gd->T[R7]
                    .select one id_col related by gd->C[R8]
                    .invoke p = get_class_name( model_data )
                    .invoke a = get_attribute_accessor( id_col )
                case Modeltype_c.${gd.modelId}:
                     details.parent = ${p.body}.$Cr{model_data.bpName}Instance(modelRoot, new ClassQueryInterface_c(){
                         public boolean evaluate(Object selected){
                             return (((${p.body})selected).${a.body}().equals(model.getOoa_id()));
                         }
                     });
                     return details;
                .end for
            }
        }
        return null;
    }
    
    public List getChildren(final NonRootModelElement element, boolean componentOnly){
        List childList = new Vector(); 
        Object[] children;
        Object child;
.select many eos from instances of EO where ((selected.componentRoot==true) or (selected.WritePosition!="none"))
.for each eo in eos
    .invoke result = get_children(eo)
    .assign children = result.children
    .if(not_empty children)
        .invoke parent_class_name = get_class_name(eo)
        .//
        .if(not_first eos)
        else
        .end if
        if(element instanceof ${parent_class_name.body}){
	        .for each child in children
	            .select one child_table related by child->EI[R3]->T[R4]
	            .if(child.Numb != 0)
		            .invoke child_class_name = get_class_name(child)
       			    .assign rel_chain = ""
  			        .if(child.rel_chain != "")
 			            .assign rel_chain = child.rel_chain
			          .else
			            .assign relAndPhrase = "R${child.Numb}"
			            .if(child.rel_phrase != "")
			              .assign relAndPhrase = relAndPhrase + ".'${child.rel_phrase}'"
			            .end if
       			      .assign rel_chain = "->${child_table.Key_Lett}[${relAndPhrase}]"
			          .end if
                .invoke nav = generate_backward_rel_chain_nav_from_kl(child_table.Key_Lett, rel_chain, child.Card, "(${parent_class_name.body})element", "", false);
                .if ( child.componentRoot )
    		if ( !componentOnly )
	    	{
	                .end if
		            .if ( child.Card == "many" )
	            children = ${nav.body}
	            for(int i=0; i<children.length; i++){
	                childList.add(children[i]);
	            }
		            .else
	            child = ${nav.body};
	            if(child != null){
	                childList.add(child);
	            }
		            .end if
		            .if ( child.componentRoot )
		    }
		            .end if
	            .else
	                 .select one gd related by child->EI[R3]->GD[R4]
	                 .select one model_data related by gd->T[R7]
                     .select one id_col related by gd->C[R8]
                     .invoke p = get_class_name( model_data )
                     .invoke a = get_attribute_accessor( id_col )                
				class Model_test_${gd.modelId}_c implements ClassQueryInterface_c {
					public boolean evaluate(Object test_instance) {
						Model_c selected = (Model_c)test_instance;
						return selected.getOoa_id().equals(((${parent_class_name.body})element).${a.body}()) && selected.getModel_type() == Modeltype_c.${gd.modelId};
					}
				}
				Model_c model${gd.modelId} = Model_c.ModelInstance(getGraphicsModelRoot(element), new Model_test_${gd.modelId}_c());
				if(model${gd.modelId} != null){
				    childList.add(model${gd.modelId});
				}
	            .end if
	        .end for
	        .// TODO FIXME: This is a hack to assure Subsystems are included
	        .//             after a component is formalized to a domain.  It has 
	        .//             been added on 2007-06-30 to allow us to prepare an 
	        .//             Alpha release, but will need to be fixed properly
	        .//             before final release.
	        .if (parent_class_name.body == "Domain_c")
				if (!componentOnly) {
				    children = Subsystem_c.getManyS_SSsOnR1((Domain_c) element);
				    for (int i = 0; i < children.length; i++) {
						childList.add(children[i]);
					}
				}
	        .end if
	        .if(parent_class_name.body == "SystemModel_c")
  			if(!componentOnly) {
	  			children = DataTypePackage_c.getManyS_DPKsOnR4400(SystemDatatypePackage_c.getManySLD_SDPsOnR4400((SystemModel_c) element));
		  		for(int i = 0; i < children.length; i++) {
			  		childList.add(children[i]);
				  }
  			}
	        .end if
	        .if(parent_class_name.body == "Component_c")
  			children = PackageableElement_c.getManyPE_PEsOnR8003((Component_c) element);

			for (int i = 0; i < children.length; i++) {
				childList.add(children[i]);
			}
	        .end if
        }
        
        .assign count = cardinality children
        .select one eo_table related by eo->EI[R3]->T[R4]
        .//print "${eo.Name} resulted in ${count} children and is from ${eo_table.DomainName} domain"
    .end if
.end for
       return childList;
    }
    
	private static HashMap associationFormalizers = new HashMap();
	static {
.select many rel_set from instances of R_REL
.for each rel in rel_set
  .select many rgo_set related by rel->R_OIR[R201]->R_RGO[R203]
  .select many form_obj_set related by rgo_set->R_OIR[R203]->O_OBJ[R201]
		associationFormalizers.put("${rel.Numb}", new Class[]{ \
  .for each form_obj in form_obj_set
    .invoke p = get_class_name( form_obj )
${p.body}.class\
    .if (not_last form_obj_set)
, \
    .end if
  .end for
});
.end for
	};

	public NonRootModelElement getAssociationRootModelElement(
		String associationName,
		NonRootModelElement object1,
		NonRootModelElement object2) {
		Class [] f = (Class [])associationFormalizers.get(associationName);
		if ( f != null )
		  // f will be null for graphics associations
		{
	        for ( int i = 0; i < f.length; ++i )
	        {
	        	if (f[i] == object1.getClass()) return object1;
	            if (object2 != null && f[i] == object2.getClass()) return object2;
	        }
        }

		return null;
	}

    private ModelRoot getGraphicsModelRoot(NonRootModelElement element){
        return Ooaofgraphics.getInstance(element.getModelRoot().getId());
    }

    private ModelRoot getModelRoot(NonRootModelElement graphicsElement){
        return Ooaofooa.getInstance(graphicsElement.getModelRoot().getId());
    }
    
    public NonRootModelElement[] getActivityModelElements(NonRootModelElement element, Class type){
.invoke result = get_all_seperately_configurable_elements()
.assign root_eos = result.value
.for each root_eo in root_eos
    .invoke eo_class_name = get_class_name(root_eo)
    .if (not_first root_eos)
        else
    .end if
        if(element instanceof ${eo_class_name.body}){
    .invoke result = generate_nav_to_parent("Function", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Bridge", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Operation", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Derived Base Attribute", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("State Machine State", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Transition", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Required Operation", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Required Signal", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Provided Operation", root_eo)
            ${result.body}
    .invoke result = generate_nav_to_parent("Provided Signal", root_eo)
            ${result.body}
            return null;
        }
.end for
        else{
            throw new IllegalArgumentException("Given model element is not a component root");
        }
    }
}
.//
.emit to file "src/com/mentor/nucleus/bp/io/mdl/${class_name}.java"
.//
.//