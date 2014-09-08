package com.mentor.nucleus.bp.core.inspector;
//========================================================================
//
//File:      $RCSfile: PackageInspector.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 13:26:34 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

import com.mentor.nucleus.bp.core.*;

import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import java.util.*;

/**
 * This file adapts the meta-model entity; 'Package_c' to provide 
 * support of traversing to its children and parents.
 * <p>
 * Do not edit this class, it was created using the Mentor Graphics
 * MC-Java code generator product.
 * </p>
 */
public class PackageInspector extends BaseModelClassInspector {

  private String parentAssoc;
  PackageTreeDifferenceSlot slot_class = new PackageTreeDifferenceSlot();
  
  public PackageInspector(MetadataSortingManager sortingManager){
    super(sortingManager);
  }

  /**
   * @see IModelClassInspector#getParent(Object)
   * Returns the parent(s) of this node
   */
  public Object getParent(Object arg) {
    Component_c result1 = Component_c.getOneC_COnR8003(PackageableElement_c.getOnePE_PEOnR8001((Package_c)arg));
     if (result1 != null) {
        return result1;
    }               
    Package_c result2 = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001((Package_c)arg));
     if (result2 != null) {
        return result2;
    }         
     SystemModel_c result3 = SystemModel_c.getOneS_SYSOnR1401((Package_c) arg);
     if(result3 != null) {
    	 return result3;
     }
        return null;    //No parent found           
  }
  	  
  /**
   * @see IModelClassInspector#getChildRelations(Object)
   * Returns the children of this node
   */
  public ObjectElement[] getChildRelations(Object arg) {
    int resultSize = 0;
    ModelClass_c [] v_class = 
ModelClass_c.getManyO_OBJsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_class);
    Function_c [] v_function = 
Function_c.getManyS_SYNCsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_function);
    ExternalEntity_c [] v_externalentity = 
ExternalEntity_c.getManyS_EEsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_externalentity);
    Association_c [] v_association = 
Association_c.getManyR_RELsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_association);
    ImportedClass_c [] v_importedclass = 
ImportedClass_c.getManyO_IOBJsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_importedclass);
    UserDataType_c [] v_userdefineddatatype = 
UserDataType_c.getManyS_UDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_userdefineddatatype);
    CoreDataType_c [] v_coredatatype = 
CoreDataType_c.getManyS_CDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_coredatatype);
    EnumerationDataType_c [] v_enumeration = 
EnumerationDataType_c.getManyS_EDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_enumeration);
    ClassInstanceParticipant_c [] v_instances = 
ClassInstanceParticipant_c.getManySQ_CIPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_instances);
    ExternalEntityParticipant_c [] v_importedexternalentities = 
ExternalEntityParticipant_c.getManySQ_EEPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_importedexternalentities);
    PackageParticipant_c [] v_importedpackages = 
PackageParticipant_c.getManySQ_PPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_importedpackages);
    ClassParticipant_c [] v_importedclasses = 
ClassParticipant_c.getManySQ_CPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_importedclasses);
    SynchronousMessage_c [] v_synchronousmessages = 
SynchronousMessage_c.getManyMSG_SMsOnR1018(
Message_c.getManyMSG_MsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_synchronousmessages);
    AsynchronousMessage_c [] v_asynchronousmessages = 
AsynchronousMessage_c.getManyMSG_AMsOnR1018(
Message_c.getManyMSG_MsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_asynchronousmessages);
    ActorParticipant_c [] v_actors = 
ActorParticipant_c.getManySQ_APsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_actors);
    ReturnMessage_c [] v_returnmessages = 
ReturnMessage_c.getManyMSG_RsOnR1018(
Message_c.getManyMSG_MsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_returnmessages);
    CommunicationLink_c [] v_communicationlinks = 
CommunicationLink_c.getManyCOMM_LNKsOnR1133(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_communicationlinks);
    UseCaseParticipant_c [] v_usecases = 
