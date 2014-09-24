.//====================================================================
.//
.// File:      $RCSfile: export_functions.inc,v $
.// Version:   $Revision: 1.48.18.1 $
.// Modified:  $Date: 2013/07/08 14:38:19 $
.//
.// (c) Copyright 2007-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "../com.mentor.nucleus.bp.io.core/arc/generate_configurable_components.inc"
.//
.function sourceVariables
  .invoke package = get_package()
  .if(package.application_root_class == "Ooaofooa")
    .invoke compMark = markComponentsAndContainments()
  .end if
  .assign attr_main_class_name = "${package.application_root_class}"
  .// Subordinate package: Canvas
  .assign attr_sub_class_name = "Ooaofgraphics"
  .//
.end function
.//
.//====================================================================
.//
.function gen_write_attr_function
  .param inst_ref table
  .param string callingProject
  .if (callingProject == "BP")
    .if (table.Key_Lett == "GD_MD")
  Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(inst);
    .elif (table.Key_Lett == "GD_SHP")
  Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(inst);
  Graphelement_c graphElem = Graphelement_c.getOneDIM_GEOnR301(node);
    .elif (table.Key_Lett == "GD_LS")
  Waypoint_c start = Waypoint_c.getOneDIM_WAYOnR21(inst);
  Waypoint_c end = Waypoint_c.getOneDIM_WAYOnR22(inst);
    .elif (table.Key_Lett == "GD_CTXT")
        // all three connector texts that belong to one connector
        // are written out as one, so if one of other texts that belongs
        // to the same connector has already been processed, we can ignore
        // this one
        long connId = inst.getConn_elementidLongBased();
        Long key = new Long(connId);
        if (connectorIds.get(key) != null) return;

        // store that the texts of this text's connector have
        // been exported, so that the others don't need to be processed
        connectorIds.put(key, key);
    .end if
  .end if
  .//
  .// find the first column to output
  .//
  .select any col related by table->C[R5] where ( selected.nextName == "" )
  .select many col_set related by table->C[R5]
  .assign x = (cardinality col_set) - 1
  .while ( x > 0 )
    .assign x = x - 1
    .select one col related by col->C[R6.'follows']
  .end while
  .//
  .assign nf_output = false
  .while ( not_empty col )
    .//
    .invoke a = get_attribute_accessor( col )
    .if ( col.type == "string" )
  m_fh.print( "'"); //$$NON-NLS-1$$
  m_fh.print( inst.${a.body}().replaceAll( "'", "''" ) ); //$$NON-NLS-1$$ //$$NON-NLS-2$$
  m_fh.print( "'"); //$$NON-NLS-1$$
    .elif ( col.type == "boolean" )
      .if((table.Key_Lett == "S_SYS") and (col.Name == "useGlobals"))
      if(inst.getModelRoot().isCompareRoot()) {
  m_fh.print("1");  //$$NON-NLS-1$$ //$$NON-NLS-2$$
      } else {
  m_fh.print( (inst.${a.body}()) ? "1" : "0" );  //$$NON-NLS-1$$ //$$NON-NLS-2$$
      }
      .else
  m_fh.print( (inst.${a.body}()) ? "1" : "0" );  //$$NON-NLS-1$$ //$$NON-NLS-2$$
      .end if
    .elif ( col.type == "real" )
      .if ( not nf_output )
  NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
  nf.setMinimumFractionDigits(6);
  nf.setGroupingUsed(false);
        .assign nf_output = true
      .end if
      .if ((callingProject == "BP") and ((table.Key_Lett == "GD_MD") and (col.Name == "ZoomFactor")))
  m_fh.print(nf.format(diagram.getZoom()));
      .else
  m_fh.print( nf.format(inst.${a.body}()) );
      .end if
    .elif ( col.type == "inst_ref<Mapping>" )
  m_fh.print( "0" );  //$$NON-NLS-1$$
    .elif ( col.type == "unique_id" )
      .if (callingProject == "BP")
        .if ((table.Key_Lett == "GD_MD") and (col.Name == "Model_ID"))
  m_fh.print(inst.getDiagramidLongBased());
        .elif ((table.Key_Lett == "GD_GE") and (col.Name == "Model_ID"))
  m_fh.print(inst.getDiagramidLongBased());
        .elif ( (table.Key_Lett == "GD_GE") and (col.Name == "OOA_ID") )
  if (inst.getOoa_type() == Ooatype_c.AssociativeLink || inst.getOoa_type() == Ooatype_c.Subtype ) {
      m_fh.print( 0 );
  }
  else {
  m_fh.print( inst.${a.body}LongBased() );
  }
        .else
  m_fh.print( inst.${a.body}LongBased() );
        .end if
      .else
  m_fh.print("\"");    //$$NON-NLS-1$$
        .if(col.isReferential)
  m_fh.print( (inst.${a.body}().equals(IdAssigner.NULL_UUID) && outputCachedIDs && inst.isProxy()) ? inst.${a.body}CachedValue():inst.${a.body}());
        .else
  m_fh.print(inst.${a.body}());
        .end if
  m_fh.print("\"");    //$$NON-NLS-1$$
      .end if
    .else
      .if (callingProject == "BP")
        .if ((table.Key_Lett == "GD_GE") and (col.Name == "GE_ID"))
      m_fh.print(inst.getElementidLongBased());
        .elif ((table.Key_Lett == "GD_SHP") and (col.Name == "GE_ID"))
      m_fh.print(inst.getElementidLongBased());
        .elif ((table.Key_Lett == "GD_CON") and (col.Name == "GE_ID"))
      m_fh.print(inst.getElementidLongBased());
        .elif ((table.Key_Lett == "GD_CON") and (col.Name == "Assoc_GE_ID"))
      m_fh.print(inst.getAssoc_elementidLongBased());
        .elif ((table.Key_Lett == "GD_MD") and (col.Name == "ScrollXPos"))
      m_fh.print((int)diagram.getViewportx());
        .elif ((table.Key_Lett == "GD_MD") and (col.Name == "ScrollYPos"))
          .//
          .// this column is special, because the vertical scroll
          .// position needs to be adjusted for the different coordinate
          .// systems between BP 6.1 and Tiger
    m_fh.print(6000 - (int)diagram.getViewporty() - (int)(621 / diagram.getZoom()));
        .elif ( (table.Key_Lett == "GD_GE") and (col.Name == "OOA_Type") )
  if (inst.getOoa_type() == Ooatype_c.AssociativeLink || inst.getOoa_type() == Ooatype_c.Subtype ) {
      m_fh.print( -1 );
  }
  else if (inst.getOoa_type() == Ooatype_c.Supertype) {
      m_fh.print( Ooatype_c.Association );
  }
  else if (inst.getOoa_type() == Ooatype_c.CreationTransition) {
      m_fh.print( Ooatype_c.Transition );
  }
  else {
      m_fh.print( inst.${a.body}() );
  }
        .elif ( (table.Key_Lett == "R_RTO") and (col.Name == "Oid_ID") )
  if ( inst.${a.body}() == -1 ) {
    m_fh.print( 99 );
  }
  else {
    m_fh.print( inst.${a.body}() );
  }
        .elif ((table.Key_Lett == "GD_SHP") and (col.Name == "NW_X"))
  m_fh.print((int)graphElem.getPositionx());
        .elif ((table.Key_Lett == "GD_SHP") and (col.Name == "NW_Y"))
  m_fh.print((int)graphElem.getPositiony());
        .elif ((table.Key_Lett == "GD_SHP") and (col.Name == "SE_X"))
  m_fh.print((int)graphElem.getPositionx() + (int)node.getWidth());
        .elif ((table.Key_Lett == "GD_SHP") and (col.Name == "SE_Y"))
  m_fh.print((int)graphElem.getPositiony() + (int)node.getHeight());
      .elif ((table.Key_Lett == "GD_CON") and (col.Name == "Starting_GE_ID"))
    GraphicalElement_c startElem =
        GraphicalElement_c.getOneGD_GEOnR23(
            Graphelement_c.getOneDIM_GEOnR311(
                Graphconnector_c.getOneDIM_CONOnR320(
                    Graphedge_c.getOneDIM_EDOnR20(inst))));
  m_fh.print((startElem != null) ? startElem.getElementidLongBased() : 0);
      .elif ((table.Key_Lett == "GD_CON") and (col.Name == "Ending_GE_ID"))
    GraphicalElement_c endElem =
        GraphicalElement_c.getOneGD_GEOnR23(
            Graphelement_c.getOneDIM_GEOnR311(
                Graphconnector_c.getOneDIM_CONOnR321(
                    Graphedge_c.getOneDIM_EDOnR20(inst))));
  m_fh.print((endElem != null) ? endElem.getElementidLongBased() : 0);
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "GE_ID"))
      m_fh.print(inst.getElementidLongBased());
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "conn_GE_ID"))
      m_fh.print(inst.getConn_elementidLongBased());
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "Previous_GE_ID"))
      m_fh.print(inst.getPrevious_elementidLongBased());
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "Start_X"))
  m_fh.print((int)start.getPositionx());
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "Start_Y"))
  m_fh.print((int)start.getPositiony());
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "End_X"))
  m_fh.print((int)end.getPositionx());
        .elif ((table.Key_Lett == "GD_LS") and (col.Name == "End_Y"))
  m_fh.print((int)end.getPositiony());
        .elif ((table.Key_Lett == "GD_CTXT") and (col.Name == "GE_ID"))

    // write out the connector id
        m_fh.print(connId);

        // retrieve the three texts of the given text's connector
        Connector_c conn = Connector_c.getOneGD_CONOnR8(inst);
        FloatingText_c[] texts = FloatingText_c.getManyGD_CTXTsOnR8(conn);

    // for each end on which a connector has a text
        String delim = ",\n\t"; //$$NON-NLS-1$$
        int[] ends = {End_c.Start, End_c.Middle, End_c.End};
        for (int i = 0; i < ends.length; i++) {
          // get the text that is for this end
            FloatingText_c text = getTextOfEnd(ends[i], texts);

            // write out the data for this text
            Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(text);
            Graphelement_c element = Graphelement_c.getOneDIM_GEOnR301(node);
            m_fh.print(delim + (int)element.getPositionx());
            m_fh.print(delim + (int)element.getPositiony());
            m_fh.print(delim + (int)(element.getPositionx() + node.getWidth()));
            m_fh.print(delim + (int)(element.getPositiony() + node.getHeight()));
            m_fh.print(delim + (int)text.getDeltax());
            m_fh.print(delim + (int)text.getDeltay());
        }
        .elif ((table.Key_Lett == "GD_CTXT"))
          .// we let the above case for GE_ID handle the export of all
          .// fields of the instance
        .else
  m_fh.print( inst.${a.body}() );
        .end if
        .if ((table.Key_Lett == "S_DOM")  and (col.Name == "Full_Der"))
  m_is_fully_derived = inst.getFull_der() != 0;
        .end if
      .else
        .if(col.isReferential)
  m_fh.print( outputCachedIDs && inst.isProxy() ? inst.${a.body}CachedValue():inst.${a.body}());
        .else
  m_fh.print(inst.${a.body}());
        .end if
      .end if
    .end if
    .//
    .select one col related by col->C[R6.'precedes']
    .if ( not_empty col )
      .if ( (table.Key_Lett == "CA_SMSMC") and (col.Name == "OIObj_ID") )
  if ( ! m_is_fully_derived )
  {
      .end if
      .// if code we're currently outputting is for BP 6.1 connector-texts, we
      .// don't need to output the enclosed delimeter, since the code
      .// for all columns is generated together in one block, above
      .if ((callingProject != "BP") or (table.Key_Lett != "GD_CTXT"))
  m_fh.print(",\n\t");  //$$NON-NLS-1$$
      .end if
    .else
      .if (table.Key_Lett == "CA_SMSMC")
  }
      .end if
    .end if
  .end while
