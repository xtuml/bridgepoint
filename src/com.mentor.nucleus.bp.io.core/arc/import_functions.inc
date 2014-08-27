.//====================================================================
.//
.// File:      $RCSfile: import_functions.inc,v $
.// Version:   $Revision: 1.66 $
.// Modified:  $Date: 2013/06/12 13:08:05 $
.//
.// (c) Copyright 2007-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.include "${mc_archetypes}/arch_utils.inc"
.include "../com.mentor.nucleus.bp.io.core/arc/generate_configurable_components.inc"
.// this function generates calls to the creation methods
.// for each table entry
.function genCreationMethodCalls
  .select many table_set from instances of T where ( selected.DomainName == "ooaofooa" )
  .for each table in table_set
    .invoke stn = get_sql_table_name( table )
        create${stn.body}(modelRoot, table, parms, rawParms, numParms, pm);
  .end for
.end function
.// this function is used to identify model elements that are licensed
.function isLicensedElement
  .param string elementName
  .assign attr_result = false
  .if (((elementName == "Component_c" ) or (elementName == "ComponentReference_c")) or (elementName == "Domain_c"))
    .assign attr_result = true;
  .end if
.end function
.// this function outputs any additional calls to the creation methods
.// for specific table entries that have to be supported
.function genExtraCreationMethodCalls
  .param String callingProject
  .//
  .if (callingProject == "NBP")
        createCP_CPFromCD_CD(modelRoot, table, parms, rawParms, numParms, pm);
        createIP_IPFromID_ID(modelRoot, table, parms, rawParms, numParms, pm);
        storeC_C(modelRoot, table, parms, rawParms, numParms, pm);
        storeC_P(modelRoot, table, parms, rawParms, numParms, pm);
        storeC_R(modelRoot, table, parms, rawParms, numParms, pm);
        storeCD_IC(modelRoot, table, parms, rawParms, numParms, pm);
        storeCD_IID(modelRoot, table, parms, rawParms, numParms, pm);
        storeCD_CID(modelRoot, table, parms, rawParms, numParms, pm);
        storeCD_CDE(modelRoot, table, parms, rawParms, numParms, pm);
        storeCL_IR(modelRoot, table, parms, rawParms, numParms, pm);
        storeCL_IPINS(modelRoot, table, parms, rawParms, numParms, pm);
        storePA_SIC(modelRoot, table, parms, rawParms, numParms, pm);
        storePA_SICP(modelRoot, table, parms, rawParms, numParms, pm);
  .end if
