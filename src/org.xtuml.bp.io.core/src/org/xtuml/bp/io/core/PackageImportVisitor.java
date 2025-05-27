package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.ClassAsSubtype_c;
import org.xtuml.bp.core.ClassAsSupertype_c;
import org.xtuml.bp.core.ClassInAssociation_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.Exception_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Implementationscope_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.ImportedProvision_c;
import org.xtuml.bp.core.ImportedReference_c;
import org.xtuml.bp.core.ImportedRequirement_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.PackageReference_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.PortReference_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.Range_c;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.Satisfaction_c;
import org.xtuml.bp.core.ServiceInSequence_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.StructureMember_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TerminatorServiceParameter_c;
import org.xtuml.bp.core.TerminatorServiceSequence_c;
import org.xtuml.bp.core.TerminatorService_c;
import org.xtuml.bp.core.Terminator_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.Bridge_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Class_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Component_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Component_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Constant_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Constant_group_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Enumeration_type_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.EnumeratorContext;
import org.xtuml.bp.io.core.XtumlParser.Exception_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.External_entity_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Function_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Interface_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Linked_relationship_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Message_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.NameContext;
import org.xtuml.bp.io.core.XtumlParser.Named_type_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Package_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.Port_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Satisfaction_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Simple_relationship_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Structure_memberContext;
import org.xtuml.bp.io.core.XtumlParser.Structure_type_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Subsuper_relationship_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Type_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Type_forward_declarationContext;

public class PackageImportVisitor extends XtumlImportVisitor {

	private SortedSet<SequenceService> sequenceServices;

	public PackageImportVisitor(Ooaofooa modelRoot) {
		super(modelRoot);
	}

	@Override
	public NonRootModelElement visitPackage_definition(Package_definitionContext ctx) {
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (!marks.containsKey(DEPLOYMENT)) {

			// find or create package
			final String pkgName = visitName(ctx.pkg_name);
			final Package_c pkg = findOrCreate(Package_c.class, currentRoot.getPath() + "::" + pkgName);
			pkg.setName(pkgName);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(pkg);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.PACKAGE);

			// process marks
			if (marks.containsKey(NUMBER_RANGE)) {
				pkg.setNum_rng(marks.get(NUMBER_RANGE).getInteger());
			}

			// set description
			if (ctx.description() != null) {
				pkg.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
						.collect(Collectors.joining(System.lineSeparator())));
			}

			// link to parent
			if (currentRoot instanceof Package_c) {
				pe.relateAcrossR8000To((Package_c) currentRoot);
				try {
					final SystemModel_c s_sys = executor
							.callAndWaitNullable(() -> SystemModel_c.getOneS_SYSOnR1405((Package_c) currentRoot));
					pkg.relateAcrossR1405To(s_sys);
				} catch (Exception e) {
					throw new CoreImport.XtumlLoadException("Could not find system model for package: " + pkgName, e);
				}
			} else if (currentRoot instanceof Component_c) {
				pe.relateAcrossR8003To((Component_c) currentRoot);
				try {
					final SystemModel_c s_sys = executor.callAndWaitNullable(() -> SystemModel_c
							.getOneS_SYSOnR1405(Package_c.PackageInstance(modelRoot, selected -> ((Package_c) selected)
									.getPackage_id().equals(((Component_c) currentRoot).Getpackageid()))));
					pkg.relateAcrossR1405To(s_sys);
				} catch (Exception e) {
					throw new CoreImport.XtumlLoadException("Could not find system model for package: " + pkgName, e);
				}
			} else if (currentRoot instanceof SystemModel_c) {
				pkg.relateAcrossR1401To((SystemModel_c) currentRoot);
				pkg.relateAcrossR1405To((SystemModel_c) currentRoot);
			}
			currentRoot = pkg;
			searchRoot = pkg;
			
			// if this is a package reference, link it up
			if (ctx.ref_name != null) {

				final String targetPkgPath = visitScoped_name(ctx.ref_name);
				final Package_c targetPkg;
				try {
					targetPkg = executor.callAndWait(() -> searchByPath(Elementtypeconstants_c.PACKAGE, targetPkgPath,
							Package_c::getOneEP_PKGOnR8001));
				} catch (Exception e) {
					throw new CoreImport.XtumlLoadException("Failed to find referred to package '" + targetPkgPath + "'.", e);
				}
				final PackageReference_c pkgRef = new PackageReference_c(modelRoot);
				pkgRef.relateAcrossR1402ToIsReferencedBy(pkg);
				pkgRef.relateAcrossR1402ToRefersTo(targetPkg);

			} else {

				// process all package items
				ctx.package_item().forEach(this::visit);

			}

