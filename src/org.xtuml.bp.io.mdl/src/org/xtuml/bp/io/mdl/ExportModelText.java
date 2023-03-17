package org.xtuml.bp.io.mdl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AttributeReferenceInClass_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.ClassAsSubtype_c;
import org.xtuml.bp.core.ClassAsSupertype_c;
import org.xtuml.bp.core.ClassIdentifierAttribute_c;
import org.xtuml.bp.core.ClassIdentifier_c;
import org.xtuml.bp.core.ClassInAssociation_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.EventIgnored_c;
import org.xtuml.bp.core.Exception_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ifdirectiontype_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.LocalEvent_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.NonLocalEvent_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.PackageReference_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.Range_c;
import org.xtuml.bp.core.ReferentialAttribute_c;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferredToIdentifierAttribute_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.Satisfaction_c;
import org.xtuml.bp.core.Scope_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.StructureMember_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.IPersistenceHierarchyMetaData;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.sorter.AttributeReferenceInClass_cSorter;
import org.xtuml.bp.core.sorter.Attribute_cSorter;
import org.xtuml.bp.core.sorter.BridgeParameter_cSorter;
import org.xtuml.bp.core.sorter.Enumerator_cSorter;
import org.xtuml.bp.core.sorter.FunctionParameter_cSorter;
import org.xtuml.bp.core.sorter.LiteralSymbolicConstant_cSorter;
import org.xtuml.bp.core.sorter.OperationParameter_cSorter;
import org.xtuml.bp.core.sorter.Operation_cSorter;
import org.xtuml.bp.core.sorter.PropertyParameter_cSorter;
import org.xtuml.bp.core.sorter.StateMachineEventDataItem_cSorter;
import org.xtuml.bp.core.sorter.StructureMember_cSorter;
import org.xtuml.bp.io.core.ProxyUtil;
import org.xtuml.bp.ui.canvas.CanvasPlugin;

// TODO determine how to sanitize names
// TODO consider implications on the path that is exported with visibility and package references
// TODO multiple instances of "unassigned etc." graphics

public class ExportModelText extends ExportModelComponent {

	private interface PortMessage {
		String getAction_semantics();

		int getNumb();

		int getSuc_pars();
	}

	private int tabDepth = 0;
	private final Stack<StringBuilder> buffers = new Stack<>();

	private final IPersistenceHierarchyMetaData metadata = new PersistenceHierarchyMetaData();

	public ExportModelText(Ooaofooa modelRoot, ByteArrayOutputStream baos, NonRootModelElement element) {
		super(modelRoot, baos, element);
	}

	public ExportModelText(Ooaofooa modelRoot, String outfileName, boolean export_graphics)
			throws FileNotFoundException {
		super(modelRoot, outfileName, true);
	}

	public ExportModelText(Ooaofooa modelRoot, String outfileName, boolean export_graphics,
			NonRootModelElement instance) throws FileNotFoundException {
		super(modelRoot, outfileName, true, instance);
	}

	public ExportModelText(String outfileName, NonRootModelElement element) throws FileNotFoundException {
		super(outfileName, element);
	}

	private void append(String format, Object... args) {
		if (!buffers.empty()) {
			buffers.peek().append(String.format(format, args));
		} else {
			m_fh.printf(format, args);
			m_fh.flush();
		}
	}

	@Override
	protected void export_Association_c(Association_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		final SimpleAssociation_c r_simp = SimpleAssociation_c.getOneR_SIMPOnR206(inst);
		final LinkedAssociation_c r_assoc = LinkedAssociation_c.getOneR_ASSOCOnR206(inst);
		final SubtypeSupertypeAssociation_c r_subsup = SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(inst);
		if (r_simp != null) {
			export_SimpleAssociation_c(r_simp, pm, writeAsProxies, isPersistable);
		} else if (r_assoc != null) {
			export_LinkedAssociation_c(r_assoc, pm, writeAsProxies, isPersistable);
		} else if (r_subsup != null) {
			export_SubtypeSupertypeAssociation_c(r_subsup, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_Attribute_c(Attribute_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// attribute metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			final DerivedBaseAttribute_c dbattr = DerivedBaseAttribute_c
					.getOneO_DBATTROnR107(BaseAttribute_c.getOneO_BATTROnR106(inst));
			if (dbattr != null && dbattr.getSuc_pars() == Parsestatus_c.doNotParse) {
				append("%s@noparse;\n", getTab());
			}
			final ReferentialAttribute_c rattr = ReferentialAttribute_c.getOneO_RATTROnR106(inst);
			if (rattr != null) {
				append("%s@ref_mode(\"%s\");\n", getTab(), rattr.getRef_mode() == 0 ? "local" : "referred_to");
				if (rattr.getRef_mode() != 0) {
					if (inst.getPfx_mode() == 1) {
						append("%s@use_prefix(prefix=\"%s\", root_name=\"%s\");\n", getTab(), inst.getPrefix(),
								inst.getRoot_nam());
					} else if (inst.getPfx_mode() == 2) {
						append("%s@use_referred_to_prefix(root_name=\"%s\");\n", getTab(), inst.getRoot_nam());
					}
				}
			}

			// get the attribute type
			DataType_c type = DataType_c.getOneS_DTOnR114(inst);
			if ("ba5eda7a-def5-0000-0000-000000000007".equals(type.getDt_id().toString())) {
				type = DataType_c.getOneS_DTOnR114(Attribute_c.getOneO_ATTROnR106(
						BaseAttribute_c.getOneO_BATTROnR113(ReferentialAttribute_c.getOneO_RATTROnR106(inst))));
			}
			final boolean isUnique = "unique_id".equals(type.getName())
					&& BaseAttribute_c.getOneO_BATTROnR106(inst) != null;
			if ("unique_id".equals(type.getName())) {
				// "unique_id" maps to 'unique integer' in textual xtUML
				type = (DataType_c) inst.getModelRoot().getInstanceList(DataType_c.class)
						.getGlobal("ba5eda7a-def5-0000-0000-000000000002");
			}

			// append the basic attribute definition
			append("%s%s%s: ", getTab(), isUnique ? "unique " : "", inst.getName());

			// append attribute references
			AttributeReferenceInClass_c[] refs = AttributeReferenceInClass_c
					.getManyO_REFsOnR108(ReferentialAttribute_c.getOneO_RATTROnR106(inst));
			new AttributeReferenceInClass_cSorter().sort(refs);
			if (refs.length > 0) {
				append("referential (");
			}
			for (AttributeReferenceInClass_c ref : refs) {
				final ReferredToIdentifierAttribute_c rtida = ReferredToIdentifierAttribute_c.getOneO_RTIDAOnR111(ref);
				final ClassInAssociation_c oir = ClassInAssociation_c
						.getOneR_OIROnR203(ReferredToClassInAssoc_c.getOneR_RTOOnR110(rtida));
				final Association_c rel = Association_c.getOneR_RELOnR201(oir);
				append("R%d.", rel.getNumb());
				if (ref.getRobj_id().equals(ref.getObj_id())) {
					append("%s.", sanitizeName(oir.Get_text_phrase()));
				}
				final Attribute_c ref_attr = Attribute_c
						.getOneO_ATTROnR105(ClassIdentifierAttribute_c.getOneO_OIDAOnR110(rtida));
				final ModelClass_c obj = ModelClass_c.getOneO_OBJOnR102(ref_attr);
				append("%s.%s", sanitizeName(obj.getName()), ref_attr.getName());
				ref = AttributeReferenceInClass_c.getOneO_REFOnR112Precedes(ref);
				if (ref != null) {
					append(", ");
				}
			}
			if (refs.length > 0) {
				append(") ");
			}

			// append the datatype
			append("%s", getTypeReference(type, inst.getDimensions()));

			// append the default value if applicable
			if (BaseAttribute_c.getOneO_BATTROnR106(inst) != null && !isUnique && !inst.getDefaultvalue().isBlank()) {
				append(" = %s", inst.getDefaultvalue().strip());
			}

			// handle derived attributes
			if (dbattr != null) {
				append(" is\n");
				tabDepth++;
				// append the inner actions
				if (!dbattr.getAction_semantics().isBlank()) {
					append("%s@noparse\n", getTab()); // TODO
				}
				dbattr.getAction_semantics().strip().lines().forEach(line -> {
					append("%s%s\n", getTab(), line);
				});
				if (!dbattr.getAction_semantics().isBlank()) {
					append("%s@endnoparse\n", getTab());
				}
				tabDepth--;
				append("%send", getTab());
			}

			// complete the attribute
			append(";\n\n");
		} else {
			write_Attribute_c_proxy_sql(inst);
		}

	}

	protected void export_Bridge_c(Bridge_c inst, IProgressMonitor pm, boolean writeAsProxies, boolean isPersistable)
			throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			// bridge metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getSuc_pars() == Parsestatus_c.doNotParse) {
				append("%s@noparse;\n", getTab());
			}

			// TODO parameter descriptions

			// build the parameter list
			final BridgeParameter_c[] s_bparms = BridgeParameter_c.getManyS_BPARMsOnR21(inst);
			new BridgeParameter_cSorter().sort(s_bparms);
			final String parameterList = Stream.of(s_bparms).map(s_bparm -> {
				buffers.push(new StringBuilder());
				try {
					export_BridgeParameter_c(s_bparm, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));

			// get the return type
			final DataType_c returnType = DataType_c.getOneS_DTOnR20(inst);

			append("%sbridge %s(%s)", getTab(), sanitizeName(inst.getName()), parameterList);
			if (!"ba5eda7a-def5-0000-0000-000000000000".equals(returnType.getDt_id().toString())) {
				append(" return %s", getTypeReference(returnType, inst.getReturn_dimensions()));
			}

			// append the inner actions
			if (!inst.getAction_semantics_internal().isBlank()) {
				append(" is\n");
				tabDepth++;
				append("%s@noparse\n", getTab()); // TODO
				inst.getAction_semantics().strip().lines().forEach(line -> {
					append("%s%s\n", getTab(), line);
				});
				append("%s@endnoparse\n", getTab());
				tabDepth--;
				append("%send bridge", getTab());
			}
			append(";\n\n");
		} else {
			write_Bridge_c_proxy_sql(inst);
		}
	}

	protected void export_BridgeParameter_c(BridgeParameter_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s: %s %s", inst.getName(), inst.getBy_ref() == 0 ? "in" : "out",
					getTypeReference(DataType_c.getOneS_DTOnR22(inst), inst.getDimensions()));
		} else {
			write_BridgeParameter_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ClassIdentifier_c(ClassIdentifier_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s%sidentifier is (", getTab(), inst.getOid_id() == 0 ? "preferred " : "");
			Set<UUID> idAttrIds = Stream.of(ClassIdentifierAttribute_c.getManyO_OIDAsOnR105(inst))
					.map(ClassIdentifierAttribute_c::getAttr_id).collect(Collectors.toSet());
			String sep = "";
			Attribute_c attr = Attribute_c.getOneO_ATTROnR102(
					ModelClass_c.getOneO_OBJOnR102(Attribute_c.getOneO_ATTROnR105(inst)),
					selected -> Attribute_c.getOneO_ATTROnR103Succeeds((Attribute_c) selected) == null);
			while (attr != null) {
				if (idAttrIds.contains(attr.getAttr_id())) {
					append("%s%s", sep, attr.getName());
					sep = ", ";
				}
				attr = Attribute_c.getOneO_ATTROnR103Precedes(attr);
			}
			append(");\n\n");
		} else {
			write_ClassIdentifier_c_proxy_sql(inst);
		}

	}

	@Override
	protected void export_ClassStateMachine_c(ClassStateMachine_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			export_StateMachine_c(StateMachine_c.getOneSM_SMOnR517(inst), pm, writeAsProxies, isPersistable);
		} else {
			write_ClassStateMachine_c_proxy_sql(inst);
		}

	}