.end function
.// this function generates creation methods
.// for each table entry
.function genCreateMethods
  .param String callingProject
  .param boolean multilevel
  .param boolean setComponent
  .param String main_class_name
  .// create a single method for each table, this will prevent
  .// the processStatement method above from exceeding the Java
  .// code 65535 bytes limit
  .assign else_str = ""
  .select many table_set from instances of T where ( selected.DomainName == "ooaofooa" )
  .for each table in table_set
    .invoke stn = get_sql_table_name( table )
  private void create${stn.body} (${main_class_name} modelRoot, String table, Vector parms, Vector rawParms, int numParms, IProgressMonitor pm) {
    .invoke cn = get_class_name ( table )
    ${cn.body} newInst = null;
    .assign local_var = "newInst ="
    .if (table.Key_Lett == "IP_IPINIP" )
    .// Handle table rename from ID_IDINID to IP_IPINIP
    if(table.equalsIgnoreCase("${stn.body}") || table.equalsIgnoreCase("ID_IDINID")) { //$$NON-NLS-1$$//$$NON-NLS-2$$
    .elif (table.Key_Lett == "CP_CPINP" )
    .// Handle table rename from CD_CDINCD to CP_CPINP
    if(table.equalsIgnoreCase("${stn.body}") || table.equalsIgnoreCase("CD_CDINCD")) { //$$NON-NLS-1$$//$$NON-NLS-2$$
    .else
    if(table.equalsIgnoreCase("${stn.body}")) { //$$NON-NLS-1$$
    .end if
    .if(table.Key_Lett == "C_SF")
      // we need to store the satisfaction before the early return
      helper.addSatisfactionToInstances(null, IdAssigner.createUUIDFromString((String) parms.elementAt(actualVersion.equals(bp7_1_3VersionNumber)?1:0)),  IdAssigner
              ..createUUIDFromString((String) parms.elementAt(actualVersion.equals(bp7_1_3VersionNumber)?0:
                  1)));
    .end if
    .invoke earlyReturnResult = shouldReturnEarlyIfNotCurrent(table.Key_Lett);
    .if(earlyReturnResult.result != "")
    ${earlyReturnResult.result}
    .end if
    .select many col_set related by table->C[R5];
    .assign no_of_cols = cardinality col_set;
    .if((((table.Key_Lett == "SQ_S") or (table.Key_Lett  == "COMM_COMM")) or (table.Key_Lett == "A_A")) or (table.Key_Lett == "UC_UCC"))
    parms.setSize(${no_of_cols});
    .end if
    .if((table.Key_Lett == "O_RATTR" ) or (table.Key_Lett == "S_SYS"))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if((((table.Key_Lett == "C_EP") or (table.Key_Lett == "C_IO")) or (table.Key_Lett == "C_AS")) or (table.Key_Lett == "C_PP"))
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "C_P")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "C_R")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "SM_PEVT")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "SM_SGEVT")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "C_SF")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "O_OIDA")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "C_DG")
      parms.setSize(${no_of_cols});
    .end if
    .if ( ((table.Key_Lett == "S_BRG") OR (table.Key_Lett == "S_SYNC")) OR ((table.Key_Lett == "O_TFR") OR (table.Key_Lett == "V_TRN")))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ( ((table.Key_Lett == "S_BPARM") OR (table.Key_Lett == "S_SPARM")) OR (table.Key_Lett == "O_TPARM"))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ( ((table.Key_Lett == "O_ATTR") OR (table.Key_Lett == "SM_EVTDI")) OR (table.Key_Lett == "S_MBR"))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ( ((table.Key_Lett == "S_ENUM") OR (table.Key_Lett == "V_PVL") ) OR (table.Key_Lett == "V_VAR") )
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ( (table.Key_Lett == "S_DT") )
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if(table.Key_Lett == "ACT_IOP")
      parms.setSize(${no_of_cols});
      if(parms.elementAt(6) == null) {
		    parms.setElementAt(Gd_c.Null_unique_id().toString(), 1);
				parms.insertElementAt(Gd_c.Null_unique_id().toString(), 2);
		  }
    .end if
    .if(table.Key_Lett == "ACT_SGN")
      parms.setSize(${no_of_cols});
      if(parms.elementAt(6) == null) {
		    parms.setElementAt(Gd_c.Null_unique_id().toString(), 1);
				parms.insertElementAt(Gd_c.Null_unique_id().toString(), 2);
		  }
    .end if
    .if(table.Key_Lett == "V_MSV")
      parms.setSize(${no_of_cols});
      if(parms.elementAt(5) == null) {
		    parms.insertElementAt(Gd_c.Null_unique_id().toString(), 1);
				parms.insertElementAt(Gd_c.Null_unique_id().toString(), 2);
				parms.removeElementAt(6);
				Object elementAt5 = parms.elementAt(5);
				if(((String)elementAt5).equals("true") || ((String)elementAt5).equals("false")) {
					parms.insertElementAt(elementAt5, 3);
					parms.removeElementAt(6);
				}
		  }
    .end if
    .if (table.Key_Lett == "O_REF" )
      parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ( table.Key_Lett == "CA_SMSMC" )
        UUID val4 = IdAssigner.NULL_UUID;
    if ( numParms == 5 ) {
        val4 = IdAssigner.createUUIDFromString((String) parms.elementAt(4));
    }
    .end if
    .if (table.Key_Lett == "EP_PKG" )
      parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if (table.Key_Lett == "C_C" )
      parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if(table.Key_Lett == "S_EE")
      boolean addToUpgradeList = false;
      if(parms.size() <= 7) {
        addToUpgradeList = true;
      }
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "O_OBJ")
      parms.setSize(${no_of_cols});
    .end if
    .//
    .// handle pre-7.1.3 models by creating the missing
    .// sequence number attributes
    .//
    .if(((table.Key_Lett == "MSG_R") or (table.Key_Lett == "MSG_SM")) or (table.Key_Lett == "MSG_AM"))
    if(!actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && (!actualVersion.equals(bp7_1_3VersionNumber)) && (!actualVersion.equals(bp7_1_4VersionNumber))) {
      .// add a blank string for the missing parameter
        parms.add("");
    }
	  .// set the non persistent attribute (particpatesInCommunication) to false
	  .// it will get configured properly by the ImportHelper
	  parms.setSize(${no_of_cols});
	  
    .end if
    .if(multilevel)
        ${local_var} ${cn.body}.resolveInstance(modelRoot,
    .else
        ${local_var} new ${cn.body}(modelRoot,
    .end if
    .invoke paramsText = generate_params( table, callingProject )
    ${paramsText.body}\
        );
        .if((not setComponent) and (multilevel))
          .// we want to fire creation events for the stream
          .// imports
        Ooaofooa.getDefaultInstance().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, newInst));
        .end if
    .if(multilevel)
      .if((table.Key_Lett == "C_I") and (setComponent))
      ImportHelper.addInterfaceToInstances(newInst);
      .end if
      .invoke element = is_configurable_element(table)
      .assign result = element.result
      .if(result)
          if (rootModelElement == null) {
            rootModelElement = newInst;
        .if(setComponent)
            m_component.setRootModelElement(newInst, false, false);
        .end if
          }
      .end if
		pm.worked(1);
      .if(setComponent)
        metadata.setComponentOfME(newInst, m_component);
      .end if
    .end if
    loadedInstances.add(newInst);
    .if((table.Key_Lett == "S_EE") and (callingProject == "NBP"))
      if(addToUpgradeList) {
      	helper.addEEToUpgradeForIsRealized(newInst);
      }
    .end if
    .// Data upgrade for 1.4.5 alpha only
    .if (table.Key_Lett == "S_DPK")
        dataPkgUUID = IdAssigner.createUUIDFromString((String)parms.elementAt(0));
    .end if
      }
    .assign else_str = "else "
    .if (multilevel)
      .if (table.Key_Lett == "IP_IPINIP" )
      .// Handle table rename from ID_IDINID_PROXY to IP_IPINIP_PROXY
      ${else_str}if ( table.equalsIgnoreCase("${stn.body}_PROXY") || table.equalsIgnoreCase("ID_IDINID_PROXY") ) //$$NON-NLS-1$$//$$NON-NLS-2$$
      .elif (table.Key_Lett == "CP_CPINP" )
      .// Handle table rename from CD_CDINCD_PROXY to CP_CPINP_PROXY
      ${else_str}if ( table.equalsIgnoreCase("${stn.body}_PROXY") || table.equalsIgnoreCase("CD_CDINCD_PROXY") ) //$$NON-NLS-1$$//$$NON-NLS-2$$
      .else
      ${else_str}if ( table.equalsIgnoreCase("${stn.body}_PROXY") ) //$$NON-NLS-1$$
      .end if
      {
      .invoke earlyReturnResult = shouldReturnEarlyIfNotCurrent(table.Key_Lett);
      .if(earlyReturnResult.result != "")
    ${earlyReturnResult.result}
      .end if
    String path = (String)parms.lastElement();
    .invoke setSize = backwardCompatibilitySizeAdjustment(table, true)
    .if (setSize.body != "")
${setSize.body}
    .end if
      .invoke cn = get_class_name ( table )
        ${local_var} ${cn.body}.createProxy(modelRoot,
      .invoke paramsText = generate_params( table, callingProject )
    ${paramsText.body}\
, removeTics(path), projRelPath);
        if (newInst.isProxy()) {
          loadedInstances.add(newInst);
		  pm.worked(1);
        } else {
          loadedProxies.add(newInst);
        }
        // newInst component is set during createProxy()
      }
    .end if
  }
  .end for  .// end generation of creation methods