UseCaseParticipant_c.getManyIA_UCPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_usecases);
    BinaryAssociation_c [] v_usecaseassociations = 
BinaryAssociation_c.getManyUC_BAsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_usecaseassociations);
    Generalization_c [] v_usecasegeneralizations = 
Generalization_c.getManyUC_GsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_usecasegeneralizations);
    Include_c [] v_usecaseincludes = 
Include_c.getManyUC_IsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_usecaseincludes);
    Extend_c [] v_usecaseextends = 
Extend_c.getManyUC_EsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_usecaseextends);
    ForkJoinNode_c [] v_forkjoins = 
ForkJoinNode_c.getManyA_FJsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_forkjoins);
    InitialNode_c [] v_initialnodes = 
InitialNode_c.getManyA_INIsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_initialnodes);
    ActivityEdge_c [] v_activityedges = 
ActivityEdge_c.getManyA_EsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_activityedges);
    ActivityFinalNode_c [] v_activityfinalnodes = 
ActivityFinalNode_c.getManyA_AFsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_activityfinalnodes);
    FlowFinalNode_c [] v_flowfinalnodes = 
FlowFinalNode_c.getManyA_FFsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_flowfinalnodes);
    ActivityDiagramAction_c [] v_activitydiagramactions = 
ActivityDiagramAction_c.getManyA_GAsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_activitydiagramactions);
    DecisionMergeNode_c [] v_decisionmergenodes = 
DecisionMergeNode_c.getManyA_DMsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_decisionmergenodes);
    ObjectNode_c [] v_objectnodes = 
ObjectNode_c.getManyA_OBJsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_objectnodes);
    AcceptEventAction_c [] v_accepteventactions = 
AcceptEventAction_c.getManyA_AEAsOnR1112(
AcceptEvent_c.getManyA_AEsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
)
;



    sort(v_accepteventactions);
    SendSignal_c [] v_sendsignalactions = 
SendSignal_c.getManyA_SSsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    sort(v_sendsignalactions);
    ActivityPartition_c [] v_activitypartitions = 
ActivityPartition_c.getManyA_APsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_activitypartitions);
    AcceptTimeEventAction_c [] v_accepttimeeventactions = 
AcceptTimeEventAction_c.getManyA_ATEsOnR1112(
AcceptEvent_c.getManyA_AEsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
)
;



    sort(v_accepttimeeventactions);
    Component_c [] v_components = 
Component_c.getManyC_CsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_components);
    ComponentReference_c [] v_componentreferences = 
ComponentReference_c.getManyCL_ICsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_componentreferences);
    Interface_c [] v_interfaces = 
Interface_c.getManyC_IsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_interfaces);
    StructuredDataType_c [] v_structureddatatype = 
StructuredDataType_c.getManyS_SDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_structureddatatype);
    ComponentParticipant_c [] v_componentparticipants = 
ComponentParticipant_c.getManySQ_COPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    sort(v_componentparticipants);
    Package_c [] v_packages = 
Package_c.getManyEP_PKGsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_packages);
    ConstantSpecification_c [] v_constantspecification = 
ConstantSpecification_c.getManyCNST_CSPsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_constantspecification);
    Satisfaction_c [] v_satisfactions = 
