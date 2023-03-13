package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.Exception_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.Range_c;
import org.xtuml.bp.core.StructureMember_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.Bridge_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Class_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Constant_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Constant_group_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Enumeration_type_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.EnumeratorContext;
import org.xtuml.bp.io.core.XtumlParser.Exception_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.External_entity_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Function_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Interface_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Named_type_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Package_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.Structure_memberContext;
import org.xtuml.bp.io.core.XtumlParser.Structure_type_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Type_declarationContext;

// TODO handle type forward declarations

public class PackageImportVisitor extends XtumlImportVisitor {

	public PackageImportVisitor(Ooaofooa modelRoot) {
		super(modelRoot);
	}

	@Override
	public NonRootModelElement visitPackage_definition(Package_definitionContext ctx) {
		// find or create package
		final String pkgName = (String) visit(ctx.pkg_name);
		final Package_c pkg = Package_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
				IdAssigner.NULL_UUID, "", "", 0, currentRoot.getPath() + "::" + pkgName);
		pkg.setName(pkgName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(pkg);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.PACKAGE);

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(NUMBER_RANGE)) {
			pkg.setNum_rng(marks.get(NUMBER_RANGE).getInteger());
		}

		// set description
		if (ctx.description() != null) {
			pkg.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// link to parent
		if (currentRoot instanceof Package_c) {
			pe.relateAcrossR8000To((Package_c) currentRoot);
			try {
				final SystemModel_c s_sys = executor.callAndWait(
						() -> Optional.ofNullable(SystemModel_c.getOneS_SYSOnR1405((Package_c) currentRoot)));
				pkg.relateAcrossR1405To(s_sys);
			} catch (Exception e) {
				throw new CoreImport.XtumlLoadException("Could not find system model for package: " + pkgName, e);
			}
		} else if (currentRoot instanceof Component_c) {
			pe.relateAcrossR8003To((Component_c) currentRoot);
			try {
				final SystemModel_c s_sys = executor.callAndWait(() -> Optional.ofNullable(SystemModel_c
						.getOneS_SYSOnR1405(Package_c.PackageInstance(modelRoot, selected -> ((Package_c) selected)
								.getPackage_id().equals(((Component_c) currentRoot).Getpackageid())))));
				pkg.relateAcrossR1405To(s_sys);
			} catch (Exception e) {
				throw new CoreImport.XtumlLoadException("Could not find system model for package: " + pkgName, e);
			}
		} else if (currentRoot instanceof SystemModel_c) {
			pkg.relateAcrossR1401To((SystemModel_c) currentRoot);
			pkg.relateAcrossR1405To((SystemModel_c) currentRoot);
		}
		currentRoot = pkg;

		// process all package items
		ctx.package_item().forEach(this::visit);

		return pkg;
	}

	@Override
	public DataType_c visitType_declaration(Type_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create type
		final String typeName = (String) visit(ctx.type_name);
		final DataType_c type = DataType_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID, "", "",
				"", parentPkg.getPath() + "::" + typeName);
		type.setName(typeName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(type);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.DATATYPE);

		// set description
		if (ctx.description() != null) {
			type.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(RAW_TYPE_DEF)) {
			// create user type
			final UserDataType_c udt = new UserDataType_c(modelRoot);
			udt.relateAcrossR17To(type);

			// link to default type
			udt.relateAcrossR18To(this.integerType);

			// set definition
			// TODO figure out spacing
			udt.setDefinition(ctx.type_definition().getText().strip());
		} else {
			// process subtype
			currentRoot = type;
			visit(ctx.type_definition());
			currentRoot = parentPkg;
		}

		// link to the parent package
		pe.relateAcrossR8000To(parentPkg);

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
		mbr.setName((String) visit(ctx.member_name));

		// set description
		if (ctx.description() != null) {
			mbr.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
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
		s_enum.setName((String) visit(ctx.enum_name));

		// set description
		if (ctx.description() != null) {
			s_enum.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
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
		final String exceptionName = (String) visit(ctx.exception_name);
		final Exception_c exception = Exception_c.resolveInstance(modelRoot, UUID.randomUUID(), "", "",
				parentPkg.getPath() + "::" + exceptionName);
		exception.setName(exceptionName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(exception);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.EXCEPTION);

		// set description
		if (ctx.description() != null) {
			exception.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
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
		final String constantGroupName = (String) visit(ctx.group_name);
		final ConstantSpecification_c constantGroup = ConstantSpecification_c.resolveInstance(modelRoot,
				UUID.randomUUID(), "", "", parentPkg.getPath() + "::" + constantGroupName);
		constantGroup.setInformalgroupname(constantGroupName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(constantGroup);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.CONSTANT);

		// set description
		if (ctx.description() != null) {
			constantGroup.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
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
		final String constantName = (String) visit(ctx.constant_name);
		final SymbolicConstant_c constant = SymbolicConstant_c.resolveInstance(modelRoot, UUID.randomUUID(), "", "",
				IdAssigner.NULL_UUID, IdAssigner.NULL_UUID, IdAssigner.NULL_UUID, IdAssigner.NULL_UUID,
				constantGroup.getPath() + "::" + constantName);
		constant.setName(constantName);

		// set description
		if (ctx.description() != null) {
			constant.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
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
		final String ifaceName = (String) visit(ctx.iface_name);
		final Interface_c iface = Interface_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID, "",
				"", parentPkg.getPath() + "::" + ifaceName);
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
		final String eeName = (String) visit(ctx.ee_name);
		final ExternalEntity_c ee = ExternalEntity_c.resolveInstance(modelRoot, UUID.randomUUID(), "", "", "",
				IdAssigner.NULL_UUID, "", "", false, parentPkg.getPath() + "::" + eeName);
		ee.setName(eeName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(ee);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.EE);

		// set description
		if (ctx.description() != null) {
			ee.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
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
		final String bridgeName = (String) visit(ctx.brg_name);
		final Bridge_c bridge = Bridge_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID, "", "", 0,
				IdAssigner.NULL_UUID, "", 0, "", 0, ee.Getpath() + "::" + bridgeName);
		bridge.setName(bridgeName);

		// set description
		if (ctx.description() != null) {
			bridge.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();

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
		bridge.setDialect(Actiondialect_c.oal); // TODO set dialect to OAL
		bridge.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		if (ctx.action_body() != null) {
			bridge.setAction_semantics_internal((String) visit(ctx.action_body()));
		}

		// link to EE
		bridge.relateAcrossR19To(ee);

		return bridge;
	}

	@Override
	public Function_c visitFunction_definition(Function_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create function
		final String funcName = (String) visit(ctx.func_name);
		final Function_c func = Function_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID, "", "",
				"", IdAssigner.NULL_UUID, 0, "", 0, 0, parentPkg.getPath() + "::" + funcName);
		func.setName(funcName);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(func);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.FUNCTION);

		// set description
		if (ctx.description() != null) {
			func.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
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
		func.setDialect(Actiondialect_c.oal); // TODO set dialect to OAL
		func.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		if (ctx.action_body() != null) {
			func.setAction_semantics_internal((String) visit(ctx.action_body()));
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
		} else {
			FunctionParameter_c prevSparm = null;
			for (ParameterContext paramCtx : ctx.parameter()) {
				final FunctionParameter_c s_sparm = (FunctionParameter_c) visit(paramCtx);
				if (prevSparm != null) {
					prevSparm.relateAcrossR54ToPrecedes(s_sparm);
				}
				prevSparm = s_sparm;
			}
			return prevSparm;
		}
	}

	@Override
	public NonRootModelElement visitParameter(ParameterContext ctx) {
		if (currentRoot instanceof Bridge_c) {
			final Bridge_c bridge = (Bridge_c) currentRoot;

			// create a new parameter
			final BridgeParameter_c s_bparm = new BridgeParameter_c(modelRoot);
			s_bparm.setName((String) visit(ctx.param_name));

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
		} else {
			final Function_c func = (Function_c) currentRoot;

			// create a new parameter
			final FunctionParameter_c s_sparm = new FunctionParameter_c(modelRoot);
			s_sparm.setName((String) visit(ctx.param_name));

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
		}
	}

	@Override
	public NonRootModelElement visitClass_declaration(Class_declarationContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;
		final String className = (String) visit(ctx.class_name);
		if (ctx.ref_name != null || "Unassigned Imported Class".equals(className)) {

			// create imported class
			final ImportedClass_c importedClass = new ImportedClass_c(modelRoot);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(importedClass);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.IMPORTED_CLASS);

			if (ctx.ref_name != null) {
				// get referenced class
				final String refClassPath = (String) visit(ctx.ref_name);
				final ModelClass_c refClass;
				try {
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
			final ModelClass_c modelClass = ModelClass_c.resolveInstance(modelRoot, UUID.randomUUID(), "", 0, "", "",
					IdAssigner.NULL_UUID, parentPkg.getPath() + "::" + className);
			modelClass.setName(className);
			final PackageableElement_c pe = new PackageableElement_c(modelRoot);
			pe.relateAcrossR8001To(modelClass);
			pe.setVisibility(Visibility_c.Public);
			pe.setType(Elementtypeconstants_c.CLASS);
			pe.relateAcrossR8000To(parentPkg);
			return modelClass;
		}
	}

}