.end function
.//
.//====================================================================
.//
.function gen_write_sql_function
  .param inst_ref table
  .param string callingProject
  .param boolean multilevel
  .param boolean stream
  .// T
  .//
  .invoke r = get_class_name( table )
  .invoke attrs = gen_write_attr_function(table, callingProject)
void write_${r.body}_sql(${r.body} inst)
throws IOException
{
if(inst != null && !inst.isProxy()){
  .if ((multilevel) and (not stream))
  writtenProxies.add(inst);
  .end if
  .if(stream)
  if(writtenInstances.contains(inst)) return;
  writtenInstances.add(inst);
  .end if
  m_fh.print( "INSERT INTO ${table.Key_Lett}\n\tVALUES (" ); //$$NON-NLS-1$$
  .if ( multilevel )
  write_${r.body}_attr_sql(inst);
  .else
${attrs.body}\
  .end if
  m_fh.print(");\n"); //$$NON-NLS-1$$
}
}

  .if (multilevel)
public void write_${r.body}_proxy_sql(${r.body} inst)
throws IOException
{
    .if(stream)
  if ( inst != null && !writtenProxies.contains(inst) && !writtenInstances.contains(inst))
    .else
  if ( inst != null && !writtenProxies.contains(inst))
    .end if
  {
  writtenProxies.add(inst);
    m_fh.print( "INSERT INTO ${table.Key_Lett}_PROXY\n\tVALUES (" ); //$$NON-NLS-1$$
    write_${r.body}_attr_sql(inst);
    m_fh.print(",\n\t"); //$$NON-NLS-1$$
    m_fh.print("'" + (!inst.getModelRoot().isCompareRoot() ? inst.getContent(m_inst.getPersistableComponent().getFullPath()):inst.getContent()) + "'");
    m_fh.print(");\n"); //$$NON-NLS-1$$
  }
}