.end function
.function shouldReturnEarlyIfNotCurrent
  .param String kl
  .assign attr_result = ""
  .if(((kl == "C_C") or (kl == "C_P")) or (kl == "C_R"))
    .// not that the only version where these above elements require
    .// upgrading is 1.4.2
    .assign attr_result = "if(actualVersion.equals(bp7_1_3VersionNumber)) { return; } // Components before 1.5 are imported using migrateImports()"
  .elif((((kl == "CL_IR") or (kl == "CL_IPINS")) or (kl == "PA_SIC")) or (kl == "PA_SICP"))
    .assign attr_result = "if (!actualVersion.equals(persistenceVersion) && !actualVersion.equals(bp7_1_5VersionNumber)) { return; }"
  .elif(kl == "C_SF")
    .assign attr_result = "if (actualVersion.equals(bp7_1_4VersionNumber) || actualVersion.equals(bp7_1_3VersionNumber)) { return; }"
  .end if
.end function
.// this function outputs any additional creation methods
.// for specific table entries that have to be supported
.function genExtraCreateMethods
  .param String callingProject
  .param boolean multilevel
  .param boolean setComponent
  .param String main_class_name
  .if (callingProject == "NBP")
  private void createCP_CPFromCD_CD(Ooaofooa modelRoot, String table, Vector parms,
                                    Vector rawParms, int numParms, IProgressMonitor pm) {
    .assign local_var = ""
    .if(multilevel)
    NonRootModelElement newInst = null;
    .assign local_var = "newInst ="
    .end if
    if (table.equalsIgnoreCase("CD_CD")) { //$$NON-NLS-1$$
    .if(multilevel)
      ${local_var} ComponentPackage_c.resolveInstance(modelRoot,
    .else
      ${local_var} new ComponentPackage_c(modelRoot,
    .end if
          IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
          IdAssigner.createUUIDFromString((String) parms.elementAt(3)),
          IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
          IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
          removeTics((String) parms.elementAt(4)).trim(),
          removeTics((String) parms.elementAt(5)));
    .if((not setComponent) and (multilevel))
    .// we want to fire creation events for the stream
    .// imports
    Ooaofooa.getDefaultInstance().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, newInst));
    .end if
    .if(multilevel)
      .select any table from instances of T where (selected.Key_Lett == "CP_CP")
      .invoke element = is_configurable_element(table)
      .assign result = element.result
      .if(result)
      if (rootModelElement == null) {
         rootModelElement = newInst;
       .if(setComponent)
         m_component.setRootModelElement(newInst, false, false);
       .end if
      }
      .end if
    loadedInstances.add(newInst);
        .if(setComponent)
    metadata.setComponentOfME(newInst, m_component);
        .end if
      .end if
    }
      .assign else_str = "else "
      .if (multilevel)
    ${else_str}if ( table.equalsIgnoreCase("CD_CD_PROXY") ) //$$NON-NLS-1$$
    {
        ${local_var} ComponentPackage_c.createProxy(modelRoot,
            IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(3)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
            removeTics((String) parms.elementAt(4)).trim(),
            removeTics((String) parms.elementAt(5)),
            removeTics((String)parms.lastElement()), projRelPath);
        if (newInst.isProxy()) {
          loadedInstances.add(newInst);
        }
        // newInst component is set during createProxy()
      }
      .end if
  }
  private void createIP_IPFromID_ID(Ooaofooa modelRoot, String table, Vector parms,
                                Vector rawParms, int numParms, IProgressMonitor pm) {
    .assign local_var = ""
    .if(multilevel)
    NonRootModelElement newInst = null;
    .assign local_var = "newInst ="
    .end if
    if (table.equalsIgnoreCase("ID_ID")) { //$$NON-NLS-1$$
    .if(multilevel)
      ${local_var} InterfacePackage_c.resolveInstance(modelRoot,
    .else
      ${local_var} new InterfacePackage_c(modelRoot,
    .end if
                IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
                IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
                IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
                IdAssigner.createUUIDFromString((String) parms.elementAt(3)),
                Gd_c.Null_unique_id(),
                Gd_c.Null_unique_id(),
                removeTics((String) parms.elementAt(4)).trim(),
                removeTics((String) parms.elementAt(5)));
    .if((not setComponent) and (multilevel))
    .// we want to fire creation events for the stream
    .// imports
    Ooaofooa.getDefaultInstance().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, newInst));
    .end if
    .if(multilevel)
      .select any table from instances of T where (selected.Key_Lett == "IP_IP")
      .invoke element = is_configurable_element(table)
      .assign result = element.result
      .if(result)
      if (rootModelElement == null) {
         rootModelElement = newInst;
        .if(setComponent)
         m_component.setRootModelElement(newInst, false, false);
        .end if
      }
      .end if
      loadedInstances.add(newInst);
      .if(setComponent)
        metadata.setComponentOfME(newInst, m_component);
      .end if
    .end if
      }
    .assign else_str = "else "
    .if (multilevel)
      ${else_str}if ( table.equalsIgnoreCase("ID_ID_PROXY") ) //$$NON-NLS-1$$
      {
        ${local_var} InterfacePackage_c.createProxy(modelRoot,
            IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(3)),
            Gd_c.Null_unique_id(),
            Gd_c.Null_unique_id(),
            removeTics((String) parms.elementAt(4)).trim(),
            removeTics((String) parms.elementAt(5)),
            removeTics((String)parms.lastElement()), projRelPath);
        if (newInst.isProxy()) {
          loadedInstances.add(newInst);
        }
        // newInst component is set during createProxy()
      }
    .end if
  }
  private void storeC_C(Ooaofooa modelRoot, String table, Vector parms,
                          Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("C_C") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      // TODO - This code was added to upgrade C_C instances, but only for
      // these instances when created before 1.5
      Component_c component = new Component_c(modelRoot, IdAssigner.createUUIDFromString((String)parms.elementAt(0)), 
          IdAssigner.NULL_UUID,
          IdAssigner.NULL_UUID,
          removeTics((String)parms.elementAt(2)).trim(), 
          removeTics((String)parms.elementAt(3)),
          0, 
          IdAssigner.NULL_UUID, false, "");
          .if(multilevel)
      if (rootModelElement == null) {
          rootModelElement = component;
            .if(setComponent)
          m_component.setRootModelElement(component, false, false);
            .end if
        }
      loadedInstances.add(component);
      metadata.setComponentOfME(component, m_component);
          .end if
      ImportHelper.C_C cc = new ImportHelper.C_C(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        removeTics((String) parms.elementAt(2)),
        removeTics((String) parms.elementAt(3)), component
        );
      helper.old_components.add(cc);
    }
  }
  private void storeC_P(Ooaofooa modelRoot, String table, Vector parms,
                        Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("C_P") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      ImportHelper.C_P cp = new ImportHelper.C_P(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(3)),
        removeTics((String) parms.elementAt(4)),
        removeTics((String) parms.elementAt(5)),
        removeTics((String) parms.elementAt(6))
      );
      ImportHelper.old_provisions.add(cp);
    }
  }
  private void storeC_R(Ooaofooa modelRoot, String table, Vector parms,
                        Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("C_R") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      ImportHelper.C_R cr = new ImportHelper.C_R(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(3)),
        removeTics((String) parms.elementAt(4)),
        removeTics((String) parms.elementAt(5)),
        removeTics((String) parms.elementAt(6))
      );
      ImportHelper.old_requirements.add(cr);
    }
  }
  private void storeCD_IC(Ooaofooa modelRoot, String table, Vector parms,
                          Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("CD_IC") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      // TODO - This code was added to upgrade CD_IC instances, but only for
      // these instances when created before 1.5
      ImportHelper.CD_IC cdic = new ImportHelper.CD_IC(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
        Integer.parseInt((String) parms.elementAt(3)),
        removeTics((String) parms.elementAt(4)),
        removeTics((String) parms.elementAt(5)), modelRoot
        );
      ImportHelper.used_components.add(cdic);
    }
  }
  private void storeCD_IID(Ooaofooa modelRoot, String table, Vector parms,
                           Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("CD_IID") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      // TODO - This code was added to upgrade CD_IID instances, but only for
      // these instances when created before 1.5
      ImportHelper.CD_IID cdiid = new ImportHelper.CD_IID(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2))
        );
      ImportHelper.interface_in_diagrams.add(cdiid);
    }
  }
  private void storeCD_CID(Ooaofooa modelRoot, String table, Vector parms,
                           Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("CD_CID") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      // TODO - This code was added to upgrade CD_CID instances, but only for
      // these instances when created before 1.5
      ImportHelper.CD_CID cdcid = new ImportHelper.CD_CID(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1))
        );
      ImportHelper.component_in_diagrams.add(cdcid);
    }
  }
  private void storeCD_CDE(Ooaofooa modelRoot, String table, Vector parms,
                           Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("CD_CDE") && !actualVersion.equals(persistenceVersion) && (!actualVersion.equals(bp7_1_5VersionNumber)) && !actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      // TODO - This code was added to upgrade CD_CDE instances, but only for
      // these instances when created before 1.5
      ImportHelper.CD_CDE cdcde = new ImportHelper.CD_CDE(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1))
        );
      ImportHelper.component_diagram_elements.add(cdcde);
    }
  }
  private void storeCL_IR(Ooaofooa modelRoot, String table, Vector parms,
                          Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("CL_IR") && actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      ImportHelper.CL_IR ir = new ImportHelper.CL_IR(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
        removeTics((String) parms.elementAt(3)),
        removeTics((String) parms.elementAt(4))
      );
      helper.old_imported_requirements.add(ir);
    }
  }
  private void storeCL_IPINS(Ooaofooa modelRoot, String table, Vector parms,
                          Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("CL_IPINS") && actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      ImportHelper.CL_IPINS ips = new ImportHelper.CL_IPINS(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2))
      );
      helper.old_imported_provision_satisfactions.add(ips);
    }
  }
  private void storePA_SICP(Ooaofooa modelRoot, String table, Vector parms,
                          Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("PA_SICP") && actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      ImportHelper.PA_SICP sicp = new ImportHelper.PA_SICP(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2))
      );
      helper.old_satisfactions_in_comppackage.add(sicp);
    }
  }
  private void storePA_SIC(Ooaofooa modelRoot, String table, Vector parms,
                          Vector rawParms, int numParms, IProgressMonitor pm) {
    if (table.equalsIgnoreCase("PA_SIC") && actualVersion.equals(bp7_1_4VersionNumber)) { //$$NON-NLS-1$$
      ImportHelper.PA_SIC sic = new ImportHelper.PA_SIC(
        IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
        IdAssigner.createUUIDFromString((String) parms.elementAt(2))
      );
      helper.old_satisfactions_in_comp.add(sic);
    }
  }
  .end if