Satisfaction_c.getManyC_SFsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    sort(v_satisfactions);
    List<ObjectElement> result = new ArrayList<ObjectElement> ();
    for (int i = 0 ; i < v_class.length ; i++) {   
        result.add(new ObjectElement("v_class", ObjectElement.RELATION_ROLE_ELEMENT, v_class[i], arg, false));
    }
    for (int i = 0 ; i < v_function.length ; i++) {   
        result.add(new ObjectElement("v_function", ObjectElement.RELATION_ROLE_ELEMENT, v_function[i], arg, false));
    }
    for (int i = 0 ; i < v_externalentity.length ; i++) {   
        result.add(new ObjectElement("v_externalentity", ObjectElement.RELATION_ROLE_ELEMENT, v_externalentity[i], arg, false));
    }
    for (int i = 0 ; i < v_association.length ; i++) {   
        result.add(new ObjectElement("v_association", ObjectElement.RELATION_ROLE_ELEMENT, v_association[i], arg, false));
    }
    for (int i = 0 ; i < v_importedclass.length ; i++) {   
        result.add(new ObjectElement("v_importedclass", ObjectElement.RELATION_ROLE_ELEMENT, v_importedclass[i], arg, false));
    }
    for (int i = 0 ; i < v_userdefineddatatype.length ; i++) {   
        result.add(new ObjectElement("v_userdefineddatatype", ObjectElement.RELATION_ROLE_ELEMENT, v_userdefineddatatype[i], arg, false));
    }
    for (int i = 0 ; i < v_coredatatype.length ; i++) {   
        result.add(new ObjectElement("v_coredatatype", ObjectElement.RELATION_ROLE_ELEMENT, v_coredatatype[i], arg, false));
    }
    for (int i = 0 ; i < v_enumeration.length ; i++) {   
        result.add(new ObjectElement("v_enumeration", ObjectElement.RELATION_ROLE_ELEMENT, v_enumeration[i], arg, false));
    }
    for (int i = 0 ; i < v_instances.length ; i++) {   
        result.add(new ObjectElement("v_instances", ObjectElement.RELATION_ROLE_ELEMENT, v_instances[i], arg, false));
    }
    for (int i = 0 ; i < v_importedexternalentities.length ; i++) {   
        result.add(new ObjectElement("v_importedexternalentities", ObjectElement.RELATION_ROLE_ELEMENT, v_importedexternalentities[i], arg, false));
    }
    for (int i = 0 ; i < v_importedpackages.length ; i++) {   
        result.add(new ObjectElement("v_importedpackages", ObjectElement.RELATION_ROLE_ELEMENT, v_importedpackages[i], arg, false));
    }
    for (int i = 0 ; i < v_importedclasses.length ; i++) {   
        result.add(new ObjectElement("v_importedclasses", ObjectElement.RELATION_ROLE_ELEMENT, v_importedclasses[i], arg, false));
    }
    for (int i = 0 ; i < v_synchronousmessages.length ; i++) {   
      if(!(InteractionParticipant_c.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(v_synchronousmessages[i])) != null)) {
        result.add(new ObjectElement("v_synchronousmessages", ObjectElement.RELATION_ROLE_ELEMENT, v_synchronousmessages[i], arg, false));
      }
    }
    for (int i = 0 ; i < v_asynchronousmessages.length ; i++) {   
      if(!(InteractionParticipant_c.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(v_asynchronousmessages[i])) != null)) {
        result.add(new ObjectElement("v_asynchronousmessages", ObjectElement.RELATION_ROLE_ELEMENT, v_asynchronousmessages[i], arg, false));
      }
    }
    for (int i = 0 ; i < v_actors.length ; i++) {   
        result.add(new ObjectElement("v_actors", ObjectElement.RELATION_ROLE_ELEMENT, v_actors[i], arg, false));
    }
    for (int i = 0 ; i < v_returnmessages.length ; i++) {   
      if(!(InteractionParticipant_c.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(v_returnmessages[i])) != null)) {
        result.add(new ObjectElement("v_returnmessages", ObjectElement.RELATION_ROLE_ELEMENT, v_returnmessages[i], arg, false));
      }
    }
    for (int i = 0 ; i < v_communicationlinks.length ; i++) {   
        result.add(new ObjectElement("v_communicationlinks", ObjectElement.RELATION_ROLE_ELEMENT, v_communicationlinks[i], arg, false));
    }
    for (int i = 0 ; i < v_usecases.length ; i++) {   
        result.add(new ObjectElement("v_usecases", ObjectElement.RELATION_ROLE_ELEMENT, v_usecases[i], arg, false));
    }
    for (int i = 0 ; i < v_usecaseassociations.length ; i++) {   
        result.add(new ObjectElement("v_usecaseassociations", ObjectElement.RELATION_ROLE_ELEMENT, v_usecaseassociations[i], arg, false));
    }
    for (int i = 0 ; i < v_usecasegeneralizations.length ; i++) {   
        result.add(new ObjectElement("v_usecasegeneralizations", ObjectElement.RELATION_ROLE_ELEMENT, v_usecasegeneralizations[i], arg, false));
    }
    for (int i = 0 ; i < v_usecaseincludes.length ; i++) {   
        result.add(new ObjectElement("v_usecaseincludes", ObjectElement.RELATION_ROLE_ELEMENT, v_usecaseincludes[i], arg, false));
    }
    for (int i = 0 ; i < v_usecaseextends.length ; i++) {   
        result.add(new ObjectElement("v_usecaseextends", ObjectElement.RELATION_ROLE_ELEMENT, v_usecaseextends[i], arg, false));
    }
    for (int i = 0 ; i < v_forkjoins.length ; i++) {   
        result.add(new ObjectElement("v_forkjoins", ObjectElement.RELATION_ROLE_ELEMENT, v_forkjoins[i], arg, false));
    }
    for (int i = 0 ; i < v_initialnodes.length ; i++) {   
        result.add(new ObjectElement("v_initialnodes", ObjectElement.RELATION_ROLE_ELEMENT, v_initialnodes[i], arg, false));
    }
    for (int i = 0 ; i < v_activityedges.length ; i++) {   
        result.add(new ObjectElement("v_activityedges", ObjectElement.RELATION_ROLE_ELEMENT, v_activityedges[i], arg, false));
    }
    for (int i = 0 ; i < v_activityfinalnodes.length ; i++) {   
        result.add(new ObjectElement("v_activityfinalnodes", ObjectElement.RELATION_ROLE_ELEMENT, v_activityfinalnodes[i], arg, false));
    }
    for (int i = 0 ; i < v_flowfinalnodes.length ; i++) {   
        result.add(new ObjectElement("v_flowfinalnodes", ObjectElement.RELATION_ROLE_ELEMENT, v_flowfinalnodes[i], arg, false));
    }
    for (int i = 0 ; i < v_activitydiagramactions.length ; i++) {   
        result.add(new ObjectElement("v_activitydiagramactions", ObjectElement.RELATION_ROLE_ELEMENT, v_activitydiagramactions[i], arg, false));
    }
    for (int i = 0 ; i < v_decisionmergenodes.length ; i++) {   
        result.add(new ObjectElement("v_decisionmergenodes", ObjectElement.RELATION_ROLE_ELEMENT, v_decisionmergenodes[i], arg, false));
    }
    for (int i = 0 ; i < v_objectnodes.length ; i++) {   
        result.add(new ObjectElement("v_objectnodes", ObjectElement.RELATION_ROLE_ELEMENT, v_objectnodes[i], arg, false));
    }
    for (int i = 0 ; i < v_accepteventactions.length ; i++) {   
        result.add(new ObjectElement("v_accepteventactions", ObjectElement.RELATION_ROLE_ELEMENT, v_accepteventactions[i], arg, false));
    }
    for (int i = 0 ; i < v_sendsignalactions.length ; i++) {   
        result.add(new ObjectElement("v_sendsignalactions", ObjectElement.RELATION_ROLE_ELEMENT, v_sendsignalactions[i], arg, false));
    }
    for (int i = 0 ; i < v_activitypartitions.length ; i++) {   
        result.add(new ObjectElement("v_activitypartitions", ObjectElement.RELATION_ROLE_ELEMENT, v_activitypartitions[i], arg, false));
    }
    for (int i = 0 ; i < v_accepttimeeventactions.length ; i++) {   
        result.add(new ObjectElement("v_accepttimeeventactions", ObjectElement.RELATION_ROLE_ELEMENT, v_accepttimeeventactions[i], arg, false));
    }
    for (int i = 0 ; i < v_components.length ; i++) {   
        result.add(new ObjectElement("v_components", ObjectElement.RELATION_ROLE_ELEMENT, v_components[i], arg, false));
    }
    for (int i = 0 ; i < v_componentreferences.length ; i++) {   
        result.add(new ObjectElement("v_componentreferences", ObjectElement.RELATION_ROLE_ELEMENT, v_componentreferences[i], arg, false));
    }
    for (int i = 0 ; i < v_interfaces.length ; i++) {   
        result.add(new ObjectElement("v_interfaces", ObjectElement.RELATION_ROLE_ELEMENT, v_interfaces[i], arg, false));
    }
    for (int i = 0 ; i < v_structureddatatype.length ; i++) {   
        result.add(new ObjectElement("v_structureddatatype", ObjectElement.RELATION_ROLE_ELEMENT, v_structureddatatype[i], arg, false));
    }
    for (int i = 0 ; i < v_componentparticipants.length ; i++) {   
        result.add(new ObjectElement("v_componentparticipants", ObjectElement.RELATION_ROLE_ELEMENT, v_componentparticipants[i], arg, false));
    }
    for (int i = 0 ; i < v_packages.length ; i++) {   
        result.add(new ObjectElement("v_packages", ObjectElement.RELATION_ROLE_ELEMENT, v_packages[i], arg, false));
    }
    for (int i = 0 ; i < v_constantspecification.length ; i++) {   
        result.add(new ObjectElement("v_constantspecification", ObjectElement.RELATION_ROLE_ELEMENT, v_constantspecification[i], arg, false));
    }
    for (int i = 0 ; i < v_satisfactions.length ; i++) {   
        result.add(new ObjectElement("v_satisfactions", ObjectElement.RELATION_ROLE_ELEMENT, v_satisfactions[i], arg, false));
    }
    return result.toArray(new ObjectElement[result.size()]);
  }
  /**
   * @see IModelClassInspector#hasChildren(Object)
   * Returns true if this node has any children
   */
  public boolean hasChildRelations(Object arg) {
    ModelClass_c [] v_class = 
ModelClass_c.getManyO_OBJsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_class.length > 0) return true;
    Function_c [] v_function = 