	@Override
	protected void export_Component_c(Component_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			append("%swithin %s is\n\n", getTab(), sanitizePath(metadata.getParent(inst).getPath()));
			tabDepth++;

			// component metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getMult() == 1) {
				append("%s@mult(\"many\");\n", getTab());
			}
			if (!inst.getKey_lett().isBlank()) {
				append("%s@key_letters(\"%s\");\n", getTab(), inst.getKey_lett().strip());
			}
			if (inst.getIsrealized()) {
				append("%s@realized", getTab());
				if (!inst.getRealized_class_path().isBlank()) {
					append("(classpath=\"%s\")", getTab(), inst.getRealized_class_path().strip());
				}
				append(";\n");
			}

			append("%scomponent %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			// TODO inner components, delegations, satisfactions, etc.

			// declare inner packages
			final Package_c[] ep_pkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(inst));
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}
			for (Package_c ep_pkg : Stream.of(ep_pkgs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				append("%spackage %s", getTab(), sanitizeName(ep_pkg.getName()));
				final Package_c referredToPackage = Package_c
						.getOneEP_PKGOnR1402RefersTo(PackageReference_c.getOneEP_PKGREFOnR1402RefersTo(ep_pkg));
				if (referredToPackage != null) {
					append(" is %s", sanitizePath(referredToPackage.getPath()));
				}
				append(";\n");
			}
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}

			// ports
			final Port_c[] c_pos = Port_c.getManyC_POsOnR4010(inst);
			for (Port_c c_po : Stream.of(c_pos).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_Port_c(c_po, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send component;\n\n", getTab());
			tabDepth--;
			append("%send;\n", getTab());
		} else {
			super.export_Component_c(inst, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_ComponentReference_c(ComponentReference_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// TODO consider multiple references to the same component in a package

			// component reference metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getMult() == 1) {
				append("%s@mult(\"many\");\n", getTab());
			}
			if (!inst.getClassifiername().isBlank()) {
				append("%s@classifier_name(\"%s\");\n", getTab(), inst.getClassifiername().strip());
			}

			final Component_c comp = Component_c.getOneC_COnR4201(inst);
			if (comp != null) {
				append("%scomponent %s is %s;\n", getTab(), sanitizeName(comp.getName()), sanitizePath(comp.getPath()));
			} else {
				append("%scomponent 'Unassigned Imported Component';\n", getTab());
			}
		} else {
			write_ComponentReference_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ConstantSpecification_c(ConstantSpecification_c inst, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			append("%sconstant group %s is\n", getTab(), sanitizeName(inst.getInformalgroupname()));
			tabDepth++;

			final LiteralSymbolicConstant_c[] cnst_lscs = LiteralSymbolicConstant_c.getManyCNST_LSCsOnR1503(
					LeafSymbolicConstant_c.getManyCNST_LFSCsOnR1502(SymbolicConstant_c.getManyCNST_SYCsOnR1504(inst)));
			new LiteralSymbolicConstant_cSorter().sort(cnst_lscs);
			for (LiteralSymbolicConstant_c cnst_lsc : cnst_lscs) {
				export_LiteralSymbolicConstant_c(cnst_lsc, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send constant group;\n\n", getTab());

		} else {
			write_ConstantSpecification_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_DataType_c(DataType_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final StructuredDataType_c sdt = StructuredDataType_c.getOneS_SDTOnR17(inst);
			final EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(inst);
			final UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(inst);
			if (sdt != null) {
				export_StructuredDataType_c(sdt, pm, writeAsProxies, isPersistable);
			} else if (edt != null) {
				export_EnumerationDataType_c(edt, pm, writeAsProxies, isPersistable);
			} else if (udt != null) {
				export_UserDataType_c(udt, pm, writeAsProxies, isPersistable);
			}
			// ignore IRDTs and CDTs
		} else {
			write_DataType_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_EnumerationDataType_c(EnumerationDataType_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final DataType_c dt = DataType_c.getOneS_DTOnR17(inst);

			// export description
			dt.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			append("%stype %s is enum (\n", getTab(), sanitizeName(dt.getName()));
			tabDepth++;

			// process enumerators
			final Enumerator_c[] enums = Enumerator_c.getManyS_ENUMsOnR27(inst);
			new Enumerator_cSorter().sort(enums);
			for (Enumerator_c s_enum : enums) {
				export_Enumerator_c(s_enum, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%s);\n\n", getTab());
		} else {
			write_EnumerationDataType_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Enumerator_c(Enumerator_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// export description
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			final boolean hasNext = Enumerator_c.getOneS_ENUMOnR56Precedes(inst) != null;
			append("%s%s%s\n", getTab(), sanitizeName(inst.getName()), hasNext ? ", " : "");

		} else {
			write_Enumerator_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Exception_c(Exception_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			append("%sexception %s;\n\n", getTab(), sanitizeName(inst.getName()));
		} else {
			write_Exception_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ExecutableProperty_c(ExecutableProperty_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// append the description
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			// TODO append parameter descriptions

			// append the message
			if (inst.getNumb() > 0) {
				append("%s@message_num(%d);\n", getTab(), inst.getNumb());
			}
			append("%s%s;\n", getTab(), getMessageSignature(inst, pm, writeAsProxies, isPersistable));

		} else {
			super.export_ExecutableProperty_c(inst, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_ExternalEntity_c(ExternalEntity_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			// EE metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (!inst.getKey_lett().isBlank()) {
				append("%s@key_letters(\"%s\");\n", getTab(), inst.getKey_lett().strip());
			}
			if (inst.getIsrealized()) {
				append("%s@realized", getTab());
				if (!inst.getRealized_class_path().isBlank()) {
					append("(classpath=\"%s\")", getTab(), inst.getRealized_class_path().strip());
				}
				append(";\n");
			}

			append("%sexternal %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			// bridges
			final Bridge_c[] s_brgs = Bridge_c.getManyS_BRGsOnR19(inst);
			for (Bridge_c s_brg : Stream.of(s_brgs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_Bridge_c(s_brg, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send external;\n\n", getTab());
		} else {
			write_ExternalEntity_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Function_c(Function_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// function metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getSuc_pars() == Parsestatus_c.doNotParse) {
				append("%s@noparse;\n", getTab());
			}
			if (inst.getNumb() > 0) {
				append("%s@function_num(%d);\n", getTab(), inst.getNumb());
			}

			// TODO parameter descriptions

			// build the parameter list
			final FunctionParameter_c[] s_sparms = FunctionParameter_c.getManyS_SPARMsOnR24(inst);
			new FunctionParameter_cSorter().sort(s_sparms);
			final String parameterList = Stream.of(s_sparms).map(s_sparm -> {
				buffers.push(new StringBuilder());
				try {
					export_FunctionParameter_c(s_sparm, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));

			// get the return type
			final DataType_c returnType = DataType_c.getOneS_DTOnR25(inst);

			append("%sfunction %s(%s)", getTab(), sanitizeName(inst.getName()), parameterList);
			if (!"ba5eda7a-def5-0000-0000-000000000000".equals(returnType.getDt_id().toString())) {
				append(" return %s", getTypeReference(returnType, inst.getReturn_dimensions()));
			}

			// append the inner actions
			if (!inst.getAction_semantics_internal().isBlank()) {
				append(" is\n");
				tabDepth++;
				append("%s@noparse\n", getTab()); // TODO
				inst.getAction_semantics().strip().lines().forEach(line -> {
					append("%s%s\n", getTab(), line);
				});
				append("%s@endnoparse\n", getTab());
				tabDepth--;
				append("%send function", getTab());
			}
			append(";\n\n");
		} else {
			write_Function_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_FunctionParameter_c(FunctionParameter_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s: %s %s", inst.getName(), inst.getBy_ref() == 0 ? "in" : "out",
					getTypeReference(DataType_c.getOneS_DTOnR26(inst), inst.getDimensions()));
		} else {
			write_FunctionParameter_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ImportedClass_c(ImportedClass_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%sclass %s", getTab(), sanitizeName(inst.getName()));
			final ModelClass_c refClass = ModelClass_c.getOneO_OBJOnR101(inst);
			if (refClass != null) {
				append(" is %s", sanitizePath(refClass.getPath()));
			}
			append(";\n");
		} else {
			write_ImportedClass_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_InstanceStateMachine_c(InstanceStateMachine_c inst, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			export_StateMachine_c(StateMachine_c.getOneSM_SMOnR517(inst), pm, writeAsProxies, isPersistable);
		} else {
			write_InstanceStateMachine_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Interface_c(Interface_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%swithin %s is\n\n", getTab(), sanitizePath(metadata.getParent(inst).getPath()));
			tabDepth++;
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			append("%sinterface %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			// interface operations
			final ExecutableProperty_c[] c_eps_1 = ExecutableProperty_c.getManyC_EPsOnR4004(
					InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inst)));
			if (c_eps_1.length > 0) {
				append("%s\n", getTab());
			}
			for (ExecutableProperty_c c_ep : c_eps_1) {
				export_ExecutableProperty_c(c_ep, pm, writeAsProxies, isPersistable);
			}

			// interface signals
			final ExecutableProperty_c[] c_eps_2 = ExecutableProperty_c.getManyC_EPsOnR4004(
					InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inst)));
			if (c_eps_2.length > 0) {
				append("%s\n", getTab());
			}
			for (ExecutableProperty_c c_ep : c_eps_2) {
				export_ExecutableProperty_c(c_ep, pm, writeAsProxies, isPersistable);
			}

			if (c_eps_1.length + c_eps_2.length > 0) {
				append("%s\n", getTab());
			}

			tabDepth--;
			append("%send interface;\n\n", getTab());
			tabDepth--;
			append("%send;\n", getTab());
		} else {
			super.export_Interface_c(inst, pm, writeAsProxies, isPersistable);
		}
	}

	@Override
	protected void export_LinkedAssociation_c(LinkedAssociation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final Association_c r_rel = Association_c.getOneR_RELOnR206(inst);

			// ouptut description
			r_rel.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			final String relName = String.format("relationship R%d is ", r_rel.getNumb());
			final String padding = relName.replaceAll(".", " ");

			append("%s%s", getTab(), relName);

			// get 'one' side information
			final ClassAsAssociatedOneSide_c r_aone = ClassAsAssociatedOneSide_c.getOneR_AONEOnR209(inst);
			final ClassInAssociation_c oneOir = ClassInAssociation_c
					.getOneR_OIROnR203(ReferredToClassInAssoc_c.getOneR_RTOOnR204(r_aone));
			final ModelClass_c oneClass = ModelClass_c.getOneO_OBJOnR201(oneOir);
			final ImportedClass_c oneImportedClass = ImportedClass_c.getOneO_IOBJOnR202(oneOir);
			final String oneName = oneImportedClass != null ? sanitizeName(oneImportedClass.getName())
					: sanitizeName(oneClass.getName());
			final String oneCond = r_aone.getCond() == 0 ? "unconditionally" : "conditionally";
			final String oneMult = r_aone.getMult() == 0 ? "one" : "many";
			final String onePhrase = r_aone.getTxt_phrs().isBlank() ? "relates to" : sanitizeName(r_aone.getTxt_phrs());

			// get 'other' side information
			final ClassAsAssociatedOtherSide_c r_aoth = ClassAsAssociatedOtherSide_c.getOneR_AOTHOnR210(inst);
			final ClassInAssociation_c otherOir = ClassInAssociation_c
					.getOneR_OIROnR203(ReferredToClassInAssoc_c.getOneR_RTOOnR204(r_aoth));
			final ModelClass_c otherClass = ModelClass_c.getOneO_OBJOnR201(otherOir);
			final ImportedClass_c otherImportedClass = ImportedClass_c.getOneO_IOBJOnR202(otherOir);
			final String otherName = otherImportedClass != null ? sanitizeName(otherImportedClass.getName())
					: sanitizeName(otherClass.getName());
			final String otherCond = r_aoth.getCond() == 0 ? "unconditionally" : "conditionally";
			final String otherMult = r_aoth.getMult() == 0 ? "one" : "many";
			final String otherPhrase = r_aoth.getTxt_phrs().isBlank() ? "relates to"
					: sanitizeName(r_aoth.getTxt_phrs());

			// get 'link' information
			final ClassAsLink_c r_assr = ClassAsLink_c.getOneR_ASSROnR211(inst);
			final ClassInAssociation_c linkOir = ClassInAssociation_c
					.getOneR_OIROnR203(ReferringClassInAssoc_c.getOneR_RGOOnR205(r_assr));
			final ModelClass_c linkClass = ModelClass_c.getOneO_OBJOnR201(linkOir);
			final ImportedClass_c linkImportedClass = ImportedClass_c.getOneO_IOBJOnR202(linkOir);
			final String linkName = linkImportedClass != null ? sanitizeName(linkImportedClass.getName())
					: sanitizeName(linkClass.getName());
			final String linkMult = r_assr.getMult() == 0 ? "one" : "many";

			append("%s %s %s %s %s,\n", oneName, otherCond, otherPhrase, otherMult, otherName);
			append("%s%s%s %s %s %s %s\n", getTab(), padding, otherName, oneCond, onePhrase, oneMult, oneName);
			append("%s%susing %s %s;\n\n", getTab(), padding, linkMult, linkName);
		} else {
			write_LinkedAssociation_c_proxy_sql(inst);
		}

	}

	@Override
	protected void export_LiteralSymbolicConstant_c(LiteralSymbolicConstant_c inst, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final SymbolicConstant_c cnst_syc = SymbolicConstant_c
					.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c.getOneCNST_LFSCOnR1503(inst));
			cnst_syc.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			final DataType_c type = DataType_c.getOneS_DTOnR1500(cnst_syc);
			append("%s%s: %s = %s;\n", getTab(), sanitizeName(cnst_syc.getName()), getTypeReference(type),
					inst.getValue().strip());

		} else {
			write_LiteralSymbolicConstant_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ModelClass_c(ModelClass_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%swithin %s is\n\n", getTab(), sanitizePath(metadata.getParent(inst).getPath()));
			tabDepth++;

			// class metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			append("%s@key_letters(\"%s\");\n", getTab(), inst.getKey_lett().strip());
			append("%s@class_num(%d);\n", getTab(), inst.getNumb());

			append("%sclass %s is\n\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			// attributes
			Attribute_c[] attrs = Attribute_c.getManyO_ATTRsOnR102(inst);
			new Attribute_cSorter().sort(attrs);
			for (Attribute_c attr : attrs) {
				if (!"current_state".equals(attr.getName())) {
					export_Attribute_c(attr, pm, writeAsProxies, isPersistable);
				}
			}

			// identifiers
			ClassIdentifier_c[] oids = ClassIdentifier_c.getManyO_IDsOnR104(inst,
					selected -> ClassIdentifierAttribute_c.getOneO_OIDAOnR105((ClassIdentifier_c) selected) != null);
			Arrays.sort(oids, (a, b) -> a.getOid_id() - b.getOid_id());
			for (ClassIdentifier_c oid : oids) {
				export_ClassIdentifier_c(oid, pm, writeAsProxies, isPersistable);
			}

			// operations
			Operation_c[] tfrs = Operation_c.getManyO_TFRsOnR115(inst);
			new Operation_cSorter().sort(tfrs);
			for (Operation_c tfr : tfrs) {
				export_Operation_c(tfr, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send class;\n\n", getTab());
			tabDepth--;
			append("%send;\n", getTab());
		} else {
			write_ModelClass_c_proxy_sql(inst);
		}

	}

	@Override
	protected void export_Operation_c(Operation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// operation metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getSuc_pars() == Parsestatus_c.doNotParse) {
				append("%s@noparse;\n", getTab());
			}
			if (inst.getNumb() > 0) {
				append("%s@operation_num(%d);\n", getTab(), inst.getNumb());
			}

			// TODO deferral required
			// TODO parameter descriptions

			// build the parameter list
			final OperationParameter_c[] o_tparms = OperationParameter_c.getManyO_TPARMsOnR117(inst);
			new OperationParameter_cSorter().sort(o_tparms);
			final String parameterList = Stream.of(o_tparms).map(o_tparm -> {
				buffers.push(new StringBuilder());
				try {
					export_OperationParameter_c(o_tparm, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));

			// get the return type
			final DataType_c returnType = DataType_c.getOneS_DTOnR116(inst);

			// get the deferral relationship if applicable
			final Association_c def_rel = Association_c.getOneR_RELOnR126(inst);

			append("%s%soperation %s(%s)", getTab(),
					inst.getInstance_based() == Scope_c.Class ? "class "
							: (def_rel != null ? ("deferred (R" + def_rel.getNumb() + ") ") : ""),
					sanitizeName(inst.getName()), parameterList);
			if (!"ba5eda7a-def5-0000-0000-000000000000".equals(returnType.getDt_id().toString())) {
				append(" return %s", getTypeReference(returnType, inst.getReturn_dimensions()));
			}

			// append the inner actions
			if (!inst.getAction_semantics_internal().isBlank()) {
				append(" is\n");
				tabDepth++;
				append("%s@noparse\n", getTab()); // TODO
				inst.getAction_semantics().strip().lines().forEach(line -> {
					append("%s%s\n", getTab(), line);
				});
				append("%s@endnoparse\n", getTab());
				tabDepth--;
				append("%send operation", getTab());
			}
			append(";\n\n");

		} else {
			write_Operation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_OperationParameter_c(OperationParameter_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s: %s %s", inst.getName(), inst.getBy_ref() == 0 ? "in" : "out",
					getTypeReference(DataType_c.getOneS_DTOnR118(inst), inst.getDimensions()));
		} else {
			write_OperationParameter_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Package_c(Package_c inst, IProgressMonitor pm, boolean writeAsProxies, boolean isPersistable)
			throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%swithin %s is\n\n", getTab(), sanitizePath(metadata.getParent(inst).getPath()));
			tabDepth++;

			// package metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getNum_rng() != 0) {
				append("%s@start_numbering(%d);\n", getTab(), inst.getNum_rng());
			}

			append("%spackage %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			// datatypes
			final DataType_c[] s_dts = DataType_c.getManyS_DTsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (DataType_c s_dt : Stream.of(s_dts).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_DataType_c(s_dt, pm, writeAsProxies, isPersistable);
			}

			// exceptions
			final Exception_c[] s_exps = Exception_c
					.getManyS_EXPsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Exception_c s_exp : Stream.of(s_exps).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_Exception_c(s_exp, pm, writeAsProxies, isPersistable);
			}

			// constant specifications
			final ConstantSpecification_c[] cnst_csps = ConstantSpecification_c
					.getManyCNST_CSPsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (ConstantSpecification_c cnst_csp : Stream.of(cnst_csps)
					.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
				export_ConstantSpecification_c(cnst_csp, pm, writeAsProxies, isPersistable);
			}

			// classes
			final ModelClass_c[] o_objs = ModelClass_c
					.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (ModelClass_c o_obj : Stream.of(o_objs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				append("%sclass %s;\n", getTab(), sanitizeName(o_obj.getName()));
			}

			// imported classes
			final ImportedClass_c[] o_iobjs = ImportedClass_c
					.getManyO_IOBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (ImportedClass_c o_iobj : Stream.of(o_iobjs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_ImportedClass_c(o_iobj, pm, writeAsProxies, isPersistable);
			}

			// relationships
			final Association_c[] r_rels = Association_c
					.getManyR_RELsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Association_c r_rel : Stream.of(r_rels).sorted(Comparator.comparing(Association_c::getNumb))
					.collect(Collectors.toList())) {
				export_Association_c(r_rel, pm, writeAsProxies, isPersistable);
			}

			// interfaces
			final Interface_c[] c_is = Interface_c.getManyC_IsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Interface_c c_i : Stream.of(c_is).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				append("%sinterface %s;\n", getTab(), sanitizeName(c_i.getName()));
			}

			// components
			final Component_c[] c_cs = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Component_c c_c : Stream.of(c_cs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				append("%scomponent %s;\n", getTab(), sanitizeName(c_c.getName()));
			}

			// component references
			final ComponentReference_c[] cl_ics = ComponentReference_c
					.getManyCL_ICsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (ComponentReference_c cl_ic : Stream.of(cl_ics)
					.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
				export_ComponentReference_c(cl_ic, pm, writeAsProxies, isPersistable);
			}

			// satisfactions
			final Satisfaction_c[] c_sfs = Satisfaction_c
					.getManyC_SFsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Satisfaction_c c_sf : Stream.of(c_sfs).sorted(Comparator.comparing(Satisfaction_c::getLabel))
					.collect(Collectors.toList())) {
				export_Satisfaction_c(c_sf, pm, writeAsProxies, isPersistable);
			}

			// TODO delegations

			// TODO deployments

			// EEs
			final ExternalEntity_c[] s_ees = ExternalEntity_c
					.getManyS_EEsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (ExternalEntity_c s_ee : Stream.of(s_ees).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_ExternalEntity_c(s_ee, pm, writeAsProxies, isPersistable);
			}

			// functions
			final Function_c[] s_syncs = Function_c
					.getManyS_SYNCsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Function_c s_sync : Stream.of(s_syncs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				export_Function_c(s_sync, pm, writeAsProxies, isPersistable);
			}

			// packages
			final Package_c[] ep_pkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(inst));
			for (Package_c ep_pkg : Stream.of(ep_pkgs).sorted(Comparator.comparing(NonRootModelElement::getName))
					.collect(Collectors.toList())) {
				append("%spackage %s", getTab(), sanitizeName(ep_pkg.getName()));
				final Package_c referredToPackage = Package_c
						.getOneEP_PKGOnR1402RefersTo(PackageReference_c.getOneEP_PKGREFOnR1402RefersTo(ep_pkg));
				if (referredToPackage != null) {
					append(" is %s", sanitizePath(referredToPackage.getPath()));
				}
				append(";\n");
			}

			tabDepth--;
			append("%send package;\n\n", getTab());
			tabDepth--;
			append("%send;\n", getTab());

		} else {
			write_Package_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Port_c(Port_c inst, IProgressMonitor pm, boolean writeAsProxies, boolean isPersistable)
			throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final InterfaceReference_c c_ir = InterfaceReference_c.getOneC_IROnR4016(inst);
			final Provision_c c_p = Provision_c.getOneC_POnR4009(c_ir);
			final Requirement_c c_r = Requirement_c.getOneC_ROnR4009(c_ir);
			final String description = c_p != null ? c_p.getDescrip() : c_r.getDescrip();
			final String informalName = c_p != null ? c_p.getInformalname() : c_r.getInformalname();

			// port metadata
			description.strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getMult() == 1) {
				append("%s@mult(\"many\");\n", getTab());
			}
			if (inst.getDonotshowportoncanvas()) {
				append("%s@hide_graphic;\n", getTab());
			}
			if (!inst.getKey_lett().isBlank()) {
				append("%s@key_letters(\"%s\");\n", getTab(), inst.getKey_lett().strip());
			}
			if (!"Unnamed Interface".equals(informalName)) {
				append("%s@informal_name(\"%s\");\n", getTab(), informalName);
			}

			append("%s%s port %s", getTab(), c_p != null ? "provided" : "required", sanitizeName(inst.getName()));
			if (c_ir.Isformal()) {
				append(" implements %s is\n", c_ir.Interfacename()); // TODO get shortest path to the interface
				tabDepth++;

				final ProvidedOperation_c[] spr_pos = ProvidedOperation_c
						.getManySPR_POsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(c_p));
				final ProvidedSignal_c[] spr_pss = ProvidedSignal_c
						.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(c_p));
				final RequiredOperation_c[] spr_ros = RequiredOperation_c
						.getManySPR_ROsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(c_r));
				final RequiredSignal_c[] spr_rss = RequiredSignal_c
						.getManySPR_RSsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(c_r));

				if (spr_pos.length + spr_pss.length + spr_ros.length + spr_rss.length > 0) {
					append("\n");
				}

				for (ProvidedOperation_c spr_po : Stream.of(spr_pos)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_ProvidedOperation_c(spr_po, pm, writeAsProxies, isPersistable);
				}
				for (ProvidedSignal_c spr_ps : Stream.of(spr_pss)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_ProvidedSignal_c(spr_ps, pm, writeAsProxies, isPersistable);
				}
				for (RequiredOperation_c spr_ro : Stream.of(spr_ros)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_RequiredOperation_c(spr_ro, pm, writeAsProxies, isPersistable);
				}
				for (RequiredSignal_c spr_rs : Stream.of(spr_rss)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_RequiredSignal_c(spr_rs, pm, writeAsProxies, isPersistable);
				}

				tabDepth--;
				append("%send port;\n\n", getTab());
			} else {
				append(";\n");
			}

		} else {
			write_Port_c_proxy_sql(inst);
		}
	}

	private void export_PortMessage(PortMessage inst, ExecutableProperty_c c_ep, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		// message metadata
		if (inst.getSuc_pars() == Parsestatus_c.doNotParse) {
			append("%s@noparse;\n", getTab());
		}
		if (inst.getNumb() > 0) {
			append("%s@message_num(%d);\n", getTab(), inst.getNumb());
		}

		append("%s%s is\n", getTab(), getMessageSignature(c_ep, pm, writeAsProxies, isPersistable));
		tabDepth++;

		// append the inner actions
		if (!inst.getAction_semantics().isBlank()) {
			append("%s@noparse\n", getTab()); // TODO
		}
		inst.getAction_semantics().strip().lines().forEach(line -> {
			append("%s%s\n", getTab(), line);
		});
		if (!inst.getAction_semantics().isBlank()) {
			append("%s@endnoparse\n", getTab());
		}

		tabDepth--;
		append("%send message;\n\n", getTab());
	}

	@Override
	protected void export_PropertyParameter_c(PropertyParameter_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s: %s %s", inst.getName(), inst.getBy_ref() == 0 ? "in" : "out",
					getTypeReference(DataType_c.getOneS_DTOnR4007(inst), inst.getDimensions()));
		} else {
			write_PropertyParameter_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ProvidedOperation_c(ProvidedOperation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4501(ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_ProvidedOperation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ProvidedSignal_c(ProvidedSignal_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4501(ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_ProvidedSignal_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_RequiredOperation_c(RequiredOperation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4500(RequiredExecutableProperty_c.getOneSPR_REPOnR4502(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_RequiredOperation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_RequiredSignal_c(RequiredSignal_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4500(RequiredExecutableProperty_c.getOneSPR_REPOnR4502(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_RequiredSignal_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Satisfaction_c(Satisfaction_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			// satisfaction description
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			final Port_c reqPort = Port_c
					.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(Requirement_c.getOneC_ROnR4002(inst)));
			final Component_c reqComp = Component_c.getOneC_COnR4010(reqPort);
			final Port_c provPort = Port_c
					.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(Provision_c.getOneC_POnR4002(inst)));
			final Component_c provComp = Component_c.getOneC_COnR4010(provPort);

			append("%ssatisfaction is %s::%s -(o- %s::%s;\n", getTab(), sanitizeName(reqComp.getName()),
					sanitizeName(reqPort.getName()), sanitizeName(provComp.getName()),
					sanitizeName(provPort.getName()));
		} else {
			write_Satisfaction_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_SimpleAssociation_c(SimpleAssociation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final Association_c r_rel = Association_c.getOneR_RELOnR206(inst);

			// ouptut description
			r_rel.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			final String relName = String.format("relationship R%d is ", r_rel.getNumb());
			final String padding = relName.replaceAll(".", " ");

			append("%s%s", getTab(), relName);

			// get 'one' side information
			final List<ClassAsSimpleParticipant_c> r_parts = Stream
					.of(ClassAsSimpleParticipant_c.getManyR_PARTsOnR207(inst))
					.sorted(Comparator.comparing(ClassAsSimpleParticipant_c::Get_name)
							.thenComparing(ClassAsSimpleParticipant_c::getTxt_phrs))
					.collect(Collectors.toList());
			final ClassInAssociation_c oneOir = ClassInAssociation_c
					.getOneR_OIROnR203(ReferredToClassInAssoc_c.getOneR_RTOOnR204(r_parts.get(0)));
			final ModelClass_c oneClass = ModelClass_c.getOneO_OBJOnR201(oneOir);
			final ImportedClass_c oneImportedClass = ImportedClass_c.getOneO_IOBJOnR202(oneOir);
			final String oneName = oneImportedClass != null ? sanitizeName(oneImportedClass.getName())
					: sanitizeName(oneClass.getName());
			final String oneCond = r_parts.get(0).getCond() == 0 ? "unconditionally" : "conditionally";
			final String oneMult = r_parts.get(0).getMult() == 0 ? "one" : "many";
			final String onePhrase = r_parts.get(0).getTxt_phrs().isBlank() ? "relates to"
					: sanitizeName(r_parts.get(0).getTxt_phrs());

			// get 'other' side information
			String otherName;
			String otherCond;
			String otherMult;
			String otherPhrase;
			if (r_parts.size() > 1) {
				final ClassInAssociation_c otherOir = ClassInAssociation_c
						.getOneR_OIROnR203(ReferredToClassInAssoc_c.getOneR_RTOOnR204(r_parts.get(1)));
				final ModelClass_c otherClass = ModelClass_c.getOneO_OBJOnR201(otherOir);
				final ImportedClass_c otherImportedClass = ImportedClass_c.getOneO_IOBJOnR202(otherOir);
				otherName = otherImportedClass != null ? sanitizeName(otherImportedClass.getName())
						: sanitizeName(otherClass.getName());
				otherCond = r_parts.get(1).getCond() == 0 ? "unconditionally" : "conditionally";
				otherMult = r_parts.get(1).getMult() == 0 ? "one" : "many";
				otherPhrase = r_parts.get(1).getTxt_phrs().isBlank() ? "relates to"
						: sanitizeName(r_parts.get(1).getTxt_phrs());
			} else {
				final ClassAsSimpleFormalizer_c r_form = ClassAsSimpleFormalizer_c.getOneR_FORMOnR208(inst);
				final ClassInAssociation_c otherOir = ClassInAssociation_c
						.getOneR_OIROnR203(ReferringClassInAssoc_c.getOneR_RGOOnR205(r_form));
				final ModelClass_c otherClass = ModelClass_c.getOneO_OBJOnR201(otherOir);
				final ImportedClass_c otherImportedClass = ImportedClass_c.getOneO_IOBJOnR202(otherOir);
				otherName = otherImportedClass != null ? sanitizeName(otherImportedClass.getName())
						: sanitizeName(otherClass.getName());
				otherCond = r_form.getCond() == 0 ? "unconditionally" : "conditionally";
				otherMult = r_form.getMult() == 0 ? "one" : "many";
				otherPhrase = r_form.getTxt_phrs().isBlank() ? "relates to" : sanitizeName(r_form.getTxt_phrs());
			}

			append("%s %s %s %s %s,\n", oneName, otherCond, otherPhrase, otherMult, otherName);
			append("%s%s%s %s %s %s %s;\n\n", getTab(), padding, otherName, oneCond, onePhrase, oneMult, oneName);
		} else {
			write_SimpleAssociation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_StateMachine_c(StateMachine_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%swithin %s is\n\n", getTab(),
					sanitizePath(metadata.getParent(metadata.getParent(inst)).getPath()));
			tabDepth++;

			final boolean isClassBased = ClassStateMachine_c.getOneSM_ASMOnR517(inst) != null;

			// declare states
			final StateMachineState_c[] sm_states = StateMachineState_c.getManySM_STATEsOnR501(inst);
			for (StateMachineState_c sm_state : Stream.of(sm_states)
					.sorted(Comparator.comparing(StateMachineState_c::getNumb)).collect(Collectors.toList())) {

				// state metadata
				append("%s@state_num(%d);\n", getTab(), sm_state.getNumb());
				if (sm_state.getFinal() != 0) {
					append("%s@final;\n", getTab());
				}

				append("%s%sstate %s;\n\n", getTab(), isClassBased ? "class " : "", sanitizeName(sm_state.getName()));

			}

			// events
			final StateMachineEvent_c[] sm_evts = StateMachineEvent_c.getManySM_EVTsOnR502(inst,
					selected -> NonLocalEvent_c.getOneSM_NLEVTOnR526(
							SemEvent_c.getOneSM_SEVTOnR525((StateMachineEvent_c) selected)) == null);
			for (StateMachineEvent_c sm_evt : Stream.of(sm_evts)
					.sorted(Comparator.comparing(StateMachineEvent_c::getNumb)).collect(Collectors.toList())) {
				export_StateMachineEvent_c(sm_evt, pm, writeAsProxies, isPersistable);
			}

			// transition table
			final Transition_c[] txns = Transition_c.getManySM_TXNsOnR505(inst);
			if (txns.length > 0) {
				append("%s%sstate machine is\n\n", getTab(), isClassBased ? "class " : "");
				tabDepth++;

				// TODO signal events
				// TODO individual cell descriptions
				// TODO no event transition

				// get events and start states
				final List<StateMachineEvent_c> semEvents = Stream
						.of(StateMachineEvent_c.getManySM_EVTsOnR525(
								SemEvent_c.getManySM_SEVTsOnR525(StateMachineEvent_c.getManySM_EVTsOnR502(inst))))
						.sorted(Comparator.comparing(StateMachineEvent_c::Islocal).reversed()
								.thenComparing(StateMachineEvent_c::Getclassname)
								.thenComparing(StateMachineEvent_c::getNumb))
						.collect(Collectors.toList());
				final List<StateMachineState_c> startStates = Stream.of(sm_states)
						.sorted(Comparator.comparing(StateMachineState_c::getNumb)).collect(Collectors.toList());

				// create a matrix of strings to hold the transitions
				final List<List<String>> matrix = new ArrayList<>();

				// create header row (events)
				matrix.add(Stream
						.concat(Stream.of(""),
								semEvents.stream().map(evt -> evt.Islocal() ? sanitizeName(evt.getMning())
										: sanitizeName(evt.Getclassname()) + "::" + sanitizeName(evt.getMning())))
						.collect(Collectors.toList()));

				// create a row for creation transitions (if they exist)
				final CreationTransition_c[] crTxns = CreationTransition_c.getManySM_CRTXNsOnR507(txns);
				if (crTxns.length > 0) {
					matrix.add(Stream.concat(Stream.of("non_existent"), semEvents.stream().map(evt -> {
						final StateMachineState_c destState = StateMachineState_c.getOneSM_STATEOnR506(
								Transition_c.getOneSM_TXNOnR507(CreationTransition_c.getOneSM_CRTXNOnR509(
										LocalEvent_c.getOneSM_LEVTOnR526(SemEvent_c.getOneSM_SEVTOnR525(evt)))));
						return destState != null ? sanitizeName(destState.getName()) : "cannot_happen";
					})).collect(Collectors.toList()));
				}

				// create a row for each start state
				for (StateMachineState_c startState : startStates) {
					matrix.add(
							Stream.concat(Stream.of(sanitizeName(startState.getName())), semEvents.stream().map(evt -> {
								final StateEventMatrixEntry_c seme = StateEventMatrixEntry_c.getOneSM_SEMEOnR503(
										SemEvent_c.getOneSM_SEVTOnR525(evt),
										selected -> ((StateEventMatrixEntry_c) selected).getSmstt_id()
												.equals(startState.getSmstt_id()));
								final StateMachineState_c destState = StateMachineState_c
										.getOneSM_STATEOnR506(Transition_c
												.getOneSM_TXNOnR507(NewStateTransition_c.getOneSM_NSTXNOnR504(seme)));
								final EventIgnored_c ei = EventIgnored_c.getOneSM_EIGNOnR504(seme);
								return destState != null ? sanitizeName(destState.getName())
										: (ei != null ? "ignore" : "cannot_happen");
							})).collect(Collectors.toList()));
				}

				// get the maximum cell width
				final int maxWidth = Integer.max(matrix.stream().flatMap(row -> row.stream()).map(s -> s.length())
						.max(Integer::compareTo).orElse(0), 3);

				// add a divider row
				matrix.add(1,
						Stream.generate(() -> Stream.generate(() -> "-").limit(maxWidth).collect(Collectors.joining()))
								.limit(semEvents.size() + 1).collect(Collectors.toList()));

				// format the matrix
				for (List<String> row : matrix) {
					append("%s| %s |\n", getTab(), row.stream().map(s -> String.format("%-" + maxWidth + "s", s))
							.collect(Collectors.joining(" | ")));
				}

				tabDepth--;
				append("\n%send state machine;\n\n", getTab());
			}

			// state actions
			final Action_c[] actions = Action_c.getManySM_ACTsOnR515(inst,
					selected -> !((Action_c) selected).getAction_semantics().isBlank());
			for (StateMachineState_c state : Stream.of(actions)
					.map(act -> StateMachineState_c.getOneSM_STATEOnR511(
							MooreActionHome_c.getOneSM_MOAHOnR513(ActionHome_c.getOneSM_AHOnR514(act))))
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(StateMachineState_c::getNumb)).collect(Collectors.toList())) {
				export_StateMachineState_c(state, pm, writeAsProxies, isPersistable);
			}

			// transition actions
			for (Transition_c txn : Stream.of(actions)
					.map(act -> Transition_c.getOneSM_TXNOnR530(
							TransitionActionHome_c.getOneSM_TAHOnR513(ActionHome_c.getOneSM_AHOnR514(act))))
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(txn -> {
						final StateMachineState_c startState = StateMachineState_c
								.getOneSM_STATEOnR503(StateEventMatrixEntry_c.getOneSM_SEMEOnR504(
										NewStateTransition_c.getOneSM_NSTXNOnR507((Transition_c) txn)));
						return startState != null ? startState.getName() : "";
					}).thenComparing(txn -> {
						StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR525(
								SemEvent_c.getOneSM_SEVTOnR503(StateEventMatrixEntry_c.getOneSM_SEMEOnR504(
										NewStateTransition_c.getOneSM_NSTXNOnR507((Transition_c) txn))));
						if (evt == null) {
							evt = StateMachineEvent_c
									.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR526(LocalEvent_c.getOneSM_LEVTOnR509(
											CreationTransition_c.getOneSM_CRTXNOnR507((Transition_c) txn))));
						}
						return evt.getNumb();
					})).collect(Collectors.toList())) {
				export_Transition_c(txn, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send;\n", getTab());
		} else {
			write_StateMachine_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_StateMachineEvent_c(StateMachineEvent_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final boolean isClassBased = ClassStateMachine_c
					.getOneSM_ASMOnR517(StateMachine_c.getOneSM_SMOnR502(inst)) != null;

			// event metadata
			inst.getDescrip().strip().lines().forEach(line -> append("%s//! %s\n", getTab(), line));
			append("%s@event_num(%d);\n", getTab(), inst.getNumb());
			if (inst.getIs_lbl_u() != 0 && !inst.getUnq_lbl().isBlank()) {
				append("%s@key_letters(\"%s\"", getTab(), inst.getUnq_lbl().strip());
			}

			append("%s%sevent %s", getTab(), isClassBased ? "class " : "", sanitizeName(inst.getMning()));

			// build the parameter list
			final StateMachineEventDataItem_c[] sm_evtdis = StateMachineEventDataItem_c.getManySM_EVTDIsOnR532(inst);
			new StateMachineEventDataItem_cSorter().sort(sm_evtdis);
			final String parameterList = Stream.of(sm_evtdis).map(sm_evtdi -> {
				buffers.push(new StringBuilder());
				try {
					export_StateMachineEventDataItem_c(sm_evtdi, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));
			if (!parameterList.isBlank()) {
				append("(%s)", parameterList);
			}

			append(";\n\n");

		} else {
			write_StateMachineEvent_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_StateMachineEventDataItem_c(StateMachineEventDataItem_c inst, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s: in %s", inst.getName(),
					getTypeReference(DataType_c.getOneS_DTOnR524(inst), inst.getDimensions()));
		} else {
			write_StateMachineEventDataItem_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_StateMachineState_c(StateMachineState_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final boolean isClassBased = ClassStateMachine_c
					.getOneSM_ASMOnR517(StateMachine_c.getOneSM_SMOnR501(inst)) != null;

			final Action_c act = Action_c
					.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(MooreActionHome_c.getOneSM_MOAHOnR511(inst)));
			act.getDescrip().strip().lines().forEach(line -> append("%s//! %s\n", getTab(), line));

			append("%s%sstate %s", getTab(), isClassBased ? "class " : "", sanitizeName(inst.getName()));

			// build parameter list
			StateMachineEvent_c evt = StateMachineEvent_c
					.getOneSM_EVTOnR525(SemEvent_c.getManySM_SEVTsOnR503(StateEventMatrixEntry_c.getManySM_SEMEsOnR504(
							NewStateTransition_c.getManySM_NSTXNsOnR507(Transition_c.getManySM_TXNsOnR506(inst)))));
			if (evt == null) {
				evt = StateMachineEvent_c
						.getOneSM_EVTOnR525(SemEvent_c.getManySM_SEVTsOnR526(LocalEvent_c.getManySM_LEVTsOnR509(
								CreationTransition_c.getManySM_CRTXNsOnR507(Transition_c.getManySM_TXNsOnR506(inst)))));
			}
			final StateMachineEventDataItem_c[] sm_evtdis = StateMachineEventDataItem_c.getManySM_EVTDIsOnR532(evt);
			new StateMachineEventDataItem_cSorter().sort(sm_evtdis);
			final String parameterList = Stream.of(sm_evtdis).map(sm_evtdi -> {
				buffers.push(new StringBuilder());
				try {
					export_StateMachineEventDataItem_c(sm_evtdi, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));
			if (!parameterList.isBlank()) {
				append("(%s)", parameterList);
			}

			// append inner actions
			append(" is\n");
			tabDepth++;
			append("%s@noparse\n", getTab());
			act.getAction_semantics().strip().lines().forEach(line -> {
				append("%s%s\n", getTab(), line);
			});
			append("%s@endnoparse\n", getTab());
			tabDepth--;
			append("%send state;\n\n", getTab());
		} else {
			write_StateMachineState_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_StructuredDataType_c(StructuredDataType_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final DataType_c dt = DataType_c.getOneS_DTOnR17(inst);

			// export description
			dt.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			append("%stype %s is structure\n", getTab(), sanitizeName(dt.getName()));
			tabDepth++;

			// process members
			final StructureMember_c[] mbrs = StructureMember_c.getManyS_MBRsOnR44(inst);
			new StructureMember_cSorter().sort(mbrs);
			for (StructureMember_c mbr : mbrs) {
				export_StructureMember_c(mbr, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send structure;\n\n", getTab());
		} else {
			write_StructuredDataType_c_proxy_sql(inst);

		}
	}

	@Override
	protected void export_StructureMember_c(StructureMember_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// export description
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			final DataType_c type = DataType_c.getOneS_DTOnR45(inst);
			append("%s%s: %s;\n", getTab(), sanitizeName(inst.getName()), getTypeReference(type, inst.getDimensions()));

		} else {
			write_StructureMember_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_SubtypeSupertypeAssociation_c(SubtypeSupertypeAssociation_c inst, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final Association_c r_rel = Association_c.getOneR_RELOnR206(inst);

			// ouptut description
			r_rel.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			// get superclass information
			final ClassAsSupertype_c r_sup = ClassAsSupertype_c.getOneR_SUPEROnR212(inst);
			final ClassInAssociation_c supOir = ClassInAssociation_c
					.getOneR_OIROnR203(ReferredToClassInAssoc_c.getOneR_RTOOnR204(r_sup));
			final ModelClass_c supClass = ModelClass_c.getOneO_OBJOnR201(supOir);
			final ImportedClass_c supImportedClass = ImportedClass_c.getOneO_IOBJOnR202(supOir);
			final String supName = supImportedClass != null ? sanitizeName(supImportedClass.getName())
					: sanitizeName(supClass.getName());

			// get subclass information
			final ClassAsSubtype_c[] r_subs = ClassAsSubtype_c.getManyR_SUBsOnR213(inst);
			final String subtypeList = Stream.of(r_subs).map(r_sub -> {
				final ClassInAssociation_c subOir = ClassInAssociation_c
						.getOneR_OIROnR203(ReferringClassInAssoc_c.getOneR_RGOOnR205(r_sub));
				final ModelClass_c subClass = ModelClass_c.getOneO_OBJOnR201(subOir);
				final ImportedClass_c subImportedClass = ImportedClass_c.getOneO_IOBJOnR202(subOir);
				return subImportedClass != null ? sanitizeName(subImportedClass.getName())
						: sanitizeName(subClass.getName());
			}).sorted().collect(Collectors.joining(", "));

			append("%srelationship R%d is %s is_a (%s);\n\n", getTab(), r_rel.getNumb(), supName, subtypeList);
		} else {
			write_SubtypeSupertypeAssociation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_SystemModel_c(SystemModel_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s@system_model(use_globals=%s);\n", getTab(), inst.getUseglobals());
			append("%spackage %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			final Package_c[] ep_pkgs = Package_c.getManyEP_PKGsOnR1401(inst);
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}
			// TODO support package references here as well
			Stream.of(ep_pkgs).sorted(Comparator.comparing(NonRootModelElement::getName)).forEach(ep_pkg -> {
				append("%spackage %s;\n", getTab(), sanitizeName(ep_pkg.getName()));
			});
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}

			tabDepth--;
			append("%send package;\n\n", getTab());

		} else {
			super.export_SystemModel_c(inst, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_Transition_c(Transition_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final StateMachineState_c startState = StateMachineState_c.getOneSM_STATEOnR503(
					StateEventMatrixEntry_c.getOneSM_SEMEOnR504(NewStateTransition_c.getOneSM_NSTXNOnR507(inst)));
			final StateMachineState_c destState = StateMachineState_c.getOneSM_STATEOnR506(inst);
			StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR503(
					StateEventMatrixEntry_c.getOneSM_SEMEOnR504(NewStateTransition_c.getOneSM_NSTXNOnR507(inst))));
			if (evt == null) {
				evt = StateMachineEvent_c.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR526(
						LocalEvent_c.getOneSM_LEVTOnR509(CreationTransition_c.getOneSM_CRTXNOnR507(inst))));

			}

			final boolean isClassBased = ClassStateMachine_c
					.getOneSM_ASMOnR517(StateMachine_c.getOneSM_SMOnR505(inst)) != null;

			append("%s%stransition %s [%s] => %s", getTab(), isClassBased ? "class " : "",
					startState != null ? sanitizeName(startState.getName()) : "non_existent",
					sanitizeName(evt.getMning()), sanitizeName(destState.getName()));

			// build parameter list
			final StateMachineEventDataItem_c[] sm_evtdis = StateMachineEventDataItem_c.getManySM_EVTDIsOnR532(evt);
			new StateMachineEventDataItem_cSorter().sort(sm_evtdis);
			final String parameterList = Stream.of(sm_evtdis).map(sm_evtdi -> {
				buffers.push(new StringBuilder());
				try {
					export_StateMachineEventDataItem_c(sm_evtdi, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));
			if (!parameterList.isBlank()) {
				append("(%s)", parameterList);
			}

			// append inner actions
			final Action_c act = Action_c.getOneSM_ACTOnR514(
					ActionHome_c.getOneSM_AHOnR513(TransitionActionHome_c.getOneSM_TAHOnR530(inst)));
			append(" is\n");
			tabDepth++;
			append("%s@noparse\n", getTab());
			act.getAction_semantics().strip().lines().forEach(line -> {
				append("%s%s\n", getTab(), line);
			});
			append("%s@endnoparse\n", getTab());
			tabDepth--;
			append("%send transition;\n\n", getTab());

		} else {
			write_Transition_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_UserDataType_c(UserDataType_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			final DataType_c dt = DataType_c.getOneS_DTOnR17(inst);

			// export description
			dt.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			// if the 'definition' field is populated, use it and ignore everything else
			if (!inst.getDefinition().isBlank()) {
				append("%s@raw_definition\n", getTab());
				append("%stype %s is ", getTab(), sanitizeName(dt.getName()));
				final String typeDef = inst.getDefinition().strip().lines()
						.collect(Collectors.joining(String.format("\n%s", getTab())));
				append("%s;\n\n", typeDef);
			} else {
				final DataType_c type = DataType_c.getOneS_DTOnR18(inst);
				append("%stype %s is %s", getTab(), sanitizeName(dt.getName()), getTypeReference(type));
				final Range_c range = Range_c.getOneS_RANGEOnR57(inst);
				if (range != null) {
					append(" range %s .. %s", range.getMin(), range.getMax());
				}
				append(";\n\n");
			}
		} else {
			write_UserDataType_c_proxy_sql(inst);
		}
	}

	@Override
	public String get_file_header(NonRootModelElement element) {
		return get_file_header("//",
				element.getClass().getSimpleName().substring(0, element.getClass().getSimpleName().length() - 2),
				"7.1.6", org.xtuml.bp.core.CorePlugin.getPersistenceVersion());
	}

	private String getMessageSignature(final ExecutableProperty_c c_ep, final IProgressMonitor pm,
			final boolean writeAsProxies, final boolean isPersistable) {

		// build the parameter list
		final PropertyParameter_c[] c_pps = PropertyParameter_c.getManyC_PPsOnR4006(c_ep);
		new PropertyParameter_cSorter().sort(c_pps);
		final String parameterList = Stream.of(c_pps).map(c_pp -> {
			buffers.push(new StringBuilder());
			try {
				export_PropertyParameter_c(c_pp, pm, writeAsProxies, isPersistable);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			return buffers.pop().toString();
		}).collect(Collectors.joining(", "));

		// get the return type if applicable
		final DataType_c returnType = DataType_c.getOneS_DTOnR4008(InterfaceOperation_c.getOneC_IOOnR4004(c_ep));
		final String returnTypeRef = (returnType != null
				&& !"ba5eda7a-def5-0000-0000-000000000000".equals(returnType.getDt_id().toString()))
						? " return " + getTypeReference(returnType,
								InterfaceOperation_c.getOneC_IOOnR4004(c_ep).getReturn_dimensions())
						: "";

		// get the message direction
		final String direction = c_ep.getDirection() == Ifdirectiontype_c.ClientServer ? "to provider"
				: "from provider";

		return String.format("message %s(%s)%s %s", c_ep.getName(), parameterList, returnTypeRef, direction);
	}

	private String getTab() {
		final IPreferenceStore store = EditorsUI.getPreferenceStore();
		final boolean spacesForTabs = store
				.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
		final String tabChar = spacesForTabs ? " " : "\t";
		final int tabWidth = spacesForTabs
				? store.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH)
				: 1;
		String tab = "";
		for (int i = 0; i < tabDepth * tabWidth; i++) {
			tab += tabChar;
		}
		return tab;
	}

	private String getTypeReference(final DataType_c type) {
		return getTypeReference(type, "");
	}

	private String getTypeReference(final DataType_c type, final String dims) {
		String type_ref = "";
		Matcher m = Pattern.compile("\\[([^\\]]*)\\]").matcher(dims);
		while (m.find()) {
			type_ref += String.format("sequence%s of ", !m.group(1).isBlank() ? " (" + m.group(1) + ")" : "");
		}
		final InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c.getOneS_IRDTOnR17(type);
		if (irdt != null) {
			final ModelClass_c obj = ModelClass_c.getOneO_OBJOnR123(irdt);
			if (irdt.getIsset()) {
				type_ref += "set of ";
			}
			type_ref += "instance of " + sanitizeName(obj.getName()); // TODO get shortest scoped name
		} else if ("ba5eda7a-def5-0000-0000-00000000000f".equals(type.getDt_id().toString())) {
			type_ref += "timer";
		} else {
			type_ref += sanitizeName(type.getName()); // TODO get shortest scoped name
		}
		return type_ref;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException {
		super.run(monitor);
		// write textual graphics if this is a root that contains a diagram
		if (m_inst instanceof SystemModel_c || m_inst instanceof Component_c || m_inst instanceof InstanceStateMachine_c
				|| m_inst instanceof ClassStateMachine_c || m_inst instanceof Package_c) {
			CanvasPlugin.getDefault().getPersistenceExtensionRegistry().getExtensions()
					.forEach(extension -> extension.getWriter().write(m_inst));
		}
	}

	private String sanitizeName(final String name) {
		if (name.matches(".*\\s.*")) {
			return "'" + name.replaceAll("\\s+", " ") + "'";
		} else {
			return name;
		}
	}

	private String sanitizePath(final String path) {
		return Stream.of(path.split("::")).map(this::sanitizeName).collect(Collectors.joining("::"));
	}

}
