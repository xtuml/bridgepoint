//========================================================================
//
//File:      $RCSfile: GenericPackageUpgradeHelper.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/12 01:07:56 $
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

package com.mentor.nucleus.bp.io.mdl.upgrade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.ActivityEdge_c;
import com.mentor.nucleus.bp.core.ActivityInActivity_c;
import com.mentor.nucleus.bp.core.ActivityNode_c;
import com.mentor.nucleus.bp.core.ActivityPartition_c;
import com.mentor.nucleus.bp.core.Activity_c;
import com.mentor.nucleus.bp.core.AssociationInUseCase_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.CommunicationInCommunication_c;
import com.mentor.nucleus.bp.core.Communication_c;
import com.mentor.nucleus.bp.core.ComponentInComponent_c;
import com.mentor.nucleus.bp.core.ComponentPackageInPackage_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ConstantInPackage_c;
import com.mentor.nucleus.bp.core.ConstantSpecification_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataTypeInPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackageInPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DatatypeInSuppression_c;
import com.mentor.nucleus.bp.core.DelegationInComponent_c;
import com.mentor.nucleus.bp.core.Delegation_c;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EePackageInPackage_c;
import com.mentor.nucleus.bp.core.Elementtypeconstants_c;
import com.mentor.nucleus.bp.core.ExternalEntityInPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackageInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackageParticipant_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceReferenceDataType_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.InterfacePackageInInterfacePackage_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.MessageInCommunication_c;
import com.mentor.nucleus.bp.core.MessageInSequence_c;
import com.mentor.nucleus.bp.core.Message_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.PackageInPackage_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.ParticipantInCommunication_c;
import com.mentor.nucleus.bp.core.ParticipantInUseCase_c;
import com.mentor.nucleus.bp.core.SatisfactionInComponentPackage_c;
import com.mentor.nucleus.bp.core.SatisfactionInComponent_c;
import com.mentor.nucleus.bp.core.Satisfaction_c;
import com.mentor.nucleus.bp.core.SequenceInSequence_c;
import com.mentor.nucleus.bp.core.Sequence_c;
import com.mentor.nucleus.bp.core.SpecificationPackage_c;
import com.mentor.nucleus.bp.core.SubsystemInSubsystem_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UseCaseAssociation_c;
import com.mentor.nucleus.bp.core.UseCaseDiagram_c;
import com.mentor.nucleus.bp.core.UseCaseInUseCase_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.Visibility_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.Diagram_c;
import com.mentor.nucleus.bp.ui.canvas.Diagramelement_c;
import com.mentor.nucleus.bp.ui.canvas.ElementInModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ElementInSuppression_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Modeltype_c;
import com.mentor.nucleus.bp.ui.canvas.NoncontainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;

/**
 * 
 * This class takes care of upgrading legacy format BridgePoint models to
 * Generic Package format.
 */
public class GenericPackageUpgradeHelper {
	private static ArrayList<NonRootModelElement> elementsToDispose = new ArrayList<NonRootModelElement>();
	private static ModelSpecification_c pms = null;
	private static ElementSpecification_c pes = null;