Function_c.getManyS_SYNCsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_function.length > 0) return true;
    ExternalEntity_c [] v_externalentity = 
ExternalEntity_c.getManyS_EEsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_externalentity.length > 0) return true;
    Association_c [] v_association = 
Association_c.getManyR_RELsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_association.length > 0) return true;
    ImportedClass_c [] v_importedclass = 
ImportedClass_c.getManyO_IOBJsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_importedclass.length > 0) return true;
    UserDataType_c [] v_userdefineddatatype = 
UserDataType_c.getManyS_UDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_userdefineddatatype.length > 0) return true;
    CoreDataType_c [] v_coredatatype = 
CoreDataType_c.getManyS_CDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_coredatatype.length > 0) return true;
    EnumerationDataType_c [] v_enumeration = 
EnumerationDataType_c.getManyS_EDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_enumeration.length > 0) return true;
    ClassInstanceParticipant_c [] v_instances = 
ClassInstanceParticipant_c.getManySQ_CIPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_instances.length > 0) return true;
    ExternalEntityParticipant_c [] v_importedexternalentities = 
ExternalEntityParticipant_c.getManySQ_EEPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_importedexternalentities.length > 0) return true;
    PackageParticipant_c [] v_importedpackages = 
PackageParticipant_c.getManySQ_PPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_importedpackages.length > 0) return true;
    ClassParticipant_c [] v_importedclasses = 