.end function
.// this function generates
.// a method which will process
.// the values with the import
.// statements
.function genProcessValueMethod
    public String processValue(String table, int col_num, String val) {
    .select many col_set from instances of C
    .assign first_bool_test = true
    .assign else_str = ""
    .for each col in col_set
      .if ( col.type == "boolean" )
        .//
        .// BP exports boolean attributes as "0" or "1"
        .// Postgresql expects either "true" or "false"
        .//
        .select one table related by col->T[R5]
        .invoke stn = get_sql_table_name( table )
        .invoke col_num = get_column_number( col )
      ${else_str}if ( table.equalsIgnoreCase("${stn.body}") && col_num == ${col_num.result} )   // ${col.Name} //$$NON-NLS-1$$
        .if ( first_bool_test )
          .assign first_bool_test = false
          .assign else_str = "else "
        .end if
        if ( val.equals("0") )   //$$NON-NLS-1$$
          return "false";        //$$NON-NLS-1$$
        else
          return "true";         //$$NON-NLS-1$$
      .end if
    .end for
      return val;
    }
.end function
.function genGraphicsCreationCode
  .param String callingProject
  .param boolean multilevel
  .param boolean setComponent
  .//
  .//
    .assign local_var = ""
    .if(multilevel)
        NonRootModelElement newInst = null;
      .assign local_var = "newInst ="
    .end if
      if(m_parseGraphics){
  .assign else_str = ""
  .select many table_set from instances of T where ( selected.DomainName == "ooaofgraphics" )
  .for each table in table_set
    .invoke stn = get_sql_table_name( table )
      ${else_str}if ( table.equalsIgnoreCase("${stn.body}") ) {   //$$NON-NLS-1$$
    .invoke cn = get_class_name ( table )
    .if (callingProject == "BP")
        .if (table.Key_Lett == "GD_MD")
        helper.parsePreBP7_1Model(rawParms);
        .elif (table.Key_Lett == "GD_GE")
        helper.parsePreBP7_1GraphicalElement(rawParms);
        .elif (table.Key_Lett == "GD_SHP")
        helper.parsePreBP7_1Shape(rawParms);
        .elif (table.Key_Lett == "GD_CON")
        helper.parsePreBP7_1Connector(rawParms);
        .elif (table.Key_Lett == "GD_LS")
        helper.parsePreBP7_1LineSegment(rawParms);
        .elif (table.Key_Lett == "GD_CTXT")
        helper.storePreBP7_1_1ConnectorText(rawParms);
        .else
        ${local_var} new ${cn.body}(getGraphicsModelRoot(),
        .invoke paramsText = generate_params( table, callingProject )
    ${paramsText.body}
);
        .if(multilevel)
        loadedGraphicsInstances.add(newInst);
          .if(setComponent)
        metadata.setComponentOfME(newInst, m_component);
          .end if
        .end if
            .end if
    .else
        boolean translated = false;
        if (actualVersion.equals(bp7_0VersionNumber)) {
        .if (table.Key_Lett == "GD_MD")
            helper.parsePreBP7_1Model(rawParms);
            translated = true;
        .elif (table.Key_Lett == "GD_GE")
            helper.parsePreBP7_1GraphicalElement(rawParms);
            translated = true;
        .elif (table.Key_Lett == "GD_SHP")
            helper.parsePreBP7_1Shape(rawParms);
            translated = true;
        .elif (table.Key_Lett == "GD_CON")
            helper.parsePreBP7_1Connector(rawParms);
            translated = true;
        .elif (table.Key_Lett == "GD_LS")
            helper.parsePreBP7_1LineSegment(rawParms);
            translated = true;
        .end if
        }
        if (actualVersion.equals(bp7_0VersionNumber)
            || actualVersion.equals(bp7_1VersionNumber)) {
        .if (table.Key_Lett == "GD_CTXT")
            helper.storePreBP7_1_1ConnectorText(rawParms);
            translated = true;
        .end if
        }
        if (!translated) {
        ${local_var} new ${cn.body}(getGraphicsModelRoot(),
        .invoke paramsText = generate_params( table, callingProject )
    ${paramsText.body}
);
      .if((not setComponent) and (multilevel))
        .// we want to fire creation events for
        .// the stream import
       Ooaofgraphics.getDefaultInstance().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, newInst));
      .end if
      .if(multilevel)
        loadedGraphicsInstances.add(newInst);
        .if(setComponent)
        metadata.setComponentOfME(newInst, m_component);
        .end if
      .end if
        }
    .end if
    .assign else_str = "} else "
  .end for
      }
    }
