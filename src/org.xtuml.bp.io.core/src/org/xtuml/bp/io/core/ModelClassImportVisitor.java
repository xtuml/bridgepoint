package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AttributeReferenceInClass_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.ClassAsSupertype_c;
import org.xtuml.bp.core.ClassIdentifierAttribute_c;
import org.xtuml.bp.core.ClassIdentifier_c;
import org.xtuml.bp.core.ClassInAssociation_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Deferral_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.NewBaseAttribute_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.ReferentialAttribute_c;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferredToIdentifierAttribute_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.Scope_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.Attribute_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Attribute_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Base_attribute_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Class_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Class_itemContext;
import org.xtuml.bp.io.core.XtumlParser.Derived_attribute_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Identifier_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Operation_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.Referential_attribute_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Relationship_specificationContext;

public class ModelClassImportVisitor extends XtumlImportVisitor {

	private final Map<ReferentialAttribute_c, List<Attribute_referenceContext>> pendingRefs = new HashMap<>();

	public ModelClassImportVisitor(Ooaofooa modelRoot) {
		super(modelRoot);
	}

	@Override
	public ModelClass_c visitClass_definition(Class_definitionContext ctx) {
		final Package_c parentPkg = (Package_c) currentRoot;

		// find or create class
		final String className = (String) visit(ctx.class_name);
		final ModelClass_c modelClass = ModelClass_c.resolveInstance(modelRoot, UUID.randomUUID(), className, 0, "", "",
				IdAssigner.NULL_UUID, parentPkg.getPath() + "::" + className);

		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(modelClass);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.CLASS);
		currentRoot = modelClass;