ClassParticipant_c.getManySQ_CPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_importedclasses.length > 0) return true;
    SynchronousMessage_c [] v_synchronousmessages = 
SynchronousMessage_c.getManyMSG_SMsOnR1018(
Message_c.getManyMSG_MsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_synchronousmessages.length > 0) return true;
    AsynchronousMessage_c [] v_asynchronousmessages = 
AsynchronousMessage_c.getManyMSG_AMsOnR1018(
Message_c.getManyMSG_MsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_asynchronousmessages.length > 0) return true;
    ActorParticipant_c [] v_actors = 
ActorParticipant_c.getManySQ_APsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_actors.length > 0) return true;
    ReturnMessage_c [] v_returnmessages = 
ReturnMessage_c.getManyMSG_RsOnR1018(
Message_c.getManyMSG_MsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_returnmessages.length > 0) return true;
    CommunicationLink_c [] v_communicationlinks = 
CommunicationLink_c.getManyCOMM_LNKsOnR1133(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_communicationlinks.length > 0) return true;
    UseCaseParticipant_c [] v_usecases = 
UseCaseParticipant_c.getManyIA_UCPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_usecases.length > 0) return true;
    BinaryAssociation_c [] v_usecaseassociations = 
BinaryAssociation_c.getManyUC_BAsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_usecaseassociations.length > 0) return true;
    Generalization_c [] v_usecasegeneralizations = 