.end function
.function genFinishLoadMethod
  .param boolean multilevel
  .param boolean setComponent
  .param String callingProject
/*
    Since the call to CoreImport.doLoadSql (see the run method
    above) is done in a different thread, we can't do anything
    that would cause a fireModelChange to occur in that sub-
    thread.  All the UI processing is therefore done by this
    function, after the file has been loaded.
*/
    public void finishLoad(IProgressMonitor pm)
    {
      if ( m_success )
      {
        Ooaofooa modelRoot = getModelRoot();
      .if ( not multilevel )
        modelRoot.setFullModelIsLoaded();
      .end if
      .select any domain_inst from instances of T where ( selected.Name == "Domain" )
      .invoke dcn = get_class_name( domain_inst )
      .invoke dia = get_instance_accessor( domain_inst )
        ${dcn.body} d2 = null;
                ModelRoot.disableChangeNotification();
        try {
      .select many table_set from instances of T
      .// add one for the SM_SUPDT fixup
      .assign num_tasks = (cardinality table_set) + 1

      .select many table_set from instances of T where (selected.DomainName == "ooaofooa")
      .assign num_tasks_without_graphics = (cardinality table_set) + 1
      .// we add three below for the domain id checks
        if(m_parseGraphics){
        pm.beginTask("Completing import...", ${num_tasks} + 3);
        }else{
            pm.beginTask("Completing import...", ${num_tasks_without_graphics} + 3);
        }

    .if((multilevel) and (not setComponent))
            for(int i=0; i<loadedInstances.size(); i++){
                ((NonRootModelElement)loadedInstances.get(i)).batchRelate(modelRoot, true, true, false);
            }
    .else
      .assign lastDomain = "";
      .for each table in table_set
        .invoke cn = get_class_name ( table )
        ${cn.body}.batchRelateAll(modelRoot, false, true, false);
        .assign lastDomain = table.DomainName;
        pm.worked(1);
      .end for
    .end if


    if(m_parseGraphics){
    .if((multilevel) and (not setComponent))
                ModelRoot graphicsModelRoot = getGraphicsModelRoot();

                for(int i=0; i<loadedGraphicsInstances.size(); i++){
                    ((NonRootModelElement)loadedGraphicsInstances.get(i)).batchRelate(graphicsModelRoot, true, false);
                }
    .else
    .select many table_set from instances of T where (selected.DomainName == "ooaofgraphics")
      .assign lastDomain = "";
      .for each table in table_set
        .invoke cn = get_class_name ( table )
        ${cn.body}.batchRelateAll(getGraphicsModelRoot(), false, true);
        .assign lastDomain = table.DomainName;
        pm.worked(1);
      .end for
    .end if
    .if (callingProject == "BP")
        .// explicitly call batchRelateAll() on the classes that didn't exist in the
        .// BP SQL schema
                WaypointInMove_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Graphelement_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Graphconnector_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Property_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Diagramelement_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Reference_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Diagramlink_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Graphnode_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Diagram_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Semanticmodelbridge_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Simplesemanticmodelelement_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Graphedge_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Waypoint_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Coresemanticmodelbridge_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Element_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Leafelement_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Image_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Textelement_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Graphicprimitive_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Polyline_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
                Ellipse_c.batchRelateAll(getGraphicsModelRoot(), false, true);
                pm.worked(1);
    .end if
        }
      .// this call must be before the unique id check below
      .// found within the if test below
    .if(multilevel)  
        loadedInstances.addAll( helper.upgradePackages(getRootModelElement(), getModelRoot()) );
    .else
        helper.upgradePackages(getRootModelElement(), getModelRoot());
    .end if
    .if((multilevel) and (not setComponent))
            // upgrade 7.1.4 satisfactions
            if(actualVersion.equals(bp7_1_4VersionNumber))
            		helper.associateSatisfactionsThroughIdentifier(getModelRoot());
          .// generate code to recreate UUIDs
            // first set the represents for all
            // models that were loaded into
            // the model-root
                Model_c[] models = Model_c.ModelInstances(getGraphicsModelRoot());
                for (int i = 0; i < models.length; i++) {
                    models[i].setRepresents(Cl_c.Getinstancefromooa_id(
                            modelRoot, models[i].getOoa_id(), models[i].getOoa_type()));
                }
                // then set the represents for each
                // graphical element loaded
                GraphicalElement_c[] ges = GraphicalElement_c.GraphicalElementInstances(getGraphicsModelRoot());
                for (int i = 0; i < ges.length; i++) {
                    ges[i].setRepresents(Cl_c.Getinstancefromooa_id(modelRoot,
                            ges[i].getOoa_id(), ges[i].getOoa_type()));
                }

                // clear the id map held in CorePlugin
                CorePlugin.getImportedElementKeyMap().clear();
                for(int i = 0; i < loadedInstances.size(); i++) {
                	if(loadedInstances.get(i).isProxy()) {
                		CorePlugin.getImportedElementKeyMap().put(loadedInstances.get(i), loadedInstances.get(i).getInstanceKey());
                	}
                }
                helper.addPortReferenceInstances(loadedInstances);
                if(createUniqueIds) {                
	                // then for every loaded instance update
	                // any unique id UUID value
	                for (int i = 0; i < loadedInstances.size(); i++) {
	                    if (IDConversionUtil.getInstance().getId(
	                            loadedInstances.get(i)) != null) {
							if (!loadedInstances.get(i).hasSuperType()) {
								Object oldKey = loadedInstances.get(i).getInstanceKey();
								IDConversionUtil.getInstance().setId(
										loadedInstances.get(i),
										loadedInstances.get(i).getIdAssigner().createUUID());
								loadedInstances.get(i).updateInstanceKey(oldKey,
										loadedInstances.get(i).getInstanceKey());
							}
						}
	                }
	                // and for every loaded graphical instance
	                // do the same
	                for (int i = 0; i < loadedGraphicsInstances.size(); i++) {
	                    if (IDConversionUtil.getInstance().getId(
	                            loadedGraphicsInstances.get(i)) != null) {
	                    	Object oldKey = loadedGraphicsInstances.get(i).getInstanceKey();
	                        IDConversionUtil.getInstance().setId(
	                                loadedGraphicsInstances.get(i),
	                                loadedGraphicsInstances.get(i).getIdAssigner().createUUID());
	                        loadedGraphicsInstances.get(i).updateInstanceKey(oldKey,
	                                        loadedGraphicsInstances.get(i).getInstanceKey());
	                    }
	                }
	                // now update the ooa_id values in each
	                // model instance
	                for (int i = 0; i < models.length; i++) {
	                    UUID newId = Cl_c.Getooaid(models[i].getRepresents());
	                    if (!models[i].getOoa_id().equals(newId)) {
	                        models[i].setOoa_id(newId);
	                    }
	                }
	                // do the same for each graphical element
	                // instance
	                for (int i = 0; i < ges.length; i++) {
	                    UUID newId = Cl_c.Getooaid(ges[i].getRepresents());
	                    if (!ges[i].getOoa_id().equals(newId)) {
	                        ges[i].setOoa_id(newId);
	                    }
	                }
	           }
        .else
        helper.importPreBP7_1LineSegments();
        helper.importPreBP7_1Connectors();
        helper.importPreBP7_1_1ConnectorTexts();
        if((!actualVersion.equals("7.1.2")) && (!actualVersion.equals("7.1.3")))
            helper.createAnchorsOnSegmentsForPre7_1_2Connectors();
        .end if
    .if(setComponent or (not multilevel and not setComponent))
        d2 = ${dcn.body}.${dia.body}(modelRoot);
      .if ( multilevel )
        PersistableModelComponent parentComp = m_component.getParent();
        SystemModel_c parent = null;
        if (parentComp != null) {
            parent = (SystemModel_c) parentComp.getRootModelElement();
        }
        if (parent != null) {
            modelRoot.setRoot(parent);
        }
      .else
        if ( m_parent != null )
        {
            d2.relateAcrossR28To((SystemModel_c)m_parent);
            modelRoot.setRoot((SystemModel_c)m_parent);
        }
       .end if

        //Setting the root model element
        rootModelElement = d2;

        pm.worked(1);
        // see if the domains id is unique
    .if (callingProject == "BP")
        // Move root domain items into Tiger-compatible packages
        Function_c [] fns = Function_c.getManyS_SYNCsOnR23(d2);
        if ( fns.length > 0 ){
          FunctionPackage_c fpk = createDefaultFunctionPackage(modelRoot);
          for (int i =0; i < fns.length; i++){
            FunctionInPackage_c fip = new FunctionInPackage_c(modelRoot);
            fpk.relateAcrossR31To(fip);
            fip.relateAcrossR31To(fns[i]);
          }
        }
        DataType_c[] dts = DataType_c.DataTypeInstances(modelRoot);
        if ( dts.length > 0 ) {
          DataTypePackage_c dpk = createDefaultDataTypePackage(modelRoot);
          for (int i = 0; i < dts.length; i++) {
            DataTypeInPackage_c dip = new DataTypeInPackage_c(modelRoot);
            dpk.relateAcrossR39To(dip);
            dip.relateAcrossR39To(dts[i]);
          }
        }
        ExternalEntity_c[] ees = ExternalEntity_c.getManyS_EEsOnR8(d2);
        if ( ees.length > 0 ) {
          ExternalEntityPackage_c epk = createDefaultExternalEntityPackage(modelRoot);
          for (int i = 0; i < ees.length; i++) {
            ExternalEntityInPackage_c eip = new ExternalEntityInPackage_c(modelRoot);
            epk.relateAcrossR33To(eip);
            eip.relateAcrossR33To(ees[i]);
          }
        }
        Subsystem_c[] sss = Subsystem_c.getManyS_SSsOnR1(d2);
        for (int i = 0; i < sss.length; i++){
            SubsystemInDomain_c sid = new SubsystemInDomain_c(modelRoot);
            d2.relateAcrossR43To(sid);
            sid.relateAcrossR43To(sss[i]);
        }
    .end if
        ModelClass_c [] mc_set = ModelClass_c.ModelClassInstances(modelRoot);
        for (int i = 0; i < mc_set.length; i++)
        {
            mc_set[i].Addidentifiers();
        }
        ReferentialAttribute_c [] rattr_set = ReferentialAttribute_c.ReferentialAttributeInstances(modelRoot);
        for (int i = 0; i < rattr_set.length; i++)
        {
            rattr_set[i].Setdatatype();
        }
    .if (callingProject == "BP")
        helper.fixUpGraphicalData();
        Ooaofooa.Removeinconsistentnonlocalevents(getModelRoot());
    .end if
        .if(multilevel)
    if(actualVersion.equals(bp7_1_3VersionNumber))
        helper.migrateComponents(getModelRoot());
        .end if
        setGraphicalRepresents();
        pm.done();
        if(m_parseAll){
        AllActivityModifier aam = new AllActivityModifier(d2, pm);
        aam.processAllActivities(AllActivityModifier.PARSE);
      }

        //check if they are long based UUIDs or
        //even in case of UUID based model, it could be copy of same file with different name.
        //there is also unit test for this
    if (d2 != null && !isDomainIdUnique(d2)) {
       IDConvertor.getInstance().recreateUUID(d2);
    }
    .end if
        Ooaofooa mr = Ooaofooa.getDefaultInstance();
        Ooaofgraphics gmr = Ooaofgraphics.getDefaultInstance();
        try {
		  mr.disableDeltaCollection();
		  gmr.disableDeltaCollection();
          helper.upgradeDSAs(getModelRoot());
        helper.associateDTsWithDomain(getModelRoot());
        helper.formalizeExternalEntityPackageLinkingAssociations(getModelRoot());
        helper.formalizeFunctionPackageLinkingAssociations(getModelRoot());
        helper.createShapeSubtypes(getGraphicsModelRoot());
        loadedInstances.addAll(helper.upgradeTransitions(loadedInstances));
        helper.upgradeEventData(getModelRoot(), getRootModelElement());
        helper.upgradeLifespans(getRootModelElement());
        helper.upgradeElementOrder(getModelRoot());
        helper.upgradeEEsForIsRealized();
		.if(multilevel or setComponent)
		loadedInstances.addAll(helper.upgradeDatatypes(loadedInstances));
		loadedInstances.addAll(helper.upgradeSystemLevelPackages(loadedInstances));
		loadedInstances.addAll(helper.upgradeNestedPackages(getRootModelElement()));
	    loadedInstances.addAll(helper.addLinkClassesForContainment(loadedInstances));
		helper.removeSupertypePackageableElement(loadedInstances);
		helper.upgradeGraphicalAnchors(loadedGraphicsInstances);
		helper.configureMessages(loadedGraphicsInstances);
		helper.fixSatisfactionAssociations(loadedInstances);
		helper.loadDataTypesFromCoreTypeProxy(loadedInstances);
		.else
		helper.upgradeDatatypes(loadedInstances);
		.end if
        }
        finally {
            mr.enableDeltaCollection();
            gmr.enableDeltaCollection();
        }
    }   finally {
                    ModelRoot.enableChangeNotification();
    }
        .if((not multilevel) and (not setComponent))
	  if (d2 != null) {
        Ooaofooa.getDefaultInstance().fireModelElementLoaded(d2);
	  }
        .end if
    }
  }