			return pkg;

		} else {
			final Package_c parentPkg = (Package_c) currentRoot;

			// find or create deployment
			final var deploymentName = visitName(ctx.pkg_name);
			final var deployment = findOrCreate(Deployment_c.class, parentPkg.getPath() + "::" + deploymentName);
			deployment.setName(deploymentName);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(deployment);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.DEPL);

			// set description
			if (ctx.description() != null) {
				deployment.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
						.collect(Collectors.joining(System.lineSeparator())));
			}

			// process marks
			if (marks.containsKey(KEY_LETTERS)) {
				deployment.setKey_lett(marks.get(KEY_LETTERS).getString());
			}

			// process terminators
			currentRoot = deployment;
			ctx.package_item().forEach(this::visit);
			currentRoot = parentPkg;

			// link to parent package
			pe.relateAcrossR8000To(parentPkg);

			return deployment;
		}
	}

	@Override
	public DataType_c visitType_forward_declaration(Type_forward_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create type
		final String typeName = visitName(ctx.type_name);
		final DataType_c type = findOrCreate(DataType_c.class, parentPkg.getPath() + "::" + typeName, true);
		type.setName(typeName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(type);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.DATATYPE);

		// create a UDT that will get removed later when the type is formally defined
		final UserDataType_c udt = new UserDataType_c(modelRoot);
		udt.relateAcrossR17To(type);
		udt.relateAcrossR18To(this.integerType);

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

		return type;
	}

	@Override
	public DataType_c visitType_declaration(Type_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create type
		final String typeName = visitName(ctx.type_name);
		final DataType_c type = findOrCreate(DataType_c.class, parentPkg.getPath() + "::" + typeName, true);
		type.setName(typeName);
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(type);
		if (pe != null) {
			// if the type was predeclared, remove the dummy UDT
			final UserDataType_c dummyUDT = UserDataType_c.getOneS_UDTOnR17(type);
			type.unrelateAcrossR17From(dummyUDT);
			dummyUDT.Dispose();
		} else {
			pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(type);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.DATATYPE);
		}

		// set description
		if (ctx.description() != null) {
			type.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(RAW_TYPE_DEF)) {
			// create user type
			final UserDataType_c udt = new UserDataType_c(modelRoot);
			udt.relateAcrossR17To(type);

			// link to default type
			udt.relateAcrossR18To(this.integerType);

			// set definition
			// TODO figure out spacing
			udt.setDefinition(getRuleText(ctx.type_definition()));
		} else {

			// process subtype
			currentRoot = type;
			visit(ctx.type_definition());
			currentRoot = parentPkg;
		}

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

		type.setDeclarationOnly(false);
		return type;
	}

	@Override
	public StructuredDataType_c visitStructure_type_definition(Structure_type_definitionContext ctx) {
		final DataType_c type = (DataType_c) currentRoot;

		// create structured type
		final StructuredDataType_c sdt = new StructuredDataType_c(modelRoot);
		sdt.relateAcrossR17To(type);

		// process members
		currentRoot = sdt;
		StructureMember_c prevMbr = null;
		for (Structure_memberContext mbrCtx : ctx.structure_member()) {
			final StructureMember_c mbr = visitStructure_member(mbrCtx);
			if (prevMbr != null) {
				prevMbr.relateAcrossR46ToPrecedes(mbr);
			}
			prevMbr = mbr;
		}
		currentRoot = type;

		return sdt;
	}

	@Override
	public StructureMember_c visitStructure_member(Structure_memberContext ctx) {
		// create new member
		final StructureMember_c mbr = new StructureMember_c(modelRoot);
		mbr.relateAcrossR44To((StructuredDataType_c) currentRoot);
		mbr.setName(visitName(ctx.member_name));

		// set description
		if (ctx.description() != null) {
			mbr.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// link the data type
		mbr.relateAcrossR45To((DataType_c) visit(ctx.type_reference()));

		// set the array dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		mbr.setDimensions(dimString);
		final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, mbr);
		for (int i = 0; i < dims.size(); i++) {
			mbr.Resizedimensions(i, dims.get(i), dims.size());
		}

		return mbr;
	}

	@Override
	public EnumerationDataType_c visitEnumeration_type_definition(Enumeration_type_definitionContext ctx) {
		final DataType_c type = (DataType_c) currentRoot;

		// create enumeration type
		final EnumerationDataType_c edt = new EnumerationDataType_c(modelRoot);
		edt.relateAcrossR17To(type);

		// process enums
		currentRoot = edt;
		Enumerator_c prevEnum = null;
		for (EnumeratorContext enumCtx : ctx.enumerator()) {
			final Enumerator_c s_enum = visitEnumerator(enumCtx);
			if (prevEnum != null) {
				prevEnum.relateAcrossR56ToPrecedes(s_enum);
			}
			prevEnum = s_enum;
		}
		currentRoot = type;

		return edt;
	}

	@Override
	public Enumerator_c visitEnumerator(EnumeratorContext ctx) {
		// create new enumerator
		final Enumerator_c s_enum = new Enumerator_c(modelRoot);
		s_enum.relateAcrossR27To((EnumerationDataType_c) currentRoot);
		s_enum.setName(visitName(ctx.enum_name));

		// set description
		if (ctx.description() != null) {
			s_enum.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		return s_enum;
	}

	@Override
	public UserDataType_c visitNamed_type_definition(Named_type_definitionContext ctx) {
		final DataType_c type = (DataType_c) currentRoot;

		// create user type
		final UserDataType_c udt = new UserDataType_c(modelRoot);
		udt.relateAcrossR17To(type);

		// link the data type
		udt.relateAcrossR18To((DataType_c) visit(ctx.type_reference()));

		if (ctx.min != null && ctx.max != null) {
			// create the range
			final Range_c rng = new Range_c(modelRoot);
			rng.relateAcrossR57To(udt);
			rng.setMin(visit(ctx.min).toString());
			rng.setMax(visit(ctx.max).toString());
		}

		return udt;
	}

	@Override
	public Exception_c visitException_definition(Exception_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create exception
		final String exceptionName = visitName(ctx.exception_name);
		final Exception_c exception = findOrCreate(Exception_c.class, parentPkg.getPath() + "::" + exceptionName);
		exception.setName(exceptionName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(exception);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.EXCEPTION);

		// set description
		if (ctx.description() != null) {
			exception.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

		return exception;
	}

	@Override
	public ConstantSpecification_c visitConstant_group_definition(Constant_group_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create constant group
		final String constantGroupName = visitName(ctx.group_name);
		final ConstantSpecification_c constantGroup = findOrCreate(ConstantSpecification_c.class,
				parentPkg.getPath() + "::" + constantGroupName);
		constantGroup.setInformalgroupname(constantGroupName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(constantGroup);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.CONSTANT);

		// set description
		if (ctx.description() != null) {
			constantGroup.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process individual constants
		currentRoot = constantGroup;
		SymbolicConstant_c prevCnst = null;
		for (Constant_definitionContext constCtx : ctx.constant_definition()) {
			final SymbolicConstant_c cnst = visitConstant_definition(constCtx);
			if (prevCnst != null) {
				prevCnst.relateAcrossR1505ToPrecedes(cnst);
			}
			prevCnst = cnst;
		}
		currentRoot = parentPkg;

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

		return constantGroup;
	}

	@Override
	public SymbolicConstant_c visitConstant_definition(Constant_definitionContext ctx) {
		final ConstantSpecification_c constantGroup = (ConstantSpecification_c) currentRoot;

		// find or create constant
		final String constantName = visitName(ctx.constant_name);
		final SymbolicConstant_c constant = findOrCreate(SymbolicConstant_c.class,
				constantGroup.getPath() + "::" + constantName);
		constant.setName(constantName);

		// set description
		if (ctx.description() != null) {
			constant.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// link type
		constant.relateAcrossR1500To((DataType_c) visit(ctx.type_reference()));

		// create subtypes
		final LeafSymbolicConstant_c lfsc = new LeafSymbolicConstant_c(modelRoot);
		lfsc.relateAcrossR1502To(constant);
		final LiteralSymbolicConstant_c lsc = new LiteralSymbolicConstant_c(modelRoot);
		lsc.relateAcrossR1503To(lfsc);

		// set value
		lsc.setValue(visit(ctx.const_expression()).toString());

		// relate to parent group
		constant.relateAcrossR1504To(constantGroup);

		return constant;
	}

	@Override
	public Interface_c visitInterface_declaration(Interface_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;
		final String ifaceName = visitName(ctx.iface_name);
		final Interface_c iface = findOrCreate(Interface_c.class, parentPkg.getPath() + "::" + ifaceName, true);
		iface.setName(ifaceName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(iface);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.INTERFACE);
		pe.relateAcrossR8000To(parentPkg);
		return iface;
	}

	@Override
	public ExternalEntity_c visitExternal_entity_definition(External_entity_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create external entity
		final String eeName = visitName(ctx.ee_name);
		final ExternalEntity_c ee = findOrCreate(ExternalEntity_c.class, parentPkg.getPath() + "::" + eeName);
		ee.setName(eeName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(ee);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.EE);

		// set description
		if (ctx.description() != null) {
			ee.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(KEY_LETTERS)) {
			ee.setKey_lett(marks.get(KEY_LETTERS).getString());
		}
		if (marks.containsKey(REALIZED)) {
			ee.setIsrealized(true);
			if (marks.get(REALIZED).containsKey("classpath")) {
				ee.setRealized_class_path(marks.get(REALIZED).getString("classpath"));
			}
		}

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

		// process bridges
		currentRoot = ee;
		ctx.bridge_definition().forEach(this::visit);
		currentRoot = parentPkg;

		return ee;
	}

	@Override
	public Bridge_c visitBridge_definition(Bridge_definitionContext ctx) {
		final ExternalEntity_c ee = (ExternalEntity_c) currentRoot;

		// find or create bridge
		final String bridgeName = visitName(ctx.brg_name);
		final Bridge_c bridge = findOrCreate(Bridge_c.class, ee.getPath() + "::" + bridgeName);
		bridge.setName(bridgeName);

		// set description
		if (ctx.description() != null) {
			bridge.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();

		// set return type
		if (ctx.type_reference() != null) {
			bridge.relateAcrossR20To((DataType_c) visit(ctx.type_reference()));

			// set the return array dimensions
			final String dimString = getDimString(ctx.type_reference().array_type_reference());
			bridge.setReturn_dimensions(dimString);
			final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, bridge);
			for (int i = 0; i < dims.size(); i++) {
				bridge.Resizereturn_dimensions(i, dims.get(i), dims.size());
			}
		} else {
			bridge.relateAcrossR20To(voidType);
		}

		// handle parameters
		if (ctx.parameter_list() != null) {
			currentRoot = bridge;
			visit(ctx.parameter_list());
			currentRoot = ee;
		}

		// set action semantics
		bridge.setDialect(
				marks.containsKey(DIALECT) ? getDialectCode(marks.get(DIALECT).getString()) : Actiondialect_c.none);
		bridge.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		if (ctx.action_body() != null) {
			bridge.setAction_semantics_internal(visitAction_body(ctx.action_body()));
		}

		// link to EE
		bridge.relateAcrossR19To(ee);

		return bridge;
	}

	@Override
	public Function_c visitFunction_definition(Function_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create function
		final String funcName = visitName(ctx.func_name);
		final Function_c func = findOrCreate(Function_c.class, parentPkg.getPath() + "::" + funcName);
		func.setName(funcName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(func);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.FUNCTION);

		// set description
		if (ctx.description() != null) {
			func.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(FUNCTION_NUM)) {
			func.setNumb(marks.get(FUNCTION_NUM).getInteger());
		}

		// set return type
		if (ctx.type_reference() != null) {
			func.relateAcrossR25To((DataType_c) visit(ctx.type_reference()));

			// set the return array dimensions
			final String dimString = getDimString(ctx.type_reference().array_type_reference());
			func.setReturn_dimensions(dimString);
			final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, func);
			for (int i = 0; i < dims.size(); i++) {
				func.Resizereturn_dimensions(i, dims.get(i), dims.size());
			}
		} else {
			func.relateAcrossR25To(voidType);
		}

		// handle parameters
		if (ctx.parameter_list() != null) {
			currentRoot = func;
			visit(ctx.parameter_list());
			currentRoot = parentPkg;
		}

		// set action semantics
		func.setDialect(
				marks.containsKey(DIALECT) ? getDialectCode(marks.get(DIALECT).getString()) : Actiondialect_c.none);
		func.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		if (ctx.action_body() != null) {
			func.setAction_semantics_internal(visitAction_body(ctx.action_body()));
		}

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

		return func;
	}

	@Override
	public NonRootModelElement visitParameter_list(Parameter_listContext ctx) {
		// link parameters to each other in order
		if (currentRoot instanceof Bridge_c) {
			BridgeParameter_c prevBparm = null;
			for (ParameterContext paramCtx : ctx.parameter()) {
				final BridgeParameter_c s_bparm = (BridgeParameter_c) visit(paramCtx);
				if (prevBparm != null) {
					prevBparm.relateAcrossR55ToPrecedes(s_bparm);
				}
				prevBparm = s_bparm;
			}
			return prevBparm;
		} else if (currentRoot instanceof Function_c) {
			FunctionParameter_c prevSparm = null;
			for (ParameterContext paramCtx : ctx.parameter()) {
				final FunctionParameter_c s_sparm = (FunctionParameter_c) visit(paramCtx);
				if (prevSparm != null) {
					prevSparm.relateAcrossR54ToPrecedes(s_sparm);
				}
				prevSparm = s_sparm;
			}
			return prevSparm;
		} else if (currentRoot instanceof TerminatorService_c) {
			TerminatorServiceParameter_c prevTsparm = null;
			for (ParameterContext paramCtx : ctx.parameter()) {
				final TerminatorServiceParameter_c d_tsparm = (TerminatorServiceParameter_c) visit(paramCtx);
				if (prevTsparm != null) {
					prevTsparm.relateAcrossR1654ToPrecedes(d_tsparm);
				}
				prevTsparm = d_tsparm;
			}
			return prevTsparm;
		} else {
			throw new CoreImport.XtumlLoadException(
					"Unexpected root type for parameter list: " + currentRoot.getClass().getSimpleName());
		}
	}

	@Override
	public NonRootModelElement visitParameter(ParameterContext ctx) {
		if (currentRoot instanceof Bridge_c) {
			final Bridge_c bridge = (Bridge_c) currentRoot;

			// create a new parameter
			final BridgeParameter_c s_bparm = new BridgeParameter_c(modelRoot);
			s_bparm.setName(visitName(ctx.param_name));

			// set by value/ref
			s_bparm.setBy_ref("in".equals(ctx.by_ref.getText()) ? 0 : 1);

			// link the data type
			s_bparm.relateAcrossR22To((DataType_c) visit(ctx.type_reference()));

			// set the array dimensions
			final String dimString = getDimString(ctx.type_reference().array_type_reference());
			s_bparm.setDimensions(dimString);
			final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, s_bparm);
			for (int i = 0; i < dims.size(); i++) {
				s_bparm.Resizedimensions(i, dims.get(i), dims.size());
			}

			// link to message
			s_bparm.relateAcrossR21To(bridge);

			return s_bparm;
		} else if (currentRoot instanceof Function_c) {
			final Function_c func = (Function_c) currentRoot;

			// create a new parameter
			final FunctionParameter_c s_sparm = new FunctionParameter_c(modelRoot);
			s_sparm.setName(visitName(ctx.param_name));

			// set by value/ref
			s_sparm.setBy_ref("in".equals(ctx.by_ref.getText()) ? 0 : 1);

			// link the data type
			s_sparm.relateAcrossR26To((DataType_c) visit(ctx.type_reference()));

			// set the array dimensions
			final String dimString = getDimString(ctx.type_reference().array_type_reference());
			s_sparm.setDimensions(dimString);
			final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, s_sparm);
			for (int i = 0; i < dims.size(); i++) {
				s_sparm.Resizedimensions(i, dims.get(i), dims.size());
			}

			// link to message
			s_sparm.relateAcrossR24To(func);

			return s_sparm;
		} else if (currentRoot instanceof TerminatorService_c) {
			final TerminatorService_c svc = (TerminatorService_c) currentRoot;

			// create a new parameter
			final TerminatorServiceParameter_c d_tsparm = new TerminatorServiceParameter_c(modelRoot);
			d_tsparm.setName(visitName(ctx.param_name));

			// set by value/ref
			d_tsparm.setBy_ref("in".equals(ctx.by_ref.getText()) ? 0 : 1);

			// link the data type
			d_tsparm.relateAcrossR1653To((DataType_c) visit(ctx.type_reference()));

			// set the array dimensions
			final String dimString = getDimString(ctx.type_reference().array_type_reference());
			d_tsparm.setDimensions(dimString);
			final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, d_tsparm);
			for (int i = 0; i < dims.size(); i++) {
				d_tsparm.Resizedimensions(i, dims.get(i), dims.size());
			}

			// link to message
			d_tsparm.relateAcrossR1652To(svc);

			return d_tsparm;
		} else {
			throw new CoreImport.XtumlLoadException(
					"Unexpected root type for parameter list: " + currentRoot.getClass().getSimpleName());
		}
	}

	@Override
	public NonRootModelElement visitClass_declaration(Class_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;
		final String className = visitName(ctx.class_name);
		if (ctx.ref_name != null || "Unassigned Imported Class".equals(className)) {

			// create imported class
			final ImportedClass_c importedClass = new ImportedClass_c(modelRoot);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(importedClass);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.IMPORTED_CLASS);

			if (ctx.ref_name != null) {
				// get referenced class
				final String refClassPath = visitScoped_name(ctx.ref_name);
				final ModelClass_c refClass;
				try {
					// TODO this might cause a circular reference
					refClass = executor.callAndWait(() -> searchByPath(Elementtypeconstants_c.CLASS, refClassPath,
							ModelClass_c::getOneO_OBJOnR8001));
				} catch (Exception e) {
					throw new CoreImport.XtumlLoadException("Failed to find class '" + refClassPath + "'.", e);
				}

				// get the reference number
				try {
					importedClass
							.setRef_num(Integer.parseInt(className.substring(refClass.getName().length()).strip()));
				} catch (NumberFormatException e) {
					// ignore if there is no ref num
				}

				// link to ref class
				importedClass.relateAcrossR101To(refClass);
			}

			// link to parent package
			pe.relateAcrossR8000To(parentPkg);

			return importedClass;
		} else {
			final ModelClass_c modelClass = findOrCreate(ModelClass_c.class, parentPkg.getPath() + "::" + className);
			modelClass.setName(className);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(modelClass);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.CLASS);
			pe.relateAcrossR8000To(parentPkg);
			return modelClass;
		}
	}

	@Override
	public Association_c visitSimple_relationship_definition(Simple_relationship_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create relationship
		final int relNum = Integer.parseInt(ctx.RelName().getText().substring(1));
		final Association_c rel = findOrCreate(Association_c.class, parentPkg.getPath() + "::R" + relNum);
		rel.setNumb(relNum);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(rel);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.ASSOCIATION);

		// set description
		if (ctx.description() != null) {
			rel.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// create simple association
		final SimpleAssociation_c simp = new SimpleAssociation_c(modelRoot);
		simp.relateAcrossR206To(rel);

		// NOTE: all simple associations are created unformalized

		// find the one class
		final String oneClassName = visitName(ctx.half_rels.get(1).first_class);
		final ImportedClass_c oneImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
				selected -> ((ImportedClass_c) selected).getName().equals(oneClassName));
		final ModelClass_c oneClass;
		if (oneImportedClass != null) {
			oneClass = ModelClass_c.getOneO_OBJOnR101(oneImportedClass);
		} else {
			oneClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ModelClass_c) selected).getName().equals(oneClassName));
		}

		// find the other class
		final String otherClassName = visitName(ctx.half_rels.get(0).first_class);
		final ImportedClass_c otherImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
				selected -> ((ImportedClass_c) selected).getName().equals(otherClassName));
		final ModelClass_c otherClass;
		if (otherImportedClass != null) {
			otherClass = ModelClass_c.getOneO_OBJOnR101(otherImportedClass);
		} else {
			otherClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ModelClass_c) selected).getName().equals(otherClassName));
		}

		// create the one side participant
		final ClassAsSimpleParticipant_c onePart = new ClassAsSimpleParticipant_c(modelRoot);
		onePart.setMult("one".equals(ctx.half_rels.get(0).mult.getText()) ? 0 : 1);
		onePart.setCond("unconditionally".equals(ctx.half_rels.get(0).cond.getText()) ? 0 : 1);
		onePart.setTxt_phrs(ctx.half_rels.get(0).phrase != null ? visitName(ctx.half_rels.get(0).phrase) : "");
		onePart.relateAcrossR207To(simp);
		final ReferredToClassInAssoc_c oneRto = new ReferredToClassInAssoc_c(modelRoot);
		oneRto.relateAcrossR204To(onePart);
		final ClassInAssociation_c oneOir = new ClassInAssociation_c(modelRoot);
		oneOir.relateAcrossR203To(oneRto);
		if (oneImportedClass != null) {
			oneImportedClass.relateAcrossR202To(oneOir);
		}

		// create the other side participant
		final ClassAsSimpleParticipant_c otherPart = new ClassAsSimpleParticipant_c(modelRoot);
		otherPart.setMult("one".equals(ctx.half_rels.get(1).mult.getText()) ? 0 : 1);
		otherPart.setCond("unconditionally".equals(ctx.half_rels.get(1).cond.getText()) ? 0 : 1);
		otherPart.setTxt_phrs(ctx.half_rels.get(1).phrase != null ? visitName(ctx.half_rels.get(1).phrase) : "");
		otherPart.relateAcrossR207To(simp);
		final ReferredToClassInAssoc_c otherRto = new ReferredToClassInAssoc_c(modelRoot);
		otherRto.relateAcrossR204To(otherPart);
		final ClassInAssociation_c otherOir = new ClassInAssociation_c(modelRoot);
		otherOir.relateAcrossR203To(otherRto);
		if (otherImportedClass != null) {
			otherImportedClass.relateAcrossR202To(otherOir);
		}

		// link the relationship
		rel.relateAcrossR201To(oneOir);
		oneClass.relateAcrossR201To(oneOir);
		rel.relateAcrossR201To(otherOir);
		otherClass.relateAcrossR201To(otherOir);

		// link to parent package
		pe.relateAcrossR8000To(parentPkg);

		return rel;
	}

	@Override
	public Association_c visitLinked_relationship_definition(Linked_relationship_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create relationship
		final int relNum = Integer.parseInt(ctx.RelName().getText().substring(1));
		final Association_c rel = findOrCreate(Association_c.class, parentPkg.getPath() + "::R" + relNum);
		rel.setNumb(relNum);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(rel);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.ASSOCIATION);

		// set description
		if (ctx.description() != null) {
			rel.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// create linked association
		final LinkedAssociation_c assoc = new LinkedAssociation_c(modelRoot);
		assoc.relateAcrossR206To(rel);

		// find the one class
		final String oneClassName = visitName(ctx.half_rels.get(1).first_class);
		final ImportedClass_c oneImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
				selected -> ((ImportedClass_c) selected).getName().equals(oneClassName));
		final ModelClass_c oneClass;
		if (oneImportedClass != null) {
			oneClass = ModelClass_c.getOneO_OBJOnR101(oneImportedClass);
		} else {
			oneClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ModelClass_c) selected).getName().equals(oneClassName));
		}

		// find the other class
		final String otherClassName = visitName(ctx.half_rels.get(0).first_class);
		final ImportedClass_c otherImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
				selected -> ((ImportedClass_c) selected).getName().equals(otherClassName));
		final ModelClass_c otherClass;
		if (otherImportedClass != null) {
			otherClass = ModelClass_c.getOneO_OBJOnR101(otherImportedClass);
		} else {
			otherClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ModelClass_c) selected).getName().equals(otherClassName));
		}

		// find the link class
		final String linkClassName = visitName(ctx.link_class);
		final ImportedClass_c linkImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
				selected -> ((ImportedClass_c) selected).getName().equals(linkClassName));
		final ModelClass_c linkClass;
		if (linkImportedClass != null) {
			linkClass = ModelClass_c.getOneO_OBJOnR101(linkImportedClass);
		} else {
			linkClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ModelClass_c) selected).getName().equals(linkClassName));
		}

		// create the one side participant
		final ClassAsAssociatedOneSide_c onePart = new ClassAsAssociatedOneSide_c(modelRoot);
		onePart.setMult("one".equals(ctx.half_rels.get(0).mult.getText()) ? 0 : 1);
		onePart.setCond("unconditionally".equals(ctx.half_rels.get(0).cond.getText()) ? 0 : 1);
		onePart.setTxt_phrs(ctx.half_rels.get(0).phrase != null ? visitName(ctx.half_rels.get(0).phrase) : "");
		onePart.relateAcrossR209To(assoc);
		final ReferredToClassInAssoc_c oneRto = new ReferredToClassInAssoc_c(modelRoot);
		oneRto.relateAcrossR204To(onePart);
		final ClassInAssociation_c oneOir = new ClassInAssociation_c(modelRoot);
		oneOir.relateAcrossR203To(oneRto);
		if (oneImportedClass != null) {
			oneImportedClass.relateAcrossR202To(oneOir);
		}

		// create the other side participant
		final ClassAsAssociatedOtherSide_c otherPart = new ClassAsAssociatedOtherSide_c(modelRoot);
		otherPart.setMult("one".equals(ctx.half_rels.get(1).mult.getText()) ? 0 : 1);
		otherPart.setCond("unconditionally".equals(ctx.half_rels.get(1).cond.getText()) ? 0 : 1);
		otherPart.setTxt_phrs(ctx.half_rels.get(1).phrase != null ? visitName(ctx.half_rels.get(1).phrase) : "");
		otherPart.relateAcrossR210To(assoc);
		final ReferredToClassInAssoc_c otherRto = new ReferredToClassInAssoc_c(modelRoot);
		otherRto.relateAcrossR204To(otherPart);
		final ClassInAssociation_c otherOir = new ClassInAssociation_c(modelRoot);
		otherOir.relateAcrossR203To(otherRto);
		if (otherImportedClass != null) {
			otherImportedClass.relateAcrossR202To(otherOir);
		}

		// create the link
		final ClassAsLink_c link = new ClassAsLink_c(modelRoot);
		link.setMult((ctx.link_mult == null || "one".equals(ctx.link_mult.getText())) ? 0 : 1);
		link.relateAcrossR211To(assoc);
		final ReferringClassInAssoc_c linkRgo = new ReferringClassInAssoc_c(modelRoot);
		linkRgo.relateAcrossR205To(link);
		final ClassInAssociation_c linkOir = new ClassInAssociation_c(modelRoot);
		linkOir.relateAcrossR203To(linkRgo);
		if (linkImportedClass != null) {
			linkImportedClass.relateAcrossR202To(linkOir);
		}

		// link the relationship
		rel.relateAcrossR201To(oneOir);
		oneClass.relateAcrossR201To(oneOir);
		rel.relateAcrossR201To(otherOir);
		otherClass.relateAcrossR201To(otherOir);
		rel.relateAcrossR201To(linkOir);
		linkClass.relateAcrossR201To(linkOir);

		// link to parent package
		pe.relateAcrossR8000To(parentPkg);

		return rel;
	}

	@Override
	public Association_c visitSubsuper_relationship_definition(Subsuper_relationship_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create relationship
		final int relNum = Integer.parseInt(ctx.RelName().getText().substring(1));
		final Association_c rel = findOrCreate(Association_c.class, parentPkg.getPath() + "::R" + relNum);
		rel.setNumb(relNum);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(rel);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.ASSOCIATION);

		// set description
		if (ctx.description() != null) {
			rel.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// create simple association
		final SubtypeSupertypeAssociation_c subsup = new SubtypeSupertypeAssociation_c(modelRoot);
		subsup.relateAcrossR206To(rel);

		// find the supertype class
		final String superClassName = visitName(ctx.super_class);
		final ImportedClass_c superImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
				selected -> ((ImportedClass_c) selected).getName().equals(superClassName));
		final ModelClass_c superClass;
		if (superImportedClass != null) {
			superClass = ModelClass_c.getOneO_OBJOnR101(superImportedClass);
		} else {
			superClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ModelClass_c) selected).getName().equals(superClassName));
		}

		// create the supertype participant
		final ClassAsSupertype_c superPart = new ClassAsSupertype_c(modelRoot);
		superPart.relateAcrossR212To(subsup);
		final ReferredToClassInAssoc_c superRto = new ReferredToClassInAssoc_c(modelRoot);
		superRto.relateAcrossR204To(superPart);
		final ClassInAssociation_c superOir = new ClassInAssociation_c(modelRoot);
		superOir.relateAcrossR203To(superRto);
		if (superImportedClass != null) {
			superImportedClass.relateAcrossR202To(superOir);
		}

		// link supertype to the relationship
		rel.relateAcrossR201To(superOir);
		superClass.relateAcrossR201To(superOir);

		// handle each subtype class
		for (NameContext subtypeCtx : ctx.sub_classes) {
			// find the subtype class
			final String subClassName = visitName(subtypeCtx);
			final ImportedClass_c subImportedClass = ImportedClass_c.getOneO_IOBJOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
					selected -> ((ImportedClass_c) selected).getName().equals(subClassName));
			final ModelClass_c subClass;
			if (subImportedClass != null) {
				subClass = ModelClass_c.getOneO_OBJOnR101(subImportedClass);
			} else {
				subClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
						selected -> ((ModelClass_c) selected).getName().equals(subClassName));
			}

			// create the subtype participant
			final ClassAsSubtype_c subPart = new ClassAsSubtype_c(modelRoot);
			subPart.relateAcrossR213To(subsup);
			final ReferringClassInAssoc_c subRgo = new ReferringClassInAssoc_c(modelRoot);
			subRgo.relateAcrossR205To(subPart);
			final ClassInAssociation_c subOir = new ClassInAssociation_c(modelRoot);
			subOir.relateAcrossR203To(subRgo);
			if (subImportedClass != null) {
				subImportedClass.relateAcrossR202To(subOir);
			}

			// link subtype to the relationship
			rel.relateAcrossR201To(subOir);
			subClass.relateAcrossR201To(subOir);
		}

		// link to parent package
		pe.relateAcrossR8000To(parentPkg);

		return rel;
	}

	@Override
	public NonRootModelElement visitComponent_declaration(Component_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;
		final String compName = visitName(ctx.comp_name);
		if (ctx.ref_name != null || "Unassigned Imported Component".equals(compName)) {

			// create component reference
			final ComponentReference_c compRef = new ComponentReference_c(modelRoot);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(compRef);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.COMPONENT_REFERENCE);

			// set description
			if (ctx.description() != null) {
				compRef.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
						.collect(Collectors.joining(System.lineSeparator())));
			}

			// process marks
			final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
			if (marks.containsKey(MULTIPLICITY)) {
				compRef.setMult(marks.get(MULTIPLICITY).getInteger());
			}
			if (marks.containsKey(CLASSIFIER_NAME)) {
				compRef.setClassifiername(marks.get(CLASSIFIER_NAME).getString());
			}

			if (ctx.ref_name != null) {
				// get referenced component
				final String refCompPath = visitScoped_name(ctx.ref_name);
				final Component_c refComp;
				try {
					refComp = executor.callAndWait(() -> searchByPath(Elementtypeconstants_c.COMPONENT, refCompPath,
							Component_c::getOneC_COnR8001));
				} catch (Exception e) {
					throw new CoreImport.XtumlLoadException("Failed to find component '" + refCompPath + "'.", e);
				}

				// link to ref component
				compRef.Assigntocomp(refComp.getId());

			}

			// link to parent package
			pe.relateAcrossR8000To(parentPkg);

			return compRef;
		} else {
			final Component_c comp = findOrCreate(Component_c.class, parentPkg.getPath() + "::" + compName);
			comp.setName(compName);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(comp);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.COMPONENT);
			pe.relateAcrossR8000To(parentPkg);
			return comp;
		}
	}

	@Override
	public Satisfaction_c visitSatisfaction_definition(Satisfaction_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// get required port reference
		final String reqCompName = visitName(ctx.req_comp_ref);
		final String reqPortName = visitName(ctx.req_port_ref);
		final NonRootModelElement reqRef;
		try {
			reqRef = executor.callAndWaitNullable(() -> {
				final Requirement_c req = Requirement_c
						.getOneC_ROnR4009(
								InterfaceReference_c
										.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(
												Component_c.getManyC_CsOnR8001(
														PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
														selected -> ((Component_c) selected).getName()
																.equals(reqCompName)),
												selected -> ((Port_c) selected).getName().equals(reqPortName))));
				if (req != null) {
					return req;
				} else {

					final PortReference_c cl_por = PortReference_c.getOneCL_POROnR4707(
							ComponentReference_c.getManyCL_ICsOnR8001(
									PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
									selected -> Component_c.getOneC_COnR4201((ComponentReference_c) selected).getName()
											.equals(reqCompName)),
							selected -> ((PortReference_c) selected).getName().equals(reqPortName));
					final ImportedReference_c cl_iir = ImportedReference_c.getOneCL_IIROnR4708(cl_por);
					final ImportedRequirement_c cl_ir = ImportedRequirement_c.getOneCL_IROnR4703(cl_iir);
					return cl_ir;
				}

			});
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException(
					"Failed to find required port reference '" + reqCompName + "::" + reqPortName + "'.", e);
		}

		// get provided port reference
		final String provCompName = visitName(ctx.prov_comp_ref);
		final String provPortName = visitName(ctx.prov_port_ref);
		final NonRootModelElement provRef;
		try {
			provRef = executor.callAndWaitNullable(() -> {
				final Provision_c prov = Provision_c
						.getOneC_POnR4009(
								InterfaceReference_c
										.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(
												Component_c.getManyC_CsOnR8001(
														PackageableElement_c.getManyPE_PEsOnR8000(parentPkg),
														selected -> ((Component_c) selected).getName()
																.equals(provCompName)),
												selected -> ((Port_c) selected).getName().equals(provPortName))));
				if (prov != null) {
					return prov;
				} else {
					return ImportedProvision_c
							.getOneCL_IPOnR4703(
									ImportedReference_c
											.getOneCL_IIROnR4708(
													PortReference_c.getOneCL_POROnR4707(
															ComponentReference_c.getManyCL_ICsOnR8001(
																	PackageableElement_c
																			.getManyPE_PEsOnR8000(parentPkg),
																	selected -> Component_c
																			.getOneC_COnR4201(
																					(ComponentReference_c) selected)
																			.getName().equals(provCompName)),
															selected -> ((PortReference_c) selected).getName()
																	.equals(provPortName))));
				}

			});
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException(
					"Failed to find provided port reference '" + provCompName + "::" + provPortName + "'.", e);
		}

		// create satisfaction
		if (provRef instanceof Provision_c) {
			final Provision_c provision = (Provision_c) provRef;
			if (reqRef instanceof Requirement_c) {
				final Requirement_c requirement = (Requirement_c) reqRef;
				provision.Linkconnector(requirement.getRequirement_id());
				return Satisfaction_c.getOneC_SFOnR4002(requirement);
			} else {
				final ImportedRequirement_c importedRequirement = (ImportedRequirement_c) reqRef;
				provision.Linkconnector(importedRequirement.getId());
				return Satisfaction_c.getOneC_SFOnR4706(importedRequirement);
			}
		} else {
			final ImportedProvision_c importedProvision = (ImportedProvision_c) provRef;
			if (reqRef instanceof Requirement_c) {
				final Requirement_c requirement = (Requirement_c) reqRef;
				importedProvision.Linkconnector(requirement.getRequirement_id());
				return Satisfaction_c.getOneC_SFOnR4002(requirement);
			} else {
				final ImportedRequirement_c importedRequirement = (ImportedRequirement_c) reqRef;
				importedProvision.Linkconnector(importedRequirement.getId());
				return Satisfaction_c.getOneC_SFOnR4706(importedRequirement);
			}
		}

	}

	@Override
	public Terminator_c visitPort_definition(Port_definitionContext ctx) {
		final var deployment = (Deployment_c) currentRoot;

		// create terminator
		final var terminator = new Terminator_c(modelRoot);
		terminator.setProvider(ctx.direction.getText().equals("provided"));
		terminator.setDomain_name(visitName(((Component_definitionContext) ctx.getParent().getParent()).comp_name));
		if (!terminator.getProvider()) {
			terminator.setTerminator_name(visitName(ctx.port_name));
		}

		// set description
		if (ctx.description() != null) {
			terminator.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(KEY_LETTERS)) {
			terminator.setKey_lett(marks.get(KEY_LETTERS).getString());
		}
		if (marks.containsKey(IMPLEMENTATION_SYSTEM)) {
			terminator.setImplementation_system(marks.get(IMPLEMENTATION_SYSTEM).getString());
		}

		// process terminator services
		currentRoot = terminator;

		sequenceServices = new TreeSet<>();
		ctx.message_definition().forEach(this::visit);

		// sort by sequence number
		if (!sequenceServices.isEmpty()) {
			final var terminatorSequence = new TerminatorServiceSequence_c(modelRoot);
			terminatorSequence.relateAcrossR1658To(terminator);
			ServiceInSequence_c prevSis = null;
			for (final var sequenceService : sequenceServices) {
				final var sis = new ServiceInSequence_c(modelRoot);
				sis.relateAcrossR1659To(terminatorSequence);
				sis.relateAcrossR1660To(sequenceService.getService());
				if (prevSis != null) {
					sis.relateAcrossR1661ToSucceeds(prevSis);
				}
				prevSis = sis;
			}
		}

		currentRoot = deployment;

		// link to deployment
		terminator.relateAcrossR1650To(deployment);

		return terminator;
	}

	@Override
	public TerminatorService_c visitMessage_definition(Message_definitionContext ctx) {
		final var terminator = (Terminator_c) currentRoot;

		// create terminator service
		final var serviceName = visitScoped_name(ctx.msg_name);
		final var terminatorService = new TerminatorService_c(modelRoot);
		terminatorService.setName(serviceName);

		// set description
		if (ctx.description() != null) {
			terminatorService.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(MESSAGE_NUM)) {
			terminatorService.setNumb(marks.get(MESSAGE_NUM).getInteger());
		}
		terminatorService.setImplementation_scope(marks.containsKey(IMPLEMENTATION_SCOPE)
				&& "deployment".equals(marks.get(IMPLEMENTATION_SCOPE).getString()) ? Implementationscope_c.Deployment
						: Implementationscope_c.Domain);
		terminatorService.setIs_stale(marks.containsKey(STALE));

		// add the service to the sequence set to be sorted later
		if (marks.containsKey(SEQUENCE_NUM)) {
			sequenceServices.add(new SequenceService(marks.get(SEQUENCE_NUM).getInteger(), terminatorService));
		}

		// set return type
		if (ctx.type_reference() != null) {
			terminatorService.relateAcrossR1656To((DataType_c) visit(ctx.type_reference()));

			// set the return array dimensions
			final String dimString = getDimString(ctx.type_reference().array_type_reference());
			terminatorService.setReturn_dimensions(dimString);
			final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, terminatorService);
			for (int i = 0; i < dims.size(); i++) {
				terminatorService.Resizereturn_dimensions(i, dims.get(i), dims.size());
			}
		} else {
			terminatorService.relateAcrossR1656To(voidType);
		}

		// handle parameters
		if (ctx.parameter_list() != null) {
			currentRoot = terminatorService;
			visit(ctx.parameter_list());
			currentRoot = terminator;
		}

		// set action semantics
		terminatorService.setDialect(
				marks.containsKey(DIALECT) ? getDialectCode(marks.get(DIALECT).getString()) : Actiondialect_c.none);
		terminatorService
				.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		if (ctx.action_body() != null) {
			terminatorService.setAction_semantics_internal(visitAction_body(ctx.action_body()));
		}

		// link to terminator
		terminatorService.relateAcrossR1651To(terminator);

		// TODO link in sequence

		return terminatorService;
	}

	private String getRuleText(ParseTree tree) {
		if (tree instanceof TerminalNode) {
			return tree.getText();
		} else {
			return IntStream.iterate(0, i -> i + 1).limit(tree.getChildCount())
					.mapToObj(i -> getRuleText(tree.getChild(i))).collect(Collectors.joining(" "));
		}

	}

	private static class SequenceService implements Comparable<SequenceService> {

		private int sequenceNum;
		private TerminatorService_c service;

		SequenceService(final int sequenceNum, final TerminatorService_c service) {
			this.sequenceNum = sequenceNum;
			this.service = service;
		}

		@Override
		public int compareTo(SequenceService o) {
			if (o != null) {
				return sequenceNum - o.sequenceNum;
			} else {
				return 0;
			}
		}

		TerminatorService_c getService() {
			return service;
		}

		public boolean equals(Object o) {
			return o instanceof SequenceService && sequenceNum == ((SequenceService) o).sequenceNum;
		}

	}

}