Generalization_c.getManyUC_GsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_usecasegeneralizations.length > 0) return true;
    Include_c [] v_usecaseincludes = 
Include_c.getManyUC_IsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_usecaseincludes.length > 0) return true;
    Extend_c [] v_usecaseextends = 
Extend_c.getManyUC_EsOnR1210(
UseCaseAssociation_c.getManyUC_UCAsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_usecaseextends.length > 0) return true;
    ForkJoinNode_c [] v_forkjoins = 
ForkJoinNode_c.getManyA_FJsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_forkjoins.length > 0) return true;
    InitialNode_c [] v_initialnodes = 
InitialNode_c.getManyA_INIsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_initialnodes.length > 0) return true;
    ActivityEdge_c [] v_activityedges = 
ActivityEdge_c.getManyA_EsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_activityedges.length > 0) return true;
    ActivityFinalNode_c [] v_activityfinalnodes = 
ActivityFinalNode_c.getManyA_AFsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_activityfinalnodes.length > 0) return true;
    FlowFinalNode_c [] v_flowfinalnodes = 
FlowFinalNode_c.getManyA_FFsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_flowfinalnodes.length > 0) return true;
    ActivityDiagramAction_c [] v_activitydiagramactions = 
ActivityDiagramAction_c.getManyA_GAsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_activitydiagramactions.length > 0) return true;
    DecisionMergeNode_c [] v_decisionmergenodes = 
DecisionMergeNode_c.getManyA_DMsOnR1106(
ControlNode_c.getManyA_CTLsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_decisionmergenodes.length > 0) return true;
    ObjectNode_c [] v_objectnodes = 
ObjectNode_c.getManyA_OBJsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_objectnodes.length > 0) return true;
    AcceptEventAction_c [] v_accepteventactions = 
AcceptEventAction_c.getManyA_AEAsOnR1112(
AcceptEvent_c.getManyA_AEsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
)
;



    if (v_accepteventactions.length > 0) return true;
    SendSignal_c [] v_sendsignalactions = 
SendSignal_c.getManyA_SSsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
;



    if (v_sendsignalactions.length > 0) return true;
    ActivityPartition_c [] v_activitypartitions = 
ActivityPartition_c.getManyA_APsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_activitypartitions.length > 0) return true;
    AcceptTimeEventAction_c [] v_accepttimeeventactions = 
AcceptTimeEventAction_c.getManyA_ATEsOnR1112(
AcceptEvent_c.getManyA_AEsOnR1107(
ActionNode_c.getManyA_ACTsOnR1105(
ActivityNode_c.getManyA_NsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
)
)
;



    if (v_accepttimeeventactions.length > 0) return true;
    Component_c [] v_components = 
Component_c.getManyC_CsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_components.length > 0) return true;
    ComponentReference_c [] v_componentreferences = 
ComponentReference_c.getManyCL_ICsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_componentreferences.length > 0) return true;
    Interface_c [] v_interfaces = 
Interface_c.getManyC_IsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_interfaces.length > 0) return true;
    StructuredDataType_c [] v_structureddatatype = 