.end function
.function backwardCompatibilitySizeAdjustment
  .param inst_ref table
  .param boolean isProxy
    .select many col_set related by table->C[R5];
    .assign no_of_cols = cardinality col_set;
    .if (isProxy)
      .assign no_of_cols = no_of_cols + 1
    .end if
    .if(table.Key_Lett == "O_RATTR" )
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if(((table.Key_Lett == "C_IO") or (table.Key_Lett == "C_AS")) or (table.Key_Lett == "C_PP"))
      parms.setSize(${no_of_cols});
    .end if
    .if((((((((((table.Key_Lett == "COMM_COMM") or (table.Key_Lett == "SQ_S")) or (table.Key_Lett == "UC_UCC")) or (table.Key_Lett == "A_A")) or (table.Key_Lett == "C_EP")) or (table.Key_Lett == "SM_PEVT")) or (table.Key_Lett == "C_P")) or (table.Key_Lett == "C_R")) or (table.Key_Lett == "C_SF")) or (table.Key_Lett == "O_OIDA"))
      .// move the proxy path param to the end of the list
    	 Object result = parms.remove(parms.indexOf(parms.lastElement()));
    	 parms.setSize(${no_of_cols} - 1);
    	 parms.add(result);
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "C_DG")
      .// move the proxy path param to the end of the list
    	 Object result = parms.remove(parms.indexOf(parms.lastElement()));
    	 parms.setSize(${no_of_cols} - 1);
    	 parms.add(result);
      parms.setSize(${no_of_cols});
    .end if
    .if ( ((table.Key_Lett == "S_BRG") OR (table.Key_Lett == "S_SYNC")) OR ((table.Key_Lett == "O_TFR") OR (table.Key_Lett == "V_TRN")))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ( ((table.Key_Lett == "S_BPARM") OR (table.Key_Lett == "S_SPARM")) OR (table.Key_Lett == "O_TPARM"))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if ((table.Key_Lett == "O_ATTR") OR (table.Key_Lett == "SM_EVTDI"))
    parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if (table.Key_Lett == "V_PVL")
      parms.setSize(${no_of_cols});
    .end if
    .if (table.Key_Lett == "V_VAR")
      parms.setSize(${no_of_cols});
    .end if
    .if(table.Key_Lett == "ACT_IOP")
      parms.setSize(${no_of_cols});
      if(parms.elementAt(6) == null) {
		    parms.setElementAt(Gd_c.Null_unique_id().toString(), 1);
				parms.insertElementAt(Gd_c.Null_unique_id().toString(), 2);
		  }
    .end if
    .if(table.Key_Lett == "ACT_SGN")
      parms.setSize(${no_of_cols});
      if(parms.elementAt(6) == null) {
		    parms.setElementAt(Gd_c.Null_unique_id().toString(), 1);
				parms.insertElementAt(Gd_c.Null_unique_id().toString(), 2);
		  }
    .end if
    .if(table.Key_Lett == "V_MSV")
      parms.setSize(${no_of_cols});
      if(parms.elementAt(5) == null) {
		    parms.insertElementAt(Gd_c.Null_unique_id().toString(), 1);
				parms.insertElementAt(Gd_c.Null_unique_id().toString(), 2);
				parms.removeElementAt(6);
				Object elementAt5 = parms.elementAt(5);
				if(((String)elementAt5).equals("true") || ((String)elementAt5).equals("false")) {
					parms.insertElementAt(elementAt5, 3);
					parms.removeElementAt(6);
				}
		  }
    .end if
    .if (table.Key_Lett == "O_REF" )
      parms.setSize(${no_of_cols});
        .// set columns size in case of backward compatibility
    .end if
    .if (table.Key_Lett == "C_C" )
      parms.setSize(${no_of_cols});
        .// set columns size for backward compatibility
    .end if
    .if (table.Key_Lett == "S_EE" )
      parms.setSize(${no_of_cols});
        .// set columns size for backward compatibility
    .end if
    .if (table.Key_Lett == "O_OBJ" )
      parms.setSize(${no_of_cols});
        .// set columns size for backward compatibility
    .end if
.end function