private void write_${r.body}_attr_sql(${r.body} inst)
throws IOException
{
${attrs.body}\
}
  .end if
.end function
.//
.//====================================================================
.//
.function gen_proxy_export
  .param inst_ref obj   .// O_OBJ
  .param inst_ref rel
  .param integer proxy_index
  .param boolean checkParentForComponentContainment
  .param boolean many
  .//
  .invoke rto_cn = get_class_name(obj)
  .assign temp_var = "temp${proxy_index}"
  .if(not many)
            ${rto_cn.body} ${temp_var} = ${rto_cn.body}.getOne$U_{obj.Key_Lett}OnR${rel.Numb}(inst,true);
  .if(checkParentForComponentContainment)
	.// TODO The code below works for references to data
    .// types only. This all that the current global
	.// definitions include. The globals infrastructure
	.// supports any subtype of Packageable Element, but
	.// before this can be leveraged, the code below will
	.// have to be expanded to support this or else PMC
	.// exceptions will be thrown.
	.if ((obj.Name == "Data Type") or (obj.Name == "Core Data Type"))
	  	  boolean isGlobal = false;
	  .if (obj.Name == "Data Type")
		  GlobalElementInSystem_c geis =
				     GlobalElementInSystem_c.getOneG_EISOnR9100(
							      PackageableElement_c.getOnePE_PEOnR8001(${temp_var}));
	  .else
		  GlobalElementInSystem_c geis =
				     GlobalElementInSystem_c.getOneG_EISOnR9100(
							      PackageableElement_c.getOnePE_PEOnR8001(DataType_c.getOneS_DTOnR17(${temp_var})));
	  .end if
		  if (geis != null) {
		  	isGlobal = true;
		  }
		  // Global elements are not persisted, so do not throw an exception in this case
		  if(!isGlobal && ${temp_var} != null) {
	.else
		  if(${temp_var} != null) {
	.end if
                PersistableModelComponent inst_pmc = m_inst.getPersistableComponent();
                PersistableModelComponent ${temp_var}_pmc = ${temp_var}.getPersistableComponent();
                if ((( inst_pmc != null ) && ( ${temp_var}_pmc != null )) || (m_inst.getModelRoot().isCompareRoot() && ${temp_var}.isProxy()))
                {
                    if((m_inst.getModelRoot().isCompareRoot() && ${temp_var}.isProxy()) || !m_inst.getPersistableComponent().getFullPath().isPrefixOf(
                                  ${temp_var}.getPersistableComponent().getFullPath()))
                  {
  .else
    .assign checkPkgStmt = ""
    .if ((rel.Numb == 8001) and (obj.Name == "Packageable Element"))
            Package_c tempPKG = Package_c.getOneEP_PKGOnR8000(${temp_var});
            Component_c tempCOMP = Component_c.getOneC_COnR8003(${temp_var});
      .assign checkPkgStmt = "(tempPKG != null || tempCOMP != null) &&"
    .end if
            if (${checkPkgStmt} NonRootModelElement.shouldWriteProxy( ${temp_var}, inst ) )
            {
  .end if
  .if(checkParentForComponentContainment)
                ProxyPath proxyPath = new ProxyPath(${temp_var}, m_inst);
                collectedProxies.add(proxyPath);
  .else
                write_${rto_cn.body}_proxy_sql(${temp_var});
  .end if
  .else
            ${rto_cn.body}[] ${temp_var}_set = ${rto_cn.body}.getMany$U_{obj.Key_Lett}sOnR${rel.Numb}(inst,true);
            for(int i = 0; i < ${temp_var}_set.length; i++) {
    .if(checkParentForComponentContainment)
                  ProxyPath proxyPath = new ProxyPath(${temp_var}_set[i], m_inst);
                  collectedProxies.add(proxyPath);
    .else
                  write_${rto_cn.body}_proxy_sql(${temp_var}_set[i]);
    .end if
  .end if
  .if(checkParentForComponentContainment)
    .// special case for writing dt proxy for cdt proxy
    .// this is required by the stream export so that
    .// udts may fully be resolved, this is different
    .// then the special case below as this is for all
    .// times that a UDT is exported.  The one below is
    .// for when the UDT is directly copied, instead of
    .// through it's parent.
    .if((rel.Numb == 18) and (obj.Name == "Core Data Type"))
                DataType_c dt = DataType_c.getOneS_DTOnR17(${temp_var});
                proxyPath = new ProxyPath(dt, m_inst);
                collectedProxies.add(proxyPath);
    .elif((rel.Numb == 527) and (obj.Name == "Polymorphic Event"))
      .// special case to allow event proxy, this is required
      .// when an imported class is copied and the representing
      .// class is not.  We also want to export the proxy for the
      .// event's state machine to allow name resolution
                 StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR525(${temp_var});
                proxyPath = new ProxyPath(evt, m_inst);
                collectedProxies.add(proxyPath);
                StateMachine_c sm = StateMachine_c.getOneSM_SMOnR502(evt);
                proxyPath = new ProxyPath(sm, m_inst);
                collectedProxies.add(proxyPath);
                InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR517(sm);
                proxyPath = new ProxyPath(ism, m_inst);
                collectedProxies.add(proxyPath);
    .end if
    .if(not many)
              }
                } else {
                  .// The following is an error check to determine if the
                  .// in-memory model matches the persisted model.  Note that
                  .// some type (for instance OAL instance Types) are persisted
                  .// in the stream export mechanism but not in the model
                  .// file.  This test is only valid for type that we expect
                  .// should have a PMC.
                  if (isPersistable && !ignoreMissingPMCErrors) {
                      if ( ${temp_var}.getPersistableComponent() == null ) {
                            Throwable e = new Throwable("Unable to locate PMC: " + ${temp_var}.getContent());
                            e.fillInStackTrace();
                            CorePlugin.logError("Unable to locate PMC: " + ${temp_var}.getContent(), e);
                      }					  
                      if ( m_inst.getPersistableComponent() == null ) {
                          Throwable e = new Throwable("Unable to locate PMC: " + m_inst.getContent());
                          e.fillInStackTrace();
                          CorePlugin.logError("Unable to locate PMC: " + m_inst.getContent(), e);
                      }
                  }
                }
    .end if
  .end if
                }
  .if(checkParentForComponentContainment)
    .// special case for writing dt proxy for cdt proxy
    .// this is required by the stream export so that
    .// udts may fully be resolved
    .if((rel.Numb == 18) and (obj.Name == "Core Data Type"))
                if(m_inst instanceof UserDataType_c) {
                  CoreDataType_c coreType = CoreDataType_c.getOneS_CDTOnR17(DataType_c.getOneS_DTOnR18(inst, false));
                  ProxyPath proxyPath = new ProxyPath(coreType, m_inst);
                  collectedProxies.add(proxyPath);
                  DataType_c dt = DataType_c.getOneS_DTOnR17(coreType);
                   proxyPath = new ProxyPath(dt, m_inst);
                  collectedProxies.add(proxyPath);
                }
    .elif((rel.Numb == 45) and (obj.Name == "Data Type"))
                if(m_inst instanceof StructuredDataType_c) {
                   DataType_c dt = DataType_c.getOneS_DTOnR45(inst);
                  ProxyPath proxyPath = new ProxyPath(dt, m_inst);
                  collectedProxies.add(proxyPath);
                }
    .end if
  .end if
.end function
.//
.//====================================================================
.//
.function is_containment
  .param inst_ref rel   .// R_REL
  .assign attr_result = false
.end function
.function should_export_rgo_proxy
  .param inst_ref object
  .param integer rel_numb
    .assign attr_result = false
    .if(object.Key_Lett == "C_IO")
      .assign attr_result = true
    .elif(object.Key_Lett == "C_PP")
      .assign attr_result = true
    .elif(object.Key_Lett == "S_BPARM")
      .assign attr_result = true
    .elif(object.Key_Lett == "S_BRG")
      .assign attr_result = true
    .elif(object.Key_Lett == "S_SYNC")
      .assign attr_result = true
    .elif(object.Key_Lett == "S_SPARM")
      .assign attr_result = true
    .elif(object.Key_Lett == "O_TFR")
      .assign attr_result = true
    .elif(object.Key_Lett == "O_TPARM")
      .assign attr_result = true
    .elif(object.Key_Lett == "S_MBR")
      .assign attr_result = true
    .elif(object.Key_Lett == "O_ATTR")
      .assign attr_result = true
    .elif(object.Key_Lett == "V_VAL")
      .assign attr_result = true
    .elif(object.Key_Lett == "V_VAR")
      .assign attr_result = true
    .elif(object.Key_Lett == "SM_EVTDI")
      .assign attr_result = true
    .elif((object.Key_Lett == "S_UDT") and (rel_numb != 17))
	  .assign attr_result = true
	.elif(object.Key_Lett == "C_IR")
	  .assign attr_result = true
	.elif(object.Key_Lett == "CL_IC")
	  .assign attr_result = true
	.elif(object.Key_Lett == "SQ_COP")
	  .assign attr_result = true
    .elif(object.Key_Lett == "SPR_PEP")
	  .assign attr_result = true
	.elif(object.Key_Lett == "SPR_REP")
	  .assign attr_result = true	
	.elif(object.Key_Lett == "CL_IIR")
	  .assign attr_result = true	
	.elif(object.Key_Lett == "CL_POR")
	  .assign attr_result = true	  
	.end if
.end function
.//
.//  This function generates a method
.//  for each EO element which handles
.//  the exporting of the element and
.//  children
.//
.function gen_export_methods
  .param boolean multilevel
  .param String domain_class_name
  .param String tool
  .param boolean checkParentForComponentContainment
  .//
  .// Generate the export method stubs
  .//
  .select many eo_set from instances of EO
  .for each eo in eo_set
    .invoke fOcc=  firstOccurrence(eo)
    .assign export=fOcc.firstOccurrence
    .if (export)
      .// output all export functions
      .assign parent_name = ""
      .assign parent_arg_type = ""
      .assign extra_args = ""
      .select one parent_table related by eo->EI[R3]->T[R4]
      .if ( not_empty parent_table )
        .invoke p = get_class_name( parent_table )
        .assign parent_name = "${p.body}"
        .assign parent_arg_type = "${p.body}"
      .else
        .select one parent_gd related by eo->EI[R3]->GD[R4]
        .assign parent_name = "${parent_gd.modelName}"
        .select one parent_parent related by parent_gd->T[R7]
        .invoke p = get_class_name( parent_parent )
        .assign parent_arg_type = "${p.body}"
        .assign extra_args = ", pm"
      .end if
      .assign addExportRGOProxyCall = false
      .if(not_empty parent_table)
        .select any class from instances of O_OBJ where (selected.Key_Lett == parent_table.Key_Lett)
        .select one root_pkg related by class->PE_PE[R8001]->EP_PKG[R8000]->PE_PE[R8001]->EP_PKG[R8000]
        .// no need to export RGO proxies for ooaofgraphics
        .// currently only data types are supported
		.invoke rgoResolution = rgo_resolution_supported(parent_table.Key_Lett)
        .if(((checkParentForComponentContainment) and (not_empty root_pkg)) and (rgoResolution.supported))
          .assign proxy_index = 0
          .// for every rto associated with the class, export an RGO
          .// proxy
          .assign addExportRGOProxyCall = true
  public void export_${parent_name}_RGO_Proxies(${parent_name} inst) {
    if (!m_fileExportInProgress) {
          .select many rtos related by class->R_OIR[R201]->R_RTO[R203]
          .for each rto in rtos
            .assign proxy_index = proxy_index + 1
            .select one rel related by rto->R_OIR[R203]->R_REL[R201]
            .invoke ir = is_reflexive(rel)
            .invoke ic = is_containment(rel)
            .assign ignored = false
            .// some relationships should not be used for persistence,
            .// ignore them.
            .if("${rel.Descrip:Persistent}" == "false")
              .assign ignored = true
            .end if
            .if(((not ir.result) and (not ic.result)) and (not ignored))
              .select one rgoOBJ related by rto->R_PART[R204]->R_SIMP[R207]->R_FORM[R208]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201];
              .if(not_empty rgoOBJ)
                .invoke rgoProxyResult = should_export_rgo_proxy(rgoOBJ, rel.Numb)
                .if(rgoProxyResult.result)
    // select R_FORM ${rgoOBJ.Key_Lett} on R${rel.Numb}
                  .invoke gpe = gen_proxy_export(rgoOBJ, rel, proxy_index, checkParentForComponentContainment, true)
${gpe.body}\
                .end if
              .else
                .select one rgoOBJ related by rto->R_AONE[R204]->R_ASSOC[R209]->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
                .if(not_empty rgoOBJ)
                  .invoke rgoProxyResult = should_export_rgo_proxy(rgoOBJ, rel.Numb)
                  .if(rgoProxyResult.result)
    // select R_AONE ${rgoOBJ.Key_Lett} on R${rel.Numb}
                    .invoke gpe = gen_proxy_export(rgoOBJ, rel, proxy_index, checkParentForComponentContainment, true)
${gpe.body}\
                  .end if
                .else
                  .select one rgoOBJ related by rto->R_AOTH[R204]->R_ASSOC[R210]->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
                  .if(not_empty rgoOBJ)
                    .invoke rgoProxyResult = should_export_rgo_proxy(rgoOBJ, rel.Numb)
                    .if(rgoProxyResult.result)
    // select R_AOTH ${rgoOBJ.Key_Lett} on R${rel.Numb}
                      .invoke gpe = gen_proxy_export(rgoOBJ, rel, proxy_index, checkParentForComponentContainment, true)
${gpe.body}\
                    .end if
                  .else
                    .select many rgoOBJs related by rto->R_SUPER[R204]->R_SUBSUP[R212]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
                    .for each rgoOBJ in rgoOBJs
                      .invoke rgoProxyResult = should_export_rgo_proxy(rgoOBJ, rel.Numb)
                      .if(rgoProxyResult.result)
                        .assign proxy_index = proxy_index + 1
    // select R_SUB ${rgoOBJ.Key_Lett} on R${rel.Numb}
                        .invoke gpe = gen_proxy_export(rgoOBJ, rel, proxy_index, checkParentForComponentContainment, true)
${gpe.body}\
                      .end if
                    .end for
                  .end if
                .end if
              .end if
            .end if
          .end for
    }
  }
      .end if
    .end if
    .select any childAep related by eo->AEP[R10]
    .if(empty childAep)
      .select many otherEOs from instances of EO where (selected.Name == eo.Name)
      .for each otherEO in otherEOs
        .select any otherAep related by otherEO->AEP[R10]
        .if(not_empty otherAep)
          .assign childAep = otherAep
        .end if
      .end for
    .end if
    .if(empty childAep)
protected void export_${parent_name}( ${parent_arg_type} inst, IProgressMonitor pm, boolean writeAsProxies, boolean isPersistable )
    .else
protected void export_${parent_name}( ${parent_arg_type} inst, IProgressMonitor pm, boolean writeAsProxies, boolean isPersistable, boolean exportAlternateChildren )
    .end if
throws IOException
{
  if ( inst == null )
  {
    return;
  }  
    .if  ( (parent_name =="ReferringClassInAssoc_c") and (domain_class_name=="")) 
      if (ClassAsSubtype_c.getOneR_SUBOnR205(inst) !=null)
                {	  
              	  if(m_inst.getClass().toString().endsWith("Association_c"))
              	  {
              		ClassInAssociation_c classInAssociation =ClassInAssociation_c.getOneR_OIROnR203(inst) ;
              	    ModelClass_c modelClass = ModelClass_c.getOneO_OBJOnR201( classInAssociation );
              	    ImportedClass_c importedClass = ImportedClass_c.getOneO_IOBJOnR202( classInAssociation );  
              	     if (! Cl_c.Isselected( modelClass )&&!Cl_c.Isselected( importedClass ))
              		   {
              		     return;
              		   }    
              	   }  
                } 
   .end if
  
  
  
      .if (eo.isGraphical)
  if (!exportGraphics()) {
    return;
  }
      .end if
      .if (eo.isOAL)
  if (!exportOAL()) {
    return;
  }
      .end if
      .if(not multilevel) .// since we have already taken care of header in run method incase of multilevel export.
        .if ( parent_name == domain_class_name )
    m_fh.print( get_file_header("domain", "${tool}", com.mentor.nucleus.bp.core.CorePlugin.getPersistenceVersion()));  //$$NON-NLS-1$$ //$$NON-NLS-2$$ //$$NON-NLS-3$$
        .end if
      .end if
      .if (( eo.writePosition == "first" ) or (eo.writePosition == "none"))
    if(!writeAsProxies && !forceWriteAsProxy)
      write_${parent_name}_sql(inst${extra_args});
        .if((multilevel) and (eo.Numb != 0))
    else {
          .if(checkParentForComponentContainment)
      ProxyPath proxyPath = new ProxyPath(inst${extra_args}, m_inst);
      collectedProxies.add(proxyPath);
          .else
      write_${parent_name}_proxy_sql(inst${extra_args});
          .end if
    }
        .end if
      .end if
      .invoke isOALRootResult = isOALRootType(parent_arg_type)
      .// OAL Instance data types are not persisted in the model files,
      .// and therefore do not have PMCs.  If this is an OAL Root then as we
      .// recursive and export the children we do not want to perform the PMC
      .// check because OAL Instance types will not have PMCs so the check
      .// would fail for them.
      .assign isPersistableParamValue = "isPersistable"
      .if ( isOALRootResult.isOALRoot )
        .assign isPersistableParamValue = "false"
      .end if
      .select one child related by eo->EO[R1.'is_first_child_of']
      .while ( not_empty child )
        .invoke ch_fOcc=  firstOccurrence(child)
        .assign exp_child=((ch_fOcc.firstOccurrence) or (child.componentRoot==True))
        .if((multilevel) and (not checkParentForComponentContainment))
          .assign exp_child=(child.writePosition != "none") and (child.componentRoot==False)
        .else
          .// for non multi-level files we need to export all
          .// children except some entries which cause loops
          .invoke result = export_element_for_sf(eo, child);
          .assign exp_child = result.result
        .end if
        .if(exp_child)
          .select one ei related by child->EI[R3]
          .select one table related by ei->T[R4]
          .select any aep related by child->AEP[R10]
          .if(empty aep)
            .select many otherEOs from instances of EO where (selected.Name == child.Name)
            .for each otherEO in otherEOs
              .select any otherAep related by otherEO->AEP[R10]
              .if(not_empty otherAep)
                .assign aep = otherAep
              .end if
            .end for
          .end if
          .assign alternateChildBool = ", false"
          .assign writeAsProxies = ", false"
          .if(empty aep)
            .assign alternateChildBool = ""
          .else
            .if(aep.alwaysExportAlternateChildren)
              .assign alternateChildBool = ", true"
            .end if
            .if(aep.writeProxies)
              .assign writeAsProxies = ", true"
            .end if
          .end if
          .if ( not_empty table )
            .invoke child_name = get_class_name( table )
            .assign nav_string = ""
            .assign variableName = "${child_name.body}"
            .if(child.Numb != -1)
              .assign variableName = variableName + "${child.Numb}"
            .else
              .if(child.Card == "one")
                .assign variableName = variableName + "_inst"
              .else
                .assign variableName = variableName + "_set"
              .end if
            .end if
            .if(child.rel_chain != "")
               .invoke nav = generate_backward_rel_chain_nav_from_kl(table.Key_Lett, child.rel_chain, child.Card, "inst", variableName, false);
               .assign nav_string = nav.body
            .else
              .assign relNumbAndPhrase = "R${child.Numb}"
              .if(child.rel_phrase != "")
                .assign relNumbAndPhrase = relNumbAndPhrase + ".'${child.rel_phrase}'"
              .end if
              .invoke nav = generate_backward_rel_chain_nav_from_kl(table.Key_Lett, "->${table.Key_Lett}[${relNumbAndPhrase}]", child.Card, "inst", variableName, false);
              .assign nav_string = nav.body
            .end if
            ${nav_string}
            .if ( child.Card == "many" )
              .if ( eo.writePosition == "first if child not empty" )
      if ( ${variableName}.length > 0 )
      {
                .if(multilevel)
        if(writeAsProxies) {
                  .if(checkParentForComponentContainment)
          ProxyPath proxyPath = new ProxyPath(inst${extra_args});
          collectedProxies.add(proxyPath);
                  .else
          write_${parent_name}_proxy_sql(inst${extra_args});
                  .end if
        }
                .end if
                .if((multilevel) and (eo.Numb != 0))
        else
                .end if
          write_${parent_name}_sql(inst${extra_args});
      }
              .end if
      for ( int ${child_name.body}_index = 0;
            ${child_name.body}_index < ${variableName}.length;
            ++${child_name.body}_index )
      {
        export_${child_name.body}(${variableName}[${child_name.body}_index], pm, writeAsProxies, ${isPersistableParamValue} ${alternateChildBool});
      }
            .else
      export_${child_name.body}( ${variableName}, pm, writeAsProxies, ${isPersistableParamValue} ${alternateChildBool});
            .end if
            .if ( eo.writePosition == table.bpName )
      if(!writeAsProxies && !forceWriteAsProxy)
        write_${parent_name}_sql(inst${extra_args});
            .end if
          .else
            .select one gd related by ei->GD[R4]
      if (m_export_graphics && !writeAsProxies && !forceWriteAsProxy) export_${gd.modelName}(inst, pm, false, ${isPersistableParamValue});
          .end if
        .end if .//  .if (process1 or process2)
        .select one child related by child->EO[R2.'precedes']

      .end while
      .if ( eo.writePosition == "last" )

     if(!writeAsProxies && !forceWriteAsProxy)
      write_${parent_name}_sql(inst${extra_args});
        .if((multilevel) and (eo.Numb != 0))
    else {
          .if(checkParentForComponentContainment)
      ProxyPath proxyPath = new ProxyPath(inst${extra_args}, m_inst);
      collectedProxies.add(proxyPath);
          .else
      write_${parent_name}_proxy_sql(inst${extra_args});
          .end if
    }
        .end if
      .end if
      .if ( multilevel and (not_empty parent_table) )
        .// export the cross component proxy instances
        .assign proxy_index = 0
        .select any class from instances of O_OBJ where (selected.Key_Lett == parent_table.Key_Lett)
        .select many rgo_set related by class->R_OIR[R201]->R_RGO[R203]
        .for each rgo in rgo_set
          .assign proxy_index = proxy_index + 1
          .select one rel related by rgo->R_OIR[R203]->R_REL[R201]
          .invoke ir = is_reflexive(rel)
          .invoke ic = is_containment(rel)
          .assign ignored = false
          .// some relationships should not be used for persistence,
          .// ignore them.
          .if("${rel.Descrip:Persistent}" == "false")
            .assign ignored = true
          .end if
          .// reflexive relationships can't cross component boundaries
          .// proxies are not exported for containment relationships
          .if ( (not ir.result) and ((not ic.result) and (not ignored)) )
            .select one form related by rgo->R_FORM[R205]
            .if ( not_empty form )
              .select one part_obj related by form->R_SIMP[R208]->R_PART[R207]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
              .if("${part_obj.Descrip:Persistent}" != "false")
    // select part ${part_obj.Key_Lett} on R${rel.Numb}
                .invoke gpe = gen_proxy_export(part_obj, rel, proxy_index, checkParentForComponentContainment, false)
${gpe.body}\
              .end if
            .else
              .select one assr related by rgo->R_ASSR[R205]
              .if ( not_empty assr )
                .select one aone_obj related by assr->R_ASSOC[R211]->R_AONE[R209]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
                .select one aoth_obj related by assr->R_ASSOC[R211]->R_AOTH[R210]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
    // select aone ${aone_obj.Key_Lett} on R${rel.Numb}
                .invoke gpe = gen_proxy_export(aone_obj, rel, proxy_index, checkParentForComponentContainment, false)
${gpe.body}\
                .assign proxy_index = proxy_index + 1
    // select aoth ${aoth_obj.Key_Lett} on R${rel.Numb}
                .invoke gpe = gen_proxy_export(aoth_obj, rel, proxy_index, checkParentForComponentContainment, false)
${gpe.body}\
              .else
			    .if(checkParentForComponentContainment)
				  .// for stream export, write each supertype
				  .// of the current element
// only export the supertypes if this element was selected for export
// without its parents
if(m_inst == inst && exportSupertypes) {
				  .invoke gsws = gen_supertype_write_statements(rel, class, "")
${gsws.body}
}
				.else
                  .select one sub related by rgo->R_SUB[R205]
                  .select one super_obj related by sub->R_SUBSUP[R213]->R_SUPER[R212]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
    // select super ${super_obj.Key_Lett} on R${rel.Numb}
                  .invoke gpe = gen_proxy_export(super_obj, rel, proxy_index, checkParentForComponentContainment, false)
${gpe.body}\
                .end if
              .end if
            .end if
          .end if
        .end for
      .end if
      .if(addExportRGOProxyCall)
        export_${parent_name}_RGO_Proxies(inst);
      .end if
      .// generate code for alternate children
      .select many aeps related by eo->AEP[R10]
      .if((not_empty aeps) and (multilevel))
    if(exportAlternateChildren && !ignoreAlternateChildren) {
        .assign count = 0
        .assign proxyCount = 0
        .for each aep in aeps
          .assign count = count + 1
          .select one child_eo related by aep->EO[R11]
          .select any child_aep related by child_eo->AEP[R10]
          .assign alternateChildBool = ", false"
          .if(empty child_aep)
            .assign alternateChildBool = ""
          .else
            .if(child_aep.alwaysExportAlternateChildren)
              .assign alternateChildBool = ", true"
            .else
              .assign alternateChildBool = ", false"
            .end if
          .end if
          .select one table related by child_eo->EI[R3]->T[R4]
          .if(not_empty table)
            .invoke child_name = get_class_name(table)
            .assign variableName = child_name.body
            .if(aep.Card == "many")
              .if(child_eo.Numb != -1)
                .assign variableName = variableName + "${child_eo.Numb}"
              .end if
              .invoke nav = generate_backward_rel_chain_nav_from_kl(table.Key_Lett, aep.Rel_Chain, aep.Card, "inst", "${variableName}${count}_set", false)
      ${nav.body}
      for ( int ${child_name.body}_index = 0;
            ${child_name.body}_index < ${variableName}${count}_set.length;
            ++${child_name.body}_index )
      {
              .if(aep.exportChildrenOfChild)
                .if(aep.writeProxies)
        export_${child_name.body}(${variableName}${count}_set[${child_name.body}_index], pm, true, ${isPersistableParamValue} ${alternateChildBool});
                .else
				  .if(aep.forceProxyWritingForChildren)
        export_${child_name.body}(${variableName}${count}_set[${child_name.body}_index], pm, (m_inst == inst) ? false : true, ${isPersistableParamValue} ${alternateChildBool});
		          .else
        export_${child_name.body}(${variableName}${count}_set[${child_name.body}_index], pm, false, ${isPersistableParamValue} ${alternateChildBool});
				  .end if
                .end if
              .else
                .if(not aep.writeProxies)
          write_${child_name.body}_sql(${variableName}${count}_set[${child_name.body}_index]);
                .else
                  .if(checkParentForComponentContainment)
          ProxyPath proxyPath = new ProxyPath(${variableName}${count}_set[${child_name.body}_index], m_inst);
          collectedProxies.add(proxyPath);
                  .else
          write_${child_name.body}_proxy_sql(${variableName}${count}_set[${child_name.body}_index]);
                  .end if
                .end if
              .end if
      }
            .else
              .invoke nav = generate_backward_rel_chain_nav_from_kl(table.Key_Lett, aep.Rel_Chain, aep.Card, "inst", "${child_name.body}${count}_inst", false)
      ${nav.body}
              .if(aep.exportChildrenOfChild)
                .if(aep.writeProxies)
      export_${child_name.body}(${child_name.body}${count}_inst, pm, true, ${isPersistableParamValue} ${alternateChildBool});
                .else
				  .if(aep.forceProxyWritingForChildren)
      export_${child_name.body}(${child_name.body}${count}_inst, pm, (m_inst == inst) ? false : true, ${isPersistableParamValue} ${alternateChildBool});
	              .else
      export_${child_name.body}(${child_name.body}${count}_inst, pm, false, ${isPersistableParamValue} ${alternateChildBool});
				  .end if
                .end if
              .else
                .if(not aep.writeProxies)
        write_${child_name.body}_sql(${child_name.body}${count}_inst);
                .else
                  .if(checkParentForComponentContainment)
                    .assign proxyCount = proxyCount + 1
                    .if(proxyCount == 1)
        ProxyPath proxyPath = new ProxyPath(${child_name.body}${count}_inst, m_inst);
                    .else
        proxyPath = new ProxyPath(${child_name.body}${count}_inst, m_inst);
                    .end if
        collectedProxies.add(proxyPath);
                  .else
        write_${child_name.body}_proxy_sql(${child_name.body}${count}_inst);
                  .end if
                .end if
              .end if
            .end if
          .end if
        .end for
    }
      .end if
}
    .end if
  .end for
.end function
.function rgo_resolution_supported
  .param String lett
    .assign attr_supported = false
    .if(lett == "S_DT")
	  .assign attr_supported = true
	.elif(lett == "C_I")
	  .assign attr_supported = true
	.elif(lett == "C_C")
	  .assign attr_supported = true
	.elif(lett == "C_EP")
	  .assign attr_supported = true
	.elif(lett == "C_IR")
	  .assign attr_supported = true
     .elif(lett == "C_PO")
	  .assign attr_supported = true  
	.end if
.end function
.//
.//  This function generates methods
.//  which will place the string
.//  representation of the sql statement
.//
.function gen_sql_write_statements
  .param String callingProject
  .param boolean multilevel
  .param boolean stream
    .select many table_set from instances of T
    .for each table in table_set
      .// output all write_sql functions
      .invoke sql = gen_write_sql_function( table, callingProject, multilevel, stream )
${sql.body}\
    .end for
.end function

.//
.//  This function processes all GD instances
.//  generating a method that will write the
.//  required sql statement
.//
.function gen_diagram_export_statements
  .//
  .//
  .select many gd_set from instances of GD
  .for each gd in gd_set
    .select one model_data related by gd->T[R7]
    .select one id_col related by gd->C[R8]
    .invoke p = get_class_name( model_data )
    .invoke a = get_attribute_accessor( id_col )
void write_${gd.modelName}_sql(final ${p.body} inst, IProgressMonitor pm )
throws IOException
{
  class Model_test1_c implements ClassQueryInterface_c {
    public boolean evaluate(Object test_instance) {
      Model_c selected = (Model_c)test_instance;
      return selected.getOoa_id().equals(inst.${a.body}()) && selected.getModel_type() == Modeltype_c.${gd.modelId};
    }
  }
  Model_c model =
    Model_c.ModelInstance(getGraphicsModelRoot(), new Model_test1_c());
  export_Model_c( model, pm, false, true );
    pm.worked(1);
}

  .end for
.end function

.function export_element_for_sf
  .param Inst_Ref eo
  .param Inst_Ref child
  .assign attr_result = true
  .if(eo.Name == "Subsystem")
    .if(child.Name == "Subsystem in Domain")
      .assign attr_result = false
    .elif(child.Name == "Subsystem in Subsystem")
      .if(child.Numb == 42)
        .assign attr_result = false
      .end if
    .end if
  .elif(eo.Name == "External Entity Package")
    .if(child.Name == "External Entity Package in Domain")
      .if(child.writePosition == "none")
        .assign attr_result = false
      .end if
    .end if
  .elif(eo.Name == "Function Package")
    .if(child.Name == "Function Package in Domain")
      .if(child.writePosition == "none")
        .assign attr_result = false
      .end if
    .end if
  .elif(eo.Name == "Sequence")
    .if(child.Name == "Sequence in Sequence")
      .if(child.Numb == 928)
        .assign attr_result = false
      .end if
    .end if
  .elif(eo.Name == "Communication")
    .if(child.Name == "Communication in Communication")
      .if(child.Numb == 1129)
        .assign attr_result = false
      .end if
    .end if
  .elif(eo.Name == "Use Case Diagram")
    .if(child.Name == "Use Case in Use Case")
      .if(child.Numb == 1209)
        .assign attr_result = false
      .end if
    .end if
  .end if
.end function
.function isOALRootType
  .param string className
  .assign attr_isOALRoot = false
  .if ((className == "RequiredSignal_c") or (className == "RequiredOperation_c"))
    .assign attr_isOALRoot = true
  .end if
  .if ((className == "ProvidedSignal_c") or (className == "ProvidedOperation_c"))
    .assign attr_isOALRoot = true
  .end if
  .if ((className == "Action_c") or (className == "DerivedBaseAttribute_c"))
    .assign attr_isOALRoot = true
  .end if
  .if ((className == "Function_c") or (className == "Operation_c"))
    .assign attr_isOALRoot = true
  .end if
  .if (className == "Bridge_c")
    .assign attr_isOALRoot = true
  .end if
.end function
.function gen_supertype_write_statements
  .param Inst_ref rel
  .param Inst_ref obj
  .param String navChain
    .// for each supertype up the hierarchy generate a write statement
	.select one supertype related by rel->R_SUBSUP[R206]->R_SUPER[R212]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201];
	.select any super_eo from instances of EO where (selected.Name == supertype.Name)
    .select one table related by super_eo->EI[R3]->T[R4]
    .if(not_empty table)
      .invoke child_name = get_class_name(table)
      .invoke nav = generate_backward_rel_chain_nav_from_kl(table.Key_Lett, "${navChain}->${supertype.Key_Lett}[R${rel.Numb}]", "one", "inst", "${child_name.body}_inst", false)
${nav.body}
	write_${child_name.body}_sql(${child_name.body}_inst);
      .// special case for writing dt proxy for udt/edt
      .// this is required by the stream export so that
      .// udts/edts may fully be resolved
      .if(rel.Numb == 17)
	    .if(((obj.Name == "User Data Type") or (obj.Name == "Enumeration Data Type")) or (obj.Name == "Structured Data Type"))
                if((m_inst instanceof UserDataType_c) || (m_inst instanceof EnumerationDataType_c) || (m_inst instanceof StructuredDataType_c)) {
                  DataType_c dt = DataType_c.getOneS_DTOnR17(inst);
                  export_DataType_c_RGO_Proxies(dt);
                }
	    .end if
      .end if
    .end if
	.// select supertype of this supertype
	.select any nextRel related by supertype->R_OIR[R201]->R_RGO[R203]->R_SUB[R205]->R_SUBSUP[R213]->R_REL[R206];
	.if(not_empty nextRel)
	  .invoke gsws = gen_supertype_write_statements(nextRel, supertype, "${navChain}->${supertype.Key_Lett}[R${rel.Numb}]")
${gsws.body}
	.end if
.end function
