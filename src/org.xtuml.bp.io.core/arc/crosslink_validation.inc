.//====================================================================
.//
.// File:      $RCSfile: crosslink_validation.inc,v $
.// Version:   $Revision: 1.9 $
.// Modified:  $Date: 2013/01/10 23:07:22 $
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
.invoke arc_env = GET_ENV_VAR( "CORE_ARC_DIR" )
.assign core_archetypes = arc_env.result
.if ( core_archetypes == "" )

   
  .print "\nERROR: Environment variable CORE_ARC_DIR not set."
  .exit 100
.end if
.include "${core_archetypes}/parse_chain.inc"
.//
.function isOriginator .// Helper function
  .param inst_ref link
  .param string from_Key_Lett
  .assign attr_found = FALSE
.//  
  .select one prev_link related by link->ACT_LNK[R604.'succeeds']
  .if(not_empty prev_link) 
  	.if(prev_link.Target_Obj_KL == from_Key_Lett)
		.assign attr_found = TRUE
	.end if
  .else
	.select one var related by link->ACT_SEL[R637]->V_VAR[R613]
	.select any inst related by var->V_INT[R814]
	.select one class related by inst->O_OBJ[R818]
	.if(empty class)
		.select one class1 related by var->V_INS[R814]->O_OBJ[R819]
		.assign class = class1
	.end if
	.if(class.Key_Lett == from_Key_Lett)
		.assign attr_found = TRUE
	.end if
  .end if
.//   
.end function
.//
.// Use this function to find a chain in OAL of the ooaofooa
.//
.function isChainInOAL
  .param string from_Key_Lett
  .param string to_Key_Lett
  .param string relation_Name    .// Usage :- e.g. "R10", "R5". 'R' is necessary
  .param string relational_Phrase  
.//
	.assign phrase = ""
	.if(relational_Phrase!= "")
  		.assign phrase = ".'${relational_Phrase}'"
	.end if
	.assign attr_query = "${from_Key_Lett}->${to_Key_Lett}[${relation_Name}${phrase}]"
	.assign attr_result = FALSE
.//  
	.select many links from instances of ACT_LNK
	.for each link in links
.//  		  			
	.//CHECK THE TARGET CLASS ('TO' CLASS) NAME
		.if(link.Target_Obj_KL == to_Key_Lett)
			.//NOW CHECK THE SOURCE CLASS ('FROM' CLASS) NAME
			.invoke source = isOriginator(link,from_Key_Lett)
			.if(source.found == TRUE)
				.//NOW CHECK THE RELATIONSHIP NAME	
				.select one relation related by link->R_REL[R681]
				.if(not_empty relation)
					.assign relationName = "R${relation.Numb}"
					.if(relationName == relation_Name)
					  	.//NOW WHEN EVERYTHING HAS BEEN FOUND SUCCESSFULLY,  
						.//SO CHECK THE RELATIONAL PHRASE AND BASING ON THAT 
						.//ASSIGN THE TRUE TO FRAGMENT VARIABLE AND BREAK THE LOOP.
						.assign attr_result = TRUE
						.//NOW CHECK THE RELATIONAL PHRASE
						.if(relational_Phrase!= "")
							.if(link.Rel_Phrase == relational_Phrase)
				  	 			.break for
					  		.else
								.assign attr_result = FALSE						  			
					  		.end if
					  	.else 
						  	.break for
				  		.end if					
					.end if
				.end if
			.end if
		.end if
	  .end for
.end function
.//
.// Use this function to find a chain in Heirarchy model.
.//
.function isChainInHierarchy
  .param string from_Key_Lett
  .param string to_Key_Lett
  .param string relation_Name .// Usage :- e.g. "R10", "R5". 'R' is necessary
.//	
	.assign attr_query = "${from_Key_Lett}->${to_Key_Lett}[${relation_Name}]"
	.assign attr_result = FALSE
	.select many relationSpecs from instances of T_TPS
  	.for each spec in relationSpecs 
		.assign chain = spec.parentChildRelChain
		.select one node related by spec->T_TNS[R1000]
		.assign fromSearchClass_Key_Lett = node.Key_Lett
		.while("$r{chain}"!= "")
			.invoke nav = parse_chain(chain,"forward")
			.assign toclass = nav.class
			.assign rel = nav.rel 
			.if(fromSearchClass_Key_Lett == from_Key_Lett )
				.if(toclass.Key_Lett == to_Key_Lett)
					.if(relation_Name == "R${rel.Numb}")
						.assign attr_result = TRUE
						.break while
					.end if
				.end if  	 			   
			.end if
			.assign chain = nav.remainder
  	 		.assign fromSearchClass_Key_Lett = "${toclass.Key_Lett}"
		.end while
		.if(attr_result == TRUE)
			.break for
		.end if
	.end for 
.end function
.//
.// 
.function isChainInCustomChains
  .param string from_Key_Lett
  .param string to_Key_Lett
  .param string relation_Name    .// Usage :- e.g. "R10", "R5". 'R' is necessary
.//
	.assign attr_result = false
.//
.end function
.//
.//
.function validateCrossLink
  .param string from_Key_Lett
  .param string to_Key_Lett
  .param string relation_Name    .// Usage :- e.g. "R10", "R5". 'R' is necessary
  .param string relational_Phrase
.//
.//
	.assign attr_from = ""
	.invoke isChainInOALFunc = isChainInOAL(from_Key_Lett, to_Key_Lett, relation_Name, relational_Phrase)
	.if (isChainInOALFunc.result)
		.assign attr_from = attr_from + " ChainInOAL"
	.end if
	.invoke isChainInHierarchyFunc = isChainInHierarchy(from_Key_Lett, to_Key_Lett, relation_Name)
	.if (isChainInHierarchyFunc.result)
		.assign attr_from = attr_from + " ChainInHierarchy"	
	.end if	
	.invoke isChainInCustomChains = isChainInCustomChains(from_Key_Lett, to_Key_Lett, relation_Name)
	.if (isChainInCustomChains.result)
		.assign attr_from = attr_from + " CustomChain"	
	.end if
	.assign attr_result = ((isChainInOALFunc.result or isChainInHierarchyFunc.result) or (isChainInCustomChains.result))
.end function