		// load description
		modelClass.setDescrip(ctx.description() != null ? ctx.description().getText().lines()

				.map(line -> line.replace("//!", "").strip()).collect(Collectors.joining(System.lineSeparator())) : "");

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(KEY_LETTERS)) {
			modelClass.setKey_lett(marks.get(KEY_LETTERS).getString());
		}
		if (marks.containsKey(CLASS_NUM)) {
			modelClass.setNumb(marks.get(CLASS_NUM).getInteger());
		}

		// process attributes
		Attribute_c prevAttr = null;
		for (Attribute_definitionContext attrCtx : ctx.class_item().stream()
				.map(Class_itemContext::attribute_definition).filter(Objects::nonNull).collect(Collectors.toList())) {
			final Attribute_c attr = (Attribute_c) visit(attrCtx);
			if (prevAttr != null) {
				prevAttr.relateAcrossR103ToPrecedes(attr);
			}
			prevAttr = attr;
		}

		// process identifiers
		modelClass.Addidentifiers();

		// process the preferred identifier first
		Optional<Identifier_definitionContext> prefId = ctx.class_item().stream()
				.map(Class_itemContext::identifier_definition).filter(id -> id != null && id.pref != null).findAny();
		if (prefId.isPresent()) {
			currentRoot = ClassIdentifier_c.getOneO_IDOnR104(modelClass,
					selected -> ((ClassIdentifier_c) selected).getOid_id() == 0);
			visit(prefId.get());
		}

		// process the rest of the identifiers in file order
		List<Identifier_definitionContext> otherIds = ctx.class_item().stream()
				.map(Class_itemContext::identifier_definition).filter(id -> id != null && id.pref == null)
				.collect(Collectors.toList());
		for (int i = 1; i < 3 && i < otherIds.size(); i++) {
			final int oidId = i;
			currentRoot = ClassIdentifier_c.getOneO_IDOnR104(modelClass,
					selected -> ((ClassIdentifier_c) selected).getOid_id() == oidId);
			visit(otherIds.get(i));
		}
		if (otherIds.size() > 2) {
			CorePlugin.getDefault().getLog()
					.warn("Ignoring additional identifers for class: '" + modelClass.getName() + "'");
		}
		currentRoot = modelClass;

		// process operations
		Operation_c prevTfr = null;
		for (Operation_definitionContext tfrCtx : ctx.class_item().stream().map(Class_itemContext::operation_definition)
				.filter(Objects::nonNull).collect(Collectors.toList())) {
			final Operation_c tfr = (Operation_c) visit(tfrCtx);
			if (prevTfr != null) {
				prevTfr.relateAcrossR125ToPrecedes(tfr);
			}
			prevTfr = tfr;
		}

		// link to the package
		pe.relateAcrossR8000To(parentPkg);

		// process referentials
		// Note: after all instances are loaded, R113 is processed in the
		// "finishComponentLoad" step
		for (ReferentialAttribute_c rattr : pendingRefs.keySet()) {
			currentRoot = rattr;
			AttributeReferenceInClass_c prevRef = null;
			for (AttributeReferenceInClass_c ref : pendingRefs.get(rattr).stream().map(this::visitAttribute_reference)
					.collect(Collectors.toList())) {
				if (prevRef != null) {
					prevRef.relateAcrossR112ToPrecedes(ref);
				}
				prevRef = ref;
			}
		}

		return modelClass;
	}

	@Override
	public Attribute_c visitBase_attribute_definition(Base_attribute_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;

		// find or create attribute
		final String attrName = (String) visit(ctx.attr_name);
		final Attribute_c attr = Attribute_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
				IdAssigner.NULL_UUID, "", "", "", "", 0, IdAssigner.NULL_UUID, "", "",
				modelClass.getPath() + "::" + attrName);
		final BaseAttribute_c battr = new BaseAttribute_c(modelRoot);
		battr.relateAcrossR106To(attr);
		final NewBaseAttribute_c nbattr = new NewBaseAttribute_c(modelRoot);
		nbattr.relateAcrossR107To(battr);

		// set attribute name and description
		attr.setRoot_nam(attrName);
		attr.setDescrip(ctx.description() != null ? ctx.description().getText().lines()
				.map(line -> line.replace("//!", "").strip()).collect(Collectors.joining(System.lineSeparator())) : "");

		// set data type
		final boolean isUnique = ctx.unq != null;
		DataType_c dt = (DataType_c) visit(ctx.type_reference());
		if (isUnique && dt.equals(integerType)) {
			// treat 'unique integer' as the 'unique_id' type
			dt = uniqueIdType;
		}
		attr.relateAcrossR114To(dt);

		// set up type dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		attr.setDimensions(dimString);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, attr);
		for (int i = 0; i < dims.size(); i++) {
			attr.Resizedimensions(i, dims.get(i), dims.size());
		}

		// set default value
		if (ctx.default_value != null) {
			attr.setDefaultvalue(ctx.default_value.getText()); // Note: current xtUML uses literal strings for default
																// values
		}

		// link to the class
		attr.relateAcrossR102To(modelClass);

		return attr;
	}

	@Override
	public Attribute_c visitReferential_attribute_definition(Referential_attribute_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;

		// find or create attribute
		final String attrName = (String) visit(ctx.attr_name);
		final Attribute_c attr = Attribute_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
				IdAssigner.NULL_UUID, "", "", "", "", 0, IdAssigner.NULL_UUID, "", "",
				modelClass.getPath() + "::" + attrName);
		final ReferentialAttribute_c rattr = new ReferentialAttribute_c(modelRoot);
		rattr.relateAcrossR106To(attr);

		// set attribute name and prefix mode
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(REF_MODE) && "local".equals(marks.get(REF_MODE).getString())) {
			rattr.setRef_mode(0);
			attr.setRoot_nam(attrName);
		} else {
			rattr.setRef_mode(1);
			if (marks.containsKey(USE_PREFIX)) {
				attr.setPfx_mode(1);
				attr.setPrefix(marks.get(USE_PREFIX).getString("prefix"));
				attr.setRoot_nam(marks.get(USE_PREFIX).getString("root_name"));
			} else if (marks.containsKey(USE_REF_PREFIX)) {
				attr.setPfx_mode(2);
				attr.setRoot_nam(marks.get(USE_REF_PREFIX).getString("root_name"));
				attr.setPrefix(attrName.replace(attr.getRoot_nam(), ""));
			} else {
				attr.setPfx_mode(0);
				attr.setRoot_nam(attrName);
			}
		}

		// set attribute description
		attr.setDescrip(ctx.description() != null ? ctx.description().getText().lines()
				.map(line -> line.replace("//!", "").strip()).collect(Collectors.joining(System.lineSeparator())) : "");

		// queue attribute references to process
		pendingRefs.put(rattr, Collections.unmodifiableList(ctx.attribute_reference()));

		// set data type
		attr.relateAcrossR114To((DataType_c) visit(ctx.type_reference()));

		// set up type dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		attr.setDimensions(dimString);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, attr);
		for (int i = 0; i < dims.size(); i++) {
			attr.Resizedimensions(i, dims.get(i), dims.size());
		}

		// link to the class
		attr.relateAcrossR102To(modelClass);

		return attr;
	}

	@Override
	public AttributeReferenceInClass_c visitAttribute_reference(Attribute_referenceContext ctx) {
		final ReferentialAttribute_c rattr = (ReferentialAttribute_c) currentRoot;

		// get the referred to and referring objects
		final ReferredToClassInAssoc_c rto = (ReferredToClassInAssoc_c) visit(ctx.relationship_specification());
		final ReferringClassInAssoc_c rgo = ReferringClassInAssoc_c.getOneR_RGOOnR203(
				ClassInAssociation_c.getManyR_OIRsOnR201(
						Association_c.getOneR_RELOnR201(ClassInAssociation_c.getOneR_OIROnR203(rto))),
				selected -> ((ReferringClassInAssoc_c) selected).getObj_id().equals(rattr.getObj_id()));

		// get the identifier attribute
		final String attrName = (String) visit(ctx.attr_name);
		final ClassIdentifierAttribute_c oida;
		try {
			oida = executor.callAndWait(() -> Optional
					.ofNullable(ClassIdentifierAttribute_c.getOneO_OIDAOnR105(Attribute_c.getManyO_ATTRsOnR102(
							ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c.getOneR_OIROnR203(rto)),
							selected -> ((Attribute_c) selected).getName().equals(attrName)))));
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException("Failed to find attribute '" + ctx.getText() + "'.", e);
		}

		// create the referred to identifier attribute
		final ReferredToIdentifierAttribute_c rtida = new ReferredToIdentifierAttribute_c(modelRoot);
		rtida.relateAcrossR110To(oida);
		rtida.relateAcrossR110To(rto);

		// create the ref
		final AttributeReferenceInClass_c ref = new AttributeReferenceInClass_c(modelRoot);
		ref.relateAcrossR111To(rtida);
		ref.relateAcrossR111To(rgo);
		ref.relateAcrossR108To(rattr);

		return ref;
	}

	@Override
	public ReferredToClassInAssoc_c visitRelationship_specification(Relationship_specificationContext ctx) {
		final ReferentialAttribute_c rattr = (ReferentialAttribute_c) currentRoot;
		final int relNum = Integer.parseInt(ctx.RelName().getText().substring(1));
		final Association_c rel;
		try {
			rel = executor.callAndWait(() -> {
				return Optional.ofNullable(Association_c.getOneR_RELOnR201(
						ModelClass_c.getOneO_OBJOnR102(Attribute_c.getOneO_ATTROnR106(rattr)),
						selected -> ((Association_c) selected).getNumb() == relNum));
			});
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException("Failed to find association 'R" + relNum + "'.", e);
		}

		// try with just the relationship number
		ReferredToClassInAssoc_c[] rtos = ReferredToClassInAssoc_c
				.getManyR_RTOsOnR203(ClassInAssociation_c.getManyR_OIRsOnR201(rel));
		if (rtos.length == 1) {
			return rtos[0];
		}

		// try with the class name and relationship number
		if (ctx.class_or_role != null && ctx.class_name == null) {
			rtos = ReferredToClassInAssoc_c.getManyR_RTOsOnR203(ClassInAssociation_c.getManyR_OIRsOnR201(rel,
					selected -> ModelClass_c.getOneO_OBJOnR201((ClassInAssociation_c) selected).getName()
							.equals(visit(ctx.class_or_role))));
			if (rtos.length == 1) {
				return rtos[0];
			}
		}

		// try with the class name, relationship number, and role phrase
		if (ctx.class_or_role != null && ctx.class_name != null) {
			rtos = ReferredToClassInAssoc_c.getManyR_RTOsOnR203(ClassInAssociation_c.getManyR_OIRsOnR201(rel,
					selected -> ModelClass_c.getOneO_OBJOnR201((ClassInAssociation_c) selected).getName()
							.equals(visit(ctx.class_name))
							&& ((ClassInAssociation_c) selected).Get_text_phrase().equals(visit(ctx.class_or_role))));
			if (rtos.length == 1) {
				return rtos[0];
			}
		}

		throw new CoreImport.XtumlLoadException("Failed to find referred to object '" + ctx.getText() + "'.");
	}

	@Override
	public Attribute_c visitDerived_attribute_definition(Derived_attribute_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;

		// find or create attribute
		final String attrName = (String) visit(ctx.attr_name);
		final Attribute_c attr = Attribute_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
				IdAssigner.NULL_UUID, "", "", "", "", 0, IdAssigner.NULL_UUID, "", "",
				modelClass.getPath() + "::" + attrName);
		final BaseAttribute_c battr = new BaseAttribute_c(modelRoot);
		battr.relateAcrossR106To(attr);
		final DerivedBaseAttribute_c dbattr = new DerivedBaseAttribute_c(modelRoot);
		dbattr.relateAcrossR107To(battr);

		// set attribute name and description
		attr.setRoot_nam(attrName);
		attr.setDescrip(ctx.description() != null ? ctx.description().getText().lines()
				.map(line -> line.replace("//!", "").strip()).collect(Collectors.joining(System.lineSeparator())) : "");

		// set data type
		attr.relateAcrossR114To((DataType_c) visit(ctx.type_reference()));

		// set up type dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		attr.setDimensions(dimString);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, attr);
		for (int i = 0; i < dims.size(); i++) {
			attr.Resizedimensions(i, dims.get(i), dims.size());
		}

		// set action semantics
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		dbattr.setDialect(Actiondialect_c.oal); // TODO set dialect to OAL
		dbattr.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		dbattr.setAction_semantics_internal((String) visit(ctx.action_body()));

		// link to the class
		attr.relateAcrossR102To(modelClass);

		return attr;
	}

	@Override
	public ClassIdentifier_c visitIdentifier_definition(Identifier_definitionContext ctx) {
		final ClassIdentifier_c oid = (ClassIdentifier_c) currentRoot;
		for (String attrName : ctx.attrs.stream().map(this::visitName).collect(Collectors.toList())) {
			final Attribute_c attr = Attribute_c.getOneO_ATTROnR102(ModelClass_c.getOneO_OBJOnR104(oid),
					selected -> ((Attribute_c) selected).getName().equals(attrName));
			if (attr != null) {
				final ClassIdentifierAttribute_c oida = new ClassIdentifierAttribute_c(modelRoot);
				oida.relateAcrossR105To(attr);
				oida.relateAcrossR105To(oid);
			} else {
				throw new CoreImport.XtumlLoadException("Failed to find attribute '" + attrName + "'.");
			}
		}
		return oid;
	}

	@Override
	public Operation_c visitOperation_definition(Operation_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;

		// find or create operation
		final String operationName = (String) visit(ctx.operation_name);
		final Operation_c tfr = Operation_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
				operationName, "", IdAssigner.NULL_UUID, 0, "", 0, "", IdAssigner.NULL_UUID, 0, 0,
				modelClass.getPath() + "::" + operationName);

		// set name and description
		tfr.setName(operationName);
		tfr.setDescrip(ctx.description() != null ? ctx.description().getText().lines()
				.map(line -> line.replace("//!", "").strip()).collect(Collectors.joining(System.lineSeparator())) : "");

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(OPERATION_NUM)) {
			tfr.setNumb(marks.get(OPERATION_NUM).getInteger());
		}

		// set instance based
		tfr.setInstance_based(ctx.class_based != null ? Scope_c.Class : Scope_c.Instance);

		// link deferral
		if (ctx.RelName() != null) {
			final int relNum = Integer.parseInt(ctx.RelName().getText().substring(1));
			final Association_c rel;
			try {
				rel = executor.callAndWait(() -> Optional.ofNullable(Association_c.getOneR_RELOnR206(
						SubtypeSupertypeAssociation_c.getManyR_SUBSUPsOnR212(
								ClassAsSupertype_c.getManyR_SUPERsOnR204(ReferredToClassInAssoc_c
										.getManyR_RTOsOnR203(ClassInAssociation_c.getManyR_OIRsOnR201(modelClass)))),
						selected -> ((Association_c) selected).getNumb() == relNum)));
			} catch (Exception e) {
				throw new CoreImport.XtumlLoadException("Could not find relationship for deferred operation: " + ctx.RelName(), e);
			}
			final Deferral_c def = new Deferral_c(modelRoot);
			def.relateAcrossR126To(rel);
			def.relateAcrossR126To(tfr);
			// TODO set required/optional
		}

		// set return type
		tfr.relateAcrossR116To((DataType_c) visit(ctx.type_reference()));

		// set the return array dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		tfr.setReturn_dimensions(dimString);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, tfr);
		for (int i = 0; i < dims.size(); i++) {
			tfr.Resizereturn_dimensions(i, dims.get(i), dims.size());
		}

		// handle parameters
		if (ctx.parameter_list() != null) {
			currentRoot = tfr;
			visit(ctx.parameter_list());
			currentRoot = modelClass;
		}

		// set action semantics
		tfr.setDialect(Actiondialect_c.oal); // TODO set dialect to OAL
		tfr.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		if (ctx.action_body() != null) {
			tfr.setAction_semantics_internal((String) visit(ctx.action_body()));
		}

		// link to class
		tfr.relateAcrossR115To(modelClass);

		return tfr;
	}

	@Override
	public OperationParameter_c visitParameter_list(Parameter_listContext ctx) {
		// link parameters to each other in order
		OperationParameter_c prevTparm = null;
		for (ParameterContext paramCtx : ctx.parameter()) {
			final OperationParameter_c o_tparm = (OperationParameter_c) visit(paramCtx);
			if (prevTparm != null) {
				prevTparm.relateAcrossR124ToPrecedes(o_tparm);
			}
			prevTparm = o_tparm;
		}
		return prevTparm;
	}

	@Override
	public OperationParameter_c visitParameter(ParameterContext ctx) {
		final Operation_c tfr = (Operation_c) currentRoot;

		// create a new parameter
		final OperationParameter_c o_tparm = new OperationParameter_c(modelRoot);
		o_tparm.setName((String) visit(ctx.param_name));

		// set by value/ref
		o_tparm.setBy_ref("in".equals(ctx.by_ref.getText()) ? 0 : 1);

		// link the data type
		o_tparm.relateAcrossR118To((DataType_c) visit(ctx.type_reference()));

		// set the array dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		o_tparm.setDimensions(dimString);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, o_tparm);
		for (int i = 0; i < dims.size(); i++) {
			o_tparm.Resizedimensions(i, dims.get(i), dims.size());
		}

		// link to message
		o_tparm.relateAcrossR117To(tfr);

		return o_tparm;
	}

}