	public static Package_c[] upgradeToGenerics(SystemModel_c system,
			NonRootModelElement[] loadedInstances,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			IProgressMonitor monitor) {
		elementsToDispose.clear();
		ArrayList<Package_c> results = new ArrayList<Package_c>();
		Domain_c[] dms = Domain_c.getManyS_DOMsOnR28(system);
		ComponentPackage_c[] cps = ComponentPackage_c
				.getManyCP_CPsOnR4602(system);
		InterfacePackage_c[] ifps = InterfacePackage_c
				.getManyIP_IPsOnR4302(system);
		DataTypePackage_c[] sysDtpkgs = DataTypePackage_c
				.getManyS_DPKsOnR4400(system);
		Sequence_c[] seqDgrms = Sequence_c.getManySQ_SsOnR950(system);
		UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c
				.getManyUC_UCCsOnR1211(system);
		Communication_c[] comDgrms = Communication_c
				.getManyCOMM_COMMsOnR1136(system);
		Activity_c[] actDgrms = Activity_c.getManyA_AsOnR1113(system);
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(system);

		ArrayList<NonRootModelElement> loaded = null;
		if (loadedInstances != null) {
			loaded = new ArrayList<NonRootModelElement>(loadedInstances.length);
			Collections.addAll(loaded, loadedInstances);
		}
		ArrayList<Domain_c> dmsTemp = new ArrayList<Domain_c>();
		ArrayList<ComponentPackage_c> cpsTemp = new ArrayList<ComponentPackage_c>();
		ArrayList<InterfacePackage_c> ifpsTemp = new ArrayList<InterfacePackage_c>();
		ArrayList<DataTypePackage_c> sysDtpkgsTemp = new ArrayList<DataTypePackage_c>();
		ArrayList<Sequence_c> seqDgrmsTemp = new ArrayList<Sequence_c>();
		ArrayList<UseCaseDiagram_c> ucDgrmsTemp = new ArrayList<UseCaseDiagram_c>();
		ArrayList<Communication_c> comDgrmsTemp = new ArrayList<Communication_c>();
		ArrayList<Activity_c> actDgrmsTemp = new ArrayList<Activity_c>();
		ArrayList<Package_c> pkgsTemp = new ArrayList<Package_c>();

		for (int i = 0; i < dms.length; i++) {
			if (loaded == null || loaded.contains(dms[i]))
				Collections.addAll(dmsTemp, dms[i]);
		}
		dms = dmsTemp.toArray(new Domain_c[] {});
		;
		for (int i = 0; i < cps.length; i++) {
			if (loaded == null || loaded.contains(cps[i]))
				Collections.addAll(cpsTemp, cps[i]);
		}
		cps = cpsTemp.toArray(new ComponentPackage_c[] {});

		for (int i = 0; i < ifps.length; i++) {
			if (loaded == null || loaded.contains(ifps[i]))
				Collections.addAll(ifpsTemp, ifps[i]);
		}
		ifps = ifpsTemp.toArray(new InterfacePackage_c[] {});

		for (int i = 0; i < sysDtpkgs.length; i++) {
			if (loaded == null || loaded.contains(sysDtpkgs[i]))
				Collections.addAll(sysDtpkgsTemp, sysDtpkgs[i]);
		}
		sysDtpkgs = sysDtpkgsTemp.toArray(new DataTypePackage_c[] {});

		for (int i = 0; i < seqDgrms.length; i++) {
			if (loaded == null || loaded.contains(seqDgrms[i]))
				Collections.addAll(seqDgrmsTemp, seqDgrms[i]);
		}
		seqDgrms = seqDgrmsTemp.toArray(new Sequence_c[] {});

		for (int i = 0; i < ucDgrms.length; i++) {
			if (loaded == null || loaded.contains(ucDgrms[i]))
				Collections.addAll(ucDgrmsTemp, ucDgrms[i]);
		}
		ucDgrms = ucDgrmsTemp.toArray(new UseCaseDiagram_c[] {});

		for (int i = 0; i < comDgrms.length; i++) {
			if (loaded == null || loaded.contains(comDgrms[i]))
				Collections.addAll(comDgrmsTemp, comDgrms[i]);
		}
		comDgrms = comDgrmsTemp.toArray(new Communication_c[] {});

		for (int i = 0; i < actDgrms.length; i++) {
			if (loaded == null || loaded.contains(actDgrms[i]))
				Collections.addAll(actDgrmsTemp, actDgrms[i]);
		}
		actDgrms = actDgrmsTemp.toArray(new Activity_c[] {});
		for (int i = 0; i < packages.length; i++) {
			if (loaded == null || loaded.contains(packages[i]))
				Collections.addAll(pkgsTemp, packages[i]);
		}
		packages = pkgsTemp.toArray(new Package_c[] {});
		int ssCount = 0;
		for (Domain_c dom : dms) {
			ssCount += dom.getModelRoot().getInstanceList(Subsystem_c.class)
					.size();
		}
		int taskSize = dms.length + cps.length + ifps.length + sysDtpkgs.length
				+ seqDgrms.length + ucDgrms.length + comDgrms.length
				+ actDgrms.length + ssCount;
		if (taskSize > 0) {
			float taskIncrement = 200.0f / taskSize;
			// Preselect the package model specification . . .
			pms = ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics
					.getDefaultInstance(), new ClassQueryInterface_c() {
				public boolean evaluate(Object ms) {
					ModelSpecification_c selected = (ModelSpecification_c) ms;
					return selected.getModel_type() == Modeltype_c.Package
							&& selected.getOoa_type() == Ooatype_c.Package;
				}
			});
			// Preselect the package element specification . . .
			pes = ElementSpecification_c.ElementSpecificationInstance(
					Ooaofgraphics.getDefaultInstance(),
					new ClassQueryInterface_c() {
						public boolean evaluate(Object es) {
							ElementSpecification_c selected = (ElementSpecification_c) es;
							return selected.getOoa_type() == Ooatype_c.Package;
						}
					});
			for (int i = 0; i < dms.length; i++) {
				SystemModel_c sys = SystemModel_c.getOneS_SYSOnR28(dms[i]);
				if (sys != null) {
					Package_c pkg = upgradeDomain(dms[i], sys, null, resultMap,
							monitor, taskIncrement, Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					// Add a package symbol for the newly imported model
					addGraphicsToSystemDiagram(sys, pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}

			ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
					system.getModelRoot().getId(), OoaofgraphicsUtil
							.getGraphicsClass());
			// Get graphics for domain package diagram . . .
			Model_c[] mdls = Model_c.ModelInstances(grRoot);
			Model_c sysModel = null;
			for (int i = 0; i < mdls.length; i++) {
				if (mdls[i].getRepresents() != null
						&& mdls[i].getRepresents().equals(system)) {
					sysModel = mdls[i];
					break;
				}
			}
			CanvasModelListener.setGraphicalRepresents(sysModel);
			GraphicalElement_c[] ges = GraphicalElement_c
					.getManyGD_GEsOnR1(sysModel);
			for (int i = 0; i < ifps.length; i++) {
				SystemModel_c sys = SystemModel_c.getOneS_SYSOnR4302(ifps[i]);
				if (sys != null) {
					// Get the data type package symbol on the parent package
					// diagram.
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(ifps[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeInterfacePackage(ifps[i], ge, sys,
							resultMap, Gd_c.Null_unique_id(), Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}
			for (int i = 0; i < cps.length; i++) {
				SystemModel_c sys = SystemModel_c.getOneS_SYSOnR4606(cps[i]);
				if (sys != null) {
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(cps[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeComponentPackage(cps[i], ge, sys,
							resultMap, monitor, taskIncrement, Gd_c.Null_unique_id(), Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}
			// Update SystemLevelDataTypePackages
			for (int i = 0; i < sysDtpkgs.length; i++) {
				SystemModel_c sys = SystemModel_c
						.getOneS_SYSOnR4400(sysDtpkgs[i]);
				if (sysDtpkgs[i].Candelete()
						&& (DataTypeInPackage_c
								.getManyS_DIPsOnR39(sysDtpkgs[i]) != null || ConstantInPackage_c
								.getManyCNST_CIPsOnR1506(sysDtpkgs[i]) != null)) {

					// Get the data type package symbol on the parent package
					// diagram.
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(sysDtpkgs[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeDTPackage(sysDtpkgs[i], ge, sys,
							resultMap, Gd_c.Null_unique_id(), Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}

			}
			// Update Sequence Diagrams
			for (int i = 0; i < seqDgrms.length; i++) {
				SystemModel_c sys = SystemModel_c
						.getOneS_SYSOnR950(seqDgrms[i]);
				if (sys != null) {
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(seqDgrms[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeSequenceDiagram(seqDgrms[i], ge,
							sys, resultMap,
							Gd_c.Null_unique_id(), Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}
			// Update Use Case Diagrams
			for (int i = 0; i < ucDgrms.length; i++) {
				SystemModel_c sys = SystemModel_c
						.getOneS_SYSOnR1211(ucDgrms[i]);
				if (sys != null) {
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(ucDgrms[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeUseCaseDiagram(ucDgrms[i], ge, sys,
							resultMap, Gd_c.Null_unique_id(),
							Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}

			// Update Communication Diagrams
			for (int i = 0; i < comDgrms.length; i++) {
				SystemModel_c sys = SystemModel_c
						.getOneS_SYSOnR1136(comDgrms[i]);
				if (sys != null) {
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(comDgrms[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeCommunicationDiagram(comDgrms[i],
							ge, sys, resultMap, Gd_c.Null_unique_id(),
							Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}
			// Update Activity Diagrams
			for (int i = 0; i < actDgrms.length; i++) {
				SystemModel_c sys = SystemModel_c
						.getOneS_SYSOnR1113(actDgrms[i]);
				if (sys != null) {
					GraphicalElement_c ge = null;
					for (int j = 0; j < ges.length; j++) {
						if (ges[j].getRepresents() != null
								&& ges[j].getRepresents().equals(actDgrms[i])) {
							ge = ges[j];
							break;
						}
					}
					Package_c pkg = upgradeActivityDiagram(actDgrms[i], ge,
							sys, resultMap, Gd_c.Null_unique_id(),
							Gd_c.Null_unique_id());
					sys.relateAcrossR1405To(pkg);
					sys.relateAcrossR1401To(pkg);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}

			// Update old packages
			for (int i = 0; i < packages.length; i++) {
				// make sure the given package is not already GP specific
				if (packages[i].verifyPackageAsGeneric(packages[i])) {
					continue;
				}
				SystemModel_c sys = SystemModel_c
						.getOneS_SYSOnR1401(packages[i]);
				if (sys != null) {
					SystemModel_c rootSystem = SystemModel_c.getOneS_SYSOnR1405(packages[i]);
					if (rootSystem == null) {
						packages[i].relateAcrossR1405To(sys);
					}
					Package_c pkg = upgradePackage(packages[i], resultMap,
							monitor, taskIncrement);
					updatePMCForPackageAndChildren(PackageableElement_c
							.getOnePE_PEOnR8001(pkg));
					results.add(pkg);
					reportProgress(monitor, taskIncrement);
				}
			}

		}
		// dispose elements that had references now
		for(NonRootModelElement element : elementsToDispose) {
			try {
				Method method = element.getClass().getMethod("Dispose", new Class[0]);
				method.invoke(element, new Object[0]);
			} catch (SecurityException e) {
				CorePlugin.logError("Unable to get dispose operation.", e);
			} catch (NoSuchMethodException e) {
				CorePlugin.logError("Unable to get dispose operation.", e);
			} catch (IllegalArgumentException e) {
				CorePlugin.logError("Unable to execute dispose operation.", e);
			} catch (IllegalAccessException e) {
				CorePlugin.logError("Unable to execute dispose operation.", e);
			} catch (InvocationTargetException e) {
				CorePlugin.logError("Unable to execute dispose operation.", e);
			}
			
		}
		return results.toArray(new Package_c[results.size()]);
	}

	private static Package_c upgradeDomain(Domain_c dom, SystemModel_c sys,
			Component_c context,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			IProgressMonitor monitor, float taskIncrement, UUID packageID) {
		if (context == null) {
			// Migrate data types to system level.
			dom.Associatechildrenwithsystemcoretypes();
			dom.Associatechildrenwithsystembuiltintypes();
			// Dispose data types migrated to system level
			DataType_c[] datatypes = DataType_c.getManyS_DTsOnR14(dom);
			DataType_c datatype = null;
			for (int i = 0; i < datatypes.length; i++) {
				datatype = datatypes[i];
				DataType_c coreDt = DataType_c.getOneS_DTOnR17(CoreDataType_c
						.getOneS_CDTOnR17(datatype));
				if (coreDt != null) {
					datatype.Dispose();
				}
				UserDataType_c builtinDt = UserDataType_c
						.getOneS_UDTOnR17(datatype);
				if ((builtinDt != null) && builtinDt.getGen_type() != 0) {
					datatype.Dispose();
				}
			}
		}
		// Get graphics for domain package diagram . . .
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(dom
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c domModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(dom)) {
				domModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(domModel);
		// Upgrade Function Packages
		FunctionPackage_c[] fnps = FunctionPackage_c.getManyS_FPKsOnR29(dom);
		ArrayList<FunctionPackage_c> funcPkgDirectlyUnderDomain = new ArrayList<FunctionPackage_c>();
		for (int i = 0; i < fnps.length; i++) {
			FunctionPackageInPackage_c fpip = FunctionPackageInPackage_c
					.getOneS_FPIPOnR32(fnps[i]);
			if (fpip == null) {
				funcPkgDirectlyUnderDomain.add(fnps[i]);
			}
		}

		Package_c[] fnPkgs = new Package_c[funcPkgDirectlyUnderDomain.size()];
		for (int i = 0; i < fnPkgs.length; i++) {
			// Get the fn package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(
								funcPkgDirectlyUnderDomain.get(i))) {
					ge = ges[j];
					break;
				}
			}
			fnPkgs[i] = upgradeFNPackage(funcPkgDirectlyUnderDomain.get(i), ge,
					sys, resultMap, dom.getDom_id(), Gd_c.Null_unique_id());
		}
		// Upgrade subsystems . . .
		// Upgrade subsystems after upgrading function packages is intentional
		// So that the formalization relation between package participant
		// instances
		// and Package instances is maintained during importing

		Subsystem_c[] sss = Subsystem_c.getManyS_SSsOnR1(dom);
		ArrayList<Subsystem_c> subsystemDirectlyUnderDomain = new ArrayList<Subsystem_c>();
		for (int i = 0; i < sss.length; i++) {
			SubsystemInSubsystem_c sis = SubsystemInSubsystem_c
					.getOneS_SISOnR42(sss[i]);
			if (sis == null) {
				subsystemDirectlyUnderDomain.add(sss[i]);
			}
		}
		Package_c[] ssPkgs = new Package_c[subsystemDirectlyUnderDomain.size()];

		for (int i = 0; i < ssPkgs.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(
								subsystemDirectlyUnderDomain.get(i))) {
					ge = ges[j];
					break;
				}
			}
			ssPkgs[i] = upgradeSubsystem(subsystemDirectlyUnderDomain.get(i),
					ge, sys, resultMap, monitor, taskIncrement, dom.getDom_id(), Gd_c.Null_unique_id());
		}
		// Upgrade External Entity Packages
		ExternalEntityPackage_c[] eeps = ExternalEntityPackage_c
				.getManyS_EEPKsOnR36(dom);
		ArrayList<ExternalEntityPackage_c> eePkgDirectlyUnderDomain = new ArrayList<ExternalEntityPackage_c>();

		for (int i = 0; i < eeps.length; i++) {
			EePackageInPackage_c epip = EePackageInPackage_c
					.getOneS_EEPIPOnR35(eeps[i]);
			if (epip == null) {
				eePkgDirectlyUnderDomain.add(eeps[i]);
			}
		}

		Package_c[] eePkgs = new Package_c[eePkgDirectlyUnderDomain.size()];
		for (int i = 0; i < eePkgs.length; i++) {
			// Get the ee package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(
								eePkgDirectlyUnderDomain.get(i))) {
					ge = ges[j];
					break;
				}
			}
			eePkgs[i] = upgradeEEPackage(eePkgDirectlyUnderDomain.get(i), ge,
					sys, resultMap, dom.getDom_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R913-> Sequence
		Sequence_c[] seqs = Sequence_c.getManySQ_SsOnR913(dom);
		Package_c[] seqPkgs = new Package_c[seqs.length];
		for (int i = 0; i < seqs.length; i++) {
			// Get the Sequence package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(seqs[i])) {
					ge = ges[j];
					break;
				}
			}
			seqPkgs[i] = upgradeSequenceDiagram(seqs[i], ge, sys, resultMap,
			                            dom.getDom_id(), Gd_c.Null_unique_id());
		}

		// Upgrade R1201-> Use Case Diagram
		UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c
				.getManyUC_UCCsOnR1201(dom);
		Package_c[] ucPkgs = new Package_c[ucDgrms.length];
		for (int i = 0; i < ucDgrms.length; i++) {
			// Get the Use Case Diagram package symbol on the parent package
			// diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ucDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			ucPkgs[i] = upgradeUseCaseDiagram(ucDgrms[i], ge, sys, resultMap,
			                            dom.getDom_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R1132 -> Communication
		Communication_c[] coms = Communication_c.getManyCOMM_COMMsOnR1132(dom);
		Package_c[] comPkgs = new Package_c[coms.length];
		for (int i = 0; i < coms.length; i++) {
			// Get the Communication package symbol on the parent package
			// diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(coms[i])) {
					ge = ges[j];
					break;
				}
			}
			comPkgs[i] = upgradeCommunicationDiagram(coms[i], ge, sys,
					resultMap, dom.getDom_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R1100 -> Activity
		Activity_c[] acts = Activity_c.getManyA_AsOnR1100(dom);
		Package_c[] actPkgs = new Package_c[acts.length];
		for (int i = 0; i < acts.length; i++) {
			// Get the Activity package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(acts[i])) {
					ge = ges[j];
					break;
				}
			}
			actPkgs[i] = upgradeActivityDiagram(acts[i], ge, sys, resultMap,
			                            dom.getDom_id(), Gd_c.Null_unique_id());
		}
		// Upgrade Data type Packages
		DataTypePackage_c[] dtps = DataTypePackage_c.getManyS_DPKsOnR40(dom);
		ArrayList<DataTypePackage_c> dataTypeUnderDomain =
		                                     new ArrayList<DataTypePackage_c>();
		for (int i = 0; i < dtps.length; i++) {
			PackageInPackage_c pip = PackageInPackage_c
					.getOneEP_PIPOnR1404(Package_c
							.getOneEP_PKGOnR1400(SpecificationPackage_c
									.getOneEP_SPKGOnR1402(dtps[i])));

			if (pip == null) {
				dataTypeUnderDomain.add(dtps[i]);
			}
		}

		Package_c[] dtPkgs = new Package_c[dataTypeUnderDomain.size()];
		int nonEmptyDataTypePackages = 0;
		for (int i = 0; i < dtPkgs.length; i++) {
			DataTypeInPackage_c[] dips = DataTypeInPackage_c
					.getManyS_DIPsOnR39(dataTypeUnderDomain.get(i));
			DatatypeInSuppression_c[] diss = DatatypeInSuppression_c
					    .getManyS_DISsOnR47(DataType_c.getManyS_DTsOnR39(dips));
			DataTypePackageInPackage_c[] dpip = DataTypePackageInPackage_c
					           .getManyS_DPIPsOnR37(dataTypeUnderDomain.get(i));
			ConstantInPackage_c[] cip = ConstantInPackage_c
					.getManyCNST_CIPsOnR1506(dataTypeUnderDomain.get(i));
			if ((dips.length - diss.length > 0) || (dpip.length > 0)
					|| cip.length > 0) {
				nonEmptyDataTypePackages++;
				// Get the data type package symbol on the parent package
				// diagram.
				GraphicalElement_c ge = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(
									dataTypeUnderDomain.get(i))) {
						ge = ges[j];
						break;
					}
				}
				dtPkgs[i] = upgradeDTPackage(dataTypeUnderDomain.get(i), ge,
						sys, resultMap, dom.getDom_id(), Gd_c.Null_unique_id());
			}
		}
		// Clean up the domain package diagram. Any elements on here
		// which are not converted to Packages by now need to be removed.
		for (int i = 0; i < ges.length; i++) {
			ElementSpecification_c es = ElementSpecification_c
					.getOneGD_ESOnR10(ges[i]);
			if (!pes.equals(es)) {
				Object modelElem = ges[i].getRepresents();
				if (modelElem instanceof DataTypePackage_c) {
					((DataTypePackage_c) modelElem).Dispose();
					// delete the file and folder, as this is not done during a
					// transaction (ComponentTransactionListener handles
					// deletions)
					try {
						((DataTypePackage_c) modelElem).getFile().getParent().delete(true,
								monitor);
					} catch (CoreException e) {
						CorePlugin
								.logError(
										"Unable to remove data type package during upgrade.",
										e);
				    }
				}
				ges[i].Dispose();
			}
		}
		Package_c[] pkgs = new Package_c[ssPkgs.length + fnPkgs.length
				+ eePkgs.length + seqPkgs.length + ucPkgs.length
				+ comPkgs.length + actPkgs.length + nonEmptyDataTypePackages];
		for (int i = 0; i < ssPkgs.length; i++) {
			pkgs[i] = ssPkgs[i];
		}
		for (int i = 0; i < fnPkgs.length; i++) {
			pkgs[ssPkgs.length + i] = fnPkgs[i];
		}
		for (int i = 0; i < eePkgs.length; i++) {
			pkgs[ssPkgs.length + fnPkgs.length + i] = eePkgs[i];
		}
		for (int i = 0; i < seqPkgs.length; i++) {
			pkgs[ssPkgs.length + fnPkgs.length + eePkgs.length + i] = seqPkgs[i];
		}
		for (int i = 0; i < ucPkgs.length; i++) {
			pkgs[ssPkgs.length + fnPkgs.length + eePkgs.length + seqPkgs.length
					+ i] = ucPkgs[i];
		}
		for (int i = 0; i < comPkgs.length; i++) {
			pkgs[ssPkgs.length + fnPkgs.length + eePkgs.length + seqPkgs.length
					+ ucPkgs.length + i] = comPkgs[i];
		}
		for (int i = 0; i < actPkgs.length; i++) {
			pkgs[ssPkgs.length + fnPkgs.length + eePkgs.length + seqPkgs.length
					+ ucPkgs.length + comPkgs.length + i] = actPkgs[i];
		}
		int dtPkgsFound = 0;
		for (int i = 0; i < dtPkgs.length; i++) {
			if (dtPkgs[i] != null) {
				pkgs[ssPkgs.length + fnPkgs.length + eePkgs.length
						+ seqPkgs.length + ucPkgs.length + comPkgs.length
						+ actPkgs.length + dtPkgsFound++] = dtPkgs[i];
			}
		}
		Package_c result = null;
		if (context == null) {
			Package_c upgrade = new Package_c(dom.getModelRoot(), dom
					.getDom_id(), sys.getSys_id(), sys.getSys_id(), dom
					.getName(), dom.getDescrip(), 0);
			// Private visibility is best for domains - separate subject
			// matters.
			PackageableElement_c pe = new PackageableElement_c(dom
					.getModelRoot(), dom.getDom_id(), Visibility_c.Private,
					packageID, Gd_c.Null_unique_id(),
					Elementtypeconstants_c.PACKAGE);
			pe.relateAcrossR8001To(upgrade);
			upgrade.Initialize();
			pe.Initialize();
			pe.setType(Elementtypeconstants_c.PACKAGE);
			// Private visibility is best for domains - separate subject
			// matters.
			pe.setVisibility(Visibility_c.Private);
			for (int i = 0; i < pkgs.length; i++) {
				pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
				pe.relateAcrossR8000To(upgrade);
			}
			// ------ upgrade domain itself ---------
			if (domModel != null) {
				domModel.relateAcrossR9To(pms);
				domModel.setRepresents(upgrade);
				domModel.setOoa_id(upgrade.Get_ooa_id());
				clearExistingModelTools(domModel);
			}
			upgrade.setName(dom.getName());
			upgrade.setDescrip(dom.getDescrip());
			if (sys != null) {
				upgrade.relateAcrossR1405To(sys);
				upgrade.relateAcrossR1401To(sys);
			}
			result = upgrade;
		} else {
			for (int i = 0; i < pkgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(pkgs[i]);
				pe.relateAcrossR8003To(context);
			}
		}
		dom.Dispose();
		resultMap.put(result, dom);
		return result;
	}

	/**
	 * This function clears out existing graphical tools, this prevents existing
	 * specialized tools in any editors that were open during the upgrade.
	 * 
	 * @param {@link ModelTool_c} model
	 */
	private static void clearExistingModelTools(Model_c model) {
		ModelTool_c[] tools = ModelTool_c.getManyCT_MTLsOnR100(model);
		for(int i = 0; i < tools.length; i++) {
			tools[i].Dispose();
		}
	}

	private static void addGraphicsToSystemDiagram(SystemModel_c sys,
			Package_c pkg) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(sys
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		// Get graphics for domain package diagram . . .
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c sysModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(sys)) {
				sysModel = mdls[i];
				break;
			}
		}
		// Find the lower rightmost clear area.
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(sysModel);
		Graphelement_c[] grElems = Graphelement_c.getManyDIM_GEsOnR23(ges);
		float peakX = 0;
		for (int i = 0; i < grElems.length; i++) {
			Graphnode_c gn = Graphnode_c.getOneDIM_NDOnR301(grElems[i]);
			if (grElems[i].getPositionx() + gn.getWidth() > peakX) {
				peakX = grElems[i].getPositionx() + gn.getWidth();
			}
		}
		// if there are no other graphical elements then
		// use the scroll positions
		if (grElems.length == 0) {
			Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(sysModel);
			peakX = diagram.getViewportx();
		}
		// Create the graphical elements . . .
		GraphicalElement_c elem = new GraphicalElement_c(sysModel
				.getModelRoot());
		Shape_c shape = new Shape_c(sysModel.getModelRoot());
		Graphnode_c graphNode = new Graphnode_c(sysModel.getModelRoot());
		graphNode.relateAcrossR19To(shape);
		Graphelement_c graphEle = new Graphelement_c(sysModel.getModelRoot());
		graphEle.relateAcrossR301To(graphNode);
		graphEle.relateAcrossR23To(elem);
		Diagramelement_c diaEle = new Diagramelement_c(sysModel.getModelRoot());
		diaEle.relateAcrossR302To(graphEle);
		NoncontainingShape_c ncs = new NoncontainingShape_c(sysModel
				.getModelRoot());
		ncs.relateAcrossR28To(shape);
		graphEle.setPositionx(peakX + 10);
		graphNode.setWidth(pes.getDefaultwidth());
		graphNode.setHeight(pes.getDefaultheight());
		elem.relateAcrossR2To(shape);
		elem.relateAcrossR1To(sysModel);
		pes.relateAcrossR10To(elem);
		//
		elem.setRepresents(pkg);
		elem.setOoa_id(pkg.getPackage_id());
		//
	}

	private static void updatePMCForPackageAndChildren(
			PackageableElement_c pkgElem) {
		Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
		PackageableElement_c[] pkgElems = null;
		if (pkg != null) {
			PersistableModelComponent pmc = pkg.getPersistableComponent();
			pmc.setRootModelElement(pkg, false, false);
			pkgElems = PackageableElement_c.getManyPE_PEsOnR8000(pkg);
		}
		Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
		if (comp != null) {
			pkgElems = PackageableElement_c.getManyPE_PEsOnR8003(comp);
		}
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR8001(pkgElems);
		for (int i = 0; i < pkgs.length; i++) {
			PackageableElement_c childElem = PackageableElement_c
					.getOnePE_PEOnR8001(pkgs[i]);
			updatePMCForPackageAndChildren(childElem);
		}
		Component_c[] comps = Component_c.getManyC_CsOnR8001(pkgElems);
		for (int i = 0; i < comps.length; i++) {
			PackageableElement_c childElem = PackageableElement_c
					.getOnePE_PEOnR8001(comps[i]);
			updatePMCForPackageAndChildren(childElem);
		}
	}

	private static float accumulatedProgress = 0;

	private static void reportProgress(IProgressMonitor monitor, float increment) {
		accumulatedProgress += increment;
		if (accumulatedProgress > 1.0) {
			int progress = (int) accumulatedProgress;
			monitor.worked(progress);
			accumulatedProgress -= progress;
			while (Display.getCurrent().readAndDispatch()) {
			}
			;
		}
	}

	private static Package_c upgradeInterfacePackage(InterfacePackage_c ifPkg,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap, UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(ifPkg
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c ifPkgModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(ifPkg)) {
				ifPkgModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(ifPkgModel);
		InterfacePackage_c[] ifps = InterfacePackage_c
				.getManyIP_IPsOnR4301(InterfacePackageInInterfacePackage_c
						.getManyIP_IPINIPsOnR4300(ifPkg));
		Package_c[] pkgs = new Package_c[ifps.length];
		for (int i = 0; i < ifps.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ifps[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeInterfacePackage(ifps[i], childGe, sys, resultMap, ifPkg.getPackage_id(), Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(ifPkg.getModelRoot(), ifPkg
				.getPackage_id(), Gd_c.Null_unique_id(), sys.getSys_id(), ifPkg
				.getName(), ifPkg.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(
				ifPkg.getModelRoot(), ifPkg.getPackage_id(),
				Visibility_c.Public, packageID, componentID,
				Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		Interface_c[] interfaces = Interface_c.getManyC_IsOnR4303(ifPkg);
		for (int i = 0; i < interfaces.length; i++) {
			pe = new PackageableElement_c(ifPkg.getModelRoot(), interfaces[i]
					.getId(), Visibility_c.Public, Gd_c.Null_unique_id(), Gd_c
					.Null_unique_id(), Elementtypeconstants_c.INTERFACE);
			pe.relateAcrossR8001To(interfaces[i]);
			upgrade.relateAcrossR8000To(pe);
			ifPkg.unrelateAcrossR4303From(interfaces[i]);
		}
		// ------ upgrade interface package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (ifPkgModel != null) {
			ifPkgModel.relateAcrossR9To(pms);
			ifPkgModel.setRepresents(upgrade);
			ifPkgModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(ifPkgModel);
		}
		upgrade.setName(ifPkg.getName());
		upgrade.setDescrip(ifPkg.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		elementsToDispose .add(ifPkg);
		resultMap.put(upgrade, ifPkg);
		return upgrade;
	}

	private static Package_c upgradeComponentPackage(
			ComponentPackage_c compPkg, GraphicalElement_c parentGe,
			SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			IProgressMonitor monitor, float taskIncrement, UUID packageID, UUID componentID) {
		// Get graphics for component package diagram . . .
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
				compPkg.getModelRoot().getId(), OoaofgraphicsUtil
						.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c cpModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(compPkg)) {
				cpModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(cpModel);

		// Upgrade compPackages . . .
		ComponentPackage_c[] cps = ComponentPackage_c
				.getManyCP_CPsOnR4601(ComponentPackageInPackage_c
						.getManyCP_CPINPsOnR4600(compPkg));
		Package_c[] compPkgs = new Package_c[cps.length];
		for (int i = 0; i < cps.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(cps[i])) {
					ge = ges[j];
					break;
				}
			}
			compPkgs[i] = upgradeComponentPackage(cps[i], ge, sys, resultMap,
					monitor, taskIncrement, compPkg.getPackage_id(), Gd_c.Null_unique_id());
		}
		// Upgrade components . . .
		Component_c[] comps = Component_c.getManyC_CsOnR4604(compPkg);
		PackageableElement_c[] pkges = new PackageableElement_c[comps.length];
		for (int i = 0; i < comps.length; i++) {
			pkges[i] = upgradeComponent(comps[i], sys, resultMap, monitor,
					taskIncrement);
		}
		// Upgrade interface packages . . .
		InterfacePackage_c[] ifPkgs = InterfacePackage_c
				.getManyIP_IPsOnR4607(compPkg);
		Package_c[] ifNewPkgs = new Package_c[ifPkgs.length];
		for (int i = 0; i < ifPkgs.length; i++) {
			// Get the component symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ifPkgs[i])) {
					ge = ges[j];
					break;
				}
			}
			ifNewPkgs[i] = upgradeInterfacePackage(ifPkgs[i], ge, sys,
					resultMap, compPkg.getPackage_id(), Gd_c.Null_unique_id());
		}

		// Upgrade Satisfactions
		Satisfaction_c[] sats = Satisfaction_c
				.getManyC_SFsOnR9001(SatisfactionInComponentPackage_c
						.getManyPA_SICPsOnR9001(compPkg));
		for (int i = 0; i < sats.length; i++) {
			SatisfactionInComponentPackage_c sicp = SatisfactionInComponentPackage_c
					.getOnePA_SICPOnR9001(sats[i]);
			compPkg.unrelateAcrossR9001From(sicp);
			sats[i].unrelateAcrossR9001From(sicp);
			PackageableElement_c pe = new PackageableElement_c(sats[i]
					.getModelRoot(), sats[i].getId(), Visibility_c.Public, Gd_c
					.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.SATISFACTION);
			sats[i].relateAcrossR8001To(pe);
			sicp.delete();
		}
		// Upgrade Component References
		ComponentReference_c[] crs = ComponentReference_c
				.getManyCL_ICsOnR4605(compPkg);
		for (int i = 0; i < crs.length; i++) {
			compPkg.unrelateAcrossR4605From(crs[i]);
			PackageableElement_c pe = new PackageableElement_c(crs[i]
					.getModelRoot(), crs[i].getId(), Visibility_c.Public, Gd_c
					.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.COMPONENT_REFERENCE);
			crs[i].relateAcrossR8001To(pe);
		}
		// Upgrade R951-> Sequence
		Sequence_c[] seqs = Sequence_c.getManySQ_SsOnR951(compPkg);
		Package_c[] seqPkgs = new Package_c[seqs.length];
		for (int i = 0; i < seqs.length; i++) {
			// Get the Sequence package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(seqs[i])) {
					ge = ges[j];
					break;
				}
			}
			seqPkgs[i] = upgradeSequenceDiagram(seqs[i], ge, sys, resultMap, compPkg.getPackage_id(), Gd_c.Null_unique_id());
		}

		// Upgrade R1212-> Use Case Diagram
		UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c
				.getManyUC_UCCsOnR1212(compPkg);
		Package_c[] ucPkgs = new Package_c[ucDgrms.length];
		for (int i = 0; i < ucDgrms.length; i++) {
			// Get the Use Case Diagram package symbol on the parent package
			// diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ucDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			ucPkgs[i] = upgradeUseCaseDiagram(ucDgrms[i], ge, sys, resultMap, compPkg.getPackage_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R1137 -> Communication
		Communication_c[] coms = Communication_c
				.getManyCOMM_COMMsOnR1137(compPkg);
		Package_c[] comPkgs = new Package_c[coms.length];
		for (int i = 0; i < coms.length; i++) {
			// Get the Communication package symbol on the parent package
			// diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(coms[i])) {
					ge = ges[j];
					break;
				}
			}
			comPkgs[i] = upgradeCommunicationDiagram(coms[i], ge, sys,
					resultMap, compPkg.getPackage_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R1114 -> Activity
		Activity_c[] acts = Activity_c.getManyA_AsOnR1114(compPkg);
		Package_c[] actPkgs = new Package_c[acts.length];
		for (int i = 0; i < acts.length; i++) {
			// Get the Activity package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(acts[i])) {
					ge = ges[j];
					break;
				}
			}
			actPkgs[i] = upgradeActivityDiagram(acts[i], ge, sys, resultMap,
			        compPkg.getPackage_id(), Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(compPkg.getModelRoot(), compPkg
				.getPackage_id(), Gd_c.Null_unique_id(), sys.getSys_id(),
				compPkg.getName(), compPkg.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(compPkg
				.getModelRoot(), compPkg.getPackage_id(), Visibility_c.Public,
				packageID, componentID,	Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		// ------ upgrade contents ------------
		for (int i = 0; i < pkges.length; i++) {
			upgrade.relateAcrossR8000To(pkges[i]);
		}
		for (int i = 0; i < compPkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(compPkgs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < ifNewPkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(ifNewPkgs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < sats.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(sats[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < crs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(crs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < seqPkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(seqPkgs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < ucPkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(ucPkgs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < comPkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(comPkgs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < actPkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(actPkgs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		// ------ upgrade component package itself ---------
		if (cpModel != null) {
			cpModel.relateAcrossR9To(pms);
			cpModel.setRepresents(upgrade);
			cpModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(cpModel);
		}
		upgrade.setName(compPkg.getName());
		upgrade.setDescrip(compPkg.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		// upgrade.relateAcrossR1401To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		elementsToDispose.add(compPkg);
		resultMap.put(upgrade, compPkg);
		return upgrade;
	}

	private static Package_c upgradeDTPackage(DataTypePackage_c dtp,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(dtp
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c dtpModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents().equals(dtp)) {
				dtpModel = mdls[i];
				break;
			}
		}
		// ------ upgrade nested Datatype Packages------------
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(dtpModel);
		DataTypePackage_c[] dtpkgs = DataTypePackage_c
				.getManyS_DPKsOnR38(DataTypePackageInPackage_c
						.getManyS_DPIPsOnR37(dtp));
		Package_c[] pkgs = new Package_c[dtpkgs.length];
		for (int i = 0; i < dtpkgs.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(dtpkgs[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeDTPackage(dtpkgs[i], childGe, sys, resultMap,
			                        dtp.getPackage_id(), Gd_c.Null_unique_id());
		}

		Package_c upgrade = new Package_c(dtp.getModelRoot(), dtp
				.getPackage_id(), Gd_c.Null_unique_id(), sys.getSys_id(), dtp
				.getName(), "", 0);
		PackageableElement_c pe = new PackageableElement_c(dtp.getModelRoot(),
				dtp.getPackage_id(), Visibility_c.Public,
				packageID, componentID,
				Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		DataTypeInPackage_c[] dtips = DataTypeInPackage_c
				.getManyS_DIPsOnR39(dtp);
		for (int i = 0; i < dtips.length; i++) {
			DataType_c dt = DataType_c.getOneS_DTOnR39(dtips[i]);
			DatatypeInSuppression_c dis = DatatypeInSuppression_c
					.getOneS_DISOnR47(dt);
			if (dis == null) {
				pe = new PackageableElement_c(dtp.getModelRoot(),
						dt.getDt_id(), Visibility_c.Public, Gd_c
								.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.DATATYPE);
				pe.relateAcrossR8001To(dt);
				upgrade.relateAcrossR8000To(pe);
			} else {
				// TODO need to purge the graphic too . . . use R32 (Element In
				// Suppression)
				dt.Dispose();
			}
			dtp.unrelateAcrossR39From(dtips[i]);
			dtp.unrelateAcrossR39From(dtips[i]);
			Domain_c dom = Domain_c.getOneS_DOMOnR14(dt);
			if (dom != null) {
				dom.unrelateAcrossR14From(dt);
			}
			dtips[i].delete();
		}
		ConstantInPackage_c[] cips = ConstantInPackage_c
				.getManyCNST_CIPsOnR1506(dtp);
		for (int i = 0; i < cips.length; i++) {
			ConstantSpecification_c cs = ConstantSpecification_c
					.getOneCNST_CSPOnR1506(cips[i]);
			pe = new PackageableElement_c(dtp.getModelRoot(), cs
					.getConstant_spec_id(), Visibility_c.Public, Gd_c
					.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.CONSTANT);
			pe.relateAcrossR8001To(cs);
			upgrade.relateAcrossR8000To(pe);
			dtp.unrelateAcrossR1506From(cips[i]);
			cs.unrelateAcrossR1506From(cips[i]);
			cips[i].delete();
		}
		// Dispose graphics for disposed and suppressed datatype elements
		// (typically built in data types shifted to the system level).
		for (int i = 0; i < ges.length; i++) {
			Object elem = ges[i].getRepresents();
			if (elem instanceof CoreDataType_c) {
				DataType_c dt = DataType_c
						.getOneS_DTOnR17((CoreDataType_c) elem);
				if (dt == null) {
					ges[i].Dispose();
					continue;
				}
			}
			if (elem instanceof UserDataType_c) {
				DataType_c dt = DataType_c
						.getOneS_DTOnR17((UserDataType_c) elem);
				if (dt == null) {
					ges[i].Dispose();
					continue;
				}
			}
			ElementInSuppression_c eis = ElementInSuppression_c
					.getOneGD_EISOnR32(ges[i]);
			if (eis != null) {
				// This element was suppressed after domain -> component
				// upgrade,
				// dispose it
				ges[i].Dispose();
			}
		}
		// ------ upgrade DT Package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (dtpModel != null) {
			dtpModel.relateAcrossR9To(pms);
			dtpModel.setRepresents(upgrade);
			dtpModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(dtpModel);
		}
		upgrade.setName(dtp.getName());
		// upgrade.setDescrip(dtp.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		SpecificationPackage_c spPkg = SpecificationPackage_c
				.getOneEP_SPKGOnR1402(dtp);
		elementsToDispose.add(spPkg);
		resultMap.put(upgrade, spPkg);
		return upgrade;
	}

	private static Package_c upgradeSequenceDiagram(Sequence_c sequence,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
				sequence.getModelRoot().getId(), OoaofgraphicsUtil
						.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c seqModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(sequence)) {
				seqModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(seqModel);
		Sequence_c[] seqDgrms = Sequence_c
				.getManySQ_SsOnR928(SequenceInSequence_c
						.getManySQ_SISsOnR911(sequence));
		Package_c[] pkgs = new Package_c[seqDgrms.length];
		for (int i = 0; i < seqDgrms.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(seqDgrms[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeSequenceDiagram(seqDgrms[i], childGe, sys,
					resultMap, sequence.getPackage_id(), Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(sequence.getModelRoot(), sequence
				.getPackage_id(), Gd_c.Null_unique_id(), sys.getSys_id(),
				sequence.getName(), sequence.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(sequence
				.getModelRoot(), sequence.getPackage_id(), Visibility_c.Public,
				packageID, componentID,	Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		InteractionParticipant_c[] participants = InteractionParticipant_c
				.getManySQ_PsOnR929(sequence);
		for (int i = 0; i < participants.length; i++) {
			FunctionPackageParticipant_c fPP = FunctionPackageParticipant_c
					.getOneSQ_FPPOnR930(participants[i]);
			if (fPP != null) {
				PackageParticipant_c pp = new PackageParticipant_c(fPP
						.getModelRoot(), fPP.getPart_id(), fPP.getFunpack_id(),
						fPP.getLabel(), fPP.getInformalname(),
						fPP.getDescrip(), fPP.getIsformal());
				GraphicalElement_c fPPGe = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(fPP)) {
						fPPGe = ges[j];
						break;
					}
				}
				if (fPPGe != null) {
					fPPGe.setRepresents(pp);
					fPPGe.setOoa_type(Ooatype_c.PackageParticipant);
					// need to also relate to the package part element spec
					// otherwise persistence will write the old function part
					// value
					ElementSpecification_c currentSpec = ElementSpecification_c
							.getOneGD_ESOnR10(fPPGe);
					ElementSpecification_c newSpec = ElementSpecification_c
							.getOneGD_ESOnR11(
									ElementInModelSpecification_c
											.getManyGD_EMSsOnR11(ModelSpecification_c
													.ModelSpecificationInstances(Ooaofgraphics
															.getDefaultInstance())),
									new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											return ((ElementSpecification_c) candidate)
													.getOoa_type() == Ooatype_c.PackageParticipant;
				}
									});
					Assert.isNotNull(newSpec,
						"Unable to locate Package Participant element specification.");
					fPPGe.unrelateAcrossR10From(currentSpec);
					fPPGe.relateAcrossR10To(newSpec);
				}
				participants[i].unrelateAcrossR930From(fPP);
				final UUID FormalizedPackageIdCashed = fPP
						.getFunpack_idCachedValue();
				final UUID FormalizedPackageId = fPP.getFunpack_id();
				fPP.Dispose();
				participants[i].relateAcrossR930To(pp);

				pe = new PackageableElement_c(sequence.getModelRoot(),
						participants[i].getPart_id(), Visibility_c.Public, Gd_c
								.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.INTERACTION_PARTICIPANT);
				pe.relateAcrossR8001To(participants[i]);
				upgrade.relateAcrossR8000To(pe);
				sequence.unrelateAcrossR929From(participants[i]);
				if (FormalizedPackageId != Gd_c.Null_unique_id()) {
					class PackageId_c implements ClassQueryInterface_c {
						PackageId_c(java.util.UUID p_id) {
							m_id = p_id;
						}

						private java.util.UUID m_id;

						public boolean evaluate(Object candidate) {
							Package_c selected = (Package_c) candidate;
							return selected.getPackage_id().equals(m_id);
						}
					}

					Package_c PKG = Package_c.getOneEP_PKGOnR1405(sys,
							new PackageId_c(FormalizedPackageId));
					pp.relateAcrossR956To(PKG);
				} else if (FormalizedPackageIdCashed != Gd_c.Null_unique_id()) {
					class PackageIdc_c implements ClassQueryInterface_c {
						PackageIdc_c(java.util.UUID p_id) {
							m_id = p_id;
						}

						private java.util.UUID m_id;

						public boolean evaluate(Object candidate) {
							Package_c selected = (Package_c) candidate;
							return selected.getPackage_idCachedValue().equals(
									m_id);
						}
					}

					Package_c PKG = Package_c.getOneEP_PKGOnR1405(sys,
							new PackageIdc_c(FormalizedPackageIdCashed));
					pp.relateAcrossR956To(PKG);
				}

			} else {
				pe = new PackageableElement_c(sequence.getModelRoot(),
						participants[i].getPart_id(), Visibility_c.Public, Gd_c
								.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.INTERACTION_PARTICIPANT);
				pe.relateAcrossR8001To(participants[i]);
				upgrade.relateAcrossR8000To(pe);
				sequence.unrelateAcrossR929From(participants[i]);
			}
		}
		Message_c[] msgs = Message_c.getManyMSG_MsOnR1007(participants);
		for (int i = 0; i < msgs.length; i++) {
			Message_c msg = msgs[i];
			MessageInSequence_c mis = MessageInSequence_c
					.getOneSQ_MISOnR954(msg);
			if(mis != null) {
				mis.unrelateAcrossR953From(sequence);
				mis.unrelateAcrossR954From(msg);
				mis.delete();
			}
			PackageableElement_c msgPE = new PackageableElement_c(sequence
					.getModelRoot(), msg.getMsg_id(), Visibility_c.Public, pe
					.getElement_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.MESSAGE);
			msgPE.relateAcrossR8001To(msg);
			msgPE.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade sequence package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (seqModel != null) {
			seqModel.relateAcrossR9To(pms);
			seqModel.setRepresents(upgrade);
			seqModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(seqModel);
		}
		upgrade.setName(sequence.getName());
		upgrade.setDescrip(sequence.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		sequence.Dispose();
		resultMap.put(upgrade, sequence);
		return upgrade;

	}

	private static Package_c upgradeUseCaseDiagram(
			UseCaseDiagram_c useCaseDiagram, GraphicalElement_c parentGe,
			SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
				useCaseDiagram.getModelRoot().getId(), OoaofgraphicsUtil
						.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c ucModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(useCaseDiagram)) {
				ucModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(ucModel);
		UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c
				.getManyUC_UCCsOnR1209(UseCaseInUseCase_c
						.getManyUC_UIUsOnR1208(useCaseDiagram));
		Package_c[] pkgs = new Package_c[ucDgrms.length];
		for (int i = 0; i < ucDgrms.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ucDgrms[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeUseCaseDiagram(ucDgrms[i], childGe, sys, resultMap, useCaseDiagram.getPackage_id(), Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(useCaseDiagram.getModelRoot(),
				useCaseDiagram.getPackage_id(), Gd_c.Null_unique_id(), sys.getSys_id(), useCaseDiagram.getName(),
				useCaseDiagram.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(useCaseDiagram
				.getModelRoot(), useCaseDiagram.getPackage_id(),
				Visibility_c.Public, packageID, componentID,
				Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		InteractionParticipant_c[] participants = InteractionParticipant_c
				.getManySQ_PsOnR1203((ParticipantInUseCase_c
						.getManyUC_PIUCsOnR1203(useCaseDiagram)));
		for (int i = 0; i < participants.length; i++) {
			pe = new PackageableElement_c(useCaseDiagram.getModelRoot(),
					participants[i].getPart_id(), Visibility_c.Public, Gd_c
							.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.INTERACTION_PARTICIPANT);
			pe.relateAcrossR8001To(participants[i]);
			upgrade.relateAcrossR8000To(pe);
			useCaseDiagram.unrelateAcrossR1203From(ParticipantInUseCase_c
					.getOneUC_PIUCOnR1203(participants[i]));
		}
		UseCaseAssociation_c[] assocs = UseCaseAssociation_c
				.getManyUC_UCAsOnR1206(participants);
		for (int i = 0; i < assocs.length; i++) {
			// remove link class
			AssociationInUseCase_c aiuc = AssociationInUseCase_c
					.getOneUC_AIUCOnR1215(assocs[i]);
			if (aiuc != null) {
				UseCaseDiagram_c ucd = UseCaseDiagram_c
						.getOneUC_UCCOnR1214(aiuc);
				aiuc.unrelateAcrossR1214From(ucd);
				aiuc.unrelateAcrossR1215From(assocs[i]);
				aiuc.delete();
			}
			pe = new PackageableElement_c(useCaseDiagram.getModelRoot(),
					assocs[i].getAssoc_id(), Visibility_c.Public, Gd_c
							.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.USE_CASE_ASSOCIATION);
			pe.relateAcrossR8001To(assocs[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		// ------ upgrade use case package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (ucModel != null) {
			ucModel.relateAcrossR9To(pms);
			ucModel.setRepresents(upgrade);
			ucModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(ucModel);
		}
		upgrade.setName(useCaseDiagram.getName());
		upgrade.setDescrip(useCaseDiagram.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		useCaseDiagram.Dispose();
		resultMap.put(upgrade, useCaseDiagram);
		return upgrade;
	}

	private static Package_c upgradeCommunicationDiagram(
			Communication_c communication, GraphicalElement_c parentGe,
			SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
				communication.getModelRoot().getId(), OoaofgraphicsUtil
						.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c comModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(communication)) {
				comModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(comModel);
		Communication_c[] comDgrms = Communication_c
				.getManyCOMM_COMMsOnR1129(CommunicationInCommunication_c
						.getManyCOMM_CICsOnR1130(communication));
		Package_c[] pkgs = new Package_c[comDgrms.length];
		for (int i = 0; i < comDgrms.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(comDgrms[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeCommunicationDiagram(comDgrms[i], childGe, sys,
					resultMap, communication.getPackage_id(),
					Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(communication.getModelRoot(),
				communication.getPackage_id(), Gd_c.Null_unique_id(), sys.getSys_id(), communication.getName(),
				communication.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(communication
				.getModelRoot(), communication.getPackage_id(),
				Visibility_c.Public, packageID, componentID,
				Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		InteractionParticipant_c[] participants = InteractionParticipant_c
				.getManySQ_PsOnR1126(communication);
		for (int i = 0; i < participants.length; i++) {
			FunctionPackageParticipant_c fPP = FunctionPackageParticipant_c
					.getOneSQ_FPPOnR930(participants[i]);
			if (fPP != null) {
				PackageParticipant_c pp = new PackageParticipant_c(fPP
						.getModelRoot(), fPP.getPart_id(), fPP.getFunpack_id(),
						fPP.getLabel(), fPP.getInformalname(),
						fPP.getDescrip(), fPP.getIsformal());
				GraphicalElement_c fPPGe = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(fPP)) {
						fPPGe = ges[j];
						break;
					}
				}
				if (fPPGe != null) {
					fPPGe.setRepresents(pp);
					fPPGe.setOoa_type(Ooatype_c.PackageParticipant);
					// need to also relate to the package part element spec
					// otherwise persistence will write the old function part
					// value
					ElementSpecification_c currentSpec = ElementSpecification_c
							.getOneGD_ESOnR10(fPPGe);
					ElementSpecification_c newSpec = ElementSpecification_c
							.getOneGD_ESOnR11(
									ElementInModelSpecification_c
											.getManyGD_EMSsOnR11(ModelSpecification_c
													.ModelSpecificationInstances(Ooaofgraphics
															.getDefaultInstance())),
									new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											return ((ElementSpecification_c) candidate)
													.getOoa_type() == Ooatype_c.PackageParticipant;
										}
									});
					Assert.isNotNull(newSpec,
						"Unable to locate Package Participant element specification.");
					fPPGe.unrelateAcrossR10From(currentSpec);
					fPPGe.relateAcrossR10To(newSpec);
				}
				participants[i].unrelateAcrossR930From(fPP);
				final UUID FormalizedPackageIdCashed = fPP
						.getFunpack_idCachedValue();
				final UUID FormalizedPackageId = fPP.getFunpack_id();
				fPP.Dispose();
				participants[i].relateAcrossR930To(pp);

				pe = new PackageableElement_c(communication.getModelRoot(),
						participants[i].getPart_id(), Visibility_c.Public, Gd_c
								.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.INTERACTION_PARTICIPANT);
				pe.relateAcrossR8001To(participants[i]);
				upgrade.relateAcrossR8000To(pe);
				communication
						.unrelateAcrossR1126From(ParticipantInCommunication_c
								.getOneCOMM_PICOnR1126(participants[i]));
				if (FormalizedPackageId != Gd_c.Null_unique_id()) {
					class PackageId_c implements ClassQueryInterface_c {
						PackageId_c(java.util.UUID p_id) {
							m_id = p_id;
						}

						private java.util.UUID m_id;

						public boolean evaluate(Object candidate) {
							Package_c selected = (Package_c) candidate;
							return selected.getPackage_id().equals(m_id);
						}
					}

					Package_c PKG = Package_c.getOneEP_PKGOnR1405(sys,
							new PackageId_c(FormalizedPackageId));
					pp.relateAcrossR956To(PKG);
				} else if (FormalizedPackageIdCashed != Gd_c.Null_unique_id()) {
					class PackageIdc_c implements ClassQueryInterface_c {
						PackageIdc_c(java.util.UUID p_id) {
							m_id = p_id;
						}

						private java.util.UUID m_id;

						public boolean evaluate(Object candidate) {
							Package_c selected = (Package_c) candidate;
							return selected.getPackage_idCachedValue().equals(
									m_id);
						}
					}

					Package_c PKG = Package_c.getOneEP_PKGOnR1405(sys,
							new PackageIdc_c(FormalizedPackageIdCashed));
					pp.relateAcrossR956To(PKG);
				}

			} else {
				pe = new PackageableElement_c(communication.getModelRoot(),
						participants[i].getPart_id(), Visibility_c.Public, Gd_c
								.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.INTERACTION_PARTICIPANT);
				pe.relateAcrossR8001To(participants[i]);
				upgrade.relateAcrossR8000To(pe);
				communication
						.unrelateAcrossR1126From(ParticipantInCommunication_c
								.getOneCOMM_PICOnR1126(participants[i]));
			}
		}
		Message_c[] msgs = Message_c.getManyMSG_MsOnR1135(communication);
		for (int j = 0; j < msgs.length; j++) {
			pe = new PackageableElement_c(communication.getModelRoot(), msgs[j]
					.getMsg_id(), Visibility_c.Public, Gd_c.Null_unique_id(),
					Gd_c.Null_unique_id(), Elementtypeconstants_c.MESSAGE);
			pe.relateAcrossR8001To(msgs[j]);
			upgrade.relateAcrossR8000To(pe);
			msgs[j].setParticipatesincommunication(true);
			MessageInCommunication_c mic = MessageInCommunication_c
					.getOneCOMM_MICOnR1135(msgs[j]);
			if (mic != null) {
				communication.unrelateAcrossR1135From(mic);
				msgs[j].unrelateAcrossR1135From(mic);
				mic.delete();
			}
		}

		// ------ upgrade communication package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (comModel != null) {
			comModel.relateAcrossR9To(pms);
			comModel.setRepresents(upgrade);
			comModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(comModel);
		}
		upgrade.setName(communication.getName());
		upgrade.setDescrip(communication.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		communication.Dispose();
		resultMap.put(upgrade, communication);
		return upgrade;

	}

	private static Package_c upgradeActivityDiagram(Activity_c activity,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			UUID packageID, UUID componentID) {

		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
				activity.getModelRoot().getId(), OoaofgraphicsUtil
						.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c actModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(activity)) {
				actModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(actModel);
		Activity_c[] actDgrms = Activity_c
				.getManyA_AsOnR1110(ActivityInActivity_c
						.getManyA_AIAsOnR1109(activity));
		Package_c[] pkgs = new Package_c[actDgrms.length];
		for (int i = 0; i < actDgrms.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(actDgrms[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeActivityDiagram(actDgrms[i], childGe, sys,
					resultMap, activity.getPackage_id(), Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(activity.getModelRoot(), activity
				.getPackage_id(), Gd_c.Null_unique_id(), Gd_c.Null_unique_id(),
				activity.getName(), activity.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(activity
				.getModelRoot(), activity.getPackage_id(), Visibility_c.Public,
				packageID, componentID, Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		ActivityPartition_c[] actParts = ActivityPartition_c
				.getManyA_APsOnR1111(activity);
		for (int i = 0; i < actParts.length; i++) {
			pe = new PackageableElement_c(activity.getModelRoot(), actParts[i]
					.getId(), Visibility_c.Public, Gd_c.Null_unique_id(), Gd_c
					.Null_unique_id(),
					Elementtypeconstants_c.ACTIVITY_PARTITION);
			pe.relateAcrossR8001To(actParts[i]);
			upgrade.relateAcrossR8000To(pe);
			activity.unrelateAcrossR1111From(actParts[i]);
		}
		ActivityNode_c[] actNodes = ActivityNode_c.getManyA_NsOnR1101(activity);
		for (int i = 0; i < actNodes.length; i++) {
			pe = new PackageableElement_c(activity.getModelRoot(), actNodes[i]
					.getId(), Visibility_c.Public, Gd_c.Null_unique_id(), Gd_c
					.Null_unique_id(), Elementtypeconstants_c.ACTIVITY_NODE);
			pe.relateAcrossR8001To(actNodes[i]);
			upgrade.relateAcrossR8000To(pe);
			activity.unrelateAcrossR1101From(actNodes[i]);
		}
		ActivityEdge_c[] actEdges = ActivityEdge_c.getManyA_EsOnR1102(activity);
		for (int i = 0; i < actEdges.length; i++) {
			pe = new PackageableElement_c(activity.getModelRoot(), actEdges[i]
					.getId(), Visibility_c.Public, Gd_c.Null_unique_id(), Gd_c
					.Null_unique_id(), Elementtypeconstants_c.ACTIVITY_EDGE);
			pe.relateAcrossR8001To(actEdges[i]);
			upgrade.relateAcrossR8000To(pe);
			activity.unrelateAcrossR1102From(actEdges[i]);
		}

		// ------ upgrade activity package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (actModel != null) {
			actModel.relateAcrossR9To(pms);
			actModel.setRepresents(upgrade);
			actModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(actModel);
		}
		upgrade.setName(activity.getName());
		upgrade.setDescrip(activity.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		activity.Dispose();
		resultMap.put(upgrade, activity);
		return upgrade;

	}

	/**
	 * This method will take on old style package (pre GP) and convert all
	 * children specification packages to Generic packages.
	 * 
	 * @param packageC
	 */
	private static Package_c upgradePackage(Package_c pkg,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			IProgressMonitor monitor, float taskIncrement) {
		// Get graphics for package diagram . . .
		Model_c[] mdls = Model_c.ModelInstances(Ooaofgraphics.getInstance(pkg
				.getModelRoot().getId()));
		Model_c pkgModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(pkg)) {
				pkgModel = mdls[i];
				break;
			}
		}

		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(pkgModel);
		ComponentPackage_c[] cps = ComponentPackage_c
				.getManyCP_CPsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		InterfacePackage_c[] ifps = InterfacePackage_c
				.getManyIP_IPsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		DataTypePackage_c[] dtPkgs = DataTypePackage_c
				.getManyS_DPKsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		Sequence_c[] seqDgrms = Sequence_c
				.getManySQ_SsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c
				.getManyUC_UCCsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		Communication_c[] comDgrms = Communication_c
				.getManyCOMM_COMMsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		Activity_c[] actDgrms = Activity_c
				.getManyA_AsOnR1402(SpecificationPackage_c
						.getManyEP_SPKGsOnR1400(pkg));
		Package_c[] packages = Package_c
				.getManyEP_PKGsOnR1404(PackageInPackage_c
						.getManyEP_PIPsOnR1403(pkg));
		for (int i = 0; i < cps.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(cps[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeComponentPackage(cps[i], ge,
					SystemModel_c.getOneS_SYSOnR1405(pkg), resultMap, monitor,
					taskIncrement, pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < ifps.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ifps[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeInterfacePackage(ifps[i], ge,
					SystemModel_c.getOneS_SYSOnR1405(pkg), resultMap,
					pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < dtPkgs.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(dtPkgs[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeDTPackage(dtPkgs[i], ge, SystemModel_c
					.getOneS_SYSOnR1405(pkg), resultMap, pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < seqDgrms.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(seqDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeSequenceDiagram(seqDgrms[i], ge,
					SystemModel_c.getOneS_SYSOnR1405(pkg), resultMap, pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < ucDgrms.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ucDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeUseCaseDiagram(ucDgrms[i], ge,
					SystemModel_c.getOneS_SYSOnR1405(pkg), resultMap, pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < comDgrms.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(comDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeCommunicationDiagram(comDgrms[i], ge,
					SystemModel_c.getOneS_SYSOnR1405(pkg), resultMap, pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < actDgrms.length; i++) {
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(actDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			Package_c upgraded = upgradeActivityDiagram(actDgrms[i], ge,
					SystemModel_c.getOneS_SYSOnR1405(pkg), resultMap,
					pkg.getPackage_id(), Gd_c.Null_unique_id());
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(upgraded);
			pkg.relateAcrossR8000To(pe);
		}
		for (int i = 0; i < packages.length; i++) {
			// re-associate package to package using R8000
			PackageInPackage_c pip = PackageInPackage_c
					.getOneEP_PIPOnR1403(pkg);
			pkg.unrelateAcrossR1403From(pip);
			packages[i].unrelateAcrossR1404From(pip);
			pkg.relateAcrossR1405To(SystemModel_c.getOneS_SYSOnR1405(pkg));
			PackageableElement_c pe = new PackageableElement_c(pkg
					.getModelRoot(), pkg.getPackage_id(), Visibility_c.Public,
					Gd_c.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.PACKAGE);
			pe.Initialize();
			pe.setType(Elementtypeconstants_c.PACKAGE);
			pkg.relateAcrossR8000To(pe);
			upgradePackage(pkg, resultMap, monitor, taskIncrement);
		}
		// Packages are retained by the upgrade process, map pkg to itself
		resultMap.put(pkg, pkg);
		return pkg;
	}

	private static Package_c upgradeFNPackage(FunctionPackage_c fnp,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(fnp
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c fnpModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(fnp)) {
				fnpModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(fnpModel);
		FunctionPackage_c[] fnps = FunctionPackage_c
				.getManyS_FPKsOnR32(FunctionPackageInPackage_c
						.getManyS_FPIPsOnR30(fnp));
		Package_c[] pkgs = new Package_c[fnps.length];
		for (int i = 0; i < fnps.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(fnps[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeFNPackage(fnps[i], childGe, sys, resultMap,
			fnp.getFunpack_id(), Gd_c.Null_unique_id());
		}
		Package_c upgrade = new Package_c(fnp.getModelRoot(), fnp
				.getFunpack_id(), Gd_c.Null_unique_id(), sys.getSys_id(), fnp
				.getName(), "", 0);
		PackageableElement_c pe = new PackageableElement_c(fnp.getModelRoot(),
				fnp.getFunpack_id(), Visibility_c.Public,
				packageID, componentID, Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		FunctionInPackage_c[] fips = FunctionInPackage_c
				.getManyS_FIPsOnR31(fnp);
		for (int i = 0; i < fips.length; i++) {
			Function_c fn = Function_c.getOneS_SYNCOnR31(fips[i]);
			pe = new PackageableElement_c(fnp.getModelRoot(), fn.getSync_id(),
					Visibility_c.Public, Gd_c.Null_unique_id(), Gd_c
							.Null_unique_id(), Elementtypeconstants_c.FUNCTION);
			pe.relateAcrossR8001To(fn);
			upgrade.relateAcrossR8000To(pe);
			fnp.unrelateAcrossR31From(fips[i]);
			fn.unrelateAcrossR31From(fips[i]);
			Domain_c dom = Domain_c.getOneS_DOMOnR23(fn);
			if (dom != null) {
				dom.unrelateAcrossR23From(fn);
			}
			fips[i].delete();
		}
		// ------ upgrade Function Package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (fnpModel != null) {
			fnpModel.relateAcrossR9To(pms);
			fnpModel.setRepresents(upgrade);
			fnpModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(fnpModel);
		}
		upgrade.setName(fnp.getName());
		// upgrade.setDescrip(fnp.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		// These packages are disposed in the reverse order that they would be
		// in normal operation, so unrelate the fpips to avoid error log entries
		// The parent package will dispose the fpips
		FunctionPackageInPackage_c[] fpips = FunctionPackageInPackage_c
				.getManyS_FPIPsOnR32(fnp);
		for (int i = 0; i < fpips.length; i++) {
			fnp.unrelateAcrossR32From(fpips[i]);
		}
		elementsToDispose.add(fnp);
		upgrade.setPackage_id(fnp.getFunpack_id());
		PackageableElement_c.getOnePE_PEOnR8001(upgrade).setPackage_id(
				fnp.getFunpack_id());
		upgrade.setUniqueId(fnp.getFunpack_id());
		resultMap.put(upgrade, fnp);
		return upgrade;
	}

	private static Package_c upgradeSubsystem(Subsystem_c ss,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			IProgressMonitor monitor, float taskIncrement,
			UUID packageID, UUID componentID) {
		monitor.subTask("Upgrading subsytem: " + ss.getName());
		while (Display.getCurrent().readAndDispatch()) {
		}
		;
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(ss
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c ssModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null
					&& mdls[i].getRepresents().equals(ss)) {
				ssModel = mdls[i];
				break;
			}
		}
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(ssModel);
		Subsystem_c[] sss = Subsystem_c
				.getManyS_SSsOnR42(SubsystemInSubsystem_c
						.getManyS_SISsOnR41(ss));
		Package_c[] ssPkgs = new Package_c[sss.length];
		for (int i = 0; i < sss.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(sss[i])) {
					childGe = ges[j];
					break;
				}
			}
			ssPkgs[i] = upgradeSubsystem(sss[i], childGe, sys, resultMap,
					monitor, taskIncrement, ss.getSs_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R914-> Sequence
		Sequence_c[] seqs = Sequence_c.getManySQ_SsOnR914(ss);
		Package_c[] seqPkgs = new Package_c[seqs.length];
		for (int i = 0; i < seqs.length; i++) {
			// Get the Sequence package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(seqs[i])) {
					ge = ges[j];
					break;
				}
			}
			seqPkgs[i] = upgradeSequenceDiagram(seqs[i], ge, sys, resultMap, ss.getSs_id(), Gd_c.Null_unique_id());
		}

		// Upgrade R1202-> Use Case Diagram
		UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c.getManyUC_UCCsOnR1202(ss);
		Package_c[] ucPkgs = new Package_c[ucDgrms.length];
		for (int i = 0; i < ucDgrms.length; i++) {
			// Get the Use Case Diagram package symbol on the parent package
			// diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(ucDgrms[i])) {
					ge = ges[j];
					break;
				}
			}
			ucPkgs[i] = upgradeUseCaseDiagram(ucDgrms[i], ge, sys, resultMap, ss.getSs_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R1131 -> Communication
		Communication_c[] coms = Communication_c.getManyCOMM_COMMsOnR1131(ss);
		Package_c[] comPkgs = new Package_c[coms.length];
		for (int i = 0; i < coms.length; i++) {
			// Get the Communication package symbol on the parent package
			// diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(coms[i])) {
					ge = ges[j];
					break;
				}
			}
			comPkgs[i] = upgradeCommunicationDiagram(coms[i], ge, sys,
					resultMap, ss.getSs_id(), Gd_c.Null_unique_id());
		}
		// Upgrade R1108 -> Activity
		Activity_c[] acts = Activity_c.getManyA_AsOnR1108(ss);
		Package_c[] actPkgs = new Package_c[acts.length];
		for (int i = 0; i < acts.length; i++) {
			// Get the Activity package symbol on the parent package diagram.
			GraphicalElement_c ge = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(acts[i])) {
					ge = ges[j];
					break;
				}
			}
			actPkgs[i] = upgradeActivityDiagram(acts[i], ge, sys, resultMap,
			         ss.getSs_id(), Gd_c.Null_unique_id());
		}
		ArrayList<Package_c> packages = new ArrayList<Package_c>(ssPkgs.length
				+ seqPkgs.length + ucPkgs.length + comPkgs.length
				+ actPkgs.length);
		Collections.addAll(packages, ssPkgs);
		Collections.addAll(packages, seqPkgs);
		Collections.addAll(packages, ucPkgs);
		Collections.addAll(packages, comPkgs);
		Collections.addAll(packages, actPkgs);
		Package_c[] pkgs = packages.toArray(new Package_c[] {});
		Package_c upgrade = new Package_c(ss.getModelRoot(), ss.getSs_id(),
				Gd_c.Null_unique_id(), sys.getSys_id(), ss.getName(), ss
						.getDescrip(), 0);
		PackageableElement_c pe = new PackageableElement_c(ss.getModelRoot(),
				ss.getSs_id(), Visibility_c.Public, packageID, componentID,
                Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		upgrade.setNum_rng(ss.getNum_rng());
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}
		// ------ upgrade contents ------------
		ModelClass_c[] classes = ModelClass_c.getManyO_OBJsOnR2(ss);
		for (int i = 0; i < classes.length; i++) {
			pe = new PackageableElement_c(ss.getModelRoot(), classes[i]
					.getObj_id(), Visibility_c.Public, Gd_c.Null_unique_id(),
					Gd_c.Null_unique_id(), Elementtypeconstants_c.CLASS);
			pe.relateAcrossR8001To(classes[i]);
			upgrade.relateAcrossR8000To(pe);
			ss.unrelateAcrossR2From(classes[i]);
		}
		InstanceReferenceDataType_c[] irdts = InstanceReferenceDataType_c.getManyS_IRDTsOnR123(classes);
		DataType_c[] dts = DataType_c.getManyS_DTsOnR17(irdts);
		for ( int i = 0; i < dts.length ; i++){
			pe = new PackageableElement_c(ss.getModelRoot(),dts[i]
			        .getDt_id(), Visibility_c.Public, Gd_c.Null_unique_id(), 
			        Gd_c.Null_unique_id(),Elementtypeconstants_c.DATATYPE);
			pe.relateAcrossR8001To(dts[i]);
			upgrade.relateAcrossR8000To(pe);
		}
		
		Association_c[] assocs = Association_c.getManyR_RELsOnR4(ss);
		for (int i = 0; i < assocs.length; i++) {
			pe = new PackageableElement_c(ss.getModelRoot(), assocs[i]
					.getRel_id(), Visibility_c.Public, Gd_c.Null_unique_id(),
					Gd_c.Null_unique_id(), Elementtypeconstants_c.ASSOCIATION);
			pe.relateAcrossR8001To(assocs[i]);
			upgrade.relateAcrossR8000To(pe);
			ss.unrelateAcrossR4From(assocs[i]);
		}
		ImportedClass_c[] iclasses = ImportedClass_c.getManyO_IOBJsOnR3(ss);
		for (int i = 0; i < iclasses.length; i++) {
			pe = new PackageableElement_c(ss.getModelRoot(), iclasses[i]
					.getIobj_id(), Visibility_c.Public, Gd_c.Null_unique_id(),
					Gd_c.Null_unique_id(),
					Elementtypeconstants_c.IMPORTED_CLASS);
			pe.relateAcrossR8001To(iclasses[i]);
			upgrade.relateAcrossR8000To(pe);
			ss.unrelateAcrossR3From(iclasses[i]);
		}
		// ------ upgrade subsystem itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (ssModel != null) {
			ssModel.relateAcrossR9To(pms);
			ssModel.setRepresents(upgrade);
			ssModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(ssModel);
		}
		upgrade.setName(ss.getName());
		upgrade.setDescrip(ss.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		elementsToDispose.add(ss);
		resultMap.put(upgrade, ss);
		reportProgress(monitor, taskIncrement);
		return upgrade;
	}

	private static Package_c upgradeEEPackage(ExternalEntityPackage_c eep,
			GraphicalElement_c parentGe, SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap, UUID packageID, UUID componentID) {
		ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(eep
				.getModelRoot().getId(), OoaofgraphicsUtil.getGraphicsClass());
		Model_c[] mdls = Model_c.ModelInstances(grRoot);
		Model_c eepModel = null;
		for (int i = 0; i < mdls.length; i++) {
			if (mdls[i].getRepresents() != null && mdls[i].getRepresents().equals(eep)) {
				eepModel = mdls[i];
				break;
			}
		}
		// ------ upgrade nested External Entity Packages------------
		GraphicalElement_c[] ges = GraphicalElement_c
				.getManyGD_GEsOnR1(eepModel);
		ExternalEntityPackage_c[] EEpkgs = ExternalEntityPackage_c
				.getManyS_EEPKsOnR35(EePackageInPackage_c
						.getManyS_EEPIPsOnR34(eep));
		Package_c[] pkgs = new Package_c[EEpkgs.length];
		for (int i = 0; i < EEpkgs.length; i++) {
			// Get the subsystem symbol on the parent package diagram.
			GraphicalElement_c childGe = null;
			for (int j = 0; j < ges.length; j++) {
				if (ges[j].getRepresents() != null
						&& ges[j].getRepresents().equals(EEpkgs[i])) {
					childGe = ges[j];
					break;
				}
			}
			pkgs[i] = upgradeEEPackage(EEpkgs[i], childGe, sys, resultMap, eep.getEepack_id(), Gd_c.Null_unique_id());
		}

		Package_c upgrade = new Package_c(eep.getModelRoot(), eep
				.getEepack_id(), Gd_c.Null_unique_id(), sys.getSys_id(), eep
				.getName(), "", 0);
		PackageableElement_c pe = new PackageableElement_c(eep.getModelRoot(),
				eep.getEepack_id(), Visibility_c.Public, packageID,
				componentID, Elementtypeconstants_c.PACKAGE);
		pe.relateAcrossR8001To(upgrade);
		upgrade.Initialize();
		pe.Initialize();
		pe.setType(Elementtypeconstants_c.PACKAGE);
		for (int i = 0; i < pkgs.length; i++) {
			pe = PackageableElement_c.getOnePE_PEOnR8001(pkgs[i]);
			pe.relateAcrossR8000To(upgrade);
		}

		// ------ upgrade contents ------------
		ExternalEntityInPackage_c[] eeips = ExternalEntityInPackage_c
				.getManyS_EEIPsOnR33(eep);
		for (int i = 0; i < eeips.length; i++) {
			ExternalEntity_c ee = ExternalEntity_c.getOneS_EEOnR33(eeips[i]);
			pe = new PackageableElement_c(eep.getModelRoot(), ee.getEe_id(),
					Visibility_c.Public, Gd_c.Null_unique_id(), Gd_c
							.Null_unique_id(), Elementtypeconstants_c.EE);
			pe.relateAcrossR8001To(ee);
			upgrade.relateAcrossR8000To(pe);
			eep.unrelateAcrossR33From(eeips[i]);
			ee.unrelateAcrossR33From(eeips[i]);
			Domain_c dom = Domain_c.getOneS_DOMOnR8(ee);
			if (dom != null) {
				dom.unrelateAcrossR8From(ee);
			}
			eeips[i].delete();
		}
		// ------ upgrade EE Package itself ---------
		ModelSpecification_c pms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {
							public boolean evaluate(Object ms) {
								ModelSpecification_c selected = (ModelSpecification_c) ms;
								return selected.getModel_type() == Modeltype_c.Package
										&& selected.getOoa_type() == Ooatype_c.Package;
							}
						});
		if (eepModel != null) {
			eepModel.relateAcrossR9To(pms);
			eepModel.setRepresents(upgrade);
			eepModel.setOoa_id(upgrade.Get_ooa_id());
			clearExistingModelTools(eepModel);
		}
		upgrade.setName(eep.getName());
		// upgrade.setDescrip(eep.getDescrip());
		upgrade.relateAcrossR1405To(sys);
		if (parentGe != null) {
			parentGe.relateAcrossR10To(pes);
			parentGe.setRepresents(upgrade);
			parentGe.setOoa_id(upgrade.Get_ooa_id());
		}
		// These packages are disposed in the reverse order that they would be
		// in normal operation, unrelate the eepips to avoid error log entries
		// The parent package will dispose the eepips
		EePackageInPackage_c[] eepips = EePackageInPackage_c
				.getManyS_EEPIPsOnR35(eep);
		for (int i = 0; i < eepips.length; i++) {
			eep.unrelateAcrossR35From(eepips[i]);
		}
		elementsToDispose.add(eep);
		resultMap.put(upgrade, eep);
		return upgrade;
	}

	private static PackageableElement_c upgradeComponent(Component_c comp,
			SystemModel_c sys,
			Map<NonRootModelElement, NonRootModelElement> resultMap,
			IProgressMonitor monitor, float taskIncrement) {
		PackageableElement_c supertype = PackageableElement_c
				.getOnePE_PEOnR8001(comp);
		if (supertype != null) {
			return supertype;
		} else {
			ModelRoot grRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
					comp.getModelRoot().getId(), OoaofgraphicsUtil
							.getGraphicsClass());
			Model_c[] mdls = Model_c.ModelInstances(grRoot);
			Model_c compModel = null;
			for (int i = 0; i < mdls.length; i++) {
				if (mdls[i].getRepresents() != null
						&& mdls[i].getRepresents().equals(comp)) {
					compModel = mdls[i];
					break;
				}
			}
			GraphicalElement_c[] ges = GraphicalElement_c
					.getManyGD_GEsOnR1(compModel);
			Component_c[] cps = Component_c
					.getManyC_CsOnR4203(ComponentInComponent_c
							.getManyCN_CICsOnR4202(comp));
			PackageableElement_c[] pkgElems = new PackageableElement_c[cps.length];
			for (int i = 0; i < cps.length; i++) {
				pkgElems[i] = upgradeComponent(cps[i], sys, resultMap, monitor,
						taskIncrement);
				ComponentInComponent_c cic = ComponentInComponent_c
						.getOneCN_CICOnR4203(cps[i]);
				cic.unrelateAcrossR4203From(cps[i]);
				cic.unrelateAcrossR4202From(comp);
				cic.delete();
			}
			for (int i = 0; i < pkgElems.length; i++) {
				pkgElems[i].relateAcrossR8003To(comp);
			}
			Domain_c dom = Domain_c.getOneS_DOMOnR4204(DomainAsComponent_c
					.getOneCN_DCOnR4204(comp));
			if (dom != null) {
				upgradeDomain(dom, sys, comp, resultMap, monitor, taskIncrement, Gd_c.Null_unique_id());
				Package_c[] pkgs = Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(comp));
				Point point = new Point(0, 0);
				Graphelement_c compGe = null;
				if (compModel != null) {
					for (int i = 0; i < pkgs.length; i++) {
						// Locate graphics from the old domain and move
						// to the component diagram (need to associate with
						// the component symbol to support containment)
						GraphicalElement_c elem = locateGraphicalElementFromDomain(
								dom, pkgs[i].getPackage_id());
						// Get the component symbol on the parent package
						// diagram.
						GraphicalElement_c childGe = null;
						for (int j = 0; j < ges.length; j++) {
							if (ges[j].getRepresents() != null
									&& ges[j].getRepresents().equals(comp)) {
								childGe = ges[j];
								break;
							}
						}
						if (childGe != null) {
							compGe = Graphelement_c
									.getOneDIM_GEOnR23(childGe);							
						} else {
							compGe = Graphelement_c
									.getOneDIM_GEOnR23(elem);							
						}						point.x = (int) compGe.getPositionx();
						point.y = (int) compGe.getPositiony();
						Graphelement_c domEle = Graphelement_c.getOneDIM_GEOnR23(elem); 
						if(point.x > domEle.getPositionx()) {
							point.x = (int) domEle.getPositionx();
						}
						if(point.y > domEle.getPositiony()) {
							point.y = (int) domEle.getPositiony();
						}
						if (compGe != null) {
							Diagramelement_c diaEle = Diagramelement_c
									.getOneDIM_ELEOnR302(Graphelement_c
											.getOneDIM_GEOnR23(elem));
							Model_c oldModel = Model_c.getOneGD_MDOnR1(elem);
							elem.unrelateAcrossR1From(oldModel);
							elem.relateAcrossR1To(Model_c
									.getOneGD_MDOnR1(GraphicalElement_c
											.getOneGD_GEOnR23(compGe)));
							compGe.relateAcrossR307To(diaEle);
						}
						elem.relateAcrossR1To(compModel);
						pes.relateAcrossR10To(elem);
						elem.setRepresents(pkgs[i]);
						elem.setOoa_id(pkgs[i].getPackage_id());
					}
				}
				if(compGe != null) {
					point.x = point.x - 20;
					point.y = point.y - 20;
					Point originalPoint = new Point(
							(int) compGe.getPositionx(), (int) compGe
									.getPositiony());
					compGe.setPositionx(point.x);
					compGe.setPositiony(point.y);
					// also need to adjust any anchors and waypoints that
					// end on this container shape
					Graphconnector_c[] connectors = Graphconnector_c
							.getManyDIM_CONsOnR311(Graphelement_c
									.getManyDIM_GEsOnR23(GraphicalElement_c
											.getManyGD_GEsOnR23(compGe)));
					for(Graphconnector_c connector : connectors) {
						Point delta = new Point((int) compGe.getPositionx()
								- originalPoint.x, (int) compGe.getPositiony()
								- originalPoint.y);
						connector.setPositionx(connector.getPositionx() + delta.x);
						connector.setPositiony(connector.getPositiony() + delta.y);
						Waypoint_c[] startPoints = Waypoint_c
								.getManyDIM_WAYsOnR319(Graphedge_c
										.getManyDIM_EDsOnR320(connector));
						for(Waypoint_c way : startPoints) {
							way.setPositionx(way.getPositionx() + delta.x);
							way.setPositiony(way.getPositiony() + delta.y);
						}
						Waypoint_c[] endPoints = Waypoint_c
								.getManyDIM_WAYsOnR319(Graphedge_c
										.getManyDIM_EDsOnR321(connector));
						for (Waypoint_c way : endPoints) {
							way.setPositionx(way.getPositionx() + delta.x);
							way.setPositiony(way.getPositiony() + delta.y);
						}
					}
					ContainingShape_c cs = ContainingShape_c
							.getOneGD_CTROnR28(Shape_c
									.getOneGD_SHPOnR2(GraphicalElement_c
											.getOneGD_GEOnR23(compGe)));
					if(cs != null) {
						cs.Autoresize();
					}
				}
			}
			// Upgrade Satisfactions
			Satisfaction_c[] sats = Satisfaction_c
					.getManyC_SFsOnR9000(SatisfactionInComponent_c
							.getManyPA_SICsOnR9000(comp));
			for (int i = 0; i < sats.length; i++) {
				SatisfactionInComponent_c sic = SatisfactionInComponent_c
						.getOnePA_SICOnR9000(sats[i]);
				comp.unrelateAcrossR9000From(sic);
				sats[i].unrelateAcrossR9000From(sic);
				PackageableElement_c pe = new PackageableElement_c(sats[i]
						.getModelRoot(), sats[i].getId(), Visibility_c.Public,
						Gd_c.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.SATISFACTION);
				sats[i].relateAcrossR8001To(pe);
				sic.delete();
			}
			// Relate satisfactions to the component
			for (int i = 0; i < sats.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(sats[i]);
				pe.relateAcrossR8003To(comp);
			}
			// Upgrade Delegations
			Delegation_c[] dlgs = Delegation_c
					.getManyC_DGsOnR9002(DelegationInComponent_c
							.getManyPA_DICsOnR9002(comp));
			for (int i = 0; i < dlgs.length; i++) {
				DelegationInComponent_c dic = DelegationInComponent_c
						.getOnePA_DICOnR9002(dlgs[i]);
				comp.unrelateAcrossR9002From(dic);
				dlgs[i].unrelateAcrossR9002From(dic);
				PackageableElement_c pe = new PackageableElement_c(dlgs[i]
						.getModelRoot(), dlgs[i].getId(), Visibility_c.Public,
						Gd_c.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.DELEGATION);
				dlgs[i].relateAcrossR8001To(pe);
				dic.delete();
			}
			// Relate delegations to the component
			for (int i = 0; i < dlgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(dlgs[i]);
				pe.relateAcrossR8003To(comp);
			}
			// Upgrade Component References
			ComponentReference_c[] crs = ComponentReference_c
					.getManyCL_ICsOnR4205(comp);
			for (int i = 0; i < crs.length; i++) {
				comp.unrelateAcrossR4205From(crs[i]);
				PackageableElement_c pe = new PackageableElement_c(crs[i]
						.getModelRoot(), crs[i].getId(), Visibility_c.Public,
						Gd_c.Null_unique_id(), Gd_c.Null_unique_id(),
						Elementtypeconstants_c.COMPONENT_REFERENCE);
				crs[i].relateAcrossR8001To(pe);
			}
			// Relate component references to the component
			for (int i = 0; i < crs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(crs[i]);
				pe.relateAcrossR8003To(comp);
			}
			//
			// Upgrade R952-> Sequence
			Sequence_c[] seqs = Sequence_c.getManySQ_SsOnR952(comp);
			Package_c[] seqPkgs = new Package_c[seqs.length];
			for (int i = 0; i < seqs.length; i++) {
				// Get the Sequence package symbol on the parent package
				// diagram.
				GraphicalElement_c ge = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(seqs[i])) {
						ge = ges[j];
						break;
					}
				}
				seqPkgs[i] = upgradeSequenceDiagram(seqs[i], ge, sys, resultMap,
				                           Gd_c.Null_unique_id(), comp.getId());
			}
			for (int i = 0; i < seqPkgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(seqPkgs[i]);
				pe.relateAcrossR8003To(comp);
			}

			// Upgrade R1213-> Use Case Diagram
			UseCaseDiagram_c[] ucDgrms = UseCaseDiagram_c
					.getManyUC_UCCsOnR1213(comp);
			Package_c[] ucPkgs = new Package_c[ucDgrms.length];
			for (int i = 0; i < ucDgrms.length; i++) {
				// Get the Use Case Diagram package symbol on the parent package
				// diagram.
				GraphicalElement_c ge = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(ucDgrms[i])) {
						ge = ges[j];
						break;
					}
				}
				ucPkgs[i] = upgradeUseCaseDiagram(ucDgrms[i], ge, sys,
						resultMap, Gd_c.Null_unique_id(), comp.getId());
			}
			for (int i = 0; i < ucPkgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(ucPkgs[i]);
				pe.relateAcrossR8003To(comp);
			}
			// Upgrade R1138 -> Communication
			Communication_c[] coms = Communication_c
					.getManyCOMM_COMMsOnR1138(comp);
			Package_c[] comPkgs = new Package_c[coms.length];
			for (int i = 0; i < coms.length; i++) {
				// Get the Communication package symbol on the parent package
				// diagram.
				GraphicalElement_c ge = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(coms[i])) {
						ge = ges[j];
						break;
					}
				}
				comPkgs[i] = upgradeCommunicationDiagram(coms[i], ge, sys,
						resultMap, Gd_c.Null_unique_id(), comp.getId());
			}
			for (int i = 0; i < comPkgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(comPkgs[i]);
				pe.relateAcrossR8003To(comp);
			}
			// Upgrade R1115 -> Activity
			Activity_c[] acts = Activity_c.getManyA_AsOnR1115(comp);
			Package_c[] actPkgs = new Package_c[acts.length];
			for (int i = 0; i < acts.length; i++) {
				// Get the Activity package symbol on the parent package
				// diagram.
				GraphicalElement_c ge = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(acts[i])) {
						ge = ges[j];
						break;
					}
				}
				actPkgs[i] = upgradeActivityDiagram(acts[i], ge, sys, resultMap,
				                           Gd_c.Null_unique_id(), comp.getId());
			}
			for (int i = 0; i < actPkgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(actPkgs[i]);
				pe.relateAcrossR8003To(comp);
			}

			// Upgrade R4206-> InterfacePackage
			InterfacePackage_c[] ifPs = InterfacePackage_c
					.getManyIP_IPsOnR4206(comp);
			Package_c[] ifPkgs = new Package_c[ifPs.length];
			for (int i = 0; i < ifPs.length; i++) {
				// Get the Sequence package symbol on the parent package
				// diagram.
				GraphicalElement_c ge = null;
				for (int j = 0; j < ges.length; j++) {
					if (ges[j].getRepresents() != null
							&& ges[j].getRepresents().equals(ifPs[i])) {
						ge = ges[j];
						break;
					}
				}
				ifPkgs[i] = upgradeInterfacePackage(ifPs[i], ge, sys, resultMap,
				                           Gd_c.Null_unique_id(), comp.getId());
			}
			for (int i = 0; i < ifPkgs.length; i++) {
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(ifPkgs[i]);
				pe.relateAcrossR8003To(comp);
			}
			// ------ upgrade Component itself ---------
			PackageableElement_c upgrade = new PackageableElement_c(comp
					.getModelRoot(), comp.getId(), Visibility_c.Public, Gd_c
					.Null_unique_id(), Gd_c.Null_unique_id(),
					Elementtypeconstants_c.COMPONENT);
			upgrade.Initialize();
			upgrade.relateAcrossR8001To(comp);
			ComponentPackage_c cp = ComponentPackage_c.getOneCP_CPOnR4608(comp);
			if (cp != null) {
				cp.unrelateAcrossR4608From(comp);
			}
			cp = ComponentPackage_c.getOneCP_CPOnR4604(comp);
			if (cp != null) {
				cp.unrelateAcrossR4604From(comp);
			}
			// Components are retained by the upgrade process, map comp to
			// itself
			resultMap.put(comp, comp);
			return upgrade;
		}
	}

	private static GraphicalElement_c locateGraphicalElementFromDomain(
			final Domain_c dom, final UUID id) {
		Model_c model = Model_c.ModelInstance(Ooaofgraphics.getInstance(dom
				.getModelRoot().getId()), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Model_c) candidate).getRepresents() == dom;
			}
		});
		return GraphicalElement_c.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((GraphicalElement_c) candidate).getOoa_id().equals(id);
			}
		});
	}

}