StructuredDataType_c.getManyS_SDTsOnR17(
DataType_c.getManyS_DTsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_structureddatatype.length > 0) return true;
    ComponentParticipant_c [] v_componentparticipants = 
ComponentParticipant_c.getManySQ_COPsOnR930(
InteractionParticipant_c.getManySQ_PsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
)
;



    if (v_componentparticipants.length > 0) return true;
    Package_c [] v_packages = 
Package_c.getManyEP_PKGsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_packages.length > 0) return true;
    ConstantSpecification_c [] v_constantspecification = 
ConstantSpecification_c.getManyCNST_CSPsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_constantspecification.length > 0) return true;
    Satisfaction_c [] v_satisfactions = 
Satisfaction_c.getManyC_SFsOnR8001(
PackageableElement_c.getManyPE_PEsOnR8000(
(Package_c)arg)
)
;



    if (v_satisfactions.length > 0) return true;
    return false;
  }
  
  public Object[] getReferentialDetails(Class<?> referentialClass, Object arg) {
  	Object[] details = new Object[4];
	return details;
  }
  
  /**
  * @seee IModelClassInspector#getReferentials(Object)
  * Returns the an array of Role Objects specifying the referenctial attibutes of
  * this model element
  * Role Name: <T_TPS.NameOnly>
  * Role Value: <Chain_result>
  * Role Type: "Referential"
  */
  public ObjectElement[] getReferentials(Object arg) {
     List<ObjectElement> referentials = new ArrayList<ObjectElement>();
    return referentials.toArray(new ObjectElement [referentials.size()]);        
  }

  /**
  * @seee IModelClassInspector#getAttributes(Object)
  * Returns the an array of Role objects that give the attibutes of the model 
  * element it self e.g.
  * Role Name: "Name"
  * Role Value: metaModelElement.getName()
  * Role Type: "Primitive"
  */
  public ObjectElement[] getAttributes(Object arg) {
    ObjectElement attrSet[] = new ObjectElement[7];
      attrSet[0] = new ObjectElement("Name", ObjectElement.ATTRIBUTE_ELEMENT,  ((Package_c) arg).getName(), arg, "getName", true);
      attrSet[1] = new ObjectElement("Descrip", ObjectElement.ATTRIBUTE_ELEMENT,  ((Package_c) arg).getDescrip(), arg, "getDescrip", true);
      attrSet[2] = new ObjectElement("Num_Rng", ObjectElement.ATTRIBUTE_ELEMENT,  ((Package_c) arg).getNum_rng(), arg, "getNum_rng", true);
      PackageableElement_c VisibilityOwner = 
      PackageableElement_c.getOnePE_PEOnR8001(
(Package_c) arg)

;
      if(VisibilityOwner != null) {
		   attrSet[3] = new ObjectElement("Visibility", ObjectElement.ATTRIBUTE_ELEMENT, VisibilityOwner.getVisibility(), arg, "getVisibility", true);
		   attrSet[3].setAttributeOwner(VisibilityOwner);
  	  }
    ObjectElement compAttrSet[] = new ObjectElement[4];    
    for (int i = 0; i < 4 ; i++) {
        compAttrSet[i] = attrSet[i];
    }
    return compAttrSet;
  }

    /**
     * Return configured image if none exists for the element type
     */
    public Image getImage(Object element) {
		Image image = CorePlugin.getImageFor(element, false);
		if(image != null) {
			return image;
		}
		return CorePlugin.getImageFor("Package.gif");
	}

    /**
     * Returns a "slot number" to be used when comparing and merging tree elements
     * that contain multiple children.  All model element inspector's contain this 
     * function, but it only returns a value for cases where it a parent element
     * in a tree and the parent has multiple children.
     * 
     * @returns 0 if this is not a "parent" in the tree OR if the parent has less 
     * than 2 children. 
     */
   	@Override
   	public int getTreeDifferenceSlot(Object element) {
   		return slot_class.getTreeDifferenceSlot(element);
   	}
   	}       

